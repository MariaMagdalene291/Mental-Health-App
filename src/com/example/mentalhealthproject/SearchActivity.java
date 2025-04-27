package com.example.mentalhealthproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class SearchActivity extends Activity {

    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> itemList;
    
    // Map of search keywords to corresponding activities
    private final Map<String, Class<?>> activityMap = new HashMap<String, Class<?>>() {{
        put("moodtracker", MoodTrackerActivity.class);
        put("products", ProductsActivity.class);
        put("user", UserActivity.class);
        put("routines", RoutinesActivity.class);
        put("breathe", BreatheActivity.class);
        put("sleep", SleepActivity.class);
        put("wakeup", WakeupActivity.class);
        put("move", MoveActivity.class);
        put("motivation", MotivationActivity.class);
        put("journal", JournalActivity.class);
        put("home", HomeActivity.class);
        put("favorites", FavoriteActivity.class);
        put("sounds", SoundsActivity.class);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.listView);

        // Convert keys from activityMap to a List for displaying search suggestions
        itemList = new ArrayList<String>(activityMap.keySet());
        adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, itemList);

        listView.setAdapter(adapter);

        // Search query listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        	@Override
        	public boolean onQueryTextSubmit(String query) {
        	    query = query.toLowerCase().trim();
        	    if (activityMap.containsKey(query)) {
        	        Intent intent = new Intent(SearchActivity.this, activityMap.get(query));
        	        startActivity(intent);
        	    } else {
        	        Toast.makeText(SearchActivity.this, "No results found!", Toast.LENGTH_SHORT).show();
        	        searchView.setQuery("", false); // Clear search field
        	        searchView.clearFocus();
        	    }
        	    return true;
        	}

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        // List item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = adapter.getItem(position);
                if (activityMap.containsKey(selectedItem)) {
                    Intent intent = new Intent(SearchActivity.this, activityMap.get(selectedItem));
                    startActivity(intent);
                }
            }
        });
    }
}
