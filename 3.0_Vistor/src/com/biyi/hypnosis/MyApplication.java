package com.biyi.hypnosis;

import android.support.multidex.MultiDexApplication;


/**
 * Created by boyko on 2018/3/5.
 */

public class MyApplication extends MultiDexApplication {
    public static MyApplication instance;
    
    public static MyApplication getAppContext() {
        return instance;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        LeakCanary.install(this);
        initBugly();  // release环境关闭
        
        /**
         * 设置传输数据加密类型 1：RSA  2：DES 3：AES
         */
//        Ntalker.getInstance().setEncryption(1);
        /**
         * 是否开启debug模式,开启小能特有的log，可将小能log存到本地， release环境请关闭.
         */
    }
    
    
    private void initBugly() {
    }
    
    
}
