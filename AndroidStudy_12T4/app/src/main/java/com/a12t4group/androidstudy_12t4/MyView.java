package com.a12t4group.androidstudy_12t4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

public class MyView extends View {
    private int x,y;
    Circle c1;
    MoveRectangle moveRectangle;
    ArrayList<Brick> brickList;
    ArrayList<Brick> collistionBrick;
    static int colorBrick[]={Color.LTGRAY,Color.CYAN ,Color.GRAY,Color.GREEN, Color.YELLOW,Color.MAGENTA};
    public MyView(Context context){
        super(context);
        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        x=display.getWidth();
        y=display.getHeight()-50;
         c1=new Circle(100,100,20,5,5,Color.RED);
         //c2=new Circle(200,200,30,5,5,"BLUE");
         moveRectangle = new MoveRectangle(x/2 -100, y-170,200,20);
        brickList = new ArrayList<>();
            for (int i=0;i<10;i++){
                Random rd = new Random();
                Brick b = new Brick(i*x/10,0,x/10,20,colorBrick[rd.nextInt(6)]);
                brickList.add(b);
            }
        collistionBrick = new ArrayList<>();

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        brickList.removeAll(collistionBrick);
        collistionBrick.clear();
        Paint p = new Paint();
        p.setColor(Color.BLACK); //đặt màn hình màu đen
        canvas.drawPaint(p);
        c1.checkTouchEdge(x,y);
        c1.move();
        c1.draw(canvas);
        moveRectangle.drawRectangle(canvas);
        c1.checkTouchBox(moveRectangle);

        for(Brick brick: brickList){
            brick.drawRectangle(canvas);
            if(c1.checkTouchBrick(brick)){
                collistionBrick.add(brick);
            }
        }


        try {
            Thread.sleep(25);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        moveRectangle.onTouchEvent(event,(float)x);
        return true;
    }
}