package com.nurali.kampunggerabah.api;

import com.nurali.kampunggerabah.api.responses.BaseResponse;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("auth/daftar")
    Call<BaseResponse> daftar(
            @Query("email") String email,
            @Query("username") String username,
            @Query("password") String password,
            @Query("alamat") String alamat,
            @Query("peran") String peran
    );

    @GET("pengguna")
    Call<PenggunaResponse> getPenggunaByEmail(
            @Query("email") String email
    );
}
