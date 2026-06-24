package vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.UsuarioDAO;

public class VentanaRecuperarContrasena extends JFrame {

    // --- Componentes ---
    private JTextField campEmail;
    private JButton btnVerificar;
    private JPasswordField campNuevaContrasena;
    private JPasswordField campConfirmarContrasena;
    private JButton btnCambiar;
    private JButton btnVolver;
    private JLabel labelMensaje;

    // --- Colores Mirrors Airlines ---
    private static final Color AZUL_OSCURO = new Color(26, 45, 90);
    private static final Color AZUL_BTN    = new Color(30, 100, 200);
    private static final Color GRIS_PANEL  = new Color(220, 220, 220);
    private static final Color BLANCO      = Color.WHITE;
    private static final Color NEGRO       = Color.BLACK;
    private static final Color ROJO_ERROR  = new Color(180, 30, 30);

    // --- Constructor ---
    public VentanaRecuperarContrasena() {
        setTitle("Mirrors Airlines - Recuperar Contraseña");
        setSize(780, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        iniciarComponentes();

        setVisible(true);
    }

    private void iniciarComponentes() {

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
        labelM.setBounds(18, -6, 30, 40);
        labelM.setFont(new Font("Serif", Font.ITALIC, 32));
        labelM.setForeground(AZUL_OSCURO);

        JLabel labelIrrors = new JLabel("IRRORS");
        labelIrrors.setBounds(40, 10, 80, 20);
        labelIrrors.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelIrrors.setForeground(NEGRO);

        JLabel labelAirlines = new JLabel("AIRLINES");
        labelAirlines.setBounds(35, 25, 80, 18);
        labelAirlines.setFont(new Font("SansSerif", Font.PLAIN, 11));
        labelAirlines.setForeground(NEGRO);

        barraTop.add(labelM);
        barraTop.add(labelIrrors);
        barraTop.add(labelAirlines);

        // ============================================================
        // PANEL CENTRAL
        // ============================================================
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(null);
        panelCentral.setBounds(125, 100, 510, 270);
        panelCentral.setBackground(GRIS_PANEL);
        panelCentral.setBorder(BorderFactory.createLineBorder(AZUL_OSCURO, 1));

        // --- Título ---
        JLabel labelTitulo = new JLabel("RECUPERAR CONTRASEÑA");
        labelTitulo.setBounds(160, 18, 220, 20);
        labelTitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        labelTitulo.setForeground(NEGRO);

        // ============================================================
        // COLUMNA IZQUIERDA — Paso 1
        // ============================================================
        JLabel labelPaso1 = new JLabel("PASO 1 - INGRESAR CORREO ELECTRÓNICO");
        labelPaso1.setBounds(15, 48, 235, 15);
        labelPaso1.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelPaso1.setForeground(NEGRO);

        campEmail = new JTextField();
        campEmail.setBounds(15, 110, 160, 28);
        campEmail.setFont(new Font("SansSerif", Font.PLAIN, 11));
        campEmail.setForeground(Color.GRAY);
        campEmail.setText("CORREO ELECTRÓNICO");
        campEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        campEmail.setBackground(BLANCO);

        btnVerificar = new JButton("VERIFICAR");
        btnVerificar.setBounds(183, 110, 90, 28);
        btnVerificar.setBackground(AZUL_OSCURO);
        btnVerificar.setForeground(BLANCO);
        btnVerificar.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnVerificar.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnVerificar.setFocusPainted(false);
        btnVerificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // ============================================================
        // COLUMNA DERECHA — Paso 2
        // Arranca deshabilitada hasta que se verifique el email
        // ============================================================
        JLabel labelPaso2 = new JLabel("PASO 2 - INGRESAR NUEVA CONTRASEÑA");
        labelPaso2.setBounds(270, 48, 230, 15);
        labelPaso2.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelPaso2.setForeground(NEGRO);

        campNuevaContrasena = new JPasswordField();
        campNuevaContrasena.setBounds(270, 90, 220, 28);
        campNuevaContrasena.setEchoChar((char) 0);
        campNuevaContrasena.setForeground(Color.GRAY);
        campNuevaContrasena.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        campNuevaContrasena.setBackground(BLANCO);
        campNuevaContrasena.setEnabled(false);

        JLabel labelPlaceholderNueva = new JLabel("NUEVA CONTRASEÑA");
        labelPlaceholderNueva.setBounds(278, 92, 160, 24);
        labelPlaceholderNueva.setFont(new Font("SansSerif", Font.PLAIN, 11));
        labelPlaceholderNueva.setForeground(Color.GRAY);

        campConfirmarContrasena = new JPasswordField();
        campConfirmarContrasena.setBounds(270, 130, 220, 28);
        campConfirmarContrasena.setEchoChar((char) 0);
        campConfirmarContrasena.setForeground(Color.GRAY);
        campConfirmarContrasena.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        campConfirmarContrasena.setBackground(BLANCO);
        campConfirmarContrasena.setEnabled(false);

        JLabel labelPlaceholderConf = new JLabel("CONFIRMAR NUEVA CONTRASEÑA");
        labelPlaceholderConf.setBounds(278, 132, 210, 24);
        labelPlaceholderConf.setFont(new Font("SansSerif", Font.PLAIN, 11));
        labelPlaceholderConf.setForeground(Color.GRAY);

        btnCambiar = new JButton("CAMBIAR");
        btnCambiar.setBounds(350, 175, 140, 30);
        btnCambiar.setBackground(AZUL_OSCURO);
        btnCambiar.setForeground(BLANCO);
        btnCambiar.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnCambiar.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnCambiar.setFocusPainted(false);
        btnCambiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCambiar.setEnabled(false);

        btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(350, 215, 140, 30);
        btnVolver.setBackground(AZUL_OSCURO);
        btnVolver.setForeground(BLANCO);
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnVolver.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // --- Label mensaje ---
        labelMensaje = new JLabel("");
        labelMensaje.setBounds(15, 245, 480, 18);
        labelMensaje.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelMensaje.setForeground(ROJO_ERROR);

        // --- Agregamos todo al panel central ---
        panelCentral.add(labelTitulo);
        panelCentral.add(labelPaso1);
        panelCentral.add(campEmail);
        panelCentral.add(btnVerificar);
        panelCentral.add(labelPaso2);
        panelCentral.add(campNuevaContrasena);
        panelCentral.add(labelPlaceholderNueva);
        panelCentral.add(campConfirmarContrasena);
        panelCentral.add(labelPlaceholderConf);
        panelCentral.add(btnCambiar);
        panelCentral.add(btnVolver);
        panelCentral.add(labelMensaje);

        // --- Agregamos al panel principal ---
        panel.add(barraTop);
        panel.add(panelCentral);

        // ============================================================
        // EVENTOS
        // ============================================================

        // Botón VERIFICAR
        btnVerificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String email = campEmail.getText().trim();

                // Validación 1: campo no vacío
                if (email.isEmpty() || email.equals("CORREO ELECTRÓNICO")) {
                    labelMensaje.setForeground(new Color(180, 30, 30));
                    labelMensaje.setText("Ingresá tu correo electrónico.");
                    return;
                }

                // Verificamos si el email existe en la BD
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                if (!usuarioDAO.emailExiste(email)) {
                    labelMensaje.setForeground(new Color(180, 30, 30));
                    labelMensaje.setText("El email no está registrado.");
                    return;
                }

                habilitarPaso2();
            }
        });

        // Botón CAMBIAR
        btnCambiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nueva      = new String(campNuevaContrasena.getPassword()).trim();
                String confirmar  = new String(campConfirmarContrasena.getPassword()).trim();

                // Validación 1: campos no vacíos
                if (nueva.isEmpty() || confirmar.isEmpty()) {
                    labelMensaje.setForeground(new Color(180, 30, 30));
                    labelMensaje.setText("Completá los campos de contraseña.");
                    return;
                }

                // Validación 2: contraseñas coinciden
                if (!nueva.equals(confirmar)) {
                    labelMensaje.setForeground(new Color(180, 30, 30));
                    labelMensaje.setText("Las contraseñas no coinciden.");
                    return;
                }

                // Actualizamos la contraseña en la BD
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                boolean exito = usuarioDAO.actualizarContrasena(campEmail.getText().trim(), nueva);

                if (exito) {
                    new VentanaLogin();
                    dispose();
                } else {
                    labelMensaje.setForeground(new Color(180, 30, 30));
                    labelMensaje.setText("Error al actualizar la contraseña. Intentá de nuevo.");
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

        add(panel);
    }

    // ============================================================
    // MÉTODOS PÚBLICOS
    // ============================================================

    // Habilita el paso 2 cuando el email es válido
    public void habilitarPaso2() {
        campNuevaContrasena.setEnabled(true);
        campConfirmarContrasena.setEnabled(true);
        btnCambiar.setEnabled(true);
        labelMensaje.setForeground(new Color(0, 120, 0));
        labelMensaje.setText("Email encontrado. Ingresá tu nueva contraseña.");
    }

    public void setMensaje(String mensaje) {
        labelMensaje.setText(mensaje);
    }
}