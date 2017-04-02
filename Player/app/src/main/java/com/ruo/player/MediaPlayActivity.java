package com.ruo.player;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ruo.player.Utils.DensityUtils;
import com.ruo.player.Utils.DialogUtils;
import com.ruo.player.Utils.PLGT;
import com.ruo.player.base.BaseActivity;
import com.ruo.player.media.IRenderView;
import com.ruo.player.media.IjkVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by Administrator on 2017/3/28.
 */

public class MediaPlayActivity extends BaseActivity {

    private static final String TAG = "MediaPlayActivity";
    private static final int UPDATE_UI = 0x110;
    private static final int HIDE_VIEW = 0x111;


    private final int ADJUST_GAP = 54;

    @BindView(R.id.media_view)
    IjkVideoView mVideoView;
    @BindView(R.id.media_title)
    TextView mTitleView;
    @BindView(R.id.media_lockorunlock)
    ImageView mLockorUnLockView;
    @BindView(R.id.media_progress)
    SeekBar mProgressView;
    @BindView(R.id.media_btn)
    ImageView mBtnView;
    @BindView(R.id.media_currenttime)
    TextView mCurrentTimeView;
    @BindView(R.id.media_totaltime)
    TextView mTotalTimeView;
    @BindView(R.id.media_controller)
    View mControllerView;
    @BindView(R.id.media_centerIcon)
    ImageView mCenterIconView;
    @BindView(R.id.media_centerprogress)
    ProgressBar mCenterProgressView;
    @BindView(R.id.media_centeradjust)
    View mCenterAdjustView;

    private int mScreenWidth = 0;
    private int mScreenHeight = 0;

    private ObjectAnimator topEnterAnim;
    private ObjectAnimator topOutAnim;
    private ObjectAnimator bottonEnterAnim;
    private ObjectAnimator bottonOutAnim;

    private GestureDetector mGestureDetector;

    private boolean showController = false;
    private boolean lockScreen = false;

    private String mediaName;
    private String mediaPath;
    private int seekTo;

    private AudioManager mAudioManager;
    private int mMaxAudioVoice;

