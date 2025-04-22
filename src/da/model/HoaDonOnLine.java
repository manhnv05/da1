
package da.model;

import java.sql.Timestamp;

/**
 *
 * @author ADMIN
 */
public class HoaDonOnLine {
    private int id;
    private String maHoaDon;
    private Timestamp ngayTao;
    private int trangThai; // 0: Chờ xác nhận, 1: Đang giao, 2: Hoàn tất, 3: Hủy
    private String soDienThoai;
    private String diaChiGiaoHang;
    private String hinhThucThanhToan;
    private String hinhThucVanChuyen;
    private String luuy;

    public HoaDonOnLine() {
    }

    public HoaDonOnLine(int id, String maHoaDon, Timestamp ngayTao, int trangThai, String soDienThoai, String diaChiGiaoHang, String hinhThucThanhToan, String hinhThucVanChuyen, String luuy) {
        this.id = id;
        this.maHoaDon = maHoaDon;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        this.soDienThoai = soDienThoai;
        this.diaChiGiaoHang = diaChiGiaoHang;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.hinhThucVanChuyen = hinhThucVanChuyen;
        this.luuy = luuy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public Timestamp getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Timestamp ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChiGiaoHang() {
        return diaChiGiaoHang;
    }

    public void setDiaChiGiaoHang(String diaChiGiaoHang) {
        this.diaChiGiaoHang = diaChiGiaoHang;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public String getHinhThucVanChuyen() {
        return hinhThucVanChuyen;
    }

    public void setHinhThucVanChuyen(String hinhThucVanChuyen) {
        this.hinhThucVanChuyen = hinhThucVanChuyen;
    }

    public String getLuuy() {
        return luuy;
    }

    public void setLuuy(String luuy) {
        this.luuy = luuy;
    }
    
    
}
