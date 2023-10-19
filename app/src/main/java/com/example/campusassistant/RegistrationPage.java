package com.example.campusassistant;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class RegistrationPage extends AppCompatActivity {

    private EditText nameEditText;
    private EditText rollNumberEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText mobileNoEditText;
    private EditText otpEditText;
    private Button otpButton;
    private String verificationId; // Store the verification ID
    public FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        firebaseAuth = FirebaseAuth.getInstance();

        nameEditText = findViewById(R.id.name);
        rollNumberEditText = findViewById(R.id.Roll_number);
        emailEditText = findViewById(R.id.roll_number);
        passwordEditText = findViewById(R.id.password2);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        mobileNoEditText = findViewById(R.id.mobileno);
        otpEditText = findViewById(R.id.otpEditText); // Add the OTP EditText
        otpButton = findViewById(R.id.otp_btn);

        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String rollNumber = rollNumberEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String mobileNumber = mobileNoEditText.getText().toString();

                if (name.isEmpty() || rollNumber.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || mobileNumber.isEmpty()) {
                    Toast.makeText(RegistrationPage.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegistrationPage.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Send OTP to the user's mobile number using Firebase
                    sendOTP(mobileNumber);
                }
            }
        });
    }

    // Send OTP using Firebase Phone Number Authentication
    private void sendOTP(String phoneNumber) {


        PhoneAuthProvider.verifyPhoneNumber(
                PhoneAuthOptions
                        .newBuilder(FirebaseAuth.getInstance())
                        .setActivity(this)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                          @Override
                                          public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                              // Auto-retrieval or instant verification of OTP completed
                                              // You can also use phoneAuthCredential to sign in the user
                                              signInWithPhoneAuthCredential(phoneAuthCredential);
                                          }

                                          @Override
                                          public void onVerificationFailed(@NonNull FirebaseException e) {
                                              // Verification failed, handle the error
                                              if (e instanceof FirebaseAuthInvalidUserException) {
                                                  Toast.makeText(RegistrationPage.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                                              } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                                  Toast.makeText(RegistrationPage.this, "Invalid request", Toast.LENGTH_SHORT).show();
                                              } else {
                                                  Toast.makeText(RegistrationPage.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                              }
                                          }

                                          @Override
                                          public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                              super.onCodeSent(s, forceResendingToken);
                                              // Store the verification ID for later use
                                              verificationId = s;

                                              // Display OTP EditText for user to enter OTP
                                              otpEditText.setVisibility(View.VISIBLE);
                                              otpButton.setText("Verify OTP");
                                              Toast.makeText(RegistrationPage.this, "OTP sent to your mobile number", Toast.LENGTH_SHORT).show();
                                          }
                                      }
                        )
                        .build());
        /*PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60, // Timeout duration
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        // Auto-retrieval or instant verification of OTP completed
                        // You can also use phoneAuthCredential to sign in the user
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // Verification failed, handle the error
                        if (e instanceof FirebaseAuthInvalidUserException) {
                            Toast.makeText(RegistrationPage.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(RegistrationPage.this, "Invalid request", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegistrationPage.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        // Store the verification ID for later use
                        verificationId = s;

                       /// // Display OTP EditText for user to enter OTP
                        otpEditText.setVisibility(View.VISIBLE);
                        otpButton.setText("Verify OTP");
                        Toast.makeText(RegistrationPage.this, "OTP sent to your mobile number", Toast.LENGTH_SHORT).show();
                    }
                }
        );*/
    }

    // Verify the OTP entered by the user
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-in successful, you can proceed with registration
                        Toast.makeText(RegistrationPage.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        // Now you can save user data or navigate to the next screen
                    } else {
                        // Sign-in failed, display an error message
                        Toast.makeText(RegistrationPage.this, "Verification failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}