package com.nurali.kampunggerabah.api;

import com.nurali.kampunggerabah.api.responses.BaseResponse;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("auth/daftar")
    Call<BaseResponse> daftar(
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("alamat") String alamat,
            @Field("peran") String peran
    );

    @GET("pengguna")
    Call<PenggunaResponse> getPenggunaByEmail(
            @Query("email") String email
    );
}
