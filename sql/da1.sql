create database da1;

use da1;

select * from NguoiDung
select * from NhanVien
select * from SanPham

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

SELECT v.idVaiTro, d.id AS idChucNang, d.tenChucNang 
FROM VaiTro_ChucNang v
JOIN DanhMucChucNang d ON v.idChucNang = d.id
WHERE v.idVaiTro = 1;


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

--drop table NhanVien
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
INSERT INTO NhanVien (maNV, ho, ten, email, chucVu, ngayVaoLam, idVaiTro, gioiTinh, ngaySinh, sdt, diaChi, trangThai) 
VALUES 
('NV000', N'Nguyễn Văn', N'Mạnh', 'admin@email.com', N'Admin', '01/01/2023', 1, 1, '15/06/1985', '0987654321', N'123 Đường ABC, Quận 1, TP.HCM', N'Đang Làm'),
('NV001', N'Nguyễn Văn', N'F', 'nvf@email.com', N'Nhân viên kho', '15/02/2023', 2, 1, '22/09/1990', '0912345678', N'456 Đường XYZ, Quận 2, TP.HCM', N'Đang Làm'),
('NV002', N'Trần Thị', N'G', 'ttg@email.com', N'Nhân viên bán hàng', '20/03/2023', 2, 0, '10/12/1993', '0938765432', N'789 Đường LMN, Quận 3, TP.HCM', N'Nghỉ việc'),
('NV003', N'Lê Hoàng', N'H', 'lhh@email.com', N'Nhân viên bán hàng', '10/04/2023', 2, 1, '05/03/1995', '0976543210', N'101 Đường PQR, Quận 4, TP.HCM', N'Đang Làm'),
('NV004', N'Phạm Minh', N'I', 'pmi@email.com', N'Nhân viên kho', '25/05/2023', 2, 1, '18/07/1998', '0954321876', N'202 Đường STU, Quận 5, TP.HCM', N'Đang thử việc');



CREATE TABLE ChatLieu (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenChatLieu NVARCHAR(100) NOT NULL UNIQUE
);

-- Thêm dữ liệu vào bảng Chất liệu
INSERT INTO ChatLieu (tenChatLieu) VALUES 
(N'Nhựa'), 
(N'Kim loại'), 
(N'Gỗ'), 
(N'Vải'), 
(N'Kính');

CREATE TABLE XuatXu (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenXuatXu NVARCHAR(100) NOT NULL UNIQUE
);

-- Thêm dữ liệu vào bảng Xuất xứ
INSERT INTO XuatXu (tenXuatXu) VALUES 
(N'Việt Nam'), 
(N'Nhật Bản'), 
(N'Hàn Quốc'), 
(N'Mỹ'), 
(N'Trung Quốc');
drop table KichThuoc
CREATE TABLE KichThuoc (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenKT NVARCHAR(100) NOT NULL UNIQUE
);

-- Thêm dữ liệu vào bảng Kích thước
INSERT INTO KichThuoc (tenKT) VALUES 
(N'Nhỏ'), 
(N'Vừa'), 
(N'Lớn'), 
(N'Rộng'), 
(N'Cao');

CREATE TABLE MauSac (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenMau NVARCHAR(50) NOT NULL UNIQUE
);

-- Thêm dữ liệu vào bảng Màu sắc
INSERT INTO MauSac (tenMau) VALUES 
(N'Đỏ'), 
(N'Xanh Dương'), 
(N'Xanh Lá'), 
(N'Vàng'), 
(N'Đen'), 
(N'Trắng');

CREATE TABLE NhaCungCap (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenNCC NVARCHAR(255) NOT NULL UNIQUE,
    diaChi NVARCHAR(255) NOT NULL,
    soDienThoai NVARCHAR(15) NOT NULL UNIQUE,
    email NVARCHAR(100) UNIQUE NOT NULL
);

-- Thêm dữ liệu vào bảng Nhà cung cấp
INSERT INTO NhaCungCap (tenNCC, diaChi, soDienThoai, email) VALUES 
(N'Công ty A', N'123 Đường ABC, Hà Nội', '0123456789', 'contact@congtya.com'),
(N'Công ty B', N'456 Đường XYZ, TP. Hồ Chí Minh', '0987654321', 'info@congtyb.com'),
(N'Công ty C', N'789 Đường DEF, Đà Nẵng', '0345678901', 'support@congtyc.com'),
(N'Công ty D', N'101 Đường GHI, Hải Phòng', '0765432109', 'sales@congtyd.com'),
(N'Công ty E', N'202 Đường JKL, Cần Thơ', '0923456781', 'admin@congtye.com');

