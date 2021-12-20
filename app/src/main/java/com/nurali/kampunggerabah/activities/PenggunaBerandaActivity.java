package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nurali.kampunggerabah.databinding.ActivityPenggunaBerandaBinding;

public class PenggunaBerandaActivity extends AppCompatActivity {

    ActivityPenggunaBerandaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPenggunaBerandaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.daftarUmkm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PenggunaBerandaActivity.this, DaftarUmkmActivity.class));
            }
        });

        binding.tokogerabah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PenggunaBerandaActivity.this, TokoGerabahActivity.class));
            }
        });
    }
}