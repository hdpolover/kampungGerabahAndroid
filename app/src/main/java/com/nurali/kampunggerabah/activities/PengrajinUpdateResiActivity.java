package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nurali.kampunggerabah.api.ApiClient;
import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.api.responses.BaseResponse;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;
import com.nurali.kampunggerabah.databinding.ActivityPengrajinUpdateResiBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengrajinUpdateResiActivity extends AppCompatActivity {

    ActivityPengrajinUpdateResiBinding binding;
    String idTransaksi;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPengrajinUpdateResiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        idTransaksi = getIntent().getStringExtra("id_transaksi");

        binding.simpanResiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
    }

    private void checkData() {
        boolean cekUsername = true;

        if (binding.noResiEt.getText().toString().isEmpty()) {
            binding.noResiEt.setError("Mohon isi data berikut");
            cekUsername = false;
        }


        if (cekUsername) {
            apiInterface.updateResi(
                    binding.noResiEt.getText().toString().trim(),
                    idTransaksi
            ).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response != null) {
                        if (response.body().status) {
                            Toast.makeText(getApplicationContext(), "Update Resi berhasil.", Toast.LENGTH_SHORT).show();
                            finish();
                            onBackPressed();
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
}