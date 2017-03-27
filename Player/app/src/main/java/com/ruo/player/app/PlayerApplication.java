package com.ruo.player.app;

import android.app.Application;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by Administrator on 2017/3/24.
 */

public class PlayerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化ijkPlayer
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
    }
}
