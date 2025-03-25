/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repo;

import Model.Model_HoaDonChiTiet;
import connect.DBConnect;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class Repo_HoDonChiTiet {
       private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public Repo_HoDonChiTiet() {
        con = DBConnect.getConnection();
    }
    
    public ArrayList<Model.Model_HoaDonChiTiet> getAll() {
        ArrayList<Model.Model_HoaDonChiTiet> listHDCT = new ArrayList<>();
         sql = "SELECT        SanPham.MaSanPham, HoaDon.MaHoaDon, ChiTietHoaDon.SoLuong, ChiTietHoaDon.DonGia, ChiTietHoaDon.TrangThai\n" +
"FROM            SanPham INNER JOIN\n" +
"                         HoaDon ON SanPham.ID = HoaDon.ID INNER JOIN\n" +
"                         ChiTietHoaDon ON SanPham.ID = ChiTietHoaDon.IDSanPham AND HoaDon.ID = ChiTietHoaDon.IDHoaDon";
         try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
             while (rs.next()) {                 
                 String maSanPham,maHoaDon;
                 int soLuong;
                 BigDecimal donGia;
                 boolean trangThai;
                 maSanPham = rs.getString(1);
                 maHoaDon = rs.getString(2);
                 soLuong = rs.getInt(3);
                 donGia = rs.getBigDecimal(4);
                 trangThai = rs.getBoolean(5);
                 listHDCT.add(new Model_HoaDonChiTiet(maSanPham, maHoaDon, soLuong, donGia, trangThai));
             }
             return listHDCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
