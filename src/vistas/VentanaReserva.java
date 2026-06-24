package vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Pasajero;
import java.util.ArrayList;

public class VentanaReserva extends JFrame {

    private JLabel labelAerolinea;
    private JLabel labelRuta;
    private JLabel labelFecha;
    private JLabel labelPrecio;
    private JLabel labelEstado;
    private JTextField campNombre;
    private JTextField campApellido;
    private JTextField campFechaNacimiento;
    private JTextField campPasaporte;
    private JButton btnAgregarPasajero;
    private JTable tablaPasajeros;
    private DefaultTableModel modeloTabla;
    private JLabel labelTotal;
    private JButton btnConfirmar;
    private JButton btnVolver;
    private JLabel labelMensaje;

    // Guardamos el ID del vuelo y la lista de pasajeros
    // para pasarlos a VentanaPago al confirmar
    private int idVuelo;
    private int maxPasajeros = 1; // límite según lo elegido en VentanaBusqueda
    private ArrayList<Pasajero> listaPasajeros = new ArrayList<>();

    private static final Color AZUL_OSCURO = new Color(26, 45, 90);
    private static final Color GRIS_PANEL  = new Color(220, 220, 220);
    private static final Color BLANCO      = Color.WHITE;
    private static final Color NEGRO       = Color.BLACK;
    private static final Color ROJO_ERROR  = new Color(180, 30, 30);

    public VentanaReserva() {
        setTitle("Mirrors Airlines - Reserva");
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

        JLabel labelTitulo = new JLabel("RESERVA DE PASAJE");
        labelTitulo.setBounds(275, 15, 220, 20);
        labelTitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        labelTitulo.setForeground(NEGRO);

        // --- Info vuelo ---
        JLabel labelTitVuelo = new JLabel("VUELO SELECCIONADO");
        labelTitVuelo.setBounds(20, 45, 200, 15);
        labelTitVuelo.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitVuelo.setForeground(NEGRO);

        labelAerolinea = new JLabel("Aerolínea: -");
        labelAerolinea.setBounds(20, 63, 300, 15);
        labelAerolinea.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelAerolinea.setForeground(NEGRO);

        labelRuta = new JLabel("Ruta: -");
        labelRuta.setBounds(20, 80, 300, 15);
        labelRuta.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelRuta.setForeground(NEGRO);

        labelFecha = new JLabel("Fecha: -");
        labelFecha.setBounds(350, 63, 300, 15);
        labelFecha.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelFecha.setForeground(NEGRO);

        labelPrecio = new JLabel("Precio por pasajero: -");
        labelPrecio.setBounds(350, 80, 300, 15);
        labelPrecio.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelPrecio.setForeground(NEGRO);

        labelEstado = new JLabel("Estado: -");
        labelEstado.setBounds(20, 97, 300, 15);
        labelEstado.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelEstado.setForeground(NEGRO);

        // --- Formulario pasajero ---
        JLabel labelTitPasajero = new JLabel("DATOS DEL PASAJERO");
        labelTitPasajero.setBounds(20, 125, 200, 15);
        labelTitPasajero.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitPasajero.setForeground(NEGRO);

        campNombre = new JTextField();
        campNombre.setBounds(20, 145, 155, 28);
        campNombre.setFont(new Font("SansSerif", Font.PLAIN, 11));
        campNombre.setForeground(Color.GRAY);
        campNombre.setText("NOMBRE");
        campNombre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        campNombre.setBackground(BLANCO);

        campApellido = new JTextField();
        campApellido.setBounds(185, 145, 155, 28);
        campApellido.setFont(new Font("SansSerif", Font.PLAIN, 11));
        campApellido.setForeground(Color.GRAY);
        campApellido.setText("APELLIDO");
        campApellido.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        campApellido.setBackground(BLANCO);

        campFechaNacimiento = new JTextField();
        campFechaNacimiento.setBounds(350, 145, 155, 28);
        campFechaNacimiento.setFont(new Font("SansSerif", Font.PLAIN, 11));
        campFechaNacimiento.setForeground(Color.GRAY);
        campFechaNacimiento.setText("NACIMIENTO");
        campFechaNacimiento.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        campFechaNacimiento.setBackground(BLANCO);

        campPasaporte = new JTextField();
        campPasaporte.setBounds(515, 145, 155, 28);
        campPasaporte.setFont(new Font("SansSerif", Font.PLAIN, 11));
        campPasaporte.setForeground(Color.GRAY);
        campPasaporte.setText("PASAPORTE");
        campPasaporte.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        campPasaporte.setBackground(BLANCO);

        btnAgregarPasajero = new JButton("AGREGAR PASAJERO");
        btnAgregarPasajero.setBounds(240, 185, 200, 28);
        btnAgregarPasajero.setBackground(AZUL_OSCURO);
        btnAgregarPasajero.setForeground(BLANCO);
        btnAgregarPasajero.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnAgregarPasajero.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnAgregarPasajero.setFocusPainted(false);
        btnAgregarPasajero.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // --- Tabla pasajeros ---
        JLabel labelTitTabla = new JLabel("PASAJEROS EN ESTA RESERVA");
        labelTitTabla.setBounds(20, 225, 250, 15);
        labelTitTabla.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitTabla.setForeground(NEGRO);

        String[] columnas = {"NOMBRE", "APELLIDO", "NACIMIENTO", "PASAPORTE"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPasajeros = new JTable(modeloTabla);
        tablaPasajeros.setDefaultEditor(Object.class, null);
        tablaPasajeros.setFont(new Font("SansSerif", Font.PLAIN, 10));
        tablaPasajeros.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 10));
        tablaPasajeros.getTableHeader().setBackground(AZUL_OSCURO);
        tablaPasajeros.getTableHeader().setForeground(BLANCO);

