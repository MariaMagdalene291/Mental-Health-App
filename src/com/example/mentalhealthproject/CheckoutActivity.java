package com.example.mentalhealthproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class CheckoutActivity extends Activity {

    private ListView checkoutListView;
    private TextView totalAmountTextView;
    private Button payNowButton;
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Initialize UI components
        checkoutListView = (ListView) findViewById(R.id.checkoutListView);
        totalAmountTextView = (TextView) findViewById(R.id.totalAmountTextView);
        payNowButton = (Button) findViewById(R.id.payNowButton);

        // Get cart items
        List<CartActivity.CartItem> cartItems = CartActivity.getCartItems();

        // Set adapter for list view
        CheckoutAdapter adapter = new CheckoutAdapter(cartItems);
        checkoutListView.setAdapter(adapter);

        // Calculate and display total price
        totalPrice = calculateTotalPrice(cartItems);
        totalAmountTextView.setText("Total: $" + String.format("%.2f", totalPrice));

        // Pay Now button click event
        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckoutActivity.this, PaymentActivity.class);
                intent.putExtra("TOTAL_PRICE", totalPrice); // Pass total price to PaymentActivity
                startActivity(intent);
            }
        });
    }

    // Calculate total price
    private double calculateTotalPrice(List<CartActivity.CartItem> cartItems) {
        double total = 0;
        for (CartActivity.CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    // Adapter for Checkout ListView
    private class CheckoutAdapter extends BaseAdapter {
        private List<CartActivity.CartItem> items;

        public CheckoutAdapter(List<CartActivity.CartItem> items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.checkout_item, parent, false);
                holder = new ViewHolder();
                holder.productImageView = (ImageView) convertView.findViewById(R.id.productImageView);
                holder.productNameTextView = (TextView) convertView.findViewById(R.id.productNameTextView);
                holder.productPriceTextView = (TextView) convertView.findViewById(R.id.productPriceTextView);
                holder.quantityTextView = (TextView) convertView.findViewById(R.id.quantityTextView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // Get the current item
            CartActivity.CartItem item = items.get(position);

            // Bind data to views
            holder.productImageView.setImageResource(item.getImageResource());
            holder.productNameTextView.setText(item.getProductName());
            holder.productPriceTextView.setText("Price: $" + item.getPrice());
            holder.quantityTextView.setText("Qty: " + item.getQuantity());

            return convertView;
        }

        // ViewHolder for optimization
        private class ViewHolder {
            ImageView productImageView;
            TextView productNameTextView;
            TextView productPriceTextView;
            TextView quantityTextView;
        }
    }
}
