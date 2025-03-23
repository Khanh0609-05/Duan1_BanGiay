CREATE DATABASE BanHangTaiQuay
GO
USE BanHangTaiQuay
GO




-- 1. ChucVu
CREATE TABLE ChucVu (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MaChucVu NVARCHAR(50) NOT NULL UNIQUE,
    TenChucVu NVARCHAR(255) NOT NULL
);
GO

-- 2. NhanVien
CREATE TABLE NhanVien (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MaNhanVien NVARCHAR(50) NOT NULL UNIQUE,
    TenNhanVien NVARCHAR(255),
    NgaySinh DATETIME,
    SDT VARCHAR(20) CHECK (SDT LIKE '[0-9]%'),
    GioiTinh NVARCHAR(10),
    QueQuan NVARCHAR(255),
    MatKhau NVARCHAR(255),
    TrangThai BIT DEFAULT 1 CHECK (TrangThai IN (0,1)),
    IDChucVu INT FOREIGN KEY REFERENCES ChucVu(ID)
);
GO

-- 3. KhachHang
CREATE TABLE KhachHang (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    TenKhachHang NVARCHAR(255),
	DiaChi NVARCHAR(255),
    SDT NVARCHAR(15),
    GioiTinh NVARCHAR(10),
    NgaySinh DATETIME
);
GO

-- 4. ThuongHieu
CREATE TABLE ThuongHieu (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MaTH NVARCHAR(50) NOT NULL UNIQUE,
    TenTH NVARCHAR(255) NOT NULL,
    TrangThai BIT DEFAULT 1 CHECK (TrangThai IN (0,1))
);
GO

-- 5. MauSac
CREATE TABLE MauSac (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MaMS NVARCHAR(50) NOT NULL UNIQUE,
    TenMS NVARCHAR(255) NOT NULL,
    TrangThai BIT DEFAULT 1 CHECK (TrangThai IN (0,1))
);
GO

-- 6. KichThuoc
CREATE TABLE KichThuoc (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MaKT NVARCHAR(50) NOT NULL UNIQUE,
    TenKT NVARCHAR(255) NOT NULL,
    TrangThai BIT DEFAULT 1 CHECK (TrangThai IN (0,1))
);
GO

-- 7. ChiTietSanPham
CREATE TABLE ChiTietSanPham (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    IDThuongHieu INT FOREIGN KEY REFERENCES ThuongHieu(ID),
    IDMauSac INT FOREIGN KEY REFERENCES MauSac(ID),
    IDKichThuoc INT FOREIGN KEY REFERENCES KichThuoc(ID),
    SoLuong INT DEFAULT 0 CHECK (SoLuong >= 0),
    DonGia DECIMAL(10, 2) CHECK (DonGia > 0),
    TrangThai BIT DEFAULT 1 CHECK (TrangThai IN (0,1))
);
GO

-- 8. SanPham
CREATE TABLE SanPham (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MaSanPham NVARCHAR(50) NOT NULL UNIQUE,
    TenSanPham NVARCHAR(255) NOT NULL,
    HinhAnh NVARCHAR(255),
    IDChiTietSanPham INT FOREIGN KEY REFERENCES ChiTietSanPham(ID)
);
GO

-- 9. PhieuGiamGia
CREATE TABLE PhieuGiamGia (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MaPhieuGiamGia NVARCHAR(50) NOT NULL UNIQUE,
    TenPhieuGiamGia NVARCHAR(255),
    SoLuong INT DEFAULT 0 CHECK (SoLuong >= 0),
	kieuGiam CHAR(3) CHECK (kieuGiam IN ('VND', '%')) NOT NULL,
    mucGiam DECIMAL(10,2) NOT NULL,
    hoaDonToiThieu DECIMAL(10,2) NOT NULL ,
    NgayBatDau DATETIME NOT NULL,
    NgayKetThuc DATETIME NOT NULL,
    TrangThai BIT DEFAULT 1 CHECK (TrangThai IN (0,1)),
    CONSTRAINT CK_PGG_Ngay CHECK (NgayBatDau < NgayKetThuc)
);
GO

