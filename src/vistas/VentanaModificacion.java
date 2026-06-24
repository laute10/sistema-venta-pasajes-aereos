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
import modelo.Politicas;
import modelo.Pasajero;
import modelo.Vuelo;
import dao.PasajeroDAO;
import dao.VueloDAO;
import java.util.ArrayList;

public class VentanaModificacion extends JFrame {

    private JLabel labelVuelo;
    private JLabel labelFecha;
    private JLabel labelPasajeros;
    private JComboBox<String> comboTipoModificacion;
    private JPanel panelPasajero;
    private JComboBox<String> comboPasajeros;
    private JTextField campNuevoNombre;
    private JTextField campNuevoApellido;
    private JTextField campNuevoNacimiento;
    private JTextField campNuevoPasaporte;
    private JLabel labelCargoPasajero;
    private JPanel panelAsiento;
    private JComboBox<String> comboPasajerosAsiento;
    private JComboBox<String> comboCategoriaAsiento;
    private JLabel labelCargoAsiento;
    private JPanel panelVuelo;
    private JComboBox<String> comboNuevoOrigen;
    private JComboBox<String> comboNuevoDestino;
    private JTextField campNuevaFecha;
    private JLabel labelCargoVuelo;
    private JButton btnCalcular;
    private JButton btnConfirmar;
    private JButton btnVolver;
    private JLabel labelMensaje;

    // Datos de la reserva recibidos desde VentanaMisReservas
    private int idReserva;
    private int idVuelo;
    private double precioVuelo;
    private ArrayList<Pasajero> pasajeros;

    private static final Color AZUL_OSCURO = new Color(26, 45, 90);
    private static final Color GRIS_PANEL  = new Color(220, 220, 220);
    private static final Color BLANCO      = Color.WHITE;
    private static final Color NEGRO       = Color.BLACK;
    private static final Color ROJO_ERROR  = new Color(180, 30, 30);

