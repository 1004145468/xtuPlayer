package com.ruo.player.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import cn.bmob.v3.Bmob;
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
        //初始化图片加载器
        Fresco.initialize(this);
        Bmob.initialize(this, "2e8eda190e4c5a811cdb5aecf2ecc160");
    }
}
