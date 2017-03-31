package com.ruo.player.Utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import static android.R.attr.width;

/**
 * Created by YL on 2017/3/9.
 */

public class WindowUtils {

    /**
     * 添加半屏界面
     */
    public static void addScreenView(final Context context, View contentView) {
        if (contentView == null) {
            return;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = DensityUtils.dip2px(context, 320);
        params.height = DensityUtils.dip2px(context, 200);
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.RGBA_8888;
        params.gravity = (Gravity.CENTER);
        windowManager.addView(contentView, params);
    }

    public static void removeScreenView(Context context, View contentView) {
        if (contentView == null) {
            return;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.removeView(contentView);
    }
}
