/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.repository;

import duan1_bangiay.model.PhieuGiamGia;
import duan1_bangiay.utils.DBConnect;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Vinh
 */
public class PhieuGiamGiaRepository {

    public List<PhieuGiamGia> getActivePhieuGiamGia() {
        List<PhieuGiamGia> phieuGiamGiaList = new ArrayList<>();
        String query = "SELECT MaPhieuGiamGia, TenPhieuGiamGia, kieuGiam, mucGiam "
                + "FROM PhieuGiamGia "
                + "WHERE TrangThai = 1 AND SoLuong > 0 AND GETDATE() BETWEEN NgayBatDau AND NgayKetThuc";

        try (Connection connection = DBConnect.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                PhieuGiamGia phieuGiamGia = new PhieuGiamGia();
                phieuGiamGia.setMaPhieuGiamGia(resultSet.getString("MaPhieuGiamGia"));
                phieuGiamGia.setTenPhieuGiamGia(resultSet.getString("TenPhieuGiamGia"));
                phieuGiamGia.setKieuGiam(resultSet.getString("kieuGiam"));
                phieuGiamGia.setMucGiam(resultSet.getBigDecimal("mucGiam"));

                phieuGiamGiaList.add(phieuGiamGia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phieuGiamGiaList;
    }
}
