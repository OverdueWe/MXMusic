package com.mxnavi.gf.backstage.provider.network.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mxnavi.gf.backstage.provider.network.cache.EnhancedCacheInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 描述 ：网络请求 Retrofit Api
 *
 * @author Mark
 * @date 2018.08.08
 */
public class Api {

    private Context mContext;

    /**
     * 读超时长，单位：秒
     */
    private static final int READ_TIME_OUT = 25;

    /**
     * 写超时长，单位：秒
     */
    private static final int WRITE_TIME_OUT = 25;

    /**
     * 连接时长，单位：秒
     */
    private static final int CONNECT_TIME_OUT = 25;

    private Retrofit mRetrofit;

    private OkHttpClient mOkHttpClient;

    public Api(Context context) {

        mContext = context;

        /**
         * 开启Log
         */
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        /**
         * 增加头部信息
         */
//        Interceptor headerInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request build = chain.request().newBuilder()
//                        .addHeader("Content-Type", "application/json")
//                        .build();
//                return chain.proceed(build);
//            }
//        };

        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT,TimeUnit.SECONDS)
//                .addInterceptor(headerInterceptor)
                .addInterceptor(logInterceptor)
//                .addInterceptor(new BasicInterceptor(userCert))
                .addInterceptor(new EnhancedCacheInterceptor())
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .serializeNulls().create();

        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .baseUrl(ApiConstants.QQMUSIC_BASE_URL)
                .build();
    }

    public <T> T get(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }

    public <T> T getProxy(Class<T> tClass) {
        T t = mRetrofit.create(tClass);
//        return (T) Proxy.newProxyInstance(tClass.getClassLoader(),
//                new Class<?>[]{tClass}, new ProxyHandler(t));
        return mRetrofit.create(tClass);
    }

}
