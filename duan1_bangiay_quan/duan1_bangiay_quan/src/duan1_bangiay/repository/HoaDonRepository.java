/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.repository;

import duan1_bangiay.model.HoaDon;
import duan1_bangiay.utils.DBConnect;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Vinh
 */
public class HoaDonRepository {

    public List<HoaDon> getUnpaidInvoices() {
        List<HoaDon> hoaDonList = new ArrayList<>();
        String query = "SELECT hd.ID, kh.TenKhachHang, nv.TenNhanVien, hd.NgayTao, hd.ThanhTien, hd.TrangThai "
                + "FROM HoaDon hd "
                + "JOIN NhanVien nv ON hd.IDNhanVien = nv.ID "
                + "JOIN KhachHang kh ON hd.IDKhachHang = kh.ID "
                + "WHERE hd.TrangThai = 0"; // TrangThai = 0 means unpaid

        try (Connection connection = DBConnect.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(resultSet.getString("ID"));
                hoaDon.setTenKhachHang(resultSet.getString("TenKhachHang"));
                hoaDon.setTenNhanVien(resultSet.getString("TenNhanVien"));
                hoaDon.setNgayTao(resultSet.getTimestamp("NgayTao"));
                hoaDon.setThanhTien(resultSet.getBigDecimal("ThanhTien"));
                hoaDon.setTrangThai(resultSet.getBoolean("TrangThai"));

                hoaDonList.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoaDonList;
    }

    public List<HoaDon> getPaidInvoices() {
        List<HoaDon> hoaDonList = new ArrayList<>();
        String query = "SELECT hd.ID, kh.TenKhachHang, nv.TenNhanVien, hd.NgayTao, hd.ThanhTien, hd.TrangThai "
                + "FROM HoaDon hd "
                + "JOIN NhanVien nv ON hd.IDNhanVien = nv.ID "
                + "JOIN KhachHang kh ON hd.IDKhachHang = kh.ID "
                + "WHERE hd.TrangThai = 1"; // TrangThai = 0 means unpaid

        try (Connection connection = DBConnect.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(resultSet.getString("ID"));
                hoaDon.setTenKhachHang(resultSet.getString("TenKhachHang"));
                hoaDon.setTenNhanVien(resultSet.getString("TenNhanVien"));
                hoaDon.setNgayTao(resultSet.getTimestamp("NgayTao"));
                hoaDon.setThanhTien(resultSet.getBigDecimal("ThanhTien"));
                hoaDon.setTrangThai(resultSet.getBoolean("TrangThai"));

                hoaDonList.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoaDonList;
    }
}
