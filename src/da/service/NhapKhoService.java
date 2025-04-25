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
        "JOIN SanPham sp ON nk.idSanPham = sp.id " +  // ← thêm khoảng trắng ở cuối dòng này
        "WHERE nk.statuss = 1";

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
            conn.setAutoCommit(false);
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
            int newSoLuongTon = currentSoLuongTon - oldSoLuong + soLuong;
            if (newSoLuongTon < 0) {
                System.out.println("Error: Không đủ sản phẩm trong kho để cập nhật.");
                conn.rollback();
                return false;
            }
            double tongTien = giaSanPham * soLuong;
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
        String delete = "UPDATE NhapKho SET statuss = 0 WHERE id = ?";
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
    
    
    public int getIdNhanVienByNhapKhoId(int idNhapKho) {
    String sql = "SELECT idNhanVien FROM NhapKho WHERE  id = ?";
    int idNhanVien = -1; // Giá trị mặc định nếu không tìm thấy

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idNhapKho);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                idNhanVien = rs.getInt("idNhanVien");
            }
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi truy vấn idNhanVien: " + e.getMessage());
    }

    return idNhanVien;
}
    
    public int getIdNhaCungCapByNhapKhoId(int idNhapKho) {
    String sql = "SELECT idNhaCungCap FROM NhapKho WHERE id = ?";
    int idNhaCungCap = -1; // Giá trị mặc định nếu không tìm thấy

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idNhapKho); // Gán giá trị idNhapKho vào câu lệnh SQL
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                idNhaCungCap = rs.getInt("idNhaCungCap"); // Lấy idNhaCungCap từ kết quả truy vấn
            }
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi truy vấn idNhaCungCap: " + e.getMessage());
    }

    return idNhaCungCap; // Trả về idNhaCungCap hoặc -1 nếu không tìm thấy
}
    
    public int getIdSanPhamByNhapKhoId(int idNhapKho) {
    String sql = "SELECT idSanPham FROM NhapKho WHERE id = ?";
    int idSanPham = -1; // Giá trị mặc định nếu không tìm thấy

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idNhapKho); // Gán giá trị idNhapKho vào câu lệnh SQL
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                idSanPham = rs.getInt("idSanPham"); // Lấy idSanPham từ kết quả truy vấn
            }
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi truy vấn idSanPham: " + e.getMessage());
    }

    return idSanPham; // Trả về idSanPham hoặc -1 nếu không tìm thấy
}
    
    
    public List<NhapKho> searchNhapKho(String keyword) {
    List<NhapKho> list = new ArrayList<>();

    String sql = "SELECT " +
            "nk.id, nk.maNhap, nk.soLuong, nk.ngayNhap, nk.tongTien, " +
            "ncc.tenNCC AS tenNhaCungCap, " +
            "nv.ho + ' ' + nv.ten AS tenNhanVien, " +
            "sp.tenSP AS tenSanPham " +
            "FROM NhapKho nk " +
            "JOIN NhaCungCap ncc ON nk.idNhaCungCap = ncc.id " +
            "JOIN NhanVien nv ON nk.idNhanVien = nv.id " +
            "JOIN SanPham sp ON nk.idSanPham = sp.id " +
            "WHERE nk.statuss = 1"; // Giả sử cột statuss thuộc bảng NhapKho

    // Thêm điều kiện tìm kiếm nếu có keyword
    if (keyword != null && !keyword.trim().isEmpty()) {
        sql += " AND (nk.maNhap LIKE ? OR nv.ho + ' ' + nv.ten LIKE ? OR sp.tenSP LIKE ?)";
    }

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            String searchPattern = "%" + keyword.trim() + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
        }

        try (ResultSet rs = ps.executeQuery()) {
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
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi truy vấn NhapKho: " + e.getMessage());
    }

    return list;
}

}
    
