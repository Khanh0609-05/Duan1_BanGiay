/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package duan1_bangiay.view;

import duan1_bangiay.model.HoaDon;
import duan1_bangiay.model.PhieuGiamGia;
import duan1_bangiay.repository.HoaDonChiTietRepo;
import duan1_bangiay.repository.HoaDonRepo;
import duan1_bangiay.repository.PhieuGiamGiaRepo;
import duan1_bangiay.repository.SanPhamRepo;

import duan1_bangiay.utils.DBConnect;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.DecimalFormat;

/**
 *
 * @author quanr
 */
public class BanHangView extends javax.swing.JFrame {


    private String maHoaDonHienTai = null; // Biến toàn cục để lưu mã hóa đơn hiện tại
    PhieuGiamGiaRepo pggRepo = new PhieuGiamGiaRepo();

    public BanHangView() {
        initComponents();

        tblHoaDonChiTiet.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            private DecimalFormat formatter = new DecimalFormat("###,###,###");

            @Override
            protected void setValue(Object value) {
                if (value instanceof BigDecimal) {
                    String formatted = formatter.format(((BigDecimal) value).setScale(0, RoundingMode.HALF_UP));
                    setText(formatted.replace(",", ".")); // Thay dấu phẩy bằng dấu chấm
                } else {
                    super.setValue(value);
                }
            }
        });