-- 10. HoaDon
CREATE TABLE HoaDon (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    IDNhanVien INT FOREIGN KEY REFERENCES NhanVien(ID),
    IDKhachHang INT FOREIGN KEY REFERENCES KhachHang(ID),
    IDPhieuGiamGia INT FOREIGN KEY REFERENCES PhieuGiamGia(ID),
    TongTien DECIMAL(10, 2) CHECK (TongTien > 0),
    GiamGia DECIMAL(10, 2) CHECK (GiamGia >= 0),
    NgayTao DATETIME DEFAULT GETDATE(),
    ThanhTien DECIMAL(10, 2) CHECK (ThanhTien > 0),
    TrangThai BIT DEFAULT 1 CHECK (TrangThai IN (0,1))
);
GO

-- 11. ChiTietHoaDon
CREATE TABLE ChiTietHoaDon (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    IDSanPham INT FOREIGN KEY REFERENCES SanPham(ID),
    IDHoaDon INT FOREIGN KEY REFERENCES HoaDon(ID),
    SoLuong INT CHECK (SoLuong > 0),
    DonGia DECIMAL(10, 2) CHECK (DonGia > 0),
    TrangThai BIT DEFAULT 1 CHECK (TrangThai IN (0,1))
);

GO



-- 1. ChucVu (Chức vụ)
INSERT INTO ChucVu (MaChucVu, TenChucVu) VALUES
('CV001', N'Quản lý'),
('CV002', N'Nhân viên '),
('CV003', N'Nhân viên'),
('CV004', N'Nhân viên'),
('CV005', N'Quản lý'),
('CV006', N'Nhân viên'),
('CV007', N'Nhân viên'),
('CV008', N'Nhân viên'),
('CV009', N'Nhân viên'),
('CV010', N'Nhân viên');

-- 2. NhanVien (Nhân viên)
INSERT INTO NhanVien (MaNhanVien, TenNhanVien, NgaySinh, SDT, GioiTinh, QueQuan, MatKhau, TrangThai, IDChucVu) VALUES
('NV001', N'Nguyễn Văn An', '1990-01-15', '0987654321', N'Nam', N'Hà Nội', N'123456', 1, 1),
('NV002', N'Trần Thị Ba', '1992-02-20', '0912345678', N'Nữ', N'Hồ Chí Minh', N'abcdef', 1, 2),
('NV003', N'Lê Văn Luyện', '1988-03-25', '0934567890', N'Nam', N'Đà Nẵng', N'qwerty', 1, 3),
('NV004', N'Phạm Thị Dương', '1995-04-10', '0967890123', N'Nữ', N'Hải Phòng', N'123abc', 1, 4),
('NV005', N'Hoàng Văn Toàn', '1986-05-05', '0945678901', N'Nam', N'Cần Thơ', N'pass123', 1, 5),
('NV006', N'Võ Thị Sáu', '1993-06-30', '0978901234', N'Nữ', N'Nghệ An', N'789xyz', 1, 6),
('NV007', N'Hồ Văn Giang', '1987-07-15', '0911122233', N'Nam', N'Huế', N'456pass', 1, 7),
('NV008', N'Đinh Thị Hoa', '1991-08-25', '0922233445', N'Nữ', N'Hà Nam', N'abc123', 1, 8),
('NV009', N'Ngô Văn Tín', '1994-09-10', '0933344556', N'Nam', N'Lào Cai', N'pass789', 1, 9),
('NV010', N'Bùi Thị Mai', '1989-10-20', '0944455667', N'Nữ', N'Bắc Ninh', N'xyz456', 1, 10);

