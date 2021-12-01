/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import modelo.Producto;

/**
 *
 * @author Raul
 */
public class ProductoDAO {
    private static final String SQL_SELECT_PRECIO = "select precio from productos where id = ?";
    private static final String SQL_SELECT_ALL = "select id, nombre, tipo, precio from productos order by tipo, nombre";
    private static final String SQL_SELECT_TIPO = "select id, nombre, precio from productos where tipo = ?  and activo = ? order by nombre";
    
    private static final String SQL_INSERT = "insert into productos(nombre,tipo, precio) values (?,?,?)" ;
    
    private static final String SQL_UPDATE_PRODUCTO = "update productos set nombre = ?, tipo = ?, precio = ? where id = ?";
    private static final String SQL_UPDATE_PRODUCTO_ACTIVADO = "update productos set activo = ? where id = ?";
    
    public static int insert(Producto p){
         
        PreparedStatement stmt = null;
        Connection conn = null;
        int registros = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getTipo());
            stmt.setFloat(3, p.getPrecio());

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
    
    public static float seleccionarPrecio(int id){
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        float precio = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_PRECIO);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                precio = rs.getFloat("precio");
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
        return precio;
    }
    
    public static ArrayList<Producto> seleccionarTodos(){
        ArrayList<Producto> productos = null;
         PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            productos = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                float precio = rs.getFloat("precio");
                Producto p = new Producto(id, tipo, nombre,  precio);
                productos.add(p);
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
        return productos;
    }
    
    public static ArrayList<Producto> seleccionarPorTipo(String tipo, boolean buscarSoloActivos){
        ArrayList<Producto> productos = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            productos = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_TIPO);
            stmt.setString(1, tipo);
            stmt.setInt(2, buscarSoloActivos? 1 : 0);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                float precio = rs.getFloat("precio");
                Producto p = new Producto(id, tipo, nombre, precio);
                productos.add(p);
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
        return productos;
    }
   
    public static int updateProducto(Producto p, int id){
        PreparedStatement stmt = null;
        
        Connection conn = null;
        int registros = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_PRODUCTO);
            stmt.setString(1,p.getNombre(   ));
            stmt.setString(2, p.getTipo());
            stmt.setFloat(3, p.getPrecio());
            stmt.setInt(4, id);
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
    public static int updateActivo(int id, boolean desactivar){
          PreparedStatement stmt = null;
        
        Connection conn = null;
        int registros = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_PRODUCTO_ACTIVADO);
            stmt.setInt(1, desactivar? 0: 1);
            
            stmt.setInt(2, id);
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
