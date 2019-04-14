package com.nirlocorp.snake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nirlocorp.snake.engine.GameEngine;


public class Complete extends AppCompatActivity {

    private Button restart;
    private Button retour;
    private Button score;


    public static TextView nbApples;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        restart = (Button)findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSnake();
            }
        });

        retour = (Button)findViewById(R.id.retour);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTitleScreen();
            }
        });

        score();

//        score = (Button)findViewById(R.id.score);
//        score.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openRecord();
//            }
//        });
    }

    public void openSnake(){
        Intent go = new Intent(this,MainActivity.class);
        startActivity(go);
    }

    public void openTitleScreen(){
        Intent back = new Intent(this,TitleScreen.class);
        startActivity(back);
    }

    public void score(){
        nbApples = (TextView)findViewById(R.id.nbApples);
        nbApples.setText("Score : "+GameEngine.apl);

    }


//    public void openRecord(){
//        Intent score = new Intent(this, Records.class);
//        startActivity(score);
//    }

//    private void startTimer(){
//        CountDownTimer countDownTimer = new CountDownTimer(timeLeftInMillisconds, CONVERT_TO_SECONDS) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                timeLeftInMillisconds = millisUntilFinished;
//                updateTimer();
//            }
//
//            @Override
//            public void onFinish() {
//                endGame = true;
//            }
//        }.start();
//    }
//
//    private void updateTimer(){
//        minutes = (int) (timeLeftInMillisconds / CONVERT_TO_MINUTES);
//        seconds = (int) (timeLeftInMillisconds % CONVERT_TO_MINUTES/ CONVERT_TO_SECONDS);
//        timer = minutes+":";
//        if(seconds<CHANGE_TIMER_DISPLAY) {
//            timer = timer+"0";
//        }
//        timer = timer + seconds;
//    }



}
