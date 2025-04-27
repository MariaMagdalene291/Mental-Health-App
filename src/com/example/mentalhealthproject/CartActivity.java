package com.example.mentalhealthproject;

import android.app.Activity;
import android.content.Intent; // Import Intent
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends Activity {

    private static List<CartItem> cartItems = new ArrayList<CartItem>();

    public static void addToCart(CartItem item) {
        if (!cartItems.contains(item)) {
            cartItems.add(item);
        }
    }

    public static void removeFromCart(CartItem item) {
        cartItems.remove(item);
    }

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    public static void clearCart() {
        cartItems.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ListView cartListView = (ListView) findViewById(R.id.cartListView);
        final CartAdapter adapter = new CartAdapter();
        cartListView.setAdapter(adapter);

        Button checkoutButton = (Button) findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartItems.isEmpty()) {
                    Toast.makeText(CartActivity.this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    // Navigate to CheckoutActivity
                    Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private class CartAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return cartItems.size();
        }

        @Override
        public Object getItem(int position) {
            return cartItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.cart_item, parent, false);

                holder = new ViewHolder();
                holder.productImageView = (ImageView) convertView.findViewById(R.id.productImageView);
                holder.productNameTextView = (TextView) convertView.findViewById(R.id.productNameTextView);
                holder.productPriceTextView = (TextView) convertView.findViewById(R.id.productPriceTextView);
                holder.quantityEditText = (EditText) convertView.findViewById(R.id.quantityEditText);
                holder.decreaseQuantityButton = (Button) convertView.findViewById(R.id.decreaseQuantityButton);
                holder.increaseQuantityButton = (Button) convertView.findViewById(R.id.increaseQuantityButton);
                holder.removeButton = (Button) convertView.findViewById(R.id.removeButton);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final CartItem item = cartItems.get(position);

            holder.productImageView.setImageResource(item.getImageResource());
            holder.productNameTextView.setText(item.getProductName());
            holder.productPriceTextView.setText("Price: $" + item.getPrice());
            holder.quantityEditText.setText(String.valueOf(item.getQuantity()));

            holder.decreaseQuantityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = item.getQuantity();
                    if (quantity > 1) {
                        item.setQuantity(quantity - 1);
                        notifyDataSetChanged();
                    }
                }
            });

            holder.increaseQuantityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setQuantity(item.getQuantity() + 1);
                    notifyDataSetChanged();
                }
            });

            holder.removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartItems.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(CartActivity.this, "Product removed", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

        private class ViewHolder {
            ImageView productImageView;
            TextView productNameTextView;
            TextView productPriceTextView;
            EditText quantityEditText;
            Button decreaseQuantityButton;
            Button increaseQuantityButton;
            Button removeButton;
        }
    }

    public static class CartItem {
        private String productName;
        private double price;
        private int quantity;
        private int imageResource;

        public CartItem(String productName, double price, int quantity, int imageResource) {
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
            this.imageResource = imageResource;
        }

        public String getProductName() {
            return productName;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getImageResource() {
            return imageResource;
        }
    }
}
