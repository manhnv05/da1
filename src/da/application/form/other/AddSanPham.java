
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
public class AddSanPham extends javax.swing.JPanel {
    SanPhamService service = new SanPhamService();
    /**
     * Creates new form AddNhanVien
     */
    public AddSanPham() {
        initComponents();
        initChatLieu();
        initXuatXu();
        initKT();
        initMS();
        initNCC();
        initKhuVucKho();
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
    
    public boolean checkForm() {
        String ma = "^[A-Z]{2}\\d{4}$";
        String ten = "^[\\p{L}\\s]+$";
        if (txtMa.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa nhập mã sản phẩm!");
            txtMa.requestFocus();
            return false;
        }
        if (!txtMa.getText().trim().matches(ma)) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Mã Không Hợp lệ!");
            txtMa.requestFocus();
            return false;
        }
        if (txtTen.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa nhập tên sản phẩm!");
            txtTen.requestFocus();
            return false;
        }
        if (txtTen.getText().trim().length() > 50) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Tên sản phẩm không được vượt quá 50 ký tự!");
            txtTen.requestFocus();
            return false;
        }
        if (txtTen.getText().trim().length() < 6) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Tên sản phẩm không được ít hơn 6 kí tự!");
            txtTen.requestFocus();
            return false;
        }
        if (!txtTen.getText().trim().matches(ten)) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Tên Không Hợp lệ!");
            txtTen.requestFocus();
            return false;
        }
        if (txtGia.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa nhập giá sản phẩm!");
            txtGia.requestFocus();
            return false;
        }
        try {
            BigDecimal gia = new BigDecimal(txtGia.getText().trim());
            if (gia.compareTo(BigDecimal.ZERO) < 0) {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Giá không được là số âm!");
                txtGia.requestFocus();
                return false;
            }
            if (gia.compareTo(new BigDecimal("10000000")) >= 0) {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Giá phải nhỏ hơn 10 triệu!");
                txtGia.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Giá phải là số hợp lệ!");
            txtGia.requestFocus();
            return false;
        }
//        if (txtSoLuong.getText().trim().isEmpty()) {
//            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa nhập số lượng tồn!");
//            txtSoLuong.requestFocus();
//            return false;
//        }
//        try {
//            int slt = Integer.parseInt(txtSoLuong.getText().trim());
//            if (slt < 0) {
//                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Số lượng tồn không được âm!");
//                txtSoLuong.requestFocus();
//                return false;
//            }
//            if (slt > 1000) {
//                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Số lượng tồn không được vượt quá 1000!");
//                txtSoLuong.requestFocus();
//                return false;
//            }
//        } catch (NumberFormatException e) {
//            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Số lượng tồn phải là số nguyên!");
//            txtSoLuong.requestFocus();
//            return false;
//        }
        if (txaMoTa.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa nhập mô tả sản phẩm!");
            txaMoTa.requestFocus();
            return false;
        }
        if (txaMoTa.getText().trim().length() > 500) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Mô tả không được vượt quá 500 ký tự!");
            txaMoTa.requestFocus();
            return false;
        }
        if (cboChatLieu.getSelectedItem() == null || cboChatLieu.getSelectedItem().toString().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa chọn chất liệu!");
            cboChatLieu.requestFocus();
            return false;
        }
        if (cboXuatXu.getSelectedItem() == null || cboXuatXu.getSelectedItem().toString().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa chọn xuất xứ!");
            cboXuatXu.requestFocus();
            return false;
        }
        if (cboNhaCC.getSelectedItem() == null || cboNhaCC.getSelectedItem().toString().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa chọn nhà cung cấp!");
            cboNhaCC.requestFocus();
            return false;
        }
        if (cboKichThuoc.getSelectedItem() == null || cboKichThuoc.getSelectedItem().toString().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa chọn kích thước!");
            cboKichThuoc.requestFocus();
            return false;
        }
        if (cboMauSac.getSelectedItem() == null || cboMauSac.getSelectedItem().toString().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa chọn màu sắc!");
            cboMauSac.requestFocus();
            return false;
        }

        return true;
    }
    
    public void addSanPham() {
        if (checkMa() && checkForm()) {
            try {
                String ma = txtMa.getText().trim();
                String ten = txtTen.getText().trim();
                String mota = txaMoTa.getText().trim();
                BigDecimal gia = new BigDecimal(txtGia.getText().trim());
                int soluongTon = 0;
                int idChatLieu = cboChatLieu.getSelectedIndex() + 1;
                int idXuatXu = cboXuatXu.getSelectedIndex() + 1;
                int idKichThuoc = cboKichThuoc.getSelectedIndex() + 1;
                int idMauSac = cboMauSac.getSelectedIndex() + 1;
                int idNhaCungCap = cboNhaCC.getSelectedIndex() + 1;
                int idKhuVucKho = cboKhuVucKho.getSelectedIndex() + 1;
                String hinhanh = (profile != null) ? profile.getPath() : null;
                SanPham sp = new SanPham(0, ma, ten, mota, gia, soluongTon, idChatLieu, idXuatXu, idKichThuoc, idMauSac, idNhaCungCap,idKhuVucKho, hinhanh);
                boolean result = service.addSanPham(sp);
                if (result) {
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm sản phẩm thành công!");
                    Application.showForm(new FormQLSanPham());
                } else {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Thêm sản phẩm thất bại!");
                }
            } catch (Exception e) {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Lỗi: " + e.getMessage());
                e.printStackTrace();
            }
        }
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
        jLabel1.setText("Thêm Sản Phẩm");
        crazyPanel1.add(jLabel1);

        jLabel2.setText("Thông tin chung sản phẩm");
        crazyPanel1.add(jLabel2);

        jLabel3.setText("Được sử dụng để nhận diện sản phẩm");
        crazyPanel1.add(jLabel3);

        jLabel4.setText("Mã SP");
        crazyPanel1.add(jLabel4);
        crazyPanel1.add(txtMa);
        crazyPanel1.add(txtTen);

        jLabel5.setText("Giá");
        crazyPanel1.add(jLabel5);
        crazyPanel1.add(txtGia);

        jLabel10.setText("Mô tả");
        crazyPanel1.add(jLabel10);

        txaMoTa.setColumns(20);
        txaMoTa.setRows(5);
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
        crazyPanel1.add(cboKhuVucKho);

        jLabel18.setText("Chất liệu");
        crazyPanel1.add(jLabel18);

        cboChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        crazyPanel1.add(cboChatLieu);

        jLabel19.setText("Xuất xứ");
        crazyPanel1.add(jLabel19);

        cboXuatXu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        crazyPanel1.add(cboXuatXu);

        jLabel22.setText("Kích thước");
        crazyPanel1.add(jLabel22);

        cboKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        crazyPanel1.add(cboKichThuoc);

        jLabel23.setText("Màu sắc");
        crazyPanel1.add(jLabel23);

        cboMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        crazyPanel1.add(cboMauSac);

        jLabel24.setText("Nhà CC");
        crazyPanel1.add(jLabel24);

        cboNhaCC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        crazyPanel1.add(cboNhaCC);

        jLabel25.setText("Hình ảnh");
        crazyPanel1.add(jLabel25);

        jToolBar1.setRollover(true);
        jToolBar1.setOpaque(false);

        cmdBrowse.setText("Browse");
        cmdBrowse.setFocusable(false);
        cmdBrowse.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdBrowse.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBrowseActionPerformed(evt);
            }
        });
        jToolBar1.add(cmdBrowse);

        cmdDelete.setForeground(new java.awt.Color(255, 0, 0));
        cmdDelete.setText("Delete");
        cmdDelete.setFocusable(false);
        cmdDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDeleteActionPerformed(evt);
            }
        });
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

    private void cmdBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBrowseActionPerformed
        JnaFileChooser ch = new JnaFileChooser();
        ch.addFilter("Image", "png", "jpg");
        boolean act = ch.showOpenDialog(SwingUtilities.getWindowAncestor(this));
        if (act) {
            File file = ch.getSelectedFile();
            resizeAndSetImage(file);
        }
    }//GEN-LAST:event_cmdBrowseActionPerformed

    private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDeleteActionPerformed
        pic.setImage(new FlatSVGIcon("da/icon/svg/profile.svg", 5f));
        profile = null;
    }//GEN-LAST:event_cmdDeleteActionPerformed

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveActionPerformed
        addSanPham();
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
