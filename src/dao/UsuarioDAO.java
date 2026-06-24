package dao;

import modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// ============================================================
//  CLASE: UsuarioDAO
//  Maneja todas las operaciones de la tabla "usuarios"
//  en la base de datos.
// ============================================================

public class UsuarioDAO {

    // ============================================================
    // LOGIN
    // Busca un usuario por email y contraseña.
    // Devuelve el objeto Usuario si existe, o null si no.
    // Se usa en VentanaLogin al presionar INICIAR SESIÓN.
    // ============================================================
    public Usuario login(String email, String contrasena) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            conexion = Conexion.getConexion();

            String sql = "SELECT * FROM usuarios WHERE email = ? AND contrasena = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, contrasena);

            rs = stmt.executeQuery();

            // Si encontró un resultado, construimos el objeto Usuario
            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("contrasena"),
                        rs.getString("telefono")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
        } finally {
            // Siempre cerramos todo al terminar
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexion.cerrarConexion(conexion);
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return usuario;
    }

    // ============================================================
    // REGISTRAR
    // Inserta un usuario nuevo en la tabla.
    // Devuelve true si se guardó correctamente, false si no.
    // Se usa en VentanaRegistro al presionar GUARDAR.
    // ============================================================
    public boolean registrar(Usuario usuario) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            conexion = Conexion.getConexion();

            String sql = "INSERT INTO usuarios (nombre, apellido, email, contrasena, telefono) " +
                    "VALUES (?, ?, ?, ?, ?)";
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getContrasena());
            stmt.setString(5, usuario.getTelefono());

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                Conexion.cerrarConexion(conexion);
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return exito;
    }

    // ============================================================
    // EMAIL EXISTE
    // Verifica si un email ya está registrado en la BD.
    // Devuelve true si existe, false si no.
    // Se usa en VentanaRegistro para evitar emails duplicados
    // y en VentanaRecuperarContrasena para verificar el email.
    // ============================================================
    public boolean emailExiste(String email) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean existe = false;

        try {
            conexion = Conexion.getConexion();

            String sql = "SELECT id FROM usuarios WHERE email = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, email);

            rs = stmt.executeQuery();
            existe = rs.next(); // si hay resultado, el email existe

        } catch (SQLException e) {
            System.out.println("Error al verificar email: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexion.cerrarConexion(conexion);
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return existe;
    }

    // ============================================================
    // ACTUALIZAR CONTRASEÑA
    // Cambia la contraseña de un usuario buscándolo por email.
    // Devuelve true si se actualizó correctamente.
    // Se usa en VentanaRecuperarContrasena al presionar CAMBIAR.
    // ============================================================
    public boolean actualizarContrasena(String email, String nuevaContrasena) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            conexion = Conexion.getConexion();

            String sql = "UPDATE usuarios SET contrasena = ? WHERE email = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, nuevaContrasena);
            stmt.setString(2, email);

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar contraseña: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                Conexion.cerrarConexion(conexion);
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return exito;
    }
}