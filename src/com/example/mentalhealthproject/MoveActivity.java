package com.example.mentalhealthproject;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MoveActivity extends Activity {

    private TextView filter5min, filter15min, filter25min;
    private GridView exercisesGridView;
    private MediaPlayer mediaPlayer;
    private ArrayList<ExerciseItem> exerciseItems;
    private ExerciseAdapter adapter;
    private LinearLayout timerLayout;
    private TextView countdownText, timerTitle;
    private Button cancelTimerButton;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);

        // Initializing UI Components
        filter5min = (TextView) findViewById(R.id.filter5min);
        filter15min = (TextView) findViewById(R.id.filter15min);
        filter25min = (TextView) findViewById(R.id.filter25min);
        exercisesGridView = (GridView) findViewById(R.id.exercisesGridView);
        timerLayout = (LinearLayout) findViewById(R.id.timerLayout);
        countdownText = (TextView) findViewById(R.id.countdownText);
        timerTitle = (TextView) findViewById(R.id.timerTitle);
        cancelTimerButton = (Button) findViewById(R.id.cancelTimerButton);

     // Bottom Navigation Click Listeners
        findViewById(R.id.nav_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MoveActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(MoveActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.nav_favorites).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MoveActivity.this, "Favorites Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MoveActivity.this, FavoriteActivity.class);
                 startActivity(intent);
            }
        });

        findViewById(R.id.nav_sounds).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MoveActivity.this, "Sounds Clicked", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(MoveActivity.this, SoundsActivity.class);
              startActivity(intent);
            }
        });

        findViewById(R.id.user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MoveActivity.this, "User Profile Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MoveActivity.this, UserActivity.class);
                 startActivity(intent);
            }
        });

        setupFilters();
        loadExercises();
        playBackgroundMusic();
    }
    private void setupFilters() {
        View.OnClickListener filterClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFilters();
                v.setBackgroundColor(getResources().getColor(R.color.selectedFilter));

                int filterMinutes = 0;
                switch (v.getId()) {
                    case R.id.filter5min:
                        filterMinutes = 5;
                        break;
                    case R.id.filter15min:
                        filterMinutes = 15;
                        break;
                    case R.id.filter25min:
                        filterMinutes = 25;
                        break;
                }

                filterExercisesByDuration(filterMinutes);
            }
        };

        filter5min.setOnClickListener(filterClickListener);
        filter15min.setOnClickListener(filterClickListener);
        filter25min.setOnClickListener(filterClickListener);
    }

    private void filterExercisesByDuration(int minutes) {
        ArrayList<ExerciseItem> filtered = new ArrayList<ExerciseItem>();
        for (ExerciseItem item : exerciseItems) {
            if (item.duration == minutes) {
                filtered.add(item);
            }
        }

        adapter = new ExerciseAdapter(this, filtered);
        exercisesGridView.setAdapter(adapter);
    }


    private void resetFilters() {
        filter5min.setBackgroundColor(getResources().getColor(R.color.defaultFilter));
        filter15min.setBackgroundColor(getResources().getColor(R.color.defaultFilter));
        filter25min.setBackgroundColor(getResources().getColor(R.color.defaultFilter));
    }

    private void loadExercises() {
    	exerciseItems = new ArrayList<ExerciseItem>();
    	exerciseItems.add(new ExerciseItem("Yoga Pilates", R.drawable.yoga, "Improve flexibility & focus", 5));
    	exerciseItems.add(new ExerciseItem("Full Body Stretch", R.drawable.stretch, "Relax muscles and reduce stress", 15));
    	exerciseItems.add(new ExerciseItem("Strength Training", R.drawable.strength, "Boost endurance & tone muscles", 25));
    	exerciseItems.add(new ExerciseItem("Dance Therapy", R.drawable.dance, "Express emotions through movement", 15));
    	exerciseItems.add(new ExerciseItem("Morning Jog", R.drawable.jog, "Start your day with fresh energy", 5));
    	exerciseItems.add(new ExerciseItem("Nature Walk & Meditation", R.drawable.nature, "Reconnect with nature & inner calm", 25));
    	
 


        adapter = new ExerciseAdapter(this, exerciseItems);
        exercisesGridView.setAdapter(adapter);

        exercisesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	ExerciseItem item = exerciseItems.get(position);
            	Toast.makeText(MoveActivity.this, "Starting: " + item.title, Toast.LENGTH_SHORT).show();
            	startExerciseTimer(item.title, item.duration);
            }
        });
    }
    
    private void startExerciseTimer(String title, int minutes) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        timerTitle.setText("Now doing: " + title);
        timerLayout.setVisibility(View.VISIBLE);

        long durationMillis = minutes * 60 * 1000;

        countDownTimer = new CountDownTimer(durationMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                countdownText.setText(String.format("%02d:%02d", minutes, seconds));
            }

            public void onFinish() {
                countdownText.setText("00:00");
                Toast.makeText(MoveActivity.this, "Great job! Session complete ", Toast.LENGTH_LONG).show();
                timerLayout.setVisibility(View.GONE);
            }
        }.start();

        cancelTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                timerLayout.setVisibility(View.GONE);
            }
        });
    }


    private void playBackgroundMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.forest); // Make sure forest.mp3 is in res/raw
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
    
    
    
}
