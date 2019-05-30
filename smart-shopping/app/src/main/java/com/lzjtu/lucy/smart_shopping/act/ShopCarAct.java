package com.lzjtu.lucy.smart_shopping.act;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.lzjtu.lucy.smart_shopping.R;
import com.lzjtu.lucy.smart_shopping.adapter.ProductAdapter;
import com.lzjtu.lucy.smart_shopping.adapter.ShopCarAdapter;
import com.lzjtu.lucy.smart_shopping.listener.OnItemClickListener;
import com.lzjtu.lucy.smart_shopping.manager.ProductManager;
import com.lzjtu.lucy.smart_shopping.manager.ShopCarManager;
import com.lzjtu.lucy.smart_shopping.model.Product;
import java.util.List;

public class ShopCarAct extends BaseAct {

  RecyclerView shopCarList;
  TextView totalPrice;
  Button toPay;

  ShopCarAdapter adapter;

  public static void Start(Context from) {
    Intent intent = new Intent(from, ShopCarAct.class);
    from.startActivity(intent);
  }

  @Override
  public void initView() {
    setContentView(R.layout.activity_shop_car);
    back();
    shopCarList = findViewById(R.id.shop_car_list);
    totalPrice = findViewById(R.id.total_price);
    toPay = findViewById(R.id.to_payment);
    toPay.setOnClickListener(
        v -> {
          PaymentAct.Start(ShopCarAct.this, ShopCarManager.getInstance().getTotalPrice());
          finish();
        });
  }

  @Override
  public void initData() {
    List<Product> products = ShopCarManager.getInstance().getProducts();
    adapter = new ShopCarAdapter(this, products);
    adapter.setOnItemClicklistener(new OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        Toast.makeText(ShopCarAct.this, products.get(position).productName, Toast.LENGTH_LONG)
            .show();
        ProductDetailAct.Start(ShopCarAct.this, products.get(position).id);
      }
    });
    shopCarList.setLayoutManager(new LinearLayoutManager(this));
    shopCarList.setAdapter(adapter);
    shopCarList
        .addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    totalPrice.setText("¥" + ShopCarManager.getInstance().getTotalPrice());
    shopCarBehavior.subscribe(o -> {
      totalPrice.setText("¥" + ShopCarManager.getInstance().getTotalPrice());
    });
  }
}
