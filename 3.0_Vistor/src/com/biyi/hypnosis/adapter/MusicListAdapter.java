package com.biyi.hypnosis.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.biyi.hypnosis.R;
import com.biyi.hypnosis.http.model.MusicListModel;
import com.biyi.hypnosis.http.model.TagListModel;
import com.biyi.hypnosis.utils.SpUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Description：
 * Time：2019-09-23 22:08
 *
 * @author：ltc
 */
public class MusicListAdapter extends BaseQuickAdapter<MusicListModel.TagListBean , BaseViewHolder> {
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    private int selectedPosition = -1;
    
    public MusicListAdapter(int layoutResId, @Nullable List<MusicListModel.TagListBean> data) {
        super(layoutResId, data);
    }
    
    
    @Override
    protected void convert(@NonNull BaseViewHolder helper, MusicListModel.TagListBean item) {
        helper.setText(R.id.music_name,item.getMusicName());
    
    }
    
   
    
  
}
