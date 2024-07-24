package com.example.advancepizza_1190083_1190113.ui.home.addadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.advancepizza_1190083_1190113.SharedViewModel;
import com.example.advancepizza_1190083_1190113.SignUP2;
import com.example.advancepizza_1190083_1190113.databinding.FragmentAddAdminBinding;


public class AddAdminFragment extends Fragment {

    private FragmentAddAdminBinding binding;
    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        // Retrieve email from the shared ViewModel
        String emailReceived = sharedViewModel.getUserEmail();
        Intent intent =new Intent(getActivity(), SignUP2.class);
        intent.putExtra("userType", 1);
        intent.putExtra("nextIntentAdminHome", 1);
        intent.putExtra("AdminEmail", emailReceived);
        startActivity(intent);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}