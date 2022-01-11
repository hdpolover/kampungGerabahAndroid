package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nurali.kampunggerabah.databinding.ActivityAdminBerandaBinding;
import com.nurali.kampunggerabah.databinding.ActivityAdminPengaturanBinding;

public class AdminPengaturanActivity extends AppCompatActivity {

    ActivityAdminPengaturanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminPengaturanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.tambahPengrajinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPengaturanActivity.this, AdminTambahPengrajinActivity.class));
            }
        });

        binding.tambahAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPengaturanActivity.this, AdminTambahAdminActivity.class));
            }
        });
    }
}