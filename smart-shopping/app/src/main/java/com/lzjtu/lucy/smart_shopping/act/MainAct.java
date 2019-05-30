package com.lzjtu.lucy.smart_shopping.act;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.iflytek.cloud.SpeechUtility;
import com.lzjtu.lucy.smart_shopping.R;
import com.lzjtu.lucy.smart_shopping.adapter.ProductAdapter;
import com.lzjtu.lucy.smart_shopping.adapter.pager.ViewPagerAdapter;
import com.lzjtu.lucy.smart_shopping.dao.DatabaseHelper;
import com.lzjtu.lucy.smart_shopping.frag.HomeFrag;
import com.lzjtu.lucy.smart_shopping.frag.MineFrag;
import com.lzjtu.lucy.smart_shopping.listener.OnItemClickListener;
import com.lzjtu.lucy.smart_shopping.model.Product;
import com.lzjtu.lucy.smart_shopping.view.RecyclerViewDivider;
import java.util.ArrayList;
import java.util.List;

public class MainAct extends BaseAct {


  private ViewPager viewPager;
  private ViewPagerAdapter adapter;
  private TabLayout mainTab;
  private HomeFrag homeFrag = new HomeFrag();
  private MineFrag mineFrag = new MineFrag();

  @Override
  public void initView() {
    Fresco.initialize(this);
    setContentView(R.layout.activity_main);
    setTitle("首页");
    initRightView(new OnClickListener() {
      @Override
      public void onClick(View v) {
        SpeechAct.Start(MainAct.this);
      }
    });
    viewPager = findViewById(R.id.main_content);
    List<Fragment> fragments = new ArrayList<>();
    fragments.add(homeFrag);
    fragments.add(mineFrag);
    List<String> titles = new ArrayList<>();
    titles.add("首页");
    titles.add("我的");
    adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments,titles);
    viewPager.setAdapter(adapter);
    mainTab = findViewById(R.id.main_bottom);
    mainTab.setupWithViewPager(viewPager);
    mainTab.addOnTabSelectedListener(new OnTabSelectedListener() {
      @Override
      public void onTabSelected(Tab tab) {
        int position = tab.getPosition();
        if (position == 0){
          setTitle("首页");
        }else{
          setTitle("我的");
        }
      }

      @Override
      public void onTabUnselected(Tab tab) {

      }

      @Override
      public void onTabReselected(Tab tab) {

      }
    });
  }

  @Override
  public void initData() {

  }
}
