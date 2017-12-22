package com.maj.demo;

import android.app.Application;

import org.xutils.x;

/**
 * Created by maJian on 2017/12/20 0020 13:11.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        new RudenessScreenHelper(this, 750).activate();
    }
}
