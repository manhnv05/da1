package da.service;

import da.model.XuatXu;
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
public class XuatXuService {
    private Connection conn;

    public XuatXuService() {
        conn = connectDB.getConnection();
    }
    
    // 🔍 Tìm kiếm Xuất Xứ theo từ khóa
    public ArrayList<XuatXu> searchXuatXu(String keyword) {
        String SQL = "SELECT id, tenXuatXu FROM XuatXu";
        if (keyword != null && !keyword.trim().isEmpty()) {
            SQL += " WHERE tenXuatXu LIKE ? ";
        }
        
        ArrayList<XuatXu> xuatXuList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(1, "%" + keyword.trim() + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                xuatXuList.add(new XuatXu(rs.getInt("id"), rs.getString("tenXuatXu")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return xuatXuList;
    }
    
    // 📜 Lấy toàn bộ danh sách Xuất Xứ
    public ArrayList<XuatXu> getAllXuatXu() {
        String SQL = "SELECT id, tenXuatXu FROM XuatXu";
        ArrayList<XuatXu> xuatXuList = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                xuatXuList.add(new XuatXu(rs.getInt("id"), rs.getString("tenXuatXu")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return xuatXuList;
    }

    // ➕ Thêm Xuất Xứ mới
    public boolean addXuatXu(String tenXuatXu) {
        String SQL = "INSERT INTO XuatXu (tenXuatXu) VALUES (?)";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, tenXuatXu.trim());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✏️ Cập nhật Xuất Xứ theo ID
    public boolean updateXuatXu(int id, String newTenXuatXu) {
        String SQL = "UPDATE XuatXu SET tenXuatXu = ? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, newTenXuatXu.trim());
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ❌ Xóa Xuất Xứ theo ID
    public boolean deleteXuatXu(int id) {
        String SQL = "DELETE FROM XuatXu WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
