package com.maj.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.maj.demo.R;
import com.maj.demo.util.ToolsUtil;

/**
 * Created by maJian on 2017/12/20 0020 17:32.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private Context mContext;
    private int[] ids;

    private OnItemClickListener onItemClickListener;

    private int centerPosition = -1;//居中的view position
    private int preCenterPosition = -1;//之前居中的view position
    private int padding;

    public interface OnItemClickListener{
        public void OnItemClick(int position);
    }

    public void setCenterPosition(int centerPosition) {
        this.centerPosition = centerPosition;
        notifyItemChanged(centerPosition);
        if (preCenterPosition != -1 && preCenterPosition != centerPosition){
            notifyItemChanged(preCenterPosition);
        }
        preCenterPosition = centerPosition;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TestAdapter(Context mContext, int[] ids) {
        this.mContext = mContext;
        this.ids = ids;
        padding = ToolsUtil.dip2px(mContext, 10);
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView imgItem;

        public ViewHolder(View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.img_item);

        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.imgItem.setImageResource(ids[position%7]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(position);
            }
        });

        if (centerPosition == position){
            holder.itemView.setPadding(0,0,0,0);
        }else {
            holder.itemView.setPadding(padding,padding,padding,padding);
        }
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}
