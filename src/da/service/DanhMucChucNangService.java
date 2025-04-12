package da.service;

import da.menu.MenuItem;
import da.menu.Menu;
import da.util.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DanhMucChucNangService {
    private Connection conn;

    public DanhMucChucNangService() {
        conn = connectDB.getConnection();
    }

    /**
     * Lấy danh mục chức năng dựa vào idVaiTro
     */
    public List<MenuItem> layDanhMucChucNang(Menu menu, int idVaiTro) {
        List<MenuItem> danhMucList = new ArrayList<>();
        String SQL = """
            SELECT dm.id, dm.tenChucNang 
            FROM DanhMucChucNang dm
            JOIN VaiTro_ChucNang vc ON dm.id = vc.idChucNang
            WHERE vc.idVaiTro = ? 
            ORDER BY dm.id
        """;
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, idVaiTro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idChucNang = rs.getInt("id");
                    String tenChucNang = rs.getString("tenChucNang");
                    String[] menus = {tenChucNang};
                    MenuItem item = new MenuItem(menu, menus, idChucNang, new ArrayList<>());
                    danhMucList.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhMucList;
    }

    /**
     * Lấy vai trò (idVaiTro) của người dùng dựa vào email
     */
    public int getCurrentUserRole(String email) {
        String SQL = """
            SELECT TOP 1 idVaiTro FROM NguoiDung WHERE email = ?
            UNION 
            SELECT TOP 1 idVaiTro FROM NhanVien WHERE email = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, email);
            ps.setString(2, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idVaiTro");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu không tìm thấy vai trò
    }
}