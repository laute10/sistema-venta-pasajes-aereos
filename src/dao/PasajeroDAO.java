package dao;

import modelo.Pasajero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// ============================================================
//  CLASE: PasajeroDAO
//  Maneja todas las operaciones de la tabla "pasajeros"
//  en la base de datos.
// ============================================================

public class PasajeroDAO {

    // ============================================================
    // GUARDAR PASAJERO
    // Inserta un pasajero nuevo asociado a una reserva.
    // Devuelve true si se guardó correctamente.
    // Se llama desde VentanaPago al confirmar el pago,
    // una vez por cada pasajero cargado en VentanaReserva.
    // ============================================================
    public boolean guardar(Pasajero pasajero) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            conexion = Conexion.getConexion();

            String sql = "INSERT INTO pasajeros (id_reserva, nombre, apellido, fecha_nacimiento, nro_pasaporte) " +
                    "VALUES (?, ?, ?, ?, ?)";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, pasajero.getIdReserva());
            stmt.setString(2, pasajero.getNombre());
            stmt.setString(3, pasajero.getApellido());
            stmt.setString(4, pasajero.getFechaNacimiento());
            stmt.setString(5, pasajero.getNroPasaporte());

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar pasajero: " + e.getMessage());
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
    // ACTUALIZAR PASAJERO
    // Actualiza los datos de un pasajero existente.
    // Se usa en VentanaModificacion al cambiar datos de pasajero.
    // ============================================================
    public boolean actualizar(int idPasajero, String nombre, String apellido,
                              String fechaNacimiento, String nroPasaporte) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            conexion = Conexion.getConexion();

            String sql = "UPDATE pasajeros SET nombre = ?, apellido = ?, " +
                    "fecha_nacimiento = ?, nro_pasaporte = ? WHERE id = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, fechaNacimiento);
            stmt.setString(4, nroPasaporte);
            stmt.setInt(5, idPasajero);

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar pasajero: " + e.getMessage());
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

    // Devuelve todos los pasajeros de una reserva específica.
    // Se usa en VentanaCheckin para cargar la tabla de pasajeros.
    // ============================================================
    public ArrayList<Pasajero> obtenerPorReserva(int idReserva) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Pasajero> pasajeros = new ArrayList<>();

        try {
            conexion = Conexion.getConexion();

            String sql = "SELECT * FROM pasajeros WHERE id_reserva = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idReserva);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Pasajero pasajero = new Pasajero(
                        rs.getInt("id"),
                        rs.getInt("id_reserva"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("fecha_nacimiento"),
                        rs.getString("nro_pasaporte")
                );
                pasajeros.add(pasajero);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener pasajeros: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexion.cerrarConexion(conexion);
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return pasajeros;
    }

    // ============================================================
    // VERIFICAR CHECK-IN
    // Devuelve true si el pasajero ya realizó el check-in.
    // ============================================================
    public boolean tieneCheckin(int idPasajero) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean resultado = false;

        try {
            conexion = Conexion.getConexion();
            String sql = "SELECT checkin FROM pasajeros WHERE id = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idPasajero);
            rs = stmt.executeQuery();
            if (rs.next()) {
                resultado = rs.getInt("checkin") == 1;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar check-in: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexion.cerrarConexion(conexion);
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return resultado;
    }

    // ============================================================
    // MARCAR CHECK-IN
    // Marca el check-in de un pasajero como realizado.
    // ============================================================
    public boolean marcarCheckin(int idPasajero) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            conexion = Conexion.getConexion();
            String sql = "UPDATE pasajeros SET checkin = 1 WHERE id = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idPasajero);
            exito = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al marcar check-in: " + e.getMessage());
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
    // GUARDAR ASIENTO
    // Guarda el número de asiento asignado al hacer check-in.
    // ============================================================
    public boolean guardarAsiento(int idPasajero, String asiento) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            conexion = Conexion.getConexion();
            String sql = "UPDATE pasajeros SET asiento = ? WHERE id = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, asiento);
            stmt.setInt(2, idPasajero);
            exito = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar asiento: " + e.getMessage());
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
    // OBTENER ASIENTO
    // Devuelve el asiento asignado a un pasajero.
    // ============================================================
    public String obtenerAsiento(int idPasajero) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String asiento = null;

        try {
            conexion = Conexion.getConexion();
            String sql = "SELECT asiento FROM pasajeros WHERE id = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idPasajero);
            rs = stmt.executeQuery();
            if (rs.next()) {
                asiento = rs.getString("asiento");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener asiento: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexion.cerrarConexion(conexion);
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return asiento;
    }
}