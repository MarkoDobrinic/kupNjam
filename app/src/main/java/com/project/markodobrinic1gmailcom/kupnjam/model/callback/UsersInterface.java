package com.project.markodobrinic1gmailcom.kupnjam.model.callback;

import com.project.markodobrinic1gmailcom.kupnjam.controller.usercontroller.ServerRequest;
import com.project.markodobrinic1gmailcom.kupnjam.controller.usercontroller.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by marko.dobrinic1@gmail.com on 25.4.2016..
 */
public interface UsersInterface {

    @POST("userapp/")
    Call<ServerResponse> operation(@Body ServerRequest request);
}
