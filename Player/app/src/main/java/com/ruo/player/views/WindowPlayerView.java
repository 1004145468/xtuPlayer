package com.ruo.player.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ruo.player.R;
import com.ruo.player.Utils.PLGT;
import com.ruo.player.media.IRenderView;
import com.ruo.player.media.IjkVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by Administrator on 2017/3/31.
 */

public class WindowPlayerView extends FrameLayout {

    @BindView(R.id.window_play)
    ImageView showView;

    @BindView(R.id.window_videoview)
    IjkVideoView ijkVideoView;

    private String videoname;
    private String videopath;

    public WindowPlayerView(@NonNull Context context) {
        super(context);
        View rootView = LayoutInflater.from(context).inflate(R.layout.view_windowplayerview, this, true);
        ButterKnife.bind(this, rootView);
        ijkVideoView.setAspectRatio(IRenderView.AR_ASPECT_WRAP_CONTENT); //设置视频展示的样式
    }

    public void setVideoInfo(String videoname, String videopath, final int seekto) {
        this.videoname = videoname;
        this.videopath = videopath;
        ijkVideoView.setVideoPath(videopath);
        ijkVideoView.start();
        ijkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                showView.setImageResource(R.drawable.select_pause_grep);
                ijkVideoView.seekTo(seekto);
            }
        });
        ijkVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                return false;
            }
        });
        ijkVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                showView.setImageResource(R.drawable.select_play_grep); //设置播放图标
            }
        });
    }


    @OnClick(R.id.window_play)
    public void setBtnClick() {
        if (ijkVideoView == null) {
            return;
        }
        if (ijkVideoView.isPlaying()){
            ijkVideoView.pause();
            showView.setImageResource(R.drawable.select_play_grep);
        }else{
            ijkVideoView.start();
            showView.setImageResource(R.drawable.select_pause_grep);
        }
    }

    @OnClick(R.id.window_delete)
    public void exitWindow(){
        ijkVideoView.pause();
        ijkVideoView.release(true);
        if(mListener != null){
            mListener.onExit();
        }
    }

    @OnClick(R.id.window_controller)
    public void openMediaActivity() {
        int currentIndex = ijkVideoView.getCurrentPosition();
        ijkVideoView.pause();
        ijkVideoView.release(true);
        if(mListener != null){
            mListener.onScale(currentIndex);
        }
    }

    private onWindowOperationListener mListener;
    public void setonRemoveWindowListener(onWindowOperationListener mListener) {
        this.mListener = mListener;
    }
    public interface onWindowOperationListener{
        void onExit();
        void onScale(int currentIndex);
    }
}
