package com.biyi.hypnosis.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.biyi.hypnosis.R;
import com.biyi.hypnosis.http.model.MusicListModel;
import com.biyi.hypnosis.http.model.TagListModel;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Description：
 * Time：2019-09-23 22:08
 *
 * @author：ltc
 */
public class MusicTypeAdapter extends  BaseRecyclerAdapter<TagListModel.TagListBean> {
    
    public MusicTypeAdapter(Context ctx, List<TagListModel.TagListBean> list) {
        super(ctx, list);
    }
    
    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_music_type;
    }
    
    @Override
    public void bindData(RecyclerViewHolder holder, int position, TagListModel.TagListBean item) {
        ImageView imageView = holder.getImageView(R.id.iv_t_type);
        TextView textView = holder.getTextView(R.id.tv_t_type);
        Glide.with(mContext).load(item.getIconUrl()).into(imageView);
        textView.setText(item.getTagName());
    }
    
  
    
  
}
