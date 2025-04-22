/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package da.service;

import da.model.HoaDonOnlineChiTiet;
import da.util.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class HoaDonOnlineService {
        private Connection conn;

    public HoaDonOnlineService() {
        conn = connectDB.getConnection();
    }
    
    public ArrayList<HoaDonOnlineChiTiet> searchHoaDonOnlineChiTiet(String keyword) {
    String SQL = """
        SELECT 
            hd.id AS hoadononlineID,
            hd.mahoadon AS MaHoaDon, 
            hd.trangthai AS TrangThai, 
            hd.ngaytao AS NgayTao, 
            hd.tenkhachhang AS TenKhachHang,
            hd.sodienthoai AS SoDienThoai,
            hd.diaChiGiaoHang AS DiaChiGiaoHang,
            hd.hinhthucvanchuyen AS HinhThucVanChuyen,
            ctht.id AS ChiTietID, 
            sp.tensp AS TenSanPham, 
            gh.soLuong AS SoLuong, 
            sp.gia AS GiaBan, 
                (gh.soLuong * sp.gia) AS TongTien, 
            gh.tenMauSac AS MauSac, 
            gh.kichThuoc AS KichThuoc 
        FROM ChiTietHoaDonOnline ctht
        JOIN HoaDonOnLine hd ON ctht.hoadononlineID = hd.id
        JOIN GioHang gh ON ctht.gioHangid = gh.id
        JOIN SanPham sp ON gh.idSanPham = sp.id
    """;

    if (keyword != null && !keyword.trim().isEmpty()) {
        SQL += """
            WHERE hd.mahoadon LIKE ? 
            OR hd.tenkhachhang LIKE ? 
            OR sp.tensp LIKE ? 
            OR CAST(hd.ngaytao AS NVARCHAR) LIKE ?
        """;
    }

    ArrayList<HoaDonOnlineChiTiet> list = new ArrayList<>();
    try {
        PreparedStatement ps = conn.prepareStatement(SQL);
        if (keyword != null && !keyword.trim().isEmpty()) {
            String searchPattern = "%" + keyword.trim() + "%";
            ps.setString(1, searchPattern); // Mã hóa đơn
            ps.setString(2, searchPattern); // Tên khách hàng
            ps.setString(3, searchPattern); // Tên sản phẩm
            ps.setString(4, searchPattern); // Ngày tạo
        }
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            HoaDonOnlineChiTiet ct = new HoaDonOnlineChiTiet();
            ct.setHoadononlineID(rs.getInt("hoadononlineID"));
            ct.setMaHD(rs.getString("MaHoaDon"));
            ct.setTrangThai(rs.getInt("TrangThai"));
            ct.setNgay(rs.getTimestamp("NgayTao"));
            ct.setTenKH(rs.getString("TenKhachHang"));
            ct.setSDT(rs.getString("SoDienThoai"));
            ct.setDiaChiGiaoHang(rs.getString("DiaChiGiaoHang"));
            ct.setHinhThucVanChuyen(rs.getString("HinhThucVanChuyen"));
            ct.setId(rs.getInt("ChiTietID"));
            ct.setTenSP(rs.getString("TenSanPham"));
            ct.setSoLuong(rs.getInt("SoLuong"));
            ct.setDonGia(rs.getBigDecimal("GiaBan"));
            ct.setTongTien(rs.getBigDecimal("TongTien"));
            ct.setMauSac(rs.getString("MauSac"));
            ct.setKichThuoc(rs.getString("KichThuoc"));

            list.add(ct);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}
    
    public List<HoaDonOnlineChiTiet> getAll() {
    List<HoaDonOnlineChiTiet> list = new ArrayList<>();
    String sql = """
            SELECT 
                hd.id AS hoadononlineID,
                hd.mahoadon AS MaHoaDon, 
                hd.trangthai AS TrangThai, 
                hd.ngaytao AS NgayTao, 
                hd.tenkhachhang AS TenKhachHang,
                hd.sodienthoai AS SoDienThoai,
                hd.diaChiGiaoHang AS DiaChiGiaoHang,
                hd.hinhthucvanchuyen AS HinhThucVanChuyen,
                ctht.id AS ChiTietID, 
                sp.tensp AS TenSanPham, 
                gh.soLuong AS SoLuong, 
                sp.gia AS GiaBan, 
                (gh.soLuong * sp.gia) AS TongTien, 
                gh.tenMauSac AS MauSac, 
                gh.kichThuoc AS KichThuoc 
            FROM ChiTietHoaDonOnline ctht
            JOIN HoaDonOnLine hd ON ctht.hoadononlineID = hd.id
            JOIN GioHang gh ON ctht.gioHangid = gh.id
            JOIN SanPham sp ON gh.idSanPham = sp.id;
        """;

    try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            HoaDonOnlineChiTiet ct = new HoaDonOnlineChiTiet();
            ct.setHoadononlineID(rs.getInt("hoadononlineID"));
            ct.setMaHD(rs.getString("MaHoaDon"));
            ct.setTrangThai(rs.getInt("TrangThai"));
            ct.setNgay(rs.getTimestamp("NgayTao"));
            ct.setTenKH(rs.getString("TenKhachHang"));
            ct.setSDT(rs.getString("SoDienThoai"));
            ct.setDiaChiGiaoHang(rs.getString("DiaChiGiaoHang"));
            ct.setHinhThucVanChuyen(rs.getString("HinhThucVanChuyen"));
            ct.setId(rs.getInt("ChiTietID"));
            ct.setTenSP(rs.getString("TenSanPham"));
            ct.setSoLuong(rs.getInt("SoLuong"));
            ct.setDonGia(rs.getBigDecimal("GiaBan"));
            ct.setTongTien(rs.getBigDecimal("TongTien"));
            ct.setMauSac(rs.getString("MauSac"));
            ct.setKichThuoc(rs.getString("KichThuoc"));
            list.add(ct);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}
    
    public int addHoaDonOnline(String maHoaDon, String soDienThoai, String tenKhachHang, String diaChiGiaoHang, String hinhThucThanhToan, String hinhThucVanChuyen, String luuy) {
        String insertHoaDonSQL = """
            INSERT INTO HoaDonOnLine (mahoadon, sodienthoai, tenkhachhang, diaChiGiaoHang, hinhthucthanhtoan, hinhthucvanchuyen, luuy)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(insertHoaDonSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, maHoaDon);
            ps.setString(2, soDienThoai);
            ps.setString(3, tenKhachHang);
            ps.setString(4, diaChiGiaoHang);
            ps.setString(5, hinhThucThanhToan);
            ps.setString(6, hinhThucVanChuyen);
            ps.setString(7, luuy);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Thêm hóa đơn thất bại, không có dòng nào được tạo.");
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
    
    public boolean addChiTietHoaDon(List<HoaDonOnlineChiTiet> chiTietList) {
    // Chỉ thêm dữ liệu vào các cột hoadonID và gioHangid
    String insertChiTietSQL = """
        INSERT INTO ChiTietHoaDonOnline (hoadononlineID, gioHangid)
        VALUES (?, ?)
    """;

    try (PreparedStatement ps = conn.prepareStatement(insertChiTietSQL)) {
        for (HoaDonOnlineChiTiet chiTiet : chiTietList) {
            ps.setInt(1, chiTiet.getHoadononlineID()); // Gán hoadonID
            ps.setInt(2, chiTiet.getGioHangID()); // Gán gioHangid
            ps.addBatch(); // Thêm vào batch
        }

        int[] results = ps.executeBatch(); // Thực thi batch
        for (int result : results) {
            if (result == PreparedStatement.EXECUTE_FAILED) {
                throw new SQLException("Lỗi khi thêm chi tiết hóa đơn.");
            }
        }

        return true; // Thành công
    } catch (SQLException e) {
        e.printStackTrace(); // Ghi log lỗi
    }

    return false; // Trả về false nếu có lỗi
}
    
    public boolean duyetDon() {
    String sql = """
            UPDATE HoaDonOnLine
            SET trangthai = 1
            WHERE trangthai = 0;
        """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        int rowsAffected = ps.executeUpdate(); // Thực thi câu lệnh SQL
        return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng được cập nhật
    } catch (SQLException e) {
        e.printStackTrace(); // In lỗi ra console để kiểm tra
        return false; // Trả về false nếu xảy ra lỗi
    }
}
    
    public boolean ChuanBiHang() {
    String sql = """
            UPDATE HoaDonOnLine
            SET trangthai = 2
            WHERE trangthai = 1;
        """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        int rowsAffected = ps.executeUpdate(); // Thực thi câu lệnh SQL
        return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng được cập nhật
    } catch (SQLException e) {
        e.printStackTrace(); // In lỗi ra console để kiểm tra
        return false; // Trả về false nếu xảy ra lỗi
    }
}
    
    public boolean giaoHang() {
    String sql = """
            UPDATE HoaDonOnLine
            SET trangthai = 3
            WHERE trangthai = 2;
        """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        int rowsAffected = ps.executeUpdate(); // Thực thi câu lệnh SQL
        return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng được cập nhật
    } catch (SQLException e) {
        e.printStackTrace(); // In lỗi ra console để kiểm tra
        return false; // Trả về false nếu xảy ra lỗi
    }
}
    
    public boolean hoanTat() {
    String sql = """
            UPDATE HoaDonOnLine
            SET trangthai = 4
            WHERE trangthai = 3;
        """;

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        int rowsAffected = ps.executeUpdate(); // Thực thi câu lệnh SQL
        return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng được cập nhật
    } catch (SQLException e) {
        e.printStackTrace(); // In lỗi ra console để kiểm tra
        return false; // Trả về false nếu xảy ra lỗi
    }
}
}
