/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import ConexionBD.Conexion;
import IGU.Promociones;
import Modelos.Producto;
import Modelos.Promocion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author giaan
 */
public class ServicioPromocion {

    Connection con = null;
    Conexion cone = new Conexion();
    ResultSet rs = null;
    PreparedStatement pst = null;

    public boolean altaPromocion(String nombre, int cantidad, int descuento, int cant_minima, String fecha_desde, String fecha_hasta, ArrayList<Producto> listaSeleccion) {

        try {

            for (Producto p : listaSeleccion) {
                if (promocionActiva(p.getId_producto())) {
                    return false;
                }
            }
            con = cone.conect();
            pst = con.prepareStatement("INSERT INTO promociones_productos (descuento,fecha_desde,fecha_hasta,nombre_promocion,cant_minima,vigente) VALUES ('" + descuento + "','" + fecha_desde + "','" + fecha_hasta + "','" + nombre + "','" + cantidad + "','1')", Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();

            int id_promo = 0;
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id_promo = rs.getInt(1);
            }

            for (Producto p : listaSeleccion) {

                con = cone.conect();
                pst = con.prepareStatement("INSERT INTO producto_x_promocion (id_producto,id_producto_prom) VALUES ('" + p.getId_producto() + "','" + id_promo + "')");
                pst.executeUpdate();

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return true;
    }

    public boolean promocionActiva(int id_producto) {
        try {
            con = cone.conect();
            pst = con.prepareStatement("select * from productos right join producto_x_promocion as pxp on pxp.id_producto = productos.id_producto right join promociones_productos as pp on pp.id_promocion_producto = pxp.id_producto_prom where productos.id_producto = " + id_producto + " and pp.vigente = 1");
            rs = pst.executeQuery();
            if (!rs.next()) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicioPromocion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;

    }

    public /*ArrayList<*/ Promocion/*>*/ promocionXProd(int id_producto/*[]*/) {
        try {

            //ArrayList<Promocion> listaPromociones = new ArrayList<Promocion>();
            /*String id_consulta = String.valueOf(id_producto[0]);
            if (id_producto.length > 1) {
                for (int i = 1; i < id_producto.length; i++) {
                    id_consulta += " or p.id_producto = " + id_producto[i];
                    i++;
                }
            }*/
            con = cone.conect();
            pst = con.prepareStatement("select p.id_producto,prpr.descuento,prpr.nombre_promocion,prpr.cant_minima ,prpr.vigente from promociones_productos as prpr right join producto_x_promocion as pxp on pxp.id_producto_prom = prpr.id_promocion_producto right join productos as p on p.id_producto = pxp.id_producto where prpr.vigente = 1 and p.id_producto = " + id_producto); //+ id_consulta + ",0)");
            rs = pst.executeQuery();

            if (rs.next()) {

                Promocion promo = new Promocion();
                promo.setId_producto(Integer.parseInt(rs.getString("id_producto")));
                promo.setDescuento(Float.parseFloat(rs.getString("descuento")));
                promo.setCantidad_minima(Integer.parseInt(rs.getString("cant_minima")));
                promo.setNombre_promocion(rs.getString("nombre_promocion"));
                promo.setVigente(Boolean.parseBoolean(rs.getString("vigente")));
                //listaPromociones.add(promo);
                return promo;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ServicioPromocion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public void verificarPromos() {
        try {
            con = cone.conect();
            pst = con.prepareStatement("UPDATE promociones_productos as pp SET  vigente = IF(CURRENT_DATE() NOT BETWEEN pp.fecha_desde and pp.fecha_hasta, 0, 1) ");
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServicioPromocion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Promocion> todasLasPromos() {
        try {
            ArrayList<Promocion> todasLasPromociones = new ArrayList<>();
            con = cone.conect();
            pst = con.prepareStatement("select * from promociones_productos");
            rs = pst.executeQuery();
            while (rs.next()) {
                Promocion pro = new Promocion();
                pro.setId_producto(Integer.parseInt(rs.getString("id_promocion_producto")));
                pro.setNombre_promocion(rs.getString("nombre_promocion"));
                pro.setCantidad_minima(Integer.parseInt(rs.getString("cant_minima")));
                pro.setFecha_desde(rs.getString("fecha_desde"));
                pro.setFecha_hasta(rs.getString("fecha_hasta"));
                pro.setDescuento(Float.parseFloat(rs.getString("descuento")));
               if(Integer.parseInt(rs.getString("vigente")) == 1){
                   pro.setVigente(true);
               }else{
                   pro.setVigente(false);
               }
                todasLasPromociones.add(pro);
            }
            return todasLasPromociones;
        } catch (SQLException ex) {
            Logger.getLogger(ServicioPromocion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
       public void cambiarFechaHasta(int id_promo, String fecha_nueva) {
        try {
            con = cone.conect();
            pst = con.prepareStatement("UPDATE promociones_productos as pp SET  fecha_hasta =' "+fecha_nueva+"' where id_promocion_producto = "+id_promo);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServicioPromocion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
