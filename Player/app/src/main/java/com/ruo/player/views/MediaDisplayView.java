package com.ruo.player.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruo.player.R;

/**
 * Created by Administrator on 2017/3/27.
 */

public class MediaDisplayView extends FrameLayout {

    private final ImageView mCoverView;
    private final TextView mTitleView;

    public MediaDisplayView(Context context) {
        super(context);
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_launcher, this, true);//内填充
        mCoverView = (ImageView) rootView.findViewById(R.id.launcher_cover);
        mTitleView = (TextView) rootView.findViewById(R.id.launcher_title);
    }

    public void setCoverImage(Bitmap bitmap) {
        mCoverView.setImageBitmap(bitmap);
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }


}
