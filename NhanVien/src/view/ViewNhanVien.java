package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
import java.text.ParseException;
import model.NhanVien;
import repo.NhanVienRepo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.Calendar; // Đảm bảo import này ở đầu file
/**
 *
 * @author Hoang
 */
public class ViewNhanVien extends javax.swing.JPanel {

    private NhanVienRepo repo = new NhanVienRepo();
    private NhanVienRepo repository;
    private DefaultTableModel mol = new DefaultTableModel();
    private int index = -1;
    private int i;

    /**
     * Creates new form ViewNhanVien
     */
    public ViewNhanVien() {

        initComponents();
        this.fillTable(repo.getAll());
        txt_ns.setMaxSelectableDate(new Date());
    }

    void fillTable(ArrayList<NhanVien> list) {
        mol = (DefaultTableModel) tbl_Sach.getModel();
        mol.setRowCount(0);
        for (NhanVien nv : list) {
            mol.addRow(nv.toDataRow());
        }
    }

    private void reset() {
        txt_ns.setDate(null);
        txt_ma.setText("");
        txt_MatKhau.setText("");
        txt_ten.setText("");
        txt_sdt.setText("");
        txt_timKiem.setText("");
        buttonGroup1.clearSelection();
        buttonGroup2.clearSelection();
        buttonGroup3.clearSelection();
        tbl_Sach.clearSelection();
        index = -1;
    }

    private void showData(int index) {
        if (index < 0 || index >= tbl_Sach.getRowCount()) {
            return;
        }
        txt_ma.setText(tbl_Sach.getValueAt(index, 1).toString());
        txt_MatKhau.setText(tbl_Sach.getValueAt(index, 2).toString());
        txt_ten.setText(tbl_Sach.getValueAt(index, 3).toString());
        txt_sdt.setText(tbl_Sach.getValueAt(index, 6).toString());

        // Hiển thị chức vụ
        String chucVu = tbl_Sach.getValueAt(index, 7).toString();
        if (chucVu.equalsIgnoreCase("Quản lý")) {
            rdo_admin.setSelected(true);
        } else {
            rdo_nv.setSelected(true);
        }

        // Hiển thị giới tính
        String gioiTinh = tbl_Sach.getValueAt(index, 4).toString();
        if (gioiTinh.equalsIgnoreCase("Nam")) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }

        // Hiển thị trạng thái
        String trangThai = tbl_Sach.getValueAt(index, 8).toString();
        if (trangThai.equals("Đang làm")) {
            rdo_lam.setSelected(true);
        } else {
            rdo_nghi.setSelected(true);
        }

