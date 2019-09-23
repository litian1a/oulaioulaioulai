package com.biyi.hypnosis.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.biyi.hypnosis.R;
import com.biyi.hypnosis.http.model.MusicListModel;
import com.biyi.hypnosis.http.model.TagListModel;
import com.biyi.hypnosis.utils.SpUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Description：
 * Time：2019-09-23 22:08
 *
 * @author：ltc
 */
public class MusicListAdapter extends  BaseRecyclerAdapter<MusicListModel.TagListBean> {
    
    public MusicListAdapter(Context ctx, List<MusicListModel.TagListBean> list) {
        super(ctx, list);
    }
    
    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_music_list;
    }
    
    @Override
    public void bindData(RecyclerViewHolder holder, int position, MusicListModel.TagListBean item) {
        TextView textView = holder.getTextView(R.id.music_name);
        textView.setText(item.getMusicName());
    }
    
  
    
  
}
