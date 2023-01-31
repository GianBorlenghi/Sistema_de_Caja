package Servicios;

import ConexionBD.Conexion;
import Modelos.Producto;
import Modelos.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.Locale;


public class ServicioVenta {

    Connection con = null;
    Conexion cone = new Conexion();
    ResultSet rs = null;
    PreparedStatement pst = null;
    float totalDelDia;

    public float cerrarCaja(){
        int gdia = LocalDateTime.now().getDayOfMonth();
        int gmes = LocalDateTime.now().getMonthValue();
        int ganio = LocalDateTime.now().getYear();
        try{
        con = cone.conect();
        pst = con.prepareStatement("select SUM(total) as total_del_dia from ventas where YEAR(fecha_venta) = '"+ganio+"' && MONTH(fecha_venta) = '"+gmes+"' && DAY(fecha_venta) = '"+gdia+"' ");
        rs = pst.executeQuery();
        
        while(rs.next()){
            totalDelDia = rs.getFloat("total_del_dia");
        }
        
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        
        return totalDelDia;
    }
    
    
    public int guardarVenta(ArrayList<Producto> listaCompra,float total,String pago) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        int idv = 0;
        String date = dateFormat.format(new Date());
        System.out.println(date);
        try {
            con = cone.conect();
            pst = con.prepareStatement("INSERT INTO ventas (id_venta,fecha_venta,total,forma_de_pago) VALUES (NULL, '"+date+"' , "+total+", '"+pago+"')");
            pst.executeUpdate();

            pst.clearBatch();
            pst = con.prepareStatement("SELECT MAX(id_venta) as id_v from ventas");
            rs = pst.executeQuery();
            while(rs.next()){
            idv = rs.getInt("id_v");
            }
            rs = null;
            
            pst.clearBatch();           
            for(Producto p : listaCompra){
                
                pst = con.prepareStatement("INSERT INTO venta_x_productos (id_ventaxpr,cantidad,id_producto,id_venta) VALUES (NULL, "+p.getCant()+" , "+p.getId_producto()+", (SELECT MAX(id_venta) FROM ventas))");
                pst.executeUpdate();
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e.toString());

        }
          return idv;

    }
    
    public ArrayList<Venta> listarVentas() throws ParseException{
        
        ArrayList<Venta> listaVentas = new ArrayList<>();
        try{
            con = cone.conect();
            pst = con.prepareStatement("SELECT * FROM ventas");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Venta v = new Venta();
                //prod.setId_producto(Integer.parseInt(rs.getString("id_producto")));
                v.setId_venta(Integer.parseInt(rs.getString("id_venta")));
                String fecha = String.valueOf(rs.getString("fecha_venta"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                LocalDateTime local = LocalDateTime.parse(fecha, formatter);
                v.setFecha(local);
                v.setTotal(Float.parseFloat(rs.getString("total")));
                v.setMetodo_pago(rs.getString("forma_de_pago"));
                
                listaVentas.add(v);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return listaVentas;
        
    }
}
