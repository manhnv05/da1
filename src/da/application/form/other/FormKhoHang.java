/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package da.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import da.component.MyList;
import da.model.KhuVucKho;
import da.model.NhaCC;
import da.model.NhanVien;
import da.model.NhapKho;
import da.model.SanPham;
import da.service.KhuVucKhoService;
import da.service.NhaCCService;
import da.service.NhanVienService;
import da.service.NhapKhoService;
import da.service.SanPhamService;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import raven.toast.Notifications;

/**
 *
 * @author ADMIN
 */
public class FormKhoHang extends javax.swing.JPanel {
    KhuVucKhoService service = new KhuVucKhoService();
    NhapKhoService service2 = new NhapKhoService();
    SanPhamService service1 = new SanPhamService();

    /**
     * Creates new form FormKhoHang
     */
    public FormKhoHang() {
        initComponents();
        applyTableStyle(tblKhuVuc);
        applyTableStyle(tblNhapKho);
        loadKhuVucKhoData(service.searchKhuVucKhoByName(""));
        loadNhapKho(service2.getListNhapKho());
    }
    
    private void applyTableStyle(JTable table) {

        cmdSearch.setIcon(new FlatSVGIcon("da/icon/svg/search.svg", 0.35f));
        cmdAdd.setIcon(new FlatSVGIcon("da/icon/svg/add.svg", 0.35f));
        cmdDelete.setIcon(new FlatSVGIcon("da/icon/svg/delete.svg", 0.35f));
        cmdUpdate.setIcon(new FlatSVGIcon("da/icon/svg/edit.svg", 0.35f));
        cmdDetails1.setIcon(new FlatSVGIcon("da/icon/svg/details.svg", 0.35f));
        cmdUpdate1.setIcon(new FlatSVGIcon("da/icon/svg/edit.svg", 0.35f));
        cmdAdd1.setIcon(new FlatSVGIcon("da/icon/svg/delete.svg", 0.35f));
        cmdExcel1.setIcon(new FlatSVGIcon("da/icon/svg/print.svg", 0.35f));
        cmdNew1.setIcon(new FlatSVGIcon("da/icon/svg/reset.svg", 0.35f));
        lblFilter1.setIcon(new FlatSVGIcon("da/icon/svg/filter.svg", 0.35f));

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
                        case  3 -> label.setHorizontalAlignment(SwingConstants.TRAILING);
                        default -> label.setHorizontalAlignment(SwingConstants.LEADING);
                    }             
                }
                return com;
            }
        };
    }
    
    
    private void applyListStyle(int idKhuVucKho) {
        List<SanPham> khList = service1.getSanPhamByKhuVucKho(idKhuVucKho);
        myList1.removeAll();
        if (khList.isEmpty()) {
            JLabel emptyLabel = new JLabel("Không có sản phẩm nào trong khu vực kho này.");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            myList1.setLayout(new BorderLayout());
            myList1.add(emptyLabel, BorderLayout.CENTER);
        } else {
            MyList<SanPham> list = new MyList<>();
            for (SanPham sp : khList) {
                list.addItem(sp);
            }
            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            myList1.setLayout(new BorderLayout());
            myList1.add(scrollPane, BorderLayout.CENTER);
        }
        myList1.revalidate();
        myList1.repaint();
    }
    
    public void loadKhuVucKhoData(ArrayList<KhuVucKho> list) {
        DefaultTableModel tblModel = (DefaultTableModel) tblKhuVuc.getModel();
        tblModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        for (KhuVucKho kvk : list) {
            Object[] rowData = {
                kvk.getId(),
                kvk.getTenKhuVuc(),       // Tên khu vực
                kvk.getMoTa(),            // Mô tả khu vực
            };
            tblModel.addRow(rowData);
        }
    }
    
    public void search() {
        String keyword = txtSearch.getText().trim();
        loadKhuVucKhoData(service.searchKhuVucKhoByName(keyword));
    }
    


    public void add() {
        KhuVucKho khuVucKho = showInputDialog();
        if (khuVucKho == null) {
            return;
        }

        boolean isAdded = service.addKhuVucKho(khuVucKho);
        if (isAdded) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm khu vực kho thành công!");
            loadKhuVucKhoData(service.getAllKhuVucKho());
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Thêm khu vực kho thất bại!");
        }
    }

    private KhuVucKho showInputDialog() {
        JTextField txtTenKhuVuc = new JTextField();
        JTextArea txtMoTa = new JTextArea(5, 20);
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Tên Khu Vực:"), gbc);
        gbc.gridx = 1;
        panel.add(txtTenKhuVuc, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Mô Tả:"), gbc);
        gbc.gridx = 1;
        panel.add(scrollMoTa, gbc);
        while (true) {
            int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Thêm Khu Vực Kho",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
            if (result != JOptionPane.OK_OPTION) {
                return null;
            }
            String tenKhuVuc = txtTenKhuVuc.getText().trim();
            String moTa = txtMoTa.getText().trim();

            if (tenKhuVuc.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên khu vực kho không được để trống!");
                continue;
            }
            if (tenKhuVuc.length() > 50) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên khu vực không được vượt quá 50 kí tự!");
                continue;
            }
            if (moTa.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Mô tả không được để trống!");
                continue;
            }
            if (moTa.length() > 255) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Mô tả không được vượt quá 255 kí tự!");
                continue;
            }
            return new KhuVucKho(0, tenKhuVuc, moTa);
        }
    }
    
    public void update() {
        // Lấy dòng được chọn trong bảng
        int selectedRow = tblKhuVuc.getSelectedRow();
        if (selectedRow == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn một khu vực để cập nhật!");
            return;
        }

        // Lấy thông tin hiện tại từ bảng
        int id = (int) tblKhuVuc.getValueAt(selectedRow, 0);
        String currentTenKhuVuc = (String) tblKhuVuc.getValueAt(selectedRow, 1);
        String currentMoTa = (String) tblKhuVuc.getValueAt(selectedRow, 2);

        // Tạo các trường nhập liệu cho hộp thoại
        JTextField txtTenKhuVuc = new JTextField(currentTenKhuVuc);
        JTextArea txtMoTa = new JTextArea(currentMoTa, 5, 20);
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);

        // Panel để sắp xếp các trường nhập liệu
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Tên Khu Vực:"), gbc);
        gbc.gridx = 1;
        panel.add(txtTenKhuVuc, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Mô Tả:"), gbc);
        gbc.gridx = 1;
        panel.add(scrollMoTa, gbc);

        // Hiển thị hộp thoại với vòng lặp kiểm tra
        while (true) {
            int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Cập nhật Khu Vực Kho",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
            if (result != JOptionPane.OK_OPTION) {
                return; // Người dùng nhấn "Cancel"
            }

            String tenKhuVuc = txtTenKhuVuc.getText().trim();
            String moTa = txtMoTa.getText().trim();

            // Kiểm tra dữ liệu đầu vào
            if (tenKhuVuc.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên khu vực không được để trống!");
                txtTenKhuVuc.requestFocus();
                continue;
            }
            if (tenKhuVuc.length() > 50) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tên khu vực không được vượt quá 50 ký tự!");
                txtTenKhuVuc.requestFocus();
                continue;
            }
            if (moTa.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Mô tả không được để trống!");
                txtMoTa.requestFocus();
                continue;
            }
            if (moTa.length() > 255) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Mô tả không được vượt quá 255 ký tự!");
                txtMoTa.requestFocus();
                continue;
            }

            // Tạo đối tượng KhuVucKho với dữ liệu đã chỉnh sửa
            KhuVucKho updatedKhuVucKho = new KhuVucKho(id, tenKhuVuc, moTa);

            // Gọi service để cập nhật thông tin khu vực kho
            boolean isUpdated = service.updateKhuVucKho(updatedKhuVucKho);
            if (isUpdated) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Cập nhật khu vực kho thành công!");
                loadKhuVucKhoData(service.getAllKhuVucKho()); // Tải lại danh sách khu vực kho
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Cập nhật khu vực kho thất bại!");
            }
            return; // Thoát vòng lặp sau khi cập nhật thành công
        }
    }
    
    public void delete() {
        // Lấy dòng được chọn trong bảng
        int selectedRow = tblKhuVuc.getSelectedRow();
        if (selectedRow == -1) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng chọn một khu vực để xóa!");
            return;
        }

        // Lấy thông tin khu vực từ bảng
        int id = (int) tblKhuVuc.getValueAt(selectedRow, 0);
        String tenKhuVuc = (String) tblKhuVuc.getValueAt(selectedRow, 1);

        // Hiển thị hộp thoại xác nhận
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc chắn muốn xóa khu vực kho \"" + tenKhuVuc + "\"?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        // Nếu người dùng chọn "No", thoát
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Gọi service để xóa khu vực kho
        boolean isDeleted = service.deleteKhuVucKho(id);
        if (isDeleted) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Xóa khu vực kho thành công!");
            loadKhuVucKhoData(service.getAllKhuVucKho()); // Tải lại danh sách khu vực kho
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Xóa khu vực kho thất bại!");
        }
    }
    
    public void loadNhapKho(List<NhapKho> list) {
        DefaultTableModel tblModel = (DefaultTableModel) tblNhapKho.getModel();
        tblModel.setRowCount(0);
        for (NhapKho nk : list) {
            Object[] rowData = {
                nk.getId(),
                nk.getManhap(),
                nk.getNhacungcap(),
                nk.getNhanvien(),
                nk.getSanPham(),
                nk.getSoLuong(),
                nk.getKhuvuckho(),
                nk.getNgaynhap(),
                nk.getTongtien()
            };
            tblModel.addRow(rowData);
        }
    }
    
    public void Nhap() {
    NhanVienService nhanVienService = new NhanVienService();
    NhaCCService nhaCCRepository = new NhaCCService();
        SanPhamService sanPhamService = new SanPhamService();
    KhuVucKhoService khuVucKhoService = new KhuVucKhoService();

    List<NhanVien> nhanVienList = nhanVienService.getAllNhanVien();
    List<NhaCC> nhaCCList = nhaCCRepository.getAllNhaCungCap();
    List<SanPham> sanPhamList = sanPhamService.getAll();
    List<KhuVucKho> khuVucKhoList = khuVucKhoService.getAllKhuVucKho();

    JTextField txtMaNhap = new JTextField();
    JComboBox<NhaCC> cbNhaCungCap = new JComboBox<>(new DefaultComboBoxModel<>(nhaCCList.toArray(new NhaCC[0])));
    JComboBox<NhanVien> cbNhanVien = new JComboBox<>(new DefaultComboBoxModel<>(nhanVienList.toArray(new NhanVien[0])));
    JTextField txtNgayNhap = new JTextField();
    JTextField txtTongTien = new JTextField();
    JTextField txtSoLuong = new JTextField();
    JComboBox<SanPham> cboSanPham = new JComboBox<>(new DefaultComboBoxModel<>(sanPhamList.toArray(new SanPham[0])));
    JComboBox<KhuVucKho> cboKhuVucKho = new JComboBox<>(new DefaultComboBoxModel<>(khuVucKhoList.toArray(new KhuVucKho[0])));

    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    txtNgayNhap.setText(today.format(formatter));

    cbNhaCungCap.setRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof NhaCC nhaCC) {
                setText(nhaCC.getTen());
            }
            return this;
        }
    });

    cbNhanVien.setRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof NhanVien nhanVien) {
                setText(nhanVien.getHo() + " " + nhanVien.getTen());
            }
            return this;
        }
    });

    cboSanPham.setRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof SanPham sanPham) {
                setText(sanPham.getTensp());
            }
            return this;
        }
    });

    cboKhuVucKho.setRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof KhuVucKho khuVucKho) {
                setText(khuVucKho.getTenKhuVuc());
            }
            return this;
        }
    });

    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(new JLabel("Mã Nhập Kho:"), gbc);
    gbc.gridx = 1;
    panel.add(txtMaNhap, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(new JLabel("Nhà Cung Cấp:"), gbc);
    gbc.gridx = 1;
    panel.add(cbNhaCungCap, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(new JLabel("Nhân Viên Nhập:"), gbc);
    gbc.gridx = 1;
    panel.add(cbNhanVien, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(new JLabel("Ngày Nhập (dd/MM/yyyy):"), gbc);
    gbc.gridx = 1;
    panel.add(txtNgayNhap, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    panel.add(new JLabel("Tổng Tiền:"), gbc);
    gbc.gridx = 1;
    panel.add(txtTongTien, gbc);

    gbc.gridx = 0;
    gbc.gridy = 5;
    panel.add(new JLabel("Sản Phẩm:"), gbc);
    gbc.gridx = 1;
    panel.add(cboSanPham, gbc);

    gbc.gridx = 0;
    gbc.gridy = 6;
    panel.add(new JLabel("Số Lượng:"), gbc);
    gbc.gridx = 1;
    panel.add(txtSoLuong, gbc);

    gbc.gridx = 0;
    gbc.gridy = 7;
    panel.add(new JLabel("Khu Vực Kho:"), gbc);
    gbc.gridx = 1;
    panel.add(cboKhuVucKho, gbc);

    while (true) {
        int result = JOptionPane.showConfirmDialog(
            null,
            panel,
            "Thêm Phiếu Nhập Kho",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String maNhap = txtMaNhap.getText().trim();
        NhaCC nhaCungCap = (NhaCC) cbNhaCungCap.getSelectedItem();
        NhanVien nhanVien = (NhanVien) cbNhanVien.getSelectedItem();
        String ngayNhapStr = txtNgayNhap.getText().trim();
        String tongTienStr = txtTongTien.getText().trim();
        SanPham sanPham = (SanPham) cboSanPham.getSelectedItem();
        String soLuongStr = txtSoLuong.getText().trim();
        KhuVucKho khuVucKho = (KhuVucKho) cboKhuVucKho.getSelectedItem();

        if (maNhap.isEmpty() || nhaCungCap == null || nhanVien == null || ngayNhapStr.isEmpty() ||
            tongTienStr.isEmpty() || sanPham == null || soLuongStr.isEmpty() || khuVucKho == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng điền đầy đủ thông tin!");
            continue;
        }

        Timestamp ngayNhap;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date parsedDate = sdf.parse(ngayNhapStr);
            ngayNhap = new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Ngày nhập không đúng định dạng (dd/MM/yyyy)!");
            txtNgayNhap.requestFocus();
            continue;
        }

        int soLuong;
        try {
            soLuong = Integer.parseInt(soLuongStr);
            if (soLuong <= 0) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Số lượng phải lớn hơn 0!");
                txtSoLuong.requestFocus();
                continue;
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Số lượng phải là số hợp lệ!");
            txtSoLuong.requestFocus();
            continue;
        }

        double tongTien;
        try {
            tongTien = Double.parseDouble(tongTienStr);
            if (tongTien < 0) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tổng tiền không được âm!");
                txtTongTien.requestFocus();
                continue;
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tổng tiền phải là số hợp lệ!");
            txtTongTien.requestFocus();
            continue;
        }

        NhapKho nhapKho = new NhapKho();
        nhapKho.setManhap(maNhap);
        nhapKho.setNhacungcap(nhaCungCap.getTen());
        nhapKho.setNhanvien(nhanVien.getHo() + " " + nhanVien.getTen());
        nhapKho.setSanPham(sanPham.getTensp());
        nhapKho.setSoLuong(soLuong);
        nhapKho.setKhuvuckho(khuVucKho.getTenKhuVuc());
        nhapKho.setNgaynhap(ngayNhap);
        nhapKho.setTongtien(tongTien);

        boolean isAdded = service2.addNhapKho(
    nhapKho,                          // Đối tượng NhapKho
    nhaCungCap.getId(),               // ID nhà cung cấp
    nhanVien.getId(),                 // ID nhân viên nhập
    sanPham.getId(),                  // ID sản phẩm
    khuVucKho.getId()                 // ID khu vực kho
);
        if (isAdded) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm phiếu nhập kho thành công!");
            loadNhapKho(service2.getListNhapKho());
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Thêm phiếu nhập kho thất bại!");
        }
        return;
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

        materialTabbed2 = new da.component.MaterialTabbed();
        jPanel5 = new javax.swing.JPanel();
        crazyPanel1 = new raven.crazypanel.CrazyPanel();
        crazyPanel2 = new raven.crazypanel.CrazyPanel();
        txtSearch = new javax.swing.JTextField();
        cmdSearch = new javax.swing.JButton();
        cmdAdd = new javax.swing.JButton();
        cmdDelete = new javax.swing.JButton();
        cmdUpdate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhuVuc = new javax.swing.JTable();
        crazyPanel3 = new raven.crazypanel.CrazyPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        myList1 = new da.component.MyList<>();
        jPanel4 = new javax.swing.JPanel();
        crazyPanel5 = new raven.crazypanel.CrazyPanel();
        panelTransparent2 = new da.component.PanelTransparent();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        crazyPanel16 = new raven.crazypanel.CrazyPanel();
        jTextField3 = new javax.swing.JTextField();
        crazyPanel17 = new raven.crazypanel.CrazyPanel();
        jComboBox4 = new javax.swing.JComboBox<>();
        crazyPanel18 = new raven.crazypanel.CrazyPanel();
        jComboBox5 = new javax.swing.JComboBox<>();
        crazyPanel19 = new raven.crazypanel.CrazyPanel();
        jTextField4 = new javax.swing.JTextField();
        crazyPanel6 = new raven.crazypanel.CrazyPanel();
        crazyPanel7 = new raven.crazypanel.CrazyPanel();
        txtSearch1 = new javax.swing.JTextField();
        lblFilter1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        cmdAdd1 = new javax.swing.JButton();
        cmdUpdate1 = new javax.swing.JButton();
        cmdDetails1 = new javax.swing.JButton();
        cmdExcel1 = new javax.swing.JButton();
        cmdNew1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhapKho = new javax.swing.JTable();

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

        cmdAdd.setText("Add");
        cmdAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAddActionPerformed(evt);
            }
        });
        crazyPanel2.add(cmdAdd);

        cmdDelete.setText("Delete");
        cmdDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDeleteActionPerformed(evt);
            }
        });
        crazyPanel2.add(cmdDelete);

        cmdUpdate.setText("Update");
        cmdUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdUpdateActionPerformed(evt);
            }
        });
        crazyPanel2.add(cmdUpdate);

        crazyPanel1.add(crazyPanel2);

        tblKhuVuc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên Khu Vực", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhuVuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhuVucMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhuVuc);

        crazyPanel1.add(jScrollPane1);

        crazyPanel3.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background;[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20",
            null
        ));
        crazyPanel3.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "wrap,fill,insets 15",
            "[fill]",
            "[grow 0][fill]",
            null
        ));

        jLabel1.setText("Danh Sách Sản Phẩm đang có trong khu vực");
        crazyPanel3.add(jLabel1);

        myList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(myList1);

        crazyPanel3.add(jScrollPane4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(685, Short.MAX_VALUE)
                .addComponent(crazyPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(crazyPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(521, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(crazyPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        materialTabbed2.addTab("Danh sách ", jPanel5);

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

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Nhà Cung Cấp");
        panelTransparent2.add(jLabel3);
        jLabel3.setBounds(10, 16, 120, 20);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Nhân Viên Nhập");
        panelTransparent2.add(jLabel4);
        jLabel4.setBounds(10, 80, 110, 20);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Từ ngày");
        panelTransparent2.add(jLabel5);
        jLabel5.setBounds(10, 140, 70, 20);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Đến ngày");
        panelTransparent2.add(jLabel6);
        jLabel6.setBounds(10, 210, 90, 20);

        crazyPanel16.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Nhập ngày;background:@background"
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
        crazyPanel16.add(jTextField3);

        panelTransparent2.add(crazyPanel16);
        crazyPanel16.setBounds(0, 240, 210, 40);

        crazyPanel17.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Search;background:@background"
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

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        crazyPanel17.add(jComboBox4);

        panelTransparent2.add(crazyPanel17);
        crazyPanel17.setBounds(0, 40, 210, 40);

        crazyPanel18.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Search;background:@background"
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

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        crazyPanel18.add(jComboBox5);

        panelTransparent2.add(crazyPanel18);
        crazyPanel18.setBounds(0, 100, 210, 40);

        crazyPanel19.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Nhập ngày;background:@background"
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
        crazyPanel19.add(jTextField4);

        panelTransparent2.add(crazyPanel19);
        crazyPanel19.setBounds(0, 160, 210, 40);

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
                "",
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
        crazyPanel7.add(lblFilter1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        crazyPanel7.add(jComboBox1);

        cmdAdd1.setText("Add new");
        crazyPanel7.add(cmdAdd1);

        cmdUpdate1.setText("Update");
        cmdUpdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdUpdate1ActionPerformed(evt);
            }
        });
        crazyPanel7.add(cmdUpdate1);

        cmdDetails1.setText("Chi Tiết");
        crazyPanel7.add(cmdDetails1);

        cmdExcel1.setText("Excel");
        crazyPanel7.add(cmdExcel1);

        cmdNew1.setText("Làm Mới");
        crazyPanel7.add(cmdNew1);

        crazyPanel6.add(crazyPanel7);

        tblNhapKho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Ma Nhap Kho", "Nha Cung cấp", "Nhân viên nhập", "Tên sản phẩm", "Số lượng", "Khu vực kho", "Ngày nhập", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblNhapKho);

        crazyPanel6.add(jScrollPane2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(crazyPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crazyPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crazyPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                    .addComponent(crazyPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE))
                .addContainerGap())
        );

        materialTabbed2.addTab("Nhập Kho", jPanel4);

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

    private void cmdAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAddActionPerformed
        add();
    }//GEN-LAST:event_cmdAddActionPerformed

    private void cmdUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdUpdateActionPerformed
        update();
    }//GEN-LAST:event_cmdUpdateActionPerformed

    private void cmdDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDeleteActionPerformed
        delete();
    }//GEN-LAST:event_cmdDeleteActionPerformed

    private void cmdSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSearchActionPerformed
        search();
    }//GEN-LAST:event_cmdSearchActionPerformed

    private void tblKhuVucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhuVucMouseClicked
        int selectedRow = tblKhuVuc.getSelectedRow();
            if (selectedRow != -1) {
                // Lấy ID khu vực kho từ bảng
                int idKhuVucKho = (int) tblKhuVuc.getValueAt(selectedRow, 0);
                // Gọi phương thức để hiển thị danh sách sản phẩm trong khu vực kho
                applyListStyle(idKhuVucKho);
            }
    }//GEN-LAST:event_tblKhuVucMouseClicked

    private void cmdUpdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdUpdate1ActionPerformed
        Nhap();
    }//GEN-LAST:event_cmdUpdate1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdAdd;
    private javax.swing.JButton cmdAdd1;
    private javax.swing.JButton cmdDelete;
    private javax.swing.JButton cmdDetails1;
    private javax.swing.JButton cmdExcel1;
    private javax.swing.JButton cmdNew1;
    private javax.swing.JButton cmdSearch;
    private javax.swing.JButton cmdUpdate;
    private javax.swing.JButton cmdUpdate1;
    private raven.crazypanel.CrazyPanel crazyPanel1;
    private raven.crazypanel.CrazyPanel crazyPanel16;
    private raven.crazypanel.CrazyPanel crazyPanel17;
    private raven.crazypanel.CrazyPanel crazyPanel18;
    private raven.crazypanel.CrazyPanel crazyPanel19;
    private raven.crazypanel.CrazyPanel crazyPanel2;
    private raven.crazypanel.CrazyPanel crazyPanel3;
    private raven.crazypanel.CrazyPanel crazyPanel5;
    private raven.crazypanel.CrazyPanel crazyPanel6;
    private raven.crazypanel.CrazyPanel crazyPanel7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel lblFilter1;
    private da.component.MaterialTabbed materialTabbed2;
    private da.component.MyList<String> myList1;
    private da.component.PanelTransparent panelTransparent2;
    private javax.swing.JTable tblKhuVuc;
    private javax.swing.JTable tblNhapKho;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearch1;
    // End of variables declaration//GEN-END:variables
}
