
package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Conexion {
   private final String url = "jdbc:mysql://localhost/project";/*
    private final String user = "root";*/
     
    private final String user = "root";
    private final String password = "";

    public Connection conect(){
        Connection con = null;
        
        try{
        con = DriverManager.getConnection(url, user, password);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e/*"Error al conectarse a la base de datos."*/);
            System.exit(1);
        }
           return con;
}
}
