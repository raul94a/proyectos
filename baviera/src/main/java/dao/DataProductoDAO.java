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
import modelo.DataProducto;

/**
 *
 * @author Raul
 */
public class DataProductoDAO {
    private static final String SQL_INSERTAR = "INSERT INTO facturaProductos values(?,?,?)";
    private static final String SQL_SELECT_PRODUCTO = "SELECT producto from facturaProductos where producto = ? and factura = ?";
    private static final String SQL_UPDATE_CANTIDAD = "update facturaProductos set cantidad = cantidad + ? where factura = ? and producto = ?";
    
    public static int insertar(int factura, DataProducto dp){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERTAR);
            stmt.setInt(1, factura);
            stmt.setInt(2, dp.getIdentificador());
            stmt.setInt(3, dp.getCantidad());
            registros = stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if(stmt != null){
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registros;
        
    }
    
    public static Integer selectProducto(int producto, int factura){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Integer id = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_PRODUCTO);
            stmt.setInt(1, producto);
            stmt.setInt(2, factura);
            rs = stmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("producto");
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
    
     public static int updateCantidad(int cantidad, int factura, int producto){
        PreparedStatement stmt = null;
        Connection conn = null;
        int registros = 0;
        try {
            
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_CANTIDAD);
            stmt.setInt(1, cantidad);
            stmt.setInt(2, factura);
            stmt.setInt(3, producto);
            registros = stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace(System.out);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace(System.out);
            }
        }
        return registros;
    }
}
