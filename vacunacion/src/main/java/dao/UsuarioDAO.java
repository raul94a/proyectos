/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import modelo.*;

/**
 *
 * @author Raul
 */
public class UsuarioDAO {
    private final static String SQL_INSERT = "insert into usuario(dni, nombre, apellidos, vacuna, lote, municipioVacunacion) values(?,?,?,?,?,?)";
    private final static String SQL_SELECT_POR_DNI = "select * from usuario U inner join vacuna V on V.id = U.vacuna inner join lotes L on L.id = U.lote where dni = ?";
    private final static String SQL_COUNT_VACUNACIONES = "select count(*) as 'numero' from usuario where dni = ?";
    private final static String SQL_DATOS_USUARIO = "select * from usuario where dni = ? limit 1";
    private final static String SQL_DATOS_VACUNACION = "SELECT * FROM usuario WHERE dni = ?";
    private final static String SQL_VACUNA_ADMINISTRADA = "select V.nombre from vacuna V inner join usuario U on U.vacuna = V.id where U.dni = ?";
   
    private final static String SQL_SELECT_SANITARIO = "select id from sanitario where nss = ?";
    
    
     public static int insertar(Usuario u){
        PreparedStatement stmt = null;
        Connection conn = null;
        
        int cantidad = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT );
            stmt.setString(1, u.getDni());
            stmt.setString(2, u.getNombre());
            stmt.setString(3, u.getApellidos());
            stmt.setInt(4, u.getVacuna().getId());
            stmt.setInt(5, u.getLote());
            stmt.setString(6, u.getMunicipio());
            stmt.execute();
         

        } catch (SQLException throwables) {
            throwables.printStackTrace(System.out);
        } finally {
            try {
                if (stmt != null) {
        
                    stmt.close();
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return cantidad;
    }
    
    
    public static int contarVacunaciones(String dni){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int cantidad = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_COUNT_VACUNACIONES);
            stmt.setString(1, dni);
            rs = stmt.executeQuery();
            while(rs.next()){
              
              cantidad = rs.getInt("numero");
                
            }
           

        } catch (SQLException throwables) {
            throwables.printStackTrace(System.out);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return cantidad;
    }
    
    
    
    public static String vacunaAdministrada(String dni){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String nombre = "";
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_VACUNA_ADMINISTRADA);
            stmt.setString(1, dni);
            rs = stmt.executeQuery();
            while(rs.next()){
               nombre = rs.getString("V.nombre");
              
                
            }
           

        } catch (SQLException throwables) {
            throwables.printStackTrace(System.out);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return nombre;
    }
    
    public static Usuario selectUsuario(String dni){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
         Usuario u = null;
        String nombre = "";
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DATOS_USUARIO);
            stmt.setString(1, dni);
            rs = stmt.executeQuery();
            while(rs.next()){
               nombre = rs.getString("nombre");
               String apellidos = rs.getString("Apellidos");
               int vac = rs.getInt("vacuna");
               Vacuna v = VacunaDAO.selectVacuna(vac);
              u = new Usuario(dni, nombre, apellidos, v);
              
                
            }
           

        } catch (SQLException throwables) {
            throwables.printStackTrace(System.out);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return u;
    }
    
    public static ArrayList<Usuario> datosVacunacion(String dni){
           PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        ArrayList<Usuario> usuarios = null;
        try {
            usuarios = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DATOS_VACUNACION);
            stmt.setString(1, dni);
            rs = stmt.executeQuery();
            while(rs.next()){
               String nombre = rs.getString("nombre");
               String apellidos = rs.getString("Apellidos");
               int vac = rs.getInt("vacuna");
               Vacuna v = VacunaDAO.selectVacuna(vac);
               LocalDate fecha = rs.getDate("fecha").toLocalDate()  ;
               String municipio = rs.getString("municipioVacunacion");
               usuarios.add(new Usuario(dni, nombre, apellidos, v, fecha, municipio));
              
     
            }
           

        } catch (SQLException throwables) {
            throwables.printStackTrace(System.out);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return usuarios;
    }
    
    public static int isSanitario(String nss){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
         Usuario u = null;
      int integer = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_SANITARIO);
            stmt.setString(1, nss);
            rs = stmt.executeQuery();
            while(rs.next()){
              integer = rs.getInt("id");                
            }
           

        } catch (SQLException throwables) {
            throwables.printStackTrace(System.out);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return integer;
    
    }
}
