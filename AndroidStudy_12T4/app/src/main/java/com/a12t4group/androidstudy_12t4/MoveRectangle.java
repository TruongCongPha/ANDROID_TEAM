package com.a12t4group.androidstudy_12t4;

import android.graphics.Color;
import android.view.MotionEvent;

public class MoveRectangle extends Rectangle {
    private float prevX;
    private float deltaX;

    public MoveRectangle(float x, float y, float width, float height){
        super(x,y,width,height, Color.YELLOW);
        prevX=x;
    }

    public void onTouchEvent(MotionEvent event,float WindowPixels) {
        float currentX = event.getX();
        switch(event.getAction()){
            case MotionEvent.ACTION_MOVE:

                deltaX = currentX - prevX;
                x+=deltaX;
                if(x<0){
                   x=0;
                }
                else if ((x+width)>WindowPixels){
                    //setX(WindowPixels-getWidth());
                    x=WindowPixels-width;
                }
                break;
        }
        prevX = currentX;
    }
}
