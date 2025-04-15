/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.repository;
import duan1_bangiay.model.KhachHang;
import duan1_bangiay.until.DBConnec;
import java.sql.*;
import java.util.ArrayList;
public class KhachHangRepository{
    private Connection conn = null;
    public KhachHangRepository() {
        conn = DBConnec.getConnection();
    }
    public ArrayList<KhachHang> getAll() {
        String sql = "SELECT * FROM KhachHang";
        ArrayList<KhachHang> danhSach = new ArrayList<>();
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("MaKhachHang"));
                kh.setTenKhachHang(rs.getString("TenKhachHang"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setSdt(rs.getString("SDT"));
                kh.setGioiTinh(rs.getBoolean("GioiTinh"));
                danhSach.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }
    public boolean themKhachHang(KhachHang kh) {
    try {
        String sql = "INSERT INTO KhachHang (tenKhachHang, diaChi, sdt, gioiTinh) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, kh.getTenKhachHang());
        stmt.setString(2, kh.getDiaChi());
        stmt.setString(3, kh.getSdt());
        stmt.setBoolean(4, kh.isGioiTinh());
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0; // returns true if row is inserted
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false; // returns false if an error occurs
    }
}

   public boolean suaKhachHang(KhachHang khachHang) {
    try {
        String sql = "UPDATE KhachHang SET tenKhachHang = ?, sdt = ?, diaChi = ?, gioiTinh = ? WHERE maKhachHang = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, khachHang.getTenKhachHang());
        ps.setString(2, khachHang.getSdt());
        ps.setString(3, khachHang.getDiaChi());
        ps.setBoolean(4, khachHang.isGioiTinh());
        ps.setString(5, khachHang.getMaKhachHang());
        
        int rowsAffected = ps.executeUpdate();  // Kiểm tra số hàng bị ảnh hưởng
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public ArrayList<KhachHang> timKiemKhachHang(String timkiem) {
        ArrayList<KhachHang> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE TenKhachHang LIKE ? OR DiaChi LIKE ? OR SDT LIKE ? OR MaKhachHang LIKE ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            String searchKeyword = "%" + timkiem + "%";
            ps.setString(1, searchKeyword);
            ps.setString(2, searchKeyword);
            ps.setString(3, searchKeyword);
            ps.setString(4, searchKeyword);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getString("MaKhachHang"));
                kh.setTenKhachHang(rs.getString("TenKhachHang"));
                kh.setDiaChi(rs.getString("DiaChi"));
                kh.setSdt(rs.getString("SDT"));
                danhSach.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public boolean checkSdtExists(String sdt) {
        String query = "SELECT COUNT(*) FROM KhachHang WHERE sdt = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, sdt);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isSdtExist(String sdt, String maKhachHang) {
        try {
            String sql = "SELECT COUNT(*) FROM KhachHang WHERE sdt = ? AND maKhachHang != ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sdt);
            ps.setString(2, maKhachHang);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
