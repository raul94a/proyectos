
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Producto;

public class EstadisticaDAO {
    private static final String SQL_SELECT_NUMERO_FACTURAS = "select count(*) as 'numero' from factura where ";
    

    private static final String SQL_SELECT_GANANCIAS_TOTALES = "select sum(precio) as 'ganancias' from factura where ";
    private static final String SQL_SELECT_PRODUCTOS_MAS_VENDIDOS_DIARIO = "select P.id, P.nombre, P.precio, sum(FP.cantidad) as 'cantidad' from productos P inner join "
            + "facturaProductos FP on FP.producto = P.id where FP.factura in (select id from factura "
            + "where ";
    
    private static final String SQL_SELECT_PRODUCTOS_MAS_DINERO_GENERADO = 
            "SELECT P.id, P.nombre, P.precio, ROUND(sum(FP.cantidad) * P.precio, 2) as 'money' "
            + "from productos P inner join facturaProductos FP on FP.producto = P.id "
            + "where FP.factura in (select id from factura where ";
    
   public  static ArrayList<Producto> productosMasDineroGenerado(int tipo){
        String sqlSelectFecha = "";
        String sqlFinal = ") "
            + "group by P.id "
            + "ORDER BY money desc "
            + "LIMIT 10";;
       
        switch(tipo){
            case 1: //diario
                sqlSelectFecha = "CAST(fecha as DATE) = CURDATE()";
                break;
            case 2: //semanal
                   sqlSelectFecha = "CAST(fecha as DATE) > DATE_SUB(CURDATE(), interval 7 day)";
                break;
            case 3: //global
                sqlSelectFecha = " 1";
                break;
            default:
                break;
        }
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ArrayList<Producto> productos = null;
        try {
            productos = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_PRODUCTOS_MAS_DINERO_GENERADO + sqlSelectFecha + sqlFinal);

            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("P.id");
                String nombre = rs.getString("P.nombre");
                float precio = rs.getFloat("money");
               
                productos.add(new Producto(id,nombre,precio));
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
        return productos;
    
    }
    
    public static int numeroFacturas(int tipo){
        String sqlSelectFecha = "";
       
        switch(tipo){
            case 1: //diario
                sqlSelectFecha = "CAST(fecha as DATE) = CURDATE()";
                break;
            case 2: //semanal
                   sqlSelectFecha = "CAST(fecha as DATE) > DATE_SUB(CURDATE(), interval 7 day)";
                break;
            case 3: //global
                sqlSelectFecha = " 1";
                break;
            default:
                break;
        }
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        int numero = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_NUMERO_FACTURAS + sqlSelectFecha);
        
            rs = stmt.executeQuery();

            while (rs.next()) {
                numero = rs.getInt("numero");
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
        return numero;
    }
    
    public static float gananciasTotales(int tipo){
       String sqlSelectFecha = "";
       
        switch(tipo){
            case 1: //diario
                sqlSelectFecha = "CAST(fecha as DATE) = CURDATE()";
                break;
            case 2: //semanal
                   sqlSelectFecha = "CAST(fecha as DATE) > DATE_SUB(CURDATE(), interval 7 day)";
                break;
            case 3: //global
                sqlSelectFecha = " 1";
                break;
            default:
                break;
        }
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        float ganancias = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_GANANCIAS_TOTALES + sqlSelectFecha);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ganancias = rs.getInt("ganancias");
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
        return ganancias;
    }
    
    public static ArrayList<Producto> productosMasVendidos(int tipo){
        String sqlSelectFecha = "";
        String sqlFinal = " ) group by P.id LIMIT 10";
       
        switch(tipo){
            case 1: //diario
                sqlSelectFecha = "CAST(fecha as DATE) = CURDATE()";
                break;
            case 2: //semanal
                   sqlSelectFecha = "CAST(fecha as DATE) > DATE_SUB(CURDATE(), interval 7 day)";
                break;
            case 3: //global
                sqlSelectFecha = " 1";
                break;
            default:
                break;
        }
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        ArrayList<Producto> productos = null;
        try {
            productos = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_PRODUCTOS_MAS_VENDIDOS_DIARIO + sqlSelectFecha + sqlFinal);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("P.id");
                String nombre = rs.getString("P.nombre");
                float precio = rs.getFloat("P.precio");
                int cantidad = rs.getInt("cantidad");
                productos.add(new Producto(id,nombre,precio,cantidad));
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
        return productos;
    }
}
