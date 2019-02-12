package com.mxnavi.gf.misc;

import android.content.Context;

import com.mxnavi.gf.misc.network.NetUtil;

/**
 * describle : 工具管理类
 *
 * @author Mark
 * @date 2018.12.03
 */

public class UtilManager {

    private static final String TAG = "UtilManager";

    private UtilManager() {

    }

    private static class UtilManagerHolder{
        public static UtilManager sInstance = new UtilManager();
    }

    public static UtilManager getInstance() {
        return UtilManagerHolder.sInstance;
    }

    /**
     * 工具类初始化
     * @param context
     */
    public void init(Context context) {
        //网络工具初始化
        NetUtil.getInstance().init(context);
    }
}
