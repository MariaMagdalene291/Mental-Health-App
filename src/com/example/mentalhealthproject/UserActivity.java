package com.example.mentalhealthproject;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class UserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        TextView usernameText = (TextView) findViewById(R.id.profile_username);
        TextView emailText = (TextView) findViewById(R.id.profile_email);

        // Get the data passed from HomeActivity
        String username = getIntent().getStringExtra("USERNAME");
        String email = getIntent().getStringExtra("EMAIL");

        // Set the values to TextViews
        usernameText.setText(" Username: " + username);
        emailText.setText(" Email: " + email);
    }
}