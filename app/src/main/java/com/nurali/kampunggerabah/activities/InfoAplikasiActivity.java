package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.github.dhaval2404.imagepicker.BuildConfig;
import com.nurali.kampunggerabah.databinding.ActivityInfoAplikasiBinding;

public class InfoAplikasiActivity extends AppCompatActivity {

    ActivityInfoAplikasiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoAplikasiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.titleText.setText("Info Aplikasi");

        binding.verText.setText("Versi " + BuildConfig.VERSION_NAME);
    }
}