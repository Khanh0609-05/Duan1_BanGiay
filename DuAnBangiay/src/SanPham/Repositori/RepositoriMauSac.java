package SanPham.Repositori;

import SanPham.Model.ModelMauSac;

import SanPham.Until.DBconnect;
import java.sql.*;
import java.util.ArrayList;

public class RepositoriMauSac {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public ArrayList<ModelMauSac> getAllMS() {
        ArrayList<ModelMauSac> listMauSac = new ArrayList<>();
        sql = "SELECT ID, MaMS, TenMS, TrangThai FROM MauSac";
        try {
            con = DBconnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String ma = rs.getString(2);
                String ten = rs.getString(3);
                boolean trangThai = rs.getBoolean(4);
                ModelMauSac mauSac = new ModelMauSac(id, ma, ten, trangThai);
                listMauSac.add(mauSac);
            }
            return listMauSac;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int themMS(ModelMauSac m) {
        sql = "INSERT INTO MauSac (MaMS, TenMS, TrangThai) VALUES (?, ?, ?)";
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
        } 
    }

    public int suaMS(int id, ModelMauSac m) {
        sql = "UPDATE MauSac SET MaMS=?, TenMS=?, TrangThai=? WHERE ID=?";
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
        }
    }

    public ModelMauSac checkTrungMS(String maMoi) {
        sql = "SELECT ID, MaMS, TenMS, TrangThai FROM MauSac WHERE TenMS = ?";
        ModelMauSac mauSac = null;
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
                mauSac = new ModelMauSac(id, ma, ten, trangThai);
            }
            return mauSac;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
    }

    
}