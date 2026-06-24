package vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.VueloDAO;
import modelo.Vuelo;
import modelo.SesionActual;
import java.util.ArrayList;

public class VentanaBusqueda extends JFrame {

    private JComboBox<String> comboOrigen;
    private JComboBox<String> comboDestino;
    private JTextField campFecha;
    private JTextField campPasajeros;
    private JButton btnBuscar;
    private JButton btnMisReservas;
    private JButton btnCerrarSesion;
    private JTable tablaVuelos;
    private DefaultTableModel modeloTabla;
    private JLabel labelMensaje;
    private JButton btnReservar;

    private static final Color AZUL_OSCURO = new Color(26, 45, 90);
    private static final Color GRIS_PANEL  = new Color(220, 220, 220);
    private static final Color BLANCO      = Color.WHITE;
    private static final Color NEGRO       = Color.BLACK;
    private static final Color ROJO_ERROR  = new Color(180, 30, 30);

    public VentanaBusqueda() {
        setTitle("Mirrors Airlines - Buscar Vuelos");
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

        btnCerrarSesion = new JButton("CERRAR SESIÓN");
        btnCerrarSesion.setBounds(630, 15, 130, 25);
        btnCerrarSesion.setBackground(AZUL_OSCURO);
        btnCerrarSesion.setForeground(BLANCO);
        btnCerrarSesion.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnMisReservas = new JButton("MIS RESERVAS");
        btnMisReservas.setBounds(490, 15, 130, 25);
        btnMisReservas.setBackground(AZUL_OSCURO);
        btnMisReservas.setForeground(BLANCO);
        btnMisReservas.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnMisReservas.setFocusPainted(false);
        btnMisReservas.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnMisReservas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        barraTop.add(labelM);
        barraTop.add(labelIrrors);
        barraTop.add(labelAirlines);
        barraTop.add(btnMisReservas);
        barraTop.add(btnCerrarSesion);

        // ============================================================
        // PANEL CENTRAL
        // ============================================================
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(null);
        panelCentral.setBounds(40, 70, 695, 460);
        panelCentral.setBackground(GRIS_PANEL);
        panelCentral.setBorder(BorderFactory.createLineBorder(AZUL_OSCURO, 1));

        JLabel labelTitulo = new JLabel("BUSCAR VUELOS");
        labelTitulo.setBounds(285, 15, 200, 20);
        labelTitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        labelTitulo.setForeground(NEGRO);

        // --- Filtros ---
        JLabel labelOrigen = new JLabel("ORIGEN");
        labelOrigen.setBounds(20, 50, 80, 15);
        labelOrigen.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelOrigen.setForeground(NEGRO);

        String[] ciudades = {"Seleccionar...", "Mendoza", "Buenos Aires", "Córdoba", "Bariloche"};
        comboOrigen = new JComboBox<>(ciudades);
        comboOrigen.setBounds(20, 68, 145, 28);
        comboOrigen.setBackground(BLANCO);
        comboOrigen.setFont(new Font("SansSerif", Font.PLAIN, 11));

        JLabel labelDestino = new JLabel("DESTINO");
        labelDestino.setBounds(180, 50, 80, 15);
        labelDestino.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelDestino.setForeground(NEGRO);

        comboDestino = new JComboBox<>(ciudades);
        comboDestino.setBounds(180, 68, 145, 28);
        comboDestino.setBackground(BLANCO);
        comboDestino.setFont(new Font("SansSerif", Font.PLAIN, 11));

        JLabel labelFecha = new JLabel("FECHA");
        labelFecha.setBounds(340, 50, 80, 15);
        labelFecha.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelFecha.setForeground(NEGRO);

        campFecha = new JTextField("2026-08-01");
        campFecha.setBounds(340, 68, 130, 28);
        campFecha.setFont(new Font("SansSerif", Font.PLAIN, 11));
        campFecha.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        campFecha.setBackground(BLANCO);

        JLabel labelPasajeros = new JLabel("PASAJEROS");
        labelPasajeros.setBounds(485, 50, 90, 15);
        labelPasajeros.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelPasajeros.setForeground(NEGRO);

        campPasajeros = new JTextField("1");
        campPasajeros.setBounds(485, 68, 60, 28);
        campPasajeros.setFont(new Font("SansSerif", Font.PLAIN, 11));
        campPasajeros.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        campPasajeros.setBackground(BLANCO);

        btnBuscar = new JButton("BUSCAR");
        btnBuscar.setBounds(570, 68, 100, 28);
        btnBuscar.setBackground(AZUL_OSCURO);
        btnBuscar.setForeground(BLANCO);
        btnBuscar.setFont(new Font("SansSerif", Font.BOLD, 11));
        btnBuscar.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // --- Tabla de resultados ---
        String[] columnas = {"ID", "AEROLÍNEA", "ORIGEN", "DESTINO", "SALIDA", "LLEGADA", "PRECIO", "ESTADO"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaVuelos = new JTable(modeloTabla);
        tablaVuelos.setDefaultEditor(Object.class, null);
        tablaVuelos.setFont(new Font("SansSerif", Font.PLAIN, 10));
        tablaVuelos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 10));
        tablaVuelos.getTableHeader().setBackground(AZUL_OSCURO);
        tablaVuelos.getTableHeader().setForeground(BLANCO);

