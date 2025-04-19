package da.service;

import da.model.GioHang;
import da.util.connectDB;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GioHangService {

    private Connection conn;

    public GioHangService() {
        conn = connectDB.getConnection();
    }

    // Lấy tất cả dữ liệu giỏ hàng
    public ArrayList<GioHang> getAllGioHang() {
        ArrayList<GioHang> list = new ArrayList<>();
        String SQL = "SELECT gh.id, gh.idNguoiDung, gh.idSanPham, sp.maSP, sp.tenSP, " +
                     "gh.tenMauSac, gh.kichThuoc, gh.tongTien, gh.soLuong " +
                     "FROM GioHang gh " +
                     "JOIN SanPham sp ON gh.idSanPham = sp.id"
                    + "WHERE gh.trangThai = 1";

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
                    rs.getInt("soLuong")
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
        String SQL = "INSERT INTO GioHang (idNguoiDung, idSanPham, tenMauSac, kichThuoc, tongTien, soLuong) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, gioHang.getIdNguoiDung());
            ps.setInt(2, gioHang.getIdSanPham());
            ps.setString(3, gioHang.getTenMauSac());
            ps.setString(4, gioHang.getKichThuoc());
            ps.setBigDecimal(5, gioHang.getTongTien());
            ps.setInt(6, gioHang.getSoLuong());

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
        String SQL = "SELECT gh.id, gh.idNguoiDung, gh.idSanPham, sp.maSP, sp.tenSP, sp.hinhanh, " +
                     "gh.tenMauSac, gh.kichThuoc, gh.tongTien, gh.soLuong, gh.trangThai " +
                     "FROM GioHang gh " +
                     "JOIN SanPham sp ON gh.idSanPham = sp.id " +
                     "JOIN NguoiDung nd ON gh.idNguoiDung = nd.id " +
                     "WHERE nd.email = ? AND gh.trangThai = 1";

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
                            rs.getString("hinhanh")
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
        String SQL = "UPDATE GioHang SET trangThai = 0 WHERE id = ?";
    try (PreparedStatement ps = conn.prepareStatement(SQL)) {
        ps.setInt(1, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
    }
    
    public boolean deleteGioHangByIds(List<Integer> ids) {
    if (ids == null || ids.isEmpty()) {
        return false; // Không có ID nào để xóa
    }

    // Tạo câu SQL động với danh sách các ID
    StringBuilder sqlBuilder = new StringBuilder("UPDATE GioHang SET trangThai = 0 WHERE id IN (");
    for (int i = 0; i < ids.size(); i++) {
        sqlBuilder.append("?"); // Thêm dấu ? làm placeholder
        if (i < ids.size() - 1) {
            sqlBuilder.append(", "); // Thêm dấu phẩy giữa các dấu ?
        }
    }
    sqlBuilder.append(")");

    try (PreparedStatement ps = conn.prepareStatement(sqlBuilder.toString())) {
        // Gán giá trị cho các placeholder ?
        for (int i = 0; i < ids.size(); i++) {
            ps.setInt(i + 1, ids.get(i)); // Chỉ số PreparedStatement bắt đầu từ 1
        }

        // Thực thi câu lệnh
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng bị ảnh hưởng
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // Trả về false nếu có lỗi
}
    
    public List<GioHang> getProductsByGioHang(List<Integer> ids) {
    List<GioHang> list = new ArrayList<>();
    if (ids == null || ids.isEmpty()) {
        return list; // Trả về danh sách rỗng nếu danh sách ID rỗng hoặc null
    }
    
    // Tạo câu SQL với điều kiện IN cho danh sách ID
    StringBuilder SQL = new StringBuilder(
        "SELECT gh.id, gh.idNguoiDung, gh.idSanPham, sp.tensp, sp.hinhanh, gh.tenMauSac, gh.kichThuoc, gh.tongTien, gh.soLuong "
        + "FROM GioHang gh "
        + "JOIN SanPham sp ON gh.idSanPham = sp.id "
        + "WHERE gh.id IN ("
    );
    
    // Thêm số lượng dấu ? tương ứng với số lượng ID
    SQL.append(String.join(",", ids.stream().map(id -> "?").toArray(String[]::new)));
    SQL.append(")");
    
    try (PreparedStatement ps = conn.prepareStatement(SQL.toString())) {
        // Gán giá trị cho các tham số (?)
        for (int i = 0; i < ids.size(); i++) {
            ps.setInt(i + 1, ids.get(i));
        }
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                GioHang gioHang = new GioHang();
                gioHang.setId(rs.getInt("id"));
                gioHang.setIdNguoiDung(rs.getInt("idNguoiDung"));
                gioHang.setIdSanPham(rs.getInt("idSanPham"));
                gioHang.setTenSP(rs.getString("tensp"));
                gioHang.setHinhAnh(rs.getString("hinhanh"));
                gioHang.setTenMauSac(rs.getString("tenMauSac"));
                gioHang.setKichThuoc(rs.getString("kichThuoc"));
                gioHang.setTongTien(rs.getBigDecimal("tongTien"));
                gioHang.setSoLuong(rs.getInt("soLuong"));
                list.add(gioHang);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
    
    // Cập nhật sản phẩm trong giỏ hàng
public boolean updateGioHang(int id, int soLuong, String tenMauSac, String kichThuoc) {
    String getGiaSQL = "SELECT gia FROM SanPham sp JOIN GioHang gh ON sp.id = gh.idSanPham WHERE gh.id = ?";
    String updateSQL = "UPDATE GioHang SET soLuong = ?, tenMauSac = ?, kichThuoc = ?, tongTien = ? WHERE id = ?";

    try (PreparedStatement psGetGia = conn.prepareStatement(getGiaSQL);
         PreparedStatement psUpdate = conn.prepareStatement(updateSQL)) {

        // Lấy giá sản phẩm từ bảng SanPham
        psGetGia.setInt(1, id);
        try (ResultSet rs = psGetGia.executeQuery()) {
            if (rs.next()) {
                BigDecimal giaSanPham = rs.getBigDecimal("gia");

                // Tính tổng tiền
                BigDecimal tongTien = giaSanPham.multiply(BigDecimal.valueOf(soLuong));

                // Cập nhật giỏ hàng
                psUpdate.setInt(1, soLuong);
                psUpdate.setString(2, tenMauSac);
                psUpdate.setString(3, kichThuoc);
                psUpdate.setBigDecimal(4, tongTien);
                psUpdate.setInt(5, id);

                return psUpdate.executeUpdate() > 0; // Trả về true nếu cập nhật thành công
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // Trả về false nếu có lỗi xảy ra
}


}
