package com.lzjtu.lucy.smart_shopping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.lzjtu.lucy.smart_shopping.R;
import com.lzjtu.lucy.smart_shopping.listener.OnItemClickListener;
import com.lzjtu.lucy.smart_shopping.manager.ProductManager;
import com.lzjtu.lucy.smart_shopping.model.Product;
import java.util.List;

public class ProductAdapter extends BaseAdapter<Product, ProductAdapter.ViewHolder>{

  private OnItemClickListener listener;
  Context context;

  public ProductAdapter(Context context,List data) {
    super(data);
    this.context = context;
  }

  public void setOnItemClicklistener(OnItemClickListener listener){
    this.listener = listener;
  }

  @Override
  public ViewHolder createHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product,viewGroup,false);
    return new ViewHolder(view);
  }

  @SuppressLint("ResourceType")
  @Override
  public void bindHolder(ViewHolder holder, int i) {
    holder.image.setImageResource(ProductManager.getImageId(data.get(i).id));
    holder.title.setText(data.get(i).productName);
    holder.originalPrice.setText("原价:"+data.get(i).originalPrice);
    holder.originalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    holder.nowPrice.setText("现价:"+data.get(i).nowPrice);
    holder.itemView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.onItemClick(v,i);
      }
    });
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView originalPrice;
    TextView nowPrice;
    ImageView image;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      image = itemView.findViewById(R.id.product_img);
      title = itemView.findViewById(R.id.title);
      nowPrice = itemView.findViewById(R.id.price);
      originalPrice = itemView.findViewById(R.id.price_original);
    }
  }
}
