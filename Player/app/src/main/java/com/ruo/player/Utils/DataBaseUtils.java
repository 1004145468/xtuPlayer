package com.ruo.player.Utils;

import android.content.Context;

import com.ruo.player.Interface.OnResultAttachListener;
import com.ruo.player.entries.NetVideoModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Administrator on 2017/3/30.
 */

public class DataBaseUtils {

    public static void saveNetVideo(Context context, final List<NetVideoModel> datas) {
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(context)
                .build();
        Realm mRealm = Realm.getInstance(realmConfig);
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (NetVideoModel model : datas) {
                    realm.copyToRealm(model);
                }
            }
        });
        mRealm.close();
    }

    /**
     * 查询数据
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
                listener.done(datas);
            }
        });
        mRealm.close();
    }

    /**
     * 清楚数据
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
}