    public VentanaModificacion() {
        setTitle("Mirrors Airlines - Modificar Reserva");
        setSize(780, 580);
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
        panelCentral.setBounds(40, 70, 695, 460);
        panelCentral.setBackground(GRIS_PANEL);
        panelCentral.setBorder(BorderFactory.createLineBorder(AZUL_OSCURO, 1));

        JLabel labelTitulo = new JLabel("MODIFICAR RESERVA");
        labelTitulo.setBounds(275, 15, 220, 20);
        labelTitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        labelTitulo.setForeground(NEGRO);

        // --- Info reserva actual ---
        JLabel labelTitInfo = new JLabel("RESERVA ACTUAL");
        labelTitInfo.setBounds(20, 45, 200, 15);
        labelTitInfo.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitInfo.setForeground(NEGRO);

        labelVuelo = new JLabel("Vuelo: -");
        labelVuelo.setBounds(20, 63, 300, 15);
        labelVuelo.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelVuelo.setForeground(NEGRO);

        labelFecha = new JLabel("Fecha: -");
        labelFecha.setBounds(20, 80, 300, 15);
        labelFecha.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelFecha.setForeground(NEGRO);

        labelPasajeros = new JLabel("Pasajeros: -");
        labelPasajeros.setBounds(360, 63, 300, 15);
        labelPasajeros.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelPasajeros.setForeground(NEGRO);

        // --- Selector tipo modificación ---
        JLabel labelTipoMod = new JLabel("TIPO DE MODIFICACIÓN");
        labelTipoMod.setBounds(20, 108, 180, 15);
        labelTipoMod.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTipoMod.setForeground(NEGRO);

        String[] tipos = {"Cambio de datos de pasajero", "Cambio de asiento", "Cambio de vuelo"};
        comboTipoModificacion = new JComboBox<>(tipos);
        comboTipoModificacion.setBounds(20, 126, 250, 28);
        comboTipoModificacion.setBackground(BLANCO);
        comboTipoModificacion.setFont(new Font("SansSerif", Font.PLAIN, 11));

        // ============================================================
        // PANEL 1: Cambio de pasajero
        // ============================================================
        panelPasajero = new JPanel();
        panelPasajero.setLayout(null);
        panelPasajero.setBounds(20, 168, 650, 195);
        panelPasajero.setBackground(GRIS_PANEL);
        panelPasajero.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        panelPasajero.setVisible(true);

        JLabel labelTitPas = new JLabel("NUEVOS DATOS DEL PASAJERO  |  CARGO: 2% DEL PASAJE");
        labelTitPas.setBounds(10, 8, 450, 15);
        labelTitPas.setFont(new Font("SansSerif", Font.BOLD, 9));
        labelTitPas.setForeground(NEGRO);

        JLabel labelSelPas = new JLabel("PASAJERO A MODIFICAR");
        labelSelPas.setBounds(10, 28, 180, 15);
        labelSelPas.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelSelPas.setForeground(NEGRO);

        comboPasajeros = new JComboBox<>();
        comboPasajeros.setBounds(10, 45, 620, 25);
        comboPasajeros.setBackground(BLANCO);
        comboPasajeros.setFont(new Font("SansSerif", Font.PLAIN, 11));

        campNuevoNombre = new JTextField();
        campNuevoNombre.setBounds(10, 85, 148, 25);
        campNuevoNombre.setFont(new Font("SansSerif", Font.PLAIN, 10));
        campNuevoNombre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)));
        campNuevoNombre.setBackground(BLANCO);
        campNuevoNombre.setForeground(Color.GRAY);
        campNuevoNombre.setText("NUEVO NOMBRE");

        campNuevoApellido = new JTextField();
        campNuevoApellido.setBounds(168, 85, 148, 25);
        campNuevoApellido.setFont(new Font("SansSerif", Font.PLAIN, 10));
        campNuevoApellido.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)));
        campNuevoApellido.setBackground(BLANCO);
        campNuevoApellido.setForeground(Color.GRAY);
        campNuevoApellido.setText("NUEVO APELLIDO");

        campNuevoNacimiento = new JTextField();
        campNuevoNacimiento.setBounds(326, 85, 148, 25);
        campNuevoNacimiento.setFont(new Font("SansSerif", Font.PLAIN, 10));
        campNuevoNacimiento.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)));
        campNuevoNacimiento.setBackground(BLANCO);
        campNuevoNacimiento.setForeground(Color.GRAY);
        campNuevoNacimiento.setText("NACIMIENTO");

        campNuevoPasaporte = new JTextField();
        campNuevoPasaporte.setBounds(484, 85, 148, 25);
        campNuevoPasaporte.setFont(new Font("SansSerif", Font.PLAIN, 10));
        campNuevoPasaporte.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)));
        campNuevoPasaporte.setBackground(BLANCO);
        campNuevoPasaporte.setForeground(Color.GRAY);
        campNuevoPasaporte.setText("PASAPORTE");

        labelCargoPasajero = new JLabel("Cargo estimado: -");
        labelCargoPasajero.setBounds(10, 160, 300, 18);
        labelCargoPasajero.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelCargoPasajero.setForeground(NEGRO);

        panelPasajero.add(labelTitPas);
        panelPasajero.add(labelSelPas);
        panelPasajero.add(comboPasajeros);
        panelPasajero.add(campNuevoNombre);
        panelPasajero.add(campNuevoApellido);
        panelPasajero.add(campNuevoNacimiento);
        panelPasajero.add(campNuevoPasaporte);
        panelPasajero.add(labelCargoPasajero);

        // ============================================================
        // PANEL 2: Cambio de asiento
        // ============================================================
        panelAsiento = new JPanel();
        panelAsiento.setLayout(null);
        panelAsiento.setBounds(20, 168, 650, 195);
        panelAsiento.setBackground(GRIS_PANEL);
        panelAsiento.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        panelAsiento.setVisible(false);

        JLabel labelTitAs = new JLabel("CAMBIO DE ASIENTO  |  CARGO SEGÚN CATEGORÍA");
        labelTitAs.setBounds(10, 8, 400, 15);
        labelTitAs.setFont(new Font("SansSerif", Font.BOLD, 9));
        labelTitAs.setForeground(NEGRO);

        JLabel labelSelPasAs = new JLabel("PASAJERO");
        labelSelPasAs.setBounds(10, 28, 100, 15);
        labelSelPasAs.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelSelPasAs.setForeground(NEGRO);

        comboPasajerosAsiento = new JComboBox<>();
        comboPasajerosAsiento.setBounds(10, 45, 620, 25);
        comboPasajerosAsiento.setBackground(BLANCO);
        comboPasajerosAsiento.setFont(new Font("SansSerif", Font.PLAIN, 11));

        JLabel labelCateg = new JLabel("NUEVA CATEGORÍA");
        labelCateg.setBounds(10, 80, 150, 15);
        labelCateg.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelCateg.setForeground(NEGRO);

        String[] categorias = {"ECONOMICA", "EJECUTIVA", "PRIMERA CLASE"};
        comboCategoriaAsiento = new JComboBox<>(categorias);
        comboCategoriaAsiento.setBounds(10, 97, 200, 25);
        comboCategoriaAsiento.setBackground(BLANCO);
        comboCategoriaAsiento.setFont(new Font("SansSerif", Font.PLAIN, 11));

        labelCargoAsiento = new JLabel("Cargo estimado: -");
        labelCargoAsiento.setBounds(10, 135, 300, 18);
        labelCargoAsiento.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelCargoAsiento.setForeground(NEGRO);

        JLabel labelInfoAs = new JLabel("Sujeto a disponibilidad al momento del cambio.");
        labelInfoAs.setBounds(10, 155, 400, 18);
        labelInfoAs.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelInfoAs.setForeground(NEGRO);

        panelAsiento.add(labelTitAs);
        panelAsiento.add(labelSelPasAs);
        panelAsiento.add(comboPasajerosAsiento);
        panelAsiento.add(labelCateg);
        panelAsiento.add(comboCategoriaAsiento);
        panelAsiento.add(labelCargoAsiento);
        panelAsiento.add(labelInfoAs);

        // ============================================================
        // PANEL 3: Cambio de vuelo
        // ============================================================
        panelVuelo = new JPanel();
        panelVuelo.setLayout(null);
        panelVuelo.setBounds(20, 168, 650, 195);
        panelVuelo.setBackground(GRIS_PANEL);
        panelVuelo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        panelVuelo.setVisible(false);

        JLabel labelTitVuelo = new JLabel("CAMBIO DE VUELO  |  CARGO: 10% DEL VUELO ORIGINAL");
        labelTitVuelo.setBounds(10, 8, 430, 15);
        labelTitVuelo.setFont(new Font("SansSerif", Font.BOLD, 9));
        labelTitVuelo.setForeground(NEGRO);

        String[] ciudades = {"Seleccionar...", "Mendoza", "Buenos Aires", "Córdoba", "Bariloche"};

        JLabel labelNuevoOrig = new JLabel("NUEVO ORIGEN");
        labelNuevoOrig.setBounds(10, 30, 120, 15);
        labelNuevoOrig.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelNuevoOrig.setForeground(NEGRO);

        comboNuevoOrigen = new JComboBox<>(ciudades);
        comboNuevoOrigen.setBounds(10, 47, 190, 25);
        comboNuevoOrigen.setBackground(BLANCO);
        comboNuevoOrigen.setFont(new Font("SansSerif", Font.PLAIN, 11));

        JLabel labelNuevoDest = new JLabel("NUEVO DESTINO");
        labelNuevoDest.setBounds(215, 30, 120, 15);
        labelNuevoDest.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelNuevoDest.setForeground(NEGRO);

        comboNuevoDestino = new JComboBox<>(ciudades);
        comboNuevoDestino.setBounds(215, 47, 190, 25);
        comboNuevoDestino.setBackground(BLANCO);
        comboNuevoDestino.setFont(new Font("SansSerif", Font.PLAIN, 11));

        JLabel labelNuevaFechaL = new JLabel("NUEVA FECHA");
        labelNuevaFechaL.setBounds(420, 30, 120, 15);
        labelNuevaFechaL.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelNuevaFechaL.setForeground(NEGRO);

        campNuevaFecha = new JTextField("2026-08-01");
        campNuevaFecha.setBounds(420, 47, 160, 25);
        campNuevaFecha.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)));
        campNuevaFecha.setBackground(BLANCO);

        labelCargoVuelo = new JLabel("Cargo estimado: -");
        labelCargoVuelo.setBounds(10, 90, 400, 18);
        labelCargoVuelo.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelCargoVuelo.setForeground(NEGRO);

        JLabel labelInfoVuelo = new JLabel("Si el nuevo vuelo es más barato, solo se cobra el 10% del original sin reembolso.");
        labelInfoVuelo.setBounds(10, 110, 580, 18);
        labelInfoVuelo.setFont(new Font("SansSerif", Font.PLAIN, 9));
        labelInfoVuelo.setForeground(NEGRO);

        panelVuelo.add(labelTitVuelo);
        panelVuelo.add(labelNuevoOrig);
        panelVuelo.add(comboNuevoOrigen);
        panelVuelo.add(labelNuevoDest);
        panelVuelo.add(comboNuevoDestino);
        panelVuelo.add(labelNuevaFechaL);
        panelVuelo.add(campNuevaFecha);
        panelVuelo.add(labelCargoVuelo);
        panelVuelo.add(labelInfoVuelo);

        // --- Botones ---
        btnCalcular = new JButton("CALCULAR CARGO");
        btnCalcular.setBounds(20, 385, 150, 28);
        btnCalcular.setBackground(AZUL_OSCURO);
        btnCalcular.setForeground(BLANCO);
        btnCalcular.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnCalcular.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnCalcular.setFocusPainted(false);
        btnCalcular.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnConfirmar = new JButton("CONFIRMAR Y PAGAR");
        btnConfirmar.setBounds(185, 385, 165, 28);
        btnConfirmar.setBackground(AZUL_OSCURO);
        btnConfirmar.setForeground(BLANCO);
        btnConfirmar.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnConfirmar.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(580, 385, 80, 28);
        btnVolver.setBackground(AZUL_OSCURO);
        btnVolver.setForeground(BLANCO);
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnVolver.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        labelMensaje = new JLabel("");
        labelMensaje.setBounds(20, 420, 580, 18);
        labelMensaje.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelMensaje.setForeground(ROJO_ERROR);

        panelCentral.add(labelTitulo);
        panelCentral.add(labelTitInfo);
        panelCentral.add(labelVuelo);
        panelCentral.add(labelFecha);
        panelCentral.add(labelPasajeros);
        panelCentral.add(labelTipoMod);
        panelCentral.add(comboTipoModificacion);
        panelCentral.add(panelPasajero);
        panelCentral.add(panelAsiento);
        panelCentral.add(panelVuelo);
        panelCentral.add(btnCalcular);
        panelCentral.add(btnConfirmar);
        panelCentral.add(btnVolver);
        panelCentral.add(labelMensaje);

        panel.add(barraTop);
        panel.add(panelCentral);

        // ============================================================
        // EVENTOS
        // ============================================================

        // Combo tipo de modificación — cambia el panel visible
        comboTipoModificacion.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String seleccion = (String) comboTipoModificacion.getSelectedItem();
                    if (seleccion.equals("Cambio de datos de pasajero")) {
                        mostrarPanelPasajero();
                    } else if (seleccion.equals("Cambio de asiento")) {
                        mostrarPanelAsiento();
                    } else {
                        mostrarPanelVuelo();
                    }
                    // Limpiamos el cargo al cambiar de panel
                    labelMensaje.setText("");
                }
            }
        });

        // Botón CALCULAR CARGO
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = (String) comboTipoModificacion.getSelectedItem();

                if (tipo.equals("Cambio de datos de pasajero")) {
                    // Cargo: 2% del precio real del vuelo
                    double cargo = precioVuelo * Politicas.CARGO_CAMBIO_PASAJERO;
                    setCargoPasajero(cargo);
                    labelMensaje.setText("");

                } else if (tipo.equals("Cambio de asiento")) {
                    String categoriaNueva = (String) comboCategoriaAsiento.getSelectedItem();
                    double cargo = Politicas.getCargoCambioAsiento("ECONOMICA", categoriaNueva);
                    setCargoAsiento(cargo);
                    labelMensaje.setText("");

                } else {
                    String nuevaFecha = campNuevaFecha.getText().trim();
                    if (!nuevaFecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        labelMensaje.setText("La fecha debe tener el formato YYYY-MM-DD. Ej: 2026-08-01");
                        return;
                    }
                    if (comboNuevoOrigen.getSelectedItem().equals("Seleccionar...") ||
                            comboNuevoDestino.getSelectedItem().equals("Seleccionar...")) {
                        labelMensaje.setText("Seleccioná origen y destino del nuevo vuelo.");
                        return;
                    }
                    // Cargo: 10% del precio real del vuelo original
                    double cargo = precioVuelo * Politicas.CARGO_CAMBIO_VUELO;
                    setCargoVuelo(cargo);
                    labelMensaje.setText("Cargo base por cambio de vuelo calculado. El monto final puede variar según el precio del nuevo vuelo.");
                }
            }
        });

        // Botón CONFIRMAR Y PAGAR
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = (String) comboTipoModificacion.getSelectedItem();
                double cargo = 0;

                if (tipo.equals("Cambio de datos de pasajero")) {
                    String cargoTexto = labelCargoPasajero.getText();
                    if (cargoTexto.equals("Cargo estimado: -")) {
                        labelMensaje.setText("Presioná CALCULAR CARGO antes de confirmar.");
                        return;
                    }
                    cargo = Double.parseDouble(cargoTexto.replace("Cargo estimado: $", "").trim());

                    // Obtenemos el pasajero seleccionado con los datos nuevos
                    int indicePasajero = comboPasajeros.getSelectedIndex();
                    if (indicePasajero == -1 || pasajeros == null || pasajeros.isEmpty()) {
                        labelMensaje.setText("Seleccioná un pasajero.");
                        return;
                    }
                    Pasajero pasajeroModificado = pasajeros.get(indicePasajero);
                    pasajeroModificado.setNombre(campNuevoNombre.getText().trim());
                    pasajeroModificado.setApellido(campNuevoApellido.getText().trim());
                    pasajeroModificado.setFechaNacimiento(campNuevoNacimiento.getText().trim());
                    pasajeroModificado.setNroPasaporte(campNuevoPasaporte.getText().trim());

                    if (pasajeroModificado.getNombre().isEmpty() || pasajeroModificado.getApellido().isEmpty()) {
                        labelMensaje.setText("Completá los datos del pasajero.");
                        return;
                    }

                    // Validación: nombre y apellido solo letras
                    if (!pasajeroModificado.getNombre().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                        labelMensaje.setText("El nombre solo puede contener letras.");
                        return;
                    }
                    if (!pasajeroModificado.getApellido().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                        labelMensaje.setText("El apellido solo puede contener letras.");
                        return;
                    }

                    // Validación: formato de fecha de nacimiento
                    String fechaNac = pasajeroModificado.getFechaNacimiento();
                    if (!fechaNac.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        labelMensaje.setText("La fecha debe tener el formato YYYY-MM-DD. Ej: 1990-05-20");
                        return;
                    }
                    try {
                        java.time.LocalDate fechaNacDate = java.time.LocalDate.parse(fechaNac);
                        if (fechaNacDate.isAfter(java.time.LocalDate.now())) {
                            labelMensaje.setText("La fecha de nacimiento no puede ser futura.");
                            return;
                        }
                    } catch (Exception ex) {
                        labelMensaje.setText("Fecha de nacimiento inválida.");
                        return;
                    }

                    // Abrimos VentanaPago pasando el pasajero modificado
                    ArrayList<Pasajero> listaModificada = new ArrayList<>();
                    listaModificada.add(pasajeroModificado);
                    VentanaPago ventanaPago = new VentanaPago();
                    ventanaPago.cargarResumen("Modificación: " + tipo, 1, cargo);
                    ventanaPago.setDatosReserva(idVuelo, listaModificada);
                    ventanaPago.setModoModificacion(idReserva);
                    dispose();
                    return;

                } else if (tipo.equals("Cambio de asiento")) {
                    String cargoTexto = labelCargoAsiento.getText();
                    if (cargoTexto.equals("Cargo estimado: -")) {
                        labelMensaje.setText("Presioná CALCULAR CARGO antes de confirmar.");
                        return;
                    }
                    cargo = Double.parseDouble(cargoTexto.replace("Cargo estimado: $", "").trim());

                } else {
                    String cargoTexto = labelCargoVuelo.getText();
                    if (cargoTexto.equals("Cargo estimado: -")) {
                        labelMensaje.setText("Presioná CALCULAR CARGO antes de confirmar.");
                        return;
                    }

                    // Validaciones de origen, destino y fecha
                    String nuevoOrigen  = (String) comboNuevoOrigen.getSelectedItem();
                    String nuevoDestino = (String) comboNuevoDestino.getSelectedItem();
                    String nuevaFecha   = campNuevaFecha.getText().trim();

                    if (nuevoOrigen.equals("Seleccionar...") || nuevoDestino.equals("Seleccionar...")) {
                        labelMensaje.setText("Seleccioná origen y destino del nuevo vuelo.");
                        return;
                    }
                    if (nuevoOrigen.equals(nuevoDestino)) {
                        labelMensaje.setText("El origen y el destino no pueden ser iguales.");
                        return;
                    }
                    if (!nuevaFecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        labelMensaje.setText("La fecha debe tener el formato YYYY-MM-DD. Ej: 2026-08-01");
                        return;
                    }

                    cargo = Double.parseDouble(cargoTexto.replace("Cargo estimado: $", "").trim());
                }

                // Abrimos VentanaPago con el cargo calculado en modo MODIFICACION
                VentanaPago ventanaPago = new VentanaPago();
                ventanaPago.cargarResumen("Modificación: " + tipo, 1, cargo);
                ventanaPago.setDatosReserva(idVuelo, new ArrayList<>());
                ventanaPago.setModoModificacion(idReserva);
                dispose();
            }
        });

        // Botón VOLVER
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaMisReservas();
                dispose();
            }
        });

        add(panel);
    }

    public void cargarReserva(String vuelo, String fecha, String pasajeros) {
        labelVuelo.setText("Vuelo: " + vuelo);
        labelFecha.setText("Fecha: " + fecha);
        labelPasajeros.setText("Pasajeros: " + pasajeros);
    }

    // Recibe los datos reales de la reserva y carga los pasajeros en los combos
    public void setReserva(int idReserva, int idVuelo) {
        this.idReserva = idReserva;
        this.idVuelo   = idVuelo;

        // Obtenemos el precio real del vuelo para calcular cargos
        VueloDAO vueloDAO = new VueloDAO();
        Vuelo vuelo = vueloDAO.obtenerPorId(idVuelo);
        if (vuelo != null) {
            this.precioVuelo = vuelo.getPrecio();
            cargarReserva(vuelo.getOrigen() + " → " + vuelo.getDestino(),
                    vuelo.getFechaSalida(), "-");
        }

        // Cargamos los pasajeros reales en los combos
        PasajeroDAO pasajeroDAO = new PasajeroDAO();
        this.pasajeros = pasajeroDAO.obtenerPorReserva(idReserva);
        for (Pasajero p : this.pasajeros) {
            agregarPasajero(p.getNombre() + " " + p.getApellido());
        }
        labelPasajeros.setText("Pasajeros: " + this.pasajeros.size());
    }

    public void agregarPasajero(String nombreCompleto) {
        comboPasajeros.addItem(nombreCompleto);
        comboPasajerosAsiento.addItem(nombreCompleto);
    }

    public void mostrarPanelPasajero() {
        panelPasajero.setVisible(true);
        panelAsiento.setVisible(false);
        panelVuelo.setVisible(false);
    }

    public void mostrarPanelAsiento() {
        panelPasajero.setVisible(false);
        panelAsiento.setVisible(true);
        panelVuelo.setVisible(false);
    }

    public void mostrarPanelVuelo() {
        panelPasajero.setVisible(false);
        panelAsiento.setVisible(false);
        panelVuelo.setVisible(true);
    }

    public void setCargoPasajero(double cargo) { labelCargoPasajero.setText("Cargo estimado: $" + cargo); }
    public void setCargoAsiento(double cargo)  { labelCargoAsiento.setText("Cargo estimado: $" + cargo); }
    public void setCargoVuelo(double cargo)    { labelCargoVuelo.setText("Cargo estimado: $" + cargo); }
    public void setMensaje(String mensaje)     { labelMensaje.setText(mensaje); }
}