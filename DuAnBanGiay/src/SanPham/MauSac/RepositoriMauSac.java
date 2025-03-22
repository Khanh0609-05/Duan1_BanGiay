/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SanPham.MauSac;
import SanPham.DBconnect;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author lenovo
 */
public class RepositoriMauSac {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;
    
    public ArrayList<ModelMauSac> getall(){
        ArrayList<ModelMauSac> List_mausac = new ArrayList();
        
        sql="SELECT ID, Ma, Ten, TrangThai FROM MauSac";
        try{
            con = DBconnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt(1);
                String ma = rs.getString(2);
                String ten = rs.getString(3);
                boolean trangThai = rs.getBoolean(4);

                ModelMauSac mauSac = new ModelMauSac(id, ma, ten, trangThai);
                List_mausac.add(mauSac);
            }
            return List_mausac;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public int themMS(ModelMauSac m) {
            sql = "INSERT INTO MauSac (Ma, Ten, TrangThai) VALUES (?, ?, ?)";
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
        sql = "UPDATE MauSac SET Ma=?, Ten=?, TrangThai=? WHERE ID=?";
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
    sql = "SELECT ID, Ma, Ten, TrangThai FROM MauSac WHERE Ma = ?";
    
    ModelMauSac mauSac = null;
    
    try {
        con = DBconnect.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, maMoi);
        rs = ps.executeQuery();
        
        while (rs.next()) {
            
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
    
    
    public int xoaMS(int id) {
    sql = "DELETE FROM MauSac WHERE ID = ?";
    try {
        con = DBconnect.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
}
