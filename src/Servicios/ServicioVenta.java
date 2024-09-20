package Servicios;

import ConexionBD.Conexion;
import Modelos.Producto;
import Modelos.Promocion;
import Modelos.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioVenta {

    Connection con = null;
    Conexion cone = new Conexion();
    ResultSet rs = null;
    PreparedStatement pst = null;
    DecimalFormat formatoDecimal = new DecimalFormat("#.00");
    ServicioPromocion servP = new ServicioPromocion();

    float totalDelDia;

    public float cerrarCaja() {
        int gdia = LocalDateTime.now().getDayOfMonth();
        int gmes = LocalDateTime.now().getMonthValue();
        int ganio = LocalDateTime.now().getYear();
        try {
            con = cone.conect();
            pst = con.prepareStatement("select SUM(total) as total_del_dia from ventas where YEAR(fecha_venta) = '" + ganio + "' && MONTH(fecha_venta) = '" + gmes + "' && DAY(fecha_venta) = '" + gdia + "' ");
            rs = pst.executeQuery();

            while (rs.next()) {
                totalDelDia = rs.getFloat("total_del_dia");
                formatoDecimal.format(totalDelDia);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        return totalDelDia;
    }

    public int guardarVenta(ArrayList<Producto> listaCompra, float total, String pago) {
        ArrayList<Promocion> listaPromos = new ArrayList<>();
        
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        int idv = 0;
        String date = dateFormat.format(new Date());
        System.out.println(date);
        try {
            con = cone.conect();
            pst = con.prepareStatement("INSERT INTO ventas (id_venta,fecha_venta,total,forma_de_pago) VALUES (NULL, '" + date + "' , " + total + ", '" + pago + "')");
            pst.executeUpdate();

            pst.clearBatch();
            pst = con.prepareStatement("SELECT MAX(id_venta) as id_v from ventas");
            rs = pst.executeQuery();
            while (rs.next()) {
                idv = rs.getInt("id_v");
            }
            rs = null;

            pst.clearBatch();
            for (Producto p : listaCompra) {
                
                pst = con.prepareStatement("INSERT INTO venta_x_productos (id_ventaxpr,cantidad,precio_total,id_producto,id_venta) VALUES (NULL, " + p.getCant() + " ," + p.getPrecio_al_publico() * p.getCant() + ", " + p.getId_producto() + ", (SELECT MAX(id_venta) FROM ventas))");
                pst.executeUpdate();

            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e.toString());

        }
        return idv;

    }

    public ArrayList<Venta> listarVentas() throws ParseException {

        ArrayList<Venta> listaVentas = new ArrayList<>();

        int gdia = LocalDateTime.now().getDayOfMonth();
        int gmes = LocalDateTime.now().getMonthValue();
        int ganio = LocalDateTime.now().getYear();
        try {
            con = cone.conect();
            pst = con.prepareStatement("select * from ventas where YEAR(fecha_venta) = '" + ganio + "' && MONTH(fecha_venta) = '" + gmes + "' && DAY(fecha_venta) = '" + gdia + "' ");
            rs = pst.executeQuery();

            while (rs.next()) {
                Venta v = new Venta();
                v.setId_venta(Integer.parseInt(rs.getString("id_venta")));
                String fecha = String.valueOf(rs.getString("fecha_venta"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                LocalDateTime local = LocalDateTime.parse(fecha, formatter);
                v.setFecha(local);
                v.setTotal(Float.parseFloat(rs.getString("total")));
                v.setMetodo_pago(rs.getString("forma_de_pago"));

                listaVentas.add(v);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        return listaVentas;

    }

    public ArrayList<Producto> detalleVenta(int id_venta) {
        ArrayList<Producto> listaProductoXCompra = new ArrayList<>();
        try {
            con = cone.conect();
            pst = con.prepareStatement("SELECT nombre_producto,cantidad,precio from venta_x_productos inner join productos on productos.id_producto = venta_x_productos.id_producto where id_venta = " + id_venta);
            rs = pst.executeQuery();

            while (rs.next()) {

                Producto p = new Producto();
                p.setNombre_producto(rs.getString("nombre_producto"));
                p.setCant(rs.getInt("cantidad"));
                p.setPrecio(rs.getFloat("precio"));
                listaProductoXCompra.add(p);

            }
        } catch (SQLException e) {

        }

        return listaProductoXCompra;
    }

    public String ventaTotal() {
        try {
            con = cone.conect();
            pst = con.prepareStatement("SELECT FORMAT(sum(total),2) as total FROM ventas");
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicioVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
