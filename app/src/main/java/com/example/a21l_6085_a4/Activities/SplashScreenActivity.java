package com.example.a21l_6085_a4.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a21l_6085_a4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logoImageView = findViewById(R.id.logoImageView);

        // Start the animation
        Animation comboAnimation = AnimationUtils.loadAnimation(this, R.anim.translate); // Use the combined animation
        logoImageView.startAnimation(comboAnimation);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Delay navigation to allow animation to complete
        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                // Navigate to MainActivity if the user is logged in
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            } else {
                // Navigate to SignInActivity if the user is not logged in
                startActivity(new Intent(SplashScreenActivity.this, SignInActivity.class));
            }
            finish(); // Close SplashScreenActivity
        }, 1500); // Match this duration with your animation
    }
}
