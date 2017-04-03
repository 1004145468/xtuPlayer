package com.ruo.player.entries;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/4/3.
 */

public class Player extends BmobUser {

    private String nick;
    private String headUrl;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
