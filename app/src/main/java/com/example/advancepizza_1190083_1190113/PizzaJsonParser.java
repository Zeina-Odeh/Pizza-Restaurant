package com.example.advancepizza_1190083_1190113;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PizzaJsonParser {
    public static List<Pizza> getPizzasFromJson(String json) {
        List<Pizza> pizzas = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Pizza pizza = new Pizza();
                pizza.setId(jsonObject.getInt("ID"));
                pizza.setType(jsonObject.getString("TYPE"));
                pizza.setCategory(jsonObject.getString("CATEGORY"));
                pizza.setPrice(jsonObject.getDouble("PRICE"));
            //    pizza.setOffer(jsonObject.getDouble("OFFER"));
                pizzas.add(pizza);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return pizzas;
    }
}
