package com.ruo.player.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ruo.player.AboutActivity;
import com.ruo.player.ChangeInfoActivity;
import com.ruo.player.ForgetPswActivity;
import com.ruo.player.HomeActivity;
import com.ruo.player.LoginActivity;
import com.ruo.player.MediaPlayActivity;
import com.ruo.player.RegeistActivity;
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
    public static void gotoUserCenterActivity(Context context, int requestCode) {
        if (context instanceof Activity) {
            Intent intent = new Intent(context, UserCenterActivity.class);
            ((Activity) context).startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 打开登录界面
     *
     * @param context
     */
    public static void gotoLoginActivity(Context context) {
        if (context instanceof Activity) {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }
    }

    /**
     * 打开修改用户信息的界面
     *
     * @param context
     */
    public static void gotoChangeInfoActivity(Context context) {
        if (context instanceof Activity) {
            Intent intent = new Intent(context, ChangeInfoActivity.class);
            ((Activity) context).startActivityForResult(intent, UserCenterActivity.CHANGE_NICK_CODE);
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

    /**
     * 打开注册页面
     *
     * @param context
     */
    public static void gotoRegeistActivity(Context context) {
        if (context instanceof Activity) {
            Intent intent = new Intent(context, RegeistActivity.class);
            context.startActivity(intent);
        }
    }

    /**
     * 忘记密码界面
     *
     * @param context
     */
    public static void gotoForgetPswActivity(Context context) {
        if (context instanceof Activity) {
            Intent intent = new Intent(context, ForgetPswActivity.class);
            context.startActivity(intent);
        }
    }


    /**
     * 打开软件介绍界面
     *
     * @param context
     */
    public static void gotoAboutActivity(Context context) {
        if (context instanceof Activity) {
            Intent intent = new Intent(context, AboutActivity.class);
            context.startActivity(intent);
        }
    }

    /**
     * 打开媒体图片选择界面
     *
     * @param context
     */
    public static void gotoImagePickActivity(Context context) {
        if (context instanceof Activity) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            ((Activity) context).startActivityForResult(intent, UserCenterActivity.CHANGE_IMAGE_CODE);
        }
    }
}
