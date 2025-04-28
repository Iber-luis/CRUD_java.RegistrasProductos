/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import conexionDB.CConexion;
import modelos.modeloUsuarios;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author IBER LUIS
 */
public class controladorLogin {
    // Método para registrar un nuevo usuario
    public boolean registrarUsuario(modeloUsuarios usuario) {
        CConexion conexion = new CConexion();
        Connection con = conexion.estableceConexion();
        
        String sql = "INSERT INTO LoginUser (nombre, clave) VALUES (?, ?)";
        
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, usuario.getNombre());
            pst.setString(2, usuario.getClave());
            
            pst.executeUpdate();
            return true; // Registro exitoso
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.toString());
            return false;
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar conexión: " + ex.toString());
            }
        }
    }

    // Método para buscar un usuario en la base de datos (validar login)
    public modeloUsuarios buscarUsuario(String nombre, String clave) {
        CConexion conexion = new CConexion();
        Connection con = conexion.estableceConexion();
        
        String sql = "SELECT * FROM LoginUser WHERE nombre = ? AND clave = ?";
        modeloUsuarios usuario = null;
        
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setString(2, clave);
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                usuario = new modeloUsuarios();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setClave(rs.getString("clave"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar usuario: " + e.toString());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar conexión: " + ex.toString());
            }
        }
        
        return usuario;
    }
}
