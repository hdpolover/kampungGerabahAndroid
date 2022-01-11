package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.nurali.kampunggerabah.R;
import com.nurali.kampunggerabah.api.ApiClient;
import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;
import com.nurali.kampunggerabah.api.responses.ProdukResponse;
import com.nurali.kampunggerabah.databinding.ActivityPengrajinDetailBinding;
import com.nurali.kampunggerabah.databinding.ActivityProdukDetailBinding;
import com.nurali.kampunggerabah.preferences.AppPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukDetailActivity extends AppCompatActivity {

    ActivityProdukDetailBinding binding;
    ApiInterface apiInterface;

    String idProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProdukDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        idProduk = getIntent().getStringExtra("id_produk");

        PenggunaResponse.PenggunaModel p = AppPreference.getUser(this);

        if (p.peran.equals("admin")) {
            binding.editProdukBtn.setVisibility(View.GONE);
        }

        binding.editProdukBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProdukDetailActivity.this, ProdukEditActivity.class);
                i.putExtra("id_produk", idProduk);
                startActivity(i);
            }
        });

        apiInterface.getDetailProduk(
                idProduk
        ).enqueue(new Callback<ProdukResponse>() {
            @Override
            public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        ProdukResponse.ProdukModel p = response.body().data.get(0);

                        binding.namaProdukTv.setText(p.getNama());
                        binding.stokTv.setText(p.getStok() + " item tersisa");
                        binding.hargaTv.setText("Rp." + p.getHargaSatuan());
                        binding.beratTv.setText(p.getBerat() + " gram");
                        binding.chipProduk.setText(p.getKategori());
                        binding.deksripsiTv.setText(p.getDeskripsi());

                        Glide.with(getApplicationContext())
                                .load(getString(R.string.base_url) + getString(R.string.produk_link) + p.gambar)
                                .centerCrop()
                                .into(binding.gambarProdukTv);

                    }
                }
            }

            @Override
            public void onFailure(Call<ProdukResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });
    }
}