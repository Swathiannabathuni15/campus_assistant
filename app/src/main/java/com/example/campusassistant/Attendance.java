package com.example.campusassistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class Attendance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // Find buttons by their respective IDs
        MaterialButton subjectWiseAttendanceButton = findViewById(R.id.subjectWiseAttendanceButton);
        MaterialButton semesterWiseAttendanceButton = findViewById(R.id.semesterWiseAttendanceButton);

        // Set click listeners for buttons
        subjectWiseAttendanceButton.setOnClickListener(v -> {
            // Handle Subject Wise Attendance button click
            Intent intent = new Intent(Attendance.this, subjectWiseAttendance.class);
            startActivity(intent);
            // You can also finish() the current activity if you don't want to come back
        });

        semesterWiseAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Semester Wise Attendance button click
                Intent intent = new Intent(Attendance.this, semesterWiseAttendance.class);
                startActivity(intent);
            }
        });
    }
}
