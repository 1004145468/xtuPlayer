package com.ruo.player.decoraion;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ruo.player.Utils.DensityUtils;


public class PlayerEmptyItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, DensityUtils.dip2px(view.getContext(), 15));
    }
}
