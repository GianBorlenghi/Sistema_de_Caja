
package Modelos;

import java.time.LocalDateTime;


public class Venta {

    private int id_venta;
    private LocalDateTime fecha;
    private double total;
    private String metodo_pago;
    private String fecha2;
    public Venta() {
    }

    public Venta(String metodo_pago,int id_venta, LocalDateTime fecha, double total) {
        this.id_venta = id_venta;
        this.fecha = fecha;
        this.total = total;
        this.metodo_pago = metodo_pago;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
 

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public void setFecha2(String fecha2) {
        
        this.fecha2 = fecha2;
        
    }
    
    
    
}
