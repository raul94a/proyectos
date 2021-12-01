package modelo;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Producto {
    private int id;
    private String nombre;
    private float precio;
    private String descripcion;
    private String estado;
    private String imagen;
    private LocalDate fecha;
    private int vendedor;

    /*vacio*/
    public Producto(){}

    /*subir producto a bd*/
    public Producto(int id, String nombre, float precio, String descripcion, String estado, String imagen, String vendedor) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.estado = estado;
        this.imagen = imagen;
        this.vendedor = Integer.parseInt(vendedor);
    }

    /*recuperar producto de bd*/
    public Producto(int id, String nombre, float precio, String descripcion, String estado, String imagen, LocalDate fecha, int vendedor){
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.estado = estado;
        this.imagen = imagen;
        this.fecha = fecha;
        this.vendedor = vendedor;
    }
    
    public Producto(int id, String nombre, float precio, String estado ,String descripcion){
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    //GETTERS Y SETTERS


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getVendedor() {
        return vendedor;
    }

    public void setVendedor(int vendedor) {
        this.vendedor = vendedor;
    }
}
