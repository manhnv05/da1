create database da1;

use da1;


select * from NguoiDung
select * from NhanVien
select * from SanPham
SELECT * FROM VaiTro;
DELETE FROM NguoiDung WHERE id IN (10, 11, 12);

drop table VaiTro
CREATE TABLE VaiTro (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenVaiTro NVARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE DanhMucChucNang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenChucNang NVARCHAR(255) NOT NULL UNIQUE
);

drop table VaiTro_ChucNang
CREATE TABLE VaiTro_ChucNang (
    idVaiTro INT,
    idChucNang INT,
    FOREIGN KEY (idVaiTro) REFERENCES VaiTro(id),
    FOREIGN KEY (idChucNang) REFERENCES DanhMucChucNang(id),
    PRIMARY KEY (idVaiTro, idChucNang)
);


--drop table NguoiDung
CREATE TABLE NguoiDung (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ho NVARCHAR(255) NOT NULL,
    ten NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) UNIQUE NOT NULL,
    matKhau NVARCHAR(255) NOT NULL,
    idVaiTro INT DEFAULT 3,  -- Mặc định là Khách hàng
    ngayTao DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (idVaiTro) REFERENCES VaiTro(id)
);

drop table NhanVien
CREATE TABLE NhanVien (
    id INT IDENTITY(1,1) PRIMARY KEY,
    idVaiTro INT DEFAULT 2,  -- Có thể là Admin hoặc Nhân viên
    maNV NVARCHAR(255) NOT NULL,
    ho NVARCHAR(255) NOT NULL,
	ten NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) UNIQUE NOT NULL,
	diaChi NVARCHAR(255) NOT NULL,
    chucVu NVARCHAR(50) NOT NULL,
    ngayVaoLam NVARCHAR(50) NOT NULL,
    gioiTinh BIT NOT NULL,  -- 1: Nam, 0: Nữ
    ngaySinh NVARCHAR(50) NOT NULL,
    sdt NVARCHAR(15) NOT NULL, -- Thêm số điện thoại
    trangThai NVARCHAR(50) NOT NULL,
    FOREIGN KEY (idVaiTro) REFERENCES VaiTro(id)
);
ALTER TABLE NhanVien
ADD statuss BIT DEFAULT 1; -- 1: Hiển thị (hoạt động), 0: Đã ẩn/xóa

select * from NhanVien
-- =============================
-- 1. Thêm dữ liệu vào bảng VaiTro
-- =============================
INSERT INTO VaiTro (tenVaiTro) VALUES 
('Admin'),
('NhanVien'),
('KhachHang');

-- =============================
-- 2. Thêm dữ liệu vào bảng DanhMucChucNang
-- =============================
INSERT INTO DanhMucChucNang (tenChucNang) VALUES 
(N'Trang Chủ'),
(N'Quản Lý Sản Phẩm'),
(N'Thuộc Tính'),
(N'Đơn Hàng'),
(N'Voucher'),
(N'Kho Hàng'),
(N'Nhân Viên'),
(N'Thống Kê'),
(N'Thông tin cá nhân'),
(N'Sản Phẩm'),
(N'Giỏ Hàng'),
(N'Logout');



-- =============================
-- 3. Phân quyền VaiTro_ChucNang
-- =============================
INSERT INTO VaiTro_ChucNang (idVaiTro, idChucNang) VALUES
-- Admin có toàn quyền
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),(1, 6),(1, 7),(1, 8),(1, 9),(1, 12),
-- Nhân viên chỉ quản lý sản phẩm và đơn hàng
(2, 1), (2, 4),(2,9), (2, 12),
-- Khách hàng chỉ xem đơn hàng và thanh toán
(3, 1),(3, 9),(3, 10), (3, 11),(3, 12);

