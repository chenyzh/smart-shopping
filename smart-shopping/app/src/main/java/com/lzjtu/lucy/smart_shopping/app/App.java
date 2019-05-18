package com.lzjtu.lucy.smart_shopping.app;

import android.app.Application;

public class App extends Application {

  public static Application Context;

  @Override
  public void onCreate() {
    super.onCreate();
    Context = this;
  }
}
