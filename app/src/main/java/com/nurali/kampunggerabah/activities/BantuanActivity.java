package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.nurali.kampunggerabah.databinding.ActivityBantuanBinding;

public class BantuanActivity extends AppCompatActivity {

    ActivityBantuanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBantuanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


    }
}