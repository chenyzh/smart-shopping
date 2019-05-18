package com.lzjtu.lucy.smart_shopping.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.lzjtu.lucy.smart_shopping.model.Product;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

  private static final String DB_NAME = "database.db";

  private static final int DB_VERSION = 1;

  public static final String TABLE_PRODUCTS = "products";

  private static final String CREATE_PRODUCTS_TABLE_SQL = "create table " +
      TABLE_PRODUCTS + "("
      + "id integer primary key autoincrement,"
      + "title varchar(20) not null,"
      + "price varchar(10) not null,"
      + "low_price varchar(10) not null,"
      + "product_img integer"
      + ");";


  public DatabaseHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_PRODUCTS_TABLE_SQL);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }

}
