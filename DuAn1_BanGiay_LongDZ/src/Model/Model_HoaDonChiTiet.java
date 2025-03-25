/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;

/**
 *
 * @author nguye
 */
public class Model_HoaDonChiTiet {
   private String maSanPham;
   private String maHoaDon;
   private int soLuong;
   private BigDecimal donGia;
   private boolean trangThai;

    public Model_HoaDonChiTiet() {
    }

    public Model_HoaDonChiTiet(String maSanPham, String maHoaDon, int soLuong, BigDecimal donGia, boolean trangThai) {
        this.maSanPham = maSanPham;
        this.maHoaDon = maHoaDon;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.trangThai = trangThai;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
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
    
     public Object[] toDataRow(){
        return new Object[]{
            this.maSanPham,this.maHoaDon,this.soLuong,this.donGia,this.isTrangThai()
                
        };
    }
}
