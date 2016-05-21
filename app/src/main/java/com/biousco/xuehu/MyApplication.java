package com.biousco.xuehu;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Biousco on 5/21.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 开启debug会影响性能
    }
}
