package com.ruo.player.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruo.player.Interface.OnResultAttachListener;
import com.ruo.player.R;
import com.ruo.player.Utils.DataBaseUtils;
import com.ruo.player.entries.NetVideoModel;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class LocalVideoFragment extends Fragment {

    private static final String TAG = "LocalVideoFragment";

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_localvideo, container, false);
        DataBaseUtils.getNetVideo(getActivity(), new OnResultAttachListener<List<NetVideoModel>>() {
            @Override
            public void done(List<NetVideoModel> models) {
                int size = models.size();
                for (int i = 0; i < size; i++) {
                    NetVideoModel model = models.get(i);
                    Log.d(TAG, "Url: " + model.getVideopath() + " , text:" + model.getTitle() + ", img:" + model.getImgPath());
                }
            }
        });
        return mRootView;
    }
}
