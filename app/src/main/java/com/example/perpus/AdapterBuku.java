package com.example.perpus;

public class AdapterBuku {
    private String Judul_Buku;
    private String Jenis_Bahan;
    private String No_Urut;
    private String Kepengarangan;
    private String Jilid;
    private String Edisi;
    private String Cetakan;
    private String Isbn;
    private String Issn;
    private String Tempat_Terbit;
    private String Penerbit;
    private String Tahun_Terbit;
    private String Klasifikasi;
    private String Berasal_Dari;
    private String Keterangan;
    private String ImageUrl;

    public AdapterBuku(String judul_Buku, String jenis_Bahan, String no_Urut, String kepengarangan, String jilid, String edisi, String cetakan, String isbn, String issn, String tempat_Terbit, String penerbit, String tahun_Terbit, String klasifikasi, String berasal_Dari, String keterangan, String imageUrl) {

        Judul_Buku = judul_Buku;
        Jenis_Bahan = jenis_Bahan;
        No_Urut = no_Urut;
        Kepengarangan = kepengarangan;
        Jilid = jilid;
        Edisi = edisi;
        Cetakan = cetakan;
        Isbn = isbn;
        Issn = issn;
        Tempat_Terbit = tempat_Terbit;
        Penerbit = penerbit;
        Tahun_Terbit = tahun_Terbit;
        Klasifikasi = klasifikasi;
        Berasal_Dari = berasal_Dari;
        Keterangan = keterangan;
        ImageUrl = imageUrl;
    }

    public AdapterBuku() {
    }

    public String getJudul_Buku() {
        return Judul_Buku;
    }

    public void setJudul_Buku(String judul_Buku) {
        Judul_Buku = judul_Buku;
    }

    public String getJenis_Bahan() {
        return Jenis_Bahan;
    }

    public void setJenis_Bahan(String jenis_Bahan) {
        Jenis_Bahan = jenis_Bahan;
    }

    public String getNo_Urut() {
        return No_Urut;
    }

    public void setNo_Urut(String no_Urut) {
        No_Urut = no_Urut;
    }

    public String getKepengarangan() {
        return Kepengarangan;
    }

    public void setKepengarangan(String kepengarangan) {
        Kepengarangan = kepengarangan;
    }

    public String getJilid() {
        return Jilid;
    }

    public void setJilid(String jilid) {
        Jilid = jilid;
    }

    public String getEdisi() {
        return Edisi;
    }

    public void setEdisi(String edisi) {
        Edisi = edisi;
    }

    public String getCetakan() {
        return Cetakan;
    }

    public void setCetakan(String cetakan) {
        Cetakan = cetakan;
    }

    public String getIsbn() {
        return Isbn;
    }

    public void setIsbn(String isbn) {
        Isbn = isbn;
    }

    public String getIssn() {
        return Issn;
    }

    public void setIssn(String issn) {
        Issn = issn;
    }

    public String getTempat_Terbit() {
        return Tempat_Terbit;
    }

    public void setTempat_Terbit(String tempat_Terbit) {
        Tempat_Terbit = tempat_Terbit;
    }

    public String getPenerbit() {
        return Penerbit;
    }

    public void setPenerbit(String penerbit) {
        Penerbit = penerbit;
    }

    public String getTahun_Terbit() {
        return Tahun_Terbit;
    }

    public void setTahun_Terbit(String tahun_Terbit) {
        Tahun_Terbit = tahun_Terbit;
    }

    public String getKlasifikasi() {
        return Klasifikasi;
    }

    public void setKlasifikasi(String klasifikasi) {
        Klasifikasi = klasifikasi;
    }

    public String getBerasal_Dari() {
        return Berasal_Dari;
    }

    public void setBerasal_Dari(String berasal_Dari) {
        Berasal_Dari = berasal_Dari;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public void setKeterangan(String keterangan) {
        Keterangan = keterangan;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
