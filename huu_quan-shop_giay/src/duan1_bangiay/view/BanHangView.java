/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package duan1_bangiay.view;

import duan1_bangiay.model.HoaDon;
import duan1_bangiay.repository.HoaDonChiTietRepository;
import duan1_bangiay.repository.HoaDonRepository;
import duan1_bangiay.repository.SanPhamRepository;
import duan1_bangiay.utils.DBConnect;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Timer;


/**
 *
 * @author quanr
 */
public class BanHangView extends javax.swing.JFrame {

    /**
     * Creates new form BanHangVIEW
     */
    private String maHoaDonHienTai = null; // Biến toàn cục để lưu mã hóa đơn hiện tại

    public BanHangView() {
        initComponents();
        loadTables();
        searchListener();
        donHangListener();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Tạo Timer để cập nhật thời gian
        Timer timer = new Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();
            txtNgayTao.setText(now.format(formatter)); // Hiển thị thời gian đã định dạng
        });
        timer.start(); // Bắt đầu timer

        txtMaNhanVien.setText(txtMaNhanVien.getText());
        txtSoDienThoai.addActionListener(e -> checkAndFillCustomerDetails(txtSoDienThoai.getText()));
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
    }

    private void searchListener() {
        txtTimKiem.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                searchAndUpdateTable();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                searchAndUpdateTable();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                searchAndUpdateTable();
            }
        });
    }

    private void donHangListener() {

        tblChuaThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tblChuaThanhToan.getSelectedRow();

                if (selectedRow != -1) {
                    handleOrderRowClick(selectedRow, tblChuaThanhToan);
                    populateFieldsFromInvoice(selectedRow); // New method to fill text fields
                }
            }
        });

        tblDaThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tblDaThanhToan.getSelectedRow();
                if (selectedRow != -1) {
                    handleOrderRowClick(selectedRow, tblDaThanhToan);
                    clearTextFields();
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
        // Reset radio buttons (assuming there are male and female options)
        rdoNam.setSelected(false);
        rdoNu.setSelected(false);

        System.out.println("All text fields and radio buttons have been cleared.");
    }

    private void populateFieldsFromInvoice(int selectedRow) {
        try {
            // Retrieve MaHoaDon from the selected row in tblChuaThanhToan
            DefaultTableModel model = (DefaultTableModel) tblChuaThanhToan.getModel();
            String maHoaDon = model.getValueAt(selectedRow, 0).toString(); // Assuming MaHoaDon is in column 0

            // Query to get all invoice details from HoaDon table based on MaHoaDon
            String sql = "SELECT hd.MaHoaDon, hd.TongTien, hd.ThanhTien, kh.SDT, kh.TenKhachHang, kh.GioiTinh, nv.MaNhanVien "
                    + "FROM HoaDon hd "
                    + "JOIN KhachHang kh ON hd.IDKhachHang = kh.ID "
                    + "JOIN NhanVien nv ON hd.IDNhanVien = nv.ID "
                    + "WHERE hd.MaHoaDon = ?";

            try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, maHoaDon);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Fill text fields with data from ResultSet
                        txtMaHoaDon.setText(rs.getString("MaHoaDon"));
                        txtSoDienThoai.setText(rs.getString("SDT"));
                        txtTenKhachHang.setText(rs.getString("TenKhachHang"));
                        rdoNam.setSelected(rs.getInt("GioiTinh") == 1); // 1 for Male
                        rdoNu.setSelected(rs.getInt("GioiTinh") == 0);  // 0 for Female
                        txtMaNhanVien.setText(rs.getString("MaNhanVien"));
                        BigDecimal tongTien = rs.getBigDecimal("TongTien");
                        BigDecimal thanhTien = rs.getBigDecimal("TongTien");
                        txtTongTien.setText(tongTien != null ? tongTien.toString() : "");
                        txtThanhTien.setText(thanhTien != null ? thanhTien.toString() : "");

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
            System.out.println("Mã Hóa Đơn Hiện Tại: " + maHoaDonHienTai);

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
        btnHuyDon1 = new javax.swing.JButton();

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
                "Mã Hóa Đơn", "Mã Khách Hàng", "Mã Nhân Viên", "Mã Giảm Giá", "Ngày Mua", "Tổng Tiền"
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

        cboPGG.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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

        btnHuyDon1.setText("LÀM MỚI");
        btnHuyDon1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 204, 0)));
        btnHuyDon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyDon1ActionPerformed(evt);
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
                                .addComponent(btnTaoDon, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnInBill, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHuyDon1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
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
                            .addComponent(tblHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnInBill, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnTaoDon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnHuyDon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnHuyDon1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    JOptionPane.showMessageDialog(null, "Đơn hàng đã được xóa thành công và số lượng sản phẩm đã được cập nhật!");
                    loadTables(); // Tải lại bảng dữ liệu
                    clearGioHang();
                    clearTextFields();
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
        createInvoice();
        loadTables();
    }//GEN-LAST:event_btnTaoDonActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        try {
            // Validate essential fields
            String maHoaDon = txtMaHoaDon.getText().trim();
            String thanhTienStr = txtThanhTien.getText().trim(); // Thành Tiền
            String soTienTraStr = txtSoTienTra.getText().trim(); // Số Tiền Trả

            if (maHoaDon.isEmpty() || thanhTienStr.isEmpty() || soTienTraStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin trước khi thanh toán!");
                return;
            }

            // Convert values to BigDecimal for calculations
            BigDecimal thanhTien = new BigDecimal(thanhTienStr);
            BigDecimal soTienTra = new BigDecimal(soTienTraStr);

            // Calculate Tiền Dư (Change Due)
            BigDecimal tienDu = soTienTra.subtract(thanhTien);

            // Check if payment is sufficient
            if (tienDu.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(null, "Số tiền trả không đủ để thanh toán hóa đơn!");
                return;
            }

            // Update Tiền Dư field in UI
            txtTienDu.setText(tienDu.toString());

            // Update database: HoaDon table
            String sql = "UPDATE HoaDon SET TongTien = ?, GiamGia = ?, ThanhTien = ?, TrangThai = ? WHERE MaHoaDon = ?";
            try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setBigDecimal(1, thanhTien); // Total amount
                ps.setBigDecimal(2, BigDecimal.ZERO); // Discount, assumed to be 0 for simplicity
                ps.setBigDecimal(3, thanhTien); // Final amount after applying discounts
                ps.setInt(4, 1);        // Mark invoice as paid
                ps.setString(5, maHoaDon);     // Invoice code

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Thanh toán thành công!");

                    // Move invoice to "Đã Thanh Toán" tab and refresh UI
                    loadTables();
                    clearTextFields(); // Reset form
                    clearGioHang();
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn để cập nhật!");
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
            return; // Exit if no order is selected
        }

