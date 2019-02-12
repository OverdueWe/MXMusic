package com.mxnavi.gf.pojo.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 描述 ：基础拦截器 封装一些公共参数
 *
 * @author Mark
 * @date 2018.12.04
 */

public class BasicInterceptor implements Interceptor {

    private static String TAG = "BasicInterceptor";

    /**
     * 请求方法-GET
     */
    private static final String REQUEST_METHOD_GET = "GET";

    /**
     * 请求方法POST
     */
    private static final String REQUEST_METHOD_POST = "POST";

    @Override
    public Response intercept(Chain chain) throws IOException {
        /**
         * 获取原先的请求对象
         */
        Request request = chain.request();
        if (REQUEST_METHOD_GET.equals(request.method())) {
            request = addGetBaseParams(request);
        } else if (REQUEST_METHOD_POST.equals(request.method())) {
            request = addPostBaseParams(request);
        }
        return chain.proceed(request);
    }

    /**
     * 添加GET方法基础参数
     *
     * @param request
     * @return
     */
    private Request addGetBaseParams(Request request) {
        HttpUrl httpUrl = request.url()
                .newBuilder()
                //TODO 封装参数
//                .addQueryParameter(REQUEST_APP_ID, mUserCert.getAppId())
                .build();

        /**
         * 添加签名
         */
        Set<String> nameSet = httpUrl.queryParameterNames();
        ArrayList<String> nameList = new ArrayList<>();
        nameList.addAll(nameSet);
        StringBuffer sign = new StringBuffer();
        Map<String, Object> paramMap = new HashMap<>(16);
        for (int i = 0; i < nameList.size(); i++) {
            paramMap.put(nameList.get(i), httpUrl.queryParameterValue(i));
            sign.append(nameList.get(i)).append(httpUrl.queryParameterValue(i));
        }
//        sign.append(mUserCert.getAppPrivateKey());
//
//        httpUrl = httpUrl.newBuilder().addQueryParameter(REQUEST_SIGN,
//                Util.stringToMD5(sign.toString())).build();
        request = request.newBuilder().url(httpUrl).build();
        return request;
    }

    /**
     * 添加POST方法基础参数
     *
     * @param request
     * @return
     */
    private Request addPostBaseParams(Request request) {
        /**
         * request.body() instanceof FormBody 为true的条件为：
         * 在ApiService 中将POST请求中设置
         * 1，请求参数注解为@FieldMap
         * 2，方法注解为@FormUrlEncoded
         */
        if (request.body() instanceof FormBody) {
            FormBody formBody = (FormBody) request.body();
            FormBody.Builder builder = new FormBody.Builder();
            //TODO 封装基本参数
            //builder.add();

            for (int i = 0; i < formBody.size(); i++) {
                //@FieldMap 注解 Map元素中 key 与 value 皆不能为null,否则会出现NullPointerException
                if (formBody.value(i) != null) {
                    builder.add(formBody.name(i), formBody.value(i));
                }
            }
            //TODO 设置签名

            request = request.newBuilder().post(formBody).build();
        }
        return request;
    }
}