-- =============================
-- 4. Thêm dữ liệu vào bảng NguoiDung (Khách hàng)
-- =============================
INSERT INTO NguoiDung (ho, ten, email, matKhau, idVaiTro) VALUES 
(N'Nguyễn Văn', N'Mạnh', 'admin@email.com', 'admin123', 1),
(N'Trần Thị',N'B', 'tranb@email.com', '654321', 2),
(N'Lê Văn',N'C', 'levanc@email.com', 'abcdef', 3),
(N'Phạm Thị',N'D', 'phamtd@email.com', 'xyz789', 3),
(N'Hoàng Văn',N'E', 'hoange@email.com', 'hoang123', 3);

-- =============================
-- 5. Thêm dữ liệu vào bảng NhanVien (Admin + Nhân viên)
INSERT INTO NhanVien (maNV, ho, ten, email, chucVu, ngayVaoLam, idVaiTro, gioiTinh, ngaySinh, sdt, diaChi, trangThai, statuss) 
VALUES 
('NV000', N'Nguyễn Văn', N'Mạnh', 'admin@email.com', N'Admin', '01/01/2023', 1, 1, '15/06/1985', '0987654321', N'123 Đường ABC, Quận 1, TP.HCM', N'Đang Làm', 1),
('NV001', N'Nguyễn Văn', N'F', 'nvf@email.com', N'Nhân viên kho', '15/02/2023', 2, 1, '22/09/1990', '0912345678', N'456 Đường XYZ, Quận 2, TP.HCM', N'Đang Làm', 1),
('NV002', N'Trần Thị', N'G', 'ttg@email.com', N'Nhân viên bán hàng', '20/03/2023', 2, 0, '10/12/1993', '0938765432', N'789 Đường LMN, Quận 3, TP.HCM', N'Nghỉ việc', 1),
('NV003', N'Lê Hoàng', N'H', 'lhh@email.com', N'Nhân viên bán hàng', '10/04/2023', 2, 1, '05/03/1995', '0976543210', N'101 Đường PQR, Quận 4, TP.HCM', N'Đang Làm', 1),
('NV004', N'Phạm Minh', N'I', 'pmi@email.com', N'Nhân viên kho', '25/05/2023', 2, 1, '18/07/1998', '0954321876', N'202 Đường STU, Quận 5, TP.HCM', N'Đang thử việc', 1);



CREATE TABLE ChatLieu (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenChatLieu NVARCHAR(100) NOT NULL UNIQUE
);
ALTER TABLE ChatLieu
ADD statuss BIT DEFAULT 1;

-- Thêm dữ liệu vào bảng Chất liệu
INSERT INTO ChatLieu (tenChatLieu, statuss) VALUES 
(N'Nhựa', 1), 
(N'Kim loại', 1), 
(N'Gỗ', 1), 
(N'Vải', 1), 
(N'Kính', 1);

CREATE TABLE XuatXu (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenXuatXu NVARCHAR(100) NOT NULL UNIQUE
);
ALTER TABLE XuatXu
ADD statuss BIT DEFAULT 1;

-- Thêm dữ liệu vào bảng Xuất xứ
INSERT INTO XuatXu (tenXuatXu, statuss) VALUES 
(N'Việt Nam', 1), 
(N'Nhật Bản', 1), 
(N'Hàn Quốc', 1), 
(N'Mỹ', 1), 
(N'Trung Quốc', 1);
drop table KichThuoc
CREATE TABLE KichThuoc (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenKT NVARCHAR(100) NOT NULL UNIQUE
);
ALTER TABLE KichThuoc
ADD statuss BIT DEFAULT 1;

-- Thêm dữ liệu vào bảng Kích thước
INSERT INTO KichThuoc (tenKT, statuss) VALUES 
(N'Nhỏ', 1), 
(N'Vừa', 1), 
(N'Lớn', 1), 
(N'Rộng', 1), 
(N'Cao', 1);

CREATE TABLE MauSac (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenMau NVARCHAR(50) NOT NULL UNIQUE
);
ALTER TABLE MauSac
ADD statuss BIT DEFAULT 1;

