package com.example.advancepizza_1190083_1190113.ui.home.addspecialoffer;

import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.advancepizza_1190083_1190113.DataBaseHelper;
import com.example.advancepizza_1190083_1190113.R;
import com.example.advancepizza_1190083_1190113.SpecialOffer;
import com.example.advancepizza_1190083_1190113.ui.home.specialoffers.SpecialOfferScheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddSpecialOfferFragment extends Fragment {

    private DataBaseHelper dbHelper;
    private TextView textViewStartDateTime;
    private TextView textViewEndDateTime;

    private Calendar startDateTimeCalendar;
    private Calendar endDateTimeCalendar;
    private TextView editTextTotalPrice;
    private Button buttonAddSpecialOffer;
    private Spinner spinnerPizzaType;
    private Spinner spinnerPizzaSize;
    private EditText editTextOfferDiscount;
    private TextView textViewCalculatedPrice;

    private double basePrice;
    private double calculatedPrice;

    public AddSpecialOfferFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DataBaseHelper(requireContext()); // Initialize the dbHelper

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_special_offer, container, false);

        textViewStartDateTime = view.findViewById(R.id.textViewStartDateTime);
        textViewEndDateTime = view.findViewById(R.id.textViewEndDateTime);
        editTextTotalPrice = view.findViewById(R.id.editTextTotalPrice);
        buttonAddSpecialOffer = view.findViewById(R.id.buttonAddSpecialOffer);
        spinnerPizzaType = view.findViewById(R.id.spinnerPizzaType);
        spinnerPizzaSize = view.findViewById(R.id.spinnerPizzaSize);
        editTextOfferDiscount = view.findViewById(R.id.editTextOfferDiscount);
        textViewCalculatedPrice = view.findViewById(R.id.textViewCalculatedPrice);

        // Ensure editTextOfferDiscount is focused initially
        editTextOfferDiscount.requestFocus();

        // Populate spinners
        populateSpinners();

        spinnerPizzaType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCalculatedPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerPizzaSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCalculatedPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button buttonStartDateTime = view.findViewById(R.id.buttonStartDateTime);
        buttonStartDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDateTimePicker();
            }
        });

        Button buttonEndDateTime = view.findViewById(R.id.buttonEndDateTime);
        buttonEndDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDateTimePicker();
            }
        });

        // Add TextWatcher for editTextOfferDiscount
        editTextOfferDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not used
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Calculate final price after discount
                calculateAndDisplayFinalPrice();
            }
        });

        // Handle click action for Add Special Offer button
        buttonAddSpecialOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSpecialOffer();
            }
        });

        // Call the method to log special offers from the database
        getSpecialOffersFromDatabase();

        return view;
    }

    private void populateSpinners() {
        // Populate spinner with pizza types
        List<String> pizzaTypes = getPizzaTypes();
        ArrayAdapter<String> adapterType = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, pizzaTypes);
        spinnerPizzaType.setAdapter(adapterType);

        // Populate spinner with pizza sizes
        List<String> pizzaSizes = new ArrayList<>();
        pizzaSizes.add("Small");
        pizzaSizes.add("Medium");
        pizzaSizes.add("Large");
        ArrayAdapter<String> adapterSize = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, pizzaSizes);
        spinnerPizzaSize.setAdapter(adapterSize);
    }

    private List<String> getPizzaTypes() {
        List<String> pizzaTypes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT DISTINCT type, price FROM Pizza", null);
        if (cursor != null) {
            int typeIndex = cursor.getColumnIndex("type");
            if (typeIndex != -1 && cursor.moveToFirst()) {
                do {
                    String type = cursor.getString(typeIndex);
                    pizzaTypes.add(type);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return pizzaTypes;
    }

    private double updateCalculatedPrice() {
        String selectedPizzaType = (String) spinnerPizzaType.getSelectedItem();
        String selectedSize = (String) spinnerPizzaSize.getSelectedItem();

        if (selectedPizzaType != null && selectedSize != null) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT price FROM Pizza WHERE type = ?", new String[]{selectedPizzaType});

            if (cursor != null && cursor.moveToFirst()) {
                basePrice = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                cursor.close();
            }

            calculatedPrice = basePrice;
            switch (selectedSize) {
                case "Medium":
                    calculatedPrice += 10;
                    break;
                case "Large":
                    calculatedPrice += 15;
                    break;
                case "Small":
                default:
                    break;
            }

            textViewCalculatedPrice.setText(String.format("Calculated Price: $%.2f", calculatedPrice));
        }
        return calculatedPrice;
    }

    private void showStartDateTimePicker() {
        startDateTimeCalendar = Calendar.getInstance();
        int year = startDateTimeCalendar.get(Calendar.YEAR);
        int month = startDateTimeCalendar.get(Calendar.MONTH);
        int dayOfMonth = startDateTimeCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDateTimeCalendar.set(year, month, dayOfMonth);
                        showStartTimePicker();
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void showStartTimePicker() {
        int hour = startDateTimeCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = startDateTimeCalendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startDateTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        startDateTimeCalendar.set(Calendar.MINUTE, minute);
                        updateStartDateTimeTextView();
                    }
                }, hour, minute, true);

        timePickerDialog.show();
    }

    private void showEndDateTimePicker() {
        endDateTimeCalendar = Calendar.getInstance();
        int year = endDateTimeCalendar.get(Calendar.YEAR);
        int month = endDateTimeCalendar.get(Calendar.MONTH);
        int dayOfMonth = endDateTimeCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDateTimeCalendar.set(year, month, dayOfMonth);
                        showEndTimePicker();
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void showEndTimePicker() {
        int hour = endDateTimeCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = endDateTimeCalendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endDateTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        endDateTimeCalendar.set(Calendar.MINUTE, minute);
                        updateEndDateTimeTextView();
                    }
                }, hour, minute, true);

        timePickerDialog.show();
    }

    private void updateStartDateTimeTextView() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String startDateTimeStr = sdf.format(startDateTimeCalendar.getTime());
        textViewStartDateTime.setText("Start Date and Time: " + startDateTimeStr);
    }

    private void updateEndDateTimeTextView() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String endDateTimeStr = sdf.format(endDateTimeCalendar.getTime());
        textViewEndDateTime.setText("End Date and Time: " + endDateTimeStr);
    }


    private void calculateAndDisplayFinalPrice() {
        String offerDiscountStr = editTextOfferDiscount.getText().toString().trim();

        if (!offerDiscountStr.isEmpty()) {
            try {
                double offerDiscount = Double.parseDouble(offerDiscountStr);
                double finalPrice = calculatedPrice - offerDiscount;

                // Update editTextTotalPrice on the main UI thread
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        editTextTotalPrice.setText(String.format("%.2f", finalPrice));
                    }
                });
            } catch (NumberFormatException e) {
                Log.e("AddSpecialOffer", "Invalid discount format: " + e.getMessage());
            }
        }
    }

    private void addSpecialOffer() {

        String pizzaType = spinnerPizzaType.getSelectedItem().toString();
        String pizzaSize = spinnerPizzaSize.getSelectedItem().toString();
        String startDateTimeStr = textViewStartDateTime.getText().toString().trim();
        String endDateTimeStr = textViewEndDateTime.getText().toString().trim();
        String offerPeriod = startDateTimeStr + " to " + endDateTimeStr;
        String offerDiscountStr = editTextOfferDiscount.getText().toString().trim();

        // Validate input
        if (pizzaType.isEmpty() || startDateTimeStr.equals("Start Date and Time: Not selected") ||
                endDateTimeStr.equals("End Date and Time: Not selected") || offerDiscountStr.isEmpty()) {
            // Show error message if any field is empty
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            double offerDiscount = Double.parseDouble(offerDiscountStr);

            // Calculate the price based on pizza type and size
            double calculatedPrice = updateCalculatedPrice();

            // Calculate final price after discount
            double finalPrice = calculatedPrice - offerDiscount;

            // Update editTextTotalPrice on the main UI thread
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    editTextTotalPrice.setText(String.format("%.2f", finalPrice));
                }
            });

            // Create SpecialOffer object
            SpecialOffer specialOffer = new SpecialOffer(0, pizzaType, pizzaSize, offerPeriod, startDateTimeStr, endDateTimeStr, finalPrice);

            int insertedId = addSpecialOfferToDatabase(specialOffer);

            // Add special offer to the database
            if (insertedId != -1) {
                // Show success message
                Toast.makeText(getContext(), "Special Offer added successfully", Toast.LENGTH_SHORT).show();

                // Schedule offer end using SpecialOfferScheduler with the retrieved id
                SpecialOfferScheduler.scheduleOfferEnd(getContext(), String.valueOf(insertedId), endDateTimeStr);

                // Clear EditText fields after adding special offer
                clearEditTextFields();
            } else {
                // Show failure message
                Toast.makeText(getContext(), "Failed to add Special Offer", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            // Show error message if total price is not a valid number
            Toast.makeText(getContext(), "Please enter a valid total price", Toast.LENGTH_SHORT).show();
        }
    }



    public int addSpecialOfferToDatabase(SpecialOffer specialOffer) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("type", specialOffer.getPizzaType());
        values.put("size", specialOffer.getPizzaSize());
        values.put("offer_period", specialOffer.getOfferPeriod());
        values.put("startDateTime", specialOffer.getStartDateTime());
        values.put("endDateTime", specialOffer.getEndDateTime());
        values.put("total_price", specialOffer.getTotalPrice());

        // Insert the row and retrieve the inserted id
        long insertedId = db.insert("SpecialOffers", null, values);
        db.close();

        // Return true if insertion was successful (insertedId > 0), false otherwise
        return (int) insertedId;
    }


    private void clearEditTextFields() {
        // Clear EditText fields after adding special offer
        textViewCalculatedPrice.setText("Calculated Price: $0.00");
        spinnerPizzaType.setSelection(0);
        spinnerPizzaSize.setSelection(0);
        editTextOfferDiscount.setText("");
        textViewStartDateTime.setText("Start Date and Time: Not selected");
        textViewEndDateTime.setText("End Date and Time: Not selected");
        editTextTotalPrice.setText("");
    }

    private void getSpecialOffersFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("SpecialOffers", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int typeIndex = cursor.getColumnIndex("type");
                int sizeIndex = cursor.getColumnIndex("size");
                int offerPeriodIndex = cursor.getColumnIndex("offer_period");
                int totalPriceIndex = cursor.getColumnIndex("total_price");

                if (typeIndex >= 0 && sizeIndex >= 0 && offerPeriodIndex >= 0 && totalPriceIndex >= 0) {
                    String type = cursor.getString(typeIndex);
                    String size = cursor.getString(sizeIndex);
                    String offerPeriod = cursor.getString(offerPeriodIndex);
                    double totalPrice = cursor.getDouble(totalPriceIndex);

                    // Log special offer details
                    String log = "Type: " + type + ", Size: " + size + ", Offer Period: " + offerPeriod + ", Total Price: " + totalPrice;
                    Log.d("SpecialOffer", log);
                } else {
                    Log.e("Database Error", "One or more columns do not exist in the query result.");
                }
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Log.d("SpecialOffer", "No special offers found in database.");
        }
    }
}
