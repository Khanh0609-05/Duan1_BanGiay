
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'BanHangTaiQuay')
BEGIN
    DROP DATABASE BanHangTaiQuay
END
GO


CREATE DATABASE BanHangTaiQuay
GO

USE BanHangTaiQuay
GO

CREATE TABLE ChucVu (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MaChucVu NVARCHAR(50) NOT NULL UNIQUE,
    TenChucVu NVARCHAR(255) NOT NULL
);
GO

CREATE TABLE NhanVien (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MaNhanVien NVARCHAR(50) UNIQUE,
    TenNhanVien NVARCHAR(255),
    NgaySinh DATETIME,
    SDT VARCHAR(20) CHECK (SDT LIKE '[0-9]%') UNIQUE,
    GioiTinh BIT DEFAULT 1,
    QueQuan NVARCHAR(255),
    MatKhau NVARCHAR(255),
    TrangThai BIT DEFAULT 1 CHECK (TrangThai IN (0,1)), 
    IDChucVu INT FOREIGN KEY REFERENCES ChucVu(ID)
);
GO

CREATE TABLE KhachHang (
    ID INT IDENTITY(1,1) PRIMARY KEY,
	MaKhachHang NVARCHAR(50) UNIQUE,
    TenKhachHang NVARCHAR(255) ,
	DiaChi NVARCHAR(255),
    SDT VARCHAR(20) UNIQUE,
    GioiTinh BIT DEFAULT 1,
    NgaySinh DATETIME
);
GO

CREATE TABLE ThuongHieu (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MaTH NVARCHAR(50) NOT NULL UNIQUE,
    TenTH NVARCHAR(255) NOT NULL UNIQUE,
    TrangThai BIT DEFAULT 1 CHECK (TrangThai IN (0,1))
);
GO

CREATE TABLE MauSac (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MaMS NVARCHAR(50) NOT NULL UNIQUE,
    TenMS NVARCHAR(255) NOT NULL UNIQUE,
    TrangThai BIT DEFAULT 1 CHECK (TrangThai IN (0,1))
);
GO



CREATE TABLE KichThuoc (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MaKT NVARCHAR(50) NOT NULL UNIQUE,
    TenKT NVARCHAR(255) NOT NULL UNIQUE,
    TrangThai BIT DEFAULT 1 CHECK (TrangThai IN (0,1))
);
GO





CREATE TABLE ChiTietSanPham (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    IDThuongHieu INT FOREIGN KEY REFERENCES ThuongHieu(ID),
    IDMauSac INT FOREIGN KEY REFERENCES MauSac(ID),
    IDKichThuoc INT FOREIGN KEY REFERENCES KichThuoc(ID),
    SoLuong INT DEFAULT 0 CHECK (SoLuong >= 0),
    DonGia DECIMAL(18, 2) CHECK (DonGia > 0),
    TrangThai BIT DEFAULT 1 CHECK (TrangThai IN (0,1))
);
GO


CREATE TABLE SanPham (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MaSanPham NVARCHAR(50) UNIQUE,
    TenSanPham NVARCHAR(255) NOT NULL UNIQUE,
    HinhAnh NVARCHAR(255),
    IDChiTietSanPham INT FOREIGN KEY REFERENCES ChiTietSanPham(ID)
);
GO




	CREATE TABLE PhieuGiamGia (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MaPhieuGiamGia NVARCHAR(50) NULL,
    TenPhieuGiamGia NVARCHAR(255) NULL,
    kieuGiam BIT NULL,
    mucGiam DECIMAL(18, 2) NOT NULL,
    mucGiamToiDa DECIMAL(18, 2) NULL,
    hoaDonToiThieu DECIMAL(10, 2) NOT NULL,
    NgayBatDau DATE NOT NULL,
    NgayKetThuc DATE NOT NULL,
    SoLuong INT NULL,
    DaDung INT NULL,
    TrangThai BIT NULL,
    CONSTRAINT CK_PGG_Ngay CHECK (NgayBatDau < NgayKetThuc)

);
GO


CREATE TABLE HoaDon (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    IDNhanVien INT FOREIGN KEY REFERENCES NhanVien(ID),
    IDKhachHang INT FOREIGN KEY REFERENCES KhachHang(ID),
    IDPhieuGiamGia INT FOREIGN KEY REFERENCES PhieuGiamGia(ID),
	MaHoaDon NVARCHAR(50) UNIQUE,
    TongTien DECIMAL(18, 2) CHECK (TongTien >= 0),
    GiamGia DECIMAL(10, 2) CHECK (GiamGia >= 0),
    NgayTao DATETIME DEFAULT GETDATE(),
    ThanhTien DECIMAL(18, 2) CHECK (ThanhTien > 0),
    TrangThai BIT DEFAULT 1 CHECK (TrangThai IN (0,1))
);
GO



