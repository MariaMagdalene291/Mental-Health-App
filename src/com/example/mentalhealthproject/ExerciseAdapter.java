package com.example.mentalhealthproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class ExerciseAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ExerciseItem> itemList;

    public ExerciseAdapter(Context context, ArrayList<ExerciseItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.grid_item_exercise, parent, false);
        }

        ImageView image = (ImageView)convertView.findViewById(R.id.exerciseImage);
        TextView title = (TextView)convertView.findViewById(R.id.exerciseTitle);
        TextView desc = (TextView)convertView.findViewById(R.id.exerciseDesc);
        TextView durationView = (TextView)convertView.findViewById(R.id.exerciseDuration);


        ExerciseItem item = itemList.get(i);
        image.setImageResource(item.imageId);
        title.setText(item.title);
        desc.setText(item.description);
        durationView.setText(item.duration + " min");




        return convertView;
    }
    
}
