package com.lzjtu.lucy.smart_shopping.act;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzjtu.lucy.smart_shopping.dao.DatabaseHelper;
import com.lzjtu.lucy.smart_shopping.manager.ReadJsonManager;
import com.lzjtu.lucy.smart_shopping.manager.SpManager;
import com.lzjtu.lucy.smart_shopping.manager.SpManager.SpConsts;
import com.lzjtu.lucy.smart_shopping.model.Product;
import java.util.List;
import java.util.Timer;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SplashAct extends BaseAct {

  CountDownTimer countDownTimer;
  DatabaseHelper databaseHelper;

  @Override
  public void initView() {

  }

  @Override
  public void initData() {
    databaseHelper = new DatabaseHelper(getApplicationContext());

    Observable.create(new OnSubscribe<Integer>() {

      @Override
      public void call(Subscriber<? super Integer> subscriber) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query(databaseHelper.TABLE_PRODUCTS, null, null, null, null, null, null);
        if (cursor.getCount() == 0) {
          List<Product> data = new Gson()
              .fromJson(ReadJsonManager.PRODUCT_JSON, new TypeToken<List<Product>>() {
              }.getType());
//        db.insert(databaseHelper.TABLE_PRODUCTS,)
          Log.d("SplashAct", data.toString());
          for (Product product : data) {
            db.insert(databaseHelper.TABLE_PRODUCTS, null, product2ContentValue(product));
          }
        }
        cursor.close();
        subscriber.onNext(0);
      }
    }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(integer -> {
          boolean isLogin = SpManager.getInstance().get(SpConsts.IS_LOGIN, false);
          Intent intent;
          if (isLogin) {
            intent = new Intent(SplashAct.this, MainAct.class);
          } else {
            intent = new Intent(SplashAct.this, LoginAct.class);
          }
          startActivity(intent);
          finish();
        });

    countDownTimer = new CountDownTimer(3000, 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        Intent intent = new Intent(SplashAct.this, MainAct.class);
        startActivity(intent);
        finish();
      }

      @Override
      public void onFinish() {

      }
    };
//    countDownTimer.start();
  }

  private ContentValues product2ContentValue(Product product) {
    ContentValues contentValues = new ContentValues();
    contentValues.put("id", product.id);
    contentValues.put("title", product.productName);
    contentValues.put("price", product.originalPrice);
    contentValues.put("low_price", product.nowPrice);
    return contentValues;
  }

}
