
package da.service;

import da.model.SanPham;
import da.util.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class SanPhamService {
        private Connection conn;

    public SanPhamService() {
        conn = connectDB.getConnection();
    }
    
    
    public ArrayList<SanPham> searchSanPham(String keyword) {
    StringBuilder SQL = new StringBuilder("""
        SELECT 
            sp.id, sp.masp, sp.tensp, sp.mota, sp.gia, sp.soluongton, sp.hinhanh,
            cl.tenChatLieu AS tenChatLieu,
            xx.tenXuatXu AS tenXuatXu,
            kt.tenKT AS tenKT,
            ms.tenMau AS tenMau,
            ncc.tenNCC AS tenNCC,
            kvk.tenKhuVuc AS tenKhuVuc
        FROM SanPham sp
        LEFT JOIN ChatLieu cl ON sp.idChatLieu = cl.id
        LEFT JOIN XuatXu xx ON sp.idXuatXu = xx.id
        LEFT JOIN KichThuoc kt ON sp.idKichThuoc = kt.id
        LEFT JOIN MauSac ms ON sp.idMauSac = ms.id
        LEFT JOIN NhaCungCap ncc ON sp.idNhaCungCap = ncc.id
        LEFT JOIN KhuVucKho kvk ON sp.idKhuVucKho = kvk.id
    """);

    boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
    if (hasKeyword) {
        SQL.append("""
            WHERE (sp.tensp LIKE ? OR sp.masp LIKE ? OR CAST(sp.gia AS NVARCHAR) LIKE ?)
              AND sp.trangThai = 1
        """);
    } else {
        SQL.append("WHERE sp.trangThai = 1");
    }

    ArrayList<SanPham> ds = new ArrayList<>();
    try {
        PreparedStatement ps = conn.prepareStatement(SQL.toString());
        if (hasKeyword) {
            String searchPattern = "%" + keyword.trim() + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
        }
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            SanPham sp = new SanPham();
            sp.setId(rs.getInt("id"));
            sp.setMasp(rs.getString("masp"));
            sp.setTensp(rs.getString("tensp"));
            sp.setMota(rs.getString("mota"));
            sp.setGia(rs.getBigDecimal("gia"));
            sp.setSoluongton(rs.getInt("soluongton"));
            sp.setHinhanh(rs.getString("hinhanh"));
            sp.setTenChatLieu(rs.getString("tenChatLieu"));
            sp.setTenXuatXu(rs.getString("tenXuatXu"));
            sp.setTenKichThuoc(rs.getString("tenKT"));
            sp.setTenMauSac(rs.getString("tenMau"));
            sp.setTenNhaCungCap(rs.getString("tenNCC"));
            sp.setTenKhuVucKho(rs.getString("tenKhuVuc"));
            ds.add(sp);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return ds;
}



    public ArrayList<SanPham> getAll() {
    String SQL = """
        SELECT 
            sp.id, sp.masp, sp.tensp, sp.mota, sp.gia, sp.soluongton, sp.hinhanh,
            cl.tenChatLieu AS tenChatLieu,
            xx.tenXuatXu AS tenXuatXu,
            kt.tenKT AS tenKT,
            ms.tenMau AS tenMau,
            ncc.tenNCC AS tenNCC,
            kvk.tenKhuVuc AS tenKhuVuc
        FROM SanPham sp
        LEFT JOIN ChatLieu cl ON sp.idChatLieu = cl.id
        LEFT JOIN XuatXu xx ON sp.idXuatXu = xx.id
        LEFT JOIN KichThuoc kt ON sp.idKichThuoc = kt.id
        LEFT JOIN MauSac ms ON sp.idMauSac = ms.id
        LEFT JOIN NhaCungCap ncc ON sp.idNhaCungCap = ncc.id
        LEFT JOIN KhuVucKho kvk ON sp.idKhuVucKho = kvk.id
        WHERE sp.trangThai = 1
    """;
    ArrayList<SanPham> ds = new ArrayList<>();
    try (PreparedStatement ps = conn.prepareStatement(SQL);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            SanPham sp = new SanPham();
            sp.setId(rs.getInt("id"));
            sp.setMasp(rs.getString("masp"));
            sp.setTensp(rs.getString("tensp"));
            sp.setMota(rs.getString("mota"));
            sp.setGia(rs.getBigDecimal("gia"));
            sp.setSoluongton(rs.getInt("soluongton"));
            sp.setHinhanh(rs.getString("hinhanh"));
            sp.setTenChatLieu(rs.getString("tenChatLieu"));
            sp.setTenXuatXu(rs.getString("tenXuatXu"));
            sp.setTenKichThuoc(rs.getString("tenKT"));
            sp.setTenMauSac(rs.getString("tenMau"));
            sp.setTenNhaCungCap(rs.getString("tenNCC"));
            sp.setTenKhuVucKho(rs.getString("tenKhuVuc"));
            ds.add(sp);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return ds;
}





    public HashSet<String> getAllChatLieu() {
        String SQL = "SELECT DISTINCT cl.tenChatLieu FROM SanPham sp LEFT JOIN ChatLieu cl ON sp.idChatLieu = cl.id";
        HashSet<String> chatLieuSet = new HashSet<>();
        try (PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                chatLieuSet.add(rs.getString("tenChatLieu"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatLieuSet;
    }

    public HashSet<String> getAllXuatXu() {
        String SQL = "SELECT DISTINCT xu.tenXuatXu FROM SanPham sp LEFT JOIN XuatXu xu ON sp.idXuatXu = xu.id";
        HashSet<String> xuatXuSet = new HashSet<>();
        try (PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                xuatXuSet.add(rs.getString("tenXuatXu"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return xuatXuSet;
    }


    public HashSet<String> getAllKichThuoc() {
        String SQL = "SELECT DISTINCT k.tenKT FROM SanPham sp LEFT JOIN KichThuoc k ON sp.idKichThuoc = k.id";
        HashSet<String> kichThuocSet = new HashSet<>();
        try (PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                kichThuocSet.add(rs.getString("tenKT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kichThuocSet;
    }


    public HashSet<String> getAllMauSac() {
        String SQL = "SELECT DISTINCT m.tenMau FROM SanPham sp LEFT JOIN MauSac m ON sp.idMauSac = m.id";
        HashSet<String> mauSacSet = new HashSet<>();
        try (PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                mauSacSet.add(rs.getString("tenMau"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mauSacSet;
    }

    public HashSet<String> getAllNhaCungCap() {
        String SQL = "SELECT DISTINCT ncc.tenNCC FROM SanPham sp LEFT JOIN NhaCungCap ncc ON sp.idNhaCungCap = ncc.id";
        HashSet<String> nhaCungCapSet = new HashSet<>();
        try (PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                nhaCungCapSet.add(rs.getString("tenNCC"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nhaCungCapSet;
    }
    
    public HashSet<String> getAllKhuVucKho() {
        String SQL = "SELECT DISTINCT kvk.tenKhuVuc FROM SanPham sp LEFT JOIN KhuVucKho kvk ON sp.idKhuVucKho = kvk.id";
        HashSet<String> khuVucKhoSet = new HashSet<>();
        try (PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                khuVucKhoSet.add(rs.getString("tenKhuVuc"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return khuVucKhoSet;
    }

    public boolean checkMaSPTonTai(String maSP) {
        String sql = "SELECT COUNT(*) FROM SanPham WHERE masp = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSP);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addSanPham(SanPham sanPham) {
        String SQL = "INSERT INTO SanPham (masp, tensp, mota, gia, soluongton, idChatLieu, idXuatXu, idKichThuoc, idMauSac, idNhaCungCap, idKhuVucKho, hinhanh) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, sanPham.getMasp());
            ps.setString(2, sanPham.getTensp());
            ps.setString(3, sanPham.getMota());
            ps.setBigDecimal(4, sanPham.getGia());
            ps.setInt(5, sanPham.getSoluongton());
            ps.setInt(6, sanPham.getIdChatLieu());
            ps.setInt(7, sanPham.getIdXuatXu());
            ps.setInt(8, sanPham.getIdKichThuoc());
            ps.setInt(9, sanPham.getIdMauSac());
            ps.setInt(10, sanPham.getIdNhaCungCap());
            ps.setInt(11, sanPham.getIdKhuVucKho());
            ps.setString(12, sanPham.getHinhanh());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updateSanPham(SanPham sanPham) {
        String SQL = "UPDATE SanPham SET tensp = ?, mota = ?, gia = ?, idChatLieu = ?, idXuatXu = ?, idKichThuoc = ?, idMauSac = ?, idNhaCungCap = ?, idKhuVucKho = ?, hinhanh = ? "
                   + "WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, sanPham.getTensp());
            ps.setString(2, sanPham.getMota());
            ps.setBigDecimal(3, sanPham.getGia());
            //ps.setInt(4, sanPham.getSoluongton());
            ps.setInt(4, sanPham.getIdChatLieu());
            ps.setInt(5, sanPham.getIdXuatXu());
            ps.setInt(6, sanPham.getIdKichThuoc());
            ps.setInt(7, sanPham.getIdMauSac());
            ps.setInt(8, sanPham.getIdNhaCungCap());
            ps.setInt(9, sanPham.getIdKhuVucKho());
            ps.setString(10, sanPham.getHinhanh());
            ps.setInt(11, sanPham.getId());
            int rowsAffected = ps.executeUpdate();
                    return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public SanPham getSanPhamById(int id) {
        String SQL = "SELECT * FROM SanPham WHERE id = ?";
        SanPham sanPham = null;
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    sanPham = new SanPham();
                    sanPham.setId(rs.getInt("id"));
                    sanPham.setMasp(rs.getString("masp"));
                    sanPham.setTensp(rs.getString("tensp"));
                    sanPham.setMota(rs.getString("mota"));
                    sanPham.setGia(rs.getBigDecimal("gia"));
                    sanPham.setSoluongton(rs.getInt("soluongton"));
                    sanPham.setHinhanh(rs.getString("hinhanh"));
                    sanPham.setIdChatLieu(rs.getInt("idChatLieu"));
                    sanPham.setIdXuatXu(rs.getInt("idXuatXu"));
                    sanPham.setIdKichThuoc(rs.getInt("idKichThuoc"));
                    sanPham.setIdMauSac(rs.getInt("idMauSac"));
                    sanPham.setIdNhaCungCap(rs.getInt("idNhaCungCap"));
                    sanPham.setIdKhuVucKho(rs.getInt("idKhuVucKho"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sanPham;
    }


    public boolean deleteSanPham(int id) {
    String SQL = "UPDATE SanPham SET trangThai = 0 WHERE id = ?";
    try (PreparedStatement ps = conn.prepareStatement(SQL)) {
        ps.setInt(1, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    
public boolean updateSoLuongTon(int idSanPham, int soLuongDaBan) {
    String SQL = "UPDATE SanPham SET soluongton = soluongton - ? WHERE id = ? AND soluongton >= ?";
    try (PreparedStatement ps = conn.prepareStatement(SQL)) {
        ps.setInt(1, soLuongDaBan); // Số lượng cần giảm
        ps.setInt(2, idSanPham);    // ID của sản phẩm
        ps.setInt(3, soLuongDaBan); // Đảm bảo số lượng tồn đủ để giảm

        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0; // Trả về true nếu cập nhật thành công
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public boolean restoreSoLuongTon(int idSanPham, int soLuongDaXoa) {
    String SQL = "UPDATE SanPham SET soluongton = soluongton + ? WHERE id = ?";
    try (PreparedStatement ps = conn.prepareStatement(SQL)) {
        ps.setInt(1, soLuongDaXoa); // Số lượng cần tăng
        ps.setInt(2, idSanPham);    // ID của sản phẩm

        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0; // Trả về true nếu cập nhật thành công
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public List<SanPham> getSanPhamByKhuVucKho(int idKhuVucKho) {
    List<SanPham> list = new ArrayList<>();
    String SQL = "SELECT tensp, soluongton, hinhanh, idKhuVucKho FROM SanPham WHERE idKhuVucKho = ?";
    try (PreparedStatement ps = conn.prepareStatement(SQL)) {
        ps.setInt(1, idKhuVucKho);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SanPham sp = new SanPham();
                //sp.setId(rs.getInt("id"));
                //sp.setMaSP(rs.getString("masp"));
                sp.setTensp(rs.getString("tensp"));
                sp.setSoluongton(rs.getInt("soluongton"));
                sp.setHinhanh(rs.getString("hinhanh"));
                sp.setIdKhuVucKho(rs.getInt("idKhuVucKho"));
                list.add(sp);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

}
