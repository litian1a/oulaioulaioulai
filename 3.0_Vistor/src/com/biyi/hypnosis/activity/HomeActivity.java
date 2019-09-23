package com.biyi.hypnosis.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.biyi.hypnosis.R;
import com.biyi.hypnosis.adapter.MusicListAdapter;
import com.biyi.hypnosis.http.RetrofitManager;
import com.biyi.hypnosis.http.model.MusicListModel;
import com.biyi.hypnosis.http.model.TagListModel;
import com.biyi.hypnosis.http.rxjava.TransformUtils;
import com.biyi.hypnosis.utils.ListUtils;
import com.biyi.hypnosis.utils.SpUtils;

import rx.Observer;


public class HomeActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_music_list,iv_settings;
    private ImageView iv_play,iv_prev,iv_clock;
    private RecyclerView mRecyclerView;
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
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
        mRecyclerView = findViewById(R.id.music_list);

        iv_music_list.setOnClickListener(this);
        iv_settings.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        iv_prev.setOnClickListener(this);
        iv_clock.setOnClickListener(this);
//        sb_progress.setOnClickListener(this);
    
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        if (SpUtils.getInt(SpUtils.KEY_TAG_ID)  == -1){
            RetrofitManager.getAppApi(this).getAppStoreService()
                    .requestTagList()
                    .compose(TransformUtils.<TagListModel>defaultSchedulers())
                    .subscribe(new Observer<TagListModel>() {
                        @Override
                        public void onCompleted() {
        
                        }
    
                        @Override
                        public void onError(Throwable e) {
        
                        }
    
                        @Override
                        public void onNext(TagListModel tagListModel) {
                            if (tagListModel == null || ListUtils.isEmpty(tagListModel.getTagList()))return;
                            TagListModel.TagListBean tagListBean = tagListModel.getTagList().get(0);
                            int tagId = tagListBean.getTagId();
                            SpUtils.putInt(SpUtils.KEY_TAG_ID,tagId);
                            SpUtils.putString(SpUtils.KEY_TAG_NAME,tagListBean.getTagName());
                            requestMusicList();
                        }
                    });
        }
        else {
            requestMusicList();
        }

    }
    private void requestMusicList(){
        int tagId = SpUtils.getInt(SpUtils.KEY_TAG_ID);
        RetrofitManager.getAppApi(this)
                .getAppStoreService()
                .requestMusiclist(tagId)
                .compose(TransformUtils.<MusicListModel>defaultSchedulers())
                .subscribe(new Observer<MusicListModel>() {
                    @Override
                    public void onCompleted() {
        
                    }
    
                    @Override
                    public void onError(Throwable e) {
        
                    }
    
                    @Override
                    public void onNext(MusicListModel tagListModel) {
                        if (tagListModel == null || ListUtils.isEmpty(tagListModel.getTagList()))return;
                        mRecyclerView.setAdapter(new MusicListAdapter(HomeActivity.this,tagListModel.getTagList()));
                    }
                });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_music_list:
                MusicTypeActivity.startActivity(this);
                finish();
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
                Intent intent = new Intent(HomeActivity.this,ClockViewActivity.class);
                startActivity(intent);
                break;
            }
//        }
//        }

    }
}
