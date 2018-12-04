package com.mxnavi.gf.backstage.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mxnavi.gf.misc.UtilManager;

/**
 * describle : MusicService
 *
 * @author Mark
 * @date 2018.12.03
 */

public class MusicService extends Service{

    public final static int GRAY_SERVICE_ID = 1001;

    @Override
    public void onCreate() {
        super.onCreate();
        //工具類初始化
        UtilManager.getInstance().init(getApplication());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, GrayService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
