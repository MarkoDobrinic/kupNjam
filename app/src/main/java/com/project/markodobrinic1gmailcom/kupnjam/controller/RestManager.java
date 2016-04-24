package com.project.markodobrinic1gmailcom.kupnjam.controller;

import com.project.markodobrinic1gmailcom.kupnjam.model.callback.ProductService;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marko.dobrinic1@gmail.com on 23.4.2016..
 */
public class RestManager {

    private ProductService mProductService;

    public ProductService getFlowerService() {
        if (mProductService == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mProductService = retrofit.create(ProductService.class);
        }
        return mProductService;
    }


}
