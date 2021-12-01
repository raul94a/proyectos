package modelo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Usuario {
    private int id;
    private int rol;
    private int telefono;
    private String nombre;
    private String email;
    private String password;
    private String token;
    private String imgPerfil;
    private Date fechaRegistro;
    private char sexo;
    private boolean validado;
    private boolean activo;
    private Estadistica estadisticas;
    private ArrayList<Producto> productos;
    private ArrayList<Trato> compras;
    private ArrayList<Trato> ventas;
    private ArrayList<Integer> valoracionesPendientes;


    /*constructor vacío*/
    public Usuario() {
    }

    /* Este constructor se utilizará para registrar un usuario en la bd*/
    public Usuario(int id, int telefono, String nombre, String email, String password, char sexo) {
        this.id = id;
        this.telefono = telefono;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.sexo = sexo;
        this.fechaRegistro = Calendar.getInstance().getTime();
        this.productos = new ArrayList<>();
        this.compras = new ArrayList<>();
        this.ventas = new ArrayList<>();
        this.valoracionesPendientes = new ArrayList<>();
    }
    public Usuario (int id , int rol, String pass){
        this.id = id;
        this.rol = rol;
        this.password = pass;
    }

    /*constructor para sacar los datos de la bd*/
     public Usuario(int id, int rol, int telefono, String nombre,
                   String email, String password, String token, Date fechaRegistro,
                   char sexo, boolean validado, boolean activo, String imgPerfil) {
        this.id = id;
        this.rol = rol;
        this.telefono = telefono;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.token = token;
        this.fechaRegistro = fechaRegistro;
        this.sexo = sexo;
        this.validado = validado;
        this.activo = activo;
        this.productos = new ArrayList<>();
        this.compras = new ArrayList<>();
        this.ventas = new ArrayList<>();
        this.valoracionesPendientes = new ArrayList<>();
        this.imgPerfil = imgPerfil;
    }
     
    public Usuario(int id, int rol, int telefono, String nombre,
                   String email, String password, String token, Date fechaRegistro,
                   char sexo, boolean validado, boolean activo, String imgPerfil, Estadistica estadisticas) {
        this.id = id;
        this.rol = rol;
        this.telefono = telefono;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.token = token;
        this.fechaRegistro = fechaRegistro;
        this.sexo = sexo;
        this.validado = validado;
        this.activo = activo;
        this.productos = new ArrayList<>();
        this.compras = new ArrayList<>();
        this.ventas = new ArrayList<>();
        this.valoracionesPendientes = new ArrayList<>();
        this.imgPerfil = imgPerfil;
        this.estadisticas = estadisticas;
    }


    ////GETTERS Y SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getImgPerfil() {
        return imgPerfil;
    }

    public void setImgPerfil(String imgPerfil) {
        this.imgPerfil = imgPerfil;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public void setCompras(ArrayList<Trato> compras) {
        this.compras = compras;
    }

    public void setVentas(ArrayList<Trato> ventas) {
        this.ventas = ventas;
    }

    public ArrayList<Integer> getValoracionesPendientes() {
        return valoracionesPendientes;
    }

    public void setValoracionesPendientes(ArrayList<Integer> valoracionesPendientes) {
        this.valoracionesPendientes = valoracionesPendientes;
    }

    public Estadistica getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(Estadistica estadisticas) {
        this.estadisticas = estadisticas;
    }

    //otros metodos
    public ArrayList<Producto> getProductos() {
        return productos;
    }
    public ArrayList<Trato> getVentas() {
        return ventas;
    }
    public ArrayList<Trato> getCompras() {
        return compras;
    }


}
