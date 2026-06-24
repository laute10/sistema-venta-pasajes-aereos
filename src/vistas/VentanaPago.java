package vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import dao.ReservaDAO;
import dao.PasajeroDAO;
import dao.PagoDAO;
import dao.VueloDAO;
import modelo.Reserva;
import modelo.Pasajero;
import modelo.Pago;
import modelo.SesionActual;
import servicios.ServicioEmail;
import java.util.ArrayList;

public class VentanaPago extends JFrame {

    private JLabel labelVuelo;
    private JLabel labelPasajeros;
    private JLabel labelTotal;
    private JComboBox<String> comboMetodoPago;
    private JPanel panelTarjeta;
    private JTextField campNombreTarjeta;
    private JTextField campNumeroTarjeta;
    private JTextField campVencimiento;
    private JTextField campCVV;
    private JPanel panelTransferencia;
    private JTextField campCBU;
    private JTextField campAlias;
    private JButton btnPagar;
    private JButton btnVolver;
    private JLabel labelMensaje;

    // Datos recibidos desde VentanaReserva o VentanaModificacion
    private int idVuelo;
    private double totalReserva;
    private ArrayList<Pasajero> pasajeros;

    // Modo: "NUEVA" para reserva nueva, "MODIFICACION" para modificar existente
    private String modo = "NUEVA";
    private int idReservaExistente = -1;

    private static final Color AZUL_OSCURO = new Color(26, 45, 90);
    private static final Color GRIS_PANEL  = new Color(220, 220, 220);
    private static final Color BLANCO      = Color.WHITE;
    private static final Color NEGRO       = Color.BLACK;
    private static final Color ROJO_ERROR  = new Color(180, 30, 30);

