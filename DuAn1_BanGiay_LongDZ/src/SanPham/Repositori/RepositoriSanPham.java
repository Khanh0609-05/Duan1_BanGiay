package SanPham.Repositori;

import SanPham.Model.SanPhamModel;
import Until.DBConnect;
import java.sql.*;
import java.util.ArrayList;

public class RepositoriSanPham {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;
    
    public ArrayList<SanPhamModel> getall(){
        ArrayList<SanPhamModel> List_sanpham = new ArrayList();
        
        sql="SELECT ID_SanPham, Ma, Ten, MoTa, GiaBan, ID_ThuongHieu, TrangThai FROM SanPham";
        try{
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt(1);
                String ma = rs.getString(2);
                String ten = rs.getString(3);
                String moTa = rs.getString(4);
                double giaBan = rs.getDouble(5);
                int idThuongHieu = rs.getInt(6);
                boolean trangThai = rs.getBoolean(7);

                SanPhamModel sanPham = new SanPhamModel(id, ma, ten, moTa, giaBan, idThuongHieu, trangThai);
                List_sanpham.add(sanPham);
            }
            return List_sanpham;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public int themSP(SanPhamModel m) {
            sql = "INSERT INTO SanPham (Ma, Ten, MoTa, GiaBan, ID_ThuongHieu, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, m.getMa());
            ps.setString(2, m.getTen());
            ps.setString(3, m.getMoTa());
            ps.setDouble(4, m.getGiaBan());
            ps.setInt(5, m.getIdThuongHieu());
            ps.setBoolean(6, m.isTrangThai());
            
            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }

    }
    public int suaSP(int id, SanPhamModel m) {
        sql = "UPDATE SanPham SET Ma=?, Ten=?, MoTa=?, GiaBan=?, ID_ThuongHieu=?, TrangThai=? WHERE ID_SanPham=?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, m.getMa());
            ps.setString(2, m.getTen());
            ps.setString(3, m.getMoTa());
            ps.setDouble(4, m.getGiaBan());
            ps.setInt(5, m.getIdThuongHieu());
            ps.setBoolean(6, m.isTrangThai());
            ps.setInt(7, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } 
    }
    public SanPhamModel checkTrungSP(String maMoi) {
    sql = "SELECT ID_SanPham, Ma, Ten, MoTa, GiaBan, ID_ThuongHieu, TrangThai FROM SanPham WHERE Ma = ?";
    
    SanPhamModel sanPham = null;
    
    try {
        con = DBConnect.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, maMoi);
        rs = ps.executeQuery();
        
        while (rs.next()) {
            
            int id = rs.getInt(1);
            String ma = rs.getString(2);
            String ten = rs.getString(3);
            String moTa = rs.getString(4);
            double giaBan = rs.getDouble(5);
            int idThuongHieu = rs.getInt(6);
            boolean trangThai = rs.getBoolean(7);
            
            sanPham = new SanPhamModel(id, ma, ten, moTa, giaBan, idThuongHieu, trangThai);
        }
        return sanPham; 
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } 
    }
    
    
    public int xoaSP(int id) {
    sql = "DELETE FROM SanPham WHERE ID_SanPham = ?";
    try {
        con = DBConnect.getConnection();
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
