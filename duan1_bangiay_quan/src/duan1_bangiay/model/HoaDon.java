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
import java.time.LocalDateTime;

public class HoaDon {

    private String maHoaDon; // Mã hóa đơn
    private String tenKhachHang; // Tên khách hàng
    private String tenNhanVien; // Tên nhân viên
    private LocalDateTime ngayTao; // Ngày tạo
    private BigDecimal thanhTien; // Thành tiền
    private boolean trangThai; // Trạng thái (đã thanh toán/chưa thanh toán)

    // Constructor
    public HoaDon(String maHoaDon, String tenKhachHang, String tenNhanVien, LocalDateTime ngayTao, BigDecimal thanhTien, boolean trangThai) {
        this.maHoaDon = maHoaDon;
        this.tenKhachHang = tenKhachHang;
        this.tenNhanVien = tenNhanVien;
        this.ngayTao = ngayTao;
        this.thanhTien = thanhTien;
        this.trangThai = trangThai;
    }

    // Default Constructor
    public HoaDon() {
    }

    // Getters and Setters
    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public LocalDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    // Override toString() for debugging/logging
    @Override
    public String toString() {
        return "HoaDon{"
                + "maHoaDon='" + maHoaDon + '\''
                + ", tenKhachHang='" + tenKhachHang + '\''
                + ", tenNhanVien='" + tenNhanVien + '\''
                + ", ngayTao=" + ngayTao
                + ", thanhTien=" + thanhTien
                + ", trangThai=" + (trangThai ? "Đã thanh toán" : "Chưa thanh toán")
                + '}';
    }
}
