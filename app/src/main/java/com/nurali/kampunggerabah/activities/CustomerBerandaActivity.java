package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nurali.kampunggerabah.databinding.ActivityCustomerBerandaBinding;
import com.nurali.kampunggerabah.preferences.AppPreference;

public class CustomerBerandaActivity extends AppCompatActivity {

    ActivityCustomerBerandaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerBerandaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.namaPenggunaTv.setText(AppPreference.getUser(this).username);

        binding.katalogProdukCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerBerandaActivity.this, InfoAplikasiActivity.class));
            }
        });

        binding.transaksiCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerBerandaActivity.this, InfoAplikasiActivity.class));
            }
        });

        binding.keranjangCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerBerandaActivity.this, CustomerKeranjangActivity.class));
            }
        });

        binding.profilCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerBerandaActivity.this, ProfilActivity.class));
            }
        });

    }
}