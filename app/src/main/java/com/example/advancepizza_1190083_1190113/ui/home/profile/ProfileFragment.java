package com.example.advancepizza_1190083_1190113.ui.home.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.advancepizza_1190083_1190113.DataBaseHelper;
import com.example.advancepizza_1190083_1190113.EditProfile;
import com.example.advancepizza_1190083_1190113.R;
import com.example.advancepizza_1190083_1190113.SharedViewModel;
import com.example.advancepizza_1190083_1190113.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private DataBaseHelper db;
    private SharedViewModel sharedViewModel;
    private ImageView profilePicture;

    TextView emailTxt;
    TextView firstNameTxt;
    TextView lastNameTxt;
    TextView genderTxt;
    TextView phoneTxt;
    TextView roleTxt;
    Button editProfileButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DataBaseHelper(requireContext());

        emailTxt = root.findViewById(R.id.emailTxt);
        firstNameTxt = root.findViewById(R.id.firstNameTxt);
        lastNameTxt = root.findViewById(R.id.lastNameTxt);
        genderTxt = root.findViewById(R.id.genderTxt);
        phoneTxt = root.findViewById(R.id.phoneTxt);
        roleTxt = root.findViewById(R.id.roleTxt);
        editProfileButton = root.findViewById(R.id.buttonEditProfile);
        profilePicture = root.findViewById(R.id.iv_inst_pic);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getUserEmailLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String emailReceived) {
                Log.d("ProfileFragment", "Received email: " + emailReceived);
                if (emailReceived != null) {
                    displayProfileData(emailReceived);
                } else {
                    Log.e("ProfileFragment", "Email is null. Cannot display profile data.");
                }
            }
        });

        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfile.class);
            intent.putExtra("email", sharedViewModel.getUserEmail());
            startActivity(intent);
        });

        return root;
    }

    @SuppressLint("Range")
    private void displayProfileData(String email) {
        Cursor cursor = db.displayProfileData(email);
        if (cursor == null) {
            Log.e("ProfileFragment", "Cursor is null. Unable to load profile data.");
            return;
        }

        if (cursor.moveToFirst()) {
            String firstName = cursor.getString(cursor.getColumnIndex("first_name"));
            String lastName = cursor.getString(cursor.getColumnIndex("last_name"));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            int userType = cursor.getInt(cursor.getColumnIndex("user_type"));
            byte[] photo = cursor.getBlob(cursor.getColumnIndex("photo"));

            emailTxt.setText(email);
            firstNameTxt.setText(firstName != null ? firstName : "N/A");
            lastNameTxt.setText(lastName != null ? lastName : "N/A");
            genderTxt.setText(gender != null ? gender : "N/A");
            phoneTxt.setText(phone != null ? phone : "N/A");

            if (photo != null && photo.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                profilePicture.setImageBitmap(bitmap);
            } else {
                profilePicture.setImageResource(R.drawable.baseline_profile); // A default image if no photo exists
            }

            if (userType == 1) {
                roleTxt.setText("Admin");
            } else {
                roleTxt.setText("Customer");
            }

            Log.d("ProfileFragment", "Profile data loaded successfully.");
        } else {
            Log.d("ProfileFragment", "No data found for the provided email.");
        }

        cursor.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
