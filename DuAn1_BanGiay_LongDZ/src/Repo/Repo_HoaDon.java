/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repo;

import Model.Model_HoaDon;
import connect.DBConnect;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author nguye
 */
public class Repo_HoaDon {
     private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public Repo_HoaDon() {
        con = DBConnect.getConnection();
    }
    
     public ArrayList<Model.Model_HoaDon> getAll() {
        ArrayList<Model.Model_HoaDon> listHD = new ArrayList<>();
        sql = "SELECT HoaDon.MaHoaDon, KhachHang.MaKhachHang, NhanVien.MaNhanVien, PhieuGiamGia.MaPhieuGiamGia, HoaDon.NgayTao, HoaDon.TongTien, HoaDon.GiamGia, HoaDon.ThanhTien\n" +
"FROM  HoaDon INNER JOIN\n" +
"                         KhachHang ON HoaDon.IDKhachHang = KhachHang.ID INNER JOIN\n" +
"                         NhanVien ON HoaDon.IDNhanVien = NhanVien.ID INNER JOIN\n" +
"                         PhieuGiamGia ON HoaDon.IDPhieuGiamGia = PhieuGiamGia.ID";
         try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
             while (rs.next()) {                 
                 String maHoaDon,maKhachHang,maNhanVien,maPhieuGiamGia;
                 Date thoiGian;
                 BigDecimal  tongTien,giamGia,thanhToan;
                 maHoaDon = rs.getString(1);
                 maKhachHang = rs.getString(2);
                 maNhanVien = rs.getString(3);
                 maPhieuGiamGia = rs.getString(4);
                 thoiGian = rs.getDate(5);
                 tongTien = rs.getBigDecimal(6);
                 giamGia = rs.getBigDecimal(7);
                 thanhToan = rs.getBigDecimal(8);
                 listHD.add(new Model_HoaDon(maHoaDon, maKhachHang, maNhanVien, maPhieuGiamGia, thoiGian, tongTien, giamGia, thanhToan));
             }
             return listHD;
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }
}
