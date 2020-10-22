package com.example.tankwargame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class Enemy {
    private Context mContext;
    MainActivity mainActivity;


    private Bitmap tankE;


    private float m_x;
    private float m_y;
    private float tankSpeed;
    private float width;
    private float height;
    private int enemyHealth;


    public Enemy(Context context, float x, float y) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screenWidth = size.x;
        int screenHeight = size.y;

        width = screenWidth / 10;
        height = screenHeight / 10;
        tankE = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_tank);
        tankE = Bitmap.createScaledBitmap(tankE, (int) (width), (int) (height), false);

        m_x = x;
        m_y = y;
    }

    public Bitmap getTankE() {
        return tankE;
    }

    public void update(long fps) {

    }

    public float getX() {

        return m_x;
    }

    public float getY() {
        return m_y;
    }

    public float getTWidth() {
        return width;
    }

    public float getTHeight() {
        return height;
    }
}
