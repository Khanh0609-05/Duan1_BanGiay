package duan1_bangiay.repository;

import duan1_bangiay.model.HoaDon;
import duan1_bangiay.utils.DBConnect;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Quân
 */
public class HoaDonRepo {

    public List<HoaDon> getHoaDonChuaThanhToan() {
        List<HoaDon> unpaidInvoices = new ArrayList<>();
        String sql = "SELECT hd.MaHoaDon, kh.MaKhachHang, nv.MaNhanVien, hd.IDPhieuGiamGia, pgg.MaPhieuGiamGia, hd.NgayTao, hd.TongTien, hd.GiamGia "
                + "FROM HoaDon hd "
                + "JOIN KhachHang kh ON hd.IDKhachHang = kh.ID "
                + "JOIN NhanVien nv ON hd.IDNhanVien = nv.ID "
                + "LEFT JOIN PhieuGiamGia pgg ON hd.IDPhieuGiamGia = pgg.ID "
                + "WHERE hd.TrangThai = 0"; // TrangThai = 0 for "Chưa thanh toán"

        try (Connection connection = DBConnect.getConnection(); 
             PreparedStatement ps = connection.prepareStatement(sql); 
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(resultSet.getString("MaHoaDon"));
                hoaDon.setMaKhachHang(resultSet.getString("MaKhachHang"));
                hoaDon.setMaNhanVien(resultSet.getString("MaNhanVien"));
                hoaDon.setIdPhieuGiamGia(resultSet.getInt("IDPhieuGiamGia") == 0 ? null : resultSet.getInt("IDPhieuGiamGia"));
                hoaDon.setMaPhieuGiamGia(resultSet.getString("MaPhieuGiamGia"));
                hoaDon.setNgayTao(resultSet.getTimestamp("NgayTao").toLocalDateTime());
                hoaDon.setTongTien(resultSet.getBigDecimal("TongTien"));
                hoaDon.setGiamGia(resultSet.getBigDecimal("GiamGia"));
                unpaidInvoices.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return unpaidInvoices;
    }

    public List<HoaDon> getHoaDonDaThanhToan() {
        List<HoaDon> paidInvoices = new ArrayList<>();
        String sql = "SELECT hd.MaHoaDon, kh.MaKhachHang, nv.MaNhanVien, hd.IDPhieuGiamGia, pgg.MaPhieuGiamGia, hd.NgayTao, hd.TongTien, hd.GiamGia "
                + "FROM HoaDon hd "
                + "JOIN KhachHang kh ON hd.IDKhachHang = kh.ID "
                + "JOIN NhanVien nv ON hd.IDNhanVien = nv.ID "
                + "LEFT JOIN PhieuGiamGia pgg ON hd.IDPhieuGiamGia = pgg.ID "
                + "WHERE hd.TrangThai = 1"; // TrangThai = 1 for "Đã thanh toán"

        try (Connection connection = DBConnect.getConnection(); 
             PreparedStatement ps = connection.prepareStatement(sql); 
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(resultSet.getString("MaHoaDon"));
                hoaDon.setMaKhachHang(resultSet.getString("MaKhachHang"));
                hoaDon.setMaNhanVien(resultSet.getString("MaNhanVien"));
                hoaDon.setIdPhieuGiamGia(resultSet.getInt("IDPhieuGiamGia") == 0 ? null : resultSet.getInt("IDPhieuGiamGia"));
                hoaDon.setMaPhieuGiamGia(resultSet.getString("MaPhieuGiamGia"));
                hoaDon.setNgayTao(resultSet.getTimestamp("NgayTao").toLocalDateTime());
                hoaDon.setTongTien(resultSet.getBigDecimal("TongTien"));
                hoaDon.setGiamGia(resultSet.getBigDecimal("GiamGia"));
                paidInvoices.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paidInvoices;
    }

    public void insertIntoChiTietHoaDon(String maSP, String maSP0, int soLuong, BigDecimal giaBan) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}