package dao;

import modelo.Vuelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// ============================================================
//  CLASE: VueloDAO
//  Maneja todas las operaciones de la tabla "vuelos"
//  en la base de datos.
// ============================================================

public class VueloDAO {

    // ============================================================
    // BUSCAR VUELOS
    // Busca vuelos según origen, destino y fecha.
    // Devuelve una lista de vuelos que coinciden con la búsqueda.
    // Se usa en VentanaBusqueda al presionar BUSCAR.
    // ============================================================
    public ArrayList<Vuelo> buscarVuelos(String origen, String destino, String fecha) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Vuelo> vuelos = new ArrayList<>();

        try {
            conexion = Conexion.getConexion();

            // Buscamos vuelos que coincidan con origen, destino
            // y cuya fecha de salida empiece con la fecha buscada
            String sql = "SELECT * FROM vuelos WHERE origen = ? AND destino = ? " +
                    "AND DATE(fecha_salida) = ? AND asientos_disponibles > 0 " +
                    "AND estado != 'CANCELADO'";
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, origen);
            stmt.setString(2, destino);
            stmt.setString(3, fecha);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Vuelo vuelo = new Vuelo(
                        rs.getInt("id"),
                        rs.getString("aerolinea"),
                        rs.getString("origen"),
                        rs.getString("destino"),
                        rs.getString("fecha_salida"),
                        rs.getString("fecha_llegada"),
                        rs.getDouble("precio"),
                        rs.getInt("asientos_disponibles"),
                        rs.getString("estado")
                );
                vuelos.add(vuelo);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar vuelos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexion.cerrarConexion(conexion);
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return vuelos;
    }

    // ============================================================
    // OBTENER VUELO POR ID
    // Devuelve un vuelo específico buscándolo por su ID.
    // Se usa al cargar los detalles de una reserva.
    // ============================================================
    public Vuelo obtenerPorId(int id) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Vuelo vuelo = null;

        try {
            conexion = Conexion.getConexion();

            String sql = "SELECT * FROM vuelos WHERE id = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                vuelo = new Vuelo(
                        rs.getInt("id"),
                        rs.getString("aerolinea"),
                        rs.getString("origen"),
                        rs.getString("destino"),
                        rs.getString("fecha_salida"),
                        rs.getString("fecha_llegada"),
                        rs.getDouble("precio"),
                        rs.getInt("asientos_disponibles"),
                        rs.getString("estado")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener vuelo: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                Conexion.cerrarConexion(conexion);
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return vuelo;
    }

    // ============================================================
    // ACTUALIZAR ESTADO
    // Cambia el estado de un vuelo (A TIEMPO / RETRASADO / CANCELADO)
    // Cuando se actualiza, se dispara el email de notificación
    // a todos los usuarios con reservas en ese vuelo.
    // ============================================================
    public boolean actualizarEstado(int idVuelo, String nuevoEstado) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            conexion = Conexion.getConexion();

            String sql = "UPDATE vuelos SET estado = ? WHERE id = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idVuelo);

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar estado: " + e.getMessage());
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
    // REDUCIR ASIENTOS DISPONIBLES
    // Resta 1 asiento disponible cuando se confirma una reserva.
    // Se llama desde ReservaDAO al guardar una reserva nueva.
    // ============================================================
    public boolean reducirAsiento(int idVuelo) {
        Connection conexion = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            conexion = Conexion.getConexion();

            String sql = "UPDATE vuelos SET asientos_disponibles = asientos_disponibles - 1 " +
                    "WHERE id = ? AND asientos_disponibles > 0";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idVuelo);

            int filasAfectadas = stmt.executeUpdate();
            exito = filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al reducir asiento: " + e.getMessage());
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