package com.example.mentalhealthproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BreatheActivity extends Activity {

    private TextView exerciseName, timerText, instructionText;
    private Button startButton, stopButton;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private int timeLeft = 60000; // Default 1-minute breathing exercise
    private ImageView breathingCircle;
    private Animation circleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathe);

        exerciseName = (TextView) findViewById(R.id.exerciseName);
        timerText = (TextView) findViewById(R.id.timerText);
        instructionText = (TextView) findViewById(R.id.instructionText);
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        breathingCircle = (ImageView) findViewById(R.id.breathingCircle);

        // Load animation
        circleAnimation = AnimationUtils.loadAnimation(this, R.anim.circle_animation);

        // Start Breathing Exercise
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBreathingExercise();
            }
        });

        // Stop Breathing Exercise
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopBreathingExercise();
            }
        });
    }

    private void startBreathingExercise() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        exerciseName.setText("Deep Breathing Exercise");
        instructionText.setText("Inhale... Hold... Exhale...");
        
        // Start animation
        breathingCircle.startAnimation(circleAnimation);

        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = (int) millisUntilFinished;
                timerText.setText("Time left: " + (timeLeft / 1000) + " sec");
            }

            @Override
            public void onFinish() {
                isRunning = false;
                timerText.setText("Exercise Complete!");
                Toast.makeText(BreatheActivity.this, "Great job! You completed the breathing exercise.", Toast.LENGTH_SHORT).show();
                
                // Stop animation when exercise is complete
                breathingCircle.clearAnimation();
            }
        }.start();
    }

    private void stopBreathingExercise() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            isRunning = false;
            timerText.setText("Exercise Stopped");
            Toast.makeText(this, "Breathing exercise stopped", Toast.LENGTH_SHORT).show();

            // Stop animation when exercise is stopped
            breathingCircle.clearAnimation();
        }
    }
}
