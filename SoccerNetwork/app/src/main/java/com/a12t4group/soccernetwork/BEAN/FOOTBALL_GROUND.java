package com.a12t4group.soccernetwork.BEAN;

/**
 * Created by User on 27/04/2016.
 */
public class FOOTBALL_GROUND {
    private int id_fg, max_player,price,id_branch;
    private String fg_name;

    public  FOOTBALL_GROUND(){}

    public FOOTBALL_GROUND(int id_fg, String fg_name, int max_player, int price, int id_branch) {
        this.id_fg = id_fg;
        this.max_player = max_player;
        this.price = price;
        this.id_branch = id_branch;
        this.fg_name = fg_name;
    }

    public FOOTBALL_GROUND(String fg_name , int max_player, int price, int id_branch) {
        this.max_player = max_player;
        this.price = price;
        this.id_branch = id_branch;
        this.fg_name = fg_name;
    }

    public int getId_fg() {
        return id_fg;
    }

    public void setId_fg(int id_fg) {
        this.id_fg = id_fg;
    }

    public int getMax_player() {
        return max_player;
    }

    public void setMax_player(int max_player) {
        this.max_player = max_player;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId_branch() {
        return id_branch;
    }

    public void setId_branch(int id_branch) {
        this.id_branch = id_branch;
    }

    public String getFg_name() {
        return fg_name;
    }

    public void setFg_name(String fg_name) {
        this.fg_name = fg_name;
    }
}