CREATE TABLE ChiTietHoaDon (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    IDSanPham INT FOREIGN KEY REFERENCES SanPham(ID),
    IDHoaDon INT FOREIGN KEY REFERENCES HoaDon(ID),
    SoLuong INT CHECK (SoLuong > 0),
    DonGia DECIMAL(18, 2) CHECK (DonGia > 0),
    TrangThai BIT DEFAULT 1 CHECK (TrangThai IN (0,1))
);

GO

INSERT INTO ChucVu (MaChucVu, TenChucVu) VALUES
('CV001', N'Quản lý'),
('CV002', N'Nhân viên');

go

INSERT INTO NhanVien (MaNhanVien, TenNhanVien, NgaySinh, SDT, GioiTinh, QueQuan, MatKhau, TrangThai, IDChucVu) VALUES
('NV001', N'Nguyễn Văn Hùng', '1990-05-15', '0901234561', 1, N'Hà Nội', 'pass123', 1, 1),
('NV002', N'Trần Thị Mai', '1995-08-20', '0912345672', 0, N'Hồ Chí Minh', 'pass456', 1, 1),
('NV003', N'Lê Hoàng Nam', '1988-03-10', '0923456783', 1, N'Đà Nẵng', 'pass789', 1, 2),
('NV004', N'Phạm Thị Lan', '1997-12-25', '0934567894', 0, N'Hải Phòng', 'pass101', 1, 2),
('NV005', N'Hoàng Văn Tuấn', '1992-07-30', '0945678905', 1, N'Quảng Ninh', 'pass202', 1, 2),
('NV006', N'Ngô Thị Hồng', '1993-09-15', '0956789016', 0, N'Cần Thơ', 'pass303', 1, 2),
('NV007', N'Vũ Văn Long', '1985-11-05', '0967890127', 1, N'Hà Tĩnh', 'pass404', 1, 2),
('NV008', N'Đỗ Thị Thu', '1996-01-20', '0978901238', 0, N'Nghệ An', 'pass505', 1, 2),
('NV009', N'Bùi Văn Đức', '1991-04-12', '0989012349', 1, N'Thanh Hóa', 'pass606', 1, 2),
('NV010', N'Lý Thị Hoa', '1994-06-18', '0990123450', 0, N'Bình Dương', 'pass707', 1, 2),
('NV011', N'Tạ Văn Phong', '1989-02-28', '0901234501', 1, N'Hà Nam', 'pass808', 1, 2),
('NV012', N'Nguyễn Thị Ngọc', '1998-10-10', '0912345612', 0, N'Đồng Nai', 'pass909', 1, 2),
('NV013', N'Trần Văn Bình', '1990-12-01', '0923456723', 1, N'Vũng Tàu', 'pass010', 1, 2),
('NV014', N'Lê Thị Hạnh', '1995-03-15', '0934567834', 0, N'Quảng Nam', 'pass111', 1, 2),
('NV015', N'Phạm Văn Khoa', '1987-08-25', '0945678945', 1, N'Bắc Giang', 'pass212', 1, 2),
('NV016', N'Hoàng Thị Linh', '1996-05-05', '0956789056', 0, N'Tiền Giang', 'pass313', 1, 2),
('NV017', N'Ngô Văn Anh', '1993-07-20', '0967890167', 1, N'Phú Thọ', 'pass414', 1, 2),
('NV018', N'Vũ Thị Yến', '1992-09-10', '0978901278', 0, N'Hưng Yên', 'pass515', 1, 2),
('NV019', N'Đỗ Văn Khôi', '1986-11-15', '0989012389', 1, N'Lào Cai', 'pass616', 1, 2),
('NV020', N'Bùi Thị Thảo', '1997-01-25', '0990123490', 0, N'Tuyên Quang', 'pass717', 1, 2);
GO

