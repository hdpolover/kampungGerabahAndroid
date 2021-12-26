package com.nurali.kampunggerabah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.github.dhaval2404.imagepicker.BuildConfig;
import com.nurali.kampunggerabah.activities.LoginActivity;
import com.nurali.kampunggerabah.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(view);

        binding.textViewVersion.setText("Ver " + BuildConfig.VERSION_NAME);

        //toMainActivity();

        int loadingTime = 2000;
        new Handler().postDelayed(() -> {
//            if (AppPreference.getUser(this) != null) {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
//            } else {
//                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
//                finish();
//            }
        }, loadingTime);

    }

    private void toMainActivity() {
        int loadingTime = 2000;
        new Handler().postDelayed(() -> {
//            if (AppPreference.getUser(this) != null) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
//            } else {
//                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
//                finish();
//            }
        }, loadingTime);
    }
}