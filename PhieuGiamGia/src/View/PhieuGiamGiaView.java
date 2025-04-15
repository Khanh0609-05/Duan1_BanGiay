/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model.PhieuGiamGia;
import Repo.PhieuGiamGiaRepo;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thanhnguyen
 */
public class PhieuGiamGiaView extends javax.swing.JFrame {
    PhieuGiamGiaRepo repo = new PhieuGiamGiaRepo();
    public PhieuGiamGiaView() {
        initComponents();
        fillTableData(repo.getAll());
        txtMa.setEnabled(false);
    }

    // Fill dữ liệu lên bảng
    public void fillTableData(ArrayList<PhieuGiamGia> danhSach) {
        DefaultTableModel model = (DefaultTableModel) tblPhieuGiamGia.getModel();
        model.setRowCount(0);
        Date now = new Date();
        int stt = 1;
        for (PhieuGiamGia pgg : danhSach) {
            String trangThai;
            if (pgg.getNgayBatDau().after(now)) {
                trangThai = "Sắp diễn ra";
            } else if (pgg.getNgayKetThuc().before(now)) {
                trangThai = "Đã kết thúc";
            } else {
                trangThai = "Đang diễn ra";
            }
            model.addRow(new Object[]{
                pgg.getId(),
                stt++,
                pgg.getMaPhieuGiamGia(),
                pgg.getTenPhieuGiamGia(),
                pgg.isKieuGiam() ? "Theo phần trăm" : "Theo số tiền",
                pgg.getMucGiam(),
                pgg.getMucGiamToiDa(),
                pgg.getHoaDonToiThieu(),
                pgg.getSoLuong(),
                pgg.getDaDung(),
                pgg.getNgayBatDau(),
                pgg.getNgayKetThuc(),
                trangThai
            });
        }
        // Ẩn id đi
        tblPhieuGiamGia.getColumnModel().getColumn(0).setMinWidth(0);
        tblPhieuGiamGia.getColumnModel().getColumn(0).setMaxWidth(0);
        tblPhieuGiamGia.getColumnModel().getColumn(0).setWidth(0);

    }

    public PhieuGiamGia readFormData() {
        String maPhieuGiamGia = txtMa.getText();
        String tenPhieuGiamGia = txtTen.getText();
        Date ngayBatDau = txtNgayBatDau.getDate();
        Date ngayKetThuc = txtNgayKetThuc.getDate();
        boolean kieuGiam = rdoTheoPhanTram.isSelected();
        double mucGiam = Double.parseDouble(txtMucGiam.getText());
        double mucGiamToiDa = Double.parseDouble(txtMucGiamToiDa.getText());
        double hoaDonToiThieu = Double.parseDouble(txtGiaTriHD.getText());
        int soLuong = Integer.parseInt(txtSoLuong.getText());
        int daDung = Integer.parseInt(txtDaDung.getText());
        boolean trangThai = true;

        return new PhieuGiamGia(0, null, tenPhieuGiamGia, ngayBatDau, ngayKetThuc,
                kieuGiam, mucGiam, mucGiamToiDa, hoaDonToiThieu, soLuong, daDung, trangThai);
    }

