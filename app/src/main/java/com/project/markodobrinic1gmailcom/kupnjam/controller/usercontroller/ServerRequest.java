package com.project.markodobrinic1gmailcom.kupnjam.controller.usercontroller;

import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.User;

/**
 * Created by marko.dobrinic1@gmail.com on 25.4.2016..
 */
public class ServerRequest {

    private String operation;
    private User user;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
