
package da.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import da.application.Application;
import da.model.NguoiDung;
import da.model.NhanVien;
import da.model.VaiTro;
import da.service.NguoiDungService;
import da.service.NhanVienService;
import da.service.VaiTroService;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import raven.toast.Notifications;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class FormNhanVien extends javax.swing.JPanel {
    NhanVienService service = new NhanVienService();

    public FormNhanVien() {
        initComponents();
        applyTableStyle(tblNhanVien);
        loadNhanVienData(service.searchNhanVien(""));
    }


    private void applyTableStyle(JTable table) {
        cmdAdd.setIcon(new FlatSVGIcon("da/icon/svg/add.svg", 0.35f));
        cmdUpdate.setIcon(new FlatSVGIcon("da/icon/svg/edit.svg", 0.35f));
        cmdDelete.setIcon(new FlatSVGIcon("da/icon/svg/delete.svg", 0.35f));
        cmdExcel.setIcon(new FlatSVGIcon("da/icon/svg/print.svg", 0.35f));
        cmdDetails.setIcon(new FlatSVGIcon("da/icon/svg/details.svg", 0.35f));
        cmdSearch.setIcon(new FlatSVGIcon("da/icon/svg/search.svg", 0.35f));
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("da/icon/svg/search.svg", 0.35f));
        //  Change scroll style
        JScrollPane scroll = (JScrollPane) table.getParent().getParent();
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Table.background;"
                + "track:$Table.background;"
                + "trackArc:999");
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
        table.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
        //  To Create table alignment
        table.getTableHeader().setDefaultRenderer(getAlignmentCellRender(table.getTableHeader().getDefaultRenderer(), true));
        table.setDefaultRenderer(Object.class, getAlignmentCellRender(table.getDefaultRenderer(Object.class), false));
    }

    private TableCellRenderer getAlignmentCellRender(TableCellRenderer oldRender, boolean header) {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component com = oldRender.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (com instanceof JLabel label) {
                    switch (column) {
                        case 0, 2, 3 -> label.setHorizontalAlignment(SwingConstants.CENTER);
                        case 1 -> label.setHorizontalAlignment(SwingConstants.TRAILING);
                        default -> label.setHorizontalAlignment(SwingConstants.LEADING);
                    }
                    if (!header) {
                        if (column == 4 && value instanceof Number) {
                            double numericValue = ((Number) value).doubleValue();
                            if (numericValue > 0) {
                                label.setForeground(new Color(17, 182, 60));
                                label.setText("+" + numericValue);
                            } else {
                                label.setForeground(new Color(202, 48, 48));
                            }
                        } else {
                            if (isSelected) {
                                label.setForeground(table.getSelectionForeground());
                            } else {
                                label.setForeground(table.getForeground());
                            }
                        }
                    }
                }
                return com;
            }
        };
    }
    
    public void loadNhanVienData(ArrayList<NhanVien> list) {
        DefaultTableModel tblModel = (DefaultTableModel) tblNhanVien.getModel();
        tblModel.setRowCount(0);
        int index = 1;
        for (NhanVien nhanVien : list) {
            Object[] thongTinNhanVien = {
                index++,
                nhanVien.getId(),
                nhanVien.getMaNV(),
                nhanVien.getHo() + " " + nhanVien.getTen(),
                nhanVien.isGioiTinh()?"Nam":"Nữ",
                nhanVien.getNgaySinh(),
                nhanVien.getSdt(),
                nhanVien.getEmail()
            };
            tblModel.addRow(thongTinNhanVien);
        }
    }
    
    public void searchNV(){
        String keyword = txtSearch.getText().trim();
        loadNhanVienData(service.searchNhanVien(keyword));
    }
    
    public void update() {
        int selectedRow = tblNhanVien.getSelectedRow();
        if (selectedRow == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui Lòng chọn nhân viên!");
            return;
        }
        Object idObj = tblNhanVien.getValueAt(selectedRow, 1);
        if (idObj == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "ID nhân viên không hợp lệ!");
            return;
        }
        int id = 0;
        try {
            id = Integer.parseInt(idObj.toString());
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "ID nhân viên không hợp lệ!");
            return;
        }
        NhanVien nhanVien = service.getNhanVienById(id);
        if (nhanVien == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Nhân viên không tồn tại!");
            return;
        }
        Application.showForm(new UpdateNhanVien(nhanVien));
    }
    
    public void chiTiet() {
        int selectedRow = tblNhanVien.getSelectedRow();
        if (selectedRow == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui Lòng chọn nhân viên!");
            return;
        }
        Object idObj = tblNhanVien.getValueAt(selectedRow, 1);
        if (idObj == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "ID nhân viên không hợp lệ!");
            return;
        }
        int id = 0;
        try {
            id = Integer.parseInt(idObj.toString());
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "ID nhân viên không hợp lệ!");
            return;
        }
        NhanVien nhanVien = service.getNhanVienById(id);
        if (nhanVien == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Nhân viên không tồn tại!");
            return;
        }
        Application.showForm(new ChiTietNhanVien(nhanVien));
    }
    
    public void deleteNhanVien() {
        int selectedRow = tblNhanVien.getSelectedRow();
        if (selectedRow == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui Lòng chọn nhân viên cần xóa!");
            return;
        }
        Object idObj = tblNhanVien.getValueAt(selectedRow, 1);
        if (idObj == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "ID nhân viên không hợp lệ!");
            return;
        }
        int id = 0;
        try {
            id = Integer.parseInt(idObj.toString()); // Chuyển đổi giá trị sang kiểu Integer
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "ID nhân viên không hợp lệ!");
            return;
        }
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên này?", "Xác nhận", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            boolean isDeleted = service.deleteNhanVien(id);
            if (isDeleted) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa nhân viên thành công!");
                loadNhanVienData(service.searchNhanVien("")); // Load lại danh sách nhân viên
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Xóa nhân viên thất bại!");
            }
        }
    }
    
    private void exportNhanVienToExcelFromDB() {
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = new File(jFileChooser.getSelectedFile().getAbsolutePath() + ".xlsx");
            ArrayList<NhanVien> nhanVienList = service.getAllNhanVien();
            try (Workbook wb = new XSSFWorkbook(); FileOutputStream out = new FileOutputStream(saveFile)) {
                Sheet sheet = wb.createSheet("Danh Sách Nhân Viên");
                String[] headers = {"STT", "ID", "ID Vai Trò", "Mã Nhân Viên", "Họ", "Tên", "Email", "Địa Chỉ", 
                                    "Chức Vụ", "Ngày Vào Làm", "Giới Tính", "Ngày Sinh", "SĐT", "Trạng Thái"};
                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    rowCol.createCell(i).setCellValue(headers[i]);
                }
                int rowIndex = 1;
                for (NhanVien nv : nhanVienList) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(rowIndex - 1); // STT
                    row.createCell(1).setCellValue(nv.getId());
                    row.createCell(2).setCellValue(nv.getIdVaiTro());
                    row.createCell(3).setCellValue(nv.getMaNV());
                    row.createCell(4).setCellValue(nv.getHo());
                    row.createCell(5).setCellValue(nv.getTen());
                    row.createCell(6).setCellValue(nv.getEmail());
                    row.createCell(7).setCellValue(nv.getDiaChi());
                    row.createCell(8).setCellValue(nv.getChucVu());
                    row.createCell(9).setCellValue(nv.getNgayVaoLam());
                    row.createCell(10).setCellValue(nv.isGioiTinh() ? "Nam" : "Nữ");
                    row.createCell(11).setCellValue(nv.getNgaySinh());
                    row.createCell(12).setCellValue(nv.getSdt());
                    row.createCell(13).setCellValue(nv.getTrangThai());
                }
                wb.write(out);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xuất file thành công!");
            } catch (IOException e) {
                e.printStackTrace();
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Lỗi khi xuất file!");
            }
        }
    }
    

    private void initializeVaiTroComboBox(JComboBox<VaiTro> cbVaiTro, VaiTroService vaiTroService) {
        List<VaiTro> list = vaiTroService.getAllVaiTro();
        if (list == null || list.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Không có vai trò nào!");
            return;
        }
        cbVaiTro.setModel(new DefaultComboBoxModel<>(list.toArray(new VaiTro[0])));
        cbVaiTro.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof VaiTro vt) {
                    setText(vt.getTenVaiTro());
                }
                return this;
            }
        });
    }

    private JPanel createAddAccountPanel(JTextField txtHo, JTextField txtTen, JTextField txtEmail, JPasswordField txtMatKhau,
                                         JComboBox<VaiTro> cbVaiTro) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Họ:"), gbc);
        gbc.gridx = 1; panel.add(txtHo, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Tên:"), gbc);
        gbc.gridx = 1; panel.add(txtTen, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; panel.add(txtEmail, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1; panel.add(txtMatKhau, gbc);
        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("Vai trò:"), gbc);
        gbc.gridx = 1; panel.add(cbVaiTro, gbc);
        return panel;
    }

    private boolean validateInputs(String ho, String ten, String email, String matKhau, VaiTro vaiTro) {
        if (ho.isBlank() || ten.isBlank() || email.isBlank() || matKhau.isBlank() || vaiTro == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin!");
            return false;
        }
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Email không hợp lệ!");
            return false;
        }
        if (matKhau.length() < 6) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Mật khẩu phải có ít nhất 6 ký tự!");
            return false;
        }
        return true;
    }

    private boolean processAddAccount(String ho, String ten, String email, String matKhau, VaiTro vaiTro, NguoiDungService nguoiDungService) {
        if (!validateInputs(ho, ten, email, matKhau, vaiTro)) {
            return false;
        }
        if (nguoiDungService.isEmailExists(email)) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Email đã tồn tại!");
            return false;
        }
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setHo(ho);
        nguoiDung.setTen(ten);
        nguoiDung.setEmail(email);
        nguoiDung.setMatKhau(matKhau);
        nguoiDung.setIdVaiTro(vaiTro.getId());
        boolean isAdded = nguoiDungService.AddTKNV(nguoiDung);
        if (!isAdded) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Thêm tài khoản thất bại!");
        }
        return isAdded;
    }

    public void themTaiKhoan() {
        NguoiDungService nguoiDungService = new NguoiDungService();
        VaiTroService vaiTroService = new VaiTroService();
        JTextField txtHo = new JTextField();
        JTextField txtTen = new JTextField();
        JTextField txtEmail = new JTextField();
        JPasswordField txtMatKhau = new JPasswordField();
        JComboBox<VaiTro> cbVaiTro = new JComboBox<>();
        initializeVaiTroComboBox(cbVaiTro, vaiTroService);
        JPanel panel = createAddAccountPanel(txtHo, txtTen, txtEmail, txtMatKhau, cbVaiTro);
        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Thêm Tài Khoản Mới", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) {
                return;
            }
            String ho = txtHo.getText().trim();
            String ten = txtTen.getText().trim();
            String email = txtEmail.getText().trim();
            String matKhau = new String(txtMatKhau.getPassword()).trim();
            VaiTro vaiTro = (VaiTro) cbVaiTro.getSelectedItem();
            if (processAddAccount(ho, ten, email, matKhau, vaiTro, nguoiDungService)) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm tài khoản thành công!");
                break;
            }
        }
    }


    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        crazyPanel1 = new raven.crazypanel.CrazyPanel();
        crazyPanel2 = new raven.crazypanel.CrazyPanel();
        txtSearch = new javax.swing.JTextField();
        cmdSearch = new javax.swing.JButton();
        cmdAdd = new javax.swing.JButton();
        cmdUpdate = new javax.swing.JButton();
        cmdDelete = new javax.swing.JButton();
        cmdDetails = new javax.swing.JButton();
        cmdExcel = new javax.swing.JButton();
        cmdAddAcount = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();

        crazyPanel1.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background;[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20",
            null
        ));
        crazyPanel1.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "wrap,fill,insets 15",
            "[fill]",
            "[grow 0][fill]",
            null
        ));

        crazyPanel2.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Search;background:@background",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1"
            }
        ));
        crazyPanel2.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));
        crazyPanel2.add(txtSearch);

        cmdSearch.setText("Search");
        cmdSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSearchActionPerformed(evt);
            }
        });
        crazyPanel2.add(cmdSearch);

        cmdAdd.setText("Add new");
        cmdAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAddActionPerformed(evt);
            }
        });
        crazyPanel2.add(cmdAdd);

        cmdUpdate.setText("Update");
        cmdUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdUpdateActionPerformed(evt);
            }
        });
        crazyPanel2.add(cmdUpdate);

        cmdDelete.setText("Delete");
        cmdDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDeleteActionPerformed(evt);
            }
        });
        crazyPanel2.add(cmdDelete);

        cmdDetails.setText("Chi tiết");
        cmdDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDetailsActionPerformed(evt);
            }
        });
        crazyPanel2.add(cmdDetails);

        cmdExcel.setText("Excel");
        cmdExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExcelActionPerformed(evt);
            }
        });
        crazyPanel2.add(cmdExcel);

        cmdAddAcount.setText("Thêm Tài Khoản Nhân Viên");
        cmdAddAcount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAddAcountActionPerformed(evt);
            }
        });
        crazyPanel2.add(cmdAddAcount);

        crazyPanel1.add(crazyPanel2);

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "ID", "Mã", "Họ Tên", "Giới tính", "Ngày Sinh", "SĐT", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        crazyPanel1.add(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(crazyPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1106, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSearchActionPerformed
        searchNV();
    }//GEN-LAST:event_cmdSearchActionPerformed

    private void cmdAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAddActionPerformed
        Application.showForm(new AddNhanVien());
    }//GEN-LAST:event_cmdAddActionPerformed

    private void cmdUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdUpdateActionPerformed
        update();
    }//GEN-LAST:event_cmdUpdateActionPerformed

    private void cmdDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDetailsActionPerformed
        chiTiet();
    }//GEN-LAST:event_cmdDetailsActionPerformed

    private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDeleteActionPerformed
        deleteNhanVien();
    }//GEN-LAST:event_cmdDeleteActionPerformed

    private void cmdExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExcelActionPerformed
        exportNhanVienToExcelFromDB();
    }//GEN-LAST:event_cmdExcelActionPerformed

    private void cmdAddAcountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAddAcountActionPerformed
        themTaiKhoan();
    }//GEN-LAST:event_cmdAddAcountActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdAdd;
    private javax.swing.JButton cmdAddAcount;
    private javax.swing.JButton cmdDelete;
    private javax.swing.JButton cmdDetails;
    private javax.swing.JButton cmdExcel;
    private javax.swing.JButton cmdSearch;
    private javax.swing.JButton cmdUpdate;
    private raven.crazypanel.CrazyPanel crazyPanel1;
    private raven.crazypanel.CrazyPanel crazyPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
