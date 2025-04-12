/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package da.model;

/**
 *
 * @author ADMIN
 */
public class XuatXu {
    private int id;
    private String tenXuatXu;

    public XuatXu() {
    }

    public XuatXu(int id, String tenXuatXu) {
        this.id = id;
        this.tenXuatXu = tenXuatXu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenXuatXu() {
        return tenXuatXu;
    }

    public void setTenXuatXu(String tenXuatXu) {
        this.tenXuatXu = tenXuatXu;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("XuatXu{");
        sb.append("id=").append(id);
        sb.append(", tenXuatXu=").append(tenXuatXu);
        sb.append('}');
        return sb.toString();
    }
    
    
}