INSERT INTO KhachHang (MaKhachHang, TenKhachHang, DiaChi, SDT, GioiTinh, NgaySinh) VALUES
('KH001', N'Nguyễn Thị Hương', N'123 Lê Lợi, Hà Nội', '0901234561', 0, '1990-06-10'),
('KH002', N'Trần Văn Dũng', N'45 Nguyễn Huệ, TP.HCM', '0912345672', 1, '1985-08-15'),
('KH003', N'Lê Thị Thúy', N'78 Trần Phú, Đà Nẵng', '0923456783', 0, '1992-03-20'),
('KH004', N'Phạm Văn Tâm', N'12 Hùng Vương, Hải Phòng', '0934567894', 1, '1988-11-25'),
('KH005', N'Hoàng Thị Nga', N'56 Lý Thường Kiệt, Quảng Ninh', '0945678905', 0, '1995-07-30'),
('KH006', N'Ngô Văn Hậu', N'89 Nguyễn Trãi, Cần Thơ', '0956789016', 1, '1991-09-05'),
('KH007', N'Vũ Thị Kim', N'34 Phạm Ngũ Lão, Hà Tĩnh', '0967890127', 0, '1987-12-10'),
('KH008', N'Đỗ Văn Hòa', N'67 Bạch Đằng, Nghệ An', '0978901238', 1, '1993-04-15'),
('KH009', N'Bùi Thị Minh', N'90 Trần Hưng Đạo, Thanh Hóa', '0989012349', 0, '1996-01-20'),
('KH010', N'Lý Văn Quang', N'23 Lê Đại Hành, Bình Dương', '0990123450', 1, '1989-06-25'),
('KH011', N'Tạ Thị Loan', N'45 Nguyễn Văn Cừ, Hà Nam', '0901234501', 0, '1994-08-30'),
('KH012', N'Nguyễn Văn Tài', N'78 Hoàng Diệu, Đồng Nai', '0912345612', 1, '1990-10-05'),
('KH013', N'Trần Thị Oanh', N'12 Pasteur, Vũng Tàu', '0923456723', 0, '1986-02-10'),
('KH014', N'Lê Văn Sơn', N'56 Điện Biên Phủ, Quảng Nam', '0934567834', 1, '1992-05-15'),
('KH015', N'Phạm Thị Tuyết', N'89 Lê Hồng Phong, Bắc Giang', '0945678945', 0, '1997-07-20'),
('KH016', N'Hoàng Văn Thắng', N'34 Nguyễn Đình Chiểu, Tiền Giang', '0956789056', 1, '1988-09-25'),
('KH017', N'Ngô Thị Nhung', N'67 Lý Tự Trọng, Phú Thọ', '0967890167', 0, '1993-11-30'),
('KH018', N'Vũ Văn Phúc', N'90 Hai Bà Trưng, Hưng Yên', '0978901278', 1, '1991-01-05'),
('KH019', N'Đỗ Thị Hà', N'23 Trần Quốc Toản, Lào Cai', '0989012389', 0, '1995-03-10'),
('KH020', N'Bùi Văn Lợi', N'45 Nguyễn Thái Học, Tuyên Quang', '0990123490', 1, '1987-06-15');
GO

INSERT INTO ThuongHieu (MaTH, TenTH, TrangThai) VALUES
('TH001', N'Nike', 1),
('TH002', N'Adidas', 1),
('TH003', N'Puma', 1),
('TH004', N'Gucci', 1),
('TH005', N'Levis', 1),
('TH006', N'Zara', 1),
('TH007', N'H&M', 1),
('TH008', N'Uniqlo', 1),
('TH009', N'Converse', 1),
('TH010', N'Vans', 1),
('TH011', N'Balenciaga', 1),
('TH012', N'Tommy Hilfiger', 1),
('TH013', N'Under Armour', 1),
('TH014', N'Reebok', 1),
('TH015', N'New Balance', 1),
('TH016', N'Asics', 1),
('TH017', N'Fila', 1),
('TH018', N'Champion', 1),
('TH019', N'Supreme', 1),
('TH020', N'North Face', 1);


