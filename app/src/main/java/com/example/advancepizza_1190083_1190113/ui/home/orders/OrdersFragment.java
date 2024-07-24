package com.example.advancepizza_1190083_1190113.ui.home.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advancepizza_1190083_1190113.DataBaseHelper;
import com.example.advancepizza_1190083_1190113.Ordered;
import com.example.advancepizza_1190083_1190113.OrderedAdapter;
import com.example.advancepizza_1190083_1190113.R;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {

    private ArrayList<Ordered> orders = new ArrayList<>();
    private String customerEmail;

    public OrdersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewOrder);

        // Retrieve the customer's email from the intent extras
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            customerEmail = extras.getString("email", "");
        }

        // Fetch orders from the database
        orders = fetchOrdersFromDatabase();

        // Pass the orders to the adapter
        OrderedAdapter orderedAdapter = new OrderedAdapter(orders, getContext());

        // Set the layout manager and adapter for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(orderedAdapter);
    }

    private ArrayList<Ordered> fetchOrdersFromDatabase() {
        // Create an instance of your database helper
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext());

        // Retrieve orders from the database for the specific customer
        ArrayList<Ordered> orders = dbHelper.getOrders(customerEmail);

        // Close the database when done
        dbHelper.close();

        return orders;
    }
}
