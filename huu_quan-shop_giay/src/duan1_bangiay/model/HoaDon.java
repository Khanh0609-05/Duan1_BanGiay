package duan1_bangiay.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HoaDon {

    private int id; // ID của hóa đơn (thêm để lưu trữ ID của hóa đơn)
    private int idPhieuGiamGia; // Foreign key - ID phiếu giảm giá
    private String maHoaDon; // Mã hóa đơn (Unique identifier)
    private String maNhanVien; // Mã nhân viên tạo hóa đơn
    private String maKhachHang; // Mã khách hàng
    private BigDecimal tongTien; // Tổng tiền của hóa đơn
    private BigDecimal giamGia; // Giảm giá
    private LocalDateTime ngayTao; // Ngày tạo hóa đơn
    private BigDecimal thanhTien; // Số tiền cuối cùng sau giảm giá
    private boolean trangThai; // Trạng thái hóa đơn (chưa thanh toán/đã thanh toán)

    // Constructors
    public HoaDon() {
    }

    public HoaDon(int id, int idPhieuGiamGia, String maHoaDon, String maNhanVien, String maKhachHang,
                  BigDecimal tongTien, BigDecimal giamGia, LocalDateTime ngayTao, BigDecimal thanhTien, boolean trangThai) {
        this.id = id;
        this.idPhieuGiamGia = idPhieuGiamGia;
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
        this.tongTien = tongTien;
        this.giamGia = giamGia;
        this.ngayTao = ngayTao;
        this.thanhTien = thanhTien;
        this.trangThai = trangThai;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPhieuGiamGia() {
        return idPhieuGiamGia;
    }

    public void setIdPhieuGiamGia(int idPhieuGiamGia) {
        this.idPhieuGiamGia = idPhieuGiamGia;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
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

    @Override
    public String toString() {
        return "HoaDon{" +
                "id=" + id +
                ", idPhieuGiamGia=" + idPhieuGiamGia +
                ", maHoaDon='" + maHoaDon + '\'' +
                ", maNhanVien='" + maNhanVien + '\'' +
                ", maKhachHang='" + maKhachHang + '\'' +
                ", tongTien=" + tongTien +
                ", giamGia=" + giamGia +
                ", ngayTao=" + ngayTao +
                ", thanhTien=" + thanhTien +
                ", trangThai=" + trangThai +
                '}';
    }
}