-- Thêm dữ liệu vào bảng Màu sắc
INSERT INTO MauSac (tenMau, statuss) VALUES 
(N'Đỏ', 1), 
(N'Xanh Dương', 1), 
(N'Xanh Lá', 1), 
(N'Vàng', 1), 
(N'Đen', 1), 
(N'Trắng', 1);

CREATE TABLE NhaCungCap (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenNCC NVARCHAR(255) NOT NULL UNIQUE,
    diaChi NVARCHAR(255) NOT NULL,
    soDienThoai NVARCHAR(15) NOT NULL UNIQUE,
    email NVARCHAR(100) UNIQUE NOT NULL
);
ALTER TABLE NhaCungCap
ADD statuss BIT DEFAULT 1;

-- Thêm dữ liệu vào bảng Nhà cung cấp
INSERT INTO NhaCungCap (tenNCC, diaChi, soDienThoai, email, statuss) VALUES 
(N'Công ty A', N'123 Đường ABC, Hà Nội', '0123456789', 'contact@congtya.com', 1),
(N'Công ty B', N'456 Đường XYZ, TP. Hồ Chí Minh', '0987654321', 'info@congtyb.com', 1),
(N'Công ty C', N'789 Đường DEF, Đà Nẵng', '0345678901', 'support@congtyc.com', 1),
(N'Công ty D', N'101 Đường GHI, Hải Phòng', '0765432109', 'sales@congtyd.com', 1),
(N'Công ty E', N'202 Đường JKL, Cần Thơ', '0923456781', 'admin@congtye.com', 1);

drop table SanPham
CREATE TABLE SanPham (
    id INT IDENTITY(1,1) PRIMARY KEY,
    masp NVARCHAR(50) NOT NULL,
    tensp NVARCHAR(255) NOT NULL,
    mota NVARCHAR(500),
    gia DECIMAL(18,0) NOT NULL,
    soluongton INT DEFAULT 0,  -- Available stock
    idChatLieu INT,           -- Material (Foreign Key)
    idXuatXu INT,             -- Country of origin (Foreign Key)
    idKichThuoc INT,          -- Size (Foreign Key)
    idMauSac INT,             -- Color (Foreign Key)
    idNhaCungCap INT,        -- Supplier (Foreign Key)
	idKhuVucKho INT,
    hinhanh NVARCHAR(255),    -- Path to the image
    FOREIGN KEY (idChatLieu) REFERENCES ChatLieu(id),
    FOREIGN KEY (idXuatXu) REFERENCES XuatXu(id),
    FOREIGN KEY (idKichThuoc) REFERENCES KichThuoc(id),
    FOREIGN KEY (idMauSac) REFERENCES MauSac(id),
    FOREIGN KEY (idNhaCungCap) REFERENCES NhaCungCap(id),
	FOREIGN KEY (idKhuVucKho) REFERENCES KhuVucKho(id)
);
ALTER TABLE SanPham
ADD trangThai BIT DEFAULT 1; -- 1: Hiển thị (hoạt động), 0: Đã ẩn/xóa

 
-- Inserting data into SanPham table
INSERT INTO SanPham (masp, tensp, mota, gia, soluongton, idChatLieu, idXuatXu, idKichThuoc, idMauSac, idNhaCungCap, idKhuVucKho, hinhanh)
VALUES 
('SP001', N'Áo Thun Nam', N'Áo thun nam chất liệu cotton, thoáng mát, dễ chịu.', 150000.00, 100, 1, 1, 1, 1, 1, 1, N'D:\da1\da1\src\da\icon\png\ao1.png'),  -- Available product
('SP002', N'Quần Jean Nữ', N'Quần jean nữ phong cách trẻ trung, dễ phối đồ.', 250000.00, 50, 2, 2, 2, 2, 2, 2, N'D:\da1\da1\src\da\icon\png\aobrazil.png'),  -- Available product
('SP003', N'Giày Thể Thao Nam', N'Giày thể thao nam, thoải mái, phù hợp với nhiều hoạt động.', 350000.00, 0, 1, 3, 3, 3, 3, 3, N'D:\da1\da1\src\da\icon\png\aobodaonha.png'),  -- Not available product
('SP004', N'Váy Đầm Nữ', N'Váy đầm nữ thanh lịch, phù hợp cho các dịp đặc biệt.', 400000.00, 70, 3, 4, 4, 4, 4, 4, N'D:\da1\da1\src\da\icon\png\images.png'),  -- Available product
('SP005', N'Kính Mắt Nam', N'Kính mắt nam thời trang, bảo vệ mắt khỏi tia UV.', 200000.00, 120, 4, 5, 5, 5, 5, 5, N'D:\da1\da1\src\da\icon\png\aomu.jpg');  -- Available product

