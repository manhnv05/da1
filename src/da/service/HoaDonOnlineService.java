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
}
