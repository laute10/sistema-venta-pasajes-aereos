package vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.UsuarioDAO;
import modelo.SesionActual;
import modelo.Usuario;
public class VentanaLogin extends JFrame {

    // --- Componentes ---
    private JTextField campEmail;
    private JPasswordField campContrasena;
    private JButton btnIngresar;
    private JButton btnRegistrarse;
    private JLabel labelMensaje;

    // --- Colores de la paleta Mirrors Airlines ---
    private static final Color AZUL_OSCURO  = new Color(26, 45, 90);   // azul marino del logo
    private static final Color GRIS_FONDO   = new Color(235, 235, 235); // fondo gris claro
    private static final Color GRIS_PANEL   = new Color(220, 220, 220); // panel central
    private static final Color BLANCO       = Color.WHITE;
    private static final Color ROJO_ERROR   = new Color(180, 30, 30);

    // --- Constructor ---
    public VentanaLogin() {
        setTitle("Mirrors Airlines - Iniciar Sesión");
        setSize(780, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        iniciarComponentes();

        setVisible(true);
    }

    private void iniciarComponentes() {

        // Panel principal con fondo gris claro
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(GRIS_FONDO);

        // ============================================================
        // BARRA SUPERIOR — logo Mirrors Airlines
        // ============================================================
        JPanel barraTop = new JPanel();
        barraTop.setLayout(null);
        barraTop.setBounds(0, 0, 780, 55);
        barraTop.setBackground(GRIS_PANEL);
        barraTop.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, AZUL_OSCURO));

        // "m" en cursiva grande
        JLabel labelM = new JLabel("m");
        labelM.setBounds(18, -6, 30, 40);
        labelM.setFont(new Font("Serif", Font.ITALIC, 32));
        labelM.setForeground(AZUL_OSCURO);

        // "IRRORS" en negrita
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
        panelCentral.setBounds(130, 80, 510, 290);
        panelCentral.setBackground(GRIS_PANEL);
        panelCentral.setBorder(BorderFactory.createLineBorder(AZUL_OSCURO, 1));

        // --- Lado izquierdo: texto de bienvenida + logo grande ---
        JLabel labelBienvenida = new JLabel("<html>INGRESA A TU<br>CUENTA EN MIRRORS</html>");
        labelBienvenida.setBounds(60, 25, 180, 40);
        labelBienvenida.setFont(new Font("SansSerif", Font.PLAIN, 11));
        labelBienvenida.setForeground(AZUL_OSCURO);

        // "m" grande cursiva decorativa
        JLabel labelLogoGrande = new JLabel("m");
        labelLogoGrande.setBounds(60, 40, 180, 160);
        labelLogoGrande.setFont(new Font("Serif", Font.ITALIC, 140));
        labelLogoGrande.setForeground(AZUL_OSCURO);

        // --- Lado derecho: logo pequeño + campos ---
        // Logo derecho
        JLabel labelMDer = new JLabel("m");
        labelMDer.setBounds(285, 10, 25, 35);
        labelMDer.setFont(new Font("Serif", Font.ITALIC, 28));
        labelMDer.setForeground(AZUL_OSCURO);

        JLabel labelIrrorsDer = new JLabel("IRRORS");
        labelIrrorsDer.setBounds(308, 22, 70, 18);
        labelIrrorsDer.setFont(new Font("SansSerif", Font.BOLD, 13));
        labelIrrorsDer.setForeground(AZUL_OSCURO);

        JLabel labelAirlinesDer = new JLabel("AIRLINES");
        labelAirlinesDer.setBounds(290, 42, 80, 15);
        labelAirlinesDer.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelAirlinesDer.setForeground(AZUL_OSCURO);

