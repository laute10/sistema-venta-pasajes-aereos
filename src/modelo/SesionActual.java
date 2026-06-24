package modelo;

// ============================================================
//  CLASE: SesionActual
//  Guarda el usuario que está logueado mientras la app corre.
//  Al cerrar sesión se limpia.
//  Es una clase con atributos estáticos — no necesita
//  instanciarse, se accede directamente con SesionActual.usuario
// ============================================================

public class SesionActual {

    // El usuario logueado actualmente
    // Es estático para que sea accesible desde cualquier ventana
    public static Usuario usuario = null;

    // --- Inicia la sesión con el usuario que se logueó ---
    public static void iniciar(Usuario u) {
        usuario = u;
    }

    // --- Cierra la sesión limpiando el usuario ---
    public static void cerrar() {
        usuario = null;
    }

    // --- Verifica si hay una sesión activa ---
    public static boolean haySession() {
        return usuario != null;
    }
}