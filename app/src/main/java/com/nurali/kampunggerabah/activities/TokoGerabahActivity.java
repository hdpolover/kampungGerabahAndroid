package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.nurali.kampunggerabah.databinding.ActivityMainBinding;
import com.nurali.kampunggerabah.databinding.ActivityTokoGerabahBinding;

public class TokoGerabahActivity extends AppCompatActivity {

    ActivityTokoGerabahBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTokoGerabahBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}