// Định dạng cột Thành Tiền
        tblHoaDonChiTiet.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            private DecimalFormat formatter = new DecimalFormat("###,###,###");

            @Override
            protected void setValue(Object value) {
                if (value instanceof BigDecimal) {
                    String formatted = formatter.format(((BigDecimal) value).setScale(0, RoundingMode.HALF_UP));
                    setText(formatted.replace(",", ".")); // Thay dấu phẩy bằng dấu chấm
                } else {
                    super.setValue(value);
                }
            }
        });
        loadTables();
        loadPhieuGiamGia();
        searchListener();
        donHangListener();
        // Định dạng thời gian
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Đặt giá trị ban đầu cho txtNgayTao với định dạng đúng
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        txtNgayTao.setText(now.format(formatter));

        // Tạo Timer để cập nhật thời gian
        Timer timer = new Timer(1000, e -> {
            LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            txtNgayTao.setText(currentTime.format(formatter)); // Hiển thị thời gian đã định dạng
        });

        timer.start(); // Bắt đầu timer

        txtNgayTao.setText(java.time.LocalDateTime.now().toString());
        txtMaNhanVien.setText(txtMaNhanVien.getText());
        txtSoDienThoai.addActionListener(e -> checkSdtKhachHang(txtSoDienThoai.getText()));
        txtSoTienTra.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                tinhTienDu();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                tinhTienDu();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                tinhTienDu();
            }
        });
        txtTongTien.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                capNhatThanhTien();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                capNhatThanhTien();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                capNhatThanhTien();
            }
        });
        cboPGG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                capNhatThanhTien();
            }
        });
    }

    private void capNhatThanhTien() {
        try {
            String tongTienStr = txtTongTien.getText().trim().replace(".", "");
            if (tongTienStr.isEmpty()) {
                txtThanhTien.setText("");
                return;
            }
            BigDecimal tongTien = new BigDecimal(tongTienStr);
            BigDecimal giamGia = BigDecimal.ZERO;
            String selectedMaPGG = (String) cboPGG.getSelectedItem();

            if (selectedMaPGG != null && !selectedMaPGG.equals("Không chọn") && !selectedMaPGG.equals(" ")) {
                PhieuGiamGia pgg = pggRepo.getActivePhieuGiamGia().stream()
                        .filter(p -> p.getMaPhieuGiamGia().equals(selectedMaPGG))
                        .findFirst()
                        .orElse(null);

                if (pgg != null) {
                    BigDecimal hoaDonToiThieu = BigDecimal.valueOf(pgg.getHoaDonToiThieu());
                    if (tongTien.compareTo(hoaDonToiThieu) < 0) {
                        JOptionPane.showMessageDialog(null,
                                "Hóa đơn chưa đạt giá trị tối thiểu " + formatVND(hoaDonToiThieu) + " để sử dụng phiếu giảm giá!");
                        cboPGG.setSelectedItem("Không chọn");
                        txtThanhTien.setText(formatVND(tongTien));
                        return;
                    }

                    if (pgg.isKieuGiam()) {
                        giamGia = tongTien.multiply(BigDecimal.valueOf(pgg.getMucGiam()))
                                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                        BigDecimal mucGiamToiDa = BigDecimal.valueOf(pgg.getMucGiamToiDa());
                        if (giamGia.compareTo(mucGiamToiDa) > 0) {
                            giamGia = mucGiamToiDa;
                        }
                    } else {
                        giamGia = BigDecimal.valueOf(pgg.getMucGiam());
                    }
                }
            }

            BigDecimal thanhTien = tongTien.subtract(giamGia).max(BigDecimal.ZERO);
            txtThanhTien.setText(formatVND(thanhTien));

        } catch (NumberFormatException ex) {
            txtThanhTien.setText("");
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số hợp lệ cho tổng tiền!");
        }
    }

    private void searchListener() {
        Timer searchTimer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchAndUpdateTable();
            }
        });
        searchTimer.setRepeats(false); // Chỉ chạy một lần sau khi dừng nhập

        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchTimer.restart();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchTimer.restart();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchTimer.restart();
            }
        });
    }

    private void donHangListener() {
        // Bộ lắng nghe cho bảng "Chưa thanh toán"
        tblChuaThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tblChuaThanhToan.getSelectedRow();
                if (selectedRow != -1) {
                    handleOrderRowClick(selectedRow, tblChuaThanhToan);
                    dayThongTinLenCacO(selectedRow, tblChuaThanhToan); // Truyền bảng
                }
            }
        });

        // Bộ lắng nghe cho bảng "Đã thanh toán"
        tblDaThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tblDaThanhToan.getSelectedRow();
                if (selectedRow != -1) {
                    handleOrderRowClick(selectedRow, tblDaThanhToan);
                    dayThongTinLenCacO(selectedRow, tblDaThanhToan); // Truyền bảng
                }
            }
        });
    }

    private void clearGioHang() {
        DefaultTableModel tblGioHang = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        tblGioHang.setRowCount(0);
    }

    private void clearTextFields() {
        txtMaHoaDon.setText(""); // Clear Mã Hóa Đơn
        txtSoDienThoai.setText(""); // Clear Số Điện Thoại
        txtTenKhachHang.setText(""); // Clear Tên Khách Hàng
        txtMaNhanVien.setText(""); // Clear Mã Nhân Viên
        txtTongTien.setText("");
        txtThanhTien.setText("");
        txtSoTienTra.setText("");
        txtTienDu.setText("");
        txtTimKiem.setText("");
        rdoNam.setSelected(false);
        rdoNu.setSelected(false);

    }

    private void dayThongTinLenCacO(int selectedRow, JTable sourceTable) {
        try {
            DefaultTableModel model = (DefaultTableModel) sourceTable.getModel();
            String maHoaDon = model.getValueAt(selectedRow, 0).toString();

            String sql = "SELECT hd.MaHoaDon, hd.TongTien, hd.ThanhTien, kh.SDT, kh.TenKhachHang, kh.GioiTinh, nv.MaNhanVien, hd.IDPhieuGiamGia "
                    + "FROM HoaDon hd "
                    + "JOIN KhachHang kh ON hd.IDKhachHang = kh.ID "
                    + "JOIN NhanVien nv ON hd.IDNhanVien = nv.ID "
                    + "WHERE hd.MaHoaDon = ?";

            try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, maHoaDon);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        txtMaHoaDon.setText(rs.getString("MaHoaDon"));
                        txtSoDienThoai.setText(rs.getString("SDT"));
                        txtTenKhachHang.setText(rs.getString("TenKhachHang"));
                        rdoNam.setSelected(rs.getInt("GioiTinh") == 1);
                        rdoNu.setSelected(rs.getInt("GioiTinh") == 0);
                        txtMaNhanVien.setText(rs.getString("MaNhanVien"));
                        BigDecimal tongTien = rs.getBigDecimal("TongTien");
                        BigDecimal thanhTien = rs.getBigDecimal("ThanhTien");
                        // Định dạng giá trị trước khi gán vào txtTongTien và txtThanhTien
                        txtTongTien.setText(tongTien != null ? formatVND(tongTien) : "");
                        txtThanhTien.setText(thanhTien != null ? formatVND(thanhTien) : "");

                        Integer idPhieuGiamGia = (Integer) rs.getObject("IDPhieuGiamGia");

                        if (idPhieuGiamGia != null) {
                            String sqlPGG = "SELECT MaPhieuGiamGia FROM PhieuGiamGia WHERE ID = ?";
                            try (PreparedStatement psPGG = connection.prepareStatement(sqlPGG)) {
                                psPGG.setInt(1, idPhieuGiamGia);
                                try (ResultSet rsPGG = psPGG.executeQuery()) {
                                    if (rsPGG.next()) {
                                        String maPhieuGiamGia = rsPGG.getString("MaPhieuGiamGia");
                                        cboPGG.setSelectedItem(maPhieuGiamGia);
                                    } else {
                                        cboPGG.setSelectedItem(" ");
                                    }
                                }
                            }
                        } else {
                            cboPGG.setSelectedItem(" ");
                        }

                        boolean daThanhToan = kiemTraTrangThaiHoaDon(maHoaDon);
                        txtMaHoaDon.setEditable(false);
                        txtSoDienThoai.setEditable(false);
                        txtTenKhachHang.setEditable(false);
                        txtMaNhanVien.setEditable(!daThanhToan);
                        txtTongTien.setEditable(false);
                        txtThanhTien.setEditable(false);
                        txtSoTienTra.setEditable(!daThanhToan);
                        txtTienDu.setEditable(!daThanhToan);
                        rdoNam.setEnabled(!daThanhToan);
                        rdoNu.setEnabled(!daThanhToan);
                        cboPGG.setEnabled(!daThanhToan);
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi truy vấn thông tin hóa đơn: " + e.getMessage());
        }
    }

    private void handleOrderRowClick(int selectedRow, JTable sourceTable) {
        try {
            DefaultTableModel sourceModel = (DefaultTableModel) sourceTable.getModel();
            maHoaDonHienTai = sourceModel.getValueAt(selectedRow, 0).toString(); // Lấy mã hóa đơn hiện tại

            // Cập nhật bảng tblHoaDonChiTiet
            capNhatChiTietHoaDon(maHoaDonHienTai);
            tinhTongTienTuTblHoaDonChiTiet();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xử lý hàng đơn: " + e.getMessage());
        }
    }

    private void capNhatChiTietHoaDon(String maHoaDon) {
        List<Object[]> danhSachChiTiet = layDanhSachChiTietHoaDonTuCSDL(maHoaDon);

        // Cập nhật tblHoaDonChiTiet
        DefaultTableModel model = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (Object[] dong : danhSachChiTiet) {
            model.addRow(dong); // Thêm dữ liệu mới
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnHuyDon = new javax.swing.JButton();
        btnTaoDon = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btnInBill = new javax.swing.JButton();
        tblHoaDon = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblChuaThanhToan = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDaThanhToan = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTenKhachHang = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtThanhTien = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTienDu = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtSoTienTra = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        txtMaNhanVien = new javax.swing.JTextField();
        cboPGG = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        btnLamMoi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(204, 204, 204));

        tblHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên Hàng Hóa", "Đơn Giá", "Số lượng", "Thành Tiền"
            }
        ));
        tblHoaDonChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonChiTietMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDonChiTiet);

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "Thương Hiệu", "Giá Bán", "Số Lượng", "Size", "Màu Sắc"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setHeaderValue("STT");
            tblSanPham.getColumnModel().getColumn(1).setHeaderValue("Mã SP");
            tblSanPham.getColumnModel().getColumn(2).setHeaderValue("Tên SP");
            tblSanPham.getColumnModel().getColumn(3).setHeaderValue("Thương Hiệu");
            tblSanPham.getColumnModel().getColumn(4).setHeaderValue("Giá Bán");
            tblSanPham.getColumnModel().getColumn(5).setHeaderValue("Số Lượng");
            tblSanPham.getColumnModel().getColumn(6).setHeaderValue("Size");
            tblSanPham.getColumnModel().getColumn(7).setHeaderValue("Màu Sắc");
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("QUẢN LÍ BÁN HÀNG");

        btnHuyDon.setText("HỦY ĐƠN");
        btnHuyDon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));
        btnHuyDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyDonActionPerformed(evt);
            }
        });

        btnTaoDon.setText("TẠO ĐƠN");
        btnTaoDon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 51)));
        btnTaoDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoDonActionPerformed(evt);
            }
        });

        btnThanhToan.setText("THANH TOÁN");
        btnThanhToan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 255)));
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Giỏ Hàng");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Thông Tin Sản Phẩm");

        jLabel12.setText("Tìm Kiếm");

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Danh sách đơn hàng");

        btnInBill.setText("IN BILL");
        btnInBill.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0)));
        btnInBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInBillActionPerformed(evt);
            }
        });

        tblChuaThanhToan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Hóa Đơn", "Mã Khách Hàng", "Mã Nhân Viên", "Mã Giảm Giá", "Ngày Mua", "Tổng Tiền"
            }
        ));
        tblChuaThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChuaThanhToanMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblChuaThanhToan);

        tblHoaDon.addTab("Chưa thanh toán", jScrollPane4);

        tblDaThanhToan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Hóa Đơn", "Mã Khách Hàng", "Mã Nhân Viên", "Mã Giảm Giá", "Ngày Mua", "Tổng Tiền", "Thành tiền"
            }
        ));
        jScrollPane5.setViewportView(tblDaThanhToan);

        tblHoaDon.addTab("Đã thanh toán", jScrollPane5);

        jLabel2.setText("Mã Hóa Đơn");

        txtNgayTao.setEditable(false);

        jLabel3.setText("Số Điện Thoại");

        jLabel4.setText("Tên Khách Hàng");

        jLabel5.setText("Mã Nhân Viên");

        jLabel6.setText("Thành Tiền");

        txtThanhTien.setEditable(false);

        jLabel7.setText("Tiền Dư");

        txtTienDu.setEditable(false);

        jLabel8.setText("Số Tiền Trả");

        jLabel9.setText("Phiếu Giảm Giá");

        jLabel14.setText("Tổng Tiền");

        jLabel15.setText("Giới Tính");

        txtTongTien.setEditable(false);

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");
        rdoNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNamActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        jLabel18.setText("Ngày Tạo");

        txtMaHoaDon.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rdoNam)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdoNu))
                                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(47, 47, 47)
                        .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(18, 18, 18)
                            .addComponent(cboPGG, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTienDu, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel6))
                            .addGap(41, 41, 41)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtSoTienTra, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cboPGG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel8)
                    .addComponent(txtSoTienTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtTienDu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        jLabel16.setText("jLabel16");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setText("Tổng Tiền :");

        btnLamMoi.setText("LÀM MỚI");
        btnLamMoi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 51)));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(494, 494, 494)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(9, 9, 9)
                                .addComponent(lblTongTien)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(516, 516, 516)
                                .addComponent(btnHuyDon, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(558, 558, 558)
                                .addComponent(jLabel16))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(tblHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnTaoDon, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnInBill, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel11))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel1)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(lblTongTien))
                        .addGap(31, 31, 31)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnInBill, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnHuyDon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnTaoDon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tblHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    private void btnHuyDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyDonActionPerformed
        // Lấy mã hóa đơn từ giao diện
        String maHoaDon = txtMaHoaDon.getText().trim();
        if (maHoaDon.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn mã hóa đơn cần xóa!");
            return;
        }

        // Kiểm tra trạng thái hóa đơn
        if (kiemTraTrangThaiHoaDon(maHoaDon)) {
            JOptionPane.showMessageDialog(null, "Hóa đơn đã thanh toán, không thể hủy!");
            return;
        }

