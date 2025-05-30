package SanPham.Repositori;

import SanPham.Model.SanPhamChiTietModel;
import SanPham.Until.DBconnect;
import java.math.BigDecimal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositoriSanPhamChiTiet {

    public ArrayList<SanPhamChiTietModel> getAllSPCT() {
        ArrayList<SanPhamChiTietModel> list = new ArrayList<>();
        String sql = """
            SELECT SanPham.MaSanPham, SanPham.TenSanPham, ChiTietSanPham.SoLuong, 
                   ThuongHieu.TenTH, MauSac.TenMS, KichThuoc.TenKT, 
                   ChiTietSanPham.DonGia, ChiTietSanPham.TrangThai
            FROM ChiTietSanPham 
            INNER JOIN KichThuoc ON ChiTietSanPham.IDKichThuoc = KichThuoc.ID 
            INNER JOIN MauSac ON ChiTietSanPham.IDMauSac = MauSac.ID 
            INNER JOIN SanPham ON ChiTietSanPham.ID = SanPham.IDChiTietSanPham 
            INNER JOIN ThuongHieu ON ChiTietSanPham.IDThuongHieu = ThuongHieu.ID
        """;

        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString(1);
                String tenSanPham = rs.getString(2);
                int soLuong = rs.getInt(3);
                String tenThuongHieu = rs.getString(4);
                String tenMauSac = rs.getString(5);
                String tenKichThuoc = rs.getString(6);
                double donGia = rs.getDouble(7);
                boolean trangThai = rs.getBoolean(8);

                SanPhamChiTietModel spct = new SanPhamChiTietModel(id, tenSanPham, soLuong, tenThuongHieu,
                        tenMauSac, tenKichThuoc, donGia, trangThai);
                list.add(spct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int themSPCT(SanPhamChiTietModel spct, int idThuongHieu, int idMauSac, int idKichThuoc) {
        if (!kiemTraID("ThuongHieu", idThuongHieu)
                || !kiemTraID("MauSac", idMauSac)
                || !kiemTraID("KichThuoc", idKichThuoc)) {
            System.out.println("Lỗi: ID không tồn tại trong bảng liên quan.");
            return 0;
        }

        String sqlChiTiet = """
            INSERT INTO ChiTietSanPham (IDThuongHieu, IDMauSac, IDKichThuoc, SoLuong, DonGia, TrangThai) 
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        String sqlSanPham = "INSERT INTO SanPham (TenSanPham, IDChiTietSanPham) VALUES (?, ?)";

        try (Connection con = DBconnect.getConnection(); PreparedStatement psChiTiet = con.prepareStatement(sqlChiTiet, Statement.RETURN_GENERATED_KEYS); PreparedStatement psSanPham = con.prepareStatement(sqlSanPham)) {

            con.setAutoCommit(false);

            psChiTiet.setInt(1, idThuongHieu);
            psChiTiet.setInt(2, idMauSac);
            psChiTiet.setInt(3, idKichThuoc);
            psChiTiet.setInt(4, spct.getSoLuong());
            psChiTiet.setDouble(5, spct.getDonGia());
            psChiTiet.setBoolean(6, spct.isTrangThai());
            int rowsAffected = psChiTiet.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = psChiTiet.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idChiTiet = rs.getInt(1);

                        psSanPham.setString(1, spct.getTenSanPham());
                        psSanPham.setInt(2, idChiTiet);
                        psSanPham.executeUpdate();

                        con.commit();
                        return idChiTiet;
                    }
                }
            }
            con.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int suaSPCT(int id, SanPhamChiTietModel spct, int idThuongHieu, int idMauSac, int idKichThuoc) {
        if (!kiemTraID("ThuongHieu", idThuongHieu)
                || !kiemTraID("MauSac", idMauSac)
                || !kiemTraID("KichThuoc", idKichThuoc)) {
            System.out.println("Lỗi: ID không tồn tại trong bảng liên quan.");
            return 0;
        }

        String sqlChiTiet = """
            UPDATE ChiTietSanPham 
            SET IDThuongHieu = ?, IDMauSac = ?, IDKichThuoc = ?, SoLuong = ?, DonGia = ?, TrangThai = ? 
            WHERE ID = ?
        """;
        String sqlSanPham = "UPDATE SanPham SET TenSanPham = ? WHERE IDChiTietSanPham = ?";

        try (Connection con = DBconnect.getConnection(); PreparedStatement psChiTiet = con.prepareStatement(sqlChiTiet); PreparedStatement psSanPham = con.prepareStatement(sqlSanPham)) {

            con.setAutoCommit(false);

            psChiTiet.setInt(1, idThuongHieu);
            psChiTiet.setInt(2, idMauSac);
            psChiTiet.setInt(3, idKichThuoc);
            psChiTiet.setInt(4, spct.getSoLuong());
            psChiTiet.setDouble(5, spct.getDonGia());
            psChiTiet.setBoolean(6, spct.isTrangThai());
            psChiTiet.setInt(7, id);
            int rowsAffected = psChiTiet.executeUpdate();

            if (rowsAffected > 0) {
                psSanPham.setString(1, spct.getTenSanPham());
                psSanPham.setInt(2, id);
                psSanPham.executeUpdate();

                con.commit();
                return 1;
            }
            con.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean kiemTraID(String tableName, int id) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE ID = ?";
        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Them tim kiem san pham
    public List<Object[]> searchSanPham(String keyword) {
        List<Object[]> searchResults = new ArrayList<>();
        String sql = """
        SELECT sp.MaSanPham, sp.TenSanPham, th.TenTH AS ThuongHieu, 
               ms.TenMS AS MauSac, kt.TenKT AS KichThuoc, 
               ctp.SoLuong, ctp.DonGia, ctp.TrangThai
        FROM SanPham sp
        JOIN ChiTietSanPham ctp ON sp.IDChiTietSanPham = ctp.ID
        JOIN ThuongHieu th ON ctp.IDThuongHieu = th.ID
        JOIN MauSac ms ON ctp.IDMauSac = ms.ID
        JOIN KichThuoc kt ON ctp.IDKichThuoc = kt.ID
        WHERE sp.MaSanPham LIKE ? OR sp.TenSanPham LIKE ?
    """;

        try (Connection connection = DBconnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            // Sử dụng ký tự đại diện để tìm kiếm một phần
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[]{
                    rs.getString("MaSanPham"), // Mã sản phẩm
                    rs.getString("TenSanPham"), // Tên sản phẩm
                    rs.getInt("SoLuong"), // Số lượng
                    rs.getString("ThuongHieu"), // Tên thương hiệu
                    rs.getString("MauSac"), // Tên màu sắc
                    rs.getString("KichThuoc"), // Tên kích thước
                    
                    rs.getDouble("DonGia"), // Đơn giá (double, khớp với model)
                    rs.getBoolean("TrangThai") ? "Còn Bán" : "Ngừng Bán" // Trạng thái
                };
                searchResults.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

    //add san pham
    public boolean insertSanPham(String maSanPham, String tenSanPham,
            int idThuongHieu, int idMauSac, int idKichThuoc,
            int soLuong, BigDecimal donGia) {
        String sqlChiTietSanPham = "INSERT INTO ChiTietSanPham (IDThuongHieu, IDMauSac, IDKichThuoc, SoLuong, DonGia, TrangThai) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        String sqlSanPham = "INSERT INTO SanPham (MaSanPham, TenSanPham, IDChiTietSanPham) VALUES (?, ?, ?)";

        try (Connection connection = DBconnect.getConnection()) {
            // Bắt đầu giao dịch
            connection.setAutoCommit(false);

            // Chèn vào bảng ChiTietSanPham
            try (PreparedStatement psChiTiet = connection.prepareStatement(sqlChiTietSanPham, Statement.RETURN_GENERATED_KEYS)) {
                psChiTiet.setInt(1, idThuongHieu); // ID Thương Hiệu
                psChiTiet.setInt(2, idMauSac);    // ID Màu Sắc
                psChiTiet.setInt(3, idKichThuoc); // ID Kích Thước
                psChiTiet.setInt(4, soLuong);     // Số Lượng
                psChiTiet.setBigDecimal(5, donGia); // Đơn Giá
                psChiTiet.setInt(6, 1);            // Mặc định trạng thái là 1

                int affectedRows = psChiTiet.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Chèn vào ChiTietSanPham thất bại, không có dòng nào bị ảnh hưởng.");
                }

                // Lấy ID của ChiTietSanPham vừa tạo
                try (ResultSet generatedKeys = psChiTiet.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idChiTietSanPham = generatedKeys.getInt(1);

                        // Chèn vào bảng SanPham
                        try (PreparedStatement psSanPham = connection.prepareStatement(sqlSanPham)) {
                            psSanPham.setString(1, maSanPham);       // Mã sản phẩm
                            psSanPham.setString(2, tenSanPham);      // Tên sản phẩm
                            psSanPham.setInt(3, idChiTietSanPham); // ID của ChiTietSanPham

                            psSanPham.executeUpdate();
                        }
                    } else {
                        throw new SQLException("Không lấy được ID từ ChiTietSanPham.");
                    }
                }
            }

            // Hoàn tất giao dịch
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            // Rollback nếu xảy ra lỗi
            return false;

        } finally {
//            connection.setAutoCommit(true); // Khôi phục chế độ AutoCommit
        }
    }

    //them update 
    public boolean updateSanPham(String maSanPham, String tenSanPham, int idThuongHieu,
            int idMauSac, int idKichThuoc, int soLuong, BigDecimal donGia, boolean TrangThai) {
        String sqlChiTietSanPham = "UPDATE ChiTietSanPham SET IDThuongHieu = ?, IDMauSac = ?, IDKichThuoc = ?, "
                + "SoLuong = ?, DonGia = ?, TrangThai = ? WHERE ID = (SELECT IDChiTietSanPham FROM SanPham WHERE MaSanPham = ?)";
        String sqlSanPham = "UPDATE SanPham SET TenSanPham = ? WHERE MaSanPham = ?";

        try (Connection connection = DBconnect.getConnection()) {
            // Bắt đầu giao dịch
            connection.setAutoCommit(false);

            // Cập nhật thông tin sản phẩm chi tiết
            try (PreparedStatement psChiTiet = connection.prepareStatement(sqlChiTietSanPham)) {
                psChiTiet.setInt(1, idThuongHieu); // Cập nhật thương hiệu
                psChiTiet.setInt(2, idMauSac);    // Cập nhật màu sắc
                psChiTiet.setInt(3, idKichThuoc); // Cập nhật kích thước
                psChiTiet.setInt(4, soLuong);     // Cập nhật số lượng
                psChiTiet.setBigDecimal(5, donGia); // Cập nhật đơn giá
                psChiTiet.setBoolean(6, TrangThai);
                psChiTiet.setString(7, maSanPham); // Điều kiện dựa vào mã sản phẩm

                int affectedRowsChiTiet = psChiTiet.executeUpdate();
                if (affectedRowsChiTiet == 0) {
                    throw new SQLException("Cập nhật ChiTietSanPham thất bại.");
                }
            }

            // Cập nhật thông tin chung của sản phẩm
            try (PreparedStatement psSanPham = connection.prepareStatement(sqlSanPham)) {
                psSanPham.setString(1, tenSanPham); // Cập nhật tên sản phẩm
                psSanPham.setString(2, maSanPham); // Điều kiện dựa vào mã sản phẩm

                int affectedRowsSanPham = psSanPham.executeUpdate();
                if (affectedRowsSanPham == 0) {
                    throw new SQLException("Cập nhật SanPham thất bại.");
                }
            }

            // Hoàn tất giao dịch
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
//            connection.rollback(); // Rollback nếu xảy ra lỗi
            return false;

        } finally {
//            connection.setAutoCommit(true); // Khôi phục chế độ AutoCommit
        }
    }

    //doi trang thai san pham
    public boolean updateTrangThaiSanPham(String maSanPham) {
        String sql = "UPDATE ChiTietSanPham "
                + "SET TrangThai = 0 "
                + "WHERE ID = (SELECT IDChiTietSanPham FROM SanPham WHERE MaSanPham = ?)";

        try (Connection connection = DBconnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, maSanPham); // Điều kiện: dựa vào Mã Sản Phẩm

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0; // Trả về true nếu có dòng được cập nhật

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }

    public boolean checkTrungTenSPCT(String tenSanPham) {
        String sql = """
        SELECT COUNT(*) 
        FROM SanPham 
        WHERE TenSanPham = ?
    """;

        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tenSanPham.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if a matching name exists
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Default to false if an error occurs
    }

}
