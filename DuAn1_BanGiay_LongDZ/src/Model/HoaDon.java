/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author nguye
 */
public class HoaDon {
    private String maHoaDon; 
    private String maKhachHang; 
    private String maNhanVien;
    private String maPhieuGiamGia; 
    private LocalDateTime ngayTao;
    private double tongTien;
    private double giamGia;
    private double thanhToan;

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, String maKhachHang, String maNhanVien, String maPhieuGiamGia, LocalDateTime ngayTao, double tongTien, double giamGia, double thanhToan) {
        this.maHoaDon = maHoaDon;
        this.maKhachHang = maKhachHang;
        this.maNhanVien = maNhanVien;
        this.maPhieuGiamGia = maPhieuGiamGia;
        this.ngayTao = ngayTao;
        this.tongTien = tongTien;
        this.giamGia = giamGia;
        this.thanhToan = thanhToan;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaPhieuGiamGia() {
        return maPhieuGiamGia;
    }

    public void setMaPhieuGiamGia(String maPhieuGiamGia) {
        this.maPhieuGiamGia = maPhieuGiamGia;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public double getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(double thanhToan) {
        this.thanhToan = thanhToan;
    }
    
    
    public Object[] toDataRow(){
       DecimalFormat formatter = new DecimalFormat("#,###");
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    symbols.setGroupingSeparator('.'); // Dùng dấu chấm làm phân cách hàng nghìn
    formatter.setDecimalFormatSymbols(symbols);

    // Định dạng Tổng Tiền, Giảm Giá, và Thanh Tiền
    String formattedTongTien = formatter.format(this.tongTien) + "đ";
    String formattedGiamGia = formatter.format(this.giamGia) + "đ";
    String formattedThanhToan = formatter.format(this.thanhToan) + "đ";

    return new Object[]{
        this.maHoaDon,
        this.maKhachHang,
        this.maNhanVien,
        this.maPhieuGiamGia,
        this.ngayTao,
        formattedTongTien,  // Định dạng Tổng Tiền
        formattedGiamGia,   // Định dạng Giảm Giá
        formattedThanhToan  // Định dạng Thanh Tiền
    };
    }
}
