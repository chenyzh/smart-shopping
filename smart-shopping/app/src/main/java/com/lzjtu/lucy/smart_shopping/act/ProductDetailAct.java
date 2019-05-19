package com.lzjtu.lucy.smart_shopping.act;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.lzjtu.lucy.smart_shopping.R;
import com.lzjtu.lucy.smart_shopping.manager.ProductManager;
import com.lzjtu.lucy.smart_shopping.model.Product;

public class ProductDetailAct extends BaseAct {

  ImageView productImg;
  TextView productTitle;
  TextView price;
  TextView priceOriginal;
  Button add2ShopCar;
  ImageView shopCarImg;
  TextView totalPrice;
  Button buy;

  private static final String PRODUCT_ID = "product_id";

  private int id;
  private Product product;

  public static void Start(Context from,int id){
    Intent intent = new Intent(from,ProductDetailAct.class);
    intent.putExtra(PRODUCT_ID,id);
    from.startActivity(intent);
  }

  @Override
  public void initView() {
    setContentView(R.layout.activity_product_detail);
    productImg = findViewById(R.id.product_img);
    productTitle = findViewById(R.id.detail_title);
    price = findViewById(R.id.detail_price);
    priceOriginal = findViewById(R.id.detail_price_original);
    add2ShopCar = findViewById(R.id.add_to_shop_car);
    shopCarImg = findViewById(R.id.product_shop_car);
    totalPrice = findViewById(R.id.product_total_price);
    buy = findViewById(R.id.product_buy);
  }

  @Override
  public void initData() {
    id = getIntent().getIntExtra(PRODUCT_ID,-1);
    ProductManager.getInstance().getProduct(this,id,p -> {
      productImg.setImageResource(ProductManager.getImageId(id));
      productTitle.setText(p.productName);
      priceOriginal.setText(p.originalPrice);
      priceOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
      price.setText(p.nowPrice);
    });
  }
}
