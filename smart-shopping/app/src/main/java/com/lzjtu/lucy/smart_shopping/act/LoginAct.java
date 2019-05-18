package com.lzjtu.lucy.smart_shopping.act;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import com.lzjtu.lucy.smart_shopping.R;
import com.lzjtu.lucy.smart_shopping.manager.SpManager;
import com.lzjtu.lucy.smart_shopping.manager.SpManager.SpConsts;

public class LoginAct extends BaseAct {

  private EditText userNameView;
  private EditText passwordView;
  private CheckBox rememberView;
  private Button loginBtn;

  boolean isRemember = false;

  @Override
  public void initView() {
    setContentView(R.layout.activity_login);
    userNameView = findViewById(R.id.login_user_name);
    passwordView = findViewById(R.id.login_password);
    rememberView = findViewById(R.id.remember);
    rememberView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isRemember = !isRemember;
      }
    });
    loginBtn = findViewById(R.id.login);
    loginBtn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        String userName = userNameView.getText().toString().trim();
        String password = passwordView.getText().toString().trim();
        if (userName.length() != 11 && !userName.startsWith("1")) {
          Toast.makeText(LoginAct.this, "请输入正确格式的手机号", Toast.LENGTH_LONG).show();
          return;
        }
        if (password.length() < 6) {
          Toast.makeText(LoginAct.this, "请输入大于6位密码", Toast.LENGTH_LONG).show();
          return;
        }
        if (isRemember) {
          SpManager.getInstance().put(SpConsts.IS_LOGIN, true);
          SpManager.getInstance().put(SpConsts.LOGIN_USER_NAME, userName);
        }
        Intent intent = new Intent(LoginAct.this, MainAct.class);
        startActivity(intent);
        finish();
      }
    });
  }


  @Override
  public void initData() {

  }
}
