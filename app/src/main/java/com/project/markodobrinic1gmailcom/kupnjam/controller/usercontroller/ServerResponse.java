package com.project.markodobrinic1gmailcom.kupnjam.controller.usercontroller;

import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.User;

/**
 * Created by marko.dobrinic1@gmail.com on 25.4.2016..
 */
public class ServerResponse {

    private String result;
    private String message;
    private User user;

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

}
