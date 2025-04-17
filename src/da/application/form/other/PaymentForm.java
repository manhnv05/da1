package da.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import da.model.NhanVien;
import da.service.NhanVienService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import net.miginfocom.swing.MigLayout;
//import raven.datetime.component.date.DatePicker;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

import javax.swing.*;
import raven.datetime.component.date.DatePicker;

public class PaymentForm extends JPanel {
        NhanVienService service = new NhanVienService();


    public PaymentForm() {
        init();
        initNV();
    }
    
    public void initNV() {
    DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
    boxModel.addElement("-- Chọn nhân viên --");
    ArrayList<NhanVien> NVSet = service.getAllNhanVien();
    for (NhanVien nv : NVSet) {
        if (nv != null) {
            boxModel.addElement(nv.getHo()+' '+ nv.getTen());
        }
    }
    cboNhanVien.setModel(boxModel);
}

    private void init() {
        setLayout(new MigLayout("wrap 2,fillx,insets n 35 n 35", "[fill,200]"));

        JLabel lbContactDetail = new JLabel("Thông tin hóa đơn");
        lbContactDetail.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2;");
        add(lbContactDetail, "gapy 10 10,span 2");

        add(new JLabel("Hình thức thanh toán"), "span 2");
        JComboBox comboPaymentType = new JComboBox();
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
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "tên khách hàng");
        add(txtName);
        add(txtEmail);

        JLabel lbRequestDetail = new JLabel("Yêu cầu chi tiết");
        lbRequestDetail.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2;");
        add(lbRequestDetail, "gapy 10 10,span 2");

        //add(new JLabel("Tổng tiền"));
        add(new JLabel("Ngày"));

        //JTextField txtAmount = new JTextField();
        JFormattedTextField dateEditor = new JFormattedTextField();
        DatePicker datePicker = new DatePicker();
        datePicker.setEditor(dateEditor);
        datePicker.setDateSelectionAble((date) -> !date.isAfter(LocalDate.now()));
        datePicker.now();

        //txtAmount.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "0.00");
        //JLabel lbDollar = new JLabel("$");
        //lbDollar.putClientProperty(FlatClientProperties.STYLE, "" +
                //"border:0,8,0,0;");
        //txtAmount.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_COMPONENT, lbDollar);
        //add(txtAmount);
        add(dateEditor, "wrap");

        add(new JLabel("Nhân viên bán hàng"), "gapy 5,span 2");
        cboNhanVien = new JComboBox();

        add(cboNhanVien, "Span 2");
        
        

        add(new JLabel("Trạng thái"), "gapy 5,span 2");
        JComboBox comboAccount = new JComboBox();
        comboAccount.addItem("Chưa thanh toán");
        comboAccount.addItem("Đã thanh toán");

        add(comboAccount, "Span 2");

        add(new JLabel("Ghi chú"), "gapy 5,span 2");
        JTextArea textArea = new JTextArea(3,20);
        textArea.setText("Cảm ơn quý khách đã chọn mua hàng của chúng tôi!");
        textArea.setEnabled(false);
        textArea.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:1,1,1,1;" +
                "font:-1;" +
                "background:null;");
        add(textArea, "gapy 5 5,span 2");


        // action button

        JButton cmdCancel = new JButton("Cancel");
        JButton cmdPayment = new JButton("Thêm Hóa Đơn") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
        cmdCancel.addActionListener(actionEvent -> {
            ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION);
        });

        cmdPayment.addActionListener(actionEvent -> {
            ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.OK_OPTION);
        });

        add(cmdCancel, "grow 0");
        add(cmdPayment, "grow 0, al trailing");
    }
    
        private JComboBox cboNhanVien;

}
