package da.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import da.application.Application;
import da.model.HoaDonOnlineChiTiet;
import da.service.GioHangService;
import da.service.HoaDonOnlineService;
import da.service.NhanVienService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import raven.datetime.component.date.DatePicker;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;
import raven.toast.Notifications;

public class PaymentOnlineForm extends JPanel {
    private NhanVienService service = new NhanVienService();
    private HoaDonOnlineService service1 = new HoaDonOnlineService();
    private GioHangService service2 = new GioHangService();
    private String Email;
    private List<Integer> productIds;

    public PaymentOnlineForm(String Email) {
        this.Email = Email;
        this.productIds = service2.getGioHangIdsByEmail(Email);
        init();
        System.out.println("Product IDs in PaymentForm: " + productIds);
    }

    private void init() {
        setLayout(new MigLayout("wrap 2,fillx,insets n 35 n 35", "[fill,200]"));
        JLabel lbContactDetail = new JLabel("Thông tin đơn hàng");
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
        add(new JLabel("Tên người nhận"));
        JTextField txtName = new JTextField();
        JTextField txtEmail = new JTextField();
        txtName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mã hóa đơn");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên người nhận");
        add(txtName);
        add(txtEmail);
        txtName.setEnabled(false);
        JLabel lbRequestDetail = new JLabel("Yêu cầu chi tiết");
        lbRequestDetail.putClientProperty(FlatClientProperties.STYLE, "font:bold +2;");
        add(lbRequestDetail, "gapy 10 10,span 2");
        add(new JLabel("SĐT liên hệ"));
        add(new JLabel("Ngày"));
        JTextField txtSDT = new JTextField();
        JFormattedTextField dateEditor = new JFormattedTextField();
        txtSDT.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Số điện thoại liên hệ");
        DatePicker datePicker = new DatePicker();
        datePicker.setEditor(dateEditor);
        datePicker.setDateSelectionAble((date) -> !date.isAfter(LocalDate.now()));
        datePicker.now();
        add(txtSDT);
        add(dateEditor);
        add(new JLabel("Địa chỉ nhận hàng"), "gapy 5,span 2");
        JTextField txtDiaChiNhanHang = new JTextField();
        txtDiaChiNhanHang.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Địa chỉ nhận hàng");
        add(txtDiaChiNhanHang, "Span 2");
        add(new JLabel("Phương thức vận chuyển"), "gapy 5,span 2");
        JComboBox<String> comboAccount = new JComboBox<>();
        comboAccount.addItem("Vận chuyển bình thường");
        comboAccount.addItem("Vận chuyển nhanh");
        comboAccount.addItem("Vận chuyển siêu tốc");
        add(comboAccount, "Span 2");
        add(new JLabel("Lưu ý"), "gapy 5,span 2");
        JTextField txtLuuY = new JTextField();
        txtLuuY.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Lưu ý cho người bán");
        add(txtLuuY, "Span 2");
        JButton cmdCancel = new JButton("Cancel");
        JButton cmdPayment = new JButton("Thêm Hóa Đơn") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
        cmdCancel.addActionListener(actionEvent -> {
            ModalBorderAction modalAction = ModalBorderAction.getModalBorderAction(this);
            if (modalAction != null) {
                modalAction.doAction(SimpleModalBorder.CANCEL_OPTION);
            } else {
                System.out.println("ModalBorderAction is null. Ensure the modal is configured properly.");
            }
        });
        cmdPayment.addActionListener(actionEvent -> {
            String maHoaDon = "HDO" + System.currentTimeMillis(); // Mã hóa đơn tự động sinh
            String tenKhachHang = txtEmail.getText().trim(); // Tên khách hàng
            String soDienThoai = txtSDT.getText().trim();
            String diaChiGiaoHang = txtDiaChiNhanHang.getText().trim();
            String hinhThucThanhToan = (String) comboPaymentType.getSelectedItem();
            String hinhThucVanChuyen = (String) comboAccount.getSelectedItem();
            String luuy = txtLuuY.getText().trim();
            String sdtPattern = "^(0\\d{9})$";
            if (tenKhachHang.isEmpty() || soDienThoai.isEmpty() || diaChiGiaoHang.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin cần thiết!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!soDienThoai.matches(sdtPattern)) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập SDT đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtSDT.requestFocus();
                return;
            }
            int hoaDonID = service1.addHoaDonOnline(maHoaDon, soDienThoai, tenKhachHang, diaChiGiaoHang, hinhThucThanhToan, hinhThucVanChuyen, luuy);
            if (hoaDonID == -1) {
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<HoaDonOnlineChiTiet> chiTietList = new ArrayList<>();
            for (Integer productId : productIds) {
                HoaDonOnlineChiTiet chiTiet = new HoaDonOnlineChiTiet(productId);
                chiTiet.setHoadononlineID(hoaDonID);
                chiTietList.add(chiTiet);
            }
            boolean detailsSuccess = service1.addChiTietHoaDon(chiTietList);
            if (detailsSuccess) {
                boolean deleted = service2.deleteGioHangByIds(productIds);
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "Thêm hóa đơn và chi tiết hóa đơn thành công! Giỏ hàng đã được cập nhật.", "Thành Công", JOptionPane.INFORMATION_MESSAGE);
                    Application.showForm(new FormGioHang(Email));
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công nhưng không thể cập nhật trạng thái giỏ hàng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                }
                ModalBorderAction modalAction = ModalBorderAction.getModalBorderAction(this);
                if (modalAction != null) {
                    modalAction.doAction(SimpleModalBorder.OK_OPTION);
                } else {
                    System.out.println("ModalBorderAction is null. Ensure the modal is configured.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công nhưng thêm chi tiết thất bại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            }
        });
        add(cmdCancel, "grow 0");
        add(cmdPayment, "grow 0, al trailing");
    }
}