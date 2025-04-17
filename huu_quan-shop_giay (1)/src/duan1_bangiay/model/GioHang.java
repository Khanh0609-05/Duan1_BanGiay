/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.model;

import java.math.BigDecimal;

public class GioHang {

    private int id; // Primary key
    private int idSanPham; // Foreign key
    private int idHoaDon; // Foreign key
    private int soLuong; // Quantity
    private BigDecimal donGia; // Unit price
    private boolean trangThai; // Status (active/inactive)

    // Constructors
    public GioHang() {
    }

    public GioHang(int id, int idSanPham, int idHoaDon, int soLuong, BigDecimal donGia, boolean trangThai) {
        this.id = id;
        this.idSanPham = idSanPham;
        this.idHoaDon = idHoaDon;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.trangThai = trangThai;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public int getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(int idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{"
                + "id=" + id
                + ", idSanPham=" + idSanPham
                + ", idHoaDon=" + idHoaDon
                + ", soLuong=" + soLuong
                + ", donGia=" + donGia
                + ", trangThai=" + trangThai
                + '}';
    }
}
