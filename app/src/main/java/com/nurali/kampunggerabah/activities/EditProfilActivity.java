package com.nurali.kampunggerabah.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.nurali.kampunggerabah.R;
import com.nurali.kampunggerabah.api.Api;
import com.nurali.kampunggerabah.api.ApiClient;
import com.nurali.kampunggerabah.api.ApiEndpoint;
import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.api.responses.BaseResponse;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;
import com.nurali.kampunggerabah.api.responses.city.DataCity;
import com.nurali.kampunggerabah.api.responses.city.ResponseCity;
import com.nurali.kampunggerabah.databinding.ActivityAdminBerandaBinding;
import com.nurali.kampunggerabah.databinding.ActivityEditProfilBinding;
import com.nurali.kampunggerabah.preferences.AppPreference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfilActivity extends AppCompatActivity {

    ActivityEditProfilBinding binding;

    ApiInterface apiInterface;
    ApiEndpoint apiEndpoint;

    Uri fotoImg;
    ArrayList<String> cityList = new ArrayList<>();
    ArrayList<String> cityIdList = new ArrayList<>();

    String selectedIdKota, selectedNamaKota;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfilBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();
        apiEndpoint = Api.getClient();

        PenggunaResponse.PenggunaModel p = AppPreference.getUser(this);

        if (p.peran.equals("admin")) {
            binding.lat.setVisibility(View.GONE);
            binding.al.setVisibility(View.GONE);
            binding.longi.setVisibility(View.GONE);
            binding.spinnerLayout.setVisibility(View.GONE);
            binding.telp.setVisibility(View.GONE);
        } else if (p.peran.equals("pengrajin")) {
            binding.spinnerLayout.setVisibility(View.GONE);
        } else {
            binding.lat.setVisibility(View.GONE);
            binding.longi.setVisibility(View.GONE);
        }

        binding.usernameEt.setText(p.username);
        binding.alamatEt.setText(p.alamat);
        binding.noTelpEt.setText(p.noTelp);
        binding.emailEt.setText(p.email);
        binding.latitudeLokasiEt.setText(p.latitude);
        binding.longitudeokasiEt.setText(p.longitude);
        binding.passwordEt.setText(p.password);

        Glide.with(getApplicationContext())
                .load(getString(R.string.base_url) + getString(R.string.profile_link) + p.foto)
                .centerCrop()
                .into(binding.fotoPenggunaIv);

        apiEndpoint.getCity().enqueue(new Callback<ResponseCity>() {
            @Override
            public void onResponse(Call<ResponseCity> call, Response<ResponseCity> response) {
                if (response != null) {
                    List<DataCity> dt = response.body().getRajaongkir().getResults();

                    for (int i = 0; i < dt.size(); i++) {
                        cityIdList.add(dt.get(i).cityId);
                        cityList.add(dt.get(i).type + " " + dt.get(i).cityName);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfilActivity.this, android.R.layout.simple_spinner_item, cityList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseCity> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedIdKota = cityIdList.get(i);
                selectedNamaKota = cityList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p.peran.equals("customer")) {
                    updateCust();
                } else if (p.peran.equals("pengrajin")) {
                    updatePengrajin();
                } else {
                    updateAdmin();
                }
            }
        });

        binding.pilihFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(EditProfilActivity.this)
                        .compress(3000)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri fileUri = data.getData();

            fotoImg = fileUri;
            binding.fotoPenggunaIv.setImageURI(fileUri);
        }
    }

    void updateAdmin() {
        apiInterface.editAdmin(
                AppPreference.getUser(this).idPengguna,
                binding.usernameEt.getText().toString().trim(),
                binding.passwordEt.getText().toString().trim(),
                binding.emailEt.getText().toString().trim()
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        apiInterface.getPenggunaByEmail(
                                binding.emailEt.getText().toString().trim()
                        ).enqueue(new Callback<PenggunaResponse>() {
                            @Override
                            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                                if (response != null) {
                                    if (response.body().status) {
                                        PenggunaResponse.PenggunaModel p = response.body().data.get(0);

                                        AppPreference.saveUser(EditProfilActivity.this, p);

                                        Toast.makeText(getApplicationContext(), "Edit Profil berhasil", Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Edit Profil gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                                Log.e("login", t.getMessage());
                            }
                        });
                    } else {
                        Toast.makeText(EditProfilActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }

    void updatePengrajin() {
        apiInterface.editPengrajin(
                AppPreference.getUser(this).idPengguna,
                binding.usernameEt.getText().toString().trim(),
                binding.alamatEt.getText().toString().trim(),
                binding.passwordEt.getText().toString().trim(),
                binding.emailEt.getText().toString().trim(),
                binding.noTelpEt.getText().toString().trim(),
                binding.latitudeLokasiEt.getText().toString().trim(),
                binding.longitudeokasiEt.getText().toString().trim()
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        apiInterface.getPenggunaByEmail(
                                binding.emailEt.getText().toString().trim()
                        ).enqueue(new Callback<PenggunaResponse>() {
                            @Override
                            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                                if (response != null) {
                                    if (response.body().status) {
                                        PenggunaResponse.PenggunaModel p = response.body().data.get(0);

                                        AppPreference.saveUser(EditProfilActivity.this, p);

                                        Toast.makeText(getApplicationContext(), "Edit Profil berhasil", Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Edit Profil gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                                Log.e("login", t.getMessage());
                            }
                        });
                    } else {
                        Toast.makeText(EditProfilActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }

    void updateCust() {
        apiInterface.editCust(
                AppPreference.getUser(this).idPengguna,
                binding.usernameEt.getText().toString().trim(),
                binding.alamatEt.getText().toString().trim(),
                binding.passwordEt.getText().toString().trim(),
                binding.emailEt.getText().toString().trim(),
                binding.noTelpEt.getText().toString().trim(),
                selectedIdKota,
                selectedNamaKota
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        apiInterface.getPenggunaByEmail(
                                binding.emailEt.getText().toString().trim()
                        ).enqueue(new Callback<PenggunaResponse>() {
                            @Override
                            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                                if (response != null) {
                                    if (response.body().status) {
                                        PenggunaResponse.PenggunaModel p = response.body().data.get(0);

                                        AppPreference.saveUser(EditProfilActivity.this, p);

                                        Toast.makeText(getApplicationContext(), "Edit Profil berhasil", Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Edit Profil gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                                Log.e("login", t.getMessage());
                            }
                        });
                    } else {
                        Toast.makeText(EditProfilActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }

//    void updateCust() {
//        String a = binding.usernameEt.getText().toString().trim();
//        String b = binding.alamatEt.getText().toString().trim();
//        String c = binding.passwordEt.getText().toString().trim();
//        String d = binding.emailEt.getText().toString().trim();
//        String e = binding.noTelpEt.getText().toString().trim();
//
//        if (a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty() || fotoImg == null) {
//            Toast.makeText(EditProfilActivity.this, "Ada field yang masih kosong. Silakan isi terlebih dahulu.", Toast.LENGTH_LONG).show();
//        } else {
//            progressDialog = new ProgressDialog(EditProfilActivity.this);
//            progressDialog.setCancelable(false);
//            progressDialog.setTitle("Pesan");
//            progressDialog.setMessage("Mohon tunggu sebentar...");
//            progressDialog.show();
//
//            RequestBody aR = RequestBody.create(MediaType.parse("text/plain"), a);
//            RequestBody bR = RequestBody.create(MediaType.parse("text/plain"), b);
//            RequestBody cR = RequestBody.create(MediaType.parse("text/plain"), c);
//            RequestBody dR = RequestBody.create(MediaType.parse("text/plain"), d);
//            RequestBody eR = RequestBody.create(MediaType.parse("text/plain"), e);
//            RequestBody fR = RequestBody.create(MediaType.parse("text/plain"), selectedIdKota);
//            RequestBody hR = RequestBody.create(MediaType.parse("text/plain"), selectedNamaKota);
//            RequestBody gR = RequestBody.create(MediaType.parse("text/plain"), AppPreference.getUser(this).idPengguna);
//
//            //image
//            File file = new File(fotoImg.getPath());
//            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
//            MultipartBody.Part f = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
//
//            apiInterface.editCust(
//                    gR,
//                    aR,
//                    bR,
//                    cR,
//                    dR,
//                    eR,
//                    fR,
//                    hR,
//                    f
//            ).enqueue(new Callback<BaseResponse>() {
//                @Override
//                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
//                    if (response != null) {
//                        if (response.body().status) {
//                            if (progressDialog.isShowing()) {
//                                progressDialog.dismiss();
//                            }
//
//                            apiInterface.getPenggunaByEmail(
//                                    binding.emailEt.getText().toString().trim()
//                            ).enqueue(new Callback<PenggunaResponse>() {
//                                @Override
//                                public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
//                                    if (response != null) {
//                                        if (response.body().status) {
//                                            PenggunaResponse.PenggunaModel p = response.body().data.get(0);
//
//                                            AppPreference.saveUser(EditProfilActivity.this, p);
//
//                                            Toast.makeText(getApplicationContext(), "Edit Profil berhasil", Toast.LENGTH_SHORT).show();
//                                            onBackPressed();
//                                            finish();
//                                        } else {
//                                            Toast.makeText(getApplicationContext(), "Edit Profil gagal", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<PenggunaResponse> call, Throwable t) {
//                                    Log.e("login", t.getMessage());
//                                }
//                            });
//                        } else {
//                            Toast.makeText(EditProfilActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<BaseResponse> call, Throwable t) {
//                    Log.e("daftar", t.getMessage());
//                }
//            });
//        }
//    }
}