package com.example.advancepizza_1190083_1190113;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseRole extends AppCompatActivity {

    ImageButton adminButton;
    ImageButton customerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);

        adminButton = findViewById(R.id.adminButton);
        customerButton = findViewById(R.id.customerButton);

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ChooseRole.this, SignUP.class);
                intent.putExtra("userType", 1);
                startActivity(intent);
            }
        });

        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ChooseRole.this, SignUP.class);
                intent.putExtra("userType", 2);
                startActivity(intent);
            }
        });
    }
}
