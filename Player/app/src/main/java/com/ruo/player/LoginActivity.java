package com.ruo.player;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ruo.player.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/3.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_forgetpsw)
    TextView forgetPswView;

    @OnClick(R.id.login_regeist)
    public void openRegeistActivity() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        forgetPswView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }
}
