package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Estadistica;

public class EstadisticaDAO {

    private static final String SQL_ACTUALIZAR_NUMERO_COMPRAS_USUARIO =
            "update estadisticas set compras = compras + 1 where usuario = ?";
    private static final String SQL_ACTUALIZAR_NUMERO_VENTAS_USUARIO =
            "update estadisticas set ventas = ventas + 1 where usuario = ?";
    private static final String SQL_UPDATE_ESTADISTICAS =
            "update estadisticas set valoracionesRecibidas = valoracionesRecibidas + 1, totalPuntos = totalPuntos + ?, valoracionMedia = totalPuntos / valoracionesRecibidas where usuario = ?";
    private static final String SQL_SELECCIONAR_STATS_USUARIO = "select * from estadisticas where usuario = ?";
    private static final String SQL_SELECCIONAR_VALORACION_MEDIA = "select valoracionMedia from estadisticas where usuario = ?";

    public static int actualizarNumeroDeComprasUsuario(int comprador){
        int registros = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_ACTUALIZAR_NUMERO_COMPRAS_USUARIO);
            stmt.setInt(1,comprador);
            registros = stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally {
            try{
                if(stmt != null){
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return registros;
    }
    public static int actualizarNumeroDeVentasUsuario(int vendedor){
        int registros = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_ACTUALIZAR_NUMERO_VENTAS_USUARIO);
            stmt.setInt(1,vendedor);
            registros = stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally {
            try{
                if(stmt != null){
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return registros;

    }
    public static int actualizarEstadisticas(float valoracion, int usuario){
        int registros = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_ESTADISTICAS);
            stmt.setFloat(1,valoracion);
            stmt.setInt(2, usuario);
            registros = stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally {
            try{
                if(stmt != null){
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return registros;

    }
    
    public static Estadistica seleccionarEstadisticaUsuario(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
         Estadistica s = null;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_STATS_USUARIO);
            stmt.setInt(1,id);
            rs = stmt.executeQuery();
            while(rs.next()){
                int idEstadistica = rs.getInt("id");
                int ventas = rs.getInt("ventas");
                int compras = rs.getInt("compras");
                float totalPuntos = rs.getFloat("totalPuntos");
                int valoraciones = rs.getInt("valoracionesRecibidas");
                s = new Estadistica (idEstadistica,compras,ventas,totalPuntos,valoraciones,id);
            }
           
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally {
            try{
                if(rs!= null){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return s;
    }
    public static float seleccionarValoracionMedia(int id){
         Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
       float valMedia = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECCIONAR_STATS_USUARIO);
            stmt.setInt(1,id);
            rs = stmt.executeQuery();
            while(rs.next()){
               
                        
                      valMedia = rs.getFloat("ValoracionMedia");
                
            }
           
        }catch (SQLException e){
            e.printStackTrace(System.out);
        }finally {
            try{
                if(rs!= null){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch (SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return valMedia;
    }

}
