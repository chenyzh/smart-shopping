package com.lzjtu.lucy.smart_shopping.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ReadJsonManager {

  private static final String FILE_PATH = "products.json";

  public static final String PRODUCT_JSON = "[\n"
      + "  {\n"
      + "    \"id\": 0,\n"
      + "    \"productName\": \"fe金典生物牙膏\",\n"
      + "    \"originalPrice\": 14.8,\n"
      + "    \"nowPrice\": 9.9\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 1,\n"
      + "    \"productName\": \"珍爱湿巾80片/包\",\n"
      + "    \"originalPrice\": 15.9,\n"
      + "    \"nowPrice\": 10.9\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 2,\n"
      + "    \"productName\": \"好一典倍柔抽取式面巾纸8连包/提\t\",\n"
      + "    \"originalPrice\": 18.9,\n"
      + "    \"nowPrice\": 13.5\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 3,\n"
      + "    \"productName\": \"雕牌全效加浓洗洁精1.5kg/桶\t\",\n"
      + "    \"originalPrice\": 20.9,\n"
      + "    \"nowPrice\": 11.9\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 4,\n"
      + "    \"productName\": \"立白生姜洗洁精1kg/瓶\t\",\n"
      + "    \"originalPrice\": 17,\n"
      + "    \"nowPrice\": 8.9\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 5,\n"
      + "    \"productName\": \"阿道夫人参系列500kg\",\n"
      + "    \"originalPrice\": 79,\n"
      + "    \"nowPrice\": 39.5\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 6,\n"
      + "    \"productName\": \"溜溜清梅160g/袋\",\n"
      + "    \"originalPrice\": 11.9,\n"
      + "    \"nowPrice\": 9.9\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 7,\n"
      + "    \"productName\": \"Monster能量型维生素饮料330ml/罐\",\n"
      + "    \"originalPrice\": 6.5,\n"
      + "    \"nowPrice\": 5.2\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 8,\n"
      + "    \"productName\": \"特价火龙果/500g\",\n"
      + "    \"originalPrice\": 8.8,\n"
      + "    \"nowPrice\": 6.8\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 9,\n"
      + "    \"productName\": \"醇熟切片面包/袋\",\n"
      + "    \"originalPrice\": 7.8,\n"
      + "    \"nowPrice\": 6.5\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 10,\n"
      + "    \"productName\": \"特价小台芒/500g\",\n"
      + "    \"originalPrice\": 10.9,\n"
      + "    \"nowPrice\": 8.8\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 11,\n"
      + "    \"productName\": \"金河奶啤300ml\",\n"
      + "    \"originalPrice\": 6,\n"
      + "    \"nowPrice\": 3.9\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 12,\n"
      + "    \"productName\": \"康师傅柠檬味冰红茶310ml*6\",\n"
      + "    \"originalPrice\": 12.5,\n"
      + "    \"nowPrice\": 9.9\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 13,\n"
      + "    \"productName\": \"思念南瓜饼200g\",\n"
      + "    \"originalPrice\": 4.1,\n"
      + "    \"nowPrice\": 2.5\n"
      + "  }\n"
      + "]\n";

  public static String readProducts(){
    StringBuffer stringBuffer = new StringBuffer();
    File productFile = new File(FILE_PATH);
    try {
      FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
      InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"UTF-8");
      BufferedReader in = new BufferedReader(inputStreamReader);
      String str;
      while((str = in.readLine())!= null){
        stringBuffer.append(str);
      }
      in.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stringBuffer.toString();
  }

}
