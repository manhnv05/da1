/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package da.model;

/**
 *
 * @author ADMIN
 */
public class KichThuoc {
    private int id;
    private String ten;

    public KichThuoc() {
    }

    public KichThuoc(int id, String ten) {
        this.id = id;
        this.ten = ten;
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
    
    

    @Override
    public String toString() {
        return "KichThuoc{" + "id=" + id + ", ten=" + ten + '}';
    }
    
    
}
