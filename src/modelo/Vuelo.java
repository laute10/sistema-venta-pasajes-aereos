package modelo;

// ============================================================
//  CLASE: Vuelo
//  Representa un vuelo disponible en el sistema.
//  Sus atributos coinciden con la tabla "vuelos" de la BD.
// ============================================================

public class Vuelo {

    // --- Atributos ---
    private int id;
    private String aerolinea;
    private String origen;
    private String destino;
    private String fechaSalida;   // Guardamos como String por ahora (ej: "2026-08-01 07:00")
    private String fechaLlegada;  // Cuando veamos JDBC usaremos tipos de fecha de Java
    private double precio;
    private int asientosDisponibles;
    private String estado;        // "A TIEMPO", "RETRASADO" o "CANCELADO"

    // --- Constructor completo (usado al cargar desde la BD) ---
    public Vuelo(int id, String aerolinea, String origen, String destino,
                 String fechaSalida, String fechaLlegada,
                 double precio, int asientosDisponibles, String estado) {
        this.id                  = id;
        this.aerolinea           = aerolinea;
        this.origen              = origen;
        this.destino             = destino;
        this.fechaSalida         = fechaSalida;
        this.fechaLlegada        = fechaLlegada;
        this.precio              = precio;
        this.asientosDisponibles = asientosDisponibles;
        this.estado              = estado;
    }

    // --- Constructor sin ID (usado al cargar un vuelo nuevo) ---
    public Vuelo(String aerolinea, String origen, String destino,
                 String fechaSalida, String fechaLlegada,
                 double precio, int asientosDisponibles, String estado) {
        this.aerolinea           = aerolinea;
        this.origen              = origen;
        this.destino             = destino;
        this.fechaSalida         = fechaSalida;
        this.fechaLlegada        = fechaLlegada;
        this.precio              = precio;
        this.asientosDisponibles = asientosDisponibles;
        this.estado              = estado;
    }

    // --- Getters ---
    public int getId()                    { return id; }
    public String getAerolinea()          { return aerolinea; }
    public String getOrigen()             { return origen; }
    public String getDestino()            { return destino; }
    public String getFechaSalida()        { return fechaSalida; }
    public String getFechaLlegada()       { return fechaLlegada; }
    public double getPrecio()             { return precio; }
    public int getAsientosDisponibles()   { return asientosDisponibles; }
    public String getEstado()             { return estado; }

    // --- Setters ---
    public void setId(int id)                               { this.id = id; }
    public void setAerolinea(String aerolinea)               { this.aerolinea = aerolinea; }
    public void setOrigen(String origen)                     { this.origen = origen; }
    public void setDestino(String destino)                   { this.destino = destino; }
    public void setFechaSalida(String fechaSalida)           { this.fechaSalida = fechaSalida; }
    public void setFechaLlegada(String fechaLlegada)         { this.fechaLlegada = fechaLlegada; }
    public void setPrecio(double precio)                     { this.precio = precio; }
    public void setAsientosDisponibles(int asientos)         { this.asientosDisponibles = asientos; }
    public void setEstado(String estado)                     { this.estado = estado; }

    // --- toString ---
    // Muy útil para mostrar el vuelo en una JList o JComboBox más adelante
    @Override
    public String toString() {
        return aerolinea + " | " + origen + " → " + destino +
                " | " + fechaSalida + " | $" + precio + " | " + estado;
    }
}