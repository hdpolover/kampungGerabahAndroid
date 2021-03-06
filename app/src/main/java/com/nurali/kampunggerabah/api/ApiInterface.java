package com.nurali.kampunggerabah.api;

import com.nurali.kampunggerabah.api.responses.BaseResponse;
import com.nurali.kampunggerabah.api.responses.DetailTransaksiResponse;
import com.nurali.kampunggerabah.api.responses.KeranjangResponse;
import com.nurali.kampunggerabah.api.responses.PembayaranResponse;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;
import com.nurali.kampunggerabah.api.responses.ProdukResponse;
import com.nurali.kampunggerabah.api.responses.TransaksiResponse;

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
            @Field("no_telp") String noTelp,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
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

    @GET("produk/get_kategori_customer")
    Call<ProdukResponse> getKategoriProdukCustomer(
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

    @FormUrlEncoded
    @POST("transaksi/simpan")
    Call<BaseResponse> simpanTransaksi(
            @Field("id_transaksi") String idTransaksi,
            @Field("id_pengguna") String idPengguna,
            @Field("total") String total,
            @Field("ongkir") String ongkir
    );

    @FormUrlEncoded
    @POST("detail_transaksi/simpan")
    Call<BaseResponse> simpanDetailTransaksi(
            @Field("id_transaksi") String idTransaksi,
            @Field("id_produk") String idProduk,
            @Field("subtotal") String subtotal,
            @Field("jumlah") String jumlah
    );

    @Multipart
    @POST("pembayaran/simpan")
    Call<BaseResponse> simpanPembayaran(
            @Part("id_transaksi") RequestBody idTransaksi,
            @Part("nama_akun_pengirim") RequestBody namaAkunPengirim,
            @Part("bank_pengirim") RequestBody bankPengirim,
            @Part("nominal_transfer") RequestBody nominalTransfer,
            @Part("tgl_transfer") RequestBody tglTransfer,
            @Part MultipartBody.Part image
    );

    @GET("transaksi/get_tr_berlangsung")
    Call<TransaksiResponse> getTrBerlangsung(
            @Query("id_pengguna") String idPengguna
    );

    @GET("transaksi/get_tr_selesai")
    Call<TransaksiResponse> getTrSelesai(
            @Query("id_pengguna") String idPengguna
    );

    @GET("transaksi/get_detail_transaksi")
    Call<TransaksiResponse> getTransaksi(
            @Query("id_transaksi") String idTransaksi
    );

    @GET("detail_transaksi/get_detail_transaksi")
    Call<DetailTransaksiResponse> getDetailTransaksi(
            @Query("id_transaksi") String idTransaksi
    );

    @GET("pembayaran/get_pembayaran")
    Call<PembayaranResponse> getPembayaran(
            @Query("id_transaksi") String idTransaksi
    );

    @GET("pembayaran/validasi")
    Call<BaseResponse> validasiPembayaran(
            @Query("id_pembayaran") String idPembayaran,
            @Query("id_transaksi") String idTransaksi
    );

    @GET("pembayaran/tolak")
    Call<BaseResponse> tolakPembayaran(
            @Query("id_pembayaran") String idPembayaran
    );

    @GET("transaksi/update_resi")
    Call<BaseResponse> updateResi(
            @Query("no_resi") String noResi,
            @Query("id_transaksi") String idTransaksi
    );

//    @Multipart
//    @POST("pengguna/edit_cust")
//    Call<BaseResponse> editCust(
//            @Part("id_pengguna") RequestBody idPengguna,
//            @Part("username") RequestBody username,
//            @Part("alamat") RequestBody alamat,
//            @Part("password") RequestBody password,
//            @Part("email") RequestBody email,
//            @Part("no_telp") RequestBody noTelp,
//            @Part("id_kota") RequestBody idKota,
//            @Part("nama_kota") RequestBody namaKota,
//            @Part MultipartBody.Part image
//    );

    @GET("pengguna/edit_cust")
    Call<BaseResponse> editCust(
        @Query("id_pengguna") String idPengguna,
        @Query("username") String username,
        @Query("alamat") String alamat,
        @Query("password") String password,
        @Query("email") String email,
        @Query("no_telp") String noTelp,
        @Query("id_kota") String idKota,
        @Query("nama_kota") String namaKota
    );

    @GET("pengguna/edit_pengrajin")
    Call<BaseResponse> editPengrajin(
            @Query("id_pengguna") String idPengguna,
            @Query("username") String username,
            @Query("alamat") String alamat,
            @Query("password") String password,
            @Query("email") String email,
            @Query("no_telp") String noTelp,
            @Query("latitude") String latitude,
            @Query("longitude") String longitu
    );

    @GET("pengguna/edit_admin")
    Call<BaseResponse> editAdmin(
            @Query("id_pengguna") String idPengguna,
            @Query("username") String username,
            @Query("password") String password,
            @Query("email") String email
    );

    @GET("produk/edit_produk")
    Call<BaseResponse> editProduk(
            @Query("id_produk") String idProduk,
            @Query("nama") String nama,
            @Query("deskripsi") String deskripsi,
            @Query("stok") String stok,
            @Query("harga_satuan") String harga_satuan,
            @Query("berat") String berat,
            @Query("kategori") String kategori
    );

    @GET("keranjang/get_keranjang")
    Call<KeranjangResponse> getKeranjang(
            @Query("id_pengguna") String idPengguna
    );

    @GET("keranjang/hapus")
    Call<BaseResponse> hapusKeranjang(
            @Query("id_keranjang") String idKeranjang
    );

    @FormUrlEncoded
    @POST("keranjang/simpan")
    Call<BaseResponse> simpanKeranjang(
            @Field("id_pengguna") String idPengguna,
            @Field("id_produk") String idProduk
    );

    @GET("transaksi/pesanan_sampai")
    Call<BaseResponse> pesananSampai(
            @Query("id_transaksi") String idTransaksi
    );

    @GET("produk/update_stok")
    Call<BaseResponse> updateStok(
            @Query("id_produk") String idProduk,
            @Query("stok") String stok
    );

    @GET("transaksi/get_tr_berlangsung_pengrajin")
    Call<TransaksiResponse> getTrBerlangsungPengrajin(
            @Query("id_pengguna") String idPengguna
    );

    @GET("transaksi/get_tr_selesai_pengrajin")
    Call<TransaksiResponse> getTrSelesaiPengrajin(
            @Query("id_pengguna") String idPengguna
    );
}
