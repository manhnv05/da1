package da.service;

import da.model.MauSac;
import da.util.connectDB;
import java.sql.*;
import java.util.ArrayList;

public class MauSacService {
    private Connection conn;

    public MauSacService() {
        conn = connectDB.getConnection();
    }

    // 🔍 Tìm kiếm màu sắc theo từ khóa
    public ArrayList<MauSac> searchMauSac(String keyword) {
        String SQL = "SELECT id, tenMau FROM MauSac";
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            SQL += " WHERE tenMau LIKE ? ";
        }

        ArrayList<MauSac> mauSacList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(1, "%" + keyword.trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String tenMau = rs.getString("tenMau");
                mauSacList.add(new MauSac(id, tenMau));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mauSacList;
    }
    
    // 📋 Lấy danh sách tất cả màu sắc
    public ArrayList<MauSac> getAllMauSac() {
        String SQL = "SELECT id, tenMau FROM MauSac";
        ArrayList<MauSac> mauSacList = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                mauSacList.add(new MauSac(rs.getInt("id"), rs.getString("tenMau")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mauSacList;
    }
    
    // ➕ Thêm màu sắc mới
    public boolean addMauSac(String tenMau) {
        String SQL = "INSERT INTO MauSac (tenMau) VALUES (?)";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, tenMau.trim());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✏️ Cập nhật màu sắc theo ID
    public boolean updateMauSac(int id, String newTenMau) {
        String SQL = "UPDATE MauSac SET tenMau = ? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, newTenMau.trim());
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ❌ Xóa màu sắc theo ID
    public boolean deleteMauSac(int id) {
        String SQL = "DELETE FROM MauSac WHERE id = ?";
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
