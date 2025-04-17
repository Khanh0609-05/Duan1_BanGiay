package duan1_bangiay.repository;

import duan1_bangiay.model.DanhSachDonHang;
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
public class DanhSachDonHangRepo {

    public List<DanhSachDonHang> getHoaDonChuaThanhToan() {
        List<DanhSachDonHang> unpaidInvoices = new ArrayList<>();
        String sql = "SELECT hd.MaHoaDon, kh.MaKhachHang, nv.MaNhanVien, hd.IDPhieuGiamGia, pgg.MaPhieuGiamGia, hd.NgayTao, hd.TongTien, hd.GiamGia "
                + "FROM HoaDon hd "
                + "JOIN KhachHang kh ON hd.IDKhachHang = kh.ID "
                + "JOIN NhanVien nv ON hd.IDNhanVien = nv.ID "
                + "LEFT JOIN PhieuGiamGia pgg ON hd.IDPhieuGiamGia = pgg.ID "
                + "WHERE hd.TrangThai = 0"; // TrangThai = 0 for "Chưa thanh toán"

        try (Connection connection = DBConnect.getConnection(); 
             PreparedStatement ps = connection.prepareStatement(sql); 
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                DanhSachDonHang hoaDon = new DanhSachDonHang();
                hoaDon.setMaHoaDon(resultSet.getString("MaHoaDon"));
                hoaDon.setMaKhachHang(resultSet.getString("MaKhachHang"));
                hoaDon.setMaNhanVien(resultSet.getString("MaNhanVien"));
                hoaDon.setIdPhieuGiamGia(resultSet.getInt("IDPhieuGiamGia") == 0 ? null : resultSet.getInt("IDPhieuGiamGia"));
                hoaDon.setMaPhieuGiamGia(resultSet.getString("MaPhieuGiamGia"));
                hoaDon.setNgayTao(resultSet.getTimestamp("NgayTao").toLocalDateTime());
                hoaDon.setTongTien(resultSet.getBigDecimal("TongTien"));
                hoaDon.setGiamGia(resultSet.getBigDecimal("GiamGia"));
                unpaidInvoices.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return unpaidInvoices;
    }

    public List<DanhSachDonHang> getHoaDonDaThanhToan() {
        List<DanhSachDonHang> paidInvoices = new ArrayList<>();
        String sql = "SELECT hd.MaHoaDon, kh.MaKhachHang, nv.MaNhanVien, hd.IDPhieuGiamGia, pgg.MaPhieuGiamGia, hd.NgayTao, hd.TongTien, hd.GiamGia "
                + "FROM HoaDon hd "
                + "JOIN KhachHang kh ON hd.IDKhachHang = kh.ID "
                + "JOIN NhanVien nv ON hd.IDNhanVien = nv.ID "
                + "LEFT JOIN PhieuGiamGia pgg ON hd.IDPhieuGiamGia = pgg.ID "
                + "WHERE hd.TrangThai = 1"; // TrangThai = 1 for "Đã thanh toán"

        try (Connection connection = DBConnect.getConnection(); 
             PreparedStatement ps = connection.prepareStatement(sql); 
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                DanhSachDonHang hoaDon = new DanhSachDonHang();
                hoaDon.setMaHoaDon(resultSet.getString("MaHoaDon"));
                hoaDon.setMaKhachHang(resultSet.getString("MaKhachHang"));
                hoaDon.setMaNhanVien(resultSet.getString("MaNhanVien"));
                hoaDon.setIdPhieuGiamGia(resultSet.getInt("IDPhieuGiamGia") == 0 ? null : resultSet.getInt("IDPhieuGiamGia"));
                hoaDon.setMaPhieuGiamGia(resultSet.getString("MaPhieuGiamGia"));
                hoaDon.setNgayTao(resultSet.getTimestamp("NgayTao").toLocalDateTime());
                hoaDon.setTongTien(resultSet.getBigDecimal("TongTien"));
                hoaDon.setGiamGia(resultSet.getBigDecimal("GiamGia"));
                paidInvoices.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paidInvoices;
    }
        public boolean kiemTraTrangThaiHoaDon(String maHoaDonHienTai) {
        try {
            Connection conn = DBConnect.getConnection();
            String sql = "SELECT trangThai FROM HoaDon WHERE maHoaDon = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maHoaDonHienTai);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int trangThai = rs.getInt("trangThai");
                return trangThai == 1; // Trả về true nếu trạng thái là 1 (đã thanh toán)
            }

            return false; // Mặc định trả về false nếu không tìm thấy hóa đơn
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kiểm tra trạng thái hóa đơn: " + e.getMessage());
            return true; // Ngăn hành động nếu có lỗi
        }
    }
}