package com.ruo.player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruo.player.R;
import com.ruo.player.Utils.PLGT;
import com.ruo.player.base.BaseViewHolder;
import com.ruo.player.entries.LocalMovieModel;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/28.
 */

public class LocalVideoListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<LocalMovieModel> mDatas;

    public LocalVideoListAdapter(Context context, List<LocalMovieModel> mDatas) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mLayoutInflater.inflate(R.layout.item_launcherlist, parent, false);
        return new LauncherListHolder(rootView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class LauncherListHolder extends BaseViewHolder {

        private LocalMovieModel localMovieModel;

        @BindView(R.id.launcherlist_img)
        ImageView imgView;
        @BindView(R.id.launcherlist_title)
        TextView titleView;
        @BindView(R.id.launcherlist_size)
        TextView sizeView;
        @BindView(R.id.launcherlist_path)
        TextView pathView;

        public LauncherListHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(Object obj) {
            if (obj == null) {
                return;
            }
            localMovieModel = (LocalMovieModel) obj;
            if(localMovieModel.getThumbnail() != null){
                imgView.setImageBitmap(localMovieModel.getThumbnail());
            }
            titleView.setText(localMovieModel.getMovieName());
            sizeView.setText(localMovieModel.getFileSize());
            pathView.setText(localMovieModel.getFilePath());
        }

        @OnClick(R.id.launcher_root)
        public void onItemClick() {
            PLGT.gotoMediaPlayActivity(mContext, localMovieModel.getMovieName(), localMovieModel.getFilePath());
        }
    }
}
