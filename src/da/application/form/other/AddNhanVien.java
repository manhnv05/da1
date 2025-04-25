
package da.application.form.other;

import da.application.Application;
import da.model.NhanVien;
import da.service.NhanVienService;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import javax.swing.DefaultComboBoxModel;
import raven.toast.Notifications;


public class AddNhanVien extends javax.swing.JPanel {
        NhanVienService service = new NhanVienService();


    public AddNhanVien() {
        initComponents();
        initUI();
        initChucVu();
        initTrangThai();
    }
    
    public void initChucVu() {
    DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
    boxModel.addElement("-- Chọn chức vụ --"); // Giá trị mặc định
    HashSet<String> chucVuSet = service.getAllChucVu();
    for (String chucVu : chucVuSet) {
        if (chucVu != null) {
            boxModel.addElement(chucVu);
        }
    }
    cboChucVu.setModel(boxModel);
}

public void initTrangThai() {
    DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
    boxModel.addElement("-- Chọn trạng thái --"); // Giá trị mặc định
    HashSet<String> trangThaiSet = service.getAllTrangThai();
    for (String trangThai : trangThaiSet) {
        if (trangThai != null) {
            boxModel.addElement(trangThai);
        }
    }
    cboTrangThai.setModel(boxModel);
}


    
    private void initUI() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (datePicker1 != null) {
            datePicker1.setEditor(txtNgaySinh);
            datePicker1.addDateSelectionListener(event -> {
                LocalDate date = datePicker1.getSelectedDate();
                txtNgaySinh.setText((date != null) ? date.format(df) : "");
            });
            datePicker1.setDateSelectionAble(date -> date != null && !date.isAfter(LocalDate.now()));
        }
        if (datePicker2 != null) {
            datePicker2.setEditor(txtNgayLam);
            datePicker2.addDateSelectionListener(event -> {
                LocalDate date = datePicker2.getSelectedDate();
                txtNgayLam.setText((date != null) ? date.format(df) : "");
            });
            datePicker2.setDateSelectionAble(date -> date != null && !date.isAfter(LocalDate.now()));
        }
    }
    
    public boolean checkMa() {     
        String maNV = txtMa.getText().trim();
        if (service.checkMaNhanVienExists(maNV)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Mã Nhân Viên đã tồn tại. Vui lòng nhập mã khác!");
            txtMa.requestFocus();
            return false;
        }
        return true;
    }

    public boolean checkEmail() {
        String email = txtEmail.getText().trim();
        if (service.checkEmailNhanVienExists(email)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Email đã tồn tại. Vui lòng nhập email khác!");
            txtEmail.requestFocus();
            return false;
        }
        return true;
    }

    public boolean checkForm() {
    String maPattern = "^[A-Z]{2}\\d*$"; // Ví dụ: NV0001, HS1234
    String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    String sdtPattern = "^(0\\d{9})$";
    String hoPattern = "^[\\p{Lu}][\\p{L}]*([\\s][\\p{Lu}][\\p{L}]*)*$"; // Họ: mỗi từ viết hoa chữ cái đầu, hỗ trợ Unicode
    String tenPattern = "^[\\p{Lu}][\\p{L}]*([\\s][\\p{Lu}][\\p{L}]*)*$"; // Tên: tương tự họ
    // Kiểm tra họ
    if (txtHo.getText().trim().isEmpty()) {
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa nhập họ!");
        txtHo.requestFocus();
        return false;
    }
    if (!txtHo.getText().trim().matches(hoPattern)) {
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Họ không hợp lệ! Họ chỉ chứa chữ cái và mỗi từ phải viết hoa chữ cái đầu.");
        txtHo.requestFocus();
        return false;
    }

    // Kiểm tra tên
    if (txtTen.getText().trim().isEmpty()) {
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa nhập tên nhân viên!");
        txtTen.requestFocus();
        return false;
    }
    if (!txtTen.getText().trim().matches(tenPattern)) {
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Tên không hợp lệ! Tên chỉ chứa chữ cái và mỗi từ phải viết hoa chữ cái đầu.");
        txtTen.requestFocus();
        return false;
    }

    // Kiểm tra số điện thoại
    if (txtSDT.getText().trim().isEmpty()) {
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa nhập số điện thoại!");
        txtSDT.requestFocus();
        return false;
    } else if (!txtSDT.getText().trim().matches(sdtPattern)) {
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Số điện thoại không hợp lệ!");
        txtSDT.requestFocus();
        return false;
    } else if (service.checkSdtExists(txtSDT.getText().trim())) {
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Số điện thoại đã tồn tại, vui lòng nhập số khác!");
        txtSDT.requestFocus();
        return false;
    }

    // Kiểm tra email
    if (txtEmail.getText().trim().isEmpty()) {
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa nhập email!");
        txtEmail.requestFocus();
        return false;
    } else if (!txtEmail.getText().trim().matches(emailPattern)) {
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Email không hợp lệ!");
        txtEmail.requestFocus();
        return false;
    } else if (service.checkEmailNhanVienExists(txtEmail.getText().trim())) {
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Email đã tồn tại, vui lòng nhập email khác!");
        txtEmail.requestFocus();
        return false;
    }

    // Kiểm tra mã nhân viên
    if (txtMa.getText().trim().isEmpty()) {
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa nhập mã nhân viên!");
        txtMa.requestFocus();
        return false;
    } else if (!txtMa.getText().trim().matches(maPattern)) {
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Mã không hợp lệ!");
        txtMa.requestFocus();
        return false;
    }

    // Kiểm tra địa chỉ
    if (txtDiaChi.getText().trim().isEmpty()) {
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa nhập địa chỉ!");
        txtDiaChi.requestFocus();
        return false;
    }

    // Kiểm tra ngày sinh
    if (datePicker1 != null) {
        LocalDate ngaySinh = datePicker1.getSelectedDate();
        if (ngaySinh == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa nhập ngày sinh!");
            datePicker1.requestFocus();
            return false;
        }
        int tuoi = Period.between(ngaySinh, LocalDate.now()).getYears();
        if (tuoi < 18) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Nhân viên phải đủ 18 tuổi trở lên!");
            datePicker1.requestFocus();
            return false;
        }
    } else {
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Không thể kiểm tra ngày sinh, vui lòng kiểm tra lại!");
        return false;
    }

    // Kiểm tra ngày vào làm
    if (datePicker2 != null) {
        LocalDate ngayLam = datePicker2.getSelectedDate();
        if (ngayLam == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa nhập ngày vào làm!");
            datePicker2.requestFocus();
            return false;
        }
        if (ngayLam.isAfter(LocalDate.now())) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Ngày vào làm không được lớn hơn ngày hiện tại!");
            datePicker2.requestFocus();
            return false;
        }
    } else {
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Không thể kiểm tra ngày vào làm, vui lòng kiểm tra lại!");
        return false;
    }

    // Kiểm tra chức vụ
    if (cboChucVu.getSelectedItem() == null || cboChucVu.getSelectedItem().toString().equals("-- Chọn chức vụ --")) {
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa chọn chức vụ!");
        cboChucVu.requestFocus();
        return false;
    }

    // Kiểm tra trạng thái
    if (cboTrangThai.getSelectedItem() == null || cboTrangThai.getSelectedItem().toString().equals("-- Chọn trạng thái --")) {
        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Bạn chưa chọn trạng thái!");
        cboTrangThai.requestFocus();
        return false;
    }

    return true; // Nếu tất cả điều kiện đều hợp lệ
}

    public void addNV() {
        if (checkMa() && checkEmail()) {
            if (checkForm()) {
                try {
                    String ma = txtMa.getText().trim();
                    String ho = txtHo.getText().trim();
                    String ten = txtTen.getText().trim();
                    String email = txtEmail.getText().trim();
                    String sdt = txtSDT.getText().trim();
                    String diaChi = txtDiaChi.getText().trim();
                    String gioiTinhStr = cboGioiTinh.getSelectedItem().toString();
                    boolean gioiTinh = gioiTinhStr.equalsIgnoreCase("Nam");
                    String ngaySinh = txtNgaySinh.getText().trim();
                    String ngayVaoLam = txtNgayLam.getText().trim();
                    String chucVu = cboChucVu.getSelectedItem().toString();
                    String trangThai = cboTrangThai.getSelectedItem().toString();
                    int idVaiTro = 2;
                    NhanVien nv = new NhanVien(0, idVaiTro, ma, ho, ten, email, diaChi, chucVu, ngayVaoLam, gioiTinh, ngaySinh, sdt, trangThai);
                    boolean result = service.addNhanVien(nv);
                    if (result) {
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm nhân viên thành công!");
                        Application.showForm(new FormNhanVien());
                    } else {
                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Thêm nhân viên thất bại!");
                    }
                } catch (Exception e) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Lỗi: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        datePicker1 = new raven.datetime.component.date.DatePicker();
        datePicker2 = new raven.datetime.component.date.DatePicker();
        crazyPanel1 = new raven.crazypanel.CrazyPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        txtHo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cboGioiTinh = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        txtNgayLam = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        cboChucVu = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        btnAdd = new javax.swing.JButton();

        crazyPanel1.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20;[light]background:shade(@background,2%);[dark]background:tint(@background,2%)",
            new String[]{
                "font:bold +10",
                "font:bold +1",
                "",
                "",
                "showClearButton:true;JTextField.placeholderText=Tên",
                "showClearButton:true;JTextField.placeholderText=Họ",
                "",
                "showClearButton:true;JTextField.placeholderText=Số điện thoại",
                "",
                "showClearButton:true;JTextField.placeholderText=example@gmail.com",
                "",
                "font:bold +1",
                "",
                "",
                "showClearButton:true;JTextField.placeholderText=Mã Nhân Viên",
                "",
                "showClearButton:true;JTextField.placeholderText=Địa chỉ nơi ở",
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
        jLabel1.setText("Thêm Nhân Viên");
        crazyPanel1.add(jLabel1);

        jLabel2.setText("Thông tin liên lạc");
        crazyPanel1.add(jLabel2);

        jLabel3.setText("Được sử dụng để theo dõi nhân viên");
        crazyPanel1.add(jLabel3);

        jLabel4.setText("Họ Tên");
        crazyPanel1.add(jLabel4);
        crazyPanel1.add(txtTen);
        crazyPanel1.add(txtHo);

        jLabel5.setText("SĐT");
        crazyPanel1.add(jLabel5);
        crazyPanel1.add(txtSDT);

        jLabel6.setText("Email");
        crazyPanel1.add(jLabel6);
        crazyPanel1.add(txtEmail);
        crazyPanel1.add(jSeparator1);

        jLabel7.setText("Thông tin nhân viên");
        crazyPanel1.add(jLabel7);

        jLabel8.setText("Được sử dụng để quản lý nhân viên");
        crazyPanel1.add(jLabel8);

        jLabel9.setText("Mã Nhân Viên");
        crazyPanel1.add(jLabel9);
        crazyPanel1.add(txtMa);

        jLabel10.setText("Địa Chỉ");
        crazyPanel1.add(jLabel10);
        crazyPanel1.add(txtDiaChi);

        jLabel11.setText("Giới Tính");
        crazyPanel1.add(jLabel11);

        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        crazyPanel1.add(cboGioiTinh);

        jLabel16.setText("Ngày Sinh");
        crazyPanel1.add(jLabel16);
        crazyPanel1.add(txtNgaySinh);

        jLabel17.setText("Ngày Vào Làm");
        crazyPanel1.add(jLabel17);
        crazyPanel1.add(txtNgayLam);

        jLabel18.setText("Chức Vụ");
        crazyPanel1.add(jLabel18);

        cboChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        crazyPanel1.add(cboChucVu);

        jLabel19.setText("Trạng thái");
        crazyPanel1.add(jLabel19);

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        crazyPanel1.add(cboTrangThai);

        btnAdd.setText("Save");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        crazyPanel1.add(btnAdd);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 601, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(26, 26, 26)
                    .addComponent(crazyPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                    .addGap(26, 26, 26)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 548, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(crazyPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        addNV();
    }//GEN-LAST:event_btnAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JComboBox<String> cboChucVu;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private javax.swing.JComboBox<String> cboTrangThai;
    private raven.crazypanel.CrazyPanel crazyPanel1;
    private raven.datetime.component.date.DatePicker datePicker1;
    private raven.datetime.component.date.DatePicker datePicker2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHo;
    private javax.swing.JTextField txtMa;
    private javax.swing.JFormattedTextField txtNgayLam;
    private javax.swing.JFormattedTextField txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables

}
