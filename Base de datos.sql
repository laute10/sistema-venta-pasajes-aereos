-- ============================================================
--  SISTEMA DE VENTA DE PASAJES DE AVIÓN
--  Programación III - Universidad Champagnat
-- ============================================================
--  Instrucciones:
--  1. Abrí MySQL Workbench (o tu cliente favorito)
--  2. Ejecutá este script completo
--  3. Listo, la base queda creada con datos de prueba
-- ============================================================


-- Creamos la base de datos y la seleccionamos
CREATE DATABASE IF NOT EXISTS venta_pasajes;
USE venta_pasajes;


-- ============================================================
-- TABLA: usuarios
-- Guarda los datos de cada persona registrada en el sistema
-- ============================================================
CREATE TABLE IF NOT EXISTS usuarios (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(100) NOT NULL,
    apellido      VARCHAR(100) NOT NULL,
    email         VARCHAR(150) NOT NULL UNIQUE,  -- no puede haber dos iguales
    contrasena    VARCHAR(100) NOT NULL,
    telefono      VARCHAR(20),
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);


-- ============================================================
-- TABLA: vuelos
-- Guarda la información de cada vuelo disponible
-- ============================================================
CREATE TABLE IF NOT EXISTS vuelos (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    aerolinea     VARCHAR(100) NOT NULL,
    origen        VARCHAR(100) NOT NULL,
    destino       VARCHAR(100) NOT NULL,
    fecha_salida  DATETIME    NOT NULL,
    fecha_llegada DATETIME    NOT NULL,
    precio        DECIMAL(10, 2) NOT NULL,        -- ej: 45000.00
    asientos_disponibles INT NOT NULL,
    estado        VARCHAR(20) DEFAULT 'A TIEMPO'  -- A TIEMPO / RETRASADO / CANCELADO
);


-- ============================================================
-- TABLA: reservas
-- Cada reserva vincula un usuario con un vuelo
-- ============================================================
CREATE TABLE IF NOT EXISTS reservas (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario    INT NOT NULL,
    id_vuelo      INT NOT NULL,
    fecha_reserva DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado        VARCHAR(20) DEFAULT 'PENDIENTE', -- PENDIENTE / CONFIRMADA / CANCELADA
    total         DECIMAL(10, 2) NOT NULL,

    -- Relacionamos con las otras tablas
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_vuelo)   REFERENCES vuelos(id)
);


-- ============================================================
-- TABLA: pasajeros
-- Cada pasajero está asociado a una reserva
-- Un mismo pasajero puede aparecer en varias reservas
-- ============================================================
CREATE TABLE IF NOT EXISTS pasajeros (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    id_reserva     INT NOT NULL,
    nombre         VARCHAR(100) NOT NULL,
    apellido       VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    nro_pasaporte  VARCHAR(50) NOT NULL,

    FOREIGN KEY (id_reserva) REFERENCES reservas(id)
);


-- ============================================================
-- TABLA: pagos
-- Registra el pago asociado a cada reserva
-- ============================================================
CREATE TABLE IF NOT EXISTS pagos (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    id_reserva      INT NOT NULL UNIQUE,           -- una reserva = un pago
    metodo_pago     VARCHAR(50) NOT NULL,           -- TARJETA / TRANSFERENCIA
    monto           DECIMAL(10, 2) NOT NULL,
    fecha_pago      DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado          VARCHAR(20) DEFAULT 'APROBADO', -- APROBADO / RECHAZADO

    FOREIGN KEY (id_reserva) REFERENCES reservas(id)
);


-- ============================================================
-- DATOS DE PRUEBA
-- Así podés testear la app sin cargar datos a mano
-- ============================================================

-- Usuarios de prueba
INSERT INTO usuarios (nombre, apellido, email, contrasena, telefono) VALUES
('Juan',   'Perez',    'juan@mail.com',  '1234', '2614000001'),
('Maria',  'Gomez',    'maria@mail.com', '1234', '2614000002'),
('Carlos', 'Lopez',    'carlos@mail.com','1234', '2614000003');

-- Vuelos de prueba
INSERT INTO vuelos (aerolinea, origen, destino, fecha_salida, fecha_llegada, precio, asientos_disponibles, estado) VALUES
('Aerolíneas Argentinas', 'Mendoza',       'Buenos Aires', '2026-08-01 07:00:00', '2026-08-01 08:30:00', 45000.00, 120, 'A TIEMPO'),
('Aerolíneas Argentinas', 'Buenos Aires',  'Mendoza',      '2026-08-05 18:00:00', '2026-08-05 19:30:00', 42000.00, 100, 'A TIEMPO'),
('LATAM',                 'Mendoza',       'Córdoba',      '2026-08-10 09:00:00', '2026-08-10 10:00:00', 30000.00,  80, 'A TIEMPO'),
('LATAM',                 'Córdoba',       'Mendoza',      '2026-08-15 16:00:00', '2026-08-15 17:00:00', 28000.00,  75, 'RETRASADO'),
('Flybondi',              'Mendoza',       'Bariloche',    '2026-08-20 11:00:00', '2026-08-20 13:00:00', 55000.00,  60, 'A TIEMPO');