GO
INSERT INTO MauSac (MaMS, TenMS, TrangThai) VALUES
('MS001', N'Đen', 1),
('MS002', N'Trắng', 1),
('MS003', N'Đỏ', 1),
('MS004', N'Xanh dương', 1),
('MS005', N'Vàng', 1),
('MS006', N'Xám', 1),
('MS007', N'Hồng', 1),
('MS008', N'Tím', 1),
('MS009', N'Xanh lá', 1),
('MS010', N'Cam', 1),
('MS011', N'Nâu', 1),
('MS012', N'Bạc', 1),
('MS013', N'Vàng đồng', 1),
('MS014', N'Xanh ngọc', 1),
('MS015', N'Hồng phấn', 1),
('MS016', N'Xanh navy', 1),
('MS017', N'Đỏ đun', 1),
('MS018', N'Trắng ngọc trai', 1),
('MS019', N'Xám tro', 1),
('MS020', N'Be', 1);
GO
INSERT INTO KichThuoc (MaKT, TenKT, TrangThai) VALUES
('KT001', N'S', 1),
('KT002', N'M', 1),
('KT003', N'L', 1),
('KT004', N'XL', 1),
('KT005', N'XXL', 1),
('KT006', N'XS', 1),
('KT007', N'28', 1),
('KT008', N'29', 1),
('KT009', N'30', 1),
('KT010', N'31', 1),
('KT011', N'32', 1),
('KT012', N'33', 1),
('KT013', N'34', 1),
('KT014', N'35', 1),
('KT015', N'36', 1),
('KT016', N'37', 1),
('KT017', N'38', 1),
('KT018', N'39', 1),
('KT019', N'40', 1),
('KT020', N'41', 1);
GO

INSERT INTO ChiTietSanPham (IDThuongHieu, IDMauSac, IDKichThuoc, SoLuong, DonGia, TrangThai) VALUES
(1, 1, 1, 50000, 500000.00, 1),
(2, 2, 2, 30, 600000.00, 1),
(3, 3, 3, 20, 450000.00, 1),
(4, 4, 4, 15, 2000000.00, 1),
(5, 5, 5, 40, 350000.00, 1),
(6, 6, 6, 25, 300000.00, 1),
(7, 7, 7, 60, 250000.00, 1),
(8, 8, 8, 35, 200000.00, 1),
(9, 9, 9, 45, 400000.00, 1),
(10, 10, 10, 10, 550000.00, 1),
(11, 11, 11, 20, 1500000.00, 1),
(12, 12, 12, 30, 700000.00, 1),
(13, 13, 13, 25, 450000.00, 1),
(14, 14, 14, 15, 500000.00, 1),
(15, 15, 15, 50, 600000.00, 1),
(16, 16, 16, 40, 350000.00, 1),
(17, 17, 17, 30, 300000.00, 1),
(18, 18, 18, 20, 250000.00, 1),
(19, 19, 19, 10, 800000.00, 1),
(20, 20, 20, 25, 900000.00, 1);
GO
INSERT INTO SanPham (MaSanPham, TenSanPham, HinhAnh, IDChiTietSanPham) VALUES
('SP001', N'Giày Nike Air Max', N'nike_air_max.jpg', 1),
('SP002', N'Giày Adidas ', N'adidas_polo.jpg', 2),
('SP003', N'Giày Puma ', N'puma_jogger.jpg', 3),
('SP004', N'Giày Gucci Tote', N'gucci_tote.jpg', 4),
('SP005', N'Giày Jeans Levis', N'levis_jeans.jpg', 5),
('SP006', N'Giày Zara Basic', N'zara_basic.jpg', 6),
('SP007', N'Giày H&M Midi', N'hm_midi.jpg', 7),
('SP008', N'Giày Uniqlo Flannel', N'uniqlo_flannel.jpg', 8),
('SP009', N'Giày Converse Chuck', N'converse_chuck.jpg', 9),
('SP010', N'Giày Vans Old Skool', N'vans_oldskool.jpg', 10),
('SP011', N'Giày Balenciaga Triple S', N'balenciaga_triple.jpg', 11),
('SP012', N'Giày Tommy Hilfiger', N'tommy_hilfiger.jpg', 12),
('SP013', N'Giày Under Armour', N'under_armour.jpg', 13),
('SP014', N'Giày Reebok Classic', N'reebok_classic.jpg', 14),
('SP015', N'Giày New Balance', N'new_balance_574.jpg', 15),
('SP016', N'Giày Asics Gel', N'asics_gel.jpg', 16),
('SP017', N'Giày Fila Sport', N'fila_sport.jpg', 17),
('SP018', N'Giày Champion ', N'champion_hoodie.jpg', 18),
('SP019', N'Giày Supreme Logo', N'supreme_logo.jpg', 19),
('SP020', N'Giày North Face ', N'north_face_jacket.jpg', 20);
GO
INSERT INTO PhieuGiamGia (MaPhieuGiamGia, TenPhieuGiamGia, kieuGiam, mucGiam, mucGiamToiDa, hoaDonToiThieu, NgayBatDau, NgayKetThuc, SoLuong, DaDung, TrangThai) VALUES
('PG001', N'Giảm giá mùa hè', 0, 100000.00, NULL, 200000.00, '2025-04-01', '2025-06-30', 100, 20, 1),
('PG002', N'Giảm giá Tết', 1, 10.00, 100000.00, 300000.00, '2025-01-01', '2025-02-15', 150, 75, 0),
('PG003', N'Ưu đãi sinh viên', 0, 50000.00, NULL, 150000.00, '2025-04-15', '2025-05-15', 80, 30, 1),
('PG004', N'Giảm giá cuối năm', 1, 20.00, 200000.00, 500000.00, '2025-12-01', '2025-12-31', 200, 100, 0),
('PG005', N'Khuyến mãi mùa xuân', 0, 80000.00, NULL, 250000.00, '2025-03-01', '2025-04-30', 120, 50, 1),
('PG006', N'Ưu đãi khách hàng thân thiết', 1, 15.00, 150000.00, 400000.00, '2025-04-01', '2025-06-30', 90, 45, 1),
('PG007', N'Giảm giá Black Friday', 0, 100000.00, NULL, 300000.00, '2025-11-01', '2025-11-30', 180, 90, 0),
('PG008', N'Ưu đãi ngày 20/10', 1, 10.00, 50000.00, 200000.00, '2025-10-15', '2025-10-25', 70, 35, 0),
('PG009', N'Giảm giá mùa đông', 0, 60000.00, NULL, 180000.00, '2025-12-01', '2025-12-31', 110, 55, 0),
('PG010', N'Khuyến mãi cuối tuần', 1, 5.00, 30000.00, 150000.00, '2025-04-15', '2025-04-21', 60, 15, 1),
('PG011', N'Giảm giá hè cuối', 0, 70000.00, NULL, 250000.00, '2025-07-01', '2025-08-31', 90, 25, 0),
('PG012', N'Khuyến mãi lễ 30/4', 1, 15.00, 100000.00, 350000.00, '2025-04-25', '2025-05-05', 120, 60, 1),
('PG013', N'Ưu đãi sinh nhật', 0, 50000.00, NULL, 200000.00, '2025-06-01', '2025-06-30', 80, 40, 0),
('PG014', N'Giảm giá trung thu', 1, 20.00, 120000.00, 400000.00, '2025-09-01', '2025-09-15', 100, 50, 0),
('PG015', N'Khuyến mãi đông', 0, 90000.00, NULL, 300000.00, '2025-12-15', '2025-12-31', 110, 55, 0),
('PG016', N'Ưu đãi khách VIP', 1, 25.00, 200000.00, 500000.00, '2025-05-01', '2025-07-31', 130, 65, 1),
('PG017', N'Giảm giá Cyber Monday', 0, 150000.00, NULL, 450000.00, '2025-11-25', '2025-12-02', 150, 75, 0),
('PG018', N'Khuyến mãi ngày 2/9', 1, 10.00, 50000.00, 250000.00, '2025-08-29', '2025-09-03', 90, 45, 0),
('PG019', N'Giảm giá mùa thu', 0, 80000.00, NULL, 220000.00, '2025-09-15', '2025-10-31', 100, 30, 0),
('PG020', N'Khuyến mãi cuối năm', 1, 30.00, 300000.00, 600000.00, '2025-12-20', '2025-12-31', 140, 70, 0);
GO


