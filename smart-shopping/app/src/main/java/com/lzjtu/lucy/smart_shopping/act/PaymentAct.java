package com.lzjtu.lucy.smart_shopping.act;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.lzjtu.lucy.smart_shopping.R;
import com.lzjtu.lucy.smart_shopping.manager.ShopCarManager;

public class PaymentAct extends BaseAct {

  private static final String PRICE = "price";

  TextView price;
  Button pay;

  public static void Start(Context from, String price) {
    Intent intent = new Intent(from, PaymentAct.class);
    intent.putExtra(PRICE, price);
    from.startActivity(intent);
  }

  @Override
  public void initView() {
    setContentView(R.layout.activity_payment);
    price = findViewById(R.id.total_price);
    pay = findViewById(R.id.pay);
    pay.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ShopCarManager.getInstance().clearShopCar();
        price.setText("支付成功！");
        v.setVisibility(View.GONE);
      }
    });
  }

  @Override
  public void initData() {
    price.setText("¥"+getIntent().getStringExtra(PRICE));
  }
}
