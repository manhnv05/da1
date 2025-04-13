package da.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GioHang {
    private int id;
    private int idNguoiDung;
    private int idSanPham;
    private String maSP;
    private String tenSP;
    private String tenMauSac;   // Thêm tên màu sắc
    private String kichThuoc;   // Thêm kích thước
    private BigDecimal tongTien;
    private int soLuong;
    private Timestamp ngayThem;

    // Constructor mặc định
    public GioHang() {
    }

    // Constructor đầy đủ
    public GioHang(int id, int idNguoiDung, int idSanPham, String maSP, String tenSP,
                   String tenMauSac, String kichThuoc,
                   BigDecimal tongTien, int soLuong, Timestamp ngayThem) {
        this.id = id;
        this.idNguoiDung = idNguoiDung;
        this.idSanPham = idSanPham;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.tenMauSac = tenMauSac;
        this.kichThuoc = kichThuoc;
        this.tongTien = tongTien;
        this.soLuong = soLuong;
        this.ngayThem = ngayThem;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(int idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getTenMauSac() {
        return tenMauSac;
    }

    public void setTenMauSac(String tenMauSac) {
        this.tenMauSac = tenMauSac;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Timestamp getNgayThem() {
        return ngayThem;
    }

    public void setNgayThem(Timestamp ngayThem) {
        this.ngayThem = ngayThem;
    }

    @Override
    public String toString() {
        return "GioHang{" +
               "id=" + id +
               ", idNguoiDung=" + idNguoiDung +
               ", idSanPham=" + idSanPham +
               ", maSP='" + maSP + '\'' +
               ", tenSP='" + tenSP + '\'' +
               ", tenMauSac='" + tenMauSac + '\'' +
               ", kichThuoc='" + kichThuoc + '\'' +
               ", tongTien=" + tongTien +
               ", soLuong=" + soLuong +
               ", ngayThem=" + ngayThem +
               '}';
    }
}
