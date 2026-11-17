package servicios;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

// ============================================================
//  CLASE: Configuracion
//  Lee los datos sensibles (contraseña de MySQL y de Gmail)
//  desde el archivo config.properties, que está en la raíz
//  del proyecto y NO se sube a GitHub (está en .gitignore).
//
//  Para crearlo, copiá config.example.properties, renombralo
//  a config.properties y completá tus datos.
// ============================================================

public class Configuracion {

    private static final String ARCHIVO = "config.properties";
    private static final Properties props = new Properties();

    // Se carga una sola vez, la primera vez que se usa la clase
    static {
        // Se lee en UTF-8 para que funcionen tildes y la ñ
        try (Reader entrada = new InputStreamReader(new FileInputStream(ARCHIVO), StandardCharsets.UTF_8)) {
            props.load(entrada);
        } catch (IOException e) {
            System.out.println("No se encontró " + ARCHIVO + ". "
                    + "Copiá config.example.properties como config.properties y completá tus datos.");
        }
    }

    // Devuelve el valor de la clave, o el valor por defecto si no está cargado
    public static String get(String clave, String porDefecto) {
        String valor = props.getProperty(clave);
        if (valor == null || valor.isBlank()) {
            return porDefecto;
        }
        return valor.trim();
    }
}
