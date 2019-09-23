package com.biyi.hypnosis.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.biyi.hypnosis.R;


public class HomeActivity extends AppCompatActivity {
    public static void startActivity(Context context){
           Intent intent= new Intent(context,HomeActivity.class);
           context.startActivity(intent);
           
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {

    }



}
