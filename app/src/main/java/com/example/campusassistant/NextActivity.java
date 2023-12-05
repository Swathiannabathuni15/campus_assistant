package com.example.campusassistant;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class NextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Get the user's information from the previous activity
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");

        // Display the user's information
       // TextView textView = findViewById(R.id.textView);
       // textView.setText("Successfully Registered" + name + "!");

        // TODO: Add additional functionality to the next activity
    }
}
