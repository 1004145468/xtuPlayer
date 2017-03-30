package com.ruo.player.entries;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class OriginNetVideoModel {

    private String message;
    private NoteBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NoteBean getData() {
        return data;
    }

    public void setData(NoteBean data) {
        this.data = data;
    }

    public class NoteBean {

        private ArrayList<MediaInfo> data;

        public List<MediaInfo> getData() {
            return data;
        }

        public void setData(ArrayList<MediaInfo> data) {
            this.data = data;
        }

    }

    public class MediaInfo {

        private MediaGroup group;

        public MediaGroup getGroup() {
            return group;
        }

        public void setGroup(MediaGroup group) {
            this.group = group;
        }
    }

    public class MediaGroup {
        private String mp4_url;
        private String text;
        private ImageCover large_cover;

        public String getMp4_url() {
            return mp4_url;
        }

        public void setMp4_url(String mp4_url) {
            this.mp4_url = mp4_url;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public ImageCover getLarge_cover() {
            return large_cover;
        }

        public void setLarge_cover(ImageCover large_cover) {
            this.large_cover = large_cover;
        }
    }

    public class ImageCover {

        private String uri;

        public String getUri() {
            return "http://pb3.pstatp.com/" + uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }
}
