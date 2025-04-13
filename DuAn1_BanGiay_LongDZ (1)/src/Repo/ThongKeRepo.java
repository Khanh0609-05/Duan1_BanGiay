/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repo;

import Model.HoaDon;
import Model.ThongKe;
import Model.ThongKeTongQuan;
import Until.DBConnect;
import static Until.DBConnect.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author nguye
 */
public class ThongKeRepo {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public ThongKeRepo() {
        con = DBConnect.getConnection();
    }

    public ArrayList<ThongKeTongQuan> getAll(java.util.Date dateFrom, java.util.Date dateTo) {
        ArrayList<ThongKeTongQuan> listTKTQ = new ArrayList<>();
        sql = "SELECT \n" +
"    COALESCE(SUM(hd.ThanhTien), 0) AS TongDoanhThu,\n" +
"    COALESCE(SUM(hdct.SoLuong), 0) AS TongSanPham,\n" +
"    COALESCE(COUNT(DISTINCT hd.IDKhachHang), 0) AS TongKhachHang\n" +
"FROM HoaDon hd\n" +
"LEFT JOIN ChiTietHoaDon hdct ON hd.ID = hdct.IDHoaDon\n" +
"WHERE hd.NgayTao BETWEEN ? AND ?\n" +
"  AND hd.TrangThai = 1;";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            // Thiết lập tham số cho câu truy vấn
            ps.setDate(1, new java.sql.Date(dateFrom.getTime()));
            ps.setDate(2, new java.sql.Date(dateTo.getTime()));
            rs = ps.executeQuery();
            while (rs.next()) {                
                double tongDoanhThu = rs.getDouble("TongDoanhThu");
                int tongSanPham = rs.getInt("TongSanPham");
                int tongKhachHang = rs.getInt("TongKhachHang");
                listTKTQ.add(new ThongKeTongQuan(tongDoanhThu, tongSanPham, tongKhachHang));
            }
              return listTKTQ;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
      
    }
   public Map<Integer, Double> getDoanhThuTheoThang(int year) {
    Map<Integer, Double> doanhThuTheoThang = new HashMap<>();
    for (int month = 1; month <= 12; month++) {
        doanhThuTheoThang.put(month, 0.0);
    }

    String sql = "SELECT MONTH(hd.NgayTao) AS Thang, " +
                 "COALESCE(SUM(hd.ThanhTien), 0) AS TongDoanhThu " +
                 "FROM HoaDon hd " +
                 "WHERE YEAR(hd.NgayTao) = ? AND hd.TrangThai = 1 " +
                 "GROUP BY MONTH(hd.NgayTao)";

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, year);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int thang = rs.getInt("Thang");
                double tongDoanhThu = rs.getDouble("TongDoanhThu");
                doanhThuTheoThang.put(thang, tongDoanhThu);
            }
        }
        // Ghi log để kiểm tra dữ liệu
        System.out.println("Doanh thu theo tháng cho năm " + year + ": " + doanhThuTheoThang);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return doanhThuTheoThang;
}
   
  public List<Map<String, Object>> getTop5SanPhamBanChay(java.util.Date tuNgayTop5, java.util.Date denNgayTop5) {
        List<Map<String, Object>> top5SanPham = new ArrayList<>();

        String sql = "SELECT TOP 5 " +
                     "sp.MaSanPham, " +
                     "sp.TenSanPham, " +
                     "SUM(cthd.SoLuong) AS SoLuong " +
                     "FROM ChiTietHoaDon cthd " +
                     "JOIN HoaDon hd ON cthd.IDHoaDon = hd.ID " +
                     "JOIN SanPham sp ON cthd.IDSanPham = sp.ID " +
                     "WHERE hd.NgayTao BETWEEN ? AND ? " +
                     "AND hd.TrangThai = 1 " +
                     "GROUP BY sp.MaSanPham, sp.TenSanPham " +
                     "ORDER BY SUM(cthd.SoLuong) DESC";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Chuyển java.util.Date thành java.sql.Date
            ps.setDate(1, new java.sql.Date(tuNgayTop5.getTime()));
            ps.setDate(2, new java.sql.Date(denNgayTop5.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> sanPham = new HashMap<>();
                sanPham.put("MaSanPham", rs.getString("MaSanPham"));
                sanPham.put("TenSanPham", rs.getString("TenSanPham"));
                sanPham.put("SoLuong", rs.getInt("SoLuong"));
                top5SanPham.add(sanPham);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return top5SanPham;
    }

   
}


