package com.example.mentalhealthproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductSearchActivity extends Activity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productsearch);

        // Initialize the search bar
        EditText searchBar = (EditText) findViewById(R.id.searchBar);

        // Initialize the ListView and Adapter
        listView = (ListView) findViewById(R.id.productListView);

        // List of products
        String[] products = {
                "Meditation Kit", "Aromatherapy Diffuser", "Stress Relief Ball Set",
                "Sleep Sound Machine", "Weighted Blanket", "Herbal Tea Set",
                "Guided Journal", "Sleep Mask with Bluetooth", "Himalayan Salt Lamp",
                "Relaxing Candle Set"
        };

        // Convert array to a list for filtering
        productList = new ArrayList<String>(Arrays.asList(products));

        // Initialize the adapter with the full list of products
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, productList);
        listView.setAdapter(adapter);

        // Add a TextWatcher to the search bar
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter the list based on user input
                filterProducts(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed
            }
        });

        // Set item click listener for the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected product details
                String selectedProduct = adapter.getItem(position);
                String selectedProductPrice = getProductPrice(selectedProduct); // Get price
                int selectedProductImage = getProductImage(selectedProduct); // Get image resource ID

                // Navigate to ProductDetailActivity with product details
                Intent intent = new Intent(ProductSearchActivity.this, ProductDetailActivity.class);
                intent.putExtra("PRODUCT_NAME", selectedProduct);
                intent.putExtra("PRODUCT_PRICE", selectedProductPrice);
                intent.putExtra("PRODUCT_IMAGE", selectedProductImage);
                startActivity(intent);
            }
        });
    }

    // Helper method to filter products based on search query
    private void filterProducts(String query) {
        // Create a new filtered list
        List<String> filteredList = new ArrayList<String>();

        // Loop through the original list and add matching items to the filtered list
        for (String product : productList) {
            if (product.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(product);
            }
        }

        // Update the adapter with the filtered list
        adapter.clear();
        adapter.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }

    // Helper method to get product price (using if-else)
    private String getProductPrice(String productName) {
        if ("Meditation Kit".equals(productName)) {
            return "1,499";
        } else if ("Aromatherapy Diffuser".equals(productName)) {
            return "2,199";
        } else if ("Stress Relief Ball Set".equals(productName)) {
            return "1,199";
        } else if ("Sleep Sound Machine".equals(productName)) {
            return "2,199";
        } else if ("Weighted Blanket".equals(productName)) {
            return "2,199";
        } else if ("Herbal Tea Set".equals(productName)) {
            return "2,199";
        } else if ("Guided Journal".equals(productName)) {
            return "2,199";
        } else if ("Sleep Mask with Bluetooth".equals(productName)) {
            return "2,199";
        } else if ("Himalayan Salt Lamp".equals(productName)) {
            return "2,199";
        } else if ("Relaxing Candle Set".equals(productName)) {
            return "2,199";
        } else {
            return "0";
        }
    }

    // Helper method to get product image resource ID (using if-else)
    private int getProductImage(String productName) {
        if ("Meditation Kit".equals(productName)) {
            return R.drawable.meditation_kit;
        } else if ("Aromatherapy Diffuser".equals(productName)) {
            return R.drawable.aromatherapy;
        } else if ("Stress Relief Ball Set".equals(productName)) {
            return R.drawable.stressreliefball;
        } else if ("Sleep Sound Machine".equals(productName)) {
            return R.drawable.sleepsoundmachine;
        } else if ("Weighted Blanket".equals(productName)) {
            return R.drawable.blanket;
        } else if ("Herbal Tea Set".equals(productName)) {
            return R.drawable.herbalteaset;
        } else if ("Guided Journal".equals(productName)) {
            return R.drawable.guidedjournal;
        } else if ("Sleep Mask with Bluetooth".equals(productName)) {
            return R.drawable.sleepbluetooth;
        } else if ("Himalayan Salt Lamp".equals(productName)) {
            return R.drawable.saltlamp;
        } else if ("Relaxing Candle Set".equals(productName)) {
            return R.drawable.candleset;
        } else {
            return R.drawable.meditation_kit; // Add a default image in your drawable folder
        }
    }
}