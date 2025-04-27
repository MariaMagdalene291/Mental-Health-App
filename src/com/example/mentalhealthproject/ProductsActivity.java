package com.example.mentalhealthproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ProductsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        // Initialize search bar
        EditText searchBar = (EditText) findViewById(R.id.searchBar);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ProductSearchActivity
                Intent intent = new Intent(ProductsActivity.this, ProductSearchActivity.class);
                startActivity(intent);
            }
        });

        // Initialize cart image
        ImageView cartImage = (ImageView) findViewById(R.id.cartIcon);
        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CartActivity
                Intent intent = new Intent(ProductsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        // Array-based initialization to avoid redundancy
        int[] addButtonIds = {R.id.btn_add1, R.id.btn_add2, R.id.btn_add3, R.id.btn_add4, R.id.btn_add5,
                R.id.btn_add6, R.id.btn_add7, R.id.btn_add8, R.id.btn_add9, R.id.btn_add10};
       
        final String[] productNames = {"Meditation Kit", "Aromatherapy Diffuser", "Stress Relief Ball Set", "Sleep Sound Machine",
                "Weighted Blanket", "Herbal Tea Set", "Guided Journal", "Sleep Mask with Bluetooth", "Himalayan Salt Lamp", "Relaxing Candle Set"};
        final double[] productPrices = {499.99, 299.99, 199.99, 399.99, 599.99, 149.99, 99.99, 249.99, 129.99, 199.99};
        final int[] productImages = {R.drawable.meditation_kit, R.drawable.aromatherapy, R.drawable.stressreliefball,
                R.drawable.sleepsoundmachine, R.drawable.blanket, R.drawable.herbalteaset, R.drawable.guidedjournal,
                R.drawable.sleepbluetooth, R.drawable.saltlamp, R.drawable.candleset};

        for (int i = 0; i < addButtonIds.length; i++) {
            final int index = i;
            Button btnAdd = (Button) findViewById(addButtonIds[i]);

            if (btnAdd != null) {
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addToCart(productNames[index], productPrices[index], productImages[index]);
                    }
                });
            }
           
        }

        // Bottom Navigation
        ImageView homeImage = (ImageView) findViewById(R.id.nav_home);
        ImageView userImage = (ImageView) findViewById(R.id.user);
        ImageView soundsImage = (ImageView) findViewById(R.id.nav_sounds);
        ImageView favoriteImage = (ImageView) findViewById(R.id.nav_favorites);

        if (homeImage != null) {
            homeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity(HomeActivity.class);
                }
            });
        }
        if (userImage != null) {
            userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity(UserActivity.class);
                }
            });
        }
        if (soundsImage != null) {
            soundsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity(SoundsActivity.class);
                }
            });
        }
        if (favoriteImage != null) {
            favoriteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity(FavoriteActivity.class);
                }
            });
        }
    }

    private void addToCart(String productName, double productPrice, int productImage) {
        // Create a CartItem object with the product details
        CartActivity.CartItem cartItem = new CartActivity.CartItem(
            productName, // Product name
            productPrice, // Product price
            1, // Default quantity
            productImage // Product image
        );

        // Add the CartItem to the cart
        CartActivity.addToCart(cartItem);

        // Show a confirmation message
        Toast.makeText(this, productName + " added to cart!", Toast.LENGTH_SHORT).show();
    }

    

    private void openActivity(Class<?> activityClass) {
        Intent intent = new Intent(ProductsActivity.this, activityClass);
        startActivity(intent);
    }
}