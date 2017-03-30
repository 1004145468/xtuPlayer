package com.ruo.player.Utils;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.ruo.player.Interface.OnResultAttachListener;
import com.ruo.player.entries.NetVideoModel;
import com.ruo.player.entries.OriginNetVideoModel;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/3/30.
 */

public class NetUtils {

    /**
     * 获取网络视频的
     *
     * @param url
     * @param listener
     */
    public static void getNetVideosByGet(final Context context, String url, final OnResultAttachListener<List<NetVideoModel>> listener) {
        final Handler handler = new Handler();
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (TextUtils.isEmpty(result)) {
                    return;
                }
                Gson gson = new Gson();
                OriginNetVideoModel originNetVideoModel = gson.fromJson(result, OriginNetVideoModel.class);
                if (originNetVideoModel == null || !originNetVideoModel.getMessage().equals("success")) {
                    return;
                }
                OriginNetVideoModel.NoteBean data = originNetVideoModel.getData();
                if (data == null || data.getData() == null || data.getData().size() < 1) {
                    return;
                }
                List<OriginNetVideoModel.MediaInfo> mediainfos = data.getData();
                if (mediainfos == null || mediainfos.size() < 1) {
                    return;
                }
                //重新组装数据
                final List<NetVideoModel> datas = new ArrayList<>();
                NetVideoModel model = null;

                OriginNetVideoModel.MediaGroup group = null;
                for (OriginNetVideoModel.MediaInfo mediainfo : mediainfos) {
                    if (mediainfo != null && mediainfo.getGroup() != null) {
                        group = mediainfo.getGroup();
                        model = new NetVideoModel(group.getText(), group.getMp4_url(), group.getLarge_cover() == null ? "" : group.getLarge_cover().getUri());
                        datas.add(model);
                    }
                }
                DataBaseUtils.clearNetVideo(context);
                //存数据库,断网可以读取本地
                DataBaseUtils.saveNetVideo(context,datas);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.done(datas);
                    }
                });
            }
        });

    }
}
