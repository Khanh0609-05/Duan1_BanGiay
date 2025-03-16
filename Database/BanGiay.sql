﻿CREATE DATABASE BanGiay3;
GO

USE BanGiay3;
GO

-- Bảng ThuongHieu
CREATE TABLE ThuongHieu (
    ID INT PRIMARY KEY IDENTITY,
    Ma NVARCHAR(50) UNIQUE NOT NULL,
    Ten NVARCHAR(100) NOT NULL,
    TrangThai BIT DEFAULT 1
);
GO

-- Bảng SanPham
CREATE TABLE SanPham (
    ID_SanPham INT PRIMARY KEY IDENTITY,
    Ma NVARCHAR(50) UNIQUE NOT NULL,
    Ten NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(500),
    GiaBan DECIMAL(18,2) CHECK (GiaBan >= 0),
    ID_ThuongHieu INT NOT NULL FOREIGN KEY REFERENCES ThuongHieu(ID) ON DELETE CASCADE ON UPDATE CASCADE,
    TrangThai BIT DEFAULT 1
);
GO

-- Bảng MauSac
CREATE TABLE MauSac (
    ID_MauSac INT PRIMARY KEY IDENTITY,
    Ma NVARCHAR(50) UNIQUE NOT NULL,
    Ten NVARCHAR(100) NOT NULL,
    TrangThai BIT DEFAULT 1
);
GO

-- Bảng KichThuoc
CREATE TABLE KichThuoc (
    ID INT PRIMARY KEY IDENTITY,
    Ma NVARCHAR(50) UNIQUE NOT NULL,
    Ten NVARCHAR(50) NOT NULL,
    TrangThai BIT DEFAULT 1
);
GO

-- Bảng SPChiTiet (Sản phẩm chi tiết)
CREATE TABLE SPChiTiet (
    ID_SPCT INT PRIMARY KEY IDENTITY,
    MaSPCT NVARCHAR(50) UNIQUE NOT NULL,
    ID_MauSac INT NOT NULL FOREIGN KEY REFERENCES MauSac(ID_MauSac) ON DELETE CASCADE ON UPDATE CASCADE,
    ID_KichThuoc INT NOT NULL FOREIGN KEY REFERENCES KichThuoc(ID) ON DELETE CASCADE ON UPDATE CASCADE,
    ID_SanPham INT NOT NULL FOREIGN KEY REFERENCES SanPham(ID_SanPham) ON DELETE CASCADE ON UPDATE CASCADE,
    SoLuong INT NOT NULL CHECK (SoLuong >= 0),
    DonGia DECIMAL(18,2) CHECK (DonGia >= 0),
    TrangThai BIT DEFAULT 1
);
GO

-- Bảng KhachHang
CREATE TABLE KhachHang (
    ID INT PRIMARY KEY IDENTITY,
    Ten NVARCHAR(100) NOT NULL,
    SDT NVARCHAR(15) UNIQUE NOT NULL,
    MaKH NVARCHAR(50) UNIQUE NOT NULL,
    TrangThai BIT DEFAULT 1
);
GO

-- Bảng NhanVien
CREATE TABLE NhanVien (
    ID INT PRIMARY KEY IDENTITY,
    Ten NVARCHAR(100) NOT NULL,
    MaNV NVARCHAR(50) UNIQUE NOT NULL,
    TenDangNhap NVARCHAR(50) UNIQUE NOT NULL,
    MatKhau VARBINARY(64) NOT NULL,
    TrangThai BIT DEFAULT 1
);
GO



CREATE TABLE PhieuGiamGia (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MaPhieuGiamGia NVARCHAR(50) UNIQUE NOT NULL,
    TenPhieuGiamGia NVARCHAR(255) NOT NULL,
    SoLuong INT NOT NULL CHECK (SoLuong >= 0),
    NgayBatDau DATE,
    NgayKetThuc DATE,
    SoTienGiam DECIMAL(18,2) NOT NULL CHECK (SoTienGiam >= 0),
	TrangThai BIT DEFAULT 1 CHECK (TrangThai IN (0,1)),
    CONSTRAINT CHK_NgayBatDau_NgayKetThuc CHECK (NgayBatDau <= NgayKetThuc)
);
GO