    public VentanaPago() {
        setTitle("Mirrors Airlines - Pago");
        setSize(780, 560);
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
        // BARRA SUPERIOR
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
        panelCentral.setBounds(130, 70, 510, 440);
        panelCentral.setBackground(GRIS_PANEL);
        panelCentral.setBorder(BorderFactory.createLineBorder(AZUL_OSCURO, 1));

        JLabel labelTitulo = new JLabel("PROCESAMIENTO DE PAGO");
        labelTitulo.setBounds(150, 15, 250, 20);
        labelTitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        labelTitulo.setForeground(NEGRO);

        // --- Resumen ---
        JLabel labelTitResumen = new JLabel("RESUMEN DE LA COMPRA");
        labelTitResumen.setBounds(20, 48, 220, 15);
        labelTitResumen.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitResumen.setForeground(NEGRO);

        labelVuelo = new JLabel("Vuelo: -");
        labelVuelo.setBounds(20, 66, 460, 15);
        labelVuelo.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelVuelo.setForeground(NEGRO);

        labelPasajeros = new JLabel("Pasajeros: -");
        labelPasajeros.setBounds(20, 83, 220, 15);
        labelPasajeros.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelPasajeros.setForeground(NEGRO);

        labelTotal = new JLabel("Total a pagar: -");
        labelTotal.setBounds(260, 83, 220, 15);
        labelTotal.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTotal.setForeground(NEGRO);

        // --- Método de pago ---
        JLabel labelTitMetodo = new JLabel("MÉTODO DE PAGO");
        labelTitMetodo.setBounds(20, 115, 150, 15);
        labelTitMetodo.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitMetodo.setForeground(NEGRO);

        String[] metodos = {"TARJETA", "TRANSFERENCIA"};
        comboMetodoPago = new JComboBox<>(metodos);
        comboMetodoPago.setBounds(20, 133, 160, 28);
        comboMetodoPago.setBackground(BLANCO);
        comboMetodoPago.setFont(new Font("SansSerif", Font.PLAIN, 11));

        // --- Panel tarjeta ---
        panelTarjeta = new JPanel();
        panelTarjeta.setLayout(null);
        panelTarjeta.setBounds(20, 175, 465, 165);
        panelTarjeta.setBackground(GRIS_PANEL);
        panelTarjeta.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        panelTarjeta.setVisible(true);

        JLabel labelTitTarjeta = new JLabel("DATOS DE LA TARJETA");
        labelTitTarjeta.setBounds(10, 8, 200, 15);
        labelTitTarjeta.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitTarjeta.setForeground(NEGRO);

        JLabel labelNombreTarjeta = new JLabel("NOMBRE EN TARJETA");
        labelNombreTarjeta.setBounds(10, 28, 150, 15);
        labelNombreTarjeta.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelNombreTarjeta.setForeground(NEGRO);

        campNombreTarjeta = new JTextField();
        campNombreTarjeta.setBounds(10, 45, 430, 28);
        campNombreTarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        campNombreTarjeta.setBackground(BLANCO);

        JLabel labelNroTarjeta = new JLabel("NÚMERO DE TARJETA");
        labelNroTarjeta.setBounds(10, 78, 150, 15);
        labelNroTarjeta.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelNroTarjeta.setForeground(NEGRO);

        campNumeroTarjeta = new JTextField();
        campNumeroTarjeta.setBounds(10, 95, 430, 28);
        campNumeroTarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        campNumeroTarjeta.setBackground(BLANCO);

        JLabel labelVenc = new JLabel("VENCIMIENTO (MM/AA)");
        labelVenc.setBounds(10, 128, 160, 15);
        labelVenc.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelVenc.setForeground(NEGRO);

        campVencimiento = new JTextField();
        campVencimiento.setBounds(10, 143, 100, 18);
        campVencimiento.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        campVencimiento.setBackground(BLANCO);

        JLabel labelCVV = new JLabel("CVV");
        labelCVV.setBounds(200, 128, 50, 15);
        labelCVV.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelCVV.setForeground(NEGRO);

        campCVV = new JTextField();
        campCVV.setBounds(200, 143, 60, 18);
        campCVV.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        campCVV.setBackground(BLANCO);

        panelTarjeta.add(labelTitTarjeta);
        panelTarjeta.add(labelNombreTarjeta);
        panelTarjeta.add(campNombreTarjeta);
        panelTarjeta.add(labelNroTarjeta);
        panelTarjeta.add(campNumeroTarjeta);
        panelTarjeta.add(labelVenc);
        panelTarjeta.add(campVencimiento);
        panelTarjeta.add(labelCVV);
        panelTarjeta.add(campCVV);

        // --- Panel transferencia ---
        panelTransferencia = new JPanel();
        panelTransferencia.setLayout(null);
        panelTransferencia.setBounds(20, 175, 465, 165);
        panelTransferencia.setBackground(GRIS_PANEL);
        panelTransferencia.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        panelTransferencia.setVisible(false);

        JLabel labelTitTransf = new JLabel("DATOS DE TRANSFERENCIA");
        labelTitTransf.setBounds(10, 8, 250, 15);
        labelTitTransf.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitTransf.setForeground(NEGRO);

        JLabel labelDatosBanco = new JLabel("Transferir a: CBU 0000003100090000000001  |  Banco Nación  |  Mirrors Airlines SA");
        labelDatosBanco.setBounds(10, 28, 445, 15);
        labelDatosBanco.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelDatosBanco.setForeground(NEGRO);

        JLabel labelCBULabel = new JLabel("TU CBU / CVU");
        labelCBULabel.setBounds(10, 55, 120, 15);
        labelCBULabel.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelCBULabel.setForeground(NEGRO);

        campCBU = new JTextField();
        campCBU.setBounds(10, 72, 430, 28);
        campCBU.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        campCBU.setBackground(BLANCO);

        JLabel labelAliasLabel = new JLabel("TU ALIAS");
        labelAliasLabel.setBounds(10, 108, 120, 15);
        labelAliasLabel.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelAliasLabel.setForeground(NEGRO);

        campAlias = new JTextField();
        campAlias.setBounds(10, 125, 430, 28);
        campAlias.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        campAlias.setBackground(BLANCO);

        panelTransferencia.add(labelTitTransf);
        panelTransferencia.add(labelDatosBanco);
        panelTransferencia.add(labelCBULabel);
        panelTransferencia.add(campCBU);
        panelTransferencia.add(labelAliasLabel);
        panelTransferencia.add(campAlias);

        // --- Botones ---
        btnPagar = new JButton("CONFIRMAR PAGO");
        btnPagar.setBounds(200, 360, 150, 30);
        btnPagar.setBackground(AZUL_OSCURO);
        btnPagar.setForeground(BLANCO);
        btnPagar.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnPagar.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnPagar.setFocusPainted(false);
        btnPagar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnVolver = new JButton("CANCELAR");
        btnVolver.setBounds(365, 360, 120, 30);
        btnVolver.setBackground(AZUL_OSCURO);
        btnVolver.setForeground(BLANCO);
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnVolver.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        labelMensaje = new JLabel("");
        labelMensaje.setBounds(20, 400, 460, 18);
        labelMensaje.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelMensaje.setForeground(ROJO_ERROR);

        panelCentral.add(labelTitulo);
        panelCentral.add(labelTitResumen);
        panelCentral.add(labelVuelo);
        panelCentral.add(labelPasajeros);
        panelCentral.add(labelTotal);
        panelCentral.add(labelTitMetodo);
        panelCentral.add(comboMetodoPago);
        panelCentral.add(panelTarjeta);
        panelCentral.add(panelTransferencia);
        panelCentral.add(btnPagar);
        panelCentral.add(btnVolver);
        panelCentral.add(labelMensaje);

        // ============================================================
        // EVENTOS
        // ============================================================

        // Combo método de pago — cambia el panel visible
        comboMetodoPago.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String seleccion = (String) comboMetodoPago.getSelectedItem();
                    if (seleccion.equals("TARJETA")) {
                        mostrarPanelTarjeta();
                    } else {
                        mostrarPanelTransferencia();
                    }
                }
            }
        });

        // Botón CONFIRMAR PAGO
        btnPagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String metodo = (String) comboMetodoPago.getSelectedItem();

                if (metodo.equals("TARJETA")) {
                    // Validaciones para tarjeta
                    String nombre  = campNombreTarjeta.getText().trim();
                    String numero  = campNumeroTarjeta.getText().trim();
                    String vencim  = campVencimiento.getText().trim();
                    String cvv     = campCVV.getText().trim();

                    if (nombre.isEmpty() || numero.isEmpty() ||
                            vencim.isEmpty() || cvv.isEmpty()) {
                        labelMensaje.setText("Completá todos los datos de la tarjeta.");
                        return;
                    }
                    // Validamos que el nombre en tarjeta solo tenga letras
                    if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                        labelMensaje.setText("El nombre en la tarjeta solo puede contener letras.");
                        return;
                    }
                    // Validamos que el número de tarjeta tenga 16 dígitos
                    if (!numero.replaceAll(" ", "").matches("\\d{16}")) {
                        labelMensaje.setText("El número de tarjeta debe tener 16 dígitos.");
                        return;
                    }
                    // Validamos formato vencimiento MM/AA
                    if (!vencim.matches("\\d{2}/\\d{2}")) {
                        labelMensaje.setText("El vencimiento debe tener el formato MM/AA. Ej: 08/27");
                        return;
                    }
                    // Validamos que el vencimiento no sea pasado
                    try {
                        String[] partes = vencim.split("/");
                        int mes = Integer.parseInt(partes[0]);
                        int anio = Integer.parseInt("20" + partes[1]);
                        java.time.YearMonth vencimiento = java.time.YearMonth.of(anio, mes);
                        if (vencimiento.isBefore(java.time.YearMonth.now())) {
                            labelMensaje.setText("La tarjeta está vencida.");
                            return;
                        }
                    } catch (Exception ex) {
                        labelMensaje.setText("Vencimiento inválido. Usá el formato MM/AA.");
                        return;
                    }
                    // Validamos que el CVV tenga 3 dígitos
                    if (!cvv.matches("\\d{3}")) {
                        labelMensaje.setText("El CVV debe tener 3 dígitos.");
                        return;
                    }

                } else {
                    // Validaciones para transferencia
                    String cbu   = campCBU.getText().trim();
                    String alias = campAlias.getText().trim();

                    if (cbu.isEmpty() && alias.isEmpty()) {
                        labelMensaje.setText("Ingresá tu CBU/CVU o alias.");
                        return;
                    }
                    // Si ingresó CBU validamos que tenga 22 dígitos
                    if (!cbu.isEmpty() && !cbu.matches("\\d{22}")) {
                        labelMensaje.setText("El CBU/CVU debe tener exactamente 22 dígitos.");
                        return;
                    }
                }

                // Todo OK — guardamos en la BD según el modo
                String metodoFinal = metodo;

                if (modo.equals("NUEVA")) {
                    // Paso 1: Guardar la reserva nueva
                    Reserva reserva = new Reserva(
                            SesionActual.usuario.getId(),
                            idVuelo,
                            "PENDIENTE",
                            totalReserva
                    );
                    ReservaDAO reservaDAO = new ReservaDAO();
                    int idReserva = reservaDAO.guardar(reserva);

                    if (idReserva == -1) {
                        labelMensaje.setText("Error al guardar la reserva. Intentá de nuevo.");
                        return;
                    }

                    // Paso 2: Guardar cada pasajero
                    PasajeroDAO pasajeroDAO = new PasajeroDAO();
                    for (Pasajero p : pasajeros) {
                        p.setIdReserva(idReserva);
                        pasajeroDAO.guardar(p);
                    }

                    // Paso 3: Guardar el pago
                    Pago pago = new Pago(idReserva, metodoFinal, totalReserva, "APROBADO");
                    PagoDAO pagoDAO = new PagoDAO();
                    pagoDAO.guardar(pago);

                    // Paso 4: Confirmar la reserva
                    reservaDAO.confirmar(idReserva);

                    // Paso 5: Reducir asientos disponibles del vuelo
                    VueloDAO vueloDAO = new VueloDAO();
                    vueloDAO.reducirAsiento(idVuelo);

                    // Paso 6: Mandar email de confirmación de compra
                    ServicioEmail.enviarConfirmacionCompra(
                            SesionActual.usuario.getEmail(),
                            SesionActual.usuario.getNombre(),
                            labelVuelo.getText().replace("Vuelo: ", ""),
                            "-", "-", "-",
                            pasajeros.size(),
                            totalReserva,
                            metodoFinal
                    );

                } else {
                    // MODIFICACION — guardamos el pago del cargo adicional
                    PagoDAO pagoDAO = new PagoDAO();
                    Pago pago = new Pago(idReservaExistente, metodoFinal, totalReserva, "APROBADO");
                    pagoDAO.guardar(pago);

                    // Si hay pasajeros en la lista significa que se modificaron datos
                    if (pasajeros != null && !pasajeros.isEmpty()) {
                        PasajeroDAO pasajeroDAO = new PasajeroDAO();
                        for (Pasajero p : pasajeros) {
                            pasajeroDAO.actualizar(p.getId(), p.getNombre(), p.getApellido(),
                                    p.getFechaNacimiento(), p.getNroPasaporte());
                        }
                    }
                }

                new VentanaMisReservas();
                dispose();
            }
        });

        // Botón CANCELAR
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaReserva();
                dispose();
            }
        });

        panel.add(barraTop);
        panel.add(panelCentral);

        add(panel);
    }

    public void cargarResumen(String vuelo, int cantPasajeros, double total) {
        labelVuelo.setText("Vuelo: " + vuelo);
        labelPasajeros.setText("Pasajeros: " + cantPasajeros);
        labelTotal.setText("Total a pagar: $" + total);
        this.totalReserva = total;
    }

    // Recibe los datos del vuelo y pasajeros desde VentanaReserva
    public void setDatosReserva(int idVuelo, ArrayList<Pasajero> pasajeros) {
        this.idVuelo   = idVuelo;
        this.pasajeros = pasajeros;
    }

    // Activa el modo modificación — no crea reserva nueva sino que actualiza la existente
    public void setModoModificacion(int idReserva) {
        this.modo = "MODIFICACION";
        this.idReservaExistente = idReserva;
    }

    public void mostrarPanelTarjeta() {
        panelTarjeta.setVisible(true);
        panelTransferencia.setVisible(false);
    }

    public void mostrarPanelTransferencia() {
        panelTarjeta.setVisible(false);
        panelTransferencia.setVisible(true);
    }

    public String getMetodoPago() {
        return (String) comboMetodoPago.getSelectedItem();
    }

    public void setMensaje(String mensaje) {
        labelMensaje.setText(mensaje);
    }
}