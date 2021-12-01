
package modelo;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Producto {
    private int id;
    private String tipo;
    private String nombre;
    private float precio;
    private int cantidad;

    public Producto() {
    }
    

    public Producto(int id, String tipo, String nombre, float precio) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto(int id, String nombre, float precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    public Producto(String nombre, String tipo, float precio) {
        this.nombre =nombre;
        this.tipo = tipo;
        this.precio = precio;
    }
    public Producto(int id, String nombre, String tipo)  {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Producto(int id, String nombre, float precio) {
         this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        
        return nombre;
    }
    public String path() throws IOException {

        File file = new File("C:\\Users\\Raul\\Desktop\\musica.txt");
        file.createNewFile();
        
        String path = file.getCanonicalPath();
        return path;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    //otros métodos
    public float calcularPrecio(){
        return this.cantidad * this.precio;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id +  ", nombre=" + nombre + ", precio=" + precio + ", cantidad=" + cantidad + '}';
    }
    
}
