package modelo;

// ============================================================
//  CLASE: Reserva
//  Es la clase central del sistema: une un Usuario con un Vuelo.
//  Una reserva puede tener varios Pasajeros asociados.
//  Sus atributos coinciden con la tabla "reservas" de la BD.
// ============================================================

import java.util.ArrayList;

public class Reserva {

    // --- Atributos ---
    private int id;
    private int idUsuario;
    private int idVuelo;
    private String fechaReserva;  // ej: "2026-07-15 10:30"
    private String estado;        // "PENDIENTE", "CONFIRMADA" o "CANCELADA"
    private double total;

    // Lista de pasajeros asociados a esta reserva
    // ArrayList es lo que ya viste en clase — puede crecer dinámicamente
    private ArrayList<Pasajero> pasajeros;

    // --- Constructor completo (usado al cargar desde la BD) ---
    public Reserva(int id, int idUsuario, int idVuelo,
                   String fechaReserva, String estado, double total) {
        this.id           = id;
        this.idUsuario    = idUsuario;
        this.idVuelo      = idVuelo;
        this.fechaReserva = fechaReserva;
        this.estado       = estado;
        this.total        = total;
        this.pasajeros    = new ArrayList<>();  // empieza vacía, se llena después
    }

    // --- Constructor sin ID (usado al crear una reserva nueva) ---
    public Reserva(int idUsuario, int idVuelo, String estado, double total) {
        this.idUsuario = idUsuario;
        this.idVuelo   = idVuelo;
        this.estado    = estado;
        this.total     = total;
        this.pasajeros = new ArrayList<>();
    }

    // --- Metodo para agregar un pasajero a la reserva ---
    public void agregarPasajero(Pasajero pasajero) {
        pasajeros.add(pasajero);
    }

    // --- Metodo para obtener cuántos pasajeros tiene la reserva ---
    public int getCantidadPasajeros() {
        return pasajeros.size();
    }

    // --- Getters ---
    public int getId()                        { return id; }
    public int getIdUsuario()                 { return idUsuario; }
    public int getIdVuelo()                   { return idVuelo; }
    public String getFechaReserva()           { return fechaReserva; }
    public String getEstado()                 { return estado; }
    public double getTotal()                  { return total; }
    public ArrayList<Pasajero> getPasajeros() { return pasajeros; }

    // --- Setters ---
    public void setId(int id)                               { this.id = id; }
    public void setIdUsuario(int idUsuario)                 { this.idUsuario = idUsuario; }
    public void setIdVuelo(int idVuelo)                     { this.idVuelo = idVuelo; }
    public void setFechaReserva(String fechaReserva)        { this.fechaReserva = fechaReserva; }
    public void setEstado(String estado)                    { this.estado = estado; }
    public void setTotal(double total)                      { this.total = total; }

    // --- toString ---
    @Override
    public String toString() {
        return "Reserva{id=" + id + ", vuelo=" + idVuelo +
                ", estado='" + estado + "', total=$" + total +
                ", pasajeros=" + pasajeros.size() + "}";
    }
}