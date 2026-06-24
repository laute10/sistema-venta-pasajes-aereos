package modelo;

// ============================================================
//  CLASE: Politicas
//  Centraliza todos los valores de las políticas de la empresa.
//  Al tener los números acá, si mañana cambia alguno
//  solo lo modificamos en un lugar y aplica a todo el sistema.
// ============================================================

public class Politicas {

    // --- Cancelación ---
    public static final double CARGO_CANCELACION    = 0.30; // 30%
    public static final double REEMBOLSO_CANCELACION = 0.70; // 70%
    public static final int    HORAS_LIMITE          = 48;   // horas antes del vuelo

    // --- Cambio de pasajero ---
    public static final double CARGO_CAMBIO_PASAJERO = 0.02; // 2% del valor del pasaje

    // --- Cambio de vuelo ---
    public static final double CARGO_CAMBIO_VUELO = 0.10; // 10% del vuelo original
    // Si el nuevo vuelo es más caro: 10% + diferencia
    // Si el nuevo vuelo es más barato: solo 10%, sin reembolso

    // --- Cambio de asiento ---
    // Los cargos están en pesos, según las categorías disponibles
    public static final double ECONOMICA_A_EJECUTIVA      =  8000.00;
    public static final double ECONOMICA_A_PRIMERA        = 20000.00;
    public static final double EJECUTIVA_A_PRIMERA        = 12000.00;
    public static final double EJECUTIVA_A_ECONOMICA      =  3000.00;
    public static final double PRIMERA_A_EJECUTIVA        =  3000.00;
    public static final double PRIMERA_A_ECONOMICA        =  6000.00;

    // --- Método para obtener el cargo de cambio de asiento ---
    // Recibe la categoría actual y la nueva, devuelve el cargo correspondiente
    // Lo vamos a usar cuando conectemos la lógica de VentanaModificacion
    public static double getCargoCambioAsiento(String categoriaActual, String categoriaNueva) {

        if (categoriaActual.equals("ECONOMICA")) {
            if (categoriaNueva.equals("EJECUTIVA"))    return ECONOMICA_A_EJECUTIVA;
            if (categoriaNueva.equals("PRIMERA CLASE")) return ECONOMICA_A_PRIMERA;
        }

        if (categoriaActual.equals("EJECUTIVA")) {
            if (categoriaNueva.equals("PRIMERA CLASE")) return EJECUTIVA_A_PRIMERA;
            if (categoriaNueva.equals("ECONOMICA"))    return EJECUTIVA_A_ECONOMICA;
        }

        if (categoriaActual.equals("PRIMERA CLASE")) {
            if (categoriaNueva.equals("EJECUTIVA"))    return PRIMERA_A_EJECUTIVA;
            if (categoriaNueva.equals("ECONOMICA"))    return PRIMERA_A_ECONOMICA;
        }

        return 0; // misma categoría, sin cargo
    }
}