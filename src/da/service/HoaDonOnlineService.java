/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package da.service;

import da.util.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class HoaDonOnlineService {
        private Connection conn;

    public HoaDonOnlineService() {
        conn = connectDB.getConnection();
    }
    
    public int addHoaDonOnline(String maHoaDon, int trangThai, String soDienThoai, String tenKhachHang, String diaChiGiaoHang, String hinhThucThanhToan, String hinhThucVanChuyen) {
        String insertHoaDonSQL = """
            INSERT INTO HoaDonTaiQuay (mahoadon, trangthai, ghichu, tenkhachhang, nhanvienID, hinhthucthanhtoan)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(insertHoaDonSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, maHoaDon);
            ps.setInt(2, trangThai);
            ps.setString(3, soDienThoai);
            ps.setString(4, tenKhachHang);
            ps.setString(5, diaChiGiaoHang);
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
}
