package servicios;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class ServicioEmail {

    // --- Datos de la cuenta Gmail que envía los correos ---
    // Reemplazá estos valores con tu cuenta y contraseña de aplicación
    private static final String EMAIL_ORIGEN = "lautiespejo10@gmail.com";
    private static final String CONTRASENA   = "nuhs gude kimm gltv"; // contraseña de aplicación

    // --- Configuración del servidor de Gmail ---
    // Estos valores son siempre los mismos para Gmail, no los toques
    private static Session crearSesion() {
        Properties props = new Properties();
        props.put("mail.smtp.auth",            "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.host",            "smtp.gmail.com");
        props.put("mail.smtp.port",            "587");
        props.put("mail.smtp.ssl.trust",       "*");
        props.put("mail.smtp.ssl.protocols",   "TLSv1.2");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout",           "10000");

        // La sesión se autentica con el email y contraseña de aplicación
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_ORIGEN, CONTRASENA);
            }
        });

        return session;
    }

    // ============================================================
    // MÉTODO PRIVADO BASE
    // Los tres métodos públicos de abajo lo usan internamente
    // ============================================================
    private static void enviarEmail(String destinatario, String asunto, String cuerpo) {
        try {
            Session session = crearSesion();

            MimeMessage mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(EMAIL_ORIGEN));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject(asunto);
            mensaje.setText(cuerpo);

            Transport.send(mensaje);
            System.out.println("Email enviado a: " + destinatario);

        } catch (MessagingException e) {
            System.out.println("Error al enviar email: " + e.getMessage());
        }
    }

    // ============================================================
    // 1. CONFIRMACIÓN DE COMPRA
    // Se llama después de que el pago es aprobado
    // ============================================================
    public static void enviarConfirmacionCompra(String destinatario, String nombreUsuario,
                                                String aerolinea, String origen, String destino,
                                                String fechaSalida, int cantPasajeros,
                                                double total, String metodoPago) {
        String asunto = "Confirmación de compra - Venta de Pasajes";

        String cuerpo =
                "Hola " + nombreUsuario + ",\n\n" +
                        "Tu compra fue procesada exitosamente. Aquí están los detalles:\n\n" +
                        "============================================\n" +
                        "           RECIBO DE COMPRA\n" +
                        "============================================\n" +
                        "Aerolínea:        " + aerolinea + "\n" +
                        "Origen:           " + origen + "\n" +
                        "Destino:          " + destino + "\n" +
                        "Fecha de salida:  " + fechaSalida + "\n" +
                        "Pasajeros:        " + cantPasajeros + "\n" +
                        "--------------------------------------------\n" +
                        "Total pagado:     $" + total + "\n" +
                        "Método de pago:   " + metodoPago + "\n" +
                        "============================================\n\n" +
                        "Recordá hacer el check-in online antes de tu vuelo.\n\n" +
                        "¡Buen viaje!\n" +
                        "Equipo de Venta de Pasajes";

        enviarEmail(destinatario, asunto, cuerpo);
    }

    // ============================================================
    // 2. CONFIRMACIÓN DE REGISTRO
    // Se llama cuando un nuevo usuario crea su cuenta
    // ============================================================
    public static void enviarConfirmacionRegistro(String destinatario, String nombreUsuario) {
        String asunto = "Bienvenido a Venta de Pasajes";

        String cuerpo =
                "Hola " + nombreUsuario + ",\n\n" +
                        "Tu cuenta fue creada exitosamente.\n\n" +
                        "Ya podés iniciar sesión con tu email y contraseña para buscar\n" +
                        "vuelos, hacer reservas y gestionar tus pasajes.\n\n" +
                        "Si no creaste esta cuenta, ignorá este mensaje.\n\n" +
                        "Saludos,\n" +
                        "Equipo de Venta de Pasajes";

        enviarEmail(destinatario, asunto, cuerpo);
    }

    // ============================================================
    // 3. NOTIFICACIÓN DE CAMBIO EN VUELO
    // Se llama cuando el estado de un vuelo cambia
    // (por ejemplo, pasa de A TIEMPO a RETRASADO o CANCELADO)
    // ============================================================
    public static void enviarNotificacionCambioVuelo(String destinatario, String nombreUsuario,
                                                     String aerolinea, String origen, String destino,
                                                     String fechaSalida, String estadoAnterior,
                                                     String estadoNuevo) {
        String asunto = "Aviso importante sobre tu vuelo - " + aerolinea;

        String cuerpo =
                "Hola " + nombreUsuario + ",\n\n" +
                        "Te informamos que hubo un cambio en tu vuelo:\n\n" +
                        "============================================\n" +
                        "           AVISO DE CAMBIO DE VUELO\n" +
                        "============================================\n" +
                        "Aerolínea:        " + aerolinea + "\n" +
                        "Ruta:             " + origen + " → " + destino + "\n" +
                        "Fecha de salida:  " + fechaSalida + "\n" +
                        "--------------------------------------------\n" +
                        "Estado anterior:  " + estadoAnterior + "\n" +
                        "Estado actual:    " + estadoNuevo + "\n" +
                        "============================================\n\n" +
                        "Te recomendamos estar atento a nuevas notificaciones.\n" +
                        "Para consultas comunicate con la aerolínea.\n\n" +
                        "Saludos,\n" +
                        "Equipo de Venta de Pasajes";

        enviarEmail(destinatario, asunto, cuerpo);
    }
}