package com.ruo.player.Utils;

import android.content.Context;

import com.ruo.player.Interface.OnResultAttachListener;
import com.ruo.player.entries.LocalMovieModel;
import com.ruo.player.entries.NetVideoModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Administrator on 2017/3/30.
 */

public class DataBaseUtils {

    /**
     * 存视频数据
     * @param context
     * @param datas
     * @param <T>
     */
    public static <T extends RealmObject> void saveVideoModel(Context context, final List<T> datas) {
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(context)
                .build();
        Realm mRealm = Realm.getInstance(realmConfig);
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (T model : datas) {
                    realm.copyToRealm(model);
                }
            }
        });
        mRealm.close();
    }

    /**
     * 查询网络数据
     * @param context
     * @param listener
     */
    public static void getNetVideo(Context context, final OnResultAttachListener<List<NetVideoModel>> listener) {
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(context)
                .build();
        Realm mRealm = Realm.getInstance(realmConfig);
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<NetVideoModel> netVideoModels = realm.where(NetVideoModel.class).findAll();
                List<NetVideoModel> datas = realm.copyFromRealm(netVideoModels);
                if(listener != null){
                    listener.done(datas);
                }
            }
        });
        mRealm.close();
    }

    /**
     * 查询本地数据
     * @param context
     * @param listener
     */
    public static void getLocalVideo(Context context, final OnResultAttachListener<List<LocalMovieModel>> listener) {
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(context)
                .build();
        Realm mRealm = Realm.getInstance(realmConfig);
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<LocalMovieModel> netVideoModels = realm.where(LocalMovieModel.class).findAll();
                List<LocalMovieModel> datas = realm.copyFromRealm(netVideoModels);
                if(listener != null){
                    listener.done(datas);
                }
            }
        });
        mRealm.close();
    }



    /**
     * 清楚网络数据
     * @param context
     */
    public static void clearNetVideo(Context context) {
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(context)
                .build();
        Realm mRealm = Realm.getInstance(realmConfig);
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<NetVideoModel> all = realm.where(NetVideoModel.class).findAll();
                all.deleteAllFromRealm();
            }
        });
        mRealm.close();
    }

    /**
     * 清楚本地数据
     * @param context
     */
    public static void clearLocalVideo(Context context) {
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(context)
                .build();
        Realm mRealm = Realm.getInstance(realmConfig);
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<LocalMovieModel> all = realm.where(LocalMovieModel.class).findAll();
                all.deleteAllFromRealm();
            }
        });
        mRealm.close();
    }
}
