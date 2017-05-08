package com.ruo.player;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ruo.player.Utils.BmobUtils;
import com.ruo.player.Utils.DialogUtils;
import com.ruo.player.Utils.PLGT;
import com.ruo.player.base.BaseTitleBackActivity;
import com.ruo.player.entries.Player;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/4/3.
 */

public class LoginActivity extends BaseTitleBackActivity {

    @BindView(R.id.login_forgetpsw)
    TextView forgetPswView;

    @BindView(R.id.login_user)
    EditText mUserView;

    @BindView(R.id.login_psw)
    EditText mPswView;

    @BindView(R.id.login_usercontainer)
    View mUserContainerView;

    @BindView(R.id.login_pswcontainer)
    View mPswContainerView;

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
        mUserContainerView.setVisibility(View.INVISIBLE);
        mPswContainerView.setVisibility(View.INVISIBLE);
        DialogUtils.showIndeterminateDialog(this, "正在登陆");
        BmobUtils.login(username, password, new SaveListener<Player>() {
            @Override
            public void done(Player player, BmobException e) {
                mUserContainerView.setVisibility(View.VISIBLE);
                mPswContainerView.setVisibility(View.VISIBLE);
                DialogUtils.shutdownIndeterminateDialog();
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
        forgetPswView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public String getBarTitle() {
        return "登录";
    }
}
