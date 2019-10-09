package com.kaola.sleep.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kaola.sleep.R;
import com.kaola.sleep.http.model.MusicListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Description：
 * Time：2019-09-23 22:08
 *
 * @author：ltc
 */
public class MusicListAdapter extends BaseQuickAdapter<MusicListModel.TagListBean, BaseViewHolder> {
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    
    
    private int selectedPosition = -1;
    
    public MusicListAdapter(int layoutResId, @Nullable List<MusicListModel.TagListBean> data) {
        super(layoutResId, data);
    }
    
    
    @Override
    protected void convert(@NonNull BaseViewHolder helper, MusicListModel.TagListBean item) {
        
        helper.setText(R.id.music_name, item.getMusicName());
        int position = getData().indexOf(item);
        if (item.isPlaying()) {
            helper.setGone(R.id.tv_playing, true);
            helper.setGone(R.id.iv_palysmall, false);
            helper.setGone(R.id.front, true);
            helper.setText(R.id.tv_playing, "正在播放");
    
    
    
        } else if (position == selectedPosition && !item.isPlaying() ) {
            helper.setGone(R.id.front, true);
            helper.setGone(R.id.iv_palysmall, false);
            helper.setGone(R.id.tv_playing, true);
            helper.setText(R.id.tv_playing, "已暂停");
            
            
        } else {
            
            helper.setGone(R.id.iv_palysmall, true);
            helper.setGone(R.id.tv_playing, false);
            helper.setGone(R.id.front, false);
        }
        
    }
    
    
    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
    
    public int getSelectedPosition() {
        return selectedPosition;
    }
    
    
}
