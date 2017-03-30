package com.ruo.player.entries;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2017/3/30.
 */

public class NetVideoModel extends RealmObject {

    private String title;
    private String videopath;
    private String imgPath;

    public NetVideoModel() {}

    public NetVideoModel(String title, String videopath, String imgPath) {
        this.title = title;
        this.videopath = videopath;
        this.imgPath = imgPath;
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
