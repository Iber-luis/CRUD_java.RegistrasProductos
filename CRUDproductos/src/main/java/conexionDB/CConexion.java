
package conexionDB;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author IBER LUIS
 */
public class CConexion {
    
    Connection conectar = null;
    
    String usuario = "root";
    String contraseña = "root";
    String bd = "resgistroProductos";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;
    
    public Connection estableceConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena, usuario, contraseña);
            /*JOptionPane.showMessageDialog(null, "Conexión Correcta");*/
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "No se conectó: " + e.toString());
        }
        return conectar;
    }
    
    public void cerrarConexion() {
        // Aquí podrías cerrar la conexión si quieres agregarlo
    }

    public Connection conectar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

