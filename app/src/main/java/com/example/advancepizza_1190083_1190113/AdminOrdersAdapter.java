package com.example.advancepizza_1190083_1190113;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminOrdersAdapter extends RecyclerView.Adapter<AdminOrdersAdapter.AdminOrdersViewHolder> {

    private Context context;
    private List<Ordered> reservedOrders;

    public AdminOrdersAdapter(Context context, List<Ordered> reservedOrders) {
        this.context = context;
        this.reservedOrders = reservedOrders;
    }

    @NonNull
    @Override
    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_customer_card, parent, false);
        return new AdminOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, int position) {
        Ordered order = reservedOrders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return reservedOrders.size();
    }

    public class AdminOrdersViewHolder extends RecyclerView.ViewHolder {

        private TextView pizzaType, orderDate, orderTime, size, quantity, basePrice, finalPrice, customerName;

        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            pizzaType = itemView.findViewById(R.id.order_type);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderTime = itemView.findViewById(R.id.orderTime);
            size = itemView.findViewById(R.id.orderSize);
            quantity = itemView.findViewById(R.id.orderQuantity);
            basePrice = itemView.findViewById(R.id.orderPrice);
            finalPrice = itemView.findViewById(R.id.orderOffer);
            customerName = itemView.findViewById(R.id.customer_name);
        }

        public void bind(Ordered order) {
            pizzaType.setText(order.getPizzaType());
            orderDate.setText(order.getOrderDate());
            orderTime.setText(order.getOrderTime());
            size.setText("Size:" +  order.getSize());
            quantity.setText("Quantity: " + String.valueOf(order.getQuantity()));
            basePrice.setText("Price for one Pizza: " + String.valueOf(order.getBasePrice()));
            finalPrice.setText("Final Price: " + String.valueOf(order.getFinalPrice()));
            customerName.setText("Ordered By: " + order.getCustomerName());
        }
    }
}