        JScrollPane scrollTabla = new JScrollPane(tablaPasajeros);
        scrollTabla.setBounds(20, 243, 650, 150);

        // --- Total y botones ---
        labelTotal = new JLabel("TOTAL: $0.00");
        labelTotal.setBounds(20, 408, 200, 20);
        labelTotal.setFont(new Font("SansSerif", Font.BOLD, 11));
        labelTotal.setForeground(NEGRO);

        btnConfirmar = new JButton("IR A PAGAR");
        btnConfirmar.setBounds(400, 430, 130, 28);
        btnConfirmar.setBackground(AZUL_OSCURO);
        btnConfirmar.setForeground(BLANCO);
        btnConfirmar.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnConfirmar.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(545, 430, 100, 28);
        btnVolver.setBackground(AZUL_OSCURO);
        btnVolver.setForeground(BLANCO);
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnVolver.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        labelMensaje = new JLabel("");
        labelMensaje.setBounds(20, 465, 500, 18);
        labelMensaje.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelMensaje.setForeground(ROJO_ERROR);

        panelCentral.add(labelTitulo);
        panelCentral.add(labelTitVuelo);
        panelCentral.add(labelAerolinea);
        panelCentral.add(labelRuta);
        panelCentral.add(labelFecha);
        panelCentral.add(labelPrecio);
        panelCentral.add(labelEstado);
        panelCentral.add(labelTitPasajero);
        panelCentral.add(campNombre);
        panelCentral.add(campApellido);
        panelCentral.add(campFechaNacimiento);
        panelCentral.add(campPasaporte);
        panelCentral.add(btnAgregarPasajero);
        panelCentral.add(labelTitTabla);
        panelCentral.add(scrollTabla);
        panelCentral.add(labelTotal);
        panelCentral.add(btnConfirmar);
        panelCentral.add(btnVolver);
        panelCentral.add(labelMensaje);

        // ============================================================
        // EVENTOS
        // ============================================================

        // Botón AGREGAR PASAJERO
        btnAgregarPasajero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombre    = campNombre.getText().trim();
                String apellido  = campApellido.getText().trim();
                String fechaNac  = campFechaNacimiento.getText().trim();
                String pasaporte = campPasaporte.getText().trim();

                // Validación: no superar el máximo de pasajeros
                if (listaPasajeros.size() >= maxPasajeros) {
                    labelMensaje.setText("Ya cargaste el máximo de " + maxPasajeros + " pasajero/s.");
                    return;
                }

                // Validación: ningún campo vacío ni con placeholder
                if (nombre.isEmpty()    || nombre.equals("NOMBRE")     ||
                        apellido.isEmpty()  || apellido.equals("APELLIDO")  ||
                        fechaNac.isEmpty()  || fechaNac.equals("NACIMIENTO")||
                        pasaporte.isEmpty() || pasaporte.equals("PASAPORTE")) {
                    labelMensaje.setText("Completá todos los datos del pasajero.");
                    return;
                }

