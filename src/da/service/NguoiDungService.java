package da.service;

import da.model.NguoiDung;
import da.util.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt; //

/**
 *
 * @author ADMIN
 */
public class NguoiDungService {
    private Connection conn;

    public NguoiDungService() {
        conn = connectDB.getConnection();
    }


     private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt()); // Generate a BCrypt hash
    }

    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }


    public NguoiDung login(String email, String matKhau) {
        String SQL = "SELECT id, ho, ten, email, matKhau, idVaiTro, ngayTao FROM NguoiDung WHERE email = ?";

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("matKhau");

                // Kiểm tra nếu mật khẩu trong cơ sở dữ liệu là hash BCrypt
                if (storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$") || storedPassword.startsWith("$2y$")) {
                    // So sánh mật khẩu với BCrypt
                    if (verifyPassword(matKhau, storedPassword)) {
                        return new NguoiDung(
                            rs.getInt("id"),
                            rs.getString("ho"),
                            rs.getString("ten"),
                            rs.getString("email"),
                            null, // Không trả về mật khẩu
                            rs.getInt("idVaiTro"),
                            rs.getDate("ngayTao")
                        );
                    }
                } else {
                    // Trường hợp mật khẩu không phải BCrypt (có thể là plain text)
                    if (matKhau.equals(storedPassword)) { // So sánh trực tiếp
                        return new NguoiDung(
                            rs.getInt("id"),
                            rs.getString("ho"),
                            rs.getString("ten"),
                            rs.getString("email"),
                            null, // Không trả về mật khẩu
                            rs.getInt("idVaiTro"),
                            rs.getDate("ngayTao")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Đăng nhập thất bại
    }


    public boolean registerUser(String ho, String ten, String email, String matKhau) {
        String sql = "INSERT INTO NguoiDung (ho, ten, email, matKhau) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ho);
            ps.setString(2, ten);
            ps.setString(3, email);
            ps.setString(4, hashPassword(matKhau)); // Hash mật khẩu trước khi lưu

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean AddTKNV(NguoiDung nguoiDung) {
    // Câu lệnh SQL thêm tài khoản nhân viên
    String sql = "INSERT INTO NguoiDung (ho, ten, email, matKhau, idVaiTro) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        // Thiết lập các giá trị cho PreparedStatement
        ps.setString(1, nguoiDung.getHo());
        ps.setString(2, nguoiDung.getTen());
        ps.setString(3, nguoiDung.getEmail());
        ps.setString(4, hashPassword(nguoiDung.getMatKhau())); // Hash mật khẩu trước khi lưu
        ps.setInt(5, nguoiDung.getIdVaiTro());

        // Thực thi câu lệnh và kiểm tra số hàng bị ảnh hưởng
        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;
    } catch (SQLException e) {
        // Log lỗi và trả về false nếu có lỗi xảy ra
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
        return -1; // Return -1 if not found
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
                        rs.getString("matKhau") // If needed
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

     public boolean updateNguoiDungByEmail(String email, String ho, String ten, String newPassword) {
        String sql = "UPDATE NguoiDung SET ho = ?, ten = ?, matKhau = ? WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ho); // Cập nhật họ
            ps.setString(2, ten); // Cập nhật tên
            ps.setString(3, hashPassword(newPassword)); // Hash mật khẩu mới
            ps.setString(4, email); // Điều kiện WHERE để xác định người dùng

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0; // Trả về true nếu ít nhất một dòng được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM NguoiDung WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // If COUNT > 0, email exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Email does not exist
    }
}