
package da.service;

import da.model.HoaDon;
import da.model.HoaDonChiTiet;
import da.util.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class HoaDonService {
    private Connection conn;

    public HoaDonService() {
        conn = connectDB.getConnection();
    }
    
public List<HoaDonChiTiet> getAll() {
    List<HoaDonChiTiet> list = new ArrayList<>();
    String sql = """
            SELECT 
                hd.mahoadon AS MaHoaDon, 
                hd.trangthai AS TrangThai, 
                hd.ngaytao AS NgayTao, 
                nv.ho + ' ' + nv.ten AS TenNhanVien, 
                hd.tenkhachhang AS TenKhachHang, 
                ctht.id AS ChiTietID, 
                sp.tensp AS TenSanPham, 
                gh.soLuong AS SoLuong, 
                sp.gia AS GiaBan, 
                (gh.soLuong * sp.gia) AS TongTien, 
                gh.tenMauSac AS MauSac, 
                gh.kichThuoc AS KichThuoc 
            FROM ChiTietHoaDonTaiQuay ctht
            JOIN HoaDonTaiQuay hd ON ctht.hoadonID = hd.id
            JOIN NhanVien nv ON hd.nhanvienID = nv.id
            JOIN GioHang gh ON ctht.gioHangid = gh.id
            JOIN SanPham sp ON gh.idSanPham = sp.id;
        """;
    try (
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()
    ) {
        while (rs.next()) {
            HoaDonChiTiet ct = new HoaDonChiTiet();
            ct.setMaHD(rs.getString("MaHoaDon")); // Mã hóa đơn
            ct.setTrangThai(rs.getString("TrangThai")); // Trạng thái hóa đơn
            ct.setNgay(rs.getTimestamp("NgayTao")); // Ngày tạo hóa đơn
            ct.setTenNV(rs.getString("TenNhanVien")); // Tên nhân viên
            ct.setTenKH(rs.getString("TenKhachHang")); // Tên khách hàng
            ct.setId(rs.getInt("ChiTietID")); // ID chi tiết hóa đơn
            ct.setTenSP(rs.getString("TenSanPham")); // Tên sản phẩm
            ct.setSoLuong(rs.getInt("SoLuong")); // Số lượng sản phẩm
            ct.setDonGia(rs.getBigDecimal("GiaBan")); // Giá bán
            ct.setTongTien(rs.getBigDecimal("TongTien")); // Tổng tiền
            ct.setMauSac(rs.getString("MauSac")); // Màu sắc
            ct.setKichThuoc(rs.getString("KichThuoc")); // Kích thước

            list.add(ct);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}
    
    public List<HoaDon> getAllHD() {
    List<HoaDon> list = new ArrayList<>();
    String sql = """
        SELECT 
            hd.id,
            hd.mahoadon,
            hd.ngaytao,
            hd.trangthai,
            hd.ghichu,
            hd.tenkhachhang,
            hd.nhanvienID,
            hd.hinhthucthanhtoan,
            nv.ho + ' ' + nv.ten AS tenNhanVien
        FROM HoaDonTaiQuay hd
        JOIN NhanVien nv ON hd.nhanvienID = nv.id
    """;

    try (
        PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            HoaDon hd = new HoaDon();
            hd.setId(rs.getInt("id"));
            hd.setMaHoaDon(rs.getString("mahoadon"));
            hd.setNgayTao(rs.getTimestamp("ngaytao"));
            hd.setTrangThai(rs.getString("trangthai"));
            hd.setGhiChu(rs.getString("ghichu"));
            hd.setTenKhachHang(rs.getString("tenkhachhang"));
            hd.setNhanVienID(rs.getInt("nhanvienID"));
            hd.setHinhThucThanhToan(rs.getString("hinhthucthanhtoan"));
            hd.setTenNhanVien(rs.getString("tenNhanVien")); // thêm tên nhân viên

            list.add(hd);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}
    
    
   public int addHoaDon(String maHoaDon, int trangThai, String ghiChu, String tenKhachHang, int nhanVienID, String hinhThucThanhToan) {
        String insertHoaDonSQL = """
            INSERT INTO HoaDonTaiQuay (mahoadon, trangthai, ghichu, tenkhachhang, nhanvienID, hinhthucthanhtoan)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(insertHoaDonSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, maHoaDon);
            ps.setInt(2, trangThai);
            ps.setString(3, ghiChu);
            ps.setString(4, tenKhachHang);
            ps.setInt(5, nhanVienID);
            ps.setString(6, hinhThucThanhToan);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Thêm hóa đơn thất bại, không có dòng nào được tạo.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Trả về ID hóa đơn
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Trả về -1 nếu có lỗi
    }

    /**
     * Thêm danh sách chi tiết hóa đơn vào cơ sở dữ liệu.
     *
     * @param chiTietList Danh sách chi tiết hóa đơn
     * @return true nếu thêm thành công, false nếu có lỗi
     */
    public boolean addChiTietHoaDon(List<HoaDonChiTiet> chiTietList) {
    // Chỉ thêm dữ liệu vào các cột hoadonID và gioHangid
    String insertChiTietSQL = """
        INSERT INTO ChiTietHoaDonTaiQuay (hoadonID, gioHangid)
        VALUES (?, ?)
    """;

    try (PreparedStatement ps = conn.prepareStatement(insertChiTietSQL)) {
        for (HoaDonChiTiet chiTiet : chiTietList) {
            ps.setInt(1, chiTiet.getHoaDonID()); // Gán hoadonID
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
    
    public String getTenSanPhamById(int id) {
    String tenSP = null;
    try (PreparedStatement ps = conn.prepareStatement("SELECT tensp FROM SanPham WHERE masp = ?")) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            tenSP = rs.getString("tensp");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return tenSP;
}


}
