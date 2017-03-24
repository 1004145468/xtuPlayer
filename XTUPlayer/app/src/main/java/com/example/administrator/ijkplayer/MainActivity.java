package com.example.administrator.ijkplayer;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.ijkplayer.media.IRenderView;
import com.example.administrator.ijkplayer.media.IjkVideoView;

public class MainActivity extends AppCompatActivity {

    private IjkVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = (IjkVideoView) findViewById(R.id.videoview);
        videoView.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            videoView.setVideoURI(data.getData());
            videoView.start();
        }
    }
}
