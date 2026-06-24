package vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.UsuarioDAO;
import modelo.Usuario;
import servicios.ServicioEmail;

public class VentanaRegistro extends JFrame {

    // --- Componentes ---
    private JTextField campNombre;
    private JTextField campApellido;
    private JTextField campTelefono;
    private JTextField campEmail;
    private JPasswordField campContrasena;
    private JPasswordField campConfirmarContrasena;
    private JCheckBox checkTerminos;
    private JButton btnGuardar;
    private JButton btnVolver;
    private JLabel labelMensaje;

    // --- Colores Mirrors Airlines ---
    private static final Color AZUL_OSCURO = new Color(26, 45, 90);
    private static final Color AZUL_BTN    = new Color(30, 100, 200);
    private static final Color GRIS_PANEL  = new Color(220, 220, 220);
    private static final Color BLANCO      = Color.WHITE;
    private static final Color ROJO_ERROR  = new Color(180, 30, 30);

    // --- Constructor ---
    public VentanaRegistro() {
        setTitle("Mirrors Airlines - Crear Cuenta");
        setSize(780, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        iniciarComponentes();

        setVisible(true);
    }

    private void iniciarComponentes() {

        // Panel principal blanco
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(BLANCO);

        // ============================================================
        // BARRA SUPERIOR — igual que VentanaLogin
        // ============================================================
        JPanel barraTop = new JPanel();
        barraTop.setLayout(null);
        barraTop.setBounds(0, 0, 780, 55);
        barraTop.setBackground(GRIS_PANEL);
        barraTop.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, AZUL_OSCURO));

        JLabel labelM = new JLabel("m");
        labelM.setBounds(18, 8, 30, 40);
        labelM.setFont(new Font("Serif", Font.ITALIC, 32));
        labelM.setForeground(AZUL_OSCURO);

        JLabel labelIrrors = new JLabel("IRRORS");
        labelIrrors.setBounds(42, 10, 80, 20);
        labelIrrors.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelIrrors.setForeground(AZUL_OSCURO);

        JLabel labelAirlines = new JLabel("AIRLINES");
        labelAirlines.setBounds(18, 30, 80, 18);
        labelAirlines.setFont(new Font("SansSerif", Font.PLAIN, 11));
        labelAirlines.setForeground(AZUL_OSCURO);

        barraTop.add(labelM);
        barraTop.add(labelIrrors);
        barraTop.add(labelAirlines);

        // ============================================================
        // PANEL CENTRAL — formulario
        // ============================================================
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(null);
        panelCentral.setBounds(130, 80, 510, 300);
        panelCentral.setBackground(GRIS_PANEL);
        panelCentral.setBorder(BorderFactory.createLineBorder(AZUL_OSCURO, 1));

        // --- Título ---
        JLabel labelTitulo = new JLabel("CREAR CUENTA NUEVA");
        labelTitulo.setBounds(170, 18, 220, 20);
        labelTitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        labelTitulo.setForeground(AZUL_OSCURO);

        // --- Columna izquierda ---
        JLabel labelInfoContacto = new JLabel("INFORMACIÓN DE CONTACTO");
        labelInfoContacto.setBounds(20, 45, 210, 15);
        labelInfoContacto.setFont(new Font("SansSerif", Font.BOLD, 9));
        labelInfoContacto.setForeground(AZUL_OSCURO);

        campNombre = new JTextField();
        campNombre.setBounds(20, 65, 185, 28);
        campNombre.setFont(new Font("SansSerif", Font.PLAIN, 11));
        campNombre.setForeground(Color.GRAY);
        campNombre.setText("NOMBRE");
        campNombre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        campNombre.setBackground(BLANCO);

        campApellido = new JTextField();
        campApellido.setBounds(20, 105, 185, 28);
        campApellido.setFont(new Font("SansSerif", Font.PLAIN, 11));
        campApellido.setForeground(Color.GRAY);
        campApellido.setText("APELLIDO");
        campApellido.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        campApellido.setBackground(BLANCO);

        campTelefono = new JTextField();
        campTelefono.setBounds(20, 145, 185, 28);
        campTelefono.setFont(new Font("SansSerif", Font.PLAIN, 11));
        campTelefono.setForeground(Color.GRAY);
        campTelefono.setText("TELÉFONO");
        campTelefono.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        campTelefono.setBackground(BLANCO);

        // --- Columna derecha ---
        JLabel labelInfoUsuario = new JLabel("INFORMACIÓN DE USUARIO");
        labelInfoUsuario.setBounds(295, 45, 210, 15);
        labelInfoUsuario.setFont(new Font("SansSerif", Font.BOLD, 9));
        labelInfoUsuario.setForeground(AZUL_OSCURO);

        campEmail = new JTextField();
        campEmail.setBounds(295, 65, 195, 28);
        campEmail.setFont(new Font("SansSerif", Font.PLAIN, 11));
        campEmail.setForeground(Color.GRAY);
        campEmail.setText("CORREO ELECTRÓNICO");
        campEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        campEmail.setBackground(BLANCO);

        campContrasena = new JPasswordField();
        campContrasena.setBounds(295, 105, 195, 28);
        campContrasena.setFont(new Font("SansSerif", Font.PLAIN, 11));
        campContrasena.setForeground(Color.GRAY);
        campContrasena.setEchoChar((char) 0);
        campContrasena.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        campContrasena.setBackground(BLANCO);

        JLabel labelPlaceholderPass = new JLabel("CONTRASEÑA");
        labelPlaceholderPass.setBounds(303, 107, 120, 24);
        labelPlaceholderPass.setFont(new Font("SansSerif", Font.PLAIN, 11));
        labelPlaceholderPass.setForeground(Color.GRAY);

