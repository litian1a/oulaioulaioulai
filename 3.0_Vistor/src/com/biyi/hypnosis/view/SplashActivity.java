package com.biyi.hypnosis.view;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.biyi.hypnosis.R;
import com.biyi.hypnosis.http.RetrofitManager;
import com.biyi.hypnosis.http.model.CheckVerModel;
import com.biyi.hypnosis.http.model.FeedbackModel;
import com.biyi.hypnosis.http.model.RequestBean;
import com.biyi.hypnosis.http.model.TagListModel;
import com.biyi.hypnosis.http.rxjava.TransformUtils;

import rx.Observer;

public class SplashActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HomeActivity.startActivity(SplashActivity.this);
                finish();
            }
        },3000);
//                RetrofitManager.getAppApi(this).getAppStoreService().requestCheckVer().compose(TransformUtils.<CheckVerModel>defaultSchedulers()).subscribe(new Observer<CheckVerModel>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(CheckVerModel checkVerModel) {
//
//                    }
//                });
//
//        RetrofitManager.getAppApi(this).getAppStoreService().requestFeedback().compose(TransformUtils.<FeedbackModel>defaultSchedulers()).subscribe(new Observer<FeedbackModel>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(FeedbackModel feedbackModel) {
//
//            }
//        });
        
//        RetrofitManager.getAppApi(this).getAppStoreService()
//                .requestMusiclistr()
//                .compose(TransformUtils.<TagListModel>defaultSchedulers())
//        .subscribe(new Observer<TagListModel>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(TagListModel tagListModel) {
//
//            }
//        });
    }
}
