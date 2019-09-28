package com.biyi.hypnosis.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Description：
 * Time：2019-09-23 22:54
 *
 * @author：ltc
 */
public class SpUtils {
    
    public static final int DEFAULT_VALUE = -1;
    private static SpUtils sharePreferenceUtils;
    private SharedPreferences mSharedPreferences;
    private static Context mContext;
    
    public static final String KEY_TAG_NAME = "KEY_TAG_NAME";
    public static final String KEY_TYPE_MUSIC_URL= "KEY_TYPE_MUSIC_URL";
    public static final String KEY_TAG_ID = "KEY_TAG_ID";
    public static final String KEY_COUNT_DOWN_TIME = "KEY_COUNT_DOWN_TIME";
    public static final String KEY_TAG_CLOCKSWIFT = "KEY_TAG_CLOCKSWIFT";
    public static final String KEY_TAG_PLAYMUSIC = "KEY_TAG_PLAYMUSIC";
    public static final String KEY_TAG_TIME1 = "KEY_TAG_TIME1";
    public static final String KEY_PLAYER_TYPE = "KEY_PLAYER_TYPE";
    public SpUtils() {
    
    }
    
    public static void init(Context context) {
        if (context == null) {
            return;
        }
        if (sharePreferenceUtils == null) {
            sharePreferenceUtils = new SpUtils();
            sharePreferenceUtils.mSharedPreferences = context
                    .getSharedPreferences(context.getPackageName(),
                            Context.MODE_PRIVATE);
        }
        
    }
    
    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = sharePreferenceUtils.mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    
    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharePreferenceUtils.mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    
    public static void putFloat(String key, float value) {
        SharedPreferences.Editor editor = sharePreferenceUtils.mSharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }
    
    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharePreferenceUtils.mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public static boolean getBoolean(String key, boolean value) {
        return sharePreferenceUtils.mSharedPreferences.getBoolean(key,value);
    }
    public static void putLong(String key, long value) {
        SharedPreferences.Editor editor = sharePreferenceUtils.mSharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }
    
    public static void putStringSet(String key, Set value) {
        SharedPreferences.Editor editor = sharePreferenceUtils.mSharedPreferences.edit();
        editor.putStringSet(key, value);
        editor.commit();
    }
    
 
    
    public static int getInt(String key) {
        return sharePreferenceUtils.mSharedPreferences.getInt(key,
                DEFAULT_VALUE);
    }
    public static int getInt(String key,int DEFAULT_VALUE) {
        return sharePreferenceUtils.mSharedPreferences.getInt(key,
                DEFAULT_VALUE);
    }
    
    
    public static String getString(String key) {
        return sharePreferenceUtils.mSharedPreferences.getString(key, null);
    }
    
    public static long getLong(String key) {
        return sharePreferenceUtils.mSharedPreferences.getLong(key,
                DEFAULT_VALUE);
    }
    
    public static float getFloat(String key) {
        return sharePreferenceUtils.mSharedPreferences.getFloat(key,
                DEFAULT_VALUE);
    }
    
    public static Set<String> getStringSet(String key) {
        return sharePreferenceUtils.mSharedPreferences.getStringSet(key, null);
    }
    
   
 
    
    public static void remove(String key) {
        SharedPreferences.Editor editor = sharePreferenceUtils.mSharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }
    
    
    public static SharedPreferences getSharePreference() {
        return sharePreferenceUtils.mSharedPreferences;
    }
    private static void checkInit() throws Exception {
        if (sharePreferenceUtils == null
                || sharePreferenceUtils.mSharedPreferences == null) {
            throw new Exception("SharePreferenceUtils未初始化，请先初始化！");
        }
    }
  
    
    
}