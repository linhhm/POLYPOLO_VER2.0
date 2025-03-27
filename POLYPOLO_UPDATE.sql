CREATE DATABASE POLYPOLO_UPDATE;
DROP DATABASE POLYPOLO_UPDATE;

USE POLYPOLO_UPDATE;
--CREATE TABLE
CREATE TABLE DanhMuc(
	MaDanhMuc INT IDENTITY(1,1) PRIMARY KEY,
	TenDanhMuc NVARCHAR(250) NOT NULL,
	TrangThai NVARCHAR(30) NOT NULL,
	Deleted INT
);

CREATE TABLE SanPham (
    MaSanPham INT PRIMARY KEY IDENTITY(1,1),
    MaDanhMuc INT,
    TrangThai NVARCHAR(30) NOT NULL,
    GiaNhap FLOAT NOT NULL,
    GiaBan FLOAT NOT NULL,
	KhuVucID INT NOT NULL,
	Deleted INT
    FOREIGN KEY (MaDanhMuc) REFERENCES DanhMuc(MaDanhMuc)
);

ALTER TABLE SanPham ADD PhanTramThue FLOAT NOT NULL DEFAULT 10;

CREATE TABLE KhuVucKho (
    KhuVucID INT PRIMARY KEY IDENTITY(1,1),
    TenKhuVuc NVARCHAR(255) NOT NULL,
	MoTa NVARCHAR(255) NOT NULL,
	Deleted INT
);

ALTER TABLE SanPham
ADD CONSTRAINT FK_SanPham_KhuVucKho FOREIGN KEY (KhuVucID) REFERENCES KhuVucKho(KhuVucID);

CREATE TABLE Size (
    MaSize INT PRIMARY KEY IDENTITY(1,1),
    TenSize NVARCHAR(10) NOT NULL,
	Deleted INT
);

CREATE TABLE MauSac (
    MaMau INT PRIMARY KEY IDENTITY(1,1),
    TenMau NVARCHAR(50) NOT NULL,
	Deleted INT
);

CREATE TABLE ChatLieu (
    MaChatLieu INT PRIMARY KEY IDENTITY(1,1),
    TenChatLieu NVARCHAR(50) NOT NULL,
	Deleted INT
);

CREATE TABLE SanPhamChiTiet (
    MaSanPhamChiTiet INT PRIMARY KEY IDENTITY(1,1),
    MaSanPham INT,
    TenSanPhamChiTiet NVARCHAR(250) NOT NULL,
	HinhAnh VARCHAR(255),
    MaSize INT NOT NULL,
    MaMau INT NOT NULL,
	MaChatLieu INT NOT NULL,
    TrangThai NVARCHAR(30) NOT NULL,
    SoLuongTon INT NOT NULL,
	NgayNhapKho DATE NOT NULL,
	Deleted INT,
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham),
    FOREIGN KEY (MaSize) REFERENCES Size(MaSize),
    FOREIGN KEY (MaMau) REFERENCES MauSac(MaMau),
	FOREIGN KEY (MaChatLieu) REFERENCES ChatLieu(MaChatLieu)
);

CREATE TABLE NhanHang (
    NhanHangID INT PRIMARY KEY IDENTITY(1,1),
    TenNhanHang NVARCHAR(255) NOT NULL,
	Deleted INT
);

ALTER TABLE SanPhamChiTiet
ADD NhanHangID INT;
ALTER TABLE SanPhamChiTiet
ADD CONSTRAINT FK_SanPham_NhanHang FOREIGN KEY (NhanHangID) REFERENCES NhanHang(NhanHangID);

ALTER TABLE SanPhamChiTiet ADD Barcode NVARCHAR(255); 

--ALTER TABLE SanPhamChiTiet ADD GiaKhuyenMai DECIMAL(13,3);

CREATE TABLE NguoiDung(
    MaNguoiDung INT PRIMARY KEY IDENTITY(1,1),
    TenDangNhap VARCHAR(50) UNIQUE NOT NULL,
    MatKhau VARCHAR(10) NOT NULL, 
    VaiTro NVARCHAR(50) NOT NULL,
	Email VARCHAR(250) NOT NULL,
	Deleted INT
);

CREATE TABLE NhanVien (
    MaNhanVien INT PRIMARY KEY IDENTITY(1,1),
    MaNguoiDung INT,
	HinhAnh VARCHAR(255),
    TenNhanVien NVARCHAR(250) NOT NULL,
    GioiTinh NVARCHAR(5) NOT NULL,
    NgaySinh DATE NOT NULL,
    SoDienThoai VARCHAR(15) NOT NULL,
    DiaChi NVARCHAR(250) NOT NULL,
	Deleted INT,
    FOREIGN KEY (MaNguoiDung) REFERENCES NguoiDung(MaNguoiDung)
);

