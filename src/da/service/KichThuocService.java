package da.service;

import da.model.KichThuoc;
import da.util.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class KichThuocService {
    private Connection conn;

    public KichThuocService() {
        conn = connectDB.getConnection();
    }
    
    // 🔍 Tìm kiếm kích thước theo từ khóa
    public ArrayList<KichThuoc> searchKichThuoc(String keyword) {
        ArrayList<KichThuoc> kichThuocList = new ArrayList<>();
        String SQL = "SELECT id, tenKT FROM KichThuoc WHERE statuss = 1";

        // Nếu có từ khóa thì thêm điều kiện
        if (keyword != null && !keyword.trim().isEmpty()) {
            SQL += " AND tenKT LIKE ?";
        }

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(1, "%" + keyword.trim() + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    kichThuocList.add(new KichThuoc(
                        rs.getInt("id"),
                        rs.getString("tenKT")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kichThuocList;
    }
    
    // 📋 Lấy danh sách tất cả kích thước
    public ArrayList<KichThuoc> getAllKichThuoc() {
        String SQL = "SELECT id, tenKT FROM KichThuoc WHERE statuss = 1";
        ArrayList<KichThuoc> kichThuocList = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                kichThuocList.add(new KichThuoc(rs.getInt("id"), rs.getString("tenKT")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kichThuocList;
    }
    
    // ➕ Thêm kích thước mới
    public boolean addKichThuoc(String ten) {
        String SQL = "INSERT INTO KichThuoc (tenKT) VALUES (?)";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, ten.trim());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✏️ Cập nhật kích thước theo ID
    public boolean updateKichThuoc(int id, String newTen) {
        String SQL = "UPDATE KichThuoc SET tenKT = ? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, newTen.trim());
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ❌ Xóa kích thước theo ID
    public boolean deleteKichThuoc(int id) {
        String SQL = "UPDATE KichThuoc SET statuss = 0 WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
