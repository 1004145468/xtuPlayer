package com.ruo.player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ruo.player.R;
import com.ruo.player.base.BaseViewHolder;
import com.ruo.player.entries.NetVideoModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/30.
 */

public class NetVideoAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ArrayList<NetVideoModel> mDatas;
    private LayoutInflater mLayoutInflater;

    public NetVideoAdapter(Context context, ArrayList<NetVideoModel> mDatas) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mLayoutInflater.inflate(R.layout.item_netvideo, parent, false);
        return new NetVideoHolder(rootView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class NetVideoHolder extends BaseViewHolder {

        private NetVideoModel model;

        @BindView(R.id.item_netvideo_title)
        TextView titleView;
        @BindView(R.id.item_netvideo_img)
        SimpleDraweeView imgView;

        public NetVideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Object obj) {
            if (obj == null) {
                return;
            }
            model = (NetVideoModel) obj;
            titleView.setText(model.getTitle());
            imgView.setImageURI(model.getImgPath());
        }
    }
}
