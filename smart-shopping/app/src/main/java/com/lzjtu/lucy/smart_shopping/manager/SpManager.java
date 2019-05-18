package com.lzjtu.lucy.smart_shopping.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.lzjtu.lucy.smart_shopping.app.App;

public class SpManager {

  private static final String SHOPPING_SP = "SHOPPING_SP";

  private static SpManager instance;

  private SpManager() {
    init();
  }

  public static SpManager getInstance() {
    if (instance == null) {
      instance = new SpManager();
    }
    return instance;
  }

  SharedPreferences sp;
  Editor editor;

  private void init() {
    sp = App.Context.getSharedPreferences(SHOPPING_SP,
        Context.MODE_PRIVATE);
    editor = sp.edit();
  }

  public void put(String key, int value) {
    editor.putInt(key, value);
    editor.commit();
  }

  public int get(String key, int defValue) {
    return sp.getInt(key, defValue);
  }

  public void put(String key, boolean value) {
    editor.putBoolean(key, value);
    editor.commit();
  }

  public boolean get(String key, boolean defValue) {
    return sp.getBoolean(key, defValue);
  }

  public void put(String key, String value) {
    editor.putString(key, value);
    editor.commit();
  }

  public String get(String key, String defValue) {
    return sp.getString(key, defValue);
  }

  public void put(String key, float value) {
    editor.putFloat(key, value);
    editor.commit();
  }

  public float get(String key, float defValue) {
    return sp.getFloat(key, defValue);
  }

  public void put(String key, long value) {
    editor.putLong(key, value);
    editor.commit();
  }

  public long get(String key, long defValue) {
    return sp.getLong(key, defValue);
  }
  public class SpConsts{
    public static final String IS_LOGIN = "is_login";
    public static final String LOGIN_USER_NAME = "login_user_name";
  }
}
