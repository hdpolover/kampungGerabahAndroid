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
import com.nurali.kampunggerabah.api.Api;
import com.nurali.kampunggerabah.api.ApiClient;
import com.nurali.kampunggerabah.api.ApiEndpoint;
import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.api.Helper;
import com.nurali.kampunggerabah.api.responses.BaseResponse;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;
import com.nurali.kampunggerabah.api.responses.ProdukResponse;
import com.nurali.kampunggerabah.api.responses.city.DataCity;
import com.nurali.kampunggerabah.api.responses.city.ResponseCity;
import com.nurali.kampunggerabah.api.responses.cost.DataCost;
import com.nurali.kampunggerabah.api.responses.cost.DataType;
import com.nurali.kampunggerabah.api.responses.cost.ResponseCost;
import com.nurali.kampunggerabah.databinding.ActivityProdukCekoutBinding;
import com.nurali.kampunggerabah.preferences.AppPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukCekoutActivity extends AppCompatActivity {

    ActivityProdukCekoutBinding binding;
    ApiInterface apiInterface;
    ApiEndpoint apiEndpoint;

    String idProduk;
    private int jumlahOrder = 1;
    int hargaSatuan = 0;
    String nama = "";
    String ongkir = "0", estimasi = "";
    int beratProduk = 0;

    String idTransaksi;

    int totalHarga = 0;
    int subtotal = 0;
    int stokLama = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProdukCekoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();
        apiEndpoint = Api.getClient();

        PenggunaResponse.PenggunaModel p = AppPreference.getUser(this);

        idProduk = getIntent().getStringExtra("id_produk");

        cekOngkir(p.idKota, 1000);

        apiInterface.getDetailProduk(
                idProduk
        ).enqueue(new Callback<ProdukResponse>() {
            @Override
            public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        ProdukResponse.ProdukModel p = response.body().data.get(0);
                        nama = p.getNama();

                        binding.namaProdukTv.setText(nama);
                        binding.hargaTv.setText(Helper.formatRupiah(Integer.parseInt(p.getHargaSatuan())));
                        binding.subTotalTv.setText(nama + " x " + jumlahOrder);
                        binding.beratSatuanTv.setText(p.getBerat() + " gram");

                        stokLama = Integer.parseInt(p.getStok());

                        hargaSatuan = Integer.parseInt(p.getHargaSatuan());
                        int harga = hargaSatuan * jumlahOrder;
                        subtotal = harga;
                        binding.hargaSubtotalTv.setText(Helper.formatRupiah(harga));

                        totalHarga = harga + Integer.parseInt(ongkir);

                        binding.totalHargaTv.setText(Helper.formatRupiah(harga + Integer.parseInt(ongkir)));

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

        binding.alamatTv.setText(p.getAlamat());
        binding.namaTv.setText(p.getUsername());
        binding.noHpTv.setText(p.getNoTelp());

        binding.editTextJumlahOrder.setText(String.valueOf(jumlahOrder));

        binding.materialButtonTambahJumlahOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lama = jumlahOrder;
                jumlahOrder++;

                if (jumlahOrder > stokLama) {
                    jumlahOrder = lama;
                    Toast.makeText(getApplicationContext(), "Jumlah order melebihi stok. Tidak bisa.", Toast.LENGTH_SHORT).show();
                } else {
                    binding.editTextJumlahOrder.setText(String.valueOf(jumlahOrder));
                    int harga = hargaSatuan * jumlahOrder;
                    subtotal = harga;
                    binding.hargaSubtotalTv.setText(Helper.formatRupiah(harga));
                    binding.subTotalTv.setText(nama + " x " + jumlahOrder);

                    binding.totalHargaTv.setText(Helper.formatRupiah(harga + Integer.parseInt(ongkir)));

                    totalHarga = harga + Integer.parseInt(ongkir);
                }

            }
        });

        binding.materialButtonKurangJumlahOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jumlahOrder > 1) {
                    jumlahOrder--;
                    binding.editTextJumlahOrder.setText(String.valueOf(jumlahOrder));
                    int harga = hargaSatuan * jumlahOrder;
                    subtotal = harga;
                    binding.hargaSubtotalTv.setText("Rp." + harga);
                    binding.subTotalTv.setText(nama + " x " + jumlahOrder);

                    binding.totalHargaTv.setText(Helper.formatRupiah(harga + Integer.parseInt(ongkir)));

                    totalHarga = harga + Integer.parseInt(ongkir);
                }
            }
        });

        binding.bayarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProdukCekoutActivity.this);
                builder.setTitle("Lanjutkan ke Pembayaran?");
                builder.setMessage("Pastikan data transaksi Anda sudah benar sebelum melanjutkan. Lanjut?");
                builder.setPositiveButton("LANJUTKAN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        simpanTr();
                    }
                });
                builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ProdukCekoutActivity.this, "Dibatalkan", Toast.LENGTH_SHORT).show();
                    }
                });

                //NOW, WE CREATE THE ALERT DIALG OBJECT
                AlertDialog ad = builder.create();

                //FINALLY, WE SHOW THE DIALOG
                ad.show();
            }
        });
    }

    void cekOngkir(String idKota, int berat) {
        apiEndpoint.postCost(
                "255",
                idKota,
                berat,
                "jne"
        ).enqueue(new Callback<ResponseCost>() {
            @Override
            public void onResponse(Call<ResponseCost> call, Response<ResponseCost> response) {
                if (response != null) {
                    List<DataType> dt = response.body().getRajaongkir().getResults().get(0).getCosts();
                    List<DataCost> dc = dt.get(0).cost;

                    ongkir = dc.get(0).value.toString();
                    estimasi = dc.get(0).etd;

                    binding.ongkirTv.setText(Helper.formatRupiah(Integer.parseInt(ongkir)));
                    binding.estimasiTv.setText("Estimasi " + estimasi + " hari");
                    binding.biayaOngkirTv.setText(Helper.formatRupiah(Integer.parseInt(ongkir)));

                }
            }

            @Override
            public void onFailure(Call<ResponseCost> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });

    }

    public static String generateString() {
        String uuid = "TR-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        return uuid;
    }

    void simpanTr() {
        idTransaksi = generateString();

        apiInterface.simpanTransaksi(
                idTransaksi,
                AppPreference.getUser(this).idPengguna,
                totalHarga + "",
                ongkir
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        detailTransaksi(idTransaksi, idProduk, jumlahOrder, subtotal);
                        updateStokProduk();

                        Toast.makeText(getApplicationContext(), "Transaksi berhasil!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ProdukCekoutActivity.this, TransaksiBayarActivity.class);
                        i.putExtra("id_transaksi", idTransaksi);
                        startActivity(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }

    void updateStokProduk() {
        int stokBaru = stokLama - jumlahOrder;
        apiInterface.updateStok(
                idProduk,
                stokBaru + ""
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }


    void detailTransaksi(String idTransaksi, String idProduk, int jumlah, int subtotal) {
        apiInterface.simpanDetailTransaksi(
                idTransaksi,
                idProduk,
                subtotal + "",
                jumlah + ""
                ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }
}