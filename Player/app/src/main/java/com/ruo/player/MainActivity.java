package com.ruo.player;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruo.player.Utils.DialogUtils;
import com.ruo.player.Utils.MediaUtils;
import com.ruo.player.Utils.PLGT;
import com.ruo.player.adapter.LauncherImageAdapter;
import com.ruo.player.adapter.LauncherListAdapter;
import com.ruo.player.base.BaseActivity;
import com.ruo.player.decoraion.PlayerEmptyItemDecoration;
import com.ruo.player.entries.MovieModel;
import com.ruo.player.pagetransformer.Transformer3D;
import com.ruo.player.views.MediaDisplayView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, MediaDisplayView.OnBtnOnclickListener {

    private static final String TAG = "MainActivity";

    private final int READSTORAGE_CODE = 1;

    private List<MovieModel> mDatas;
    private List<MediaDisplayView> mViews;


    @BindView(R.id.launcher_pager)
    ViewPager mViewPager;

    @BindView(R.id.media_title)
    TextView mTitleView;

    @BindView(R.id.launcher_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.launchertype_bigpic)
    ImageView mBigPicTypeView;

    @BindView(R.id.launchertype_list)
    ImageView mListTypeView;

    @BindView(R.id.launcher_empty)
    ImageView mEmptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        int grantResult = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (grantResult != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READSTORAGE_CODE);
        } else {
            initViews();
        }
    }

    /**
     * 异步加载数据
     */
    private void initViews() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                mDatas = MediaUtils.getMediasFromMediaStore(MainActivity.this);
                return null;
            }

            @Override
            protected void onPostExecute(Void args) {
                if (mDatas == null || mDatas.size() < 1) {
                    return;
                }
                mEmptyView.setVisibility(View.GONE);
                //设置viewpager数据
                mViews = new ArrayList<>();
                MediaDisplayView view = null;
                for (MovieModel model : mDatas) {
                    view = new MediaDisplayView(MainActivity.this);
                    view.setCoverImage(model.getThumbnail());
                    view.setTitle(model.getMovieName());
                    view.setOnBtnOnclickListener(MainActivity.this);
                    mViews.add(view);
                }
                LauncherImageAdapter launcherImageAdapter = new LauncherImageAdapter(mViews);
                mViewPager.setAdapter(launcherImageAdapter);
                mViewPager.setPageTransformer(true, new Transformer3D());
                mViewPager.addOnPageChangeListener(MainActivity.this);
                //设置Recyclerview数据
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                mRecyclerView.addItemDecoration(new PlayerEmptyItemDecoration());
                mRecyclerView.setAdapter(new LauncherListAdapter(MainActivity.this, mDatas));
            }

        }.execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READSTORAGE_CODE && grantResults != null
                && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //授予成功
            initViews();
        } else {
            DialogUtils.showToast(this, getString(R.string.readsdcard_permission_fail));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        MediaDisplayView displayView = mViews.get(position);
        if (position == 0) {
            displayView.setBackViewVisiable(false);
            displayView.setForwardViewVisiable(true);
        } else if (position == mViews.size() - 1) {
            displayView.setBackViewVisiable(true);
            displayView.setForwardViewVisiable(false);
        } else {
            displayView.setBackViewVisiable(true);
            displayView.setForwardViewVisiable(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onBackBtnClick() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
    }

    @Override
    public void onForwardBtnClick() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
    }

    @Override
    public void onItemClick() {
        int currentIndex = mViewPager.getCurrentItem();
        MovieModel movieModel = mDatas.get(currentIndex);
        PLGT.gotoMediaPlayActivity(this, movieModel.getMovieName(),movieModel.getFilePath());
    }

    @OnClick(R.id.launchertype_bigpic)
    public void changeBigPicType() {
        mBigPicTypeView.setVisibility(View.GONE);
        mListTypeView.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.VISIBLE);
        mTitleView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @OnClick(R.id.launchertype_list)
    public void changeListType() {
        mListTypeView.setVisibility(View.GONE);
        mBigPicTypeView.setVisibility(View.VISIBLE);
        mTitleView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
    }
}
