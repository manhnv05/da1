
package da.application.form.other;

import da.application.form.*;
import com.formdev.flatlaf.FlatClientProperties;
import da.application.Application;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import da.component.PasswordStrengthStatus;
import da.manager.FormsManager;
import da.model.NguoiDung;
import da.service.NguoiDungService;
import java.awt.Color;
import java.util.Arrays;
import org.springframework.security.crypto.bcrypt.BCrypt;
import raven.toast.Notifications;


public class ThongTinCaNhaForm extends JPanel {
    NguoiDungService service = new NguoiDungService();
            private String Email;

    public ThongTinCaNhaForm(String Email) {
        this.Email = Email;
        init();
        detail();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtUsername = new JTextField();
        txtUsername.setEnabled(Boolean.FALSE);
        txtPassword = new JPasswordField();
        txtPasswordNew = new JPasswordField();
        txtConfirmPassword = new JPasswordField();
        lblError = new JLabel(""); // Label thông báo lỗi
        lblError.setForeground(Color.RED);
        lblError.setVisible(false);
        cmdRegister = new JButton("Lưu Thông Tin");

        cmdRegister.addActionListener(e -> {
            if (isMatchPassword()) {
        updateNguoiDung();
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Passwords don't match. Try again!");
            }
        });
        passwordStrengthStatus = new PasswordStrengthStatus();

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        txtFirstName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên");
        txtLastName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Họ");
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập tên tài khoản hoặc email của bạn");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "\"Nhập mật khẩu của bạn");
        txtPasswordNew.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "\"Nhập mật khẩu mới của bạn");
        txtConfirmPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập lại mật khẩu của bạn");
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");
        txtPasswordNew.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");
        txtConfirmPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");

        cmdRegister.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        JLabel lbTitle = new JLabel("Thông tin tài khoản!");
        JLabel description = new JLabel("Thông tin cá nhân của bạn được hiển thị bên dưới. Vui lòng kiểm tra và cập nhật nếu có thay đổi!");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        passwordStrengthStatus.initPasswordField(txtPasswordNew);

        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Họ Tên"), "gapy 10");
        panel.add(txtFirstName, "split 2");
        panel.add(txtLastName);
        panel.add(new JSeparator(), "gapy 5 5");
        panel.add(new JLabel("Tài khoản hoặc email"));
        panel.add(txtUsername);
        panel.add(new JLabel("Mật Khẩu hiện tại"), "gapy 8");
        panel.add(txtPassword);
        panel.add(new JLabel("Mật Khẩu Mới"), "gapy 8");
        panel.add(txtPasswordNew);
        panel.add(passwordStrengthStatus, "gapy 0");
        panel.add(new JLabel("Xác nhận mật khẩu"), "gapy 0");
        panel.add(txtConfirmPassword);
        panel.add(lblError, "gapy 8");
        panel.add(cmdRegister, "gapy 20");
        add(panel);
    }

    public boolean isMatchPassword() {
    char[] password = txtPasswordNew.getPassword();
    char[] confirmPassword = txtConfirmPassword.getPassword();

    boolean isMatch = Arrays.equals(password, confirmPassword);

    // Xóa dữ liệu sau khi sử dụng
    Arrays.fill(password, ' ');
    Arrays.fill(confirmPassword, ' ');

    return isMatch;
    }
    
    public void detail(){
        NguoiDung nd = service.getNguoiDungByEmail(Email);
    if (nd == null) {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Không tìm thấy thông tin người dùng!");
        return;
    }

    // Hiển thị thông tin người dùng lên các trường
    txtFirstName.setText(nd.getTen());
    txtLastName.setText(nd.getHo());
    txtUsername.setText(nd.getEmail());

    // Mật khẩu hiện tại và mật khẩu mới không được hiển thị
    txtPassword.setText(""); // Mật khẩu hiện tại
    txtPasswordNew.setText(""); // Mật khẩu mới
    txtConfirmPassword.setText(""); // Xác nhận mật khẩu

    Notifications.getInstance().show(Notifications.Type.INFO, "Thông tin người dùng đã được tải lên!");
}
    
private void updateNguoiDung() {
    String ho = txtLastName.getText().trim();
    String ten = txtFirstName.getText().trim();
    String matKhauHienTai = String.valueOf(txtPassword.getPassword()).trim();
    String matKhauMoi = String.valueOf(txtPasswordNew.getPassword()).trim();
    String matKhauXacNhan = String.valueOf(txtConfirmPassword.getPassword()).trim();

    // Kiểm tra các trường nhập liệu
    if (ho.isEmpty() || ten.isEmpty() || matKhauHienTai.isEmpty() || matKhauMoi.isEmpty() || matKhauXacNhan.isEmpty()) {
        Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng điền đầy đủ thông tin!");
        return;
    }

    // Kiểm tra mật khẩu mới và xác nhận mật khẩu
    if (!matKhauMoi.equals(matKhauXacNhan)) {
        Notifications.getInstance().show(Notifications.Type.ERROR, "Mật khẩu mới không khớp với xác nhận mật khẩu!");
        return;
    }

    // Lấy thông tin người dùng từ cơ sở dữ liệu
    NguoiDung nd = service.getNguoiDungByEmail(Email);
    if (nd == null) {
        Notifications.getInstance().show(Notifications.Type.ERROR, "Không tìm thấy thông tin người dùng!");
        return;
    }

    // Kiểm tra mật khẩu hiện tại (so sánh trực tiếp, không mã hóa)
    if (!matKhauHienTai.equals(nd.getMatKhau())) {
        Notifications.getInstance().show(Notifications.Type.ERROR, "Mật khẩu hiện tại không chính xác!");
        return;
    }

    // Gọi phương thức cập nhật từ service
    boolean updated = service.updateNguoiDungByEmail(Email, ho, ten, matKhauMoi);
    if (updated) {
        Notifications.getInstance().show(Notifications.Type.SUCCESS, "Cập nhật thông tin thành công!");
        clear();
    } else {
        Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật thông tin thất bại!");
    }
}

public void clear(){
    txtPassword.setText("");
    txtPasswordNew.setText("");
    txtConfirmPassword.setText("");
}
    

    
    private void cmdRegisterActionPerformed(java.awt.event.ActionEvent evt) {                                         
        updateNguoiDung();
}  

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtPasswordNew;
    private JPasswordField txtConfirmPassword;
    private JButton cmdRegister;
    private PasswordStrengthStatus passwordStrengthStatus;
    private JLabel lblError;    
}
