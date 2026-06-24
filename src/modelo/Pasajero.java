package modelo;

// ============================================================
//  CLASE: Pasajero
//  Representa a una persona que viaja en una reserva.
//  Una reserva puede tener varios pasajeros.
//  Sus atributos coinciden con la tabla "pasajeros" de la BD.
// ============================================================

public class Pasajero {

    // --- Atributos ---
    private int id;
    private int idReserva;         // a qué reserva pertenece este pasajero
    private String nombre;
    private String apellido;
    private String fechaNacimiento; // guardamos como String por ahora (ej: "1990-05-20")
    private String nroPasaporte;

    // --- Constructor completo (usado al cargar desde la BD) ---
    public Pasajero(int id, int idReserva, String nombre, String apellido,
                    String fechaNacimiento, String nroPasaporte) {
        this.id               = id;
        this.idReserva        = idReserva;
        this.nombre           = nombre;
        this.apellido         = apellido;
        this.fechaNacimiento  = fechaNacimiento;
        this.nroPasaporte     = nroPasaporte;
    }

    // --- Constructor sin ID (usado al crear un pasajero nuevo) ---
    public Pasajero(int idReserva, String nombre, String apellido,
                    String fechaNacimiento, String nroPasaporte) {
        this.idReserva        = idReserva;
        this.nombre           = nombre;
        this.apellido         = apellido;
        this.fechaNacimiento  = fechaNacimiento;
        this.nroPasaporte     = nroPasaporte;
    }

    // --- Getters ---
    public int getId()                  { return id; }
    public int getIdReserva()           { return idReserva; }
    public String getNombre()           { return nombre; }
    public String getApellido()         { return apellido; }
    public String getFechaNacimiento()  { return fechaNacimiento; }
    public String getNroPasaporte()     { return nroPasaporte; }

    // --- Setters ---
    public void setId(int id)                              { this.id = id; }
    public void setIdReserva(int idReserva)                { this.idReserva = idReserva; }
    public void setNombre(String nombre)                   { this.nombre = nombre; }
    public void setApellido(String apellido)               { this.apellido = apellido; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setNroPasaporte(String nroPasaporte)       { this.nroPasaporte = nroPasaporte; }

    // --- toString ---
    @Override
    public String toString() {
        return apellido + ", " + nombre + " (Pasaporte: " + nroPasaporte + ")";
    }
}