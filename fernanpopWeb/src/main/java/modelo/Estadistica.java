package modelo;

public class Estadistica {
    private int id;
    private int compras;
    private int ventas;
    private float totalPuntos;
    private int valoracionesRecibidas;
    private int usuario;
    
    public Estadistica(int id, int compras, int ventas, float totalPuntos, int valoracionesRecibidas, int usuario){
        this.id = id;
        this.compras = compras;
        this.ventas = ventas;
        this.totalPuntos = totalPuntos;
        this.valoracionesRecibidas = valoracionesRecibidas;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompras() {
        return compras;
    }

    public void setCompras(int compras) {
        this.compras = compras;
    }

    public int getVentas() {
        return ventas;
    }

    public void setVentas(int ventas) {
        this.ventas = ventas;
    }

    public float getTotalPuntos() {
        return totalPuntos;
    }

    public void setTotalPuntos(float totalPuntos) {
        this.totalPuntos = totalPuntos;
    }

    public int getValoracionesRecibidas() {
        return valoracionesRecibidas;
    }

    public void setValoracionesRecibidas(int valoracionesRecibidas) {
        this.valoracionesRecibidas = valoracionesRecibidas;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }
    public Float getValoracionMedia(){
        return (float)Math.round((totalPuntos/(float)valoracionesRecibidas) * 10) / 10;
    }
    
}
