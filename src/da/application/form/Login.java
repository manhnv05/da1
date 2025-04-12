package da.application.form;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import da.application.Application;
import da.manager.FormsManager;
import da.model.NguoiDung;
import da.service.NguoiDungService;
import java.awt.Color;
import raven.toast.Notifications;

public class Login extends JPanel {
    NguoiDungService service = new NguoiDungService();

    public Login() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        lblError = new JLabel(""); // Label thông báo lỗi
        lblError.setForeground(Color.RED);
        lblError.setVisible(false);
        chRememberMe = new JCheckBox("Remember me");
        cmdLogin = new JButton("Login");
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,250:280"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        txtPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập tên tài khoản hoặc email của bạn");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mật khẩu của bạn");

        JLabel lbTitle = new JLabel("Chào mừng trở lại!");
        JLabel description = new JLabel("Vui lòng đăng nhập để truy cập tài khoản của bạn");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Tài Khoản"), "gapy 8");
        panel.add(txtUsername);
        panel.add(new JLabel("Mật Khẩu"), "gapy 8");
        panel.add(txtPassword);
        panel.add(lblError, "gapy 8");
        panel.add(chRememberMe, "grow 0");
        panel.add(cmdLogin, "gapy 10");
        panel.add(createSignupLabel(), "gapy 10");
        add(panel);
        cmdLogin.addActionListener(this::cmdLoginActionPerformed);
    }

    private Component createSignupLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        JButton cmdRegister = new JButton("<html><a href=\"#\">Tạo tài khoản</a></html>");
        cmdRegister.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:3,3,3,3");
        cmdRegister.setContentAreaFilled(false);
        cmdRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdRegister.addActionListener(e -> {
            FormsManager.getInstance().showForm(new Register());
        });
        JLabel label = new JLabel("Chưa có tài khoản ?");
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");
        panel.add(label);
        panel.add(cmdRegister);
        return panel;
    }
    
    public void Login() {
        
        String email = txtUsername.getText();
        String password = txtPassword.getText();
        if (email.isEmpty() || password.isEmpty()) {
            lblError.setVisible(true);
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Vui lòng nhập đầy đủ thông tin!");
            lblError.setText("Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        NguoiDung nd = service.login(email, password);
        if (nd != null) {
            String fullName = nd.getHo() + " " + nd.getTen(); // Lấy tên đầy đủ của người dùng
            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Đăng Nhập Thành Công");
            Application.login(email, fullName); // Truyền thêm tên người dùng
            txtUsername.setText("");
            txtPassword.setText("");
        } else {
            lblError.setVisible(true);
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Lỗi Đăng nhập");
            lblError.setText("Tài khoản hoặc mật khẩu không đúng!");
        }
    }

    private void cmdLoginActionPerformed(java.awt.event.ActionEvent evt) {                                         
        Login();
    }  

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chRememberMe;
    private JButton cmdLogin;
    private JLabel lblError;    
}