package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nurali.kampunggerabah.MainActivity;
import com.nurali.kampunggerabah.R;
import com.nurali.kampunggerabah.api.ApiClient;
import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.api.responses.BaseResponse;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;
import com.nurali.kampunggerabah.databinding.ActivityMainBinding;
import com.nurali.kampunggerabah.databinding.ActivitySignUpBinding;
import com.nurali.kampunggerabah.preferences.AppPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    ApiInterface apiInterface;
    String peran = "Customer";

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        peran = "Customer";

        apiInterface = ApiClient.getClient();

        apiInterface.getPenggunaByEmail(
                "ali@email.com"
        ).enqueue(new Callback<PenggunaResponse>() {
            @Override
            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        Toast.makeText(getApplicationContext(), response.body().data.get(0).username, Toast.LENGTH_SHORT).show();
                        Log.e("ds", response.body().data.get(0).username);
                    }
                }
            }

            @Override
            public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButtonCustomer:
                        peran = "Customer";
                        break;
                    case R.id.radioButtonPetani:
                        peran = "Pengrajin";
                        break;
                }
            }
        });

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
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

        if (binding.namaEt.getText().toString().isEmpty()) {
            binding.namaEt.setError("Mohon isi data berikut");
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

        if (cekUsername && cekAlamat && cekEmail && cekPassword) {
            progressDialog = new ProgressDialog(SignUpActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Pesan");
            progressDialog.setMessage("Mohon tunggu sebentar...");
            progressDialog.show();

            //insert tabel pengguna
            apiInterface.daftar(
                    binding.emailEt.getText().toString().trim(),
                    binding.namaEt.getText().toString().trim(),
                    binding.passwordEt.getText().toString().trim(),
                    binding.alamatEt.getText().toString().trim(),
                    peran
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

                                            //save
                                            AppPreference.saveUser(SignUpActivity.this, response.body().data.get(0));

                                            Toast.makeText(getApplicationContext(), "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                            finish();
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