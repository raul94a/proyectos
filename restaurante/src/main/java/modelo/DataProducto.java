/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Raul
 */
public class DataProducto {
    private int identificador;
    private int cantidad;
    public DataProducto(){}
    public DataProducto(int id, int cantidad){
        this.identificador = id;
        this.cantidad = cantidad;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int id) {
        this.identificador = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
