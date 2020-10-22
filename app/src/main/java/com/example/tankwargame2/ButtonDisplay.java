package com.example.tankwargame2;

import android.graphics.Bitmap;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class ButtonDisplay {
    private Bitmap arrup;
    private Bitmap arrright;
    private Bitmap arrdown;
    private Bitmap arrleft;
    private Bitmap fire_prj;
    private float m_x;
    private float m_y;
    private float width;
    private float height;
    private float shootH;
    private float shootW;

    // Constructor
    public ButtonDisplay(Context context, float x, float y){

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screenWidth = size.x;
        int screenHeight = size.y;
        m_x = x;
        m_y = y;

        if(screenWidth>screenHeight){
            width = screenHeight /10;
            height = screenHeight /10;

        }
        else{
            width = screenWidth/10;
            height = screenWidth/10;
        }
        shootH= screenHeight/13;
        shootW=screenWidth/4;


        arrup = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_arr_up);
        arrup = Bitmap.createScaledBitmap(arrup, (int)(width),(int)(height), false);
        //
        arrleft = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_arr_left);
        arrleft = Bitmap.createScaledBitmap(arrleft, (int)(width),(int)(height), false);
        //
        arrright = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_arr_right);
        arrright = Bitmap.createScaledBitmap(arrright, (int)(width),(int)(height), false);
        //
        arrdown = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_arr_down);
        arrdown = Bitmap.createScaledBitmap( arrdown, (int)(width),(int)(height), false);
        //
        fire_prj = BitmapFactory.decodeResource(context.getResources(), R.drawable.shoot);
        fire_prj = Bitmap.createScaledBitmap(fire_prj, (int)(shootW),(int)(shootH), false);

    }
    public Bitmap getArrD(){
        return arrdown;
    }
    public Bitmap getArrR(){
        return arrright;
    }
    public Bitmap getArrL(){
        return arrleft;
    }
    public Bitmap getArrU(){
        return arrup;
    }
    public Bitmap getFire_prj(){
       return fire_prj;
    }
    public float getX(){
        return m_x;
    }
    public float getY(){
        return m_y;
    }
    public float getBpWidth(){
        return width;
    }
    public float getBpHeight(){
        return height;
    }
    public float getShootH(){ return shootH;}
    public float getShootW(){ return shootW;}

}