    private boolean validateForm() {
        // 1. Kiểm tra Tên phiếu giảm giá
        String tenPhieuGiamGia = txtTen.getText().trim();
        if (tenPhieuGiamGia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên phiếu giảm giá không được để trống!");
            return false;
        }

        // Kiểm tra khoảng trắng liên tiếp
        if (tenPhieuGiamGia.contains("  ")) {
            JOptionPane.showMessageDialog(this, "Tên phiếu giảm giá không được chứa khoảng trắng liên tiếp!");
            return false;
        }

        // Kiểm tra ký tự đặc biệt
        String kyTuDacBiet = "[!@#$^&*()+=\\{\\}\\[\\]|\\\\:;\"'<>,?\n\t]";
        if (tenPhieuGiamGia.matches(".*" + kyTuDacBiet + ".*")) {
            JOptionPane.showMessageDialog(this, "Tên phiếu giảm giá không được chứa ký tự đặc biệt hoặc dấu xuống dòng/tab!");
            return false;
        }

        // Kiểm tra dấu gạch ngang hoặc gạch dưới liên tục
        if (tenPhieuGiamGia.contains("--") || tenPhieuGiamGia.contains("___")) {
            JOptionPane.showMessageDialog(this, "Tên phiếu giảm giá không được chứa dấu gạch ngang hoặc gạch dưới liên tục!");
            return false;
        }

        // 2. Kiểm tra Ngày bắt đầu < Ngày kết thúc
        Date ngayBatDau = txtNgayBatDau.getDate();
        Date ngayKetThuc = txtNgayKetThuc.getDate();
        if (ngayBatDau== null || ngayKetThuc == null) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu và ngày kết thúc không được để trống!");
            return false;
        }
//        if (ngayBatDau.before(ngayKetThuc)) {
//            return true;
//        }else{
//            JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải nhỏ hơn ngày kết thúc!");
//            return false;
//        }
        if (!ngayBatDau.before(ngayKetThuc)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải nhỏ hơn ngày kết thúc!");
            return false;
        }

        // 3. Kiểm tra các trường số: Mức giảm, Giá trị đơn hàng tối thiểu, Số lượng, Đã dùng
        double mucGiam = 0, giaTriHDToiThieu = 0, soLuong = 0, daDung = 0;

