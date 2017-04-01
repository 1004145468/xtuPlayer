package com.ruo.player.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ruo.player.HomeActivity;
import com.ruo.player.MediaPlayActivity;
import com.ruo.player.UserCenterActivity;
import com.ruo.player.service.WindowService;

/**
 * Created by Administrator on 2017/3/28.
 * 统跳
 */

public class PLGT {

    //进入主页
    public static void gotoHomeActivity(Context context) {
        if (context instanceof Activity) {
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
        }
    }

    //跳转进行媒体的播放
    public static void gotoMediaPlayActivity(Context context, String fileName, String filePath, int seekto) {
        if (context instanceof Activity) {
            Intent intent = new Intent(context, MediaPlayActivity.class);
            intent.putExtra("mediaName", fileName);
            intent.putExtra("mediaPath", filePath);
            intent.putExtra("seekTo", seekto);
            context.startActivity(intent);
        }
    }

    //跳转进行媒体的播放
    public static void gotoMediaPlayActivityByService(Context context, String fileName, String filePath, int seekto) {
        Intent intent = new Intent(context, MediaPlayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("mediaName", fileName);
        intent.putExtra("mediaPath", filePath);
        intent.putExtra("seekTo", seekto);
        context.startActivity(intent);
    }

    /**
     * 进入用户管理中心
     *
     * @param context
     */
    public static void gotoUserCenterActivity(Context context) {
        if (context instanceof Activity) {
            Intent intent = new Intent(context, UserCenterActivity.class);
            context.startActivity(intent);
        }
    }

    /**
     * 开启window 服务
     *
     * @param context
     * @param videoName
     * @param videoPath
     */
    public static void openWindowService(Context context, String videoName, String videoPath, int seekTo) {
        if (context instanceof Activity) {
            Intent intent = new Intent(context, WindowService.class);
            intent.putExtra("videoName", videoName);
            intent.putExtra("videoPath", videoPath);
            intent.putExtra("seekTo", seekTo);
            context.startService(intent);
        }

    }
}
