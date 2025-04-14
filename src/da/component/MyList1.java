package da.component;

import da.model.GioHang;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MyList1<E> extends JList<E> {

    private final DefaultListModel<E> model;

    public MyList1() {
        model = new DefaultListModel<>();
        setModel(model);

        // Tăng chiều cao của mỗi mục để tạo khoảng cách
        setFixedCellHeight(100); // Tăng chiều cao để phù hợp với lblGia

        // Tùy chỉnh Renderer để hiển thị đẹp hơn
        setCellRenderer(new ProductRenderer());
    }

    public void addItem(E item) {
        model.addElement(item);
    }

    // Renderer để tùy chỉnh giao diện từng mục
    private class ProductRenderer extends JPanel implements ListCellRenderer<E> {
        private JLabel lblImage = new JLabel();
        private JLabel lblTitle = new JLabel();
        private JLabel lblQuantity = new JLabel();
        private JLabel lblGia = new JLabel(); // Thêm nhãn lblGia

        public ProductRenderer() {
            setLayout(new BorderLayout(10, 10));
            setBorder(new EmptyBorder(10, 10, 10, 10));

            JPanel textPanel = new JPanel(new GridLayout(3, 1)); // Sửa thành 3 hàng để chứa lblGia
            textPanel.add(lblTitle);
            textPanel.add(lblQuantity);
            textPanel.add(lblGia); // Thêm lblGia vào textPanel

            add(lblImage, BorderLayout.WEST);
            add(textPanel, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof GioHang) {
                GioHang gh = (GioHang) value;

                // Đặt nội dung
                lblTitle.setText("<html><b>" + gh.getTenSP() + "</b></html>");
                lblQuantity.setText("Số lượng: " + gh.getSoLuong());
                lblGia.setText("<html>Giá: <font color='red'>" + gh.getTongTien() + " VND</font></html>"); // Đặt giá trị cho lblGia

                // Hiển thị hình ảnh (scale cho vừa)
                ImageIcon icon = new ImageIcon(gh.getHinhAnh());
                Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(img));

                // Căn chỉnh màu nền xen kẽ (không đổi màu khi được chọn)
                setBackground(index % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
            }
            return this;
        }
    }
}