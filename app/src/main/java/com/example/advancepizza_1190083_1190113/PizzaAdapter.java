package com.example.advancepizza_1190083_1190113;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.ViewHolder> {
    private ArrayList<Pizza> pizzas;
    private String customerEmail;

    public PizzaAdapter(ArrayList<Pizza> pizzas, String customerEmail) {
        this.pizzas = pizzas;
        this.customerEmail = customerEmail;
    }

    public void updateData(ArrayList<Pizza> updatedPizzas) {
        this.pizzas.clear();
        this.pizzas.addAll(updatedPizzas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_pizza_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pizza pizza = pizzas.get(position);
        holder.bind(pizza);
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView pizzaType, pizzaCategory, pizzaPrice, pizzaOffer;
        private Button orderButton;
        private ImageView favoriteButton;

        public ViewHolder(@NonNull View view) {
            super(view);

            // Initialize UI elements
            pizzaType = view.findViewById(R.id.pizza_type);
            pizzaCategory = view.findViewById(R.id.pizza_category);
            pizzaPrice = view.findViewById(R.id.pizza_price);
            orderButton = view.findViewById(R.id.orderButton);
            favoriteButton = view.findViewById(R.id.favorite);

            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    DataBaseHelper dbHelper = new DataBaseHelper(itemView.getContext());
                    if (position != RecyclerView.NO_POSITION) {
                        Pizza clickedPizza = pizzas.get(position);
                        if (dbHelper.isPizzaOrderedBy(clickedPizza.getType(), customerEmail)) {
                            boolean deleted = dbHelper.deleteOrder(clickedPizza.getType(), customerEmail);
                            if (deleted) {
                                Toast.makeText(itemView.getContext(), "Order Cancelled Successfully", Toast.LENGTH_SHORT).show();
                                orderButton.setText("Order"); // Set the order button text to "Order"
                                orderButton.setEnabled(true);
                            }
                        } else {
                                showOrderPopup(clickedPizza, orderButton);

                        }
                    }
                }
            });

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleFavoriteState(getAdapterPosition());
                }
            });
        }

        public void bind(Pizza pizza) {
            DataBaseHelper dbHelper = new DataBaseHelper(itemView.getContext());

            // Bind data to UI elements
            pizzaType.setText(pizza.getType());
            pizzaCategory.setText(pizza.getCategory());
            pizzaPrice.setText(String.valueOf(pizza.getPrice()));

            if (dbHelper.isPizzaOrderedBy(pizza.getType(), customerEmail)) {
                orderButton.setText("Cancel Order");
                orderButton.setEnabled(true);

            } else {
                orderButton.setText("Order");
                orderButton.setEnabled(true);
            }

            // Set the initial state of the favorite button based on SharedPreferences
            boolean isPizzaInFavorites = dbHelper.isPizzaInFavorites(pizza.getId(), customerEmail);
            if (isPizzaInFavorites) {
                favoriteButton.setImageResource(R.drawable.like);
            } else {
                favoriteButton.setImageResource(R.drawable.unlike);
            }
        }

        private String getCurrentDate() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = Calendar.getInstance().getTime();
            return dateFormat.format(currentDate);
        }

        // Helper method to get the current time
        private String getCurrentTime() {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Date currentTime = Calendar.getInstance().getTime();
            return timeFormat.format(currentTime);
        }

        private void showOrderPopup(Pizza pizza, Button orderButton) {
            Context context = itemView.getContext();
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_order);

            TextView tvPizzaName = dialog.findViewById(R.id.pizza_name);
            Spinner spinnerSize = dialog.findViewById(R.id.spinner_size);
            TextView tvBasePrice = dialog.findViewById(R.id.base_price);
            TextView tvFinalPrice = dialog.findViewById(R.id.final_price);
            EditText etQuantity = dialog.findViewById(R.id.order_quantity);
            Button btnSubmitOrder = dialog.findViewById(R.id.btn_submit_order);

            tvPizzaName.setText(pizza.getType());
            double basePrice = pizza.getPrice();
            tvBasePrice.setText("Price per Pizza: $" + basePrice); // Set base price

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.pizza_sizes, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSize.setAdapter(adapter);

            spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedSize = parent.getItemAtPosition(position).toString();
                    double price = basePrice;
                    switch (selectedSize) {
                        case "Medium":
                            price = basePrice + 10;
                            break;
                        case "Large":
                            price = basePrice + 15;
                            break;
                        case "Small":
                        default:
                            price = basePrice;
                            break;
                    }

                    tvBasePrice.setText("Price per Pizza: $" + price); // Update base price

                    String quantityText = etQuantity.getText().toString();
                    int quantity = quantityText.isEmpty() ? 0 : Integer.parseInt(quantityText);
                    tvFinalPrice.setText("Final Price: $" + (price * quantity));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            btnSubmitOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Pizza clickedPizza = pizzas.get(position);
                    String quantityText = etQuantity.getText().toString();
                    if (!quantityText.isEmpty()) {
                        int quantity = Integer.parseInt(quantityText);
                        String selectedSize = spinnerSize.getSelectedItem().toString();
                        // Get final price
                        String finalPriceString = tvFinalPrice.getText().toString().replace("Final Price: $", "");
                        double finalPrice = Double.parseDouble(finalPriceString);
                        // Get base price
                        String basePriceString = tvBasePrice.getText().toString().replace("Price per Pizza: $", "");
                        double initialPrice = Double.parseDouble(basePriceString);

                        // Save the order to the database
                        clickedPizza.setOrderedBy(customerEmail);
                        insertOrderIntoDatabase(clickedPizza.getType(), selectedSize, quantity, initialPrice, finalPrice);
                        orderButton.setText("Cancel Order"); // Update the button text to "Cancel Order"
                        orderButton.setEnabled(true);
                        dialog.dismiss();
                    }
                    } else {
                        // Show an error message or handle the empty quantity case
                        Toast.makeText(context, "Please enter a quantity", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            dialog.show();


        // Adjust dialog width and height
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);
        }


        private void insertOrderIntoDatabase(String pizzaType, String size, int quantity, double basePrice, double finalPrice) {
            // Get the current date and time
            String currentDate = getCurrentDate();
            String currentTime = getCurrentTime();

            // Create the order object with the final price
            Ordered order = new Ordered(pizzaType, customerEmail, currentDate, currentTime, size, quantity, basePrice, finalPrice);

            DataBaseHelper dbHelper = new DataBaseHelper(itemView.getContext());

            boolean inserted = dbHelper.insertOrder(order);

            // Handle the result if needed
            if (inserted) {
                Toast.makeText(itemView.getContext(), "Order added successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(itemView.getContext(), "Error adding order, try again", Toast.LENGTH_SHORT).show();
            }
        }


        private void toggleFavoriteState(int position) {
            Pizza clickedPizza = pizzas.get(position);

            // After the transition, perform database operations
            performDatabaseOperation(clickedPizza);
        }

        private void performDatabaseOperation(Pizza pizza) {

            DataBaseHelper dbHelper = new DataBaseHelper(itemView.getContext());

            // Check if the pizza is already in favorites
            boolean isPizzaInFavorites = dbHelper.isPizzaInFavorites(pizza.getId(), customerEmail);

            // If the pizza is not in favorites, add it; otherwise, remove it
            if (!isPizzaInFavorites) {
                Favorites favorites = new Favorites(pizza.getId(), customerEmail);
                boolean inserted = dbHelper.insertFavorites(favorites);

                if (inserted) {
                    Toast.makeText(itemView.getContext(), "Pizza added to favorites", Toast.LENGTH_SHORT).show();
                    favoriteButton.setImageResource(R.drawable.like);
                } else {
                    Toast.makeText(itemView.getContext(), "Error adding pizza to favorites", Toast.LENGTH_SHORT).show();
                }
            } else {
                boolean deleted = dbHelper.deleteFavorites(pizza.getId(), customerEmail);

                if (deleted) {
                    // Pizza removed from favorites successfully
                    Toast.makeText(itemView.getContext(), "Pizza removed from favorites", Toast.LENGTH_SHORT).show();
                    favoriteButton.setImageResource(R.drawable.unlike);

                } else {
                    Toast.makeText(itemView.getContext(), "Error removing pizza from favorites", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
