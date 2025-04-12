/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package da.model;

/**
 *
 * @author ADMIN
 */
public class NhaCC {
    private int id;
    private String ten;
    private String diaChi;
    private String sdt;
    private String email;

    public NhaCC() {
    }

    public NhaCC(int id, String ten) {
        this.id = id;
        this.ten = ten;
    }
    
    

    public NhaCC(int id, String ten, String diaChi, String sdt, String email) {
        this.id = id;
        this.ten = ten;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "NhaCC{" + "id=" + id + ", ten=" + ten + ", diaChi=" + diaChi + ", sdt=" + sdt + ", email=" + email + '}';
    }
    
    
}
