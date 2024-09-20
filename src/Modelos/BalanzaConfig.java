/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

public class BalanzaConfig {

    private String prefijo;
    private int longitudCodigoProducto;
    private int longitudPeso;

    public BalanzaConfig(String prefijo, int longitudCodigoProducto, int longitudPeso) {
        this.prefijo = prefijo;
        this.longitudCodigoProducto = longitudCodigoProducto;
        this.longitudPeso = longitudPeso;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public int getLongitudCodigoProducto() {
        return longitudCodigoProducto;
    }

    public int getLongitudPeso() {
        return longitudPeso;
    }

    /**
     * Extrae el código del producto desde el código de barras.
     *
     * @param codigoBarra El código de barras escaneado.
     * @return El código del producto.
     */
    public String extraerCodigoProducto(String codigoBarra) {
        return codigoBarra.substring(prefijo.length(), prefijo.length() + longitudCodigoProducto);
    }

    /**
     * Extrae el peso desde el código de barras.
     *
     * @param codigoBarra El código de barras escaneado.
     * @return El peso en formato flotante.
     */
    public float extraerPeso(String codigoBarra) {
        String pesoStr = codigoBarra.substring(prefijo.length() + longitudCodigoProducto, prefijo.length() + longitudCodigoProducto + longitudPeso);
        return Float.parseFloat(pesoStr) / 10000.0f;  // Suponiendo que el peso está en gramos y lo conviertes a kilogramos
    }
}
