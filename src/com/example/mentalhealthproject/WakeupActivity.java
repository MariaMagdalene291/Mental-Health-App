package com.example.mentalhealthproject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class WakeupActivity extends Activity {

    private EditText editTextGoal;
    private Button submitGoalButton;
    private Spinner habitSpinner;
    private TextView todaysFocus;
    private Switch focusSwitch;
    private CheckBox habit1, habit2, habit3, habit4;
    private TextView achievementText;
    private GridView morningTasksGrid;
    private boolean isSpinnerInitialized = false;

    String[] morningTasks = {
        "Make Bed", "Meditate", "Journal",
        "Stretch", "Plan Day", "Smile",
        "Water Plants", "Open Windows"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wakeup);

        // Linking UI components
        editTextGoal = (EditText) findViewById(R.id.editTextGoal);
        submitGoalButton = (Button) findViewById(R.id.submitGoalButton);
        habitSpinner = (Spinner) findViewById(R.id.habitSpinner);
        todaysFocus = (TextView) findViewById(R.id.todaysFocus);
        habit1 = (CheckBox) findViewById(R.id.habit1);
        habit2 = (CheckBox) findViewById(R.id.habit2);
        habit3 = (CheckBox) findViewById(R.id.habit3);
        habit4 = (CheckBox) findViewById(R.id.habit4);
        achievementText = (TextView) findViewById(R.id.achievementText);
        morningTasksGrid = (GridView) findViewById(R.id.morningTasksGrid);

        // Spinner setup
        String[] challenges = {
            "Gratitude Journal", "No Sugar Today", "Deep Breathing", "Compliment Someone", "10-min Walk"
        };

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, challenges
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        habitSpinner.setAdapter(spinnerAdapter);

        habitSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                if (!isSpinnerInitialized) {
                    isSpinnerInitialized = true;
                    return;
                }

                String selected = parent.getItemAtPosition(position).toString();
                if (selected.equals("Gratitude Journal")) {
                    Toast.makeText(WakeupActivity.this, "Write 3 things you're grateful for today!", Toast.LENGTH_LONG).show();
                } else if (selected.equals("No Sugar Today")) {
                    Toast.makeText(WakeupActivity.this, "Stay strong! Replace sweets with fruits ", Toast.LENGTH_LONG).show();
                } else if (selected.equals("Deep Breathing")) {
                    Toast.makeText(WakeupActivity.this, "Try 4-7-8 breathing: Inhale 4s, Hold 7s, Exhale 8s.", Toast.LENGTH_LONG).show();
                } else if (selected.equals("Compliment Someone")) {
                    Toast.makeText(WakeupActivity.this, "Brighten someone’s day with a kind word ", Toast.LENGTH_LONG).show();
                } else if (selected.equals("10-min Walk")) {
                    Toast.makeText(WakeupActivity.this, "Take a walk and breathe in the fresh air ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                // Optional: Handle when nothing is selected
            }
        });

        // GridView setup
        ArrayAdapter<String> gridAdapter = new ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, morningTasks
        );
        morningTasksGrid.setAdapter(gridAdapter);

        // Submit Goal Button
        submitGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String goal = editTextGoal.getText().toString().trim();
                if (!goal.isEmpty()) {
                    Toast.makeText(WakeupActivity.this, "Goal Saved: " + goal, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WakeupActivity.this, "Please enter your goal!", Toast.LENGTH_SHORT).show();
                }
            }
        });

      

        // Habit Checkbox Listeners
        View.OnClickListener checkListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkHabitsCompleted();
            }
        };

        habit1.setOnClickListener(checkListener);
        habit2.setOnClickListener(checkListener);
        habit3.setOnClickListener(checkListener);
        habit4.setOnClickListener(checkListener);
    }

    private void checkHabitsCompleted() {
        if (habit1.isChecked() && habit2.isChecked() && habit3.isChecked() && habit4.isChecked()) {
            achievementText.setText("You’ve earned an Achievement!");
        } else {
            achievementText.setText("Complete all to earn an Achievement!");
        }
    }
}
