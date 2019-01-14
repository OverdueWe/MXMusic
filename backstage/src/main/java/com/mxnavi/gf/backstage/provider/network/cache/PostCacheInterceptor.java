package com.mxnavi.gf.backstage.provider.network.cache;

import android.text.TextUtils;

import com.mxnavi.gf.misc.network.NetUtil;
import com.mxnavi.gf.misc.util.MxLog;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

import static okhttp3.internal.Util.closeQuietly;

/**
 * Created by Administrator on 2018/12/10.
 */

public class PostCacheInterceptor implements Interceptor {

    private static final String TAG = "PostCacheInterceptor";

    public PostCacheInterceptor() {
    }

    private final int REQUEST_URL = 0;
    private final int REQUEST_METHOD = 1;
    private final int REQUESTCONTENTTYPE = 2;
    private final int PROTOCAL = 3;
    private final int CODE = 4;
    private final int MESSAGE = 5;
    private final int REPONSE_BODY = 6;
    private final int MEDIA_TYPE = 7;
    private final int SETN_REQUEST_AT_MILLIS = 8;
    private final int RECEIVE_REPONSE_AT_MILLIS = 9;
    private final int CACHE_LENGTH = 10;


    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        if (!isNeedCache(chain.request().url().toString())) {
            return chain.proceed(chain.request());
        }

        //获取缓存
        String key = createKey(chain.request());
        MxLog.d(TAG, "cache key: " + key);
        Response cacheResponse = null;
        String cacheRes = key != null
                ? CacheManager.getInstance().getCache(key)
                : null;

        if (!TextUtils.isEmpty(cacheRes)) {
            MxLog.d(TAG, "cacheRes: " + cacheRes);
            cacheResponse = combineCacheToResponse(cacheRes);
        }

        //没有网络连接的时候读取缓存
        if (!NetUtil.getInstance().hasNetWork()) {
            MxLog.d(TAG, "no network connected jujge cache available");
            if (cacheResponse != null) {
                MxLog.d(TAG, "no network connected, return cache： " + cacheResponse);
                return cacheResponse;
            }
        }

        MxLog.d(TAG, "waiting for network response...");
        //获取网络响应
        Request netWorkRequest = chain.request();
        Response networkResponse = null;
        try {
            networkResponse = chain.proceed(netWorkRequest);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (networkResponse == null) {
                MxLog.d(TAG, "close cache response...");
                if (cacheResponse != null && HttpHeaders.hasBody(cacheResponse)) {
                    closeQuietly(cacheResponse.body());
                }
                return chain.proceed(netWorkRequest);
            }
        }
        MxLog.d(TAG, "prepare update cache response...");
        //更新缓存
        if (cacheResponse != null) {
            Response response = null;
            response = networkResponse.newBuilder()
                    .request(new Request.Builder()
                            .method("GET", null)
                            .url(netWorkRequest.url())
                            .headers(netWorkRequest.headers())
                            .tag(netWorkRequest.tag())
                            .build())
                    .build();
            MxLog.d(TAG, "update cache response");
            if (key != null) {
                CacheManager.getInstance().putCache(key, createCache(response));
            }
            if (cacheResponse != null && HttpHeaders.hasBody(cacheResponse)) {
                closeQuietly(cacheResponse.body());
            }
            return networkResponse;
        }

        Request newRequest = new Request.Builder()
                .method("GET", null)
                .url(netWorkRequest.url())
                .headers(netWorkRequest.headers())
                .tag(netWorkRequest.tag())
                .build();

        Response newResponse = networkResponse.newBuilder()
                .request(newRequest)
                .build();