select * from GioHang
drop table GioHang
DELETE FROM GioHang WHERE id IN (12, 13);
CREATE TABLE GioHang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    idNguoiDung INT NOT NULL,
    idSanPham INT NOT NULL,
    tenMauSac NVARCHAR(50),         -- Tên màu sắc thay vì idMauSac
    kichThuoc NVARCHAR(50),         -- Kích thước thay vì idKichThuoc
    tongTien DECIMAL(18, 0) NOT NULL,
    soLuong INT DEFAULT 1,
    FOREIGN KEY (idNguoiDung) REFERENCES NguoiDung(id),
    FOREIGN KEY (idSanPham) REFERENCES SanPham(id)
);
ALTER TABLE GioHang
ADD trangThai BIT DEFAULT 1; -- 1: Hiển thị (hoạt động), 0: Đã ẩn/xóa


drop table KhuVucKho

CREATE TABLE KhuVucKho (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenKhuVuc NVARCHAR(100) NOT NULL,
    moTa NVARCHAR(255)
);
ALTER TABLE KhuVucKho
ADD statuss BIT DEFAULT 1; -- 1: Hiển thị (hoạt động), 0: Đã ẩn/xóa

INSERT INTO KhuVucKho (tenKhuVuc, moTa, statuss) VALUES
(N'Khu vực A', N'Lưu trữ hàng điện tử', 1),
(N'Khu vực B', N'Hàng gia dụng và nhà bếp', 1),
(N'Khu vực C', N'Thiết bị văn phòng', 1),
(N'Khu vực D', N'Hàng dễ vỡ', 1),
(N'Khu vực E', N'Khu vực lưu trữ tổng hợp', 1);




drop table NhapKho

select *from NhapKho
CREATE TABLE NhapKho (
    id INT PRIMARY KEY IDENTITY(1,1),
    maNhap VARCHAR(50),
    idNhaCungCap INT,
    idNhanVien INT,
    idSanPham INT,
	soLuong INT,
    ngayNhap DATETIME,
    tongTien DECIMAL(18, 0),

    FOREIGN KEY (idNhaCungCap) REFERENCES NhaCungCap(id),
    FOREIGN KEY (idNhanVien) REFERENCES NhanVien(id),
    FOREIGN KEY (idSanPham) REFERENCES SanPham(id)
);
ALTER TABLE NhapKho
ADD statuss BIT DEFAULT 1; -- 1: Hiển thị (hoạt động), 0: Đã ẩn/xóa

