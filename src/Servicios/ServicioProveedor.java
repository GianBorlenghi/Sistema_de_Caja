/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import ConexionBD.Conexion;
import Modelos.Proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author giaan
 */
public class ServicioProveedor {
    
    
    Connection con = null;
    Conexion cone = new Conexion();
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    public boolean existeProveedor(String nombre_proveedor){
        try {
            con = cone.conect();
            pst = con.prepareStatement("SELECT * FROM proveedores WHERE nombre_proveedor = '"+nombre_proveedor+"'");
            rs = pst.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return false;
    }
    
    public void crearProveedor(String nombre_proveedor){
        try {
            con = cone.conect();
            pst = con.prepareStatement("INSERT INTO proveedores (nombre_proveedor) VALUES ('"+nombre_proveedor+"')");
            pst.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    
    
    
     public ArrayList<Proveedor> listarProveedores() {
        ArrayList<Proveedor> listaProveedores = new ArrayList<>();
        try {
            con = cone.conect();
            pst = con.prepareStatement("SELECT * FROM proveedores");
            rs = pst.executeQuery();
            while (rs.next()) {

                Proveedor prov = new Proveedor();
                prov.setId_proveedor(Integer.parseInt(rs.getString("id_proveedor")));
                prov.setNombre_proveedor(rs.getString("nombre_proveedor"));


                listaProveedores.add(prov);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        return listaProveedores;
    }
     
     
    public String nombreProveedor(int id_proveedor){
        try {
            con = cone.conect();
            pst = con.prepareStatement("SELECT nombre_proveedor FROM proveedores WHERE id_proveedor ="+id_proveedor);
            rs = pst.executeQuery();
            if(rs.next()){
                System.out.println(rs.getString("nombre_proveedor"));
              return rs.getString("nombre_proveedor");    

            }
             } catch (SQLException ex) {
            Logger.getLogger(ServicioProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
