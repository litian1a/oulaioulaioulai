package com.biyi.hypnosis.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.biyi.hypnosis.R;

/**
 * 设置Activity
 */
public class SettingsActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_title;
    private LinearLayout ll_evaluate,ll_update;
    private RelativeLayout rl_riseup;
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
        ll_evaluate = findViewById(R.id.ll_evaluate);
        ll_update = findViewById(R.id.ll_update);
        ll_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,FeedbackActivity.class);
                startActivity(intent);

            }
        });
        iv_back = (ImageView) findViewById(R.id.iv_back);
        setTittle("设置");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }




}
