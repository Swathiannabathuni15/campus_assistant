package com.example.campusassistant;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Results extends AppCompatActivity {

    private TextView hallTicketLabel;
    private TextView hallTicketValue;
    private TextView nameLabel;
    private TextView nameValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results2);

        // Find views by ID
        Spinner branchSpinner = findViewById(R.id.branchSpinner);
        Spinner yearSpinner = findViewById(R.id.yearSpinner);
        Spinner semesterSpinner = findViewById(R.id.semesterSpinner);
        EditText rollNumberEditText = findViewById(R.id.rollNumberEditText);
        Button submitButton = findViewById(R.id.submitButton);

        // Initialize TextViews for Hall Ticket number and Name
        hallTicketLabel = findViewById(R.id.hallTicketLabel);
        hallTicketValue = findViewById(R.id.hallTicketValue);
        nameLabel = findViewById(R.id.nameLabel);
        nameValue = findViewById(R.id.nameValue);

        // Set up animations
        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);

        // Set up branch spinner
        ArrayAdapter<CharSequence> branchAdapter = ArrayAdapter.createFromResource(
                this, R.array.branch_options, android.R.layout.simple_spinner_item);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchSpinner.setAdapter(branchAdapter);

        // Set up year spinner
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(
                this, R.array.year_options, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        // Set up semester spinner
        ArrayAdapter<CharSequence> semesterAdapter = ArrayAdapter.createFromResource(
                this, R.array.semester_options, android.R.layout.simple_spinner_item);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setAdapter(semesterAdapter);

        // Set up onItemSelectedListener for spinners
        AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle spinner item selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        };

        branchSpinner.setOnItemSelectedListener(spinnerListener);
        yearSpinner.setOnItemSelectedListener(spinnerListener);
        semesterSpinner.setOnItemSelectedListener(spinnerListener);

        // Set up onClickListener for submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle submit button click
                String rollNumber = rollNumberEditText.getText().toString();

                // Perform validation or other actions as needed

                // Example: If roll number is empty, apply shake animation
                if (rollNumber.isEmpty()) {
                    rollNumberEditText.startAnimation(animShake);
                } else {
                    // Show Hall Ticket number and Name with fade-in animation
                    fadeIn(hallTicketLabel);
                    fadeIn(hallTicketValue);
                    fadeIn(nameLabel);
                    fadeIn(nameValue);

                    // Example data (replace with your actual data)
                    String hallTicketNumber = "HT123456";
                    String name = "John Doe";

                    // Update TextViews with data
                    hallTicketValue.setText(hallTicketNumber);
                    nameValue.setText(name);
                }
            }
        });
    }

    private void fadeIn(View view) {
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(1000); // Adjust the duration as needed
        view.startAnimation(fadeIn);
        view.setVisibility(View.VISIBLE);
    }
}
