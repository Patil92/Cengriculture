package com.companies.patil.agri_di;

public class User {

    public String email;
    public String passwd;
    public String id;
    public String phonenum;

    // Default constructor required for calls to
    // DataSnapshot.getValue(com.companies.patil.agri_di.User.class)
    public User() {
    }

    public User(String id,String email, String passwd,String phonenum) {
        this.passwd = passwd;
        this.email = email;
        this.id = id;
        this.phonenum = phonenum;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getid() {
        return id;
    }

    public String getPhonenum() {
        return phonenum;
    }
}