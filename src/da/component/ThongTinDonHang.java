package da.component;

import da.model.HoaDonOnlineChiTiet;
import da.service.HoaDonOnlineService;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ThongTinDonHang<E> extends JList<E> {

    private final DefaultListModel<E> model;
    HoaDonOnlineService service = new HoaDonOnlineService();

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

    public void huyDon(HoaDonOnlineChiTiet hoaDon) {
        boolean success = service.huyDon(hoaDon.getHoadononlineID());
        if (success) {
            System.out.println("Hủy đơn hàng thành công: " + hoaDon.getTenSP());
            hoaDon.setTrangThai(5); // Cập nhật trạng thái thành "Đã Hủy"
            refreshList(); // Làm mới giao diện
        } else {
            System.out.println("Hủy đơn hàng thất bại: " + hoaDon.getTenSP());
        }
    }

    public void datLai(HoaDonOnlineChiTiet hoaDon) {
        boolean success = service.datLai(hoaDon.getHoadononlineID());
        if (success) {
            System.out.println("Đặt lại đơn hàng thành công: " + hoaDon.getTenSP());
            hoaDon.setTrangThai(0); // Cập nhật trạng thái thành "Chờ xác nhận"
            refreshList(); // Làm mới giao diện
        } else {
            System.out.println("Đặt lại đơn hàng thất bại: " + hoaDon.getTenSP());
        }
    }

    private void refreshList() {
        // Làm mới danh sách sau khi cập nhật
        revalidate();
        repaint();
    }

    // Renderer để tùy chỉnh giao diện từng mục
    private class ProductRenderer extends JPanel implements ListCellRenderer<E> {
        private final JLabel lblImage = new JLabel();
        private final JLabel lblTitle = new JLabel();
        private final JLabel lblQuantity = new JLabel();
        private final JLabel lblGia = new JLabel(); // Thêm nhãn lblGia
        private final JLabel lblTrangThai = new JLabel(); // Thêm nhãn lblTrangThai
        private final JButton btnAction = new JButton("Hủy Đơn"); // Thêm JButton
        private final JButton btnDatHang = new JButton("Đặt Lại"); // Thêm JButton

        public ProductRenderer() {
            setLayout(new BorderLayout(10, 10));
            setBorder(new EmptyBorder(10, 10, 10, 10));

            JPanel textPanel = new JPanel(new GridLayout(6, 1));
            textPanel.add(lblTitle);
            textPanel.add(lblQuantity);
            textPanel.add(lblGia);
            textPanel.add(lblTrangThai);
            textPanel.add(btnAction);
            textPanel.add(btnDatHang);

            add(lblImage, BorderLayout.WEST);
            add(textPanel, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof HoaDonOnlineChiTiet) {
                HoaDonOnlineChiTiet hoaDon = (HoaDonOnlineChiTiet) value;

                // Đặt thông tin vào renderer
                lblTitle.setText("<html><b>" + hoaDon.getTenSP() + "</b></html>");
                lblQuantity.setText("Số lượng: " + hoaDon.getSoLuong());
                lblGia.setText("<html>Giá: <font color='red'>" + hoaDon.getTongTien() + " VND</font></html>");
                lblTrangThai.setText("Trạng Thái: " + getTrangThaiText(hoaDon.getTrangThai()));

                // Hiển thị hình ảnh (scale cho vừa)
                ImageIcon icon = new ImageIcon(hoaDon.getHinhAnh());
                Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                lblImage.setIcon(new ImageIcon(img));

                // Hiển thị hoặc ẩn nút dựa trên trạng thái
                btnAction.setVisible(hoaDon.getTrangThai() == 0);
                btnDatHang.setVisible(hoaDon.getTrangThai() == 4 || hoaDon.getTrangThai() == 5);

                // Xóa tất cả các listener cũ trước khi thêm mới
                for (ActionListener al : btnAction.getActionListeners()) {
                    btnAction.removeActionListener(al);
                }
                btnAction.addActionListener(e -> huyDon(hoaDon));

                for (ActionListener al : btnDatHang.getActionListeners()) {
                    btnDatHang.removeActionListener(al);
                }
                btnDatHang.addActionListener(e -> datLai(hoaDon));

                // Căn chỉnh màu nền xen kẽ
                setBackground(index % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
            }
            return this;
        }

        private String getTrangThaiText(int trangThai) {
            return switch (trangThai) {
                case 0 -> "Chờ xác nhận";
                case 1 -> "Đã Duyệt";
                case 2 -> "Đang chuẩn bị hàng";
                case 3 -> "Đang Giao Hàng";
                case 4 -> "Hoàn Tất";
                case 5 -> "Đã Hủy";
                default -> "Không xác định";
            };
        }
    }
}