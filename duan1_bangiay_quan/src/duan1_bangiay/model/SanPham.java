/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.model;

import java.math.BigDecimal;

/**
 *
 * @author Vinh
 */
public class SanPham {
    private int stt; // Serial number
    private String maSP; // Product Code
    private String tenSP; // Product Name
    private String thuongHieu; // Brand
    private BigDecimal giaBan; // Price
    private int soLuong; // Quantity
    private String size; // Size
    private String mauSac; // Color

    // Constructor
    public SanPham(int stt, String maSP, String tenSP, String thuongHieu, BigDecimal giaBan, int soLuong, String size, String mauSac) {
        this.stt = stt;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.thuongHieu = thuongHieu;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.size = size;
        this.mauSac = mauSac;
    }

    // Getters and Setters
    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(String thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    @Override
    public String toString() {
        return "SanPham{" +
                "stt=" + stt +
                ", maSP='" + maSP + '\'' +
                ", tenSP='" + tenSP + '\'' +
                ", thuongHieu='" + thuongHieu + '\'' +
                ", giaBan=" + giaBan +
                ", soLuong=" + soLuong +
                ", size='" + size + '\'' +
                ", mauSac='" + mauSac + '\'' +
                '}';
    }
}
