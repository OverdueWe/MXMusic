package com.mxnavi.gf.server.provider.network.cache;

import com.mxnavi.gf.server.provider.network.exception.ServerException;
import com.mxnavi.gf.misc.util.MxLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 描述 ： Post请求方式拦截器
 *
 * @author Mark
 * @date 2018.09.14
 */

public class EnhancedCacheInterceptor implements Interceptor {

    private static final String TAG = "EnhancedCacheInterceptor";

    /**
     * 请求方法POST
     */
    private static final String REQUEST_METHOD_POST = "POST";

    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();
        HttpUrl httpUrl = request.url();
        RequestBody requestBody = request.body();
        Charset charset = Charset.forName("UTF-8");
        StringBuilder sb = new StringBuilder();
        sb.append(httpUrl.toString());
        if (REQUEST_METHOD_POST.equals(request.method())) {
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(Charset.forName("UTF-8"));
            }
            Buffer buffer = new Buffer();
            try {
                requestBody.writeTo(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.append(assembleKey(buffer.readString(charset)));
            buffer.close();
        }
        MxLog.d(TAG, "EnhancedCacheInterceptor -> key:" + sb.toString());
        Response response = null;
        try {
                response = chain.proceed(request);
                    ResponseBody responseBody = response.body();
                    MediaType contentType = responseBody.contentType();

                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE);
                    Buffer buffer = source.buffer();

                    if (contentType != null) {
                        charset = contentType.charset(Charset.forName("UTF-8"));
                    }
                    String key = sb.toString();
                    String json = buffer.clone().readString(charset);
                    CacheManager.getInstance().putCache(key, json);
                    MxLog.d(TAG, "put cache-> key:" + key + "-> json:" + json);
        } catch (Exception e) {
            MxLog.d(TAG,"Exception : " + e.toString());
            e.printStackTrace();
            throw new ServerException(request);
        }
        return response;
    }

    /**
     * 重新拼装key 去掉其中的时间戳及签名
     *
     * @param content
     * @return
     */
    private String assembleKey(String content) {
        String key = "";
        try {
            JSONObject jsonObject = new JSONObject(content);
            key = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return key;
    }


}
