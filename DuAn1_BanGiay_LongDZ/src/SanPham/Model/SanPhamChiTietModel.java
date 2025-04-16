package SanPham.Model;

public class SanPhamChiTietModel {
    private String id;
    
    private String tenSanPham;    // Từ bảng SanPham
    private int soLuong;
    private String tenThuongHieu; // Từ bảng ThuongHieu
    private String tenMauSac;     // Từ bảng MauSac
    private String tenKichThuoc;  // Từ bảng KichThuoc
    private double donGia;
    private boolean trangThai;

    public SanPhamChiTietModel() {}

    public SanPhamChiTietModel(String id, String tenSanPham, int soLuong, String tenThuongHieu, 
                               String tenMauSac, String tenKichThuoc, double donGia, boolean trangThai) {
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.tenThuongHieu = tenThuongHieu;
        this.tenMauSac = tenMauSac;
        this.tenKichThuoc = tenKichThuoc;
        this.donGia = donGia;
        this.trangThai = trangThai;
    }

    public SanPhamChiTietModel(String tenSanPham, int soLuong, String tenThuongHieu, String tenMauSac, String tenKichThuoc, double donGia, boolean trangThai) {
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.tenThuongHieu = tenThuongHieu;
        this.tenMauSac = tenMauSac;
        this.tenKichThuoc = tenKichThuoc;
        this.donGia = donGia;
        this.trangThai = trangThai;
    }
    
    

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public String getTenThuongHieu() { return tenThuongHieu; }
    public void setTenThuongHieu(String tenThuongHieu) { this.tenThuongHieu = tenThuongHieu; }

    public String getTenMauSac() { return tenMauSac; }
    public void setTenMauSac(String tenMauSac) { this.tenMauSac = tenMauSac; }

    public String getTenKichThuoc() { return tenKichThuoc; }
    public void setTenKichThuoc(String tenKichThuoc) { this.tenKichThuoc = tenKichThuoc; }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }

    public Object[] toDataRowSPCT() {
        return new Object[]{
            this.id,
            this.tenSanPham,
            this.soLuong,
            this.tenThuongHieu,
            this.tenMauSac,
            this.tenKichThuoc,
            this.donGia,
            this.trangThai ? "Còn Bán" : "Ngừng Bán"
        };
    }

    @Override
    public String toString() {
        return "ChiTietSanPham{" +
                "id=" + id +
                ", tenSanPham='" + tenSanPham + '\'' +
                ", soLuong=" + soLuong +
                ", tenThuongHieu='" + tenThuongHieu + '\'' +
                ", tenMauSac='" + tenMauSac + '\'' +
                ", tenKichThuoc='" + tenKichThuoc + '\'' +
                ", donGia=" + donGia +
                ", trangThai=" + trangThai +
                '}';
    }
}