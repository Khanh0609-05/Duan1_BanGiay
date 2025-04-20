/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.repository;

import duan1_bangiay.model.NhanVien;
import duan1_bangiay.utils.DBConnect;

import java.sql.*;
import java.util.ArrayList;


public class NhanVienRepo {

    public boolean dangNhap(String maNhanVien, String matKhau) {
        String sql = "SELECT * FROM NhanVien WHERE MaNhanVien = ? AND MatKhau = ? AND TrangThai = 1";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNhanVien);
            ps.setString(2, matKhau);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<NhanVien> getAll() {
        ArrayList<NhanVien> listNV = new ArrayList<>();
        // SQL JOIN để lấy TenChucVu
        String sql = """
                     SELECT nv.ID, nv.MaNhanVien, nv.MatKhau, nv.TenNhanVien, nv.GioiTinh,
                            nv.NgaySinh, nv.SDT, nv.QueQuan, nv.IDChucVu, nv.TrangThai, cv.TenChucVu
                     FROM NhanVien nv
                     INNER JOIN ChucVu cv ON nv.IDChucVu = cv.ID
                     """;

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setId(rs.getInt("ID"));
                nv.setMaNhanVien(rs.getString("MaNhanVien"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setTenNhanVien(rs.getString("TenNhanVien"));
                nv.setGioiTinh(rs.getBoolean("GioiTinh"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setSoDienThoai(rs.getString("SDT"));
                nv.setQueQuan(rs.getString("QueQuan"));
                nv.setIdChucVu(rs.getInt("IDChucVu"));
                nv.setTrangThai(rs.getBoolean("TrangThai"));
                nv.setTenChucVu(rs.getString("TenChucVu"));

                listNV.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listNV;
    }

    public int updateNV(NhanVien nv) {
        String sql = "UPDATE NhanVien SET MaNhanVien = ?, MatKhau = ?, TenNhanVien = ?, SDT = ?, GioiTinh = ?, NgaySinh = ?, QueQuan = ?, IDChucVu = ?, TrangThai = ? WHERE ID = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nv.getMaNhanVien());
            ps.setString(2, nv.getMatKhau());
            ps.setString(3, nv.getTenNhanVien());
            ps.setString(4, nv.getSoDienThoai());
            ps.setBoolean(5, nv.isGioiTinh());
            ps.setString(6, nv.getNgaySinh());
            ps.setString(7, nv.getQueQuan());
            ps.setInt(8, nv.getIdChucVu());
            ps.setBoolean(9, nv.isTrangThai());
            ps.setInt(10, nv.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int addNV(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (MaNhanVien, MatKhau, TenNhanVien, GioiTinh, NgaySinh, SDT, QueQuan, IDChucVu, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nv.getMaNhanVien());
            ps.setString(2, nv.getMatKhau());
            ps.setString(3, nv.getTenNhanVien());
            ps.setBoolean(4, nv.isGioiTinh());
            ps.setString(5, nv.getNgaySinh());
            ps.setString(6, nv.getSoDienThoai());
            ps.setString(7, nv.getQueQuan());
            ps.setInt(8, nv.getIdChucVu());
            ps.setBoolean(9, nv.isTrangThai());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public NhanVien getNVLogin(String username, String pass) {
        String sql = """
                     SELECT nv.ID, nv.MaNhanVien, nv.TenNhanVien, nv.NgaySinh, nv.SDT, nv.GioiTinh, nv.QueQuan, nv.TrangThai, nv.IDChucVu, cv.TenChucVu
                     FROM NhanVien nv
                     JOIN ChucVu cv ON cv.ID = nv.IDChucVu
                     WHERE nv.MaNhanVien = ? AND nv.MatKhau = ?
                     """;
        NhanVien nv = null;
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nv = new NhanVien();
                nv.setId(rs.getInt("ID"));
                nv.setMaNhanVien(rs.getString("MaNhanVien"));
                nv.setTenNhanVien(rs.getString("TenNhanVien"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setSoDienThoai(rs.getString("SDT"));
                nv.setGioiTinh(rs.getBoolean("GioiTinh"));
                nv.setQueQuan(rs.getString("QueQuan"));
                nv.setTrangThai(rs.getBoolean("TrangThai"));
                nv.setIdChucVu(rs.getInt("IDChucVu"));
                nv.setTenChucVu(rs.getString("TenChucVu"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nv;
    }

    public NhanVien getNhanVienByMa(String maNhanVien) {
        String sql = """
                     SELECT nv.ID, nv.MaNhanVien, nv.MatKhau, nv.TenNhanVien, nv.GioiTinh, nv.NgaySinh, nv.SDT, nv.QueQuan, nv.IDChucVu, nv.TrangThai, cv.TenChucVu
                     FROM NhanVien nv
                     JOIN ChucVu cv ON nv.IDChucVu = cv.ID
                     WHERE nv.MaNhanVien = ?
                     """;
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNhanVien);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setId(rs.getInt("ID"));
                nv.setMaNhanVien(rs.getString("MaNhanVien"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setTenNhanVien(rs.getString("TenNhanVien"));
                nv.setGioiTinh(rs.getBoolean("GioiTinh"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setSoDienThoai(rs.getString("SDT"));
                nv.setQueQuan(rs.getString("QueQuan"));
                nv.setIdChucVu(rs.getInt("IDChucVu"));
                nv.setTrangThai(rs.getBoolean("TrangThai"));
                nv.setTenChucVu(rs.getString("TenChucVu"));
                return nv;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

   public ArrayList<NhanVien> search(String search) {
    ArrayList<NhanVien> list = new ArrayList<>();
    String sql = """
                 SELECT nv.ID, nv.MaNhanVien, nv.MatKhau, nv.TenNhanVien, nv.GioiTinh,
                        nv.NgaySinh, nv.SDT, nv.QueQuan, nv.IDChucVu, nv.TrangThai, cv.TenChucVu
                 FROM NhanVien nv
                 INNER JOIN ChucVu cv ON nv.IDChucVu = cv.ID
                 WHERE nv.MaNhanVien LIKE ? 
                    OR nv.TenNhanVien LIKE ? 
                    OR nv.SDT LIKE ? 
                    OR nv.QueQuan LIKE ? 
                    OR nv.MatKhau LIKE ? 
                    OR CAST(nv.ID AS VARCHAR) LIKE ? 
                    OR CAST(nv.IDChucVu AS VARCHAR) LIKE ? 
                    OR CAST(nv.NgaySinh AS VARCHAR) LIKE ? 
                    OR (CASE WHEN nv.GioiTinh = 1 THEN 'Nam' ELSE 'Nữ' END) LIKE ? 
                    OR (CASE WHEN nv.TrangThai = 1 THEN 'Đang làm' ELSE 'Đã nghỉ' END) LIKE ? 
                    OR cv.TenChucVu LIKE ?
                 """;
    try (Connection con = DBConnect.getConnection(); 
         PreparedStatement ps = con.prepareStatement(sql)) {
        String searchPattern = "%" + search + "%";
        for (int i = 1; i <= 11; i++) {
            ps.setString(i, searchPattern); // Gán từ khóa cho tất cả 11 tham số
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setId(rs.getInt("ID"));
                nv.setMaNhanVien(rs.getString("MaNhanVien"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setTenNhanVien(rs.getString("TenNhanVien"));
                nv.setGioiTinh(rs.getBoolean("GioiTinh"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setSoDienThoai(rs.getString("SDT"));
                nv.setQueQuan(rs.getString("QueQuan"));
                nv.setIdChucVu(rs.getInt("IDChucVu"));
                nv.setTrangThai(rs.getBoolean("TrangThai"));
                nv.setTenChucVu(rs.getString("TenChucVu"));
                list.add(nv);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

    public boolean updateTrangThaiNhanVien(String idNhanVien) {
        String sql = "UPDATE NhanVien SET TrangThai = 0 WHERE ID = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(idNhanVien));
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<NhanVien> getActiveNhanVien() {
        ArrayList<NhanVien> listNV = new ArrayList<>();
        String sql = """
                     SELECT nv.ID, nv.MaNhanVien, nv.MatKhau, nv.TenNhanVien, nv.GioiTinh, nv.NgaySinh,
                            nv.SDT, nv.QueQuan, nv.IDChucVu, nv.TrangThai, cv.TenChucVu
                     FROM NhanVien nv
                     INNER JOIN ChucVu cv ON nv.IDChucVu = cv.ID
                     WHERE nv.TrangThai = 1
                     """;

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setId(rs.getInt("ID"));
                nv.setMaNhanVien(rs.getString("MaNhanVien"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setTenNhanVien(rs.getString("TenNhanVien"));
                nv.setGioiTinh(rs.getBoolean("GioiTinh"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setSoDienThoai(rs.getString("SDT"));
                nv.setQueQuan(rs.getString("QueQuan"));
                nv.setIdChucVu(rs.getInt("IDChucVu"));
                nv.setTrangThai(rs.getBoolean("TrangThai"));
                nv.setTenChucVu(rs.getString("TenChucVu"));
                listNV.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listNV;
    }

    public boolean isPhoneNumberExists(String soDienThoai, int excludeId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE SDT = ? AND ID != ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, soDienThoai);
            ps.setInt(2, excludeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
//            e.printStackTrace();
            throw new SQLException("Số điện thoại đã trùng" + e.getMessage(), e);
        }
        return false;
    }
}