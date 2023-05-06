/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IGU;

import Modelos.Marca;
import Modelos.Producto;
import Modelos.Tipo;
import Servicios.ServicioMarca;
import Servicios.ServicioProducto;
import Servicios.ServicioTipo;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author giaan
 */
public class AltaProducto extends javax.swing.JFrame implements ActionListener {

    /**
     * Creates new form AltaProducto
     */
    ServicioProducto servP = new ServicioProducto();
    ServicioTipo servT = new ServicioTipo();
    ArrayList<Tipo> listaTipos = servT.listarTipos();
    ServicioMarca servM = new ServicioMarca();
    ArrayList<Marca> listaMarcas = servM.listaMarcas();
    int idSeleccionEdit;
    String nombreProductoEdit;
    String precio;
    String markUp;
    byte[] bytesImg;
    String originalFilename;
    String rutaAbsoluta;
    Path rutaCompleta;
    private ImageIcon imageIco;
    private Icon icon;
    
    public AltaProducto() {
        initComponents();
        TableRowSorter tb = new TableRowSorter();
        
        btnUpload.addActionListener(this);
        llenarComboTipos();
        llenarComboMarcas();
        llenarTablaMuestra();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Productos");
        

    }

    public final void llenarComboTipos() {

        comboTipo.removeAllItems();
        listaTipos.forEach(p -> comboTipo.addItem(p.getTipo()));
    }

    public final void llenarComboMarcas() {

        comboMarca.removeAllItems();
        listaMarcas.forEach(p -> comboMarca.addItem(p.getMarca()));

    }

    public final void llenarTablaMuestra() {

        ArrayList<Producto> listaProductos = servP.listarProductos();
        Object[] producto = new Object[8];
        DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();


        for (Producto p : listaProductos) {

            producto[0] = p.getId_producto();
            producto[1] = p.getNombre_producto();
            producto[2] = p.getPrecio();
            producto[3] = p.getMark_up();
            producto[4] = p.getPrecio_con_iva();
            producto[5] = p.getPrecio_al_publico();
            producto[6] = p.getMarca_nombre();
            producto[7] = p.getTipo_nombre();

            model.addRow(producto);
        }

                tablaProductos.setModel(model);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jPanel1 = new javax.swing.JPanel();
        comboTipo = new javax.swing.JComboBox<>();
        comboMarca = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtProducto = new java.awt.TextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        btnAgregar = new java.awt.Button();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtEditPrecio = new java.awt.TextField();
        jLabel8 = new javax.swing.JLabel();
        txtEditNombre = new java.awt.TextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMarkup = new java.awt.TextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtPrecioConIva = new java.awt.TextField();
        txtPrecioFinal = new java.awt.TextField();
        jLabel14 = new javax.swing.JLabel();
        btnModificar = new java.awt.Button();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnInfoEliminar = new javax.swing.JButton();
        btnUpload = new javax.swing.JToggleButton();
        lblIco = new javax.swing.JLabel();
        lblIco2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(19, 0, 90));

