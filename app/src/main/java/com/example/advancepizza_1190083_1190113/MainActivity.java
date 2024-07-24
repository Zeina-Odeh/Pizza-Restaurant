package com.example.advancepizza_1190083_1190113;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button getStartedButton;
    private TextView overlayText;
    private DataBaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getStartedButton = findViewById(R.id.get_started);
        overlayText = findViewById(R.id.overlayText);

        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Execute the AsyncTask to fetch data
                ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
                connectionAsyncTask.execute("https://androidproject.free.beeceptor.com/pizza1");
            }
        });

        dbHelper = new DataBaseHelper(MainActivity.this);

        String hashedPassword = PasswordHasher.hashPassword("ahmad123@");
        Admin admin = new Admin("ahmadodeh@gmail.com", hashedPassword, "Ahmad", "Odeh", "Male", "0599543321", null);
        admin.setUserType(1);

        boolean isAdminInserted = dbHelper.insertAdmin(admin);
        Log.d("MainActivity", "Admin insertion status: " + isAdminInserted);

        Admin fetchedAdmin = dbHelper.getAdminByEmail("AhmadOdeh@gmail.com");
        Log.d("MainActivity", "Fetched Admin: " + fetchedAdmin);

        dbHelper = new DataBaseHelper(this);
        dbHelper.printAllSpecialOffers();
    }

    public void setButtonText(String text) {
        getStartedButton.setText(text);
    }

    public void setOverlayText(String text) {
        overlayText.setText(text);
    }

    public void handlePizzaList(List<Pizza> pizzas) {
        // Handle the list of pizzas as needed, for example, fill them into an ArrayList
        if (pizzas != null) {

            DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this);

            for (Pizza pizza : pizzas) {
                boolean isInserted = dbHelper.insertPizza(pizza);

                if (isInserted) {
                    // Pizza inserted successfully
                    Log.d("Database", "Pizza inserted successfully");
                } else {
                    // Pizza already exists in the database
                    Log.d("Database", "Pizza already exists in the database");
                }
            }
        }
    }
}
