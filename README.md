# KinalApp — Sistema de Gestión de Ventas

**KinalApp** es una aplicación web full-stack desarrollada en Java con Spring Boot. Permite gestionar clientes, productos, ventas y su detalle de manera segura, con una interfaz visual propia basada en Material Design y una API REST disponible para integraciones externas.

---

## Tabla de Contenidos

1. [¿Qué hace esta aplicación?](#qué-hace-esta-aplicación)
2. [Tecnologías Utilizadas](#tecnologías-utilizadas)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Requisitos Previos](#requisitos-previos)
5. [Configuración de la Base de Datos](#configuración-de-la-base-de-datos)
6. [Instalación paso a paso](#instalación-paso-a-paso)
7. [Ejecutar la Aplicación](#ejecutar-la-aplicación)
8. [Interfaz Web (Frontend)](#interfaz-web-frontend)
9. [API REST — Endpoints](#api-rest--endpoints)
10. [Seguridad y Autenticación](#seguridad-y-autenticación)
11. [Modelo de Base de Datos](#modelo-de-base-de-datos)
12. [Autor](#autor)
13. [Capturas de Pantalla](#capturas-de-pantalla)

---

## ¿Qué hace esta aplicación?

KinalApp es un **sistema de gestión de ventas** con dos capas de acceso:

**Interfaz web (para usuarios finales):** Un panel con diseño Material Design oscuro, accesible desde el navegador en `http://localhost:8000`, que permite iniciar sesión, ver estadísticas en un dashboard, y gestionar clientes, productos, ventas, detalle de ventas y usuarios del sistema.

**API REST (para integraciones y pruebas con Postman):** Los mismos datos son accesibles mediante peticiones HTTP estándar con autenticación básica, sin necesidad de usar la interfaz web.

---

## Tecnologías Utilizadas

| Tecnología | Versión | Para qué sirve |
|---|---|---|
| **Java** | 21 | Lenguaje de programación principal |
| **Spring Boot** | 4.0.2 | Marco de trabajo principal |
| **Spring MVC + Thymeleaf** | — | Motor de plantillas para el frontend web |
| **Spring Security** | — | Autenticación y control de acceso |
| **JWT (jjwt)** | 0.11.5 | Tokens de autenticación para la API |
| **Spring Data JPA / Hibernate** | — | Comunicación con la base de datos |
| **MySQL** | 8+ | Base de datos relacional |
| **Maven** | — | Gestión de dependencias |
| **Bootstrap 5** | 5.3.3 | Grid y utilidades base del frontend |
| **Bootstrap Icons** | 1.11.3 | Iconografía de la interfaz |
| **DM Sans / DM Mono** | — | Tipografía del sistema de diseño |

---

## Estructura del Proyecto

```
KinalApp/
├── src/
│   └── main/
│       ├── java/com/joseescobar/kinalapp/
│       │   ├── KinalAppApplication.java              ← Punto de entrada
│       │   ├── SecurityConfig.java                   ← Configuración de seguridad
│       │   │
│       │   ├── controller/                           ← Controladores REST (devuelven JSON)
│       │   │   ├── ClienteController.java
│       │   │   ├── ProductoController.java
│       │   │   ├── VentaController.java
│       │   │   ├── DetalleVentaController.java
│       │   │   └── UsuarioController.java
│       │   │
│       │   ├── controller/  (vistas Thymeleaf)       ← Controladores del frontend web
│       │   │   ├── DashboardController.java
│       │   │   ├── ClienteViewController.java
│       │   │   ├── ProductoViewController.java
│       │   │   ├── VentaViewController.java
│       │   │   ├── DetalleVentaViewController.java
│       │   │   ├── UsuarioViewController.java
│       │   │   └── RegistroController.java
│       │   │
│       │   ├── entity/                               ← Modelos de datos (tablas BD)
│       │   │   ├── Cliente.java
│       │   │   ├── Producto.java
│       │   │   ├── Venta.java
│       │   │   ├── DetalleVenta.java
│       │   │   └── Usuario.java
│       │   │
│       │   ├── repository/                           ← Acceso a la base de datos
│       │   │   ├── ClienteRepository.java
│       │   │   ├── ProductoRepository.java
│       │   │   ├── VentaRepository.java
│       │   │   ├── DetalleVentaRepository.java
│       │   │   └── UsuarioRepository.java
│       │   │
│       │   ├── service/                              ← Lógica de negocio
│       │   │   ├── IClienteService.java / ClienteService.java
│       │   │   ├── IProductoService.java / ProductoService.java
│       │   │   ├── IVentaService.java / VentaService.java
│       │   │   ├── IDetalleVentaService.java / DetalleVentaService.java
│       │   │   └── IUsuarioService.java / UsuarioService.java
│       │   │
│       │   └── security/                             ← Filtros JWT
│       │       ├── JwtService.java
│       │       └── JwtAuthenticationFilter.java
│       │
│       └── resources/
│           ├── application.properties                ← Configuración (BD, puerto, JWT)
│           │
│           ├── templates/                            ← Vistas HTML (Thymeleaf)
│           │   ├── login.html
│           │   ├── registro.html
│           │   ├── dashboard.html
│           │   ├── clientes/lista.html, formulario.html
│           │   ├── productos/lista.html, formulario.html
│           │   ├── ventas/lista.html, formulario.html
│           │   ├── detalles/lista.html, formulario.html
│           │   ├── usuarios/lista.html, formulario.html
│           │   └── fragments/navbar.html, sidebar.html
│           │
│           └── static/
│               ├── css/kinalapp.css                  ← Sistema de diseño Material Design
│               ├── js/kinalapp.js                    ← Sidebar, buscador, validaciones
│               └── images/
│                   ├── favicon.svg                   ← Favicon por defecto (reemplazable)
│                   └── INSTRUCCIONES.txt             ← Guía para logo y favicon propios
│
├── kinalappdb.mwb                                    ← Modelo visual de BD (MySQL Workbench)
└── pom.xml                                           ← Dependencias del proyecto
```

---

## Requisitos Previos

### 1. Java Development Kit (JDK) 21

1. Descarga desde: https://www.oracle.com/java/technologies/downloads/#java21
2. Instala siguiendo el asistente de tu sistema operativo
3. Verifica con:
   ```
   java -version
   ```
   Deberías ver: `java version "21.x.x"`

### 2. MySQL Community Server 8+

1. Descarga desde: https://dev.mysql.com/downloads/installer/
2. Selecciona **"Developer Default"** durante la instalación
3. Anota la contraseña del usuario `root`
4. Instala **MySQL Workbench** cuando el instalador lo ofrezca

### 3. Maven (opcional)

El proyecto incluye el script `mvnw` que funciona sin instalar Maven. Si aun así lo necesitas: https://maven.apache.org/download.cgi

### 4. IDE recomendado (opcional)

- **IntelliJ IDEA Community:** https://www.jetbrains.com/idea/download/
- **VS Code** con "Extension Pack for Java": https://code.visualstudio.com/

---

## Configuración de la Base de Datos

### Crear el usuario de MySQL

Abre **MySQL Workbench** o la consola de MySQL y ejecuta:

```sql
CREATE USER 'IN5AM'@'localhost' IDENTIFIED BY '_odmon5Am';
GRANT ALL PRIVILEGES ON dbClientes_in5am.* TO 'IN5AM'@'localhost';
FLUSH PRIVILEGES;
```

La base de datos `dbClientes_in5am` se crea automáticamente al iniciar la aplicación por primera vez.

### Archivo de configuración

`src/main/resources/application.properties`:

```properties
spring.application.name=KinalApp
server.port=8000

spring.datasource.url=jdbc:mysql://localhost:3306/dbClientes_in5am?createDatabaseIfNotExist=true
spring.datasource.username=IN5AM
spring.datasource.password=_odmon5Am

jwt.secret.key=NDM0NTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQ=
jwt.expiration.time=86400000

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jackson.deserialization.fail-on-null-for-primitives=false
```

Si tu MySQL usa credenciales distintas, actualiza `username` y `password`.

---

## Instalación paso a paso

### Paso 1: Obtener el proyecto

**Con Git:**
```bash
git clone https://github.com/jescobar-2025070/KinalApp-2025070.git
cd KinalApp-2025070
```

**Sin Git — descarga ZIP:**
1. Ve a https://github.com/jescobar-2025070/KinalApp-2025070
2. Clic en **"Code"** → **"Download ZIP"**
3. Extrae el archivo en la carpeta de tu preferencia

### Paso 2: Abrir en el IDE

**IntelliJ IDEA:** `File → Open` → selecciona la carpeta. IntelliJ detecta el proyecto Maven y descarga dependencias automáticamente.

**VS Code:** `Archivo → Abrir carpeta` → selecciona la carpeta. Requiere "Extension Pack for Java".

---

## Ejecutar la Aplicación

### Desde la terminal

Dentro de la carpeta raíz del proyecto:

**Windows:**
```cmd
mvnw.cmd spring-boot:run
```

**Mac / Linux:**
```bash
./mvnw spring-boot:run
```

Cuando veas este mensaje, la aplicación está lista:
```
Started KinalAppApplication in X.XXX seconds
```

Abre el navegador en: **http://localhost:8000**

### Desde IntelliJ IDEA

1. Abre `KinalAppApplication.java`
2. Haz clic en el botón ▶️ verde junto al método `main`

### Generar un ejecutable (.jar)

```bash
./mvnw clean package
java -jar target/kinalapp-0.0.1-SNAPSHOT.jar
```

---

## Interfaz Web (Frontend)

### Vistas disponibles

| Ruta | Vista | Acceso |
|---|---|---|
| `/login` | Formulario de inicio de sesión | Público |
| `/registro` | Formulario de creación de cuenta | Público |
| `/dashboard` | Panel con estadísticas generales | Autenticado |
| `/vista/clientes` | Lista y gestión de clientes | Autenticado |
| `/vista/productos` | Lista y gestión de productos | Autenticado |
| `/vista/ventas` | Lista y edición de ventas | Autenticado |
| `/vista/detalles` | Lista y edición de detalle de ventas | Autenticado |
| `/vista/usuarios` | Gestión de usuarios del sistema | Solo ADMIN |

### Funcionalidades destacadas

**Dashboard:** tarjetas con totales de clientes, productos, ventas y usuarios, más tabla con las últimas 5 ventas registradas.

**Módulos de gestión:** cada módulo incluye lista con buscador en tiempo real, formulario de creación y edición, y confirmación con modal antes de eliminar.

**Ventas y Detalle de Ventas:** la eliminación está desactivada en la interfaz web intencionalmente. Solo están disponibles crear, listar y editar. Si se requiere eliminar, puede hacerse a través de la API REST.

**Formulario de Detalle de Venta:** al seleccionar un producto el precio unitario se rellena automáticamente, y al ingresar la cantidad el subtotal se calcula en tiempo real (cantidad × precio). El botón guardar permanece deshabilitado hasta que el cálculo sea válido.

**Sidebar colapsable:** se oculta con el botón de menú. En móvil se comporta como panel deslizante. El ítem activo se resalta con el color de acento.

---

## API REST — Endpoints

URL base: `http://localhost:8000`

La API usa **autenticación HTTP básica** — envía usuario y contraseña en cada petición (en Postman: pestaña Authorization → Basic Auth).

### Usuarios (`/usuarios`)

| Método | URL | Descripción | Auth requerida |
|---|---|---|---|
| `POST` | `/usuarios` | Crear usuario | No |
| `GET` | `/usuarios` | Listar todos | Sí — ADMIN |
| `GET` | `/usuarios/{id}` | Buscar por código | Sí — ADMIN |
| `GET` | `/usuarios/estado/{estado}` | Filtrar por estado | Sí — ADMIN |
| `PUT` | `/usuarios/{id}` | Actualizar | Sí |
| `DELETE` | `/usuarios/{id}` | Eliminar | Sí — ADMIN |

**Body para crear usuario:**
```json
{
  "userName": "admin",
  "password": "mi_contrasena",
  "email": "admin@kinal.edu.gt",
  "rol": "ADMIN",
  "estado": 1
}
```

### Clientes (`/clientes`)

| Método | URL | Descripción | Auth requerida |
|---|---|---|---|
| `GET` | `/clientes` | Listar todos | Sí |
| `GET` | `/clientes/{dpi}` | Buscar por DPI | Sí |
| `GET` | `/clientes/estado/{estado}` | Filtrar por estado | Sí |
| `POST` | `/clientes` | Crear cliente | Sí |
| `PUT` | `/clientes/{dpi}` | Actualizar | Sí |
| `DELETE` | `/clientes/{dpi}` | Eliminar | Sí |

**Body para crear cliente:**
```json
{
  "DPICliente": 1234567890101,
  "nombreCliente": "Juan",
  "apellidoCliente": "Pérez",
  "direccion": "Zona 1, Guatemala",
  "estado": 1
}
```

### Productos (`/productos`)

| Método | URL | Descripción | Auth requerida |
|---|---|---|---|
| `GET` | `/productos` | Listar todos | Sí |
| `GET` | `/productos/{id}` | Buscar por código | Sí |
| `GET` | `/productos/estado/{estado}` | Filtrar por estado | Sí |
| `POST` | `/productos` | Crear producto | Sí |
| `PUT` | `/productos/{id}` | Actualizar | Sí |
| `DELETE` | `/productos/{id}` | Eliminar | Sí |

**Body para crear producto:**
```json
{
  "nombreProducto": "Cuaderno universitario",
  "precio": 15.50,
  "stock": 100,
  "estado": 1
}
```

### Ventas (`/ventas`)

| Método | URL | Descripción | Auth requerida |
|---|---|---|---|
| `GET` | `/ventas` | Listar todas | Sí |
| `GET` | `/ventas/{id}` | Buscar por código | Sí |
| `GET` | `/ventas/estado/{estado}` | Filtrar por estado | Sí |
| `POST` | `/ventas` | Registrar venta | Sí |
| `PUT` | `/ventas/{id}` | Actualizar | Sí |
| `DELETE` | `/ventas/{id}` | Eliminar | Sí |

### Detalle de Venta (`/detalles`)

| Método | URL | Descripción | Auth requerida |
|---|---|---|---|
| `GET` | `/detalles` | Listar todos | Sí |
| `GET` | `/detalles/{id}` | Buscar por código | Sí |
| `POST` | `/detalles` | Crear detalle | Sí |
| `PUT` | `/detalles/{id}` | Actualizar | Sí |
| `DELETE` | `/detalles/{id}` | Eliminar | Sí |

---

## Seguridad y Autenticación

### Dos mecanismos que conviven

**Interfaz web:** Spring Security gestiona el login con formulario en `/login`. Al autenticarse, Spring crea una sesión en el navegador que protege todas las vistas del panel.

**API REST:** Autenticación HTTP básica en cada petición. Los tokens JWT están configurados para integraciones externas.

### Roles

| Rol | Permisos |
|---|---|
| `ADMIN` | Acceso total — puede ver, crear, editar y eliminar en todos los módulos incluyendo usuarios |
| `USER` | Puede gestionar clientes, productos, ventas y detalles, pero no puede acceder al módulo de usuarios |

### Rutas públicas (sin autenticación)

- `GET /login` — Formulario de acceso
- `GET /registro` — Formulario de registro
- `POST /usuarios` — Crear cuenta vía API

### Rutas protegidas

Todo lo demás requiere sesión activa. Las rutas `/vista/usuarios/**` y los endpoints `GET /usuarios/**` y `DELETE /usuarios/**` requieren además el rol `ADMIN`.

---

## Modelo de Base de Datos

La base de datos se llama `dbClientes_in5am`:

```
┌──────────────┐       ┌──────────────┐       ┌──────────────┐
│   Usuarios   │       │   Clientes   │       │   Productos  │
│──────────────│       │──────────────│       │──────────────│
│ codigo_usr   │       │ dpi_cliente  │       │ codigo_prod  │
│ userName     │       │ nombre       │       │ nombre       │
│ password     │       │ apellido     │       │ precio       │
│ email        │       │ direccion    │       │ stock        │
│ rol          │       │ estado       │       │ estado       │
│ estado       │       └──────┬───────┘       └──────┬───────┘
└──────┬───────┘              │                      │
       │                      ▼                      │
       │               ┌──────────────┐              │
       └──────────────►│    Ventas    │◄─────────────┤
                        │──────────────│              │
                        │ codigo_venta │   ┌──────────▼─────┐
                        │ fecha_venta  │◄──│  DetalleVenta  │
                        │ total        │   │────────────────│
                        │ estado       │   │ codigo_detalle │
                        └──────────────┘   │ cantidad       │
                                           │ precio_unit    │
                                           │ sub_total      │
                                           └────────────────┘
```

El archivo `kinalappdb.mwb` incluido en el repositorio contiene el diagrama visual. Ábrelo con **MySQL Workbench** para verlo gráficamente.

---

## Autor

| Campo | Información |
|-------|-------------|
| **Nombre** | José Escobar |
| **Carné** | 2025070 |
| **GitHub** | [@jescobar-2025070](https://github.com/jescobar-2025070) |
| **Correo** | jescobar-2025070@kinal.edu.gt |

---

## Capturas de Pantalla

---

### Login

![Login](imagenes/login.png)

---

### Dashboard principal

![Dashboard](imagenes/dashboard.png)

---

### Registro

![Registro](imagenes/registro.png)

---

### Prueba de endpoints en Postman

---

#### GET

![Prueba GET Clientes](imagenes/listCliente.png)

---

![Prueba GET Usuarios](imagenes/listUsuarios.png)

---

![Prueba GET Ventas](imagenes/listVenta.png)

---

![Prueba GET DetalleVenta](imagenes/listDetalleVenta.png)

---

![Prueba GET Productos](imagenes/listProducto.png)

#### POST

![Prueba POST Clientes](imagenes/postCliente.png)

---

![Prueba POST Usuarios](imagenes/postUsuario.png)

---

![Prueba POST Ventas](imagenes/postVenta.png)

---

![Prueba POST DetalleVenta](imagenes/postDetalleVenta.png)

---

![Prueba POST Productos](imagenes/postProducto.png)

#### PUT

![Prueba PUT Clientes](imagenes/putCliente.png)

---

![Prueba PUT Usuarios](imagenes/putUsuario.png)

---

![Prueba PUT Ventas](imagenes/putVenta.png)

---

![Prueba PUT DetalleVenta](imagenes/putDetalleVenta.png)

---

![Prueba PUT Productos](imagenes/putProducto.png)

#### DELETE

![Prueba DELETE Clientes](imagenes/deleteCliente.png)

---

![Prueba DELETE Usuarios](imagenes/deleteUsuario.png)

---

![Prueba DELETE Ventas](imagenes/deleteVenta.png)

---

![Prueba DELETE DetalleVenta](imagenes/deleteDetalleVenta.png)

---

![Prueba DELETE Productos](imagenes/deleteProducto.png)
