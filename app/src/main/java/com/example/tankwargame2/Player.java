package com.example.tankwargame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class Player {
    //data declarations
    private Bitmap tankU;
    private float m_x;
    private float m_y;
    private float width;
    private float height;



    public Player(Context context, float x, float y){

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screenWidth = size.x;
        int screenHeight = size.y;

        width = screenWidth /10;
        height = screenHeight/10;

        m_x = x;
        m_y= y;

        tankU = BitmapFactory.decodeResource(context.getResources(), R.drawable.tank);
        tankU = Bitmap.createScaledBitmap(tankU, (int)(width),(int)(height), false);
    }
    public Bitmap getTankU(){
        return tankU;
    }
    public float getX(){

        return m_x;
    }
    public float getY(){
        return m_y;
    }
    public void update(long fps){
    }
    public float getTWidth(){
        return width;
    }
    public float getTHeight(){
        return height;
    }
    public void moveUp(){
        m_y -= 10;
    }
    public void moveDown(){
        m_y += 10;
    }
    public void moveLeft(){
        m_x -= 10;
    }
    public void moveRight(){
        m_x += 10;
    }
}
