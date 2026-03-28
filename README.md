# KinalApp — Sistema de Gestión de Ventas

**KinalApp** es una aplicación de servidor (API REST) desarrollada en Java con Spring Boot, diseñada para gestionar clientes, productos, ventas y usuarios de manera segura. Fue creada como proyecto académico en el Instituto Técnico Kinal.

---

## Tabla de Contenidos

1. [¿Qué hace esta aplicación?](#-qué-hace-esta-aplicación)
2. [Tecnologías Utilizadas](#-tecnologías-utilizadas)
3. [Estructura del Proyecto](#-estructura-del-proyecto)
4. [Requisitos Previos](#-requisitos-previos)
5. [Instalación paso a paso](#-instalación-paso-a-paso)
6. [Configuración de la Base de Datos](#-configuración-de-la-base-de-datos)
7. [Ejecutar la Aplicación](#-ejecutar-la-aplicación)
8. [Endpoints de la API](#-endpoints-de-la-api)
9. [Seguridad y Autenticación](#-seguridad-y-autenticación)
10. [Modelo de Base de Datos](#-modelo-de-base-de-datos)
11. [Capturas de Pantalla](#-capturas-de-pantalla)

---

## ¿Qué hace esta aplicación?

KinalApp es un **sistema de gestión de ventas** que funciona como un servicio web. Esto significa que no tiene pantallas propias, sino que expone una serie de servicios (llamados *endpoints*) a los que otras aplicaciones o herramientas de prueba (como Postman) pueden conectarse para:

- **Registrar y administrar clientes** (con su DPI, nombre, dirección y estado)
- **Gestionar un catálogo de productos** (nombre, precio, stock y estado)
- **Registrar ventas** (asociadas a un usuario y un cliente)
- **Registrar el detalle de cada venta** (qué productos se vendieron, en qué cantidad y a qué precio)
- **Administrar usuarios** del sistema con roles y contraseñas encriptadas
- **Proteger la información** mediante autenticación: solo los usuarios registrados pueden acceder a los datos

---

## Tecnologías Utilizadas

| Tecnología | Versión | ¿Para qué sirve? |
|---|---|---|
| **Java** | 21 | Lenguaje de programación principal |
| **Spring Boot** | 4.0.2 | Marco de trabajo que facilita crear aplicaciones Java |
| **Spring Security + JWT** | - | Seguridad: controla quién puede acceder a qué |
| **Spring Data JPA** | - | Comunicación con la base de datos sin escribir SQL manualmente |
| **MySQL** | 8+ | Base de datos donde se guardan todos los datos |
| **Maven** | - | Herramienta que descarga las librerías necesarias automáticamente |

---

## Estructura del Proyecto

```
KinalApp/
├── src/
│   └── main/
│       ├── java/com/joseescobar/kinalapp/
│       │   ├── controller/       ← Reciben las peticiones HTTP (GET, POST, PUT, DELETE)
│       │   │   ├── ClienteController.java
│       │   │   ├── ProductoController.java
│       │   │   ├── VentaController.java
│       │   │   ├── DetalleVentaController.java
│       │   │   └── UsuarioController.java
│       │   ├── entity/           ← Representan las tablas de la base de datos
│       │   │   ├── Cliente.java
│       │   │   ├── Producto.java
│       │   │   ├── Venta.java
│       │   │   ├── DetalleVenta.java
│       │   │   └── Usuario.java
│       │   ├── repository/       ← Se encargan de guardar y leer datos
│       │   ├── service/          ← Contienen la lógica de negocio
│       │   └── security/         ← Configuración de seguridad y JWT
│       └── resources/
│           └── application.properties  ← Configuración de la app (BD, puerto, etc.)
├── kinalappdb.mwb               ← Modelo de base de datos (abrir con MySQL Workbench)
└── pom.xml                      ← Lista de librerías que necesita el proyecto
```

---

## Requisitos Previos

Antes de poder ejecutar esta aplicación, necesitas instalar los siguientes programas en tu computadora. A continuación se explica qué es cada uno y cómo instalarlo:

### 1. Java Development Kit (JDK) 21

Java es el lenguaje en que está escrita la aplicación. Sin él, no puede funcionar.

**¿Cómo instalarlo?**
1. Ingresa a: https://www.oracle.com/java/technologies/downloads/#java21
2. Descarga la versión para tu sistema operativo (Windows, Mac o Linux)
3. Ejecuta el instalador y sigue los pasos (siguiente → siguiente → finalizar)
4. Para verificar que quedó bien instalado, abre una **ventana de comandos** (en Windows: busca "cmd" en el menú inicio) y escribe:
   ```
   java -version
   ```
   Deberías ver algo como: `java version "21.x.x"`

---

### 2. MySQL Community Server 8+

MySQL es el sistema de base de datos donde la aplicación guardará toda la información.

**¿Cómo instalarlo?**
1. Ingresa a: https://dev.mysql.com/downloads/installer/
2. Descarga **MySQL Installer for Windows** (o la versión de tu sistema operativo)
3. Durante la instalación, selecciona **"Developer Default"**
4. Cuando te pida una contraseña para el usuario `root`, guárdala bien — la necesitarás después
5. También instala **MySQL Workbench** si el instalador te lo ofrece (es una herramienta visual para ver la base de datos)

---

### 3. Maven (opcional si usas el wrapper incluido)

Maven descarga automáticamente todas las librerías que necesita el proyecto. El proyecto ya incluye un script llamado `mvnw` (Maven Wrapper) que **no requiere instalar Maven por separado** en la mayoría de casos.

Si aun así necesitas instalarlo manualmente:
1. Ingresa a: https://maven.apache.org/download.cgi
2. Descarga el archivo `.zip`
3. Extráelo y configura la variable de entorno `MAVEN_HOME`

---

### 4. IDE recomendado: IntelliJ IDEA o VS Code (opcional pero recomendado)

Un IDE es un editor de código avanzado que facilita abrir y ejecutar el proyecto.

- **IntelliJ IDEA Community (gratuito):** https://www.jetbrains.com/idea/download/
- **VS Code:** https://code.visualstudio.com/ (instalar extensión "Extension Pack for Java")

---

## Configuración de la Base de Datos

### Paso 1: Crear el usuario de MySQL

La aplicación está configurada para conectarse con un usuario específico de MySQL. Abre **MySQL Workbench** o la línea de comandos de MySQL y ejecuta:

```sql
-- Crear el usuario que usa la aplicación
CREATE USER 'IN5AM'@'localhost' IDENTIFIED BY '_odmon5Am';

-- Darle todos los permisos sobre la base de datos del proyecto
GRANT ALL PRIVILEGES ON dbClientes_in5am.* TO 'IN5AM'@'localhost';

-- Aplicar los cambios
FLUSH PRIVILEGES;
```

> **Nota:** La base de datos `dbClientes_in5am` se creará automáticamente cuando inicies la aplicación por primera vez. No necesitas crearla manualmente.

### Paso 2: (Opcional) Cargar el modelo de base de datos

El archivo `kinalappdb.mwb` contiene el diseño visual de la base de datos. Puedes abrirlo con **MySQL Workbench** para ver cómo están relacionadas las tablas.

---

## Instalación paso a paso

### Paso 1: Descargar el proyecto

**Opción A — Desde GitHub (requiere Git instalado):**
```bash
git clone https://github.com/jescobar-2025070/KinalApp-2025070.git
cd KinalApp-2025070
```

**Opción B — Descarga directa:**
1. Ve a https://github.com/jescobar-2025070/KinalApp-2025070
2. Haz clic en el botón verde **"Code"**
3. Selecciona **"Download ZIP"**
4. Extrae el archivo ZIP en una carpeta de tu elección

---

### Paso 2: Abrir el proyecto

Si usas **IntelliJ IDEA:**
1. Abre IntelliJ IDEA
2. Selecciona **"Open"** y navega hasta la carpeta del proyecto
3. IntelliJ detectará automáticamente que es un proyecto Maven y descargará las dependencias

Si usas **VS Code:**
1. Abre VS Code
2. Ve a **Archivo → Abrir carpeta** y selecciona la carpeta del proyecto
3. Asegúrate de tener instalado el "Extension Pack for Java"

---

### Paso 3: Revisar la configuración

Antes de ejecutar, verifica el archivo `src/main/resources/application.properties`. Este archivo contiene la configuración de conexión a la base de datos:

```properties
spring.application.name=KinalApp

# Puerto donde escucha la aplicación
server.port=8000

# Conexión a MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/dbClientes_in5am?createDatabaseIfNotExist=true
spring.datasource.username=IN5AM
spring.datasource.password=_odmon5Am

# Seguridad JWT
jwt.secret.key=NDM0NTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQzNTQ=
jwt.expiration.time=86400000

# Configuración JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> Si tu MySQL tiene un usuario o contraseña diferente, cambia los valores de `username` y `password` aquí.

---

## Ejecutar la Aplicación

### Opción A: Desde la terminal (línea de comandos)

1. Abre una terminal o cmd dentro de la carpeta del proyecto
2. Ejecuta el siguiente comando:

**En Windows:**
```cmd
mvnw.cmd spring-boot:run
```

**En Mac/Linux:**
```bash
./mvnw spring-boot:run
```

3. Espera a que aparezca un mensaje similar a:
   ```
   Started KinalAppApplication in 3.5 seconds
   ```
4. La aplicación estará corriendo en: **http://localhost:8000**

---

### Opción B: Desde IntelliJ IDEA

1. Abre el archivo `KinalAppApplication.java` (ubicado en `src/main/java/com/joseescobar/kinalapp/`)
2. Haz clic en el botón ▶️ verde que aparece junto al método `main`
3. Observa la consola en la parte inferior — cuando diga "Started KinalAppApplication", la app está lista

---

### Opción C: Generar un archivo ejecutable (.jar)

Si quieres empaquetar la aplicación para distribuirla:

```bash
./mvnw clean package
```

Esto generará un archivo `.jar` en la carpeta `target/`. Puedes ejecutarlo con:

```bash
java -jar target/kinalapp-0.0.1-SNAPSHOT.jar
```

---

## Endpoints de la API

Una vez que la aplicación esté corriendo, puedes interactuar con ella mediante una herramienta como **Postman** (https://www.postman.com/downloads/) o desde cualquier navegador web para las peticiones GET.

La URL base es: `http://localhost:8000`

---

### Usuarios (`/usuarios`)

| Método | URL | Descripción | Requiere autenticación |
|--------|-----|-------------|----------------------|
| `POST` | `/usuarios` | Registrar un nuevo usuario | No |
| `GET` | `/usuarios` | Listar todos los usuarios | Sí (solo ADMIN) |
| `GET` | `/usuarios/{id}` | Buscar usuario por código | Sí (solo ADMIN) |
| `GET` | `/usuarios/estado/{estado}` | Listar usuarios por estado (1=activo, 0=inactivo) | Sí (solo ADMIN) |
| `PUT` | `/usuarios/{id}` | Actualizar un usuario | Sí |
| `DELETE` | `/usuarios/{id}` | Eliminar un usuario | Sí (solo ADMIN) |

**Ejemplo para crear un usuario (body JSON):**
```json
{
  "userName": "admin",
  "password": "mi_contraseña",
  "email": "admin@kinal.edu.gt",
  "rol": "ADMIN",
  "estado": 1
}
```

---

### Clientes (`/clientes`)

| Método | URL | Descripción | Requiere autenticación |
|--------|-----|-------------|----------------------|
| `GET` | `/clientes` | Listar todos los clientes | Sí |
| `GET` | `/clientes/{dpi}` | Buscar cliente por DPI | Sí |
| `GET` | `/clientes/estado/{estado}` | Listar clientes por estado | Sí |
| `POST` | `/clientes` | Crear un nuevo cliente | Sí |
| `PUT` | `/clientes/{dpi}` | Actualizar datos de un cliente | Sí |
| `DELETE` | `/clientes/{dpi}` | Eliminar un cliente | Sí |

**Ejemplo para crear un cliente (body JSON):**
```json
{
  "DPICliente": 1234567890101,
  "nombreCliente": "Juan",
  "apellidoCliente": "Pérez",
  "direccion": "Zona 1, Guatemala",
  "estado": 1
}
```

---

### Productos (`/productos`)

| Método | URL | Descripción | Requiere autenticación |
|--------|-----|-------------|----------------------|
| `GET` | `/productos` | Listar todos los productos | Sí |
| `GET` | `/productos/{id}` | Buscar producto por código | Sí |
| `GET` | `/productos/estado/{estado}` | Listar productos por estado | Sí |
| `POST` | `/productos` | Crear un nuevo producto | Sí |
| `PUT` | `/productos/{id}` | Actualizar un producto | Sí |
| `DELETE` | `/productos/{id}` | Eliminar un producto | Sí |

**Ejemplo para crear un producto (body JSON):**
```json
{
  "nombreProducto": "Cuaderno",
  "precio": 15.50,
  "stock": 100,
  "estado": 1
}
```

---

### Ventas (`/ventas`)

| Método | URL | Descripción | Requiere autenticación |
|--------|-----|-------------|----------------------|
| `GET` | `/ventas` | Listar todas las ventas | Sí |
| `GET` | `/ventas/{id}` | Buscar venta por código | Sí |
| `GET` | `/ventas/estado/{estado}` | Listar ventas por estado | Sí |
| `POST` | `/ventas` | Registrar una nueva venta | Sí |
| `PUT` | `/ventas/{id}` | Actualizar una venta | Sí |
| `DELETE` | `/ventas/{id}` | Eliminar una venta | Sí |

---

### Detalle de Venta (`/detalles`)

| Método | URL | Descripción | Requiere autenticación |
|--------|-----|-------------|----------------------|
| `GET` | `/detalles` | Listar todos los detalles | Sí |
| `GET` | `/detalles/{id}` | Buscar detalle por código | Sí |
| `POST` | `/detalles` | Agregar un detalle de venta | Sí |
| `PUT` | `/detalles/{id}` | Actualizar un detalle | Sí |
| `DELETE` | `/detalles/{id}` | Eliminar un detalle | Sí |

---

## Seguridad y Autenticación

La aplicación usa **autenticación básica HTTP** y **JWT (JSON Web Token)** para proteger los datos.

### ¿Cómo funciona?

1. **Registro:** Cualquiera puede crear una cuenta enviando un `POST` a `/usuarios`
2. **Login:** Una vez registrado, puedes iniciar sesión en `/login` con tu usuario y contraseña
3. **Acceso protegido:** Para consultar clientes, productos, ventas, etc., debes enviar tus credenciales en cada petición

### Roles del sistema

| Rol | Permisos |
|-----|----------|
| `ADMIN` | Acceso total: puede ver, crear, editar y eliminar usuarios, clientes, productos y ventas |
| `USER` | Puede gestionar clientes, productos y ventas, pero NO puede administrar otros usuarios |

### Rutas públicas (sin necesidad de iniciar sesión)

- `POST /usuarios` → Crear cuenta
- `GET /login` → Iniciar sesión

### Rutas protegidas (requieren sesión iniciada)

- Todo lo relacionado con `/clientes/**`, `/productos/**`, `/ventas/**`, `/detalles/**`

---

## Modelo de Base de Datos

La base de datos se llama `dbClientes_in5am` y contiene las siguientes tablas:

```
┌─────────────┐       ┌─────────────┐       ┌──────────────┐
│  Usuarios   │       │   Clientes  │       │   Productos  │
│─────────────│       │─────────────│       │──────────────│
│ codigo_usr  │       │ dpi_cliente │       │ codigo_prod  │
│ userName    │       │ nombre      │       │ nombre       │
│ password    │       │ apellido    │       │ precio       │
│ email       │       │ direccion   │       │ stock        │
│ rol         │       │ estado      │       │ estado       │
│ estado      │       └──────┬──────┘       └──────┬───────┘
└──────┬──────┘              │                     │
       │                     ▼                     ▼
       │              ┌─────────────┐       ┌──────────────┐
       └─────────────►│    Ventas   │◄──────│ DetalleVenta │
                      │─────────────│       │──────────────│
                      │ codigo_vta  │       │ codigo_det   │
                      │ fecha_venta │       │ cantidad     │
                      │ total       │       │ precio_unit  │
                      │ estado      │       │ sub_total    │
                      └─────────────┘       └──────────────┘
```

El archivo `kinalappdb.mwb` incluido en el proyecto contiene el modelo visual completo. Ábrelo con **MySQL Workbench** para verlo gráficamente.

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
