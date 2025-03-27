package SanPham.KichThuoc;

import SanPham.DBconnect;
import java.sql.*;
import java.util.ArrayList;

public class RepositoriKichThuoc {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public ArrayList<ModelKichThuoc> getAllKT() {
        ArrayList<ModelKichThuoc> listKichThuoc = new ArrayList<>();
        sql = "SELECT ID, MaKT, TenKT, TrangThai FROM KichThuoc";
        try {
            con = DBconnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String ma = rs.getString(2);
                String ten = rs.getString(3);
                boolean trangThai = rs.getBoolean(4);
                ModelKichThuoc kichThuoc = new ModelKichThuoc(id, ma, ten, trangThai);
                listKichThuoc.add(kichThuoc);
            }
            return listKichThuoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResources();
        }
    }

    public int themKT(ModelKichThuoc m) {
        sql = "INSERT INTO KichThuoc (MaKT, TenKT, TrangThai) VALUES (?, ?, ?)";
        try {
            con = DBconnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, m.getMa());
            ps.setString(2, m.getTen());
            ps.setBoolean(3, m.isTrangThai());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeResources();
        }
    }

    public int suaKT(int id, ModelKichThuoc m) {
        sql = "UPDATE KichThuoc SET MaKT=?, TenKT=?, TrangThai=? WHERE ID=?";
        try {
            con = DBconnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, m.getMa());
            ps.setString(2, m.getTen());
            ps.setBoolean(3, m.isTrangThai());
            ps.setInt(4, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeResources();
        }
    }

    public ModelKichThuoc checkTrungKT(String maMoi) {
        sql = "SELECT ID, MaKT, TenKT, TrangThai FROM KichThuoc WHERE MaKT = ?";
        ModelKichThuoc kichThuoc = null;
        try {
            con = DBconnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maMoi);
            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String ma = rs.getString(2);
                String ten = rs.getString(3);
                boolean trangThai = rs.getBoolean(4);
                kichThuoc = new ModelKichThuoc(id, ma, ten, trangThai);
            }
            return kichThuoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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