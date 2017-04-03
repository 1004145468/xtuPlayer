package com.ruo.player;

import android.os.Bundle;

import com.ruo.player.base.BaseTitleBackActivity;

/**
 * Created by Administrator on 2017/4/3.
 */

public class AboutActivity extends BaseTitleBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public String getBarTitle() {
        return "关于";
    }
}
