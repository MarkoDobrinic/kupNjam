package com.project.markodobrinic1gmailcom.kupnjam.model.pojo;

/**
 * Created by marko.dobrinic1@gmail.com on 6.5.2016..
 */
public class ShoppingItem extends Item {

    private ShoppingProductDetail detail;

    public ShoppingItem(ShoppingProductDetail detail) {
        this.detail = detail;
    }

    public ShoppingProductDetail getDetail() {
        return detail;
    }

    @Override
    public int getType() {
        return SHOP_ITEM_TYPE;
    }
}
