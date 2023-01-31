
package Modelos;

public class Producto_Venta {
    
    private int id_ventapr;
    private int cantidad;
    private int id_producto;
    private int id_venta;

    public Producto_Venta(int id_ventapr, int cantidad, int id_producto, int id_venta) {
        this.id_ventapr = id_ventapr;
        this.cantidad = cantidad;
        this.id_producto = id_producto;
        this.id_venta = id_venta;
    }

    public Producto_Venta() {
    }

    public int getId_ventapr() {
        return id_ventapr;
    }

    public void setId_ventapr(int id_ventapr) {
        this.id_ventapr = id_ventapr;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }
    
    
}
