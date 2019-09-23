package com.biyi.hypnosis.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.biyi.hypnosis.R;

/**
 * Description：
 * Time：2019-09-23 21:03
 *
 * @author：ltc
 */
public abstract class BaseActivity extends AppCompatActivity {
    
    private ImageView mBack;
    private TextView mTextView;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        
        mBack = findViewById(R.id.iv_back);
        mTextView = findViewById(R.id.tv_title);
    
        if (mBack != null){
            mBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
        
                }
            });
        }
        
       
    
    }
    abstract int  getLayoutId();
    
    public  void  setTittle (String tittle){
        if (mTextView != null){
            mTextView.setText(tittle);
        }
    }
}
