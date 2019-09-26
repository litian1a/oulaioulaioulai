package com.biyi.hypnosis.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    private int selectedPosition = -1;
    public MusicListAdapter(Context ctx, List<MusicListModel.TagListBean> list) {
        super(ctx, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_music_list;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, MusicListModel.TagListBean item) {
        TextView textView = holder.getTextView(R.id.music_name);
        textView.setText(item.getMusicName());
                if (selectedPosition == position){
            holder.getImageView(R.id.iv_palysmall).setVisibility(View.GONE);
            holder.getTextView(R.id.tv_playing).setVisibility(View.VISIBLE);
        }else {
            holder.getTextView(R.id.tv_playing).setVisibility(View.GONE);
            holder.getImageView(R.id.iv_palysmall).setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemChanged(selectedPosition);//刷新以前item 回置状态
                selectedPosition = position;
                notifyItemChanged(selectedPosition);//刷新当前点击item
            }
        });

//        holder.
    }
    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
  
    
  
}
