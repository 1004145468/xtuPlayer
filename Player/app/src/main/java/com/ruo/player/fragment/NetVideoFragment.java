package com.ruo.player.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruo.player.Interface.OnResultAttachListener;
import com.ruo.player.R;
import com.ruo.player.Utils.NetUtils;
import com.ruo.player.adapter.NetVideoAdapter;
import com.ruo.player.decoraion.PlayerEmptyItemDecoration;
import com.ruo.player.entries.NetVideoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/30.
 */

public class NetVideoFragment extends Fragment {

    private static final String TAG = "NetVideoFragment";

    @BindView(R.id.netvideo_list)
    RecyclerView mNetVideoListView;

    private ArrayList<NetVideoModel> mDatas;
    private NetVideoAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_netvideo, container, false);
        ButterKnife.bind(this, mRootView);
        initViews();
        initDatas();
        return mRootView;
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mNetVideoListView.setLayoutManager(linearLayoutManager);
        mNetVideoListView.addItemDecoration(new PlayerEmptyItemDecoration());
        mDatas = new ArrayList<>();
        mAdapter = new NetVideoAdapter(getActivity(), mDatas);
        mNetVideoListView.setAdapter(mAdapter);
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("http://is.snssdk.com/neihan/stream/mix/v1/?")
                .append("mpic=1&webp=1&essence=1&content_type=-104&message_cursor=-1")
                .append("&am_longitude=112.866052&am_latitude=27.878534&am_city=%E6%B9%98%E6%BD%AD%E5%B8%82")
                .append("&am_loc_time=1490848959231&count=30")
                .append("&min_time=" + SystemClock.currentThreadTimeMillis())
                .append("&screen_width=1440&double_col_mode=0&iid=8094100419&device_id=35121824822&ac=wifi")
                .append("&channel=tengxun&aid=7&app_name=joke_essay&version_code=601&version_name=6.0.1&device_platform=android")
                .append("&ssmix=a&device_type=SM919&device_brand=SMARTISAN&os_api=23&os_version=6.0.1")
                .append("&uuid=990007181217339&openudid=d51fcc7a0ab54dd5&manifest_version_code=601&resolution=1440*2560&dpi=560&update_version_code=6011");

        Log.d(TAG, "initDatas: " + urlBuilder.toString());

        NetUtils.getNetVideosByGet(getActivity(), urlBuilder.toString(), new OnResultAttachListener<List<NetVideoModel>>() {
            @Override
            public void done(List<NetVideoModel> models) {
                if(models != null && models.size() > 0){
                    mDatas.addAll(models);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }


}
