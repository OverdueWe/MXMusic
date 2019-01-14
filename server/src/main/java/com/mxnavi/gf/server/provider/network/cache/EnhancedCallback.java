package com.mxnavi.gf.server.provider.network.cache;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 描述 ：Post请求方式回调
 *
 * @author Mark
 * @date 2018.09.14
 */

public interface EnhancedCallback<T> {

    /**
     * 網絡回調
     * @param call
     * @param response
     */
    void onResponse(Call<T> call, Response<T> response);

    /**
     * 失败回调
     * @param call
     * @param t
     */
    void onFailure(Call<T> call, Throwable t);

    /**
     * 缓存回调
     * @param t
     */
    void onGetCache(T t);
}
