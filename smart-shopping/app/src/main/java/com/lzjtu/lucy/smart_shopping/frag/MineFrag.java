package com.lzjtu.lucy.smart_shopping.frag;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lzjtu.lucy.smart_shopping.R;
import com.lzjtu.lucy.smart_shopping.act.ShopCarAct;
import com.lzjtu.lucy.smart_shopping.manager.SpManager;
import com.lzjtu.lucy.smart_shopping.manager.SpManager.SpConsts;

public class MineFrag extends BaseFrag {

  SimpleDraweeView headerView;
  TextView nameView;
  LinearLayout settingView;
  LinearLayout exitView;
  LinearLayout shopCarView;

  AlertDialog.Builder dialogBuilder;

  @Override
  public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.frag_mine,container,false);
    headerView = view.findViewById(R.id.header);
    RoundingParams params = new RoundingParams();
    params.setBorder(R.color.white,5);
    params.setRoundAsCircle(true);
    headerView.getHierarchy().setRoundingParams(params);
    nameView = view.findViewById(R.id.name);
    settingView = view.findViewById(R.id.setting);
    shopCarView = view.findViewById(R.id.shop_car);
    shopCarView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ShopCarAct.Start(getActivity());
      }
    });
    exitView = view.findViewById(R.id.exit);
    dialogBuilder = new AlertDialog.Builder(getContext());
    dialogBuilder.setMessage("确定退出？");
    dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        SpManager.getInstance().put(SpConsts.IS_LOGIN,false);
        SpManager.getInstance().put(SpConsts.LOGIN_USER_NAME,"");
        getActivity().finish();
      }
    });
    dialogBuilder.setNegativeButton("取消",((dialog, which) -> {
      dialogBuilder.create().dismiss();
    }));
    exitView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        dialogBuilder.create().show();
      }
    });
    return view;
  }

  @Override
  public void initData() {
    nameView.setText(SpManager.getInstance().get(SpConsts.LOGIN_USER_NAME,"——"));
  }
}
