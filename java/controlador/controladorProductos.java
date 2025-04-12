package controlador;

import conexionDB.CConexion;
import modelos.ModeloProductos;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class controladorProductos {

    public void mostrarProductos(JTable tb_productos) {
        CConexion conexion = new CConexion();
        DefaultTableModel modelo = new DefaultTableModel();
        

        modelo.addColumn("ID");
        modelo.addColumn("NOMBRES");
        modelo.addColumn("PRECIO");
        modelo.addColumn("STOCK");
        modelo.addColumn("ESTADO");
        

        tb_productos.setModel(modelo);

        String sql = "SELECT productos.id, productos.nombres, productos.precio, productos.stock, productos.estado FROM productos";

        try {
            Statement st = conexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombres"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    rs.getString("estado"),
                });
            }

            tb_productos.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR al mostrar productos: " + e.toString());
        }
    }

    public void agregarProducto(JTextField nombre, JTextField precio, JTextField stock, JTextField estado) {
        CConexion conexion = new CConexion();
        ModeloProductos producto = new ModeloProductos();

        String consulta = "INSERT INTO productos (nombres, precio, stock, estado) VALUES (?, ?, ?, ?)";

        try {
            producto.setNombre(nombre.getText());
            producto.setPrecio(Double.valueOf(precio.getText()));
            producto.setStock(Integer.parseInt(stock.getText()));
            producto.setEstado(estado.getText());

            CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, producto.getNombre());
            cs.setDouble(2, producto.getPrecio());
            cs.setInt(3, producto.getStock());
            cs.setString(4, producto.getEstado());
            
           
            cs.execute();
            JOptionPane.showMessageDialog(null, "Producto guardado");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.toString());
        }
    }

    public void seleccionarProducto(JTable tabla, JTextField id, JTextField nombre, JTextField precio, JTextField stock, JTextField estado ) {
        int fila = tabla.getSelectedRow();

        try {
            if (fila >= 0) {
                id.setText(tabla.getValueAt(fila, 0).toString());
                nombre.setText(tabla.getValueAt(fila, 1).toString());
                precio.setText(tabla.getValueAt(fila, 2).toString());
                stock.setText(tabla.getValueAt(fila, 3).toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR al seleccionar producto: " + e.toString());
        }
    }

    public void modificarProducto(JTextField id, JTextField nombre, JTextField precio, JTextField stock) {
    CConexion conexion = new CConexion();
    ModeloProductos producto = new ModeloProductos();

    String consulta = "UPDATE productos SET nombres = ?, precio = ?, stock = ?, estado = ? WHERE id = ?";

    try {
        // Asignamos los valores al objeto producto
        producto.setId(Integer.parseInt(id.getText()));
        producto.setNombre(nombre.getText());
        producto.setPrecio(Double.parseDouble(precio.getText()));
        int stockValor = Integer.parseInt(stock.getText());
        producto.setStock(stockValor);

        // Determinamos el estado según el stock
        String estado = stockValor > 0 ? "activo" : "no activo";
        producto.setEstado(estado);

        // Usamos PreparedStatement en lugar de CallableStatement
        PreparedStatement ps = conexion.estableceConexion().prepareStatement(consulta);
        ps.setString(1, producto.getNombre());
        ps.setDouble(2, producto.getPrecio());
        ps.setInt(3, producto.getStock());
        ps.setString(4, producto.getEstado());
        ps.setInt(5, producto.getId());

        ps.executeUpdate(); // Usamos executeUpdate para INSERT, UPDATE o DELETE
        JOptionPane.showMessageDialog(null, "Producto modificado");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "ERROR al modificar: " + e.toString());
    }
}


    public void eliminarProducto(JTextField id) {
        CConexion conexion = new CConexion();
        ModeloProductos producto = new ModeloProductos();

        String consulta = "DELETE FROM productos WHERE id = ?";

        try {
            producto.setId(Integer.parseInt(id.getText()));
            CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, producto.getId());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR al eliminar: " + e.toString());
        }
    }

    public void limpiarCampos(JTextField id, JTextField nombre, JTextField precio, JTextField stock, JTextField estado ) {
        id.setText("");
        nombre.setText("");
        precio.setText("");
        stock.setText("");
        estado.setText("");
        
    }
}

