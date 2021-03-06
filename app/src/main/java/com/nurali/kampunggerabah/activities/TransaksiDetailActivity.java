package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Binder;
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
import com.nurali.kampunggerabah.api.responses.DetailTransaksiResponse;
import com.nurali.kampunggerabah.api.responses.PembayaranResponse;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;
import com.nurali.kampunggerabah.api.responses.ProdukResponse;
import com.nurali.kampunggerabah.api.responses.TransaksiResponse;
import com.nurali.kampunggerabah.databinding.ActivityProdukCekoutBinding;
import com.nurali.kampunggerabah.databinding.ActivityProdukDetailBinding;
import com.nurali.kampunggerabah.databinding.ActivityTransaksiDetailBinding;
import com.nurali.kampunggerabah.preferences.AppPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiDetailActivity extends AppCompatActivity {

    ActivityTransaksiDetailBinding binding;
    ApiInterface apiInterface;

    String idTransaksi, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransaksiDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        idTransaksi = getIntent().getStringExtra("id_transaksi");
        status = getIntent().getStringExtra("status");

        PenggunaResponse.PenggunaModel p = AppPreference.getUser(this);

        apiInterface.getPembayaran(
                idTransaksi
        ).enqueue(new Callback<PembayaranResponse>() {
            @Override
            public void onResponse(Call<PembayaranResponse> call, Response<PembayaranResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        PembayaranResponse.PembayaranModel p = response.body().data.get(0);

                        binding.belumBayarTv.setVisibility(View.GONE);
                        binding.tglTransferTv.setText("Dibayarkan pada " + p.tglTransfer);
                        binding.nominalTv.setText(Helper.formatRupiah(Integer.parseInt(p.getNominalTransfer())));

                        if (Integer.parseInt(p.getStatus()) == 0) {
                            binding.chipBayarTv.setText("Menunggu Validasi");
                        } else {
                            binding.chipBayarTv.setText("Pembayaran Dikonfirmasi");
                        }

                        binding.bayarBtn.setText("Detail Pembayarann");
                        binding.bayarBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(TransaksiDetailActivity.this, PembayaranDetailActivity.class);
                                i.putExtra("id_transaksi", idTransaksi);
                                startActivity(i);
                            }
                        });
                    } else {
                        binding.sudahBayarLayout.setVisibility(View.GONE);

                        if (p.peran.equals("customer")) {
                            binding.bayarBtn.setText("Bayar sekarang");
                            binding.bayarBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(TransaksiDetailActivity.this, TransaksiBayarActivity.class);
                                    i.putExtra("id_transaksi", idTransaksi);
                                    startActivity(i);
                                }
                            });
                        } else {
                            binding.bayarBtn.setVisibility(View.GONE);
                            binding.belumBayarTv.setText("Customer belum melakukan pembayaran transaksi ini.");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PembayaranResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });

        apiInterface.getTransaksi(
                idTransaksi
        ).enqueue(new Callback<TransaksiResponse>() {
            @Override
            public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        TransaksiResponse.TransaksiModel t = response.body().data.get(0);

                        binding.resiTV.setText(t.getNoResi());
                        binding.biayaOngkirTv.setText(Helper.formatRupiah(Integer.parseInt(t.getOngkir())));

                    }
                }
            }

            @Override
            public void onFailure(Call<TransaksiResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });

        apiInterface.getDetailTransaksi(
                idTransaksi
        ).enqueue(new Callback<DetailTransaksiResponse>() {
            @Override
            public void onResponse(Call<DetailTransaksiResponse> call, Response<DetailTransaksiResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        DetailTransaksiResponse.DetailTransaksiModel dt = response.body().data.get(0);

                        binding.jumlahTv.setText(dt.jumlah + " item");
                        binding.hargaTv.setText(Helper.formatRupiah(Integer.parseInt(dt.subtotal)));

                        apiInterface.getDetailProduk(
                                dt.idProduk
                        ).enqueue(new Callback<ProdukResponse>() {
                            @Override
                            public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                                if (response != null) {
                                    if (response.body().status) {
                                        ProdukResponse.ProdukModel p = response.body().data.get(0);

                                        binding.namaProdukTv.setText(p.getNama());

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
            }

            @Override
            public void onFailure(Call<DetailTransaksiResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });

        if (p.peran.equals("admin")) {
            binding.btn.setVisibility(View.GONE);
        } else if (p.peran.equals("pengrajin")) {
            if (Integer.parseInt(status) == 1) {
                binding.btn.setText("UPDATE RESI");
                binding.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(TransaksiDetailActivity.this, PengrajinUpdateResiActivity.class);
                        i.putExtra("id_transaksi", idTransaksi);
                        startActivity(i);
                    }
                });
            } else {
                binding.btn.setVisibility(View.GONE);
            }
        } else {
            if (Integer.parseInt(status) == 2) {
                binding.btn.setText("PESANAN SAMPAI");
                binding.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Pesanan selesai", Toast.LENGTH_SHORT).show();

                        apiInterface.pesananSampai(
                                idTransaksi
                        ).enqueue(new Callback<BaseResponse>() {
                            @Override
                            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                if (response != null) {
                                    if (response.body().status) {
                                        Toast.makeText(getApplicationContext(), "Pesanan dikonfirmasi telah sampai", Toast.LENGTH_SHORT).show();

                                        onBackPressed();
                                        onBackPressed();
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
                });
            } else {
                binding.btn.setVisibility(View.GONE);
            }
        }
    }
}