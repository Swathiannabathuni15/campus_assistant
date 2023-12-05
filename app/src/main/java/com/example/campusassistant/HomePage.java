package com.example.campusassistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Find views by their respective IDs
        TextInputEditText searchEditText = findViewById(R.id.searchEditText);
        MaterialButton attendanceButton = findViewById(R.id.attendanceButton);
        MaterialButton resultsButton = findViewById(R.id.resultsButton);
        MaterialButton announcementsButton = findViewById(R.id.announcementsButton);

        // Set click listeners for buttons
        attendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open AttendanceActivity
                Intent intent = new Intent(HomePage.this, Attendance.class);
                startActivity(intent);
            }
        });

        resultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Results button click
            }
        });

        announcementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Announcements button click
            }
        });

        // Add any additional setup code as needed
    }
}
