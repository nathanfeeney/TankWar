package com.example.tankwargame2;
import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Paint;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;


public class Bullet {
    private Bitmap bullet;
    double moveY = 0;
    private float length;
    private float height;
    boolean isMoving = false;
    private float m_x;
    private float m_y;
    private int bulletDamage = 20;


    //Methods
    public Bullet(Context context, float x, float y) {


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        length = screenWidth/30;
        height = screenHeight/30;
        bullet = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        bullet = Bitmap.createScaledBitmap(bullet, (int)(length),(int)(height),false);

        m_x = x;
        m_y= y;

    }

    public Bitmap getpBullet() {
        return bullet;
    }
    public void shootUP() {

        moveY = 1;
    }
    public void update(long delta) {
        //moves the bullet along the y axis at the speed set by moveY
        m_y -= moveY * delta;
    }

    public float getX() {


        return m_x;
    }

    public float getY() {
        return m_y;


    }

    }
