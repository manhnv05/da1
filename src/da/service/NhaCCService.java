package da.service;

import da.model.NhaCC;
import da.util.connectDB;
import java.sql.*;
import java.util.ArrayList;

public class NhaCCService {
    private Connection conn;

    public NhaCCService() {
        conn = connectDB.getConnection();
    }

    // 🔍 Tìm kiếm nhà cung cấp theo từ khóa
    public ArrayList<NhaCC> searchNhaCungCap(String keyword) {
        String SQL = "SELECT id, tenNCC, diaChi, soDienThoai, email FROM NhaCungCap";
        if (keyword != null && !keyword.trim().isEmpty()) {
            SQL += " WHERE tenNCC LIKE ?";
        }

        ArrayList<NhaCC> nccList = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(1, "%" + keyword.trim() + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nccList.add(new NhaCC(
                    rs.getInt("id"), 
                    rs.getString("tenNCC"), 
                    rs.getString("diaChi"), 
                    rs.getString("soDienThoai"), 
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nccList;
    }

    // 📋 Lấy danh sách tất cả nhà cung cấp
    public ArrayList<NhaCC> getAllNhaCungCap() {
        String SQL = "SELECT id, tenNCC, diaChi, soDienThoai, email FROM NhaCungCap";
        ArrayList<NhaCC> nccList = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                nccList.add(new NhaCC(
                    rs.getInt("id"),
                    rs.getString("tenNCC"),
                    rs.getString("diaChi"),
                    rs.getString("soDienThoai"),
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nccList;
    }
    
    // ➕ Thêm nhà cung cấp mới
    public boolean addNhaCungCap(String tenNCC, String diaChi, String soDienThoai, String email) {
        String SQL = "INSERT INTO NhaCungCap (tenNCC, diaChi, soDienThoai, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, tenNCC.trim());
            ps.setString(2, diaChi.trim());
            ps.setString(3, soDienThoai.trim());
            ps.setString(4, email.trim());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✏️ Cập nhật thông tin nhà cung cấp theo ID
    public boolean updateNhaCungCap(int id, String tenNCC, String diaChi, String soDienThoai, String email) {
        String SQL = "UPDATE NhaCungCap SET tenNCC = ?, diaChi = ?, soDienThoai = ?, email = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, tenNCC.trim());
            ps.setString(2, diaChi.trim());
            ps.setString(3, soDienThoai.trim());
            ps.setString(4, email.trim());
            ps.setInt(5, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ❌ Xóa nhà cung cấp theo ID
    public boolean deleteNhaCungCap(int id) {
        String SQL = "DELETE FROM NhaCungCap WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
