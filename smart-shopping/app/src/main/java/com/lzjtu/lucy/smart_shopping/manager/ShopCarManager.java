package com.lzjtu.lucy.smart_shopping.manager;

import com.lzjtu.lucy.smart_shopping.model.Product;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

public class ShopCarManager {

  private static ShopCarManager instance;

  private ShopCarManager() {
  }

  public static ShopCarManager getInstance() {
    if (instance == null) {
      instance = new ShopCarManager();
    }
    return instance;
  }

  public List<Product> products;

  public List<Product> getProducts() {
    return products;
  }

  public void addProduct(Product product, BehaviorSubject subject) {
    if (products == null) {
      products = new ArrayList<>();
    }
    products.add(product);
    subject.onNext(null);
  }

  public void remove(int id, Action1 a){
    int index  = -1;
    for (int i = 0 ; i<products.size();i++){
      if (id == products.get(i).id){
        index = i;
      }
    }
    if (index != -1){
      products.remove(index);
      a.call(null);
    }
  }

  public boolean isEmpty() {
    return products == null || products.isEmpty();
  }

  public int getCount() {
    if (products != null) {
      return products.size();
    }
    return 0;
  }

  public String getTotalPrice() {
    double price = 0;
    if (products != null) {
      for (Product p : products) {
        price += Double.parseDouble(p.nowPrice);
      }
    }
    return getDiscount(price);
  }

  private static String getDiscount(double amount) {
    DecimalFormat df = new DecimalFormat("#.##");
    return df.format(amount);
  }

  public void clearShopCar(){
    if (products != null && !products.isEmpty()) {
      products.clear();
    }
  }

}
