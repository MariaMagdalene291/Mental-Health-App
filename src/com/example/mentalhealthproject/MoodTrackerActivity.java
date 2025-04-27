package com.example.mentalhealthproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


import java.util.ArrayList;

public class MoodTrackerActivity extends Activity {

    private TextView questionText, moodScoreText, moodQuote;
    private Button yesButton, noButton, saveMoodButton;
    private ListView moodHistoryList;

    private String[] positiveQuestions = {
            "I feel energetic today.",
            "I had a good sleep.",
            "I feel motivated.",
            "I ate healthy today.",
            "I feel socially connected."
    };

    private String[] negativeQuestions = {
            "I feel anxious today.",
            "I feel sad without reason.",
            "I am easily irritated.",
            "I feel tired even after rest.",
            "I have trouble focusing."
    };

    private ArrayList<String> moodHistory = new ArrayList<String>();
    private ArrayAdapter<String> historyAdapter;

    private int currentQuestionIndex = 0;
    private int score = 0;

    private ArrayList<Boolean> responses = new ArrayList<Boolean>();
    private int totalQuestions = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moodtracker); // Make sure this matches your XML file name
        
        // Initialize Views
        questionText = (TextView) findViewById(R.id.questionText);
        moodScoreText = (TextView) findViewById(R.id.moodScoreText);
        moodQuote = (TextView) findViewById(R.id.moodQuote);
        saveMoodButton = (Button) findViewById(R.id.saveMoodButton);
        moodHistoryList = (ListView) findViewById(R.id.moodHistoryList);
        yesButton = (Button)findViewById(R.id.yesButton); //
        noButton = (Button)findViewById(R.id.noButton);
        
        historyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, moodHistory);
        moodHistoryList.setAdapter(historyAdapter);
        
        populateMoodGrid();


        showNextQuestion();
        
        

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnswer(true);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnswer(false);
            }
        });

        saveMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moodHistory.add("Mood Score: " + score + "%");
                historyAdapter.notifyDataSetChanged();
                Toast.makeText(MoodTrackerActivity.this, "Mood Saved", Toast.LENGTH_SHORT).show();
                resetMoodTracker();
            }
        });
    }
    

    private void populateMoodGrid() {
        GridLayout moodGrid = (GridLayout) findViewById(R.id.moodGridLayout);
        final TextView selectedMoodText = (TextView) findViewById(R.id.selectedMoodText);
        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout); // Your main parent layout

        String[] moodLabels = {"Happy", "Sad", "Angry", "Relaxed", "Anxious", "Excited"};
        int[] moodIcons = {
                R.drawable.happy, R.drawable.sad, R.drawable.angry,
                R.drawable.relaxed, R.drawable.anxious, R.drawable.excited
        };

        final int[] moodColors = {
                0xFFFFFF99, // Yellow for Happy
                0xFF90CAF9, // Light Blue for Sad
                0xFFFF8A65, // Orange for Angry
                0xFFB2DFDB, // Teal for Relaxed
                0xFFE1BEE7, // Purple for Anxious
                0xFFA5D6A7  // Light Green for Excited
        };

        final LinearLayout[] lastSelected = {null};

        for (int i = 0; i < moodLabels.length; i++) {
            final int index = i;

            final LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.VERTICAL);
            itemLayout.setPadding(16, 16, 16, 16);
            itemLayout.setGravity(android.view.Gravity.CENTER);
            itemLayout.setBackgroundResource(R.drawable.unselected_background);

            ImageView icon = new ImageView(this);
            icon.setImageResource(moodIcons[i]);
            icon.setLayoutParams(new LinearLayout.LayoutParams(110, 110));

            TextView label = new TextView(this);
            label.setText(moodLabels[i]);
            label.setTextColor(getResources().getColor(android.R.color.black));
            label.setGravity(android.view.Gravity.CENTER);

            itemLayout.addView(icon);
            itemLayout.addView(label);

            final String mood = moodLabels[i];

            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedMoodText.setText("Selected Mood: " + mood);

                    if (lastSelected[0] != null) {
                        lastSelected[0].setBackgroundResource(R.drawable.unselected_background);
                    }

                    itemLayout.setBackgroundResource(R.drawable.selected_background);
                    lastSelected[0] = itemLayout;

                    // Change the background color of the main layout
                    mainLayout.setBackgroundColor(moodColors[index]);
                }
            });

            moodGrid.addView(itemLayout);
        }
    }



    private void showNextQuestion() {
        if (currentQuestionIndex < totalQuestions) {
            if (currentQuestionIndex < positiveQuestions.length) {
                questionText.setText(positiveQuestions[currentQuestionIndex]);
            } else {
                int negIndex = currentQuestionIndex - positiveQuestions.length;
                questionText.setText(negativeQuestions[negIndex]);
            }
        } else {
            updateMoodScore();
        }
    }

    private void handleAnswer(boolean answer) {
        if (currentQuestionIndex < totalQuestions) {
            responses.add(answer);
            // Score: +10 for Yes to positive, +10 for No to negative
            if (currentQuestionIndex < positiveQuestions.length) {
                if (answer) score += 10;
            } else {
                if (!answer) score += 10;
            }
            currentQuestionIndex++;
            showNextQuestion();
        }
    }

    private void updateMoodScore() {
        moodScoreText.setText("Mood Score: " + score + "%");

        if (score >= 80) {
            moodQuote.setText("You're doing amazing! Keep it up ");
        } else if (score >= 50) {
            moodQuote.setText("You're doing okay. Take it slow and care for yourself ");
        } else {
            moodQuote.setText("It's okay to not be okay. Take a break and reach out if needed ");
        }

        // Disable buttons after completion
        yesButton.setEnabled(false);
        noButton.setEnabled(false);
    }

    private void resetMoodTracker() {
        currentQuestionIndex = 0;
        score = 0;
        responses.clear();
        yesButton.setEnabled(true);
        noButton.setEnabled(true);
        showNextQuestion();
    }
}
