/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duan1_bangiay.model;

/**
 *
 * @author lenovo
 */
public class ModelThuongHieu {
    private int id;
    private String maTH;
    private String tenTH;
    private boolean trangThai;

    public ModelThuongHieu(int id, String maTH, String tenTH, boolean trangThai) {
        this.id = id;
        this.maTH = maTH;
        this.tenTH = tenTH;
        this.trangThai = trangThai;
    }

    public ModelThuongHieu(String maTH, String tenTH, boolean trangThai) {
        this.maTH = maTH;
        this.tenTH = tenTH;
        this.trangThai = trangThai;
    }

    public ModelThuongHieu(String maTH, String tenTH) {
        this.maTH = maTH;
        this.tenTH = tenTH;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaTH() {
        return maTH;
    }

    public void setMaTH(String maTH) {
        this.maTH = maTH;
    }

    public String getTenTH() {
        return tenTH;
    }

    public void setTenTH(String tenTH) {
        this.tenTH = tenTH;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "ModelThuongHieu{" +
                "id=" + id +
                ", maTH='" + maTH + '\'' +
                ", tenTH='" + tenTH + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
    public Object[] toDataRow() {
        return new Object[]{
            //this.getId(),
            this.getMaTH(),
            this.getTenTH(),
            this.isTrangThai() ? "Hoạt động" : "Ngừng Hoạt Động"
        };
    
}
}
