package com.lzjtu.lucy.smart_shopping.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.lzjtu.lucy.smart_shopping.R;
import com.lzjtu.lucy.smart_shopping.dao.DatabaseHelper;
import com.lzjtu.lucy.smart_shopping.model.Product;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ProductManager {

  private static ProductManager instance;

  private ProductManager() {
  }

  public static ProductManager getInstance() {
    if (instance == null) {
      instance = new ProductManager();
    }
    return instance;
  }

  private DatabaseHelper dbHelper;
  private List<Product> products = new ArrayList<>();

  public void getProducts(Context context, Action1<List<Product>> action) {
    Observable.create(new OnSubscribe<List<Product>>() {
      @Override
      public void call(Subscriber<? super List<Product>> subscriber) {
        if (products.isEmpty()) {
          queryProducts(context);
        }
        subscriber.onNext(products);
      }
    }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(action);
  }

  public void getProduct(Context context, int id,Action1<Product> action) {
    Observable.create(new OnSubscribe<Product>() {
      @Override
      public void call(Subscriber<? super Product> subscriber) {
        if (products.isEmpty()){
          queryProducts(context);
        }
        for (Product p:products) {
          if (p.id == id){
            subscriber.onNext(p);
            break;
          }
        }
      }
    }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(action);
  }

  private void queryProducts(Context context){
    dbHelper = new DatabaseHelper(context.getApplicationContext());
    products = new ArrayList<>();
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    Cursor cursor = db.query(dbHelper.TABLE_PRODUCTS, null, null, null, null, null, null);
    cursor.moveToFirst();
    while (cursor.moveToNext()) {
      Product product = new Product();
      int id = cursor.getInt(0);
      String title = cursor.getString(cursor.getColumnIndex("title"));
      double price = cursor.getDouble(cursor.getColumnIndex("price"));
      double lowPrice = cursor.getDouble(cursor.getColumnIndex("low_price"));
      product.id = id;
      product.productName = title;
      product.originalPrice = price + "";
      product.nowPrice = lowPrice + "";
      products.add(product);
    }
  }

  public static int getImageId(int productId) {
    switch (productId) {
      case 1:
        return R.drawable.shijin;
      case 2:
        return R.drawable.mianjinzhi;
      case 3:
        return R.drawable.diaopai;
      case 4:
        return R.drawable.libai;
      case 5:
        return R.drawable.adaofu;
      case 6:
        return R.drawable.liuliumei;
      case 7:
        return R.drawable.monster;
      case 8:
        return R.drawable.huolongguo;
      case 9:
        return R.drawable.bread;
      case 10:
        return R.drawable.xiaotaimang;
      case 11:
        return R.drawable.naipi;
      case 12:
        return R.drawable.binghongcha;
      case 13:
        return R.drawable.nanguabing;
      default:
        return R.drawable.image_default;

    }
  }

}
