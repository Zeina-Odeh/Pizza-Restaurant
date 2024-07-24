package com.example.advancepizza_1190083_1190113;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.advancepizza_1190083_1190113.databinding.ActivityAdminHomeBinding;
import com.google.android.material.navigation.NavigationView;

public class AdminHomeActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityAdminHomeBinding binding;
    private SharedViewModel sharedViewModel;
    private DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarAdminHome.toolbar);

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        db = new DataBaseHelper(this);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navViewAdmin;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile_admin)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_admin_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Intent intent = getIntent();
        if (intent != null) {
            String email = intent.getStringExtra("email");
            Log.d("Email", "Received email: " + email);
            if (email == null) {
                Log.e("AdminHomeActivity", "Email is null");
            } else {
                sharedViewModel.setUserEmail(email);
                String username = db.getUsername(email);
                Log.d("Username", "Received username: " + username);
                byte[] photo = db.getPhoto(email);

                updateNavHeader(email, username, photo);
            }
        }
    }


    public void updateNavHeader(String emailAddress, String username, byte[] photo) {
        NavigationView navigationView = binding.navViewAdmin;
        View headerView = navigationView.getHeaderView(0);

        TextView usernameTextView = headerView.findViewById(R.id.nav_username);
        TextView emailTextView = headerView.findViewById(R.id.nav_email);
        ImageView profilePicture = headerView.findViewById(R.id.nav_imageView);

        if (photo != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            profilePicture.setImageBitmap(bitmap);
        }

        if (usernameTextView != null) {
            usernameTextView.setText(username);
        } else {
            Log.e("UpdateNavHeader", "Username TextView is null");
        }

        if (emailTextView != null) {
            emailTextView.setText(emailAddress);
        } else {
            Log.e("UpdateNavHeader", "Email TextView is null");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_admin_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}
