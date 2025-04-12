
package da.application.form;

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


public class Register extends JPanel {
    NguoiDungService service = new NguoiDungService();
    public Register() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtConfirmPassword = new JPasswordField();
        lblError = new JLabel(""); // Label thông báo lỗi
        lblError.setForeground(Color.RED);
        lblError.setVisible(false);
        cmdRegister = new JButton("Sign Up");

        cmdRegister.addActionListener(e -> {
            if (isMatchPassword()) {
        registerUser();
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
        txtConfirmPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập lại mật khẩu của bạn");
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");
        txtConfirmPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");

        cmdRegister.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        JLabel lbTitle = new JLabel("Chào mừng bạn đến với chúng tôi!");
        JLabel description = new JLabel("Hãy tham gia cùng chúng tôi để mua sắm trang phục mới. Đăng ký ngay và bắt đầu mua sắm!");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        passwordStrengthStatus.initPasswordField(txtPassword);

        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Họ Tên"), "gapy 10");
        panel.add(txtFirstName, "split 2");
        panel.add(txtLastName);
        panel.add(new JSeparator(), "gapy 5 5");
        panel.add(new JLabel("Tài khoản hoặc email"));
        panel.add(txtUsername);
        panel.add(new JLabel("Mật Khẩu"), "gapy 8");
        panel.add(txtPassword);
        panel.add(passwordStrengthStatus, "gapy 0");
        panel.add(new JLabel("Xác nhận mật khẩu"), "gapy 0");
        panel.add(txtConfirmPassword);
        panel.add(lblError, "gapy 8");
        panel.add(cmdRegister, "gapy 20");
        panel.add(createLoginLabel(), "gapy 10");
        add(panel);
    }


    private Component createLoginLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        JButton cmdLogin = new JButton("<html><a href=\"#\">Đăng nhập tại đây</a></html>");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:3,3,3,3");
        cmdLogin.setContentAreaFilled(false);
        cmdLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdLogin.addActionListener(e -> {
            FormsManager.getInstance().showForm(new Login());
        });
        JLabel label = new JLabel("Bạn đã có tài khoản?");
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");
        panel.add(label);
        panel.add(cmdLogin);
        return panel;
    }

    public boolean isMatchPassword() {
    char[] password = txtPassword.getPassword();
    char[] confirmPassword = txtConfirmPassword.getPassword();

    boolean isMatch = Arrays.equals(password, confirmPassword);

    // Xóa dữ liệu sau khi sử dụng
    Arrays.fill(password, ' ');
    Arrays.fill(confirmPassword, ' ');

    return isMatch;
    }
    
    private void registerUser() {
    String ho = txtLastName.getText().trim();
    String ten = txtFirstName.getText().trim();
    String email = txtUsername.getText().trim();
    String password = String.valueOf(txtPassword.getPassword());
    String confirmPassword = String.valueOf(txtConfirmPassword.getPassword());

    if (ho.isEmpty() || ten.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
        lblError.setVisible(true);
        Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng điền đầy đủ thông tin!");
        lblError.setText("Vui lòng nhập đầy đủ thông tin!");
        return;
    }

    if (!password.equals(confirmPassword)) {
        lblError.setVisible(true);
        Notifications.getInstance().show(Notifications.Type.ERROR, "Mật khẩu không khớp!");
        lblError.setText("Mật khẩu không khớp!");
        return;
    }

    // Gọi service để đăng ký mà không mã hóa mật khẩu
    boolean isRegistered = service.registerUser(ho, ten, email, password);
    if (isRegistered) {
        Notifications.getInstance().show(Notifications.Type.SUCCESS, "Đăng ký thành công!");
            Application.login(email, ho + " " + ten); // Truyền thêm tên người dùng
    } else {
        Notifications.getInstance().show(Notifications.Type.ERROR, "Đăng ký thất bại!");
    }
}

    
    private void cmdRegisterActionPerformed(java.awt.event.ActionEvent evt) {                                         
        registerUser();
}  

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JButton cmdRegister;
    private PasswordStrengthStatus passwordStrengthStatus;
    private JLabel lblError;    
}
