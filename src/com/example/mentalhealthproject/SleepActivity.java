package com.example.mentalhealthproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SleepActivity extends Activity {

    private TextView currentTimeTextView, quoteTextView;
    private EditText sleepDurationInput;
    private RatingBar sleepQualityRating;
    private Button sleepTipsBtn, logSleepBtn;
    private Switch darkModeSwitch;
    private RelativeLayout rootLayout;
    private MediaPlayer mediaPlayer;

    private GridView soundscapesGridView;
    private String[] soundLabels = {"Rain", "Ocean"};

    private Handler timeHandler = new Handler();

    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
            currentTimeTextView.setText(currentTime);
            timeHandler.postDelayed(this, 60000); // update every minute
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        // Initialize views
        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
        currentTimeTextView = (TextView) findViewById(R.id.currentTimeTextView);
        quoteTextView = (TextView) findViewById(R.id.quoteTextView);
        sleepDurationInput = (EditText) findViewById(R.id.sleepDurationInput);
        sleepQualityRating = (RatingBar) findViewById(R.id.sleepQualityRating);
        sleepTipsBtn = (Button) findViewById(R.id.sleepTipsBtn);
        logSleepBtn = (Button) findViewById(R.id.logSleepBtn);
        darkModeSwitch = (Switch) findViewById(R.id.darkModeSwitch);
        soundscapesGridView = (GridView) findViewById(R.id.soundscapesGridView);

        // Start showing time
        timeHandler.post(updateTimeRunnable);

        // Rating change listener
        sleepQualityRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                displayQuote((int) rating);
            }
        });

        // Sleep Tips Button
        sleepTipsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSleepTips();
            }
        });

        // Log Sleep Button
        logSleepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logSleepData();
            }
        });

        // Dark Mode Switch
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                toggleDarkMode(isChecked);
            }
        });

        // Soundscape adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, soundLabels);
        soundscapesGridView.setAdapter(adapter);

        // Soundscape click listener
        soundscapesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0: // Rain
                        playSound(R.raw.rain);
                        Toast.makeText(SleepActivity.this, "Playing Rain Sounds", Toast.LENGTH_SHORT).show();
                        break;
                    case 1: // Ocean
                        playSound(R.raw.ocean);
                        Toast.makeText(SleepActivity.this, "Playing Ocean Sounds", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    // Quote generator based on rating
    private void displayQuote(int rating) {
        String quote;
        switch (rating) {
            case 1:
                quote = "A restless night? Tomorrow is a new day.";
                break;
            case 2:
                quote = "Not the best rest, but you're trying!";
                break;
            case 3:
                quote = "An average night. Sweet dreams next time!";
                break;
            case 4:
                quote = "Well done! You're almost there.";
                break;
            case 5:
                quote = "Great sleep! Keep it up for a better you.";
                break;
            default:
                quote = "Rate your sleep honestly!";
        }
        quoteTextView.setText(quote);
    }

    // Display sleep tips
    private void showSleepTips() {
        new AlertDialog.Builder(SleepActivity.this)
                .setTitle("Sleep Tips")
                .setMessage("• Avoid screens before bedtime.\n" +
                            "• Stick to a sleep schedule.\n" +
                            "• Create a calm environment.\n" +
                            "• Avoid caffeine before sleep.")
                .setPositiveButton("OK", null)
                .show();
    }

    // Validate and log sleep data
    private void logSleepData() {
        String durationStr = sleepDurationInput.getText().toString().trim();

        if (TextUtils.isEmpty(durationStr)) {
            Toast.makeText(this, "Please enter sleep duration.", Toast.LENGTH_SHORT).show();
            return;
        }

        float duration;
        try {
            duration = Float.parseFloat(durationStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number format.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (duration < 5.0) {
            Toast.makeText(this, "Warning: Sleep duration is less than 5 hours. Not enough rest!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Sleep duration logged: " + duration + " hrs", Toast.LENGTH_SHORT).show();
        }
    }

    // Dark mode simulation
    private void toggleDarkMode(boolean enabled) {
        if (enabled) {
            rootLayout.setBackgroundColor(Color.BLACK);
            Toast.makeText(this, "Dark Mode Enabled", Toast.LENGTH_SHORT).show();
        } else {
            rootLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            Toast.makeText(this, "Dark Mode Disabled", Toast.LENGTH_SHORT).show();
        }
    }

    // Play sound with safe media player handling
    private void playSound(int soundResId) {
        // Stop and release existing player
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.reset();
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mediaPlayer = null;
        }

        // Create and start new sound
        try {
            mediaPlayer = MediaPlayer.create(this, soundResId);
            if (mediaPlayer != null) {
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            } else {
                Toast.makeText(this, "Unable to play sound.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error playing sound: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeHandler.removeCallbacks(updateTimeRunnable);

        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.reset();
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mediaPlayer = null;
        }
    }
}
