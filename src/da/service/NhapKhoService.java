package da.service;

import da.model.NhapKho;
import da.util.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class NhapKhoService {

    private Connection conn;

    public NhapKhoService() {
        conn = connectDB.getConnection();
    }

    public List<NhapKho> getListNhapKho() {
    List<NhapKho> list = new ArrayList<>();
    String sql = "SELECT " +
        "nk.id, nk.maNhap, nk.soLuong, nk.ngayNhap, nk.tongTien, " +
        "ncc.tenNCC AS tenNhaCungCap, " +
        "nv.ho + ' ' + nv.ten AS tenNhanVien, " +
        "sp.tenSP AS tenSanPham " +
        "FROM NhapKho nk " +
        "JOIN NhaCungCap ncc ON nk.idNhaCungCap = ncc.id " +
        "JOIN NhanVien nv ON nk.idNhanVien = nv.id " +
        "JOIN SanPham sp ON nk.idSanPham = sp.id";

    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            NhapKho nk = new NhapKho();
            nk.setId(rs.getInt("id"));
            nk.setManhap(rs.getString("maNhap"));
            nk.setSoLuong(rs.getInt("soLuong"));
            nk.setNgaynhap(rs.getTimestamp("ngayNhap"));
            nk.setTongtien(rs.getDouble("tongTien"));
            nk.setNhacungcap(rs.getString("tenNhaCungCap"));
            nk.setNhanvien(rs.getString("tenNhanVien"));
            nk.setSanPham(rs.getString("tenSanPham"));
            list.add(nk);
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi truy vấn NhapKho: " + e.getMessage());
    }

    return list;
}


    public boolean addNhapKho(NhapKho nhapKho, int idNhaCungCap, int idNhanVien, int idSanPham) {
    String getSanPhamGia = "SELECT gia FROM SanPham WHERE id = ?";
    String insert = "INSERT INTO NhapKho (maNhap, idNhaCungCap, idNhanVien, idSanPham, soLuong, ngayNhap, tongTien) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
    String updateSanPham = "UPDATE SanPham SET soluongton = soluongton + ? WHERE id = ?";
    try (PreparedStatement psGetGia = conn.prepareStatement(getSanPhamGia);
         PreparedStatement psInsert = conn.prepareStatement(insert);
         PreparedStatement psUpdate = conn.prepareStatement(updateSanPham)) {
        conn.setAutoCommit(false);
        psGetGia.setInt(1, idSanPham);
        ResultSet rs = psGetGia.executeQuery();
        if (!rs.next()) {
            System.out.println("Error: Sản phẩm không tồn tại.");
            conn.rollback();
            return false;
        }
        double giaSanPham = rs.getDouble("gia");
        double tongTien = giaSanPham * nhapKho.getSoLuong();
        psInsert.setString(1, nhapKho.getManhap());
        psInsert.setInt(2, idNhaCungCap);
        psInsert.setInt(3, idNhanVien);
        psInsert.setInt(4, idSanPham);
        psInsert.setInt(5, nhapKho.getSoLuong());
        psInsert.setTimestamp(6, nhapKho.getNgaynhap());
        psInsert.setDouble(7, tongTien);
        int rowsInserted = psInsert.executeUpdate();
        if (rowsInserted > 0) {
            psUpdate.setInt(1, nhapKho.getSoLuong());
            psUpdate.setInt(2, idSanPham);
            int rowsUpdated = psUpdate.executeUpdate();
            if (rowsUpdated > 0) {
                conn.commit(); // Hoàn tất transaction
                return true;
            } else {
                System.out.println("Error updating SanPham: No rows affected.");
            }
        } else {
            System.out.println("Error inserting NhapKho: No rows affected.");
        }
        conn.rollback();
        return false;
    } catch (SQLException e) {
        System.out.println("Error adding NhapKho: " + e.getMessage());
        try {
            conn.rollback(); // Rollback nếu có lỗi
        } catch (SQLException rollbackEx) {
            System.out.println("Rollback failed: " + rollbackEx.getMessage());
        }
        return false;
    } finally {
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Failed to reset AutoCommit: " + e.getMessage());
        }
    }
}

