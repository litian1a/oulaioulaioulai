package com.biyi.hypnosis.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.biyi.hypnosis.R;
import com.biyi.hypnosis.http.RetrofitManager;
import com.biyi.hypnosis.http.model.CheckVerModel;
import com.biyi.hypnosis.http.model.FeedbackModel;
import com.biyi.hypnosis.http.model.RequestBean;
import com.biyi.hypnosis.http.model.TagListModel;
import com.biyi.hypnosis.http.rxjava.TransformUtils;
import com.biyi.hypnosis.http.utils.Constans;
import com.biyi.hypnosis.permission.Action;
import com.biyi.hypnosis.permission.AndPermission;
import com.biyi.hypnosis.permission.Permission;

import java.util.List;

import cn.com.ad4.quad.ad.QUAD;
import cn.com.ad4.quad.listener.QuadSplashAdLoadListener;
import cn.com.ad4.quad.loader.QuadSplashAdLoader;
import cn.com.ad4.quad.view.QuadSplashAd;
import rx.Observer;

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
            Permission.READ_PHONE_STATE ,Permission.ACCESS_COARSE_LOCATION,Permission.ACCESS_FINE_LOCATION};
    
    
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
                        System.exit(0);
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
