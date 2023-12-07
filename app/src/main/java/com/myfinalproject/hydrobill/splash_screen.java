package com.myfinalproject.hydrobill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.ProgressBar;

public class splash_screen extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Reference to the ConstraintLayout that you want to animate
        ConstraintLayout splashLayout = findViewById(R.id.constraint_id);

        // Reference to the ProgressBar (loading animation)
        ProgressBar loadingProgressBar = findViewById(R.id.progressBar);

        // Set initial visibility of the ProgressBar
        loadingProgressBar.setVisibility(View.INVISIBLE);

        // Fade-in animation for the layout
        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1000); // 1 second
        fadeIn.setFillAfter(true);

        // Fade-out animation for the layout
        AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setStartOffset(1000); // Start after 1 second (after fadeIn)
        fadeOut.setDuration(1000); // 1 second
        fadeOut.setFillAfter(true);

        // Combine the animations for the layout
        AnimationSet layoutAnimationSet = new AnimationSet(false);
        layoutAnimationSet.addAnimation(fadeIn);
        layoutAnimationSet.addAnimation(fadeOut);

        // Apply the animation to the layout
        splashLayout.startAnimation(layoutAnimationSet);

        // Simulate loading with a delay
        new Handler().postDelayed(() -> {
            // Show the loading animation after the layout fade-in
            loadingProgressBar.setVisibility(View.VISIBLE);

            // This method will be executed once the timer is over
            // Start your app's main activity
            Intent intent = new Intent(splash_screen.this, login_page.class);
            startActivity(intent);

            // Close this activity
            finish();
        }, 1000); // Wait for the layout animation to complete
    }
}