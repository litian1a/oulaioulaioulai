package com.biyi.hypnosis.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.biyi.hypnosis.R;


public class HomeActivity extends BaseActivity {
    public static void startActivity(Context context){
           Intent intent= new Intent(context,HomeActivity.class);
           context.startActivity(intent);
           
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    
    @Override
    int getLayoutId() {
        return R.layout.activity_home;
    }
    
    /**
     * 初始化控件
     */
    private void initView() {

    }



}
