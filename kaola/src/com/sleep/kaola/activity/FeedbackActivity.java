package com.sleep.kaola.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sleep.kaola.R;
import com.sleep.kaola.http.RetrofitManager;
import com.sleep.kaola.http.model.FeedbackModel;
import com.sleep.kaola.http.rxjava.TransformUtils;

import rx.Observer;

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
        feedback_btn = findViewById(R.id.feedback_btn);
        et_feedback = findViewById(R.id.et_feedback);
        setTittle("意见反馈");
        feedback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (TextUtils.isEmpty(et_feedback.getText())){
//                ToastUtils.show(FeedbackActivity.this,"输入不许为空");
                com.sleep.kaola.utils.ToastUtils.getInstance().showToast(FeedbackActivity.this,"输入不许为空");
//
                return;
            }
                RetrofitManager.getAppApi(getApplicationContext())
                        .getAppStoreService()
                        .requestFeedback(et_feedback.getText().toString())
                        .compose(TransformUtils.<FeedbackModel>defaultSchedulers())
                        .subscribe(new Observer<FeedbackModel>() {
                            @Override
                            public void onCompleted() {
        
                            }
    
                            @Override
                            public void onError(Throwable e) {
        
                            }
    
                            @Override
                            public void onNext(FeedbackModel feedbackModel) {
                                if (feedbackModel == null) return;
                                com.sleep.kaola.utils.ToastUtils.getInstance().showToast(FeedbackActivity.this,feedbackModel.getMessage());
//
//                                ToastUtils.show(FeedbackActivity.this,feedbackModel.getMessage());
    
                            }
                        });
                finish();
            }
        });

     

    }




}
