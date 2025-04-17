 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model.HoaDon;
import Model.HoaDonChiTiet;
import Model.ThongKeTongQuan;
import Repo.HoaDonChiTietRepo;
import Repo.HoaDonRepo;
import Repo.ThongKeRepo;
import SanPham.Model.SanPhamChiTietModel;
import SanPham.Repositori.RepositoriSanPham;
import SanPham.Repositori.RepositoriSanPhamChiTiet;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;



import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author nguye
 */
public class ThongKeView extends javax.swing.JFrame {
    private ThongKeRepo repoTK = new ThongKeRepo();
    private HoaDonRepo repoHD = new HoaDonRepo();
    private HoaDonChiTietRepo repoHDCT = new HoaDonChiTietRepo();
   private RepositoriSanPham repoSP = new RepositoriSanPham();
   private RepositoriSanPhamChiTiet repoSPCT = new RepositoriSanPhamChiTiet();
   private DefaultTableModel mol = new DefaultTableModel();
   
    /**
     * Creates new form Thong_Ke
     */
    public ThongKeView() {
        initComponents();
         dtc_Tu.setDate(new Date());
        dtc_Den.setDate(new Date());
        dtc_TuTop5.setDate(new Date());
        dtc_DenTop5.setDate(new Date());
        // Thêm sự kiện cho nút "Lọc" (btnLoc)
        // Khi người dùng nhấn nút, phương thức btnLocActionPerformed() sẽ được gọi
        btn_Loc.addActionListener(e -> btn_LocActionPerformed(e));
        btn_TimKiem.addActionListener(e -> btn_TimKiemActionPerformed(e));
        btn_Tim.addActionListener(e -> btn_TimKiemActionPerformed(e));
        
        loadDefaultData();
         int currentYear = LocalDate.now().getYear();
//        showLineChart(currentYear); // Hiển thị biểu đồ cho năm hiện tại mặc định
//        showBarChartTop5(new Date(), new Date()); // Hiển thị biểu đồ top 5 mặc định
    }
    
private void loadDefaultData() {
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);
        // Chuyển LocalDate thành java.util.Date
        Date tuNgay = java.util.Date.from(firstDayOfMonth.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant());
        Date denNgay = new Date();
        dtc_Tu.setDate(tuNgay);
        dtc_Den.setDate(denNgay);
        loadSummaryData(tuNgay, denNgay);
    }

    private void loadSummaryData(Date tuNgay, Date denNgay) {
        try {
            ArrayList<ThongKeTongQuan> listTKTQ = repoTK.getAll(tuNgay, denNgay);
            if (listTKTQ != null && !listTKTQ.isEmpty()) {
                ThongKeTongQuan thongKe = listTKTQ.get(0);
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                txt_DoanhThu.setText(currencyFormat.format(thongKe.getTongDoanhThu()));
                txt_TongSP.setText(String.valueOf(thongKe.getTongSanPham()));
                txt_TongKhach.setText(String.valueOf(thongKe.getTongKhachHang()));
            } else {
                txt_DoanhThu.setText("0 VNĐ");
                txt_TongSP.setText("0");
                txt_TongKhach.setText("0");
                JOptionPane.showMessageDialog(this, "Không có dữ liệu trong khoảng thời gian này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            String errorMessage = ex.getMessage() != null ? ex.getMessage() : "Không xác định được lỗi";
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi load dữ liệu tổng quan: " + errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
   public void showLineChart(int year) {
   DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Lấy dữ liệu doanh thu cho năm được chọn
        Map<Integer, Double> doanhThuTheoThang = repoTK.getDoanhThuTheoThang(year);
        System.out.println("Doanh thu theo tháng cho năm " + year + ": " + doanhThuTheoThang);

        // Kiểm tra dữ liệu
        boolean hasData = doanhThuTheoThang.values().stream().anyMatch(dt -> dt != 0.0);
        if (!hasData) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu doanh thu trong năm " + year + "!", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            panel_BieuDo.removeAll();
            panel_BieuDo.revalidate();
            panel_BieuDo.repaint();
            return;
        }

        // Định nghĩa tên các tháng cho biểu đồ
        String[] monthNames = {
            "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6","Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
        };

        // Điền dữ liệu doanh thu cho từng tháng vào tập dữ liệu
        for (int month = 1; month <= 12; month++) {
            Double doanhThu = doanhThuTheoThang.getOrDefault(month, 0.0);
            dataset.setValue(doanhThu, "Doanh thu", monthNames[month - 1]);
        }

        // Tạo biểu đồ
        JFreeChart lineChart = ChartFactory.createLineChart(
            "Doanh thu theo tháng (" + year + ")", // Tiêu đề biểu đồ
            "Tháng", // Nhãn trục X
            "Doanh thu (VNĐ)", // Nhãn trục Y
            dataset, 
            PlotOrientation.VERTICAL, 
            false, // Không hiển thị chú thích
            true,  // Hiển thị tooltip
            false  // Không sử dụng URL
        );

        // Tùy chỉnh biểu đồ
        CategoryPlot lineCategoryPlot = lineChart.getCategoryPlot();
        lineCategoryPlot.setBackgroundPaint(Color.white);
        lineCategoryPlot.setRangeGridlinesVisible(true); // Thêm đường lưới

        // Tùy chỉnh trục Y
        NumberAxis rangeAxis = (NumberAxis) lineCategoryPlot.getRangeAxis();
        rangeAxis.setNumberFormatOverride(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")));
        rangeAxis.setLowerBound(0); // Đặt giới hạn dưới của trục Y là 0

        // Tùy chỉnh đường vẽ
        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
        Color lineChartColor = new Color(204, 0, 51); // Màu đỏ cho đường vẽ
        lineRenderer.setSeriesPaint(0, lineChartColor);
        lineRenderer.setSeriesStroke(0, new BasicStroke(2.0f)); // Đặt độ dày đường vẽ

        // Tạo panel để hiển thị biểu đồ
        ChartPanel lineChartPanel = new ChartPanel(lineChart);
        lineChartPanel.setPreferredSize(new Dimension(800, 400)); // Đặt kích thước cho panel
        panel_BieuDo.removeAll();
        panel_BieuDo.setLayout(new BorderLayout());
        panel_BieuDo.add(lineChartPanel, BorderLayout.CENTER);
        panel_BieuDo.revalidate();
        panel_BieuDo.repaint();

        // Ghi log để kiểm tra
        System.out.println("Đã vẽ biểu đồ cho năm: " + year);
}
   
   public void showBarChartTop5(Date tuNgayTop5, Date denNgayTop5) {
    // Khai báo dataset cho biểu đồ
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    // Lấy dữ liệu top 5 sản phẩm từ repo
    List<Map<String, Object>> top5SanPham = repoTK.getTop5SanPhamBanChay(tuNgayTop5, denNgayTop5);
    System.out.println("Top 5 sản phẩm bán chạy nhất từ " + tuNgayTop5 + " đến " + denNgayTop5 + ": " + top5SanPham);

    // Kiểm tra nếu không có dữ liệu
    if (top5SanPham.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Không có dữ liệu sản phẩm bán chạy trong khoảng thời gian này!", 
            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        panel_Top5.removeAll();
        panel_Top5.revalidate();
        panel_Top5.repaint();
        return;
    }

    // Sắp xếp top5SanPham theo số lượng giảm dần
    top5SanPham.sort((Map<String, Object> sp1, Map<String, Object> sp2) -> {
        Integer soLuong1 = (Integer) sp1.get("SoLuong");
        Integer soLuong2 = (Integer) sp2.get("SoLuong");
        return soLuong2.compareTo(soLuong1); // Sắp xếp giảm dần
    });

    // Tìm số lượng lớn nhất để điều chỉnh trục Y
    int maxSoLuong = 0;
    for (Map<String, Object> sanPham : top5SanPham) {
        int soLuong = (Integer) sanPham.get("SoLuong");
        if (soLuong > maxSoLuong) {
            maxSoLuong = soLuong;
        }
    }

    // Điền dữ liệu vào dataset theo thứ tự đã sắp xếp
    for (Map<String, Object> sanPham : top5SanPham) {
        String tenSanPham = (String) sanPham.get("TenSanPham");
        int soLuong = (Integer) sanPham.get("SoLuong");
        dataset.addValue(soLuong, "Số lượng", tenSanPham);
    }

    // Tạo biểu đồ cột
    JFreeChart barChart = ChartFactory.createBarChart(
        "Top 5 sản phẩm bán chạy nhất",
        "Sản phẩm",
        "Số lượng bán ra",
        dataset,
        PlotOrientation.VERTICAL,
        false,
        true,
        false
    );

    // Tùy chỉnh biểu đồ
    CategoryPlot plot = barChart.getCategoryPlot();
    plot.setBackgroundPaint(Color.white);
    plot.setRangeGridlinesVisible(true);

    // Tùy chỉnh trục Y (NumberAxis)
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setLowerBound(0); // Giới hạn dưới là 0
    rangeAxis.setUpperBound(maxSoLuong + 1); // Giới hạn trên là số lượng lớn nhất + 1 để có khoảng trống
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); // Chỉ hiển thị số nguyên
    rangeAxis.setTickUnit(new NumberTickUnit(1)); // Khoảng cách giữa các tick là 1

    plot.getRenderer().setSeriesPaint(0, new Color(51, 153, 255));

    // Hiển thị biểu đồ trên panel
    ChartPanel barChartPanel = new ChartPanel(barChart);
    barChartPanel.setPreferredSize(new Dimension(800, 400));
    panel_Top5.removeAll();
    panel_Top5.setLayout(new BorderLayout());
    panel_Top5.add(barChartPanel, BorderLayout.CENTER);
    panel_Top5.revalidate();
    panel_Top5.repaint();

    System.out.println("Đã vẽ biểu đồ top 5 sản phẩm bán chạy từ " + tuNgayTop5 + " đến " + denNgayTop5);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_DoanhThu = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_TongSP = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txt_TongKhach = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpn_DoanhThu = new javax.swing.JPanel();
        jYearChooser1 = new com.toedter.calendar.JYearChooser();
        btn_TimKiem = new javax.swing.JButton();
        panel_BieuDo = new javax.swing.JPanel();
        jpn_SanPham = new javax.swing.JPanel();
        dtc_TuTop5 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        dtc_DenTop5 = new com.toedter.calendar.JDateChooser();
        btn_Tim = new javax.swing.JButton();
        panel_Top5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        dtc_Tu = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        dtc_Den = new com.toedter.calendar.JDateChooser();
        btn_Loc = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(204, 255, 204));

        jPanel2.setBackground(new java.awt.Color(255, 255, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Tổng doanh thu :");

        txt_DoanhThu.setEditable(false);
        txt_DoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_DoanhThuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_DoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_DoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 102));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Sản phẩm bán ra :");

        txt_TongSP.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txt_TongSP, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_TongSP, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 102));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Tổng khách hàng :");

        txt_TongKhach.setEditable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txt_TongKhach, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_TongKhach, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(137, 137, 137))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        btn_TimKiem.setText("Tìm Kiếm");
        btn_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_BieuDoLayout = new javax.swing.GroupLayout(panel_BieuDo);
        panel_BieuDo.setLayout(panel_BieuDoLayout);
        panel_BieuDoLayout.setHorizontalGroup(
            panel_BieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panel_BieuDoLayout.setVerticalGroup(
            panel_BieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 385, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpn_DoanhThuLayout = new javax.swing.GroupLayout(jpn_DoanhThu);
        jpn_DoanhThu.setLayout(jpn_DoanhThuLayout);
        jpn_DoanhThuLayout.setHorizontalGroup(
            jpn_DoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_DoanhThuLayout.createSequentialGroup()
                .addGap(467, 467, 467)
                .addComponent(jYearChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btn_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(666, Short.MAX_VALUE))
            .addGroup(jpn_DoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_BieuDo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpn_DoanhThuLayout.setVerticalGroup(
            jpn_DoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_DoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpn_DoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_TimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jYearChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_BieuDo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        jTabbedPane1.addTab("Biểu đồ doanh thu", jpn_DoanhThu);

        dtc_TuTop5.setDateFormatString("yyyy-MM-dd");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Từ :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Đến");

        dtc_DenTop5.setDateFormatString("yyyy-MM-dd");

        btn_Tim.setBackground(new java.awt.Color(153, 255, 204));
        btn_Tim.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_Tim.setText("Tìm");
        btn_Tim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_Top5Layout = new javax.swing.GroupLayout(panel_Top5);
        panel_Top5.setLayout(panel_Top5Layout);
        panel_Top5Layout.setHorizontalGroup(
            panel_Top5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panel_Top5Layout.setVerticalGroup(
            panel_Top5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 391, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpn_SanPhamLayout = new javax.swing.GroupLayout(jpn_SanPham);
        jpn_SanPham.setLayout(jpn_SanPhamLayout);
        jpn_SanPhamLayout.setHorizontalGroup(
            jpn_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_SanPhamLayout.createSequentialGroup()
                .addGap(490, 490, 490)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(dtc_TuTop5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel7)
                .addGap(30, 30, 30)
                .addComponent(dtc_DenTop5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(btn_Tim)
                .addContainerGap(374, Short.MAX_VALUE))
            .addGroup(jpn_SanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_Top5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpn_SanPhamLayout.setVerticalGroup(
            jpn_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_SanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpn_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpn_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(dtc_DenTop5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(dtc_TuTop5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(jpn_SanPhamLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btn_Tim)))
                .addGap(18, 18, 18)
                .addComponent(panel_Top5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Biểu đồ top 5 sản phẩm bán chạy nhất", jpn_SanPham);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thời gian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 51, 51))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Thời gian từ : ");

        dtc_Tu.setDateFormatString("yyyy-MM-dd");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("đến");

        dtc_Den.setDateFormatString("yyyy-MM-dd");

        btn_Loc.setBackground(new java.awt.Color(153, 255, 255));
        btn_Loc.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_Loc.setText("Lọc");
        btn_Loc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(249, 249, 249)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dtc_Tu, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jLabel6)
                .addGap(49, 49, 49)
                .addComponent(dtc_Den, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addComponent(btn_Loc)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btn_Loc)
                        .addComponent(dtc_Den, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(dtc_Tu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jPanel8.getAccessibleContext().setAccessibleName("Lọc theo thời gian");
        jPanel8.getAccessibleContext().setAccessibleParent(jPanel8);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_LocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LocActionPerformed
        // TODO add your handling code here:
   Date tuNgay = dtc_Tu.getDate();
        Date denNgay = dtc_Den.getDate();

        // Kiểm tra ngày hợp lệ trước khi gọi loadSummaryData
        if (tuNgay == null || denNgay == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (tuNgay.after(denNgay)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
        // Chuyển tuNgay thành LocalDate để xử lý thời gian
        LocalDate tuNgayLocal = tuNgay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate denNgayLocal = denNgay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Đặt tuNgay thành 00:00:00 của ngày bắt đầu
        LocalDateTime tuNgayStart = tuNgayLocal.atTime(0, 0, 0);
        tuNgay = Date.from(tuNgayStart.atZone(ZoneId.systemDefault()).toInstant());

        // Đặt denNgay thành 23:59:59 của ngày kết thúc
        LocalDateTime denNgayEnd = denNgayLocal.atTime(23, 59, 59);
        denNgay = Date.from(denNgayEnd.atZone(ZoneId.systemDefault()).toInstant());

        // Cập nhật dữ liệu tổng quan với khoảng thời gian đã điều chỉnh
        loadSummaryData(tuNgay, denNgay);

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi lọc dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
        // Cập nhật dữ liệu tổng quan
        loadSummaryData(tuNgay, denNgay);
    }//GEN-LAST:event_btn_LocActionPerformed
    
    private void txt_DoanhThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_DoanhThuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_DoanhThuActionPerformed

    private void btn_TimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimActionPerformed
        // TODO add your handling code here:
        Date tuNgayTop5 = dtc_TuTop5.getDate();
        Date denNgayTop5 = dtc_DenTop5.getDate();

        // Kiểm tra ngày hợp lệ
        if (tuNgayTop5 == null || denNgayTop5 == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (tuNgayTop5.after(denNgayTop5)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        showBarChartTop5(tuNgayTop5, denNgayTop5);
    }//GEN-LAST:event_btn_TimActionPerformed

    private void btn_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimKiemActionPerformed
        int year = jYearChooser1.getYear();
        showLineChart(year);
    }//GEN-LAST:event_btn_TimKiemActionPerformed

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
            java.util.logging.Logger.getLogger(ThongKeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongKeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongKeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongKeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThongKeView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Loc;
    private javax.swing.JButton btn_Tim;
    private javax.swing.JButton btn_TimKiem;
    private com.toedter.calendar.JDateChooser dtc_Den;
    private com.toedter.calendar.JDateChooser dtc_DenTop5;
    private com.toedter.calendar.JDateChooser dtc_Tu;
    private com.toedter.calendar.JDateChooser dtc_TuTop5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JYearChooser jYearChooser1;
    private javax.swing.JPanel jpn_DoanhThu;
    private javax.swing.JPanel jpn_SanPham;
    private javax.swing.JPanel panel_BieuDo;
    private javax.swing.JPanel panel_Top5;
    private javax.swing.JTextField txt_DoanhThu;
    private javax.swing.JTextField txt_TongKhach;
    private javax.swing.JTextField txt_TongSP;
    // End of variables declaration//GEN-END:variables

}
