/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.time.LocalDate;
import java.util.ArrayList;


public class Factura {
    private int id;
    private LocalDate fecha;
    private String direccion;
    private int mesa;
    private float precio;
    private ArrayList<Producto> productos;

    public Factura(int id, LocalDate fecha, String direccion, int mesa, float precio) {
        this.id = id;
        this.fecha = fecha;
        this.direccion = direccion;
        this.mesa = mesa;
        this.precio = precio;
        this.productos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
    
    public void addProducto(Producto p){
        productos.add(p);
    }
    
    
    
    
    
    
}
