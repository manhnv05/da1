/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package da.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import da.application.Application;
import da.model.SanPham;
import da.service.SanPhamService;
import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import raven.toast.Notifications;

/**
 *
 * @author ADMIN
 */
public class FormQLSanPham extends javax.swing.JPanel {
    SanPhamService service = new SanPhamService();
    /**
     * Creates new form FormSanPham
     */
    public FormQLSanPham() {
        initComponents();
        applyTableStyle(tblSanPham);
        loadSanPhamData(service.searchSanPham(""));
        loadSanPhamData(service.getAll());
    }
    
    private void applyTableStyle(JTable table) {
        cmdDelete.setIcon(new FlatSVGIcon("da/icon/svg/details.svg", 0.35f));
        cmdSearch.setIcon(new FlatSVGIcon("da/icon/svg/search.svg", 0.35f));
        cmdAdd.setIcon(new FlatSVGIcon("da/icon/svg/add.svg", 0.35f));
        cmdUpdate.setIcon(new FlatSVGIcon("da/icon/svg/edit.svg", 0.35f));
        cmdDelete.setIcon(new FlatSVGIcon("da/icon/svg/delete.svg", 0.35f));
        cmdExcel.setIcon(new FlatSVGIcon("da/icon/svg/print.svg", 0.35f));
        cmdDetails.setIcon(new FlatSVGIcon("da/icon/svg/details.svg", 0.35f));
        cmdNew.setIcon(new FlatSVGIcon("da/icon/svg/reset.svg", 0.35f));

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
                // Căn chỉnh cột
                    switch (column) {
                        case 0, 2, 3, 4 -> label.setHorizontalAlignment(SwingConstants.CENTER);
                        case 1 -> label.setHorizontalAlignment(SwingConstants.TRAILING);
                        default -> label.setHorizontalAlignment(SwingConstants.LEADING);
                    }

                }
                return com;
            }
        };
    }
    
    public void loadSanPhamData(ArrayList<SanPham> list) {
        DefaultTableModel tblModel = (DefaultTableModel) tblSanPham.getModel();
        tblModel.setRowCount(0);
        int index = 1; 
        for (SanPham sanPham : list) {
            Object[] thongTinSanPham = {
                index++,
                sanPham.getId(),
                sanPham.getMasp(),
                sanPham.getTensp(),
                sanPham.getSoluongton(),
                sanPham.getGia(),
                sanPham.getTrangThai(),
                sanPham.getTenKhuVucKho()
            };
            tblModel.addRow(thongTinSanPham);
        }
    }
    
    public void search() {
        String keyword = txtSearch.getText().trim();
        loadSanPhamData(service.searchSanPham(keyword));
    }
    
    public void update() {
        int selectedRow = tblSanPham.getSelectedRow();
        if (selectedRow == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn sản phẩm!");
            return;
        }
        Object idObj = tblSanPham.getValueAt(selectedRow, 1);
        if (idObj == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "ID sản phẩm không hợp lệ!");
            return;
        }
        int id = 0;
        try {
            id = Integer.parseInt(idObj.toString());
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "ID sản phẩm không hợp lệ!");
            return;
        }
        SanPham sanPham = service.getSanPhamById(id);
        if (sanPham == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Sản phẩm không tồn tại!");
            return;
        }
        Application.showForm(new UpdateSanPham(sanPham));
    }
     
     
    public void details() {
        int selectedRow = tblSanPham.getSelectedRow();
        if (selectedRow == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn sản phẩm!");
            return;
        }
        Object idObj = tblSanPham.getValueAt(selectedRow, 1);
        if (idObj == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "ID sản phẩm không hợp lệ!");
            return;
        }
        int id = 0;
        try {
            id = Integer.parseInt(idObj.toString());  // Chuyển đổi giá trị sang kiểu Integer
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "ID sản phẩm không hợp lệ!");
            return;
        }
        SanPham sanPham = service.getSanPhamById(id);  // Phương thức lấy sản phẩm theo ID từ service hoặc từ database
        if (sanPham == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Sản phẩm không tồn tại!");
            return;
        }
            Application.showForm(new ChiTietSanPham(sanPham));
    }
      
    public void deleteSanPham() {
        int selectedRow = tblSanPham.getSelectedRow();
        if (selectedRow == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn sản phẩm cần xóa!");
            return;
        }
        Object idObj = tblSanPham.getValueAt(selectedRow, 1);
        if (idObj == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "ID sản phẩm không hợp lệ!");
            return;
        }
        int id = 0;
        try {
            id = Integer.parseInt(idObj.toString());
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "ID sản phẩm không hợp lệ!");
            return;
        }
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            boolean isDeleted = service.deleteSanPham(id);
            if (isDeleted) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa sản phẩm thành công!");
                loadSanPhamData(service.getAll());
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Xóa sản phẩm thất bại!");
            }
        }
    }
      
    public void refreshSanPham() {
        ArrayList<SanPham> danhSachSanPham = service.getAll();
        loadSanPhamData(danhSachSanPham);
        Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Đã làm mới danh sách sản phẩm!");
    }
      
    private void exportSanPhamToExcelFromDB() {
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = new File(jFileChooser.getSelectedFile().getAbsolutePath() + ".xlsx");
            ArrayList<SanPham> sanPhamList = service.getAll();
            try (Workbook wb = new XSSFWorkbook();
                    FileOutputStream out = new FileOutputStream(saveFile)) {
                Sheet sheet = wb.createSheet("Danh Sách Sản Phẩm");
                String[] headers = {"STT", "ID", "Mã SP", "Tên SP", "Mô Tả", "Giá", "SL Tồn", 
                                    "Chất Liệu", "Xuất Xứ", "Kích Thước", "Màu Sắc", "Nhà Cung Cấp", "Khu vực kho", "Hình Ảnh"};
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    headerRow.createCell(i).setCellValue(headers[i]);
                }
                int rowIndex = 1;
                for (SanPham sp : sanPhamList) {
                    Row row = sheet.createRow(rowIndex);
                    row.createCell(0).setCellValue(rowIndex); // STT
                    row.createCell(1).setCellValue(sp.getId());
                    row.createCell(2).setCellValue(sp.getMasp());
                    row.createCell(3).setCellValue(sp.getTensp());
                    row.createCell(4).setCellValue(sp.getMota());
                    row.createCell(5).setCellValue(sp.getGia().doubleValue());
                    row.createCell(6).setCellValue(sp.getSoluongton());
                    row.createCell(7).setCellValue(sp.getTenChatLieu());
                    row.createCell(8).setCellValue(sp.getTenXuatXu());
                    row.createCell(9).setCellValue(sp.getTenKichThuoc());
                    row.createCell(10).setCellValue(sp.getTenMauSac());
                    row.createCell(11).setCellValue(sp.getTenNhaCungCap());
                    row.createCell(12).setCellValue(sp.getTenKhuVucKho());
                    row.createCell(13).setCellValue(sp.getHinhanh());
                    rowIndex++;
                }
                wb.write(out);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xuất file sản phẩm thành công!");
            } catch (IOException e) {
                e.printStackTrace();
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Lỗi khi xuất file sản phẩm!");
            }
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

        jButton1 = new javax.swing.JButton();
        crazyPanel1 = new raven.crazypanel.CrazyPanel();
        crazyPanel2 = new raven.crazypanel.CrazyPanel();
        txtSearch = new javax.swing.JTextField();
        cmdSearch = new javax.swing.JButton();
        cmdAdd = new javax.swing.JButton();
        cmdUpdate = new javax.swing.JButton();
        cmdDelete = new javax.swing.JButton();
        cmdDetails = new javax.swing.JButton();
        cmdExcel = new javax.swing.JButton();
        cmdNew = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();

        jButton1.setText("jButton1");

        setPreferredSize(new java.awt.Dimension(1132, 509));

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
                "",
                "background:lighten(@background,8%);borderWidth:1",
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

        cmdDetails.setText("Chi Tiết");
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

        cmdNew.setText("Làm mới");
        cmdNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNewActionPerformed(evt);
            }
        });
        crazyPanel2.add(cmdNew);

        jButton2.setText("Lưu Trữ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        crazyPanel2.add(jButton2);

        crazyPanel1.add(crazyPanel2);

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "ID", "Mã", "Tên Sản Phẩm", "Số Lượng", "Giá", "Trạng thái", "Khu Vực kho"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        crazyPanel1.add(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1120, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSearchActionPerformed
        search();
    }//GEN-LAST:event_cmdSearchActionPerformed

    private void cmdAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAddActionPerformed
        Application.showForm(new AddSanPham());
    }//GEN-LAST:event_cmdAddActionPerformed

    private void cmdUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdUpdateActionPerformed
        update();
    }//GEN-LAST:event_cmdUpdateActionPerformed

    private void cmdDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDetailsActionPerformed
        details();
    }//GEN-LAST:event_cmdDetailsActionPerformed

    private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDeleteActionPerformed
        deleteSanPham();
    }//GEN-LAST:event_cmdDeleteActionPerformed

    private void cmdNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdNewActionPerformed
        refreshSanPham();
    }//GEN-LAST:event_cmdNewActionPerformed

    private void cmdExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExcelActionPerformed
        exportSanPhamToExcelFromDB();
    }//GEN-LAST:event_cmdExcelActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Application.showForm(new FormLuuTru());

    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdAdd;
    private javax.swing.JButton cmdDelete;
    private javax.swing.JButton cmdDetails;
    private javax.swing.JButton cmdExcel;
    private javax.swing.JButton cmdNew;
    private javax.swing.JButton cmdSearch;
    private javax.swing.JButton cmdUpdate;
    private raven.crazypanel.CrazyPanel crazyPanel1;
    private raven.crazypanel.CrazyPanel crazyPanel2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
