package com.example.mentalhealthproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.app.Activity;
import java.util.Map;
import java.util.HashMap;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        new android.app.AlertDialog.Builder(this)
        .setTitle(" Reminder")
        .setMessage("Don’t forget to smile today!")
        .setPositiveButton("Got it!", null)
        .show();
        
     // Get reference to your welcome TextView (make sure it has an ID like welcome_textview)
        TextView welcomeText = (TextView) findViewById(R.id.welcome_textview);

        // Get the username passed from LoginActivity
        String username = getIntent().getStringExtra("USERNAME");

        if (username != null && !username.isEmpty()) {
            welcomeText.setText("Welcome, " + username + "!");
        }

        // Initialize ImageViews
        ImageView moodtrackerImage = (ImageView)findViewById(R.id.moodTrackerIcon);
        ImageView productsImage = (ImageView)findViewById(R.id.productsIcon);
        ImageView userImage = (ImageView)findViewById(R.id.userIcon);
        ImageView routinesImage = (ImageView)findViewById(R.id.routines);
        ImageView breatheImage = (ImageView)findViewById(R.id.section1Breathe);
        ImageView sleepImage = (ImageView)findViewById(R.id.section1Sleep);
        ImageView wakeImage = (ImageView)findViewById(R.id.section1Wakeup);
        ImageView moveImage = (ImageView)findViewById(R.id.section2Move);
        ImageView motiveImage = (ImageView)findViewById(R.id.section2Motivation);
        ImageView journalImage = (ImageView)findViewById(R.id.section2Journal);
        ImageView homeImage = (ImageView)findViewById(R.id.nav_home);
        ImageView soundsImage = (ImageView)findViewById(R.id.nav_sounds);
        ImageView favoriteImage = (ImageView)findViewById(R.id.nav_favorites);

        final SearchView searchView = (SearchView)findViewById(R.id.searchView);
        
        // Map of search keywords to corresponding activities
        final Map<String, Class<?>> activityMap = new HashMap<String, Class<?>>();
        activityMap.put("moodtracker", MoodTrackerActivity.class);
        activityMap.put("products", ProductsActivity.class);
        activityMap.put("user", UserActivity.class);
        activityMap.put("routines", RoutinesActivity.class);
        activityMap.put("breathe", BreatheActivity.class);
        activityMap.put("sleep", SleepActivity.class);
        activityMap.put("wakeup", WakeupActivity.class);
        activityMap.put("move", MoveActivity.class);
        activityMap.put("motivation", MotivationActivity.class);
        activityMap.put("journal", JournalActivity.class);
        activityMap.put("home", HomeActivity.class);
        activityMap.put("favorites", FavoriteActivity.class);
        activityMap.put("sounds", SoundsActivity.class);
        
        
        // Set search query listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query = query.toLowerCase().trim(); // Convert query to lowercase and trim spaces

                if (activityMap.containsKey(query)) {
                    Intent intent = new Intent(HomeActivity.this, activityMap.get(query));
                    startActivity(intent);
                } else {
                    searchView.setQuery("", false); // Clear search field
                    searchView.clearFocus();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false; // No action needed while typing
            }
        });
        
        
        // Set Click Listeners
        moodtrackerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MoodTrackerActivity.class);
                startActivity(intent);
            }
        });
        
        productsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProductsActivity.class);
                startActivity(intent);
            }
        });
        
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(HomeActivity.this, UserActivity.class);

                // Get the extras received from LoginActivity
                String username = getIntent().getStringExtra("USERNAME");
                String email = getIntent().getStringExtra("EMAIL");

                // Pass them to ProfileActivity
                profileIntent.putExtra("USERNAME", username);
                profileIntent.putExtra("EMAIL", email);

                startActivity(profileIntent);
            }
        });
        
        routinesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RoutinesActivity.class);
                startActivity(intent);
            }
        });
        
        breatheImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BreatheActivity.class);
                startActivity(intent);
            }
        });

        sleepImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SleepActivity.class);
                startActivity(intent);
            }
        });

        wakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WakeupActivity.class);
                startActivity(intent);
            }
        });

       moveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MoveActivity.class);
                startActivity(intent);
            }
        });
       
       motiveImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(HomeActivity.this, MotivationActivity.class);
               startActivity(intent);
           }
       });
       
       journalImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(HomeActivity.this, JournalActivity.class);
               startActivity(intent);
           }
       });
       
       homeImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
               startActivity(intent);
           }
       });
       favoriteImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(HomeActivity.this, ProductsActivity.class);
               startActivity(intent);
           }
       });
       soundsImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(HomeActivity.this, MoodTrackerActivity.class);
               startActivity(intent);
           }
       });
    }
}