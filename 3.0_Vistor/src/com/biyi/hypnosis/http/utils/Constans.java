package com.biyi.hypnosis.http.utils;

import android.content.Context;

/**
 * Description：
 * Time：2019-09-23 14:45
 *
 * @author：ltc
 */
public class Constans {
    public static final String ACTION_MUSIC_NOTIFY = "ACTION_MUSIC_NOTIFY";
    public static String KAOLA = "https://app111.upapp.cc";
    public static int SYS = 0;
    public static String SIGN = "8A164f54BcF50B57478cd07FC8d3";
    public static String SIGN2 = "e6f47e91396a133f35ebee2fbe4d409a";
    public static final String TIME = "time";
    public static String VER = "1.0.1";
    
    public static final String SPLASH_AD = "19187001";
    public static final String BANNER_AD = "19187002";
    public static final String MESSAGE_AD = "19187003";
    public static final String AD_KEY = "df526fee30ea4106";
    public static final int MUSICT_DANQU = 0;
    public static final int MUSICT_SHUNXUN = 1;
    public static final int MUSICT_SUIJI = 2;
    

    /**
     * 得到手机的缓存目录
     *
     * @param context
     * @return
     */
    public static String getCacheDir(Context context) {
        return context.getFilesDir().getParent();
    }
    public static String getCachePath(Context context) {
        return getCacheDir(context)+ "/kaola_music/";
    }
    
    
    
}
