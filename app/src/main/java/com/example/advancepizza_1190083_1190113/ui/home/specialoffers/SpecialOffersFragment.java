package com.example.advancepizza_1190083_1190113.ui.home.specialoffers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advancepizza_1190083_1190113.DataBaseHelper;
import com.example.advancepizza_1190083_1190113.R;
import com.example.advancepizza_1190083_1190113.SpecialOffer;

import java.util.List;

public class SpecialOffersFragment extends Fragment {

    private RecyclerView recyclerViewSpecialOffers;
    private SpecialOffersAdapter specialOffersAdapter;
    private DataBaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        recyclerViewSpecialOffers = view.findViewById(R.id.recyclerViewSpecialOffers);
        recyclerViewSpecialOffers.setLayoutManager(new LinearLayoutManager(requireContext()));
        dbHelper = new DataBaseHelper(requireContext());

        loadSpecialOffers();

        return view;
    }

    private void loadSpecialOffers() {
        List<SpecialOffer> specialOffers = dbHelper.getSpecialOffers();
        specialOffersAdapter = new SpecialOffersAdapter(specialOffers);
        recyclerViewSpecialOffers.setAdapter(specialOffersAdapter);
    }
}
