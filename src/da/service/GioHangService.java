package da.service;

import da.model.GioHang;
import da.util.connectDB;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GioHangService {

    private Connection conn;

    public GioHangService() {
        conn = connectDB.getConnection();
    }

    // Lấy tất cả dữ liệu giỏ hàng
    public ArrayList<GioHang> getAllGioHang() {
        ArrayList<GioHang> list = new ArrayList<>();
        String SQL = "SELECT gh.id, gh.idNguoiDung, gh.idSanPham, sp.maSP, sp.tenSP, " +
                     "gh.tenMauSac, gh.kichThuoc, gh.tongTien, gh.soLuong, gh.ngayThem " +
                     "FROM GioHang gh " +
                     "JOIN SanPham sp ON gh.idSanPham = sp.id";

        try (PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                GioHang gh = new GioHang(
                    rs.getInt("id"),
                    rs.getInt("idNguoiDung"),
                    rs.getInt("idSanPham"),
                    rs.getString("maSP"),
                    rs.getString("tenSP"),
                    rs.getString("tenMauSac"),
                    rs.getString("kichThuoc"),
                    rs.getBigDecimal("tongTien"),
                    rs.getInt("soLuong"),
                    rs.getTimestamp("ngayThem")
                );
                list.add(gh);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm sản phẩm vào giỏ hàng
    public boolean addGioHang(GioHang gioHang) {
        String SQL = "INSERT INTO GioHang (idNguoiDung, idSanPham, tenMauSac, kichThuoc, tongTien, soLuong, ngayThem) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, gioHang.getIdNguoiDung());
            ps.setInt(2, gioHang.getIdSanPham());
            ps.setString(3, gioHang.getTenMauSac());
            ps.setString(4, gioHang.getKichThuoc());
            ps.setBigDecimal(5, gioHang.getTongTien());
            ps.setInt(6, gioHang.getSoLuong());
            ps.setTimestamp(7, gioHang.getNgayThem());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy id sản phẩm dựa vào mã sản phẩm
    public int getIdByMaSP(String maSP) {
        String SQL = "SELECT id FROM SanPham WHERE maSP = ?";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, maSP);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Lấy giỏ hàng theo email người dùng
    public ArrayList<GioHang> getGioHangByEmail(String email) {
        ArrayList<GioHang> list = new ArrayList<>();
        String SQL = "SELECT gh.id, gh.idNguoiDung, gh.idSanPham, sp.maSP, sp.tenSP, " +
                     "gh.tenMauSac, gh.kichThuoc, gh.tongTien, gh.soLuong, gh.ngayThem " +
                     "FROM GioHang gh " +
                     "JOIN SanPham sp ON gh.idSanPham = sp.id " +
                     "JOIN NguoiDung nd ON gh.idNguoiDung = nd.id " +
                     "WHERE nd.email = ?";

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    GioHang gh = new GioHang(
                        rs.getInt("id"),
                        rs.getInt("idNguoiDung"),
                        rs.getInt("idSanPham"),
                        rs.getString("maSP"),
                        rs.getString("tenSP"),
                        rs.getString("tenMauSac"),
                        rs.getString("kichThuoc"),
                        rs.getBigDecimal("tongTien"),
                        rs.getInt("soLuong"),
                        rs.getTimestamp("ngayThem")
                    );
                    list.add(gh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Xóa sản phẩm khỏi giỏ hàng theo id
    public boolean deleteGioHangById(int id) {
        String SQL = "DELETE FROM GioHang WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateGioHang(int id, int soLuong, BigDecimal tongTien, String tenMauSac, String kichThuoc) {
    String SQL = "UPDATE GioHang SET soLuong = ?, tongTien = ?, tenMauSac = ?, kichThuoc = ? WHERE id = ?";
    try (PreparedStatement ps = conn.prepareStatement(SQL)) {
        ps.setInt(1, soLuong);
        ps.setBigDecimal(2, tongTien);
        ps.setString(3, tenMauSac);
        ps.setString(4, kichThuoc);
        ps.setInt(5, id);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}
