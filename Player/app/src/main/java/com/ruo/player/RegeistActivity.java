package com.ruo.player;

import android.text.TextUtils;
import android.widget.EditText;

import com.ruo.player.Utils.BmobUtils;
import com.ruo.player.Utils.DialogUtils;
import com.ruo.player.base.BaseTitleBackActivity;
import com.ruo.player.entries.Player;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/4/3.
 */

public class RegeistActivity extends BaseTitleBackActivity {

    @BindView(R.id.regeist_userid)
    EditText mUserView;

    @BindView(R.id.regeist_psw)
    EditText mPswView;

    @BindView(R.id.regeist_pswagain)
    EditText mPswAgainView;

    @OnClick(R.id.regeist_btn)
    public void regeistUser() {
        String userName = mUserView.getText().toString().trim();
        String passWord = mPswView.getText().toString().trim();
        String passwordAgain = mPswAgainView.getText().toString().trim();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord) || TextUtils.isEmpty(passwordAgain)) {
            DialogUtils.showToast(this, "请将信息填写完整");
            return;
        }
        if (!passWord.equals(passwordAgain)) {
            DialogUtils.showToast(this, "两次密码输入不一致！");
            return;
        }
        //开始注册
        BmobUtils.signUpWithFile(this, userName, passWord, null, new SaveListener<Player>() {
            @Override
            public void done(Player player, BmobException e) {
                if (e != null) {
                    if (e.getErrorCode() == 202) {
                        DialogUtils.showToast(RegeistActivity.this, "注册失败，请重新尝试!");
                    } else {
                        DialogUtils.showToast(RegeistActivity.this, "该用户名已存在!");
                    }
                } else {
                    DialogUtils.showToast(RegeistActivity.this, "注册成功!");
                    finish();
                }
            }
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_regeist;
    }

    @Override
    public String getBarTitle() {
        return "注册";
    }
}
