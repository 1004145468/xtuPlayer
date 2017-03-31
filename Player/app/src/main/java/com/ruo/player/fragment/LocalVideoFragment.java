package com.ruo.player.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ruo.player.Interface.OnResultAttachListener;
import com.ruo.player.R;
import com.ruo.player.Utils.DataBaseUtils;
import com.ruo.player.Utils.MediaUtils;
import com.ruo.player.adapter.LocalVideoListAdapter;
import com.ruo.player.decoraion.PlayerEmptyItemDecoration;
import com.ruo.player.entries.LocalMovieModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/30.
 */

public class LocalVideoFragment extends Fragment {

    private static final int READ_SDCARD_CODE = 1;
    private static final String TAG = "LocalVideoFragment";

    private View mRootView;

    @BindView(R.id.launcher_recyclerview)
    RecyclerView mRecyclerView;

    private List<LocalMovieModel> mDatas;
    private LocalVideoListAdapter localVideoListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_localvideo, container, false);
            ButterKnife.bind(this, mRootView);
            initViews();
            localDbDatas();
            refreshData();
        }
        return mRootView;
    }

    private void initViews() {
        //设置Recyclerview数据
        mRecyclerView.requestFocus();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new PlayerEmptyItemDecoration());
        mDatas = new ArrayList<>();
        localVideoListAdapter = new LocalVideoListAdapter(getActivity(), mDatas);
        mRecyclerView.setAdapter(localVideoListAdapter);
    }

    /**
     * 先从数据库中取一批数据
     */
    private void localDbDatas() {
        DataBaseUtils.getLocalVideo(getActivity(), new OnResultAttachListener<List<LocalMovieModel>>() {
            @Override
            public void done(List<LocalMovieModel> localMovieModels) {
                if (localMovieModels != null && localMovieModels.size() > 0) {
                    mDatas.addAll(localMovieModels);
                    localVideoListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 刷新数据
     */
    private void refreshData() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_SDCARD_CODE);
        } else { //授予权限
            loadNewData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READ_SDCARD_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadNewData();
        }
    }

    /**
     * 加载最新的数据，并覆盖
     */
    private void loadNewData() {
        new AsyncTask<Void, Void, List<LocalMovieModel>>() {

            @Override
            protected List<LocalMovieModel> doInBackground(Void... params) {
                return MediaUtils.getMediasFromMediaStore(getActivity());
            }

            @Override
            protected void onPostExecute(List<LocalMovieModel> localMovieModels) {
                if (mDatas != null && mDatas.size() > 0) {
                    mDatas.clear();
                }
                if(localMovieModels != null && localMovieModels.size() > 0){
                    mDatas.addAll(localMovieModels);
                    localVideoListAdapter.notifyDataSetChanged();
                }
            }

        }.execute();
    }
}
