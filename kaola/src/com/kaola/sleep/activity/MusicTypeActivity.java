package com.sleep.kaola.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sleep.kaola.R;
import com.sleep.kaola.adapter.MusicTypeAdapter;
import com.sleep.kaola.http.RetrofitManager;
import com.sleep.kaola.http.model.TagListModel;
import com.sleep.kaola.http.rxjava.TransformUtils;
import com.sleep.kaola.http.utils.Constans;
import com.sleep.kaola.utils.ListUtils;
import com.sleep.kaola.utils.SpUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONObject;

import cn.com.ad4.quad.ad.QUAD;
import cn.com.ad4.quad.base.QuadNativeAd;
import cn.com.ad4.quad.listener.QuadNativeAdLoadListener;
import cn.com.ad4.quad.loader.QuadNativeAdLoader;
import rx.Observer;

/**
 * 音乐类型Activity
 */
public class MusicTypeActivity extends BaseActivity {
    
    MusicTypeAdapter musicTypeAdapter;
    
    private QuadNativeAd nativeAD;
    private JSONObject json;
    private View mAd_relative;
    private TextView mAd_name;
    private ImageView mAd_img;
    private View mInflate;
    private int downX;
    private int downY;
    private int upx;
    private int upy;
    private String TAG = "MusicTypeActivity1";
    
    
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MusicTypeActivity.class);
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
        
        TextView tvMusic = findViewById(R.id.t_musictype);
        mInflate = LayoutInflater.from(MusicTypeActivity.this).inflate(R.layout.rv_header, null);
        mAd_relative = mInflate.findViewById(R.id.ad_relative);
        mAd_name = mInflate.findViewById(R.id.ad_name);
        mAd_img = mInflate.findViewById(R.id.ad_img);
    
        mAd_relative.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int eventaction = motionEvent.getAction();
                switch (eventaction) {
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) motionEvent.getX();
                        downY = (int) motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        upx = (int) motionEvent.getX();
                        upy = (int) motionEvent.getY();
                        if (nativeAD != null) {
                            nativeAD.onAdClick(MusicTypeActivity.this,mAd_relative, downX + "", downY + "", upx + "", upy + "");
                        }
                        break;
                }
                return true;
            }
        });
        
        
        
        
        
        
        tvMusic.setText("当前播放列表，" + SpUtils.getString(SpUtils.KEY_TAG_NAME));
        setTittle("全部分类");
        final RecyclerView recyclerView = findViewById(R.id.rycycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        
        
        RetrofitManager.getAppApi(this).getAppStoreService().requestTagList()
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
                        
                        musicTypeAdapter = new MusicTypeAdapter(R.layout.item_music_type, tagListModel.getTagList());
                        
                        recyclerView.setAdapter(musicTypeAdapter);
                        musicTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                TagListModel.TagListBean tagListBean = musicTypeAdapter.getItem(position);
                                SpUtils.putInt(SpUtils.KEY_TAG_ID, tagListBean.getTagId());
                                SpUtils.putString(SpUtils.KEY_TAG_NAME, tagListBean.getTagName());
                                SpUtils.putString(SpUtils.KEY_TYPE_MUSIC_URL, tagListBean.getIconUrl());
                                HomeActivity.startActivity(MusicTypeActivity.this);
                                finish();
                            }
                        });
                        
                        QuadNativeAdLoader adLoader = QUAD.getNativeAdLoader(MusicTypeActivity.this, Constans.MESSAGE_AD, new QuadNativeAdLoadListener() {
                            @Override
                            public void onAdLoadSuccess(QuadNativeAd nativeAd) {
                                
                                Log.i("adjson", nativeAd.getContent().toString());
                                nativeAD = nativeAd;
                                json = nativeAd.getContent();
                                try {
                                    mAd_name.setText(json.optString("title"));
                                    
                                    Glide.with(MusicTypeActivity.this)
                                            .load(json.optJSONArray("contentimg").get(0))
                                            .fitCenter()
                                            .into(mAd_img);
                                    musicTypeAdapter.addFooterView(mInflate);
                                    nativeAD.onAdShowed(mAd_relative);
                                    musicTypeAdapter.notifyDataSetChanged();
                                    
                                    
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            
                            @Override
                            public void onAdLoadFailed(int i, String s) {
                                Log.i("adjson", "onAdLoadSuccess: " + s);
                                
                                
                            }
                        });
                        if (adLoader != null) adLoader.loadAds();
                    }
                });
    }
    
    
}
