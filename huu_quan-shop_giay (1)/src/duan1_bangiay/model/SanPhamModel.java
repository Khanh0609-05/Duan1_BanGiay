/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.model;

/**
 *
 * @author lenovo
 */
public class SanPhamModel {
    private int idSanPham;
    private String ma;
    private String ten;
    private String moTa;
    private double giaBan;
    private int idThuongHieu;
    private boolean trangThai;

    public SanPhamModel() {}

    public SanPhamModel(int idSanPham, String ma, String ten, String moTa, double giaBan, int idThuongHieu, boolean trangThai) {
        this.idSanPham = idSanPham;
        this.ma = ma;
        this.ten = ten;
        this.moTa = moTa;
        this.giaBan = giaBan;
        this.idThuongHieu = idThuongHieu;
        this.trangThai = trangThai;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getIdThuongHieu() {
        return idThuongHieu;
    }

    public void setIdThuongHieu(int idThuongHieu) {
        this.idThuongHieu = idThuongHieu;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "SanPham{" +
                "idSanPham=" + idSanPham +
                ", ma='" + ma + '\'' +
                ", ten='" + ten + '\'' +
                ", moTa='" + moTa + '\'' +
                ", giaBan=" + giaBan +
                ", idThuongHieu=" + idThuongHieu +
                ", trangThai=" + trangThai +
                '}';
    }
    public Object[] toDataRow() {
        return new Object[]{
            idSanPham,
            ma,
            ten,
            moTa,
            giaBan,
            idThuongHieu,
            trangThai ? "Hoạt động" : "Ngừng hoạt động"
        };
}
}