-- 3. KhachHang (Khách hàng)
INSERT INTO KhachHang (TenKhachHang, DiaChi, SDT, GioiTinh, NgaySinh)
VALUES
(N'Nguyễn Văn An', N'123 Đường Lê Lợi, Quận 1, TP.HCM', '0987654321', N'Nam', '1995-06-15'),
(N'Trần Thị Bích', N'456 Đường Hai Bà Trưng, Quận 3, TP.HCM', '0978123456', N'Nữ', '1998-09-22'),
(N'Lê Hữu Minh', N'789 Đường Nguyễn Huệ, Quận 1, TP.HCM', '0965111222', N'Nam', '1992-03-10'),
(N'Phạm Hồng Nhung', N'321 Đường Lý Tự Trọng, Quận 5, TP.HCM', '0902333444', N'Nữ', '2000-07-18'),
(N'Hoàng Văn Tuấn', N'654 Đường Nguyễn Trãi, Quận 10, TP.HCM', '0911444555', N'Nam', '1988-12-05'),
(N'Bùi Thanh Tâm', N'987 Đường Cách Mạng Tháng 8, Quận 3, TP.HCM', '0922666777', N'Nam', '1997-05-20'),
(N'Đỗ Mỹ Linh', N'159 Đường Phạm Ngũ Lão, Quận 1, TP.HCM', '0933555666', N'Nữ', '1994-10-08'),
(N'Võ Thị Lan', N'753 Đường Trần Phú, Quận 7, TP.HCM', '0944666888', N'Nữ', '1999-02-28'),
(N'Ngô Quang Dũng', N'258 Đường Nguyễn Văn Cừ, Quận 5, TP.HCM', '0955777999', N'Nam', '1990-11-30'),
(N'Phan Hải Nam', N'357 Đường Hoàng Văn Thụ, Quận Phú Nhuận, TP.HCM', '0966888111', N'Nam', '1985-08-25');

-- 4. ThuongHieu (Thương hiệu)
INSERT INTO ThuongHieu (MaTH, TenTH, TrangThai) VALUES
('TH001', N'Nike', 1),
('TH002', N'Adidas', 1),
('TH003', N'Puma', 1),
('TH004', N'Reebok', 1),
('TH005', N'Vans', 1),
('TH006', N'Converse', 1),
('TH007', N'New Balance', 1),
('TH008', N'Jordan', 1),
('TH009', N'Under Armour', 1),
('TH010', N'Skechers', 1);

-- 5. MauSac (Màu sắc)
INSERT INTO MauSac (MaMS, TenMS, TrangThai) VALUES
('MS001', N'Đen', 1),
('MS002', N'Trắng', 1),
('MS003', N'Xanh Dương', 1),
('MS004', N'Xanh Lá', 1),
('MS005', N'Đỏ', 1),
('MS006', N'Vàng', 1),
('MS007', N'Nâu', 1),
('MS008', N'Hồng', 1),
('MS009', N'Tím', 1),
('MS010', N'Xám', 1);

-- 6. KichThuoc (Kích thước)
INSERT INTO KichThuoc (MaKT, TenKT, TrangThai) VALUES
('KT001', N'36', 1),
('KT002', N'37', 1),
('KT003', N'38', 1),
('KT004', N'39', 1),
('KT005', N'40', 1),
('KT006', N'41', 1),
('KT007', N'42', 1),
('KT008', N'43', 1),
('KT009', N'44', 1),
('KT010', N'45', 1);

-- 7. PhieuGiamGia (Phiếu giảm giá)
INSERT INTO PhieuGiamGia (MaPhieuGiamGia, TenPhieuGiamGia, SoLuong, kieuGiam, mucGiam, hoaDonToiThieu, NgayBatDau, NgayKetThuc, TrangThai)
VALUES
('PGG001', 'Giảm 50K cho đơn từ 500K', 100, 'VND', 50000, 500000, '2025-04-01', '2025-04-30', 1),
('PGG002', 'Giảm 10% tối đa 100K', 200, '%', 10, 500000, '2025-04-01', '2025-04-30', 1),
('PGG003', 'Giảm 30K cho đơn từ 300K', 150, 'VND', 30000, 300000, '2025-03-25', '2025-04-15', 1),
('PGG004', 'Giảm 20% tối đa 200K', 100, '%', 20, 1000000, '2025-04-05', '2025-04-25', 1),
('PGG005', 'Flash Sale - Giảm 70K', 50, 'VND', 70000, 700000, '2025-03-28', '2025-04-02', 1),
('PGG006', 'Giảm 15% tối đa 150K', 120, '%', 15, 750000, '2025-04-10', '2025-04-30', 1),
('PGG007', 'Khuyến mãi sinh nhật - 100K', 80, 'VND', 100000, 1000000, '2025-04-01', '2025-04-07', 1),
('PGG008', 'Mua nhiều giảm nhiều - 25%', 60, '%', 25, 2000000, '2025-04-15', '2025-05-01', 1),
('PGG009', 'Giảm 5% cho đơn từ 300K', 90, '%', 5, 300000, '2025-04-01', '2025-04-20', 1),
('PGG010', 'Voucher tháng 4 - 40K', 110, 'VND', 40000, 400000, '2025-04-01', '2025-04-30', 1);

