package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nurali.kampunggerabah.R;
import com.nurali.kampunggerabah.adapters.PengrajinAdapter;
import com.nurali.kampunggerabah.adapters.ProdukAdapter;
import com.nurali.kampunggerabah.api.ApiClient;
import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.api.responses.BaseResponse;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;
import com.nurali.kampunggerabah.api.responses.ProdukResponse;
import com.nurali.kampunggerabah.databinding.ActivityAdminDaftarUmkmBinding;
import com.nurali.kampunggerabah.databinding.ActivityPengrajinDetailBinding;
import com.nurali.kampunggerabah.preferences.AppPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengrajinDetailActivity extends AppCompatActivity {

    ActivityPengrajinDetailBinding binding;
    ApiInterface apiInterface;

    String idPengguna;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPengrajinDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        idPengguna = getIntent().getStringExtra("id_pengguna");

        apiInterface.getDetailPengrajin(
                idPengguna
        ).enqueue(new Callback<PenggunaResponse>() {
            @Override
            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        PenggunaResponse.PenggunaModel p = response.body().data.get(0);

                        binding.namaPengrajinTv.setText(p.username);
                        binding.noTelpTv.setText(p.noTelp);
                        binding.alamatTv.setText(p.alamat);
                        binding.daftarTv.setText("Terdaftar sejak " + p.tanggalDibuat);

                        Glide.with(getApplicationContext())
                                .load(getString(R.string.base_url) + getString(R.string.profile_link) + p.foto)
                                .centerCrop()
                                .into(binding.detailProfilIv);

                    }
                }
            }

            @Override
            public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });

        getProduk();
    }

    void getProduk() {
        binding.rv.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rv.setHasFixedSize(true);

        apiInterface.getProdukPengrajin(idPengguna).enqueue(new Callback<ProdukResponse>() {
            @Override
            public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                if (response.body().status) {
                    List<ProdukResponse.ProdukModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    binding.rv.setAdapter(new ProdukAdapter(list, getApplicationContext()));

                    if (list.isEmpty()) {
                        binding.noProdukLayout.setVisibility(View.VISIBLE);
                    } else {
                        binding.noProdukLayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProdukResponse> call, Throwable t) {

            }
        });
    }
}