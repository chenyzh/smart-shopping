package com.lzjtu.lucy.smart_shopping.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

public class RecyclerViewDivider extends RecyclerView.ItemDecoration {

  private Drawable divider;
  private int dividerHeight = 2;
  private int orientation;

  private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

  public RecyclerViewDivider(Context context, int orientation) {
    if (orientation != LinearLayoutManager.VERTICAL
        && orientation != LinearLayoutManager.HORIZONTAL) {
      throw new IllegalArgumentException("输入参数错误");
    }
    this.orientation = orientation;
    final TypedArray a = context.obtainStyledAttributes(ATTRS);
    divider = a.getDrawable(0);
    a.recycle();
  }
}
