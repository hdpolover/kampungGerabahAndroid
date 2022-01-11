package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nurali.kampunggerabah.api.ApiClient;
import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;
import com.nurali.kampunggerabah.databinding.ActivityLoginBinding;
import com.nurali.kampunggerabah.preferences.AppPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    ApiInterface apiInterface;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cekEmail = true;
                boolean cekPassword = true;

                if (binding.emailEt.getText().toString().isEmpty()) {
                    binding.emailEt.setError("Mohon isi data berikut");
                    cekEmail = false;
                }

                if (binding.passwordEt.getText().toString().isEmpty()) {
                    binding.passwordEt.setError("Mohon isi data berikut");
                    cekPassword = false;
                }

                if (cekEmail && cekPassword) {
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setCancelable(false);
                    progressDialog.setTitle("Pesan");
                    progressDialog.setMessage("Mohon tunggu sebentar...");
                    progressDialog.show();

                    login();
                }
            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private  void login() {
        apiInterface.login(
                binding.emailEt.getText().toString(),
                binding.passwordEt.getText().toString()
        ).enqueue(new Callback<PenggunaResponse>() {
            @Override
            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        //save
                        PenggunaResponse.PenggunaModel p = response.body().data.get(0);

                        AppPreference.saveUser(LoginActivity.this, p);

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        if (p.peran.equals("customer")) {
                            startActivity(new Intent(LoginActivity.this, CustomerBerandaActivity.class));
                            Toast.makeText(LoginActivity.this, "Selamat datang, " + p.username, Toast.LENGTH_LONG).show();
                            finish();
                        } else if (p.peran.equals("admin")) {
                            startActivity(new Intent(LoginActivity.this, AdminBerandaActivity.class));
                            Toast.makeText(LoginActivity.this, "Selamat datang, " + p.username, Toast.LENGTH_LONG).show();
                            finish();
                        } else if (p.peran.equals("pengrajin")){
                            startActivity(new Intent(LoginActivity.this, PengrajinBerandaActivity.class));
                            Toast.makeText(LoginActivity.this, "Selamat datang, " + p.username, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    } else {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Pesan")
                                .setMessage(response.body().message)
                                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }

                                        dialog.dismiss();
                                    }
                                })
                                .create()
                                .show();
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