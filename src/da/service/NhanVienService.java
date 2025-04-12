
package da.service;

import da.model.NhanVien;
import da.util.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author ADMIN
 */
public class NhanVienService {
        private Connection conn;

    public NhanVienService() {
        conn = connectDB.getConnection();
    }
    
    public ArrayList<NhanVien> searchNhanVien(String keyword) {
        String SQL = "SELECT * FROM NhanVien";

        if (keyword != null && !keyword.trim().isEmpty()) {
            SQL += " WHERE maNV LIKE ? OR ho LIKE ? OR ten LIKE ?";
        }

        ArrayList<NhanVien> nhanVienList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            if (keyword != null && !keyword.trim().isEmpty()) {
                String searchPattern = "%" + keyword.trim() + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nhanVienList.add(new NhanVien(
                        rs.getInt("id"),
                        rs.getInt("idVaiTro"),
                        rs.getString("maNV"),
                        rs.getString("ho"),
                        rs.getString("ten"),
                        rs.getString("email"),
                        rs.getString("diaChi"),
                        rs.getString("chucVu"),
                        rs.getString("ngayVaoLam"),
                        rs.getBoolean("gioiTinh"),
                        rs.getString("ngaySinh"),
                        rs.getString("sdt"),
                        rs.getString("trangThai")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nhanVienList;
    }

    // 📋 Lấy danh sách tất cả nhân viên
    public ArrayList<NhanVien> getAllNhanVien() {
        String SQL = "SELECT * FROM NhanVien";
        ArrayList<NhanVien> nhanVienList = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                nhanVienList.add(new NhanVien(
                        rs.getInt("id"),
                        rs.getInt("idVaiTro"),
                        rs.getString("maNV"),
                        rs.getString("ho"),
                        rs.getString("ten"),
                        rs.getString("email"),
                        rs.getString("diaChi"),
                        rs.getString("chucVu"),
                        rs.getString("ngayVaoLam"),
                        rs.getBoolean("gioiTinh"),
                        rs.getString("ngaySinh"),
                        rs.getString("sdt"),
                        rs.getString("trangThai")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nhanVienList;
    }
    
    public boolean addNhanVien(NhanVien nhanVien) {
        String SQL = "INSERT INTO NhanVien (idVaiTro, maNV, ho, ten, email, diaChi, chucVu, ngayVaoLam, gioiTinh, ngaySinh, sdt, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, nhanVien.getIdVaiTro());
            ps.setString(2, nhanVien.getMaNV());
            ps.setString(3, nhanVien.getHo());
            ps.setString(4, nhanVien.getTen());
            ps.setString(5, nhanVien.getEmail());
            ps.setString(6, nhanVien.getDiaChi());
            ps.setString(7, nhanVien.getChucVu());
            ps.setString(8, nhanVien.getNgayVaoLam());
            ps.setBoolean(9, nhanVien.isGioiTinh());
            ps.setString(10, nhanVien.getNgaySinh());
            ps.setString(11, nhanVien.getSdt());
            ps.setString(12, nhanVien.getTrangThai());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateNhanVien(NhanVien nhanVien) {
        // Đặt giá trị idVaiTro cố định là 2 trong cơ sở dữ liệu
        String SQL = "UPDATE NhanVien SET maNV = ?, ho = ?, ten = ?, email = ?, diaChi = ?, chucVu = ?, ngayVaoLam = ?, gioiTinh = ?, ngaySinh = ?, sdt = ?, trangThai = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, nhanVien.getMaNV());
            ps.setString(2, nhanVien.getHo());
            ps.setString(3, nhanVien.getTen());
            ps.setString(4, nhanVien.getEmail());
            ps.setString(5, nhanVien.getDiaChi());
            ps.setString(6, nhanVien.getChucVu());
            ps.setString(7, nhanVien.getNgayVaoLam());
            ps.setBoolean(8, nhanVien.isGioiTinh());
            ps.setString(9, nhanVien.getNgaySinh());
            ps.setString(10, nhanVien.getSdt());
            ps.setString(11, nhanVien.getTrangThai());
            ps.setInt(12, nhanVien.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    public boolean deleteNhanVien(int id) {
        String SQL = "DELETE FROM NhanVien WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean checkMaNhanVienExists(String maNV) {
        String SQL = "SELECT COUNT(*) FROM NhanVien WHERE maNV = ?";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, maNV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu số lượng > 0 tức là đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public HashSet<String> getAllChucVu() {
        String SQL = "SELECT DISTINCT chucVu FROM NhanVien";
        HashSet<String> chucVuSet = new HashSet<>();  // Dùng HashSet để đảm bảo không có trùng lặp

        try (PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                chucVuSet.add(rs.getString("chucVu"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chucVuSet;
    }


    public HashSet<String> getAllTrangThai() {
        String SQL = "SELECT DISTINCT trangThai FROM NhanVien";
        HashSet<String> trangThaiSet = new HashSet<>();  // Dùng HashSet để đảm bảo không có trùng lặp

        try (PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                trangThaiSet.add(rs.getString("trangThai"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trangThaiSet;
    }
    
    public NhanVien getNhanVienById(int id) {
        // Tìm kiếm nhân viên trong cơ sở dữ liệu dựa trên ID
        String query = "SELECT * FROM NhanVien WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);  // Gán giá trị ID vào câu lệnh SQL
            ResultSet rs = ps.executeQuery();  // Thực thi câu lệnh và lấy kết quả

            if (rs.next()) {  // Nếu có kết quả
                NhanVien nhanVien = new NhanVien();
                nhanVien.setId(rs.getInt("id"));
                nhanVien.setIdVaiTro(rs.getInt("idVaiTro"));
                nhanVien.setMaNV(rs.getString("maNV"));
                nhanVien.setHo(rs.getString("ho"));
                nhanVien.setTen(rs.getString("ten"));
                nhanVien.setGioiTinh(rs.getBoolean("gioiTinh"));
                nhanVien.setNgaySinh(rs.getString("ngaySinh"));
                nhanVien.setSdt(rs.getString("sdt"));
                nhanVien.setEmail(rs.getString("email"));
                nhanVien.setDiaChi(rs.getString("diaChi"));  // Địa chỉ
                nhanVien.setChucVu(rs.getString("chucVu"));  // Chức vụ
                nhanVien.setTrangThai(rs.getString("trangThai"));  // Trạng thái
                nhanVien.setNgayVaoLam(rs.getString("ngayVaoLam"));  // Ngày vào làm
                return nhanVien;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Trả về null nếu không tìm thấy nhân viên
    }

    public boolean checkEmailNhanVienExists(String email) {
        String SQL = "SELECT COUNT(*) FROM NhanVien WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu số lượng > 0 tức là đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
