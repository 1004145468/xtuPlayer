package com.ruo.player.Utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.format.Formatter;

import com.ruo.player.entries.LocalMovieModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/26.
 */

public class MediaUtils {

    /**
     * 获取手机上面所有扫描到的视频文件
     *
     * @return 视频实体集
     */
    public static List<LocalMovieModel> getMediasFromMediaStore(Context context) {
        String[] projections = new String[]{MediaStore.Video.VideoColumns.DISPLAY_NAME, MediaStore.Video.VideoColumns.DATA,
                MediaStore.Video.VideoColumns.MIME_TYPE, MediaStore.Video.VideoColumns.SIZE};
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projections, null, null, null);
        if (cursor == null || cursor.getCount() < 1) {
            return null;
        }
        cursor.moveToFirst();
        ArrayList<LocalMovieModel> mDatas = new ArrayList<>();
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        LocalMovieModel model = null;
        do {
            String title = cursor.getString(cursor.getColumnIndex(projections[0]));      //视频标题
            String filePath = cursor.getString(cursor.getColumnIndex(projections[1]));  // 视频路径
            int audioType = cursor.getInt(cursor.getColumnIndex(projections[2]));  // 视频类型
            long totalfilesize = cursor.getLong(cursor.getColumnIndex(projections[3]));
            String fileSize = Formatter.formatFileSize(context, totalfilesize);    //视频大小
            model = new LocalMovieModel(screenWidth,screenHeight,title, filePath, fileSize, audioType);
            mDatas.add(model);
        } while (cursor.moveToNext());
        //覆盖原来的数据
        DataBaseUtils.clearLocalVideo(context);
        //存入新的数据
        DataBaseUtils.saveVideoModels(context,mDatas);
        return mDatas;
    }


}
