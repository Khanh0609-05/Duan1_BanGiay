/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nguye
 */
public class ThongKe {
    private String maSanPham;
    private String tenSanPham;
    private String hang;
    private String mauSac;
    private String size;
    private int soLuong;
    private double doanhThu;


    public ThongKe() {
    }

    public ThongKe(String maSanPham, String tenSanPham, String hang, String mauSac, String size, int soLuong, double doanhThu) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.hang = hang;
        this.mauSac = mauSac;
        this.size = size;
        this.soLuong = soLuong;
        this.doanhThu = doanhThu;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(double doanhThu) {
        this.doanhThu = doanhThu;
    }

    
  
    public Object[] toDataRow(){
        return new Object[]{
            this.maSanPham,this.tenSanPham,this.hang,this.mauSac,this.size,this.soLuong,this.soLuong
    };
                }
}
