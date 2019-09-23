package com.biyi.hypnosis.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.biyi.hypnosis.R;


public class HomeActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_music_list,iv_settings;
    private ImageView iv_play,iv_prev,iv_clock;
    private SeekBar sb_progress;
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

        iv_music_list = (ImageView) findViewById(R.id.iv_music_list);
        iv_settings = (ImageView) findViewById(R.id.iv_settings);
        iv_play = (ImageView) findViewById(R.id.iv_play);
        iv_prev = (ImageView) findViewById(R.id.iv_prev);
        iv_clock = (ImageView) findViewById(R.id.iv_clock);
        sb_progress = (SeekBar) findViewById(R.id.sb_progress);

        iv_music_list.setOnClickListener(this);
        iv_settings.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        iv_prev.setOnClickListener(this);
        iv_clock.setOnClickListener(this);
//        sb_progress.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_music_list:
                MusicTypeActivity.startActivity(this);
                break;
            case R.id.iv_settings:
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult( intent, 1);
                break;
            case R.id.iv_play:
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult( intent, 1);
                break;
            case R.id.iv_prev:
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult( intent, 1);
                break;
            case R.id.iv_clock:
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult( intent, 1);
                break;
            }
//        }
//        }

    }
}
