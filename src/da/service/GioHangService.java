package da.service;

import da.model.GioHang;
import da.util.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.math.BigDecimal;

public class GioHangService {

    private Connection conn;

    public GioHangService() {
        conn = connectDB.getConnection();
    }

    // Lấy tất cả dữ liệu giỏ hàng
    public ArrayList<GioHang> getAllGioHang() {
        ArrayList<GioHang> list = new ArrayList<>();
        String SQL = "SELECT gh.id, gh.idNguoiDung, gh.idSanPham, sp.maSP, sp.tenSP, gh.tongTien, gh.soLuong, gh.ngayThem " +
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
        String SQL = "INSERT INTO GioHang (idNguoiDung, idSanPham, tongTien, soLuong, ngayThem) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, gioHang.getIdNguoiDung());
            ps.setInt(2, gioHang.getIdSanPham());
            ps.setBigDecimal(3, gioHang.getTongTien()); // Thêm tổng tiền
            ps.setInt(4, gioHang.getSoLuong());
            ps.setTimestamp(5, gioHang.getNgayThem());

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
    
    
    public ArrayList<GioHang> getGioHangByUserId(int idNguoiDung) {
    ArrayList<GioHang> list = new ArrayList<>();
    String SQL = "SELECT gh.id, gh.idNguoiDung, gh.idSanPham, sp.maSP, sp.tenSP, gh.tongTien, gh.soLuong, gh.ngayThem " +
                 "FROM GioHang gh " +
                 "JOIN SanPham sp ON gh.idSanPham = sp.id " +
                 "WHERE gh.idNguoiDung = ?"; // Lọc theo idNguoiDung

    try (PreparedStatement ps = conn.prepareStatement(SQL)) {
        ps.setInt(1, idNguoiDung); // Gán giá trị idNguoiDung vào câu truy vấn
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                GioHang gh = new GioHang(
                    rs.getInt("id"),
                    rs.getInt("idNguoiDung"),
                    rs.getInt("idSanPham"),
                    rs.getString("maSP"),
                    rs.getString("tenSP"),
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
}