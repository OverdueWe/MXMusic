package com.mxnavi.gf.misc.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mxnavi.gf.misc.util.MxLog;

/**
 * 描述 ： 网络状态工具类
 *
 * @author Mark
 * @date 2018.09.14
 */

public class NetUtil {
    /**
     * 没有网络
     */
    private static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    private static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    private static final int NETWORK_WIFI = 1;

    private Context mContext;

    private NetUtil() {

    }

    private static class NetUtilHolder {
        public static NetUtil sInstance = new NetUtil();
    }

    public static NetUtil getInstance() {
        return NetUtilHolder.sInstance;
    }

    public void init(Context context) {
        mContext = context;
        //实例化IntentFilter对象
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        NetBroadcastReceiver netBroadcastReceiver = new NetBroadcastReceiver();
        //注册广播接收
        mContext.registerReceiver(netBroadcastReceiver, filter);
    }

    private int getNetWorkState(Context context) {
        //得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        //如果网络连接，判断该网络类型
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;//wifi
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;//mobile
            }
        } else {
            //网络异常
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }

    /**
     * 是否有网
     * @return
     */
    public boolean hasNetWork() {
        if (mContext != null) {
            int netState = getNetWorkState(mContext);
            if (netState == NETWORK_MOBILE || netState == NETWORK_WIFI) {
                return true;
            }
        }
        return false;
    }

    public class NetBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // 如果相等的话就说明网络状态发生了变化
            MxLog.d("NetBroadcastReceiver", "NetBroadcastReceiver changed");
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                int netWorkState = getNetWorkState(context);
                // 当网络发生变化，判断当前网络状态，并通过NetEvent回调当前网络状态
            }
        }

    }

}