package com.ruo.player.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruo.player.R;
import com.ruo.player.Utils.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/27.
 */

public class MediaDisplayView extends FrameLayout {

    @BindView(R.id.launcher_cover)
    ImageView mCoverView;
    @BindView(R.id.launcher_title)
    TextView mTitleView;
    @BindView(R.id.launcher_back)
    ImageView mBackView;
    @BindView(R.id.launcher_forward)
    ImageView mForwardView;

    public MediaDisplayView(Context context) {
        super(context);
        View rootView = LayoutInflater.from(context).inflate(R.layout.view_mediadisplay, this, true);//内填充
        ButterKnife.bind(this, rootView);
        mCoverView = (ImageView) rootView.findViewById(R.id.launcher_cover);
        mTitleView = (TextView) rootView.findViewById(R.id.launcher_title);
    }

    public void setCoverImage(Bitmap bitmap) {
        mCoverView.setImageBitmap(bitmap);
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    public void setBackViewVisiable(boolean visiable) {
        mBackView.setVisibility(visiable ? VISIBLE : GONE);
    }

    public void setForwardViewVisiable(boolean visiable) {
        mForwardView.setVisibility(visiable ? VISIBLE : GONE);
    }

    @OnClick(R.id.mediadisplay_play)
    public void onItemClick(){
        if (mListener != null) {
            mListener.onItemClick();
        }
    }

    @OnClick(R.id.launcher_back)
    public void onBackClick() {
        if (mListener != null) {
            mListener.onBackBtnClick();
        }
    }

    @OnClick(R.id.launcher_forward)
    public void onForwardClick() {
        if (mListener != null) {
            mListener.onForwardBtnClick();
        }
    }

    public interface OnBtnOnclickListener {
        void onBackBtnClick();
        void onForwardBtnClick();
        void onItemClick();
    }

    private OnBtnOnclickListener mListener;

    public void setOnBtnOnclickListener(OnBtnOnclickListener listener) {
        mListener = listener;
    }


}
