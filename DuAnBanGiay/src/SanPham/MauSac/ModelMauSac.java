/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SanPham.MauSac;

/**
 *
 * @author lenovo
 */
public class ModelMauSac {
    private int idMauSac;
    private String ma;
    private String ten;
    private boolean trangThai;

    public ModelMauSac() {
    }

    public ModelMauSac(int idMauSac, String ma, String ten, boolean trangThai) {
        this.idMauSac = idMauSac;
        this.ma = ma;
        this.ten = ten;
        this.trangThai = trangThai;
    }

    

   
    public int getIdMauSac() {
        return idMauSac;
    }

    public void setIdMauSac(int idMauSac) {
        this.idMauSac = idMauSac;
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

    @Override
    public String toString() {
        return "MauSac{" +
                "idMauSac=" + idMauSac +
                ", ma='" + ma + '\'' +
                ", ten='" + ten + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
    public Object[] todataRow(){
        return new Object[]{this.getIdMauSac(),
            this.getMa(),
            this.getTen(),
            this.isTrangThai()?"Hoạt Động":"Ngừng Hoạt Động"};
    }
}
