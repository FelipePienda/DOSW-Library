# DOSW-Library

## Descripción del Proyecto

Aplicación backend desarrollada con Spring Boot para la gestión de una biblioteca académica. Incluye gestión de libros, préstamos, devoluciones y seguridad con JWT.

## Tecnologías usadas

- Java 21
- Spring Boot 3.4.3
- Spring Data JPA
- Spring Security con JWT
- H2 (base de datos en memoria para desarrollo local)
- Maven
- JUnit 5
- JaCoCo

## Características principales

- Autenticación y autorización con JWT
- Control de acceso por roles (`LIBRARIAN` y `USER`)
- Gestión de inventario de libros
- Registro de préstamos y devoluciones
- API REST simple y extensible

## Ejecución local

### Requisitos

- Java JDK 17 o superior
- Maven 3.6+

### Pasos

```bash
git clone https://github.com/FelipePienda/DOSW-Library.git
cd DOSW-Library
mvn clean install
mvn spring-boot:run
```

La aplicación se ejecuta en `http://localhost:8081`.

### H2 Console

Accede a la consola H2 en:

`http://localhost:8081/h2-console`

Configuración de conexión:

- JDBC URL: `jdbc:h2:mem:testdb`
- Usuario: `sa`
- Contraseña: (vacío)

## Uso de la API

### Login

`POST http://localhost:8081/api/v1/auth/login`

Body JSON:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

### Token

Usa el token JWT devuelto en el encabezado `Authorization` para las demás peticiones:

`Authorization: Bearer <token>`

### Endpoints relevantes

| Método | Endpoint | Descripción | Acceso |
|---|---|---|---|
| `POST` | `/api/v1/auth/login` | Autenticación y JWT | Público |
| `GET` | `/api/v1/books/{id}/stock` | Consultar stock de libro | Autenticado |
| `POST` | `/api/v1/books` | Crear libro | `LIBRARIAN` |
| `POST` | `/api/v1/books/{id}/loan/{userId}` | Registrar préstamo | Autenticado |
| `POST` | `/api/v1/books/{id}/return/{userId}` | Registrar devolución | Autenticado |
| `GET` | `/api/v1/users` | Listar usuarios | Autenticado |
| `GET` | `/api/v1/users/{id}` | Consultar usuario por ID | Autenticado |

## Usuario de prueba

| Campo | Valor |
|---|---|
| Usuario | `admin` |
| Contraseña | `admin123` |
| Rol | `LIBRARIAN` |

## Estructura del proyecto

```
src/main/java/edu/eci/tdd/
├── config/       # Configuración e inicializadores
├── controller/   # Controladores REST
├── service/      # Lógica de negocio
├── persistence/  # Entidades y repositorios JPA
├── security/     # Seguridad JWT y configuración
└── model/        # Modelos de datos
```

## Pruebas

Ejecuta las pruebas con:

```bash
mvn test
```

## Notas

- El proyecto está configurado actualmente para usar H2 en memoria para facilitar pruebas locales.
- Para migrar a PostgreSQL, actualiza `application.properties` y asegúrate de tener el driver `org.postgresql:postgresql` en `pom.xml`.
- El puerto configurado es `8081`.
