package Modelos;


public class ReporteDTO {
    private String nombre_producto;
    private int stock;
    private int cant_venta_total;
    private int cant_venta_7_dias;
    private int cant_venta_15_dias;
    private float cant_venta_total_en_pesos;
    private String tipo;
    
    public ReporteDTO() {
    }

    public ReporteDTO(String nombre_producto, String tipo,int stock, int cant_venta_total, int cant_venta_7_dias, int cant_venta_15_dias, float cant_venta_total_en_pesos) {
        this.nombre_producto = nombre_producto;
        this.stock = stock;
        this.cant_venta_total = cant_venta_total;
        this.cant_venta_7_dias = cant_venta_7_dias;
        this.cant_venta_15_dias = cant_venta_15_dias;
        this.cant_venta_total_en_pesos = cant_venta_total_en_pesos;
        this.tipo = tipo;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCant_venta_total() {
        return cant_venta_total;
    }

    public void setCant_venta_total(int cant_venta_total) {
        this.cant_venta_total = cant_venta_total;
    }

    public int getCant_venta_7_dias() {
        return cant_venta_7_dias;
    }

    public void setCant_venta_7_dias(int cant_venta_7_dias) {
        this.cant_venta_7_dias = cant_venta_7_dias;
    }

    public int getCant_venta_15_dias() {
        return cant_venta_15_dias;
    }

    public void setCant_venta_15_dias(int cant_venta_15_dias) {
        this.cant_venta_15_dias = cant_venta_15_dias;
    }

    public float getCant_venta_total_en_pesos() {
        return cant_venta_total_en_pesos;
    }

    public void setCant_venta_total_en_pesos(float cant_venta_total_en_pesos) {
        this.cant_venta_total_en_pesos = cant_venta_total_en_pesos;
    }
    
    
}
