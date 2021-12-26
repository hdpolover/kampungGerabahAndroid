package com.nurali.kampunggerabah.api.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PenggunaResponse extends BaseResponse {
    @SerializedName("data")
    public List<PenggunaModel> data;

    public static class PenggunaModel {
        @SerializedName("id_pengguna")
        public String idPengguna;

        @SerializedName("peran")
        public String peran;

        @SerializedName("email")
        public String email;

        @SerializedName("password")
        public String password;

        @SerializedName("tgl_daftar")
        public String tanggalDibuat;

        @SerializedName("status")
        public String status;

        @SerializedName("foto")
        public String foto;

        @SerializedName("alamat")
        public String alamat;

        @SerializedName("username")
        public String username;

    }
}