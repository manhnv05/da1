/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package da.model;

/**
 *
 * @author ADMIN
 */
public class VaiTro {
    private int id;
    private String tenVaiTro;

    public VaiTro() {
    }

    public VaiTro(int id, String tenVaiTro) {
        this.id = id;
        this.tenVaiTro = tenVaiTro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenVaiTro() {
        return tenVaiTro;
    }

    public void setTenVaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }
    
    
    
}
