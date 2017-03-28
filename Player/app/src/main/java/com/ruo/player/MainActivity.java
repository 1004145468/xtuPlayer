package com.ruo.player;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ruo.player.Utils.DialogUtils;
import com.ruo.player.Utils.MediaUtils;
import com.ruo.player.adapter.ImageAdapter;
import com.ruo.player.base.BaseActivity;
import com.ruo.player.entries.MovieModel;
import com.ruo.player.pagetransformer.Transformer3D;
import com.ruo.player.views.MediaDisplayView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, MediaDisplayView.OnBtnOnclickListener {

    private static final String TAG = "MainActivity";

    private final int READSTORAGE_CODE = 1;

    private List<MediaDisplayView> mDatas;

    @BindView(R.id.launcher_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        new AsyncTask<Void, Void, List<MovieModel>>() {
            @Override
            protected List<MovieModel> doInBackground(Void... params) {
                List<MovieModel> mDatas = MediaUtils.getMediasFromMediaStore(MainActivity.this);
                if (mDatas == null || mDatas.size() < 1) {
                    return null;
                }
                return mDatas;
            }

            @Override
            protected void onPostExecute(List<MovieModel> movies) {
                if (movies == null || movies.size() < 1) {
                    return;
                }
                mDatas = new ArrayList<>();
                MediaDisplayView view = null;
                for (MovieModel model : movies) {
                    view = new MediaDisplayView(MainActivity.this);
                    view.setCoverImage(model.getThumbnail());
                    view.setTitle(model.getMovieName());
                    view.setOnBtnOnclickListener(MainActivity.this);
                    mDatas.add(view);
                }
                ImageAdapter imageAdapter = new ImageAdapter(mDatas);
                mViewPager.setAdapter(imageAdapter);
                mViewPager.setPageTransformer(true, new Transformer3D());
                mViewPager.addOnPageChangeListener(MainActivity.this);
            }

        }.execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READSTORAGE_CODE && grantResults != null
                && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //授予成功
            initViews();
        } else {
            DialogUtils.showToast(this,getString(R.string.readsdcard_permission_fail));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        MediaDisplayView displayView = mDatas.get(position);
        if (position == 0) {
            displayView.setBackViewVisiable(false);
            displayView.setForwardViewVisiable(true);
        } else if (position == mDatas.size() - 1) {
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
}
