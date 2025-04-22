
package da.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author ADMIN
 */
public class HoaDonOnlineChiTiet {
    private int id;
    private int hoadononlineID;
    private int gioHangID;
    private String tenSP;
    private int soLuong;
    private BigDecimal donGia;
    private BigDecimal tongTien;
    private String mauSac;
    private String kichThuoc;
    private String tenKH;
    private String SDT;
    private String diaChiGiaoHang;
    private String HinhThucVanChuyen;
    private String maHD;
    private int trangThai;
    private Timestamp ngay;

    public HoaDonOnlineChiTiet() {
    }

    public HoaDonOnlineChiTiet(int id, int hoadononlineID, int gioHangID, String tenSP, int soLuong, BigDecimal donGia, BigDecimal tongTien, String mauSac, String kichThuoc, String tenKH, String SDT, String diaChiGiaoHang, String HinhThucVanChuyen, String maHD, int trangThai, Timestamp ngay) {
        this.id = id;
        this.hoadononlineID = hoadononlineID;
        this.gioHangID = gioHangID;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.tongTien = tongTien;
        this.mauSac = mauSac;
        this.kichThuoc = kichThuoc;
        this.tenKH = tenKH;
        this.SDT = SDT;
        this.diaChiGiaoHang = diaChiGiaoHang;
        this.HinhThucVanChuyen = HinhThucVanChuyen;
        this.maHD = maHD;
        this.trangThai = trangThai;
        this.ngay = ngay;
    }

    
    
    public HoaDonOnlineChiTiet(int gioHangID) {
        this.gioHangID = gioHangID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHoadononlineID() {
        return hoadononlineID;
    }

    public void setHoadononlineID(int hoadononlineID) {
        this.hoadononlineID = hoadononlineID;
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

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiaChiGiaoHang() {
        return diaChiGiaoHang;
    }

    public void setDiaChiGiaoHang(String diaChiGiaoHang) {
        this.diaChiGiaoHang = diaChiGiaoHang;
    }

    public String getHinhThucVanChuyen() {
        return HinhThucVanChuyen;
    }

    public void setHinhThucVanChuyen(String HinhThucVanChuyen) {
        this.HinhThucVanChuyen = HinhThucVanChuyen;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public Timestamp getNgay() {
        return ngay;
    }

    public void setNgay(Timestamp ngay) {
        this.ngay = ngay;
    }


    
    
    
}