-- Bảng HoaDon
CREATE TABLE HoaDon (
    ID INT PRIMARY KEY IDENTITY,
    ID_KhachHang INT NOT NULL FOREIGN KEY REFERENCES KhachHang(ID) ON DELETE CASCADE ON UPDATE CASCADE,
    ID_NhanVien INT NOT NULL FOREIGN KEY REFERENCES NhanVien(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	ID_PhieuGiamGia INT FOREIGN KEY REFERENCES PhieuGiamGia(ID)ON DELETE CASCADE ON UPDATE CASCADE,
    NgayMuaHang DATETIME NOT NULL DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1
);
GO

-- Bảng BangThanhToan
CREATE TABLE BangThanhToan (
    ID INT PRIMARY KEY IDENTITY,
    ID_HoaDon INT NOT NULL FOREIGN KEY REFERENCES HoaDon(ID) ON DELETE CASCADE ON UPDATE CASCADE,
    PhuongThuc NVARCHAR(50) NOT NULL,
    TrangThai BIT DEFAULT 1
);
GO

-- Bảng HoaDonChiTiet
CREATE TABLE HoaDonChiTiet (
    ID INT PRIMARY KEY IDENTITY,
    ID_SPCT INT NOT NULL FOREIGN KEY REFERENCES SPChiTiet(ID_SPCT) ON DELETE CASCADE ON UPDATE CASCADE,
    ID_HoaDon INT NOT NULL FOREIGN KEY REFERENCES HoaDon(ID) ON DELETE CASCADE ON UPDATE CASCADE,
    SoLuong INT NOT NULL CHECK (SoLuong >= 0),
    DonGia DECIMAL(18,2) CHECK (DonGia >= 0),
    TrangThai BIT DEFAULT 1
);
GO






INSERT INTO ThuongHieu (Ma, Ten) VALUES 
('TH001', N'Nike'),
('TH002', N'Adidas'),
('TH003', N'Puma'),
('TH004', N'Reebok'),
('TH005', N'Converse'),
('TH006', N'Vans'),
('TH007', N'New Balance'),
('TH008', N'Fila'),
('TH009', N'Under Armour'),
('TH010', N'Asics');

GO


INSERT INTO SanPham (Ma, Ten, MoTa, GiaBan, ID_ThuongHieu) VALUES 
('SP001', N'Giày chạy bộ Nike', N'Dành cho người yêu thích chạy bộ', 1500000, 1),
('SP002', N'Giày đá bóng Adidas', N'Chuyên dụng trên sân cỏ', 2000000, 2),
('SP003', N'Dép Puma', N'Dép thoải mái cho mùa hè', 500000, 3),
('SP004', N'Giày Reebok Training', N'Hỗ trợ luyện tập hiệu quả', 1200000, 4),
('SP005', N'Converse Classic', N'Phong cách cổ điển', 1100000, 5),
('SP006', N'Vans Old Skool', N'Đậm chất đường phố', 1000000, 6),
('SP007', N'New Balance 574', N'Thiết kế retro, phù hợp mọi phong cách', 1300000, 7),
('SP008', N'Fila Sneakers', N'Modern and trendy', 900000, 8),
('SP009', N'Under Armour Running', N'Supports long-distance running', 1800000, 9),
('SP010', N'Asics Gel-Kayano', N'For professional runners', 2500000, 10);

GO


INSERT INTO MauSac (Ma, Ten) VALUES 
('MS001', N'Đỏ'),
('MS002', N'Xanh dương'),
('MS003', N'Xanh lá'),
('MS004', N'Đen'),
('MS005', N'Tráng'),
('MS006', N'Vàng'),
('MS007', N'Cam'),
('MS008', N'Hồng'),
('MS009', N'Tím'),
('MS010', N'Nâu');

GO


INSERT INTO KichThuoc (Ma, Ten) VALUES 
('KT001', '35'),
('KT002', '36'),
('KT003', '37'),
('KT004', '38'),
('KT005', '39'),
('KT006', '40'),
('KT007', '41'),
('KT008', '42'),
('KT009', '43'),
('KT010', '44');

GO



INSERT INTO SPChiTiet (MaSPCT, ID_MauSac, ID_KichThuoc, ID_SanPham, SoLuong, DonGia) VALUES 
('SPCT001', 1, 6, 1, 10, 1500000),
('SPCT002', 2, 7, 2, 15, 2000000),
('SPCT003', 3, 8, 3, 20, 500000),
('SPCT004', 4, 9, 4, 12, 1200000),
('SPCT005', 5, 10, 5, 18, 1100000),
('SPCT006', 6, 1, 6, 25, 1000000),
('SPCT007', 7, 2, 7, 30, 1300000),
('SPCT008', 8, 3, 8, 10, 900000),
('SPCT009', 9, 4, 9, 15, 1800000),
('SPCT010', 10, 5, 10, 5, 2500000);

GO

INSERT INTO KhachHang (Ten, SDT, MaKH) VALUES 
(N'Nguyễn Văn A', '0123456789', 'KH001'),
(N'Trần Thị B', '0987654321', 'KH002'),
(N'Hoàng Minh C', '0911223344', 'KH003'),
(N'Lê Thanh D', '0922334455', 'KH004'),
(N'Phạm Văn E', '0944556677', 'KH005'),
(N'Hồ Ngọc F', '0977665544', 'KH006'),
(N'Võ Ngọc G', '0933445566', 'KH007'),
(N'Tạ Thị H', '0966778899', 'KH008'),
(N'Dương Văn I', '0999888777', 'KH009'),
(N'Phan Thị J', '0900112233', 'KH010');


GO

INSERT INTO NhanVien (Ten, MaNV, TenDangNhap, MatKhau) VALUES 
(N'Nguyễn Hữu K', 'NV001', 'user1', HASHBYTES('SHA2_256', 'password1')),
(N'Trần Mai L', 'NV002', 'user2', HASHBYTES('SHA2_256', 'password2')),
(N'Lê Văn M', 'NV003', 'user3', HASHBYTES('SHA2_256', 'password3')),
(N'Phạm Thị N', 'NV004', 'user4', HASHBYTES('SHA2_256', 'password4')),
(N'Hoàng Văn O', 'NV005', 'user5', HASHBYTES('SHA2_256', 'password5')),
(N'Hồ Mai P', 'NV006', 'user6', HASHBYTES('SHA2_256', 'password6')),
(N'Võ Văn Q', 'NV007', 'user7', HASHBYTES('SHA2_256', 'password7')),
(N'Tạ Hữu R', 'NV008', 'user8', HASHBYTES('SHA2_256', 'password8')),
(N'Nguyễn Văn S', 'NV009', 'user9', HASHBYTES('SHA2_256', 'password9')),
(N'Phan Thị T', 'NV010', 'user10', HASHBYTES('SHA2_256', 'password10'));


GO


INSERT INTO HoaDon (ID_KhachHang, ID_NhanVien) VALUES 
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);

