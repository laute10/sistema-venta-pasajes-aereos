package vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPoliticas extends JFrame {

    private JButton btnVolver;

    // --- Colores Mirrors Airlines ---
    private static final Color AZUL_OSCURO = new Color(26, 45, 90);
    private static final Color GRIS_PANEL  = new Color(220, 220, 220);
    private static final Color BLANCO      = Color.WHITE;
    private static final Color NEGRO       = Color.BLACK;

    // --- Constructor ---
    public VentanaPoliticas() {
        setTitle("Mirrors Airlines - Políticas");
        setSize(780, 620);
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
        panelCentral.setBounds(80, 70, 610, 490);
        panelCentral.setBackground(GRIS_PANEL);
        panelCentral.setBorder(BorderFactory.createLineBorder(AZUL_OSCURO, 1));

        // --- Título ---
        JLabel labelTitulo = new JLabel("POLÍTICAS DE CANCELACIÓN Y MODIFICACIÓN");
        labelTitulo.setBounds(130, 18, 380, 20);
        labelTitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        labelTitulo.setForeground(NEGRO);

        // ============================================================
        // SECCIÓN 1: Límite de tiempo
        // ============================================================
        JLabel labelTitTiempo = new JLabel("LÍMITE DE TIEMPO");
        labelTitTiempo.setBounds(20, 50, 200, 15);
        labelTitTiempo.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitTiempo.setForeground(NEGRO);

        JLabel labelTiempo1 = new JLabel("Las cancelaciones y modificaciones se pueden realizar hasta 48 horas antes de la fecha de salida.");
        labelTiempo1.setBounds(20, 68, 570, 15);
        labelTiempo1.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelTiempo1.setForeground(NEGRO);

        // ============================================================
        // SECCIÓN 2: Cancelación
        // ============================================================
        JLabel labelTitCanc = new JLabel("CANCELACIÓN DE RESERVA");
        labelTitCanc.setBounds(20, 100, 250, 15);
        labelTitCanc.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitCanc.setForeground(NEGRO);

        JLabel labelCanc1 = new JLabel("• Cargo por cancelación: 30% del total abonado.");
        labelCanc1.setBounds(20, 118, 400, 15);
        labelCanc1.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelCanc1.setForeground(NEGRO);

        JLabel labelCanc2 = new JLabel("• Reembolso: 70% del total abonado.");
        labelCanc2.setBounds(20, 135, 400, 15);
        labelCanc2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelCanc2.setForeground(NEGRO);

        JLabel labelCanc3 = new JLabel("• El reembolso se acredita en el mismo método de pago utilizado.");
        labelCanc3.setBounds(20, 152, 500, 15);
        labelCanc3.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelCanc3.setForeground(NEGRO);

        // ============================================================
        // SECCIÓN 3: Cambio de pasajero
        // ============================================================
        JLabel labelTitPas = new JLabel("CAMBIO DE DATOS DE PASAJERO");
        labelTitPas.setBounds(20, 184, 280, 15);
        labelTitPas.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitPas.setForeground(NEGRO);

        JLabel labelPas1 = new JLabel("• Cargo del 2% del valor del pasaje por cada pasajero modificado.");
        labelPas1.setBounds(20, 202, 500, 15);
        labelPas1.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelPas1.setForeground(NEGRO);

        // ============================================================
        // SECCIÓN 4: Cambio de vuelo
        // ============================================================
        JLabel labelTitVuelo = new JLabel("CAMBIO DE VUELO");
        labelTitVuelo.setBounds(20, 234, 200, 15);
        labelTitVuelo.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitVuelo.setForeground(NEGRO);

        JLabel labelVuelo1 = new JLabel("• Cargo base: 10% del valor del vuelo original.");
        labelVuelo1.setBounds(20, 252, 400, 15);
        labelVuelo1.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelVuelo1.setForeground(NEGRO);

        JLabel labelVuelo2 = new JLabel("• Nuevo vuelo más caro: se abona el 10% + la diferencia de precio.");
        labelVuelo2.setBounds(20, 269, 500, 15);
        labelVuelo2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelVuelo2.setForeground(NEGRO);

        JLabel labelVuelo3 = new JLabel("• Nuevo vuelo más barato: se abona solo el 10%, sin reembolso por la diferencia.");
        labelVuelo3.setBounds(20, 286, 560, 15);
        labelVuelo3.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelVuelo3.setForeground(NEGRO);

        // ============================================================
        // SECCIÓN 5: Cambio de asiento
        // ============================================================
        JLabel labelTitAsiento = new JLabel("CAMBIO DE ASIENTO");
        labelTitAsiento.setBounds(20, 318, 200, 15);
        labelTitAsiento.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitAsiento.setForeground(NEGRO);

        JLabel labelAs0 = new JLabel("• El cargo varía según la categoría de origen y destino:");
        labelAs0.setBounds(20, 336, 400, 15);
        labelAs0.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelAs0.setForeground(NEGRO);

        JLabel labelAs1 = new JLabel("    Económica  →  Ejecutiva:              $8.000");
        labelAs1.setBounds(20, 353, 400, 15);
        labelAs1.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelAs1.setForeground(NEGRO);

        JLabel labelAs2 = new JLabel("    Económica  →  Primera Clase:      $20.000");
        labelAs2.setBounds(20, 370, 400, 15);
        labelAs2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelAs2.setForeground(NEGRO);

        JLabel labelAs3 = new JLabel("    Ejecutiva    →  Primera Clase:      $12.000");
        labelAs3.setBounds(20, 387, 400, 15);
        labelAs3.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelAs3.setForeground(NEGRO);

        JLabel labelAs4 = new JLabel("    Ejecutiva    →  Económica:             $3.000");
        labelAs4.setBounds(20, 404, 400, 15);
        labelAs4.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelAs4.setForeground(NEGRO);

        JLabel labelAs5 = new JLabel("    Primera Clase  →  Ejecutiva:          $3.000");
        labelAs5.setBounds(20, 421, 400, 15);
        labelAs5.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelAs5.setForeground(NEGRO);

        JLabel labelAs6 = new JLabel("    Primera Clase  →  Económica:       $6.000");
        labelAs6.setBounds(20, 438, 400, 15);
        labelAs6.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelAs6.setForeground(NEGRO);

        JLabel labelAs7 = new JLabel("• Sujeto a disponibilidad al momento del cambio.");
        labelAs7.setBounds(20, 455, 400, 15);
        labelAs7.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelAs7.setForeground(NEGRO);

        // --- Botón VOLVER ---
        btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(450, 455, 140, 28);
        btnVolver.setBackground(AZUL_OSCURO);
        btnVolver.setForeground(BLANCO);
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnVolver.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // --- Agregamos todo al panel central ---
        panelCentral.add(labelTitulo);
        panelCentral.add(labelTitTiempo);
        panelCentral.add(labelTiempo1);
        panelCentral.add(labelTitCanc);
        panelCentral.add(labelCanc1);
        panelCentral.add(labelCanc2);
        panelCentral.add(labelCanc3);
        panelCentral.add(labelTitPas);
        panelCentral.add(labelPas1);
        panelCentral.add(labelTitVuelo);
        panelCentral.add(labelVuelo1);
        panelCentral.add(labelVuelo2);
        panelCentral.add(labelVuelo3);
        panelCentral.add(labelTitAsiento);
        panelCentral.add(labelAs0);
        panelCentral.add(labelAs1);
        panelCentral.add(labelAs2);
        panelCentral.add(labelAs3);
        panelCentral.add(labelAs4);
        panelCentral.add(labelAs5);
        panelCentral.add(labelAs6);
        panelCentral.add(labelAs7);
        panelCentral.add(btnVolver);

        // ============================================================
        // EVENTOS
        // ============================================================

        // Botón VOLVER — cierra esta ventana y vuelve a MisReservas
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // --- Agregamos al panel principal ---
        panel.add(barraTop);
        panel.add(panelCentral);

        add(panel);
    }
}