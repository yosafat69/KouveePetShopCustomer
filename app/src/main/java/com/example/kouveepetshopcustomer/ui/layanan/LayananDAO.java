package com.example.kouveepetshopcustomer.ui.layanan;

public class LayananDAO
{
    public String nama, Ukuran, link_gambar;
    public int harga;

    public String getNama() {
        return nama;
    }

    public String getUkuran() {
        return Ukuran;
    }

    public int getHarga() {
        return harga;
    }

    public String getLink_gambar() {
        return link_gambar;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setUkuran(String Ukuran) {
        this.Ukuran = Ukuran;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public void setLink_gambar(String link_gambar) {
        this.link_gambar = link_gambar;
    }
}
