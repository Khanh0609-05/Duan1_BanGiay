/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.model;

import java.util.Date;

/**
 *
 * @author admin
 */
public class PhieuGiamGia {

    private int id;
    private String maPhieuGiamGia;
    private String tenPhieuGiamGia;
    private Date ngayBatDau; 
    private Date ngayKetThuc; 
    private boolean kieuGiam;
    private double mucGiam;
    private double mucGiamToiDa;
    private double hoaDonToiThieu;
    private int soLuong;
    private int daDung;
    private boolean trangThai;

    public PhieuGiamGia() {
    }

    public PhieuGiamGia(int id, String maPhieuGiamGia, String tenPhieuGiamGia, Date ngayBatDau, Date ngayKetThuc,
            boolean kieuGiam, double mucGiam, double mucGiamToiDa, double hoaDonToiThieu,
            int soLuong, int daDung, boolean trangThai) {
        this.id = id;
        this.maPhieuGiamGia = maPhieuGiamGia;
        this.tenPhieuGiamGia = tenPhieuGiamGia;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.kieuGiam = kieuGiam;
        this.mucGiam = mucGiam;
        this.mucGiamToiDa = mucGiamToiDa;
        this.hoaDonToiThieu = hoaDonToiThieu;
        this.soLuong = soLuong;
        this.daDung = daDung;
        this.trangThai = trangThai;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaPhieuGiamGia() {
        return maPhieuGiamGia;
    }

    public void setMaPhieuGiamGia(String maPhieuGiamGia) {
        this.maPhieuGiamGia = maPhieuGiamGia;
    }

    public String getTenPhieuGiamGia() {
        return tenPhieuGiamGia;
    }

    public void setTenPhieuGiamGia(String tenPhieuGiamGia) {
        this.tenPhieuGiamGia = tenPhieuGiamGia;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public boolean isKieuGiam() {
        return kieuGiam;
    }

    public void setKieuGiam(boolean kieuGiam) {
        this.kieuGiam = kieuGiam;
    }

    public double getMucGiam() {
        return mucGiam;
    }

    public void setMucGiam(double mucGiam) {
        this.mucGiam = mucGiam;
    }

    public double getMucGiamToiDa() {
        return mucGiamToiDa;
    }

    public void setMucGiamToiDa(double mucGiamToiDa) {
        this.mucGiamToiDa = mucGiamToiDa;
    }

    public double getHoaDonToiThieu() {
        return hoaDonToiThieu;
    }

    public void setHoaDonToiThieu(double hoaDonToiThieu) {
        this.hoaDonToiThieu = hoaDonToiThieu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDaDung() {
        return daDung;
    }

    public void setDaDung(int daDung) {
        this.daDung = daDung;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}

