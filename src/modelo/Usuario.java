package modelo;

// ============================================================
//  CLASE: Usuario
//  Representa a una persona registrada en el sistema.
//  Sus atributos coinciden exactamente con la tabla "usuarios"
//  de la base de datos.
// ============================================================

public class Usuario {

    // --- Atributos ---
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasena;
    private String telefono;

    // --- Constructor completo (usado al cargar desde la BD) ---
    public Usuario(int id, String nombre, String apellido, String email, String contrasena, String telefono) {
        this.id         = id;
        this.nombre     = nombre;
        this.apellido   = apellido;
        this.email      = email;
        this.contrasena = contrasena;
        this.telefono   = telefono;
    }

    // --- Constructor sin ID (usado al registrar un usuario nuevo) ---
    // Cuando insertamos en la BD, el ID lo genera MySQL automáticamente
    public Usuario(String nombre, String apellido, String email, String contrasena, String telefono) {
        this.nombre     = nombre;
        this.apellido   = apellido;
        this.email      = email;
        this.contrasena = contrasena;
        this.telefono   = telefono;
    }

    // --- Getters ---
    public int getId()            { return id; }
    public String getNombre()     { return nombre; }
    public String getApellido()   { return apellido; }
    public String getEmail()      { return email; }
    public String getContrasena() { return contrasena; }
    public String getTelefono()   { return telefono; }

    // --- Setters ---
    public void setId(int id)                   { this.id = id; }
    public void setNombre(String nombre)         { this.nombre = nombre; }
    public void setApellido(String apellido)     { this.apellido = apellido; }
    public void setEmail(String email)           { this.email = email; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setTelefono(String telefono)     { this.telefono = telefono; }

    // --- toString ---
    // Útil para mostrar el usuario en consola mientras desarrollamos
    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nombre='" + nombre + "', apellido='" + apellido + "', email='" + email + "'}";
    }
}