package com.example.mentalhealthproject;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RoutinesActivity extends Activity {

    private GridLayout routineGridLayout;
    private EditText routineHeading, routineDay, routineSchedule;
    private Button startTimeButton, endTimeButton, saveRoutineButton;
    private String startTime = "", endTime = "";

    private List<String> routines = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routines);

        routineGridLayout = (GridLayout) findViewById(R.id.routineGridLayout);
        routineHeading = (EditText) findViewById(R.id.routineHeading);
        routineDay = (EditText) findViewById(R.id.routineDay);
        routineSchedule = (EditText) findViewById(R.id.routineSchedule);
        startTimeButton = (Button) findViewById(R.id.startTimeButton);
        endTimeButton = (Button) findViewById(R.id.endTimeButton);
        saveRoutineButton = (Button) findViewById(R.id.saveRoutineButton);

        // Set Click Listeners using View.OnClickListener
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(true);
            }
        });

        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(false);
            }
        });

        saveRoutineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRoutine();
            }
        });
    }

    private void showTimePicker(final boolean isStartTime) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(android.widget.TimePicker view, int selectedHour, int selectedMinute) {
                String time = String.format("%02d:%02d", selectedHour, selectedMinute);
                if (isStartTime) {
                    startTime = time;
                    startTimeButton.setText("Start Time: " + startTime);
                } else {
                    endTime = time;
                    endTimeButton.setText("End Time: " + endTime);
                }
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void saveRoutine() {
        String heading = routineHeading.getText().toString().trim();
        String day = routineDay.getText().toString().trim();
        String schedule = routineSchedule.getText().toString().trim();

        if (heading.isEmpty() || day.isEmpty() || schedule.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        String routineDetails = heading + "\n" + day + "\nTime: " + startTime + " - " + endTime + "\n" + schedule;
        routines.add(routineDetails);

        // Display routine in a new grid
        TextView routineView = new TextView(this);
        routineView.setText(routineDetails);
        routineView.setPadding(20, 20, 20, 20);
        routineView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        routineView.setTextColor(getResources().getColor(android.R.color.white));

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setMargins(10, 10, 10, 10);
        routineView.setLayoutParams(params);

        routineGridLayout.addView(routineView);
        Toast.makeText(this, "Routine Added!", Toast.LENGTH_SHORT).show();

        // Clear input fields after saving
        routineHeading.setText("");
        routineDay.setText("");
        routineSchedule.setText("");
        startTime = "";
        endTime = "";
        startTimeButton.setText("Pick Start Time");
        endTimeButton.setText("Pick End Time");
    }
}
