
package da.application.form.other;


public class Slide1 extends javax.swing.JPanel {

    public Slide1() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pictureBox2 = new da.component.PictureBox();
        panelTransparent2 = new da.component.PanelTransparent();

        pictureBox2.setImage(new javax.swing.ImageIcon(getClass().getResource("/da/icon/png/th7.jpg"))); // NOI18N

        panelTransparent2.setAlpha(0.5F);
        pictureBox2.add(panelTransparent2);
        panelTransparent2.setBounds(-1, 0, 450, 400);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pictureBox2, javax.swing.GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pictureBox2, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private da.component.PanelTransparent panelTransparent2;
    private da.component.PictureBox pictureBox2;
    // End of variables declaration//GEN-END:variables
}
