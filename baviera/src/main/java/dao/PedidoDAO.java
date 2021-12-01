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
import modelo.Factura;
import modelo.Pedido;

/**
 *
 * @author Raul
 */
public class PedidoDAO {
    private static final String SQL_INSERT = "insert into pedidos(id, factura, nombre, direccion, telefono) values (?,?,?,?,?)";
    private static final String SQL_SELECCIONAR_PEDIDO = "select id, nombre, direccion, telefono from pedidos where factura = ?";
    
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
    public static Pedido seleccionarPedidoPorFactura(int factura){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        Pedido pedido = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_PEDIDO);
            stmt.setInt(1, factura);
            rs = stmt.executeQuery();
            while(rs.next()){
                long id = rs.getLong("id");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                int telefono = rs.getInt("telefono");
                pedido = new Pedido(id ,nombre, direccion, telefono);
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
        return pedido;
    }
    
}
