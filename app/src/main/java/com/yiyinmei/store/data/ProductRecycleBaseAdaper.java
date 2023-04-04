package com.yiyinmei.store.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yiyinmei.store.R;
import com.yiyinmei.store.beans.ProductInfo;

import java.util.ArrayList;

public class ProductRecycleBaseAdaper extends RecyclerView.Adapter<ProductRecycleBaseAdaper.ViewHolder>{
    private final OnItemClickListener mlistener;
    private ArrayList<ProductInfo> mData;

    public ProductRecycleBaseAdaper(ArrayList data,OnItemClickListener listener) {
        mData = data;
        mlistener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecycleBaseAdaper.ViewHolder holder, int position) {
        holder.tvId.setText(mData.get(position).getmId()+"");
        holder.tvName.setText(mData.get(position).getName());
        holder.tvStore.setText(mData.get(position).getStore()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.onClick(position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mlistener.onLongClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvId,tvName,tvStore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.datainfoid);
            tvName = itemView.findViewById(R.id.datainfoname);
            tvStore = itemView.findViewById(R.id.datainfostore);
        }
    }

    public interface OnItemClickListener {
        public void onClick(int position);
        public void onLongClick(int position);
    }
}
