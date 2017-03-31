package com.ruo.player;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.ruo.player.Utils.MediaUtils;
import com.ruo.player.Utils.NetUtils;
import com.ruo.player.Utils.PLGT;
import com.ruo.player.base.BaseActivity;

/**
 * Created by Administrator on 2017/3/31.
 */

public class SplashActivity extends BaseActivity {

    private final int READ_SDCARD_CODE = 1;
    private Handler mHanlder = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initTimer();
        initPermission();
        initDatas();
    }

    private void initTimer() {
        mHanlder.postDelayed(new Runnable() {
            @Override
            public void run() {
                NetUtils.cancelNetVideosByGet(); //取消网络请求
                PLGT.gotoHomeActivity(SplashActivity.this);
                finish();
            }
        }, 3000);
    }

    /**
     * 首次申请授权
     */
    private void initPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_SDCARD_CODE);
        } else {
            scanLocalVideo();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READ_SDCARD_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            scanLocalVideo();
        }
    }

    private void scanLocalVideo() {
        MediaUtils.getMediasFromMediaStore(this);
    }

    private void initDatas() {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("http://is.snssdk.com/neihan/stream/mix/v1/?")
                .append("mpic=1&webp=1&essence=1&content_type=-104&message_cursor=-1")
                .append("&am_longitude=112.866052&am_latitude=27.878534&am_city=%E6%B9%98%E6%BD%AD%E5%B8%82")
                .append("&am_loc_time=1490848959231&count=30")
                .append("&min_time=" + SystemClock.currentThreadTimeMillis())
                .append("&screen_width=1440&double_col_mode=0&iid=8094100419&device_id=35121824822&ac=wifi")
                .append("&channel=tengxun&aid=7&app_name=joke_essay&version_code=601&version_name=6.0.1&device_platform=android")
                .append("&ssmix=a&device_type=SM919&device_brand=SMARTISAN&os_api=23&os_version=6.0.1")
                .append("&uuid=990007181217339&openudid=d51fcc7a0ab54dd5&manifest_version_code=601&resolution=1440*2560&dpi=560&update_version_code=6011");
        NetUtils.getNetVideosByGet(this, urlBuilder.toString(), null);
    }
}
