package Modelos;


public class Producto {
    
    private int id_producto;
     private String nombre_producto;
     private int marca_producto;
     private int tipo_producto;
     private float precio;
     private String marca_nombre;
     private String tipo_nombre;
     private int cant;

    public Producto() {
    }

    public Producto(int cant,int id_producto,float precio,String nombre_producto, int marca_producto, int tipo_producto) {
        this.nombre_producto = nombre_producto;
        this.marca_producto = marca_producto;
        this.tipo_producto = tipo_producto;
        this.precio = precio;
        this.id_producto = id_producto;
        this.cant = cant;
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

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
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

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }
     
     
    
    
}
