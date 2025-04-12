/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package da.model;

import java.math.BigDecimal;

/**
 *
 * @author ADMIN
 */
public class SanPham {
    private int id;
    private String masp;
    private String tensp;
    private String mota;
    private BigDecimal gia;
    private int soluongton;
    private int idChatLieu;
    private int idXuatXu;
    private int idKichThuoc;
    private int idMauSac;
    private int idNhaCungCap;
    private String hinhanh;
    
    private String tenChatLieu;
private String tenXuatXu;
private String tenKichThuoc;
private String tenMauSac;
private String tenNhaCungCap;



    public SanPham() {
    }

    public SanPham(int id, String masp, String tensp, String mota, BigDecimal gia, int soluongton, int idChatLieu, int idXuatXu, int idKichThuoc, int idMauSac, int idNhaCungCap, String hinhanh) {
        this.id = id;
        this.masp = masp;
        this.tensp = tensp;
        this.mota = mota;
        this.gia = gia;
        this.soluongton = soluongton;
        this.idChatLieu = idChatLieu;
        this.idXuatXu = idXuatXu;
        this.idKichThuoc = idKichThuoc;
        this.idMauSac = idMauSac;
        this.idNhaCungCap = idNhaCungCap;
        this.hinhanh = hinhanh;
    }

    public SanPham(int id, String masp, String tensp, BigDecimal gia, String tenNhaCungCap, String hinhanh) {
        this.id = id;
        this.masp = masp;
        this.tensp = tensp;
        this.gia = gia;
        this.tenNhaCungCap = tenNhaCungCap;
        this.hinhanh = hinhanh;
        
    }
    
    

    public SanPham(int id, String masp, String tensp, String mota, BigDecimal gia, int soluongton, int idChatLieu, int idXuatXu, int idKichThuoc, int idMauSac, int idNhaCungCap, String hinhanh, String tenChatLieu, String tenXuatXu, String tenKichThuoc, String tenMauSac, String tenNhaCungCap) {
        this.id = id;
        this.masp = masp;
        this.tensp = tensp;
        this.mota = mota;
        this.gia = gia;
        this.soluongton = soluongton;
        this.idChatLieu = idChatLieu;
        this.idXuatXu = idXuatXu;
        this.idKichThuoc = idKichThuoc;
        this.idMauSac = idMauSac;
        this.idNhaCungCap = idNhaCungCap;
        this.hinhanh = hinhanh;
        this.tenChatLieu = tenChatLieu;
        this.tenXuatXu = tenXuatXu;
        this.tenKichThuoc = tenKichThuoc;
        this.tenMauSac = tenMauSac;
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public BigDecimal getGia() {
        return gia;
    }

    public void setGia(BigDecimal gia) {
        this.gia = gia;
    }

    public int getSoluongton() {
        return soluongton;
    }

    public void setSoluongton(int soluongton) {
        this.soluongton = soluongton;
    }

    public int getIdChatLieu() {
        return idChatLieu;
    }

    public void setIdChatLieu(int idChatLieu) {
        this.idChatLieu = idChatLieu;
    }

    public int getIdXuatXu() {
        return idXuatXu;
    }

    public void setIdXuatXu(int idXuatXu) {
        this.idXuatXu = idXuatXu;
    }

    public int getIdKichThuoc() {
        return idKichThuoc;
    }

    public void setIdKichThuoc(int idKichThuoc) {
        this.idKichThuoc = idKichThuoc;
    }

    public int getIdMauSac() {
        return idMauSac;
    }

    public void setIdMauSac(int idMauSac) {
        this.idMauSac = idMauSac;
    }

    public int getIdNhaCungCap() {
        return idNhaCungCap;
    }

    public void setIdNhaCungCap(int idNhaCungCap) {
        this.idNhaCungCap = idNhaCungCap;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTenChatLieu() {
        return tenChatLieu;
    }

    public void setTenChatLieu(String tenChatLieu) {
        this.tenChatLieu = tenChatLieu;
    }

    public String getTenXuatXu() {
        return tenXuatXu;
    }

    public void setTenXuatXu(String tenXuatXu) {
        this.tenXuatXu = tenXuatXu;
    }

    public String getTenKichThuoc() {
        return tenKichThuoc;
    }

    public void setTenKichThuoc(String tenKichThuoc) {
        this.tenKichThuoc = tenKichThuoc;
    }

    public String getTenMauSac() {
        return tenMauSac;
    }

    public void setTenMauSac(String tenMauSac) {
        this.tenMauSac = tenMauSac;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }
    
    

    
    public String getTrangThai() {
        if (soluongton <= 0) {
            return "Hết hàng";
        } else {
            return "Còn hàng";
        }
    }

    @Override
    public String toString() {
        return "SanPham{" + "id=" + id + ", masp=" + masp + ", tensp=" + tensp + ", mota=" + mota + ", gia=" + gia + ", soluongton=" + soluongton + ", idChatLieu=" + idChatLieu + ", idXuatXu=" + idXuatXu + ", idKichThuoc=" + idKichThuoc + ", idMauSac=" + idMauSac + ", idNhaCungCap=" + idNhaCungCap + ", hinhanh=" + hinhanh + ", tenChatLieu=" + tenChatLieu + ", tenXuatXu=" + tenXuatXu + ", tenKichThuoc=" + tenKichThuoc + ", tenMauSac=" + tenMauSac + ", tenNhaCungCap=" + tenNhaCungCap + '}';
    }
    
    
}
