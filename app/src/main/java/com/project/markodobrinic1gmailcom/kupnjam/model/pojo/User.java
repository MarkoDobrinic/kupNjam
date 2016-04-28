package com.project.markodobrinic1gmailcom.kupnjam.model.pojo;

/**
 * Created by marko.dobrinic1@gmail.com on 25.4.2016..
 */
public class User {

    private String name;
    private String email;
    private String unique_id;
    private String password;


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUnique_id() {
        return unique_id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
