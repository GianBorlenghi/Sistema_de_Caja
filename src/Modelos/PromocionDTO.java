/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author giaan
 */
public class PromocionDTO {

    int id_producto;
    float descuento;
    String nombre_promocion;
    int cant_minima;
    float descuento_producto;
    String nombre_producto;
   

    public PromocionDTO(String nombre_producto,int id_producto, float descuento, String nombre_promocion, int cant_minima, float descuento_producto) {
        this.id_producto = id_producto;
        this.descuento = descuento;
        this.nombre_promocion = nombre_promocion;
        this.cant_minima = cant_minima;
        this.descuento_producto = descuento_producto;
        this.nombre_producto = nombre_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

  

    public PromocionDTO() {
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public String getNombre_promocion() {
        return nombre_promocion;
    }

    public void setNombre_promocion(String nombre_promocion) {
        this.nombre_promocion = nombre_promocion;
    }

    public int getCant_minima() {
        return cant_minima;
    }

    public void setCant_minima(int cant_minima) {
        this.cant_minima = cant_minima;
    }

    public float getDescuento_producto() {
        return descuento_producto;
    }

    public void setDescuento_producto(float descuento_producto) {
        this.descuento_producto = descuento_producto;
    }

}
