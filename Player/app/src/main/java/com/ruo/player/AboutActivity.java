package com.ruo.player;

import com.ruo.player.base.BaseTitleBackActivity;

/**
 * Created by Administrator on 2017/4/3.
 */

public class AboutActivity extends BaseTitleBackActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public String getBarTitle() {
        return "关于";
    }
}