drop table SanPham
CREATE TABLE SanPham (
    id INT IDENTITY(1,1) PRIMARY KEY,
    masp NVARCHAR(50) NOT NULL,
    tensp NVARCHAR(255) NOT NULL,
    mota NVARCHAR(500),
    gia DECIMAL(18,2) NOT NULL,
    soluongton INT DEFAULT 0,  -- Available stock
    idChatLieu INT,           -- Material (Foreign Key)
    idXuatXu INT,             -- Country of origin (Foreign Key)
    idKichThuoc INT,          -- Size (Foreign Key)
    idMauSac INT,             -- Color (Foreign Key)
    idNhaCungCap INT,         -- Supplier (Foreign Key)
    hinhanh NVARCHAR(255),    -- Path to the image
    FOREIGN KEY (idChatLieu) REFERENCES ChatLieu(id),
    FOREIGN KEY (idXuatXu) REFERENCES XuatXu(id),
    FOREIGN KEY (idKichThuoc) REFERENCES KichThuoc(id),
    FOREIGN KEY (idMauSac) REFERENCES MauSac(id),
    FOREIGN KEY (idNhaCungCap) REFERENCES NhaCungCap(id)
);
 
-- Inserting data into SanPham table
INSERT INTO SanPham (masp, tensp, mota, gia, soluongton, idChatLieu, idXuatXu, idKichThuoc, idMauSac, idNhaCungCap, hinhanh)
VALUES 
('SP001', N'Áo Thun Nam', N'Áo thun nam chất liệu cotton, thoáng mát, dễ chịu.', 150000.00, 100, 1, 1, 1, 1, 1, N'path_to_image/SP001.jpg'),  -- Available product
('SP002', N'Quần Jean Nữ', N'Quần jean nữ phong cách trẻ trung, dễ phối đồ.', 250000.00, 50, 2, 2, 2, 2, 2, N'path_to_image/SP002.jpg'),  -- Available product
('SP003', N'Giày Thể Thao Nam', N'Giày thể thao nam, thoải mái, phù hợp với nhiều hoạt động.', 350000.00, 0, 1, 3, 3, 3, 3, N'path_to_image/SP003.jpg'),  -- Not available product
('SP004', N'Váy Đầm Nữ', N'Váy đầm nữ thanh lịch, phù hợp cho các dịp đặc biệt.', 400000.00, 70, 3, 4, 4, 4, 4, N'path_to_image/SP004.jpg'),  -- Available product
('SP005', N'Kính Mắt Nam', N'Kính mắt nam thời trang, bảo vệ mắt khỏi tia UV.', 200000.00, 120, 4, 5, 5, 5, 5, N'path_to_image/SP005.jpg');  -- Available product

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
    ngayThem DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (idNguoiDung) REFERENCES NguoiDung(id),
    FOREIGN KEY (idSanPham) REFERENCES SanPham(id)
);



SELECT gh.id, gh.idNguoiDung, gh.idSanPham, sp.maSP, sp.tenSP, gh.tongTien, gh.soLuong, gh.ngayThem 
FROM GioHang gh  
JOIN SanPham sp ON gh.idSanPham = sp.id 
JOIN NguoiDung nd ON gh.idNguoiDung = nd.id 
WHERE nd.email = 'admin@email.com';

CREATE TABLE KhuVucKho (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tenKhuVuc NVARCHAR(100) NOT NULL,
    moTa NVARCHAR(255)
);

INSERT INTO KhuVucKho (tenKhuVuc, moTa) VALUES
(N'Khu vực A', N'Lưu trữ hàng điện tử'),
(N'Khu vực B', N'Hàng gia dụng và nhà bếp'),
(N'Khu vực C', N'Thiết bị văn phòng'),
(N'Khu vực D', N'Hàng dễ vỡ'),
(N'Khu vực E', N'Khu vực lưu trữ tổng hợp');


drop table KhoHang
CREATE TABLE KhoHang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    idSanPham INT NOT NULL,
    idKhuVucKho INT NOT NULL,
    ngayNhap DATETIME DEFAULT GETDATE(),

    FOREIGN KEY (idSanPham) REFERENCES SanPham(id),
    FOREIGN KEY (idKhuVucKho) REFERENCES KhuVucKho(id),
);


select *from SanPham


-- Thêm dữ liệu vào bảng KhoHang
INSERT INTO KhoHang (idSanPham, idKhuVucKho, ngayNhap) VALUES
(1, 1, '2025-04-01'), -- Sản phẩm SP001 lưu trữ tại khu vực A
(2, 1, '2025-04-02'), -- Sản phẩm SP002 lưu trữ tại khu vực A
(3, 2, '2025-04-03'), -- Sản phẩm SP003 lưu trữ tại khu vực B
(4, 2, '2025-04-04'), -- Sản phẩm SP004 lưu trữ tại khu vực B
(5, 3, '2025-04-05'), -- Sản phẩm SP005 lưu trữ tại khu vực C
(1, 3, '2025-04-06'), -- Sản phẩm SP001 lưu trữ tại khu vực C
(2, 4, '2025-04-07'), -- Sản phẩm SP002 lưu trữ tại khu vực D
(3, 4, '2025-04-08'), -- Sản phẩm SP003 lưu trữ tại khu vực D
(4, 5, '2025-04-09'), -- Sản phẩm SP004 lưu trữ tại khu vực E
(5, 5, '2025-04-10'); -- Sản phẩm SP005 lưu trữ tại khu vực E

-- Kiểm tra dữ liệu trong bảng KhoHang
SELECT * FROM KhoHang;