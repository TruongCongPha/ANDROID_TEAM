package com.a12t4group.androidstudy_12t4;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Rectangle {
    protected float x,y,width,height;
    private int color;
    private Paint paint1,paint2;
    public Rectangle(float x, float y, float width, float height, int color){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
         paint1 = new Paint();
        paint2=new Paint();
        paint1.setStyle(Paint.Style.FILL_AND_STROKE);
        paint1.setColor(color);
        paint2.setColor(Color.RED);
    }
    public void drawRectangle(Canvas canvas){

        canvas.drawRect(x,y, x+width,y+height,paint1);
        canvas.drawLine(x,y,x+width,y,paint2); //vẽ border_top
        canvas.drawLine(x+width,y,x+width,y+height,paint2); //vẽ border_right
        canvas.drawLine(x,y+height,x+width,y+height,paint2); //vẽ border_bottom
        canvas.drawLine(x,y,x,y+height,paint2); //vẽ border_left
    }

    public boolean isAreaRectangle(Circle c){
        if((c.x>= x) && (c.x <= (x+width)) && ((c.y+ c.getRadius())>=y) && ((c.y+ c.getRadius())<=(y+height))){
            return true;
        }
        return false;
    }

    public void setX(float x){
        this.x=x;
    }
    public void setY(float y){
        this.y=y;
    }
    public void setWidth(float width){
        this.width=width;
    }
    public void setHeight(float height){this.height=height;}
    public void setColor(int color){
        this.color=color;
    }
    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }
    public float getWidth(){
        return this.width;
    }
    public float getHeight(){
        return this.height;
    }
    public int getColor(){
        return this.color;
    }


}
