# Price Service API

Este proyecto implementa un servicio REST reactivo para consultar precios de productos según fecha, usando Spring WebFlux y Clean Architecture.

## 🛠 Tecnologías Utilizadas

### Core
- Java 17
- Spring Boot 3.x
- Spring WebFlux
- R2DBC
- H2 Database
- Flyway

### Contenedorización
- Docker
- Alpine Linux
- Multi-stage building

### Testing
- JUnit 5
- WebTestClient
- Test Containers (opcional)

### Herramientas
- Maven
- Git
- Project Lombok
- Spring Actuator

- Java 17
- Spring Boot 3.x
- Spring WebFlux
- R2DBC
- H2 Database
- Flyway
- JUnit 5
- Project Lombok

## 🏗 Arquitectura

El proyecto sigue los principios de Clean Architecture y Domain-Driven Design (DDD):

```
com.commerce.prices/
├── domain/
│   ├── Price.java
│   └── exception/
├── application/
│   ├── port/
│   │   ├── in/
│   │   └── out/
│   └── service/
├── adapter/
│   ├── web/
│   └── persistence/
└── config/
```

### Capas
- **Domain**: Entidades y reglas de negocio
- **Application**: Casos de uso y puertos
- **Adapters**: Implementaciones de interfaces (Web, Persistencia)
- **Config**: Configuraciones de la aplicación

## 📋 Requisitos Previos

- Java 17 o superior
- Maven 3.6 o superior

## 🚀 Inicio Rápido

### Ejecución Local

1. Clonar el repositorio:
```bash
git clone https://github.com/danielbernalo/price-service.git
```

2. Navegar al directorio del proyecto:
```bash
cd price-service
```

3. Compilar el proyecto:
```bash
mvn clean install
```

4. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

### Ejecución con Docker

#### Pre-requisitos
- Docker instalado
- Docker Compose (opcional)

#### Construcción de la Imagen
```bash
# Construir la imagen
docker build -t prices-service:1.0.0 .
```

#### Ejecución del Contenedor
```bash
# Ejecutar el contenedor
docker run -d \
  --name prices-service \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  prices-service:1.0.0
```

#### Variables de Entorno Disponibles
| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| SPRING_PROFILES_ACTIVE | Perfil activo de Spring | prod |
| SPRING_R2DBC_URL | URL de conexión R2DBC | r2dbc:h2:mem:///testdb |
| SPRING_R2DBC_USERNAME | Usuario de base de datos | sa |
| SPRING_R2DBC_PASSWORD | Contraseña de base de datos | |
| JAVA_OPTS | Opciones de la JVM | -Xms512m -Xmx512m |

#### Comandos Docker Útiles
```bash
# Ver logs del contenedor
docker logs prices-service

# Verificar estado de salud
docker inspect prices-service | jq '.[0].State.Health'

```

#### Healthcheck
El contenedor incluye un healthcheck configurado que verifica el endpoint de salud cada 30 segundos:
- Intervalo: 30s
- Timeout: 3s
- Periodo inicial: 30s
- Reintentos: 3

```bash
# Verificar estado del healthcheck
docker inspect --format='{{json .State.Health}}' prices-service | jq
```
## 📚 API Documentation

### OpenAPI / Swagger

La documentación de la API está disponible a través de Swagger UI y sigue las especificaciones de OpenAPI 3.0.

#### Acceso a la Documentación

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/api-docs`
- **OpenAPI YAML**: `http://localhost:8080/api-docs.yaml`

#### Características de la Documentación

- Documentación completa de endpoints
- Ejemplos de requests y responses
- Esquemas de datos
- Try-out de endpoints
- Modelos de datos
- Códigos de respuesta
- Descripción de errores

### Endpoints Principales

#### GET /api/prices

Obtiene el precio aplicable para un producto en una fecha específica.

##### Parámetros de Consulta

| Parámetro | Tipo | Descripción | Ejemplo | 
|--------- |-----|-------------|------|
|applicationDate|String (ISO DateTime)|Fecha de aplicación|2020-06-14T10:00:00|
|productId|Long|ID del producto|35455|
|brandId|Long|ID de la marca (1 = ZARA)|1|

##### Respuestas

###### Éxito (200 OK)
```json
{
    "productId": 35455,
    "brandId": 1,
    "priceList": 1,
    "startDate": "2020-06-14T00:00:00",
    "endDate": "2020-12-31T23:59:59",
    "price": 35.50
}
```

###### Error (404 Not Found)
```json
{
    "status": 404,
    "message": "Price not found"
}
```

###### Error (400 Bad Request)
```json
{
    "status": 400,
    "message": "Invalid parameter format"
}
```

### Generación de Cliente

La documentación OpenAPI puede ser utilizada para generar clientes en diversos lenguajes utilizando herramientas como:
- OpenAPI Generator
- Swagger Codegen
- Spring Cloud OpenFeign

### Postman Collection

Puedes importar la especificación OpenAPI directamente en Postman:
1. En Postman, ve a "Import"
2. Selecciona "Link"
3. Ingresa: `http://localhost:8080/api-docs`
4. Postman generará automáticamente una colección con todos los endpoints

### Consideraciones de Seguridad

- Rate Limiting configurado
- Validación de parámetros
- Manejo de errores estandarizado
- Mensajes de error seguros
## 🔍 API Endpoints

### Consultar Precio
```http
GET /api/prices?applicationDate={date}&productId={id}&brandId={id}
```

#### Parámetros
- `applicationDate`: Fecha de aplicación (formato ISO: yyyy-MM-dd'T'HH:mm:ss)
- `productId`: ID del producto
- `brandId`: ID de la marca

## 🧪 Tests

El proyecto incluye tests exhaustivos que cubren diferentes escenarios:

```bash
mvn test
```

### Casos de Test
1. Petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)
2. Petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)
3. Petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)
4. Petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)
5. Petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)

## 🐳 Características del Docker

### Seguridad
- Ejecución con usuario no root (spring)
- Imagen base Alpine para minimizar superficie de ataque
- Permisos mínimos necesarios

### Optimización
- Multi-stage building para reducir tamaño de imagen
- Basado en Alpine Linux
- Caché de dependencias Maven
- .dockerignore para excluir archivos innecesarios

### Monitoreo
- Healthcheck integrado
- Endpoints de Actuator habilitados
- Logs configurados
- Métricas disponibles

## 📦 Base de Datos

La aplicación utiliza H2 en memoria con inicialización mediante Flyway:

- Archivo schema: `src/main/resources/db/migration/V1__create_prices_table.sql`
- Datos iniciales: `src/main/resources/db/migration/V2__insert_initial_data.sql`

## 🏷 Versionado

Se utiliza [SemVer](http://semver.org/) para el versionado.

## 🤝 Contribuir

1. Fork del repositorio
2. Crear rama para feature (`git checkout -b feature/AmazingFeature`)
3. Commit de cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Crear Pull Request

## ✨ Buenas Prácticas Implementadas

- Clean Architecture
- Domain-Driven Design
- SOLID Principles
- Reactive Programming
- Test-Driven Development
- Database Migration con Flyway
- Error Handling Global
- Logging apropiado
- Validación de parámetros
- Documentación clara

## 👥 Autores

* **[Daniel Bernal]** - *Init Repo* - [GitHub](https://github.com/danielbernalo) - [LinkedIn](https://www.linkedin.com/in/danielbernalo) 