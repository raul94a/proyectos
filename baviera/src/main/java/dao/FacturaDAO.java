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
import modelo.Factura;


public class FacturaDAO {
    private static final String SQL_INSERTAR = "INSERT into factura (direccion) values(?)";
    private static final String SQL_FETCH_ID_POR_DIRECCION_NO_PAGADA = "select id from factura where direccion = ? and pagada = 0";
    private static final String SQL_SELECCIONAR_FACTURA_POR_MESA_NO_PAGADA = "select id from factura where mesa = ? and pagada = 0";
    private static final String SQL_UPDATE_PRECIO = "update factura set precio = precio + ? where id = ?";
    private static final String SQL_UPDATE_CERRAR_FACTURA = "update factura set pagada = 1, fecha = ? where id = ?";
    private static final String SQL_SELECT_FACTURA = "select * from factura F inner join facturaProductos fp on F.id = fp.factura inner join productos p on fp.producto = p.id where F.id = ? ";
    private static final String SQL_UPDATE_PAGAR_FACTURA = "update factura set pagada = 1 where id = ?";
    
    public static int insertar(String direccion){
         Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERTAR);
            stmt.setString(1, direccion);
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
    
    public static int seleccionarId(String direccion){
        int id = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_FETCH_ID_POR_DIRECCION_NO_PAGADA);
            stmt.setString(1, direccion);
            rs = stmt.executeQuery();

            while (rs.next()) {
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
                throwables.printStackTrace();
            }
        }
        return id;
    }
    
    public static Factura seleccionarFactura(int factura){
        Factura f = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_FACTURA);
            stmt.setInt(1, factura);
            rs = stmt.executeQuery();

            ArrayList<Producto> productos = new ArrayList<>();
            while(rs.next()){
                LocalDate fecha = rs.getDate("F.fecha").toLocalDate();
                String direccion = rs.getString("F.direccion");
                int mesa = rs.getInt("F.mesa");
                float precioFac = rs.getFloat("F.precio");
                if(f == null){
                    f = new Factura(factura, fecha, direccion, mesa, precioFac);

                }
                int identificador = rs.getInt("p.id");
                String nombre = rs.getString("p.nombre");
                float precio = rs.getFloat("p.precio");
                int cantidad = rs.getInt("fp.cantidad");
                Producto p = new Producto(identificador, nombre, precio, cantidad);
                productos.add(p);
            }
            f.setProductos(productos);
            
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
        return f;
    }
    
    public static int actualizarPrecio(float precio, int id){
   
        PreparedStatement stmt = null;
        
        Connection conn = null;
        int registros = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_PRECIO);
            stmt.setFloat(1, precio);
            stmt.setInt(2,id);
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
                throwables.printStackTrace();
            }
        }
        return registros;
    }
    
    public static int cerrarFactura(int factura){
   
        PreparedStatement stmt = null;
        
        Connection conn = null;
        int registros = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_CERRAR_FACTURA);
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setInt(2,factura);
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
                throwables.printStackTrace();
            }
        }
        return registros;
    }
    
    
    
}
