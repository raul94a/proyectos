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
import java.util.ArrayList;
import modelo.Vacuna;

/**
 *
 * @author Raul
 */
public class VacunaDAO {
    private static final String SQL_SELECT_VACUNA = "select * from vacuna where id = ?";
    private static final String SQL_SELECT_VACUNA_CON_UNIDADES = "select v.id, v.nombre from vacuna v inner join lotes l on l.vacuna = v.id where cantidad > 0";
    
     public static Vacuna selectVacuna(int id){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        Vacuna v = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_VACUNA);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while(rs.next()){
                id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                v = new Vacuna(id, nombre);
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
                throwables.printStackTrace(System.out);
            }
        }
        return v;
    }
     
     public static ArrayList<Vacuna> seleccionarVacunasConUnidades(){
         PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        ArrayList<Vacuna> vacunas = null;
        try {
            vacunas = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_VACUNA_CON_UNIDADES);
           
            rs = stmt.executeQuery();
            while(rs.next()){
               vacunas.add(new Vacuna(rs.getInt("v.id"), rs.getString("nombre")));
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
                throwables.printStackTrace(System.out);
            }
        }
        return vacunas;
     }
}
