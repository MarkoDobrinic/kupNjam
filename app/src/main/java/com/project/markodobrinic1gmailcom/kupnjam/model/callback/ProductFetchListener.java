package com.project.markodobrinic1gmailcom.kupnjam.model.callback;

import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.Product;

import java.util.List;

/**
 * Created by marko.dobrinic1@gmail.com on 23.4.2016..
 */
public interface ProductFetchListener {

    void onDeliverAllProducts(List<Product> productList);

    void onDeliverProduct(Product product);

    void onHideDialog();
}
