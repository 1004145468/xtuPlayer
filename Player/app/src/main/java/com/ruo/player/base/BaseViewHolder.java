package com.ruo.player.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/28.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder{

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public abstract void setData(Object obj);
}
