package com.ruo.player;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ruo.player.Utils.BmobUtils;
import com.ruo.player.Utils.DialogUtils;
import com.ruo.player.Utils.PLGT;
import com.ruo.player.base.BaseTitleBackActivity;
import com.ruo.player.entries.Player;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/3/30.
 */

public class UserCenterActivity extends BaseTitleBackActivity {

    public static final int CHANGE_IMAGE_CODE = 1;
    public static final int CHANGE_NICK_CODE = 2;

    @BindView(R.id.userinfo_head)
    SimpleDraweeView mHeadView;
    @BindView(R.id.userinfo_nick)
    TextView mNickView;

    private Player currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = BmobUtils.getCurrentUser();
        if (currentUser != null) {
            mHeadView.setImageURI(currentUser.getHeadUrl());
            mNickView.setText(currentUser.getNick());
        }
    }


    @OnClick(R.id.userinfo_head)
    public void loginOrHeadUpdateHead() {
        if (currentUser == null) {  //注册
            PLGT.gotoRegeistActivity(this);
        } else { //更改头像
            PLGT.gotoImagePickActivity(this);
        }
    }

    @OnClick(R.id.userinfo_nickcontainer)
    public void changeNick() {
        if (currentUser != null) {
            PLGT.gotoChangeInfoActivity(this);
        }
    }

    @OnClick(R.id.userinfo_playhistorycontainer)
    public void watchHistory() {

    }

    @OnClick(R.id.userinfo_sharecontainer)
    public void shareSofe() {
        DialogUtils.showShareDialog(this);
    }

    @OnClick(R.id.userinfo_aboutcontainer)
    public void showAbout() {
        PLGT.gotoAboutActivity(this);
    }

    /**
     * 退出登录
     */
    @OnClick(R.id.userinfo_exit)
    public void exit() {
        setResult(RESULT_OK, null);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CHANGE_IMAGE_CODE) {
                BmobUtils.uploadFile(this, data.getData(), new BmobUtils.onUploadFileResult() {
                    @Override
                    public void onResult(BmobException e, final String fileUrl) {
                        if (e != null) {
                            DialogUtils.showToast(UserCenterActivity.this, "头像上传失败！");
                        } else {
                            currentUser.setHeadUrl(fileUrl);
                            BmobUtils.updateInfo(currentUser, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e != null) {
                                        DialogUtils.showToast(UserCenterActivity.this, "头像更新失败！");
                                    } else {
                                        mHeadView.setImageURI(fileUrl);
                                    }
                                }
                            });
                        }
                    }
                });
            } else if (requestCode == CHANGE_NICK_CODE) {
                String newNick = data.getStringExtra("nick");
                mNickView.setText(newNick);
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_usercenter;
    }

    @Override
    public String getBarTitle() {
        return "个人中心";
    }
}
