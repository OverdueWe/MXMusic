package com.mxnavi.gf.mxmusic;

import android.app.Application;
import android.content.Intent;

import com.mxnavi.gf.server.service.MusicService;

/**
 * describle : MusicApplication
 *
 * @author Mark
 * @date 2018.12.03
 */

public class MusicApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, MusicService.class));
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
