package duan1_bangiay.model;

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

    public ModelMauSac(String ma, String ten, boolean trangThai) {
        this.ma = ma;
        this.ten = ten;
        this.trangThai = trangThai;
    }

    public ModelMauSac(String ma, String ten) {
        this.ma = ma;
        this.ten = ten;
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
        return "MauSac{"
                + "idMauSac=" + idMauSac
                + ", ma='" + ma + '\''
                + ", ten='" + ten + '\''
                + ", trangThai=" + trangThai
                + '}';
    }

    public Object[] toDataRowMS() {
        return new Object[]{
            this.getMa(), // Trả về mã thay vì ID để phù hợp với bảng giao diện
            this.getTen(),
            this.isTrangThai() ? "Hoạt Động" : "Ngừng Hoạt Động"
        };
    }
}