        JScrollPane scrollTabla = new JScrollPane(tablaVuelos);
        scrollTabla.setBounds(20, 115, 650, 270);

        labelMensaje = new JLabel("Completá los campos y presioná BUSCAR");
        labelMensaje.setBounds(20, 395, 400, 20);
        labelMensaje.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelMensaje.setForeground(NEGRO);

        btnReservar = new JButton("RESERVAR VUELO SELECCIONADO");
        btnReservar.setBounds(390, 420, 270, 28);
        btnReservar.setBackground(AZUL_OSCURO);
        btnReservar.setForeground(BLANCO);
        btnReservar.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnReservar.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnReservar.setFocusPainted(false);
        btnReservar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        panelCentral.add(labelTitulo);
        panelCentral.add(labelOrigen);
        panelCentral.add(comboOrigen);
        panelCentral.add(labelDestino);
        panelCentral.add(comboDestino);
        panelCentral.add(labelFecha);
        panelCentral.add(campFecha);
        panelCentral.add(labelPasajeros);
        panelCentral.add(campPasajeros);
        panelCentral.add(btnBuscar);
        panelCentral.add(scrollTabla);
        panelCentral.add(labelMensaje);
        panelCentral.add(btnReservar);

        // ============================================================
        // EVENTOS
        // ============================================================

