
package Modelos;

import java.util.Date;


public class Producto_factura {
    int id_prod_factura;
    int cantidad;
    double precio_unitario;
    int id_producto;
    int id_factura;
    String nombre_producto;
    Date fecha_factura;
    String nombre_proveedor;
    int referencia;
    
    public Producto_factura() {
    }

    public Producto_factura(int id_prod_factura, int cantidad, double precio_unitario, int id_producto, int id_factura, String nombre_producto, Date fecha_factura, String nombre_proveedor, int referencia) {
        this.id_prod_factura = id_prod_factura;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.id_producto = id_producto;
        this.id_factura = id_factura;
        this.nombre_producto = nombre_producto;
        this.fecha_factura = fecha_factura;
        this.nombre_proveedor = nombre_proveedor;
        this.referencia = referencia;
    }

 

    public Date getFecha_factura() {
        return fecha_factura;
    }

    public void setFecha_factura(Date fecha_factura) {
        this.fecha_factura = fecha_factura;
    }

    public String getNombre_proveedor() {
        return nombre_proveedor;
    }

    public void setNombre_proveedor(String nombre_proveedor) {
        this.nombre_proveedor = nombre_proveedor;
    }

 

    public int getId_prod_factura() {
        return id_prod_factura;
    }

    public void setId_prod_factura(int id_prod_factura) {
        this.id_prod_factura = id_prod_factura;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }
    
    
    
}
