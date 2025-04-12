
package da.service;

import da.model.ChatLieu;
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
public class ChatLieuService {
    private Connection conn;

    public ChatLieuService() {
        conn = connectDB.getConnection();
    }
    
    public ArrayList<ChatLieu> searchChatLieu(String keyword) {
        String SQL = "SELECT id, tenChatLieu FROM ChatLieu";
        if (keyword != null && !keyword.trim().isEmpty()) {
            SQL += " WHERE tenChatLieu LIKE ? ";
        }
        ArrayList<ChatLieu> chatLieuList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(1, "%" + keyword.trim() + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String tenChatLieu = rs.getString("tenChatLieu");
                ChatLieu chatLieu = new ChatLieu(id, tenChatLieu);
                chatLieuList.add(chatLieu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatLieuList;
    }
    
    public ArrayList<ChatLieu> getAllChatLieu() {
        String SQL = "SELECT id, tenChatLieu FROM ChatLieu";
        ArrayList<ChatLieu> chatLieuList = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                chatLieuList.add(new ChatLieu(rs.getInt("id"), rs.getString("tenChatLieu")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatLieuList;
    }
    
    
     public boolean addChatLieu(String tenChatLieu) {
        String SQL = "INSERT INTO ChatLieu (tenChatLieu) VALUES (?)";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, tenChatLieu.trim());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✏️ Cập nhật ChatLieu theo ID
    public boolean updateChatLieu(int id, String newTenChatLieu) {
        String SQL = "UPDATE ChatLieu SET tenChatLieu = ? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, newTenChatLieu.trim());
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ❌ Xóa ChatLieu theo ID
    public boolean deleteChatLieu(int id) {
        String SQL = "DELETE FROM ChatLieu WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
