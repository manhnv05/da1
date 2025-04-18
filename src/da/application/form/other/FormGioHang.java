/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package da.application.form.other;

import da.component.MyList1;
import da.model.GioHang;
import da.service.GioHangService;
import da.service.SanPhamService;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author ADMIN
 */
public class FormGioHang extends javax.swing.JPanel {
        SanPhamService service = new SanPhamService();

    GioHangService service1 = new GioHangService();
    private String Email;
    /**
     * Creates new form FormGioHang
     */
    public FormGioHang(String Email) {
        this.Email = Email;
        initComponents();
        applyListStyle();
    }
    
    
    private void applyListStyle() {
    // Lấy danh sách giỏ hàng theo email
    List<GioHang> khList = service1.getGioHangByEmail(Email);
    myListGioHang1.removeAll(); // Xóa các phần tử cũ trong giao diện

    if (khList == null || khList.isEmpty()) {
        // Hiển thị thông báo nếu giỏ hàng trống
        JLabel emptyLabel = new JLabel("Giỏ hàng của bạn hiện đang trống.");
        emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        myListGioHang1.setLayout(new BorderLayout());
        myListGioHang1.add(emptyLabel, BorderLayout.CENTER);
    } else {
        // Tạo danh sách giỏ hàng
        MyList1<GioHang> list = new MyList1<>();
        for (GioHang kh : khList) {
            list.addItem(kh); // Thêm từng sản phẩm vào danh sách
        }

        // Xử lý sự kiện nhấn chuột vào danh sách
        list.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int index = list.locationToIndex(evt.getPoint()); // Lấy vị trí nhấn chuột
                if (index >= 0) {
                    GioHang selectedItem = list.getModel().getElementAt(index);
                    // Thực hiện hành động khi nhấn vào một mục (ví dụ: hiển thị chi tiết sản phẩm)
                    System.out.println("Sản phẩm được chọn: " + selectedItem.getTenSP());
                    // Hiển thị thông tin chi tiết
                    showProductDetails(selectedItem);
                }
            }
        });

        // Đặt danh sách trong JScrollPane
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        myListGioHang1.setLayout(new BorderLayout());
        myListGioHang1.add(scrollPane, BorderLayout.CENTER);
    }

    // Làm mới giao diện
    myListGioHang1.revalidate();
    myListGioHang1.repaint();
}
    
    private void showProductDetails(GioHang gioHang) {
    // Tạo các trường nhập liệu cho hộp thoại
    JTextField txtTenSP = new JTextField(gioHang.getTenSP());
    txtTenSP.setEnabled(false); // Không cho phép chỉnh sửa tên sản phẩm
    JTextField txtSoLuong = new JTextField(String.valueOf(gioHang.getSoLuong()));
    JTextField txtTongTien = new JTextField(gioHang.getTongTien().toString());
    txtTongTien.setEnabled(false); // Không cho phép chỉnh sửa tổng tiền

    // Tạo các ComboBox
    JComboBox<String> cboKichThuoc = new JComboBox<>();
        DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
        boxModel.addElement("-- Chọn kích thước --");
        HashSet<String> kichThuocSet = service.getAllKichThuoc();
        for (String kichThuoc : kichThuocSet) {
            if (kichThuoc != null) {
                boxModel.addElement(kichThuoc);
            }
        }
        cboKichThuoc.setModel(boxModel);
    
    
    JComboBox<String> cboMauSac = new JComboBox<>();
    
    DefaultComboBoxModel<String> boxModel2 = new DefaultComboBoxModel<>();
        boxModel2.addElement("-- Chọn màu sắc --");
        HashSet<String> mauSacSet = service.getAllMauSac();
        for (String mauSac : mauSacSet) {
            if (mauSac != null) {
                boxModel2.addElement(mauSac);
            }
        }
        cboMauSac.setModel(boxModel2);

    // Đặt giá trị hiện tại cho các ComboBox
    cboKichThuoc.setSelectedItem(gioHang.getKichThuoc());
    cboMauSac.setSelectedItem(gioHang.getTenMauSac());

    // Panel để sắp xếp các trường nhập liệu
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(new JLabel("Tên Sản Phẩm:"), gbc);
    gbc.gridx = 1;
    panel.add(txtTenSP, gbc);

    

    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(new JLabel("Kích Thước:"), gbc);
    gbc.gridx = 1;
    panel.add(cboKichThuoc, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(new JLabel("Màu Sắc:"), gbc);
    gbc.gridx = 1;
    panel.add(cboMauSac, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    panel.add(new JLabel("Số Lượng:"), gbc);
    gbc.gridx = 1;
    panel.add(txtSoLuong, gbc);

    gbc.gridx = 0;
    gbc.gridy = 5;
    panel.add(new JLabel("Tổng Tiền:"), gbc);
    gbc.gridx = 1;
    panel.add(txtTongTien, gbc);

    // Hiển thị hộp thoại với các nút Update và Delete
    Object[] options = {"Update", "Delete", "Cancel"};
    while (true) {
        int result = JOptionPane.showOptionDialog(
            null,
            panel,
            "Chi Tiết Sản Phẩm",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );

        if (result == 2 || result == JOptionPane.CLOSED_OPTION) {
            // Người dùng nhấn Cancel hoặc đóng hộp thoại
            return;
        } else if (result == 1) {
            // Người dùng nhấn Delete
            int confirm = JOptionPane.showConfirmDialog(
                null,
                "Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                boolean isDeleted = new GioHangService().deleteGioHangById(gioHang.getId());
                if (isDeleted) {
                    JOptionPane.showMessageDialog(null, "Xóa sản phẩm thành công!", "Thành Công", JOptionPane.INFORMATION_MESSAGE);
                    refreshGioHangData(); // Làm mới danh sách giỏ hàng
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (result == 0) {
            // Người dùng nhấn Update
            String soLuongStr = txtSoLuong.getText().trim();
            int soLuong;
            try {
                soLuong = Integer.parseInt(soLuongStr);
                if (soLuong <= 0) {
                    JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0!", "Cảnh Báo", JOptionPane.WARNING_MESSAGE);
                    continue;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Số lượng phải là số hợp lệ!", "Cảnh Báo", JOptionPane.WARNING_MESSAGE);
                continue;
            }

            // Lấy giá trị từ ComboBox
            String kichThuoc = (String) cboKichThuoc.getSelectedItem();
            String mauSac = (String) cboMauSac.getSelectedItem();

            // Cập nhật sản phẩm
            boolean isUpdated = new GioHangService().updateGioHang(
                gioHang.getId(), soLuong, kichThuoc, mauSac
            );
            if (isUpdated) {
                JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thành công!", "Thành Công", JOptionPane.INFORMATION_MESSAGE);
                refreshGioHangData(); // Làm mới danh sách giỏ hàng
                return;
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
    
    public void refreshGioHangData() {
        applyListStyle();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        materialTabbed1 = new da.component.MaterialTabbed();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        myListGioHang1 = new da.component.MyListGioHang<>();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        myListGioHang1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(myListGioHang1);

        jButton1.setText("Thanh Toán");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Tổng tiền");

        jButton3.setText("refesh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 506, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(110, 110, 110)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1)
                    .addComponent(jButton3))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        materialTabbed1.addTab("Giỏ hàng", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 854, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 611, Short.MAX_VALUE)
        );

        materialTabbed1.addTab("Lịch Sử mua hàng", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(materialTabbed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(materialTabbed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        refreshGioHangData();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private da.component.MaterialTabbed materialTabbed1;
    private da.component.MyListGioHang<String> myListGioHang1;
    // End of variables declaration//GEN-END:variables
}
