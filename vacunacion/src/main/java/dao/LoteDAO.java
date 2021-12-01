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

/**
 *
 * @author Raul
 */
public class LoteDAO {
    private static final String SQL_SELECCIONAR_LOTE_VACUNA = "select id from lotes where vacuna = ? and cantidad > 0 LIMIT 1";
    private static final String SQL_ACTUALIZAR_CANTIDAD = "update lotes set cantidad = cantidad - 1 where id = ?";
  
    
     public static int vacunaAdministrada(int vacuna){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int id = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_LOTE_VACUNA );
            stmt.setInt(1, vacuna);
            rs = stmt.executeQuery();
            while(rs.next()){
                id = rs.getInt("id");
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
        return id;
    }
     
     public static int disminuirCantidad(int lote){
        PreparedStatement stmt = null;
        Connection conn = null;
   
        int id = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_ACTUALIZAR_CANTIDAD);
            stmt.setInt(1, lote);
            id = stmt.executeUpdate();
            
        } catch (SQLException throwables) {
            throwables.printStackTrace(System.out);
        } finally {
            try {
                if (stmt!= null) {
                    stmt.close();
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace(System.out);
            }
        }
        return id;
    }
}
