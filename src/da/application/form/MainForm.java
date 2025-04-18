package da.application.form;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import da.application.Application;
import da.application.form.other.*;
import da.menu.Menu;
import da.menu.MenuAction;
import da.menu.MenuItem;
import da.service.DanhMucChucNangService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainForm extends JLayeredPane {

    private Menu menu;
    private JPanel panelBody;
    private JButton menuButton;
    private Map<Integer, Component> formMap;
    private DanhMucChucNangService danhMucChucNangService;
    private String currentUserEmail;

    public MainForm(Menu menu, String currentUserEmail) {
        this.menu = menu;
        this.currentUserEmail = currentUserEmail;
        this.danhMucChucNangService = new DanhMucChucNangService();
        init();
        initializeFormMap();
        loadMenuItems();
        initMenuEvent();
        
    }

    public String getCurrentUserEmail() {
        return currentUserEmail;
    }
    
    

    private void init() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new MainFormLayout());
        panelBody = new JPanel(new BorderLayout());
        initMenuArrowIcon();
        menuButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.button.background;"
                + "arc:999;"
                + "focusWidth:0;"
                + "borderWidth:0");
        menuButton.addActionListener((ActionEvent e) -> {
            setMenuFull(!menu.isMenuFull());
        });
        setLayer(menuButton, JLayeredPane.POPUP_LAYER);
        add(menuButton);
        add(menu);
        add(panelBody);
    }

    private void initializeFormMap() {
        formMap = new HashMap<>();
        formMap.put(1, new FormTrangChu());
        formMap.put(2, new FormQLSanPham());
        formMap.put(3, new FormThuocTinh());
        formMap.put(4, new FormDonHang(currentUserEmail));
        //formMap.put(5, new FormVoucher());
        formMap.put(6, new FormKhoHang());
        formMap.put(7, new FormNhanVien());
        formMap.put(8, new DashboardForm());
        formMap.put(9, new ThongTinCaNhaForm(currentUserEmail));
        formMap.put(10, new FprmSanPham());
        formMap.put(11, new FormGioHang(currentUserEmail));
    }

    private void loadMenuItems() {
        int idVaiTro = danhMucChucNangService.getCurrentUserRole(currentUserEmail);
        List<MenuItem> menuItems = danhMucChucNangService.layDanhMucChucNang(menu, idVaiTro);
        for (MenuItem item : menuItems) {
            menu.add(item);
        }
    }

    @Override
    public void applyComponentOrientation(ComponentOrientation o) {
        super.applyComponentOrientation(o);
        initMenuArrowIcon();
    }

    private void initMenuArrowIcon() {
        if (menuButton == null) {
            menuButton = new JButton();
        }
        String icon = (getComponentOrientation().isLeftToRight()) ? "menu_left.svg" : "menu_right.svg";
        menuButton.setIcon(new FlatSVGIcon("da/icon/svg/" + icon, 0.8f));
    }

    private void initMenuEvent() {
        menu.addMenuEvent((int id, int subIndex, MenuAction action) -> {
            Component form = formMap.get(id);
            if (form != null) {
                Application.showForm(form);
            } else {
                switch (id) {
                    case 1:
                        Application.showForm(new FormTrangChu());
                        break;
                    case 2:
                        Application.showForm(new FormQLSanPham());
                        break;
                    case 3:
                        Application.showForm(new FormThuocTinh());
                        break;
                    case 4:
                        Application.showForm(new FormDonHang(currentUserEmail));
                        break;
                    case 5:
                        JOptionPane.showMessageDialog(null, "Chức năng 'Voucher' sẽ sớm được phát triển!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 6:
                        Application.showForm(new FormKhoHang());
                        break;
                    case 7:
                        Application.showForm(new FormNhanVien());
                        break;
                    case 8:
                        Application.showForm(new FormDonHang(currentUserEmail));
                        break;
                    case 9:
                        Application.showForm(new ThongTinCaNhaForm(currentUserEmail));
                        break;
                    case 10:
                        Application.showForm(new FormKhoHang());
                        break;
                    case 11:
                        Application.showForm(new FormGioHang(currentUserEmail));
                        break;
                    case 12: // Assuming 12 is the id for logout
                        Application.logout();
                        break;
                    default:
                        action.cancel();
                        break;
                }
            }
        });
    }
    public void showInitialForm() {
        Application.showForm(formMap.get(1)); // Hiển thị form Trang Chủ
    }


    private void setMenuFull(boolean full) {
        String icon;
        if (getComponentOrientation().isLeftToRight()) {
            icon = (full) ? "menu_left.svg" : "menu_right.svg";
        } else {
            icon = (full) ? "menu_right.svg" : "menu_left.svg";
        }
        menuButton.setIcon(new FlatSVGIcon("da/icon/svg/" + icon, 0.8f));
        menu.setMenuFull(full);
        revalidate();
    }

    public void hideMenu() {
        menu.hideMenuItem();
    }

    public void showForm(Component component) {
        panelBody.removeAll();
        panelBody.add(component);
        panelBody.repaint();
        panelBody.revalidate();
    }

    public void setSelectedMenu(int index, int subIndex) {
        menu.setSelectedMenu(index, subIndex);
    }

    private class MainFormLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(5, 5);
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                boolean ltr = parent.getComponentOrientation().isLeftToRight();
                Insets insets = UIScale.scale(parent.getInsets());
                int x = insets.left;
                int y = insets.top;
                int width = parent.getWidth() - (insets.left + insets.right);
                int height = parent.getHeight() - (insets.top + insets.bottom);
                int menuWidth = UIScale.scale(menu.isMenuFull() ? menu.getMenuMaxWidth() : menu.getMenuMinWidth());
                int menuX = ltr ? x : x + width - menuWidth;
                menu.setBounds(menuX, y, menuWidth, height);
                int menuButtonWidth = menuButton.getPreferredSize().width;
                int menuButtonHeight = menuButton.getPreferredSize().height;
                int menubX;
                if (ltr) {
                    menubX = (int) (x + menuWidth - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.3f)));
                } else {
                    menubX = (int) (menuX - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.7f)));
                }
                menuButton.setBounds(menubX, UIScale.scale(30), menuButtonWidth, menuButtonHeight);
                int gap = UIScale.scale(5);
                int bodyWidth = width - menuWidth - gap;
                int bodyHeight = height;
                int bodyx = ltr ? (x + menuWidth + gap) : x;
                int bodyy = y;
                panelBody.setBounds(bodyx, bodyy, bodyWidth, bodyHeight);
            }
        }
    }
}