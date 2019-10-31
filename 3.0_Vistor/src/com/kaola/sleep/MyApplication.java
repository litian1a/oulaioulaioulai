package com.sleep.kaola;

import android.support.multidex.MultiDexApplication;

import com.sleep.kaola.http.utils.Constans;
import com.sleep.kaola.utils.SpUtils;
import com.meituan.android.walle.WalleChannelReader;

import cn.com.ad4.quad.ad.QUAD;


/**
 * Created by boyko on 2018/3/5.
 */

public class MyApplication extends MultiDexApplication {
    public static MyApplication instance;
    public String mChannel;
    
    public static MyApplication getAppContext() {
        return instance;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SpUtils.init(this);
//        LeakCanary.install(this);
        initBugly();  // release环境关闭
        QUAD.initSdk(this, Constans.AD_KEY ,BuildConfig.DEBUG, -1,-1);
        mChannel = WalleChannelReader.getChannel(this.getApplicationContext(),"xiaomi");
    
    
    
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
