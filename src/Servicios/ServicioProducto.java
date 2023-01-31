/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import ConexionBD.Conexion;
import Modelos.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ServicioProducto {

    Connection con = null;
    Conexion cone = new Conexion();
    ResultSet rs = null;
    PreparedStatement pst = null;

    public boolean altaProducto(String nombre, float precio, int tipo, int marca) {

        try {
            con = cone.conect();
            pst = con.prepareStatement("SELECT * FROM productos where nombre_producto = '" + nombre + "' and id_tipo = '" + tipo + "' and id_marca = '" + marca + "'");
            rs = pst.executeQuery();

            if (!rs.next()) {
                pst = con.prepareStatement("INSERT INTO productos (nombre_producto,precio,id_tipo,id_marca) VALUES ('" + nombre + "' , '" + precio + "' , '" + tipo + "' , '" + marca + "')");
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
            pst = con.prepareStatement("SELECT id_producto,nombre_producto, precio, marca, tipo FROM productos inner join marcas on marcas.id_marca = productos.id_marca inner join tipo_productos on tipo_productos.id_tipo_producto = productos.id_tipo");
            rs = pst.executeQuery();
            while (rs.next()) {

                Producto prod = new Producto();
                prod.setId_producto(Integer.parseInt(rs.getString("id_producto")));
                prod.setNombre_producto(rs.getString("nombre_producto"));
                prod.setPrecio(Float.parseFloat(rs.getString("precio")));
                prod.setMarca_nombre(rs.getString("marca"));
                prod.setTipo_nombre(rs.getString("tipo"));

                listaProducto.add(prod);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        return listaProducto;
    }

    public void editarProducto(int id, float precio, String nombre) {

        try {
            con = cone.conect();
           /* pst = con.prepareStatement("SELECT * from productos where nombre_producto = '"+nombre+"'");
            
            rs = pst.executeQuery();
            if (rs.next()) {*/
                pst = con.prepareStatement("UPDATE productos SET nombre_producto = '" + nombre + "', precio = " + precio + " WHERE id_producto = " + id);
                pst.executeUpdate();
           /* } else {
                return false;
            }*/
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
       // return true;

    }
}