// Check if the invoice has already been paid
        boolean daThanhToan = kiemTraTrangThaiHoaDon(maHoaDonHienTai);
        if (daThanhToan) {
            JOptionPane.showMessageDialog(null, "Không thể thêm sản phẩm vào hóa đơn đã thanh toán!");
            return;
        }

        int row = tblSanPham.getSelectedRow();
        if (row != -1) {
            try {
                // Retrieve product details from the selected row
                String maSP = tblSanPham.getValueAt(row, 1).toString(); // Mã sản phẩm
                String tenSP = tblSanPham.getValueAt(row, 2).toString(); // Tên sản phẩm
                Integer soLuongTon = (Integer) tblSanPham.getValueAt(row, 5); // Tên sản phẩm
                BigDecimal donGia = new BigDecimal(tblSanPham.getValueAt(row, 4).toString()); // Giá bán
                // Prompt for the quantity
                String soLuongStr = JOptionPane.showInputDialog("Nhập số lượng cho sản phẩm: " + tenSP);
                if (soLuongStr == null || soLuongStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Bạn chưa nhập số lượng!");
                    return;
                }

                // Parse and validate the quantity input
                int soLuong = Integer.parseInt(soLuongStr.trim());

                if (soLuong <= 0) {
                    JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0!");
                    return;
                }
                if (soLuong > soLuongTon) {
                    JOptionPane.showMessageDialog(null, "Không thể thêm sản phẩm \"" + tenSP + "\". Số lượng tồn kho chỉ còn: " + soLuongTon);
                    return;
                }
                // Check if the product already exists in the invoice details
                boolean productExists = checkProductExistsInInvoice(maHoaDonHienTai, maSP);

                if (productExists) {
                    // If the product exists, update its quantity in the invoice details table
                    updateProductQuantityInInvoice(maHoaDonHienTai, maSP, soLuong);
                } else {
                    // If the product doesn't exist, add it as a new row in the invoice details table
                    themSanPhamVaoChiTietHoaDon(maHoaDonHienTai, maSP, soLuong, donGia);
                }

                // Update product quantity in ChiTietSanPham
                int idSanPham = getIdSanPhamFromMaSP(maSP);
                capNhatSoLuongSanPham(idSanPham, soLuong); // Deduct stock quantity

                // Refresh invoice details and product list
                capNhatChiTietHoaDon(maHoaDonHienTai);
                SanPhamRepository sanPhamRepository = new SanPhamRepository();
                sanPhamRepository.getAllSanPham(); // Refresh product inventory

                // Update total price for the invoice
                capNhatTongTienChoHoaDon();

                
                loadTables();
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

    public void updateProductQuantityInInvoice(String maHoaDon, String maSP, int soLuongThem) {
        String sql = "UPDATE ChiTietHoaDon SET SoLuong = SoLuong + ? WHERE IDHoaDon = (SELECT ID FROM HoaDon WHERE MaHoaDon = ?) AND IDSanPham = (SELECT ID FROM SanPham WHERE MaSanPham = ?)";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, soLuongThem); // Increase the quantity
            ps.setString(2, maHoaDon); // Invoice ID
            ps.setString(3, maSP);     // Product ID
            ps.executeUpdate();
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
            //JOptionPane.showMessageDialog(null, "Đã thêm sản phẩm vào hóa đơn chi tiết!");
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
                // Refresh inventory and UI
                SanPhamRepository sanPhamRepository = new SanPhamRepository();
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
                    danhSachChiTiet.add(new Object[]{
                        rs.getInt("STT"),
                        rs.getString("TenSanPham"),
                        rs.getBigDecimal("DonGia"),
                        rs.getInt("SoLuong"),
                        rs.getBigDecimal("ThanhTien")
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
        // TODO add your handling code here:
    if (maHoaDonHienTai == null) {
        JOptionPane.showMessageDialog(null, "Chưa chọn hóa đơn!");
        return; // Exit if no invoice is selected
    }

    // Check if the invoice has already been paid
    if (kiemTraTrangThaiHoaDon(maHoaDonHienTai)) {
        JOptionPane.showMessageDialog(null, "Hóa đơn đã thanh toán, không thể xóa hoặc cập nhật sản phẩm!");
        return; // Exit if the invoice is paid
    }

    int row = tblHoaDonChiTiet.getSelectedRow();
    if (row != -1) {
        try {
            // Retrieve product details from the selected row
            String tenHangHoa = tblHoaDonChiTiet.getValueAt(row, 1).toString(); // Product Name
            int soLuongHienTai = Integer.parseInt(tblHoaDonChiTiet.getValueAt(row, 3).toString()); // Current Quantity
            BigDecimal donGia = new BigDecimal(tblHoaDonChiTiet.getValueAt(row, 2).toString()); // Unit Price

            // Show option dialog for user to choose action
            Object[] options = {"Xóa sản phẩm", "Cập nhật số lượng", "Hủy"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Chọn hành động cho sản phẩm \"" + tenHangHoa + "\":",
                    "Chọn hành động",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[2]); // Default button is "Hủy"

            if (choice == JOptionPane.YES_OPTION) { // Xóa sản phẩm
                // Confirm deletion
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xóa sản phẩm \"" + tenHangHoa + "\" khỏi hóa đơn?",
                        "Xác nhận xóa sản phẩm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Delete the product from invoice details
                    xoaSanPhamKhoiChiTietHoaDon(maHoaDonHienTai, tenHangHoa);

                    // Update product quantity in the inventory
                    int idSanPham = getIdSanPhamFromTenSanPham(tenHangHoa); // Retrieve Product ID
                    if (idSanPham != -1) {
                        capNhatSoLuongSanPhamSetQuantity(idSanPham, soLuongHienTai); // Add the quantity back to inventory
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm để cập nhật số lượng.");
                    }

                    // Refresh table details
                    capNhatChiTietHoaDon(maHoaDonHienTai); // Refresh invoice details
                    SanPhamRepository sanPhamRepository = new SanPhamRepository();
                    sanPhamRepository.getAllSanPham(); // Refresh inventory list
                    loadTables();

                    // Recalculate and update total
                    capNhatTongTienChoHoaDon();
                }
            } else if (choice == JOptionPane.NO_OPTION) { // Cập nhật số lượng
                // Show dialog to input new quantity
                String newQuantityStr = JOptionPane.showInputDialog(null,
                        "Nhập số lượng mới cho sản phẩm \"" + tenHangHoa + "\":",
                        soLuongHienTai); // Default value is current quantity

                if (newQuantityStr != null && !newQuantityStr.trim().isEmpty()) {
                    try {
                        int newQuantity = Integer.parseInt(newQuantityStr);

                        if (newQuantity < 0) {
                            JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn hoặc bằng 0!");
                            return;
                        }

                        // Check inventory availability
                        int idSanPham = getIdSanPhamFromTenSanPham(tenHangHoa);
                        if (idSanPham != -1) {
                            int soLuongTonKho = laySoLuongTonKho(idSanPham);
                            int soLuongCanThem = newQuantity - soLuongHienTai; // Positive if increasing, negative if reducing
                            if (soLuongCanThem > 0 && soLuongCanThem > soLuongTonKho) {
                                JOptionPane.showMessageDialog(null, "Không đủ hàng trong kho! Số lượng tồn kho hiện tại: " + soLuongTonKho);
                                return;
                            }

                            // Update quantity in the invoice detail
                            capNhatSoLuongSanPhamTrongHoaDon(maHoaDonHienTai, tenHangHoa, newQuantity);

                            // Adjust inventory
                            capNhatSoLuongSanPhamSetQuantity(idSanPham, -soLuongCanThem); // Adjust inventory: negative to deduct, positive to add back

                            // Refresh table details
                            capNhatChiTietHoaDon(maHoaDonHienTai);
                            loadTables();

                            // Recalculate and update total
                            capNhatTongTienChoHoaDon();

                            JOptionPane.showMessageDialog(null, "Đã cập nhật số lượng sản phẩm thành công!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm trong kho!");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ! Vui lòng nhập lại.");
                    }
                }
            }
            // If choice is CANCEL_OPTION or closed dialog, do nothing
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xử lý số lượng sản phẩm! Vui lòng kiểm tra lại.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm để xóa hoặc cập nhật!");
    }
    }//GEN-LAST:event_tblHoaDonChiTietMouseClicked

    
    private void capNhatSoLuongSanPhamTrongHoaDon(String maHoaDon, String tenHangHoa, int newQuantity) {
    String sql = "UPDATE ChiTietHoaDon SET SoLuong = ? " +
                 "WHERE IDHoaDon = (SELECT ID FROM HoaDon WHERE MaHoaDon = ?) " +
                 "AND IDSanPham = (SELECT TOP 1 ID FROM SanPham WHERE TenSanPham = ?)";
    try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, newQuantity);  // New quantity
        ps.setString(2, maHoaDon);  // Invoice ID
        ps.setString(3, tenHangHoa); // Product name

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            JOptionPane.showMessageDialog(null, "Không thể cập nhật số lượng sản phẩm!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật số lượng: " + e.getMessage());
    }
}

    
private int laySoLuongTonKho(int idSanPham) {
    String sql = "SELECT SoLuong FROM ChiTietSanPham WHERE ID = ?";
    try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, idSanPham);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("SoLuong");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra số lượng tồn kho: " + e.getMessage());
    }
    return 0; // Return 0 if error occurs or no stock found
}
    private void btnHuyDon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyDon1ActionPerformed
        // TODO add your handling code here:
        clearGioHang();
        clearTextFields();
        loadTables();
        txtTimKiem.setText("");
        lblTongTien.setText("0 VND");
    }//GEN-LAST:event_btnHuyDon1ActionPerformed

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
    private javax.swing.JButton btnHuyDon1;
    private javax.swing.JButton btnInBill;
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
        HoaDonRepository hoaDonRepository = new HoaDonRepository();
        SanPhamRepository sanPhamRepository = new SanPhamRepository();

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
        // Clear existing rows in the table model
        tableModel.setRowCount(0);

        // Check if the list is not null to avoid NullPointerException
        if (hoaDonList != null) {
            // Add rows to the table model
            for (HoaDon hoaDon : hoaDonList) {
                tableModel.addRow(new Object[]{
                    hoaDon.getMaHoaDon(), // Mã Hóa Đơn
                    hoaDon.getMaKhachHang(), // Mã Khách Hàng
                    hoaDon.getMaNhanVien(), // Mã Nhân Viên
                    hoaDon.getIdPhieuGiamGia(), // Mã Giảm Giá
                    hoaDon.getNgayTao(), // Ngày Mua
                    hoaDon.getTongTien() // Tổng Tiền
                });
            }
        }
    }

    private void fillToTableSanPham(DefaultTableModel tableModel, List<Object[]> productData) {
        // Clear existing rows in the table model
        tableModel.setRowCount(0);

        // Add rows to the table model
        if (productData != null && !productData.isEmpty()) {
            for (Object[] row : productData) {
                tableModel.addRow(row);
            }
        } else {
            System.out.println("No data available to populate the table.");
        }
    }

    private void searchAndUpdateTable() {
        // Get the search keyword
        String keyword = txtTimKiem.getText().trim();

        // Get the table model
        DefaultTableModel modelSanPham = (DefaultTableModel) tblSanPham.getModel();

        // Create repository instance
        SanPhamRepository repository = new SanPhamRepository();

        // Perform search and get results
        List<Object[]> searchResults = repository.searchSanPham(keyword);

        // Fill the table with search results
        fillToTableSanPham(modelSanPham, searchResults);
    }
    //////Tao hoa don

    private void checkAndFillCustomerDetails(String soDienThoai) {
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

    private void saveCustomer(String soDienThoai, String tenKhachHang, boolean isNam) {
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

    private void createInvoice() {
        String maHoaDon = txtMaHoaDon.getText();
        String soDienThoai = txtSoDienThoai.getText();
        String tenKhachHang = txtTenKhachHang.getText();
        boolean isNam = rdoNam.isSelected();
        String maNhanVien = txtMaNhanVien.getText();

        // Use LocalDateTime for the current date and time
        LocalDateTime ngayTao = LocalDateTime.now(); // Current date and time
        String formattedNgayTao = ngayTao.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Correct format

        // Validate inputs
        if (soDienThoai.isEmpty() && tenKhachHang.isEmpty() && maNhanVien.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        try (Connection connection = DBConnect.getConnection()) {
            // Check if customer exists, or insert new customer
            int customerId = fetchCustomerId(soDienThoai);
            if (customerId == -1) {
                String customerSql = "INSERT INTO KhachHang (SDT, TenKhachHang, GioiTinh) VALUES (?, ?, ?)";
                try (PreparedStatement customerPs = connection.prepareStatement(customerSql, Statement.RETURN_GENERATED_KEYS)) {
                    customerPs.setString(1, soDienThoai);
                    customerPs.setString(2, tenKhachHang);
                    customerPs.setInt(3, isNam ? 1 : 0); // 1 for "Nam", 0 for "Nữ"

                    customerPs.executeUpdate();
                    ResultSet generatedKeys = customerPs.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        customerId = generatedKeys.getInt(1);
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
            // Create invoice
            String invoiceSql = "INSERT INTO HoaDon (MaHoaDon, IDKhachHang, IDNhanVien, NgayTao, TrangThai, TongTien) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement invoicePs = connection.prepareStatement(invoiceSql)) {
                invoicePs.setString(1, maHoaDon); // Mã hóa đơn
                invoicePs.setInt(2, customerId); // ID khách hàng
                invoicePs.setInt(3, Integer.parseInt(maNhanVien)); // ID nhân viên
                invoicePs.setTimestamp(4, Timestamp.valueOf(formattedNgayTao)); // Ngày tạo
                invoicePs.setBoolean(5, false); // Chưa thanh toán (false)
                invoicePs.setBigDecimal(6, null); // Set TongTien to 0 initially

                invoicePs.executeUpdate();
                JOptionPane.showMessageDialog(null, "Tạo hóa đơn thành công!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Tạo hóa đơn lỗi: " + e.getMessage());
        }
    }
// Helper method to fetch customer ID from the database

// Helper to fetch Customer ID from database
    private int fetchCustomerId(String soDienThoai) throws SQLException {
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
        HoaDonChiTietRepository hoaDonChiTietRepository = new HoaDonChiTietRepository();
        hoaDonChiTietRepository.insertIntoChiTietHoaDon(maSP, maSP, soLuong, giaBan);
    }

    private BigDecimal tinhTongTienTuTblHoaDonChiTiet() {
        BigDecimal tongTien = BigDecimal.ZERO; // Khởi tạo tổng tiền ban đầu là 0
        DefaultTableModel model = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        if (model.getRowCount() == 0) {
            lblTongTien.setText(" 0 VND");
            return tongTien;
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            Object thanhTienObj = model.getValueAt(i, 4); // Cột 4 là "ThanhTien"
            if (thanhTienObj != null) {
                BigDecimal thanhTien = new BigDecimal(thanhTienObj.toString());
                tongTien = tongTien.add(thanhTien); // Cộng từng giá trị "ThanhTien" vào tổng
                lblTongTien.setText(tongTien.compareTo(BigDecimal.ZERO) > 0
                        ? tongTien.toString() + " VND"
                        : " 0 VND");
                txtTongTien.setText(tongTien.toString());
                txtThanhTien.setText(tongTien.toString());
            }
        }

        return tongTien;
    }

    private void luuTongTienVaoHoaDon(BigDecimal tongTien, String maHoaDon) {
        String sql = "UPDATE HoaDon SET TongTien = ? WHERE MaHoaDon = ?";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setBigDecimal(1, tongTien); // Gán tổng tiền vào tham số đầu tiên
            ps.setString(2, maHoaDon);    // Gán mã hóa đơn vào tham số thứ hai

            int rowsAffected = ps.executeUpdate(); // Thực thi câu lệnh SQL
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Đã cập nhật tổng tiền thành công!");
            } else {
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
}
    private void tinhTienDu() {
        try {
            // Get the values from the text fields
            BigDecimal thanhTien = new BigDecimal(txtThanhTien.getText().trim()); // Thành Tiền
            BigDecimal soTienTra = new BigDecimal(txtSoTienTra.getText().trim()); // Số Tiền Trả

            // Calculate Tiền Dư (Change Due)
            BigDecimal tienDu = soTienTra.subtract(thanhTien);

            // Update Tiền Dư field
            txtTienDu.setText(tienDu.toString());
        } catch (NumberFormatException e) {
            // Handle invalid or empty input
            txtTienDu.setText(""); // Clear Tiền Dư field if input is invalid
            System.err.println("Lỗi định dạng số: " + e.getMessage());
        }
    }

    private boolean kiemTraTrangThaiHoaDon(String maHoaDonHienTai) {
        try {
            // Assume you have a database connection method
            Connection conn = DBConnect.getConnection();
            String sql = "SELECT trangThai FROM HoaDon WHERE maHoaDon = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maHoaDonHienTai);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int trangThai = rs.getInt("trangThai");
                return trangThai == 1; // Return true if status is 1 (paid)
            }

            return false; // Default to false if no record found
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kiểm tra trạng thái hóa đơn: " + e.getMessage());
            return true; // Prevent adding product in case of error
        }
    }

    public void xoaSanPhamKhoiChiTietHoaDon(String maHoaDon, String tenHangHoa) {
        String sql = "DELETE FROM ChiTietHoaDon WHERE IDHoaDon = (SELECT ID FROM HoaDon WHERE MaHoaDon = ?) "
                + "AND IDSanPham = (SELECT TOP 1 ID FROM SanPham WHERE TenSanPham = ?)";
        try (Connection connection = DBConnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            // Set parameters for the SQL query
            ps.setString(1, maHoaDon);  // Hóa đơn ID
            ps.setString(2, tenHangHoa);  // Tên sản phẩm (must match database)

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Đã xóa sản phẩm \"" + tenHangHoa + "\" khỏi hóa đơn!");
            } else {
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
}
