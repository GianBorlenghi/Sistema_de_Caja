package Servicios;

import ConexionBD.Conexion;
import Modelos.Tipo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ServicioTipo {

    Connection con = null;
    Conexion cone = new Conexion();
    ResultSet rs = null;
    PreparedStatement pst = null;

    public boolean altaTipo(String tipo) {

        try {
            con = cone.conect();
            pst = con.prepareStatement("SELECT * FROM tipo_productos WHERE tipo = '" + tipo + "'");
            rs = pst.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                pst = con.prepareStatement("INSERT INTO tipo_productos (tipo) VALUES ('" + tipo + "')");
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null, e.toString());
        }

        return false;
    }

    public boolean editarTipo(int id, String tipo) {

        try {

            con = cone.conect();
            pst = con.prepareStatement("SELECT * FROM tipo_productos WHERE tipo = '" + tipo + "'");
            rs = pst.executeQuery();

            if (rs.next()) {
                return false;
            } else {

                pst = con.prepareStatement("UPDATE tipo_productos SET tipo = '" + tipo + "' WHERE id_tipo_producto = " + id);
                pst.executeUpdate();

            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e.toString());

        }

        return true;
    }

    public ArrayList<Tipo> listarTipos() {

        ArrayList<Tipo> listaTipos = new ArrayList<>();
        try {
            con = cone.conect();
            pst = con.prepareStatement("SELECT * FROM tipo_productos");
            rs = pst.executeQuery();

            while (rs.next()) {

                Tipo tipo = new Tipo();
                tipo.setTipo(rs.getString("tipo"));
                tipo.setId_tipo(Integer.parseInt(rs.getString("id_tipo_producto")));
                listaTipos.add(tipo);
                System.out.println(tipo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        return listaTipos;
    }

    public boolean bajaTipo(int id) {

        try {
            con = cone.conect();
            pst = con.prepareStatement("SELECT * FROM tipo_productos where id_tipo_producto = " + id);
            rs = pst.executeQuery();

            if (rs.next()) {

                pst = con.prepareStatement("DELETE from tipo_productos WHERE id_tipo_producto = " + id);
                pst.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        return false;
    }

}
