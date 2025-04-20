/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.repository;

import duan1_bangiay.model.GioHang;
import duan1_bangiay.utils.DBConnect;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Vinh
 */
public class GioHangRepo {

    public List<Object[]> layChiTietDonHangTuCSDL(String maDonHang) {
        List<Object[]> chiTietDonHang = new ArrayList<>();
        String sql = "SELECT ROW_NUMBER() OVER (ORDER BY cthd.ID) AS STT, "
                + "sp.TenSanPham AS TenHangHoa, cthd.DonGia, cthd.SoLuong, "
                + "(cthd.DonGia * cthd.SoLuong) AS ThanhTien "
                + "FROM ChiTietHoaDon cthd "
                + "JOIN SanPham sp ON cthd.IDSanPham = sp.ID "
                + "JOIN HoaDon hd ON cthd.IDHoaDon = hd.ID "
                + "WHERE hd.MaHoaDon = ?";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, maDonHang);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] dong = new Object[]{ // Adjust variable name too
                        rs.getInt("STT"),
                        rs.getString("TenHangHoa"),
                        rs.getBigDecimal("DonGia"),
                        rs.getInt("SoLuong"),
                        rs.getBigDecimal("ThanhTien")
                    };
                    chiTietDonHang.add(dong);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi lấy chi tiết đơn hàng: " + e.getMessage());
        }

        return chiTietDonHang;
    }

    public void saveProductToInvoice(String maDonHang, String maSanPham, int soLuong, BigDecimal donGia) {
        String sql = "INSERT INTO ChiTietHoaDon (IDHoaDon, IDSanPham, SoLuong, DonGia) "
                + "VALUES ((SELECT ID FROM HoaDon WHERE MaHoaDon = ?), "
                + "(SELECT ID FROM SanPham WHERE MaSanPham = ?), ?, ?)";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, maDonHang);
            ps.setString(2, maSanPham);
            ps.setInt(3, soLuong);
            ps.setBigDecimal(4, donGia);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIntoChiTietHoaDon(String maHoaDon, String maSP, int soLuong, BigDecimal donGia) {
        String sql = "INSERT INTO ChiTietHoaDon (IDHoaDon, IDSanPham, SoLuong, DonGia, TrangThai) "
                + "VALUES ((SELECT ID FROM HoaDon WHERE MaHoaDon = ?), "
                + "(SELECT ID FROM SanPham WHERE MaSanPham = ?), ?, ?, 1)";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maHoaDon);
            ps.setString(2, maSP);
            ps.setInt(3, soLuong);
            ps.setBigDecimal(4, donGia);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi thêm sản phẩm vào hóa đơn chi tiết: " + e.getMessage());
        }
    }
        public List<Object[]> layDanhSachChiTietHoaDonTuCSDL(String maHoaDon) {
        List<Object[]> danhSachChiTiet = new ArrayList<>();
        String sql = "SELECT ROW_NUMBER() OVER (ORDER BY cthd.ID) AS STT, "
                + "sp.TenSanPham, cthd.DonGia, cthd.SoLuong, "
                + "(cthd.DonGia * cthd.SoLuong) AS ThanhTien "
                + "FROM ChiTietHoaDon cthd "
                + "JOIN SanPham sp ON cthd.IDSanPham = sp.ID "
                + "JOIN HoaDon hd ON cthd.IDHoaDon = hd.ID "
                + "WHERE hd.MaHoaDon = ?";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maHoaDon);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BigDecimal donGia = rs.getBigDecimal("DonGia");
                    BigDecimal thanhTien = rs.getBigDecimal("ThanhTien");
                    danhSachChiTiet.add(new Object[]{
                        rs.getInt("STT"),
                        rs.getString("TenSanPham"),
                        donGia, // Lưu giá trị gốc (BigDecimal)
                        rs.getInt("SoLuong"),
                        thanhTien // Lưu giá trị gốc (BigDecimal)
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy chi tiết hóa đơn: " + e.getMessage());
        }
        return danhSachChiTiet;
    }

            

                    public void capNhatSoLuongSanPhamSetQuantity(int idSanPham, int soLuongThem) {
        // SQL to increase stock quantity
        String sql = "UPDATE ChiTietSanPham SET SoLuong = SoLuong + ? WHERE ID = ?";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, soLuongThem); // Add stock
            ps.setInt(2, idSanPham);   // Reference product by ID

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Số lượng sản phẩm đã được cập nhật thành công!");
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm để cập nhật số lượng.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật số lượng sản phẩm: " + e.getMessage());
        }
    }
                        public void xoaSanPhamKhoiChiTietHoaDon(String maHoaDon, String tenHangHoa) {
        String sql = "DELETE FROM ChiTietHoaDon WHERE IDHoaDon = (SELECT ID FROM HoaDon WHERE MaHoaDon = ?) "
                + "AND IDSanPham = (SELECT ID FROM SanPham WHERE TenSanPham = ?)";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            // Set parameters for the SQL query
            ps.setString(1, maHoaDon);  // Hóa đơn ID
            ps.setString(2, tenHangHoa);  // Tên sản phẩm (must match database)

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm \"" + tenHangHoa + "\" trong hóa đơn!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa sản phẩm: " + e.getMessage());
        }
    }
                            public void capNhatSoLuongSanPhamTrongChiTietHoaDon(String maHoaDon, String tenHangHoa, int soLuongMoi) {
        String sql = "UPDATE ChiTietHoaDon SET SoLuong = ? "
                + "WHERE IDHoaDon = (SELECT ID FROM HoaDon WHERE MaHoaDon = ?) "
                + "AND IDSanPham = (SELECT ID FROM SanPham WHERE TenSanPham = ?)";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, soLuongMoi); // Số lượng mới
            ps.setString(2, maHoaDon); // Mã hóa đơn
            ps.setString(3, tenHangHoa); // Tên sản phẩm

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                // Không cần thông báo ở đây vì thông báo sẽ được hiển thị trong tblHoaDonChiTietMouseClicked
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm \"" + tenHangHoa + "\" trong hóa đơn!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật số lượng sản phẩm: " + e.getMessage());
        }
    }
     public void luuTongTienVaoHoaDon(BigDecimal tongTien, String maHoaDon) {
        String sql = "UPDATE HoaDon SET TongTien = ? WHERE MaHoaDon = ?";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setBigDecimal(1, tongTien); // Gán tổng tiền vào tham số đầu tiên
            ps.setString(2, maHoaDon);    // Gán mã hóa đơn vào tham số thứ hai

            int rowsAffected = ps.executeUpdate(); // Thực thi câu lệnh SQL
            if (rowsAffected == 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thất bại! Mã hóa đơn không tồn tại.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu tổng tiền vào hóa đơn: " + e.getMessage());
        }
    }
}
