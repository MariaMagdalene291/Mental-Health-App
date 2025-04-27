package com.example.mentalhealthproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class JournalActivity extends Activity {

    private ListView journalListView;
    private Button addNoteButton;
    private JournalAdapter adapter;
    private ArrayList<JournalEntry> journalEntries;
    private ImageView themeToggle;
    private boolean isDarkTheme = false;

    private static final int ADD_ENTRY_REQUEST_CODE = 1; // request code for AddJournalEntryActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadThemePreference(); // Apply theme BEFORE super.onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        journalListView = (ListView) findViewById(R.id.journalListView);
        addNoteButton = (Button) findViewById(R.id.addNoteButton);
        themeToggle = (ImageView) findViewById(R.id.themeToggle);

        // Dummy Data
        journalEntries = new ArrayList<JournalEntry>();
        journalEntries.add(new JournalEntry("Plan for the Day", "Meeting at 3 PM, Yoga at 6 PM", R.color.red));
        journalEntries.add(new JournalEntry("Tax Payment", "Don't forget to pay before March!", R.color.blue));
        journalEntries.add(new JournalEntry("Self-Care Reminder", "Take a break, meditate for 10 min.", R.color.green));

        adapter = new JournalAdapter(this, journalEntries);
        journalListView.setAdapter(adapter);

        // Add Note Button Click
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JournalActivity.this, AddJournalEntryActivity.class);
                startActivityForResult(intent, ADD_ENTRY_REQUEST_CODE);
            }
        });

        // Toggle Theme
        themeToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDarkTheme = !isDarkTheme;
                saveThemePreference(isDarkTheme);
                recreate(); // Restart activity to apply theme
            }
        });
    }

    private void saveThemePreference(boolean isDark) {
        SharedPreferences prefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("DarkTheme", isDark);
        editor.apply();
    }

    private void loadThemePreference() {
        SharedPreferences prefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        isDarkTheme = prefs.getBoolean("DarkTheme", false);
    }

    // Receive data from AddJournalEntryActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ENTRY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");

            if (title != null && content != null) {
                // Default color for new entries (you can randomize or allow user to choose later)
                int defaultColorId = R.color.light_gray;
                journalEntries.add(new JournalEntry(title, content, defaultColorId));
                adapter.notifyDataSetChanged(); // Refresh the ListView
            }
        }
    }
}
