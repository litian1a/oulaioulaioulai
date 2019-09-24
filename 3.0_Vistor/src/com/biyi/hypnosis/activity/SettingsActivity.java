package com.biyi.hypnosis.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.biyi.hypnosis.R;
import com.biyi.hypnosis.utils.ToastUtils;

/**
 * 设置Activity
 */
public class SettingsActivity extends BaseActivity {

    private ImageView iv_back,iv_switchon;
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
        ll_evaluate = findViewById(R.id.ll_feeckback);
        ll_update = findViewById(R.id.ll_update);
        iv_switchon = findViewById(R.id.iv_switchon);
        ll_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.getInstance().showToast(getApplicationContext(), "暂无最新版本");
            }
        });
        iv_switchon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ll_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,FeedbackActivity.class);
                startActivity(intent);

            }
        });
        setTittle("设置");

    }




}
