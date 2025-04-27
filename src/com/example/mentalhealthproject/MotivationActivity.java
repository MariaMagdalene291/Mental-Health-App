package com.example.mentalhealthproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MotivationActivity extends Activity {

    private GridView quotesGridView;
    private QuoteAdapter adapter;
    private ArrayList<QuoteItem> quotesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivation);

        // Initialize UI Components
        quotesGridView = (GridView) findViewById(R.id.quotesGridView);

        // Setup tabs for categories

        // Load motivational quotes
        loadQuotes();

        // Set adapter
        adapter = new QuoteAdapter(this, quotesList);
        quotesGridView.setAdapter(adapter);

        // Handle click events on quotes
        quotesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QuoteItem selectedQuote = quotesList.get(position);
                Toast.makeText(MotivationActivity.this, "Selected: " + selectedQuote.getQuote(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    

    private void loadQuotes() {
        quotesList = new ArrayList<QuoteItem>();
        quotesList.add(new QuoteItem("Any product that needs a manual to work is broken.", "Elon Musk"));
        quotesList.add(new QuoteItem("Make it simple but significant.", "Don Draper"));
        quotesList.add(new QuoteItem("Design is where science and art break even.", "Robin Mathew"));
        quotesList.add(new QuoteItem("Stay positive, work hard, make it happen.", "Unknown"));
        quotesList.add(new QuoteItem("Do what you love, and you’ll never work a day in your life.", "Confucius"));
        quotesList.add(new QuoteItem("Success is not final; failure is not fatal: It is the courage to continue that counts.", "Winston Churchill"));
    }
}
