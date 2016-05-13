package com.a12t4group.soccernetwork.BEAN;

/**
 * Created by User on 24/04/2016.
 */
public class CITIES {
    private int city_id;
    private String city;

    public CITIES(int city_id, String city) {
        this.city_id = city_id;
        this.city = city;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
