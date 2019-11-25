package com.sleep.kaola.activity;

import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.sleep.kaola.R;
import com.sleep.kaola.http.utils.Constans;
import com.sleep.kaola.permission.Action;
import com.sleep.kaola.permission.AndPermission;
import com.sleep.kaola.permission.Permission;

import java.util.List;

import cn.com.ad4.quad.ad.QUAD;
import cn.com.ad4.quad.listener.QuadSplashAdLoadListener;
import cn.com.ad4.quad.loader.QuadSplashAdLoader;
import cn.com.ad4.quad.view.QuadSplashAd;

public class SplashActivity extends BaseActivity {
    
    private QuadSplashAd mQuadSplashAd;
    private boolean aBoolean = false;
    private String TAG = "SplashActivity1";
    private ViewGroup mParent;
    private boolean isShow;
    private Handler mHandler;
    private Runnable mRunnable;
    //   必要权限  如果没有获取到直接退出程序
    public static String[] MUST_PERMISSION = {Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE,
            Permission.READ_PHONE_STATE};
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndPermission.with(this)
                .runtime()
                .permission(MUST_PERMISSION)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Log.i(TAG, "onAction: onGranted");
                        initView();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Log.i(TAG, "onAction: onDenied");
                        initView();
                    }
                })
                .start();
    
    
    
    
    }
    
    private void initView() {
        mParent = findViewById(R.id.parent);
        mQuadSplashAd = new QuadSplashAd(SplashActivity.this);
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    Log.i(TAG, "postDelayed: ");
    
                    HomeActivity.startActivity(SplashActivity.this);
                    finish();
                }
            }
        };
        mHandler.postDelayed(mRunnable, 5000);
        final QuadSplashAdLoader splashAdLoader = QUAD.getSplashAdLoader(SplashActivity.this, Constans.SPLASH_AD, new QuadSplashAdLoadListener() {
            
            @Override
            public void onAdDismissed() {
                Log.i(TAG, "onAdDismissed: ");
                if (!isFinishing() && isShow && !aBoolean) {
                    HomeActivity.startActivity(SplashActivity.this);
                    finish();
                }
               
            }
            
            @Override
            public void onAdReady(QuadSplashAd quadSplashAd) {
                mHandler.removeCallbacks(mRunnable);
                Log.i(TAG, "onAdReady: " + quadSplashAd);
                mParent.removeAllViews();
                mParent.addView(quadSplashAd);
            }
            
            @Override
            public void onAdShowed() {
                isShow =true;
                mHandler.removeCallbacks(mRunnable);
                Log.i(TAG, "onAdShowed: ");
            }
            
            @Override
            public void onAdClick() {
                Log.i(TAG, "onAdClick: ");
            
            }
            
            @Override
            public void onAdFailed(int i, String s) {
                Log.i(TAG, "onAdFailed: ");
            
            }
        });
        if (splashAdLoader != null) {
            splashAdLoader.loadAds();
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if (mQuadSplashAd == null) return;
        mQuadSplashAd.setSplash(false);
        aBoolean=true;
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (mQuadSplashAd == null) return;
        if(aBoolean){
            HomeActivity.startActivity(SplashActivity.this);
            finish();
        }
    }
    
    
    @Override
    int getLayoutId() {
        return R.layout.activity_splash;
    }
}
