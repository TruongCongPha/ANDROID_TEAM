package com.a12t4group.soccernetwork.BEAN;

/**
 * Created by User on 24/04/2016.
 */
public class DISTRICTS {
    private int district_id, city_id;
    private String district_name;

    public DISTRICTS(int district_id, String district_name, int city_id) {
        this.district_id = district_id;
        this.city_id = city_id;
        this.district_name = district_name;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }
}