INSERT INTO HoaDon (IDNhanVien, IDKhachHang, IDPhieuGiamGia, MaHoaDon, TongTien, GiamGia, NgayTao, ThanhTien, TrangThai) VALUES
(1, 1, 1, 'HD001', 1000000.00, 100000.00, '2025-04-15', 900000.00, 1),
(2, 2, 3, 'HD002', 600000.00, 50000.00, '2025-04-15', 550000.00, 1),
(3, 3, 5, 'HD003', 1350000.00, 80000.00, '2025-04-15', 1270000.00, 1),
(4, 4, 6, 'HD004', 2000000.00, 300000.00, '2025-04-15', 1700000.00, 1),
(5, 5, 10, 'HD005', 700000.00, 3500.00, '2025-04-15', 696500.00, 0),
(6, 6, 12, 'HD006', 1200000.00, 180000.00, '2025-04-25', 1020000.00, 1),
(7, 7, 1, 'HD007', 500000.00, 100000.00, '2025-04-15', 400000.00, 1),
(8, 8, 3, 'HD008', 200000.00, 50000.00, '2025-04-15', 150000.00, 1),
(9, 9, 5, 'HD009', 1200000.00, 80000.00, '2025-04-15', 1120000.00, 1),
(10, 10, 10, 'HD010', 550000.00, 2750.00, '2025-04-15', 547250.00, 1),
(11, 11, 12, 'HD011', 1500000.00, 225000.00, '2025-04-25', 1275000.00, 1),
(12, 12, 1, 'HD012', 1400000.00, 100000.00, '2025-04-15', 1300000.00, 0),
(13, 13, 3, 'HD013', 900000.00, 50000.00, '2025-04-15', 850000.00, 1),
(14, 14, 5, 'HD014', 500000.00, 80000.00, '2025-04-15', 420000.00, 0),
(15, 15, 10, 'HD015', 1200000.00, 6000.00, '2025-04-15', 1194000.00, 1),
(16, 16, 12, 'HD016', 1050000.00, 157500.00, '2025-04-25', 892500.00, 1),
(17, 17, 1, 'HD017', 600000.00, 100000.00, '2025-04-15', 500000.00, 1),
(18, 18, 3, 'HD018', 250000.00, 50000.00, '2025-04-15', 200000.00, 1),
(19, 19, 5, 'HD019', 800000.00, 80000.00, '2025-04-15', 720000.00, 1),
(20, 20, 10, 'HD020', 900000.00, 4500.00, '2025-04-15', 895500.00, 1);
GO




