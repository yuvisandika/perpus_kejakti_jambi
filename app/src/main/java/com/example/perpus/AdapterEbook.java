package com.example.perpus;

public class AdapterEbook {

    private String Nama;
    private String FileUrl;

    public AdapterEbook(String nama, String fileUrl) {
        Nama = nama;
        FileUrl = fileUrl;
    }

    public AdapterEbook() {
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String fileUrl) {
        FileUrl = fileUrl;
    }
}
