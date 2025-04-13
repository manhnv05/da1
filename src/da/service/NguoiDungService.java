package da.service;

import da.model.NguoiDung;
import da.util.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class NguoiDungService {
    private Connection conn;

    public NguoiDungService() {
        conn = connectDB.getConnection();
    }

    public NguoiDung login(String email, String matKhau) {
        String SQL = "SELECT id, ho, ten, email, matKhau, idVaiTro, ngayTao FROM NguoiDung WHERE email = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("matKhau");

                // So sánh trực tiếp mật khẩu
                if (matKhau.equals(storedPassword)) {
                    // Trả về đối tượng NguoiDung với đầy đủ thông tin
                    return new NguoiDung(rs.getInt("id"), rs.getString("ho"), rs.getString("ten"), rs.getString("email"), storedPassword, rs.getInt("idVaiTro"), rs.getDate("ngayTao"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registerUser(String ho, String ten, String email, String matKhau) {
        String sql = "INSERT INTO NguoiDung (ho, ten, email, matKhau) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ho);
            ps.setString(2, ten);
            ps.setString(3, email);
            ps.setString(4, matKhau); // Lưu mật khẩu trực tiếp vào CSDL (KHÔNG mã hóa)

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getIdNguoiDungByEmail(String email) {
    String sql = "SELECT id FROM NguoiDung WHERE email = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, email);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // Trả về -1 nếu không tìm thấy
}
    
    
public NguoiDung getNguoiDungByEmail(String email) {
    String SQL = "SELECT * FROM NguoiDung WHERE email = ?";
    try (PreparedStatement ps = conn.prepareStatement(SQL)) {
        ps.setString(1, email);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new NguoiDung(
                    rs.getInt("id"), 
                    rs.getString("ho"), 
                    rs.getString("ten"), 
                    rs.getString("email"), 
                    rs.getString("matKhau") // Nếu cần sử dụng
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

public boolean updateNguoiDungByEmail(String email, String ho, String ten, String matKhau) {
    String sql = "UPDATE NguoiDung SET ho = ?, ten = ?, matKhau = ? WHERE email = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, ho); // Cập nhật họ
        ps.setString(2, ten); // Cập nhật tên
        ps.setString(3, matKhau); // Cập nhật mật khẩu (có thể mã hóa trước khi lưu)
        ps.setString(4, email); // Điều kiện WHERE để xác định người dùng theo email

        int affectedRows = ps.executeUpdate();
        return affectedRows > 0; // Trả về true nếu có ít nhất một dòng được cập nhật
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}