        // Kiểm tra Mức giảm
        String mucGiamText = txtMucGiam.getText().trim();
        if (mucGiamText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mức giảm không được để trống!");
            return false;
        }
        try {
            mucGiam = Double.parseDouble(mucGiamText);
            if (mucGiam < 0) {
                JOptionPane.showMessageDialog(this, "Mức giảm phải lớn hơn hoặc bằng 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mức giảm chỉ được chứa số, không chứa chữ hoặc ký tự đặc biệt!");
            return false;
        }

        // Kiểm tra Giá trị đơn hàng tối thiểu
        String giaTriHDText = txtGiaTriHD.getText().trim();
        if (giaTriHDText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá trị đơn hàng tối thiểu không được để trống!");
            return false;
        }
        try {
            giaTriHDToiThieu = Double.parseDouble(giaTriHDText);
            if (giaTriHDToiThieu < 0) {
                JOptionPane.showMessageDialog(this, "Giá trị đơn hàng tối thiểu phải lớn hơn hoặc bằng 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá trị đơn hàng tối thiểu chỉ được chứa số, không chứa chữ hoặc ký tự đặc biệt!");
            return false;
        }

        // Kiểm tra Số lượng
        String soLuongText = txtSoLuong.getText().trim();
        if (soLuongText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số lượng không được để trống!");
            return false;
        }
        try {
            soLuong = Double.parseDouble(soLuongText);
            if (soLuong < 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn hoặc bằng 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng chỉ được chứa số, không chứa chữ hoặc ký tự đặc biệt!");
            return false;
        }

        // Kiểm tra Đã dùng
        String daDungText = txtDaDung.getText().trim();
        if (daDungText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Đã dùng không được để trống!");
            return false;
        }
        try {
            daDung = Double.parseDouble(daDungText);
            if (daDung < 0) {
                JOptionPane.showMessageDialog(this, "Đã dùng phải lớn hơn hoặc bằng 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đã dùng chỉ được chứa số, không chứa chữ hoặc ký tự đặc biệt!");
            return false;
        }

        // Kiểm tra Mức giảm tối đa - Theo phần trăm
        double mucGiamToiDa = 0;
        String mucGiamToiDaText = txtMucGiamToiDa.getText().trim();
        if (rdoTheoPhanTram.isSelected()) {
            if (mucGiamToiDaText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mức giảm tối đa không được để trống khi chọn kiểu giảm theo phần trăm!");
                return false;
            }
            try {
                mucGiamToiDa = Double.parseDouble(mucGiamToiDaText);
                if (mucGiamToiDa <= 0) {
                    JOptionPane.showMessageDialog(this, "Mức giảm tối đa phải lớn hơn 1");
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Mức giảm tối đa chỉ được chứa số, không chứa chữ hoặc ký tự đặc biệt!");
                return false;
            }
        } else { // Những TH còn lại (khi chọn theo số tiền - setMucGiamToiDa = 0)
            txtMucGiamToiDa.setText("0");
            mucGiamToiDa = 0;
        }

        // Kiểm tra điều kiện khi chọn giảm theo số tiền
        if (rdoTheoSoTien.isSelected()) {
            // ĐK1: Mức giảm không được lớn hơn 69% giá trị đơn hàng tối thiểu
            double mucGiamMax = giaTriHDToiThieu * 0.69;
            if (mucGiam > mucGiamMax) {
                JOptionPane.showMessageDialog(this, "Mức giảm không được lớn hơn 69% giá trị đơn hàng tối thiểu!");
                return false;
            }

            // DDK2: Phải nhỏ hơn 100.000.000
            if (mucGiam >= 100000000) {
                JOptionPane.showMessageDialog(this, "Mức giảm theo số tiền phải nhỏ hơn 100.000.000!");
                return false;
            }

            // ĐK3: Giá trị giảm không được vượt quá giá trị tối thiểu của đơn hàng
            if (mucGiam > giaTriHDToiThieu) {
                JOptionPane.showMessageDialog(this, "Giá trị giảm không được vượt quá giá trị tối thiểu của đơn hàng!");
                return false;
            }
            // Kiểm tra điều kiện khi chọn giảm theo phần trăm
        } else if (rdoTheoPhanTram.isSelected()) {
            // ĐK1: Giá trị giảm không được vượt quá giá trị tối thiểu của đơn hàng
            if (mucGiamToiDa > giaTriHDToiThieu) {
                JOptionPane.showMessageDialog(this, "Giá trị mức giảm tối đa không được vượt quá giá trị tối thiểu của đơn hàng!");
                return false;
            }

            // ĐK2: Mức giảm theo phần trăm phải từ 1 đến 100
            if (mucGiam < 1 || mucGiam > 100) {
                JOptionPane.showMessageDialog(this, "Mức giảm theo phần trăm phải từ 1 đến 100!");
                return false;
            }

            // ĐK3: Mức giảm tối đa ≤ Giá trị đơn hàng tối thiểu × (Tỷ lệ giảm giá / 100)
            double congThuc = giaTriHDToiThieu * (mucGiam / 100);
            if (mucGiamToiDa > congThuc) {
                JOptionPane.showMessageDialog(this, "Mức giảm tối đa không được vượt quá " + congThuc);
                return false;
            }
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNgayBatDau = new com.toedter.calendar.JDateChooser();
        txtNgayKetThuc = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        rdoSapDienRa = new javax.swing.JRadioButton();
        rdoDangDienRa = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        rdoDaKetThuc = new javax.swing.JRadioButton();
        rdoTheoPhanTram = new javax.swing.JRadioButton();
        rdoTheoSoTien = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        txtMucGiam = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtMucGiamToiDa = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtGiaTriHD = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPhieuGiamGia = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txtDaDung = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Mã phiếu giảm giá");

        txtMa.setEditable(false);
        txtMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaActionPerformed(evt);
            }
        });

        jLabel2.setText("Tên phiếu giảm giá");

        jLabel3.setText("Ngày bắt đầu");

        jLabel4.setText("Ngày kết thúc");

        txtNgayBatDau.setDateFormatString("yyyy-MM-dd");

        txtNgayKetThuc.setDateFormatString("yyyy-MM-dd");

        jLabel5.setText("Trạng thái");

        buttonGroup1.add(rdoSapDienRa);
        rdoSapDienRa.setText("Sắp diễn ra");

        buttonGroup1.add(rdoDangDienRa);
        rdoDangDienRa.setText("Đang diễn ra");

        jLabel6.setText("Kiểu giảm");

        buttonGroup1.add(rdoDaKetThuc);
        rdoDaKetThuc.setText("Đã kết thúc");

        buttonGroup2.add(rdoTheoPhanTram);
        rdoTheoPhanTram.setText("Theo phần trăm");

        buttonGroup2.add(rdoTheoSoTien);
        rdoTheoSoTien.setText("Theo số tiền");
        rdoTheoSoTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTheoSoTienActionPerformed(evt);
            }
        });

