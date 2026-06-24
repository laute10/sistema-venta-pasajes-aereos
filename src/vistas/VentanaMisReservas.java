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
import dao.ReservaDAO;
import dao.VueloDAO;
import dao.PagoDAO;
import dao.PasajeroDAO;
import modelo.Reserva;
import modelo.Vuelo;
import modelo.Pago;
import modelo.SesionActual;
import java.util.ArrayList;

public class VentanaMisReservas extends JFrame {

    private JTable tablaReservas;
    private DefaultTableModel modeloTabla;
    private JLabel labelDetalleVuelo;
    private JLabel labelDetalleFecha;
    private JLabel labelDetalleEstado;
    private JLabel labelDetallePasajeros;
    private JLabel labelDetallePago;
    private JButton btnCancelar;
    private JButton btnModificar;
    private JButton btnCheckin;
    private JButton btnVerPoliticas;
    private JButton btnVolver;
    private JLabel labelMensaje;

    private static final Color AZUL_OSCURO = new Color(26, 45, 90);
    private static final Color GRIS_PANEL  = new Color(220, 220, 220);
    private static final Color BLANCO      = Color.WHITE;
    private static final Color NEGRO       = Color.BLACK;
    private static final Color ROJO_ERROR  = new Color(180, 30, 30);

    public VentanaMisReservas() {
        setTitle("Mirrors Airlines - Mis Reservas");
        setSize(780, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        iniciarComponentes();
        cargarReservas(); // cargamos las reservas del usuario logueado
        setVisible(true);
    }

    // Carga las reservas del usuario logueado en la tabla
    private void cargarReservas() {
        limpiarTabla();
        ReservaDAO reservaDAO = new ReservaDAO();
        VueloDAO vueloDAO = new VueloDAO();
        PasajeroDAO pasajeroDAO = new PasajeroDAO();
        ArrayList<Reserva> reservas = reservaDAO.obtenerPorUsuario(SesionActual.usuario.getId());

        for (Reserva r : reservas) {
            Vuelo vuelo = vueloDAO.obtenerPorId(r.getIdVuelo());
            String nombreVuelo = vuelo != null
                    ? vuelo.getOrigen() + " → " + vuelo.getDestino()
                    : "Vuelo #" + r.getIdVuelo();

            // Obtenemos la cantidad real de pasajeros desde la BD
            int cantPasajeros = pasajeroDAO.obtenerPorReserva(r.getId()).size();

            agregarReserva(r.getId(), nombreVuelo, r.getFechaReserva(),
                    cantPasajeros, r.getTotal(), r.getEstado());
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
        panelCentral.setBounds(30, 70, 715, 480);
        panelCentral.setBackground(GRIS_PANEL);
        panelCentral.setBorder(BorderFactory.createLineBorder(AZUL_OSCURO, 1));

        JLabel labelTitulo = new JLabel("MIS RESERVAS");
        labelTitulo.setBounds(300, 15, 200, 20);
        labelTitulo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        labelTitulo.setForeground(NEGRO);

        // --- Tabla de reservas ---
        JLabel labelTitTabla = new JLabel("LISTADO DE RESERVAS");
        labelTitTabla.setBounds(20, 45, 200, 15);
        labelTitTabla.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitTabla.setForeground(NEGRO);

        String[] columnas = {"ID", "VUELO", "FECHA RESERVA", "PASAJEROS", "TOTAL", "ESTADO"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaReservas = new JTable(modeloTabla);
        tablaReservas.setDefaultEditor(Object.class, null);
        tablaReservas.setFont(new Font("SansSerif", Font.PLAIN, 10));
        tablaReservas.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 10));
        tablaReservas.getTableHeader().setBackground(AZUL_OSCURO);
        tablaReservas.getTableHeader().setForeground(BLANCO);

        JScrollPane scrollTabla = new JScrollPane(tablaReservas);
        scrollTabla.setBounds(20, 63, 670, 160);

        // --- Detalle reserva seleccionada ---
        JLabel labelTitDetalle = new JLabel("DETALLE DE LA RESERVA SELECCIONADA");
        labelTitDetalle.setBounds(20, 238, 310, 15);
        labelTitDetalle.setFont(new Font("SansSerif", Font.BOLD, 10));
        labelTitDetalle.setForeground(NEGRO);

        labelDetalleVuelo = new JLabel("Vuelo: -");
        labelDetalleVuelo.setBounds(20, 258, 330, 15);
        labelDetalleVuelo.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelDetalleVuelo.setForeground(NEGRO);

        labelDetalleFecha = new JLabel("Fecha de salida: -");
        labelDetalleFecha.setBounds(20, 276, 330, 15);
        labelDetalleFecha.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelDetalleFecha.setForeground(NEGRO);

        labelDetalleEstado = new JLabel("Estado: -");
        labelDetalleEstado.setBounds(20, 294, 330, 15);
        labelDetalleEstado.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelDetalleEstado.setForeground(NEGRO);

        labelDetallePasajeros = new JLabel("Pasajeros: -");
        labelDetallePasajeros.setBounds(370, 258, 310, 15);
        labelDetallePasajeros.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelDetallePasajeros.setForeground(NEGRO);

        labelDetallePago = new JLabel("Método de pago: -");
        labelDetallePago.setBounds(370, 276, 310, 15);
        labelDetallePago.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelDetallePago.setForeground(NEGRO);

        // --- Botones de acción ---
        btnCancelar = new JButton("CANCELAR RESERVA");
        btnCancelar.setBounds(20, 380, 155, 28);
        btnCancelar.setBackground(AZUL_OSCURO);
        btnCancelar.setForeground(BLANCO);
        btnCancelar.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnModificar = new JButton("MODIFICAR RESERVA");
        btnModificar.setBounds(185, 380, 158, 28);
        btnModificar.setBackground(AZUL_OSCURO);
        btnModificar.setForeground(BLANCO);
        btnModificar.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnModificar.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnModificar.setFocusPainted(false);
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnCheckin = new JButton("HACER CHECK-IN");
        btnCheckin.setBounds(353, 380, 140, 28);
        btnCheckin.setBackground(AZUL_OSCURO);
        btnCheckin.setForeground(BLANCO);
        btnCheckin.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnCheckin.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnCheckin.setFocusPainted(false);
        btnCheckin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnVerPoliticas = new JButton("VER POLÍTICAS");
        btnVerPoliticas.setBounds(503, 380, 120, 28);
        btnVerPoliticas.setBackground(AZUL_OSCURO);
        btnVerPoliticas.setForeground(BLANCO);
        btnVerPoliticas.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnVerPoliticas.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnVerPoliticas.setFocusPainted(false);
        btnVerPoliticas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(633, 380, 80, 28);
        btnVolver.setBackground(AZUL_OSCURO);
        btnVolver.setForeground(BLANCO);
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 10));
        btnVolver.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        labelMensaje = new JLabel("");
        labelMensaje.setBounds(20, 420, 600, 18);
        labelMensaje.setFont(new Font("SansSerif", Font.PLAIN, 10));
        labelMensaje.setForeground(ROJO_ERROR);

