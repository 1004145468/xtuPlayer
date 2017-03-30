package com.ruo.player;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ruo.player.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/30.
 */

public class UserCenterActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercenter);
        ButterKnife.bind(this);
    }
}
