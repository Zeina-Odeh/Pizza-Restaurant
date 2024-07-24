package com.example.advancepizza_1190083_1190113;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class OrderedAdapter extends RecyclerView.Adapter<OrderedAdapter.ViewHolder> {

    private ArrayList<Ordered> orderedList;
    private Context context;

    public OrderedAdapter(ArrayList<Ordered> orderedList, Context context) {
        this.orderedList = orderedList;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ordered ordered = orderedList.get(position);
        holder.bind(ordered);
    }

    @Override
    public int getItemCount() {
        return orderedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView pizzaType, orderDate, orderTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pizzaType = itemView.findViewById(R.id.order_type);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderTime = itemView.findViewById(R.id.orderTime);

            itemView.setOnClickListener(this);
        }

        public void bind(Ordered ordered) {
            pizzaType.setText(ordered.getPizzaType());
            orderDate.setText(ordered.getOrderDate());
            orderTime.setText(ordered.getOrderTime());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Ordered ordered = orderedList.get(position);
                showOrderDetailDialog(ordered);
            }
        }

        private void showOrderDetailDialog(Ordered ordered) {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.activity_order_detail);

            TextView tvPizzaType = dialog.findViewById(R.id.tvPizzaType);
            TextView tvSize = dialog.findViewById(R.id.tvSize);
            TextView tvQuantity = dialog.findViewById(R.id.tvQuantity);
            TextView tvBasePrice = dialog.findViewById(R.id.tvBasePrice);
            TextView tvFinalPrice = dialog.findViewById(R.id.tvFinalPrice);


            tvPizzaType.setText(ordered.getPizzaType());
            tvSize.setText(ordered.getSize());
            tvQuantity.setText(String.valueOf(ordered.getQuantity()));
            tvBasePrice.setText(String.valueOf(ordered.getBasePrice()));
            tvFinalPrice.setText(String.valueOf(ordered.getFinalPrice()));

            dialog.show();
        }
    }
}
