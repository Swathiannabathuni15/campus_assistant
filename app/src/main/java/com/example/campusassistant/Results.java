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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Results extends AppCompatActivity {

    private TextView hallTicketLabel;
    private TextView hallTicketValue;
    private TextView nameLabel;
    private TextView nameValue;
    private EditText rollNumberEditText;
    private Button submitButton;
    private TableLayout marksTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results2);

        // Find views by ID
        Spinner branchSpinner = findViewById(R.id.branchSpinner);
        Spinner yearSpinner = findViewById(R.id.yearSpinner);
        Spinner semesterSpinner = findViewById(R.id.semesterSpinner);
        rollNumberEditText = findViewById(R.id.rollNumberEditText);
        submitButton = findViewById(R.id.submitButton);
        marksTable = findViewById(R.id.marksTable);

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

                    // Update TextViews with data
                    hallTicketValue.setText(rollNumber);  // Use the entered Hall Ticket No. as the value
                    // Example data (replace with your actual data)
                    String name = "John Doe";
                    nameValue.setText(name);

                    // Show Marks and SGPA label and marksTable with fade-in animation
                    fadeIn(findViewById(R.id.marksTable));
                    fadeIn(marksTable);

                    // Populate the table with dynamic data (replace with your actual data)
                    populateTable();
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

    private void populateTable() {
        // Clear existing rows
        marksTable.removeAllViews();

        // Create table header
        createTableRow("Subject Code", "Subject", "Grade", "Credits", true);

        // Example data (replace with your actual data)
        String[] subjectCodes = {"SC001", "SC002", "SC003"};
        String[] subjects = {"Subject 1", "Subject 2", "Subject 3"};
        String[] grades = {"A", "B", "C"};
        String[] credits = {"3", "4", "2"};

        // Populate the table with data
        for (int i = 0; i < subjectCodes.length; i++) {
            createTableRow(subjectCodes[i], subjects[i], grades[i], credits[i], i % 2 == 0);
        }
    }

    private void createTableRow(String subjectCode, String subject, String grade, String credits, boolean isEven) {
        TableRow row = new TableRow(this);
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );

        // Set margin to the row
        params.setMargins(0, 0, 0, 10);
        row.setLayoutParams(params);

        // Set background color based on even or odd row
        int bgColor = isEven ? ContextCompat.getColor(this, android.R.color.white) : ContextCompat.getColor(this, R.color.tableRowColor);
        row.setBackgroundColor(bgColor);


        // Subject Code column
        TextView codeTextView = new TextView(this);
        codeTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        codeTextView.setText(subjectCode);
        codeTextView.setTextSize(16);
        codeTextView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        row.addView(codeTextView);

        // Subject column
        TextView subjectTextView = new TextView(this);
        subjectTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
        subjectTextView.setText(subject);
        subjectTextView.setTextSize(16);
        subjectTextView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        row.addView(subjectTextView);

        // Grade column
        TextView gradeTextView = new TextView(this);
        gradeTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        gradeTextView.setText(grade);
        gradeTextView.setTextSize(16);
        gradeTextView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        row.addView(gradeTextView);

        // Credits column
        TextView creditsTextView = new TextView(this);
        creditsTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        creditsTextView.setText(credits);
        creditsTextView.setTextSize(16);
        creditsTextView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        row.addView(creditsTextView);

        // Add the row to the table
        marksTable.addView(row);
    }
}
