package com.ruo.player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.ruo.player.base.BaseActivity;
import com.ruo.player.media.AndroidMediaController;
import com.ruo.player.media.IRenderView;
import com.ruo.player.media.IjkVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/28.
 */

public class MediaPlayActivity extends BaseActivity {

    @BindView(R.id.view_mediaplayer)
    IjkVideoView mVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplay);
        ButterKnife.bind(this);
        mVideoView.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT); //设置视频展示的样式
        mVideoView.setMediaController(new AndroidMediaController(this,true));  //设置默认的播放控制器
        String mediaPath = getIntent().getStringExtra("mediaPath");  //如果指定了数据打开
        if(!TextUtils.isEmpty(mediaPath)){
            mVideoView.setVideoPath(mediaPath);
            mVideoView.start();
        }

    }
}