public boolean updateNhapKho(int id, int idNhaCungCap, int idNhanVien, int idSanPham, int soLuong) {
    String getOldSoLuongQuery = "SELECT soLuong FROM NhapKho WHERE id = ?";
    String getSanPhamGiaQuery = "SELECT gia, soluongton FROM SanPham WHERE id = ?";
    String updateNhapKhoQuery = "UPDATE NhapKho SET idNhaCungCap = ?, idNhanVien = ?, idSanPham = ?, soLuong = ?, tongTien = ? WHERE id = ?";
    String updateSanPhamQuery = "UPDATE SanPham SET soluongton = ? WHERE id = ?";

    try {
        // Start transaction
        conn.setAutoCommit(false);

        // Fetch old quantity from NhapKho
        int oldSoLuong;
        try (PreparedStatement psGetOldSoLuong = conn.prepareStatement(getOldSoLuongQuery)) {
            psGetOldSoLuong.setInt(1, id);
            try (ResultSet rsOldSoLuong = psGetOldSoLuong.executeQuery()) {
                if (!rsOldSoLuong.next()) {
                    System.out.println("Error: Phiếu Nhập không tồn tại.");
                    conn.rollback();
                    return false;
                }
                oldSoLuong = rsOldSoLuong.getInt("soLuong");
            }
        }

        // Fetch product details (price and current stock)
        double giaSanPham;
        int currentSoLuongTon;
        try (PreparedStatement psGetSanPham = conn.prepareStatement(getSanPhamGiaQuery)) {
            psGetSanPham.setInt(1, idSanPham);
            try (ResultSet rsSanPham = psGetSanPham.executeQuery()) {
                if (!rsSanPham.next()) {
                    System.out.println("Error: Sản phẩm không tồn tại.");
                    conn.rollback();
                    return false;
                }
                giaSanPham = rsSanPham.getDouble("gia");
                currentSoLuongTon = rsSanPham.getInt("soluongton");
            }
        }

        // Calculate new stock level
        int newSoLuongTon = currentSoLuongTon - oldSoLuong + soLuong;
        if (newSoLuongTon < 0) {
            System.out.println("Error: Không đủ sản phẩm trong kho để cập nhật.");
            conn.rollback();
            return false;
        }

        // Calculate total price
        double tongTien = giaSanPham * soLuong;

        // Update NhapKho
        try (PreparedStatement psUpdateNhapKho = conn.prepareStatement(updateNhapKhoQuery)) {
            psUpdateNhapKho.setInt(1, idNhaCungCap);
            psUpdateNhapKho.setInt(2, idNhanVien);
            psUpdateNhapKho.setInt(3, idSanPham);
            psUpdateNhapKho.setInt(4, soLuong);
            psUpdateNhapKho.setDouble(5, tongTien);
            psUpdateNhapKho.setInt(6, id);

            int rowsUpdatedNhapKho = psUpdateNhapKho.executeUpdate();
            if (rowsUpdatedNhapKho <= 0) {
                System.out.println("Error updating NhapKho: No rows affected.");
                conn.rollback();
                return false;
            }
        }

        try (PreparedStatement psUpdateSanPham = conn.prepareStatement(updateSanPhamQuery)) {
            psUpdateSanPham.setInt(1, newSoLuongTon);
            psUpdateSanPham.setInt(2, idSanPham);

            int rowsUpdatedSanPham = psUpdateSanPham.executeUpdate();
            if (rowsUpdatedSanPham <= 0) {
                System.out.println("Error updating SanPham: No rows affected.");
                conn.rollback();
                return false;
            }
        }

        // Commit transaction
        conn.commit();
        return true;

    } catch (SQLException e) {
        System.out.println("Error updating NhapKho: " + e.getMessage());
        try {
            conn.rollback();
        } catch (SQLException rollbackEx) {
            System.out.println("Rollback failed: " + rollbackEx.getMessage());
        }
        return false;
    } finally {
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Failed to reset AutoCommit: " + e.getMessage());
        }
    }
}

    public boolean deleteNhapKho(int id) {
        String delete = "DELETE FROM NhapKho WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(delete);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting NhapKho: " + e.getMessage());
            return false;
        }
    }
}