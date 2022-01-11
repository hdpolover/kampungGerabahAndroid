package com.nurali.kampunggerabah.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nurali.kampunggerabah.adapters.PengrajinAdapter;
import com.nurali.kampunggerabah.api.ApiClient;
import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;
import com.nurali.kampunggerabah.databinding.ActivityAdminDaftarUmkmBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDaftarUmkmActivity extends AppCompatActivity {

    ActivityAdminDaftarUmkmBinding binding;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDaftarUmkmBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        apiInterface.getDaftarPengrajin().enqueue(new Callback<PenggunaResponse>() {
            @Override
            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                if (response.body().status) {
                    List<PenggunaResponse.PenggunaModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    binding.rv.setAdapter(new PengrajinAdapter(list, getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<PenggunaResponse> call, Throwable t) {

            }
        });
    }
}