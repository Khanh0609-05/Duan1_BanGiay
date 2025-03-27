package SanPham;

import java.sql.*;
import java.util.ArrayList;

public class RepositoriSanPhamChiTiet {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public ArrayList<SanPhamChiTietModel> getAllSPCT() {
        ArrayList<SanPhamChiTietModel> list = new ArrayList<>();
        String sql = "SELECT SanPham.MaSanPham, SanPham.TenSanPham, ChiTietSanPham.SoLuong, ThuongHieu.TenTH, MauSac.TenMS, KichThuoc.TenKT, ChiTietSanPham.DonGia, ChiTietSanPham.TrangThai\n" +
"FROM     ChiTietSanPham INNER JOIN\n" +
"                  KichThuoc ON ChiTietSanPham.IDKichThuoc = KichThuoc.ID INNER JOIN\n" +
"                  MauSac ON ChiTietSanPham.IDMauSac = MauSac.ID INNER JOIN\n" +
"                  SanPham ON ChiTietSanPham.ID = SanPham.IDChiTietSanPham INNER JOIN\n" +
"                  ThuongHieu ON ChiTietSanPham.IDThuongHieu = ThuongHieu.ID";
        try {
            con = DBconnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString(1);
                String tenSanPham = rs.getString(2);
                int soLuong = rs.getInt(3);
                String tenThuongHieu = rs.getString(4);
                String tenMauSac = rs.getString(5);
                String tenKichThuoc = rs.getString(6);
                double donGia = rs.getDouble(7);
                boolean trangThai = rs.getBoolean(8);
                SanPhamChiTietModel spct = new SanPhamChiTietModel(id, tenSanPham, soLuong, tenThuongHieu, 
                        tenMauSac, tenKichThuoc, donGia, trangThai);
                list.add(spct);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResources();
        }
    }

    public int themSPCT(SanPhamChiTietModel spct, int idThuongHieu, int idMauSac, int idKichThuoc) {
        String sqlChiTiet = "INSERT INTO ChiTietSanPham (IDThuongHieu, IDMauSac, IDKichThuoc, SoLuong, DonGia, TrangThai) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";
        String sqlSanPham = "INSERT INTO SanPham (TenSanPham, IDChiTietSanPham) VALUES (?, ?)";
        try {
            con = DBconnect.getConnection();
            con.setAutoCommit(false); // Bắt đầu transaction

            // Thêm vào ChiTietSanPham
            ps = con.prepareStatement(sqlChiTiet, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idThuongHieu);
            ps.setInt(2, idMauSac);
            ps.setInt(3, idKichThuoc);
            ps.setInt(4, spct.getSoLuong());
            ps.setDouble(5, spct.getDonGia());
            ps.setBoolean(6, spct.isTrangThai());
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Lấy ID vừa thêm
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int idChiTiet = rs.getInt(1);

                    // Thêm vào SanPham
                    ps = con.prepareStatement(sqlSanPham);
                    ps.setString(1, spct.getTenSanPham());
                    ps.setInt(2, idChiTiet);
                    ps.executeUpdate();

                    con.commit(); // Commit transaction
                    return idChiTiet; // Trả về ID của ChiTietSanPham
                }
            }
            con.rollback(); // Rollback nếu không thành công
            return 0;
        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return 0;
        } finally {
            closeResources();
        }
    }

    public int suaSPCT(int id, SanPhamChiTietModel spct, int idThuongHieu, int idMauSac, int idKichThuoc) {
        String sqlChiTiet = "UPDATE ChiTietSanPham SET IDThuongHieu = ?, IDMauSac = ?, IDKichThuoc = ?, " +
                            "SoLuong = ?, DonGia = ?, TrangThai = ? WHERE ID = ?";
        String sqlSanPham = "UPDATE SanPham SET TenSanPham = ? WHERE IDChiTietSanPham = ?";
        try {
            con = DBconnect.getConnection();
            con.setAutoCommit(false); // Bắt đầu transaction

            // Cập nhật ChiTietSanPham
            ps = con.prepareStatement(sqlChiTiet);
            ps.setInt(1, idThuongHieu);
            ps.setInt(2, idMauSac);
            ps.setInt(3, idKichThuoc);
            ps.setInt(4, spct.getSoLuong());
            ps.setDouble(5, spct.getDonGia());
            ps.setBoolean(6, spct.isTrangThai());
            ps.setInt(7, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Cập nhật SanPham
                ps = con.prepareStatement(sqlSanPham);
                ps.setString(1, spct.getTenSanPham());
                ps.setInt(2, id);
                ps.executeUpdate();

                con.commit(); // Commit transaction
                return 1;
            }
            con.rollback(); // Rollback nếu không thành công
            return 0;
        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return 0;
        } finally {
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}