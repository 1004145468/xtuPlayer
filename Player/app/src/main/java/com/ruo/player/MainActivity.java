package com.ruo.player;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.ruo.player.Utils.MediaUtils;
import com.ruo.player.adapter.ImageAdapter;
import com.ruo.player.base.BaseActivity;
import com.ruo.player.entries.MovieModel;
import com.ruo.player.views.Gallery3D;

import java.util.List;

public class MainActivity extends BaseActivity {

    private final int READSTORAGE_CODE = 1;
    private Gallery3D gallery3D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gallery3D = (Gallery3D) findViewById(R.id.launch_gallery3d);
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

    private void initViews() {
        List<MovieModel> movies = MediaUtils.getMediasFromMediaStore(this);
        if(movies != null && movies.size() > 0){
            ImageAdapter imageAdapter = new ImageAdapter(this, movies);
            gallery3D.setAdapter(imageAdapter);
        }
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
