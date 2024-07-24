package com.example.advancepizza_1190083_1190113.ui.home.specialoffers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advancepizza_1190083_1190113.R;
import com.example.advancepizza_1190083_1190113.SpecialOffer;

import java.util.List;

public class SpecialOffersAdapter extends RecyclerView.Adapter<SpecialOffersAdapter.SpecialOfferViewHolder> {
    private final List<SpecialOffer> specialOffers;

    public SpecialOffersAdapter(List<SpecialOffer> specialOffers) {
        this.specialOffers = specialOffers;
    }

    @NonNull
    @Override
    public SpecialOfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragmnet_special_offer_card, parent, false);
        return new SpecialOfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialOfferViewHolder holder, int position) {
        SpecialOffer specialOffer = specialOffers.get(position);
        holder.bind(specialOffer);
    }

    @Override
    public int getItemCount() {
        return specialOffers.size();
    }

    static class SpecialOfferViewHolder extends RecyclerView.ViewHolder {
        private final TextView pizzaTypeTextView;
        private final TextView pizzaSizeTextView;
        private final TextView offerPeriodTextView;
        private final TextView totalPriceTextView;

        public SpecialOfferViewHolder(@NonNull View itemView) {
            super(itemView);
            pizzaTypeTextView = itemView.findViewById(R.id.pizza_type);
            pizzaSizeTextView = itemView.findViewById(R.id.pizza_size);
            offerPeriodTextView = itemView.findViewById(R.id.offer_period);
            totalPriceTextView = itemView.findViewById(R.id.pizza_price);

        }

        public void bind(SpecialOffer specialOffer) {
            pizzaTypeTextView.setText(specialOffer.getPizzaType());
            pizzaSizeTextView.setText(specialOffer.getPizzaSize());
            offerPeriodTextView.setText(specialOffer.getOfferPeriod());
            totalPriceTextView.setText("Offer Price:" + "$" + specialOffer.getTotalPrice());
        }
    }

}