/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package da.model;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class NguoiDung {
    private int id;
    private String ho;
    private String ten;
    private String email;
    private String matKhau;
    private int idVaiTro;
    private Date ngayTao;

    public NguoiDung() {
    }

    public NguoiDung(String email) {
        this.email = email;
    }

    public NguoiDung( String email, String matKhau) {
        this.email = email;
        this.matKhau = matKhau;
    }
    
    



    public NguoiDung(String ho, String email, String matKhau) {
        this.ho = ho;
        this.email = email;
        this.matKhau = matKhau;
    }

    public NguoiDung(int id, String ho, String ten, String email, String matKhau) {
        this.id = id;
        this.ho = ho;
        this.ten = ten;
        this.email = email;
        this.matKhau = matKhau;
    }
    
    
    
    
    
    

    public NguoiDung(int id, String ho, String ten, String email, String matKhau, int idVaiTro, Date ngayTao) {
        this.id = id;
        this.ho = ho;
        this.ten = ten;
        this.email = email;
        this.matKhau = matKhau;
        this.idVaiTro = idVaiTro;
        this.ngayTao = ngayTao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getIdVaiTro() {
        return idVaiTro;
    }

    public void setIdVaiTro(int idVaiTro) {
        this.idVaiTro = idVaiTro;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    @Override
    public String toString() {
        return "NguoiDung{" + "id=" + id + ", ho=" + ho + ", ten=" + ten + ", email=" + email + ", matKhau=" + matKhau + ", idVaiTro=" + idVaiTro + ", ngayTao=" + ngayTao + '}';
    }
    
    
}
