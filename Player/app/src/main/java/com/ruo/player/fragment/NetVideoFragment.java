package com.ruo.player.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ruo.player.Interface.OnResultAttachListener;
import com.ruo.player.R;
import com.ruo.player.Utils.DataBaseUtils;
import com.ruo.player.Utils.NetUtils;
import com.ruo.player.adapter.NetVideoAdapter;
import com.ruo.player.entries.NetVideoModel;
import com.ruo.player.views.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/30.
 */

public class NetVideoFragment extends Fragment {

    private static final String TAG = "NetVideoFragment";

    private View mRootView;

    @BindView(R.id.netvideo_list)
    ListView mNetVideoListView;

    @BindView(R.id.netvideo_refreshview)
    RefreshLayout mRefreshLayout;

    private ArrayList<NetVideoModel> mDatas;
    private NetVideoAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_netvideo, container, false);
            ButterKnife.bind(this, mRootView);
            initViews();
            loadLocalDatas();
            loadNetData(false);
        }
        return mRootView;
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        mDatas = new ArrayList<>();
        mAdapter = new NetVideoAdapter(getActivity(), mDatas);
        mNetVideoListView.setAdapter(mAdapter);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_blue_light);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNetData(false);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new RefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoad() {
                mNetVideoListView.setSelection(mDatas.size());
               loadNetData(true);
            }
        });
    }

    private void loadLocalDatas() {
        DataBaseUtils.getNetVideo(getActivity(), new OnResultAttachListener<List<NetVideoModel>>() {
            @Override
            public void done(List<NetVideoModel> localmodels) {
                if (localmodels != null && localmodels.size() > 0) {
                    mDatas.addAll(localmodels);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 加载网络数据
     */
    private void loadNetData(final boolean isLoadMore) {
        NetUtils.getNetVideosByGet(getActivity(), new OnResultAttachListener<List<NetVideoModel>>() {
            @Override
            public void done(List<NetVideoModel> models) {
                if (models != null && models.size() > 0) {
                    if (!isLoadMore && mDatas != null && mDatas.size() > 1) {
                        mDatas.clear();
                    }
                    mDatas.addAll(models);
                    mAdapter.notifyDataSetChanged();
                    if(mRefreshLayout.isRefreshing()){ //如果正在刷新
                        mRefreshLayout.setRefreshing(false);
                    }
                    if(mRefreshLayout.isLoading()){ //如果正在加载更多
                        mRefreshLayout.setLoading(false);
                    }
                }
            }
        });
    }


}
