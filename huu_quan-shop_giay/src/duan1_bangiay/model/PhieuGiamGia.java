/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.model;

/**
 *
 * @author Vinh
 */
import java.math.BigDecimal;

public class PhieuGiamGia {

    private String maPhieuGiamGia; // Voucher Code
    private String tenPhieuGiamGia; // Voucher Name
    private String kieuGiam; // Discount Type ('VND' or '%')
    private BigDecimal mucGiam; // Discount Amount

    // Getters and Setters
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

    public String getKieuGiam() {
        return kieuGiam;
    }

    public void setKieuGiam(String kieuGiam) {
        this.kieuGiam = kieuGiam;
    }

    public BigDecimal getMucGiam() {
        return mucGiam;
    }

    public void setMucGiam(BigDecimal mucGiam) {
        this.mucGiam = mucGiam;
    }
}
