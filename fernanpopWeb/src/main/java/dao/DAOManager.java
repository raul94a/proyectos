package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOManager {
    private Connection conn;
    private final String URL;
    private final String USER;
    private final String PASS;
    private static DAOManager singlenton;

    private DAOManager() {
        this.conn = null;
        this.URL = "jdbc:mysql://remotemysql.com/P3osQiebpt?allowPublicKeyRetrieval=true&useSSL=false";//Fichero.getPropiedad("bd_url",2);
        this.USER = "P3osQiebpt";//Fichero.getPropiedad("bd_user",2);
        this.PASS = "fkQVXKmlsH"; //Fichero.getPropiedad("bd_psw",2);
    }

    public static DAOManager getSinglentonInstance() {
        if (singlenton == null) {
            singlenton = new DAOManager();
            return singlenton;
        } else return null;
    }

    public Connection getConn() {
        return conn;
    }

    public void open() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            throw e;
        }
    }

    public void close() throws SQLException {
        try {
            if (this.conn != null) this.conn.close();
        } catch (Exception e) { throw e; }
    }
}

