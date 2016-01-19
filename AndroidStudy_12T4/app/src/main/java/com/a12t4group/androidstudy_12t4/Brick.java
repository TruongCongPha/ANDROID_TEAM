package com.a12t4group.androidstudy_12t4;

public class Brick extends Rectangle {
    int color;
    public Brick(float x, float y, float width, float height, int color){
        super(x,y,width,height,color);
    }


    public boolean isAreaBrick(Circle c){
        if((c.x>= x) && (c.x<= (x+width)) && ((c.y- c.getRadius())<=(y+height))){
            return true;
        }
        return false;
    }
}
