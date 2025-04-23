package da.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import da.model.HoaDonChiTiet;
import da.model.NhanVien;
import da.service.GioHangService;
import da.service.HoaDonService;
import da.service.NhanVienService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import raven.datetime.component.date.DatePicker;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

public class PaymentForm extends JPanel {
    private NhanVienService service = new NhanVienService();
    private HoaDonService service1 = new HoaDonService();
    private GioHangService service2 = new GioHangService();
    private List<Integer> productIds;

    public PaymentForm(List<Integer> productIds) {
        this.productIds = productIds;
        init();
        initNV();
        System.out.println("Product IDs in PaymentForm: " + productIds);
    }

    public void initNV() {
        DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
        boxModel.addElement("-- Chọn nhân viên --");
        ArrayList<NhanVien> NVSet = service.getAllNhanVien();
        for (NhanVien nv : NVSet) {
            if (nv != null) {
                boxModel.addElement(nv.getHo() + ' ' + nv.getTen());
            }
        }
        cboNhanVien.setModel(boxModel);
    }

    private void init() {
        setLayout(new MigLayout("wrap 2,fillx,insets n 35 n 35", "[fill,200]"));
        JLabel lbContactDetail = new JLabel("Thông tin hóa đơn");
        lbContactDetail.putClientProperty(FlatClientProperties.STYLE, "font:bold +2;");
        add(lbContactDetail, "gapy 10 10,span 2");
        add(new JLabel("Hình thức thanh toán"), "span 2");
        JComboBox<String> comboPaymentType = new JComboBox<>();
        comboPaymentType.addItem("Chuyển khoản ngân hàng");
        comboPaymentType.addItem("Tiền mặt");
        comboPaymentType.addItem("Thanh toán di động");
        comboPaymentType.addItem("Cổng thanh toán trực tuyến");
        comboPaymentType.addItem("Thẻ tín dụng/ghi nợ");
        add(comboPaymentType, "gapy n 5,span 2");
        add(new JLabel("Mã Hóa Đơn"));
        add(new JLabel("Tên Khách Hàng"));
        JTextField txtName = new JTextField();
        JTextField txtEmail = new JTextField();
        txtName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mã hóa đơn");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên khách hàng");
        add(txtName);
        add(txtEmail);
        txtName.setEnabled(false);
        JLabel lbRequestDetail = new JLabel("Yêu cầu chi tiết");
        lbRequestDetail.putClientProperty(FlatClientProperties.STYLE, "font:bold +2;");
        add(lbRequestDetail, "gapy 10 10,span 2");
        add(new JLabel("Ngày"), "gapy 5,span 2");
        JFormattedTextField dateEditor = new JFormattedTextField();
        DatePicker datePicker = new DatePicker();
        datePicker.setEditor(dateEditor);
        datePicker.setDateSelectionAble((date) -> !date.isAfter(LocalDate.now()));
        datePicker.now();
        add(dateEditor, "Span 2");
        add(new JLabel("Nhân viên bán hàng"), "gapy 5,span 2");
        cboNhanVien = new JComboBox<>();
        add(cboNhanVien, "Span 2");
        add(new JLabel("Trạng thái"), "gapy 5,span 2");
        JComboBox<String> comboAccount = new JComboBox<>();
        comboAccount.addItem("Chưa thanh toán");
        comboAccount.addItem("Đã thanh toán");
        add(comboAccount, "Span 2");
        add(new JLabel("Ghi chú"), "gapy 5,span 2");
        JTextArea textArea = new JTextArea(3, 20);
        textArea.setText("Cảm ơn quý khách đã chọn mua hàng của chúng tôi!");
        textArea.setEnabled(false);
        textArea.putClientProperty(FlatClientProperties.STYLE, "border:1,1,1,1;font:-1;background:null;");
        add(textArea, "gapy 5 5,span 2");
        JButton cmdCancel = new JButton("Cancel");
        JButton cmdPayment = new JButton("Thêm Hóa Đơn") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
        cmdCancel.addActionListener(actionEvent -> ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION));
        cmdPayment.addActionListener(actionEvent -> {
            String maHoaDon = "HD" + System.currentTimeMillis();
            String tenKhachHang = txtEmail.getText().trim();
            String ghiChu = textArea.getText().trim();
            String hinhThucThanhToan = (String) comboPaymentType.getSelectedItem();
            int trangThai = comboAccount.getSelectedIndex();
            if (tenKhachHang.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên khách hàng không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int nhanVienID = cboNhanVien.getSelectedIndex() > 0 ? cboNhanVien.getSelectedIndex() : -1;
            if (nhanVienID == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên bán hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int hoaDonID = service1.addHoaDon(maHoaDon, trangThai, ghiChu, tenKhachHang, nhanVienID, hinhThucThanhToan);
            if (hoaDonID == -1) {
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<HoaDonChiTiet> chiTietList = new ArrayList<>();
            for (Integer productId : productIds) {
                HoaDonChiTiet chiTiet = new HoaDonChiTiet(productId); 
                chiTiet.setHoaDonID(hoaDonID);
                chiTietList.add(chiTiet);
            }
            boolean detailsSuccess = service1.addChiTietHoaDon(chiTietList);
            if (detailsSuccess) {
                boolean deleted = service2.deleteGioHangByIds(productIds);
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "Thêm hóa đơn và chi tiết hóa đơn thành công! Giỏ hàng đã được cập nhật.", "Thành Công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công nhưng không thể cập nhật trạng thái giỏ hàng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                }
                ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.OK_OPTION);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công nhưng thêm chi tiết thất bại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            }
        });
        add(cmdCancel, "grow 0");
        add(cmdPayment, "grow 0, al trailing");
    }
    
    
    private JComboBox<String> cboNhanVien;
    private JTextField txtEmail;
            }