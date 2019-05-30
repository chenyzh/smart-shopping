package com.lzjtu.lucy.smart_shopping.adapter;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.lzjtu.lucy.smart_shopping.R;
import com.lzjtu.lucy.smart_shopping.act.BaseAct;
import com.lzjtu.lucy.smart_shopping.listener.OnItemClickListener;
import com.lzjtu.lucy.smart_shopping.manager.ProductManager;
import com.lzjtu.lucy.smart_shopping.manager.ShopCarManager;
import com.lzjtu.lucy.smart_shopping.model.Product;
import java.util.List;

public class ShopCarAdapter extends BaseAdapter<Product, ShopCarAdapter.ViewHolder> {

  private OnItemClickListener listener;
  private Context context;

  public ShopCarAdapter(Context context,List<Product> data) {
    super(data);
    this.context = context;
  }

  public void setOnItemClicklistener(OnItemClickListener listener){
    this.listener = listener;
  }

  @Override
  public ViewHolder createHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shop_car,null,false);
    return new ViewHolder(view);
  }

  @Override
  public void bindHolder(ViewHolder holder, int i) {
    holder.img.setImageResource(ProductManager.getImageId(data.get(i).id));
    holder.title.setText(data.get(i).productName);
    holder.price.setText("¥"+data.get(i).nowPrice);
    holder.removeBtn.setOnClickListener(v -> {
      Builder builder = new Builder(context);
      builder.setTitle("提示").setMessage("确定删除该商品吗？").setNegativeButton("确定", (d, i1) ->{
        ShopCarManager.getInstance().remove(data.get(i).id,o -> {
          notifyDataSetChanged();
          ((BaseAct)context).shopCarBehavior.onNext(null);
        });
      }).setPositiveButton("取消",(d,w) ->{});
      builder.create().show();
    });
    holder.itemView.setOnClickListener(v -> listener.onItemClick(v,i));
  }

  class ViewHolder extends RecyclerView.ViewHolder{

    ImageView img;
    TextView title;
    TextView price;
    Button removeBtn;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      img = itemView.findViewById(R.id.product_img);
      title = itemView.findViewById(R.id.title);
      price = itemView.findViewById(R.id.price);
      removeBtn = itemView.findViewById(R.id.remove);

    }
  }
}

