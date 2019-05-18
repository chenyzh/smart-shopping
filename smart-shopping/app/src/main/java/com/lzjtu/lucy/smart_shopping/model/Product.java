package com.lzjtu.lucy.smart_shopping.model;

public class Product {

  public int id;
  public String productName;
  public String originalPrice;
  public String nowPrice;
  public String productImage;

  @Override
  public String toString() {
    return "[ id:"+id+" productName:"+productName+" originalPrice:"+originalPrice+" nowPrice:"+nowPrice+" productImage:"+productImage+" ]";
  }
}
