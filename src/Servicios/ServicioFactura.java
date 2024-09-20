package Servicios;

import ConexionBD.Conexion;
import Modelos.Facturas;
import Modelos.Producto;
import Modelos.Producto_factura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioFactura {

    Connection con = null;
    Conexion cone = new Conexion();
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void altaFactura(String referencia, float total, String fecha_factura, int id_proveedor,
            ArrayList<Producto_factura> prod_factura) {
        try {
            con = cone.conect();
            pst = con.prepareStatement("INSERT INTO facturas (referencia,total,fecha_factura,id_proveedor) VALUES ('" + referencia + "','" + total + "','" + fecha_factura + "','" + id_proveedor + "')", Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();

            int id_factura = 0;
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id_factura = rs.getInt(1);
            }
            for (Producto_factura p : prod_factura) {
                con = cone.conect();
                pst = con.prepareStatement("INSERT INTO productos_x_factura (cantidad,precio_total,id_producto,id_factura) VALUES ('" + p.getCantidad() + "','" + p.getPrecio_unitario() + "','" + p.getId_producto() + "','" + id_factura + "')");
                pst.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicioFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Facturas> busquedaDeFactura(String fecha_factura,String producto,int cont) throws ParseException {
        try {
            ArrayList<Facturas> facturas = new ArrayList<>();
            con = cone.conect();
            if(cont != 0){
            pst = con.prepareStatement("SELECT f.id_proveedor,f.id_factura,f.referencia,f.fecha_factura,f.total FROM facturas as f inner join productos_x_factura as pxf on pxf.id_factura = f.id_factura inner join productos as p on p.id_producto = pxf.id_producto WHERE f.fecha_factura "+fecha_factura+" AND p.nombre_producto LIKE '"+producto+"%' GROUP BY f.id_factura");
            rs = pst.executeQuery();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
           
            while (rs.next()) {
                
                Facturas fac = new Facturas();
                fac.setId_factura(Integer.parseInt(rs.getString("id_factura")));
                fac.setReferencia(Integer.parseInt(rs.getString("referencia")));
                fac.setFecha_factura(formatter.parse(rs.getString("fecha_factura")));
                fac.setId_proveedor(Integer.parseInt(rs.getString("id_proveedor")));
                fac.setTotal(Double.parseDouble(rs.getString("total")));
                facturas.add(fac);
            }

        return facturas;
            }else{
            pst = con.prepareStatement("SELECT f.id_proveedor,f.id_factura,f.referencia,f.fecha_factura,f.total FROM facturas as f INNER JOIN productos_x_factura as pxf on pxf.id_factura = f.id_factura INNER JOIN productos as p on p.id_producto = pxf.id_producto GROUP BY f.id_factura");
            rs = pst.executeQuery();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
           
            while (rs.next()) {
                
                Facturas fac = new Facturas();
                fac.setId_factura(Integer.parseInt(rs.getString("id_factura")));
                fac.setReferencia(Integer.parseInt(rs.getString("referencia")));
                fac.setFecha_factura(formatter.parse(rs.getString("fecha_factura")));
                fac.setId_proveedor(Integer.parseInt(rs.getString("id_proveedor")));
                fac.setTotal(Double.parseDouble(rs.getString("total")));
                facturas.add(fac);
            
            
            }
            return facturas;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicioFactura.class.getName()).log(Level.SEVERE, null, ex);
        
        }
            return null;
    }
    
    public ArrayList<Producto_factura> lista_producto_x_factura(int id_factura) throws ParseException{
        try {
            ArrayList<Producto_factura> listaProductos_factura= new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            con = cone.conect();
            pst = con.prepareStatement("SELECT productos.nombre_producto, referencia,cantidad,precio_total,fecha_factura,total,nombre_proveedor FROM productos_x_factura  inner join productos on productos.id_producto = productos_x_factura.id_producto inner join facturas on facturas.id_factura = productos_x_factura.id_factura inner join proveedores on proveedores.id_proveedor = facturas.id_proveedor WHERE facturas.id_factura = "+id_factura);
            rs = pst.executeQuery();
            while(rs.next()){
                Producto_factura pdo = new Producto_factura();
                pdo.setCantidad(Integer.parseInt(rs.getString("cantidad")));
                pdo.setNombre_producto(rs.getString("nombre_producto"));
                pdo.setPrecio_unitario(Double.parseDouble(rs.getString("precio_total")));
                pdo.setFecha_factura(formatter.parse(rs.getString("fecha_factura")));
                pdo.setNombre_proveedor(rs.getString("nombre_proveedor"));
                pdo.setReferencia(Integer.parseInt(rs.getString("referencia")));
                listaProductos_factura.add(pdo);
            }
            return listaProductos_factura;
        } catch (SQLException ex) {
            Logger.getLogger(ServicioFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public float precio_anterior(int id){
        float precio_anterior = 0;
        try {
            con = cone.conect();
            pst = con.prepareStatement("select precio_total/cantidad as precio_anterior FROM productos_x_factura as pxf inner join productos as p on p.id_producto = pxf.id_producto inner join facturas as f on f.id_factura = pxf.id_factura where p.id_producto = "+id+" order by f.fecha_factura DESC LIMIT 1");
            rs = pst.executeQuery();
            
            if(rs.next()){
                precio_anterior = Float.valueOf(rs.getString("precio_anterior"));
            }
            return precio_anterior;
        } catch (SQLException ex) {
            Logger.getLogger(ServicioFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return precio_anterior;
    }
}
