package com.ruo.player;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ruo.player.Utils.BmobUtils;
import com.ruo.player.Utils.PLGT;
import com.ruo.player.entries.Player;
import com.ruo.player.fragment.LocalVideoFragment;
import com.ruo.player.fragment.NetVideoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/30.
 */

public class HomeActivity extends FragmentActivity {

    private static final int REQUEST_USERINFO = 1;

    @BindView(R.id.home_netvideo)
    ImageView mNetVideoView;
    @BindView(R.id.home_localvideo)
    ImageView mLocalVideoView;
    @BindView(R.id.home_head)
    SimpleDraweeView mHeadView;

    private List<Fragment> mFragmentFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Player currentUser = BmobUtils.getCurrentUser();
        if (currentUser != null) {
            mHeadView.setImageURI(currentUser.getHeadUrl());
        }
    }

    private void initFragment() {
        mFragmentFactory = new ArrayList<>();
        NetVideoFragment netVideoFragment = new NetVideoFragment();
        LocalVideoFragment localVideoFragment = new LocalVideoFragment();
        mFragmentFactory.add(netVideoFragment);
        mFragmentFactory.add(localVideoFragment);
        getSupportFragmentManager().beginTransaction().add(R.id.home_content, netVideoFragment).commit();
    }

    @OnClick(R.id.home_netvideo)
    public void loadNetVideoFragment() {
        selectFragment(0);
    }

    @OnClick(R.id.home_localvideo)
    public void loadLocalVideoFragment() {
        selectFragment(1);

    }

    @OnClick(R.id.home_head)
    public void gotoUserCenter() {
        PLGT.gotoUserCenterActivity(this, REQUEST_USERINFO);
    }

    /**
     * 选择展示哪一个fragment
     *
     * @param index
     */
    private void selectFragment(int index) {
        //切换显示
        mNetVideoView.setImageResource(index == 0 ? R.drawable.netvideo_press : R.drawable.netvideo_normal);
        mLocalVideoView.setImageResource(index == 0 ? R.drawable.localvideo_normal : R.drawable.localvideo_press);
        //切换内容面板
        getSupportFragmentManager().beginTransaction().replace(R.id.home_content, mFragmentFactory.get(index)).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_USERINFO && resultCode == RESULT_OK) {
            PLGT.gotoLoginActivity(this);
            finish();
        }
    }
}
