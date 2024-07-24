package com.example.advancepizza_1190083_1190113.ui.home.pizzamenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.advancepizza_1190083_1190113.Pizza;
import com.example.advancepizza_1190083_1190113.PizzaAdapter;
import com.example.advancepizza_1190083_1190113.DataBaseHelper;
import com.example.advancepizza_1190083_1190113.R;

public class PizzaMenuFragment extends Fragment {

    private ArrayList<Pizza> pizzas = new ArrayList<>();
    private String customerEmail; // Add a field to store the customer's email
    private PizzaAdapter pizzaAdapter;

    private DataBaseHelper db;

    public PizzaMenuFragment() {
    }


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
        SearchView searchView = view.findViewById(R.id.searchViewPizzaMenu);

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            customerEmail = extras.getString("email", "");
        }

        pizzaAdapter = new PizzaAdapter(pizzas, customerEmail);


        // Set the layout manager and adapter for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(pizzaAdapter);

        // Set a query listener for the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission if needed
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query change
                filterPizzas(newText);
                return true;
            }
        });

    }

    private void fetchDataFromDatabase() {
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext());

        // Retrieve the list of pizzas from the database
        List<Pizza> retrievedPizzas = dbHelper.getAllPizzas();

        // Clear the existing list before adding new data
        pizzas.clear();

        // Add the retrieved pizzas to your existing list or use it as needed
        pizzas.addAll(retrievedPizzas);

    }

    private List<Pizza> originalPizzas = new ArrayList<>();

    private void filterPizzas(String searchText) {
        // If the original list is empty, initialize it with the current pizzas list
        if (originalPizzas.isEmpty()) {
            originalPizzas.addAll(pizzas);
        }

        // Use the selected filter to determine which property to filter
        ArrayList<Pizza> filteredPizzas = new ArrayList<>();

        // If the search text is empty, show all pizzas
        if (searchText.isEmpty()) {
            filteredPizzas.addAll(originalPizzas);
        } else {

            for (Pizza pizza : pizzas) {
                if (
                        pizza.getCategory().toLowerCase().contains(searchText.toLowerCase())||
                        String.valueOf(pizza.getPrice()).toLowerCase().contains(searchText.toLowerCase()))
                        {
                    filteredPizzas.add(pizza);
                }
            }
        }

        // Update the adapter with the filtered pizzas
        pizzaAdapter.updateData(filteredPizzas);
    }
}
