package com.example.mentalhealthproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MoodHistoryAdapter extends ArrayAdapter<String> {

    public MoodHistoryAdapter(Context context, List<String> moodHistory) {
        super(context, 0, moodHistory);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_mood_history, parent, false);
        }

        // Get the mood entry
        String moodEntry = getItem(position);

        // Find the TextView and set the mood entry
        TextView moodTextView = (TextView) convertView.findViewById(R.id.moodTextView);
        moodTextView.setText(moodEntry);

        return convertView;
    }
}
