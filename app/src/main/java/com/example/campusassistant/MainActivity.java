package com.example.campusassistant;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView Rollnumber = (TextView)findViewById(R.id.Roll_number);
        TextView password = (TextView)findViewById(R.id.password);

        Button loginbtn =(Button) findViewById(R.id.log_btn);


        //admin
        loginbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(Rollnumber.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    // Correct
                    Toast.makeText(MainActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                } else {
                    // Incorrect
                    Toast.makeText(MainActivity.this, "LOGIN UNSUCCESSFUL", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button registerButton = findViewById(R.id.Register_text);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationPage.class);
                startActivity(intent);
            }
        });





    }
}
