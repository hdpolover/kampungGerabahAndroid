package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nurali.kampunggerabah.api.ApiClient;
import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.api.responses.BaseResponse;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;
import com.nurali.kampunggerabah.databinding.ActivityAdminTambahPengrajinBinding;
import com.nurali.kampunggerabah.preferences.AppPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminTambahPengrajinActivity extends AppCompatActivity {

    ActivityAdminTambahPengrajinBinding binding;
    ApiInterface apiInterface;

    ProgressDialog progressDialog;

    String peran = "pengrajin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminTambahPengrajinBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        binding.simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
    }

    private void checkData() {
        boolean cekUsername = true;
        boolean cekAlamat = true;
        boolean cekPassword = true;
        boolean cekEmail = true;
        boolean cekNoTelp = true;
        boolean cekLat = true;
        boolean cekLongi = true;

        if (binding.usernameEt.getText().toString().isEmpty()) {
            binding.usernameEt.setError("Mohon isi data berikut");
            cekUsername = false;
        }

        if (binding.alamatEt.getText().toString().isEmpty()) {
            binding.alamatEt.setError("Mohon isi data berikut");
            cekAlamat = false;
        }

        if (binding.passwordEt.getText().toString().isEmpty()) {
            binding.passwordEt.setError("Mohon isi data berikut");
            cekPassword = false;
        }

        if (binding.passwordEt.getText().toString().trim().length() < 6) {
            binding.passwordEt.setError("Password harus lebih dari 6 karakter");
            cekPassword = false;
        }

        if (binding.emailEt.getText().toString().isEmpty()) {
            binding.emailEt.setError("Mohon isi data berikut");
            cekEmail = false;
        }

        if (binding.noTelpEt.getText().toString().isEmpty()) {
            binding.noTelpEt.setError("Mohon isi data berikut");
            cekNoTelp = false;
        }

        if (binding.latitudeLokasiEt.getText().toString().isEmpty()) {
            binding.latitudeLokasiEt.setError("Mohon isi data berikut");
            cekLat = false;
        }

        if (binding.longitudeokasiEt.getText().toString().isEmpty()) {
            binding.longitudeokasiEt.setError("Mohon isi data berikut");
            cekLongi = false;
        }

        if (cekUsername && cekAlamat && cekEmail && cekPassword && cekLongi && cekNoTelp && cekLat) {
            progressDialog = new ProgressDialog(AdminTambahPengrajinActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Pesan");
            progressDialog.setMessage("Mohon tunggu sebentar...");
            progressDialog.show();

            apiInterface.getPenggunaByEmail(
                    binding.emailEt.getText().toString().trim()
            ).enqueue(new Callback<PenggunaResponse>() {
                @Override
                public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                    if (response != null) {
                        if (response.body().status) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            Toast.makeText(getApplicationContext(), "Mohon maaf. Email sudah terdaftar..", Toast.LENGTH_SHORT).show();
                        } else {
                            //insert tabel pengguna
                            apiInterface.daftar(
                                    binding.emailEt.getText().toString().trim(),
                                    binding.usernameEt.getText().toString().trim(),
                                    binding.passwordEt.getText().toString().trim(),
                                    binding.alamatEt.getText().toString().trim(),
                                    peran,
                                    binding.noTelpEt.getText().toString().trim(),
                                    binding.latitudeLokasiEt.getText().toString().trim(),
                                    binding.longitudeokasiEt.getText().toString().trim()
                            ).enqueue(new Callback<BaseResponse>() {
                                @Override
                                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                    if (response != null) {
                                        if (response.body().status) {
                                            //get id yang baru diinsert
                                            apiInterface.getPenggunaByEmail(
                                                    binding.emailEt.getText().toString().trim()
                                            ).enqueue(new Callback<PenggunaResponse>() {
                                                @Override
                                                public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                                                    if (response != null) {
                                                        if (response.body().status) {
                                                            if (progressDialog.isShowing()) {
                                                                progressDialog.dismiss();
                                                            }

                                                            PenggunaResponse.PenggunaModel p = response.body().data.get(0);

                                                            onBackPressed();
                                                            Toast.makeText(getApplicationContext(), "Penambahan pengrajin berhasil!", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                                                    Log.e("login", t.getMessage());
                                                }
                                            });
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
                }

                @Override
                public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                    Log.e("login", t.getMessage());
                }
            });

        }
    }
}