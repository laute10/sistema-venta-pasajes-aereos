package dao;

import modelo.Reserva;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

// ============================================================
//  CLASE: ReservaDAO
//  Maneja todas las operaciones de la tabla "reservas"
//  en la base de datos.
// ============================================================

public class ReservaDAO {

    // ============================================================
    // GUARDAR RESERVA
    // Inserta una reserva nueva en la tabla.
    // Devuelve el ID generado por MySQL, o -1 si falló.
    // Se usa en VentanaPago al confirmar el pago.
    // ============================================================
    public int guardar(Reserva reserva) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int idGenerado = -1;

        try {
            conexion = Conexion.getConexion();

            String sql = "INSERT INTO reservas (id_usuario, id_vuelo, estado, total) " +
                    "VALUES (?, ?, ?, ?)";

            // RETURN_GENERATED_KEYS le dice a JDBC que nos devuelva el ID generado
            stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, reserva.getIdUsuario());
            stmt.setInt(2, reserva.getIdVuelo());
            stmt.setString(3, reserva.getEstado());
            stmt.setDouble(4, reserva.getTotal());

            stmt.executeUpdate();

            // Obtenemos el ID que MySQL le asignó a la reserva
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error al guardar reserva: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexion.cerrarConexion(conexion);
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return idGenerado;
    }

    // ============================================================
    // OBTENER RESERVAS POR USUARIO
    // Devuelve todas las reservas de un usuario específico.
    // Se usa en VentanaMisReservas para llenar la tabla.
    // ============================================================
    public ArrayList<Reserva> obtenerPorUsuario(int idUsuario) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Reserva> reservas = new ArrayList<>();

        try {
            conexion = Conexion.getConexion();

            String sql = "SELECT * FROM reservas WHERE id_usuario = ? ORDER BY fecha_reserva DESC";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idUsuario);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva(
                        rs.getInt("id"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_vuelo"),
                        rs.getString("fecha_reserva"),
                        rs.getString("estado"),
                        rs.getDouble("total")
                );
                reservas.add(reserva);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener reservas: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexion.cerrarConexion(conexion);
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return reservas;
    }

    // ============================================================
    // CANCELAR RESERVA
    // Cambia el estado de una reserva a CANCELADA.
    // Se usa en VentanaMisReservas al confirmar la cancelación.
    // ============================================================
    public boolean cancelar(int idReserva) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            conexion = Conexion.getConexion();

            String sql = "UPDATE reservas SET estado = 'CANCELADA' WHERE id = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idReserva);

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al cancelar reserva: " + e.getMessage());
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
    // CONFIRMAR RESERVA
    // Cambia el estado de una reserva a CONFIRMADA.
    // Se llama después de que el pago es aprobado.
    // ============================================================
    public boolean confirmar(int idReserva) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            conexion = Conexion.getConexion();

            String sql = "UPDATE reservas SET estado = 'CONFIRMADA' WHERE id = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idReserva);

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al confirmar reserva: " + e.getMessage());
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