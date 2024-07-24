// OrderDetailActivity.java
package com.example.advancepizza_1190083_1190113;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OrderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        // Get the intent extras
        String pizzaType = getIntent().getStringExtra("pizzaType");
        String size = getIntent().getStringExtra("size");
        int quantity = getIntent().getIntExtra("quantity", 0);
        double basePrice = getIntent().getDoubleExtra("basePrice", 0.0);
        double finalPrice = getIntent().getDoubleExtra("finalPrice", 0.0);

        // Initialize views
        TextView tvPizzaType = findViewById(R.id.tvPizzaType);
        TextView tvSize = findViewById(R.id.tvSize);
        TextView tvQuantity = findViewById(R.id.tvQuantity);
        TextView tvBasePrice = findViewById(R.id.tvBasePrice);
        TextView tvFinalPrice = findViewById(R.id.tvFinalPrice);


        // Set data to views
        tvPizzaType.setText(pizzaType);
        tvSize.setText(size);
        tvQuantity.setText(String.valueOf(quantity));
        tvBasePrice.setText(String.valueOf(basePrice));
        tvFinalPrice.setText(String.valueOf(finalPrice));
    }
}
