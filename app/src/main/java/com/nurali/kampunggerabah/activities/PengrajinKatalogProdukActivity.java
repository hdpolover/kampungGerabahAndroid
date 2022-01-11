package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nurali.kampunggerabah.adapters.PengrajinAdapter;
import com.nurali.kampunggerabah.adapters.ProdukVerticalAdapter;
import com.nurali.kampunggerabah.api.ApiClient;
import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;
import com.nurali.kampunggerabah.api.responses.ProdukResponse;
import com.nurali.kampunggerabah.databinding.ActivityPengrajinDetailBinding;
import com.nurali.kampunggerabah.databinding.ActivityPengrajinKatalogProdukBinding;
import com.nurali.kampunggerabah.preferences.AppPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengrajinKatalogProdukActivity extends AppCompatActivity {

    ActivityPengrajinKatalogProdukBinding binding;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPengrajinKatalogProdukBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        binding.tambahProdukBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PengrajinKatalogProdukActivity.this, PengrajinTambahProdukActivity.class));
            }
        });

        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        apiInterface.getProdukPengrajin(AppPreference.getUser(this).idPengguna).enqueue(new Callback<ProdukResponse>() {
            @Override
            public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                if (response.body().status) {
                    List<ProdukResponse.ProdukModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    binding.rv.setAdapter(new ProdukVerticalAdapter(list, getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<ProdukResponse> call, Throwable t) {

            }
        });
    }
}