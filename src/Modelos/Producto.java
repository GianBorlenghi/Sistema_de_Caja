package Modelos;

import java.util.Date;


public class Producto {
    
    private int id_producto;
    String codigo_barra;
     private String nombre_producto;
     private int marca_producto;
     private int tipo_producto;
     private float precio;
     private float mark_up;
     private float precio_con_iva;
     private float precio_al_publico;
     private String marca_nombre;
     private String tipo_nombre;
     private float cant;
     private float totalXFactura;
     private String url_imagen;
     private int stock;
     private String fecha_factura;

    public Producto() {
    }

    public Producto(String codigo_barra,int id_producto,String fecha_factura, int stock, String nombre_producto, int marca_producto, int tipo_producto, float precio, float mark_up, float precio_con_iva, float precio_al_publico, String marca_nombre, String tipo_nombre, float cant, float totalXFactura, String url_imagen) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.marca_producto = marca_producto;
        this.tipo_producto = tipo_producto;
        this.precio = precio;
        this.mark_up = mark_up;
        this.precio_con_iva = precio_con_iva;
        this.precio_al_publico = precio_al_publico;
        this.marca_nombre = marca_nombre;
        this.tipo_nombre = tipo_nombre;
        this.cant = cant;
        this.totalXFactura = totalXFactura;
        this.url_imagen = url_imagen;
        this.stock = stock;
        this.fecha_factura = fecha_factura;
        this.codigo_barra= codigo_barra;
    }

    public String getCodigo_barra() {
        return codigo_barra;
    }

    public void setCodigo_barra(String codigo_barra) {
        this.codigo_barra = codigo_barra;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

  
    public float getTotalXFactura() {
        return totalXFactura;
    }

    public void setTotalXFactura(float totalXFactura) {
        this.totalXFactura = totalXFactura;
    }

    
    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public int getMarca_producto() {
        return marca_producto;
    }

    public void setMarca_producto(int marca_producto) {
        this.marca_producto = marca_producto;
    }

    public int getTipo_producto() {
        return tipo_producto;
    }

    public void setTipo_producto(int tipo_producto) {
        this.tipo_producto = tipo_producto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getMark_up() {
        return mark_up;
    }

    public void setMark_up(float mark_up) {
        this.mark_up = mark_up;
    }

    public float getPrecio_con_iva() {
        return precio_con_iva;
    }

    public void setPrecio_con_iva(float precio_con_iva) {
        this.precio_con_iva = precio_con_iva;
    }

    public float getPrecio_al_publico() {
        return precio_al_publico;
    }

    public void setPrecio_al_publico(float precio_al_publico) {
        this.precio_al_publico = precio_al_publico;
    }

    public String getMarca_nombre() {
        return marca_nombre;
    }

    public void setMarca_nombre(String marca_nombre) {
        this.marca_nombre = marca_nombre;
    }

    public String getTipo_nombre() {
        return tipo_nombre;
    }

    public void setTipo_nombre(String tipo_nombre) {
        this.tipo_nombre = tipo_nombre;
    }

    public float getCant() {
        return cant;
    }

    public void setCant(float cant) {
        this.cant = cant;
    }

    public String getFecha_factura() {
        return fecha_factura;
    }

    public void setFecha_factura(String fecha_factura) {
        this.fecha_factura = fecha_factura;
    }
    
    

    


   
}
