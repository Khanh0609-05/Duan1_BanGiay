package duan1_bangiay.repository;

import duan1_bangiay.model.ModelKichThuoc;
import duan1_bangiay.utils.DBConnect;


import java.sql.*;
import java.util.ArrayList;

public class KichThuocRepo {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public ArrayList<ModelKichThuoc> getAllKT() {
        ArrayList<ModelKichThuoc> listKichThuoc = new ArrayList<>();
        sql = "SELECT ID, MaKT, TenKT, TrangThai FROM KichThuoc";
        try {
            con = DBConnect.getConnection();
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
        } 
    }

    public int themKT(ModelKichThuoc m) {
        sql = "INSERT INTO KichThuoc (MaKT, TenKT, TrangThai) VALUES (?, ?, ?)";
        try {
            con = DBConnect.getConnection();
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

    public int suaKT(int id, ModelKichThuoc m) {
        sql = "UPDATE KichThuoc SET MaKT=?, TenKT=?, TrangThai=? WHERE ID=?";
        try {
            con = DBConnect.getConnection();
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

    public ModelKichThuoc checkTrungKT(String maMoi) {
        sql = "SELECT ID, MaKT, TenKT, TrangThai FROM KichThuoc WHERE TenKT = ?";
        ModelKichThuoc kichThuoc = null;
        try {
            con = DBConnect.getConnection();
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
        } 
    }

    
    }
