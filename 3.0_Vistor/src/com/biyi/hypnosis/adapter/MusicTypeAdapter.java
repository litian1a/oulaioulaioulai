package com.biyi.hypnosis.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

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
public class MusicTypeAdapter extends BaseQuickAdapter<TagListModel.TagListBean, BaseViewHolder> {
    
    
    public MusicTypeAdapter(int layoutResId, @Nullable List<TagListModel.TagListBean> data) {
        super(layoutResId, data);
    }
    

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TagListModel.TagListBean item) {
        helper.setText(R.id.tv_t_type, item.getTagName());
        Glide.with(mContext).load(item.getIconUrl()).into((ImageView) helper.getView(R.id.iv_t_type));
    
    }
}
