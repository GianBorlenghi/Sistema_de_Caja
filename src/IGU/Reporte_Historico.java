package IGU;

import Modelos.ReporteDTO;
import Servicios.ServicioProducto;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


public class Reporte_Historico extends javax.swing.JFrame {
    Servicios.ServicioProducto servP = new ServicioProducto();
    public Reporte_Historico() {
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Reporte Histórico de productos");
        llenarTablaMuestra();
        
    }
    
    public void llenarTablaMuestra(){
        ArrayList<ReporteDTO> listaReporte = new ArrayList<>();
        listaReporte = servP.reporte();
        Object[] producto = new Object[7];
        DefaultTableModel model = (DefaultTableModel) tbReporte.getModel();
        
        for(ReporteDTO r : listaReporte){
            producto[0] = r.getNombre_producto();
            producto[1] = r.getTipo();
            producto[2] = r.getStock();
            producto[3] = r.getCant_venta_7_dias();
            producto[4] = r.getCant_venta_15_dias();
            producto[5] = r.getCant_venta_total();
            producto[6] = r.getCant_venta_total_en_pesos();
                    
            model.addRow(producto);          
          
        }
        tbReporte.setModel(model);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbReporte = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 153, 255));

        tbReporte.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tbReporte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOMBRE_PRODUCTO", "TIPO", "STOCK", "CANT_VENTAS_7_DÍAS", "CANT_VENTAS_15_DIAS", "CANT_VENTAS_HISTÓRICO", "VENTA_TOTAL_EN_PESOS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbReporte.setEnabled(false);
        tbReporte.setRowHeight(35);
        jScrollPane1.setViewportView(tbReporte);
        if (tbReporte.getColumnModel().getColumnCount() > 0) {
            tbReporte.getColumnModel().getColumn(0).setResizable(false);
            tbReporte.getColumnModel().getColumn(0).setPreferredWidth(500);
            tbReporte.getColumnModel().getColumn(1).setResizable(false);
            tbReporte.getColumnModel().getColumn(2).setResizable(false);
            tbReporte.getColumnModel().getColumn(3).setResizable(false);
            tbReporte.getColumnModel().getColumn(4).setResizable(false);
            tbReporte.getColumnModel().getColumn(5).setResizable(false);
            tbReporte.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1543, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Reporte_Historico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reporte_Historico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reporte_Historico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reporte_Historico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reporte_Historico().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbReporte;
    // End of variables declaration//GEN-END:variables
}
