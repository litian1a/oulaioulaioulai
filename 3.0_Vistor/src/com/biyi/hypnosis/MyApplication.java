package com.biyi.hypnosis;

import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;


/**
 * Created by boyko on 2018/3/5.
 */

public class MyApplication extends MultiDexApplication  {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        initBugly();  // release环境关闭

    }


    private void initBugly() {
    }


}
