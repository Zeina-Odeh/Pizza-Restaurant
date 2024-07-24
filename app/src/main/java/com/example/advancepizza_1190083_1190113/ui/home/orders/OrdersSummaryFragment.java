package com.example.advancepizza_1190083_1190113.ui.home.orders;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.advancepizza_1190083_1190113.DataBaseHelper;
import com.example.advancepizza_1190083_1190113.R;

import java.util.Map;

public class OrdersSummaryFragment extends Fragment {

    private TextView textViewOrderDetails;
    private DataBaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_orders_summary, container, false);

        textViewOrderDetails = view.findViewById(R.id.textViewOrderDetails);
        dbHelper = new DataBaseHelper(requireContext());

        displayOrdersSummary();

        return view;
    }

    private void displayOrdersSummary() {
        Map<String, Integer> ordersCountMap = dbHelper.getOrdersCountByPizzaType();

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : ordersCountMap.entrySet()) {
            String pizzaType = entry.getKey();
            int orderCount = entry.getValue();
            stringBuilder.append("* ").append(pizzaType).append(" ").append("has").append(":  ").append(orderCount).append(" order/s\n");
        }

        textViewOrderDetails.setText(stringBuilder.toString());
    }
}

