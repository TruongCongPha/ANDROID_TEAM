package com.a12t4group.soccernetwork.BEAN;

import java.io.Serializable;

/**
 * Created by User on 11/04/2016.
 */
public class USER implements Serializable {
    private int user_id, district_id;
    private String username, pass, email, phone_number, role;

    public USER(int user_id,String username, String pass, String email,  String phone_number, String role, int district_id) {
        this.user_id = user_id;
        this.district_id = district_id;
        this.pass = pass;
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.role = role;
    }

    public USER(String username, String pass, String email,  String phone_number, String role, int district_id) {
        this.district_id = district_id;
        this.pass = pass;
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.role = role;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
