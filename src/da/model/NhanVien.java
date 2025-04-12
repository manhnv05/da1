
package da.model;

/**
 *
 * @author ADMIN
 */
public class NhanVien {
    private int id;
    private int idVaiTro;
    private String maNV;
    private String ho;
    private String ten;
    private String email;
    private String diaChi;
    private String chucVu;
    private String ngayVaoLam;
    private boolean gioiTinh;
    private String ngaySinh;
    private String sdt;
    private String trangThai;

    public NhanVien() {
    }

    public NhanVien(int id, int idVaiTro, String maNV, String ho, String ten, String email, String diaChi, String chucVu, String ngayVaoLam, boolean gioiTinh, String ngaySinh, String sdt, String trangThai) {
        this.id = id;
        this.idVaiTro = idVaiTro;
        this.maNV = maNV;
        this.ho = ho;
        this.ten = ten;
        this.email = email;
        this.diaChi = diaChi;
        this.chucVu = chucVu;
        this.ngayVaoLam = ngayVaoLam;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVaiTro() {
        return idVaiTro;
    }

    public void setIdVaiTro(int idVaiTro) {
        this.idVaiTro = idVaiTro;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
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

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(String ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "NhanVien{" + "id=" + id + ", idVaiTro=" + idVaiTro + ", maNV=" + maNV + ", ho=" + ho + ", ten=" + ten + ", email=" + email + ", diaChi=" + diaChi + ", chucVu=" + chucVu + ", ngayVaoLam=" + ngayVaoLam + ", gioiTinh=" + gioiTinh + ", ngaySinh=" + ngaySinh + ", sdt=" + sdt + ", trangThai=" + trangThai + '}';
    }

    
}
