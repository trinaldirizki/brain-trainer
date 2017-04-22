package com.permana.braintrainer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity {

    Button startGame, restartGame;
    TextView question,textScore,textTime,textCorrect, gameResult;
    ArrayList<Integer> answerList = new ArrayList<Integer>();
    int locationOfCorrectAnswer, scores = 0, questionNum = 0;
    Button answer1, answer2,answer3,answer4;
    LinearLayout gameOverLayout,startPage;
    RelativeLayout mainPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign start game button
        startGame = (Button) findViewById(R.id.buttonPlay);
        question = (TextView) findViewById(R.id.question);
        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);
        textScore = (TextView) findViewById(R.id.textScore);
        textCorrect = (TextView) findViewById(R.id.textCorrect);
        textTime = (TextView) findViewById(R.id.timeLeft);
        gameOverLayout = (LinearLayout) findViewById(R.id.gameOverLayout);
        gameResult = (TextView) findViewById(R.id.gameResult);
        restartGame = (Button) findViewById(R.id.buttonPlayAgain);
        startPage = (LinearLayout) findViewById(R.id.startPage);
        mainPage = (RelativeLayout) findViewById(R.id.mainPage);

    }

    // start game
    public void startGame(View view){
        startPage.setVisibility(View.INVISIBLE);
        mainPage.setVisibility(View.VISIBLE);
        restartGame(restartGame);
    }

    // choose answer
    public void chooseAnswer(View view){
        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            scores++;
            textCorrect.setTextColor(Color.rgb(76,175,80));
            textCorrect.setText("Correct!");
        } else {
            textCorrect.setTextColor(Color.rgb(244,67,54));
            textCorrect.setText("Incorrect");
        }
        questionNum++;
        textScore.setText(Integer.toString(scores) + "/" + Integer.toString(questionNum));
        generateQuestions();
    }

    public void generateQuestions(){
        // reset answerList
        answerList.clear();


        // assign random number to question
        Random rand = new Random();
        int a = rand.nextInt(51);
        int b = rand.nextInt(51);

        question.setText(Integer.toString(a) + " + " + Integer.toString(b));

        // assign correct and incorrect answers to answerList
        locationOfCorrectAnswer = rand.nextInt(4);
        int incorrectAnswer;
        for (int i=0; i<4;i++){
            if (i == locationOfCorrectAnswer){
                answerList.add(a+b);
            } else{
                incorrectAnswer = rand.nextInt(101);
                while (incorrectAnswer == a+b){
                    incorrectAnswer = rand.nextInt(101);
                }
                answerList.add(incorrectAnswer);
            }
        }

        // assign answers to buttons
        answer1.setText(Integer.toString(answerList.get(0)));
        answer2.setText(Integer.toString(answerList.get(1)));
        answer3.setText(Integer.toString(answerList.get(2)));
        answer4.setText(Integer.toString(answerList.get(3)));
    }

    public void startCountdown(){
        new CountDownTimer(30100,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                textTime.setText(Long.toString(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                textTime.setText("0s");
                gameResult.setText(Integer.toString(scores) + " out of " + Integer.toString(questionNum) + " questions");
                enableAnswers(false);
                gameOverLayout.setVisibility(View.VISIBLE);
                restartGame.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void enableAnswers(boolean state){
        answer1.setClickable(state);
        answer2.setClickable(state);
        answer3.setClickable(state);
        answer4.setClickable(state);
    }

    public void restartGame(View view){
        scores = 0;
        questionNum = 0;
        textCorrect.setText("");
        textScore.setText("0/0");
        textTime.setText("30s");
        gameOverLayout.setVisibility(View.INVISIBLE);
        restartGame.setVisibility(View.INVISIBLE);
        enableAnswers(true);
        generateQuestions();
        startCountdown();
    }

}