-- 8. ChiTietSanPham
INSERT INTO ChiTietSanPham (IDThuongHieu, IDMauSac, IDKichThuoc, SoLuong, DonGia, TrangThai) VALUES
(1, 1, 1, 50, 1000000, 1),
(2, 2, 2, 40, 1200000, 1),
(3, 3, 3, 30, 1100000, 1),
(4, 4, 4, 20, 900000, 1),
(5, 5, 5, 25, 1300000, 1),
(6, 6, 6, 15, 800000, 1),
(7, 7, 7, 35, 1400000, 1),
(8, 8, 8, 10, 1500000, 1),
(9, 9, 9, 5, 1600000, 1),
(10, 10, 10, 45, 700000, 1);

-- 9. SanPham
INSERT INTO SanPham (MaSanPham, TenSanPham, HinhAnh, IDChiTietSanPham) VALUES
('SP001', N'Jordan 1', N'jordan_1.jpg', 8),
('SP002', N'Under Armour HOVR', N'under_armour_hovr.jpg', 9),
('SP003', N'Puma RS-X', N'puma_rsx.jpg', 3),
('SP004', N'Reebok Classic', N'reebok_classic.jpg', 4),
('SP005', N'Vans Old Skool', N'vans_old_skool.jpg', 5),
('SP006', N'Converse Chuck Taylor', N'converse_chuck_taylor.jpg', 6),
('SP007', N'New Balance 574', N'new_balance_574.jpg', 7),
('SP008', N'Jordan 1', N'jordan_1.jpg', 8),
('SP009', N'Under Armour HOVR', N'under_armour_hovr.jpg', 9),
('SP010', N'Skechers D-Lite', N'skechers_dlite.jpg', 10);

-- 10. HoaDon
INSERT INTO HoaDon (IDNhanVien, IDKhachHang, IDPhieuGiamGia, TongTien, GiamGia, NgayTao, ThanhTien, TrangThai) VALUES
(1, 1, 1, 1700000, 85000, '2025-03-06', 1615000, 1),
(2, 2, 2, 2900000, 1450000, '2025-03-07', 1450000, 1),
(3, 3, 3, 2500000, 375000, '2025-03-03', 2125000, 1),
(4, 4, 4, 1800000, 540000, '2025-03-04', 1260000, 1),
(5, 5, 5, 2200000, 550000, '2025-03-05', 1650000, 1),
(6, 6, 6, 1700000, 85000, '2025-03-06', 1615000, 1),
(7, 7, 7, 2900000, 1450000, '2025-03-07', 1450000, 1),
(8, 8, 8, 3200000, 1280000, '2025-03-08', 1920000, 1),
(9, 9, 9, 2400000, 840000, '2025-03-09', 1560000, 1),
(10, 10, 10, 2000000, 400000, '2025-03-10', 1600000, 1);

-- 11. ChiTietHoaDon
INSERT INTO ChiTietHoaDon (IDSanPham, IDHoaDon, SoLuong, DonGia, TrangThai) VALUES
(1, 1, 1, 1500000, 1),
(2, 2, 2, 1600000, 1),
(3, 3, 1, 1100000, 1),
(4, 4, 2, 900000, 1),
(5, 5, 1, 1300000, 1),
(6, 6, 1, 800000, 1),
(7, 7, 3, 1400000, 1),
(8, 8, 1, 1500000, 1),
(9, 9, 2, 1600000, 1),
(10, 10, 2, 700000, 1);






SELECT * FROM ThuongHieu;
SELECT * FROM MauSac;
SELECT * FROM KichThuoc;
SELECT * FROM ChucVu;
SELECT * FROM PhieuGiamGia;
SELECT * FROM ChiTietSanPham;
SELECT * FROM SanPham;
SELECT * FROM HoaDon;
SELECT * FROM ChiTietHoaDon;
SELECT * FROM KhachHang;
SELECT * FROM NhanVien;



