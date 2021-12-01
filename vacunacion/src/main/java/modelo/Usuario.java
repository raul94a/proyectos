/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Raul
 */
public class Usuario {
    private int id;
    private String dni;
    private String nombre;
    private String apellidos;
    private Vacuna vacuna;
    private LocalDate fecha;
    private String municipio;
    private int lote;

    public Usuario(String dni, String nombre, String apellidos, Vacuna vacuna, int lote, String municipio) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.vacuna = vacuna;
        this.lote = lote;
        this.municipio = municipio;
    }
      public Usuario(String dni, String nombre, String apellidos, Vacuna vacuna, LocalDate fecha, String municipio) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.vacuna = vacuna;
        this.fecha = fecha;
        this.municipio = municipio;
    }

    public Usuario(String dni, String nombre, String apellidos, Vacuna vacuna) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.vacuna = vacuna;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Vacuna getVacuna() {
        return vacuna;
    }

    public void setVacuna(Vacuna vacuna) {
        this.vacuna = vacuna;
    }

    public String getFecha() {
       return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getLote() {
        return lote;
    }

    public void setLote(int lote) {
        this.lote = lote;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
    
    
}
