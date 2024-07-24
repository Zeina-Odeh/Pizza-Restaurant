package com.example.advancepizza_1190083_1190113.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.advancepizza_1190083_1190113.R;
import com.example.advancepizza_1190083_1190113.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize HomeViewModel
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Set up views
        ImageView imageView = binding.imageView2;
        TextView textView = binding.textHome;

        // Observe LiveData from HomeViewModel
        homeViewModel.getText().observe(getViewLifecycleOwner(), text -> {
            textView.setText(text);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
