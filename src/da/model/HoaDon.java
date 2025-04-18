
package da.model;

import java.sql.Timestamp;


public class HoaDon {
    private int id;
    private String maHoaDon;
    private Timestamp ngayTao;
    private String trangThai; // 0: Chưa thanh toán, 1: Đã thanh toán, 2: Hủy
    private String ghiChu;
    private String tenKhachHang;
    private int nhanVienID;
    private String tenNhanVien;
    private String hinhThucThanhToan;

    public HoaDon() {
    }

    public HoaDon(int id, String maHoaDon, Timestamp ngayTao, String trangThai, String ghiChu, String tenKhachHang, int nhanVienID, String tenNhanVien, String hinhThucThanhToan) {
        this.id = id;
        this.maHoaDon = maHoaDon;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
        this.tenKhachHang = tenKhachHang;
        this.nhanVienID = nhanVienID;
        this.tenNhanVien = tenNhanVien;
        this.hinhThucThanhToan = hinhThucThanhToan;
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

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public int getNhanVienID() {
        return nhanVienID;
    }

    public void setNhanVienID(int nhanVienID) {
        this.nhanVienID = nhanVienID;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }
    
    
}
