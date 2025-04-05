/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.repository;

import duan1_bangiay.model.HoaDon;
import duan1_bangiay.utils.DBConnect;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Vinh
 */
public class HoaDonRepository {

    public List<HoaDon> getHoaDonChuaThanhToan() {
        List<HoaDon> unpaidInvoices = new ArrayList<>();
        String sql = "SELECT MaHoaDon, MaKhachHang, MaNhanVien, IDPhieuGiamGia, NgayTao, TongTien "
                + "FROM HoaDon "
                + "JOIN KhachHang ON HoaDon.IDKhachHang = KhachHang.ID "
                + "JOIN NhanVien ON HoaDon.IDNhanVien = NhanVien.ID "
                + "WHERE HoaDon.TrangThai = 0"; // TrangThai = 0 for "Chưa thanh toán"

        try (Connection connection = DBConnect.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(resultSet.getString("MaHoaDon"));
                hoaDon.setMaKhachHang(resultSet.getString("MaKhachHang"));
                hoaDon.setMaNhanVien(resultSet.getString("MaNhanVien"));
                hoaDon.setIdPhieuGiamGia(resultSet.getInt("IDPhieuGiamGia"));
                hoaDon.setNgayTao(resultSet.getTimestamp("NgayTao").toLocalDateTime());
                hoaDon.setTongTien(resultSet.getBigDecimal("TongTien"));
                unpaidInvoices.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return unpaidInvoices;
    }

    public List<HoaDon> getHoaDonDaThanhToan() {
        List<HoaDon> paidInvoices = new ArrayList<>();
        String sql = "SELECT MaHoaDon, MaKhachHang, MaNhanVien, IDPhieuGiamGia, NgayTao, TongTien "
                + "FROM HoaDon "
                + "JOIN KhachHang ON HoaDon.IDKhachHang = KhachHang.ID "
                + "JOIN NhanVien ON HoaDon.IDNhanVien = NhanVien.ID "
                + "WHERE HoaDon.TrangThai = 1"; // TrangThai = 1 for "Đã thanh toán"

        try (Connection connection = DBConnect.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(resultSet.getString("MaHoaDon"));
                hoaDon.setMaKhachHang(resultSet.getString("MaKhachHang"));
                hoaDon.setMaNhanVien(resultSet.getString("MaNhanVien"));
                hoaDon.setIdPhieuGiamGia(resultSet.getInt("IDPhieuGiamGia"));
                hoaDon.setNgayTao(resultSet.getTimestamp("NgayTao").toLocalDateTime());
                hoaDon.setTongTien(resultSet.getBigDecimal("TongTien"));
                paidInvoices.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paidInvoices;
    }
    
}
