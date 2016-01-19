package com.a12t4group.androidstudy_12t4;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Circle extends Point{
    private float radius;
    private int color;
    private float dx,dy;
    //private Point p;
    /*dx >0 : sang phải
    dx>0: sang trái
    dy>0 đi xuống
    dy<: đi lên*/
    Paint paint;
    public Circle(float x, float y, float radius,float dx, float dy, int color){
        //p = new Point(x,y);
        super(x,y);
        this.radius=radius;
        this.color=color;
        this.dx=dx;
        this.dy=dy;
         paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(this.color);
    }
    public void draw(Canvas canvas){
        canvas.drawCircle(this.x, this.y,this.radius,paint);

    }

    public void move(){
        x+=dx;
        y+=dy;
    }
    public boolean checkTouchEdge(float width, float height){    //width,height: dộ phân giải của máy
        if((x-radius)<0 || (x+radius) > width){
            dx*=-1;
            return true;
        }else if((y-radius)<0 || (y+radius)> height){
            dy*=-1;
            return true;
        }
        return false;
    }
    public boolean checkTouchBox(MoveRectangle moveRectangle){
        if(moveRectangle.isAreaRectangle(this)==true) {
                    dx*=1;  //tiếp tục theo hướng cũ theo chiều X
                    dy*=-1; //dội bóng lên
            return true;
        }
        return false;
    }

    public boolean checkTouchBrick(Brick brick){
        if(brick.isAreaBrick(this)==true) {
            dy*=-1;
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
    public float getX(){return  x;}
    public float getY(){
        return  y;
    }
    public  void setRadius(float radius){
        this.radius=radius;
    }
    public float getRadius(){
        return this.radius;
    }
}
