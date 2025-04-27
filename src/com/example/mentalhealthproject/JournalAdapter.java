package com.example.mentalhealthproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class JournalAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<JournalEntry> entries;

    public JournalAdapter(Context context, ArrayList<JournalEntry> entries) {
        this.context = context;
        this.entries = entries;
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Object getItem(int position) {
        return entries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        View card;
        TextView title;
        TextView content;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.journal_item, parent, false);
            holder = new ViewHolder();
            holder.card = convertView.findViewById(R.id.card);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        JournalEntry entry = entries.get(position);
        holder.title.setText(entry.title);
        holder.content.setText(entry.content);
        holder.card.setBackgroundColor(context.getResources().getColor(entry.colorId));

        return convertView;
   
    }
}
