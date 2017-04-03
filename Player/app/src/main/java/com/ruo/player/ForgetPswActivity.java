package com.ruo.player;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ruo.player.R;
import com.ruo.player.Utils.BmobUtils;
import com.ruo.player.base.BaseTitleBackActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetPswActivity extends BaseTitleBackActivity {

    @BindView(R.id.et_modify_content)
    EditText mModifyContentView;
    @BindView(R.id.tv_motify_result)
    TextView mModifyResultView;
    @BindView(R.id.btn_operation)
    Button operatonBtn;

    @Override
    public String getBarTitle() {
        return "重置密码";
    }

    //重置密码
    @OnClick(R.id.btn_operation)
    public void startModity() {
        mModifyResultView.setText("");
        String email = mModifyContentView.getText().toString();
        if (TextUtils.isEmpty(email)) {
            return;
        }
        operatonBtn.setText("正在重置");
        BmobUtils.resetPasswordByEmail(email, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                operatonBtn.setText("确　定");
                if (e == null) {
                    mModifyResultView.setText("密码重置邮件已发送至邮箱！");
                } else {
                    mModifyResultView.setText("密码重置失败，请重新尝试！");
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forgetpsw;
    }
}
