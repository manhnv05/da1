
package da.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import da.model.ChatLieu;
import da.model.KichThuoc;
import da.model.MauSac;
import da.model.NhaCC;
import da.model.XuatXu;
import da.service.ChatLieuService;
import da.service.KichThuocService;
import da.service.MauSacService;
import da.service.NhaCCService;
import da.service.XuatXuService;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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


public class FormThuocTinh extends javax.swing.JPanel {
    ChatLieuService service = new ChatLieuService();
    XuatXuService serviceXX = new XuatXuService();
    KichThuocService servicekt = new KichThuocService();
    MauSacService servicems = new MauSacService();
    NhaCCService servicencc = new NhaCCService();


    public FormThuocTinh() {
        initComponents();
        applyTableStyle(tblChatLieu);
        applyTableStyle(tblXuatXu);
        applyTableStyle(tblKichThuoc);
        applyTableStyle(tblMauSac);
        applyTableStyle(tblNhaCungCap);
        loadChatLieuData(service.searchChatLieu(""));
        loadXuatXuData(serviceXX.searchXuatXu(""));
        loadKichThuocData(servicekt.searchKichThuoc(""));
        loadMauSacData(servicems.searchMauSac(""));
        loadNhaCungCapData(servicencc.searchNhaCungCap(""));
    }
    
