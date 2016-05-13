package com.a12t4group.soccernetwork.BEAN;

/**
 * Created by User on 27/04/2016.
 */
public class BRANCH {
    private int id_branch, district_id;
    private String name_branch,address,number_phone;
    private float latitude, longitude;  // vĩ đỗ, kinh độ

    public  BRANCH(){}

    public BRANCH(int id_branch, String name_branch, String number_phone,String address,float latitude, float longitude, int district_id) {
        this.id_branch = id_branch;
        this.district_id = district_id;
        this.name_branch = name_branch;
        this.number_phone = number_phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address =address;
    }

    public BRANCH( String name_branch , String address, String number_phone,float latitude, float longitude, int district_id) {
        this.district_id = district_id;
        this.name_branch = name_branch;
        this.number_phone =number_phone;
        this.address =address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId_branch() {
        return id_branch;
    }

    public void setId_branch(int id_branch) {
        this.id_branch = id_branch;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public String getNumber_phone() {
        return number_phone;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setNumber_phone(String number_phone) {
        this.number_phone = number_phone;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public String getName_branch() {
        return name_branch;
    }

    public void setName_branch(String name_branch) {
        this.name_branch = name_branch;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
