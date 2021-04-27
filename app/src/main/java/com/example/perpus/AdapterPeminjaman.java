package com.example.perpus;

public class AdapterPeminjaman {

    private String Nama;
    private String NoTelp;
    private String Buku;
    private String No_urut;
    private String Tgl_pinjam;
    private String Tgl_kembali;

    public AdapterPeminjaman(String nama, String noTelp, String buku, String no_urut, String tgl_pinjam, String tgl_kembali) {
        Nama = nama;
        NoTelp = noTelp;
        Buku = buku;
        No_urut = no_urut;
        Tgl_pinjam = tgl_pinjam;
        Tgl_kembali = tgl_kembali;
    }

    public AdapterPeminjaman() {
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getNoTelp() {
        return NoTelp;
    }

    public void setNoTelp(String noTelp) {
        NoTelp = noTelp;
    }

    public String getBuku() {
        return Buku;
    }

    public void setBuku(String buku) {
        Buku = buku;
    }

    public String getNo_urut() {
        return No_urut;
    }

    public void setNo_urut(String no_urut) {
        No_urut = no_urut;
    }

    public String getTgl_pinjam() {
        return Tgl_pinjam;
    }

    public void setTgl_pinjam(String tgl_pinjam) {
        Tgl_pinjam = tgl_pinjam;
    }

    public String getTgl_kembali() {
        return Tgl_kembali;
    }

    public void setTgl_kembali(String tgl_kembali) {
        Tgl_kembali = tgl_kembali;
    }
}