CREATE TABLE KhachHang(
    MaKhachHang INT PRIMARY KEY IDENTITY(1,1),
    TenKhachHang NVARCHAR(250) NOT NULL,
    GioiTinh NVARCHAR(5) NOT NULL,
    NgaySinh DATE NOT NULL,
    SoDienThoai VARCHAR(15) NOT NULL,
    DiaChi NVARCHAR(250) NOT NULL,
    LoaiKhachHang NVARCHAR(250) NOT NULL,
    Deleted INT
);

CREATE TABLE HoaDon (
    MaHoaDon INT PRIMARY KEY IDENTITY(1,1),
    MaKhachHang INT NULL, 
    MaNhanVien INT,
	TenKhachHang NVARCHAR(250) NOT NULL,
    TongTien FLOAT NOT NULL,
    NgayLap DATE,
    TrangThai NVARCHAR(25) NOT NULL,
    PhuongThucThanhToan NVARCHAR(50) NOT NULL,
    LoaiKhachHang NVARCHAR(250) NOT NULL,
    Deleted INT,
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien)
);

CREATE TABLE HoaDonChiTiet (
    MaHoaDonChiTiet INT PRIMARY KEY IDENTITY(1,1),
    MaHoaDon INT,
    MaSanPhamChiTiet INT,
    SoLuong INT NOT NULL,
    DonGia FLOAT NOT NULL,
    Deleted INT,
    FOREIGN KEY (MaHoaDon) REFERENCES HoaDon(MaHoaDon),
    FOREIGN KEY (MaSanPhamChiTiet) REFERENCES SanPhamChiTiet(MaSanPhamChiTiet)
);

CREATE TABLE NhaCungCap (
    NhaCungCapID INT PRIMARY KEY IDENTITY(1,1),
    TenNhaCungCap NVARCHAR(255) NOT NULL,
	DiaChi NVARCHAR(255) NOT NULL,
	Email VARCHAR(250) NOT NULL,
	SoDT VARCHAR(15) NOT NULL,
	Deleted INT
);

CREATE TABLE PhieuNhapKho (
    PhieuNhapID INT PRIMARY KEY IDENTITY(1,1),
    NhaCungCapID INT NOT NULL,
    NhanVienID INT NOT NULL,
    ThoiGianNhap DATE NOT NULL,
    TongDon FLOAT NOT NULL,
	TrangThai NVARCHAR(250) NOT NULL,
	Deleted INT,
    FOREIGN KEY (NhaCungCapID) REFERENCES NhaCungCap(NhaCungCapID),
    FOREIGN KEY (NhanVienID) REFERENCES NhanVien(MaNhanVien)
);

CREATE TABLE ChiTietPhieuNhap (
    ChiTietID INT PRIMARY KEY IDENTITY(1,1),
    PhieuNhapID INT NOT NULL,
    MaSanPhamChiTiet INT NOT NULL,
    SoLuong INT NOT NULL,
    DonGia FLOAT NOT NULL,
	PhuongThucNhap NVARCHAR(250) NOT NULL,
	MoTa NVARCHAR(250),
	PhanTramThue INT NOT NULL,
	Deleted INT,
    FOREIGN KEY (PhieuNhapID) REFERENCES PhieuNhapKho(PhieuNhapID),
    FOREIGN KEY (MaSanPhamChiTiet) REFERENCES SanPhamChiTiet(MaSanPhamChiTiet)
);


--INSERT DATA
INSERT INTO NguoiDung(TenDangNhap,MatKhau,Email,VaiTro,Deleted) 
						VALUES('admin','111', 'admin@gmail.com', 'Admin',0)
						      , ('staff', '222', 'staff@gmail.com', 'Staff',0)
							  , ('hoadk','444', 'hoadk@fpt.edu.vn','Staff',0)
							  , ('linhhm','333', 'linhhmth01083@fpt.edu.vn', 'Admin',0)
							  , ('huongdm', '555', 'huongdmth02005@fpt.edu.vn', 'Staff',0)
							  ;
---------------
INSERT INTO NhanVien(MaNguoiDung,TenNhanVien, HinhAnh,GioiTinh,NgaySinh,SoDienThoai,DiaChi,Deleted)
			VALUES('1', 'Loi Choi', NULL, 'Nam', '1997-09-01', '0123456789', N'Hà Nội',0)
					, ('2', N'Ly Lê', NULL, N'Nữ', '1998-07-28', '0234567891', N'TP.HCM',0)
					, ('3', N'Hòa Đinh', NULL, N'Nam', '2000-06-26', '0234156781', N'Quảng Ninh',0)
					, ('4', N'Linh Hoàng', NULL, N'Nữ', '2001-08-28', '0634156781', N'Hải Phòng',0)
					, ('5', N'Hương Đào', NULL, N'Nữ', '1996-01-08', '0321451671', N'Ninh Bình',0)
					;
