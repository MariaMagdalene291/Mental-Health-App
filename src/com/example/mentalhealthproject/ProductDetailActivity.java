package com.example.mentalhealthproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);

        // Initialize views
        TextView productNameTextView = (TextView) findViewById(R.id.productNameTextView);
        TextView productPriceTextView = (TextView) findViewById(R.id.productPriceTextView);
        ImageView productImageView = (ImageView) findViewById(R.id.productImageView);
        Button addToCartButton = (Button) findViewById(R.id.addToCartButton);

        // Get the selected product details from the intent
        Intent intent = getIntent();
        final String productName = intent.getStringExtra("PRODUCT_NAME");
        final String productPrice = intent.getStringExtra("PRODUCT_PRICE");
        final int productImage = intent.getIntExtra("PRODUCT_IMAGE", R.drawable.product1);

        // Display the product details
        productNameTextView.setText(productName);
        productPriceTextView.setText("Rs. " + productPrice);
        productImageView.setImageResource(productImage);

        // Handle "Add to Cart" button click
     // Handle "Add to Cart" button click
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Remove commas from the price string
                    String priceString = productPrice.replace(",", "");
                    double price = Double.parseDouble(priceString); // Convert price to double

                    // Create a CartItem object with the product details
                    CartActivity.CartItem cartItem = new CartActivity.CartItem(
                        productName, // Use the product name from the intent
                        price, // Convert price to double
                        1, // Default quantity
                        productImage // Use the product image from the intent
                    );

                    // Add the CartItem to the cart
                    CartActivity.addToCart(cartItem);

                    // Show a confirmation message
                    Toast.makeText(ProductDetailActivity.this, productName + " added to cart!", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    // Handle invalid price format
                    Toast.makeText(ProductDetailActivity.this, "Invalid product price!", Toast.LENGTH_SHORT).show();
                    Log.e("ProductDetailActivity", "Error parsing product price: " + e.getMessage(), e);
                }
            }
        });
    }
}