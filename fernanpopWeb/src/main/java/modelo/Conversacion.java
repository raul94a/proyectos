package modelo;

public class Conversacion {
    private int id;
    private int comprador;
    private int vendedor;
    private int producto;
    
    public Conversacion(){}

    public Conversacion(int id, int comprador, int vendedor, int producto) {
        this.id = id;
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.producto = producto;
    }
      public Conversacion(int id, int comprador, int vendedor) {
        this.id = id;
        this.comprador = comprador;
        this.vendedor = vendedor;
        
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComprador() {
        return comprador;
    }

    public void setComprador(int comprador) {
        this.comprador = comprador;
    }

    public int getVendedor() {
        return vendedor;
    }

    public void setVendedor(int vendedor) {
        this.vendedor = vendedor;
    }

    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }
    
    
    
    
}
