/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IGU;

import Impresion.Imprimir;
import Modelos.BalanzaConfig;
import Modelos.Producto;
import Modelos.Promocion;
import Modelos.PromocionDTO;
import Servicios.ServicioProducto;
import Servicios.ServicioPromocion;
import Servicios.ServicioVenta;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author giaan
 */
public class Principal extends javax.swing.JFrame {

    ArrayList<Producto> compra = new ArrayList<>();
    ArrayList<Producto> listaProd = new ArrayList<>();
    ArrayList<Producto> sinStock = new ArrayList<>();
    List<Producto> productoConPrecioDistACero = new ArrayList<>();
    ServicioProducto servP = new ServicioProducto();
    ServicioVenta serV = new ServicioVenta();
    ServicioPromocion servPro = new ServicioPromocion();
    DecimalFormat formatoDecimal = new DecimalFormat("#.00");
    ArrayList<Promocion> listaPromocionesXProducto = new ArrayList<>();
    ArrayList<PromocionDTO> listaProducto_promociones = new ArrayList<>();
    int contador = 0;
    int cont = 0;
    int con = 0;

    float subtotal = 0;
    float total = 0;
    String descuento = "";
    String mPa = "";

    private ItemListener itemListener = new ItemListener() {

        @Override
        public void itemStateChanged(ItemEvent e) {
            int i = comboProducto.getSelectedIndex();
            Producto p = productoConPrecioDistACero.get(i);
            // Cargar la imagen desde el JAR
            String rutaImagen = "assets/images/" + p.getUrl_imagen();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(rutaImagen);

            if (inputStream != null) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(inputStream);

                    // Escalar la imagen para que se ajuste al JLabel
                    Image imagenEscalada = bufferedImage.getScaledInstance(lblIco.getWidth(), lblIco.getHeight(), Image.SCALE_SMOOTH);

                    // Establecer la imagen escalada como icono del JLabel
                    ImageIcon imageIcon = new ImageIcon(imagenEscalada);
                    lblIco.setIcon(imageIcon);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                System.err.println("No se pudo encontrar la imagen: " + rutaImagen);
            }

            // Actualizar otros campos si es necesario
            if (cont != 0) {
                txtMarca.setText(p.getMarca_nombre());
                barCode.setText(p.getCodigo_barra());
            }
            cont++;
        }
    };

    public Principal() {
        initComponents();
        JFrame f = new JFrame();
        String mPago[] = {"EFECTIVO", "DEBITO", "CREDITO", "MERCADOPAGO", "MODO", "ANK", "UALA"};
        comboProducto.setVisible(false);
        txtMarca.setText("");
        barCode.setText("");
        barCode.requestFocus();
        txtCantidad.setVisible(false);
        lblCantidad.setVisible(false);
        /*Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int ancho = (int)d.getWidth() / 2;
        int alto = (int)d.getHeight() / 2;
        this.setSize(ancho, alto);*/
        llenarComboProducto();
        productosSinStock();
        setTitle("Principal");
        this.pack();
        AutoCompleteDecorator.decorate(comboProducto);
        menuJubilado.setEnabled(false);
        btnBorrarCompra.setEnabled(false);
        btnImprimir.setEnabled(false);
        verificarPromociones();

        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            private Object Component;

            public void eventDispatched(AWTEvent event) {

                // Como se usa la máscara AWTEvent.KEY_EVENT_MASK nunca va a fallar 
                KeyEvent keyEvent = (KeyEvent) event;

                // es true cuando se ha soltado la tecla
                if (keyEvent.getKeyCode() == KeyEvent.VK_F1 && con == 0) {
                    //con++;
                    con++;
                    System.out.println("Hola");

                    String mP = JOptionPane.showInputDialog(f, "<html><h2>1 - EFECTIVO</h2><h2>2 - DEBITO</h2><h2>3 - CREDITO</h2><h2>4 - MERCADOPAGO</h2><h2>5 - MODO</h2><h2>6 - ANK</h2><h2>7 - UALA</h2></html>", "Hola2", 2);
                    mPa = mPago[Integer.parseInt(mP) - 1];
                    con = 0;
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_F5 && con == 0) {
                    con++;
                    btnImprimir.doClick();
                    con = 0;
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_F6 && con == 0) {
                    con++;
                    btnBorrarCompra.doClick();
                    con = 0;
                }

            }
        }, AWTEvent.KEY_EVENT_MASK);
    }

    public void productosSinStock() {
        sinStock = servP.productosSinStock();
        String s = "";
        if (!sinStock.isEmpty()) {
            for (Producto p : sinStock) {
                s += "\r\nNOMBRE PRODUCTO : " + p.getNombre_producto() + " || FECHA ÚLTIMA COMPRA: " + p.getFecha_factura() + "\r\n";
            }
            s += "\r\nPORFAVOR INGRESE A PRODUCTOS-> ALTA PRODUCTO -> VER -> REPORTE HISTÓRICO";
            JOptionPane.showMessageDialog(this, s, "ALERTA!! PRODUCTOS SIN STOCK", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void verificarPromociones() {
        servPro.verificarPromos();
    }

    public void llenarComboProducto() {
        comboProducto.removeItemListener(itemListener);
        comboProducto.removeAllItems();
        contador++;
        listaProd = servP.listarProductos();
        productoConPrecioDistACero = listaProd.stream()
                .filter(p -> (p.getPrecio_al_publico() > 0 && p.getStock() > 0))
                .collect(Collectors.toList());

        if (productoConPrecioDistACero.isEmpty() && contador == 0) {

            JOptionPane.showMessageDialog(this, "No hay productos con stock.");
        } else {

            comboProducto.addItemListener(itemListener);
            productoConPrecioDistACero.forEach(p -> comboProducto.addItem(p.getNombre_producto() + " - " + p.getId_producto()));

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPanel1 = new javax.swing.JPanel();
        panelPrint = new javax.swing.JPanel();
        btnBorrarCompra = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        printPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaCompra = new javax.swing.JTable();
        txtTot = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        comboProducto = new javax.swing.JComboBox<>();
        txtCantidad = new javax.swing.JSpinner();
        lblCantidad = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        txtMarca = new javax.swing.JTextField();
        lblIco = new javax.swing.JLabel();
        barCode = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        check = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        cerrarCajaMenu = new javax.swing.JMenuItem();
        menuDescuento = new javax.swing.JMenu();
        menuJubilado = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(19, 0, 90));

        panelPrint.setBackground(new java.awt.Color(19, 0, 90));
        panelPrint.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, java.awt.Color.white, new java.awt.Color(204, 204, 204)));

        btnBorrarCompra.setBackground(new java.awt.Color(204, 51, 0));
        btnBorrarCompra.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnBorrarCompra.setForeground(new java.awt.Color(0, 0, 0));
        btnBorrarCompra.setText("BORRAR COMPRA");
        btnBorrarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarCompraActionPerformed(evt);
            }
        });

        btnImprimir.setBackground(new java.awt.Color(3, 201, 136));
        btnImprimir.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnImprimir.setForeground(new java.awt.Color(0, 0, 0));
        btnImprimir.setText("IMPRIMIR TICKET");
        btnImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnImprimirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnImprimirMouseExited(evt);
            }
        });
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        printPanel.setBackground(new java.awt.Color(204, 204, 204));

        tablaCompra.setBackground(new java.awt.Color(150, 150, 150));
        tablaCompra.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        tablaCompra.setForeground(new java.awt.Color(0, 0, 0));
        tablaCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOMBRE", "MARCA", "PRECIO", "CANTIDAD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCompra.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablaCompra.setRowHeight(30);
        tablaCompra.setRowMargin(5);
        tablaCompra.setShowVerticalLines(false);
        tablaCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaCompraKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tablaCompra);
        if (tablaCompra.getColumnModel().getColumnCount() > 0) {
            tablaCompra.getColumnModel().getColumn(0).setResizable(false);
            tablaCompra.getColumnModel().getColumn(0).setPreferredWidth(250);
            tablaCompra.getColumnModel().getColumn(1).setResizable(false);
            tablaCompra.getColumnModel().getColumn(1).setPreferredWidth(150);
            tablaCompra.getColumnModel().getColumn(2).setResizable(false);
            tablaCompra.getColumnModel().getColumn(2).setPreferredWidth(150);
            tablaCompra.getColumnModel().getColumn(3).setResizable(false);
            tablaCompra.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        txtTot.setBackground(new java.awt.Color(51, 51, 51));
        txtTot.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        txtTot.setForeground(new java.awt.Color(204, 0, 0));
        txtTot.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("TOTAL");

        txtSubtotal.setBackground(new java.awt.Color(51, 51, 51));
        txtSubtotal.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        txtSubtotal.setForeground(new java.awt.Color(204, 0, 0));
        txtSubtotal.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("SUBTOTAL");

        javax.swing.GroupLayout printPanelLayout = new javax.swing.GroupLayout(printPanel);
        printPanel.setLayout(printPanelLayout);
        printPanelLayout.setHorizontalGroup(
            printPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, printPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(printPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(40, 40, 40)
                .addGroup(printPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTot, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
            .addGroup(printPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        printPanelLayout.setVerticalGroup(
            printPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, printPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(printPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(printPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTot, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelPrintLayout = new javax.swing.GroupLayout(panelPrint);
        panelPrint.setLayout(panelPrintLayout);
        panelPrintLayout.setHorizontalGroup(
            panelPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrintLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelPrintLayout.createSequentialGroup()
                        .addComponent(btnBorrarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(313, 313, 313)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(printPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelPrintLayout.setVerticalGroup(
            panelPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrintLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(printPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(panelPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBorrarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        jPanel3.setBackground(new java.awt.Color(0, 51, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        comboProducto.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txtCantidad, org.jdesktop.beansbinding.ObjectProperty.create(), comboProducto, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtCantidad.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txtCantidad.setModel(new javax.swing.SpinnerNumberModel(1, 1, 15, 1));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, btnAgregar, org.jdesktop.beansbinding.ObjectProperty.create(), txtCantidad, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        lblCantidad.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblCantidad.setForeground(new java.awt.Color(255, 255, 255));
        lblCantidad.setText("Cantidad");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Seleccionar producto");

        btnAgregar.setBackground(new java.awt.Color(3, 201, 136));
        btnAgregar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(0, 0, 0));
        btnAgregar.setText("Agregar");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, comboProducto, org.jdesktop.beansbinding.ObjectProperty.create(), btnAgregar, org.jdesktop.beansbinding.BeanProperty.create("nextFocusableComponent"));
        bindingGroup.addBinding(binding);

        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        txtMarca.setBackground(new java.awt.Color(51, 51, 51));
        txtMarca.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        txtMarca.setForeground(new java.awt.Color(153, 0, 0));
        txtMarca.setEnabled(false);

        lblIco.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        barCode.setBackground(new java.awt.Color(51, 51, 51));
        barCode.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        barCode.setForeground(new java.awt.Color(153, 0, 0));
        barCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barCodeActionPerformed(evt);
            }
        });

        jButton1.setText(" ");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        check.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkItemStateChanged(evt);
            }
        });
        check.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIco, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(lblCantidad))
                    .addComponent(barCode, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(check)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtCantidad)
                                .addComponent(comboProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(228, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(48, 48, 48)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(check)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(barCode, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCantidad)
                        .addGap(43, 43, 43)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblIco, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(panelPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jMenuBar1.setBackground(new java.awt.Color(153, 153, 153));
        jMenuBar1.setForeground(new java.awt.Color(0, 0, 0));
        jMenuBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenu1.setBackground(new java.awt.Color(204, 204, 204));
        jMenu1.setForeground(new java.awt.Color(0, 0, 0));
        jMenu1.setText("Acciones");

        jMenuItem1.setText("Productos");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Promociones");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setBackground(new java.awt.Color(204, 204, 204));
        jMenu2.setForeground(new java.awt.Color(0, 0, 0));
        jMenu2.setText("Caja");

        cerrarCajaMenu.setBackground(new java.awt.Color(204, 204, 204));
        cerrarCajaMenu.setForeground(new java.awt.Color(0, 0, 0));
        cerrarCajaMenu.setText("Cerrar caja");
        cerrarCajaMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarCajaMenuActionPerformed(evt);
            }
        });
        jMenu2.add(cerrarCajaMenu);

        jMenuBar1.add(jMenu2);

        menuDescuento.setBackground(new java.awt.Color(204, 204, 204));
        menuDescuento.setForeground(new java.awt.Color(0, 0, 0));
        menuDescuento.setText("Descuentos");

        menuJubilado.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        menuJubilado.setText("Jubilados");
        menuJubilado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuJubiladoActionPerformed(evt);
            }
        });
        menuDescuento.add(menuJubilado);

        jMenuBar1.add(menuDescuento);

        jMenu3.setBackground(new java.awt.Color(0, 0, 0));
        jMenu3.setForeground(new java.awt.Color(0, 0, 0));
        jMenu3.setText("Facturación");

        jMenuItem4.setText("Pedido de compra");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem5.setForeground(new java.awt.Color(0, 0, 0));
        jMenuItem5.setText("Buscar Factura");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuBar1.add(jMenu3);

        jMenu4.setForeground(new java.awt.Color(0, 0, 0));
        jMenu4.setText("Finanzas");

        jMenuItem6.setText("Ventas totales");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        AltaProducto altP = new AltaProducto();
        altP.setVisible(true);
        altP.setLocationRelativeTo(null);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed

        DefaultTableModel model = (DefaultTableModel) tablaCompra.getModel();
        compra.removeIf(p -> p == null);

        if (compra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lista de productos vacía. \nPor favor, ingrese productos a la compra.");
            return;
        }

        if (mPa == null || mPa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un método de pago.");
            return;
        }

        try {
            ArrayList<PromocionDTO> listaPromosDTO = new ArrayList<>();

            for (Producto p : compra) {
                Promocion promo = servPro.promocionXProd(p.getId_producto());
                if (promo != null) {
                    PromocionDTO pr = calcularDescuento(p, promo);
                    listaPromosDTO.add(pr);
                }
            }

            int nTicker = serV.guardarVenta(compra, this.subtotal, mPa);
            Imprimir im = new Imprimir();
            ByteArrayOutputStream documentoBytes = im.crearTicketVenta(compra, subtotal, nTicker, descuento, mPa, listaPromosDTO, this.total);
            im.imprimir(documentoBytes);

            compra.forEach(p -> servP.actualizarStock(p.getId_producto(), "baja"));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.toString());
        } finally {
            // Limpieza y restablecimiento de la interfaz
            btnImprimir.setEnabled(false);
            tablaCompra.setEnabled(false);
            this.subtotal = 0;
            this.total = 0;
            menuJubilado.setEnabled(false);
            txtTot.setText("0");
            txtSubtotal.setText("0");
            model.setRowCount(0);
            compra.clear();
            btnAgregar.setEnabled(true);
            llenarComboProducto();
        }
    }

// Método extraído para el cálculo de promociones y descuentos
    private PromocionDTO calcularDescuento(Producto p, Promocion promo) {
        PromocionDTO pr = new PromocionDTO();
        pr.setId_producto(p.getId_producto());
        pr.setDescuento(promo.getDescuento());
        pr.setNombre_promocion(promo.getNombre_promocion());
        pr.setCant_minima(promo.getCantidad_minima());
        pr.setNombre_producto(p.getNombre_producto());

        int cantidadDescontada = (int) (p.getCant() - (p.getCant() % promo.getCantidad_minima()));
        float descuento = p.getPrecio_al_publico() * (promo.getDescuento() / 100.0f);
        float descuento_producto = descuento * cantidadDescontada;

        pr.setDescuento_producto(descuento_producto);
        return pr;


    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnBorrarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarCompraActionPerformed
        DefaultTableModel model = (DefaultTableModel) tablaCompra.getModel();
        if (compra.size() > 0) {
            int opc = JOptionPane.showConfirmDialog(this, "¿ Está seguro de borrar toda la compra ?");

            if (opc == 0) {

                compra.removeAll(compra);
                this.subtotal = 0;
                txtSubtotal.setText("$ 0");
                this.total = 0;
                txtTot.setText("$ 0");
                model.setRowCount(0);
                btnBorrarCompra.setEnabled(false);
                btnImprimir.setEnabled(false);
                tablaCompra.setEnabled(true);
                menuJubilado.setEnabled(false);
                btnAgregar.setEnabled(true);

            }
        } else {
            JOptionPane.showMessageDialog(this, "Lista de productos vacía. \nPorfavor ingrese productos a la compra.");

        }
    }//GEN-LAST:event_btnBorrarCompraActionPerformed

    private void tablaCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaCompraKeyPressed

        int key = evt.getKeyChar();
        int fila = tablaCompra.getSelectedRow();

        if (key == 127 && compra.size() > 0) {

            DefaultTableModel model = (DefaultTableModel) tablaCompra.getModel();
            String precio = String.valueOf(tablaCompra.getValueAt(fila, 2));
            String cant = String.valueOf(tablaCompra.getValueAt(fila, 3));

            model.removeRow(fila);
            compra.remove(fila);
            this.subtotal = this.subtotal - (Float.parseFloat(precio) * Integer.parseInt(cant));
            String p = formatoDecimal.format(this.subtotal);
            txtTot.setText("$ " + p);

        }

    }//GEN-LAST:event_tablaCompraKeyPressed

    private void cerrarCajaMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarCajaMenuActionPerformed

        try {
            VistaCierre vistaCierre = new VistaCierre();
            vistaCierre.setVisible(true);
            vistaCierre.setLocationRelativeTo(null);
            Imprimir im = new Imprimir();
            float total_del_dia = serV.cerrarCaja();
            if (total_del_dia == 0) {
                JOptionPane.showMessageDialog(this, "Aun no hay ventas");
            } else {
                try {
                    ByteArrayOutputStream bytes = im.imprimirCierreCaja(total_del_dia);
                    im.imprimir(bytes);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, ex.toString());
                }

            }
        } catch (ParseException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_cerrarCajaMenuActionPerformed


    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        int id = 0;
        BalanzaConfig balanzaConfig = new BalanzaConfig("25", 5, 6);

        // int cant = (int) txtCantidad.getValue();
        Number valor = (Number) txtCantidad.getValue();
        BigDecimal bigDecimalValue = BigDecimal.valueOf(valor.floatValue());
        BigDecimal cantCon3Decimales = bigDecimalValue.setScale(3, RoundingMode.HALF_UP);  // Redondeo a 3 decimales
        float cant = cantCon3Decimales.floatValue();
        Promocion pr = new Promocion();
        pr = servPro.promocionXProd(id);
        Object[] objec = new Object[4];
        DefaultTableModel model = (DefaultTableModel) tablaCompra.getModel();
        tablaCompra.setRowHeight(35);
        if (check.isSelected()) {
            String seleccion = comboProducto.getSelectedItem().toString();
            String[] arrayString = seleccion.split("-");
            id = Integer.parseInt(arrayString[1].trim());

            for (Producto prod : productoConPrecioDistACero) {
                if (prod.getId_producto() == id) {

                    if (!compra.contains(prod)) {
                        if (cant <= prod.getStock()) {
                            prod.setCant(cant);
                            compra.add(prod);
                            pr = servPro.promocionXProd(id);
                            if (pr == null || (cant % pr.getCantidad_minima() != 0)) {
                                crearFilaProducto(prod, cant, model);

                                formatoDecimal.format(prod.getPrecio_al_publico());
                                this.subtotal += prod.getPrecio_al_publico() * cant;
                                String p = formatoDecimal.format(this.subtotal);
                                txtSubtotal.setText("$ " + p);
                                this.total += prod.getPrecio_al_publico();
                                String t = formatoDecimal.format(this.total);
                                txtTot.setText("$ " + t);
                                txtCantidad.setValue(1);
                                break;
                            } else if (pr != null && (cant % pr.getCantidad_minima() == 0)) {
                                crearFilaProducto(prod, cant, model);
                                objec[0] = prod.getCodigo_barra();
                                objec[1] = "-" + pr.getDescuento() + "% ";
                                objec[2] = pr.getNombre_promocion();
                                objec[3] = "";

                                for (int i = 0; i < compra.size(); i++) {
                                    String valor3 = (String) model.getValueAt(i, 0);
                                    System.out.println(valor);

                                    if (valor3 == prod.getCodigo_barra()) {
                                        System.out.println("Hay 1 ");
                                        break;
                                    } else {
                                        System.out.println("No hay 1");
                                        model.addRow(objec);
                                        compra.add(null);
                                        break;
                                    }
                                }

                                formatoDecimal.format(prod.getPrecio_al_publico());
                                this.subtotal += prod.getPrecio_al_publico() * cant;
                                this.total += ((prod.getPrecio_al_publico() - (prod.getPrecio_al_publico() * pr.getDescuento() / 100))) * cant;

                                String p = formatoDecimal.format(this.subtotal);
                                String t = formatoDecimal.format(this.total);

                                txtSubtotal.setText("$ " + p);
                                txtTot.setText("$ " + t);
                                txtCantidad.setValue(1);

                                break;
                            }

                        } else {
                            JOptionPane.showMessageDialog(this, "Solo hay " + prod.getStock() + " del producto " + prod.getNombre_producto() + " en stock!");
                            break;
                        }
                    } else {
                        //int q = (int) model.getValueAt(compra.indexOf(prod), 3);
                        Number valor2 = (Number) model.getValueAt(compra.indexOf(prod), 3);
                        float q = valor2.floatValue();
                        q += cant;
                        pr = servPro.promocionXProd(id);
                        if (q <= prod.getStock() && pr != null && (q % pr.getCantidad_minima() != 0)) {
                            prod.setCant(q);
                            formatoDecimal.format(prod.getPrecio_al_publico());
                            this.subtotal += prod.getPrecio_al_publico() * cant;
                            String p = formatoDecimal.format(this.subtotal);
                            txtSubtotal.setText("$ " + p);
                            txtCantidad.setValue(1);
                            this.total += prod.getPrecio_al_publico();
                            String t = formatoDecimal.format(this.total);
                            txtTot.setText("$ " + t);
                            model.setValueAt(q, compra.indexOf(prod), 3);
                        } //agregado
                        else if (q <= prod.getStock() && pr != null && (q % pr.getCantidad_minima() == 0)) {
                            boolean existe = false;

                            objec[0] = prod.getCodigo_barra();
                            objec[1] = "-" + pr.getDescuento() + "% ";
                            objec[2] = pr.getNombre_promocion();
                            objec[3] = "";
                            model.addRow(objec);
                            compra.add(null);
                            prod.setCant(q);
                            formatoDecimal.format(prod.getPrecio_al_publico());
                            this.subtotal += prod.getPrecio_al_publico() * cant;
                            String p = formatoDecimal.format(this.subtotal);
                            txtSubtotal.setText("$ " + p);
                            if (cant == pr.getCantidad_minima()) {
                                this.total += prod.getPrecio_al_publico() * cant;
                                this.total -= (prod.getPrecio_al_publico() * pr.getDescuento() / 100) * pr.getCantidad_minima();

                            } else {
                                this.total += prod.getPrecio_al_publico();
                                this.total -= (prod.getPrecio_al_publico() * pr.getDescuento() / 100) * pr.getCantidad_minima();

                            }

                            String t = formatoDecimal.format(this.total);
                            txtTot.setText("$ " + t);
                            model.setValueAt(q, compra.indexOf(prod), 3);
                        }//agregado
                        else if (pr == null) {
                            prod.setCant(q);
                            formatoDecimal.format(prod.getPrecio_al_publico());
                            this.subtotal += prod.getPrecio_al_publico() * cant;
                            String p = formatoDecimal.format(this.subtotal);
                            txtSubtotal.setText("$ " + p);
                            txtCantidad.setValue(1);
                            this.total += prod.getPrecio_al_publico();
                            String t = formatoDecimal.format(this.total);
                            txtTot.setText("$ " + t);
                            model.setValueAt(q, compra.indexOf(prod), 3);
                        } else {
                            JOptionPane.showMessageDialog(this, "Solo hay " + prod.getStock() + " del producto " + prod.getNombre_producto() + " en stock!");
                        }

                    }
                }

            }
        } else {
            for (Producto prod : productoConPrecioDistACero) {
                id = prod.getId_producto();
                if (prod.getCodigo_barra().equals(barCode.getText())) {

                    if (!compra.contains(prod)) {
                        if (cant <= prod.getStock()) {
                            prod.setCant(cant);
                            compra.add(prod);
                            pr = servPro.promocionXProd(id);

                            if (pr == null || (cant % pr.getCantidad_minima() != 0)) {
                                /* objec[0] = prod.getNombre_producto();
                                objec[1] = prod.getMarca_nombre();
                                objec[2] = prod.getPrecio_al_publico();
                                objec[3] = cant;*/
                                crearFilaProducto(prod, cant, model);
                                // model.addRow(objec);
                                formatoDecimal.format(prod.getPrecio_al_publico());
                                this.subtotal += prod.getPrecio_al_publico() * cant;
                                String p = formatoDecimal.format(this.subtotal);
                                txtSubtotal.setText("$ " + p);
                                this.total += prod.getPrecio_al_publico();
                                String t = formatoDecimal.format(this.total);
                                txtTot.setText("$ " + t);
                                txtCantidad.setValue(1);
                                break;
                            } else if (pr != null && (cant % pr.getCantidad_minima() == 0)) {
                                /*objec[0] = prod.getNombre_producto();
                                objec[1] = prod.getMarca_nombre();
                                objec[2] = prod.getPrecio_al_publico();
                                objec[3] = cant;
                                model.addRow(objec);*/
                                crearFilaProducto(prod, cant, model);

                                objec[0] = prod.getCodigo_barra();
                                objec[1] = "-" + pr.getDescuento() + "% ";
                                objec[2] = pr.getNombre_promocion();
                                objec[3] = "";

                                model.addRow(objec);
                                compra.add(null);

                                formatoDecimal.format(prod.getPrecio_al_publico());
                                this.subtotal += prod.getPrecio_al_publico() * cant;
                                this.total += ((prod.getPrecio_al_publico() - (prod.getPrecio_al_publico() * pr.getDescuento() / 100))) * cant;
                                System.out.println(total);
                                String p = formatoDecimal.format(this.subtotal);
                                String t = formatoDecimal.format(this.total);
                                txtSubtotal.setText("$ " + p);
                                txtTot.setText("$ " + t);
                                txtCantidad.setValue(1);

                                break;
                            }

                        } else {
                            JOptionPane.showMessageDialog(this, "Solo hay " + prod.getStock() + " del producto " + prod.getNombre_producto() + " en stock!");
                            break;
                        }
                    } else {
                        int q = (int) model.getValueAt(compra.indexOf(prod), 3);
                        q += cant;
                        pr = servPro.promocionXProd(id);

                        if (q <= prod.getStock() && pr != null && (q % pr.getCantidad_minima() != 0)) {
                            prod.setCant(q);
                            formatoDecimal.format(prod.getPrecio_al_publico());
                            this.subtotal += prod.getPrecio_al_publico() * cant;
                            String p = formatoDecimal.format(this.subtotal);
                            txtSubtotal.setText("$ " + p);
                            txtCantidad.setValue(1);
                            this.total += prod.getPrecio_al_publico();
                            String t = formatoDecimal.format(this.total);
                            txtTot.setText("$ " + t);
                            model.setValueAt(q, compra.indexOf(prod), 3);
                        } //agregado
                        else if (q <= prod.getStock() && pr != null && (q % pr.getCantidad_minima() == 0)) {
                            objec[0] = prod.getCodigo_barra();
                            objec[1] = "-" + pr.getDescuento() + "% ";
                            objec[2] = pr.getNombre_promocion();
                            objec[3] = "";
                            model.addRow(objec);
                            compra.add(null);

                            prod.setCant(q);
                            formatoDecimal.format(prod.getPrecio_al_publico());
                            this.subtotal += prod.getPrecio_al_publico() * cant;
                            String p = formatoDecimal.format(this.subtotal);
                            txtSubtotal.setText("$ " + p);

                            if (cant == pr.getCantidad_minima()) {
                                this.total += prod.getPrecio_al_publico() * cant;
                                this.total -= (prod.getPrecio_al_publico() * pr.getDescuento() / 100) * pr.getCantidad_minima();
                            } else {
                                this.total += prod.getPrecio_al_publico();
                                this.total -= (prod.getPrecio_al_publico() * pr.getDescuento() / 100) * pr.getCantidad_minima();
                            }

                            String t = formatoDecimal.format(this.total);
                            txtTot.setText("$ " + t);
                            model.setValueAt(q, compra.indexOf(prod), 3);
                        } else if (pr == null) {
                            prod.setCant(q);
                            formatoDecimal.format(prod.getPrecio_al_publico());
                            this.subtotal += prod.getPrecio_al_publico() * cant;
                            String p = formatoDecimal.format(this.subtotal);
                            txtSubtotal.setText("$ " + p);
                            txtCantidad.setValue(1);
                            this.total += prod.getPrecio_al_publico();
                            String t = formatoDecimal.format(this.total);
                            txtTot.setText("$ " + t);
                            model.setValueAt(q, compra.indexOf(prod), 3);
                        } else {
                            JOptionPane.showMessageDialog(this, "Solo hay " + prod.getStock() + " del producto " + prod.getNombre_producto() + " en stock!");
                        }

                    }
                } else if (barCode.getText().startsWith(balanzaConfig.getPrefijo())) {
                    String codigoProducto = balanzaConfig.extraerCodigoProducto(barCode.getText());  // Extrae el código del producto
                    float peso = balanzaConfig.extraerPeso(barCode.getText());  // Extrae el peso en kilogramos
                    if (prod.getId_producto() == Integer.parseInt(codigoProducto)) {
                        prod.setCant(peso);
                        System.out.println(barCode.getText());
                        crearFilaProducto(prod, cant, model);
                        System.out.println(prod.getPrecio_al_publico());
                        compra.add(prod);
                        // model.addRow(objec);
                        formatoDecimal.format(prod.getPrecio_al_publico());
                        this.subtotal += prod.getPrecio_al_publico() * peso;
                        String p = formatoDecimal.format(this.subtotal);
                        txtSubtotal.setText("$ " + p);
                        this.total += prod.getPrecio_al_publico()*peso;
                        String t = formatoDecimal.format(this.total);
                        txtTot.setText("$ " + t);
                        txtCantidad.setValue(1);
                    }
                }

            }

        }

        habilitarBotonesPostCompra();
        resetearCamposEntrada();
    }//GEN-LAST:event_btnAgregarActionPerformed
    private void habilitarBotonesPostCompra() {
        btnBorrarCompra.setEnabled(true);
        btnImprimir.setEnabled(true);
        menuJubilado.setEnabled(true);
    }

    private void resetearCamposEntrada() {
        txtCantidad.setValue(1);
        barCode.setText("");
        barCode.requestFocus();
    }

    public void crearFilaProducto(Producto prod, float cant, DefaultTableModel model) {
        Object[] objec = new Object[4];
        objec[0] = prod.getNombre_producto();
        objec[1] = prod.getMarca_nombre();
        objec[2] = prod.getPrecio_al_publico();
        objec[3] = cant;
        model.addRow(objec);
    }


    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped

    }//GEN-LAST:event_txtCantidadKeyTyped

    private void menuJubiladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuJubiladoActionPerformed

        if (this.subtotal > 0) {
            this.subtotal = (float) (this.subtotal - (this.subtotal * 0.1));
            String p = formatoDecimal.format(this.subtotal);
            txtSubtotal.setText("$ " + p);
            DefaultTableModel model = (DefaultTableModel) tablaCompra.getModel();
            Object[] o = new Object[1];
            o[0] = "Jubilado -10%";
            model.addRow(o);
            menuJubilado.setEnabled(false);
            this.descuento = "Jubilado -10%";
        }

    }//GEN-LAST:event_menuJubiladoActionPerformed

    private void btnImprimirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseEntered

        if (btnImprimir.isEnabled()) {
            btnImprimir.setBackground(new java.awt.Color(120, 201, 125));
            //[3,201,136]
        }
    }//GEN-LAST:event_btnImprimirMouseEntered

    private void btnImprimirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseExited
        if (btnImprimir.isEnabled()) {
            btnImprimir.setBackground(new java.awt.Color(3, 201, 136));

        }

            }//GEN-LAST:event_btnImprimirMouseExited

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        Alta_Pedido_de_compra altaP = new Alta_Pedido_de_compra(comboProducto);
        altaP.setVisible(true);
        altaP.setLocationRelativeTo(null);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        BuscarFactura buscarF = new BuscarFactura();
        buscarF.setVisible(true);
        buscarF.setLocationRelativeTo(null);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        String t = serV.ventaTotal();
        JOptionPane.showMessageDialog(this, t, "VENTA TOTAL EN $", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        comboProducto.removeItemListener(itemListener);
        comboProducto.removeAllItems();
        llenarComboProducto();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void checkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkMouseClicked

    }//GEN-LAST:event_checkMouseClicked

    private void checkItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkItemStateChanged
        if (!check.isSelected()) {
            comboProducto.setVisible(false);
            txtMarca.setText(" ");
            lblCantidad.setVisible(false);
            txtCantidad.setVisible(false);
            //jButton1.setVisible(false);

        } else {
            txtCantidad.setVisible(true);
            lblCantidad.setVisible(true);
            comboProducto.setVisible(true);

            //jButton1.setVisible(true);

        }    }//GEN-LAST:event_checkItemStateChanged

    private void barCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barCodeActionPerformed
        for (Producto p : productoConPrecioDistACero) {
            if (p.getCodigo_barra().equals(barCode.getText())) {
                Path directorioImagen = Paths.get("src//assets//images//" + p.getUrl_imagen());
                String rutaAbsoluta = directorioImagen.toFile().getAbsolutePath();
                rsscalelabel.RSScaleLabel.setScaleLabel(lblIco, rutaAbsoluta.toString());
            }
        }

        btnAgregar.doClick();    }//GEN-LAST:event_barCodeActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        /*int[] id = new int[compra.size()];
        int i = 0;
        for (Producto p : compra) {

            id[i] = p.getId_producto();
            i++;

        }

        listaPromocionesXProducto = servPro.promocionXProd(id);    }//GEN-LAST:event_jMenuItem2ActionPerformed
*/
    }

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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField barCode;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBorrarCompra;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JMenuItem cerrarCajaMenu;
    private javax.swing.JCheckBox check;
    private javax.swing.JComboBox<String> comboProducto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblIco;
    private javax.swing.JMenu menuDescuento;
    private javax.swing.JMenuItem menuJubilado;
    private javax.swing.JPanel panelPrint;
    private javax.swing.JPanel printPanel;
    private javax.swing.JTable tablaCompra;
    private javax.swing.JSpinner txtCantidad;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTot;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
