package com.ruo.player.pagetransformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Administrator on 2017/3/27.
 * 主要是控制旋转和透明
 */

public class Transformer3D implements ViewPager.PageTransformer {

    private final int maxRotate = 30; // 最大旋转角度

    @Override
    public void transformPage(View page, float position) {
        if (position < -1 || position > 1) {
            page.setRotationY(0);
        } else {
            page.setPivotX(page.getMeasuredWidth() / 2);
            page.setPivotY(page.getMeasuredHeight() / 2);
            page.setRotationY(maxRotate * position);
            page.setScaleX(1- Math.abs(position) * 0.5f);
           // page.setScaleY(1- Math.abs(position) * 0.5f);
        }
    }
}
