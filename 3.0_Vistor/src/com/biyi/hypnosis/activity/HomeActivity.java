package com.biyi.hypnosis.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.biyi.hypnosis.R;
import com.biyi.hypnosis.adapter.BaseRecyclerAdapter;
import com.biyi.hypnosis.adapter.MusicListAdapter;
import com.biyi.hypnosis.download.DownLoadCallback;
import com.biyi.hypnosis.download.DownloadManager;
import com.biyi.hypnosis.http.RetrofitManager;
import com.biyi.hypnosis.http.model.MusicListModel;
import com.biyi.hypnosis.http.model.TagListModel;
import com.biyi.hypnosis.http.rxjava.TransformUtils;
import com.biyi.hypnosis.utils.ListUtils;
import com.biyi.hypnosis.utils.SpUtils;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;

import java.io.File;
import java.io.IOException;

import rx.Observer;


public class HomeActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_music_list,iv_settings,iv_rotatepic;
    private ImageView iv_play,iv_playtype,iv_clock;
    private RecyclerView mRecyclerView;
    private SeekBar sb_progress;
    private String path = getSDCardPathByEnvironment()+"/kaola_music/";
    private MediaPlayer mediaPlayer;
    private Animation mOperatingAnim;
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
        iv_playtype = (ImageView) findViewById(R.id.iv_playtype);
        iv_clock = (ImageView) findViewById(R.id.iv_clock);
        sb_progress = (SeekBar) findViewById(R.id.sb_progress);
        mRecyclerView = findViewById(R.id.music_list);
        iv_rotatepic =(ImageView) findViewById(R.id.iv_rotatepic);
        iv_music_list.setOnClickListener(this);
        iv_settings.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        iv_playtype.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_music_list:
                MusicTypeActivity.startActivity(this);
                break;
            case R.id.iv_settings:
                SettingsActivity.startActivity(this);
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult( intent, 1);
                break;
            case R.id.iv_play:
                boolean switching = !iv_play.isSelected();
                iv_play.setSelected(switching);
                SpUtils.putBoolean(SpUtils.KEY_TAG_PLAYMUSIC,switching);
                if (!switching){
                    mOperatingAnim = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.clock_bg);

                    LinearInterpolator lin = new LinearInterpolator();
                    if (mOperatingAnim != null) {
                        iv_rotatepic.startAnimation(mOperatingAnim);
                    }
                    mOperatingAnim.setInterpolator(lin);
                    //        开始动画
                    mOperatingAnim.startNow();
                }else{
                    iv_rotatepic.clearAnimation();
                }
                break;
            case R.id.iv_playtype:
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
                        final MusicListAdapter adapter = new MusicListAdapter(HomeActivity.this, tagListModel.getTagList());
                        mRecyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View itemView, int pos) {
//                                final MusicListModel.TagListBean tagListBean = adapter.getDatas().get(pos);
//                                String url = tagListBean.getUrl();
//
//                                DownloadManager.getInstance().downloadUrl(url, path + tagListBean.getMusicId() + ".mp3", new DownLoadCallback() {
//                                    @Override
//                                    public void onProgress(long currentOffset, long mTotalLength) {
//
//                                    }
//
//                                    @Override
//                                    public void onSuccess() {
////                                        mediaPlayer = new MediaPlayer();
////                                        // 设置指定的流媒体地址
////                                        try {
////                                            mediaPlayer.setDataSource(path + tagListBean.getMusicId() + ".mp3");
////                                        } catch (IOException e) {
////                                            e.printStackTrace();
////                                        }
////                                        // 设置音频流的类型
////                                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
////                                        // 通过异步的方式装载媒体资源
////                                        mediaPlayer.prepareAsync();
////                                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
////                                            @Override
////                                            public void onPrepared(MediaPlayer mp) {
////                                                // 装载完毕 开始播放流媒体
////                                                mediaPlayer.start();
////                                                Toast.makeText(HomeActivity.this, "开始播放", Toast.LENGTH_SHORT).show();
////                                                // 避免重复播放，把播放按钮设置为不可用
////                                            }
////                                        });
//                                        //
//                                    }
//
//                                    @Override
//                                    public void onFail() {
//
//                                    }
//                                });
                            }
                        });
                    }
                });
    }



    public static String getSDCardPathByEnvironment() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return "";
    }
}
