package com.ruo.player.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.MotionEvent;
import android.widget.Toast;

import com.ruo.player.Utils.DialogUtils;
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
        final String videoName = intent.getStringExtra("videoName");
        final String videoPath = intent.getStringExtra("videoPath");
        int seekTo = intent.getIntExtra("seekTo", 0);
        final WindowPlayerView windowPlayerView = new WindowPlayerView(WindowService.this);
        windowPlayerView.setVideoInfo(videoName, videoPath, seekTo);
        windowPlayerView.setonRemoveWindowListener(new WindowPlayerView.onWindowOperationListener() {
            @Override
            public void onExit() {
                //移除窗口
                WindowUtils.removeScreenView(WindowService.this, windowPlayerView);
                stopSelf();
            }

            @Override
            public void onScale(int currentIndex) {
                //移除窗口
                WindowUtils.removeScreenView(WindowService.this, windowPlayerView);
                PLGT.gotoMediaPlayActivityByService(WindowService.this, videoName, videoPath, currentIndex);
                stopSelf();
            }

            @Override
            public void onScroll(MotionEvent e) {
                WindowUtils.refreshScreenViewPosition(WindowService.this, windowPlayerView, e.getRawX(), e.getRawY());
            }
        });
        //解决 oppo 手机没有授权，应用崩溃
        try {
            WindowUtils.addScreenView(this, windowPlayerView);
        } catch (Exception e) {
            e.printStackTrace();
            DialogUtils.showToast(this, "悬浮窗权限禁止");
            stopSelf();
        }
    }
}
