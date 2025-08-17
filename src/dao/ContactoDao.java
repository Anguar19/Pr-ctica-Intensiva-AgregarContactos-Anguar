/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import conexion.DatabaseConnection;
import dominio.Contacto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Anguar Alberto Rodriguez Fonseca
 */
public class ContactoDao implements CrudDao{
    @Override
    public void agregarContacto(Contacto contacto) throws SQLException {
        String sql = "INSERT INTO Contactos (nombre, telefono, email) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contacto.getNombre());
            stmt.setString(2, contacto.getTelefono());
            stmt.setString(3, contacto.getEmail());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Contacto> obtenerTodos() throws SQLException {
        List<Contacto> contactos = new ArrayList<>();
        String sql = "SELECT * FROM Contactos";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Contacto c = new Contacto();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setTelefono(rs.getString("telefono"));
                c.setEmail(rs.getString("email"));
                contactos.add(c);
            }
        }
        return contactos;
    }

    @Override
    public void eliminarContacto(int id) throws SQLException {
        String sql = "DELETE FROM Contactos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    
}
