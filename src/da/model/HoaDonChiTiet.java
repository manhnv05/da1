
package da.model;

import java.math.BigDecimal;
import java.sql.Timestamp;


public class HoaDonChiTiet {
     private int id;
    private int hoaDonID;
    private int gioHangID;
    private String tenSP;
    private int soLuong;
    private BigDecimal donGia;
    private BigDecimal tongTien;
    private String mauSac;
    private String kichThuoc;
    private String tenKH;
    private String tenNV;
    private String maHD;
    private String trangThai;
    private Timestamp ngay;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(int id, int hoaDonID, int gioHangID, String tenSP, int soLuong, BigDecimal donGia, BigDecimal tongTien, String mauSac, String kichThuoc, String tenKH, String tenNV, String maHD, String trangThai, Timestamp ngay) {
        this.id = id;
        this.hoaDonID = hoaDonID;
        this.gioHangID = gioHangID;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.tongTien = tongTien;
        this.mauSac = mauSac;
        this.kichThuoc = kichThuoc;
        this.tenKH = tenKH;
        this.tenNV = tenNV;
        this.maHD = maHD;
        this.trangThai = trangThai;
        this.ngay = ngay;
    }
    
    public HoaDonChiTiet(int gioHangID) {
        this.gioHangID = gioHangID;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHoaDonID() {
        return hoaDonID;
    }

    public void setHoaDonID(int hoaDonID) {
        this.hoaDonID = hoaDonID;
    }

    public int getGioHangID() {
        return gioHangID;
    }

    public void setGioHangID(int gioHangID) {
        this.gioHangID = gioHangID;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Timestamp getNgay() {
        return ngay;
    }

    public void setNgay(Timestamp ngay) {
        this.ngay = ngay;
    }
    
    
}
