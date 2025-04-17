package da.service;

import da.model.NhapKho;
import da.util.connectDB;
import java.sql.Timestamp;
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
        String select = "SELECT " +
            "nk.id, " +
            "nk.maNhap, " +
            "ncc.tenNCC AS tenNhaCungCap, " +
            "nv.ho + ' ' + nv.ten AS tenNhanVien, " +
            "sp.tenSP AS tenSanPham, " +
            "nk.soLuong, " +
            "kvk.tenKhuVuc AS tenKhuVucKho, " +
            "nk.ngayNhap, " +
            "nk.tongTien " +
            "FROM NhapKho nk " +
            "JOIN NhaCungCap ncc ON nk.idNhaCungCap = ncc.id " +
            "JOIN NhanVien nv ON nk.idNhanVien = nv.id " +
            "JOIN SanPham sp ON nk.idSanPham = sp.id " +
            "JOIN KhuVucKho kvk ON nk.idKhuVucKho = kvk.id";

        try {
            PreparedStatement ps = conn.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhapKho nk = new NhapKho();
                nk.setId(rs.getInt("id"));
                nk.setManhap(rs.getString("maNhap"));
                nk.setNhacungcap(rs.getString("tenNhaCungCap"));
                nk.setNhanvien(rs.getString("tenNhanVien"));
                nk.setSanPham(rs.getString("tenSanPham"));
                nk.setSoLuong(rs.getInt("soLuong"));
                nk.setKhuvuckho(rs.getString("tenKhuVucKho"));
                nk.setNgaynhap(rs.getTimestamp("ngayNhap"));
                nk.setTongtien(rs.getDouble("tongTien"));
                list.add(nk);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi truy vấn NhapKho: " + e.getMessage());
        }

        return list;
    }

    public boolean addNhapKho(NhapKho nhapKho, int idNhaCungCap, int idNhanVien, int idSanPham, int idKhuVucKho) {
        String insert = "INSERT INTO NhapKho (maNhap, idNhaCungCap, idNhanVien, idSanPham, idKhuVucKho, soLuong, ngayNhap, tongTien) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(insert);
            ps.setString(1, nhapKho.getManhap());
            ps.setInt(2, idNhaCungCap);
            ps.setInt(3, idNhanVien);
            ps.setInt(4, idSanPham);
            ps.setInt(5, idKhuVucKho);
            ps.setInt(6, nhapKho.getSoLuong());
            ps.setTimestamp(7, nhapKho.getNgaynhap());
            ps.setDouble(8, nhapKho.getTongtien());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding NhapKho: " + e.getMessage());
            return false;
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