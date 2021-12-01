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
import modelo.Factura;
import modelo.Pedido;
import modelo.Producto;

/**
 *
 * @author Raul
 */
public class PedidoDAO {
    private static final String SQL_INSERT = "insert into pedidos(factura, nombre, direccion, telefono) values (?,?,?,?)";
    private static final String SQL_SELECT_PEDIDOS_NO_FINALIZADOS = "select factura from pedidos where finalizado = 0";
    private static final String SQL_SELECT_PEDIDO_BY_FACTURA = "select * from pedidos where factura = ?";
    private static final String SQL_PRODUCTOS_PEDIDO_POR_FACTURA = "select P.nombre, FP.cantidad from productos P inner join facturaProductos FP on FP.producto = P.id where FP.factura = ?";
    private static final String SQL_COUNT_PEDIDOS_PENDIENTES = "select count(*) as 'cantidad' from pedidos where finalizado = 0";
    private static final String SQL_FINALIZAR_PEDIDO = "update pedidos set finalizado = 1 where id = ?";
    
    public static int insertar(Pedido pedido){
        int registros = 0;
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setLong(1, pedido.getId());
            stmt.setInt(2, pedido.getFactura());
            stmt.setString(3, pedido.getNombre());
            stmt.setString(4, pedido.getDireccion());
            stmt.setInt(5, pedido.getTelefono());
            registros = stmt.executeUpdate()    ;
           

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
    
    public static ArrayList<Pedido> seleccionarPedidosNoFinalizados(){
         PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ArrayList<Pedido> pedidos = null;
        try {
            pedidos = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_PEDIDOS_NO_FINALIZADOS);
            rs = stmt.executeQuery();

            while(rs.next()){
                pedidos.add(seleccionarPedidoPorFactura(rs.getInt("factura")));
                
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
        return pedidos;
    }
    
    public static Pedido seleccionarPedidoPorFactura(int factura){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        Pedido p = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_PEDIDO_BY_FACTURA);
            stmt.setInt(1, factura);
            rs = stmt.executeQuery();

            while(rs.next()){
                long id = rs.getLong("id");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                int telefono = rs.getInt("telefono");
                p = new Pedido(id, nombre, direccion, telefono, factura);
            }
            //ahora debemos capturar todos los productos de ese mismo pedido
            p.setProductos(seleccionarProductosPedido(factura));
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
        return p;
    }
    
    public static ArrayList<Producto> seleccionarProductosPedido(int factura){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ArrayList<Producto> productos = null;
        try {
            productos = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_PRODUCTOS_PEDIDO_POR_FACTURA);
            stmt.setInt(1, factura);
            rs = stmt.executeQuery();

            while(rs.next()){
                String nombre = rs.getString("P.nombre");
                int cantidad = rs.getInt("FP.cantidad");
                productos.add(new Producto(nombre, cantidad));
            }
            //ahora debemos capturar todos los productos de ese mismo pedido
            
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
        return productos;
    }
    
    public static int contarPedidosNoFinalizados(){
         PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
       int cantidad = 0;
        try {
           
          
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_COUNT_PEDIDOS_PENDIENTES);
            rs = stmt.executeQuery();
            while(rs.next()){
                cantidad = rs.getInt("cantidad");

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
    
    public static int finalizarPedido(int pedido){
        
        PreparedStatement stmt = null;
        
        Connection conn = null;
        int registros = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_FINALIZAR_PEDIDO);
            stmt.setInt(1,pedido);
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
