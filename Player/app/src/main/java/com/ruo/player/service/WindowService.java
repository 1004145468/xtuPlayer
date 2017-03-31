package com.ruo.player.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ruo.player.Utils.PLGT;
import com.ruo.player.Utils.WindowUtils;
import com.ruo.player.views.WindowPlayerView;

public class WindowService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        addViewsToScreen(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void addViewsToScreen(Intent intent) {
        String videoName = intent.getStringExtra("videoName");
        String videoPath = intent.getStringExtra("videoPath");
        int seekTo = intent.getIntExtra("seekTo", 0);
        final WindowPlayerView windowPlayerView = new WindowPlayerView(WindowService.this);
        windowPlayerView.setVideoInfo(videoName, videoPath, seekTo);
        windowPlayerView.setonRemoveWindowListener(new WindowPlayerView.onWindowOperationListener() {
            @Override
            public void onExit() {
                //移除窗口
                WindowUtils.removeScreenView(WindowService.this,windowPlayerView);
                stopSelf();
            }

            @Override
            public void onScale() {
              //  PLGT.gotoMediaPlayActivity(getContext(), videoname, videopath);
            }
        });
        WindowUtils.addScreenView(this, windowPlayerView);
    }
}
