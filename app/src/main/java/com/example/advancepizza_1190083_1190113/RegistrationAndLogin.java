package com.example.advancepizza_1190083_1190113;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationAndLogin extends AppCompatActivity {
    private CheckBox rememberMeCheckBox;
    private EditText emailEditText;
    private EditText editText_Password;
    private Button loginButton;
    private Button signUpButton;
    SharedPreferences sharedPreferences;

    DataBaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_and_login);
        databaseHelper = new DataBaseHelper(RegistrationAndLogin.this);

        loginButton = findViewById(R.id.signInButton);
        rememberMeCheckBox = findViewById(R.id.checkBox_rememberMe);
        emailEditText = findViewById(R.id.emailInput);
        editText_Password = findViewById(R.id.passwordInput);
        signUpButton = findViewById(R.id.signUpButton);


        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        // Load email from shared preferences
        String savedEmail = sharedPreferences.getString("savedEmail", "");

        emailEditText.setText(savedEmail);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper db = new DataBaseHelper(RegistrationAndLogin.this);

                String email = emailEditText.getText().toString().toLowerCase();
                String password = editText_Password.getText().toString();
                String hashedPassword = PasswordHasher.hashPassword(password);

                Log.d("Login", "Attempting login with email: " + email + " and hashedPassword: " + hashedPassword);

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegistrationAndLogin.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Admin admin = databaseHelper.getAdminByEmail(email);
                Log.d("Login", "Fetched Admin from DB: " + admin);

                // Get user's type trying to login
                int userType = db.validateUser(email, hashedPassword);
                if (userType == -1) {
                    Toast.makeText(RegistrationAndLogin.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                    return;
                }

                // If "remember me" is checked, save the email
                if (rememberMeCheckBox.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("savedEmail", email);
                    editor.apply();
                }

                //TODO: Admin page left
                Intent intent;
                switch (userType) {
                    case 1: // Admin
                        intent = new Intent(RegistrationAndLogin.this, AdminHomeActivity.class);
                        intent.putExtra("email", email);
                        finish();
                        break;
                    case 2: // Customer
                        intent = new Intent(RegistrationAndLogin.this, CustomerHomeActivity.class);
                        intent.putExtra("email", email);
                        finish();
                        break;
                    default:
                        Toast.makeText(RegistrationAndLogin.this, "Error! Unknown user type.", Toast.LENGTH_LONG).show();
                        return;
                }

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear all the activities above in the stack
                startActivity(intent);
                finish();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationAndLogin.this, ChooseRole.class);
                startActivity(intent);
            }
        });
    }

}
