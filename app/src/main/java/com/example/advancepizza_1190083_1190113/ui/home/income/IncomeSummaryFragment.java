package com.example.advancepizza_1190083_1190113.ui.home.income;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.advancepizza_1190083_1190113.IncomeSummaryViewModel;
import com.example.advancepizza_1190083_1190113.R;

import java.util.Map;

public class IncomeSummaryFragment extends Fragment {

    private TextView textViewIncomeDetails;
    private IncomeSummaryViewModel incomeSummaryViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_income_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewIncomeDetails = view.findViewById(R.id.textViewIncomeDetails);

        incomeSummaryViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(IncomeSummaryViewModel.class);

        incomeSummaryViewModel.getIncomeSummary().observe(getViewLifecycleOwner(), this::displayIncomeSummary);
    }

    private void displayIncomeSummary(Map<String, Double> incomeSummaryMap) {
        StringBuilder stringBuilder = new StringBuilder();
        double totalIncome = 0.0;

        for (Map.Entry<String, Double> entry : incomeSummaryMap.entrySet()) {
            String pizzaType = entry.getKey();
            double income = entry.getValue();
            totalIncome += income;
            stringBuilder.append("* ").append("Total Income for ").append(pizzaType).append(" ").append("is").append(": $").append(income).append("\n");
        }

        stringBuilder.append("\nTotal Income for All Types: $").append(totalIncome);
        textViewIncomeDetails.setText(stringBuilder.toString());
    }
}
