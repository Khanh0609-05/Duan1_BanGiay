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
public class PhieuGiamGiaRepo {

    public List<PhieuGiamGia> getActivePhieuGiamGia() {
        List<PhieuGiamGia> phieuGiamGiaList = new ArrayList<>();
        String query = "SELECT ID, MaPhieuGiamGia, TenPhieuGiamGia, kieuGiam, mucGiam, mucGiamToiDa, "
                + "hoaDonToiThieu, soLuong, daDung, ngayBatDau, ngayKetThuc, trangThai "
                + "FROM PhieuGiamGia ";
//           + "WHERE trangThai = 1 AND soLuong > daDung";

        try (Connection connection = DBConnect.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                PhieuGiamGia phieuGiamGia = new PhieuGiamGia();
                phieuGiamGia.setId(resultSet.getInt("ID"));
                phieuGiamGia.setMaPhieuGiamGia(resultSet.getString("MaPhieuGiamGia"));
                phieuGiamGia.setTenPhieuGiamGia(resultSet.getString("TenPhieuGiamGia"));
                String kieuGiamStr = resultSet.getString("kieuGiam");
                phieuGiamGia.setKieuGiam(kieuGiamStr != null && kieuGiamStr.equals("%"));
                phieuGiamGia.setMucGiam(resultSet.getDouble("mucGiam"));
                phieuGiamGia.setMucGiamToiDa(resultSet.getDouble("mucGiamToiDa"));
                phieuGiamGia.setHoaDonToiThieu(resultSet.getDouble("hoaDonToiThieu"));
                phieuGiamGia.setSoLuong(resultSet.getInt("soLuong"));
                phieuGiamGia.setDaDung(resultSet.getInt("daDung"));
                phieuGiamGia.setNgayBatDau(resultSet.getDate("ngayBatDau"));
                phieuGiamGia.setNgayKetThuc(resultSet.getDate("ngayKetThuc"));
                phieuGiamGia.setTrangThai(resultSet.getBoolean("trangThai"));

                phieuGiamGiaList.add(phieuGiamGia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phieuGiamGiaList;
    }
   
    
    public double getHoaDonToiThieu(String maPhieuGiamGia) {
        String sql = "SELECT hoaDonToiThieu FROM PhieuGiamGia WHERE MaPhieuGiamGia = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieuGiamGia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("hoaDonToiThieu");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public Double getMucGiamToiDa(String maPhieuGiamGia) {
        String sql = "SELECT mucGiamToiDa FROM PhieuGiamGia WHERE MaPhieuGiamGia = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhieuGiamGia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("mucGiamToiDa");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Integer getIdPhieuGiamGia(String maPhieuGiamGia) {
        String sql = "SELECT ID FROM PhieuGiamGia WHERE MaPhieuGiamGia = ?";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maPhieuGiamGia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
