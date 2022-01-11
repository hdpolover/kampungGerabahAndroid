package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.nurali.kampunggerabah.databinding.ActivityCustomerKeranjangBinding;

public class CustomerKeranjangActivity extends AppCompatActivity {

    ActivityCustomerKeranjangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerKeranjangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}