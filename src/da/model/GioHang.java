/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package da.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author ADMIN
 */
public class GioHang {
    private int id;
    private int idNguoiDung;
    private int idSanPham;
    private String maSP;
    private String tenSP;
    private BigDecimal tongTien; // Cập nhật từ "gia" thành "tongTien" để phản ánh đúng cột cơ sở dữ liệu
    private int soLuong;
    private Timestamp ngayThem;

    // Constructor mặc định
    public GioHang() {
    }

    // Constructor đầy đủ
    public GioHang(int id, int idNguoiDung, int idSanPham, String maSP, String tenSP, BigDecimal tongTien, int soLuong, Timestamp ngayThem) {
        this.id = id;
        this.idNguoiDung = idNguoiDung;
        this.idSanPham = idSanPham;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.tongTien = tongTien;
        this.soLuong = soLuong;
        this.ngayThem = ngayThem;
    }

    // Getter và Setter
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

    // Phương thức toString để debug
    @Override
    public String toString() {
        return "GioHang{" +
               "id=" + id +
               ", idNguoiDung=" + idNguoiDung +
               ", idSanPham=" + idSanPham +
               ", maSP='" + maSP + '\'' +
               ", tenSP='" + tenSP + '\'' +
               ", tongTien=" + tongTien +
               ", soLuong=" + soLuong +
               ", ngayThem=" + ngayThem +
               '}';
    }
}