        // Botón MIS RESERVAS
        btnMisReservas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaMisReservas();
                dispose();
            }
        });

        // Botón CERRAR SESIÓN
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SesionActual.cerrar();
                new VentanaLogin();
                dispose();
            }
        });

        // Botón BUSCAR — se conecta a la BD con JDBC
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String origen  = (String) comboOrigen.getSelectedItem();
                String destino = (String) comboDestino.getSelectedItem();
                String fecha   = campFecha.getText().trim();

                // Validaciones básicas
                if (origen.equals("Seleccionar...") || destino.equals("Seleccionar...")) {
                    labelMensaje.setText("Seleccioná origen y destino.");
                    return;
                }

                // Validamos que origen y destino sean distintos
                if (origen.equals(destino)) {
                    labelMensaje.setText("El origen y el destino no pueden ser iguales.");
                    return;
                }
                if (fecha.isEmpty()) {
                    labelMensaje.setText("Ingresá una fecha.");
                    return;
                }

                // Validación: formato de fecha correcto (YYYY-MM-DD)
                if (!fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    labelMensaje.setText("La fecha debe tener el formato YYYY-MM-DD. Ej: 2026-08-01");
                    return;
                }

                // Validación: la fecha no puede ser en el pasado
                try {
                    java.time.LocalDate fechaBusqueda = java.time.LocalDate.parse(fecha);
                    if (fechaBusqueda.isBefore(java.time.LocalDate.now())) {
                        labelMensaje.setText("La fecha de búsqueda no puede ser en el pasado.");
                        return;
                    }
                } catch (Exception ex) {
                    labelMensaje.setText("Fecha inválida. Usá el formato YYYY-MM-DD.");
                    return;
                }

                // Búsqueda en la BD con VueloDAO
                VueloDAO vueloDAO = new VueloDAO();
                ArrayList<Vuelo> vuelos = vueloDAO.buscarVuelos(origen, destino, fecha);

                // Limpiamos la tabla antes de cargar nuevos resultados
                limpiarTabla();

                if (vuelos.isEmpty()) {
                    labelMensaje.setText("No se encontraron vuelos para esa búsqueda.");
                    return;
                }

                // Cargamos cada vuelo en la tabla
                for (Vuelo v : vuelos) {
                    agregarVuelo(v.getId(), v.getAerolinea(), v.getOrigen(), v.getDestino(),
                            v.getFechaSalida(), v.getFechaLlegada(), v.getPrecio(), v.getEstado());
                }
                labelMensaje.setText(vuelos.size() + " vuelo/s encontrado/s.");
            }
        });

        // Botón RESERVAR
        btnReservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Verificamos que el usuario haya seleccionado una fila
                int filaSeleccionada = tablaVuelos.getSelectedRow();
                if (filaSeleccionada == -1) {
                    labelMensaje.setText("Seleccioná un vuelo de la tabla primero.");
                    return;
                }

                // Obtenemos los datos del vuelo seleccionado
                // Los índices corresponden a las columnas: ID, AEROLÍNEA, ORIGEN, DESTINO, SALIDA, LLEGADA, PRECIO, ESTADO
                int id           = (int)    modeloTabla.getValueAt(filaSeleccionada, 0);
                String aerolinea = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
                String origen    = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
                String destino   = (String) modeloTabla.getValueAt(filaSeleccionada, 3);
                String fecha     = (String) modeloTabla.getValueAt(filaSeleccionada, 4);
                String precioStr = (String) modeloTabla.getValueAt(filaSeleccionada, 6);
                String estado    = (String) modeloTabla.getValueAt(filaSeleccionada, 7);

                // Convertimos el precio — le sacamos el $ del texto
                double precio = Double.parseDouble(precioStr.replace("$", ""));

                // Abrimos VentanaReserva y le pasamos los datos del vuelo
                // Validamos que el campo pasajeros sea un número válido
                int cantPasajeros;
                try {
                    cantPasajeros = Integer.parseInt(campPasajeros.getText().trim());
                    if (cantPasajeros <= 0) {
                        labelMensaje.setText("La cantidad de pasajeros debe ser mayor a 0.");
                        return;
                    }
                    if (cantPasajeros > 9) {
                        labelMensaje.setText("El máximo de pasajeros por reserva es 9.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    labelMensaje.setText("Ingresá un número válido en el campo pasajeros.");
                    return;
                }

                // Validamos que haya suficientes asientos disponibles
                VueloDAO vueloDAO = new VueloDAO();
                Vuelo vueloSeleccionado = vueloDAO.obtenerPorId(id);
                if (vueloSeleccionado != null && cantPasajeros > vueloSeleccionado.getAsientosDisponibles()) {
                    labelMensaje.setText("No hay suficientes asientos. Disponibles: " + vueloSeleccionado.getAsientosDisponibles());
                    return;
                }

                VentanaReserva ventanaReserva = new VentanaReserva();
                ventanaReserva.cargarVuelo(id, aerolinea, origen, destino, fecha, precio, estado, cantPasajeros);
                dispose();
            }
        });

        panel.add(barraTop);
        panel.add(panelCentral);

        add(panel);
    }

    public void agregarVuelo(int id, String aerolinea, String origen, String destino,
                             String salida, String llegada, double precio, String estado) {
        Object[] fila = {id, aerolinea, origen, destino, salida, llegada, "$" + precio, estado};
        modeloTabla.addRow(fila);
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    public void setMensaje(String mensaje) {
        labelMensaje.setText(mensaje);
    }
}