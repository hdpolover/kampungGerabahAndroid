package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nurali.kampunggerabah.databinding.ActivityAdminBerandaBinding;
import com.nurali.kampunggerabah.preferences.AppPreference;

public class AdminBerandaActivity extends AppCompatActivity {

    ActivityAdminBerandaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBerandaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.namaPenggunaTv.setText(AppPreference.getUser(this).username);

        binding.daftarUmkmCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminBerandaActivity.this, AdminDaftarUmkmActivity.class));
            }
        });

        binding.katalogProdukCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminBerandaActivity.this, ProdukKatalogActivity.class));
            }
        });

        binding.transaksiCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminBerandaActivity.this, AdminTransaksiActivity.class));
            }
        });

        binding.pengaturanCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminBerandaActivity.this, AdminPengaturanActivity.class));
            }
        });

        binding.profilCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminBerandaActivity.this, ProfilActivity.class));
            }
        });
    }
}