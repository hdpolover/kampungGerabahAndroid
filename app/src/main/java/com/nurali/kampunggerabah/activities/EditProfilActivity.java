package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;
import com.nurali.kampunggerabah.databinding.ActivityAdminBerandaBinding;
import com.nurali.kampunggerabah.databinding.ActivityEditProfilBinding;
import com.nurali.kampunggerabah.preferences.AppPreference;

public class EditProfilActivity extends AppCompatActivity {

    ActivityEditProfilBinding binding;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfilBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PenggunaResponse.PenggunaModel p = AppPreference.getUser(this);

        binding.usernameEt.setText(p.username);
        binding.alamatEt.setText(p.alamat);
        binding.noTelpEt.setText(p.noTelp);
        binding.emailEt.setText(p.email);


    }
}