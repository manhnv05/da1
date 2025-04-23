package da.component;

import da.model.HoaDonOnlineChiTiet;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ThongTinDonHang<E> extends JList<E> {

    private final DefaultListModel<E> model;

    public ThongTinDonHang() {
        model = new DefaultListModel<>();
        setModel(model);

        // Tăng chiều cao của mỗi mục để tạo khoảng cách
        setFixedCellHeight(120); // Tăng chiều cao để phù hợp với nút JButton

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
        private JLabel lblTrangThai = new JLabel(); // Thêm nhãn lblTrangThai
        private JButton btnAction = new JButton("Hủy Đơn"); // Thêm JButton

        public ProductRenderer() {
            setLayout(new BorderLayout(10, 10));
            setBorder(new EmptyBorder(10, 10, 10, 10));

            JPanel textPanel = new JPanel(new GridLayout(5, 1)); // Sửa thành 5 hàng để chứa thêm JButton
            textPanel.add(lblTitle);
            textPanel.add(lblQuantity);
            textPanel.add(lblGia); // Thêm lblGia vào textPanel
            textPanel.add(lblTrangThai); // Thêm lblTrangThai vào textPanel
            textPanel.add(btnAction); // Thêm JButton vào textPanel

            add(lblImage, BorderLayout.WEST);
            add(textPanel, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof HoaDonOnlineChiTiet) {
                HoaDonOnlineChiTiet hoaDon = (HoaDonOnlineChiTiet) value;
                String trangThaiStr = hoaDon.getTrangThai() == 0 ? "Chờ xác nhận" :
                              hoaDon.getTrangThai() == 1 ? "Đã Duyệt" :
                              hoaDon.getTrangThai() == 2 ? "Đang chuẩn bị hàng" :
                              hoaDon.getTrangThai() == 3 ? "Đang Giao Hàng" : 
                              hoaDon.getTrangThai() == 4 ? "Hoàn Tất" :
                              hoaDon.getTrangThai() == 5 ? "Đã Hủy" : "Không xác định";

                // Đặt nội dung
                lblTitle.setText("<html><b>" + hoaDon.getTenSP() + "</b></html>");
                lblQuantity.setText("Số lượng: " + hoaDon.getSoLuong());
                lblGia.setText("<html>Giá: <font color='red'>" + hoaDon.getTongTien() + " VND</font></html>"); // Đặt giá trị cho lblGia
                lblTrangThai.setText("Trạng Thái: " + trangThaiStr);

                // Hiển thị hình ảnh (scale cho vừa)
                ImageIcon icon = new ImageIcon(hoaDon.getHinhAnh());
                Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(img));

                // Hiển thị nút JButton nếu trạng thái là 0
                btnAction.setVisible(hoaDon.getTrangThai() == 0);
                btnAction.setPreferredSize(new Dimension(60, 25)); // Kích thước ngắn hơn
                btnAction.setMaximumSize(new Dimension(60, 25));

                // Thêm sự kiện cho JButton nếu cần
                btnAction.addActionListener(e -> {
                    // Xử lý sự kiện khi nhấn nút
                    System.out.println("Xác nhận đơn hàng: " + hoaDon.getMaHD());
                });

                // Căn chỉnh màu nền xen kẽ (không đổi màu khi được chọn)
                setBackground(index % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
            }
            return this;
        }
    }
}