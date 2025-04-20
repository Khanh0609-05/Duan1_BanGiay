package SanPham.View;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import SanPham.Repositori.RepositoriSanPhamChiTiet;
import SanPham.Model.SanPhamChiTietModel;
import SanPham.Model.ModelKichThuoc;
import SanPham.Repositori.RepositoriKichThuoc;
import SanPham.Model.ModelMauSac;
import SanPham.Repositori.RepositoriMauSac;
import SanPham.Model.ModelThuongHieu;
import SanPham.Repositori.RepositoriThuongHieu;
import SanPham.Until.DBconnect;
import java.awt.BorderLayout;
import java.awt.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author lenovo
 */
public class SanPhamView extends javax.swing.JFrame {

    private RepositoriThuongHieu rp = new RepositoriThuongHieu();
    private RepositoriMauSac rpMauSac = new RepositoriMauSac();
    private RepositoriKichThuoc rpKichThuoc = new RepositoriKichThuoc();
    private RepositoriSanPhamChiTiet rpCTSP = new RepositoriSanPhamChiTiet();
    private DefaultTableModel mol = new DefaultTableModel();

    private int i = 0;

    /**
     * Creates new form SanPhamView
     */
    public SanPhamView() {
        initComponents();
        this.setLocationRelativeTo(null);

        this.fillTableTH(rp.getallTH()); // Bảng thương hiệu
        this.fillTableMauSac(rpMauSac.getAllMS()); // Bảng màu sắc
        this.fillTableKichThuoc(rpKichThuoc.getAllKT()); // Bảng kích thước
        this.fillTableCTSP(rpCTSP.getAllSPCT()); // Bảng chi tiết sản phẩm
        this.loadComboBoxData();
        //them tim kiem
        searchListener();
        tbl_spct.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            private final DecimalFormat df = new DecimalFormat("#,### VDN");

            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                if (value != null) {
                    try {
                        BigDecimal donGia = new BigDecimal(value.toString());
                        value = df.format(donGia);
                    } catch (NumberFormatException e) {
                        // Nếu không parse được, giữ nguyên giá trị
                    }
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });
    }

    void fillTableTH(ArrayList<ModelThuongHieu> list) {//b1 gọi model cho bảng

        mol = (DefaultTableModel) tbl_thuonghieu.getModel();
        mol.setRowCount(0);
        for (ModelThuongHieu x : list) {
            mol.addRow(x.toDataRow());

        }
    }

    void fillTableMauSac(ArrayList<ModelMauSac> list) {
        DefaultTableModel model = (DefaultTableModel) tbl_mausac.getModel();
        model.setRowCount(0);
        for (ModelMauSac ms : list) {
            model.addRow(ms.toDataRowMS());
        }
    }

    void fillTableKichThuoc(ArrayList<ModelKichThuoc> list) {
        DefaultTableModel model = (DefaultTableModel) tbl_kichthuoc.getModel();
        model.setRowCount(0);
        for (ModelKichThuoc kt : list) {
            model.addRow(kt.toDataRowKT());
        }
    }

    void fillTableCTSP(ArrayList<SanPhamChiTietModel> list) {
        DefaultTableModel model = (DefaultTableModel) tbl_spct.getModel();
        model.setRowCount(0);
        for (SanPhamChiTietModel ctsp : list) {
            model.addRow(ctsp.toDataRowSPCT());
        }

    }

    private void loadComboBoxData() {
        ArrayList<ModelThuongHieu> listTH = rp.getallTH();
        cbo_thuongHieu.removeAllItems();
        if (listTH != null && !listTH.isEmpty()) {
            for (ModelThuongHieu th : listTH) {
                cbo_thuongHieu.addItem(th.getTenTH());
            }
        } else {
            cbo_thuongHieu.addItem("Không có thương hiệu");
        }

        ArrayList<ModelMauSac> listMS = rpMauSac.getAllMS();
        cbo_mauSac.removeAllItems();
        if (listMS != null && !listMS.isEmpty()) {
            for (ModelMauSac ms : listMS) {
                cbo_mauSac.addItem(ms.getTen());
            }
        } else {
            cbo_mauSac.addItem("Không có màu sắc");
        }

        ArrayList<ModelKichThuoc> listKT = rpKichThuoc.getAllKT();
        cbo_kichThuoc.removeAllItems();
        if (listKT != null && !listKT.isEmpty()) {
            for (ModelKichThuoc kt : listKT) {
                cbo_kichThuoc.addItem(kt.getTen());
            }
        } else {
            cbo_kichThuoc.addItem("Không có kích thước");
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_maspct = new javax.swing.JTextField();
        txt_tenspct = new javax.swing.JTextField();
        txt_soluongspct = new javax.swing.JTextField();
        rdo_conban = new javax.swing.JRadioButton();
        rdo_ngungban = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cbo_thuongHieu = new javax.swing.JComboBox<>();
        cbo_mauSac = new javax.swing.JComboBox<>();
        cbo_kichThuoc = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        txt_dongiaspct = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_spct = new javax.swing.JTable();
        btn_themspct = new javax.swing.JButton();
        btn_suacpct = new javax.swing.JButton();
        btn_ngungbanSPct = new javax.swing.JButton();
        btn_lammoispct = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_maTH = new javax.swing.JTextField();
        btn_themTH = new javax.swing.JButton();
        btn_suaTH = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_thuonghieu = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txt_tenTH = new javax.swing.JTextField();
        btn_lammoiTH = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_maMS = new javax.swing.JTextField();
        txt_tenMS = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_mausac = new javax.swing.JTable();
        btn_themMS = new javax.swing.JButton();
        btn_suaMS = new javax.swing.JButton();
        btn_lammoiMS = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btn_themKT = new javax.swing.JButton();
        btn_suaKT = new javax.swing.JButton();
        txt_tenKT = new javax.swing.JTextField();
        txt_maKT = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_kichthuoc = new javax.swing.JTable();
        btn_lammoiKT = new javax.swing.JButton();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBackground(new java.awt.Color(102, 255, 255));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel10.setText("Quản Lý Sản Phẩm");

        jLabel11.setText("Mã:");

        jLabel12.setText("Tên Sản Phẩm:");

        jLabel13.setText("Số Lượng:");

        jLabel14.setText("Trạng Thái:");

        txt_maspct.setEditable(false);

        buttonGroup1.add(rdo_conban);
        rdo_conban.setSelected(true);
        rdo_conban.setText("Còn Bán");
        rdo_conban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_conbanActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdo_ngungban);
        rdo_ngungban.setText("Ngưng Bán");

        jLabel16.setText("Thương Hiệu: ");

        jLabel17.setText("Màu Sắc: ");

        jLabel18.setText("Kích Thước: ");

        cbo_thuongHieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        jLabel19.setText("Đơn Giá: ");

        tbl_spct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ", "Tên Sản Phẩm", "Số Lượng", "Thương Hiệu", "Màu Sắc", "Kích Thước", "Đơn Giá ", "Trạng Thái"
            }
        ));
        tbl_spct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_spctMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_spct);

        btn_themspct.setText("Thêm");
        btn_themspct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themspctActionPerformed(evt);
            }
        });

        btn_suacpct.setText("Sửa");
        btn_suacpct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suacpctActionPerformed(evt);
            }
        });

        btn_ngungbanSPct.setText("Ngưng bán");
        btn_ngungbanSPct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ngungbanSPctActionPerformed(evt);
            }
        });

        btn_lammoispct.setText("Làm Mới");
        btn_lammoispct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoispctActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel20.setText("Tìm Kiếm: ");

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(txt_maspct))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(42, 42, 42)
                                .addComponent(txt_soluongspct))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(txt_tenspct, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(rdo_conban, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdo_ngungban, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(77, 77, 77)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbo_thuongHieu, 0, 131, Short.MAX_VALUE)
                            .addComponent(cbo_mauSac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbo_kichThuoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_dongiaspct)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(btn_themspct, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(btn_suacpct, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btn_ngungbanSPct, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(btn_lammoispct, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(77, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txt_maspct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(cbo_thuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txt_tenspct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(cbo_mauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addComponent(jLabel13))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_soluongspct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18)
                        .addComponent(cbo_kichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(rdo_conban)
                    .addComponent(rdo_ngungban)
                    .addComponent(jLabel19)
                    .addComponent(txt_dongiaspct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_themspct)
                    .addComponent(btn_suacpct)
                    .addComponent(btn_ngungbanSPct)
                    .addComponent(btn_lammoispct))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sản Phẩm", jPanel1);

        jLabel1.setText("Mã");

        txt_maTH.setEditable(false);
        txt_maTH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_maTHActionPerformed(evt);
            }
        });

        btn_themTH.setText("Thêm");
        btn_themTH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themTHActionPerformed(evt);
            }
        });

        btn_suaTH.setText("Sửa");
        btn_suaTH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaTHActionPerformed(evt);
            }
        });

        tbl_thuonghieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã", "Thương Hiệu"
            }
        ));
        tbl_thuonghieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_thuonghieuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_thuonghieu);

        jLabel2.setText("Tên Thương hiệu:");

        txt_tenTH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tenTHActionPerformed(evt);
            }
        });

        btn_lammoiTH.setText("Làm Mới");
        btn_lammoiTH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoiTHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_tenTH, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                .addComponent(txt_maTH))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btn_themTH, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btn_suaTH, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btn_lammoiTH)))))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_maTH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tenTH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_themTH)
                    .addComponent(btn_suaTH)
                    .addComponent(btn_lammoiTH))
                .addGap(52, 52, 52)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thương hiệu", jPanel2);

        jLabel4.setText("Mã :");

        jLabel5.setText("Màu Sắc:");

        txt_maMS.setEditable(false);

        tbl_mausac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã", "Màu Sắc"
            }
        ));
        tbl_mausac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_mausacMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_mausac);

        btn_themMS.setText("Thêm ");
        btn_themMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themMSActionPerformed(evt);
            }
        });

        btn_suaMS.setText("Sửa");
        btn_suaMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaMSActionPerformed(evt);
            }
        });

        btn_lammoiMS.setText("Làm mới");
        btn_lammoiMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoiMSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_maMS)
                                    .addComponent(txt_tenMS)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(btn_themMS, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                                        .addComponent(btn_suaMS, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(btn_lammoiMS)
                                        .addGap(32, 32, 32)))))
                        .addGap(177, 177, 177)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_maMS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_tenMS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_themMS)
                    .addComponent(btn_suaMS)
                    .addComponent(btn_lammoiMS))
                .addGap(44, 44, 44)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Màu Sắc ", jPanel3);

        jLabel7.setText("Mã:");

        jLabel8.setText("Kích Thước:");

        btn_themKT.setText("Thêm");
        btn_themKT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themKTActionPerformed(evt);
            }
        });

        btn_suaKT.setText("Sửa");
        btn_suaKT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_suaKTActionPerformed(evt);
            }
        });

        txt_maKT.setEditable(false);

        tbl_kichthuoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã", "Kích Thước"
            }
        ));
        tbl_kichthuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_kichthuocMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_kichthuoc);

        btn_lammoiKT.setText("Làm Mới");
        btn_lammoiKT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lammoiKTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(btn_themKT, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_suaKT, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_lammoiKT))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_maKT, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                            .addComponent(txt_tenKT))))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_maKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_tenKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_themKT)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_suaKT)
                        .addComponent(btn_lammoiKT)))
                .addGap(39, 39, 39)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Kích Thước", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdo_conbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_conbanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_conbanActionPerformed

    private void btn_themMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themMSActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thêm màu sắc này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        ModelMauSac ms = readFormMS();

        if (ms != null) {
            // Buộc sinh mã mới bất kể giá trị hiện tại trong txt_maMS
            String newMaMS = generateNewMaMS();
            ms.setMa(newMaMS); // Cập nhật mã mới vào model
            ms.setTrangThai(true);
            txt_maMS.setText(newMaMS); // Hiển thị mã mới trên giao diện
            if (rpMauSac.checkTrungMS(ms.getTen()) != null) {
                JOptionPane.showMessageDialog(this, "Tên màu sắc đã tồn tại");
            } else {
                if (rpMauSac.themMS(ms) > 0) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                    fillTableMauSac(rpMauSac.getAllMS());
                    clearFormMS();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại");
                }
            }
        }
    }//GEN-LAST:event_btn_themMSActionPerformed

    private void btn_suaMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaMSActionPerformed
        int i = tbl_mausac.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn màu sắc cần sửa");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa màu sắc này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        ModelMauSac ms = readFormMS();
        if (ms != null) {
            String maCu = tbl_mausac.getValueAt(i, 0).toString();
            // Không kiểm tra trùng mã vì mã không thay đổi khi sửa (txt_maMS không editable)

            int id = rpMauSac.getAllMS().stream()
                    .filter(m -> m.getMa().equals(maCu))
                    .findFirst()
                    .get()
                    .getIdMauSac();

            if (rpMauSac.suaMS(id, ms) > 0) {
                JOptionPane.showMessageDialog(this, "Sửa thành công");
                fillTableMauSac(rpMauSac.getAllMS());
                clearFormMS();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại");
            }
        }
    }//GEN-LAST:event_btn_suaMSActionPerformed

    private void tbl_mausacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_mausacMouseClicked
        int i = tbl_mausac.getSelectedRow();
        if (i >= 0) {
            showDataMS(i);
            txt_maMS.setEditable(false); // Không cho chỉnh sửa mã khi sửa
        }
    }//GEN-LAST:event_tbl_mausacMouseClicked

    private void tbl_kichthuocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_kichthuocMouseClicked
        int i = tbl_kichthuoc.getSelectedRow();
        if (i >= 0) {
            showDataKT(i);
            txt_maKT.setEditable(false); // Không cho sửa mã khi chọn
        }
    }//GEN-LAST:event_tbl_kichthuocMouseClicked

    private void btn_themKTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themKTActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thêm kích thước này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        ModelKichThuoc kt = readFormKT();
        if (kt != null) {
            // Buộc sinh mã mới bất kể giá trị hiện tại trong txt_maKT
            String newMaKT = generateNewMaKT();
            kt.setMa(newMaKT); // Cập nhật mã mới vào model
            kt.setTrangThai(true);
            txt_maKT.setText(newMaKT); // Hiển thị mã mới trên giao diện
            if (rpKichThuoc.checkTrungKT(kt.getTen()) != null) {
                JOptionPane.showMessageDialog(this, "Tên kích thước đã tồn tại");
            } else {
                if (rpKichThuoc.themKT(kt) > 0) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                    fillTableKichThuoc(rpKichThuoc.getAllKT());
                    clearFormKT();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại");
                }
            }
        }
    }//GEN-LAST:event_btn_themKTActionPerformed

    private void btn_suaKTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaKTActionPerformed
        int i = tbl_kichthuoc.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn kích thước cần sửa");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa kích thước này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        ModelKichThuoc kt = readFormKT();
        if (kt != null) {
            String maCu = tbl_kichthuoc.getValueAt(i, 0).toString();
            int id = rpKichThuoc.getAllKT().stream()
                    .filter(k -> k.getMa().equals(maCu))
                    .findFirst()
                    .get()
                    .getId();

            if (rpKichThuoc.suaKT(id, kt) > 0) {
                JOptionPane.showMessageDialog(this, "Sửa thành công");
                fillTableKichThuoc(rpKichThuoc.getAllKT());
                clearFormKT();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại");
            }
        }
    }//GEN-LAST:event_btn_suaKTActionPerformed

    private void tbl_spctMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_spctMouseClicked

        int i = tbl_spct.getSelectedRow();
        if (i >= 0) {
            // Hiển thị dữ liệu từ bảng lên form
            txt_maspct.setText(tbl_spct.getValueAt(i, 0).toString());
            txt_tenspct.setText(tbl_spct.getValueAt(i, 1).toString());
            txt_soluongspct.setText(tbl_spct.getValueAt(i, 2).toString());
            cbo_thuongHieu.setSelectedItem(tbl_spct.getValueAt(i, 3).toString());
            cbo_mauSac.setSelectedItem(tbl_spct.getValueAt(i, 4).toString());
            cbo_kichThuoc.setSelectedItem(tbl_spct.getValueAt(i, 5).toString());

            // Xử lý đơn giá: loại bỏ ".0" và " VĐN" nếu có
            String donGiaStr = tbl_spct.getValueAt(i, 6).toString().replace(" VĐN", "").replace(",", "");
            try {
                BigDecimal donGia = new BigDecimal(donGiaStr);
                // Định dạng với dấu phẩy, bỏ phần thập phân .0
                DecimalFormat df = new DecimalFormat("#,###");
                txt_dongiaspct.setText(df.format(donGia));
            } catch (NumberFormatException e) {
                // Nếu không parse được, giữ nguyên giá trị đã loại bỏ " VĐN"
                txt_dongiaspct.setText(donGiaStr);
            }
            // Xử lý trạng thái
            if (tbl_spct.getValueAt(i, 7).toString().equals("Còn Bán")) {
                rdo_conban.setSelected(true);
            } else {
                rdo_ngungban.setSelected(true);
            }
        }

    }//GEN-LAST:event_tbl_spctMouseClicked

    private void btn_themspctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themspctActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thêm sản phẩm này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            // Lấy thông tin từ giao diện (không trim ngay để kiểm tra khoảng trống)
            txt_maspct.setText("");
            String maSanPham = generateNewMaSanPham();
            String tenSanPhamInput = txt_tenspct.getText();
            String thuongHieu = cbo_thuongHieu.getSelectedItem() != null ? cbo_thuongHieu.getSelectedItem().toString() : "";
            String mauSac = cbo_mauSac.getSelectedItem() != null ? cbo_mauSac.getSelectedItem().toString() : "";
            String kichThuoc = cbo_kichThuoc.getSelectedItem() != null ? cbo_kichThuoc.getSelectedItem().toString() : "";
            String soLuongStrInput = txt_soluongspct.getText();
            String donGiaStrInput = txt_dongiaspct.getText();

            // Kiểm tra khoảng trống ở đầu hoặc cuối
            if (!tenSanPhamInput.equals(tenSanPhamInput.trim())) {
                JOptionPane.showMessageDialog(this, "Tên sản phẩm không được chứa khoảng trống ở đầu hoặc cuối");
                txt_tenspct.requestFocus();
                return;
            }
            if (!soLuongStrInput.equals(soLuongStrInput.trim())) {
                JOptionPane.showMessageDialog(this, "Số lượng không được chứa khoảng trống ở đầu hoặc cuối");
                txt_soluongspct.requestFocus();
                return;
            }
            if (!donGiaStrInput.equals(donGiaStrInput.trim())) {
                JOptionPane.showMessageDialog(this, "Đơn giá không được chứa khoảng trống ở đầu hoặc cuối");
                txt_dongiaspct.requestFocus();
                return;
            }

            // Trim sau khi kiểm tra
            String tenSanPham = tenSanPhamInput.trim();
            String soLuongStr = soLuongStrInput.trim();
            String donGiaStr = donGiaStrInput.trim();

            // Validate tenSanPham
            if (tenSanPham.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên sản phẩm không được để trống");
                txt_tenspct.requestFocus();
                return;
            }
            // Kiểm tra tên sản phẩm chỉ chứa chữ cái và khoảng trắng
            if (!tenSanPham.matches("[\\p{L}\\s]+")) {
                JOptionPane.showMessageDialog(this, "Tên sản phẩm không được chứa số hoặc ký tự đặc biệt");
                txt_tenspct.requestFocus();
                return;
            }

            

            // Validate số lượng
            if (soLuongStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bạn chưa nhập số lượng");
                txt_soluongspct.requestFocus();
                return;
            }
            int soLuong;
            try {
                soLuong = Integer.parseInt(soLuongStr);
                if (soLuong <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
                    txt_soluongspct.requestFocus();
                    return;
                }
                if (soLuong > 999) {
                    JOptionPane.showMessageDialog(this, "Số lượng không được vượt quá 999");
                    txt_soluongspct.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số lượng không được chứa ký tự đặc biệt");
                txt_soluongspct.requestFocus();
                return;
            }

            // Validate đơn giá
            if (donGiaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bạn chưa nhập đơn giá");
                txt_dongiaspct.requestFocus();
                return;
            }
            BigDecimal donGia;
            try {
                String donGiaStrCleaned = donGiaStr.replace(",", "");
                donGia = new BigDecimal(donGiaStrCleaned);
                if (donGia.compareTo(new BigDecimal("49999")) <= 0) {
                    JOptionPane.showMessageDialog(this, "Đơn giá phải từ 50,000 VND trở lên");
                    txt_dongiaspct.requestFocus();
                    return;
                }
                if (donGia.compareTo(new BigDecimal("10000000")) > 0) {
                    JOptionPane.showMessageDialog(this, "Đơn giá không được vượt quá 10,000,000 VND");
                    txt_dongiaspct.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Đơn giá không được chứa chữ hoặc ký tự đặc biệt");
                txt_dongiaspct.requestFocus();
                return;
            }

            // Khởi tạo repository
            RepositoriSanPhamChiTiet repository = new RepositoriSanPhamChiTiet();

            // Kiểm tra trùng lặp tên sản phẩm
            if (repository.checkTrungTenSPCT(tenSanPham)) {
                JOptionPane.showMessageDialog(this, "Tên sản phẩm chi tiết đã tồn tại!");
                txt_tenspct.requestFocus();
                return;
            }

            // Kiểm tra mã sản phẩm trùng lặp
            if (isMaSanPhamExists(maSanPham)) {
                JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại. Vui lòng thử lại.");
                return;
            }

            // Lấy ID từ tên
            int idThuongHieu = getIdByName(thuongHieu, "ThuongHieu", "TenTH");
            int idMauSac = getIdByName(mauSac, "MauSac", "TenMS");
            int idKichThuoc = getIdByName(kichThuoc, "KichThuoc", "TenKT");

            if (idThuongHieu == -1 || idMauSac == -1 || idKichThuoc == -1) {
                JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.");
                return;
            }

            // Gọi phương thức thêm sản phẩm
            boolean isInserted = repository.insertSanPham(maSanPham, tenSanPham, idThuongHieu, idMauSac, idKichThuoc, soLuong, donGia);

            // Hiển thị thông báo kết quả
            if (isInserted) {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
                // Làm mới bảng dữ liệu
                ArrayList<SanPhamChiTietModel> list = repository.getAllSPCT();
                DefaultTableModel model = (DefaultTableModel) tbl_spct.getModel();
                model.setRowCount(0);
                for (SanPhamChiTietModel ctsp : list) {
                    model.addRow(ctsp.toDataRowSPCT());
                }
                txt_maspct.setText(maSanPham); // Hiển thị mã vừa tạo
            } else {
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage());
        }


    }//GEN-LAST:event_btn_themspctActionPerformed
    //get masanpham
    public String getLastMaSanPham() {
        String sql = "SELECT TOP 1 MaSanPham FROM SanPham ORDER BY ID DESC"; // Sử dụng TOP 1 thay cho LIMIT 1
        try (Connection connection = DBconnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getString("MaSanPham");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateNewMaSanPham() {
        String lastMaSanPham = getLastMaSanPham();
        if (lastMaSanPham == null) {
            return "SP001"; // Nếu chưa có mã nào, bắt đầu từ SP001
        }

        // Tách phần số từ mã sản phẩm
        int numberPart = Integer.parseInt(lastMaSanPham.substring(2)); // Ví dụ: "SP005" -> 5
        numberPart++; // Tăng thêm 1

        return String.format("SP%03d", numberPart); // Định dạng SPxxx
    }

    //thuoc phan them san pham
    public int getIdByName(String name, String tableName, String columnName) {
        String sql = "SELECT ID FROM " + tableName + " WHERE " + columnName + " = ?";
        try (Connection connection = DBconnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name); // Truyền tên vào câu truy vấn
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID"); // Lấy ID nếu tìm thấy
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không tìm thấy ID cho tên: " + name);
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }

    public boolean isMaSanPhamExists(String maSanPham) {
        String sql = "SELECT COUNT(*) FROM SanPham WHERE MaSanPham = ?";
        try (Connection connection = DBconnect.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maSanPham); // Gán giá trị mã sản phẩm vào câu lệnh
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu mã sản phẩm đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu không tìm thấy
    }

    private void btn_lammoispctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoispctActionPerformed
        clearFormSPCT();
        fillTableCTSP(rpCTSP.getAllSPCT());
    }//GEN-LAST:event_btn_lammoispctActionPerformed

    private void btn_suacpctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suacpctActionPerformed

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa sản phẩm này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            // Lấy thông tin từ giao diện (không trim ngay để kiểm tra khoảng trống)
            String maSanPhamInput = txt_maspct.getText();
            String tenSanPhamInput = txt_tenspct.getText();
            String thuongHieu = cbo_thuongHieu.getSelectedItem() != null ? cbo_thuongHieu.getSelectedItem().toString() : "";
            String mauSac = cbo_mauSac.getSelectedItem() != null ? cbo_mauSac.getSelectedItem().toString() : "";
            String kichThuoc = cbo_kichThuoc.getSelectedItem() != null ? cbo_kichThuoc.getSelectedItem().toString() : "";
            String soLuongStrInput = txt_soluongspct.getText();
            String donGiaStrInput = txt_dongiaspct.getText();

            // Kiểm tra khoảng trống ở đầu hoặc cuối
            if (!maSanPhamInput.equals(maSanPhamInput.trim())) {
                JOptionPane.showMessageDialog(this, "Mã sản phẩm không được chứa khoảng trống ở đầu hoặc cuối");
                txt_maspct.requestFocus();
                return;
            }
            if (!tenSanPhamInput.equals(tenSanPhamInput.trim())) {
                JOptionPane.showMessageDialog(this, "Tên sản phẩm không được chứa khoảng trống ở đầu hoặc cuối");
                txt_tenspct.requestFocus();
                return;
            }
            if (!soLuongStrInput.equals(soLuongStrInput.trim())) {
                JOptionPane.showMessageDialog(this, "Số lượng không được chứa khoảng trống ở đầu hoặc cuối");
                txt_soluongspct.requestFocus();
                return;
            }
            if (!donGiaStrInput.equals(donGiaStrInput.trim())) {
                JOptionPane.showMessageDialog(this, "Đơn giá không được chứa khoảng trống ở đầu hoặc cuối");
                txt_dongiaspct.requestFocus();
                return;
            }

            // Trim sau khi kiểm tra
            String maSanPham = maSanPhamInput.trim();
            String tenSanPham = tenSanPhamInput.trim();
            String soLuongStr = soLuongStrInput.trim();
            String donGiaStr = donGiaStrInput.trim();

            // Kiểm tra các trường không được để trống
            if (maSanPham.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã sản phẩm");
                txt_maspct.requestFocus();
                return;
            }
            if (tenSanPham.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên sản phẩm");
                txt_tenspct.requestFocus();
                return;
            }

            if (soLuongStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bạn chưa nhập số lượng");
                txt_soluongspct.requestFocus();
                return;
            }
            if (donGiaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bạn chưa nhập đơn giá");
                txt_dongiaspct.requestFocus();
                return;
            }

            // Validate và parse soLuong riêng
            int soLuong;
            try {
                soLuong = Integer.parseInt(soLuongStr);
                if (soLuong < 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng không được âm");
                    txt_soluongspct.requestFocus();
                    return;
                }
                if (soLuong > 999) {
                    JOptionPane.showMessageDialog(this, "Số lượng không được vượt quá 999");
                    txt_soluongspct.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên hợp lệ");
                txt_soluongspct.requestFocus();
                return;
            }

            // Validate và parse donGia riêng (hỗ trợ dấu phẩy)
            BigDecimal donGia;
            try {
                String donGiaStrCleaned = donGiaStr.replace(",", "");
                donGia = new BigDecimal(donGiaStrCleaned);
                if (donGia.compareTo(new BigDecimal("49999")) <= 0) {
                    JOptionPane.showMessageDialog(this, "Đơn giá phải từ 50,000 VND trở lên");
                    txt_dongiaspct.requestFocus();
                    return;
                }
                if (donGia.compareTo(new BigDecimal("10000000")) > 0) {
                    JOptionPane.showMessageDialog(this, "Đơn giá không được vượt quá 10,000,000 VND");
                    txt_dongiaspct.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Đơn giá không được chứa chữ hoặc ký tự đặc biệt");
                txt_dongiaspct.requestFocus();
                return;
            }

            // Lấy trạng thái từ radio button
            boolean trangThai = rdo_conban.isSelected(); // true: Còn Bán, false: Ngừng Bán

            // Lấy ID từ tên
            int idThuongHieu = getIdByName(thuongHieu, "ThuongHieu", "TenTH");
            int idMauSac = getIdByName(mauSac, "MauSac", "TenMS");
            int idKichThuoc = getIdByName(kichThuoc, "KichThuoc", "TenKT");

            if (idThuongHieu == -1 || idMauSac == -1 || idKichThuoc == -1) {
                JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.");
                return;
            }

            // Gọi phương thức cập nhật (bao gồm trạng thái)
            RepositoriSanPhamChiTiet repository = new RepositoriSanPhamChiTiet();
            boolean isUpdated = repository.updateSanPham(maSanPham, tenSanPham, idThuongHieu, idMauSac, idKichThuoc, soLuong, donGia, trangThai);

            if (isUpdated) {
                JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!");
                // Làm mới bảng dữ liệu
                ArrayList<SanPhamChiTietModel> list = repository.getAllSPCT();
                DefaultTableModel model = (DefaultTableModel) tbl_spct.getModel();
                model.setRowCount(0);
                for (SanPhamChiTietModel ctsp : list) {
                    model.addRow(ctsp.toDataRowSPCT());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thất bại.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage());
        }


    }//GEN-LAST:event_btn_suacpctActionPerformed

    private void btn_ngungbanSPctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ngungbanSPctActionPerformed
        // TODO add your handling code here:
        try {
            String maSanPham = txt_maspct.getText().trim(); // Lấy Mã Sản Phẩm từ giao diện

            // Kiểm tra nếu mã sản phẩm trống
            if (maSanPham.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập Mã Sản Phẩm.");
                return;
            }

            // Gọi phương thức cập nhật trạng thái
            RepositoriSanPhamChiTiet repository = new RepositoriSanPhamChiTiet();
            boolean isUpdated = repository.updateTrangThaiSanPham(maSanPham);

            // Hiển thị thông báo kết quả
            if (isUpdated) {
                JOptionPane.showMessageDialog(null, "Sản phẩm đã được chuyển sang trạng thái ngừng bán.");
                // Làm mới bảng dữ liệu
                ArrayList<SanPhamChiTietModel> list = repository.getAllSPCT();
                DefaultTableModel model = (DefaultTableModel) tbl_spct.getModel();
                model.setRowCount(0);
                for (SanPhamChiTietModel ctsp : list) {
                    model.addRow(ctsp.toDataRowSPCT());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không thể chuyển trạng thái sản phẩm. Vui lòng kiểm tra lại Mã Sản Phẩm.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi.");
        }

    }//GEN-LAST:event_btn_ngungbanSPctActionPerformed

    private void btn_lammoiMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoiMSActionPerformed
        clearFormMS();
        fillTableMauSac(rpMauSac.getAllMS());
    }//GEN-LAST:event_btn_lammoiMSActionPerformed

    private void btn_lammoiKTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoiKTActionPerformed
        clearFormKT();
        fillTableKichThuoc(rpKichThuoc.getAllKT());
    }//GEN-LAST:event_btn_lammoiKTActionPerformed

    private void btn_lammoiTHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lammoiTHActionPerformed
        clearFormTH();
        fillTableTH(rp.getallTH());
    }//GEN-LAST:event_btn_lammoiTHActionPerformed

    private void txt_tenTHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tenTHActionPerformed

    }//GEN-LAST:event_txt_tenTHActionPerformed

    private void tbl_thuonghieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_thuonghieuMouseClicked
        i = tbl_thuonghieu.getSelectedRow();
        if (i >= 0) {
            showdata(i);
            txt_maTH.setEditable(false); // Không cho sửa mã khi đã chọn
        }
    }//GEN-LAST:event_tbl_thuonghieuMouseClicked

    private void btn_suaTHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_suaTHActionPerformed
        i = tbl_thuonghieu.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thương hiệu cần sửa");
            return;
        }

        // Thêm bảng thông báo xác nhận
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa thương hiệu này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return; // Nếu người dùng chọn "No", thoát khỏi phương thức
        }

        ModelThuongHieu th = readFormTH();
        if (th != null) {
            String maCu = tbl_thuonghieu.getValueAt(i, 0).toString();
            // Kiểm tra nếu mã mới trùng với mã khác ngoài mã cũ
            ModelThuongHieu existing = rp.checkTrungtenTH(th.getMaTH());
            if (existing != null && !existing.getMaTH().equals(maCu)) {
                JOptionPane.showMessageDialog(this, "Mã thương hiệu mới đã tồn tại");
                return;
            }

            if (rp.suaTH(maCu, th) > 0) {
                JOptionPane.showMessageDialog(this, "Sửa thành công");
                fillTableTH(rp.getallTH());
                clearFormTH();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại");
            }
        }
    }//GEN-LAST:event_btn_suaTHActionPerformed

    private void btn_themTHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themTHActionPerformed

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thêm thương hiệu này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        ModelThuongHieu th = readFormTH();
        if (th != null) {
            // Buộc sinh mã mới bất kể giá trị hiện tại trong txt_maTH
            String newMaTH = generateNewMaTH();
            th.setTrangThai(true); // Đặt trạng thái mặc định là Hoạt động
            th.setMaTH(newMaTH); // Cập nhật mã mới vào model
            txt_maTH.setText(newMaTH); // Hiển thị mã mới trên giao diện

            if (rp.checkTrungtenTH(th.getTenTH()) != null) {
                JOptionPane.showMessageDialog(this, "Tên thương hiệu đã tồn tại");
            } else {
                if (rp.themTH(th) > 0) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                    fillTableTH(rp.getallTH());
                    clearFormTH();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại");
                }
            }
        }
    }//GEN-LAST:event_btn_themTHActionPerformed

    private void txt_maTHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_maTHActionPerformed

    }//GEN-LAST:event_txt_maTHActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

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
            java.util.logging.Logger.getLogger(SanPhamView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPhamView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPhamView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPhamView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SanPhamView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_lammoiKT;
    private javax.swing.JButton btn_lammoiMS;
    private javax.swing.JButton btn_lammoiTH;
    private javax.swing.JButton btn_lammoispct;
    private javax.swing.JButton btn_ngungbanSPct;
    private javax.swing.JButton btn_suaKT;
    private javax.swing.JButton btn_suaMS;
    private javax.swing.JButton btn_suaTH;
    private javax.swing.JButton btn_suacpct;
    private javax.swing.JButton btn_themKT;
    private javax.swing.JButton btn_themMS;
    private javax.swing.JButton btn_themTH;
    private javax.swing.JButton btn_themspct;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JComboBox<String> cbo_kichThuoc;
    private javax.swing.JComboBox<String> cbo_mauSac;
    private javax.swing.JComboBox<String> cbo_thuongHieu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdo_conban;
    private javax.swing.JRadioButton rdo_ngungban;
    private javax.swing.JTable tbl_kichthuoc;
    private javax.swing.JTable tbl_mausac;
    private javax.swing.JTable tbl_spct;
    private javax.swing.JTable tbl_thuonghieu;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txt_dongiaspct;
    private javax.swing.JTextField txt_maKT;
    private javax.swing.JTextField txt_maMS;
    private javax.swing.JTextField txt_maTH;
    private javax.swing.JTextField txt_maspct;
    private javax.swing.JTextField txt_soluongspct;
    private javax.swing.JTextField txt_tenKT;
    private javax.swing.JTextField txt_tenMS;
    private javax.swing.JTextField txt_tenTH;
    private javax.swing.JTextField txt_tenspct;
    // End of variables declaration//GEN-END:variables

    // Hiển thị dữ liệu khi click vào bảng
    void showdata(int i) {
        if (i >= 0 && i < tbl_thuonghieu.getRowCount()) {
            txt_maTH.setText(tbl_thuonghieu.getValueAt(i, 0).toString());
            txt_tenTH.setText(tbl_thuonghieu.getValueAt(i, 1).toString());

        }
    }

    void showDataMS(int i) {
        if (i >= 0 && i < tbl_mausac.getRowCount()) {
            txt_maMS.setText(tbl_mausac.getValueAt(i, 0).toString());
            txt_tenMS.setText(tbl_mausac.getValueAt(i, 1).toString());

        }
    }

    void showDataKT(int i) {
        if (i >= 0 && i < tbl_kichthuoc.getRowCount()) {
            txt_maKT.setText(tbl_kichthuoc.getValueAt(i, 0).toString());
            txt_tenKT.setText(tbl_kichthuoc.getValueAt(i, 1).toString());

        }
    }

// Xóa dữ liệu form
    private void clearFormTH() {
        txt_maTH.setText("");
        txt_tenTH.setText("");
        txt_maTH.setEditable(false);
        i = -1;
    }

    private void clearFormMS() {
        txt_maMS.setText(""); // Không đặt mã mặc định, sẽ sinh khi thêm
        txt_tenMS.setText("");
        txt_maMS.setEditable(true); // Đảm bảo có thể nhập mã khi thêm mới
    }

    private void clearFormKT() {
        txt_maKT.setText("");
        txt_tenKT.setText("");

        txt_maKT.setEditable(true);
    }

    private void clearFormSPCT() {
        txt_maspct.setText("");
        txt_tenspct.setText("");
        txt_soluongspct.setText("");
        txt_dongiaspct.setText("");
        cbo_thuongHieu.setSelectedIndex(0);
        cbo_mauSac.setSelectedIndex(0);
        cbo_kichThuoc.setSelectedIndex(0);
        rdo_conban.setSelected(true);
        txtTimKiem.setText("");
    }

    // Sinh mã tự động 
    private String generateNewMaTH() {
        ArrayList<ModelThuongHieu> list = rp.getallTH();
        int maxNumber = 0;
        for (ModelThuongHieu th : list) {
            String ma = th.getMaTH();
            if (ma.startsWith("TH")) {
                int num = Integer.parseInt(ma.substring(2));
                maxNumber = Math.max(maxNumber, num);
            }
        }
        return String.format("TH%03d", maxNumber + 1);
    }

    private String generateNewMaMS() {
        ArrayList<ModelMauSac> list = rpMauSac.getAllMS();
        int maxNumber = 0;
        for (ModelMauSac ms : list) {
            String ma = ms.getMa();
            if (ma != null && ma.startsWith("MS")) {
                int num = Integer.parseInt(ma.substring(2));
                maxNumber = Math.max(maxNumber, num);
            }
        }
        return String.format("MS%03d", maxNumber + 1);
    }

    private String generateNewMaKT() {
        ArrayList<ModelKichThuoc> list = rpKichThuoc.getAllKT();
        int maxNumber = 0;
        for (ModelKichThuoc kt : list) {
            String ma = kt.getMa();
            if (ma != null && ma.startsWith("KT")) {
                int num = Integer.parseInt(ma.substring(2));
                maxNumber = Math.max(maxNumber, num);
            }
        }
        return String.format("KT%03d", maxNumber + 1);
    }

    // Đọc dữ liệu từ form 
    ModelThuongHieu readFormTH() {
        // Lấy giá trị gốc để kiểm tra khoảng trống
        String maInput = txt_maTH.getText();
        String tenTHInput = txt_tenTH.getText();

        // Kiểm tra khoảng trống ở đầu hoặc cuối
        if (!tenTHInput.equals(tenTHInput.trim())) {
            JOptionPane.showMessageDialog(this, "Tên thương hiệu không được chứa khoảng trống ở đầu hoặc cuối");
            txt_tenTH.requestFocus();
            return null;
        }

        // Trim sau khi kiểm tra
        String ma = maInput.trim();
        String tenTH = tenTHInput.trim();

        // Kiểm tra tên thương hiệu rỗng
        if (tenTH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên thương hiệu");
            txt_tenTH.requestFocus();
            return null;
        }

        // Kiểm tra định dạng tên thương hiệu (chỉ chứa chữ cái và khoảng trống)
        if (!tenTH.matches("[\\p{L}\\s]+")) {
            JOptionPane.showMessageDialog(this, "Tên thương hiệu không được chứa số hoặc ký tự đặc biệt");
            txt_tenTH.requestFocus();
            return null;
        }

        // Nếu mã  (khi thêm mới), sinh mã tự động
        if (ma.isEmpty()) {
            ma = generateNewMaTH();
            txt_maTH.setText(ma);
        }

        return new ModelThuongHieu(ma, tenTH);
    }

    ModelMauSac readFormMS() {
        // Lấy giá trị gốc để kiểm tra khoảng trống
        String maInput = txt_maMS.getText();
        String tenMSInput = txt_tenMS.getText();

        // Kiểm tra khoảng trống ở đầu hoặc cuối
        if (!tenMSInput.equals(tenMSInput.trim())) {
            JOptionPane.showMessageDialog(this, "Tên màu sắc không được chứa khoảng trống ở đầu hoặc cuối");
            txt_tenMS.requestFocus();
            return null;
        }

        // Trim sau khi kiểm tra
        String ma = maInput.trim();
        String tenMS = tenMSInput.trim();

        // Kiểm tra tên màu sắc rỗng
        if (tenMS.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên màu sắc");
            txt_tenMS.requestFocus();
            return null;
        }

        // Kiểm tra định dạng tên màu sắc (chỉ chứa chữ cái và khoảng trống)
        if (!tenMS.matches("[\\p{L}\\s]+")) {
            JOptionPane.showMessageDialog(this, "Tên màu sắc không được chứa số hoặc ký tự đặc biệt");
            txt_tenMS.requestFocus();
            return null;
        }

        // Nếu mã rỗng (khi thêm mới), sinh mã tự động
        if (ma.isEmpty()) {
            ma = generateNewMaMS();
            txt_maMS.setText(ma); // Hiển thị mã mới trên giao diện
        }

        return new ModelMauSac(ma, tenMS); // ID tự động sinh trong DB
    }

    private ModelKichThuoc readFormKT() {
        // Lấy giá trị gốc để kiểm tra khoảng trống
        String maInput = txt_maKT.getText();
        String tenKTInput = txt_tenKT.getText();

        // Kiểm tra khoảng trống ở đầu hoặc cuối
        if (!maInput.equals(maInput.trim())) {
            JOptionPane.showMessageDialog(this, "Mã kích thước không được chứa khoảng trắng ở đầu hoặc cuối");
            txt_maKT.requestFocus();
            return null;
        }
        if (!tenKTInput.equals(tenKTInput.trim())) {
            JOptionPane.showMessageDialog(this, "Tên kích thước không được chứa khoảng trắng ở đầu hoặc cuối");
            txt_tenKT.requestFocus();
            return null;
        }

        // Trim sau khi kiểm tra
        String ma = maInput.trim();
        String tenKT = tenKTInput.trim();

        // Kiểm tra tên kích thước rỗng
        if (tenKT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên kích thước");
            txt_tenKT.requestFocus();
            return null;
        }

        // Cho phép chữ cái, số, khoảng trắng và dấu phẩy
        if (!tenKT.matches("[\\p{L}0-9\\s,]+")) {
            JOptionPane.showMessageDialog(this, "Tên kích thước chỉ được chứa chữ cái, số, khoảng trắng và dấu phẩy");
            txt_tenKT.requestFocus();
            return null;
        }

        // Kiểm tra nếu tenKT là số (số nguyên hoặc số thập phân với dấu phẩy)
        if (tenKT.matches("\\d+([,]\\d+)?")) {
            try {
                // Thay dấu phẩy thành dấu chấm để parse
                String tenKTNumber = tenKT.replace(",", ".");
                double tenKTValue = Double.parseDouble(tenKTNumber);
                if (tenKTValue == 0) {
                    JOptionPane.showMessageDialog(this, "Kích thước không được bằng 0");
                    txt_tenKT.requestFocus();
                    return null;
                }
                if (tenKTValue <= 2 || tenKTValue >= 60) {
                    JOptionPane.showMessageDialog(this, "Kích thước số phải lớn hơn 2 và nhỏ hơn 60");
                    txt_tenKT.requestFocus();
                    return null;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Kích thước số không hợp lệ");
                txt_tenKT.requestFocus();
                return null;
            }
        }

        // Nếu mã rỗng (khi thêm mới), sinh mã tự động
        if (ma.isEmpty()) {
            ma = generateNewMaKT();
            txt_maKT.setText(ma);
        }

        return new ModelKichThuoc(ma, tenKT);
    }

    private SanPhamChiTietModel readFormSPCT() {
        String tenSanPham = txt_tenspct.getText().trim();
        String soLuongStr = txt_soluongspct.getText().trim();
        String donGiaStr = txt_dongiaspct.getText().trim();
        String thuongHieu = cbo_thuongHieu.getSelectedItem() != null ? cbo_thuongHieu.getSelectedItem().toString() : "";
        String mauSac = cbo_mauSac.getSelectedItem() != null ? cbo_mauSac.getSelectedItem().toString() : "";
        String kichThuoc = cbo_kichThuoc.getSelectedItem() != null ? cbo_kichThuoc.getSelectedItem().toString() : "";
        boolean trangThai = rdo_conban.isSelected();

        // Kiểm tra tên sản phẩm
        if (tenSanPham.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tên sản phẩm");
            txt_tenspct.requestFocus();
            return null;
        }
        if (!tenSanPham.matches("[\\p{L}\\s]+")) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được chứa số hoặc ký tự đặc biệt");
            txt_tenspct.requestFocus();
            return null;
        }

        // Kiểm tra số lượng
        if (soLuongStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập số lượng");
            txt_soluongspct.requestFocus();
            return null;
        }
        if (!soLuongStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Số lượng chỉ được nhập số");
            txt_soluongspct.requestFocus();
            return null;
        }

        int soLuong;
        try {
            soLuong = Integer.parseInt(soLuongStr);
            if (soLuong < 0) {
                JOptionPane.showMessageDialog(this, "Số lượng không được âm");
                txt_soluongspct.requestFocus();
                return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải không được chứa ký tự đặc biệt");
            txt_soluongspct.requestFocus();
            return null;
        }

        // Kiểm tra đơn giá
        if (donGiaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập đơn giá");
            txt_dongiaspct.requestFocus();
            return null;
        }
        if (!donGiaStr.matches("\\d+(\\.\\d+)?")) {
            JOptionPane.showMessageDialog(this, "Đơn giá chỉ được nhập số và dấu chấm thập phân");
            txt_dongiaspct.requestFocus();
            return null;
        }

        double donGia;
        try {
            donGia = Double.parseDouble(donGiaStr);
            if (donGia < 0) {
                JOptionPane.showMessageDialog(this, "Đơn giá không được âm");
                txt_dongiaspct.requestFocus();
                return null;
            }
            if (donGia < 1000) {
                JOptionPane.showMessageDialog(this, "Đơn giá không được nhỏ hơn 1000");
                txt_dongiaspct.requestFocus();
                return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá không được chứa ký tự đặc biệt");
            txt_dongiaspct.requestFocus();
            return null;
        }

        return new SanPhamChiTietModel(tenSanPham, soLuong, thuongHieu, mauSac, kichThuoc, donGia, trangThai);
    }

    //Them tim kiem san pham
    private void searchListener() {
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTable();
            }

            private void updateTable() {
                String keyword = txtTimKiem.getText().trim();

                // Gọi phương thức searchSanPham để lấy dữ liệu từ cơ sở dữ liệu
                RepositoriSanPhamChiTiet repository = new RepositoriSanPhamChiTiet();
                List<Object[]> searchResults = repository.searchSanPham(keyword);

                // Cập nhật bảng với danh sách kết quả
                DefaultTableModel model = (DefaultTableModel) tbl_spct.getModel();
                model.setRowCount(0); // Xóa dữ liệu cũ
                for (Object[] row : searchResults) {
                    model.addRow(row); // Thêm dữ liệu mới
                }
            }
        });
    }

}
