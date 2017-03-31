package com.ruo.player.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
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

    private static Call mCurrentCall;

    /**
     * 获取网络视频的
     * 实现了三级缓存：  内存 本地 网络
     *
     * @param listener
     */
    public static void getNetVideosByGet(final Context context, final OnResultAttachListener<List<NetVideoModel>> listener) {
        StringBuilder urlBuilder = new StringBuilder();

        urlBuilder.append("http://is.snssdk.com/neihan/stream/mix/v1/?")
                .append("mpic=1&webp=1&essence=1&content_type=-104&message_cursor=-1")
                .append("&am_longitude=112.866052&am_latitude=27.878534&am_city=%E6%B9%98%E6%BD%AD%E5%B8%82")
                .append("&am_loc_time=1490848959231&count=30")
                .append("&min_time=" + SystemClock.currentThreadTimeMillis())
                .append("&screen_width=1440&double_col_mode=0&iid=8094100419&device_id=35121824822&ac=wifi")
                .append("&channel=tengxun&aid=7&app_name=joke_essay&version_code=601&version_name=6.0.1&device_platform=android")
                .append("&ssmix=a&device_type=SM919&device_brand=SMARTISAN&os_api=23&os_version=6.0.1")
                .append("&uuid=990007181217339&openudid=d51fcc7a0ab54dd5&manifest_version_code=601&resolution=1440*2560&dpi=560&update_version_code=6011");

        final Handler handler = new Handler();
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(urlBuilder.toString()).get().build();
        mCurrentCall = okHttpClient.newCall(request);
        mCurrentCall.enqueue(new Callback() {
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
                //保留最新的一份数据
                DataBaseUtils.clearNetVideo(context);
                //存数据库,断网可以读取本地
                DataBaseUtils.saveVideoModel(context, datas);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.done(datas);
                        }
                    }
                });
            }
        });
    }

    /**
     * 取消网络请求
     */
    public static void cancelNetVideosByGet() {
        if (mCurrentCall != null && mCurrentCall.isExecuted()) {
            mCurrentCall.cancel();
        }
    }
}
