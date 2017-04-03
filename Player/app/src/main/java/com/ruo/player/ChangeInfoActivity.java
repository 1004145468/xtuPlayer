package com.ruo.player;

import com.ruo.player.base.BaseTitleBackActivity;

import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/3.
 */

public class ChangeInfoActivity extends BaseTitleBackActivity {

    @OnClick(R.id.changeinfo_save)
    public void updateUserInfo(){

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_changeinfo;
    }

    @Override
    public String getBarTitle() {
        return "昵称修改";
    }
}
