/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SanPham.Repositori;

import SanPham.Model.ModelThuongHieu;

import SanPham.Until.DBconnect;
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
    
    public ArrayList<ModelThuongHieu> getallTH(){
        ArrayList<ModelThuongHieu> List_thuonghieu= new ArrayList();
        
        sql="SELECT MATH, TENTH, TRANGTHAI FROM THUONGHIEU";
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
            sql = "INSERT INTO ThuongHieu (MaTH, TenTH, TrangThai) VALUES (?, ?, ?)";
        try {
            con = DBconnect.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, m.getMaTH());
            ps.setString(2, m.getTenTH());
            ps.setBoolean(3, m.isTrangThai());
            
            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }

    }
    
    
    public int suaTH(String maCu, ModelThuongHieu m) {  // Đổi từ int id sang String maCu
    sql = "UPDATE ThuongHieu SET MaTH=?, TenTH=?, TrangThai=? WHERE MaTH=?";
    try {
        con = DBconnect.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, m.getMaTH());
        ps.setString(2, m.getTenTH());
        ps.setBoolean(3, m.isTrangThai());
        ps.setString(4, maCu);  // Dùng mã cũ để tìm bản ghi
        return ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    }
}
    
    
    
    
   
    
public ModelThuongHieu checkTrungtenTH(String tenMoi) {
    
    sql = "SELECT ID, MaTH, TenTH, TrangThai FROM ThuongHieu WHERE TenTH = ?";
    
    ModelThuongHieu thuongHieu = null;
    
    try {
        con = DBconnect.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, tenMoi);
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
}

