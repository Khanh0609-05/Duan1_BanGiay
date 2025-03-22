/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SanPham.ThuongHieu;

/**
 *
 * @author lenovo
 */
public class ModelThuongHieu {
     private int id;
    private String ma;
    private String ten;
    private boolean trangThai;

    public ModelThuongHieu() {}

    public ModelThuongHieu(int id, String ma, String ten, boolean trangThai) {
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public ModelThuongHieu(String ma, String ten, boolean trangThai) {
        this.ma = ma;
        this.ten = ten;
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "ThuongHieu{" +
                "id=" + id +
                ", ma='" + ma + '\'' +
                ", ten='" + ten + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
    public Object[] todataRow(){
        return new Object[]{this.getId(),
            this.getMa(),
            this.getTen(),
            this.isTrangThai()?"Hoạt động":"Ngừng Hoạt Động"};
            
    }
    
}
