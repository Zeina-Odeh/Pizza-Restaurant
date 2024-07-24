package com.example.advancepizza_1190083_1190113.ui.home.favorits;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class FavoritesFragment extends Fragment {
    private ArrayList<Pizza> favoriteCars = new ArrayList<>();
    private String customerEmail;
    private boolean isOfferAdapter; // Flag to indicate if this adapter is for offers


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFavorites);

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            customerEmail = extras.getString("email", "");
        }

        favoriteCars = fetchDataFromDatabase();

        PizzaAdapter pizzaAdapter = new PizzaAdapter(favoriteCars, customerEmail);

        // Set the layout manager and adapter for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(pizzaAdapter);
    }


    private ArrayList<Pizza> fetchDataFromDatabase() {
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext());

        ArrayList<Pizza> retrievedFavoritePizzas = dbHelper.getFavoritePizza(customerEmail);
        Log.d("FavoritesFragment", "Number of favorite pizza: " + retrievedFavoritePizzas.size());
        // Close the database when done
        dbHelper.close();

        return retrievedFavoritePizzas;
    }
}
