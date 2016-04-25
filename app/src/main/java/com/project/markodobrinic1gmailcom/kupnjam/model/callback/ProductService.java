package com.project.markodobrinic1gmailcom.kupnjam.model.callback;

import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by marko.dobrinic1@gmail.com on 23.4.2016..
 */
public interface ProductService {

    /** using GET method for data fetching **/
    /** base url : https://kupnjam-abelso.rhcloud.com **/

    @GET("/api/index.php/products?transform=1")
    Call<List<Product>> getAllProducts();
}
