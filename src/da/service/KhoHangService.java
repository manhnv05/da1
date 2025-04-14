
package da.service;

import da.model.KhoHang;
import da.util.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class KhoHangService {
    private Connection conn;

    public KhoHangService() {
        conn = connectDB.getConnection();
    }
    
    public List<KhoHang> getAll() {
        List<KhoHang> list = new ArrayList<>();
        String SQL = "SELECT kh.id, kh.idSanPham, sp.tensp, sp.soluongton, sp.hinhanh, kh.idKhuVucKho, kh.ngayNhap "
                + "FROM KhoHang kh "
                + "JOIN SanPham sp ON kh.idSanPham = sp.id";
        try (PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KhoHang khoHang = new KhoHang();
                khoHang.setId(rs.getInt("id"));
                khoHang.setIdSanPham(rs.getInt("idSanPham"));
                khoHang.setTenSP(rs.getString("tensp"));
                khoHang.setSoLuongTon(rs.getInt("soluongton"));
                khoHang.setHinhAnh(rs.getString("hinhanh"));
                khoHang.setIdKhuVucKho(rs.getInt("idKhuVucKho"));
                khoHang.setNgayNhap(rs.getTimestamp("ngayNhap"));
                list.add(khoHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<KhoHang> getProductsByKhuVucKho(int idKhuVucKho) {
    List<KhoHang> list = new ArrayList<>();
    String SQL = "SELECT kh.id, kh.idSanPham, sp.tensp, sp.soluongton, sp.hinhanh, kh.idKhuVucKho, kh.ngayNhap "
                 + "FROM KhoHang kh "
                 + "JOIN SanPham sp ON kh.idSanPham = sp.id "
                 + "WHERE kh.idKhuVucKho = ?";
    try (PreparedStatement ps = conn.prepareStatement(SQL)) {
        ps.setInt(1, idKhuVucKho);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                KhoHang khoHang = new KhoHang();
                khoHang.setId(rs.getInt("id"));
                khoHang.setIdSanPham(rs.getInt("idSanPham"));
                khoHang.setTenSP(rs.getString("tensp"));
                khoHang.setSoLuongTon(rs.getInt("soluongton"));
                khoHang.setHinhAnh(rs.getString("hinhanh"));
                khoHang.setIdKhuVucKho(rs.getInt("idKhuVucKho"));
                khoHang.setNgayNhap(rs.getTimestamp("ngayNhap"));
                list.add(khoHang);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
}
