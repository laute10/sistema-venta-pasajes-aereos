package modelo;

// ============================================================
//  CLASE: Pago
//  Representa el pago de una reserva.
//  Sus atributos coinciden con la tabla "pagos" de la BD.
// ============================================================

public class Pago {

    // --- Atributos ---
    private int id;
    private int idReserva;
    private String metodoPago;  // "TARJETA" o "TRANSFERENCIA"
    private double monto;
    private String fechaPago;   // ej: "2026-07-15 10:35"
    private String estado;      // "APROBADO" o "RECHAZADO"

    // --- Constructor completo (usado al cargar desde la BD) ---
    public Pago(int id, int idReserva, String metodoPago,
                double monto, String fechaPago, String estado) {
        this.id         = id;
        this.idReserva  = idReserva;
        this.metodoPago = metodoPago;
        this.monto      = monto;
        this.fechaPago  = fechaPago;
        this.estado     = estado;
    }

    // --- Constructor sin ID (usado al registrar un pago nuevo) ---
    public Pago(int idReserva, String metodoPago, double monto, String estado) {
        this.idReserva  = idReserva;
        this.metodoPago = metodoPago;
        this.monto      = monto;
        this.estado     = estado;
    }

    // --- Getters ---
    public int getId()            { return id; }
    public int getIdReserva()     { return idReserva; }
    public String getMetodoPago() { return metodoPago; }
    public double getMonto()      { return monto; }
    public String getFechaPago()  { return fechaPago; }
    public String getEstado()     { return estado; }

    // --- Setters ---
    public void setId(int id)                     { this.id = id; }
    public void setIdReserva(int idReserva)        { this.idReserva = idReserva; }
    public void setMetodoPago(String metodoPago)   { this.metodoPago = metodoPago; }
    public void setMonto(double monto)             { this.monto = monto; }
    public void setFechaPago(String fechaPago)     { this.fechaPago = fechaPago; }
    public void setEstado(String estado)           { this.estado = estado; }

    // --- toString ---
    @Override
    public String toString() {
        return "Pago{id=" + id + ", reserva=" + idReserva +
                ", metodo='" + metodoPago + "', monto=$" + monto +
                ", estado='" + estado + "'}";
    }
}
