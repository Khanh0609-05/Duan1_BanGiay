/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repo;

import Model.HoaDon;
import Until.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author nguye
 */
public class HoaDonRepo {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public HoaDonRepo() {
        con = DBConnect.getConnection();
    }

    public ArrayList<HoaDon> getAll() {
        ArrayList<HoaDon> listHD = new ArrayList<>();
        sql = "SELECT HoaDon.MaHoaDon, KhachHang.MaKhachHang, NhanVien.MaNhanVien, PhieuGiamGia.MaPhieuGiamGia, HoaDon.NgayTao, HoaDon.TongTien, HoaDon.GiamGia, HoaDon.ThanhTien\n"
                + "FROM  HoaDon INNER JOIN\n"
                + "                         KhachHang ON HoaDon.IDKhachHang = KhachHang.ID INNER JOIN\n"
                + "                         NhanVien ON HoaDon.IDNhanVien = NhanVien.ID INNER JOIN\n"
                + "                         PhieuGiamGia ON HoaDon.IDPhieuGiamGia = PhieuGiamGia.ID";
        try {
            System.out.println("Connection is Closed? " + con.isClosed());
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String maHoaDon, maKhachHang, maNhanVien, maPhieuGiamGia;
                LocalDateTime ngayTao;
                double tongTien, giamGia, thanhToan;
                maHoaDon = rs.getString(1);
                maKhachHang = rs.getString(2);
                maNhanVien = rs.getString(3);
                maPhieuGiamGia = rs.getString(4);
                java.sql.Timestamp timestamp = rs.getTimestamp(5);
                ngayTao = (timestamp != null) ? timestamp.toLocalDateTime() : null;
                tongTien = rs.getDouble(6);
                giamGia = rs.getDouble(7);
                thanhToan = rs.getDouble(8);
                listHD.add(new HoaDon(maHoaDon, maKhachHang, maNhanVien, maPhieuGiamGia, ngayTao, tongTien, giamGia, thanhToan));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listHD;
    }
    

public ArrayList<HoaDon> timHDTheoNgay(java.util.Date dateFrom, java.util.Date dateTo) {
    ArrayList<HoaDon> listHD = new ArrayList<>();
    String sql = "SELECT \n" +
        "    hd.MaHoaDon,\n" +
        "    kh.MaKhachHang,\n" +
        "    nv.MaNhanVien,\n" +
        "    pgg.MaPhieuGiamGia,\n" +
        "    hd.NgayTao,\n" +
        "    hd.TongTien,\n" +
        "    hd.GiamGia,\n" +
        "    hd.ThanhTien\n" +
        "FROM HoaDon hd\n" +
        "LEFT JOIN KhachHang kh ON hd.IDKhachHang = kh.ID\n" +
        "LEFT JOIN NhanVien nv ON hd.IDNhanVien = nv.ID\n" +
        "LEFT JOIN PhieuGiamGia pgg ON hd.IDPhieuGiamGia = pgg.ID\n" +
        "WHERE \n" +
        "    hd.NgayTao BETWEEN ? AND ?";

    try {
        ps = con.prepareStatement(sql);
        // Thiết lập tham số cho khoảng thời gian
        ps.setTimestamp(1, new java.sql.Timestamp(dateFrom.getTime()));
        ps.setTimestamp(2, new java.sql.Timestamp(dateTo.getTime()));
        
        rs = ps.executeQuery();

        while (rs.next()) {
            String maHoaDon, maKhachHang, maNhanVien, maPhieuGiamGia;
            LocalDateTime ngayTao;
            double tongTien, giamGia, thanhToan;

            maHoaDon = rs.getString(1);
            maKhachHang = rs.getString(2);
            maNhanVien = rs.getString(3);
            maPhieuGiamGia = rs.getString(4);
            java.sql.Timestamp timestamp = rs.getTimestamp(5);
            ngayTao = (timestamp != null) ? timestamp.toLocalDateTime() : null;
            tongTien = rs.getDouble(6);
            giamGia = rs.getDouble(7);
            thanhToan = rs.getDouble(8);

            listHD.add(new HoaDon(maHoaDon, maKhachHang, maNhanVien, maPhieuGiamGia, ngayTao, tongTien, giamGia, thanhToan));
        }
        return listHD;
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>();
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    public ArrayList<HoaDon> lamMoi() {
        // Gọi lại phương thức getAll() để lấy toàn bộ danh sách hóa đơn từ cơ sở dữ liệu
        return getAll();
    }
    
 public ArrayList<HoaDon> timHDKhongTheoNgay(String searchText) {
    ArrayList<HoaDon> listHD = new ArrayList<>();
    String sql = "SELECT \n" +
        "    hd.MaHoaDon,\n" +
        "    kh.MaKhachHang,\n" +
        "    nv.MaNhanVien,\n" +
        "    pgg.MaPhieuGiamGia,\n" +
        "    hd.NgayTao,\n" +
        "    hd.TongTien,\n" +
        "    hd.GiamGia,\n" +
        "    hd.ThanhTien\n" +
        "FROM HoaDon hd\n" +
        "LEFT JOIN KhachHang kh ON hd.IDKhachHang = kh.ID\n" +
        "LEFT JOIN NhanVien nv ON hd.IDNhanVien = nv.ID\n" +
        "LEFT JOIN PhieuGiamGia pgg ON hd.IDPhieuGiamGia = pgg.ID\n" +
        "WHERE \n" +
        "    (hd.MaHoaDon LIKE ? OR ? IS NULL OR ? = '')\n" +
        "    OR (kh.MaKhachHang LIKE ? OR ? IS NULL OR ? = '')\n" +
        "    OR (nv.MaNhanVien LIKE ? OR ? IS NULL OR ? = '')\n" +
        "    OR (pgg.MaPhieuGiamGia LIKE ? OR ? IS NULL OR ? = '')"; // Thêm điều kiện tìm kiếm maPhieuGiamGia

    try {
        ps = con.prepareStatement(sql);
        // Thiết lập tham số cho tìm kiếm (partial match với LIKE)
        String searchPattern = (searchText != null && !searchText.isEmpty()) ? "%" + searchText + "%" : null;
        // Gán pattern cho cả 4 trường
        ps.setString(1, searchPattern); // maHoaDon
        ps.setString(2, searchText);
        ps.setString(3, searchText);
        ps.setString(4, searchPattern); // maKhachHang
        ps.setString(5, searchText);
        ps.setString(6, searchText);
        ps.setString(7, searchPattern); // maNhanVien
        ps.setString(8, searchText);
        ps.setString(9, searchText);
        ps.setString(10, searchPattern); // maPhieuGiamGia
        ps.setString(11, searchText);
        ps.setString(12, searchText);
        
        rs = ps.executeQuery();

        while (rs.next()) {
            String maHoaDon, maKhachHang, maNhanVien, maPhieuGiamGia;
            LocalDateTime ngayTao;
            double tongTien, giamGia, thanhToan;

            maHoaDon = rs.getString(1);
            maKhachHang = rs.getString(2);
            maNhanVien = rs.getString(3);
            maPhieuGiamGia = rs.getString(4);
            java.sql.Timestamp timestamp = rs.getTimestamp(5);
            ngayTao = (timestamp != null) ? timestamp.toLocalDateTime() : null;
            tongTien = rs.getDouble(6);
            giamGia = rs.getDouble(7);
            thanhToan = rs.getDouble(8);

            listHD.add(new HoaDon(maHoaDon, maKhachHang, maNhanVien, maPhieuGiamGia, ngayTao, tongTien, giamGia, thanhToan));
        }
        return listHD;
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>();
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
}
