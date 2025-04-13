
package da.model;

/**
 *
 * @author ADMIN
 */
public class KhuVucKho {
    private int id;
    private String tenKhuVuc;
    private String moTa;

    public KhuVucKho() {
    }

    public KhuVucKho(int id, String tenKhuVuc, String moTa) {
        this.id = id;
        this.tenKhuVuc = tenKhuVuc;
        this.moTa = moTa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKhuVuc() {
        return tenKhuVuc;
    }

    public void setTenKhuVuc(String tenKhuVuc) {
        this.tenKhuVuc = tenKhuVuc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return "KhuVucKho{" + "id=" + id + ", tenKhuVuc=" + tenKhuVuc + ", moTa=" + moTa + '}';
    }
    
    
}