        // Campo Email
        campEmail = new JTextField();
        campEmail.setBounds(260, 80, 220, 28);
        campEmail.setFont(new Font("SansSerif", Font.PLAIN, 11));
        campEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AZUL_OSCURO, 1),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)
        ));
        campEmail.setForeground(Color.GRAY);
        campEmail.setText("CORREO ELECTRÓNICO");

        // Campo Contraseña
        campContrasena = new JPasswordField();
        campContrasena.setBounds(260, 120, 220, 28);
        campContrasena.setFont(new Font("SansSerif", Font.PLAIN, 11));
        campContrasena.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AZUL_OSCURO, 1),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)
        ));
        campContrasena.setEchoChar((char) 0); // muestra texto plano como placeholder
        campContrasena.setForeground(Color.GRAY);
        // Nota: cuando conectemos eventos, al hacer foco limpiamos el placeholder
        // y activamos el echo char para ocultar la contraseña

        // Label placeholder contraseña
        JLabel labelPlaceholderPass = new JLabel("CONTRASEÑA");
        labelPlaceholderPass.setBounds(268, 122, 120, 24);
        labelPlaceholderPass.setFont(new Font("SansSerif", Font.PLAIN, 11));
        labelPlaceholderPass.setForeground(Color.GRAY);

        // Botón INICIAR SESIÓN
        btnIngresar = new JButton("INICIAR SESIÓN");
        btnIngresar.setBounds(350, 165, 130, 28);
        btnIngresar.setBackground(AZUL_OSCURO);
        btnIngresar.setForeground(BLANCO);
        btnIngresar.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnIngresar.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnIngresar.setFocusPainted(false);
        btnIngresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Link recuperar contraseña
        JButton btnOlvideContrasena = new JButton("¿Olvidaste tu contraseña?");
        btnOlvideContrasena.setBounds(260, 195, 180, 18);
        btnOlvideContrasena.setBackground(GRIS_PANEL);
        btnOlvideContrasena.setForeground(AZUL_OSCURO);
        btnOlvideContrasena.setFont(new Font("SansSerif", Font.PLAIN, 10));
        btnOlvideContrasena.setBorder(BorderFactory.createEmptyBorder());
        btnOlvideContrasena.setFocusPainted(false);
        btnOlvideContrasena.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Texto "¿Aún no te has registrado?"
        JLabel labelPregunta = new JLabel("¿AÚN NO TE HAS REGISTRADO?");
        labelPregunta.setBounds(215, 210, 220, 20);
        labelPregunta.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelPregunta.setForeground(AZUL_OSCURO);

        // Botón REGISTRARSE
        btnRegistrarse = new JButton("REGISTRARSE");
        btnRegistrarse.setBounds(350, 230, 130, 28);
        btnRegistrarse.setBackground(AZUL_OSCURO);
        btnRegistrarse.setForeground(BLANCO);
        btnRegistrarse.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnRegistrarse.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnRegistrarse.setFocusPainted(false);
        btnRegistrarse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Label de error
        labelMensaje = new JLabel("");
        labelMensaje.setBounds(215, 262, 270, 20);
        labelMensaje.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelMensaje.setForeground(ROJO_ERROR);

        // --- Agregamos al panel central ---
        panelCentral.add(labelBienvenida);
        panelCentral.add(labelLogoGrande);
        panelCentral.add(labelMDer);
        panelCentral.add(labelIrrorsDer);
        panelCentral.add(labelAirlinesDer);
        panelCentral.add(campEmail);
        panelCentral.add(campContrasena);
        panelCentral.add(labelPlaceholderPass);
        panelCentral.add(btnIngresar);
        panelCentral.add(btnOlvideContrasena);
        panelCentral.add(labelPregunta);
        panelCentral.add(btnRegistrarse);
        panelCentral.add(labelMensaje);

        // ============================================================
        // EVENTOS
        // ============================================================

        // Botón ¿Olvidaste tu contraseña?
        btnOlvideContrasena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaRecuperarContrasena();
                dispose();
            }
        });

        // Botón INICIAR SESIÓN
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = campEmail.getText().trim();
                String contrasena = new String(campContrasena.getPassword()).trim();

                if (email.isEmpty() || email.equals("CORREO ELECTRÓNICO") ||
                        contrasena.isEmpty()) {
                    labelMensaje.setText("Por favor completá todos los campos.");
                    return;
                }

                // Consultamos la BD con el DAO
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                Usuario usuario = usuarioDAO.login(email, contrasena);

                if (usuario == null) {
                    labelMensaje.setText("Email o contraseña incorrectos.");
                    return;
                }

                // Guardamos el usuario en la sesión actual
                SesionActual.iniciar(usuario);

                new VentanaBusqueda();
                dispose();
            }
        });

        // Botón REGISTRARSE
        btnRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaRegistro();
                dispose();
            }
        });

        // --- Agregamos todo al panel principal ---
        panel.add(barraTop);
        panel.add(panelCentral);

        add(panel);
    }
}