/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.repository;

import duan1_bangiay.model.ChiTietHoaDon;
import duan1_bangiay.utils.DBConnect;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Quân
 */
public class HoaDonChiTietRepo {

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
}
