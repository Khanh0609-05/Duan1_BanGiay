/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 *
 * @author nguye
 */
public class HoaDonChiTiet {
   private String maSanPham;
   private String tenSanPham;
   private double donGia;
   private int soLuong;
   private double thanhTien;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(String maSanPham, String tenSanPham, double donGia, int soLuong, double thanhTien) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

   
    
     public Object[] toDataRow(){
    DecimalFormat formatter = new DecimalFormat("#,###");
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    symbols.setGroupingSeparator('.'); // Dùng dấu chấm làm phân cách hàng nghìn
    formatter.setDecimalFormatSymbols(symbols);

    // Định dạng Đơn Giá và Thành Tiền
    String formattedDonGia = formatter.format(this.donGia) + "đ";
    String formattedThanhTien = formatter.format(this.soLuong * this.donGia) + "đ";
        return new Object[]{
          this.maSanPham,
        this.tenSanPham,
        formattedDonGia,  // Định dạng Đơn Giá
        this.soLuong,
        formattedThanhTien // Định dạng Thành Tiền
                
        };
    }
}
