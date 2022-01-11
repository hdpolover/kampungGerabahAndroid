package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nurali.kampunggerabah.databinding.ActivityPengrajinBerandaBinding;
import com.nurali.kampunggerabah.preferences.AppPreference;

public class PengrajinBerandaActivity extends AppCompatActivity {

    ActivityPengrajinBerandaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPengrajinBerandaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.namaPenggunaTv.setText(AppPreference.getUser(this).username);

        binding.katalogProdukCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PengrajinBerandaActivity.this, PengrajinKatalogProdukActivity.class));
            }
        });

        binding.profilCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PengrajinBerandaActivity.this, ProfilActivity.class));
            }
        });

        binding.transaksiCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PengrajinBerandaActivity.this, AdminTransaksiActivity.class));
            }
        });
    }
}