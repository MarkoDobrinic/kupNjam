package com.project.markodobrinic1gmailcom.kupnjam.model.pojo;

import java.io.Serializable;

/**
 * Created by marko.dobrinic1@gmail.com on 6.5.2016..
 */
public class ShoppingProductDetail implements Serializable {

    private String productName;
    private double price;
    private int storeId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public static class Location {

        private double latitude, longitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

}
