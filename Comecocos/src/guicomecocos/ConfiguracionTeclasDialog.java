package guicomecocos;

import java.awt.Color;

public class ConfiguracionTeclasDialog extends javax.swing.JDialog {
    ComecocosFrame frame;

    /** Creates new form ConfiguracionTeclasDialog */
    public ConfiguracionTeclasDialog(ComecocosFrame cf) {
        super(cf, true);
        frame = cf;
        initComponents();
    }

    // Oscurece todos los labels de las teclas
    private void ennegrecerTeclas() {
        lArribaJ1.setForeground(Color.black);
        lAbajoJ1.setForeground(Color.black);
        lDerechaJ1.setForeground(Color.black);
        lIzquierdaJ1.setForeground(Color.black);
        lArribaJ2.setForeground(Color.black);
        lAbajoJ2.setForeground(Color.black);
        lDerechaJ2.setForeground(Color.black);
        lIzquierdaJ2.setForeground(Color.black);
        lPausa.setForeground(Color.black);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bAceptar = new javax.swing.JButton();
        jpJugador2 = new javax.swing.JPanel();
        lArribaJ2 = new javax.swing.JLabel();
        tArribaJ2 = new javax.swing.JTextField();
        lAbajoJ2 = new javax.swing.JLabel();
        tAbajoJ2 = new javax.swing.JTextField();
        lDerechaJ2 = new javax.swing.JLabel();
        tDerechaJ2 = new javax.swing.JTextField();
        lIzquierdaJ2 = new javax.swing.JLabel();
        tIzquierdaJ2 = new javax.swing.JTextField();
        jpJugador1 = new javax.swing.JPanel();
        lArribaJ1 = new javax.swing.JLabel();
        tArribaJ1 = new javax.swing.JTextField();
        lAbajoJ1 = new javax.swing.JLabel();
        tAbajoJ1 = new javax.swing.JTextField();
        lDerechaJ1 = new javax.swing.JLabel();
        tDerechaJ1 = new javax.swing.JTextField();
        lIzquierdaJ1 = new javax.swing.JLabel();
        tIzquierdaJ1 = new javax.swing.JTextField();
        lPausa = new javax.swing.JLabel();
        tPausa = new javax.swing.JTextField();
        bCancelar = new javax.swing.JButton();
        bValoresDefecto = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        bAceptar.setText("Aceptar");
        bAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAceptarActionPerformed(evt);
            }
        });

        jpJugador2.setBorder(javax.swing.BorderFactory.createTitledBorder("Jugador 2"));

        lArribaJ2.setText("Arriba");

        tArribaJ2.setEditable(false);
        tArribaJ2.setText("87");
        tArribaJ2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tArribaJ2MouseClicked(evt);
            }
        });
        tArribaJ2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tArribaJ2KeyPressed(evt);
            }
        });

        lAbajoJ2.setText("Abajo");

        tAbajoJ2.setEditable(false);
        tAbajoJ2.setText("83");
        tAbajoJ2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tAbajoJ2MouseClicked(evt);
            }
        });
        tAbajoJ2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tAbajoJ2KeyPressed(evt);
            }
        });

        lDerechaJ2.setText("Derecha");

        tDerechaJ2.setEditable(false);
        tDerechaJ2.setText("68");
        tDerechaJ2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tDerechaJ2MouseClicked(evt);
            }
        });
        tDerechaJ2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tDerechaJ2KeyPressed(evt);
            }
        });

        lIzquierdaJ2.setText("Izquierda");

        tIzquierdaJ2.setEditable(false);
        tIzquierdaJ2.setText("65");
        tIzquierdaJ2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tIzquierdaJ2MouseClicked(evt);
            }
        });
        tIzquierdaJ2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tIzquierdaJ2ActionPerformed(evt);
            }
        });
        tIzquierdaJ2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tIzquierdaJ2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jpJugador2Layout = new javax.swing.GroupLayout(jpJugador2);
        jpJugador2.setLayout(jpJugador2Layout);
        jpJugador2Layout.setHorizontalGroup(
            jpJugador2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpJugador2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpJugador2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpJugador2Layout.createSequentialGroup()
                        .addComponent(lArribaJ2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(tArribaJ2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpJugador2Layout.createSequentialGroup()
                        .addComponent(lAbajoJ2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(tAbajoJ2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpJugador2Layout.createSequentialGroup()
                        .addComponent(lDerechaJ2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(tDerechaJ2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpJugador2Layout.createSequentialGroup()
                        .addComponent(lIzquierdaJ2)
                        .addGap(18, 18, 18)
                        .addComponent(tIzquierdaJ2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpJugador2Layout.setVerticalGroup(
            jpJugador2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpJugador2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpJugador2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lArribaJ2)
                    .addComponent(tArribaJ2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jpJugador2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tAbajoJ2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lAbajoJ2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpJugador2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tDerechaJ2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lDerechaJ2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpJugador2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tIzquierdaJ2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lIzquierdaJ2))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jpJugador1.setBorder(javax.swing.BorderFactory.createTitledBorder("Jugador 1"));

        lArribaJ1.setText("Arriba");

        tArribaJ1.setEditable(false);
        tArribaJ1.setText("38");
        tArribaJ1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tArribaJ1MouseClicked(evt);
            }
        });
        tArribaJ1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tArribaJ1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tArribaJ1KeyTyped(evt);
            }
        });

        lAbajoJ1.setText("Abajo");

        tAbajoJ1.setEditable(false);
        tAbajoJ1.setText("40");
        tAbajoJ1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tAbajoJ1MouseClicked(evt);
            }
        });
        tAbajoJ1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tAbajoJ1KeyPressed(evt);
            }
        });

        lDerechaJ1.setText("Derecha");

        tDerechaJ1.setEditable(false);
        tDerechaJ1.setText("39");
        tDerechaJ1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tDerechaJ1MouseClicked(evt);
            }
        });
        tDerechaJ1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tDerechaJ1KeyPressed(evt);
            }
        });

        lIzquierdaJ1.setText("Izquierda");

        tIzquierdaJ1.setEditable(false);
        tIzquierdaJ1.setText("37");
        tIzquierdaJ1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tIzquierdaJ1MouseClicked(evt);
            }
        });
        tIzquierdaJ1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tIzquierdaJ1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jpJugador1Layout = new javax.swing.GroupLayout(jpJugador1);
        jpJugador1.setLayout(jpJugador1Layout);
        jpJugador1Layout.setHorizontalGroup(
            jpJugador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpJugador1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpJugador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpJugador1Layout.createSequentialGroup()
                        .addComponent(lArribaJ1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(tArribaJ1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpJugador1Layout.createSequentialGroup()
                        .addComponent(lAbajoJ1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(tAbajoJ1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpJugador1Layout.createSequentialGroup()
                        .addComponent(lDerechaJ1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(tDerechaJ1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpJugador1Layout.createSequentialGroup()
                        .addComponent(lIzquierdaJ1)
                        .addGap(18, 18, 18)
                        .addComponent(tIzquierdaJ1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpJugador1Layout.setVerticalGroup(
            jpJugador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpJugador1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpJugador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lArribaJ1)
                    .addComponent(tArribaJ1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jpJugador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tAbajoJ1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lAbajoJ1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpJugador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tDerechaJ1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lDerechaJ1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpJugador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tIzquierdaJ1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lIzquierdaJ1))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        lPausa.setText("Pausa");

        tPausa.setEditable(false);
        tPausa.setText("32");
        tPausa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tPausaMouseClicked(evt);
            }
        });
        tPausa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tPausaKeyPressed(evt);
            }
        });

        bCancelar.setText("Cancelar");
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });

        bValoresDefecto.setText("Valores por defecto");
        bValoresDefecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bValoresDefectoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 294, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jpJugador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lPausa))
                    .addComponent(bValoresDefecto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tPausa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(bAceptar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(bCancelar))
                        .addComponent(jpJugador2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 263, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpJugador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpJugador2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tPausa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lPausa))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bCancelar)
                    .addComponent(bAceptar)
                    .addComponent(bValoresDefecto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAceptarActionPerformed

        // Guardamos las teclas configuradas
        frame.cgui.setTecla(ConfiguracionGUI.TECLA_ABAJO_J1, Integer.parseInt(tAbajoJ1.getText()));
        frame.cgui.setTecla(ConfiguracionGUI.TECLA_ARRIBA_J1, Integer.parseInt(tArribaJ1.getText()));
        frame.cgui.setTecla(ConfiguracionGUI.TECLA_DERECHA_J1, Integer.parseInt(tDerechaJ1.getText()));
        frame.cgui.setTecla(ConfiguracionGUI.TECLA_IZQUIERDA_J1, Integer.parseInt(tIzquierdaJ1.getText()));

        frame.cgui.setTecla(ConfiguracionGUI.TECLA_ABAJO_J2, Integer.parseInt(tAbajoJ2.getText()));
        frame.cgui.setTecla(ConfiguracionGUI.TECLA_ARRIBA_J2, Integer.parseInt(tArribaJ2.getText()));
        frame.cgui.setTecla(ConfiguracionGUI.TECLA_DERECHA_J2, Integer.parseInt(tDerechaJ2.getText()));
        frame.cgui.setTecla(ConfiguracionGUI.TECLA_IZQUIERDA_J2, Integer.parseInt(tIzquierdaJ2.getText()));

        frame.cgui.setTecla(ConfiguracionGUI.TECLA_PAUSA, Integer.parseInt(tPausa.getText()));

        dispose();
}//GEN-LAST:event_bAceptarActionPerformed

    private void tArribaJ2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tArribaJ2MouseClicked
        ennegrecerTeclas();
        lArribaJ2.setForeground(Color.red);
}//GEN-LAST:event_tArribaJ2MouseClicked

    private void tArribaJ2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tArribaJ2KeyPressed
        tArribaJ2.setText(String.valueOf(evt.getKeyCode()));
}//GEN-LAST:event_tArribaJ2KeyPressed

    private void tAbajoJ2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tAbajoJ2MouseClicked
        ennegrecerTeclas();
        lAbajoJ2.setForeground(Color.red);
}//GEN-LAST:event_tAbajoJ2MouseClicked

    private void tAbajoJ2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tAbajoJ2KeyPressed
        tAbajoJ2.setText(String.valueOf(evt.getKeyCode()));
}//GEN-LAST:event_tAbajoJ2KeyPressed

    private void tDerechaJ2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tDerechaJ2MouseClicked
        ennegrecerTeclas();
        lDerechaJ2.setForeground(Color.red);
}//GEN-LAST:event_tDerechaJ2MouseClicked

    private void tDerechaJ2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tDerechaJ2KeyPressed
        tDerechaJ2.setText(String.valueOf(evt.getKeyCode()));
}//GEN-LAST:event_tDerechaJ2KeyPressed

    private void tIzquierdaJ2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tIzquierdaJ2MouseClicked
        ennegrecerTeclas();
        lIzquierdaJ2.setForeground(Color.red);
}//GEN-LAST:event_tIzquierdaJ2MouseClicked

    private void tIzquierdaJ2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tIzquierdaJ2ActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_tIzquierdaJ2ActionPerformed

    private void tIzquierdaJ2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tIzquierdaJ2KeyPressed
        tIzquierdaJ2.setText(String.valueOf(evt.getKeyCode()));
}//GEN-LAST:event_tIzquierdaJ2KeyPressed

    private void tArribaJ1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tArribaJ1MouseClicked
        ennegrecerTeclas();
        lArribaJ1.setForeground(Color.red);
}//GEN-LAST:event_tArribaJ1MouseClicked

    private void tArribaJ1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tArribaJ1KeyPressed
        tArribaJ1.setText(String.valueOf(evt.getKeyCode()));
}//GEN-LAST:event_tArribaJ1KeyPressed

    private void tArribaJ1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tArribaJ1KeyTyped

}//GEN-LAST:event_tArribaJ1KeyTyped

    private void tAbajoJ1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tAbajoJ1MouseClicked
        ennegrecerTeclas();
        lAbajoJ1.setForeground(Color.red);
}//GEN-LAST:event_tAbajoJ1MouseClicked

    private void tAbajoJ1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tAbajoJ1KeyPressed
        tAbajoJ1.setText(String.valueOf(evt.getKeyCode()));
}//GEN-LAST:event_tAbajoJ1KeyPressed

    private void tDerechaJ1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tDerechaJ1MouseClicked
        ennegrecerTeclas();
        lDerechaJ1.setForeground(Color.red);
}//GEN-LAST:event_tDerechaJ1MouseClicked

    private void tDerechaJ1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tDerechaJ1KeyPressed
        tDerechaJ1.setText(String.valueOf(evt.getKeyCode()));
}//GEN-LAST:event_tDerechaJ1KeyPressed

    private void tIzquierdaJ1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tIzquierdaJ1MouseClicked
        ennegrecerTeclas();
        lIzquierdaJ1.setForeground(Color.red);
}//GEN-LAST:event_tIzquierdaJ1MouseClicked

    private void tIzquierdaJ1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tIzquierdaJ1KeyPressed
        tIzquierdaJ1.setText(String.valueOf(evt.getKeyCode()));
}//GEN-LAST:event_tIzquierdaJ1KeyPressed

    private void tPausaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tPausaMouseClicked
        ennegrecerTeclas();
        lPausa.setForeground(Color.red);
}//GEN-LAST:event_tPausaMouseClicked

    private void tPausaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tPausaKeyPressed
        tPausa.setText(String.valueOf(evt.getKeyCode()));
}//GEN-LAST:event_tPausaKeyPressed

    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
        dispose();
}//GEN-LAST:event_bCancelarActionPerformed

    private void bValoresDefectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bValoresDefectoActionPerformed
        frame.cgui.setTeclasPorDefecto();
        tArribaJ1.setText("38");
        tAbajoJ1.setText("40");
        tDerechaJ1.setText("39");
        tIzquierdaJ1.setText("37");
        tArribaJ2.setText("87");
        tAbajoJ2.setText("83");
        tDerechaJ2.setText("68");
        tIzquierdaJ2.setText("65");
        tPausa.setText("32");
}//GEN-LAST:event_bValoresDefectoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAceptar;
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bValoresDefecto;
    private javax.swing.JPanel jpJugador1;
    private javax.swing.JPanel jpJugador2;
    private javax.swing.JLabel lAbajoJ1;
    private javax.swing.JLabel lAbajoJ2;
    private javax.swing.JLabel lArribaJ1;
    private javax.swing.JLabel lArribaJ2;
    private javax.swing.JLabel lDerechaJ1;
    private javax.swing.JLabel lDerechaJ2;
    private javax.swing.JLabel lIzquierdaJ1;
    private javax.swing.JLabel lIzquierdaJ2;
    private javax.swing.JLabel lPausa;
    private javax.swing.JTextField tAbajoJ1;
    private javax.swing.JTextField tAbajoJ2;
    private javax.swing.JTextField tArribaJ1;
    private javax.swing.JTextField tArribaJ2;
    private javax.swing.JTextField tDerechaJ1;
    private javax.swing.JTextField tDerechaJ2;
    private javax.swing.JTextField tIzquierdaJ1;
    private javax.swing.JTextField tIzquierdaJ2;
    private javax.swing.JTextField tPausa;
    // End of variables declaration//GEN-END:variables

}
