/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SanPham.KichThuoc;

/**
 *
 * @author lenovo
 */
public class ModelKichThuoc {
    private int id;
    private String ma;
    private String ten;
    private boolean trangThai;

    public ModelKichThuoc() {}

    public ModelKichThuoc(int id, String ma, String ten, boolean trangThai) {
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

    public Object[] toDataRow() {
        return new Object[]{
            id,
            ma,
            ten,
            trangThai ? "Hoạt động" : "Ngừng hoạt động"
        };
    }

    @Override
    public String toString() {
        return "KichThuoc{" +
                "id=" + id +
                ", ma='" + ma + '\'' +
                ", ten='" + ten + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}
