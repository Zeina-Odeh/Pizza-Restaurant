package com.example.advancepizza_1190083_1190113.ui.home.offers;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.advancepizza_1190083_1190113.Pizza;
import com.example.advancepizza_1190083_1190113.PizzaAdapter;
import com.example.advancepizza_1190083_1190113.DataBaseHelper;
import com.example.advancepizza_1190083_1190113.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OffersFragment extends Fragment {
    private ArrayList<Pizza> pizzas = new ArrayList<>();
    private String customerEmail; // Add a field to store the customer's email


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fetchDataFromDatabase();
        return inflater.inflate(R.layout.fragment_pizza_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewPizzaMenu);
        // Retrieve the customer's email from the intent extras
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            customerEmail = extras.getString("email", "");
        }

        PizzaAdapter pizzaAdapter = new PizzaAdapter(pizzas, customerEmail);

        // Set the layout manager and adapter for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(pizzaAdapter);
    }
    private void fetchDataFromDatabase() {
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext());

        List<Pizza> retrievedPizzas = dbHelper.getAllPizzas();

        // Clear the existing list before adding new data
        pizzas.clear();

        pizzas.addAll(retrievedPizzas);

        this.pizzas.addAll(pizzas);
        for (Pizza pizza : pizzas) {
            Log.d("PizzaMenuFragment", pizza.toString());
        }
    }

}
