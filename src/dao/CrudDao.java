/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dominio.Contacto;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Anguar Alberto Rodriguez Fonseca
 */
public interface CrudDao {
    void agregarContacto(Contacto contacto) throws SQLException;
    List<Contacto> obtenerTodos() throws SQLException;
    void eliminarContacto(int id) throws SQLException;
}