-----------------
INSERT INTO DanhMuc(TenDanhMuc, TrangThai,Deleted) VALUES
												  (N'Polo Unisex', N'Hoạt động',0)
												, (N'Polo Đặc Biệt', N'Hoạt động',0)
												, (N'Polo Gen-Z', N'Ngừng hoạt động',0)
												, (N'Polo Limited-Edition', N'Hoạt động',0)
												, (N'Polo Special Tết 2024', N'Hoạt động',0)
												, (N'Polo Nam', N'Hoạt động',0)
												, (N'Polo Nữ', N'Hoạt động',0)
												, (N'Polo Trẻ Em', N'Hoạt động',0)
												;
---------------------
INSERT INTO KhuVucKho (TenKhuVuc, MoTa, Deleted) VALUES (N'Kho A', N'Polo Cổ Điển (Classic Polo)' , 0), 
												(N'Kho B', N'Polo Thể Thao (Performance Polo)' , 0), 
												(N'Kho C', N'Polo Cao Cấp (Luxury Polo)',0), 
												(N'Kho D', N'Polo Dệt Kim (Knit Polo)',  0), 
												(N'Kho E', N'Polo Môi Trường (Eco-Friendly Polo)',  0), 
												(N'Kho G', N'Polo Đi Biển (Beach Polo)',  0), 
												(N'Kho H', N'Polo Kinh Doanh (Business Polo)',  0), 
												(N'Kho JK', N'Polo Du Lịch (Travel Polo)',  0);
------------------
INSERT INTO SanPham(MaDanhMuc, TrangThai, GiaNhap, GiaBan, KhuVucID, Deleted) 
				VALUES (1, N'Còn hàng', 450000, 550000, 1,0), 
					   (2, N'Còn hàng', 620000, 780000, 2,0),
					   (2, N'Còn hàng', 320000, 560000, 3,0),
					   (3, N'Còn hàng', 120000, 240000, 4, 0),
					   (4, N'Còn hàng', 230000, 780000,5, 0),
					   (5, N'Còn hàng', 120000, 580000,2,0),
					   (6, N'Còn hàng', 350000, 760000,3,0),
					   (7, N'Còn hàng', 1350000, 2560000,5,0),
					   (8, N'Còn hàng', 201200, 760000,5,0),
					   (1, N'Còn hàng', 550000, 600000,4,0),
					   (1, N'Còn hàng', 500000, 650000,3, 0),
					   (2, N'Còn hàng', 700000, 900000,4, 0),
					   (3, N'Còn hàng', 200000, 400000,2, 0),
					   (4, N'Còn hàng', 300000, 850000,5, 0),
					   (5, N'Còn hàng', 150000, 650000,3, 0),
					   (6, N'Còn hàng', 450000, 850000,1, 0),
					   (7, N'Còn hàng', 1600000, 3000000,1, 0),
					   (8, N'Còn hàng', 250000, 800000,7, 0),
					   (1, N'Còn hàng', 600000, 700000,8, 0),
					   (2, N'Còn hàng', 800000, 1000000,8, 0),
					   (3, N'Còn hàng', 220000, 420000,7, 0),
					   (4, N'Còn hàng', 310000, 860000,6, 0),
					   (5, N'Còn hàng', 160000, 660000,6, 0),
					   (6, N'Còn hàng', 460000, 860000,5, 0),
					   (7, N'Còn hàng', 1650000, 3050000,2, 0),
					   (8, N'Còn hàng', 255000, 810000,3, 0),
					   (1, N'Còn hàng', 610000, 710000,4, 0),
					   (2, N'Còn hàng', 810000, 1010000,7, 0),
					   (3, N'Còn hàng', 225000, 425000,8, 0),
					   (4, N'Còn hàng', 315000, 865000,5, 0);
-----------------------------------------------
INSERT INTO Size (TenSize,Deleted) VALUES ('XS',0), ('S',0), ('M',0), ('L',0);
-----------------------------------------------
INSERT INTO MauSac (TenMau,Deleted) VALUES (N'Xanh',0), (N'Đỏ',0), (N'Trắng',0), (N'Đen',0);
-----------------------------------------------
INSERT INTO ChatLieu (TenChatLieu, Deleted) VALUES (N'Len',0), (N'Cotton',0), (N'Nhung',0);
-----------------------
INSERT INTO NhanHang (TenNhanHang, Deleted) VALUES (N'Ralph Laurent', 0)
													, (N'Fred Perry', 0)
													, (N'Gucci', 0)
													, (N'Alber & Crombie', 0)
													, (N'Canifa', 0)
													, (N'Uniqlo', 0)
													, (N'Rupaul Drag', 0)
													, (N'RKive Loves', 0);
