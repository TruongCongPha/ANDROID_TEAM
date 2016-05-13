package com.a12t4group.soccernetwork.Sliding.Models;

/**
 * Created by User on 23/04/2016.
 */
public class NavItem {
    private String title, subTitle;
    private int resIcon;

    public NavItem(String title, String subTitle, int resIcon) {
        this.title = title;
        this.subTitle = subTitle;
        this.resIcon = resIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }
}
