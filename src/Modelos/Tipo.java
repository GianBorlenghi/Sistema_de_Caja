package Modelos;


public class Tipo {
    
    private int id_tipo;
    private String tipo;

    public Tipo() {
    }

    public Tipo(String tipo,int id_tipo) {
        this.tipo = tipo;
        this.id_tipo = id_tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }
    
    
    
}
