package com.mxnavi.gf.backstage.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import static com.mxnavi.gf.backstage.service.MusicService.GRAY_SERVICE_ID;

/**
 * describle : 灰色保活Service
 *
 * @author Mark
 * @date 2018.12.03
 */

public class GrayService extends Service{

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(GRAY_SERVICE_ID, new Notification());
        stopForeground(true);
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