    private void applyTableStyle(JTable table) {
        cmdSearch.setIcon(new FlatSVGIcon("da/icon/svg/search.svg", 0.35f));
        cmdAdd.setIcon(new FlatSVGIcon("da/icon/svg/add.svg", 0.35f));
        cmdUpdate.setIcon(new FlatSVGIcon("da/icon/svg/edit.svg", 0.35f));
        cmdDelete.setIcon(new FlatSVGIcon("da/icon/svg/delete.svg", 0.35f));
        cmdExcel.setIcon(new FlatSVGIcon("da/icon/svg/print.svg", 0.35f));
        cmdUpdate1.setIcon(new FlatSVGIcon("da/icon/svg/edit.svg", 0.35f));
        cmdAdd1.setIcon(new FlatSVGIcon("da/icon/svg/add.svg", 0.35f));
        cmdSearch1.setIcon(new FlatSVGIcon("da/icon/svg/search.svg", 0.35f));
        cmdDelete1.setIcon(new FlatSVGIcon("da/icon/svg/delete.svg", 0.35f));
        cmdExcel1.setIcon(new FlatSVGIcon("da/icon/svg/print.svg", 0.35f));
        cmdUpdate2.setIcon(new FlatSVGIcon("da/icon/svg/edit.svg", 0.35f));
        cmdAdd2.setIcon(new FlatSVGIcon("da/icon/svg/add.svg", 0.35f));
        cmdSearch2.setIcon(new FlatSVGIcon("da/icon/svg/search.svg", 0.35f));
        cmdDelete2.setIcon(new FlatSVGIcon("da/icon/svg/delete.svg", 0.35f));
        cmdExcel2.setIcon(new FlatSVGIcon("da/icon/svg/print.svg", 0.35f));
        cmdUpdate3.setIcon(new FlatSVGIcon("da/icon/svg/edit.svg", 0.35f));
        cmdAdd3.setIcon(new FlatSVGIcon("da/icon/svg/add.svg", 0.35f));
        cmdSearch3.setIcon(new FlatSVGIcon("da/icon/svg/search.svg", 0.35f));
        cmdDelete3.setIcon(new FlatSVGIcon("da/icon/svg/delete.svg", 0.35f));
        cmdExcel3.setIcon(new FlatSVGIcon("da/icon/svg/print.svg", 0.35f));
        cmdUpdate4.setIcon(new FlatSVGIcon("da/icon/svg/edit.svg", 0.35f));
        cmdAdd4.setIcon(new FlatSVGIcon("da/icon/svg/add.svg", 0.35f));
        cmdSearch4.setIcon(new FlatSVGIcon("da/icon/svg/search.svg", 0.35f));
        cmdDelete4.setIcon(new FlatSVGIcon("da/icon/svg/delete.svg", 0.35f));
        cmdExcel4.setIcon(new FlatSVGIcon("da/icon/svg/print.svg", 0.35f));
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("da/icon/svg/search.svg", 0.35f));
        txtSearch1.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("da/icon/svg/search.svg", 0.35f));
        txtSearch2.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("da/icon/svg/search.svg", 0.35f));
        txtSearch4.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("da/icon/svg/search.svg", 0.35f));
        txtSearch3.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("da/icon/svg/search.svg", 0.35f));
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
                        case 0, 4 -> label.setHorizontalAlignment(SwingConstants.CENTER);
                        case 2, 3 -> label.setHorizontalAlignment(SwingConstants.TRAILING);
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

    
    public void loadChatLieuData(ArrayList<ChatLieu> list) {
        DefaultTableModel tblModel = (DefaultTableModel) tblChatLieu.getModel();
        tblModel.setRowCount(0);
        int index = 1;
        for (ChatLieu chatLieu : list) {
            Object[] thongTinChatLieu = {
                index++,
                chatLieu.getTenChatLieu(),
                chatLieu.getId()
            };
            tblModel.addRow(thongTinChatLieu);
        }
    }
    
    public void loadXuatXuData(ArrayList<XuatXu> list) {
        DefaultTableModel tblModel = (DefaultTableModel) tblXuatXu.getModel();
        tblModel.setRowCount(0);
        int index = 1;
        for (XuatXu xuatXu : list) {
            Object[] thongTinXuatXu = {
                index++,
                xuatXu.getTenXuatXu(),
                xuatXu.getId()
            };
            tblModel.addRow(thongTinXuatXu);
        }
    }
    
    public void loadKichThuocData(ArrayList<KichThuoc> list) {
        DefaultTableModel tblModel = (DefaultTableModel) tblKichThuoc.getModel();
        tblModel.setRowCount(0);
        int index = 1;
        for (KichThuoc kichThuoc : list) {
            Object[] thongTinKichThuoc = {
                index++,
                kichThuoc.getTen(),
                kichThuoc.getId()
            };
            tblModel.addRow(thongTinKichThuoc);
        }
    }
    
    public void loadMauSacData(ArrayList<MauSac> list) {
        DefaultTableModel tblModel = (DefaultTableModel) tblMauSac.getModel();
        tblModel.setRowCount(0);
        int index = 1;
        for (MauSac mauSac : list) {
            Object[] thongTinMauSac = {
                index++,
                mauSac.getTen(),
                mauSac.getId()
            };
            tblModel.addRow(thongTinMauSac);
        }
    }
       
    public void loadNhaCungCapData(ArrayList<NhaCC> list) {
        DefaultTableModel tblModel = (DefaultTableModel) tblNhaCungCap.getModel();
        tblModel.setRowCount(0);
        int index = 1;
        for (NhaCC ncc : list) {
            Object[] thongTinNCC = {
                index++,
                ncc.getTen(),
                ncc.getDiaChi(),
                ncc.getSdt(),
                ncc.getEmail(),
                ncc.getId()
            };
            tblModel.addRow(thongTinNCC);
        }
    }
  

    
    public void searchCL(){
        String keyword = txtSearch.getText().trim();
        loadChatLieuData(service.searchChatLieu(keyword));
    }
    
    public void searchXX(){
        String keyword = txtSearch1.getText().trim();
        loadXuatXuData(serviceXX.searchXuatXu(keyword));
    }
    
    public void searchKT(){
        String keyword = txtSearch4.getText().trim();
        loadKichThuocData(servicekt.searchKichThuoc(keyword));
    }
    
    public void searchMS(){
        String keyword = txtSearch3.getText().trim();
        loadMauSacData(servicems.searchMauSac(keyword));
    }
    
    public void searchNCC(){
        String keyword = txtSearch2.getText().trim();
        loadNhaCungCapData(servicencc.searchNhaCungCap(keyword));
    }
    
    public boolean checkFormCL(){
        if(txtNameCL.getText().trim().isEmpty()){
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Tên chất liệu không được để trống!");
            txtNameCL.requestFocus();
            return false;
        }
        else return true;
    }
    
    public void addCL() {
        if (!checkFormCL()) {
            return;
        }
        String tenChatLieu = txtNameCL.getText().trim();
        boolean isAdded = service.addChatLieu(tenChatLieu);
        if (isAdded) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm thành công!");
            loadChatLieuData(service.getAllChatLieu());
            txtNameCL.setText("");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Thêm thất bại!");
        }
    }
    
    public void deleteSelectedCL() {
        DefaultTableModel model = (DefaultTableModel) tblChatLieu.getModel();
        int[] selectedRows = tblChatLieu.getSelectedRows();
        ArrayList<Integer> idsToDelete = new ArrayList<>();
        if (selectedRows.length == 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn ít nhất một chất liệu để xóa!");
            return;
        }
        for (int row : selectedRows) {
            Object idObj = model.getValueAt(row, 2);
            if (idObj instanceof Integer) {
                idsToDelete.add((Integer) idObj);
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "ID không hợp lệ!");
                return;
            }
        }
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa những chất liệu đã chọn?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        boolean allDeleted = true;
        for (int id : idsToDelete) {
            if (!service.deleteChatLieu(id)) {
                allDeleted = false;
            }
        }
        if (allDeleted) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa thành công!");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Có lỗi xảy ra khi xóa!");
        }
        loadChatLieuData(service.getAllChatLieu()); // Tải lại dữ liệu
    }
    
    public void updateSelectedChatLieu() {
        int selectedRow = tblChatLieu.getSelectedRow();
        if (selectedRow == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn một chất liệu để cập nhật!");
            return;
        }
        int id = (int) tblChatLieu.getValueAt(selectedRow, 2);
        String newTenChatLieu = txtNameCL.getText().trim();
        if (newTenChatLieu.isEmpty()) {
            // Tên chất liệu mới trống
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng nhập tên chất liệu mới!");
            return;
        }
        boolean success = service.updateChatLieu(id, newTenChatLieu);
        if (success) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Cập nhật thành công!");
            loadChatLieuData(service.getAllChatLieu());
            txtNameCL.setText("");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Có lỗi xảy ra khi cập nhật!");
        }
    }
    
    public void openFile(String file){
        try{
            File path = new File(file);
            Desktop.getDesktop().open(path);
        }catch(IOException ioe){
            System.out.println(ioe);
        }
    }
    
    private void exportChatLieuToExcel() {
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = new File(jFileChooser.getSelectedFile().getAbsolutePath() + ".xlsx");
            try (Workbook wb = new XSSFWorkbook(); FileOutputStream out = new FileOutputStream(saveFile)) {
                Sheet sheet = wb.createSheet("Danh Sách Chất Liệu");
                int columnCount = tblChatLieu.getColumnCount();
                int rowCount = tblChatLieu.getRowCount();
                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < columnCount; i++) {
                    rowCol.createCell(i).setCellValue(tblChatLieu.getColumnName(i));
                }
                for (int j = 0; j < rowCount; j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < columnCount; k++) {
                        Object value = tblChatLieu.getValueAt(j, k);
                        if (value != null) {
                            row.createCell(k).setCellValue(value.toString());
                        }
                    }
                }
                wb.write(out);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xuất file thành công!");
            } catch (IOException e) {
                e.printStackTrace();
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Lỗi khi xuất file!");
            }
        }
    }


    public boolean checkFormXX() {
        if (txtNameXX.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Tên nước xuất xứ không được để trống!");
            txtNameXX.requestFocus();
            return false;
        }
        return true;
    }

    public void addXX() {
        if (!checkFormXX()) {
            return;
        }
        String tenXuatXu = txtNameXX.getText().trim();
        boolean isAdded = serviceXX.addXuatXu(tenXuatXu);
        if (isAdded) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm thành công!");
            loadXuatXuData(serviceXX.getAllXuatXu());
            txtNameXX.setText("");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Thêm thất bại!");
        }
    }

    public void deleteSelectedXX() {
        DefaultTableModel model = (DefaultTableModel) tblXuatXu.getModel();
        int[] selectedRows = tblXuatXu.getSelectedRows();
        ArrayList<Integer> idsToDelete = new ArrayList<>();
        if (selectedRows.length == 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn ít nhất một xuất xứ để xóa!");
            return;
        }
        for (int row : selectedRows) {
            Object idObj = model.getValueAt(row, 2);
            if (idObj instanceof Integer) {
                idsToDelete.add((Integer) idObj);
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "ID không hợp lệ!");
                return;
            }
        }
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa những xuất xứ đã chọn?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        boolean allDeleted = true;
        for (int id : idsToDelete) {
            if (!serviceXX.deleteXuatXu(id)) {
                allDeleted = false;
            }
        }
        if (allDeleted) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa thành công!");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Có lỗi xảy ra khi xóa!");
        }
        loadXuatXuData(serviceXX.getAllXuatXu());
    }

    public void updateSelectedXuatXu() {
        int selectedRow = tblXuatXu.getSelectedRow();
        if (selectedRow == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn một xuất xứ để cập nhật!");
            return;
        }
        int id = (int) tblXuatXu.getValueAt(selectedRow, 2);
        String newTenXuatXu = txtNameXX.getText().trim();
        if (newTenXuatXu.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng nhập tên xuất xứ mới!");
            return;
        }
        boolean success = serviceXX.updateXuatXu(id, newTenXuatXu);
        if (success) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Cập nhật thành công!");
            loadXuatXuData(serviceXX.getAllXuatXu());
            txtNameXX.setText("");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Có lỗi xảy ra khi cập nhật!");
        }
    }

    private void exportXuatXuToExcel() {
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = new File(jFileChooser.getSelectedFile().getAbsolutePath() + ".xlsx");
            try (Workbook wb = new XSSFWorkbook(); FileOutputStream out = new FileOutputStream(saveFile)) {
                Sheet sheet = wb.createSheet("Danh Sách Xuất Xứ");
                int columnCount = tblXuatXu.getColumnCount();
                int rowCount = tblXuatXu.getRowCount();
                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < columnCount; i++) {
                    rowCol.createCell(i).setCellValue(tblXuatXu.getColumnName(i));
                }
                for (int j = 0; j < rowCount; j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < columnCount; k++) {
                        Object value = tblXuatXu.getValueAt(j, k);
                        if (value != null) {
                            row.createCell(k).setCellValue(value.toString());
                        }
                    }
                }
                wb.write(out);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xuất file thành công!");
            } catch (IOException e) {
                e.printStackTrace();
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Lỗi khi xuất file!");
            }
        }
    }
    
    public boolean checkFormKT() {
        if (txtNameKT.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Tên kích thước không được để trống!");
            txtNameKT.requestFocus();
            return false;
        }
        return true;
    }

    public void addKT() {
        if (!checkFormKT()) {
            return;
        }
        String tenKichThuoc = txtNameKT.getText().trim();
        boolean isAdded = servicekt.addKichThuoc(tenKichThuoc);

        if (isAdded) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm thành công!");
            loadKichThuocData(servicekt.getAllKichThuoc());
            txtNameKT.setText("");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Thêm thất bại!");
        }
    }

    public void deleteSelectedKT() {
        DefaultTableModel model = (DefaultTableModel) tblKichThuoc.getModel();
        int[] selectedRows = tblKichThuoc.getSelectedRows();
        ArrayList<Integer> idsToDelete = new ArrayList<>();
        if (selectedRows.length == 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn ít nhất một kích thước để xóa!");
            return;
        }
        for (int row : selectedRows) {
            Object idObj = model.getValueAt(row, 2); // Cột thứ 3 chứa ID
            if (idObj instanceof Integer) {
                idsToDelete.add((Integer) idObj);
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "ID không hợp lệ!");
                return;
            }
        }
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa những kích thước đã chọn?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        boolean allDeleted = true;
        for (int id : idsToDelete) {
            if (!servicekt.deleteKichThuoc(id)) {
                allDeleted = false;
            }
        }
        if (allDeleted) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa thành công!");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Có lỗi xảy ra khi xóa!");
        }
        loadKichThuocData(servicekt.getAllKichThuoc());
    }

    public void updateSelectedKichThuoc() {
        int selectedRow = tblKichThuoc.getSelectedRow();
        if (selectedRow == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn một kích thước để cập nhật!");
            return;
        }
        int id = (int) tblKichThuoc.getValueAt(selectedRow, 2);
        String newTenKichThuoc = txtNameKT.getText().trim();
        if (newTenKichThuoc.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng nhập tên kích thước mới!");
            return;
        }
        boolean success = servicekt.updateKichThuoc(id, newTenKichThuoc);
        if (success) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Cập nhật thành công!");
            loadKichThuocData(servicekt.getAllKichThuoc());
            txtNameKT.setText("");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Có lỗi xảy ra khi cập nhật!");
        }
    }

    private void exportKichThuocToExcel() {
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = new File(jFileChooser.getSelectedFile().getAbsolutePath() + ".xlsx");
            try (Workbook wb = new XSSFWorkbook(); FileOutputStream out = new FileOutputStream(saveFile)) {
                Sheet sheet = wb.createSheet("Danh Sách Kích Thước");
                int columnCount = tblKichThuoc.getColumnCount();
                int rowCount = tblKichThuoc.getRowCount();
                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < columnCount; i++) {
                    rowCol.createCell(i).setCellValue(tblKichThuoc.getColumnName(i));
                }
                for (int j = 0; j < rowCount; j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < columnCount; k++) {
                        Object value = tblKichThuoc.getValueAt(j, k);
                        if (value != null) {
                            row.createCell(k).setCellValue(value.toString());
                        }
                    }
                }
                wb.write(out);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xuất file thành công!");
            } catch (IOException e) {
                e.printStackTrace();
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Lỗi khi xuất file!");
            }
        }
    }
    
    
    public boolean checkFormMS() {
        if (txtNameMS.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Tên màu sắc không được để trống!");
            txtNameMS.requestFocus();
            return false;
        }
        return true;
    }

    public void addMS() {
        if (!checkFormMS()) {
            return;
        }
        String tenMau = txtNameMS.getText().trim();
        boolean isAdded = servicems.addMauSac(tenMau);
        if (isAdded) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm thành công!");
            loadMauSacData(servicems.getAllMauSac());
            txtNameMS.setText("");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Thêm thất bại!");
        }
    }

    public void deleteSelectedMS() {
        DefaultTableModel model = (DefaultTableModel) tblMauSac.getModel();
        int[] selectedRows = tblMauSac.getSelectedRows();
        ArrayList<Integer> idsToDelete = new ArrayList<>();
        if (selectedRows.length == 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn ít nhất một màu sắc để xóa!");
            return;
        }
        for (int row : selectedRows) {
            Object idObj = model.getValueAt(row, 2);
            if (idObj instanceof Integer) {
                idsToDelete.add((Integer) idObj);
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "ID không hợp lệ!");
                return;
            }
        }
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa những màu sắc đã chọn?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        boolean allDeleted = true;
        for (int id : idsToDelete) {
            if (!servicems.deleteMauSac(id)) {
                allDeleted = false;
            }
        }
        if (allDeleted) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa thành công!");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Có lỗi xảy ra khi xóa!");
        }
        loadMauSacData(servicems.getAllMauSac());
    }

    public void updateSelectedMauSac() {
        int selectedRow = tblMauSac.getSelectedRow();
        if (selectedRow == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn một màu sắc để cập nhật!");
            return;
        }
        int id = (int) tblMauSac.getValueAt(selectedRow, 2);
        String newTenMau = txtNameMS.getText().trim();
        if (newTenMau.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng nhập tên màu sắc mới!");
            return;
        }
        boolean success = servicems.updateMauSac(id, newTenMau);
        if (success) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Cập nhật thành công!");
            loadMauSacData(servicems.getAllMauSac());
            txtNameMS.setText("");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Có lỗi xảy ra khi cập nhật!");
        }
    }

    private void exportMauSacToExcel() {
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = new File(jFileChooser.getSelectedFile().getAbsolutePath() + ".xlsx");
            try (Workbook wb = new XSSFWorkbook(); FileOutputStream out = new FileOutputStream(saveFile)) {
                Sheet sheet = wb.createSheet("Danh Sách Màu Sắc");
                int columnCount = tblMauSac.getColumnCount();
                int rowCount = tblMauSac.getRowCount();
                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < columnCount; i++) {
                    rowCol.createCell(i).setCellValue(tblMauSac.getColumnName(i));
                }
                for (int j = 0; j < rowCount; j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < columnCount; k++) {
                        Object value = tblMauSac.getValueAt(j, k);
                        if (value != null) {
                            row.createCell(k).setCellValue(value.toString());
                        }
                    }
                }
                wb.write(out);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xuất file thành công!");
            } catch (IOException e) {
                e.printStackTrace();
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Lỗi khi xuất file!");
            }
        }
    }

    
    public boolean checkFormNCC() {
        if (txtNameNCC.getText().trim().isEmpty() || txtAdress.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin!");
            return false;
        }
        if (!txtPhone.getText().matches("^\\d{10,11}$")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Số điện thoại không hợp lệ!");
            txtPhone.requestFocus();
            return false;
        }
        if (!txtEmail.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Email không hợp lệ!");
            txtEmail.requestFocus();
            return false;
        }
        return true;
    }

    public void addNCC() {
        if (!checkFormNCC()) {
            return;
        }
        String tenNCC = txtNameNCC.getText().trim();
        String diaChi = txtAdress.getText().trim();
        String soDienThoai = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        boolean isAdded = servicencc.addNhaCungCap(tenNCC, diaChi, soDienThoai, email);
        if (isAdded) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm thành công!");
            loadNhaCungCapData(servicencc.getAllNhaCungCap());
            txtNameNCC.setText("");
            txtAdress.setText("");
            txtPhone.setText("");
            txtEmail.setText("");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Thêm thất bại!");
        }
    }

    public void deleteSelectedNCC() {
        DefaultTableModel model = (DefaultTableModel) tblNhaCungCap.getModel();
        int[] selectedRows = tblNhaCungCap.getSelectedRows();
        ArrayList<Integer> idsToDelete = new ArrayList<>();
        if (selectedRows.length == 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn ít nhất một nhà cung cấp để xóa!");
            return;
        }
        for (int row : selectedRows) {
            Object idObj = model.getValueAt(row, 0);
            if (idObj instanceof Integer) {
                idsToDelete.add((Integer) idObj);
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "ID không hợp lệ!");
                return;
            }
        }
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa những nhà cung cấp đã chọn?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        boolean allDeleted = true;
        for (int id : idsToDelete) {
            if (!servicencc.deleteNhaCungCap(id)) {
                allDeleted = false;
            }
        }
        if (allDeleted) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa thành công!");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Có lỗi xảy ra khi xóa!");
        }
        loadNhaCungCapData(servicencc.getAllNhaCungCap());
    }

    public void updateSelectedNCC() {
        int selectedRow = tblNhaCungCap.getSelectedRow();
        if (selectedRow == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn một nhà cung cấp để cập nhật!");
            return;
        }
        int id = (int) tblNhaCungCap.getValueAt(selectedRow, 0);
        String newTenNCC = txtNameNCC.getText().trim();
        String newDiaChi = txtAdress.getText().trim();
        String newSoDienThoai = txtPhone.getText().trim();
        String newEmail = txtEmail.getText().trim();
        if (!checkFormNCC()) {
            return;
        }
        boolean success = servicencc.updateNhaCungCap(id, newTenNCC, newDiaChi, newSoDienThoai, newEmail);
        if (success) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Cập nhật thành công!");
            loadNhaCungCapData(servicencc.getAllNhaCungCap());
            txtNameNCC.setText("");
            txtAdress.setText("");
            txtPhone.setText("");
            txtEmail.setText("");
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Có lỗi xảy ra khi cập nhật!");
        }
    }

    private void exportNCCToExcel() {
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = new File(jFileChooser.getSelectedFile().getAbsolutePath() + ".xlsx");
            try (Workbook wb = new XSSFWorkbook(); FileOutputStream out = new FileOutputStream(saveFile)) {
                Sheet sheet = wb.createSheet("Danh Sách Nhà Cung Cấp");
                int columnCount = tblNhaCungCap.getColumnCount();
                int rowCount = tblNhaCungCap.getRowCount();
                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < columnCount; i++) {
                    rowCol.createCell(i).setCellValue(tblNhaCungCap.getColumnName(i));
                }
                for (int j = 0; j < rowCount; j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < columnCount; k++) {
                        Object value = tblNhaCungCap.getValueAt(j, k);
                        if (value != null) {
                            row.createCell(k).setCellValue(value.toString());
                        }
                    }
                }
                wb.write(out);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xuất file thành công!");
            } catch (IOException e) {
                e.printStackTrace();
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Lỗi khi xuất file!");
            }
        }
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        materialTabbed2 = new da.component.MaterialTabbed();
        jPanel5 = new javax.swing.JPanel();
        crazyPanel1 = new raven.crazypanel.CrazyPanel();
        crazyPanel2 = new raven.crazypanel.CrazyPanel();
        txtSearch = new javax.swing.JTextField();
        cmdSearch = new javax.swing.JButton();
        cmdAdd = new javax.swing.JButton();
        cmdUpdate = new javax.swing.JButton();
        cmdDelete = new javax.swing.JButton();
        cmdExcel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChatLieu = new javax.swing.JTable();
        crazyPanel32 = new raven.crazypanel.CrazyPanel();
        panelTransparent6 = new da.component.PanelTransparent();
        jLabel19 = new javax.swing.JLabel();
        crazyPanel34 = new raven.crazypanel.CrazyPanel();
        txtNameCL = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        crazyPanel5 = new raven.crazypanel.CrazyPanel();
        panelTransparent2 = new da.component.PanelTransparent();
        jLabel3 = new javax.swing.JLabel();
        crazyPanel35 = new raven.crazypanel.CrazyPanel();
        txtNameXX = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        crazyPanel6 = new raven.crazypanel.CrazyPanel();
        crazyPanel7 = new raven.crazypanel.CrazyPanel();
        txtSearch1 = new javax.swing.JTextField();
        cmdSearch1 = new javax.swing.JButton();
        cmdAdd1 = new javax.swing.JButton();
        cmdUpdate1 = new javax.swing.JButton();
        cmdDelete1 = new javax.swing.JButton();
        cmdExcel1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblXuatXu = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        crazyPanel11 = new raven.crazypanel.CrazyPanel();
        crazyPanel12 = new raven.crazypanel.CrazyPanel();
        txtSearch4 = new javax.swing.JTextField();
        cmdSearch2 = new javax.swing.JButton();
        cmdAdd2 = new javax.swing.JButton();
        cmdUpdate2 = new javax.swing.JButton();
        cmdDelete2 = new javax.swing.JButton();
        cmdExcel2 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblKichThuoc = new javax.swing.JTable();
        crazyPanel13 = new raven.crazypanel.CrazyPanel();
        panelTransparent3 = new da.component.PanelTransparent();
        jLabel7 = new javax.swing.JLabel();
        crazyPanel36 = new raven.crazypanel.CrazyPanel();
        txtNameKT = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        crazyPanel9 = new raven.crazypanel.CrazyPanel();
        crazyPanel10 = new raven.crazypanel.CrazyPanel();
        txtSearch3 = new javax.swing.JTextField();
        cmdSearch3 = new javax.swing.JButton();
        cmdAdd3 = new javax.swing.JButton();
        cmdUpdate3 = new javax.swing.JButton();
        cmdDelete3 = new javax.swing.JButton();
        cmdExcel3 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblMauSac = new javax.swing.JTable();
        crazyPanel14 = new raven.crazypanel.CrazyPanel();
        panelTransparent4 = new da.component.PanelTransparent();
        jLabel11 = new javax.swing.JLabel();
        crazyPanel37 = new raven.crazypanel.CrazyPanel();
        txtNameMS = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        crazyPanel4 = new raven.crazypanel.CrazyPanel();
        crazyPanel8 = new raven.crazypanel.CrazyPanel();
        txtSearch2 = new javax.swing.JTextField();
        cmdSearch4 = new javax.swing.JButton();
        cmdAdd4 = new javax.swing.JButton();
        cmdUpdate4 = new javax.swing.JButton();
        cmdDelete4 = new javax.swing.JButton();
        cmdExcel4 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblNhaCungCap = new javax.swing.JTable();
        crazyPanel15 = new raven.crazypanel.CrazyPanel();
        panelTransparent5 = new da.component.PanelTransparent();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        crazyPanel28 = new raven.crazypanel.CrazyPanel();
        txtEmail = new javax.swing.JTextField();
        crazyPanel29 = new raven.crazypanel.CrazyPanel();
        txtNameNCC = new javax.swing.JTextField();
        crazyPanel30 = new raven.crazypanel.CrazyPanel();
        txtAdress = new javax.swing.JTextField();
        crazyPanel31 = new raven.crazypanel.CrazyPanel();
        txtPhone = new javax.swing.JTextField();

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

        cmdExcel.setText("Excel");
        cmdExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExcelActionPerformed(evt);
            }
        });
        crazyPanel2.add(cmdExcel);

        crazyPanel1.add(crazyPanel2);

        tblChatLieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblChatLieu);

        crazyPanel1.add(jScrollPane1);

        crazyPanel32.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background;[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20",
            null
        ));
        crazyPanel32.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "wrap,fill,insets 15",
            "[fill]",
            "[grow 0][fill]",
            null
        ));

        panelTransparent6.setPreferredSize(new java.awt.Dimension(235, 445));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Tên Chất Liệu");
        panelTransparent6.add(jLabel19);
        jLabel19.setBounds(10, 60, 120, 20);

        crazyPanel34.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Nhập Tên;background:@background"
            }
        ));
        crazyPanel34.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));
        crazyPanel34.add(txtNameCL);

        panelTransparent6.add(crazyPanel34);
        crazyPanel34.setBounds(0, 80, 210, 40);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Chất Liệu");
        panelTransparent6.add(jLabel1);
        jLabel1.setBounds(100, 0, 90, 25);

        crazyPanel32.add(panelTransparent6);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crazyPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 772, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crazyPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(crazyPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        materialTabbed2.addTab("Chất Liệu", jPanel5);

        crazyPanel5.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background;[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20",
            null
        ));
        crazyPanel5.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "wrap,fill,insets 15",
            "[fill]",
            "[grow 0][fill]",
            null
        ));

        panelTransparent2.setPreferredSize(new java.awt.Dimension(235, 445));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("Xuất Xứ");
        panelTransparent2.add(jLabel3);
        jLabel3.setBounds(90, 10, 120, 20);

        crazyPanel35.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Nhập Tên;background:@background"
            }
        ));
        crazyPanel35.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));
        crazyPanel35.add(txtNameXX);

        panelTransparent2.add(crazyPanel35);
        crazyPanel35.setBounds(10, 60, 210, 40);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("Tên Nước Xuất Xứ");
        panelTransparent2.add(jLabel20);
        jLabel20.setBounds(20, 40, 150, 20);

        crazyPanel5.add(panelTransparent2);

        crazyPanel6.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background;[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20",
            null
        ));
        crazyPanel6.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "wrap,fill,insets 15",
            "[fill]",
            "[grow 0][fill]",
            null
        ));

        crazyPanel7.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
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
        crazyPanel7.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));
        crazyPanel7.add(txtSearch1);

        cmdSearch1.setText("Search");
        cmdSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSearch1ActionPerformed(evt);
            }
        });
        crazyPanel7.add(cmdSearch1);

        cmdAdd1.setText("Add new");
        cmdAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAdd1ActionPerformed(evt);
            }
        });
        crazyPanel7.add(cmdAdd1);

        cmdUpdate1.setText("Update");
        cmdUpdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdUpdate1ActionPerformed(evt);
            }
        });
        crazyPanel7.add(cmdUpdate1);

        cmdDelete1.setText("Delete");
        cmdDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDelete1ActionPerformed(evt);
            }
        });
        crazyPanel7.add(cmdDelete1);

        cmdExcel1.setText("Excel");
        cmdExcel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExcel1ActionPerformed(evt);
            }
        });
        crazyPanel7.add(cmdExcel1);

        crazyPanel6.add(crazyPanel7);

        tblXuatXu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Quốc Gia", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblXuatXu);

        crazyPanel6.add(jScrollPane2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crazyPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crazyPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(crazyPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        materialTabbed2.addTab("Xuất Xứ", jPanel4);

        crazyPanel11.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background;[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20",
            null
        ));
        crazyPanel11.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "wrap,fill,insets 15",
            "[fill]",
            "[grow 0][fill]",
            null
        ));

        crazyPanel12.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Search;background:@background",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1"
            }
        ));
        crazyPanel12.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));
        crazyPanel12.add(txtSearch4);

        cmdSearch2.setText("Search");
        cmdSearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSearch2ActionPerformed(evt);
            }
        });
        crazyPanel12.add(cmdSearch2);

        cmdAdd2.setText("Add new");
        cmdAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAdd2ActionPerformed(evt);
            }
        });
        crazyPanel12.add(cmdAdd2);

        cmdUpdate2.setText("Update");
        cmdUpdate2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdUpdate2ActionPerformed(evt);
            }
        });
        crazyPanel12.add(cmdUpdate2);

        cmdDelete2.setText("Delete");
        cmdDelete2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDelete2ActionPerformed(evt);
            }
        });
        crazyPanel12.add(cmdDelete2);

        cmdExcel2.setText("Excel");
        cmdExcel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExcel2ActionPerformed(evt);
            }
        });
        crazyPanel12.add(cmdExcel2);

        crazyPanel11.add(crazyPanel12);

        tblKichThuoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tblKichThuoc);

        crazyPanel11.add(jScrollPane6);

        crazyPanel13.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background;[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20",
            null
        ));
        crazyPanel13.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "wrap,fill,insets 15",
            "[fill]",
            "[grow 0][fill]",
            null
        ));

        panelTransparent3.setPreferredSize(new java.awt.Dimension(235, 445));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Tên Kích Thước");
        panelTransparent3.add(jLabel7);
        jLabel7.setBounds(20, 40, 120, 20);

        crazyPanel36.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Nhập Tên;background:@background"
            }
        ));
        crazyPanel36.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));
        crazyPanel36.add(txtNameKT);

        panelTransparent3.add(crazyPanel36);
        crazyPanel36.setBounds(10, 60, 210, 40);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("Kích Thước");
        panelTransparent3.add(jLabel2);
        jLabel2.setBounds(100, 0, 110, 25);

        crazyPanel13.add(panelTransparent3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crazyPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crazyPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(crazyPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        materialTabbed2.addTab("Kích Thước", jPanel1);

        crazyPanel9.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background;[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20",
            null
        ));
        crazyPanel9.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "wrap,fill,insets 15",
            "[fill]",
            "[grow 0][fill]",
            null
        ));

        crazyPanel10.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Search;background:@background",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1"
            }
        ));
        crazyPanel10.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));
        crazyPanel10.add(txtSearch3);

        cmdSearch3.setText("Search");
        cmdSearch3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSearch3ActionPerformed(evt);
            }
        });
        crazyPanel10.add(cmdSearch3);

        cmdAdd3.setText("Add new");
        cmdAdd3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAdd3ActionPerformed(evt);
            }
        });
        crazyPanel10.add(cmdAdd3);

        cmdUpdate3.setText("Update");
        cmdUpdate3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdUpdate3ActionPerformed(evt);
            }
        });
        crazyPanel10.add(cmdUpdate3);

        cmdDelete3.setText("Delete");
        cmdDelete3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDelete3ActionPerformed(evt);
            }
        });
        crazyPanel10.add(cmdDelete3);

        cmdExcel3.setText("Excel");
        cmdExcel3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExcel3ActionPerformed(evt);
            }
        });
        crazyPanel10.add(cmdExcel3);

        crazyPanel9.add(crazyPanel10);

        tblMauSac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tblMauSac);

        crazyPanel9.add(jScrollPane5);

        crazyPanel14.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background;[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20",
            null
        ));
        crazyPanel14.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "wrap,fill,insets 15",
            "[fill]",
            "[grow 0][fill]",
            null
        ));

        panelTransparent4.setPreferredSize(new java.awt.Dimension(235, 445));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Tên Màu");
        panelTransparent4.add(jLabel11);
        jLabel11.setBounds(20, 40, 120, 20);

        crazyPanel37.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Nhập Tên;background:@background"
            }
        ));
        crazyPanel37.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));
        crazyPanel37.add(txtNameMS);

        panelTransparent4.add(crazyPanel37);
        crazyPanel37.setBounds(10, 60, 210, 40);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("Màu Sắc");
        panelTransparent4.add(jLabel4);
        jLabel4.setBounds(100, 0, 110, 25);

        crazyPanel14.add(panelTransparent4);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crazyPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crazyPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(crazyPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        materialTabbed2.addTab("Màu Sắc", jPanel2);

        crazyPanel4.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background;[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20",
            null
        ));
        crazyPanel4.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "wrap,fill,insets 15",
            "[fill]",
            "[grow 0][fill]",
            null
        ));

        crazyPanel8.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Search;background:@background",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1"
            }
        ));
        crazyPanel8.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));
        crazyPanel8.add(txtSearch2);

        cmdSearch4.setText("Search");
        cmdSearch4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSearch4ActionPerformed(evt);
            }
        });
        crazyPanel8.add(cmdSearch4);

        cmdAdd4.setText("Add new");
        cmdAdd4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAdd4ActionPerformed(evt);
            }
        });
        crazyPanel8.add(cmdAdd4);

        cmdUpdate4.setText("Update");
        cmdUpdate4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdUpdate4ActionPerformed(evt);
            }
        });
        crazyPanel8.add(cmdUpdate4);

        cmdDelete4.setText("Delete");
        cmdDelete4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDelete4ActionPerformed(evt);
            }
        });
        crazyPanel8.add(cmdDelete4);

        cmdExcel4.setText("Excel");
        cmdExcel4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExcel4ActionPerformed(evt);
            }
        });
        crazyPanel8.add(cmdExcel4);

        crazyPanel4.add(crazyPanel8);

        tblNhaCungCap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên", "Địa Chỉ", "SĐT", "Email", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhaCungCap.setToolTipText("");
        jScrollPane4.setViewportView(tblNhaCungCap);

        crazyPanel4.add(jScrollPane4);

        crazyPanel15.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background;[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20",
            null
        ));
        crazyPanel15.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "wrap,fill,insets 15",
            "[fill]",
            "[grow 0][fill]",
            null
        ));

        panelTransparent5.setPreferredSize(new java.awt.Dimension(235, 445));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Tên Nhà Cung Cấp");
        panelTransparent5.add(jLabel15);
        jLabel15.setBounds(10, 16, 140, 20);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Địa Chỉ");
        panelTransparent5.add(jLabel16);
        jLabel16.setBounds(10, 80, 110, 20);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("SĐT");
        panelTransparent5.add(jLabel17);
        jLabel17.setBounds(10, 140, 70, 20);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Email");
        panelTransparent5.add(jLabel18);
        jLabel18.setBounds(10, 210, 90, 20);

        crazyPanel28.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Nhập Email;background:@background"
            }
        ));
        crazyPanel28.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));
        crazyPanel28.add(txtEmail);

        panelTransparent5.add(crazyPanel28);
        crazyPanel28.setBounds(0, 240, 210, 40);

        crazyPanel29.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Nhập Tên;background:@background"
            }
        ));
        crazyPanel29.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));
        crazyPanel29.add(txtNameNCC);

        panelTransparent5.add(crazyPanel29);
        crazyPanel29.setBounds(0, 40, 210, 40);

        crazyPanel30.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Nhập địa chỉ;background:@background"
            }
        ));
        crazyPanel30.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));
        crazyPanel30.add(txtAdress);

        panelTransparent5.add(crazyPanel30);
        crazyPanel30.setBounds(0, 100, 210, 40);

        crazyPanel31.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Nhậpj SĐT;background:@background"
            }
        ));
        crazyPanel31.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));
        crazyPanel31.add(txtPhone);

        panelTransparent5.add(crazyPanel31);
        crazyPanel31.setBounds(0, 160, 210, 40);

        crazyPanel15.add(panelTransparent5);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crazyPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crazyPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(crazyPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        materialTabbed2.addTab("Nhà cung cấp", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(materialTabbed2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(materialTabbed2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSearchActionPerformed
        searchCL();
    }//GEN-LAST:event_cmdSearchActionPerformed

    private void cmdSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSearch1ActionPerformed
        searchXX();
    }//GEN-LAST:event_cmdSearch1ActionPerformed

    private void cmdSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSearch2ActionPerformed
        searchKT();
    }//GEN-LAST:event_cmdSearch2ActionPerformed

    private void cmdSearch3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSearch3ActionPerformed
        searchMS();
    }//GEN-LAST:event_cmdSearch3ActionPerformed

    private void cmdSearch4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSearch4ActionPerformed
        searchNCC();
    }//GEN-LAST:event_cmdSearch4ActionPerformed

    private void cmdAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAddActionPerformed
        addCL();
    }//GEN-LAST:event_cmdAddActionPerformed

    private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDeleteActionPerformed
        deleteSelectedCL();
    }//GEN-LAST:event_cmdDeleteActionPerformed

    private void cmdUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdUpdateActionPerformed
        updateSelectedChatLieu();
    }//GEN-LAST:event_cmdUpdateActionPerformed

    private void cmdExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExcelActionPerformed
        exportChatLieuToExcel();
    }//GEN-LAST:event_cmdExcelActionPerformed

    private void cmdAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAdd1ActionPerformed
        addXX();
    }//GEN-LAST:event_cmdAdd1ActionPerformed

    private void cmdUpdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdUpdate1ActionPerformed
        updateSelectedXuatXu();
    }//GEN-LAST:event_cmdUpdate1ActionPerformed

    private void cmdDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDelete1ActionPerformed
        deleteSelectedXX();
    }//GEN-LAST:event_cmdDelete1ActionPerformed

    private void cmdExcel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExcel1ActionPerformed
        exportXuatXuToExcel();
    }//GEN-LAST:event_cmdExcel1ActionPerformed

    private void cmdAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAdd2ActionPerformed
        addKT();
    }//GEN-LAST:event_cmdAdd2ActionPerformed

    private void cmdUpdate2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdUpdate2ActionPerformed
        updateSelectedKichThuoc();
    }//GEN-LAST:event_cmdUpdate2ActionPerformed

    private void cmdDelete2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDelete2ActionPerformed
        deleteSelectedKT();
    }//GEN-LAST:event_cmdDelete2ActionPerformed

    private void cmdExcel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExcel2ActionPerformed
        exportKichThuocToExcel();
    }//GEN-LAST:event_cmdExcel2ActionPerformed

    private void cmdAdd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAdd3ActionPerformed
        addMS();
    }//GEN-LAST:event_cmdAdd3ActionPerformed

    private void cmdUpdate3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdUpdate3ActionPerformed
        updateSelectedMauSac();
    }//GEN-LAST:event_cmdUpdate3ActionPerformed

    private void cmdDelete3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDelete3ActionPerformed
        deleteSelectedMS();
    }//GEN-LAST:event_cmdDelete3ActionPerformed

    private void cmdExcel3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExcel3ActionPerformed
        exportMauSacToExcel();
    }//GEN-LAST:event_cmdExcel3ActionPerformed

    private void cmdAdd4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAdd4ActionPerformed
        addNCC();
    }//GEN-LAST:event_cmdAdd4ActionPerformed

    private void cmdUpdate4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdUpdate4ActionPerformed
        updateSelectedNCC();
    }//GEN-LAST:event_cmdUpdate4ActionPerformed

    private void cmdDelete4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDelete4ActionPerformed
        deleteSelectedNCC();
    }//GEN-LAST:event_cmdDelete4ActionPerformed

    private void cmdExcel4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExcel4ActionPerformed
        exportNCCToExcel();
    }//GEN-LAST:event_cmdExcel4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdAdd;
    private javax.swing.JButton cmdAdd1;
    private javax.swing.JButton cmdAdd2;
    private javax.swing.JButton cmdAdd3;
    private javax.swing.JButton cmdAdd4;
    private javax.swing.JButton cmdDelete;
    private javax.swing.JButton cmdDelete1;
    private javax.swing.JButton cmdDelete2;
    private javax.swing.JButton cmdDelete3;
    private javax.swing.JButton cmdDelete4;
    private javax.swing.JButton cmdExcel;
    private javax.swing.JButton cmdExcel1;
    private javax.swing.JButton cmdExcel2;
    private javax.swing.JButton cmdExcel3;
    private javax.swing.JButton cmdExcel4;
    private javax.swing.JButton cmdSearch;
    private javax.swing.JButton cmdSearch1;
    private javax.swing.JButton cmdSearch2;
    private javax.swing.JButton cmdSearch3;
    private javax.swing.JButton cmdSearch4;
    private javax.swing.JButton cmdUpdate;
    private javax.swing.JButton cmdUpdate1;
    private javax.swing.JButton cmdUpdate2;
    private javax.swing.JButton cmdUpdate3;
    private javax.swing.JButton cmdUpdate4;
    private raven.crazypanel.CrazyPanel crazyPanel1;
    private raven.crazypanel.CrazyPanel crazyPanel10;
    private raven.crazypanel.CrazyPanel crazyPanel11;
    private raven.crazypanel.CrazyPanel crazyPanel12;
    private raven.crazypanel.CrazyPanel crazyPanel13;
    private raven.crazypanel.CrazyPanel crazyPanel14;
    private raven.crazypanel.CrazyPanel crazyPanel15;
    private raven.crazypanel.CrazyPanel crazyPanel2;
    private raven.crazypanel.CrazyPanel crazyPanel28;
    private raven.crazypanel.CrazyPanel crazyPanel29;
    private raven.crazypanel.CrazyPanel crazyPanel30;
    private raven.crazypanel.CrazyPanel crazyPanel31;
    private raven.crazypanel.CrazyPanel crazyPanel32;
    private raven.crazypanel.CrazyPanel crazyPanel34;
    private raven.crazypanel.CrazyPanel crazyPanel35;
    private raven.crazypanel.CrazyPanel crazyPanel36;
    private raven.crazypanel.CrazyPanel crazyPanel37;
    private raven.crazypanel.CrazyPanel crazyPanel4;
    private raven.crazypanel.CrazyPanel crazyPanel5;
    private raven.crazypanel.CrazyPanel crazyPanel6;
    private raven.crazypanel.CrazyPanel crazyPanel7;
    private raven.crazypanel.CrazyPanel crazyPanel8;
    private raven.crazypanel.CrazyPanel crazyPanel9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private da.component.MaterialTabbed materialTabbed2;
    private da.component.PanelTransparent panelTransparent2;
    private da.component.PanelTransparent panelTransparent3;
    private da.component.PanelTransparent panelTransparent4;
    private da.component.PanelTransparent panelTransparent5;
    private da.component.PanelTransparent panelTransparent6;
    private javax.swing.JTable tblChatLieu;
    private javax.swing.JTable tblKichThuoc;
    private javax.swing.JTable tblMauSac;
    private javax.swing.JTable tblNhaCungCap;
    private javax.swing.JTable tblXuatXu;
    private javax.swing.JTextField txtAdress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNameCL;
    private javax.swing.JTextField txtNameKT;
    private javax.swing.JTextField txtNameMS;
    private javax.swing.JTextField txtNameNCC;
    private javax.swing.JTextField txtNameXX;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearch1;
    private javax.swing.JTextField txtSearch2;
    private javax.swing.JTextField txtSearch3;
    private javax.swing.JTextField txtSearch4;
    // End of variables declaration//GEN-END:variables
}