INSERT INTO ChiTietHoaDon (IDSanPham, IDHoaDon, SoLuong, DonGia, TrangThai) VALUES
(1, 1, 2, 500000.00, 1),
(2, 2, 1, 600000.00, 1),
(3, 3, 3, 450000.00, 1),
(4, 4, 1, 2000000.00, 1),
(5, 5, 2, 350000.00, 1),
(6, 6, 4, 300000.00, 1),
(7, 7, 2, 250000.00, 1),
(8, 8, 1, 200000.00, 1),
(9, 9, 3, 400000.00, 1),
(10, 10, 1, 550000.00, 1),
(11, 11, 1, 1500000.00, 1),
(12, 12, 2, 700000.00, 1),
(13, 13, 2, 450000.00, 1),
(14, 14, 1, 500000.00, 1),
(15, 15, 2, 600000.00, 1),
(16, 16, 3, 350000.00, 1),
(17, 17, 2, 300000.00, 1),
(18, 18, 1, 250000.00, 1),
(19, 19, 1, 800000.00, 1),
(20, 20, 1, 900000.00, 1);
GO






CREATE TRIGGER trg_AutoGenerateMaKhachHang_Simple
ON KhachHang
AFTER INSERT
AS
BEGIN
    UPDATE KhachHang
    SET MaKhachHang = N'KH' + RIGHT('000' + CAST(i.ID AS NVARCHAR(3)), 3)
    FROM KhachHang kh
    JOIN INSERTED i ON kh.ID = i.ID;
END;
GO






CREATE TRIGGER trg_AutoGenerateMaNhanVien_Simple
ON NhanVien
AFTER INSERT
AS
BEGIN
    UPDATE NhanVien
    SET MaNhanVien = N'NV' + RIGHT('000' + CAST(i.ID AS NVARCHAR(3)), 3)
    FROM NhanVien nv
    JOIN INSERTED i ON nv.ID = i.ID;
END;
GO




CREATE TRIGGER trg_AutoGenerateMaPhieuGiamGia_Simple
ON PhieuGiamGia
AFTER INSERT
AS
BEGIN
    UPDATE PhieuGiamGia
    SET MaPhieuGiamGia = N'PG' + RIGHT('000' + CAST(i.ID AS NVARCHAR(3)), 3)
    FROM PhieuGiamGia pgg
    JOIN INSERTED i ON pgg.ID = i.ID;
END;
GO




CREATE TRIGGER trg_AutoGenerateMaHoaDon_Simple
ON HoaDon
AFTER INSERT
AS
BEGIN
    UPDATE HoaDon
    SET MaHoaDon = N'HD' + RIGHT('000' + CAST(i.ID AS NVARCHAR(3)), 3)
    FROM HoaDon hd
    JOIN INSERTED i ON hd.ID = i.ID;
END;
GO

CREATE TRIGGER trg_AutoGenerateMaSanPham_Simple
ON SanPham
AFTER INSERT
AS
BEGIN
    UPDATE SanPham
    SET MaSanPham = N'SP' + RIGHT('000' + CAST(i.ID AS NVARCHAR(3)), 3)
    FROM SanPham sp
    JOIN INSERTED i ON sp.ID = i.ID;
END;
GO




