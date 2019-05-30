package com.lzjtu.lucy.smart_shopping.act;

import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;
import com.lzjtu.lucy.smart_shopping.R;
import com.lzjtu.lucy.smart_shopping.util.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class SpeechAct extends BaseAct {

  EditText speechResult;
  Button startBtn;
  Button searchBtn;

  public static void Start(Context from) {
    Intent intent = new Intent(from, SpeechAct.class);
    from.startActivity(intent);
  }


  private static String TAG = SpeechAct.class.getSimpleName();
  //  // 语音听写对象
  // 语音听写UI
  private RecognizerDialog mIatDialog;
  // 用HashMap存储听写结果
  private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

  private StringBuffer buffer = new StringBuffer();
  // 引擎类型
  private String mEngineType = SpeechConstant.TYPE_CLOUD;

  private boolean mTranslateEnable = false;
  private String resultType = "json";

  private boolean cyclic = false;//音频流识别是否循环调用

  private Toast toast;

  int ret = 0;

  Handler han = new Handler() {

    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (msg.what == 0x001) {
        executeStream();
      }
    }
  };

  @Override
  public void initView() {
    setContentView(R.layout.activity_speech);
    back();
    speechResult = findViewById(R.id.speech_result);
    searchBtn = findViewById(R.id.search);
    searchBtn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        String key = speechResult.getText().toString();
        SearchAct.Start(SpeechAct.this,key);
        finish();
      }
    });
    startBtn = findViewById(R.id.start_speech);
    startBtn.setOnClickListener(v -> {
      // 移动数据分析，收集开始听写事件
      FlowerCollector.onEvent(SpeechAct.this, "iat_recognize");

      buffer.setLength(0);
      speechResult.setText(null);// 清空显示内容
      mIatResults.clear();
      // 显示听写对话框
      mIatDialog.setListener(mRecognizerDialogListener);
      mIatDialog.show();
      showTip("请开始说话...");
    });


    // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
    // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
    mIatDialog = new RecognizerDialog(this, mInitListener);

    toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
  }

  @Override
  public void initData() {

  }

  /**
   * 初始化监听器。
   */
  private InitListener mInitListener = new InitListener() {

    @Override
    public void onInit(int code) {
      Log.d(TAG, "SpeechRecognizer init() code = " + code);
      if (code != ErrorCode.SUCCESS) {
        showTip("初始化失败，错误码：" + code);
      }
    }
  };

  /**
   * 听写UI监听器
   */
  private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
    public void onResult(RecognizerResult results, boolean isLast) {
      if (mTranslateEnable) {
        printTransResult(results);
      } else {
        printResult(results);
      }

    }

    @Override
    public void onError(SpeechError speechError) {

    }
  };
  private void printTransResult(RecognizerResult results) {
    String trans = JsonParser.parseTransResult(results.getResultString(), "dst");
    String oris = JsonParser.parseTransResult(results.getResultString(), "src");

    if (TextUtils.isEmpty(trans) || TextUtils.isEmpty(oris)) {
      showTip("解析结果失败，请确认是否已开通翻译功能。");
    } else {
      speechResult.setText("原始语言:\n" + oris + "\n目标语言:\n" + trans);
    }

  }

  /**
   * 听写监听器。
   */
  private RecognizerListener mRecognizerListener = new RecognizerListener() {

    @Override
    public void onBeginOfSpeech() {
      // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
      showTip("开始说话");
    }

    @Override
    public void onError(SpeechError error) {
      // Tips：
      // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
      if (mTranslateEnable && error.getErrorCode() == 14002) {
        showTip(error.getPlainDescription(true) + "\n请确认是否已开通翻译功能");
      } else {
        showTip(error.getPlainDescription(true));
      }
    }

    @Override
    public void onEndOfSpeech() {
      // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
      showTip("结束说话");
    }

    @Override
    public void onResult(RecognizerResult results, boolean isLast) {
      Log.d(TAG, results.getResultString());
      if (resultType.equals("json")) {
        if (mTranslateEnable) {
          printTransResult(results);
        } else {
          printResult(results);
        }
      } else if (resultType.equals("plain")) {
        buffer.append(results.getResultString());
        speechResult.setText(buffer.toString());
        speechResult.setSelection(speechResult.length());
      }

      if (isLast & cyclic) {
        // TODO 最后的结果
        Message message = Message.obtain();
        message.what = 0x001;
        han.sendMessageDelayed(message, 100);
      }
    }

    @Override
    public void onVolumeChanged(int volume, byte[] data) {
      showTip("当前正在说话，音量大小：" + volume);
      Log.d(TAG, "返回音频数据：" + data.length);
    }

    @Override
    public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
      // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
      // 若使用本地能力，会话id为null
      //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
      //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
      //		Log.d(TAG, "session id =" + sid);
      //	}
    }
  };

  //执行音频流识别操作
  private void executeStream() {
    buffer.setLength(0);
    speechResult.setText(null);// 清空显示内容
    mIatResults.clear();
    if (ret != ErrorCode.SUCCESS) {
      showTip("识别失败,错误码：" + ret);
    } else {
      byte[] audioData = readAudioFile(SpeechAct.this, "iattest.wav");

      if (null != audioData) {
        showTip("开始音频流识别");
      } else {
        showTip("读取音频流失败");
      }
    }
  }

  private void printResult(RecognizerResult results) {
    String text = JsonParser.parseIatResult(results.getResultString());

    String sn = null;
    // 读取json结果中的sn字段
    try {
      JSONObject resultJson = new JSONObject(results.getResultString());
      sn = resultJson.optString("sn");
    } catch (JSONException e) {
      e.printStackTrace();
    }

    mIatResults.put(sn, text);

    StringBuffer resultBuffer = new StringBuffer();
    for (String key : mIatResults.keySet()) {
      resultBuffer.append(mIatResults.get(key));
    }

    speechResult.setText(resultBuffer.toString());
    speechResult.setSelection(speechResult.length());
  }


  private void showTip(final String str) {
    toast.setText(str);
    toast.show();
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override
  protected void onResume() {
    // 开放统计 移动数据统计分析
    FlowerCollector.onResume(this);
    FlowerCollector.onPageStart(TAG);
    super.onResume();
  }

  @Override
  protected void onPause() {
    // 开放统计 移动数据统计分析
    FlowerCollector.onPageEnd(TAG);
    FlowerCollector.onPause(this);
    super.onPause();
  }

  /**
   * 读取asset目录下音频文件。
   *
   * @return 二进制文件数据
   */
  public static byte[] readAudioFile(Context context, String filename) {
    try {
      InputStream ins = context.getAssets().open(filename);
      byte[] data = new byte[ins.available()];

      ins.read(data);
      ins.close();

      return data;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return null;
  }
}
