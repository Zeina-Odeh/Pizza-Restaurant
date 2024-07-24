package com.example.advancepizza_1190083_1190113;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomerCardFragment extends Fragment {

    private ImageView pizzaPhoto;
    private TextView pizzaType, pizzaPrice, pizzaSize, pizzaCategory,
            pizzaOffer, pizzaQuantity, customerName;


    // Empty constructor is required
    public CustomerCardFragment() {
    }

    // Create a new instance of the fragment with necessary data
    public static CustomerCardFragment newInstance(Pizza pizza) {
        CustomerCardFragment fragment = new CustomerCardFragment();
        Bundle args = new Bundle();
        args.putSerializable("pizza", pizza);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI elements
        pizzaPhoto = view.findViewById(R.id.margarita_photo);
        pizzaType = view.findViewById(R.id.order_type);
        pizzaCategory = view.findViewById(R.id.order_category);
        pizzaPrice = view.findViewById(R.id.orderPrice);
        pizzaSize = view.findViewById(R.id.orderSize);
        //pizzaOffer = view.findViewById(R.id.orderOffer);
        pizzaQuantity = view.findViewById(R.id.order_quantity);
        customerName = view.findViewById(R.id.customer_name);



        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("pizza")) {
            Pizza pizza = (Pizza) arguments.getSerializable("pizza");

            // Set up the UI elements with data from the Pizza object
            pizzaType.setText(pizza.getType());
            pizzaCategory.setText(pizza.getCategory());
            pizzaPrice.setText(String.valueOf(pizza.getPrice()));
            //pizzaOffer.setText(String.valueOf(pizza.getOffer()));
            //pizzaSize
            //pizzaQuantity.setText(String.valueOf(pizza.ge()));
            customerName.setText(String.valueOf(pizza.getOrderedBy()));

        }
    }



}