        MxLog.d(TAG, "init cache response");
        //放入缓存
        MxLog.d(TAG, "url: " + netWorkRequest.url().toString());
        if (HttpHeaders.hasBody(newResponse)) {
            try {
                MxLog.d(TAG, "chain request url: " + newResponse.request().url());
                if (key != null) {
                    CacheManager.getInstance().putCache(key, createCache(newResponse));
                    MxLog.d(TAG, "put cache response key: " + key);
                }
//                    String resp1 = cache.getAsString(key);
//                    MxLog.d("resp1: " + resp1);
                return networkResponse;
            } catch (Exception e) {
                MxLog.d(TAG, "put cache exception: " + e);
            } finally {
                if (cacheResponse != null && HttpHeaders.hasBody(cacheResponse)) {
                    closeQuietly(cacheResponse.body());
                }
            }
        }
        return networkResponse;
    }


    private String createKey(Request request) {
        RequestBody requestBody = request.body();
        Charset charset = Charset.forName("UTF-8");
        String url = request.url().toString();
        StringBuilder sb = new StringBuilder();
        sb.append(url + "&");
        MediaType type = requestBody.contentType();
        if (type != null) {
            charset = type.charset() == null ? charset : type.charset();
        }
        Buffer buffer = new Buffer();
        try {
            requestBody.writeTo(buffer);
            sb.append(buffer.readString(charset));
        } catch (Exception e) {
            MxLog.d(TAG, "read request error: " + e);
        } finally {
            buffer.close();
        }
        //TODO guofeng
//        if (url.startsWith(BuildConfig.SERVER_URL + "your own url")) {
//            return //这里可以根据url来定制化key
//        }
        return sb.toString();
    }

    //根据键返回索引
    private int[] getIndexofKeyValue(String str, String originStr) {
        int[] indexs = new int[2];
        indexs[0] = originStr.indexOf(str);
        indexs[1] = originStr.indexOf("&", indexs[0]) >= 0 ? originStr.indexOf("&", indexs[0]) : originStr.length();
        MxLog.d(TAG, "index0: " + indexs[0] + " index1: " + indexs[1]);
        return indexs;
    }


    private boolean isNeedCache(String url) {
        //这里可以根据Url来判断是否需要缓存
        //TODO guofeng
        return true;
    }

    private Response combineCacheToResponse(String cache) {
        String[] caches = cache.split("&#&#");
        if (caches == null || caches.length <= 0) {
            return null;
        }
        Request request = new Request.Builder()
                .url(caches[REQUEST_URL])
                .method(caches[REQUEST_METHOD], null)
                .build();
        Response.Builder builder = new Response.Builder();
        try {
            builder.protocol(Protocol.get(caches[PROTOCAL]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.message(caches[MESSAGE])
                .code(Integer.valueOf(caches[CODE]))
                .request(request)
                .receivedResponseAtMillis(Long.valueOf(caches[RECEIVE_REPONSE_AT_MILLIS]))
                .sentRequestAtMillis(Long.valueOf(caches[SETN_REQUEST_AT_MILLIS]))
                .body(ResponseBody.create(MediaType.parse(caches[MEDIA_TYPE]), caches[REPONSE_BODY]))
                .build();
    }

    private String createCache(Response response) {
        String[] caches = new String[CACHE_LENGTH];
        caches[REQUEST_URL] = response.request().url().toString();
        caches[REQUEST_METHOD] = response.request().method();
        if (response.request().body() != null && response.request().body().contentType() != null) {
            caches[REQUESTCONTENTTYPE] = response.request().body().contentType().toString();
        } else {
            caches[REQUESTCONTENTTYPE] = "application/x-www-form-urlencoded";
        }
        caches[PROTOCAL] = response.protocol().toString();
        caches[CODE] = response.code() + "";
        caches[MESSAGE] = response.message();
        if (response.body() != null && response.body().contentType() != null) {
            caches[MEDIA_TYPE] = response.body().contentType().toString();
        } else {
            caches[MEDIA_TYPE] = "application/x-www-form-urlencoded";
        }
        caches[SETN_REQUEST_AT_MILLIS] = response.sentRequestAtMillis() + "";
        caches[RECEIVE_REPONSE_AT_MILLIS] = response.receivedResponseAtMillis() + "";
        if (HttpHeaders.hasBody(response)) {
            BufferedSource source = response.body().source();
            Buffer buffer = null;
            try {
                source.request(Long.MAX_VALUE);
                buffer = source.buffer();
                Charset charset = response.body().contentType().charset();
                if (charset == null) {
                    charset = Charset.forName("UTF-8");
                }
                caches[REPONSE_BODY] = buffer.clone().readString(charset);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
//                closeQuietly(response.body());
            }
        }
        String cache = "";
        for (String str : caches) {
            cache += str + "&#&#";
        }
        return cache;
    }

    static boolean isEndToEnd(String fieldName) {
        return !"Connection".equalsIgnoreCase(fieldName)
                && !"Keep-Alive".equalsIgnoreCase(fieldName)
                && !"Proxy-Authenticate".equalsIgnoreCase(fieldName)
                && !"Proxy-Authorization".equalsIgnoreCase(fieldName)
                && !"TE".equalsIgnoreCase(fieldName)
                && !"Trailers".equalsIgnoreCase(fieldName)
                && !"Transfer-Encoding".equalsIgnoreCase(fieldName)
                && !"Upgrade".equalsIgnoreCase(fieldName);
    }

    private String subString(String str, int[] index) {
        if (index == null || index.length < 2) {
            return null;
        }
        if (index[0] < 0 || index[1] < 0) {
            return null;
        }
        return str.substring(index[0], index[1]);
    }

}


