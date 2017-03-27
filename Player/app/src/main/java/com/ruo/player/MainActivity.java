package com.ruo.player;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.ruo.player.Utils.MediaUtils;
import com.ruo.player.adapter.ImageAdapter;
import com.ruo.player.base.BaseActivity;
import com.ruo.player.entries.MovieModel;
import com.ruo.player.pagetransformer.Transformer3D;
import com.ruo.player.views.MediaDisplayView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private final int READSTORAGE_CODE = 1;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.launcher_pager);
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
                List<MovieModel> movies = MediaUtils.getMediasFromMediaStore(MainActivity.this);
                if (movies == null || movies.size() < 1) {
                    return null;
                }
                return movies;
            }

            @Override
            protected void onPostExecute(List<MovieModel> movies) {
                if (movies == null || movies.size() < 1) {
                    return;
                }
                ArrayList<MediaDisplayView> views = new ArrayList<>();
                MediaDisplayView view = null;
                for (MovieModel model : movies) {
                    view = new MediaDisplayView(MainActivity.this);
                    view.setCoverImage(model.getThumbnail());
                    view.setTitle(model.getMovieName());
                    views.add(view);
                }
                ImageAdapter imageAdapter = new ImageAdapter(views);
                mViewPager.setAdapter(imageAdapter);
                mViewPager.setPageTransformer(true, new Transformer3D());

            }

        }.execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READSTORAGE_CODE && grantResults != null
                && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //授予成功
            initViews();
        } else {
            Toast.makeText(this, "授权失败，无法展示", Toast.LENGTH_SHORT).show();
        }
    }
}
