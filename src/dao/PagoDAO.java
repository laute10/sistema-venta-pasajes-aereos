package dao;

import modelo.Pago;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// ============================================================
//  CLASE: PagoDAO
//  Maneja todas las operaciones de la tabla "pagos"
//  en la base de datos.
// ============================================================

public class PagoDAO {

    // ============================================================
    // GUARDAR PAGO
    // Inserta un pago nuevo asociado a una reserva.
    // Devuelve true si se guardó correctamente.
    // Se llama desde VentanaPago al confirmar el pago,
    // después de guardar la reserva y los pasajeros.
    // ============================================================
    public boolean guardar(Pago pago) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            conexion = Conexion.getConexion();

            String sql = "INSERT INTO pagos (id_reserva, metodo_pago, monto, estado) " +
                    "VALUES (?, ?, ?, ?)";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, pago.getIdReserva());
            stmt.setString(2, pago.getMetodoPago());
            stmt.setDouble(3, pago.getMonto());
            stmt.setString(4, pago.getEstado());

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar pago: " + e.getMessage());
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
    // OBTENER PAGO POR RESERVA
    // Devuelve el pago asociado a una reserva.
    // Se usa en VentanaMisReservas para mostrar el método de pago.
    // ============================================================
    public Pago obtenerPorReserva(int idReserva) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Pago pago = null;

        try {
            conexion = Conexion.getConexion();

            String sql = "SELECT * FROM pagos WHERE id_reserva = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idReserva);

            rs = stmt.executeQuery();

            if (rs.next()) {
                pago = new Pago(
                        rs.getInt("id"),
                        rs.getInt("id_reserva"),
                        rs.getString("metodo_pago"),
                        rs.getDouble("monto"),
                        rs.getString("fecha_pago"),
                        rs.getString("estado")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener pago: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexion.cerrarConexion(conexion);
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return pago;
    }
}