
package da.model;

import java.sql.Timestamp;

/**
 *
 * @author ADMIN
 */
public class NhapKho {
    private int id;
    private String manhap;
    private String nhacungcap;
    private String nhanvien;
    private Timestamp ngaynhap;
    private String sanPham;
    private int soLuong;
    private double tongtien;
    private int idNhaCungCap;
    private int idNhanVien;
    private int idsanpham;
    
    
    public NhapKho() {
    }

    public NhapKho(int id, String manhap, String nhacungcap, String nhanvien, Timestamp ngaynhap, String sanPham, int soLuong, double tongtien, int idNhaCungCap, int idNhanVien, int idsanpham) {
        this.id = id;
        this.manhap = manhap;
        this.nhacungcap = nhacungcap;
        this.nhanvien = nhanvien;
        this.ngaynhap = ngaynhap;
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.tongtien = tongtien;
        this.idNhaCungCap = idNhaCungCap;
        this.idNhanVien = idNhanVien;
        this.idsanpham = idsanpham;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManhap() {
        return manhap;
    }

    public void setManhap(String manhap) {
        this.manhap = manhap;
    }

    public String getNhacungcap() {
        return nhacungcap;
    }

    public void setNhacungcap(String nhacungcap) {
        this.nhacungcap = nhacungcap;
    }

    public String getNhanvien() {
        return nhanvien;
    }

    public void setNhanvien(String nhanvien) {
        this.nhanvien = nhanvien;
    }

    public Timestamp getNgaynhap() {
        return ngaynhap;
    }

    public void setNgaynhap(Timestamp ngaynhap) {
        this.ngaynhap = ngaynhap;
    }

    public String getSanPham() {
        return sanPham;
    }

    public void setSanPham(String sanPham) {
        this.sanPham = sanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }

    public int getIdNhaCungCap() {
        return idNhaCungCap;
    }

    public void setIdNhaCungCap(int idNhaCungCap) {
        this.idNhaCungCap = idNhaCungCap;
    }

    public int getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public int getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        this.idsanpham = idsanpham;
    }

    
}
