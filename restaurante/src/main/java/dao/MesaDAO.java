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

/**
 *
 * @author Raul
 */
public class MesaDAO {
    private static final String SQL_SELECCIONAR_MESAS_RESERVADAS = "select * from mesas where reservada = 1";
    private static final String SQL_SELECCIONAR_MESAS_LIBRES = "select * from mesas where reservada = 0";
    private static final String SQL_RESERVAR_MESA = "update mesas set reservada = 1 where numero = ?";
    private static final String SQL_LIBERAR_MESA = "update mesas set reservada = 0 where numero = ?";

    
    public static ArrayList<Integer> seleccionarMesas(boolean libres){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Integer> mesas = null;

        try{
            mesas = new ArrayList<>();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(libres ? SQL_SELECCIONAR_MESAS_LIBRES : SQL_SELECCIONAR_MESAS_RESERVADAS);
            rs = stmt.executeQuery();
            while(rs.next()){
                mesas.add(rs.getInt("numero"));
            }

        }catch(SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if (rs != null){
                    rs.close();
                    stmt.close();
                    conn.close();
                }
            }catch(SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return mesas;
        
    }
    
    public static int updateReservaMesa(int mesa, boolean liberar){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try{
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement( liberar ? SQL_LIBERAR_MESA : SQL_RESERVAR_MESA );
            stmt.setInt(1, mesa);
            registros = stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace(System.out);
        }finally{
            try{
                if(stmt != null){
                    stmt.close();
                    conn.close();
                }
            }catch(SQLException e){
                e.printStackTrace(System.out);
            }
        }
        return registros;
    }

}
