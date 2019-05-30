package com.lzjtu.lucy.smart_shopping.act;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.lzjtu.lucy.smart_shopping.R;
import com.lzjtu.lucy.smart_shopping.adapter.ProductAdapter;
import com.lzjtu.lucy.smart_shopping.listener.OnItemClickListener;
import com.lzjtu.lucy.smart_shopping.manager.ProductManager;

public class SearchAct extends BaseAct {

  private static final String KEY = "key";

  private ProductAdapter adapter;

  private RecyclerView recyclerView;
  private String key;

  public static void Start(Context from,String key){
    Intent intent = new Intent( from,SearchAct.class);
    intent.putExtra(KEY,key);
    from.startActivity(intent);
  }

  @Override
  public void initView() {
    setContentView(R.layout.frag_home);
    recyclerView = findViewById(R.id.product_list);
  }

  @Override
  public void initData() {
    key = getIntent().getStringExtra(KEY);
    ProductManager.getInstance().getProducts(this,key, products -> {
      adapter = new ProductAdapter(SearchAct.this,products);
      adapter.setOnItemClicklistener(new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
          Toast.makeText(SearchAct.this, products.get(position).productName, Toast.LENGTH_LONG)
              .show();
          ProductDetailAct.Start(SearchAct.this,products.get(position).id);
        }
      });
      recyclerView.setLayoutManager(new LinearLayoutManager(SearchAct.this));
      recyclerView.setAdapter(adapter);
      recyclerView
          .addItemDecoration(new DividerItemDecoration(SearchAct.this, LinearLayoutManager.VERTICAL));
    });
  }
}
