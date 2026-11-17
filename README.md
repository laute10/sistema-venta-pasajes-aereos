# Sistema de Venta de Pasajes Aéreos

Aplicación de escritorio desarrollada en Java Swing y MySQL para la gestión de usuarios, vuelos, reservas y compra de pasajes.

![Búsqueda de vuelos](capturas/Captura%20de%20pantalla%20(347).png)

## Tecnologías utilizadas

- Java
- Java Swing
- MySQL
- JDBC
- Jakarta Mail (envío de correos con Gmail)
- Programación Orientada a Objetos (POO)
- Git y GitHub

## Funcionalidades

- Registro de usuarios
- Inicio de sesión
- Recuperación de contraseña
- Búsqueda de vuelos
- Gestión de pasajeros
- Reserva de pasajes
- Compra de pasajes
- Simulación de pagos
- Gestión de reservas
- Modificación y cancelación de reservas
- Check-in online
- Correos automáticos de confirmación de registro y de compra

## Estructura del proyecto

```text
src/
├── dao/         → acceso a la base de datos (un DAO por tabla) y conexión
├── modelo/      → clases del dominio (Usuario, Vuelo, Reserva, Pasajero, Pago…)
├── servicios/   → envío de correos y lectura de la configuración
├── vistas/      → ventanas de Java Swing
└── Main.java    → punto de entrada
```

## Cómo ejecutarlo

### Requisitos

- Java 17 o superior
- MySQL 8
- Un IDE de Java (el proyecto se desarrolló con IntelliJ IDEA)

### 1. Crear la base de datos

Importá el archivo `Base de datos.sql` en MySQL (por ejemplo, desde MySQL Workbench con *File → Run SQL Script*). El script crea la base `venta_pasajes`, sus tablas y datos de ejemplo.

### 2. Agregar las librerías

Las librerías necesarias están en la carpeta `libs/`:

- `mysql-connector-j` → conexión con MySQL
- `jakarta.mail` y `jakarta.activation` → envío de correos

En IntelliJ IDEA: *File → Project Structure → Libraries → +  → Java* y seleccioná los tres archivos `.jar` de `libs/`.

### 3. Crear el archivo de configuración

Las contraseñas **no están en el código**: se leen de un archivo `config.properties` que no se sube a GitHub.

1. Copiá el archivo `config.example.properties` (está en la raíz del proyecto).
2. Renombrá la copia a `config.properties`.
3. Completá la contraseña de tu usuario de MySQL:

```properties
db.url=jdbc:mysql://localhost:3306/venta_pasajes
db.usuario=root
db.password=tu_contraseña_de_mysql
```

`config.properties` tiene que quedar en la raíz del proyecto (al lado de `README.md`), que es la carpeta desde donde IntelliJ ejecuta el programa.

### 4. Ejecutar

Ejecutá la clase `Main`. Se abre la ventana de inicio de sesión.

## Correos con Gmail (opcional)

La aplicación envía correos de confirmación cuando un usuario se registra y cuando compra un pasaje. **Si no configurás esta parte, la aplicación funciona igual**: simplemente no envía correos y lo avisa en la consola.

Para activarlos necesitás una **contraseña de aplicación** de Gmail. No es la contraseña con la que entrás a tu correo: es una clave especial que Google genera para que un programa pueda enviar correos en tu nombre.

1. Activá la **verificación en dos pasos** en tu cuenta de Google (es obligatoria para crear contraseñas de aplicación).
2. Entrá a tu cuenta de Google → **Seguridad** → **Contraseñas de aplicaciones** (si no aparece, buscá "contraseñas de aplicaciones" en la barra de búsqueda de la cuenta).
3. Creá una nueva con cualquier nombre (por ejemplo, "Venta de Pasajes") y copiá las 16 letras que te muestra.
4. Completá estas dos líneas en tu `config.properties`:

```properties
email.origen=tu_correo@gmail.com
email.contrasena=abcd efgh ijkl mnop
```

> ⚠️ Nunca escribas la contraseña directamente en el código ni subas `config.properties` a GitHub: cualquiera que la vea podría enviar correos desde tu cuenta. El archivo ya está incluido en `.gitignore`.

## Capturas

| Iniciar sesión | Crear cuenta |
|---|---|
| ![Iniciar sesión](capturas/Captura%20de%20pantalla%20(345).png) | ![Crear cuenta](capturas/Captura%20de%20pantalla%20(346).png) |

| Buscar vuelos | Reserva de pasaje |
|---|---|
| ![Buscar vuelos](capturas/Captura%20de%20pantalla%20(347).png) | ![Reserva de pasaje](capturas/Captura%20de%20pantalla%20(348).png) |

| Check-in online | Tarjeta de embarque |
|---|---|
| ![Check-in online](capturas/Captura%20de%20pantalla%20(349).png) | ![Tarjeta de embarque](capturas/Captura%20de%20pantalla%20(350).png) |
