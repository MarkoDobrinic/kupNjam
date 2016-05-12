package com.project.markodobrinic1gmailcom.kupnjam.model.helper;

import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.ShoppingProductDetail;

import java.util.Comparator;

/**
 * Created by marko.dobrinic1@gmail.com on 6.5.2016..
 */
public class StoreComparator implements Comparator<ShoppingProductDetail> {
    @Override
    public int compare(ShoppingProductDetail lhs, ShoppingProductDetail rhs) {
        if(lhs.getStoreId() == rhs.getStoreId())
            return 0;
        else if(lhs.getStoreId() < rhs.getStoreId())
            return -1;
        else
            return 1;
    }
}
