package com.example.campusassistant;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RegistrationPage extends AppCompatActivity {

    private EditText nameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText rollNumberEditText;
    private EditText emailEditText;
    private EditText mobileNumberEditText;
    private Button otpButton;
    private EditText otpEditText;
    private Button verifyOtpButton;
    private String generatedOtp;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private static final String TAG = "PhoneNumberVerification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Initialize UI elements
        nameEditText = findViewById(R.id.name);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        rollNumberEditText = findViewById(R.id.Roll_number);
        emailEditText = findViewById(R.id.email);
        mobileNumberEditText = findViewById(R.id.mobileno);
        otpButton = findViewById(R.id.otp_btn);
        otpEditText = findViewById(R.id.otpEditText);
        verifyOtpButton = findViewById(R.id.verifyOtpButton);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();

        // Initialize PhoneAuthProvider callbacks
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };

        // Set onClickListener for the OTP button
        otpButton.setOnClickListener(view -> {
            String mobileNumber = mobileNumberEditText.getText().toString();
            if (isValidMobileNumber(mobileNumber)) {
                sendOtp(mobileNumber);
            } else {
                Toast.makeText(RegistrationPage.this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
            }
        });

        // Set onClickListener for the Verify OTP button
        verifyOtpButton.setOnClickListener(view -> {
            String otp = otpEditText.getText().toString();
            if (!otp.isEmpty()) {
                verifyOtp(otp);
            } else {
                Toast.makeText(RegistrationPage.this, "Enter OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickOtpButton(View view) {
        // Start the registration process
        startRegistration();
    }

    private void startRegistration() {
        // Get user input
        String name = nameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        String rollNumber = rollNumberEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String mobileNumber = mobileNumberEditText.getText().toString();

        // Validate user input
        if (name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                rollNumber.isEmpty() || email.isEmpty() || mobileNumber.isEmpty()) {
            Toast.makeText(RegistrationPage.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proceed with phone number verification
        sendOtp(mobileNumber);
    }

    // Validate the mobile number
    private boolean isValidMobileNumber(String mobileNumber) {
        // Check if the mobile number is null or empty
        return mobileNumber != null && !mobileNumber.isEmpty() && mobileNumber.length() == 10 && mobileNumber.matches("\\d+");
    }

    // Validate the email address
    private boolean isValidEmailAddress(String emailAddress) {
        // Check if the email address is null or empty
        return emailAddress != null && !emailAddress.isEmpty() && emailAddress.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}");
    }

    // Method to generate and send OTP
    private void sendOtp(String mobileNumber) {
        // Validate the mobile number
        if (!isValidMobileNumber(mobileNumber)) {
            Toast.makeText(RegistrationPage.this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate a random OTP
        generatedOtp = generateOtp();

        // Get the SMS manager
        SmsManager smsManager = SmsManager.getDefault();

        // Send an SMS message to the user's phone number
        smsManager.sendTextMessage(mobileNumber, null, getString(R.string.otp_message, generatedOtp), null, null);

        // Start phone number verification
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(mobileNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // Method to generate a random OTP
    private String generateOtp() {
        Random random = new SecureRandom();
        int otpValue = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otpValue);
    }

    // Method to verify the entered OTP
    private void verifyOtp(String enteredOtp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, enteredOtp);
        signInWithPhoneAuthCredential(credential);
    }

    // Method to sign in
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Sign in success
                registerUser();
            } else {
                // If sign in fails, display a message to the user.
                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(getApplicationContext(), "Invalid verification code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to register the user
    private void registerUser() {
        // Create a new document in the database
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = database.child("users").push();

        // Set the document's fields to the user's information
        userRef.child("name").setValue(nameEditText.getText().toString());
        userRef.child("password").setValue(passwordEditText.getText().toString());
        userRef.child("confirmPassword").setValue(confirmPasswordEditText.getText().toString());
        userRef.child("rollNumber").setValue(rollNumberEditText.getText().toString());
        userRef.child("email").setValue(emailEditText.getText().toString());
        userRef.child("mobileNumber").setValue(mobileNumberEditText.getText().toString());

        // Save the document to the database
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // The document has been saved to the database.
                // Redirect the user to the next activity
                Intent intent = new Intent(RegistrationPage.this, NextActivity.class);
                // Pass user data to the next activity
                intent.putExtra("name", nameEditText.getText().toString());
                intent.putExtra("email", emailEditText.getText().toString());
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // An error occurred while saving the document to the database.
                // Handle the error.
            }
        });
    }
}
