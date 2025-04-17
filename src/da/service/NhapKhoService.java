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
            conn.setAutoCommit(true); // Khôi phục trạng thái AutoCommit
        } catch (SQLException e) {
            System.out.println("Failed to reset AutoCommit: " + e.getMessage());
        }
    }
}

    public boolean updateNhapKho(int id, int idNhaCungCap, int idNhanVien, int idSanPham, int idKhuVucKho, int soLuong) {
        String update = "UPDATE NhapKho SET idNhaCungCap = ?, idNhanVien = ?, idSanPham = ?, idKhuVucKho = ?, soLuong = ? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(update);
            ps.setInt(1, idNhaCungCap);
            ps.setInt(2, idNhanVien);
            ps.setInt(3, idSanPham);
            ps.setInt(4, idKhuVucKho);
            ps.setInt(5, soLuong);
            ps.setInt(6, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating NhapKho: " + e.getMessage());
            return false;
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