package com.biyi.hypnosis.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.biyi.hypnosis.R;
import com.biyi.hypnosis.adapter.BaseRecyclerAdapter;
import com.biyi.hypnosis.adapter.MusicTypeAdapter;
import com.biyi.hypnosis.http.RetrofitManager;
import com.biyi.hypnosis.http.model.TagListModel;
import com.biyi.hypnosis.http.rxjava.TransformUtils;
import com.biyi.hypnosis.utils.ListUtils;
import com.biyi.hypnosis.utils.SpUtils;

import rx.Observer;

/**
 * 音乐类型Activity
 */
public class MusicTypeActivity extends BaseActivity {
    
    private ImageView musictype_back;
    private TextView t_musictype;
    
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
        tvMusic.setText("当前选择的是：" + SpUtils.getString(SpUtils.KEY_TAG_NAME));
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
                        ;
                        final MusicTypeAdapter musicTypeAdapter;
                        musicTypeAdapter = new MusicTypeAdapter(MusicTypeActivity.this, tagListModel.getTagList());
                        recyclerView.setAdapter(musicTypeAdapter);
                        musicTypeAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View itemView, int pos) {
                                TagListModel.TagListBean tagListBean = musicTypeAdapter.getDatas().get(pos);
                                SpUtils.putInt(SpUtils.KEY_TAG_ID,tagListBean.getTagId());
                                SpUtils.putString(SpUtils.KEY_TAG_NAME,tagListBean.getTagName());
                                HomeActivity.startActivity(MusicTypeActivity.this);
                                finish();
    
                            }
                        });
                    }
                });
    }
    
    
}
