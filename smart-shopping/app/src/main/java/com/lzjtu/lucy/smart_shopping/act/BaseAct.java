package com.lzjtu.lucy.smart_shopping.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.lzjtu.lucy.smart_shopping.R;
import rx.subjects.BehaviorSubject;

public abstract class BaseAct extends AppCompatActivity {

  public BehaviorSubject shopCarBehavior = BehaviorSubject.create();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initView();
    initData();
  }

  public void back(){
    ImageView back = findViewById(R.id.header_back);
    back.setVisibility(View.VISIBLE);
    back.setOnClickListener(v -> finish());
  }

  public void setTitle(String title) {
    TextView titleView = findViewById(R.id.header_title);
    titleView.setText(title);
  }

  public void initRightView(OnClickListener listener){
    ImageView rightView = findViewById(R.id.header_right);
    rightView.setVisibility(View.VISIBLE);
    rightView.setOnClickListener(listener);
  }

  public abstract void initView();

  public abstract void initData();
}
