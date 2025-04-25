package da.service;

import da.model.KhuVucKho;
import da.util.connectDB;
import java.sql.*;
import java.util.ArrayList;

public class KhuVucKhoService {
    private Connection conn;

    public KhuVucKhoService() {
        conn = connectDB.getConnection();
    }

    // Lấy toàn bộ khu vực kho
    public ArrayList<KhuVucKho> getAllKhuVucKho() {
        ArrayList<KhuVucKho> list = new ArrayList<>();
        String sql = "SELECT id, tenKhuVuc, moTa FROM KhuVucKho WHERE statuss = 1";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KhuVucKho khu = new KhuVucKho(
                    rs.getInt("id"),
                    rs.getString("tenKhuVuc"),
                    rs.getString("moTa")
                );
                list.add(khu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm khu vực kho
    public boolean addKhuVucKho(KhuVucKho khu) {
        String sql = "INSERT INTO KhuVucKho (tenKhuVuc, moTa) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, khu.getTenKhuVuc());
            ps.setString(2, khu.getMoTa());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật khu vực kho
    public boolean updateKhuVucKho(KhuVucKho khu) {
        String sql = "UPDATE KhuVucKho SET tenKhuVuc = ?, moTa = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, khu.getTenKhuVuc());
            ps.setString(2, khu.getMoTa());
            ps.setInt(3, khu.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa khu vực kho
    public boolean deleteKhuVucKho(int id) {
        String sql = "UPDATE KhuVucKho SET statuss = 0 WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm theo tên
    public ArrayList<KhuVucKho> searchKhuVucKhoByName(String keyword) {
        ArrayList<KhuVucKho> list = new ArrayList<>();
        String sql = "SELECT id, tenKhuVuc, moTa FROM KhuVucKho WHERE statuss = 1 AND tenKhuVuc LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    KhuVucKho khu = new KhuVucKho(
                        rs.getInt("id"),
                        rs.getString("tenKhuVuc"),
                        rs.getString("moTa")
                    );
                    list.add(khu);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
