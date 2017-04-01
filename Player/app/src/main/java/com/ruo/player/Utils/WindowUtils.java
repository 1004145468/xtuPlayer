package com.ruo.player.Utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import static android.R.attr.x;
import static android.R.attr.y;

public class WindowUtils {

    /**
     * 添加半屏界面
     */
    public static void addScreenView(final Context context, View contentView) {
        if (contentView == null) {
            return;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = getDefaultLayoutParams(context);
        params.gravity = (Gravity.CENTER);
        windowManager.addView(contentView, params);
    }

    /**
     * 从屏幕上移除控件
     *
     * @param context
     * @param contentView
     */
    public static void removeScreenView(Context context, View contentView) {
        if (contentView == null) {
            return;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.removeView(contentView);
    }

    /**
     * 更新view的位置
     *  @param context
     * @param contentView
     * @param rawX
     * @param rawY
     */
    public static void refreshScreenViewPosition(Context context, View contentView, float rawX, float rawY) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = getDefaultLayoutParams(context);
        params.gravity = Gravity.LEFT | Gravity.TOP; //左上为坐标系
        params.x = (int) (rawX - contentView.getMeasuredWidth() / 2);
        params.y = (int) (rawY - contentView.getMeasuredHeight() / 2);
        windowManager.updateViewLayout(contentView, params);
    }

    private static WindowManager.LayoutParams getDefaultLayoutParams(Context context) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = DensityUtils.dip2px(context, 320);
        params.height = DensityUtils.dip2px(context, 200);
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.RGBA_8888;
        return params;
    }
}
