/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repo;

import Model.HoaDonChiTiet;
import Until.DBConnect;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class HoaDonChiTietRepo {
       private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public HoaDonChiTietRepo() {
        con = DBConnect.getConnection();
    }
    
    public ArrayList<HoaDonChiTiet> getAll() {
        ArrayList<HoaDonChiTiet> listHDCT = new ArrayList<>();
         sql = "SELECT SanPham.MaSanPham, SanPham.TenSanPham, ChiTietSanPham.DonGia, ChiTietHoaDon.SoLuong\n" +
"FROM SanPham\n" +
"INNER JOIN ChiTietSanPham ON SanPham.IDChiTietSanPham = ChiTietSanPham.ID\n" +
"INNER JOIN ChiTietHoaDon ON SanPham.ID = ChiTietHoaDon.IDSanPham\n" +
"INNER JOIN HoaDon ON ChiTietHoaDon.IDHoaDon = HoaDon.ID\n" +
"WHERE HoaDon.MaHoaDon = ?";
         try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
             while (rs.next()) {                 
                 String maSanPham,tenSanPham;
                 int soLuong;
                 double donGia,thanhTien;
                 maSanPham = rs.getString(1);
                 tenSanPham = rs.getString(2);          
                 donGia = rs.getDouble(3);
                 soLuong = rs.getInt(4);
                 thanhTien = donGia*soLuong;
                 listHDCT.add(new HoaDonChiTiet(maSanPham, tenSanPham, donGia, soLuong, thanhTien));
             }
             return listHDCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
 public ArrayList<HoaDonChiTiet> getChiTietHoaDon(String maHoaDon) {
    ArrayList<HoaDonChiTiet> listHDCT = new ArrayList<>();
    String sql = "SELECT SanPham.MaSanPham, SanPham.TenSanPham, ChiTietSanPham.DonGia, ChiTietHoaDon.SoLuong\n" +
                 "FROM SanPham\n" +
                 "INNER JOIN ChiTietSanPham ON SanPham.IDChiTietSanPham = ChiTietSanPham.ID\n" +
                 "INNER JOIN ChiTietHoaDon ON SanPham.ID = ChiTietHoaDon.IDSanPham\n" +
                 "INNER JOIN HoaDon ON ChiTietHoaDon.IDHoaDon = HoaDon.ID\n" +
                 "WHERE HoaDon.MaHoaDon = ?";
    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, maHoaDon);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String maSanPham = rs.getString(1);
                String tenSanPham = rs.getString(2);
                double donGia = rs.getDouble(3);
                int soLuong = rs.getInt(4);
                double thanhTien = donGia * soLuong;
                listHDCT.add(new HoaDonChiTiet(maSanPham, tenSanPham, donGia, soLuong, thanhTien));
            }
        }
        return listHDCT;
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>(); // Trả về danh sách rỗng thay vì null
    }
}
}
