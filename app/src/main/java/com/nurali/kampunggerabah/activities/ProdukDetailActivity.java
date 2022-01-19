package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nurali.kampunggerabah.R;
import com.nurali.kampunggerabah.api.ApiClient;
import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.api.Helper;
import com.nurali.kampunggerabah.api.responses.BaseResponse;
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

        if (p.peran.equals("admin") || p.peran.equals("customer")) {
            binding.editProdukBtn.setVisibility(View.GONE);
        }

        if (p.peran.equals("admin") || p.peran.equals("pengrajin")) {
            binding.custLayout.setVisibility(View.GONE);
        }

        binding.editProdukBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProdukDetailActivity.this, ProdukEditActivity.class);
                i.putExtra("id_produk", idProduk);
                startActivity(i);
            }
        });

        binding.cekoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProdukDetailActivity.this, ProdukCekoutActivity.class);
                i.putExtra("id_produk", idProduk);
                startActivity(i);
            }
        });

        binding.keranjangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               simpanKeranjang();
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
                        binding.hargaTv.setText(Helper.formatRupiah(Integer.parseInt(p.getHargaSatuan())));
                        binding.beratTv.setText(p.getBerat() + " gram");
                        binding.chipProduk.setText(p.getKategori());
                        binding.deksripsiTv.setText(p.getDeskripsi());

                        Glide.with(getApplicationContext())
                                .load(getString(R.string.base_url) + getString(R.string.produk_link) + p.gambar)
                                .centerCrop()
                                .into(binding.gambarProdukTv);

                        apiInterface.getDetailPengrajin(
                                p.idPengguna
                        ).enqueue(new Callback<PenggunaResponse>() {
                            @Override
                            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                                if (response != null) {
                                    if (response.body().status) {
                                        PenggunaResponse.PenggunaModel p = response.body().data.get(0);

                                        binding.namaPengrajinTv.setText(p.getUsername());
                                        binding.noTelpTv.setText(p.getNoTelp());
                                        binding.alamatPengrajinTv.setText(p.getAlamat());

                                        Glide.with(getApplicationContext())
                                                .load(getString(R.string.base_url) + getString(R.string.profile_link) + p.foto)
                                                .centerCrop()
                                                .into(binding.foroPengrajinIv);

                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                                Log.e("login", t.getMessage());
                            }
                        });

                        binding.pengrajinLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(ProdukDetailActivity.this, PengrajinDetailActivity.class);
                                i.putExtra("id_pengguna", p.idPengguna);
                                startActivity(i);
                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<ProdukResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });
    }

    void  simpanKeranjang() {
        apiInterface.simpanKeranjang(
                AppPreference.getUser(this).idPengguna,
                idProduk
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        Toast.makeText(getApplicationContext(), "Keranjang Berhasil ditambahkan", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(ProdukDetailActivity.this, CustomerKeranjangActivity.class));
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });
    }
}