package com.ruo.player.Utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by YL on 2017/3/9.
 */

public class WindowUtils {

    /**
     * 添加半屏界面
     */
    public static void addScreenView(final Context context, View contentView, int width, int height) {
        if (contentView == null) {
            return;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = width;
        params.height = height;
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
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
