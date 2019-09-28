package com.biyi.hypnosis.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.biyi.hypnosis.R;
import com.biyi.hypnosis.adapter.MusicListAdapter;
import com.biyi.hypnosis.http.RetrofitManager;
import com.biyi.hypnosis.http.model.MusicListModel;
import com.biyi.hypnosis.http.model.TagListModel;
import com.biyi.hypnosis.http.rxjava.TransformUtils;
import com.biyi.hypnosis.http.utils.Constans;
import com.biyi.hypnosis.services.Constant;
import com.biyi.hypnosis.services.MusicService;
import com.biyi.hypnosis.utils.ListUtils;
import com.biyi.hypnosis.utils.SpUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import cn.com.ad4.quad.ad.QUAD;
import cn.com.ad4.quad.listener.QuadBannerAdLoadListener;
import cn.com.ad4.quad.loader.QuadBannerAdLoader;
import cn.com.ad4.quad.view.QuadBannerAd;
import rx.Observer;


public class HomeActivity extends BaseActivity implements View.OnClickListener {
    
    public float mPercent;
    public ImageView iv_music_list, iv_settings, iv_rotatepic;
    private ImageView iv_play, iv_playtype, iv_clock;
    private RecyclerView mRecyclerView;
    public SeekBar sb_progress;
    private String path = getSDCardPathByEnvironment() + "/kaola_music/";
    private MediaPlayer mediaPlayer;
    public Animation mOperatingAnim;
    Messenger mMessengerClient;
    private Messenger mPlaygingClientMessenger;
    private int currentTime;
    private int duration;
    private List<String> mList = new ArrayList<>();
    private MyHandler myHandler;
    
    
    ServiceConnection mServiceConnection = new ServiceConnection() {
        
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mServiceMessenger = new Messenger(iBinder);
            Message msgToService = Message.obtain();
            msgToService.replyTo = mPlaygingClientMessenger;
            msgToService.what = Constant.PLAYING_ACTIVITY;
            if (0 != currentTime) {//当前进度不是0，就更新MediaPlayerService的当前进度
                msgToService.arg1 = currentTime;
            }
            try {
                mServiceMessenger.send(msgToService);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mPosition = getIntent().getIntExtra("position", 0);
            if (null != mServiceMessenger ) {
                Message msgToService1 = Message.obtain();
                msgToService.arg1 = mPosition;
                
                if (!ListUtils.isEmpty(mList)) {
                  /*  for (int i = 0; i < list.size(); i++) {
                        JLog.e(TAG, list.get(i).getSongname() + "--" + list.get(i).getUrl());
                    }*/
                    //更新专辑图片
//                    mAlbumFragmentAdapater.addList(mList);
//                    mAlbumFragmentAdapater.notifyDataSetChanged();
                    //显示是否收藏了这首歌曲
//                    showIsLike();
                    //传递歌曲集合数据
                    Bundle songsData = new Bundle();
                    songsData.putSerializable(Constant.PLAYING_ACTIVITY_DATA_KEY, (Serializable) mList);
                    msgToService1.setData(songsData);
                    msgToService1.what = Constant.PLAYING_ACTIVITY_INIT;
                    try {
                        mServiceMessenger.send(msgToService1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
            }
        }
        
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };
    private MusicListAdapter mAdapter;
   
    private String TAG = "HomeActivity1";
    
    
    static class MyHandler extends Handler {
        private WeakReference<HomeActivity> weakActivity;
        
        public MyHandler(HomeActivity activity) {
            weakActivity = new WeakReference<HomeActivity>(activity);
        }
        
        @Override
        public void handleMessage(Message msgFromService) {
            HomeActivity activity = weakActivity.get();
            if (null == activity) return;
            switch (msgFromService.what) {
                case Constant.MEDIA_PLAYER_SERVICE_PROGRESS://更新进度条
                    activity.currentTime = msgFromService.arg1;
                    activity.duration = msgFromService.arg2;
                    if (0 == activity.duration) break;
                    activity.sb_progress.setProgress(activity.currentTime * 100 / activity.duration);
                    break;
                case Constant.MEDIA_PLAYER_SERVICE_SONG_PLAYING:
                    Bundle bundle = msgFromService.getData();
//                    activity.mList.clear();
//                    activity.mList.addAll(bundle.getStringArrayList(Constant.MEDIA_PLAYER_SERVICE_MODEL_PLAYING));
//                    Message msgToService = Message.obtain();
//                    msgToService.arg1 = 1;
//                    msgToService.what = Constant.PLAYING_ACTIVITY_PLAYING_POSITION;
//                    if (null != activity.mServiceMessenger) {
//                        try {
//                            activity.mServiceMessenger.send(msgToService);
//                        } catch (RemoteException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    if (null != activity.mList && 0 < activity.mList.size()) {
//                        activity.mTvSongName.setText(activity.mList.get(msgFromService.arg1).getSongname());
//                        activity.mTvSinger.setText(activity.mList.get(msgFromService.arg1).getSingername());
                        
                        //更新专辑图片
                    }
                    break;
                case Constant.MEDIA_PLAYER_SERVICE_IS_PLAYING:
                    boolean b = 1 == msgFromService.arg1;
                    activity.playSelect(b);
                    if (b) {//正在播放
//                        activity.mBtnPlay.setImageResource(R.mipmap.play);
                    } else {
//                        activity.mBtnPlay.setImageResource(R.mipmap.pause);
                    }
                    break;
                case Constant.PLAYING_ACTIVITY_PLAY_MODE://显示播放器的播放模式
                    activity.updatePlayMode();
                    break;
                case Constant.MEDIA_PLAYER_SERVICE_UPDATE_SONG://播放完成自动播放下一首时，更新正在播放UI
                    int positionPlaying = msgFromService.arg1;
                    activity.selectPosMus(positionPlaying);
                    
                
            }
            super.handleMessage(msgFromService);
        }
    }
    
    private void updatePlayStatus(){
        if (null != mServiceMessenger) {
            Message msgToServicePlay = Message.obtain();
            msgToServicePlay.arg1 = 0x40001;//表示这个暂停是由点击按钮造成的，
            msgToServicePlay.what = Constant.PLAYING_ACTIVITY_PLAY;
            try {
                mServiceMessenger.send(msgToServicePlay);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    private void updatePlayMode() {
    
    }
    
    public Messenger mServiceMessenger;
    private int mPosition;
    
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
        
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        
    }
    
    private void bindService() {
        myHandler = new MyHandler(this);
        mPlaygingClientMessenger = new Messenger(myHandler);
        bindService(new Intent(this, MusicService.class), mServiceConnection, BIND_AUTO_CREATE);
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
        iv_rotatepic = (ImageView) findViewById(R.id.iv_rotatepic);
        iv_music_list.setOnClickListener(this);
        iv_settings.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        iv_playtype.setOnClickListener(this);
        iv_clock.setOnClickListener(this);
    
      
//        sb_progress.setOnClickListener(this);
        
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MusicListAdapter(R.layout.item_music_list, new ArrayList<MusicListModel.TagListBean>());
        
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean playing = selectPosMus(position);
    
                mAdapter.notifyDataSetChanged();
    
                if (mPosition == position ) {
                    Message msgToService = Message.obtain();
                    msgToService.arg1 = playing?0x40001:0;
                    msgToService.what = Constant.PLAYING_ACTIVITY_PLAY;
                    try {
                        mServiceMessenger.send(msgToService);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                
                }else {
                    mPosition = position;
                    Message msgToService = Message.obtain();
                    msgToService.arg1 = mPosition;
                    msgToService.what = Constant.PLAYING_ACTIVITY_PLAYING_POSITION;
                    try {
                        mServiceMessenger.send(msgToService);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
//        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View itemView, final int pos) {
//                final MusicListModel.TagListBean tagListBean = mAdapter.getDatas().get(pos);
//                String url = tagListBean.getUrl();
//                mPosition = pos;
//                bindService();
//
//            }
//        });
        //进度条的监听
        sb_progress.setOnSeekBarChangeListener(new MyOnSeekBarChangeListeger(this));
        
        if (SpUtils.getInt(SpUtils.KEY_TAG_ID) == -1) {
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
                            if (tagListModel == null || ListUtils.isEmpty(tagListModel.getTagList()))
                                return;
                            TagListModel.TagListBean tagListBean = tagListModel.getTagList().get(0);
                            int tagId = tagListBean.getTagId();
                            SpUtils.putInt(SpUtils.KEY_TAG_ID, tagId);
                            SpUtils.putString(SpUtils.KEY_TAG_NAME, tagListBean.getTagName());
                            requestMusicList();
                        }
                    });
        } else {
            requestMusicList();
        }
        
    }
    
    private boolean selectPosMus(int position) {
        final MusicListModel.TagListBean tagListBean = mAdapter.getItem(position);
        String url = tagListBean.getUrl();
        
        boolean playing = tagListBean.isPlaying();
        List<MusicListModel.TagListBean> data = mAdapter.getData();
        for (MusicListModel.TagListBean datum : data) {
            datum.setPlaying(false);
        }
        tagListBean.setPlaying(!playing);
        return playing;
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
                playSelect();
                updatePlayStatus();
                break;
            case R.id.iv_playtype:
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult( intent, 1);
                break;
            case R.id.iv_clock:
                Intent intent = new Intent(HomeActivity.this, ClockViewActivity.class);
                startActivity(intent);
                break;
        }
//        }
//        }
    
    }
    
    public void playSelect() {
        boolean switching = !iv_play.isSelected();
        
        playSelect(switching);
    }
    
    public void playSelect(boolean switching) {
        iv_play.setSelected(switching);
        SpUtils.putBoolean(SpUtils.KEY_TAG_PLAYMUSIC, switching);
        if (switching) {
            mOperatingAnim = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.clock_bg);
            
            LinearInterpolator lin = new LinearInterpolator();
            if (mOperatingAnim != null) {
                iv_rotatepic.startAnimation(mOperatingAnim);
            }
            mOperatingAnim.setInterpolator(lin);
            //        开始动画
            mOperatingAnim.startNow();
            
        } else {
            iv_rotatepic.clearAnimation();
        }
    }
    
    private void requestMusicList() {
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
                        if (tagListModel == null || ListUtils.isEmpty(tagListModel.getTagList()))
                            return;
                        List<MusicListModel.TagListBean> tagList = tagListModel.getTagList();
                        mAdapter.setNewData(tagList);
                        mList.clear();
                        for (MusicListModel.TagListBean tagListBean : tagList) {
                            mList.add(tagListBean.getUrl());
                        }
                        bindService();
                        QuadBannerAdLoader bannerAdLoader = QUAD.getBannerAdLoader(HomeActivity.this, Constans.BANNER_AD, new QuadBannerAdLoadListener() {
                            @Override
                            public void onAdReady(QuadBannerAd quadBannerAd) {
                                Log.i(TAG, "onAdReady: ");
//                                RelativeLayout.LayoutParams ad_lp = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//                                quadBannerAd.setLayoutParams(ad_lp);
                                ViewGroup.LayoutParams layoutParams = quadBannerAd.getLayoutParams();
                                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                                quadBannerAd.setLayoutParams(layoutParams);
                                mAdapter.addHeaderView(quadBannerAd);
                            }
        
                            @Override
                            public void onAdShowed() {
                                Log.i(TAG, "onAdShowed: ");
                            }
        
                            @Override
                            public void onAdClick() {
                                Log.i(TAG, "onAdClick: ");
            
                            }
        
                            @Override
                            public void onAdFailed(int i, String s) {
                                Log.i(TAG, "onAdFailed: ");
                            }
                        });
                        if (bannerAdLoader != null) {
                            bannerAdLoader.loadAds();
                        }

    
                      
    
    
                    }
                });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpUtils.putLong(SpUtils.KEY_COUNT_DOWN_TIME, 0);
    }
    
    private void setHeader(RecyclerView view) {
    
    }
    
    public static String getSDCardPathByEnvironment() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return "";
    }
    
}
