package guicomecocos;

import java.util.ListIterator;
import javax.swing.SpinnerNumberModel;
import motor_comecocos.DatosMapa;

public class NuevaPartidaDialog extends javax.swing.JDialog {
    private ComecocosFrame frame;

    /** Creates new form NuevaPartidaDialog */
    public NuevaPartidaDialog(ComecocosFrame cf) {
        super(cf, true);
        frame = cf;
        initComponents();

        this.combobox_mapa.removeAllItems();
        ListIterator<DatosMapa> i = this.frame.cgui.mapas.listIterator();
        while (i.hasNext()) {
            DatosMapa dm = i.next();
            this.combobox_mapa.addItem(dm.nombre);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        boton_aceptar = new javax.swing.JButton();
        boton_cancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        radio_1_jugador = new javax.swing.JRadioButton();
        radio_2_jugador = new javax.swing.JRadioButton();
        spinner_nivel = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        combobox_mapa = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nueva Partida");

        boton_aceptar.setText("Aceptar");
        boton_aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_aceptarActionPerformed(evt);
            }
        });

        boton_cancelar.setText("Cancelar");
        boton_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cancelarActionPerformed(evt);
            }
        });

        jLabel1.setText("Nivel Inicial:");

        buttonGroup1.add(radio_1_jugador);
        radio_1_jugador.setSelected(true);
        radio_1_jugador.setText("1 Jugador");

        buttonGroup1.add(radio_2_jugador);
        radio_2_jugador.setText("2 Jugadores");

        spinner_nivel.setModel(new javax.swing.SpinnerNumberModel(1, 1, 10, 1));

        jLabel2.setText("Mapa:");

        combobox_mapa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No deberias de ver esto", " " }));
        combobox_mapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobox_mapaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(spinner_nivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(boton_aceptar)
                                .addComponent(radio_1_jugador))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(radio_2_jugador)
                                .addComponent(boton_cancelar))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combobox_mapa, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(combobox_mapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinner_nivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radio_1_jugador)
                    .addComponent(radio_2_jugador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boton_aceptar)
                    .addComponent(boton_cancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boton_aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_aceptarActionPerformed
        boolean multijugador = false;
        int mapa = combobox_mapa.getSelectedIndex();
        SpinnerNumberModel spm = (SpinnerNumberModel) this.spinner_nivel.getModel();
        int nivel = spm.getNumber().intValue();
        multijugador = this.radio_2_jugador.isSelected();
        // Lanza una nueva partida dependiendo de la configuracion escogida
        this.frame.nuevaPartida(nivel, multijugador, mapa);
        this.dispose();
    }//GEN-LAST:event_boton_aceptarActionPerformed

    private void boton_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_boton_cancelarActionPerformed

    private void combobox_mapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobox_mapaActionPerformed

    }//GEN-LAST:event_combobox_mapaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_aceptar;
    private javax.swing.JButton boton_cancelar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox combobox_mapa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton radio_1_jugador;
    private javax.swing.JRadioButton radio_2_jugador;
    private javax.swing.JSpinner spinner_nivel;
    // End of variables declaration//GEN-END:variables

}