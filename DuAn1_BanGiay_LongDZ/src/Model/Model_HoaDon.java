/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author nguye
 */
public class Model_HoaDon {
    private String maHoaDon; 
    private String maKhachHang; 
    private String maNhanVien;
    private String maPhieuGiamGia; 
    private Date thoiGian;
    private BigDecimal tongTien;
    private BigDecimal giamGia;
    private BigDecimal thanhToan;

    public Model_HoaDon() {
    }

    public Model_HoaDon(String maHoaDon, String maKhachHang, String maNhanVien, String maPhieuGiamGia, Date thoiGian, BigDecimal tongTien, BigDecimal giamGia, BigDecimal thanhToan) {
        this.maHoaDon = maHoaDon;
        this.maKhachHang = maKhachHang;
        this.maNhanVien = maNhanVien;
        this.maPhieuGiamGia = maPhieuGiamGia;
        this.thoiGian = thoiGian;
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

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public BigDecimal getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(BigDecimal giamGia) {
        this.giamGia = giamGia;
    }

    public BigDecimal getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(BigDecimal thanhToan) {
        this.thanhToan = thanhToan;
    }

    
    
    public Object[] toDataRow(){
        return new Object[]{
            this.maHoaDon,this.maKhachHang,this.maNhanVien,this.maPhieuGiamGia,this.thoiGian,this.tongTien,this.giamGia,this.thanhToan
                
        };
    }
}
