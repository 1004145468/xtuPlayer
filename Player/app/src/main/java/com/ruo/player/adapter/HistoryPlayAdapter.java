package com.ruo.player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ruo.player.R;
import com.ruo.player.Utils.DataBaseUtils;
import com.ruo.player.Utils.DialogUtils;
import com.ruo.player.Utils.PLGT;
import com.ruo.player.base.BaseViewHolder;
import com.ruo.player.entries.HistoryVideoModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/4.
 */

public class HistoryPlayAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private ArrayList<HistoryVideoModel> mDatas;
    private LayoutInflater mLayoutInflater;

    public HistoryPlayAdapter(Context context, ArrayList<HistoryVideoModel> mDatas) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mLayoutInflater.inflate(R.layout.item_historyvideo, parent, false);
        return new HistoryPlayHolder(rootView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class HistoryPlayHolder extends BaseViewHolder {

        private HistoryVideoModel model;

        @BindView(R.id.item_netvideo_title)
        TextView titleView;
        @BindView(R.id.item_netvideo_img)
        SimpleDraweeView imgView;
        @BindView(R.id.item_netvideo_play)
        ImageView playView;
        @BindView(R.id.item_netvideo_delete)
        ImageView deleteView;


        public HistoryPlayHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(Object obj) {
            if (obj == null) {
                return;
            }
            model = (HistoryVideoModel) obj;
            titleView.setText(model.getTitle());
            imgView.setImageURI(model.getImgPath());
            if (model.getStatus() == HistoryVideoModel.EDITABLE) {
                playView.setVisibility(View.GONE);
                deleteView.setVisibility(View.VISIBLE);
            } else if (model.getStatus() == HistoryVideoModel.NOTEDITABLE) {
                playView.setVisibility(View.VISIBLE);
                deleteView.setVisibility(View.GONE);
            }
        }

        @OnClick(R.id.item_netvideo_play)
        public void playVideo() {
            PLGT.gotoMediaPlayActivity(mContext, model.getTitle(), model.getVideopath(), 0);
        }

        @OnClick(R.id.item_netvideo_delete)
        public void deleteVideo() {  //删除记录
            DataBaseUtils.clearHistoryVideo(mContext, model);
        }


    }
}
