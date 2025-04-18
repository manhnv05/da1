
package da.application.form.other;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import da.application.Application;
import da.model.SanPham;
import da.model.other.ModelProfile;
import da.service.SanPhamService;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashSet;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import jnafilechooser.api.JnaFileChooser;
import raven.toast.Notifications;



/**
 *
 * @author ADMIN
 */
public class ChiTietSanPham extends javax.swing.JPanel {
    SanPhamService service = new SanPhamService();
    private SanPham sanpham;
    /**
     * Creates new form AddNhanVien
     */
    public ChiTietSanPham(SanPham sanpham) {
        this.sanpham = sanpham;
        initComponents();
        initChatLieu();
        initXuatXu();
        initKT();
        initMS();
        initNCC();
        initKhuVucKho();
        populateFormData();
    }
    
    private void populateFormData() {
        txtMa.setText(sanpham.getMasp());
        txtTen.setText(sanpham.getTensp());
        txaMoTa.setText(sanpham.getMota());
        txtGia.setText(sanpham.getGia().toString());
        cboKhuVucKho.setSelectedIndex(sanpham.getIdKhuVucKho());
        cboChatLieu.setSelectedIndex(sanpham.getIdChatLieu());
        cboXuatXu.setSelectedIndex(sanpham.getIdXuatXu());
        cboKichThuoc.setSelectedIndex(sanpham.getIdKichThuoc());
        cboMauSac.setSelectedIndex(sanpham.getIdMauSac());
        cboNhaCC.setSelectedIndex(sanpham.getIdNhaCungCap());
        if (sanpham.getHinhanh() != null) {
            File file = new File(sanpham.getHinhanh());
            resizeAndSetImage(file);
        }
    }

    public void initChatLieu() {
        DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
        boxModel.addElement("-- Chọn chất liệu --");
        HashSet<String> chatLieuSet = service.getAllChatLieu();
        for (String chatLieu : chatLieuSet) {
            if (chatLieu != null) {
                boxModel.addElement(chatLieu);
            }
        }
        cboChatLieu.setModel(boxModel);
    }

    public void initXuatXu() {
        DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
        boxModel.addElement("-- Chọn xuất xứ --");
        HashSet<String> xuatXuSet = service.getAllXuatXu();
        for (String xuatXu : xuatXuSet) {
            if (xuatXu != null) {
                boxModel.addElement(xuatXu);
            }
        }
        cboXuatXu.setModel(boxModel);
    }

    public void initKT() {
        DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
        boxModel.addElement("-- Chọn kích thước --");
        HashSet<String> kichThuocSet = service.getAllKichThuoc();
        for (String kichThuoc : kichThuocSet) {
            if (kichThuoc != null) {
                boxModel.addElement(kichThuoc);
            }
        }
        cboKichThuoc.setModel(boxModel);
    }

    public void initMS() {
        DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
        boxModel.addElement("-- Chọn màu sắc --");
        HashSet<String> mauSacSet = service.getAllMauSac(); 
        for (String mauSac : mauSacSet) {
            if (mauSac != null) {
                boxModel.addElement(mauSac);
            }
        }
        cboMauSac.setModel(boxModel);
    }

    public void initNCC() {
        DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
        boxModel.addElement("-- Chọn nhà cung cấp --");
        HashSet<String> nhaCCSet = service.getAllNhaCungCap();
        for (String nhaCC : nhaCCSet) {
            if (nhaCC != null) {
                boxModel.addElement(nhaCC);
            }
        }
        cboNhaCC.setModel(boxModel);
    }
    
        public void initKhuVucKho() {
        DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
        boxModel.addElement("-- Chọn khu vực kho --");
        HashSet<String> KVKhoSet = service.getAllKhuVucKho();
        for (String kvucKho : KVKhoSet) {
            if (kvucKho != null) {
                boxModel.addElement(kvucKho);
            }
        }
        cboKhuVucKho.setModel(boxModel); 
    }

    public boolean checkMa() {
        String maSP = txtMa.getText().trim();
        if (service.checkMaSPTonTai(maSP)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Mã Sản Phẩm đã tồn tại. Vui lòng nhập mã khác!");
            txtMa.requestFocus();
            return false;
        }
        return true;
    }

    

    public SanPham getSanPhamFromForm() {
        SanPham sanPham = new SanPham();
        sanPham.setId(this.sanpham.getId());
        sanPham.setMasp(txtMa.getText());
        sanPham.setTensp(txtTen.getText());
        sanPham.setMota(txaMoTa.getText());
        sanPham.setGia(new BigDecimal(txtGia.getText()));
        sanPham.setIdKhuVucKho(cboKhuVucKho.getSelectedIndex());
        sanPham.setHinhanh((profile != null) ? profile.getPath() : null);
        sanPham.setIdChatLieu(cboChatLieu.getSelectedIndex());
        sanPham.setIdXuatXu(cboXuatXu.getSelectedIndex());
        sanPham.setIdKichThuoc(cboKichThuoc.getSelectedIndex());
        sanPham.setIdMauSac(cboMauSac.getSelectedIndex());
        sanPham.setIdNhaCungCap(cboNhaCC.getSelectedIndex());
        return sanPham;
    }

    

