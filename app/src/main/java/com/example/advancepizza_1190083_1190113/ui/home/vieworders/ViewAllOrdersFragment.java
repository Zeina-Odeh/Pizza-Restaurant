package com.example.advancepizza_1190083_1190113.ui.home.vieworders;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advancepizza_1190083_1190113.AdminOrdersAdapter;
import com.example.advancepizza_1190083_1190113.DataBaseHelper;
import com.example.advancepizza_1190083_1190113.Ordered;
import com.example.advancepizza_1190083_1190113.ui.home.orders.OrdersSummaryFragment;
import com.example.advancepizza_1190083_1190113.R;

import java.util.ArrayList;

public class ViewAllOrdersFragment extends Fragment {
    private ArrayList<Ordered> orders = new ArrayList<>();
    private String customerEmail;
    private Button btnCalculateOrders;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_all_orders, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve the customer's email from the intent extras
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            customerEmail = extras.getString("email", "");
        }

        // Retrieve orders from the database
        orders = fetchDataFromDatabase();

        // Set up RecyclerView and adapter
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewAllOrders);
        AdminOrdersAdapter adminOrdersAdapter = new AdminOrdersAdapter(requireContext(), orders);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adminOrdersAdapter);

        Button btnCalculateOrders = view.findViewById(R.id.btnCalculateOrders);
        btnCalculateOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_viewAllOrdersFragment_to_ordersSummaryFragment);
            }
        });

        Button btnCalculateTypes = view.findViewById(R.id.btnCalculateTypes);
        btnCalculateTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_viewAllOrdersFragment_to_incomeSummaryFragment);
            }
        });
    }

    private ArrayList<Ordered> fetchDataFromDatabase() {
        // Create an instance of your database helper
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext());

        // Get the orders for the current customer
        ArrayList<Ordered> retrievedOrders = dbHelper.getAllOrdersWithCustomerNames();

        // Log the number of orders retrieved
        Log.d("ViewAllOrdersFragment", "Number of orders: " + retrievedOrders.size());

        // Log details of each order
        for (Ordered order : retrievedOrders) {
            Log.d("ViewAllOrdersFragment", order.toString());
        }
        return retrievedOrders;
    }

}
