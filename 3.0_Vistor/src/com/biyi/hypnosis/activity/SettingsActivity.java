package com.biyi.hypnosis.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.biyi.hypnosis.R;

/**
 * 设置Activity
 */
public class SettingsActivity extends BaseActivity {

    private ImageView musictype_back;
    private TextView t_musictype;
    public static void startActivity(Context context){
           Intent intent= new Intent(context,SettingsActivity.class);
           context.startActivity(intent);
           
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    
    @Override
    int getLayoutId() {
        return R.layout.activity_settings;
    }
    
    /**
     * 初始化控件
     */
    private void initView() {


    }




}
