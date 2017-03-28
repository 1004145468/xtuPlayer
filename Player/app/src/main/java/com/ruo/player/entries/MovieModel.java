package com.ruo.player.entries;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;

import com.ruo.player.Utils.BitmapUtils;

import java.lang.ref.SoftReference;

/**
 * 视频实体类
 * Created by Administrator on 2017/3/26.
 */

public class MovieModel {

    private String movieName; //视频名称
    private String filePath;  // 视频路径
    private String fileSize;  // 视频大小
    private int movieType;  //视频类型
    private final Bitmap bitmap; //视频省略图

    public MovieModel(int width, int height, String movieName, String filePath, String fileSize, int movieType) {
        this.movieName = movieName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.movieType = movieType;
        bitmap = BitmapUtils.getVideoThumbnail(filePath, width, height, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public int getMovieType() {
        return movieType;
    }

    public void setMovieType(int movieType) {
        this.movieType = movieType;
    }

    public Bitmap getThumbnail() {
        return bitmap;
    }
}
