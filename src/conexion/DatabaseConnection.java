/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Anguar Alberto Rodriguez Fonseca
 */
public class DatabaseConnection {
    private static final String URL = "LATITUDE-7490-D\\\\SQLEXPRESS;databaseName=AgendaConctactos";
    private static final String USER = "sa";
    private static final String PASS = "1234";
    
    private static DatabaseConnection instance;
    private Connection connection;
    
    private DatabaseConnection() throws SQLException {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new SQLException("Error al conectar con la base de datos: " + e.getMessage());
        }
    }
    
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
