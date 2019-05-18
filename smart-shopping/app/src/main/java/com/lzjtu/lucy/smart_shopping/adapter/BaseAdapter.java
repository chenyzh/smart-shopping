package com.lzjtu.lucy.smart_shopping.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter {

  List<T> data = null;

  public BaseAdapter(List<T> data) {
    if (data == null){
      data = new ArrayList<>();
    }
    this.data = data;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    return createHolder(viewGroup,i);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    bindHolder(viewHolder,i);
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  public abstract ViewHolder createHolder(ViewGroup viewGroup,int i);

  public abstract void bindHolder(ViewHolder holder,int i);

}
