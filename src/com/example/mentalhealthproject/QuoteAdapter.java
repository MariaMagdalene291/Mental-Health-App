package com.example.mentalhealthproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class QuoteAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<QuoteItem> quotesList;

    public QuoteAdapter(Context context, ArrayList<QuoteItem> quotesList) {
        this.context = context;
        this.quotesList = quotesList;
    }

    @Override
    public int getCount() {
        return quotesList.size();
    }

    @Override
    public Object getItem(int i) {
        return quotesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.grid_item_quote, parent, false);
        }

        TextView quoteText = (TextView)convertView.findViewById(R.id.quoteText);
        TextView authorText = (TextView)convertView.findViewById(R.id.authorText);
     

        QuoteItem quoteItem = quotesList.get(i);
        quoteText.setText(quoteItem.getQuote());
        authorText.setText("- " + quoteItem.getAuthor());

        return convertView;
    }
}
