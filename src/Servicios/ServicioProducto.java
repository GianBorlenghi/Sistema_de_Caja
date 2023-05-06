/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import ConexionBD.Conexion;
import Modelos.Producto;
import Modelos.ReporteDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ServicioProducto {

    Connection con = null;
    Conexion cone = new Conexion();
    ResultSet rs = null;
    PreparedStatement pst = null;

    public boolean altaProducto(String nombre, int tipo, int marca,String url) {
        
        try {
            con = cone.conect();
            pst = con.prepareStatement("SELECT * FROM productos where nombre_producto = '" + nombre + "' and id_tipo = '" + tipo + "' and id_marca = '" + marca + "'");
            rs = pst.executeQuery();

            if (!rs.next()) {
                pst = con.prepareStatement("INSERT INTO productos (nombre_producto,precio,mark_up,precio_con_iva,url_imagen,precio_al_publico,id_tipo,id_marca) VALUES ('" + nombre + "', 0 , 1.41 ,0,'"+url+"',0,'" + tipo + "' , '" + marca + "')");
                pst.executeUpdate();
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        return true;
    }

    public void bajaProducto(int id) {
        try {
            con = cone.conect();
            pst = con.prepareStatement("DELETE FROM productos WHERE id_producto = " + id);
            pst.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    public ArrayList<Producto> listarProductos() {
        ArrayList<Producto> listaProducto = new ArrayList<>();
        try {
            con = cone.conect();
            pst = con.prepareStatement("SELECT id_producto,nombre_producto, stock,url_imagen,precio,mark_up,precio_con_iva,precio_al_publico, marca, tipo FROM productos inner join marcas on marcas.id_marca = productos.id_marca inner join tipo_productos on tipo_productos.id_tipo_producto = productos.id_tipo");
            rs = pst.executeQuery();
            while (rs.next()) {

                Producto prod = new Producto();
                prod.setId_producto(Integer.parseInt(rs.getString("id_producto")));
                prod.setNombre_producto(rs.getString("nombre_producto"));
                prod.setPrecio(Float.parseFloat(rs.getString("precio")));
                prod.setMark_up(Float.parseFloat(rs.getString("mark_up")));
                prod.setPrecio_con_iva(Float.parseFloat(rs.getString("precio_con_iva")));
                prod.setPrecio_al_publico(Float.parseFloat(rs.getString("precio_al_publico")));
                prod.setMarca_nombre(rs.getString("marca"));
                prod.setTipo_nombre(rs.getString("tipo"));
                prod.setUrl_imagen(rs.getString("url_imagen"));
                prod.setStock(Integer.parseInt(rs.getString("stock")));

                listaProducto.add(prod);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        return listaProducto;
    }

    public void editarProducto(int id, float precio,double precio_con_iva,double precio_al_publico,float markup, String nombre) {
        float precioAlPublico = (float) precio_al_publico;
        float precioConIva = (float) precio_con_iva;
        float markUp = (float) markup;
        try {
            con = cone.conect();
           /* pst = con.prepareStatement("SELECT * from productos where nombre_producto = '"+nombre+"'");
            
            rs = pst.executeQuery();
            if (rs.next()) {*/
                pst = con.prepareStatement("UPDATE productos SET nombre_producto = '" + nombre + "', precio = " + precio + ",mark_up = " + markUp + " , precio_con_iva = " + precioConIva + " ,precio_al_publico = " + precioAlPublico +  " WHERE id_producto = " + id);
                pst.executeUpdate();
           /* } else {
                return false;
            }*/
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
       // return true;
       
    }
    
    public Producto buscarProductoPorId(int id){
        try {
            con = cone.conect();
            pst = con.prepareStatement("SELECT * FROM productos WHERE id_producto = "+id);
            rs = pst.executeQuery();
            if(rs.next()){
                Producto p = new Producto();
                p.setNombre_producto(rs.getString("nombre_producto"));
                p.setMark_up(Float.parseFloat(rs.getString("mark_up")));
                p.setPrecio(Float.parseFloat(rs.getString("precio")));
                p.setPrecio_con_iva(Float.parseFloat(rs.getString("precio_con_iva")));
                p.setPrecio_al_publico(Float.parseFloat(rs.getString("precio_al_publico")));
                p.setId_producto(id);
                return p;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void actualizarPrecioProducto(int id,float precio_unitario,float precio_con_iva,float precio_al_publico){
        try {
            con = cone.conect();
            pst = con.prepareStatement("UPDATE productos SET precio = "+precio_unitario+", precio_con_iva = "+precio_con_iva+", precio_al_publico =  "+precio_al_publico+" WHERE id_producto = "+id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String urlImagenProducto(int id){
        try{
            con = cone.conect();
            pst = con.prepareStatement("SELECT url_imagen FROM productos WHERE id_producto = "+id);
            rs = pst.executeQuery();
            if(rs.next()){
                return rs.getString("url_imagen");
            }
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return null;
    }
    
    public void actualizarStock(int id,String metodo){
        try{
            con = cone.conect();
            if(metodo.equals("alta")){
                pst = con.prepareStatement("UPDATE productos set stock = (SELECT (p.stock + pxf.cantidad) as suma_stock from productos as p inner join productos_x_factura as pxf on pxf.id_producto = p.id_producto inner join facturas as f on f.id_factura = pxf.id_factura WHERE p.id_producto = "+id+" && f.id_factura = (SELECT id_factura as idf from facturas order by idf desc limit 1) group by p.id_producto) where productos.id_producto = "+id);            
                pst.executeUpdate();
            }else if(metodo.equals("baja")){
                pst = con.prepareStatement("UPDATE productos as p set stock = (select (p.stock - vxp.cantidad) as stock_menos from productos as p inner join venta_x_productos as vxp on vxp.id_producto = p.id_producto WHERE p.id_producto = "+id+" && vxp.id_venta = (SELECT id_venta as idv from ventas order by idv DESC limit 1)) where p.id_producto = "+id);            
                pst.executeUpdate();
            }
        }catch(SQLException e ){
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
    
    public void actualizacionDePrecioXFactura (int id){
        try {
            con = cone.conect();
            pst = con.prepareStatement("update productos set precio = IF((SELECT pxf.precio_total/pxf.cantidad from productos_x_factura as pxf inner join facturas as f on f.id_factura = pxf.id_factura Where pxf.id_producto = "+id+" order by fecha_factura desc limit 0,1) > (SELECT pxf.precio_total/pxf.cantidad from productos_x_factura as pxf inner join facturas as f on f.id_factura = pxf.id_factura Where pxf.id_producto = "+id+" order by fecha_factura desc limit 1,1),(SELECT pxf.precio_total/pxf.cantidad from productos_x_factura as pxf inner join facturas as f on f.id_factura = pxf.id_factura Where pxf.id_producto = "+id+" order by fecha_factura desc limit 0,1),precio) where productos.id_producto = "+id);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int verCantidadDeFacturas(int id){
        try {
            con = cone.conect();
            pst = con.prepareStatement("Select count(id_producto) as cantidad_de_facturas from productos_x_factura as pxf inner join facturas as f on f.id_factura = pxf.id_factura where pxf.id_producto = "+id+" group by pxf.id_producto");
            rs  = pst.executeQuery();
            if(rs.next()){
                return rs.getInt("cantidad_de_facturas");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public ArrayList<ReporteDTO> reporte(){
        ArrayList<ReporteDTO> listaReporte = new ArrayList<>();
        try {
            
            con = cone.conect();
            pst = con.prepareStatement("select GROUP_CONCAT(p.nombre_producto,' | ' ,m.marca) as nombre_producto,p.stock, t.tipo,COALESCE(vxp.cantidad,0) as cant_venta, COALESCE((SELECT SUM(cantidad) AS c from venta_x_productos inner join ventas on venta_x_productos.id_venta = ventas.id_venta INNER JOIN productos on productos.id_producto = venta_x_productos.id_producto where fecha_venta BETWEEN NOW() - interval 8 day and now()  AND productos.id_producto = p.id_producto group by productos.id_producto limit 1),0) as venta_ultimos_7_dias, COALESCE((SELECT SUM(cantidad) AS c from venta_x_productos inner join ventas on venta_x_productos.id_venta = ventas.id_venta INNER JOIN productos on productos.id_producto = venta_x_productos.id_producto where fecha_venta BETWEEN NOW() - interval 16 day and now()  AND productos.id_producto = p.id_producto group by productos.id_producto limit 1),0) as venta_ultimos_15_dias, COALESCE((SELECT sum(vxp.precio_total) from venta_x_productos as vxp where vxp.id_producto = p.id_producto GROUP by vxp.id_producto),0) as venta_total from productos as p inner join (select marca,id_marca from marcas) as m on m.id_marca = p.id_marca inner join (select id_tipo_producto,tipo from tipo_productos) as t on t.id_tipo_producto = p.id_tipo left join (select id_producto,id_venta,SUM(cantidad) as cantidad from venta_x_productos group by id_producto) as vxp on vxp.id_producto = p.id_producto group by p.id_producto");
            rs = pst.executeQuery();
            while(rs.next()){
                ReporteDTO rpDto = new ReporteDTO();
                rpDto.setNombre_producto(rs.getString("nombre_producto"));
                rpDto.setStock(Integer.parseInt(rs.getString("stock")));
                rpDto.setCant_venta_total(Integer.parseInt(rs.getString("cant_venta")));
                rpDto.setTipo(rs.getString("tipo"));
                rpDto.setCant_venta_7_dias(Integer.parseInt(rs.getString("venta_ultimos_7_dias")));
                rpDto.setCant_venta_15_dias(Integer.parseInt(rs.getString("venta_ultimos_15_dias")));
                rpDto.setCant_venta_total_en_pesos(Float.parseFloat(rs.getString("venta_total")));
                listaReporte.add(rpDto);
            }
            return listaReporte;
        } catch (SQLException ex) {
            Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Producto> productosSinStock(){
        ArrayList<Producto> listaSStock = new ArrayList<>();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 

        try {
            con = cone.conect();
            pst = con.prepareStatement("select GROUP_CONCAT(p.nombre_producto,' | ',m.marca) as nombre_producto, COALESCE((SELECT MAX(fecha_factura) from facturas as f left join productos_x_factura as pxf on pxf.id_factura = f.id_factura where pxf.id_producto = p.id_producto GROUP BY pxf.id_producto),'S/Compras') as fecha_ultima_compra from productos as p  LEFT JOIN marcas as m on m.id_marca = p.id_marca WHERE p.stock = 0 group by p.id_producto");
            rs = pst.executeQuery();              
            
            while(rs.next()){
                Producto p = new Producto();
                p.setNombre_producto(rs.getString("nombre_producto"));
                p.setFecha_factura(rs.getString("fecha_ultima_compra"));             
                listaSStock.add(p);
            }
            return listaSStock;

        } catch (SQLException ex) {
            Logger.getLogger(ServicioProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
