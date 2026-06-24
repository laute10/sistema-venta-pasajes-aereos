package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// ============================================================
//  CLASE: Conexion
//  Su única responsabilidad es abrir y cerrar la conexión
//  con la base de datos MySQL.
//  Todos los DAOs la usan para obtener una conexión.
// ============================================================

public class Conexion {

    // --- Datos de conexión ---

    private static final String URL      = "jdbc:mysql://localhost:3306/venta_pasajes";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = "44158776Le.";

    // --- Método para abrir la conexión ---
    public static Connection getConexion() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return conexion;
    }

    // --- Método para cerrar la conexión ---
    // Siempre hay que cerrar la conexión cuando terminamos de usarla
    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}