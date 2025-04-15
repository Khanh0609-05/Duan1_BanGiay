package Repo;

import Model.PhieuGiamGia;
import Util.DBConnection;
import java.util.ArrayList;
import java.sql.*;
import java.util.Date;

public class PhieuGiamGiaRepo {

    private Connection conn = null;

    public PhieuGiamGiaRepo() {
        conn = DBConnection.getConnection();
    }

    public ArrayList<PhieuGiamGia> getAll() {
        String sql = "SELECT * FROM PhieuGiamGia";
        ArrayList<PhieuGiamGia> danhSach = new ArrayList<>();
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                danhSach.add(new PhieuGiamGia(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(8),
                        rs.getDate(9),
                        rs.getBoolean(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getBoolean(12)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public int xoaPGG(String id) {
        String sql = "DELETE FROM [dbo].[PhieuGiamGia] WHERE ID = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int suaPGG(PhieuGiamGia pgg) {
        // Không cập nhật MaPhieuGiamGia
        String sql = "UPDATE PhieuGiamGia SET TenPhieuGiamGia = ?, NgayBatDau = ?, NgayKetThuc = ?, KieuGiam = ?, MucGiam = ?, MucGiamToiDa = ?, HoaDonToiThieu = ?, SoLuong = ?, DaDung = ?, TrangThai = ? WHERE ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pgg.getTenPhieuGiamGia());
            ps.setDate(2, new java.sql.Date(pgg.getNgayBatDau().getTime()));
            ps.setDate(3, new java.sql.Date(pgg.getNgayKetThuc().getTime()));
            ps.setBoolean(4, pgg.isKieuGiam());
            ps.setDouble(5, pgg.getMucGiam());
            ps.setDouble(6, pgg.getMucGiamToiDa());
            ps.setDouble(7, pgg.getHoaDonToiThieu());
            ps.setInt(8, pgg.getSoLuong());
            ps.setInt(9, pgg.getDaDung());
            ps.setBoolean(10, pgg.isTrangThai());
            ps.setInt(11, pgg.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int themPGG(PhieuGiamGia pgg) {
        // Không chèn MaPhieuGiamGia vì trigger sẽ tự sinh
        String sql = "INSERT INTO [dbo].[PhieuGiamGia] ([TenPhieuGiamGia], [NgayBatDau], [NgayKetThuc], [KieuGiam], [MucGiam], [MucGiamToiDa], [HoaDonToiThieu], [SoLuong], [DaDung], [TrangThai]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pgg.getTenPhieuGiamGia());
            ps.setDate(2, new java.sql.Date(pgg.getNgayBatDau().getTime()));
            ps.setDate(3, new java.sql.Date(pgg.getNgayKetThuc().getTime()));
            ps.setBoolean(4, pgg.isKieuGiam());
            ps.setDouble(5, pgg.getMucGiam());
            ps.setDouble(6, pgg.getMucGiamToiDa());
            ps.setDouble(7, pgg.getHoaDonToiThieu());
            ps.setInt(8, pgg.getSoLuong());
            ps.setInt(9, pgg.getDaDung());
            ps.setBoolean(10, pgg.isTrangThai());
            return ps.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
            return 0;
        }
    }

    // Tìm kiếm theo tất cả các trường trừ kiểu giảm và trạng thái
    public ArrayList<PhieuGiamGia> search(String search) {
        ArrayList<PhieuGiamGia> list = new ArrayList<>();
        String sql = """
                 SELECT [ID], [MaPhieuGiamGia], [TenPhieuGiamGia], [KieuGiam], [MucGiam], 
                        [MucGiamToiDa], [HoaDonToiThieu], [NgayBatDau], [NgayKetThuc], 
                        [SoLuong], [DaDung], [TrangThai]
                 FROM PhieuGiamGia
                 WHERE CAST([ID] AS NVARCHAR) LIKE ?
                    OR [MaPhieuGiamGia] LIKE ?
                    OR [TenPhieuGiamGia] LIKE ?
                    OR CAST([MucGiam] AS NVARCHAR) LIKE ?
                    OR CAST([MucGiamToiDa] AS NVARCHAR) LIKE ?
                    OR CAST([HoaDonToiThieu] AS NVARCHAR) LIKE ?
                    OR CONVERT(NVARCHAR, [NgayBatDau], 23) LIKE ?
                    OR CONVERT(NVARCHAR, [NgayKetThuc], 23) LIKE ?
                    OR CAST([SoLuong] AS NVARCHAR) LIKE ?
                    OR CAST([DaDung] AS NVARCHAR) LIKE ?
                 """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Gán giá trị cho các tham số
            ps.setString(1, "%" + search + "%");  // ID
            ps.setString(2, search + "%");        // MaPhieuGiamGia (bắt đầu bằng)
            ps.setString(3, "%" + search + "%");  // TenPhieuGiamGia
            ps.setString(4, "%" + search + "%");  // MucGiam
            ps.setString(5, "%" + search + "%");  // MucGiamToiDa
            ps.setString(6, "%" + search + "%");  // HoaDonToiThieu
            ps.setString(7, "%" + search + "%");  // NgayBatDau 
            ps.setString(8, "%" + search + "%");  // NgayKetThuc 
            ps.setString(9, "%" + search + "%");  // SoLuong
            ps.setString(10, "%" + search + "%"); // DaDung

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuGiamGia pgg = new PhieuGiamGia(
                            rs.getInt("ID"),
                            rs.getString("MaPhieuGiamGia"),
                            rs.getString("TenPhieuGiamGia"),
                            rs.getDate("NgayBatDau"),
                            rs.getDate("NgayKetThuc"),
                            rs.getBoolean("KieuGiam"),
                            rs.getDouble("MucGiam"),
                            rs.getDouble("MucGiamToiDa"),
                            rs.getDouble("HoaDonToiThieu"),
                            rs.getInt("SoLuong"),
                            rs.getInt("DaDung"),
                            rs.getBoolean("TrangThai")
                    );
                    list.add(pgg);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public PhieuGiamGia getById(String id) {
        String sql = "SELECT * FROM PhieuGiamGia WHERE ID = ?";
        PhieuGiamGia pgg = new PhieuGiamGia(0, id, id, null, null, true, 0, 0, 0, 0, 0, true);
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pgg = new PhieuGiamGia(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(8),
                        rs.getDate(9),
                        rs.getBoolean(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getBoolean(12)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pgg;
    }
}
