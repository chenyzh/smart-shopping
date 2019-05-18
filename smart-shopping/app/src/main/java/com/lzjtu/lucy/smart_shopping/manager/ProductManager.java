package com.lzjtu.lucy.smart_shopping.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    if (instance == null){
      instance = new ProductManager();
    }
    return instance;
  }
  private DatabaseHelper dbHelper;
  private List<Product> products = new ArrayList<>();

  public void getProducts(Context context,Action1<List<Product>> action) {
      Observable.create(new OnSubscribe<List<Product>>() {
        @Override
        public void call(Subscriber<? super List<Product>> subscriber) {
          if (products.isEmpty()) {
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
          subscriber.onNext(products);
        }
      }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(action);
    }

}
