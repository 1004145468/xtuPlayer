package com.ruo.player.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ruo.player.R;
import com.ruo.player.Utils.DataBaseUtils;
import com.ruo.player.Utils.PLGT;
import com.ruo.player.entries.HistoryVideoModel;
import com.ruo.player.entries.NetVideoModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/31.
 */

public class NetVideoAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<NetVideoModel> mDatas;

    public NetVideoAdapter(Context mContext, List<NetVideoModel> mDatas) {
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NetVideoHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_netvideo, parent, false);
            holder = new NetVideoHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (NetVideoHolder) convertView.getTag();
        }
        holder.setData(mDatas.get(position));
        return convertView;
    }

    class NetVideoHolder {

        private NetVideoModel model;

        @BindView(R.id.item_netvideo_title)
        TextView titleView;
        @BindView(R.id.item_netvideo_img)
        SimpleDraweeView imgView;

        public NetVideoHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void setData(Object obj) {
            if (obj == null) {
                return;
            }
            model = (NetVideoModel) obj;
            titleView.setText(model.getTitle());
            imgView.setImageURI(model.getImgPath());
        }

        @OnClick(R.id.item_netvideo_play)
        public void playVideo() {
            //存入数据库
            HistoryVideoModel historyVideoModel = new HistoryVideoModel(model.getTitle(), model.getVideopath(), model.getImgPath());
            DataBaseUtils.saveVideoModel(mContext,historyVideoModel);
            PLGT.gotoMediaPlayActivity(mContext, model.getTitle(), model.getVideopath(), 0);
        }
    }
}
