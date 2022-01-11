package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.nurali.kampunggerabah.R;
import com.nurali.kampunggerabah.api.ApiClient;
import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.databinding.ActivityAdminBerandaBinding;
import com.nurali.kampunggerabah.databinding.ActivityProfilBinding;
import com.nurali.kampunggerabah.preferences.AppPreference;

public class ProfilActivity extends AppCompatActivity {

    ActivityProfilBinding binding;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        binding.namaProfilTv.setText(AppPreference.getUser(this).username);
        binding.peranProfilTv.setText(AppPreference.getUser(this).peran);

        Glide.with(getApplicationContext())
                .load(getString(R.string.base_url) + getString(R.string.profile_link) + AppPreference.getUser(this).foto)
                .centerCrop()
                .into(binding.fotoProfilIv);

        binding.keluarTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreference.removeUser(ProfilActivity.this);
                startActivity(new Intent(ProfilActivity.this, LoginActivity.class));
                finish();
            }
        });

        binding.infoAplikasiTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, InfoAplikasiActivity.class));
            }
        });

        binding.bantuanTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, BantuanActivity.class));
            }
        });

        binding.editProfilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, EditProfilActivity.class));
            }
        });
    }
}