        // Hiển thị ngày sinh
        try {
            String ngaySinhStr = tbl_Sach.getValueAt(index, 5).toString();
            if (!ngaySinhStr.isEmpty()) {
                Date ngaySinh = new SimpleDateFormat("yyyy-MM-dd").parse(ngaySinhStr);
                txt_ns.setDate(ngaySinh);
            } else {
                txt_ns.setDate(null);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public NhanVien readForm() {
        String ma = txt_ma.getText().trim();
        String matKhau = txt_MatKhau.getText().trim();
        String ten = txt_ten.getText().trim();
        String sdt = txt_sdt.getText().trim();
        boolean gioiTinh = rdoNam.isSelected();
        boolean trangThai = rdo_lam.isSelected();
        int idChucVu = rdo_admin.isSelected() ? 1 : 2;

        Date selectedDate = txt_ns.getDate();
        String ngaySinh = (selectedDate != null) ? dateFormat.format(selectedDate) : "";

        // Kiểm tra các trường bắt buộc
        if (ma.isEmpty() || matKhau.isEmpty() || ten.isEmpty() || ngaySinh.isEmpty() || sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (!rdo_lam.isSelected() && !rdo_nghi.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trạng thái!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (!rdo_admin.isSelected() && !rdo_nv.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(null);
        nv.setMatKhau(matKhau);
        nv.setTenNhanVien(ten);
        nv.setNgaySinh(ngaySinh);
        nv.setSoDienThoai(sdt);
        nv.setGioiTinh(gioiTinh);
        nv.setTrangThai(trangThai);
        nv.setIdChucVu(idChucVu);
        nv.setQueQuan(""); // Gán mặc định vì không có trường nhập quê quán
        nv.setTenChucVu(idChucVu == 1 ? "Quản lý" : "Nhân viên"); // Gán dựa trên idChucVu

        return nv;
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_ma = new javax.swing.JTextField();
        txt_ten = new javax.swing.JTextField();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        txt_sdt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        rdo_lam = new javax.swing.JRadioButton();
        rdo_nghi = new javax.swing.JRadioButton();
        rdo_admin = new javax.swing.JRadioButton();
        rdo_nv = new javax.swing.JRadioButton();
        txt_MatKhau = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_ns = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        btn_clean = new javax.swing.JButton();
        btn_sua = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        btn_them = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txt_timKiem = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Sach = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1059, 510));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Quản lý nhân viên");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setText("Mã nhân viên:");

        jLabel3.setText("Tên nhân viên:");

        jLabel4.setText("Chức vụ:");

        jLabel5.setText("Giới tính:");

        jLabel7.setText("Ngày Sinh:");

        jLabel9.setText("Số điện thoại:");

        txt_ma.setEditable(false);
        txt_ma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_maActionPerformed(evt);
            }
        });

        txt_ten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tenActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        txt_sdt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sdtActionPerformed(evt);
            }
        });

        jLabel12.setText("Trạng thái:");

        buttonGroup2.add(rdo_lam);
        rdo_lam.setText("Đang làm");

        buttonGroup2.add(rdo_nghi);
        rdo_nghi.setText("Đã nghỉ");

        buttonGroup3.add(rdo_admin);
        rdo_admin.setText("Quản Lý");
        rdo_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_adminActionPerformed(evt);
            }
        });

        buttonGroup3.add(rdo_nv);
        rdo_nv.setText("Nhân Viên");

        txt_MatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MatKhauActionPerformed(evt);
            }
        });

        jLabel8.setText("Mật khẩu:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel2))
                            .addComponent(jLabel7)
                            .addComponent(jLabel5))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_ten, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                            .addComponent(txt_ma)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu)
                            .addComponent(txt_ns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel4)
                    .addComponent(jLabel12)
                    .addComponent(jLabel8))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdo_admin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdo_nv))
                            .addComponent(txt_MatKhau))
                        .addContainerGap(12, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdo_nghi, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdo_lam))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_ma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(rdo_admin)
                    .addComponent(rdo_nv))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_ten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txt_sdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addGap(23, 23, 23)
                        .addComponent(rdoNu)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_MatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_ns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel8)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addGap(2, 2, 2))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdoNam)
                                    .addComponent(jLabel5))
                                .addGap(49, 49, 49))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(rdo_lam)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdo_nghi)
                                .addGap(33, 33, 33))))))
        );

        btn_clean.setText("Làm mới");
        btn_clean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cleanActionPerformed(evt);
            }
        });

        btn_sua.setText("Sửa");
        btn_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaActionPerformed(evt);
            }
        });

        btn_xoa.setText("Xoá");
        btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaActionPerformed(evt);
            }
        });

        btn_them.setText("Thêm");
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_clean, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_them, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_sua)
                    .addComponent(btn_xoa))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_clean, btn_sua, btn_them, btn_xoa});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_sua)
                    .addComponent(btn_them))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_clean)
                    .addComponent(btn_xoa))
                .addGap(26, 26, 26))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_clean, btn_sua, btn_them, btn_xoa});

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txt_timKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_timKiemActionPerformed(evt);
            }
        });
        txt_timKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_timKiemKeyReleased(evt);
            }
        });

        tbl_Sach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã nhân viên", "Mật khẩu", "Tên nhân viên", "Giới tính", "Ngày Sinh", "Số điện thoại", "Chức vụ", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Sach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_SachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_Sach);
        if (tbl_Sach.getColumnModel().getColumnCount() > 0) {
            tbl_Sach.getColumnModel().getColumn(0).setResizable(false);
            tbl_Sach.getColumnModel().getColumn(1).setResizable(false);
            tbl_Sach.getColumnModel().getColumn(2).setResizable(false);
            tbl_Sach.getColumnModel().getColumn(3).setResizable(false);
            tbl_Sach.getColumnModel().getColumn(4).setResizable(false);
            tbl_Sach.getColumnModel().getColumn(5).setResizable(false);
            tbl_Sach.getColumnModel().getColumn(6).setResizable(false);
            tbl_Sach.getColumnModel().getColumn(7).setResizable(false);
            tbl_Sach.getColumnModel().getColumn(8).setResizable(false);
        }

        jLabel6.setText("Tìm Kiếm:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 884, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(txt_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(267, 267, 267))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_maActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_maActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_maActionPerformed

    private void txt_tenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tenActionPerformed

    private void txt_sdtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sdtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sdtActionPerformed

    private void rdo_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_adminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_adminActionPerformed

    private void btn_cleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cleanActionPerformed
        // TODO add your handling code here:
        lamMoiForm();
    }//GEN-LAST:event_btn_cleanActionPerformed

    private void btn_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaActionPerformed
        index = tbl_Sach.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa");
            return;
        }

        if (kiemTra1()) {
            try {
                NhanVien nv = reaform2();
                if (nv == null) {
                    return;
                }
                int result = repo.updateNV(nv);
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                    this.fillTable(repo.getAll());
                    this.reset();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btn_suaActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        index = tbl_Sach.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần cập nhật trạng thái!");
            return;
        }

        String idNhanVien = tbl_Sach.getValueAt(index, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đổi trạng thái nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = repo.updateTrangThaiNhanVien(idNhanVien);
            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thành công!");
                fillTable(repo.getAll());
                lamMoiForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thất bại!");
            }
        }

    }//GEN-LAST:event_btn_xoaActionPerformed

    
    
    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
    if (kiemTra1()) {
        NhanVien nv = readForm();
        if (nv == null) {
            return;
        }
        try {
          
            if (repo.isPhoneNumberExists(nv.getSoDienThoai(), -1)) {
                JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại trong hệ thống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int result = repo.addNV(nv);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                this.fillTable(repo.getAll());
                this.reset();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại. Vui lòng kiểm tra dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm nhân viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    }//GEN-LAST:event_btn_themActionPerformed

    private void txt_timKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_timKiemActionPerformed

    }//GEN-LAST:event_txt_timKiemActionPerformed

    private void txt_timKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_timKiemKeyReleased
         String keyword = txt_timKiem.getText(); // Lấy giá trị gốc, chưa trim
    
    // Kiểm tra xem chuỗi có khoảng trắng ở đầu hoặc cuối không
    if (!keyword.equals(keyword.trim())) {
        JOptionPane.showMessageDialog(this, "Không chứa khoảng trắng đầu và cuối!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        txt_timKiem.setText(keyword.trim()); // Tự động loại bỏ khoảng trắng
        keyword = txt_timKiem.getText(); // Cập nhật lại keyword
    }
    
    // Xử lý tìm kiếm
    if (keyword.isEmpty()) {
        this.fillTable(repo.getAll()); // Hiển thị toàn bộ danh sách nếu rỗng
    } else {
        this.fillTable(repo.search(keyword)); // Tìm kiếm với từ khóa
    }
    }//GEN-LAST:event_txt_timKiemKeyReleased

    private void tbl_SachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_SachMouseClicked
        index = tbl_Sach.getSelectedRow();
        this.showData(index);
    }//GEN-LAST:event_tbl_SachMouseClicked

    private void txt_MatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MatKhauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_MatKhauActionPerformed
    private void lamMoiForm() {
        reset();
    }

    public NhanVien reaform2() {
        if (index < 0) {
            return null;
        }
        String ma = txt_ma.getText().trim();
        String matKhau = txt_MatKhau.getText().trim();
        String ten = txt_ten.getText().trim();
        String sdt = txt_sdt.getText().trim();
        boolean gioiTinh = rdoNam.isSelected();
        boolean trangThai = rdo_lam.isSelected();
        int idChucVu = rdo_admin.isSelected() ? 1 : 2;

        Date selectedDate = txt_ns.getDate();
        String ngaySinh = (selectedDate != null) ? dateFormat.format(selectedDate) : "";

        NhanVien nv = new NhanVien();
        nv.setId(Integer.parseInt(tbl_Sach.getValueAt(index, 0).toString()));
        nv.setMaNhanVien(ma);
        nv.setMatKhau(matKhau);
        nv.setTenNhanVien(ten);
        nv.setNgaySinh(ngaySinh);
        nv.setSoDienThoai(sdt);
        nv.setGioiTinh(gioiTinh);
        nv.setTrangThai(trangThai);
        nv.setIdChucVu(idChucVu);
        nv.setQueQuan(""); // Gán mặc định
        nv.setTenChucVu(idChucVu == 1 ? "Quản lý" : "Nhân viên"); // Gán dựa trên idChucVu

        return nv;
    }

    public boolean kiemTra1() {
        StringBuilder stb = new StringBuilder();

        // Lấy dữ liệu từ form
        String ma = txt_ma.getText().trim();
        String matKhau = txt_MatKhau.getText().trim();
        String ten = txt_ten.getText().trim();
        String sdt = txt_sdt.getText().trim();

        // Kiểm tra các trường bắt buộc
        if (ma.isEmpty()) {
            stb.append("Mã nhân viên không được để trống\n");
        }
        if (matKhau.isEmpty()) {
            stb.append("Mật khẩu không được để trống\n");
        }
        if (ten.isEmpty()) {
            stb.append("Tên nhân viên không được để trống\n");
        }
        if (sdt.isEmpty()) {
            stb.append("Số điện thoại không được để trống\n");
        }
        if (txt_ns.getDate() == null) {
            stb.append("Ngày sinh không được để trống\n");
        }
        Date ngaySinh = txt_ns.getDate();
        Calendar ngaySinhCal = Calendar.getInstance();
        ngaySinhCal.setTime(ngaySinh);

        Calendar ngayHienTai = Calendar.getInstance();
        int tuoi = ngayHienTai.get(Calendar.YEAR) - ngaySinhCal.get(Calendar.YEAR);

        if (ngayHienTai.get(Calendar.DAY_OF_YEAR) < ngaySinhCal.get(Calendar.DAY_OF_YEAR)) {
            tuoi--;
        }

        if (tuoi < 18) {
            stb.append("Nhân viên phải từ 18 tuổi trở lên\n");
        }
        
        
        
        
        
        if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
            stb.append("Vui lòng chọn giới tính\n");
        }
        if (!rdo_lam.isSelected() && !rdo_nghi.isSelected()) {
            stb.append("Vui lòng chọn trạng thái\n");
        }
        if (!rdo_admin.isSelected() && !rdo_nv.isSelected()) {
            stb.append("Vui lòng chọn chức vụ\n");
        }

        // Kiểm tra định dạng và nội dung
        if (!ma.matches("^[a-zA-Z0-9]+$")) {
            stb.append("Mã nhân viên chỉ được chứa chữ cái và số\n");
        }
        if (ma.contains(" ")) {
            stb.append("Mã nhân viên không được chứa khoảng trắng\n");
        }
        if (matKhau.contains(" ")) {
            stb.append("Mật khẩu không được chứa khoảng trắng\n");
        }
        if (!ten.matches("^[a-zA-Z\\sÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ]+$")) {
            stb.append("Tên nhân viên không được chứa ký tự đặc biệt hoặc số\n");
        }
        if (!sdt.matches("\\d{10}")) {
            stb.append("Số điện thoại phải gồm 10 chữ số\n");
        }
        if (sdt.contains(" ")) {
            stb.append("Số điện thoại không được chứa khoảng trắng\n");
        }

        // Kiểm tra số điện thoại trùng
        try {
            int excludeId = (index >= 0) ? Integer.parseInt(tbl_Sach.getValueAt(index, 0).toString()) : -1;
            if (repo.isPhoneNumberExists(sdt, excludeId)) {
                stb.append("Số điện thoại đã tồn tại trong hệ thống\n");
            }
        } catch (SQLException e) {
            stb.append("Lỗi khi kiểm tra số điện thoại: " + e.getMessage() + "\n");
        }

        // Hiển thị thông báo lỗi nếu có
        if (stb.length() > 0) {
            JOptionPane.showMessageDialog(this, stb.toString(), "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý nhân viên");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 600); // Kích thước phù hợp với panel
            frame.add(new ViewNhanVien());
            frame.setLocationRelativeTo(null); // Đặt cửa sổ ở giữa màn hình
            frame.setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clean;
    private javax.swing.JButton btn_sua;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_xoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdo_admin;
    private javax.swing.JRadioButton rdo_lam;
    private javax.swing.JRadioButton rdo_nghi;
    private javax.swing.JRadioButton rdo_nv;
    private javax.swing.JTable tbl_Sach;
    private javax.swing.JTextField txt_MatKhau;
    private javax.swing.JTextField txt_ma;
    private com.toedter.calendar.JDateChooser txt_ns;
    private javax.swing.JTextField txt_sdt;
    private javax.swing.JTextField txt_ten;
    private javax.swing.JTextField txt_timKiem;
    // End of variables declaration//GEN-END:variables
}
