package com.biyi.hypnosis.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.biyi.hypnosis.R;

/**
 * 设置Activity
 */
public class FeedbackActivity extends BaseActivity {

    private ImageView iv_back;
    private Button feedback_btn;
    private EditText et_feedback;
    public static void startActivity(Context context){
           Intent intent= new Intent(context,FeedbackActivity.class);
           context.startActivity(intent);
           
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    
    @Override
    int getLayoutId() {
        return R.layout.activity_feedback;
    }
    
    /**
     * 初始化控件
     */
    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        feedback_btn = findViewById(R.id.feedback_btn);
        et_feedback = findViewById(R.id.et_feedback);
        setTittle("意见反馈");
        feedback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }




}
