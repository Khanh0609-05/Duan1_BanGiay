/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SanPham.ThuongHieu;

import SanPham.DBconnect;
import java.sql.*;
import java.util.ArrayList;


/**
 *
 * @author lenovo
 */
public class RepositoriThuongHieu {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;
    
    public ArrayList<ModelThuongHieu> getall(){
        ArrayList<ModelThuongHieu> List_thuonghieu= new ArrayList();
        
        sql="SELECT  Ma, Ten, TrangThai FROM ThuongHieu";
        try{
            con = DBconnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                String ma = rs.getString(1);
                String ten = rs.getString(2);
                boolean trangThai = rs.getBoolean(3);

                ModelThuongHieu thuongHieu = new ModelThuongHieu( ma, ten, trangThai);
                List_thuonghieu.add(thuongHieu);
            }
            return List_thuonghieu;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public int themTH(ModelThuongHieu m) {
            sql = "INSERT INTO ThuongHieu (Ma, Ten, TrangThai) VALUES (?, ?, ?)";
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
    public int suaTH(int id, ModelThuongHieu m) {
        sql = "UPDATE ThuongHieu SET Ma=?, Ten=?, TrangThai=? WHERE ID=?";
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
    public ModelThuongHieu checkTrungTH(String maMoi) {
    // Tìm mã mới xem nó đã tồn tại trong database chưa
    sql = "SELECT ID, Ma, Ten, TrangThai FROM ThuongHieu WHERE Ma = ?";
    
    ModelThuongHieu thuongHieu = null;
    
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
            
            thuongHieu = new ModelThuongHieu(id, ma, ten, trangThai);
        }
        return thuongHieu; 
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } 
    }
    
    
    public int xoaTH(int id) {
    sql = "DELETE FROM ThuongHieu WHERE ID = ?";
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

    