        comboTipo.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        comboTipo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        comboMarca.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        comboMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMarcaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(28, 130, 173));
        jLabel1.setText("Tipo de producto");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(28, 130, 173));
        jLabel3.setText("Marca");

        txtProducto.setBackground(new java.awt.Color(51, 51, 51));
        txtProducto.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtProducto.setForeground(new java.awt.Color(153, 153, 153));
        txtProducto.setName("asdasdsad"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        tablaProductos.setBackground(new java.awt.Color(28, 130, 173));
        tablaProductos.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        tablaProductos.setForeground(new java.awt.Color(51, 51, 51));
        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "PRECIO", "MARKUP", "PRECIO_CON_IVA", "PRECIO_AL_PUBLICO", "MARCA", "TIPO_PROD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaProductos.setRowHeight(25);
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProductosMouseClicked(evt);
            }
        });
        tablaProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaProductosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaProductos);
        if (tablaProductos.getColumnModel().getColumnCount() > 0) {
            tablaProductos.getColumnModel().getColumn(0).setResizable(false);
            tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(1);
            tablaProductos.getColumnModel().getColumn(1).setResizable(false);
            tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(150);
            tablaProductos.getColumnModel().getColumn(2).setResizable(false);
            tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(1);
            tablaProductos.getColumnModel().getColumn(3).setResizable(false);
            tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(1);
            tablaProductos.getColumnModel().getColumn(4).setResizable(false);
            tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(1);
            tablaProductos.getColumnModel().getColumn(5).setResizable(false);
            tablaProductos.getColumnModel().getColumn(5).setPreferredWidth(1);
            tablaProductos.getColumnModel().getColumn(6).setResizable(false);
            tablaProductos.getColumnModel().getColumn(6).setPreferredWidth(1);
            tablaProductos.getColumnModel().getColumn(7).setResizable(false);
            tablaProductos.getColumnModel().getColumn(7).setPreferredWidth(1);
        }

        jLabel6.setBackground(new java.awt.Color(51, 51, 51));
        jLabel6.setFont(new java.awt.Font("Dialog", 2, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Productos cargados");

        btnAgregar.setBackground(new java.awt.Color(3, 201, 136));
        btnAgregar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(0, 0, 0));
        btnAgregar.setLabel("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(28, 130, 173));
        jLabel9.setText("Nombre producto");

        jPanel3.setBackground(new java.awt.Color(19, 0, 90));

        txtEditPrecio.setBackground(new java.awt.Color(51, 51, 51));
        txtEditPrecio.setEditable(false);
        txtEditPrecio.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtEditPrecio.setForeground(new java.awt.Color(153, 153, 153));
        txtEditPrecio.setName("asdasdsad"); // NOI18N
        txtEditPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEditPrecioKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(28, 130, 173));
        jLabel8.setText("Precio sin IVA");

        txtEditNombre.setBackground(new java.awt.Color(51, 51, 51));
        txtEditNombre.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtEditNombre.setForeground(new java.awt.Color(153, 153, 153));
        txtEditNombre.setName("asdasdsad"); // NOI18N

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(28, 130, 173));
        jLabel10.setText("Nombre producto");

        jLabel5.setFont(new java.awt.Font("Dialog", 2, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Modificar Producto");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        txtMarkup.setBackground(new java.awt.Color(51, 51, 51));
        txtMarkup.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtMarkup.setForeground(new java.awt.Color(153, 153, 153));
        txtMarkup.setName("asdasdsad"); // NOI18N
        txtMarkup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMarkupKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMarkupKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(28, 130, 173));
        jLabel12.setText("Mark Up");

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(28, 130, 173));
        jLabel13.setText("Precio con IVA");

        txtPrecioConIva.setBackground(new java.awt.Color(51, 51, 51));
        txtPrecioConIva.setEditable(false);
        txtPrecioConIva.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtPrecioConIva.setForeground(new java.awt.Color(153, 153, 153));
        txtPrecioConIva.setName("asdasdsad"); // NOI18N
        txtPrecioConIva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioConIvaKeyTyped(evt);
            }
        });

        txtPrecioFinal.setBackground(new java.awt.Color(51, 51, 51));
        txtPrecioFinal.setEditable(false);
        txtPrecioFinal.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtPrecioFinal.setForeground(new java.awt.Color(153, 153, 153));
        txtPrecioFinal.setName("asdasdsad"); // NOI18N
        txtPrecioFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioFinalKeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(28, 130, 173));
        jLabel14.setText("Precio final");

        btnModificar.setActionCommand("Modificar");
        btnModificar.setBackground(new java.awt.Color(102, 102, 255));
        btnModificar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setLabel("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMarkup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtEditPrecio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtEditNombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPrecioConIva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPrecioFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel10))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING))))
                .addGap(33, 33, 33))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel8))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jLabel5)
                .addGap(38, 38, 38)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEditNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEditPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPrecioConIva, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMarkup, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPrecioFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Dialog", 2, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Alta de productos");
        jLabel11.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 549, Short.MAX_VALUE)
        );

        btnInfoEliminar.setBackground(new java.awt.Color(255, 0, 0));
        btnInfoEliminar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnInfoEliminar.setForeground(new java.awt.Color(0, 0, 0));
        btnInfoEliminar.setText("Eliminar Producto");
        btnInfoEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInfoEliminarActionPerformed(evt);
            }
        });

        btnUpload.setText("Seleccione imagen");

        lblIco.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblIco2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                        .addGap(98, 98, 98)
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(53, 53, 53)
                                                                .addComponent(jLabel9)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 485, Short.MAX_VALUE))
                                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                            .addComponent(comboTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                            .addComponent(txtProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                            .addComponent(comboMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                                            .addGap(55, 55, 55)
                                                                            .addComponent(jLabel1))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                                            .addGap(91, 91, 91)
                                                                            .addComponent(jLabel3)))
                                                                    .addComponent(btnUpload))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(lblIco, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(67, 67, 67))))
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(154, 154, 154)
                                                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                        .addGap(0, 0, Short.MAX_VALUE)
                                                        .addComponent(jLabel11)
                                                        .addGap(265, 265, 265)))
                                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jLabel6)))
                                        .addGap(37, 37, 37))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 716, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblIco2, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(33, 33, 33))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1328, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnInfoEliminar)
                        .addGap(583, 583, 583)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(411, 411, 411)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(63, 63, 63)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblIco, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20)
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(13, 13, 13)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(comboMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(btnUpload)))
                                .addGap(46, 46, 46)
                                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblIco2, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, Short.MAX_VALUE)))
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnInfoEliminar)
                .addContainerGap())
        );

        jMenu1.setText("Ver");

        jMenuItem1.setText("Reporte histórico");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInfoEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInfoEliminarActionPerformed
        JOptionPane.showMessageDialog(this, "Porfavor, seleccione un registro y presione la tecla 'SUPRIMIR'");
    }//GEN-LAST:event_btnInfoEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();

        System.out.println(this.idSeleccionEdit);
        if (txtEditNombre.getText().trim().length() != 0 && txtEditPrecio.getText().trim().length() != 0) {

            if (txtEditNombre.getText().equals(this.nombreProductoEdit) && txtEditPrecio.getText().equals(this.precio)
                && txtMarkup.getText().equals(this.markUp)) {
                JOptionPane.showMessageDialog(this, "No se detectaron cambios en el producto");
            } else {

                String nombreEditado = txtEditNombre.getText().toUpperCase().trim();
                float precioEditado = Float.parseFloat(txtEditPrecio.getText().trim());
                float markup = Float.parseFloat(txtMarkup.getText().trim());
                double precio_con_iva = precioEditado * 1.21;
                double precio_al_publico = Float.valueOf(txtPrecioFinal.getText());

                servP.editarProducto(this.idSeleccionEdit, precioEditado,precio_con_iva,precio_al_publico,markup, nombreEditado);

                model.setRowCount(0);
                llenarTablaMuestra();
                JOptionPane.showMessageDialog(this, "Producto modificado correctamente.");

            }
        } else {
            JOptionPane.showMessageDialog(this, "Se detectaron campos vacíos.");
        }

    }//GEN-LAST:event_btnModificarActionPerformed

    private void txtPrecioFinalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioFinalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioFinalKeyTyped

    private void txtPrecioConIvaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioConIvaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioConIvaKeyTyped

    private void txtMarkupKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarkupKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarkupKeyTyped

    private void txtMarkupKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarkupKeyReleased

        if(txtMarkup.getText() != "" && txtPrecioConIva.getText() != ""){
            String precio_con_iva = txtPrecioConIva.getText().trim();
            float precio_iva = Float.valueOf(precio_con_iva);
            float markUp = Float.valueOf(txtMarkup.getText());
            float precioFinal = precio_iva * markUp;
            txtPrecioFinal.setText(String.valueOf(precioFinal));
        }
    }//GEN-LAST:event_txtMarkupKeyReleased

    private void txtEditPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEditPrecioKeyTyped
        int key = evt.getKeyChar();

        boolean numeros = key >= 48 && key <= 57;
        boolean punto = key == 46;

        if (!numeros && !punto) {
            evt.consume();
        }

        /*if (txtPrecio.getText().trim().length() == 10) {
            evt.consume();
        }*/
    }//GEN-LAST:event_txtEditPrecioKeyTyped

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        int id_tipo = 0;
        int id_marca = 0;
        String nombre_producto = txtProducto.getText().toUpperCase().trim();

        DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();

        for (Tipo tipo : listaTipos) {
            if (tipo.getTipo().equals(comboTipo.getSelectedItem())) {
                id_tipo = tipo.getId_tipo();
                break;
            }
        }

        for (Marca marca : listaMarcas) {
            if (marca.getMarca().equals(comboMarca.getSelectedItem())) {
                id_marca = marca.getId_marca();
                break;
            }
        }

        if (nombre_producto.trim().length() != 0) {

            boolean p = servP.altaProducto(nombre_producto,id_tipo, id_marca,originalFilename);
            if (!p) {
                JOptionPane.showMessageDialog(this, "El producto ya existe.");
            } else {
                try {
                    model.setRowCount(0);
                    llenarTablaMuestra();
                    txtProducto.setText("");
                    Files.write(rutaCompleta,bytesImg);
                    System.out.println(bytesImg);
                    lblIco.setIcon(null);
                    JOptionPane.showMessageDialog(this, "Producto creado exitosamente");
                } catch (IOException ex) {
                    Logger.getLogger(AltaProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            JOptionPane.showMessageDialog(this, "Hay campos vacíos.");

        }

    }//GEN-LAST:event_btnAgregarActionPerformed

    private void tablaProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaProductosKeyPressed
        int key = evt.getKeyChar();
        int fila = tablaProductos.getSelectedRow();
        if (key == 127) {
            int rta = JOptionPane.showConfirmDialog(this, "Estás seguro de borrar el registro seleccionado?");

            if (rta == 0) {
                servP.bajaProducto(this.idSeleccionEdit);
                DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();
                model.removeRow(fila);
            }
        }
    }//GEN-LAST:event_tablaProductosKeyPressed

    private void tablaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductosMouseClicked

        int fila = tablaProductos.getSelectedRow();
        int columna = tablaProductos.getSelectedColumn();
        String id = String.valueOf(tablaProductos.getValueAt(fila, 0));
        String nombreProducto = String.valueOf(tablaProductos.getValueAt(fila, 1));
        String precio = String.valueOf(tablaProductos.getValueAt(fila, 2));
        String mark_up = String.valueOf(tablaProductos.getValueAt(fila,3));
        String precio_con_iva = String.valueOf(tablaProductos.getValueAt(fila,4));
        String precio_al_publico = String.valueOf(tablaProductos.getValueAt(fila,5));
        String url_imagen = servP.urlImagenProducto(Integer.parseInt(id));

        this.idSeleccionEdit = Integer.parseInt(id);
        this.nombreProductoEdit = nombreProducto;
        this.precio = precio;
        this.markUp = mark_up;
        txtEditNombre.setText(nombreProducto);
        txtEditPrecio.setText(precio);
        txtPrecioConIva.setText(precio_con_iva);
        txtMarkup.setText(mark_up);

        Path directorioImagen = Paths.get("src//assets//images//"+url_imagen);
        rutaAbsoluta = directorioImagen.toFile().getAbsolutePath();
        rsscalelabel.RSScaleLabel.setScaleLabel(lblIco2, rutaAbsoluta.toString());

    }//GEN-LAST:event_tablaProductosMouseClicked

    private void comboMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboMarcaActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        Reporte_Historico rp = new Reporte_Historico();
        rp.setVisible(true);
        rp.setLocationRelativeTo(null);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(AltaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AltaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AltaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AltaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AltaProducto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btnAgregar;
    private javax.swing.JButton btnInfoEliminar;
    private java.awt.Button btnModificar;
    private javax.swing.JToggleButton btnUpload;
    private javax.swing.JComboBox<String> comboMarca;
    private javax.swing.JComboBox<String> comboTipo;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblIco;
    private javax.swing.JLabel lblIco2;
    private javax.swing.JTable tablaProductos;
    private java.awt.TextField txtEditNombre;
    private java.awt.TextField txtEditPrecio;
    private java.awt.TextField txtMarkup;
    private java.awt.TextField txtPrecioConIva;
    private java.awt.TextField txtPrecioFinal;
    private java.awt.TextField txtProducto;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent evt) {
                    if(evt.getSource()==btnUpload){
            JFileChooser file_upload = new JFileChooser();
            int res = file_upload.showOpenDialog(null);
              if(res == JFileChooser.APPROVE_OPTION){
                try {
                    File file = file_upload.getSelectedFile();
                    Path directorioImagenes = Paths.get("src//assets//images");
                    rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
                    bytesImg = Files.readAllBytes(file.toPath());
                    originalFilename = file.getName();
                    rutaCompleta = Paths.get(rutaAbsoluta + "//" + file.getName());
                    lblIco.setIcon(null);
                    rsscalelabel.RSScaleLabel.setScaleLabel(lblIco, file.getPath());
                   
                    this.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(AltaProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
                  

             

              }
        }
        
    }
}