-----------------------------------------------										
INSERT INTO SanPhamChiTiet (MaSanPham, TenSanPhamChiTiet, HinhAnh, MaSize, MaMau, MaChatLieu, TrangThai, SoLuongTon, NgayNhapKho, NhanHangID, Deleted) VALUES 
							(1, N'Polo Nam Tay Dài Xanh Navy', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\3.jpg', 3, 1, 1, N'Còn hàng', 100, '2024-01-01', 1, 0),
							(2, N'Polo Nữ Slim Fit Đen', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\4.jpg', 3, 4, 1, N'Còn hàng', 50, '2024-01-01', 2, 0),
							(3, N'Polo Nữ Đỏ Đô (Special Tết 2024)', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\5.jpg', 1, 2, 1, N'Còn hàng', 7, '2024-01-01', 3, 0),
							(4, N'Polo Trẻ Em Essential Trắng', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\6.jpg', 2, 3, 3, N'Còn hàng', 27, '2024-01-01', 4, 0),
							(5, N'Polo Nam Huyền Bí S', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\7.jpg', 1, 1, 3, N'Còn hàng', 100, '2024-01-01', 5, 0),
							(6, N'Polo Nữ Tinh Khôi M', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\8.jpg', 2, 2, 1, N'Còn hàng', 100, '2024-01-01', 6, 0),
							(7, N'Polo Nữ Bình Minh XS', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\9.jpg', 2, 2, 3, N'Còn hàng', 100, '2024-01-01', 7, 0),
							(8, N'Polo Nam Ánh Dương Trắng', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\10.jpg', 2, 2, 1, N'Còn hàng', 100, '2024-01-01', 8, 0),
							(9, N'Polo Giải Trí Hè', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\11.jpg', 1, 3, 3, N'Còn hàng', 160, '2024-01-01', 1, 0),
							(10, N'Polo Khám Phá Bí Ẩn', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\12.jpg', 2, 3, 1, N'Còn hàng', 170, '2024-01-01', 2, 0),
							(11, N'Polo Hải Đăng Vũng Tàu', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\13.jpg', 1, 4, 3, N'Còn hàng', 180, '2024-01-01', 3, 0),
							(12, N'Polo Phố Cổ Hội An', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\14.jpg', 3, 1, 2, N'Còn hàng', 190, '2024-01-01', 4, 0),
							(13, N'Polo Sắc Màu Festival Huế', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\15.jpg', 2, 2, 3, N'Còn hàng', 200, '2024-01-01', 5, 0),
							(14, N'Polo Bình Minh Sapa', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\16.jpg', 3, 3, 1, N'Còn hàng', 210, '2024-01-01', 6, 0),
							(15, N'Polo Hoàng Hôn Đà Lạt', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\17.jpg', 1, 4, 3, N'Còn hàng', 220, '2024-01-01', 7, 0),
							(16, N'Polo Bầu Trời Đêm Phú Quốc', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\18.jpg', 2, 1, 3, N'Còn hàng', 230, '2024-01-01', 8, 0),
							(17, N'Polo Mặt Trăng Sa Pa', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\19.jpg', 3, 2, 2, N'Còn hàng', 240, '2024-01-01', 1, 0),
							(18, N'Polo Sao Băng Hạ Long', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\20.jpg', 1, 3, 1, N'Còn hàng', 250, '2024-01-01', 2, 0),
							(19, N'Polo Hành Tinh Mộc Châu', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\21.jpg', 2, 4, 3, N'Còn hàng', 260, '2024-01-01', 3, 0),
							(20, N'Polo Thiên Hà Núi LangBiang', 'C:\\Users\\X1\\OneDrive\\Documents\\NetBeansProjects\\POLYPOLO_UPDATE\\src\\Images\\IMG\\22.jpg', 3, 1, 2, N'Còn hàng', 270, '2024-01-01', 4, 0),
							(21, N'Polo Galaxy Huyền Bí', 'C:\Users\X1\OneDrive\Documents\NetBeansProjects\POLYPOLO_UPDATE\src\Images\IMG\23.jpg', 1, 2, 2, N'Còn hàng', 280, '2024-01-01', 5, 0),
							(22, N'Polo Đại Dương Xanh Thẳm', 'C:\Users\X1\OneDrive\Documents\NetBeansProjects\POLYPOLO_UPDATE\src\Images\IMG\24.jpg', 2, 3, 1, N'Còn hàng', 290, '2024-01-01', 6, 0),
							(23, N'Polo Thác Nước Hùng Vĩ', 'C:\Users\X1\OneDrive\Documents\NetBeansProjects\POLYPOLO_UPDATE\src\Images\IMG\25.jpg', 3, 4, 3, N'Còn hàng', 300, '2024-01-01', 7, 0),
							(24, N'Polo Núi Lửa Mênh Mông', 'C:\Users\X1\OneDrive\Documents\NetBeansProjects\POLYPOLO_UPDATE\src\Images\IMG\26.jpg', 1, 1, 1, N'Còn hàng', 310, '2024-01-01', 8, 0),
							(25, N'Polo Sa Mạc Bất Tận', 'C:\Users\X1\OneDrive\Documents\NetBeansProjects\POLYPOLO_UPDATE\src\Images\IMG\27.jpg', 2, 2, 2, N'Còn hàng', 320, '2024-01-01', 1, 0),
							(26, N'Polo Băng Giá Cực Bắc', 'C:\Users\X1\OneDrive\Documents\NetBeansProjects\POLYPOLO_UPDATE\src\Images\IMG\28.jpg', 3, 3, 1, N'Còn hàng', 330, '2024-01-01', 2, 0),
							(27, N'Polo Rừng Nhiệt Đới Lush', 'C:\Users\X1\OneDrive\Documents\NetBeansProjects\POLYPOLO_UPDATE\src\Images\IMG\29.jpg', 1, 4, 3, N'Còn hàng', 340, '2024-01-01', 3, 0),
							(28, N'Polo Hoa Cỏ Mùa Xuân', 'C:\Users\X1\OneDrive\Documents\NetBeansProjects\POLYPOLO_UPDATE\src\Images\IMG\30.jpg', 2, 1, 1, N'Còn hàng', 350, '2024-01-01', 4, 0),
							(29, N'Polo Mùa Xuân Rực Rỡ', 'C:\Users\X1\OneDrive\Documents\NetBeansProjects\POLYPOLO_UPDATE\src\Images\IMG\31.jpg', 3, 2, 2, N'Còn hàng', 360, '2024-01-01', 5, 0),
							(30, N'Polo Mùa Hè Nắng Vàng', 'C:\Users\X1\OneDrive\Documents\NetBeansProjects\POLYPOLO_UPDATE\src\Images\IMG\32.jpg', 1, 3, 3, N'Còn hàng', 370, '2024-01-01', 6, 0);
----------------------------------
INSERT INTO KhachHang (TenKhachHang, GioiTinh, NgaySinh, SoDienThoai, DiaChi, LoaiKhachHang, Deleted)
					VALUES 
						( N'Lê Vũ', 'Nam', '1990-01-01', '0912345688', N'Hà Nội', N'Thành Viên', 0),
						( N'Đào Phạm', N'Nữ', '1992-02-02', '0955120820', N'Bến Tre', N'Thành Viên', 0),
						( N'Lưu Quỳnh Phương', N'Nữ', '1993-03-03', '0912345689', N'TP.HCM', N'Thành Viên', 0),
						( N'My Thị Hoàng Dương', N'Nữ', '1994-04-04', '0912345777', N'Hải Phòng', N'Thành Viên', 0),
						( N'Sỹ Hiếu', 'Nữ', '1995-05-05', '098765400', N'Hà Nội', N'Thành Viên', 0),
						( N'Phạm Đức Long', 'Nam', '1996-06-06', '0912345682', N'Hải Phòng', N'Thành Viên', 0),
						( N'Vũ Văn Nguyên', 'Nam', '1997-07-07', '0912345683', N'Quảng Ninh', N'Thành Viên', 0),
						( N'Đặng Thị Moe', 'Nữ', '1998-08-08', '0912345684', N'Bình Dương', N'Thành Viên', 0);
----------------------------------
INSERT INTO HoaDon (MaNhanVien, MaKhachHang, TenKhachHang, TongTien, NgayLap, TrangThai, PhuongThucThanhToan, LoaiKhachHang, Deleted)
						VALUES 
						(1, 1, N'Lê Vũ', 1200000, '2024-01-15', N'Đã thanh toán', N'Chuyển khoản', N'Thành Viên', 0),
						(1, 2, N'Đào Phạm', 780000, '2024-01-04', N'Đã thanh toán', N'Tiền mặt', N'Thành Viên', 0),
						(2, 4, N'My Thị Hoàng Dương', 1680000, '2024-01-17', N'Đã thanh toán', N'Tiền mặt', N'Thành Viên', 0),
						(2, 3, N'Lưu Quỳnh Phương', 480000, '2024-01-22', N'Đã thanh toán', N'Chuyển khoản', N'Khách Lẻ', 0),
						(1, 5, N'Sỹ Hiếu', 3000000, '2024-02-05', N'Đã thanh toán', N'Tiền mặt', N'Khách Lẻ', 0),
						(2, 6, N'Phạm Đức Long', 3500000, '2024-02-06', N'Đã thanh toán', N'Chuyển khoản', N'Khách Lẻ', 0),
						(1, 7, N'Vũ Văn Nguyên', 4000000, '2024-02-07', N'Đã thanh toán', N'Tiền mặt', N'Khách Lẻ', 0),
						(2, 8,N'Đặng Thị Moe', 4500000, '2024-02-08', N'Đã thanh toán', N'Chuyển khoản', N'Khách Lẻ', 0)
;
---------------------
INSERT INTO HoaDonChiTiet (MaHoaDon, MaSanPhamChiTiet, SoLuong, DonGia, Deleted) 
															VALUES 
																(1, 1, 2, 600000, 0),
																(2, 2, 2, 390000, 0),
																(3, 3, 4, 420000, 0),
																(4, 4, 2, 240000, 0),
																(5, 5, 10, 300000, 0),
																(6, 6, 10, 350000, 0),
																(7, 7, 10, 400000, 0),
																(8, 8, 10, 450000, 0);
-------------------------------------------------------
INSERT INTO NhaCungCap (TenNhaCungCap, DiaChi, Email, SoDT, Deleted) VALUES 
							(N'Công ty TNHH Xuất Nhập Khẩu Kafergh', N'123 Đường ABC, Quận 1, TP. HCM', 'kafergh@example.com', '0123456789', 0),
							(N'Công ty CPTN Vĩnh Sơn 5', N'456 Đường DEF, Quận 2, TP. Hà Nội', 'vinhson5@example.com', '0234567891', 0),
							(N'Hệ Thống Phân Phối Chính Hãng AngelinaJoys', N'789 Đường GHI, Quận 3, TP. Đà Nẵng', 'angelinajoys@example.com', '0345678912', 0),
							(N'Công ty TNHH Xuất Nhập Khẩu Your Moms Faves', N'101 Đường JKL, Quận 4, TP. Cần Thơ', 'yourmomsfaves@example.com', '0456789123', 0),
							(N'Công ty TNHH Hybe World', N'202 Đường MNO, Quận 5, TP. Hải Phòng', 'hybeworld@example.com', '0567891234', 0),
							(N'Công ty CP Malibu', N'303 Đường PQR, Quận 6, TP. Huế', 'malibu@example.com', '0678912345', 0),
							(N'Hệ Thống Phân Phối Chính Hãng HETCYY', N'404 Đường STU, Quận 7, TP. Nha Trang', 'hetcuu@example.com', '0789123456', 0);

-- PhieuNhapKho
INSERT INTO PhieuNhapKho (NhaCungCapID, NhanVienID, ThoiGianNhap, TongDon,TrangThai, Deleted) 
											VALUES (1, 1, '2023-01-01', 100000, N'Hoàn thành', 0)
												, (2, 2, '2023-02-01', 200000, N'Hoàn thành', 0)
												, (3, 3, '2023-02-01', 245000, N'Hoàn thành', 0)
												, (4, 4, '2023-02-01', 6780000, N'Hoàn thành', 0)
												, (4, 4, '2023-02-01', 900000,  N'Hoàn thành', 0)
												, (2, 2, '2023-02-01', 132000000,  N'Hoàn thành', 0)
												, (3, 3, '2023-02-01', 326000000,  N'Hoàn thành', 0)
												;
-------------------------------------
INSERT INTO ChiTietPhieuNhap (PhieuNhapID, MaSanPhamChiTiet, SoLuong, DonGia, PhuongThucNhap, MoTa, PhanTramThue, Deleted) VALUES
									(1, 1, 2, 150000, N'Đại Lý Phân Phối', N'Mô tả sản phẩm 1', 10, 0),
									(2, 2, 4, 250000, N'Nhà Sản Xuất', N'Mô tả sản phẩm 2', 15, 0),
									(3, 3, 6, 750000, N'Khác', N'Mô tả sản phẩm 3', 20, 0),
									(4, 4, 8, 500000, N'Đại Lý Phân Phối', N'Mô tả sản phẩm 4', 30, 0),
									(5, 5, 10, 300000, N'Khác', N'Mô tả sản phẩm 5', 7, 0),
									(6, 6, 13, 220000, N'Nhà Sản Xuất', N'Mô tả sản phẩm 6', 10, 0),
									(7, 7, 5, 800000, N'Trụ Sở Chính', N'Mô tả sản phẩm 7', 10, 0);

-------------------------------------KO CHẠY--------------------------------------------------------
SELECT pn.DonGia*pn.SoLuong +(pn.DonGia*pn.SoLuong)*(pn.PhanTramThue/100) AS 'TOTAL' FROM ChiTietPhieuNhap pn
SELECT pn.DonGia * pn.SoLuong + (pn.DonGia * pn.SoLuong) * (CAST(pn.PhanTramThue AS FLOAT) / 100.0) AS 'TOTAL'
							FROM ChiTietPhieuNhap pn INNER JOIN PhieuNhapKho pnk ON pn.PhieuNhapID = pnk.PhieuNhapID
							      WHERE pnk.Deleted!=1 AND pnk.PhieuNhapID = 1


SELECT pn.PhieuNhapID, ncc.TenNhaCungCap, nv.TenNhanVien, pn.ThoiGianNhap, pn.TongDon 
			FROM PhieuNhapKho pn INNER JOIN NhaCungCap ncc ON pn.NhaCungCapID = ncc.NhaCungCapID
								INNER JOIN NhanVien nv ON pn.NhanVienID = nv.MaNhanVien
									WHERE pn.Deleted!=1

SELECT COUNT(*) FROM ChiTietPhieuNhap WHERE PhieuNhapID = 1 AND MaSanPhamChiTiet = 1

SELECT sp.MaSanPham, spct.TenSanPhamChiTiet, dm.TenDanhMuc, nh.TenNhanHang, ms.TenMau, sz.TenSize, cl.TenChatLieu
, sp.GiaNhap, pnct.SoLuong, pn.ThoiGianNhap, pn.TongDon  FROM SanPham sp 
										INNER JOIN SanPhamChiTiet spct ON sp.MaSanPham = spct.MaSanPham
										INNER JOIN DanhMuc dm ON sp.MaDanhMuc = dm.MaDanhMuc
										INNER JOIN NhanHang nh ON spct.NhanHangID = nh.NhanHangID
										INNER JOIN MauSac ms ON spct.MaMau = ms.MaMau
										INNER JOIN Size sz ON spct.MaSize = sz.MaSize
										INNER JOIN KhuVucKho kh ON sp.KhuVucID = kh.KhuVucID
										INNER JOIN ChatLieu cl ON spct.MaChatLieu = cl.MaChatLieu
										INNER JOIN ChiTietPhieuNhap pnct ON spct.MaSanPhamChiTiet = pnct.MaSanPhamChiTiet
										INNER JOIN PhieuNhapKho pn ON pnct.PhieuNhapID = pn.PhieuNhapID
                                                                                 WHERE sp.Deleted != 1 AND pn.PhieuNhapID = 8

SELECT DonGia FROM ChiTietPhieuNhap WHERE MaSanPhamChiTiet = 7 AND PhieuNhapID = 7

SELECT TOP 1 * FROM PhieuNhapKho ORDER BY PhieuNhapID DESC;

SELECT TOP 1 ncc.NhaCungCapID, ncc.TenNhaCungCap, ncc.SoDT, ncc.Email, ncc.DiaChi, pnct.PhuongThucNhap FROM PhieuNhapKho pn 
					INNER JOIN NhaCungCap ncc ON pn.NhaCungCapID = ncc.NhaCungCapID
					INNER JOIN ChiTietPhieuNhap pnct ON pn.PhieuNhapID = pnct.PhieuNhapID
						WHERE ncc.Deleted!=1 AND pn.PhieuNhapID = 8
								
SELECT * FROM ChiTietPhieuNhap WHERE PhieuNhapID = 8

DELETE FROM ChiTietPhieuNhap WHERE MaSanPhamChiTiet = 4 AND PhieuNhapID = 8

SELECT pn.PhieuNhapID, spct.MaSanPhamChiTiet, spct.TenSanPhamChiTiet, dm.TenDanhMuc
				, pn.DonGia, pn.SoLuong, pn.PhanTramThue
				, pn.DonGia * pn.SoLuong + (pn.DonGia * pn.SoLuong) * (CAST(pn.PhanTramThue AS FLOAT) / 100.0) AS 'TOTAL' FROM ChiTietPhieuNhap pn 
				INNER JOIN SanPhamChiTiet spct ON pn.MaSanPhamChiTiet = spct.MaSanPhamChiTiet
				INNER JOIN SanPham sp ON spct.MaSanPham = sp.MaSanPham
				INNER JOIN DanhMuc dm ON sp.MaDanhMuc = dm.MaDanhMuc
				INNER JOIN PhieuNhapKho pnk ON pn.PhieuNhapID = pnk.PhieuNhapID
						WHERE pnk.Deleted!=1 AND pnk.PhieuNhapID = 8

SELECT pn.PhieuNhapID, spct.MaSanPhamChiTiet, spct.TenSanPhamChiTiet, dm.TenDanhMuc
				, pn.DonGia, pn.SoLuong, pn.PhanTramThue
				, pn.DonGia * pn.SoLuong + (pn.DonGia * pn.SoLuong) * (CAST(pn.PhanTramThue AS FLOAT) / 100.0) AS 'TOTAL' FROM ChiTietPhieuNhap pn 
				INNER JOIN SanPhamChiTiet spct ON pn.MaSanPhamChiTiet = spct.MaSanPhamChiTiet
				INNER JOIN SanPham sp ON spct.MaSanPham = sp.MaSanPham
				INNER JOIN DanhMuc dm ON sp.MaDanhMuc = dm.MaDanhMuc
				INNER JOIN PhieuNhapKho pnk ON pn.PhieuNhapID = pnk.PhieuNhapID
						WHERE pnk.Deleted!=1 AND spct.MaSanPhamChiTiet = 6 AND pnk.PhieuNhapID = 8

SELECT SUM(pn.DonGia * pn.SoLuong + (pn.DonGia * pn.SoLuong) * (CAST(pn.PhanTramThue AS FLOAT) / 100.0)) AS 'TOTAL'
							FROM ChiTietPhieuNhap pn INNER JOIN PhieuNhapKho pnk ON pn.PhieuNhapID = pnk.PhieuNhapID
						      WHERE pnk.PhieuNhapID = 7

SELECT SUM((pn.DonGia * pn.SoLuong) * (CAST(pn.PhanTramThue AS FLOAT) / 100.0)) AS 'TaxTotal'
			FROM ChiTietPhieuNhap pn INNER JOIN PhieuNhapKho pnk ON pn.PhieuNhapID = pnk.PhieuNhapID
			WHERE pnk.PhieuNhapID = 7
SELECT SUM((hdct.SoLuong * hdct.DonGia) * (1 + sp.PhanTramThue/100)) AS 'total'
				FROM HoaDonChiTiet hdct
				INNER JOIN SanPhamChiTiet spct ON hdct.MaSanPhamChiTiet = spct.MaSanPhamChiTiet
				INNER JOIN SanPham sp ON sp.MaSanPham = spct.MaSanPham
				WHERE hdct.MaHoaDon = 1 AND hdct.Deleted !=1

SELECT * FROM HoaDonChiTiet
SELECT * FROM HoaDon
SELECT sp.MaSanPham, spct.TenSanPhamChiTiet, dm.TenDanhMuc, nh.TenNhanHang, ms.TenMau, sz.TenSize, cl.TenChatLieu
			, sp.GiaNhap, sp.GiaBan, sp.TrangThai, spct.SoLuongTon, kh.TenKhuVuc
							,spct.HinhAnh, spct.NgayNhapKho FROM SanPham sp 
										INNER JOIN SanPhamChiTiet spct ON sp.MaSanPham = spct.MaSanPham
										INNER JOIN DanhMuc dm ON sp.MaDanhMuc = dm.MaDanhMuc
										INNER JOIN NhanHang nh ON spct.NhanHangID = nh.NhanHangID
										INNER JOIN MauSac ms ON spct.MaMau = ms.MaMau
										INNER JOIN Size sz ON spct.MaSize = sz.MaSize
										INNER JOIN ChatLieu cl ON spct.MaChatLieu = cl.MaChatLieu
										INNER JOIN KhuVucKho kh ON sp.KhuVucID = kh.KhuVucID
											WHERE sp.Deleted!=1 AND sp.MaSanPham = 4

SELECT spct.MaSanPhamChiTiet, spct.TenSanPhamChiTiet
		, hdct.DonGia, hdct.SoLuong, sp.PhanTramThue
			, SUM(hdct.DonGia*hdct.SoLuong) AS 'TOTAL', SUM(hdct.DonGia * hdct.SoLuong * (1 + sp.PhanTramThue)) AS 'TOTAL_VAT'
				FROM HoaDonChiTiet hdct INNER JOIN SanPhamChiTiet spct ON hdct.MaSanPhamChiTiet = spct.MaSanPhamChiTiet
								 INNER JOIN SanPham sp ON sp.MaSanPham = spct.MaSanPham
									WHERE hdct.MaHoaDon = 2
										GROUP BY spct.MaSanPhamChiTiet, spct.TenSanPhamChiTiet
										, hdct.DonGia, hdct.SoLuong, sp.PhanTramThue

--BỔ SUNG BẢNG KM
CREATE TABLE KhuyenMai (
    MaKhuyenMai INT PRIMARY KEY IDENTITY(1,1),
    TenKhuyenMai NVARCHAR(255) NOT NULL,
    LoaiKhuyenMai NVARCHAR(50) NOT NULL, -- 'Voucher' hoặc 'DotGiamGia'
    MucGiamGia FLOAT, -- Có thể là số tiền giảm hoặc phần trăm giảm giá
    SoLuongVoucher INT, -- Sử dụng cho loại 'Voucher', NULL cho 'DotGiamGia'
    NgayBatDau DATE, -- NULL cho 'Voucher'
    NgayKetThuc DATE, -- NULL cho 'Voucher'
    MoTa NVARCHAR(250),
    TrangThai NVARCHAR(50) NOT NULL,
	Deleted INT -- 'DangDienRa', 'DaKetThuc', 'ChuaBatDau'
);

CREATE TABLE ApDungKhuyenMai (
    MaApDung INT PRIMARY KEY IDENTITY(1,1),
    MaKhuyenMai INT,
    MaHoaDon INT,
    GiaTriGiam INT,
	Deleted INT,
    FOREIGN KEY (MaKhuyenMai) REFERENCES KhuyenMai(MaKhuyenMai),
    FOREIGN KEY (MaHoaDon) REFERENCES HoaDon(MaHoaDon)
);

CREATE TABLE ChiTietKhuyenMai (
    MaChiTiet INT PRIMARY KEY IDENTITY(1,1),
    MaKhuyenMai INT,
    MaSanPham INT, -- NULL nếu áp dụng cho danh mục hoặc tất cả sản phẩm
    MaDanhMuc INT, -- NULL nếu áp dụng cho sản phẩm cụ thể
	Deleted INT,
    FOREIGN KEY (MaKhuyenMai) REFERENCES KhuyenMai(MaKhuyenMai),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham),
    FOREIGN KEY (MaDanhMuc) REFERENCES DanhMuc(MaDanhMuc)
);
