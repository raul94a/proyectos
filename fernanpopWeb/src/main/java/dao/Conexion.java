/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.sql.DataSource;
import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;
import utils.Fichero;


public class Conexion {
    private Connection conn;
    private static final String URL = /*Fichero.getPropiedadDb("url");*/"jdbc:mysql://localhost:3333/fernanpop?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = /*Fichero.getPropiedadDb("user");*/"root";
    private static final String PASS = /*Fichero.getPropiedadDb("pass");*/"root";
   
   public static Connection getConnection() throws SQLException{
     
            return DriverManager.getConnection(URL, USER, PASS);
    }

    /*public static DataSource getDataSource(){
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(URL);
        ds.setUsername(USER);
        ds.setPassword(PASS);
        ds.setInitialSize(50);
        return ds;
    }
    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }*/

    public static void close(ResultSet rs) throws SQLException{
        rs.close();
      
    }
    public static void close(PreparedStatement st) throws SQLException{
        st.close();
      
    }
    public static void close(Connection c) throws SQLException{
        c.close();
      
    }
}