GO

INSERT INTO BangThanhToan (ID_HoaDon, PhuongThuc) VALUES 
(1, N'Tiền mặt'),
(2, N'Chuyển khoản'),
(3, N'Thẻ tín dụng'),
(4, N'Tiền mặt'),
(5, N'Thẻ tín dụng'),
(6, N'Chuyển khoản'),
(7, N'Tiền mặt'),
(8, N'Tiền mặt'),
(9, N'Chuyển khoản'),
(10, N'Thẻ tín dụng');

GO

INSERT INTO HoaDonChiTiet (ID_SPCT, ID_HoaDon, SoLuong, DonGia) VALUES 
(1, 1, 2, 1500000),
(2, 2, 1, 2000000),
(3, 3, 3, 500000),
(4, 4, 2, 1200000),
(5, 5, 1, 1100000),
(6, 6, 4, 1000000),
(7, 7, 5, 1300000),
(8, 8, 2, 900000),
(9, 9, 1, 1800000),
(10, 10, 3, 2500000);



INSERT INTO Voucher (MaVoucher, Ten, MoTa, GiaTri, Loai, NgayBatDau, NgayKetThuc) VALUES
('VC001', N'Giảm 10%', N'Giảm giá 10% ', 10, N'Phần trăm', '2025-03-01', '2025-03-31'),
('VC002', N'Giảm 50k', N'Giảm 50k ', 50000, N'Số tiền', '2025-03-01', '2025-03-31'),
('VC003', N'Giảm 20%', N'Giảm giá 20% ', 20, N'Phần trăm', '2025-03-01', '2025-03-31'),
('VC004', N'Giảm 100k', N'Giảm 100k ', 100000, N'Số tiền', '2025-03-01', '2025-03-31'),
('VC005', N'Flash Sale', N'Giảm 30% ', 30, N'Phần trăm', '2025-03-15', '2025-03-15');
GO

