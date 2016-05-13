package com.a12t4group.soccernetwork.BEAN;

import java.util.Date;

/**
 * Created by User on 27/04/2016.
 */
public class MATCHES {
    private int match_id, id_fg, user_id,price;
    private boolean status, is_verified;
    private Date start_time, end_time,created, updated, deleted;

    public MATCHES(){

    }

    public MATCHES(int match_id, int id_fg, int user_id, boolean status, Date start_time, Date end_time,int price, boolean is_verified, Date created, Date updated, Date deleted) {
        this.match_id = match_id;
        this.id_fg = id_fg;
        this.user_id = user_id;
        this.status = status;
        this.is_verified = is_verified;
        this.price = price;
        this.start_time = start_time;
        this.end_time = end_time;
        this.created = created;
        this.updated = updated;
        this.deleted = deleted;
    }

    public MATCHES(int id_fg, int user_id, boolean status, Date start_time, Date end_time,int price, Date created) {
        this.id_fg = id_fg;
        this.user_id = user_id;
        this.status = status;
        this.price = price;
        this.start_time = start_time;
        this.created = created;
        this.end_time = end_time;
        this.created = created;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public int getId_fg() {
        return id_fg;
    }

    public void setId_fg(int id_fg) {
        this.id_fg = id_fg;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean is_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
