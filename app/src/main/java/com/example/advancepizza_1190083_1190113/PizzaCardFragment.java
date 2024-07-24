package com.example.advancepizza_1190083_1190113;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.advancepizza_1190083_1190113.Pizza;
import com.example.advancepizza_1190083_1190113.R;

public class PizzaCardFragment extends Fragment {

    // Define UI elements
    private ImageView pizzaPhoto;
    private TextView pizzaType, pizzaCategory, pizzaPrice ,pizzaOffer;
    private ImageView favoriteButton;
    private Button orderButton;
    private boolean isFavorite = false;

    public PizzaCardFragment() {
    }

    // Create a new instance of the fragment with necessary data
    public static PizzaCardFragment newInstance(Pizza pizza) {
        PizzaCardFragment fragment = new PizzaCardFragment();
        Bundle args = new Bundle();
        args.putSerializable("pizza", pizza);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pizza_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI elements
        pizzaPhoto = view.findViewById(R.id.margarita_photo);
        pizzaType = view.findViewById(R.id.pizza_type);
        pizzaCategory = view.findViewById(R.id.pizza_category);
        pizzaPrice = view.findViewById(R.id.pizza_price);
        favoriteButton = view.findViewById(R.id.favorite);
        orderButton = view.findViewById(R.id.orderButton);


        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("pizza")) {
            Pizza pizza = (Pizza) arguments.getSerializable("pizza");

            // Set up the UI elements with data from the Pizza object
            pizzaType.setText(pizza.getType());
            pizzaCategory.setText(pizza.getCategory());

            String formattedPrice = pizza.getFormattedPrice();
            Log.d("PizzaPrice", "Formatted Price: " + formattedPrice);
            pizzaPrice.setText(formattedPrice);

          //  pizzaOffer.setText(String.valueOf(pizza.getOffer()));

        }
    }



}
