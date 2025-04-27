package com.example.mentalhealthproject;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Show a toast message when alarm is triggered
        Toast.makeText(context, "Wake up! Sleep time is over ", Toast.LENGTH_LONG).show();

        // Launch the main activity again to disable dark mode
        Intent i = new Intent(context, SleepActivity.class); // Replace with your actual activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("disableDarkMode", true);
        context.startActivity(i);
    }
}