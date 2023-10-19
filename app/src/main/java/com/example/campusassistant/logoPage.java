package com.example.campusassistant;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class logoPage extends AppCompatActivity {

    private static final long DELAY_TIME_MILLIS = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_page);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(logoPage.this, MainActivity.class);
                startActivity(intent);


                finish();
            }
        }, DELAY_TIME_MILLIS);
    }
}
