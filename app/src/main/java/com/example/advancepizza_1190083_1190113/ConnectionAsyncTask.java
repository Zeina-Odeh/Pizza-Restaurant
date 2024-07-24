package com.example.advancepizza_1190083_1190113;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String, String> {
    Activity activity;

    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        ((MainActivity) activity).setButtonText("connecting");
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        // Perform the HTTP request and return the JSON data as a string
        return HttpManager.getData(params[0]);
    }

    @Override
    protected void onPostExecute(String jsonData) {
        if (jsonData != null) {
            // Parse the JSON data into a list of pizzas
            List<Pizza> pizzas = PizzaJsonParser.getPizzasFromJson(jsonData);

            if (pizzas != null) {
                ((MainActivity) activity).setButtonText("Connected");
                ((MainActivity) activity).handlePizzaList(pizzas);

                Intent intent = new Intent(activity, RegistrationAndLogin.class);

                activity.startActivity(intent);

            } else {
                ((MainActivity) activity).setButtonText("Parsing Error");
                Toast.makeText(((MainActivity) activity), "Error parsing data", Toast.LENGTH_SHORT).show();
            }
        } else {
            ((MainActivity) activity).setButtonText("Connection Failed");
            Toast.makeText(((MainActivity) activity), "Error connecting to the server", Toast.LENGTH_SHORT).show();
        }
    }
}
