package SanPham.Model;

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

    public ModelKichThuoc(String ma, String ten, boolean trangThai) {
        this.ma = ma;
        this.ten = ten;
        this.trangThai = trangThai;
    }

    public ModelKichThuoc(String ma, String ten) {
        this.ma = ma;
        this.ten = ten;
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

    public Object[] toDataRowKT() {
        return new Object[]{
            this.ma, // Chỉ hiển thị MaKT
            this.ten, // Chỉ hiển thị TenKT
            this.trangThai ? "Hoạt động" : "Ngừng hoạt động"
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