package com.lzjtu.lucy.smart_shopping.frag;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.lzjtu.lucy.smart_shopping.R;
import com.lzjtu.lucy.smart_shopping.act.MainAct;
import com.lzjtu.lucy.smart_shopping.adapter.ProductAdapter;
import com.lzjtu.lucy.smart_shopping.dao.DatabaseHelper;
import com.lzjtu.lucy.smart_shopping.listener.OnItemClickListener;
import com.lzjtu.lucy.smart_shopping.manager.ProductManager;
import com.lzjtu.lucy.smart_shopping.model.Product;
import java.util.ArrayList;
import java.util.List;

public class HomeFrag extends BaseFrag {


  private ProductAdapter adapter;

  private RecyclerView recyclerView;

  @Override
  public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.frag_home, container, false);
    recyclerView = view.findViewById(R.id.product_list);
    return view;
  }

  @Override
  public void initData() {
    ProductManager.getInstance().getProducts(getContext(), products -> {
      adapter = new ProductAdapter(products);
      adapter.setOnItemClicklistener(new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
          Toast.makeText(getContext(), products.get(position).productName, Toast.LENGTH_LONG)
              .show();
        }
      });
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
      recyclerView.setAdapter(adapter);
      recyclerView
          .addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    });


  }
}
