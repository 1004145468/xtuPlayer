package com.ruo.player;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.ruo.player.Utils.BmobUtils;
import com.ruo.player.Utils.DialogUtils;
import com.ruo.player.Utils.PLGT;
import com.ruo.player.base.BaseActivity;
import com.ruo.player.entries.Player;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/4/3.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_forgetpsw)
    TextView forgetPswView;

    @BindView(R.id.login_user)
    EditText mUserView;

    @BindView(R.id.login_psw)
    EditText mPswView;

    @OnClick(R.id.login_forgetpsw)
    public void forgetPsw() {
        PLGT.gotoForgetPswActivity(this);
    }


    @OnClick(R.id.login_btn)
    public void login() {
        String username = mUserView.getText().toString().trim();
        String password = mPswView.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            DialogUtils.showToast(this, "用户名或密码不能为空！");
            return;
        }
        BmobUtils.login(username, password, new SaveListener<Player>() {
            @Override
            public void done(Player player, BmobException e) {
                if (e != null) {
                    DialogUtils.showToast(LoginActivity.this, "用户名或密码错误！");
                } else {
                    PLGT.gotoHomeActivity(LoginActivity.this);
                    finish();
                }
            }
        });
    }

    @OnClick(R.id.login_regeist)
    public void openRegeistActivity() {
        PLGT.gotoRegeistActivity(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        forgetPswView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }
}