// Hiển thị hộp thoại xác nhận
        int confirm = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc chắn muốn xóa đơn hàng này khỏi cơ sở dữ liệu?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return; // Người dùng chọn "Không"
        }

        try (Connection connection = DBConnect.getConnection()) {
            connection.setAutoCommit(false); // Bắt đầu transaction

            // Lấy các sản phẩm trong hóa đơn để cập nhật số lượng
            String selectChiTietSql = "SELECT IDSanPham, SoLuong FROM ChiTietHoaDon WHERE IDHoaDon = (SELECT ID FROM HoaDon WHERE MaHoaDon = ?)";
            try (PreparedStatement psSelect = connection.prepareStatement(selectChiTietSql)) {
                psSelect.setString(1, maHoaDon);
                try (ResultSet rs = psSelect.executeQuery()) {
                    // Cập nhật lại số lượng sản phẩm trong kho
                    while (rs.next()) {
                        int idSanPham = rs.getInt("IDSanPham");
                        int soLuong = rs.getInt("SoLuong");
                        capNhatSoLuongSanPham(idSanPham, -soLuong); // Cộng lại số lượng về kho
                    }
                }
            }

            // Xóa chi tiết hóa đơn
            String deleteChiTietSql = "DELETE FROM ChiTietHoaDon WHERE IDHoaDon = (SELECT ID FROM HoaDon WHERE MaHoaDon = ?)";
            try (PreparedStatement psChiTiet = connection.prepareStatement(deleteChiTietSql)) {
                psChiTiet.setString(1, maHoaDon);
                psChiTiet.executeUpdate();
            }

            // Xóa hóa đơn
            String deleteHoaDonSql = "DELETE FROM HoaDon WHERE MaHoaDon = ?";
            try (PreparedStatement psHoaDon = connection.prepareStatement(deleteHoaDonSql)) {
                psHoaDon.setString(1, maHoaDon);
                int rowsAffected = psHoaDon.executeUpdate();

                if (rowsAffected > 0) {
                    connection.commit(); // Xác nhận transaction

                    btnLamMoiActionPerformed(evt);
                } else {
                    connection.rollback(); // Quay lại nếu không xóa được
                    JOptionPane.showMessageDialog(null, "Không tìm thấy đơn hàng để xóa!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa hóa đơn: " + e.getMessage());
        }
    }//GEN-LAST:event_btnHuyDonActionPerformed

    private void btnTaoDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoDonActionPerformed
        // TODO add your handling code here:
        taoHoaDon();
        loadTables();
    }//GEN-LAST:event_btnTaoDonActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed

        try {
            
            
            // Kiểm tra xem đã chọn hóa đơn từ tblChuaThanhToan chưa
            if (maHoaDonHienTai == null) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một hóa đơn từ danh sách chưa thanh toán!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String maHoaDon = txtMaHoaDon.getText().trim();
            String thanhTienStr = txtThanhTien.getText().trim().replace(".", "");
            String soTienTraStr = txtSoTienTra.getText().trim().replace(".", "");

            if (maHoaDon.isEmpty() || thanhTienStr.isEmpty() || soTienTraStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin trước khi thanh toán!");
                return;
            }

            BigDecimal thanhTien = new BigDecimal(thanhTienStr);
            BigDecimal soTienTra = new BigDecimal(soTienTraStr);

            BigDecimal tienDu = soTienTra.subtract(thanhTien);
            if (tienDu.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(null, "Số tiền trả không đủ để thanh toán hóa đơn!");
                return;
            }

            txtTienDu.setText(formatVND(tienDu));

            String selectedMaPGG = (String) cboPGG.getSelectedItem();
            BigDecimal giamGia = BigDecimal.ZERO;
            Integer idPhieuGiamGia = null;

            if (selectedMaPGG != null && !selectedMaPGG.equals(" ") && !selectedMaPGG.equals("Không chọn")) {
                PhieuGiamGia pgg = pggRepo.getActivePhieuGiamGia().stream()
                        .filter(p -> p.getMaPhieuGiamGia().equals(selectedMaPGG))
                        .findFirst()
                        .orElse(null);

                if (pgg != null) {
                    if (pgg.getDaDung() >= pgg.getSoLuong()) {
                        JOptionPane.showMessageDialog(null, "Phiếu giảm giá đã hết lượt sử dụng!");
                        cboPGG.setSelectedItem(" ");
                        return;
                    }

                    BigDecimal tongTien = new BigDecimal(txtTongTien.getText().trim().replace(".", ""));
                    BigDecimal hoaDonToiThieu = BigDecimal.valueOf(pgg.getHoaDonToiThieu());
                    if (tongTien.compareTo(hoaDonToiThieu) < 0) {
                        JOptionPane.showMessageDialog(null,
                                "Hóa đơn chưa đạt giá trị tối thiểu " + formatVND(hoaDonToiThieu) + " để sử dụng phiếu giảm giá!");
                        cboPGG.setSelectedItem(" ");
                        return;
                    }

                    if (pgg.isKieuGiam()) {
                        giamGia = tongTien.multiply(BigDecimal.valueOf(pgg.getMucGiam()))
                                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                        BigDecimal mucGiamToiDa = BigDecimal.valueOf(pgg.getMucGiamToiDa());
                        if (giamGia.compareTo(mucGiamToiDa) > 0) {
                            giamGia = mucGiamToiDa;
                        }
                    } else {
                        giamGia = BigDecimal.valueOf(pgg.getMucGiam());
                    }
                    idPhieuGiamGia = pgg.getId();
                }
            }

            try (Connection connection = DBConnect.getConnection()) {
                connection.setAutoCommit(false);

                String sql = "UPDATE HoaDon SET TongTien = ?, GiamGia = ?, ThanhTien = ?, TrangThai = ?, IDPhieuGiamGia = ? WHERE MaHoaDon = ?";
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setBigDecimal(1, new BigDecimal(txtTongTien.getText().trim().replace(".", "")));
                    ps.setBigDecimal(2, giamGia);
                    ps.setBigDecimal(3, thanhTien);
                    ps.setInt(4, 1); // Đã thanh toán
                    ps.setObject(5, idPhieuGiamGia, Types.INTEGER);
                    ps.setString(6, maHoaDon);

                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        if (idPhieuGiamGia != null) {
                            String updatePGG = "UPDATE PhieuGiamGia SET DaDung = DaDung + 1 WHERE ID = ?";
                            try (PreparedStatement psPGG = connection.prepareStatement(updatePGG)) {
                                psPGG.setInt(1, idPhieuGiamGia);
                                psPGG.executeUpdate();
                            }
                        }
                        connection.commit();
                        JOptionPane.showMessageDialog(null, "Thanh toán thành công!");
                        loadTables(); // Làm mới bảng
                        clearTextFields();
                        clearGioHang();
                        cboPGG.setSelectedItem(" ");
                    } else {
                        connection.rollback();
                        JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn để cập nhật!");
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Số tiền không hợp lệ! Vui lòng nhập số.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật hóa đơn: " + e.getMessage());
        }

    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnInBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInBillActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInBillActionPerformed

    private void rdoNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNamActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked

        if (maHoaDonHienTai == null) {
            JOptionPane.showMessageDialog(null, "Chưa chọn hóa đơn để thêm sản phẩm!");
            return;
        }

        boolean daThanhToan = kiemTraTrangThaiHoaDon(maHoaDonHienTai);
        if (daThanhToan) {
            JOptionPane.showMessageDialog(null, "Không thể thêm sản phẩm vào hóa đơn đã thanh toán!");
            return;
        }

        int row = tblSanPham.getSelectedRow();
        if (row != -1) {
            try {
                String maSP = tblSanPham.getValueAt(row, 1).toString();
                String tenSP = tblSanPham.getValueAt(row, 2).toString();
                String giaBanStr = tblSanPham.getValueAt(row, 4).toString().replace(".", "");
                Integer soLuongTon = Integer.parseInt(tblSanPham.getValueAt(row, 5).toString());

                if (soLuongTon == null || soLuongTon < 0) {
                    JOptionPane.showMessageDialog(null, "Số lượng tồn kho không hợp lệ!");
                    return;
                }

                BigDecimal donGia;
                try {
                    donGia = new BigDecimal(giaBanStr);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Giá bán của sản phẩm không hợp lệ!");
                    return;
                }

                String soLuongStr = JOptionPane.showInputDialog("Nhập số lượng cho sản phẩm: " + tenSP);
                if (soLuongStr == null || soLuongStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Bạn chưa nhập số lượng!");
                    return;
                }

                soLuongStr = soLuongStr.trim();
                if (!soLuongStr.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên dương!");
                    return;
                }

                int soLuong = Integer.parseInt(soLuongStr);
                if (soLuong <= 0) {
                    JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0!");
                    return;
                }
                if (soLuong > soLuongTon) {
                    JOptionPane.showMessageDialog(null, "Không thể thêm sản phẩm \"" + tenSP + "\". Số lượng tồn kho chỉ còn: " + soLuongTon);
                    return;
                }

                boolean productExists = checkProductExistsInInvoice(maHoaDonHienTai, maSP);
                if (productExists) {
                    capNhatSoLuongSanPhamTrongHoaDon(maHoaDonHienTai, maSP, soLuong);
                } else {
                    themSanPhamVaoChiTietHoaDon(maHoaDonHienTai, maSP, soLuong, donGia);
                }

                int idSanPham = getIdSanPhamFromMaSP(maSP);
                capNhatSoLuongSanPham(idSanPham, soLuong);

                capNhatChiTietHoaDon(maHoaDonHienTai);
                // Chỉ làm mới tblSanPham
                DefaultTableModel modelSanPham = (DefaultTableModel) tblSanPham.getModel();
                SanPhamRepo sanPhamRepository = new SanPhamRepo();
                fillToTableSanPham(modelSanPham, sanPhamRepository.getAllSanPham());

                capNhatTongTienChoHoaDon();
                JOptionPane.showMessageDialog(null, "Đã thêm sản phẩm \"" + tenSP + "\" thành công và cập nhật số lượng!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ! Vui lòng nhập số nguyên.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi thêm sản phẩm: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm để thêm!");
        }
    }

    public boolean checkProductExistsInInvoice(String maHoaDon, String maSP) {
        String sql = "SELECT COUNT(*) FROM ChiTietHoaDon WHERE IDHoaDon = (SELECT ID FROM HoaDon WHERE MaHoaDon = ?) AND IDSanPham = (SELECT ID FROM SanPham WHERE MaSanPham = ?)";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maHoaDon);
            ps.setString(2, maSP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if the product exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra sản phẩm: " + e.getMessage());
        }
        return false; // Return false if no match is found
    }

    public void capNhatSoLuongSanPhamTrongHoaDon(String maHoaDon, String maSP, int soLuongThem) {
        String sql = "UPDATE ChiTietHoaDon SET SoLuong = SoLuong + ? WHERE IDHoaDon = (SELECT ID FROM HoaDon WHERE MaHoaDon = ?) AND IDSanPham = (SELECT ID FROM SanPham WHERE MaSanPham = ?)";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, soLuongThem); // Increase the quantity
            ps.setString(2, maHoaDon); // Invoice ID
            ps.setString(3, maSP);     // Product ID

            ps.executeUpdate();
            capNhatChiTietHoaDon(maHoaDon);
            capNhatTongTienChoHoaDon();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật số lượng sản phẩm trong hóa đơn: " + e.getMessage());
        }
    }

    private int getIdSanPhamFromMaSP(String maSP) {
        // Query to retrieve ID corresponding to MaSP
        String sql = "SELECT ID FROM SanPham WHERE MaSanPham = ?";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maSP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy ID cho sản phẩm: " + e.getMessage());
        }
        return -1; // Return -1 if ID not found


    }//GEN-LAST:event_tblSanPhamMouseClicked
    public void themSanPhamVaoChiTietHoaDon(String maHoaDon, String maSP, int soLuong, BigDecimal donGia) {
        String sql = "INSERT INTO ChiTietHoaDon (IDHoaDon, IDSanPham, SoLuong, DonGia, TrangThai) "
                + "VALUES ((SELECT ID FROM HoaDon WHERE MaHoaDon = ?), "
                + "(SELECT ID FROM SanPham WHERE MaSanPham = ?), ?, ?, 1)";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maHoaDon);
            ps.setString(2, maSP);
            ps.setInt(3, soLuong);
            ps.setBigDecimal(4, donGia);
            ps.executeUpdate();
            // Sau khi thêm sản phẩm, cập nhật lại tổng tiền của hóa đơn
            capNhatChiTietHoaDon(maHoaDon);
            capNhatTongTienChoHoaDon();

            // Cập nhật giao diện (bảng chi tiết hóa đơn và tổng tiền)
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm sản phẩm: " + e.getMessage());
        }
    }

    public void capNhatSoLuongSanPham(int idSanPham, int soLuongThayDoi) {
        // SQL to update stock
        String sql = "UPDATE ChiTietSanPham SET SoLuong = CASE WHEN SoLuong >= ? THEN SoLuong - ? ELSE SoLuong END WHERE ID = ?";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, soLuongThayDoi); // Ensure enough stock exists
            ps.setInt(2, soLuongThayDoi); // Deduct stock
            ps.setInt(3, idSanPham);      // Reference product by ID

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {

                SanPhamRepo sanPhamRepository = new SanPhamRepo();
                sanPhamRepository.getAllSanPham();
                loadTables();
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật thất bại! Sản phẩm không đủ số lượng hoặc không tồn tại.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật số lượng sản phẩm: " + e.getMessage());
        }
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
    private void tblChuaThanhToanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChuaThanhToanMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblChuaThanhToanMouseClicked

    private void tblHoaDonChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonChiTietMouseClicked

        if (maHoaDonHienTai == null) {
            JOptionPane.showMessageDialog(null, "Chưa chọn hóa đơn!");
            return;
        }

        if (kiemTraTrangThaiHoaDon(maHoaDonHienTai)) {
            JOptionPane.showMessageDialog(null, "Hóa đơn đã thanh toán, không thể chỉnh sửa sản phẩm!");
            return;
        }

        int row = tblHoaDonChiTiet.getSelectedRow();
        if (row != -1) {
            try {
                String tenHangHoa = tblHoaDonChiTiet.getValueAt(row, 1).toString();
                int soLuongHienTai = Integer.parseInt(tblHoaDonChiTiet.getValueAt(row, 3).toString());

                Object[] options = {"Xóa sản phẩm", "Cập nhật số lượng"};
                int choice = JOptionPane.showOptionDialog(null,
                        "Bạn muốn làm gì với sản phẩm \"" + tenHangHoa + "\"?",
                        "Tùy chọn",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                int idSanPham = getIdSanPhamFromTenSanPham(tenHangHoa);
                if (idSanPham == -1) {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm!");
                    return;
                }

                int soLuongTonKho = getSoLuongTonKho(idSanPham);
                int soLuongKhaDung = soLuongTonKho + soLuongHienTai;

                if (choice == 0) {
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "Bạn có chắc chắn muốn xóa sản phẩm \"" + tenHangHoa + "\" khỏi giỏ hàng?",
                            "Xác nhận xóa sản phẩm", JOptionPane.YES_NO_OPTION);
                    if (confirm != JOptionPane.YES_OPTION) {
                        return;
                    }
                    
                    xoaSanPhamKhoiChiTietHoaDon(maHoaDonHienTai, tenHangHoa);
                    capNhatSoLuongSanPhamSetQuantity(idSanPham, soLuongHienTai);
                    capNhatChiTietHoaDon(maHoaDonHienTai);
                    capNhatTongTienChoHoaDon();
                    
                } else if (choice == 1) {
                    String soLuongMoiStr = JOptionPane.showInputDialog(null,
                            "Nhập số lượng mới cho sản phẩm \"" + tenHangHoa + "\":",
                            soLuongHienTai);
                    if (soLuongMoiStr == null || soLuongMoiStr.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Bạn chưa nhập số lượng!");
                        return;
                    }

                    int soLuongMoi = Integer.parseInt(soLuongMoiStr.trim());
                    if (soLuongMoi < 0) {
                        JOptionPane.showMessageDialog(null, "Số lượng không được âm!");
                        return;
                    }

                    if (soLuongMoi > soLuongKhaDung) {
                        JOptionPane.showMessageDialog(null, "Số lượng nhập vượt quá số lượng tồn kho khả dụng! Tồn kho hiện tại: " + soLuongKhaDung);
                        return;
                    }

                    if (soLuongMoi == 0) {
                        // Nếu số lượng mới là 0, xóa sản phẩm
                        xoaSanPhamKhoiChiTietHoaDon(maHoaDonHienTai, tenHangHoa);
                        capNhatSoLuongSanPhamSetQuantity(idSanPham, soLuongHienTai);
                        JOptionPane.showMessageDialog(null,
                                "Số lượng bằng 0, sản phẩm \"" + tenHangHoa + "\" đã được xóa khỏi giỏ hàng!");
                    } else {
                        // Cập nhật số lượng trong chi tiết hóa đơn
                        capNhatSoLuongSanPhamTrongChiTietHoaDon(maHoaDonHienTai, tenHangHoa, soLuongMoi);

                        // Điều chỉnh số lượng trong kho
                        int soLuongThayDoi = soLuongMoi - soLuongHienTai;
                        if (soLuongThayDoi > 0) {
                            // Giảm số lượng trong kho
                            capNhatSoLuongSanPham(idSanPham, soLuongThayDoi);
                        } else if (soLuongThayDoi < 0) {
                            // Tăng số lượng trong kho
                            capNhatSoLuongSanPhamSetQuantity(idSanPham, -soLuongThayDoi);
                        }

                    }

                    // Cập nhật bảng chi tiết hóa đơn và tổng tiền
                    capNhatChiTietHoaDon(maHoaDonHienTai);
                    capNhatTongTienChoHoaDon();
                }

                // Làm mới danh sách sản phẩm
                SanPhamRepo sanPhamRepository = new SanPhamRepo();
                sanPhamRepository.getAllSanPham();
                loadTables();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ! Vui lòng nhập số nguyên.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm để chỉnh sửa!");
        }

    }//GEN-LAST:event_tblHoaDonChiTietMouseClicked

    public int getSoLuongTonKho(int idSanPham) {
        String sql = "SELECT SoLuong FROM ChiTietSanPham WHERE ID = ?";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idSanPham);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("SoLuong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy số lượng tồn kho: " + e.getMessage());
        }
        return 0; // Trả về 0 nếu có lỗi hoặc không tìm thấy
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


    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed

        // Clear all text fields
        clearTextFields();

        // Clear the shopping cart (Gio Hang) table
        clearGioHang();

        // Reset the current invoice code to null
        maHoaDonHienTai = null;

        // Reset the total amount label and fields
        lblTongTien.setText("0 VND");
        txtTongTien.setText("");
        txtThanhTien.setText("");
        txtSoTienTra.setText("");
        txtTienDu.setText("");

        // Đặt lại trạng thái có thể chỉnh sửa của các thành phần giao diện
        txtMaHoaDon.setEditable(true);
        txtSoDienThoai.setEditable(true);
        txtTenKhachHang.setEditable(true);
        txtMaNhanVien.setEditable(true);
        txtTongTien.setEditable(false); // Tổng tiền thường không cho phép chỉnh sửa thủ công
        txtThanhTien.setEditable(false); // Thành tiền không cho phép chỉnh sửa thủ công
        txtSoTienTra.setEditable(true);
        txtTienDu.setEditable(false); // Tiền dư không cho phép chỉnh sửa thủ công
        rdoNam.setEnabled(true);
        rdoNu.setEnabled(true);
        cboPGG.setEnabled(true);

        // Đặt tab "Chưa thanh toán" làm tab được chọn
        tblHoaDon.setSelectedIndex(0); // 0 là chỉ số của tab "Chưa thanh toán"

        // Optionally, reload the tables to refresh data from the database
        loadTables();


    }//GEN-LAST:event_btnLamMoiActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BanHangView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BanHangView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BanHangView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BanHangView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BanHangView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuyDon;
    private javax.swing.JButton btnInBill;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnTaoDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboPGG;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblChuaThanhToan;
    private javax.swing.JTable tblDaThanhToan;
    private javax.swing.JTabbedPane tblHoaDon;
    private javax.swing.JTable tblHoaDonChiTiet;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtSoTienTra;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtThanhTien;
    private javax.swing.JTextField txtTienDu;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
    private void loadTables() {
        // Models for the HoaDon tables
        DefaultTableModel modelChuaThanhToan = (DefaultTableModel) tblChuaThanhToan.getModel();
        DefaultTableModel modelDaThanhToan = (DefaultTableModel) tblDaThanhToan.getModel();

        // Model for the SanPham table
        DefaultTableModel modelSanPham = (DefaultTableModel) tblSanPham.getModel();

        // Create repository instances
        HoaDonRepo hoaDonRepository = new HoaDonRepo();
        SanPhamRepo sanPhamRepository = new SanPhamRepo();

        // Get data for HoaDon tables
        List<HoaDon> chuaThanhToanList = hoaDonRepository.getHoaDonChuaThanhToan();
        List<HoaDon> daThanhToanList = hoaDonRepository.getHoaDonDaThanhToan();

        // Get data for SanPham table
        List<Object[]> sanPhamList = sanPhamRepository.getAllSanPham();

        // Fill data into HoaDon tables
        fillToTableHoaDon(chuaThanhToanList, modelChuaThanhToan);
        fillToTableHoaDon(daThanhToanList, modelDaThanhToan);

        // Fill data into SanPham table
        fillToTableSanPham(modelSanPham, sanPhamList);
    }

    private void fillToTableHoaDon(List<HoaDon> hoaDonList, DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        if (hoaDonList != null) {
            for (HoaDon hoaDon : hoaDonList) {
                String maPhieuGiamGia = hoaDon.getMaPhieuGiamGia() != null ? hoaDon.getMaPhieuGiamGia() : "Không có";
                BigDecimal giamGia = hoaDon.getGiamGia() != null ? hoaDon.getGiamGia() : BigDecimal.ZERO;
                BigDecimal tongTien = hoaDon.getTongTien() != null ? hoaDon.getTongTien() : BigDecimal.ZERO;
                BigDecimal thanhTien = tongTien.subtract(giamGia);

                tableModel.insertRow(0, new Object[]{
                    hoaDon.getMaHoaDon(),
                    hoaDon.getMaKhachHang(),
                    hoaDon.getMaNhanVien(),
                    maPhieuGiamGia,
                    hoaDon.getNgayTao(),
                    formatVND(tongTien), // Định dạng Tổng Tiền
                    formatVND(thanhTien) // Định dạng Thành Tiền
                });
            }
        }
    }

    private void fillToTableSanPham(DefaultTableModel tableModel, List<Object[]> productData) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        if (productData != null && !productData.isEmpty()) {
            for (Object[] row : productData) {
                // Kiểm tra số cột của dữ liệu
                if (row.length != 8) { // Mong đợi 8 cột từ repository (bao gồm STT)
                    JOptionPane.showMessageDialog(null, "Dữ liệu sản phẩm không đúng định dạng!");
                    continue;
                }

                Object[] newRow = new Object[8]; // tblSanPham có 8 cột
                newRow[0] = row[0]; // Cột STT (đã có từ SQL)
                newRow[1] = row[1]; // Mã SP
                newRow[2] = row[2]; // Tên SP
                newRow[3] = row[3]; // Thương Hiệu
                newRow[4] = formatVND((BigDecimal) row[4]); // Giá Bán (cột 4 trong dữ liệu)
                newRow[5] = row[5]; // Số Lượng
                newRow[6] = row[6]; // Size
                newRow[7] = row[7]; // Màu Sắc

                tableModel.addRow(newRow); // Thêm dòng vào bảng
            }
        }
    }

    private void searchAndUpdateTable() {
        // Get the search keyword
        String keyword = txtTimKiem.getText().trim();

        // Get the table model
        DefaultTableModel modelSanPham = (DefaultTableModel) tblSanPham.getModel();

        // Create repository instance
        SanPhamRepo repository = new SanPhamRepo();

        // Perform search and get results
        List<Object[]> searchResults = repository.searchSanPham(keyword);

        // Fill the table with search results
        fillToTableSanPham(modelSanPham, searchResults);
    }
    //////Tao hoa don

    private void checkSdtKhachHang(String soDienThoai) {
        String sql = "SELECT TenKhachHang, GioiTinh FROM KhachHang WHERE SDT = ?";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, soDienThoai);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Fill customer name
                    txtTenKhachHang.setText(rs.getString("TenKhachHang"));

                    // Fetch gender and interpret BIT value
                    int gioiTinh = rs.getInt("GioiTinh"); // GioiTinh is BIT (1 = Nam, 0 = Nu)

                    if (gioiTinh == 1) {
                        rdoNam.setSelected(true); // Select male radio button
                    } else if (gioiTinh == 0) {
                        rdoNu.setSelected(true); // Select female radio button
                    } else {
                        // Clear gender selection if invalid
                        rdoNam.setSelected(false);
                        rdoNu.setSelected(false);
                    }
                } else {
                    // Clear fields if no matching customer is found
                    txtTenKhachHang.setText("");
                    rdoNam.setSelected(false);
                    rdoNu.setSelected(false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void luuKhachHang(String soDienThoai, String tenKhachHang, boolean isNam) {
        String sql = "INSERT INTO KhachHang (SDT, TenKhachHang, GioiTinh) VALUES (?, ?, ?)";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, soDienThoai);
            ps.setString(2, tenKhachHang);
            ps.setInt(3, isNam ? 1 : 0); // 1 for Nam, 0 for Nu

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void taoHoaDon() {
        String maHoaDon = txtMaHoaDon.getText(); // Lấy mã hóa đơn từ giao diện
        String soDienThoai = txtSoDienThoai.getText(); // Lấy số điện thoại khách hàng
        String tenKhachHang = txtTenKhachHang.getText(); // Lấy tên khách hàng
        boolean isNam = rdoNam.isSelected(); // Kiểm tra giới tính của khách hàng (Nam hoặc Nữ)
        String maNhanVien = txtMaNhanVien.getText(); // Lấy mã nhân viên từ giao diện

        // Sử dụng LocalDateTime để lấy thời gian hiện tại
        LocalDateTime ngayTao = LocalDateTime.now(); // Thời gian hiện tại
        String formattedNgayTao = ngayTao.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Định dạng thời gian

        // Kiểm tra tính hợp lệ của các trường đầu vào
        if (maNhanVien.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền mã nhân viên."); // Thông báo nếu mã nhân viên trống
            return;
        }

        if (!txtMaHoaDon.getText().trim().isEmpty()) {
            txtMaHoaDon.setText("");
            JOptionPane.showMessageDialog(null, "hóa đơn đã tồn tại vui lòng tạo lại đơn");
            return;
        }
        
        
        
        if (!soDienThoai.isEmpty()) {
        if (!soDienThoai.matches("\\d{8,11}")) {
        JOptionPane.showMessageDialog(null, "Số điện thoại chỉ được chứa các chữ số (0-9) và có độ dài từ 8 đến 11 ký tự!");
        return;
    }
}

        try (Connection connection = DBConnect.getConnection()) {
            // Kiểm tra xem khách hàng đã tồn tại chưa, nếu chưa thì thêm mới
            int customerId = timSdtKhachHang(soDienThoai); // Lấy ID khách hàng dựa trên số điện thoại
            if (customerId == -1) {
                // Nếu khách hàng chưa tồn tại, thêm mới vào bảng KhachHang
                String customerSql = "INSERT INTO KhachHang (SDT, TenKhachHang, GioiTinh) VALUES (?, ?, ?)";
                try (PreparedStatement customerPs = connection.prepareStatement(customerSql, Statement.RETURN_GENERATED_KEYS)) {
                    customerPs.setString(1, soDienThoai); // Gán số điện thoại
                    customerPs.setString(2, tenKhachHang); // Gán tên khách hàng
                    customerPs.setInt(3, isNam ? 1 : 0); // Gán giới tính (1 cho Nam, 0 cho Nữ)

                    customerPs.executeUpdate(); // Thực thi câu lệnh thêm khách hàng
                    ResultSet generatedKeys = customerPs.getGeneratedKeys(); // Lấy khóa tự động tạo
                    if (generatedKeys.next()) {
                        customerId = generatedKeys.getInt(1); // Lấy ID khách hàng vừa tạo
                    }
                }
            }

            int employeeId;
            try {
                employeeId = Integer.parseInt(maNhanVien); // Chuyển đổi mã nhân viên từ chuỗi sang số
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Mã nhân viên không hợp lệ! Vui lòng nhập số.");
                return;
            }

            String checkEmployeeSql = "SELECT COUNT(*) FROM NhanVien WHERE ID = ?";
            try (PreparedStatement checkPs = connection.prepareStatement(checkEmployeeSql)) {
                checkPs.setInt(1, employeeId);
                ResultSet rs = checkPs.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) { // Nếu không tìm thấy nhân viên
                    JOptionPane.showMessageDialog(null, "Không có nhân viên này!");
                    return;
                }
            }
            // Tạo hóa đơn mới
            String invoiceSql = "INSERT INTO HoaDon (MaHoaDon, IDKhachHang, IDNhanVien, NgayTao, TrangThai, TongTien) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement invoicePs = connection.prepareStatement(invoiceSql)) {
                invoicePs.setString(1, maHoaDon); // Gán mã hóa đơn
                invoicePs.setInt(2, customerId); // Gán ID khách hàng
                invoicePs.setInt(3, Integer.parseInt(maNhanVien)); // Gán ID nhân viên
                invoicePs.setTimestamp(4, Timestamp.valueOf(formattedNgayTao)); // Gán thời gian tạo hóa đơn
                invoicePs.setBoolean(5, false); // Đặt trạng thái hóa đơn là chưa thanh toán (false)
                invoicePs.setBigDecimal(6, null); // Đặt tổng tiền ban đầu là 0

                invoicePs.executeUpdate(); // Thực thi câu lệnh tạo hóa đơn
                JOptionPane.showMessageDialog(null, "Tạo hóa đơn thành công!"); // Thông báo tạo hóa đơn thành công
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Tạo hóa đơn lỗi: " + e.getMessage()); // Thông báo lỗi nếu có sự cố
        }
    }

// Helper method to fetch customer ID from the database
// Helper to fetch Customer ID from database
    private int timSdtKhachHang(String soDienThoai) throws SQLException {
        String sql = "SELECT ID FROM KhachHang WHERE SDT = ?";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, soDienThoai);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        }
        return -1; // Return invalid ID if customer doesn't exist
    }

    public void addToInvoiceDetails(String maSP, String tenSP, BigDecimal giaBan, int soLuong) {
        // Calculate total price

        // Add product to the invoice table (model)
        HoaDonChiTietRepo hoaDonChiTietRepository = new HoaDonChiTietRepo();
        hoaDonChiTietRepository.insertIntoChiTietHoaDon(maSP, maSP, soLuong, giaBan);
    }

    private BigDecimal tinhTongTienTuTblHoaDonChiTiet() {
        BigDecimal tongTien = BigDecimal.ZERO;
        DefaultTableModel model = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        if (model.getRowCount() == 0) {
            lblTongTien.setText("0 VND");
            txtTongTien.setText("");
            txtThanhTien.setText("");
            return tongTien;
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            Object thanhTienObj = model.getValueAt(i, 4); // Cột "ThanhTien"
            if (thanhTienObj instanceof BigDecimal) {
                BigDecimal thanhTien = (BigDecimal) thanhTienObj;
                tongTien = tongTien.add(thanhTien);
            }
        }

        lblTongTien.setText(tongTien.compareTo(BigDecimal.ZERO) > 0 ? formatVND(tongTien) + " VND" : "0 VND");
        txtTongTien.setText(formatVND(tongTien));
        txtThanhTien.setText(formatVND(tongTien));

        return tongTien;
    }

    private void luuTongTienVaoHoaDon(BigDecimal tongTien, String maHoaDon) {
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

    private void capNhatTongTienChoHoaDon() {
        if (maHoaDonHienTai == null) {
            JOptionPane.showMessageDialog(null, "Chưa chọn hóa đơn để cập nhật tổng tiền!");
            return;
        }

        // Tính tổng tiền từ tblHoaDonChiTiet
        BigDecimal tongTien = tinhTongTienTuTblHoaDonChiTiet();

        // Lưu tổng tiền vào bảng HoaDon
        luuTongTienVaoHoaDon(tongTien, maHoaDonHienTai);

        // Cập nhật giao diện với định dạng tiền Việt Nam
        txtTongTien.setText(formatVND(tongTien));
        txtThanhTien.setText(formatVND(tongTien));
        lblTongTien.setText(tongTien.compareTo(BigDecimal.ZERO) > 0 ? formatVND(tongTien) + " VND" : "0 VND");
    }

    private void tinhTienDu() {
        try {
            String thanhTienStr = txtThanhTien.getText().trim().replace(".", "");
            String soTienTraStr = txtSoTienTra.getText().trim().replace(".", "");

            // Kiểm tra nếu một trong hai trường trống
            if (thanhTienStr.isEmpty() || soTienTraStr.isEmpty()) {
                txtTienDu.setText("");
                return;
            }

            // Phân tích chuỗi thành BigDecimal
            BigDecimal thanhTien = new BigDecimal(thanhTienStr);
            BigDecimal soTienTra = new BigDecimal(soTienTraStr);

            // Tính tiền dư
            BigDecimal tienDu = soTienTra.subtract(thanhTien);

            // Cập nhật giao diện với định dạng tiền Việt Nam
            txtTienDu.setText(formatVND(tienDu));
        } catch (NumberFormatException e) {
            txtTienDu.setText("");
            JOptionPane.showMessageDialog(null, "Số tiền không hợp lệ! Vui lòng nhập số.");
        }
    }

    private boolean kiemTraTrangThaiHoaDon(String maHoaDonHienTai) {
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

    public int getIdSanPhamFromTenSanPham(String tenSanPham) {
        String sql = "SELECT ID FROM SanPham WHERE TenSanPham = ?";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, tenSanPham); // Bind product name to query
            ResultSet rs = ps.executeQuery();
            if (rs.next()) { // If a matching product is found
                return rs.getInt("ID"); // Return the product ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy ID cho sản phẩm: " + e.getMessage());
        }
        return -1; // Return -1 if no matching product is found
    }

    private void loadPhieuGiamGia() {
        List<PhieuGiamGia> danhSach = pggRepo.getActivePhieuGiamGia();

        // Rong - k chon
        cboPGG.addItem(" ");

        // Thêm các mã phiếu giảm giá vào cboPGG
        Date now = new Date();
        for (PhieuGiamGia pgg : danhSach) {
            if (!pgg.getNgayBatDau().after(now) && !pgg.getNgayKetThuc().before(now)) {
                cboPGG.addItem(pgg.getMaPhieuGiamGia());
            }
        }
    }

    private String formatVND(BigDecimal amount) {
        if (amount == null) {
            return "0";
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String formatted = formatter.format(amount.setScale(0, RoundingMode.HALF_UP));
        return formatted.replace(",", "."); // Thay dấu phẩy bằng dấu chấm
    }
}
