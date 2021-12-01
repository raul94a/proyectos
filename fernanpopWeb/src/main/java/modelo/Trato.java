package modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Trato implements Serializable {
    private int id;
    private String tipo;
    private Date fecha;
    private int idOtroUser;
    private float precio;
    private Producto producto;
    private String comentario;
    private float puntuacion;
    private boolean tratoCerrado;
    private boolean tratoCalificado;

    public Trato(int id, String tipo, int idOtroUser, float precio, Producto producto) {
        this.id = id;
        this.tipo = tipo;
        this.idOtroUser = idOtroUser;
        this.precio = precio;
        this.producto = producto;
        if(tipo.equals("compra")) this.tratoCerrado = true;
    }
    public Trato(String tipo, float puntuacion, Producto producto){
        
        this.tipo = tipo;
        
        this.producto = producto;
      
        this.puntuacion = puntuacion;
       
    }
    /** Este constructor lo utilizaré para cuando vaya a insertar el trato definicitov en la tabla tratos **/
    public Trato(Trato t){
        this.id = t.id;
        this.tipo = t.tipo;
        this.idOtroUser = t.idOtroUser;
        this.precio = t.precio;
        this.producto = t.producto;
    }

    public Trato(int id, String tipo, Date fecha, int idOtroUser, float precio, Producto producto, String comentario, float puntuacion, boolean tratoCalificado) {
        this.id = id;
        this.tipo = tipo;
        this.fecha = fecha;
        this.idOtroUser = idOtroUser;
        this.precio = precio;
        this.producto = producto;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
        this.tratoCalificado = tratoCalificado;
    }
    public Trato(int id, String tipo, Date fecha, int idOtroUser, float precio, Producto producto, boolean tratoCalificado) {
        this.id = id;
        this.tipo = tipo;
        this.fecha = fecha;
        this.idOtroUser = idOtroUser;
        this.precio = precio;
        this.producto = producto;
        this.tratoCalificado = tratoCalificado;
    }
    /*Constructor copia de Trato para generar un nuevo trato tras insercion en la base de datos*/
    private Trato (int id, Trato t){
        this.id = id;
        this.tipo = "compra";
        this.idOtroUser = t.idOtroUser;
        this.precio = t.precio;
        this.producto = t.producto;

    }

    public int getId() {
        return id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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

    public String parsearFecha(Date fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        return sdf.format(fecha);
    }
    public static String getDateTime(Date fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(fecha);

    }

    public Date getDate(){
        return this.fecha;
    }

    public String getFecha() {

        return parsearFecha(this.fecha);
    }

    public void actualizarFecha() {

        this.fecha = Calendar.getInstance().getTime();
    }

    public int idOtroUser() {
        return idOtroUser;
    }

    public void setidOtroUser(int idOtroUser) {
        this.idOtroUser = idOtroUser;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public boolean isTratoCerrado() {
        return tratoCerrado;
    }

    public void setTratoCerrado(boolean tratoCerrado) {
        this.tratoCerrado = tratoCerrado;
    }

    public boolean isTratoCalificado() {
        return tratoCalificado;
    }

    public void setTratoCalificado(boolean tratoCalificado) {
        this.tratoCalificado = tratoCalificado;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdOtroUser() {
        return idOtroUser;
    }

    public void setIdOtroUser(int idOtroUser) {
        this.idOtroUser = idOtroUser;
    }


    ////OTROS METODOS/////
    public static Trato generarTrato(int id, Trato t){
        return new Trato(id, t);
    }
    /*@Override
    public String toString() {
        return "\n────────────── Trato ──────────────" +
                "\n| ID: " + id +
                "\n| Tipo: " + tipo +
                "\n| Fecha: " + getFecha() +
                (tipo.equals("venta") ? "\n| Email del comprador: " : "\n| Email del vendedor: ") + idOtroUser +
                (comentario == null ? "\n| Comentario: sin comentar" : ( comentario.length() > 0 ? "\n| Comentario: " + comentario : "\n| Comentario: sin comentar" ) ) +
                "\n| Puntuación: " + (puntuacion == 0?"Sin puntuar":puntuacion) +
                "\n──────────── Producto ────────────" +
                "\n\t| Nombre: " + producto.getNombre() +
                "\n\t| Precio: " + producto.getPrecio() +
                "\n\t| Descripción: " + producto.getDescripcion() +
                "\n\t| Estado: " + producto.getEstado();
    }*/
}
