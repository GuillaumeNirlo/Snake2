package com.nirlocorp.snake;

import android.app.PendingIntent;
import android.content.Intent;
import android.drm.DrmStore;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;


import android.view.View;
import android.view.WindowManager;
import android.view.Window;
import android.app.Activity;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.Toast;

import com.nirlocorp.snake.engine.GameEngine;
import com.nirlocorp.snake.enums.Direction;
import com.nirlocorp.snake.enums.GameState;
import com.nirlocorp.snake.views.SnakeView;



public class MainActivity extends AppCompatActivity /*implements View.OnTouchListener*/{

    private GameEngine gameEngine;
    private SnakeView snakeView;

    private final Handler handler = new Handler();

    private final long updateDelay = 125;

    private float prevX, prevY;

    //private Direction currentDirection = Direction.Est;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Set fullscreen
       this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        // Set No Title
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);


        gameEngine = new GameEngine();
        gameEngine.initGame();

        snakeView = (SnakeView)findViewById(R.id.snakeView);


        StartUpdateHandler();
    }

    private void StartUpdateHandler(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameEngine.update();

                if (gameEngine.getCurrentGameState() == GameState.Running){
                    handler.postDelayed(this, updateDelay);

                }

                if (gameEngine.getCurrentGameState() == GameState.Lost){
                    GameLost();

                }

                snakeView.setSnakeViewMap(gameEngine.getMap());
                snakeView.invalidate();
            }
        }, updateDelay);
    }

    private void GameLost(){

        Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
        openDieScreen();
    }

    public void openDieScreen(){
        Intent finish = new Intent(this,Complete.class);
        startActivity(finish);
    }

//    public boolean onTouch(View v, MotionEvent event) {
//
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                prevX=event.getX();
//                prevY=event.getY();
//                break;
//            case MotionEvent.ACTION_UP:
//                float newX = event.getX();
//                float newY = event.getY();
//
//                if (Math.abs(newX - prevX) > Math.abs(newY - prevY)){
//                    if (newX > prevX){
//                        gameEngine.updateDirection(Direction.Est);
//                    }else {
//                        gameEngine.updateDirection(Direction.Ouest);
//                    }
//                }else {
//                    if (newY > prevY){
//                        gameEngine.updateDirection(Direction.Nord);
//                    }else {
//                        gameEngine.updateDirection(Direction.Sud);
//                    }
//                }
//
//                break;
//        }
//
//        return true;
//
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                switch (currentDirection) {
//                    case Nord:
//                        gameEngine.updateDirection(Direction.Est);
//                        break;
//                    case Est:
//                        gameEngine.updateDirection(Direction.Sud);
//                        break;
//                    case Sud:
//                        gameEngine.updateDirection(Direction.Ouest);
//                        break;
//                    case Ouest:
//                        gameEngine.updateDirection(Direction.Nord);
//                        break;
//                }
//        }
//
//        return true;
//    }
//    @Override
//    public boolean onTouchEvent(MotionEvent motionEvent) {
//
//        switch (motionEvent.getAction() /*& MotionEvent.ACTION_MASK*/) {
//            case MotionEvent.ACTION_UP:
//                if (motionEvent.getX() >= GameEngine.GameWidth / 2) {
//                    switch(currentDirection){
//                        case Nord:
//                            gameEngine.updateDirection(Direction.Est);
//                            break;
//                        case Est:
//                            gameEngine.updateDirection(Direction.Sud);
//                            break;
//                        case Sud:
//                            gameEngine.updateDirection(Direction.Ouest);
//                            break;
//                        case Ouest:
//                            gameEngine.updateDirection(Direction.Nord);
//                            break;
//                    }
//                } else {
//                    switch(currentDirection){
//                        case Nord:
//                            gameEngine.updateDirection(Direction.Ouest);
//                            break;
//                        case Ouest:
//                            gameEngine.updateDirection(Direction.Sud);
//                            break;
//                        case Sud:
//                            gameEngine.updateDirection(Direction.Est);
//                            break;
//                        case Est:
//                            gameEngine.updateDirection(Direction.Nord);
//                            break;
//                    }
//                }
//        }
//        return true;
//    }

    //Rotation horaire a droite et antihoraire a gauche
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            if (motionEvent.getX()%gameEngine.GameWidth>GameEngine.GameWidth / 2) {
                switch(gameEngine.getCurrentDirection()){
                    case Nord:
                        gameEngine.updateDirection(Direction.Est);
                        break;
                    case Est:
                        gameEngine.updateDirection(Direction.Sud);
                        break;
                    case Sud:
                        gameEngine.updateDirection(Direction.Ouest);
                        break;
                    case Ouest:
                        gameEngine.updateDirection(Direction.Nord);
                        break;
                }
            } else {
                switch(gameEngine.getCurrentDirection()){
                    case Nord:
                        gameEngine.updateDirection(Direction.Ouest);
                        break;
                    case Ouest:
                        gameEngine.updateDirection(Direction.Sud);
                        break;
                    case Sud:
                        gameEngine.updateDirection(Direction.Est);
                        break;
                    case Est:
                        gameEngine.updateDirection(Direction.Nord);
                        break;
                }
            }
        }

        return true;
    }

}
