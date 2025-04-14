/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package da.model;

import java.sql.Timestamp;

/**
 *
 * @author ADMIN
 */
public class KhoHang {
     private int id;
    private int idSanPham;
    private String tenSP;
    private int soLuongTon;
    private String hinhAnh;
    private int idKhuVucKho;
    private Timestamp ngayNhap;

    public KhoHang() {
    }

    public KhoHang(int id, int idSanPham, String tenSP, int soLuongTon, String hinhAnh, int idKhuVucKho, Timestamp ngayNhap) {
        this.id = id;
        this.idSanPham = idSanPham;
        this.tenSP = tenSP;
        this.soLuongTon = soLuongTon;
        this.hinhAnh = hinhAnh;
        this.idKhuVucKho = idKhuVucKho;
        this.ngayNhap = ngayNhap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getIdKhuVucKho() {
        return idKhuVucKho;
    }

    public void setIdKhuVucKho(int idKhuVucKho) {
        this.idKhuVucKho = idKhuVucKho;
    }

    public Timestamp getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Timestamp ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    @Override
    public String toString() {
        return "KhoHang{" + "id=" + id + ", idSanPham=" + idSanPham + ", tenSP=" + tenSP + ", soLuongTon=" + soLuongTon + ", hinhAnh=" + hinhAnh + ", idKhuVucKho=" + idKhuVucKho + ", ngayNhap=" + ngayNhap + '}';
    }
    
    
}
