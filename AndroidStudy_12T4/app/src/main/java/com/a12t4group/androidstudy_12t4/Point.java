package com.a12t4group.androidstudy_12t4;

/**
 * Created by User on 17/01/2016.
 */
public class Point {
    protected float x,y;
    public Point(float x,float y){
        this.x=x;
        this.y=y;
    }
    public void move(float dx, float dy){
        this.x+=dx;
        this.y+=dy;
    }
    public void setX(float x){
        this.x=x;
    }
    public void setY(float y){
        this.y=y;
    }
    public float getX(){
        return  this.x;
    }
    public float getY(){
        return  this.y;
    }
}
