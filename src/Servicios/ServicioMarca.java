package Servicios;

import ConexionBD.Conexion;
import Modelos.Marca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ServicioMarca {

    Connection con = null;
    Conexion cone = new Conexion();
    ResultSet rs = null;
    PreparedStatement pst = null;

    public boolean altaMarca(String marca) {

        try {
            con = cone.conect();
            pst = con.prepareStatement("SELECT * FROM marcas where marca = '" + marca + "'");
            rs = pst.executeQuery();
            if (rs.next()) {
                return false;
            } else {
                pst = con.prepareStatement("INSERT INTO marcas (marca) VALUES ('" + marca + "')");
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return true;

    }

    public boolean bajaMarca(int id) {

        try {

            con = cone.conect();
            pst = con.prepareStatement("DELETE FROM `marcas` WHERE `marcas`.`id_marca` = " + id);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        return false;

    }

    public ArrayList<Marca> listaMarcas() {

        ArrayList<Marca> listaMarca = new ArrayList<>();
        try {
            con = cone.conect();
            pst = con.prepareStatement("SELECT * FROM marcas");
            rs = pst.executeQuery();

            while (rs.next()) {

                Marca marc = new Marca();

                marc.setId_marca(Integer.parseInt(rs.getString("id_marca")));
                marc.setMarca(rs.getString("marca"));

                listaMarca.add(marc);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        return listaMarca;
    }

    public void editarMarca(int id, String marca) {

        try {
            con = cone.conect();
            pst = con.prepareStatement("UPDATE marcas SET marca = '" + marca + "' WHERE id_marca = " + id);
            pst.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

    }
}
