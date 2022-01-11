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

        @SerializedName("no_telp")
        public String noTelp;

        public PenggunaModel(String idPengguna, String peran, String email, String password, String tanggalDibuat, String status, String foto, String alamat, String username, String noTelp) {
            this.idPengguna = idPengguna;
            this.peran = peran;
            this.email = email;
            this.password = password;
            this.tanggalDibuat = tanggalDibuat;
            this.status = status;
            this.foto = foto;
            this.alamat = alamat;
            this.username = username;
            this.noTelp = noTelp;
        }

        public String getIdPengguna() {
            return idPengguna;
        }

        public void setIdPengguna(String idPengguna) {
            this.idPengguna = idPengguna;
        }

        public String getPeran() {
            return peran;
        }

        public void setPeran(String peran) {
            this.peran = peran;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTanggalDibuat() {
            return tanggalDibuat;
        }

        public void setTanggalDibuat(String tanggalDibuat) {
            this.tanggalDibuat = tanggalDibuat;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNoTelp() {
            return noTelp;
        }

        public void setNoTelp(String noTelp) {
            this.noTelp = noTelp;
        }
    }
}