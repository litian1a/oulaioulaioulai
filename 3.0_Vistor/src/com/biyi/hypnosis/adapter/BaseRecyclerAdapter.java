package com.biyi.hypnosis.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * Time：2019-09-23 22:06
 *
 * @author：ltc
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    protected final List<T> mData;
    protected final Context mContext;
    protected LayoutInflater mInflater;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;
    
    public BaseRecyclerAdapter(Context ctx, List<T> list) {
        mData = (list != null) ? list : new ArrayList<T>();
        mContext = ctx;
        mInflater = LayoutInflater.from(ctx);
    }
    
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder = new RecyclerViewHolder(mContext,
                mInflater.inflate(getItemLayoutId(viewType), parent, false));
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongClickListener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                    return true;
                }
            });
        }
        return holder;
    }
    
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindData(holder, position, mData.get(position));
    }
    
    @Override
    public int getItemCount() {
        return mData.size();
    }
    
    public void add(int pos, T item) {
        mData.add(pos, item);
        notifyItemInserted(pos);
    }
    
    public void delete(int pos,String  defaultprompt) {
        if (mData.size()>pos){
            mData.remove(pos);
            notifyItemRemoved(pos);
        }else {
            Toast.makeText(mContext,defaultprompt, Toast.LENGTH_SHORT).show();
        }
    }
    public List<T> getDatas() {
        return mData;
    }
    
    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }
    
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }
    
    /**
     * 设置item的布局
     * @param viewType
     * @return
     */
    abstract public int getItemLayoutId(int viewType);
    
    /**
     * 设置数据
     * @param holder
     * @param position
     * @param item
     */
    abstract public void bindData(RecyclerViewHolder holder, int position, T item);
    
    /**
     * 添加每个条目的点击事件
     */
    public interface OnItemClickListener {
        public void onItemClick(View itemView, int pos);
    }
    
    /**
     * 添加每个条目的长按事件
     */
    public interface OnItemLongClickListener {
        public void onItemLongClick(View itemView, int pos);
    }
}