package com.example.tankwargame2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
//import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

public class MainActivity extends Activity {


    Player tankWar;
    MyCanvas myCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Point size = new Point();
        myCanvas = new MyCanvas(this, size.x, size.y);
        tankWar = new Player(this, size.x, size.y);
        setContentView(myCanvas);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Tell the gameView resume method to execute
        myCanvas.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        myCanvas.pause();
    }
}
