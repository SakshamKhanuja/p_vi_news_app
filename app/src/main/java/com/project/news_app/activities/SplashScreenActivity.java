package com.project.news_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

/**
 * Shows a splash screen containing the app logo in center.
 */
@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // MainActivity is started.
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));

        // Destroys this activity.
        finish();
    }
}