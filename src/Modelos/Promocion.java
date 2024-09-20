/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;


public class Promocion {
    
    private int id_promocion;
    private String nombre_promocion;
    private float descuento;
    private int cantidad_minima;
    private String fecha_desde;
    private String fecha_hasta;
    private boolean vigente;
    private int id_producto;

    public Promocion() {
    }

    public Promocion(int id_producto,int id_promocion, String nombre_promocion, float descuento, int cantidad_minima, String fecha_desde, String fecha_hasta, boolean vigente) {
        this.id_promocion = id_promocion;
        this.nombre_promocion = nombre_promocion;
        this.descuento = descuento;
        this.cantidad_minima = cantidad_minima;
        this.fecha_desde = fecha_desde;
        this.fecha_hasta = fecha_hasta;
        this.vigente = vigente;
        this.id_producto = id_producto;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_promocion() {
        return id_promocion;
    }

    public void setId_promocion(int id_promocion) {
        this.id_promocion = id_promocion;
    }

    public String getNombre_promocion() {
        return nombre_promocion;
    }

    public void setNombre_promocion(String nombre_promocion) {
        this.nombre_promocion = nombre_promocion;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public int getCantidad_minima() {
        return cantidad_minima;
    }

    public void setCantidad_minima(int cantidad_minima) {
        this.cantidad_minima = cantidad_minima;
    }

    public String getFecha_desde() {
        return fecha_desde;
    }

    public void setFecha_desde(String fecha_desde) {
        this.fecha_desde = fecha_desde;
    }

    public String getFecha_hasta() {
        return fecha_hasta;
    }

    public void setFecha_hasta(String fecha_hasta) {
        this.fecha_hasta = fecha_hasta;
    }

    public boolean isVigente() {
        return vigente;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }
    
    
    
}
