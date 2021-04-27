package com.example.perpus;

public class AdapterAnggota {
    private String Nama;
    private String Alamat;
    private String Email;
    private String ImageUrl;
    private String Jenis_Kelamin;
    private String Nip;
    private String Nomor_Telpon;

    public AdapterAnggota(String nama, String alamat, String email, String imageUrl, String jenis_Kelamin, String nip, String nomor_Telpon) {
        Nama = nama;
        Alamat = alamat;
        Email = email;
        ImageUrl = imageUrl;
        Jenis_Kelamin = jenis_Kelamin;
        Nip = nip;
        Nomor_Telpon = nomor_Telpon;
    }

    public AdapterAnggota() {
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getJenis_Kelamin() {
        return Jenis_Kelamin;
    }

    public void setJenis_Kelamin(String jenis_Kelamin) {
        Jenis_Kelamin = jenis_Kelamin;
    }

    public String getNip() {
        return Nip;
    }

    public void setNip(String nip) {
        Nip = nip;
    }

    public String getNomor_Telpon() {
        return Nomor_Telpon;
    }

    public void setNomor_Telpon(String nomor_Telpon) {
        Nomor_Telpon = nomor_Telpon;
    }
}
