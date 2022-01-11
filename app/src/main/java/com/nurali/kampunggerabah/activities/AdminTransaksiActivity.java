package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.nurali.kampunggerabah.databinding.ActivityAdminTransaksiBinding;
import com.nurali.kampunggerabah.databinding.ActivityPengrajinTambahProdukBinding;

public class AdminTransaksiActivity extends AppCompatActivity {

    ActivityAdminTransaksiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminTransaksiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}