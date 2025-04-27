package com.example.mentalhealthproject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class PaymentActivity extends Activity {

    private TextView totalPriceTextView, cardNumberError, expiryError, cvvError, paymentMethodError, productNamesTextView;
    private EditText cardNumberEditText, expiryEditText, cvvEditText;
    private RadioGroup paymentMethodGroup;
    private Button proceedToPayButton;
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Initialize UI components
        totalPriceTextView = (TextView) findViewById(R.id.totalPriceTextView);
        productNamesTextView = (TextView) findViewById(R.id.productNamesTextView);  // Add this TextView in XML layout
        cardNumberEditText = (EditText) findViewById(R.id.cardNumberEditText);
        expiryEditText = (EditText) findViewById(R.id.expiryEditText);
        cvvEditText = (EditText) findViewById(R.id.cvvEditText);
        paymentMethodGroup = (RadioGroup) findViewById(R.id.paymentMethodGroup);
        proceedToPayButton = (Button) findViewById(R.id.proceedToPayButton);

        // Initialize error message TextViews
        cardNumberError = (TextView) findViewById(R.id.cardNumberError);
        expiryError = (TextView) findViewById(R.id.expiryError);
        cvvError = (TextView) findViewById(R.id.cvvError);
        paymentMethodError = (TextView) findViewById(R.id.paymentMethodError);

        // Display product names from cart
        List<CartActivity.CartItem> cartItems = CartActivity.getCartItems();
        StringBuilder productsBuilder = new StringBuilder();
        for (CartActivity.CartItem item : cartItems) {
            productsBuilder.append("- ")
                    .append(item.getProductName())
                    .append(" x")
                    .append(item.getQuantity())
                    .append("\n");
        }
        productNamesTextView.setText("Items:\n" + productsBuilder.toString());

        // Retrieve total price from intent (if needed)
        totalPrice = getIntent().getDoubleExtra("TOTAL_PRICE", 0.0);
        totalPriceTextView.setText("Total Amount: " + String.format("%.2f", totalPrice));

        // Handle Proceed to Pay button click
        proceedToPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatePaymentDetails()) {
                    // Clear error messages
                    paymentMethodError.setText("");
                    cardNumberError.setText("");
                    expiryError.setText("");
                    cvvError.setText("");

                    // Show success message
                    totalPriceTextView.setText("Payment Successful!");
                    totalPriceTextView.setTextColor(Color.GREEN);
                }
            }
        });
    }

    // Validate payment details
    private boolean validatePaymentDetails() {
        boolean isValid = true;

        int selectedPaymentMethod = paymentMethodGroup.getCheckedRadioButtonId();
        if (selectedPaymentMethod == -1) {
            paymentMethodError.setText("Please select a payment method");
            paymentMethodError.setTextColor(Color.RED);
            isValid = false;
        } else {
            paymentMethodError.setText("");
        }

        String cardNumber = cardNumberEditText.getText().toString().trim();
        if (!cardNumber.matches("\\d{16}")) {
            cardNumberError.setText("Enter a valid 16-digit card number");
            cardNumberError.setTextColor(Color.RED);
            isValid = false;
        } else {
            cardNumberError.setText("");
        }

        String expiry = expiryEditText.getText().toString().trim();
        if (!expiry.matches("^(0[1-9]|1[0-2])/(\\d{2})$")) {
            expiryError.setText("Enter a valid expiry date (MM/YY)");
            expiryError.setTextColor(Color.RED);
            isValid = false;
        } else {
            expiryError.setText("");
        }

        String cvv = cvvEditText.getText().toString().trim();
        if (!cvv.matches("^\\d{3}$")) {
            cvvError.setText("Enter a valid 3-digit CVV");
            cvvError.setTextColor(Color.RED);
            isValid = false;
        } else {
            cvvError.setText("");
        }

        return isValid;
    }
}
