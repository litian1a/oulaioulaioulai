package com.biyi.hypnosis.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.biyi.hypnosis.R;

/**
 * 音乐类型Activity
 */
public class MusicTypeActivity extends BaseActivity {

    private ImageView musictype_back;
    private TextView t_musictype;
    public static void startActivity(Context context){
           Intent intent= new Intent(context,MusicTypeActivity.class);
           context.startActivity(intent);
           
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    
    @Override
    int getLayoutId() {
        return R.layout.activity_music_type;
    }
    
    /**
     * 初始化控件
     */
    private void initView() {

//        musictype_back = (ImageView) findViewById(R.id.musictype_back);
        musictype_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }




}
