package da.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class GioHang {
    private Integer id;
    private int idNguoiDung;
    private int idSanPham;
    private String maSP;
    private String tenSP;
    private String tenMauSac;   // Thêm tên màu sắc
    private String kichThuoc;   // Thêm kích thước
    private BigDecimal tongTien;
    private int soLuong;
    private String hinhAnh;

    // Constructor mặc định
    public GioHang() {
    }

    // Constructor đầy đủ
    public GioHang(Integer id, int idNguoiDung, int idSanPham, String maSP, String tenSP,
                   String tenMauSac, String kichThuoc,
                   BigDecimal tongTien, int soLuong) {
        this.id = id;
        this.idNguoiDung = idNguoiDung;
        this.idSanPham = idSanPham;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.tenMauSac = tenMauSac;
        this.kichThuoc = kichThuoc;
        this.tongTien = tongTien;
        this.soLuong = soLuong;
    }

    public GioHang(Integer id, int idNguoiDung, int idSanPham, String maSP, String tenSP, String tenMauSac, String kichThuoc, BigDecimal tongTien, int soLuong, String hinhAnh) {
        this.id = id;
        this.idNguoiDung = idNguoiDung;
        this.idSanPham = idSanPham;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.tenMauSac = tenMauSac;
        this.kichThuoc = kichThuoc;
        this.tongTien = tongTien;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
    

    public Integer getId() {
        return id;
    }

    // Getters và Setters
    public void setId(Integer id) {    
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
               '}';
    }
}
