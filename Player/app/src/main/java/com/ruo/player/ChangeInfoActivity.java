package com.ruo.player;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.ruo.player.Utils.BmobUtils;
import com.ruo.player.Utils.DialogUtils;
import com.ruo.player.base.BaseTitleBackActivity;
import com.ruo.player.entries.Player;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/4/3.
 */

public class ChangeInfoActivity extends BaseTitleBackActivity {

    @BindView(R.id.changeinfo_content)
    EditText contentView;
    private String nickStr;
    private Player currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = BmobUtils.getCurrentUser();
        if (currentUser != null) {
            nickStr = currentUser.getNick();
            contentView.setText(nickStr);
        }
    }

    @OnClick(R.id.changeinfo_save)
    public void updateUserInfo() {
        final String newNick = contentView.getText().toString().trim();
        if (TextUtils.isEmpty(newNick)) {
            DialogUtils.showToast(this, "昵称不能为空");
            return;
        }
        if (newNick.equals(nickStr)) {
            return;
        }
        //更新昵称
        DialogUtils.showIndeterminateDialog(this,"正在更新昵称");
        currentUser.setNick(newNick);
        BmobUtils.updateInfo(currentUser, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                DialogUtils.shutdownIndeterminateDialog();
                if (e != null) {
                    DialogUtils.showToast(ChangeInfoActivity.this, "昵称更新失败");
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("nick", newNick);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
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
