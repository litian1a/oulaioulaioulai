package com.biyi.hypnosis.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.biyi.hypnosis.R;

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
    private RecyclerViewHolder holder = null;
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;
    private int mHeaderCount=1;//头部View个数
    private int mBottomCount=1;//底部View个数

    public BaseRecyclerAdapter(Context ctx, List<T> list) {
        mData = (list != null) ? list : new ArrayList<T>();
        mContext = ctx;
        mInflater = LayoutInflater.from(ctx);
    }
    public int getContentItemCount(){
        return mData.size();
    }
//    public void setHeaderView() {
//        viewType = ITEM_TYPE_HEADER;
//        notifyItemInserted(0);
//    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType ==ITEM_TYPE_HEADER) {
            holder = new HeaderViewHolder(mInflater.inflate(R.layout.rv_header, parent, false));
        } else if (viewType == ITEM_TYPE_CONTENT) {
            holder =  new RecyclerViewHolder(mContext,
                    mInflater.inflate(getItemLayoutId(viewType), parent, false));
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            holder =  new BottomViewHolder(mInflater.inflate(R.layout.rv_footer, parent, false));
        }
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
    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerViewHolder {

        public HeaderViewHolder(View itemView) {
            super(null,itemView);
        }
    }
    //底部 ViewHolder
    public static class BottomViewHolder extends RecyclerViewHolder {

        public BottomViewHolder(View itemView) {
            super(null,itemView);
        }
    }
    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (mBottomCount != 0 && position >= (dataItemCount)) {
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    //内容 ViewHolder
//    public static class ContentViewHolder extends RecyclerViewHolder{
//        public ContentViewHolder(View itemView) {
//            super(null,itemView);
////            textView=(TextView)itemView.findViewById(R.id.tv_item_text);
//        }
//    }
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder,final  int position) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof RecyclerViewHolder) {
//            ((RecyclerViewHolder) holder).textView.setText(texts[position - mHeaderCount]);
            bindData(holder, position, mData.get(position));
        } else if (holder instanceof BottomViewHolder) {

        }

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