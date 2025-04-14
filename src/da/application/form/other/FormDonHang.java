
package da.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import da.application.Application;
import da.component.MyList;
import da.component.ProductItem;
import da.model.GioHang;
import da.model.SanPham;
import da.service.GioHangService;
import da.service.SanPhamService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
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
public class FormDonHang extends javax.swing.JPanel {
    SanPhamService service = new SanPhamService();
    GioHangService service1 = new GioHangService();
        private String Email;
    
    /**
     * Creates new form FormDonHang
     */
    public FormDonHang(String Email) {
        this.Email = Email;
        initComponents();
        initMS();
        initKT();
        applyListStyle();
        applyTableStyle(tblSanPham);
        applyTableStyle(tblGioHang);
        loadSanPhamData(service.searchSanPham(""));
        refreshGioHangData();
        
    }
    
    private void applyTableStyle(JTable table) {

        cmdDelete.setIcon(new FlatSVGIcon("da/icon/svg/delete.svg", 0.35f));
        cmdExcel.setIcon(new FlatSVGIcon("da/icon/svg/print.svg", 0.35f));
        cmdAdd.setIcon(new FlatSVGIcon("da/icon/svg/add.svg", 0.35f));

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
                        case 0, 4 -> label.setHorizontalAlignment(SwingConstants.CENTER);
                        case 2, 3 -> label.setHorizontalAlignment(SwingConstants.TRAILING);
                        default -> label.setHorizontalAlignment(SwingConstants.LEADING);
                    }
                }
                return com;
            }
        };
    }
    
    
    private void applyListStyle() {
        MyList<ProductItem> list = new MyList<>();
        list.addItem(new ProductItem("Xiaomi Redmi Note 12", 22, "D:\\da1\\da1\\src\\da\\icon\\png\\img6.png"));
        list.addItem(new ProductItem("Samsung Galaxy S20 FE", 6, "D:\\da1\\da1\\src\\da\\icon\\png\\img5.png"));
        list.addItem(new ProductItem("Samsung Galaxy S22+ 5G", 20, "D:\\da1\\da1\\src\\da\\icon\\png\\img4.png"));
        list.addItem(new ProductItem("OPPO Reno6 Pro 5G", 7, "D:\\da1\\da1\\src\\da\\icon\\png\\aomu.jpg"));

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        myList1.setLayout(new BorderLayout()); // Đảm bảo sử dụng BorderLayout
        myList1.add(scrollPane, BorderLayout.CENTER);

        myList1.revalidate();
        myList1.repaint();
    }

        
        public void loadSanPhamData(ArrayList<SanPham> list) {
            DefaultTableModel tblModel = (DefaultTableModel) tblSanPham.getModel();
            tblModel.setRowCount(0);
            for (SanPham sanPham : list) {
                Object[] thongTinSanPham = {
                    sanPham.getMasp(),
                    sanPham.getTensp(),
                    sanPham.getSoluongton(),
                    sanPham.getId(),
                };
                tblModel.addRow(thongTinSanPham);
            }
        }
        
        public void refreshSanPham() {
            ArrayList<SanPham> danhSachSanPham = service.getAll();
            loadSanPhamData(danhSachSanPham);
            Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Đã làm mới danh sách sản phẩm!");
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
            cboMau.setModel(boxModel);
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
        
        public void detail(){
            int selectedRow = tblSanPham.getSelectedRow();
            if (selectedRow == -1) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn sản phẩm!");
                return;
            }
            Object idObj = tblSanPham.getValueAt(selectedRow, 3); // Cột chứa ID sản phẩm
            if (idObj == null) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "ID sản phẩm không hợp lệ!");
                return;
            }
            int id = 0;
            try {
                id = Integer.parseInt(idObj.toString()); // Chuyển đổi giá trị sang kiểu Integer
            } catch (NumberFormatException e) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "ID sản phẩm không hợp lệ!");
                return;
            }
            SanPham sanPham = service.getSanPhamById(id); // Phương thức lấy sản phẩm theo ID từ service hoặc database
            if (sanPham == null) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Sản phẩm không tồn tại!");
                return;
            }
            txtMa.setText(sanPham.getMasp());
            txtTen.setText(sanPham.getTensp());
            txtSoLuongTon.setText(String.valueOf(sanPham.getSoluongton()));
            txtGia.setText(String.valueOf(sanPham.getGia()));
            //cboMau.setSelectedIndex(sanPham.getIdMauSac());
            //cboKichThuoc.setSelectedIndex(sanPham.getIdMauSac());    
        }
        
        
        
        
    public void loadGioHangData(ArrayList<GioHang> list) {
        DefaultTableModel tblModel = (DefaultTableModel) tblGioHang.getModel();
        tblModel.setRowCount(0);
        for (GioHang gioHang : list) {
            Object[] rowData = {
                gioHang.getId(),        
                gioHang.getMaSP(), 
                gioHang.getTenSP(),
                gioHang.getSoLuong(),       
                gioHang.getTongTien(),
                gioHang.getTenMauSac(),
                gioHang.getKichThuoc(),
                gioHang.getNgayThem()    
            };
            tblModel.addRow(rowData);
        }
    }
    
    
    private boolean checkForm() {
        if (txtMa.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Mã Sản Phẩm không được để trống!");
            txtMa.requestFocus();
            return false;
        }
        if (txtTen.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên Sản phẩm không được để trống!");
            txtTen.requestFocus();
            return false;
        }

        // Kiểm tra trống cho txtSoLuongTon
        if (txtSoLuongTon.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Số lượng tồn không được để trống!");
            txtSoLuongTon.requestFocus();
            return false;
        }
        int soLuongTon;
        try {
            soLuongTon = Integer.parseInt(txtSoLuongTon.getText().trim());
            if (soLuongTon < 0) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Số lượng tồn phải là số dương!");
                txtSoLuongTon.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Số lượng tồn phải là số hợp lệ!");
            txtSoLuongTon.requestFocus();
            return false;
        }

        // Kiểm tra trống cho txtGia
        if (txtGia.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Giá không được để trống!");
            txtGia.requestFocus();
            return false;
        }
        try {
            double gia = Double.parseDouble(txtGia.getText().trim());
            if (gia <= 0) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Giá phải lớn hơn 0!");
                txtGia.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Giá phải là số hợp lệ!");
            txtGia.requestFocus();
            return false;
        }

        if (cboMau.getSelectedIndex() == 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn màu sắc!");
            cboMau.requestFocus();
            return false;
        }
        if (cboKichThuoc.getSelectedIndex() == 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn kích thước!");
            cboKichThuoc.requestFocus();
            return false;
        }

        // Kiểm tra trống cho txtSoLuong
        if (txtSoLuong.getText().trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Số lượng không được để trống!");
            txtSoLuong.requestFocus();
            return false;
        }
        try {
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            if (soLuong < 0) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Số lượng phải là số dương!");
                txtSoLuong.requestFocus();
                return false;
            }
            if (soLuong > soLuongTon) { // Kiểm tra số lượng lớn hơn số lượng tồn
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Số lượng không được lớn hơn số lượng tồn!");
                txtSoLuong.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Số lượng phải là số hợp lệ!");
            txtSoLuong.requestFocus();
            return false;
        }

        return true;
    }
    
    private void addToCart() {
        if (!checkForm()) {
            return;
        }
        try {
            String maSP = txtMa.getText().trim();
            String tenSP = txtTen.getText().trim();
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            BigDecimal gia = new BigDecimal(txtGia.getText().trim());
            int idSanPham = service1.getIdByMaSP(maSP); 
            if (idSanPham == -1) {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Sản Phẩm không tồn tại!");
                return;
            }
            BigDecimal tongTien = gia.multiply(BigDecimal.valueOf(soLuong));
            int idNguoiDung = Application.getCurrentUserId();
            if (idNguoiDung == -1) {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Không tìm thấy thông tin người dùng!");
                return;
            }
            String tenMauSac = cboMau.getSelectedItem().toString();
            String kichThuoc = cboKichThuoc.getSelectedItem().toString();
            Timestamp ngayThem = new Timestamp(System.currentTimeMillis());
            // Tạo đối tượng GioHang và thêm vào cơ sở dữ liệu
            GioHang gioHang = new GioHang(0, idNguoiDung, idSanPham, maSP, tenSP,tenMauSac,kichThuoc, tongTien, soLuong, ngayThem);
            boolean success = service1.addGioHang(gioHang);
            if (success) {
                boolean updated = service.updateSoLuongTon(idSanPham, soLuong);
                if (updated) {
                    refreshSanPham();
                } else {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Không thể giảm số lượng tồn vui lòng kiểm tra lại!");
                }
                refreshGioHangData();
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm sản phẩm vào giỏ hàng thành công!");
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Thêm thất bại!");
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Số lượng phải là số hợp lệ!");
            txtSoLuong.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void refreshGioHangData() {
        ArrayList<GioHang> gioHangList = service1.getGioHangByEmail(Email); // Lấy giỏ hàng theo ID người dùng
        loadGioHangData(gioHangList);
    }
    
    private void deleteFromCart() {
        // Lấy hàng được chọn từ bảng tblGioHang
        int selectedRow = tblGioHang.getSelectedRow();
        if (selectedRow == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn mục cần xóa khỏi giỏ hàng!");
            return;
        }

        // Lấy ID của mục giỏ hàng từ cột đầu tiên
        int idGioHang = (int) tblGioHang.getValueAt(selectedRow, 0);

        // Lấy số lượng và ID sản phẩm để khôi phục số lượng tồn trong kho
        int soLuong = (int) tblGioHang.getValueAt(selectedRow, 3);
        String maSP = (String) tblGioHang.getValueAt(selectedRow, 1);
        int idSanPham = service1.getIdByMaSP(maSP);

        // Hiển thị hộp thoại xác nhận
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa mục này khỏi giỏ hàng?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return; // Người dùng chọn "Không"
        }

        // Xóa mục giỏ hàng
        boolean deleted = service1.deleteGioHangById(idGioHang);
        if (deleted) {
            // Khôi phục số lượng tồn kho của sản phẩm
            boolean restored = service.updateSoLuongTon(idSanPham, -soLuong); // Số âm để tăng số lượng tồn
            if (restored) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa sản phẩm khỏi giỏ hàng thành công!");
                refreshGioHangData(); // Làm mới bảng giỏ hàng
                refreshSanPham(); // Làm mới bảng sản phẩm
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Không thể khôi phục số lượng tồn, vui lòng kiểm tra lại!");
            }
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Xóa sản phẩm khỏi giỏ hàng thất bại!");
        }
    }
    
    private void exportGioHangToExcel() {
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = new File(jFileChooser.getSelectedFile().getAbsolutePath() + ".xlsx");

            // Lấy danh sách giỏ hàng từ cơ sở dữ liệu
            ArrayList<GioHang> gioHangList = service1.getGioHangByEmail(Email);

            try (Workbook wb = new XSSFWorkbook(); FileOutputStream out = new FileOutputStream(saveFile)) {
                Sheet sheet = wb.createSheet("Danh Sách Giỏ Hàng");

                // Tiêu đề cột
                String[] headers = {"STT", "ID Giỏ Hàng", "Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng", 
                                    "Tổng Tiền", "Màu Sắc", "Kích Thước", "Ngày Thêm"};
                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    rowCol.createCell(i).setCellValue(headers[i]);
                }

                // Dữ liệu
                int rowIndex = 1;
                for (GioHang gh : gioHangList) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(rowIndex - 1); // STT
                    row.createCell(1).setCellValue(gh.getId());
                    row.createCell(2).setCellValue(gh.getMaSP());
                    row.createCell(3).setCellValue(gh.getTenSP());
                    row.createCell(4).setCellValue(gh.getSoLuong());
                    row.createCell(5).setCellValue(gh.getTongTien().toString());
                    row.createCell(6).setCellValue(gh.getTenMauSac());
                    row.createCell(7).setCellValue(gh.getKichThuoc());
                    row.createCell(8).setCellValue(gh.getNgayThem().toString());
                }

                // Ghi dữ liệu ra file
                wb.write(out);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xuất file thành công!");
            } catch (IOException e) {
                e.printStackTrace();
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Lỗi khi xuất file!");
            }
        }
    }
    
    public void search() {
        String keyword = txtSearch.getText().trim();
        loadSanPhamData(service.searchSanPham(keyword));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        materialTabbed1 = new da.component.MaterialTabbed();
        jPanel1 = new javax.swing.JPanel();
        crazyPanel9 = new raven.crazypanel.CrazyPanel();
        panelTransparent1 = new da.component.PanelTransparent();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        crazyPanel15 = new raven.crazypanel.CrazyPanel();
        txtTen = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        crazyPanel16 = new raven.crazypanel.CrazyPanel();
        txtSearch = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        crazyPanel17 = new raven.crazypanel.CrazyPanel();
        cboMau = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        crazyPanel18 = new raven.crazypanel.CrazyPanel();
        txtSoLuongTon = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        crazyPanel19 = new raven.crazypanel.CrazyPanel();
        txtMa = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        crazyPanel20 = new raven.crazypanel.CrazyPanel();
        txtGia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        crazyPanel21 = new raven.crazypanel.CrazyPanel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        crazyPanel22 = new raven.crazypanel.CrazyPanel();
        cboKichThuoc = new javax.swing.JComboBox<>();
        crazyPanel23 = new raven.crazypanel.CrazyPanel();
        cmdAdd = new javax.swing.JButton();
        cmdDelete = new javax.swing.JButton();
        cmdExcel = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        crazyPanel13 = new raven.crazypanel.CrazyPanel();
        panelTransparent2 = new da.component.PanelTransparent();
        jLabel8 = new javax.swing.JLabel();
        crazyPanel24 = new raven.crazypanel.CrazyPanel();
        cmdUpdate5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        myList1 = new da.component.MyList<>();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1107, 504));

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

        panelTransparent1.setPreferredSize(new java.awt.Dimension(783, 586));

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tên", "Số lượng", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblSanPham);

        panelTransparent1.add(jScrollPane7);
        jScrollPane7.setBounds(0, 50, 350, 210);

        crazyPanel15.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Nhập Tên;background:@background"
            }
        ));
        crazyPanel15.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));

        txtTen.setEnabled(false);
        txtTen.setPreferredSize(new java.awt.Dimension(90, 22));
        txtTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenActionPerformed(evt);
            }
        });
        crazyPanel15.add(txtTen);

        panelTransparent1.add(crazyPanel15);
        crazyPanel15.setBounds(480, 40, 220, 36);

        jLabel1.setText("Mã Sản Phẩm");
        panelTransparent1.add(jLabel1);
        jLabel1.setBounds(370, 20, 100, 16);

        jLabel2.setText("Tên");
        panelTransparent1.add(jLabel2);
        jLabel2.setBounds(490, 20, 40, 16);

        crazyPanel16.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Search;background:@background"
            }
        ));
        crazyPanel16.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));

        txtSearch.setPreferredSize(new java.awt.Dimension(90, 22));
        crazyPanel16.add(txtSearch);

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        crazyPanel16.add(jButton2);

        jButton3.setText("Refesh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        crazyPanel16.add(jButton3);

        panelTransparent1.add(crazyPanel16);
        crazyPanel16.setBounds(0, 10, 370, 37);

        crazyPanel17.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "background:lighten(@background,8%);borderWidth:1"
            }
        ));
        crazyPanel17.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));

        cboMau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMauActionPerformed(evt);
            }
        });
        crazyPanel17.add(cboMau);

        panelTransparent1.add(crazyPanel17);
        crazyPanel17.setBounds(520, 160, 170, 36);

        jLabel3.setText("Size");
        panelTransparent1.add(jLabel3);
        jLabel3.setBounds(370, 80, 60, 16);

        crazyPanel18.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Nhập Số lượng tồn;background:@background"
            }
        ));
        crazyPanel18.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));

        txtSoLuongTon.setEnabled(false);
        txtSoLuongTon.setPreferredSize(new java.awt.Dimension(90, 22));
        crazyPanel18.add(txtSoLuongTon);

        panelTransparent1.add(crazyPanel18);
        crazyPanel18.setBounds(360, 160, 160, 36);

        jLabel4.setText("Giá");
        panelTransparent1.add(jLabel4);
        jLabel4.setBounds(540, 80, 40, 16);

        crazyPanel19.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Nhập Mã;background:@background"
            }
        ));
        crazyPanel19.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));

        txtMa.setEnabled(false);
        txtMa.setPreferredSize(new java.awt.Dimension(90, 22));
        crazyPanel19.add(txtMa);

        panelTransparent1.add(crazyPanel19);
        crazyPanel19.setBounds(360, 40, 120, 36);

        jLabel5.setText("Số lượng");
        panelTransparent1.add(jLabel5);
        jLabel5.setBounds(370, 200, 80, 16);

        crazyPanel20.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Nhập Giá;background:@background"
            }
        ));
        crazyPanel20.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));

        txtGia.setEnabled(false);
        txtGia.setPreferredSize(new java.awt.Dimension(90, 22));
        crazyPanel20.add(txtGia);

        panelTransparent1.add(crazyPanel20);
        crazyPanel20.setBounds(530, 100, 170, 36);

        jLabel6.setText("Số Lượng tồn");
        panelTransparent1.add(jLabel6);
        jLabel6.setBounds(370, 140, 90, 16);

        crazyPanel21.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Nhập Số lượng;background:@background"
            }
        ));
        crazyPanel21.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));

        txtSoLuong.setPreferredSize(new java.awt.Dimension(90, 22));
        crazyPanel21.add(txtSoLuong);

        panelTransparent1.add(crazyPanel21);
        crazyPanel21.setBounds(360, 220, 150, 36);

        jLabel7.setText("Màu Sắc");
        panelTransparent1.add(jLabel7);
        jLabel7.setBounds(530, 140, 70, 16);

        crazyPanel22.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "background:lighten(@background,8%);borderWidth:1"
            }
        ));
        crazyPanel22.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));

        cboKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        crazyPanel22.add(cboKichThuoc);

        panelTransparent1.add(crazyPanel22);
        crazyPanel22.setBounds(360, 100, 170, 36);

        crazyPanel23.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1"
            }
        ));
        crazyPanel23.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));

        cmdAdd.setText("Add new");
        cmdAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAddActionPerformed(evt);
            }
        });
        crazyPanel23.add(cmdAdd);

        cmdDelete.setText("Delete");
        cmdDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDeleteActionPerformed(evt);
            }
        });
        crazyPanel23.add(cmdDelete);

        cmdExcel.setText("Excel");
        cmdExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExcelActionPerformed(evt);
            }
        });
        crazyPanel23.add(cmdExcel);

        panelTransparent1.add(crazyPanel23);
        crazyPanel23.setBounds(0, 270, 500, 37);

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mã Sản Phẩm", "Tên SP", "Số lượng", "Tổng tiền", "Màu Sắc", "Size", "Ngày", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(tblGioHang);

        panelTransparent1.add(jScrollPane8);
        jScrollPane8.setBounds(0, 320, 710, 190);

        crazyPanel9.add(panelTransparent1);

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

        panelTransparent2.setPreferredSize(new java.awt.Dimension(139, 500));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 51, 51));
        jLabel8.setText("Tổng Tiền:");
        panelTransparent2.add(jLabel8);
        jLabel8.setBounds(40, 350, 110, 22);

        crazyPanel24.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Search;background:@background",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1"
            }
        ));
        crazyPanel24.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));

        cmdUpdate5.setText("Tạo Hóa Đơn");
        crazyPanel24.add(cmdUpdate5);

        panelTransparent2.add(crazyPanel24);
        crazyPanel24.setBounds(30, 380, 210, 40);

        myList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(myList1);

        panelTransparent2.add(jScrollPane1);
        jScrollPane1.setBounds(10, 10, 270, 330);

        crazyPanel13.add(panelTransparent2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(crazyPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crazyPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(crazyPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(crazyPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        materialTabbed1.addTab("Bán tại quầy", jPanel1);

        jButton1.setText("jButton1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(373, 373, 373)
                .addComponent(jButton1)
                .addContainerGap(654, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(jButton1)
                .addContainerGap(352, Short.MAX_VALUE))
        );

        materialTabbed1.addTab("Bán online", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(materialTabbed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(materialTabbed1, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMauActionPerformed

    private void cmdExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExcelActionPerformed
        exportGioHangToExcel();
    }//GEN-LAST:event_cmdExcelActionPerformed

    private void txtTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        refreshSanPham();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        detail();
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void cmdAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAddActionPerformed
        addToCart();
    }//GEN-LAST:event_cmdAddActionPerformed

    private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDeleteActionPerformed
        deleteFromCart();
    }//GEN-LAST:event_cmdDeleteActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        search();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboKichThuoc;
    private javax.swing.JComboBox<String> cboMau;
    private javax.swing.JButton cmdAdd;
    private javax.swing.JButton cmdDelete;
    private javax.swing.JButton cmdExcel;
    private javax.swing.JButton cmdUpdate5;
    private raven.crazypanel.CrazyPanel crazyPanel13;
    private raven.crazypanel.CrazyPanel crazyPanel15;
    private raven.crazypanel.CrazyPanel crazyPanel16;
    private raven.crazypanel.CrazyPanel crazyPanel17;
    private raven.crazypanel.CrazyPanel crazyPanel18;
    private raven.crazypanel.CrazyPanel crazyPanel19;
    private raven.crazypanel.CrazyPanel crazyPanel20;
    private raven.crazypanel.CrazyPanel crazyPanel21;
    private raven.crazypanel.CrazyPanel crazyPanel22;
    private raven.crazypanel.CrazyPanel crazyPanel23;
    private raven.crazypanel.CrazyPanel crazyPanel24;
    private raven.crazypanel.CrazyPanel crazyPanel9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private da.component.MaterialTabbed materialTabbed1;
    private da.component.MyList<String> myList1;
    private da.component.PanelTransparent panelTransparent1;
    private da.component.PanelTransparent panelTransparent2;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoLuongTon;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
