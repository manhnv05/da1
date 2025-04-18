package da.component;

import da.model.GioHang;
import da.model.SanPham;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MyListGioHang<E> extends JList<E> {

    private final DefaultListModel<E> model;

    public MyListGioHang() {
        model = new DefaultListModel<>();
        setModel(model);
        setFixedCellHeight(80);
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

        public ProductRenderer() {
            setLayout(new BorderLayout(10, 10));
            setBorder(new EmptyBorder(10, 10, 10, 10));

            JPanel textPanel = new JPanel(new GridLayout(2, 1));
            textPanel.add(lblTitle);
            textPanel.add(lblQuantity);

            add(lblImage, BorderLayout.WEST);
            add(textPanel, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof GioHang) {
                GioHang gh = (GioHang) value;

                lblTitle.setText("<html><b>" + gh.getTenSP()+ "</b></html>");
                lblQuantity.setText("Số lượng: " + gh.getSoLuong());

                ImageIcon icon = new ImageIcon(gh.getHinhAnh());
                Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(img));

                setBackground(index % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
            }
            return this;
        }
    }
}
