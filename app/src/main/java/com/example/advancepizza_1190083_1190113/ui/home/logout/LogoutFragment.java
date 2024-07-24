package com.example.advancepizza_1190083_1190113.ui.home.logout;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

import com.example.advancepizza_1190083_1190113.ChooseRole;
import com.example.advancepizza_1190083_1190113.RegistrationAndLogin;
import com.example.advancepizza_1190083_1190113.SignUP;
import com.example.advancepizza_1190083_1190113.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Intent intent = new Intent(getActivity(), RegistrationAndLogin.class);

        // Start the activity
        startActivity(intent);

        // Finish the current activity (if applicable)
        if (getActivity() != null) {
            getActivity().finish();
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
