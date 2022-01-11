package com.nurali.kampunggerabah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.github.dhaval2404.imagepicker.BuildConfig;
import com.nurali.kampunggerabah.activities.AdminBerandaActivity;
import com.nurali.kampunggerabah.activities.CustomerBerandaActivity;
import com.nurali.kampunggerabah.activities.LoginActivity;
import com.nurali.kampunggerabah.activities.PengrajinBerandaActivity;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;
import com.nurali.kampunggerabah.databinding.ActivitySplashBinding;
import com.nurali.kampunggerabah.preferences.AppPreference;

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

        int loadingTime = 2000;
        new Handler().postDelayed(() -> {
            if (AppPreference.getUser(this) != null) {
                PenggunaResponse.PenggunaModel p = AppPreference.getUser(this);

                if (p.peran.equals("customer")) {
                    startActivity(new Intent(SplashActivity.this, CustomerBerandaActivity.class));
                    finish();
                } else if (p.peran.equals("admin")) {
                    startActivity(new Intent(SplashActivity.this, AdminBerandaActivity.class));
                    finish();
                }
                else if (p.peran.equals("pengrajin")) {
                    startActivity(new Intent(SplashActivity.this, PengrajinBerandaActivity.class));
                    finish();
                }

            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
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