                // Validación: nombre y apellido solo letras
                if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    labelMensaje.setText("El nombre solo puede contener letras.");
                    return;
                }
                if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                    labelMensaje.setText("El apellido solo puede contener letras.");
                    return;
                }

                // Validación: formato de fecha correcto (YYYY-MM-DD)
                if (!fechaNac.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    labelMensaje.setText("La fecha debe tener el formato YYYY-MM-DD. Ej: 1990-05-20");
                    return;
                }

                // Validación: la fecha de nacimiento no puede ser futura
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

                // Agregamos a la tabla visual
                agregarPasajeroTabla(nombre, apellido, fechaNac, pasaporte);

                // Guardamos en la lista para pasarla a VentanaPago
                Pasajero nuevoPasajero = new Pasajero(0, nombre, apellido, fechaNac, pasaporte);
                listaPasajeros.add(nuevoPasajero);

                // Actualizamos el total según la cantidad de pasajeros
                // El precio lo extraemos del label (sacamos "Precio por pasajero: $")
                String precioTexto = labelPrecio.getText().replace("Precio por pasajero: $", "").trim();
                double precioPorPasajero = Double.parseDouble(precioTexto);
                int cantPasajeros = modeloTabla.getRowCount();
                actualizarTotal(precioPorPasajero * cantPasajeros);

                // Limpiamos el formulario para cargar el siguiente
                limpiarFormularioPasajero();
                labelMensaje.setText("");
            }
        });

        // Botón IR A PAGAR
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Validamos que haya al menos un pasajero
                if (modeloTabla.getRowCount() == 0) {
                    labelMensaje.setText("Agregá al menos un pasajero antes de continuar.");
                    return;
                }

                // Validamos que estén todos los pasajeros requeridos
                if (listaPasajeros.size() < maxPasajeros) {
                    labelMensaje.setText("Faltan " + (maxPasajeros - listaPasajeros.size()) + " pasajero/s por cargar.");
                    return;
                }

                // Obtenemos los datos para pasarlos a VentanaPago
                String totalTexto = labelTotal.getText().replace("TOTAL: $", "").trim();
                double total = Double.parseDouble(totalTexto);
                String vuelo = labelRuta.getText().replace("Ruta: ", "").trim();
                int cantPasajeros = modeloTabla.getRowCount();

                VentanaPago ventanaPago = new VentanaPago();
                ventanaPago.cargarResumen(vuelo, cantPasajeros, total);
                ventanaPago.setDatosReserva(idVuelo, listaPasajeros);
                dispose();
            }
        });

        // Botón VOLVER
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!listaPasajeros.isEmpty()) {
                    int confirmacion = JOptionPane.showConfirmDialog(
                            null,
                            "Si volvés perderás los pasajeros cargados. ¿Confirmás?",
                            "Confirmar",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirmacion != JOptionPane.YES_OPTION) return;
                }
                new VentanaBusqueda();
                dispose();
            }
        });

        panel.add(barraTop);
        panel.add(panelCentral);

        add(panel);
    }

    public void cargarVuelo(int id, String aerolinea, String origen, String destino,
                            String fecha, double precio, String estado, int maxPasajeros) {
        this.idVuelo      = id;
        this.maxPasajeros = maxPasajeros;
        labelAerolinea.setText("Aerolínea: " + aerolinea);
        labelRuta.setText("Ruta: " + origen + " → " + destino);
        labelFecha.setText("Fecha: " + fecha);
        labelPrecio.setText("Precio por pasajero: $" + precio);
        labelEstado.setText("Estado: " + estado);
    }

    public void agregarPasajeroTabla(String nombre, String apellido,
                                     String fechaNac, String pasaporte) {
        Object[] fila = {nombre, apellido, fechaNac, pasaporte};
        modeloTabla.addRow(fila);
    }

    public void actualizarTotal(double total) {
        labelTotal.setText("TOTAL: $" + total);
    }

    public void limpiarFormularioPasajero() {
        campNombre.setText("NOMBRE");
        campApellido.setText("APELLIDO");
        campFechaNacimiento.setText("NACIMIENTO");
        campPasaporte.setText("PASAPORTE");
    }
}