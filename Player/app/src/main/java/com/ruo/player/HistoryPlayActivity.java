package com.ruo.player;

import com.ruo.player.base.BaseTitleBackActivity;

/**
 * Created by Administrator on 2017/4/3.
 */

public class HistoryPlayActivity extends BaseTitleBackActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_historyplay;
    }

    @Override
    public String getBarTitle() {
        return "播放历史";
    }
}
