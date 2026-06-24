package vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.PasajeroDAO;
import dao.VueloDAO;
import modelo.Pasajero;
import modelo.Vuelo;
import java.util.ArrayList;

public class VentanaCheckin extends JFrame {

    private JLabel labelVuelo;
    private JLabel labelRuta;
    private JLabel labelFecha;
    private JLabel labelEstado;
    private JTable tablaPasajeros;
    private DefaultTableModel modeloTabla;
    private JPanel panelTarjeta;
    private JLabel labelTarjetaNombre;
    private JLabel labelTarjetaVuelo;
    private JLabel labelTarjetaRuta;
    private JLabel labelTarjetaFecha;
    private JLabel labelTarjetaAsiento;
    private JButton btnConfirmarCheckin;
    private JButton btnVolver;
    private JLabel labelMensaje;

    // ID de la reserva recibido desde VentanaMisReservas
    private int idReserva;
    private int idVuelo;

    private static final Color AZUL_OSCURO = new Color(26, 45, 90);
    private static final Color GRIS_PANEL  = new Color(220, 220, 220);
    private static final Color BLANCO      = Color.WHITE;
    private static final Color NEGRO       = Color.BLACK;
    private static final Color ROJO_ERROR  = new Color(180, 30, 30);

    public VentanaCheckin() {
        setTitle("Mirrors Airlines - Check-in");
        setSize(780, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        iniciarComponentes();
        setVisible(true);
    }

    // Recibe los datos de la reserva y carga los pasajeros
    public void setReserva(int idReserva, int idVuelo) {
        this.idReserva = idReserva;
        this.idVuelo   = idVuelo;

        VueloDAO vueloDAO = new VueloDAO();
        Vuelo vuelo = vueloDAO.obtenerPorId(idVuelo);
        if (vuelo != null) {
            cargarVuelo(vuelo.getAerolinea(), vuelo.getOrigen(), vuelo.getDestino(),
                    vuelo.getFechaSalida(), vuelo.getEstado());
        }

        // Cargamos pasajeros con su estado real de check-in desde la BD
        PasajeroDAO pasajeroDAO = new PasajeroDAO();
        ArrayList<Pasajero> pasajeros = pasajeroDAO.obtenerPorReserva(idReserva);
        for (Pasajero p : pasajeros) {
            String estadoCheckin = pasajeroDAO.tieneCheckin(p.getId()) ? "REALIZADO" : "PENDIENTE";
            Object[] fila = {p.getNombre(), p.getApellido(), p.getNroPasaporte(), estadoCheckin};
            modeloTabla.addRow(fila);
        }
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
        panelCentral.setBounds(40, 70, 695, 500);
        panelCentral.setBackground(GRIS_PANEL);
        panelCentral.setBorder(BorderFactory.createLineBorder(AZUL_OSCURO, 1));

        JLabel labelTitulo = new JLabel("CHECK-IN EN LÍNEA");
        labelTitulo.setBounds(285, 15, 200, 20);
        labelTitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        labelTitulo.setForeground(NEGRO);

        // --- Info vuelo ---
        JLabel labelTitVuelo = new JLabel("INFORMACIÓN DEL VUELO");
        labelTitVuelo.setBounds(20, 45, 220, 15);
        labelTitVuelo.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitVuelo.setForeground(NEGRO);

        labelVuelo = new JLabel("Aerolínea: -");
        labelVuelo.setBounds(20, 63, 310, 15);
        labelVuelo.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelVuelo.setForeground(NEGRO);

        labelRuta = new JLabel("Ruta: -");
        labelRuta.setBounds(20, 80, 310, 15);
        labelRuta.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelRuta.setForeground(NEGRO);

        labelFecha = new JLabel("Fecha de salida: -");
        labelFecha.setBounds(350, 63, 310, 15);
        labelFecha.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelFecha.setForeground(NEGRO);

        labelEstado = new JLabel("Estado: -");
        labelEstado.setBounds(350, 80, 310, 15);
        labelEstado.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelEstado.setForeground(NEGRO);

        // --- Tabla pasajeros ---
        JLabel labelTitPas = new JLabel("SELECCIONÁ EL PASAJERO PARA HACER CHECK-IN");
        labelTitPas.setBounds(20, 108, 380, 15);
        labelTitPas.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitPas.setForeground(NEGRO);

        String[] columnas = {"NOMBRE", "APELLIDO", "PASAPORTE", "CHECK-IN"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPasajeros = new JTable(modeloTabla);
        tablaPasajeros.setDefaultEditor(Object.class, null);
        tablaPasajeros.setFont(new Font("SansSerif", Font.PLAIN, 10));
        tablaPasajeros.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 10));
        tablaPasajeros.getTableHeader().setBackground(AZUL_OSCURO);
        tablaPasajeros.getTableHeader().setForeground(BLANCO);

        JScrollPane scrollTabla = new JScrollPane(tablaPasajeros);
        scrollTabla.setBounds(20, 126, 650, 120);

        // --- Tarjeta de embarque (oculta hasta hacer check-in) ---
        panelTarjeta = new JPanel();
        panelTarjeta.setLayout(null);
        panelTarjeta.setBounds(20, 260, 650, 140);
        panelTarjeta.setBackground(AZUL_OSCURO);
        panelTarjeta.setBorder(BorderFactory.createLineBorder(BLANCO, 1));
        panelTarjeta.setVisible(false);

        JLabel labelTitTarjeta = new JLabel("✈  TARJETA DE EMBARQUE  ✈");
        labelTitTarjeta.setBounds(185, 10, 280, 20);
        labelTitTarjeta.setFont(new Font("SansSerif", Font.BOLD, 12));
        labelTitTarjeta.setForeground(BLANCO);

        labelTarjetaNombre = new JLabel("Pasajero: -");
        labelTarjetaNombre.setBounds(15, 38, 300, 18);
        labelTarjetaNombre.setFont(new Font("SansSerif", Font.PLAIN, 11));
        labelTarjetaNombre.setForeground(BLANCO);

        labelTarjetaVuelo = new JLabel("Vuelo: -");
        labelTarjetaVuelo.setBounds(15, 58, 300, 18);
        labelTarjetaVuelo.setFont(new Font("SansSerif", Font.PLAIN, 11));
        labelTarjetaVuelo.setForeground(BLANCO);

        labelTarjetaRuta = new JLabel("Ruta: -");
        labelTarjetaRuta.setBounds(15, 78, 300, 18);
        labelTarjetaRuta.setFont(new Font("SansSerif", Font.PLAIN, 11));
        labelTarjetaRuta.setForeground(BLANCO);

        labelTarjetaFecha = new JLabel("Fecha: -");
        labelTarjetaFecha.setBounds(350, 38, 280, 18);
        labelTarjetaFecha.setFont(new Font("SansSerif", Font.PLAIN, 11));
        labelTarjetaFecha.setForeground(BLANCO);

        labelTarjetaAsiento = new JLabel("Asiento: -");
        labelTarjetaAsiento.setBounds(350, 58, 280, 18);
        labelTarjetaAsiento.setFont(new Font("SansSerif", Font.PLAIN, 11));
        labelTarjetaAsiento.setForeground(BLANCO);

        panelTarjeta.add(labelTitTarjeta);
        panelTarjeta.add(labelTarjetaNombre);
        panelTarjeta.add(labelTarjetaVuelo);
        panelTarjeta.add(labelTarjetaRuta);
        panelTarjeta.add(labelTarjetaFecha);
        panelTarjeta.add(labelTarjetaAsiento);

        // --- Botones ---
        btnConfirmarCheckin = new JButton("CONFIRMAR CHECK-IN");
        btnConfirmarCheckin.setBounds(20, 420, 180, 28);
        btnConfirmarCheckin.setBackground(AZUL_OSCURO);
        btnConfirmarCheckin.setForeground(BLANCO);
        btnConfirmarCheckin.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnConfirmarCheckin.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnConfirmarCheckin.setFocusPainted(false);
        btnConfirmarCheckin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(595, 420, 80, 28);
        btnVolver.setBackground(AZUL_OSCURO);
        btnVolver.setForeground(BLANCO);
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnVolver.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        labelMensaje = new JLabel("");
        labelMensaje.setBounds(20, 458, 580, 18);
        labelMensaje.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelMensaje.setForeground(ROJO_ERROR);

        panelCentral.add(labelTitulo);
        panelCentral.add(labelTitVuelo);
        panelCentral.add(labelVuelo);
        panelCentral.add(labelRuta);
        panelCentral.add(labelFecha);
        panelCentral.add(labelEstado);
        panelCentral.add(labelTitPas);
        panelCentral.add(scrollTabla);
        panelCentral.add(panelTarjeta);
        panelCentral.add(btnConfirmarCheckin);
        panelCentral.add(btnVolver);
        panelCentral.add(labelMensaje);

        // ============================================================
        // EVENTOS
        // ============================================================

        // Selección de fila — permite elegir el pasajero al que hacer check-in
        // Si ya hizo check-in muestra su tarjeta directamente
        tablaPasajeros.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int fila = tablaPasajeros.getSelectedRow();
                    if (fila != -1) {
                        String estadoCheckin = (String) modeloTabla.getValueAt(fila, 3);
                        if (estadoCheckin.equals("REALIZADO")) {
                            // Mostramos la tarjeta del pasajero seleccionado
                            PasajeroDAO pasajeroDAO = new PasajeroDAO();
                            ArrayList<Pasajero> pasajeros = pasajeroDAO.obtenerPorReserva(idReserva);
                            Pasajero p = pasajeros.get(fila);
                            String asiento = pasajeroDAO.obtenerAsiento(p.getId());
                            String nombre = (String) modeloTabla.getValueAt(fila, 0) + " " +
                                    (String) modeloTabla.getValueAt(fila, 1);
                            String vuelo  = labelVuelo.getText().replace("Aerolínea: ", "");
                            String ruta   = labelRuta.getText().replace("Ruta: ", "");
                            String fecha  = labelFecha.getText().replace("Fecha de salida: ", "");
                            mostrarTarjeta(nombre, vuelo, ruta, fecha, asiento != null ? asiento : "-");
                            labelMensaje.setText("Tarjeta de embarque de " + nombre);
                        } else {
                            panelTarjeta.setVisible(false);
                            labelMensaje.setText("Pasajero seleccionado. Presioná CONFIRMAR CHECK-IN.");
                        }
                    }
                }
            }
        });

        // Botón CONFIRMAR CHECK-IN
        btnConfirmarCheckin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int fila = tablaPasajeros.getSelectedRow();
                if (fila == -1) {
                    labelMensaje.setText("Seleccioná un pasajero de la tabla primero.");
                    return;
                }

                // Verificamos en la BD que no haya hecho check-in ya
                PasajeroDAO pasajeroDAO = new PasajeroDAO();
                ArrayList<Pasajero> pasajeros = pasajeroDAO.obtenerPorReserva(idReserva);
                Pasajero pasajeroSeleccionado = pasajeros.get(fila);

                if (pasajeroDAO.tieneCheckin(pasajeroSeleccionado.getId())) {
                    labelMensaje.setText("Este pasajero ya realizó el check-in.");
                    return;
                }

                // Marcamos en la tabla visual
                modeloTabla.setValueAt("REALIZADO", fila, 3);

                // Guardamos en la BD
                pasajeroDAO.marcarCheckin(pasajeroSeleccionado.getId());

                // Generamos y guardamos el asiento
                int numeroAsiento = (int)(Math.random() * 180) + 1;
                String letra = new String[]{"A", "B", "C", "D", "E", "F"}[(int)(Math.random() * 6)];
                String asiento = numeroAsiento + letra;
                pasajeroDAO.guardarAsiento(pasajeroSeleccionado.getId(), asiento);

                // Mostramos la tarjeta de embarque
                String nombre  = (String) modeloTabla.getValueAt(fila, 0) + " " +
                        (String) modeloTabla.getValueAt(fila, 1);
                String vuelo   = labelVuelo.getText().replace("Aerolínea: ", "");
                String ruta    = labelRuta.getText().replace("Ruta: ", "");
                String fecha   = labelFecha.getText().replace("Fecha de salida: ", "");
                mostrarTarjeta(nombre, vuelo, ruta, fecha, asiento);

                // Verificamos si quedan pasajeros con check-in pendiente
                boolean hayPendientes = false;
                for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                    if (modeloTabla.getValueAt(i, 3).equals("PENDIENTE")) {
                        hayPendientes = true;
                        break;
                    }
                }

                if (hayPendientes) {
                    labelMensaje.setText("Check-in realizado. Seleccioná el siguiente pasajero.");
                } else {
                    labelMensaje.setText("¡Todos los pasajeros realizaron el check-in! Presioná VOLVER cuando quieras.");
                    // Cambiamos el botón VOLVER para que vaya a VentanaBusqueda
                    btnVolver.setText("IR A BUSCAR VUELOS");
                }
            }
        });

        // Botón VOLVER
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnVolver.getText().equals("IR A BUSCAR VUELOS")) {
                    new VentanaBusqueda();
                } else {
                    new VentanaMisReservas();
                }
                dispose();
            }
        });

        panel.add(barraTop);
        panel.add(panelCentral);

        add(panel);
    }

    public void cargarVuelo(String aerolinea, String origen, String destino,
                            String fecha, String estado) {
        labelVuelo.setText("Aerolínea: " + aerolinea);
        labelRuta.setText("Ruta: " + origen + " → " + destino);
        labelFecha.setText("Fecha de salida: " + fecha);
        labelEstado.setText("Estado: " + estado);
    }

    public void agregarPasajero(String nombre, String apellido, String pasaporte) {
        Object[] fila = {nombre, apellido, pasaporte, "PENDIENTE"};
        modeloTabla.addRow(fila);
    }

    public void mostrarTarjeta(String nombre, String vuelo, String ruta,
                               String fecha, String asiento) {
        labelTarjetaNombre.setText("Pasajero: " + nombre);
        labelTarjetaVuelo.setText("Vuelo: " + vuelo);
        labelTarjetaRuta.setText("Ruta: " + ruta);
        labelTarjetaFecha.setText("Fecha: " + fecha);
        labelTarjetaAsiento.setText("Asiento: " + asiento);
        panelTarjeta.setVisible(true);
    }

    public void setMensaje(String mensaje) { labelMensaje.setText(mensaje); }
}
