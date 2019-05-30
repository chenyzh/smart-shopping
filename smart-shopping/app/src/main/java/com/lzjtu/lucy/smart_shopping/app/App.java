package com.lzjtu.lucy.smart_shopping.app;

import android.app.Application;
import com.iflytek.cloud.SpeechUtility;
import com.lzjtu.lucy.smart_shopping.R;

public class App extends Application {

  public static Application Context;

  @Override
  public void onCreate() {
    SpeechUtility.createUtility(App.this, "appid=" + getString(R.string.app_id));
    super.onCreate();
    Context = this;
  }
}