        panelCentral.add(labelTitulo);
        panelCentral.add(labelTitTabla);
        panelCentral.add(scrollTabla);
        panelCentral.add(labelTitDetalle);
        panelCentral.add(labelDetalleVuelo);
        panelCentral.add(labelDetalleFecha);
        panelCentral.add(labelDetalleEstado);
        panelCentral.add(labelDetallePasajeros);
        panelCentral.add(labelDetallePago);
        panelCentral.add(btnCancelar);
        panelCentral.add(btnModificar);
        panelCentral.add(btnCheckin);
        panelCentral.add(btnVerPoliticas);
        panelCentral.add(btnVolver);
        panelCentral.add(labelMensaje);

        // ============================================================
        // EVENTOS
        // ============================================================

        // Selección de fila en la tabla — carga el detalle abajo
        tablaReservas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int fila = tablaReservas.getSelectedRow();
                    if (fila != -1) {
                        int idReserva = (int) modeloTabla.getValueAt(fila, 0);
                        String vuelo  = (String) modeloTabla.getValueAt(fila, 1);
                        String estado = (String) modeloTabla.getValueAt(fila, 5);

                        // Obtenemos el método de pago desde la BD
                        PagoDAO pagoDAO = new PagoDAO();
                        Pago pago = pagoDAO.obtenerPorReserva(idReserva);
                        String metodoPago = pago != null ? pago.getMetodoPago() : "-";

                        // Obtenemos la fecha de salida del vuelo
                        VueloDAO vueloDAO = new VueloDAO();
                        ReservaDAO reservaDAO = new ReservaDAO();
                        ArrayList<Reserva> reservas = reservaDAO.obtenerPorUsuario(SesionActual.usuario.getId());
                        String fechaSalida = "-";
                        String pasajeros = modeloTabla.getValueAt(fila, 3).toString();
                        for (Reserva r : reservas) {
                            if (r.getId() == idReserva) {
                                Vuelo v = vueloDAO.obtenerPorId(r.getIdVuelo());
                                if (v != null) fechaSalida = v.getFechaSalida();
                                break;
                            }
                        }

                        cargarDetalle(vuelo, fechaSalida, estado, pasajeros + " pasajero/s", metodoPago);
                    }
                }
            }
        });

        // Botón CANCELAR RESERVA
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tablaReservas.getSelectedRow();
                if (fila == -1) {
                    labelMensaje.setText("Seleccioná una reserva de la tabla primero.");
                    return;
                }

                // Verificamos que la reserva no esté ya cancelada
                String estado = (String) modeloTabla.getValueAt(fila, 5);
                if (estado.equals("CANCELADA")) {
                    labelMensaje.setText("Esta reserva ya está cancelada.");
                    return;
                }

                // Verificamos el límite de 48 horas antes del vuelo
                int idReservaCanc = (int) modeloTabla.getValueAt(fila, 0);
                ReservaDAO reservaDAOCanc = new ReservaDAO();
                VueloDAO vueloDAOCanc = new VueloDAO();
                ArrayList<Reserva> reservasCanc = reservaDAOCanc.obtenerPorUsuario(SesionActual.usuario.getId());
                for (Reserva r : reservasCanc) {
                    if (r.getId() == idReservaCanc) {
                        Vuelo vuelo = vueloDAOCanc.obtenerPorId(r.getIdVuelo());
                        if (vuelo != null) {
                            try {
                                java.time.LocalDateTime fechaSalida = java.time.LocalDateTime.parse(
                                        vuelo.getFechaSalida().replace(" ", "T")
                                );
                                java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
                                long horasRestantes = java.time.Duration.between(ahora, fechaSalida).toHours();
                                if (horasRestantes < modelo.Politicas.HORAS_LIMITE) {
                                    labelMensaje.setText("No podés cancelar con menos de 48 horas antes del vuelo.");
                                    return;
                                }
                            } catch (Exception ex) {
                                System.out.println("Error al parsear fecha: " + ex.getMessage());
                            }
                        }
                        break;
                    }
                }

                // Calculamos el reembolso (70% del total)
                String totalTexto = ((String) modeloTabla.getValueAt(fila, 4)).replace("$", "").trim();
                double total = Double.parseDouble(totalTexto);
                double reembolso = total * 0.70;

                // Mostramos JOptionPane de confirmación con el monto a reembolsar
                int confirmacion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Confirmás la cancelación de la reserva?\n" +
                                "Se te reembolsará el 70% del total abonado: $" + reembolso + "\n" +
                                "El cargo por cancelación es del 30%.",
                        "Confirmar cancelación",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmacion == JOptionPane.YES_OPTION) {
                    ReservaDAO reservaDAO = new ReservaDAO();
                    reservaDAO.cancelar(idReservaCanc);
                    modeloTabla.setValueAt("CANCELADA", fila, 5);
                    limpiarDetalle();
                    labelMensaje.setText("Reserva cancelada. Reembolso de $" + reembolso + " en camino.");
                }
            }
        });

        // Botón MODIFICAR RESERVA
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tablaReservas.getSelectedRow();
                if (fila == -1) {
                    labelMensaje.setText("Seleccioná una reserva de la tabla primero.");
                    return;
                }
                String estado = (String) modeloTabla.getValueAt(fila, 5);
                if (estado.equals("CANCELADA")) {
                    labelMensaje.setText("No podés modificar una reserva cancelada.");
                    return;
                }
                int idReservaM = (int) modeloTabla.getValueAt(fila, 0);
                ReservaDAO reservaDAO2 = new ReservaDAO();
                VueloDAO vueloDAO2 = new VueloDAO();
                ArrayList<Reserva> reservas2 = reservaDAO2.obtenerPorUsuario(SesionActual.usuario.getId());
                int idVueloM = -1;
                for (Reserva r : reservas2) {
                    if (r.getId() == idReservaM) {
                        idVueloM = r.getIdVuelo();
                        Vuelo vuelo = vueloDAO2.obtenerPorId(idVueloM);
                        if (vuelo != null) {
                            try {
                                java.time.LocalDateTime fechaSalida = java.time.LocalDateTime.parse(
                                        vuelo.getFechaSalida().replace(" ", "T")
                                );
                                java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
                                long horasRestantes = java.time.Duration.between(ahora, fechaSalida).toHours();
                                if (horasRestantes < modelo.Politicas.HORAS_LIMITE) {
                                    labelMensaje.setText("No podés modificar con menos de 48 horas antes del vuelo.");
                                    return;
                                }
                            } catch (Exception ex) {
                                System.out.println("Error al parsear fecha: " + ex.getMessage());
                            }
                        }
                        break;
                    }
                }
                VentanaModificacion ventana = new VentanaModificacion();
                ventana.setReserva(idReservaM, idVueloM);
                dispose();
            }
        });

        // Botón HACER CHECK-IN
        btnCheckin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tablaReservas.getSelectedRow();
                if (fila == -1) {
                    labelMensaje.setText("Seleccioná una reserva de la tabla primero.");
                    return;
                }
                String estado = (String) modeloTabla.getValueAt(fila, 5);
                if (estado.equals("CANCELADA")) {
                    labelMensaje.setText("No podés hacer check-in de una reserva cancelada.");
                    return;
                }
                // Obtenemos el id del vuelo desde la BD
                int idReserva = (int) modeloTabla.getValueAt(fila, 0);
                ReservaDAO reservaDAO = new ReservaDAO();
                ArrayList<Reserva> reservas = reservaDAO.obtenerPorUsuario(SesionActual.usuario.getId());
                int idVuelo = -1;
                for (Reserva r : reservas) {
                    if (r.getId() == idReserva) {
                        idVuelo = r.getIdVuelo();
                        break;
                    }
                }
                VentanaCheckin ventanaCheckin = new VentanaCheckin();
                ventanaCheckin.setReserva(idReserva, idVuelo);
                dispose();
            }
        });

        // Botón VER POLÍTICAS
        btnVerPoliticas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaPoliticas();
                // No hacemos dispose() — al cerrar políticas volvemos a esta ventana
            }
        });

        // Botón VOLVER — vuelve a búsqueda SIN cerrar sesión
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaBusqueda();
                dispose();
            }
        });

        panel.add(barraTop);
        panel.add(panelCentral);

        add(panel);
    }

    public void agregarReserva(int id, String vuelo, String fechaReserva,
                               int cantPasajeros, double total, String estado) {
        Object[] fila = {id, vuelo, fechaReserva, cantPasajeros, "$" + total, estado};
        modeloTabla.addRow(fila);
    }

    public void cargarDetalle(String vuelo, String fechaSalida, String estado,
                              String pasajeros, String metodoPago) {
        labelDetalleVuelo.setText("Vuelo: " + vuelo);
        labelDetalleFecha.setText("Fecha de salida: " + fechaSalida);
        labelDetalleEstado.setText("Estado: " + estado);
        labelDetallePasajeros.setText("Pasajeros: " + pasajeros);
        labelDetallePago.setText("Método de pago: " + metodoPago);
    }

    public void limpiarDetalle() {
        labelDetalleVuelo.setText("Vuelo: -");
        labelDetalleFecha.setText("Fecha de salida: -");
        labelDetalleEstado.setText("Estado: -");
        labelDetallePasajeros.setText("Pasajeros: -");
        labelDetallePago.setText("Método de pago: -");
    }

    public void setMensaje(String mensaje) { labelMensaje.setText(mensaje); }
    public void limpiarTabla() { modeloTabla.setRowCount(0); }
}