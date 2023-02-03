
package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    private final String url = "jdbc:mysql://sql10.freesqldatabase.com/sql10595245";//"jdbc:mysql://localhost/project";
    private final String user = "sql10595245";//"root";
    private final String password = "GKIZUSJfI1";//"";

    public Connection conect(){
        Connection con = null;
        try{
        con = DriverManager.getConnection(url, user, password);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos.");
            System.exit(1);
        }
           return con;
}
}
