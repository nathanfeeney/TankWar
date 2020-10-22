package com.example.tankwargame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.SurfaceHolder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.WindowManager;

public class MyCanvas extends SurfaceView implements Runnable {
    Context context;
    // Game view variables \\
    //This is the thread
    Thread gameThread = null;
    SurfaceHolder myHolder;

    // A boolean which we will set and unset
    // when the game is running- or not.
    volatile boolean playing;
    // A Canvas and a Paint object
    Canvas canvas;
    Paint paint;
    // This variable tracks the game frame rate
    long fps;
    // This is used to help calculate the fps
    private long delta;
    private int screenX;
    private int screenY;

    // Tank starts off not moving
    boolean isMoving = false;


    private Bitmap bitmap;
    // Scree offsets //
    int offsetY = 200;
    int offsetX = 300;
    int enemyOffsetY = 100;
    int enemyOffsetX = 230;

    //DIRECTIONAL BUTTONS///////////////
    //up button static position
    int bitmapUpX =70;
    int bitmapUpY= 740;

    //right button static position
    int bitmapRightX = 130;
    int bitmapRightY= 800;

    //down button static position
    float bitmapDownX = 70;
    float bitmapDownY = 860;

    //left button static position
    int bitmapLeftX = 10;
    int bitmapLeftY = 800;

    ButtonDisplay ic_arr_down;
    ButtonDisplay ic_arr_right;
    ButtonDisplay ic_arr_up;
    ButtonDisplay ic_arr_left;
    //END OF DIRECTIONAL///////////

    //WALLS
    private Wall wall;
    int bitmapWallX = 0;
    int bitmapWallY = 500;

    /// Player Tank \\\
    private Player tankU;
    //// Enemy Decelerations///
    private  Enemy tankE;
    /// Fire Projectile \\\
    ButtonDisplay shoot;
    private Bullet bullet;
    private  Bullet pBullet;

    private int roundCount=1;

    /// Fire Projectile ///////////
    int bitmapFireX = 420;
    int bitmapFireY = 820;

    float width;
    boolean outofbounds = false;

    //Tank Healths//
    int tankHealth = 100;
    int newTankHealth = tankHealth;
    int bulletDamage = 10;

    //reload timer//
    double reloadTimer;
    // This is THE Constructor
    public MyCanvas(Context context, int x, int y) {
        super(context);
        this.context = context;
        screenX = x;
        screenY = y;

        //Screen Dimensions Calc \\\\\
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        Log.e("width", "" +screenWidth);
        Log.e("height", "" +screenHeight);

        float tankXPosition = screenWidth - offsetX;
        float tankYPosition = screenHeight - offsetY;

        float enemyXPosition =  enemyOffsetX;
        float enemyYPosition =  enemyOffsetY;


        myHolder = getHolder();
        paint = new Paint();

        ic_arr_down = new ButtonDisplay(context, bitmapDownX, bitmapDownY);
        ic_arr_right = new ButtonDisplay(context, bitmapRightX, bitmapRightY);
        ic_arr_up = new ButtonDisplay(context, bitmapUpX, bitmapUpY);
        ic_arr_left = new ButtonDisplay(context, bitmapLeftX, bitmapLeftY);

        tankU = new Player(context, tankXPosition, tankYPosition);
        tankE = new Enemy(context, enemyXPosition, enemyYPosition);

        shoot = new ButtonDisplay(context,bitmapFireX,bitmapFireY);

        wall = new Wall(context,bitmapWallX,bitmapWallY);
    }
    public boolean detectIntersect(float x, float y, float bitmapX, float bitmapY, float bitmapWidth, float bitmapHeight){
        if (x >= bitmapX && x < (bitmapX + bitmapWidth)
                && y >= bitmapY && y < (bitmapY + bitmapHeight)) {
            return true;
        }
        return false;
    }

    public void run() {
        while (playing) {
            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // Update the frame
            update();

            // Draw the frame
            draw();

            // Calculate the fps this frame
            delta = System.currentTimeMillis() - startFrameTime;
            if (delta > 0) {
                fps = 1000 / delta;
            }
        }

    }
    public void checkHealth(){
        if( tankHealth == 0){
            roundCount++;
            newTankHealth = 100;
            tankHealth = newTankHealth;
        }
    }
   // checkCollisions
    public void checkCollision(){
        ///// Bullet out of bounds \\\\\
        if (bullet != null) {
            // Check if bullet leaves the screen \\
            if (bullet.getY() < 0 ) {
                bullet = null;
                Log.e("Bullet: ", "" + "set to null");
            }else if(detectIntersect(bullet.getX(),bullet.getY(),tankE.getX(),tankE.getY(),tankE.getTWidth(),tankE.getTHeight())){
                Log.e("Tank", "" + " HIT");
                bullet = null;
                 //tankE.setHealth(tankE.getHealth() - bulletDamage);
                // tankHealth = tankE.setHealth(tankE.getHealth());
                newTankHealth = tankHealth - bulletDamage;
                tankHealth =newTankHealth;

            //checks if bullet hits wall hits a
            }else if(detectIntersect(bullet.getX(),bullet.getY(),wall.getX(),wall.getY(),wall.getWWidth(),wall.getWHeight())){
                bullet = null;
                Log.e("Tank", "" + "Mind that wall");

            }

        }
    }
    public void update() {
        tankU.update(delta);
        tankE.update(delta);

        if (bullet != null) {
            bullet.update(delta);
        }
        checkCollision();
        checkHealth();
    }

