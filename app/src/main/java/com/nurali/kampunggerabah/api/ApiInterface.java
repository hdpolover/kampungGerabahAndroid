package com.nurali.kampunggerabah.api;

import com.nurali.kampunggerabah.api.responses.BaseResponse;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;
import com.nurali.kampunggerabah.api.responses.ProdukResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("auth/daftar")
    Call<BaseResponse> daftar(
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("alamat") String alamat,
            @Field("peran") String peran,
            @Field("no_telp") String noTelp
    );

    @GET("pengguna")
    Call<PenggunaResponse> getPenggunaByEmail(
            @Query("email") String email
    );

    @GET("auth/login")
    Call<PenggunaResponse> login(
            @Query("email") String email,
            @Query("password") String password
    );

    @GET("pengrajin")
    Call<PenggunaResponse> getDaftarPengrajin();

    @GET("pengrajin")
    Call<PenggunaResponse> getDetailPengrajin(
            @Query("id_pengguna") String idPengguna
    );

    @GET("produk")
    Call<ProdukResponse> getProdukPengrajin(
            @Query("id_pengguna") String idPengguna
    );

    @GET("produk/get_detail_produk")
    Call<ProdukResponse> getDetailProduk(
            @Query("id_produk") String idProduk
    );

    @GET("produk/get_kategori")
    Call<ProdukResponse> getKategoriProduk(
            @Query("kategori") String kategori
    );

    @Multipart
    @POST("produk/tambah")
    Call<BaseResponse> tambahProduk(
            @Part("nama") RequestBody nama,
            @Part("deskripsi") RequestBody deskripsi,
            @Part("stok") RequestBody stok,
            @Part("harga_satuan") RequestBody harga_satuan,
            @Part("berat") RequestBody berat,
            @Part("kategori") RequestBody kategori,
            @Part("id_pengguna") RequestBody id_pengguna,
            @Part MultipartBody.Part image
    );
}
