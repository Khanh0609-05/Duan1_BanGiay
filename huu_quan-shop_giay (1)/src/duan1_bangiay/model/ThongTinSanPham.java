/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.model;

public class ThongTinSanPham {

    private int stt;
    private String maSanPham;
    private String tenSanPham;
    private String thuongHieu;
    private double giaBan;
    private int soLuong;
    private String size;
    private String mauSac;

    public ThongTinSanPham(int stt, String maSanPham, String tenSanPham, String thuongHieu, double giaBan, int soLuong, String size, String mauSac) {
        this.stt = stt;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.thuongHieu = thuongHieu;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.size = size;
        this.mauSac = mauSac;
    }

    // Getters and setters (omitted for brevity)
    public int getStt() {
        return stt;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public String getThuongHieu() {
        return thuongHieu;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public String getSize() {
        return size;
    }

    public String getMauSac() {
        return mauSac;
    }
}