    public void draw() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

        if (myHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            // Make the drawing surface our canvas object
            canvas = myHolder.lockCanvas();
            // clears the canvas
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            // Draw the background color
            canvas.drawColor(Color.argb(255,60, 135, 74));
            // Covers Enemies half of the screen
            paint.setColor(Color.argb(255, 38, 90, 48));
            canvas.drawRect(0,0,screenWidth,screenHeight/2, paint);
            // Display the score on the screen
            // Choose the brush color for drawing
            paint.setColor(Color.argb(255, 255, 255, 255));
            //enemy health text position \\
            paint.setTextSize(25);
            canvas.drawText("Boss Health: " + newTankHealth, 20,40, paint);
             //Round text position \\
            paint.setTextSize(30);
            canvas.drawText("Round: " + roundCount, screenWidth/2,screenHeight/2 - 10, paint);
            // Player Tank drawn to screen ////
            canvas.drawBitmap(tankU.getTankU(), tankU.getX(), tankU.getY(), paint);
            // Enemy Tank drawn to screen ////
            canvas.drawBitmap(tankE.getTankE(), tankE.getX(), tankE.getY(), paint);
            //WALLS
            canvas.drawBitmap(wall.getWall(),wall.getX(),wall.getY(), paint);
            //Buttons
            canvas.drawBitmap(ic_arr_down.getArrD(), ic_arr_down.getX(), ic_arr_down.getY(), paint);
            canvas.drawBitmap(ic_arr_right.getArrR(), ic_arr_right.getX(), ic_arr_right.getY(), paint);
            canvas.drawBitmap(ic_arr_left.getArrL(), ic_arr_left.getX(), ic_arr_left.getY(), paint);
            canvas.drawBitmap(ic_arr_up.getArrU(), ic_arr_up.getX(), ic_arr_up.getY(), paint);
            canvas.drawBitmap(shoot.getFire_prj(), shoot.getX(), shoot.getY(), paint);
            //Draw bullet if it is active
            if(bullet != null){
                canvas.drawBitmap(bullet.getpBullet(), bullet.getX(), bullet.getY(),paint);
            }
            // Draw everything to the screen
            // and unlock the drawing surface
            myHolder.unlockCanvasAndPost(canvas);
        }
    }
    // If the game Activity is paused/stopped
    // shutdown our thread.
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    // If SimpleGameEngine Activity is started then
    // start our thread.
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                float touchX = motionEvent.getX();
                float touchY = motionEvent.getY();
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int screenHeight = size.y;
                int screenWidth = size.x;


                if (detectIntersect(touchX, touchY, ic_arr_up.getX(), ic_arr_up.getY(), ic_arr_up.getBpWidth(), ic_arr_up.getBpHeight())) {
                    if (tankU.getY() > screenHeight / 2  && tankU.getX()>wall.getX()+ wall.getWWidth() || tankU.getY() > wall.getY() + wall.getWHeight()) {
                        tankU.moveUp();
                    }
                }
                if (detectIntersect(touchX, touchY, ic_arr_right.getX(), ic_arr_right.getY(), ic_arr_right.getBpWidth(), ic_arr_right.getBpHeight())) {
                    if (tankU.getX() < screenWidth - tankU.getTWidth()) {
                        tankU.moveRight();
                    }
                }
                if (detectIntersect(touchX, touchY, ic_arr_down.getX(), ic_arr_down.getY(), ic_arr_down.getBpWidth(), ic_arr_down.getBpHeight())) {
                    if (tankU.getY() < screenHeight - tankU.getTHeight()) {
                        tankU.moveDown();
                    }
                }
                if (detectIntersect(touchX, touchY, ic_arr_left.getX(), ic_arr_left.getY(), ic_arr_left.getBpWidth(), ic_arr_left.getBpHeight())) {

                    if(tankU.getX() > 0  && tankU.getX()>wall.getX()+ wall.getWWidth() || tankU.getY() > wall.getY() + wall.getWHeight()) {
                        Log.e("dont","Move");
                         tankU.moveLeft();
                    }

                    else if(detectIntersect(tankU.getX(), tankU.getY(), wall.getX(), wall.getY(), wall.getWWidth(), wall.getWHeight())) {
                        Log.e("Tank", "" + "Mind that wall");
                    }
                }
                if (detectIntersect(touchX, touchY, shoot.getX(), shoot.getY(), shoot.getShootW(), shoot.getShootH()))
                {
                    float bulletSpawnX = tankU.getX();
                    float bulletSpawnY = tankU.getY() ;

                    if (bullet != null){
                        Log.e("SHOOTup", "Bullet  already In motion");
                    }else{
                        bullet = new Bullet(context, bulletSpawnX, bulletSpawnY);
                        bullet.shootUP();
                    }
                }
                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:


                break;
        }
        return true;
    }



}

