
package da.application.form.other;

import da.application.Application;
import da.component.MyList1;
import da.component.ThongTinDonHang;
import da.model.GioHang;
import da.model.HoaDonOnlineChiTiet;
import da.service.GioHangService;
import da.service.HoaDonOnlineService;
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


public class FormGioHang extends javax.swing.JPanel {
    SanPhamService service = new SanPhamService();
    HoaDonOnlineService service2 = new HoaDonOnlineService();
    GioHangService service1 = new GioHangService();
    private String Email;

    
    public FormGioHang(String Email) {
        this.Email = Email;
        initComponents();
        applyListStyle();
        applyListStyle2();
        applyListStyle3();
        capNhatTongTien();
    }
    
    
    private void applyListStyle() {
        List<GioHang> khList = service1.getGioHangByEmail(Email);
        myListGioHang1.removeAll(); 
        if (khList == null || khList.isEmpty()) {
            JLabel emptyLabel = new JLabel("Giỏ hàng của bạn hiện đang trống.");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            myListGioHang1.setLayout(new BorderLayout());
            myListGioHang1.add(emptyLabel, BorderLayout.CENTER);
        } else {
            MyList1<GioHang> list = new MyList1<>();
            for (GioHang kh : khList) {
                list.addItem(kh);
            }
            list.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int index = list.locationToIndex(evt.getPoint());
                    if (index >= 0) {
                        GioHang selectedItem = list.getModel().getElementAt(index);
                        System.out.println("Sản phẩm được chọn: " + selectedItem.getTenSP());
                        showProductDetails(selectedItem);
                    }
                }
            });
            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            myListGioHang1.setLayout(new BorderLayout());
            myListGioHang1.add(scrollPane, BorderLayout.CENTER);
        }
        myListGioHang1.revalidate();
        myListGioHang1.repaint();
    }
    
    private void applyListStyle2() {
        List<HoaDonOnlineChiTiet> khList = service2.getDonHangByEmail(Email);
        thongTinDonHang1.removeAll();
        if (khList == null || khList.isEmpty()) {
            JLabel emptyLabel = new JLabel("Giỏ hàng của bạn hiện đang trống.");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            thongTinDonHang1.setLayout(new BorderLayout());
            thongTinDonHang1.add(emptyLabel, BorderLayout.CENTER);
        } else {
            ThongTinDonHang<HoaDonOnlineChiTiet> list = new ThongTinDonHang<>();
            for (HoaDonOnlineChiTiet kh : khList) {
                list.addItem(kh);
            }
            list.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int index = list.locationToIndex(evt.getPoint());
                    if (index >= 0) {
                        HoaDonOnlineChiTiet selectedItem = list.getModel().getElementAt(index);

                        if (selectedItem.getTrangThai() == 0) {
                            System.out.println("Hủy đơn hàng: " + selectedItem.getTenSP());
                            huyDon(selectedItem);
                        } else if (selectedItem.getTrangThai() == 4 || selectedItem.getTrangThai() == 5) {
                            System.out.println("Đặt lại đơn hàng: " + selectedItem.getTenSP());
                            datLai(selectedItem);
                        } else {
                            System.out.println("Sản phẩm được chọn: " + selectedItem.getTenSP());
                            showProductDetails(selectedItem);
                        }
                    }
                }
            });
            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            thongTinDonHang1.setLayout(new BorderLayout());
            thongTinDonHang1.add(scrollPane, BorderLayout.CENTER);
        }
        thongTinDonHang1.revalidate();
        thongTinDonHang1.repaint();
    }

    private void huyDon(HoaDonOnlineChiTiet hoaDon) {
        boolean success = service2.huyDon(hoaDon.getHoadononlineID());
        if (success) {
            System.out.println("Hủy đơn hàng thành công: " + hoaDon.getTenSP());
            hoaDon.setTrangThai(5);
            applyListStyle2();
        } else {
            System.out.println("Hủy đơn hàng thất bại: " + hoaDon.getTenSP());
        }
    }

    private void datLai(HoaDonOnlineChiTiet hoaDon) {
        boolean success = service2.datLai(hoaDon.getHoadononlineID());
        if (success) {
            System.out.println("Đặt lại đơn hàng thành công: " + hoaDon.getTenSP());
            hoaDon.setTrangThai(0); 
            applyListStyle2();
        } else {
            System.out.println("Đặt lại đơn hàng thất bại: " + hoaDon.getTenSP());
        }
    }

    private void showProductDetails(HoaDonOnlineChiTiet item) {
        JOptionPane.showMessageDialog(null,
            "Tên sản phẩm: " + item.getTenSP() + "\n" +
            "Số lượng: " + item.getSoLuong() + "\n" +
            "Giá: " + item.getTongTien() + " VND",
            "Chi tiết sản phẩm",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
        
    private void applyListStyle3() {
        List<HoaDonOnlineChiTiet> khList = service2.getDonHangByEmail2(Email);
        thongTinDonHang2.removeAll();
        if (khList == null || khList.isEmpty()) {
            JLabel emptyLabel = new JLabel("Giỏ hàng của bạn hiện đang trống.");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            thongTinDonHang2.setLayout(new BorderLayout());
            thongTinDonHang2.add(emptyLabel, BorderLayout.CENTER);
        } else {
            ThongTinDonHang<HoaDonOnlineChiTiet> list = new ThongTinDonHang<>();
            for (HoaDonOnlineChiTiet kh : khList) {
                list.addItem(kh);
            }
            list.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int index = list.locationToIndex(evt.getPoint());
                    if (index >= 0) {
                        HoaDonOnlineChiTiet selectedItem = list.getModel().getElementAt(index);
                        if (selectedItem.getTrangThai() == 0) {
                            System.out.println("Hủy đơn hàng: " + selectedItem.getTenSP());
                            huyDon(selectedItem);
                        } else if (selectedItem.getTrangThai() == 4 || selectedItem.getTrangThai() == 5) {
                            System.out.println("Đặt lại đơn hàng: " + selectedItem.getTenSP());
                            datLai(selectedItem);
                        } else {
                            System.out.println("Sản phẩm được chọn: " + selectedItem.getTenSP());
                            showProductDetails(selectedItem);
                        }
                    }
                }
            });
            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            thongTinDonHang2.setLayout(new BorderLayout());
            thongTinDonHang2.add(scrollPane, BorderLayout.CENTER);
        }
        thongTinDonHang2.revalidate();
        thongTinDonHang2.repaint();
    }
    
    private void showProductDetails(GioHang gioHang) {
        JTextField txtTenSP = new JTextField(gioHang.getTenSP());
        txtTenSP.setEnabled(false);
        JTextField txtSoLuong = new JTextField(String.valueOf(gioHang.getSoLuong()));
        JTextField txtTongTien = new JTextField(gioHang.getTongTien().toString());
        txtTongTien.setEnabled(false);
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
        cboKichThuoc.setSelectedItem(gioHang.getKichThuoc());
        cboMauSac.setSelectedItem(gioHang.getTenMauSac());
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
                return;
            } else if (result == 1) {
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
                        capNhatTongTien();
                        return;
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (result == 0) {
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
                String kichThuoc = (String) cboKichThuoc.getSelectedItem();
                String mauSac = (String) cboMauSac.getSelectedItem();
                boolean isUpdated = new GioHangService().updateGioHang(
                    gioHang.getId(), soLuong, kichThuoc, mauSac
                );
                if (isUpdated) {
                    JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thành công!", "Thành Công", JOptionPane.INFORMATION_MESSAGE);
                    refreshGioHangData(); // Làm mới danh sách giỏ hàng
                    capNhatTongTien();
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    } 
    
    public void refreshGioHangData() {
        applyListStyle();
        capNhatTongTien();
    }
    
    public void refreshThongTinData() {
        applyListStyle2();
    }
    
    public void refreshLichSuData() {
        applyListStyle3();
    }
    
    private void capNhatTongTien() {
        List<GioHang> gioHangList = service1.getGioHangByEmail(Email);
        BigDecimal total = BigDecimal.ZERO;
        for (GioHang product : gioHangList) {
            BigDecimal giaSanPham = product.getTongTien();
            total = total.add(giaSanPham);
        }
        jLabel2.setText(total.toString() + " VND");
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
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        thongTinDonHang1 = new da.component.ThongTinDonHang<>();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        thongTinDonHang2 = new da.component.ThongTinDonHang<>();
        jButton4 = new javax.swing.JButton();

        myListGioHang1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(myListGioHang1);

        jButton1.setText("Đặt Hàng");
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

        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("0VNĐ");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 534, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(55, 55, 55)
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
                    .addComponent(jButton3)
                    .addComponent(jLabel2))
                .addContainerGap(69, Short.MAX_VALUE))
        );

        materialTabbed1.addTab("Giỏ hàng", jPanel1);

        thongTinDonHang1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(thongTinDonHang1);

        jButton2.setText("Refesh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton2)
                .addGap(0, 794, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(0, 27, Short.MAX_VALUE))
        );

        materialTabbed1.addTab("Thông tin đơn hàng", jPanel2);

        thongTinDonHang2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(thongTinDonHang2);

        jButton4.setText("Refesh");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jButton4)
                .addGap(0, 794, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        materialTabbed1.addTab("Lịch sử mua hàng", jPanel3);

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
        Application.showForm(new PaymentOnlineForm(Email));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        refreshGioHangData();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        refreshThongTinData();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private da.component.MaterialTabbed materialTabbed1;
    private da.component.MyListGioHang<String> myListGioHang1;
    private da.component.ThongTinDonHang<String> thongTinDonHang1;
    private da.component.ThongTinDonHang<String> thongTinDonHang2;
    // End of variables declaration//GEN-END:variables
}
