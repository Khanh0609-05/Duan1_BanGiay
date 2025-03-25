/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.repository;

import duan1_bangiay.model.HoaDonChiTiet;
import duan1_bangiay.utils.DBConnect;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Vinh
 */
public class HoaDonChiTietRepository {

    public List<HoaDonChiTiet> getInvoiceDetailsByHoaDonId(int idHoaDon) {
        List<HoaDonChiTiet> chiTietList = new ArrayList<>();
        String query = "SELECT sp.TenSanPham, cthd.SoLuong, cthd.DonGia, (cthd.SoLuong * cthd.DonGia) AS ThanhTien "
                + "FROM ChiTietHoaDon cthd "
                + "JOIN SanPham sp ON cthd.IDSanPham = sp.ID "
                + "WHERE cthd.IDHoaDon = ?";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idHoaDon);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                int stt = 1; // Start serial number at 1
                while (resultSet.next()) {
                    HoaDonChiTiet chiTiet = new HoaDonChiTiet();
                    chiTiet.setStt(stt++);
                    chiTiet.setTenSanPham(resultSet.getString("TenSanPham"));
                    chiTiet.setSoLuong(resultSet.getInt("SoLuong"));
                    chiTiet.setDonGia(resultSet.getBigDecimal("DonGia"));
                    chiTiet.setThanhTien(resultSet.getBigDecimal("ThanhTien"));

                    chiTietList.add(chiTiet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chiTietList;
    }
}