        jLabel7.setText("Mức giảm");

        jLabel8.setText("Mức giảm tối đa");

        jLabel9.setText("Giá trị đơn hàng tối thiểu");

        jLabel10.setText("Số lượng");

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        tblPhieuGiamGia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "STT", "Mã", "Tên", "Loại giảm", "Mức giảm", "Mức giảm TĐ", "Giá trị HĐTT", "Số lượng", "Đã dùng", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"
            }
        ));
        tblPhieuGiamGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuGiamGiaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblPhieuGiamGia);

        jLabel11.setText("Đã dùng");

        jLabel12.setText("Tìm kiếm");

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(71, 71, 71)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdoSapDienRa)
                                    .addComponent(rdoDangDienRa)
                                    .addComponent(rdoDaKetThuc))
                                .addGap(136, 136, 136))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNgayBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                .addComponent(txtTen)
                                .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdoTheoPhanTram)
                                .addGap(18, 18, 18)
                                .addComponent(rdoTheoSoTien)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7))
                                .addGap(55, 55, 55)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMucGiamToiDa)
                                    .addComponent(txtMucGiam)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtGiaTriHD)
                                    .addComponent(txtSoLuong)
                                    .addComponent(txtDaDung)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(btnLamMoi)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(98, 98, 98))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 910, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(rdoTheoPhanTram)
                            .addComponent(rdoTheoSoTien))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtMucGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(txtMucGiamToiDa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtGiaTriHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoDangDienRa)))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(rdoSapDienRa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoDaKetThuc)
                        .addGap(36, 36, 36))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDaDung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(30, 30, 30)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdoTheoSoTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTheoSoTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoTheoSoTienActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validateForm()) {
            PhieuGiamGia pggMoi = readFormData();
            int result = repo.themPGG(pggMoi);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                fillTableData(repo.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int selectedRow = tblPhieuGiamGia.getSelectedRow();
        if (selectedRow >= 0) {
            if (validateForm()) {
                int id = Integer.parseInt(tblPhieuGiamGia.getValueAt(selectedRow, 0).toString());
                PhieuGiamGia pggSua = readFormData();
                pggSua.setId(id);
                int result = repo.suaPGG(pggSua);
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    fillTableData(repo.getAll());
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn 1 dòng để sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int index = tblPhieuGiamGia.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn 1 dòng để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy ra id và trạng thái
        String id = tblPhieuGiamGia.getValueAt(index, 0).toString();
        String trangThai = tblPhieuGiamGia.getValueAt(index, 12).toString();

        // Nếu trạng thái đã kết thúc thì thông báo "Phiếu giảm giá đã kết thúc!"
        if (trangThai.equals("Đã kết thúc")) {
            JOptionPane.showMessageDialog(this, "Phiếu giảm giá đã kết thúc!");
            return;
        }

        PhieuGiamGia pgg = repo.getById(id);
        if (pgg != null) {
            int result = 0;

            // Nếu trạng thái Sắp diễn ra thì xoá hẳn
            if (trangThai.equals("Sắp diễn ra")) {
                // Hiển thị hộp thoại xác nhận
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn xóa phiếu giảm giá này không?",
                        "Xác nhận xóa",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    result = repo.xoaPGG(id);
                } else {
                    return;
                }
            } else if (trangThai.equals("Đang diễn ra")) { // Nếu trạng thái Đang diễn ra thì chuyển trạng thái thành Đã kết thúc và đổi ngày
                // Chuyển trạng thái thành đã kết thúc
                pgg.setTrangThai(false);
                // ngayBatDau < ngayKetThuc
                Date ngayBatDau = pgg.getNgayBatDau();
                Date ngayHienTai = new Date();
                if (ngayHienTai.before(ngayBatDau)) {
                    pgg.setNgayKetThuc(ngayBatDau);
                } else {
                    pgg.setNgayKetThuc(ngayHienTai);
                }
                // Cập nhật trạng thái và ngày
                result = repo.suaPGG(pgg);
            }

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                fillTableData(repo.getAll());
            } else if (trangThai.equals("Đang diễn ra")) {
                JOptionPane.showMessageDialog(this, "Xóa thất bại");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu giảm giá");
        }

    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        txtMa.setText("");
        txtTen.setText("");
        txtNgayBatDau.setDate(null);
        txtNgayKetThuc.setDate(null);
        buttonGroup2.clearSelection();
        txtMucGiam.setText("");
        txtMucGiamToiDa.setText("");
        txtGiaTriHD.setText("");
        txtSoLuong.setText("");
        txtDaDung.setText("");
        buttonGroup1.clearSelection();
        fillTableData(repo.getAll());
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void tblPhieuGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuGiamGiaMouseClicked
        int selectedIndex = tblPhieuGiamGia.getSelectedRow();
        txtMa.setText(tblPhieuGiamGia.getValueAt(selectedIndex, 2).toString()); // Cột 2: Mã
        txtTen.setText(tblPhieuGiamGia.getValueAt(selectedIndex, 3).toString()); // Cột 3: Tên
        String kieuGiam = tblPhieuGiamGia.getValueAt(selectedIndex, 4).toString(); // Cột 4: Loại giảm
        if (kieuGiam.equals("Theo phần trăm")) {
            rdoTheoPhanTram.setSelected(true);
        } else {
            rdoTheoSoTien.setSelected(true);
        }
        txtMucGiam.setText(tblPhieuGiamGia.getValueAt(selectedIndex, 5).toString()); // Cột 5: Mức giảm
        txtMucGiamToiDa.setText(tblPhieuGiamGia.getValueAt(selectedIndex, 6).toString()); // Cột 6: Mức giảm tối đa
        txtGiaTriHD.setText(tblPhieuGiamGia.getValueAt(selectedIndex, 7).toString()); // Cột 7: Giá trị HĐ
        txtSoLuong.setText(tblPhieuGiamGia.getValueAt(selectedIndex, 8).toString()); // Cột 8: Số lượng
        txtDaDung.setText(tblPhieuGiamGia.getValueAt(selectedIndex, 9).toString()); // Cột 9: Đã dùng

        try {
            Date ngayBatDau = new SimpleDateFormat("yyyy-MM-dd").parse(tblPhieuGiamGia.getValueAt(selectedIndex, 10).toString()); // Cột 10: Ngày bắt đầu
            Date ngayKetThuc = new SimpleDateFormat("yyyy-MM-dd").parse(tblPhieuGiamGia.getValueAt(selectedIndex, 11).toString()); // Cột 11: Ngày kết thúc
            txtNgayBatDau.setDate(ngayBatDau);
            txtNgayKetThuc.setDate(ngayKetThuc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String trangThai = tblPhieuGiamGia.getValueAt(selectedIndex, 12).toString(); // Cột 12: Trạng thái
        if (trangThai.equals("Sắp diễn ra")) {
            rdoSapDienRa.setSelected(true);
        } else if (trangThai.equals("Đang diễn ra")) {
            rdoDangDienRa.setSelected(true);
        } else if (trangThai.equals("Đã kết thúc")) {
            rdoDaKetThuc.setSelected(true);
        }

    }//GEN-LAST:event_tblPhieuGiamGiaMouseClicked

    private void txtMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed

    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        this.fillTableData(repo.search(txtTimKiem.getText()));
    }//GEN-LAST:event_txtTimKiemKeyReleased

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
            java.util.logging.Logger.getLogger(PhieuGiamGiaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PhieuGiamGiaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PhieuGiamGiaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PhieuGiamGiaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PhieuGiamGiaView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JRadioButton rdoDaKetThuc;
    private javax.swing.JRadioButton rdoDangDienRa;
    private javax.swing.JRadioButton rdoSapDienRa;
    private javax.swing.JRadioButton rdoTheoPhanTram;
    private javax.swing.JRadioButton rdoTheoSoTien;
    private javax.swing.JTable tblPhieuGiamGia;
    private javax.swing.JTextField txtDaDung;
    private javax.swing.JTextField txtGiaTriHD;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtMucGiam;
    private javax.swing.JTextField txtMucGiamToiDa;
    private com.toedter.calendar.JDateChooser txtNgayBatDau;
    private com.toedter.calendar.JDateChooser txtNgayKetThuc;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
