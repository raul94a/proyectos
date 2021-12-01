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
public class Vacuna {
    private int id;
    private String nombre;
    private Lote lote;

    public Vacuna(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    
    public Vacuna(int id, String nombre, Lote lote) {
        this.id = id;
        this.nombre = nombre;
        this.lote = lote;
    }

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

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }
    
    
    
    
}