    private Handler TimerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_UI:
                    int currenttime = mVideoView.getCurrentPosition();
                    mProgressView.setProgress(currenttime);
                    displayFormatTime(mCurrentTimeView, currenttime);
                    TimerHandler.sendEmptyMessageDelayed(UPDATE_UI, 500);
                    break;
                case HIDE_VIEW:
                    mCenterAdjustView.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplay);
        ButterKnife.bind(this);
        initConfig();
        initAnim();
        initViews();
    }

    private void initConfig() {
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mMaxAudioVoice = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mGestureDetector = new GestureDetector(this, new VideoGestureListener());
    }

    /**
     * 初始化动画
     */
    private void initAnim() {
        mTitleView.setPivotX(mScreenWidth / 2);
        mTitleView.setPivotY(0);
        topEnterAnim = ObjectAnimator.ofFloat(mTitleView, View.SCALE_Y, 0, 1);
        topEnterAnim.setDuration(500);
        topEnterAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mTitleView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                showController = true;
            }
        });
        topOutAnim = ObjectAnimator.ofFloat(mTitleView, View.SCALE_Y, 1, 0);
        topOutAnim.setDuration(500);
        topOutAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mTitleView.setVisibility(View.INVISIBLE);
                showController = false;
            }
        });
        mControllerView.setPivotX(mScreenWidth / 2);
        mControllerView.setPivotY(DensityUtils.dip2px(this, 60));
        bottonEnterAnim = ObjectAnimator.ofFloat(mControllerView, View.SCALE_Y, 0, 1);
        topEnterAnim.setDuration(500);
        topEnterAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mControllerView.setVisibility(View.VISIBLE);
            }
        });
        bottonOutAnim = ObjectAnimator.ofFloat(mControllerView, View.SCALE_Y, 1, 0);
        topOutAnim.setDuration(500);
        topOutAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mControllerView.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initViews() {

        mediaName = getIntent().getStringExtra("mediaName");
        mediaPath = getIntent().getStringExtra("mediaPath");
        seekTo = getIntent().getIntExtra("seekTo", 0);

        //MediaView
        mVideoView.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT); //设置视频展示的样式
        mVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                Log.d(TAG, "onPrepared: 准备完成");
                int totaltime = mVideoView.getDuration();
                mProgressView.setMax(totaltime);
                mProgressView.setProgress(0);
                mBtnView.setImageResource(R.drawable.movie_ctrlbar_btn_pause_selected); //设置播放按钮
                displayFormatTime(mTotalTimeView, totaltime);
                mVideoView.seekTo(seekTo);
                TimerHandler.sendEmptyMessage(UPDATE_UI);
            }
        });
        mVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                pausePlay();
            }
        });
        mVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                DialogUtils.showToast(MediaPlayActivity.this, getString(R.string.play_error));
                return false;
            }
        });

        //ProgressSeekBar
        mProgressView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                pausePlay();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                startPlay();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mVideoView.seekTo(progress);
                    displayFormatTime(mCurrentTimeView, progress);
                }
            }
        });

        if (!TextUtils.isEmpty(mediaPath)) {
            mTitleView.setText(mediaName);
            mVideoView.setVideoPath(mediaPath);
            mVideoView.start();
        }
    }

    @OnClick(R.id.media_btn)
    public void startorpauseVideo() {
        if (mVideoView.isPlaying()) {
            pausePlay();
        } else {
            startPlay();
        }
    }

    @OnClick(R.id.media_lockorunlock)
    public void lockOrunLockScreen() {
        lockScreen = !lockScreen;
        if (lockScreen) {
            mLockorUnLockView.setImageResource(R.drawable.select_lock);
        } else {
            mLockorUnLockView.setImageResource(R.drawable.select_unlock);
        }
    }

    @OnClick(R.id.media_screencontroller)
    public void addVideoToScreen() {
        PLGT.openWindowService(this, mediaName, mediaPath, mVideoView.getCurrentPosition());
        finish();
    }

    private void pausePlay() {
        mVideoView.pause();
        mBtnView.setImageResource(R.drawable.select_play);
        TimerHandler.removeMessages(UPDATE_UI);
    }

    private void startPlay() {
        mVideoView.start();
        mBtnView.setImageResource(R.drawable.select_pause);
        TimerHandler.sendEmptyMessage(UPDATE_UI);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausePlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TimerHandler = null;
        mVideoView.release(true);
    }

    /**
     * 展示格式化的时间
     *
     * @param textview
     * @param millsion
     */
    private void displayFormatTime(TextView textview, int millsion) {
        int second = millsion / 1000;
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        String timeStr = String.format("%02d:%02d:%02d", hh, mm, ss);
        textview.setText(timeStr);

    }

    /**
     * 调节播放声音
     *
     * @param percent
     */
    private void adjustVoice(float percent) {
        TimerHandler.removeMessages(HIDE_VIEW);
        mCenterAdjustView.setVisibility(View.VISIBLE);
        mCenterIconView.setImageResource(R.drawable.voice);
        mCenterProgressView.setMax(mMaxAudioVoice);
        int currentVoice = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        currentVoice = (int) (currentVoice + percent * mMaxAudioVoice);
        if (currentVoice > mMaxAudioVoice) {
            currentVoice = mMaxAudioVoice;
        }
        if (currentVoice < 0) {
            currentVoice = 0;
        }
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVoice, 0);
        mCenterProgressView.setProgress(currentVoice);
        TimerHandler.sendEmptyMessageDelayed(HIDE_VIEW, 800);
    }

    /**
     * 调整屏幕亮度
     *
     * @param percent
     */
    private void adjustBrissness(float percent) {
        TimerHandler.removeMessages(HIDE_VIEW);
        mCenterAdjustView.setVisibility(View.VISIBLE);
        mCenterIconView.setImageResource(R.drawable.brightness);
        mCenterProgressView.setMax(1000);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        float currentBrightness = attributes.screenBrightness + percent;
        if (currentBrightness < 0.01) {
            currentBrightness = 0.01f;
        }
        if (currentBrightness > 1) {
            currentBrightness = 1;
        }
        attributes.screenBrightness = currentBrightness;
        getWindow().setAttributes(attributes);
        mCenterProgressView.setProgress((int) (currentBrightness * 1000));
        TimerHandler.sendEmptyMessageDelayed(HIDE_VIEW, 800);
    }

    /**
     * 调整播放进度
     *
     * @param percent
     */
    private void adjustVideoProgress(float percent) {
        pausePlay();
        int videototaltime = mVideoView.getDuration();
        int currentTime = mVideoView.getCurrentPosition();
        currentTime = (int) (currentTime + videototaltime * percent);
        if (currentTime > videototaltime) {
            currentTime = videototaltime;
        }
        if (currentTime < 0) {
            currentTime = 0;
        }
        mVideoView.seekTo(currentTime);
        startPlay();
    }


    class VideoGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float startX = e1.getX();
            float startY = e1.getY();
            float deltaX = e2.getX() - startX;
            float deltaY = e2.getY() - startY;
            if (Math.abs(deltaX) > ADJUST_GAP) {
                float percentX = deltaX / mScreenWidth * 0.3f;  //加上阻尼  调整进度
                adjustVideoProgress(percentX);
            } else if (Math.abs(deltaY) > ADJUST_GAP) {
                float percentY = deltaY / mScreenHeight * 0.5f;
                if (startX < mScreenWidth / 2) {  //调整亮度
                    adjustBrissness(-percentY);
                } else if (startX > mScreenWidth / 2) {
                    adjustVoice(-percentY);   //调整声音
                }
            }
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (showController) {
                topOutAnim.start();
                bottonOutAnim.start();
            } else if (!lockScreen) {
                topEnterAnim.start();
                bottonEnterAnim.start();
            }
            return false;
        }
    }
}
