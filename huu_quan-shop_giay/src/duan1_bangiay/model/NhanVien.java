/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.model;

public class NhanVien {

    private int id;
    private String maNhanVien;
    private String tenNhanVien;
    private String ngaySinh;
    private String soDienThoai;
    private boolean gioiTinh;
    private String queQuan;
    private String matKhau;
    private boolean trangThai;
    private int idChucVu;
    private String tenChucVu;

    public NhanVien() {
    }

    public NhanVien(int id, String maNhanVien, String tenNhanVien, String ngaySinh, String soDienThoai, boolean gioiTinh, String queQuan, String matKhau, boolean trangThai, int idChucVu, String tenChucVu) {
        this.id = id;
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.gioiTinh = gioiTinh;
        this.queQuan = queQuan;
        this.matKhau = matKhau;
        this.trangThai = trangThai;
        this.idChucVu = idChucVu;
        this.tenChucVu = tenChucVu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getIdChucVu() {
        return idChucVu;
    }

    public void setIdChucVu(int idChucVu) {
        this.idChucVu = idChucVu;
    }

    public String getTenChucVu() {
        return tenChucVu;
    }

    public void setTenChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }

    public Object[] toDataRow() {
        return new Object[]{
            this.getId(),
            this.getMaNhanVien(),
            this.getMatKhau(),
            this.getTenNhanVien(),
            this.isGioiTinh() ? "Nam" : "Nữ",
            this.getNgaySinh(),
            this.getSoDienThoai(),
            this.getTenChucVu(),
            this.isTrangThai() ? "Đang làm" : "Đã nghỉ"
        };
    }

}
