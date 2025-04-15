/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nguye
 */
public class ThongKeTongQuan {
    private double tongDoanhThu; 
    private int tongSanPham; 
    private int tongKhachHang;

    public ThongKeTongQuan() {
    }

    public ThongKeTongQuan(double tongDoanhThu, int tongSanPham, int tongKhachHang) {
        this.tongDoanhThu = tongDoanhThu;
        this.tongSanPham = tongSanPham;
        this.tongKhachHang = tongKhachHang;
    }

    public double getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(double tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }

    public int getTongSanPham() {
        return tongSanPham;
    }

    public void setTongSanPham(int tongSanPham) {
        this.tongSanPham = tongSanPham;
    }

    public int getTongKhachHang() {
        return tongKhachHang;
    }

    public void setTongKhachHang(int tongKhachHang) {
        this.tongKhachHang = tongKhachHang;
    }
     public Object[] toDataRow(){
        return new Object[]{
            this.tongDoanhThu,this.tongSanPham,this.tongKhachHang
        };
    }
}
