/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.model;

/**
 *
 * @author admin
 */
public class KhachHang {
    private int id; // ID tự sinh từ cơ sở dữ liệu
    private String maKhachHang; // Mã khách hàng, có thể để trống khi thêm mới, cơ sở dữ liệu tự sinh
    private String tenKhachHang;
    private String diaChi;
    private String sdt;
    private boolean gioiTinh;

    public KhachHang() {
    }

    // Constructor khi thêm mới khách hàng (không cần mã khách hàng, cơ sở dữ liệu sẽ tự sinh mã)
    public KhachHang(String tenKhachHang, String diaChi, String sdt, boolean gioiTinh) {
        this.tenKhachHang = tenKhachHang;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
    }

    // Constructor khi lấy thông tin khách hàng từ DB (có mã khách hàng)
    public KhachHang(int id, String maKhachHang, String tenKhachHang, String diaChi, String sdt, boolean gioiTinh) {
        this.id = id;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    
    public String getGioiTinhString() {
        return gioiTinh ? "Nam" : "Nữ";
    }
}

    
    
    
    
    

