package com.example.tankwargame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class Wall {
    private Bitmap wall;
    private float m_x;
    private float m_y;
    private float wallOneWidth;
    private float wallOneHeight;

    public Wall(Context context, float x, float y ){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screenWidth = size.x;
        int screenHeight = size.y;

        wallOneWidth = screenWidth /3;
        wallOneHeight = screenHeight/20;
        wall = BitmapFactory.decodeResource(context.getResources(), R.drawable.wall);
        wall = Bitmap.createScaledBitmap(wall, (int)(wallOneWidth),(int)(wallOneHeight), false);

        m_x = x;
        m_y= y;
    }
    public Bitmap getWall(){
        return wall;
    }
    public float getX(){

        return m_x;
    }
    public float getY(){
        return m_y;
    }
    public float getWWidth(){
        return wallOneWidth;
    }
    public float getWHeight(){
        return wallOneHeight;
    }
}
