
package Modelos;

import java.time.LocalDateTime;
import java.util.Date;


public class Facturas {
    
    int id_factura;
    int referencia;
    double total;
    private Date fecha_factura;
    int id_proveedor;

    public Facturas() {
    }

    public Facturas(int id_factura, int referencia, double total, Date fecha_factura, int id_proveedor) {
        this.id_factura = id_factura;
        this.referencia = referencia;
        this.total = total;
        this.fecha_factura = fecha_factura;
        this.id_proveedor = id_proveedor;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getFecha_factura() {
        return fecha_factura;
    }

    public void setFecha_factura(Date fecha_factura) {
        this.fecha_factura = fecha_factura;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }
    
    

}
