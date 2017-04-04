package com.ruo.player.entries;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by Administrator on 2017/4/4.
 */

public class HistoryVideoModel extends RealmObject {

    public static final int EDITABLE = 1;
    public static final int NOTEDITABLE = 2;

    @Ignore
    private int status = NOTEDITABLE;

    private String title;
    private String videopath;
    private String imgPath;

    public HistoryVideoModel() {}

    public HistoryVideoModel(String title, String videopath, String imgPath) {
        this.title = title;
        this.videopath = videopath;
        this.imgPath = imgPath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideopath() {
        return videopath;
    }

    public void setVideopath(String videopath) {
        this.videopath = videopath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