INSERT INTO NhapKho (maNhap, idNhaCungCap, idNhanVien, idSanPham, soLuong, ngayNhap, tongTien, statuss)
VALUES 
('NK001', 1, 1, 1,  50, '2025-04-01 10:00:00', 7500000, 1), -- Nhập 50 sản phẩm SP001 từ NCC A, NV Admin, khu vực A
('NK002', 2, 2, 2,  30, '2025-04-02 11:30:00', 7500000, 1), -- Nhập 30 sản phẩm SP002 từ NCC B, NV F, khu vực A
('NK003', 3, 3, 3,  20, '2025-04-03 09:15:00', 7000000, 1), -- Nhập 20 sản phẩm SP003 từ NCC C, NV G, khu vực B
('NK004', 4, 4, 4,  40, '2025-04-04 14:20:00', 16000000, 1), -- Nhập 40 sản phẩm SP004 từ NCC D, NV H, khu vực B
('NK005', 5, 5, 5,  60, '2025-04-05 08:45:00', 12000000, 1), -- Nhập 60 sản phẩm SP005 từ NCC E, NV I, khu vực C
('NK006', 1, 1, 1,  25, '2025-04-06 13:00:00', 3750000, 1), -- Nhập 25 sản phẩm SP001 từ NCC A, NV Admin, khu vực C
('NK007', 2, 2, 2,  15, '2025-04-07 15:10:00', 3750000, 1), -- Nhập 15 sản phẩm SP002 từ NCC B, NV F, khu vực D
('NK008', 3, 3, 3,  10, '2025-04-08 10:30:00', 3500000, 1), -- Nhập 10 sản phẩm SP003 từ NCC C, NV G, khu vực D
('NK009', 4, 4, 4,  30, '2025-04-09 11:45:00', 12000000, 1), -- Nhập 30 sản phẩm SP004 từ NCC D, NV H, khu vực E
('NK010', 5, 5, 5,  50, '2025-04-10 09:00:00', 10000000, 1); -- Nhập 50 sản phẩm SP005 từ NCC E, NV I, khu vực E

select *from HoaDonOnline
drop table HoaDonOnLine
CREATE TABLE HoaDonOnLine (
    id INT IDENTITY PRIMARY KEY,
    mahoadon VARCHAR(50) NOT NULL,
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai INT DEFAULT 0, -- 0: Chờ xác nhận, 1: Đang giao, 2: Hoàn tất, 3: Hủy
	sodienthoai NVARCHAR(15),
	tenkhachhang NVARCHAR(100),
	diaChiGiaoHang NVARCHAR(255),
    hinhthucthanhtoan NVARCHAR(50),
	hinhthucvanchuyen NVARCHAR(50),
	luuy NVARCHAR(255)
);

select *from ChiTietHoaDonOnline

drop table ChiTietHoaDonOnline
CREATE TABLE ChiTietHoaDonOnline (
    id INT IDENTITY PRIMARY KEY,
    hoadononlineID INT NOT NULL,
    gioHangid INT NOT NULL,
    FOREIGN KEY (hoadononlineID) REFERENCES HoaDonOnLine(id),
    FOREIGN KEY (gioHangid) REFERENCES GioHang(id)
);


select *from SanPham


-- Kiểm tra dữ liệu trong bảng KhoHang
select *from HoaDonTaiQuay
select *from ChiTietHoaDonTaiQuay
drop table HoaDonTaiQuay
CREATE TABLE HoaDonTaiQuay (
    id INT IDENTITY PRIMARY KEY,
    mahoadon VARCHAR(50) NOT NULL,
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai INT NOT NULL, -- 0: Chưa thanh toán, 1: Đã thanh toán, 2: Hủy
    ghichu NVARCHAR(255),
    tenkhachhang NVARCHAR(100),
    nhanvienID INT NOT NULL,
    hinhthucthanhtoan NVARCHAR(50), -- Tiền mặt, Chuyển khoản, v.v.
    FOREIGN KEY (nhanvienID) REFERENCES NhanVien(id)
);
drop table ChiTietHoaDonTaiQuay

CREATE TABLE ChiTietHoaDonTaiQuay (
    id INT IDENTITY PRIMARY KEY,
    hoadonID INT NOT NULL,
    gioHangid INT NOT NULL,
    FOREIGN KEY (hoadonID) REFERENCES HoaDonTaiQuay(id),
    FOREIGN KEY (gioHangid) REFERENCES GioHang(id)
);