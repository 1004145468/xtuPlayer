package com.ruo.player.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ruo.player.MediaPlayActivity;

/**
 * Created by Administrator on 2017/3/28.
 */

public class PLGT {

    //跳转进行媒体的播放
    public static void gotoMediaPlayActivity(Context context, String filePath) {
        if (context instanceof Activity) {
            Intent intent = new Intent(context, MediaPlayActivity.class);
            intent.putExtra("mediaPath", filePath);
            context.startActivity(intent);
        }

    }
}
