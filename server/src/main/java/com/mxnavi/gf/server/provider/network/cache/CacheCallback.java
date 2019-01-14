package com.mxnavi.gf.server.provider.network.cache;

/**
 * 描述 ：缓存回调
 *
 * @author Mark
 * @date 2018.09.14
 */

public interface CacheCallback {

    /**
     * 缓存回调
     * @param cache
     */
    void onGetCache(String cache);
}
