/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.google.gson.Gson;
import java.util.ArrayList;

/**
 *
 * @author Raul
 */
public class Estadistica {
    private double gananciasTotales;
    private int numeroFacturas;
    private ArrayList<Producto> productosMasVendidos;
    private ArrayList<Producto> productosQueMasDineroHanGenerado;

    public Estadistica() {
    }

    public Estadistica(double gananciasTotales, int numeroFacturas, ArrayList<Producto> productosMasVendidos,
            ArrayList<Producto> productosQueMasDineroHanGenerado) {
        this.gananciasTotales = gananciasTotales;
        this.numeroFacturas = numeroFacturas;
        this.productosMasVendidos = productosMasVendidos;
        this.productosQueMasDineroHanGenerado = productosQueMasDineroHanGenerado;
    }

    

    public double getGananciasTotales() {
        return gananciasTotales;
    }

    public void setGananciasTotales(double gananciasTotales) {
        this.gananciasTotales = gananciasTotales;
    }

    public int getNumeroFacturas() {
        return numeroFacturas;
    }

    public void setNumeroFacturas(int numeroFacturas) {
        this.numeroFacturas = numeroFacturas;
    }

    public ArrayList<Producto> getProductosMasVendidos() {
        return productosMasVendidos;
    }

    public void setProductosMasVendidos(ArrayList<Producto> productosMasVendidos) {
        this.productosMasVendidos = productosMasVendidos;
    }

    public ArrayList<Producto> getProductosQueMasDineroHanGenerado() {
        return productosQueMasDineroHanGenerado;
    }

    public void setProductosQueMasDineroHanGenerado(ArrayList<Producto> productosQueMasDineroHanGenerado) {
        this.productosQueMasDineroHanGenerado = productosQueMasDineroHanGenerado;
    }
    public float media(){
        return (float)(Math.floor((gananciasTotales / numeroFacturas) * 100) /100) ;
    }
    
    public String productosMasVendidosToJSON(){
        return new Gson().toJson(this.productosMasVendidos);//.replace("\"", "'");
    }
     public String productosQueMasDineroHanGeneradoToJSON(){
        return new Gson().toJson(this.productosQueMasDineroHanGenerado);//.replace("\"", "'");
    }
    
    
    
}
