package com.example.advancepizza_1190083_1190113;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import java.util.Map;

public class IncomeSummaryViewModel extends AndroidViewModel {

    private MutableLiveData<Map<String, Double>> incomeSummary;
    private DataBaseHelper dbHelper;

    public IncomeSummaryViewModel(@NonNull Application application) {
        super(application);
        dbHelper = new DataBaseHelper(application.getApplicationContext());
    }

    public LiveData<Map<String, Double>> getIncomeSummary() {
        if (incomeSummary == null) {
            incomeSummary = new MutableLiveData<>();
            loadIncomeSummary();
        }
        return incomeSummary;
    }

    private void loadIncomeSummary() {
        // Load data asynchronously from database
        Map<String, Double> incomeSummaryMap = dbHelper.getTotalIncomeByPizzaType();
        incomeSummary.setValue(incomeSummaryMap);
    }
}