        campConfirmarContrasena = new JPasswordField();
        campConfirmarContrasena.setBounds(295, 145, 195, 28);
        campConfirmarContrasena.setFont(new Font("SansSerif", Font.PLAIN, 11));
        campConfirmarContrasena.setForeground(Color.GRAY);
        campConfirmarContrasena.setEchoChar((char) 0);
        campConfirmarContrasena.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        campConfirmarContrasena.setBackground(BLANCO);

        JLabel labelPlaceholderConf = new JLabel("CONFIRMAR CONTRASEÑA");
        labelPlaceholderConf.setBounds(303, 147, 180, 24);
        labelPlaceholderConf.setFont(new Font("SansSerif", Font.PLAIN, 11));
        labelPlaceholderConf.setForeground(Color.GRAY);

        // --- Checkbox términos y condiciones ---
        checkTerminos = new JCheckBox("ACEPTO TÉRMINOS Y CONDICIONES");
        checkTerminos.setBounds(20, 180, 260, 20);
        checkTerminos.setFont(new Font("SansSerif", Font.PLAIN, 10));
        checkTerminos.setForeground(AZUL_OSCURO);
        checkTerminos.setBackground(GRIS_PANEL);
        checkTerminos.setOpaque(false);

        // --- Botón GUARDAR ---
        btnGuardar = new JButton("GUARDAR");
        btnGuardar.setBounds(350, 210, 140, 30);
        btnGuardar.setBackground(AZUL_BTN);
        btnGuardar.setForeground(BLANCO);
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // --- Botón VOLVER ---
        btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(350, 250, 140, 30);
        btnVolver.setBackground(AZUL_OSCURO);
        btnVolver.setForeground(BLANCO);
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnVolver.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // --- Label mensaje error ---
        labelMensaje = new JLabel("");
        labelMensaje.setBounds(20, 270, 480, 20);
        labelMensaje.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelMensaje.setForeground(ROJO_ERROR);

        // --- Agregamos todo al panel central ---
        panelCentral.add(labelTitulo);
        panelCentral.add(labelInfoContacto);
        panelCentral.add(labelInfoUsuario);
        panelCentral.add(campNombre);
        panelCentral.add(campApellido);
        panelCentral.add(campTelefono);
        panelCentral.add(campEmail);
        panelCentral.add(campContrasena);
        panelCentral.add(labelPlaceholderPass);
        panelCentral.add(campConfirmarContrasena);
        panelCentral.add(labelPlaceholderConf);
        panelCentral.add(checkTerminos);
        panelCentral.add(btnGuardar);
        panelCentral.add(btnVolver);
        panelCentral.add(labelMensaje);

        // ============================================================
        // EVENTOS
        // ============================================================

        // Botón GUARDAR
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombre    = campNombre.getText().trim();
                String apellido  = campApellido.getText().trim();
                String telefono  = campTelefono.getText().trim();
                String email     = campEmail.getText().trim();
                String contrasena        = new String(campContrasena.getPassword()).trim();
                String confirmarContrasena = new String(campConfirmarContrasena.getPassword()).trim();

                // Validación 1: ningún campo vacío ni con placeholder
                if (nombre.isEmpty()   || nombre.equals("NOMBRE")    ||
                        apellido.isEmpty() || apellido.equals("APELLIDO") ||
                        telefono.isEmpty() || telefono.equals("TELÉFONO") ||
                        email.isEmpty()    || email.equals("CORREO ELECTRÓNICO") ||
                        contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
                    labelMensaje.setText("Por favor completá todos los campos.");
                    return;
                }

                // Validación 2: nombre y apellido solo letras
                if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    labelMensaje.setText("El nombre solo puede contener letras.");
                    return;
                }
                if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    labelMensaje.setText("El apellido solo puede contener letras.");
                    return;
                }

                // Validación 3: teléfono solo números
                if (!telefono.matches("\\d+")) {
                    labelMensaje.setText("El teléfono solo puede contener números.");
                    return;
                }

                // Validación 3: las contraseñas deben coincidir
                if (!contrasena.equals(confirmarContrasena)) {
                    labelMensaje.setText("Las contraseñas no coinciden.");
                    return;
                }

                // Validación 4: formato de email válido
                if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    labelMensaje.setText("El email no tiene un formato válido.");
                    return;
                }

                // Validación 5: contraseña de al menos 6 caracteres
                if (contrasena.length() < 6) {
                    labelMensaje.setText("La contraseña debe tener al menos 6 caracteres.");
                    return;
                }

                // Validación 6: debe aceptar los términos y condiciones
                if (!checkTerminos.isSelected()) {
                    labelMensaje.setText("Debés aceptar los términos y condiciones.");
                    return;
                }

                // Validación 7: email no repetido
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                if (usuarioDAO.emailExiste(email)) {
                    labelMensaje.setText("El email ya está registrado. Usá otro.");
                    return;
                }

                // Todo OK — guardamos el usuario en la BD
                Usuario nuevoUsuario = new Usuario(nombre, apellido, email, contrasena, telefono);
                boolean exito = usuarioDAO.registrar(nuevoUsuario);

                if (exito) {
                    // Mandamos el email de bienvenida
                    ServicioEmail.enviarConfirmacionRegistro(email, nombre);
                    new VentanaLogin();
                    dispose();
                } else {
                    labelMensaje.setText("Error al registrar. Intentá de nuevo.");
                }
            }
        });

        // Botón VOLVER
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaLogin();
                dispose();
            }
        });

        // --- Agregamos al panel principal ---
        panel.add(barraTop);
        panel.add(panelCentral);

        add(panel);
    }
}