    private void resizeAndSetImage(File file) {
        try {
            BufferedImage originalImage = ImageIO.read(file);
            BufferedImage resizedImage = new BufferedImage(250, 250, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, 250, 250, null);
            g2d.dispose();
            pic.setImage(new ImageIcon(resizedImage));
            profile = new ModelProfile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        crazyPanel1 = new raven.crazypanel.CrazyPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaMoTa = new javax.swing.JTextArea();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cboKhuVucKho = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        cboChatLieu = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        cboXuatXu = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        cboKichThuoc = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        cboMauSac = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        cboNhaCC = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        pic = new javaswingdev.picturebox.PictureBox();
        jToolBar1 = new javax.swing.JToolBar();
        cmdBrowse = new javax.swing.JButton();
        cmdDelete = new javax.swing.JButton();
        cmdSave = new javax.swing.JButton();

        crazyPanel1.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20;[light]background:shade(@background,2%);[dark]background:tint(@background,2%)",
            new String[]{
                "font:bold +10",
                "font:bold +1",
                "",
                "",
                "showClearButton:true;JTextField.placeholderText=Mã Sản Phẩm",
                "showClearButton:true;JTextField.placeholderText=Tên Sản Phẩm",
                "",
                "showClearButton:true;JTextField.placeholderText=Giá",
                "",
                "",
                "",
                "font:bold +1",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            }
        ));
        crazyPanel1.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "wrap 2,fillx,insets 25",
            "[grow 0,trail]15[fill]",
            "",
            new String[]{
                "wrap,al lead",
                "wrap,al lead",
                "wrap,al lead",
                "",
                "split 2",
                "",
                "",
                "",
                "",
                "",
                "span 2,grow 1",
                "wrap,al lead",
                "wrap,al lead",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "span 2,al trail"
            }
        ));

        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Chi Tiết Sản Phẩm");
        crazyPanel1.add(jLabel1);

        jLabel2.setText("Thông tin chung sản phẩm");
        crazyPanel1.add(jLabel2);

        jLabel3.setText("Được sử dụng để nhận diện sản phẩm");
        crazyPanel1.add(jLabel3);

        jLabel4.setText("Mã SP");
        crazyPanel1.add(jLabel4);

        txtMa.setEnabled(false);
        crazyPanel1.add(txtMa);

        txtTen.setEnabled(false);
        crazyPanel1.add(txtTen);

        jLabel5.setText("Giá");
        crazyPanel1.add(jLabel5);

        txtGia.setEnabled(false);
        crazyPanel1.add(txtGia);

        jLabel10.setText("Mô tả");
        crazyPanel1.add(jLabel10);

        txaMoTa.setColumns(20);
        txaMoTa.setRows(5);
        txaMoTa.setEnabled(false);
        jScrollPane3.setViewportView(txaMoTa);

        crazyPanel1.add(jScrollPane3);
        crazyPanel1.add(jSeparator2);

        jLabel11.setText("Thông tin kĩ thuật");
        crazyPanel1.add(jLabel11);

        jLabel16.setText("Được sử dụng để phân biệt sản phẩm");
        crazyPanel1.add(jLabel16);

        jLabel17.setText("Khu vực kho");
        crazyPanel1.add(jLabel17);

        cboKhuVucKho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboKhuVucKho.setEnabled(false);
        crazyPanel1.add(cboKhuVucKho);

        jLabel18.setText("Chất liệu");
        crazyPanel1.add(jLabel18);

        cboChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboChatLieu.setEnabled(false);
        crazyPanel1.add(cboChatLieu);

        jLabel19.setText("Xuất xứ");
        crazyPanel1.add(jLabel19);

        cboXuatXu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboXuatXu.setEnabled(false);
        crazyPanel1.add(cboXuatXu);

        jLabel22.setText("Kích thước");
        crazyPanel1.add(jLabel22);

        cboKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboKichThuoc.setEnabled(false);
        crazyPanel1.add(cboKichThuoc);

        jLabel23.setText("Màu sắc");
        crazyPanel1.add(jLabel23);

        cboMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMauSac.setEnabled(false);
        crazyPanel1.add(cboMauSac);

        jLabel24.setText("Nhà CC");
        crazyPanel1.add(jLabel24);

        cboNhaCC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNhaCC.setEnabled(false);
        crazyPanel1.add(cboNhaCC);

        jLabel25.setText("Hình ảnh");
        crazyPanel1.add(jLabel25);

        jToolBar1.setRollover(true);
        jToolBar1.setOpaque(false);

        cmdBrowse.setText("Browse");
        cmdBrowse.setEnabled(false);
        cmdBrowse.setFocusable(false);
        cmdBrowse.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdBrowse.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdBrowse);

        cmdDelete.setForeground(new java.awt.Color(255, 0, 0));
        cmdDelete.setText("Delete");
        cmdDelete.setEnabled(false);
        cmdDelete.setFocusable(false);
        cmdDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cmdDelete);

        javax.swing.GroupLayout picLayout = new javax.swing.GroupLayout(pic);
        pic.setLayout(picLayout);
        picLayout.setHorizontalGroup(
            picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(picLayout.createSequentialGroup()
                .addContainerGap(230, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        picLayout.setVerticalGroup(
            picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(picLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(119, Short.MAX_VALUE))
        );

        crazyPanel1.add(pic);

        cmdSave.setText("Save");
        cmdSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveActionPerformed(evt);
            }
        });
        crazyPanel1.add(cmdSave);

        jScrollPane2.setViewportView(crazyPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveActionPerformed
        Application.showForm(new FormQLSanPham());
    }//GEN-LAST:event_cmdSaveActionPerformed

    private ModelProfile profile;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboChatLieu;
    private javax.swing.JComboBox<String> cboKhuVucKho;
    private javax.swing.JComboBox<String> cboKichThuoc;
    private javax.swing.JComboBox<String> cboMauSac;
    private javax.swing.JComboBox<String> cboNhaCC;
    private javax.swing.JComboBox<String> cboXuatXu;
    private javax.swing.JButton cmdBrowse;
    private javax.swing.JButton cmdDelete;
    private javax.swing.JButton cmdSave;
    private raven.crazypanel.CrazyPanel crazyPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javaswingdev.picturebox.PictureBox pic;
    private javax.swing.JTextArea txaMoTa;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables

}
