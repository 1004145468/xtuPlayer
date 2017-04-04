package com.ruo.player;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ruo.player.Interface.OnResultAttachListener;
import com.ruo.player.Utils.DataBaseUtils;
import com.ruo.player.adapter.HistoryPlayAdapter;
import com.ruo.player.base.BaseTitleBackActivity;
import com.ruo.player.decoraion.PlayerEmptyItemDecoration;
import com.ruo.player.entries.HistoryVideoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/3.
 */

public class HistoryPlayActivity extends BaseTitleBackActivity {

    private final int EDIT_MODE = 1;
    private final int NOTEDIT_MODE = 2;

    @BindView(R.id.history_playview)
    RecyclerView mHistoryPlayView;

    @BindView(R.id.history_editview)
    TextView mEditView;

    private ArrayList<HistoryVideoModel> mDatas = null;
    private HistoryPlayAdapter historyPlayAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initDatas();
    }

    //绑定展示的数据
    private void initViews() {
        mHistoryPlayView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mHistoryPlayView.addItemDecoration(new PlayerEmptyItemDecoration());
        mDatas = new ArrayList<>();
        historyPlayAdapter = new HistoryPlayAdapter(this, mDatas);
        mHistoryPlayView.setAdapter(historyPlayAdapter);
    }

    //绑定数据
    private void initDatas() {
        DataBaseUtils.getHistoryVideo(this, new OnResultAttachListener<List<HistoryVideoModel>>() {
            @Override
            public void done(List<HistoryVideoModel> historyVideoModels) {
                if (historyVideoModels != null && historyVideoModels.size() > 0) {
                    mDatas.addAll(historyVideoModels);
                    historyPlayAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    @OnClick(R.id.history_editview)
    public void editClick() {
        if (mEditView.getText().toString().equals("编辑")) { //进入编辑模式
            mEditView.setText("完成");
            changeDataStatus(EDIT_MODE);
        } else {
            mEditView.setText("编辑");  //进入非编辑模式
            changeDataStatus(NOTEDIT_MODE);
        }
    }

    //修改数据状态
    private void changeDataStatus(int mode) {
        if(mDatas == null || mDatas.size() < 1){
            return;
        }
        if(mode == EDIT_MODE) {
            for(HistoryVideoModel historyVideoModel : mDatas){
                historyVideoModel.setStatus(HistoryVideoModel.EDITABLE);
            }

        }else if(mode == NOTEDIT_MODE){
            for(HistoryVideoModel historyVideoModel : mDatas){
                historyVideoModel.setStatus(HistoryVideoModel.NOTEDITABLE);
            }
        }
        historyPlayAdapter.notifyDataSetChanged();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_historyplay;
    }

    @Override
    public String getBarTitle() {
        return "播放历史";
    }
}
