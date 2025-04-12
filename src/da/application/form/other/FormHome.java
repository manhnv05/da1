package da.application.form.other;

import da.component.EventItem;
import da.component.Item;
import da.component.ScrollBar;
import da.model.SanPham;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class FormHome extends javax.swing.JPanel {

public void setEvent(EventItem event) {
        this.event = event;
    }

    private EventItem event;

    public FormHome() {
        initComponents();
        scroll.setVerticalScrollBar(new ScrollBar());
    }

    public void addItem(SanPham data) {
        Item item = new Item();
        item.setData(data);
//        item.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent me) {
//                if (SwingUtilities.isLeftMouseButton(me)) {
//                    event.itemClick(item, data);
//                }
//            }
//        });
        panelItem1.add(item);
        panelItem1.repaint();
        panelItem1.revalidate();
    }

    public void setSelected(Component item) {
        for (Component com : panelItem1.getComponents()) {
            Item i = (Item) com;
            if (i.isSelected()) {
                i.setSelected(false);
            }
        }
        ((Item) item).setSelected(true);
    }

    public void clearItems() {
        panelItem1.removeAll();
        panelItem1.repaint();
        panelItem1.revalidate();
    }

//    public Point getPanelItemLocation() {
//        Point p = scroll.getLocation();
//        return new Point(p.x, p.y - scroll.getViewport().getViewPosition().y);
//    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scroll = new javax.swing.JScrollPane();
        panelItem1 = new da.component.PanelItem();

        setOpaque(false);

        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setViewportView(panelItem1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 1023, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private da.component.PanelItem panelItem1;
    private javax.swing.JScrollPane scroll;
    // End of variables declaration//GEN-END:variables

    
}
