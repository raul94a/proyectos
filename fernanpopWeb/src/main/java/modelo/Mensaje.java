package modelo;


import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class Mensaje {
    private int id;
    private String contenido;
    private Date fecha;
    private int emisor;
    private int receptor;
    private boolean visto;
    private boolean borrado;
    private boolean borradoParaEmisor;
    private boolean borradoParaReceptor;
    
    public Mensaje() {
    }
   public Mensaje(String contenido, int emisor, int receptor ) {
      
        this.contenido = contenido;
       // this.fecha = fecha;
        this.emisor = emisor;
        this.receptor = receptor;
        this.visto = false;
        this.borrado = false;
        this.borradoParaEmisor = false;
        this.borradoParaReceptor = false;
    }

    public Mensaje(int id, String contenido, Date fecha, int emisor, int receptor, boolean visto, boolean borradoParaEmisor, boolean borradoParaReceptor) {
        this.id = id;
        this.contenido = contenido;
        this.fecha = fecha;
        this.emisor = emisor;
        this.receptor = receptor;
        this.visto = visto;
        this.borradoParaEmisor = borradoParaEmisor;
        this.borradoParaReceptor = borradoParaReceptor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getEmisor() {
        return emisor;
    }

    public void setEmisor(int emisor) {
        this.emisor = emisor;
    }

    public int getReceptor() {
        return receptor;
    }

    public void setReceptor(int receptor) {
        this.receptor = receptor;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }
    
    public String formatFecha(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
      
        return sdf.format(fecha);
    }
    public boolean isBorrado(){
        return this.borrado;
    }
    public boolean isBorradoParaReceptor(){
        return this.borradoParaReceptor;
    }
    public boolean isBorradoParaEmisor(){
        return this.borradoParaEmisor;
    }
}
