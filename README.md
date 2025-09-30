# SAUCE.agua.core-service

[![SAUCE.agua.rest CI](https://github.com/SAUCE-services/SAUCE.agua.core-service/actions/workflows/maven.yml/badge.svg)](https://github.com/SAUCE-services/SAUCE.agua.core-service/actions/workflows/maven.yml)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.0-blue.svg)](https://spring.io/projects/spring-cloud)
[![Maven](https://img.shields.io/badge/Maven-3.8.8-red.svg)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)

Servicio core para la gestión de servicios de agua, parte del ecosistema SAUCE.

## Tecnologías Principales

- Java 21
- Spring Boot 3.5.6
- Spring Cloud 2025.0.0
- Spring Data JPA
- MySQL
- Maven 3.8.8
- OpenPDF 3.0.0
- SpringDoc OpenAPI 2.8.10
- Caffeine Cache

## Características

- Gestión de clientes y facturación
- Gestión de volúmenes de clientes
- Validación de montos máximos en facturación
- API REST con documentación OpenAPI
- Manejo de excepciones estandarizado
- Logging con Log4j2

## Requisitos

- Java 21
- Maven 3.8.8 o superior
- MySQL 8.0 o superior

## Instalación

1. Clonar el repositorio
2. Configurar las variables de entorno necesarias
3. Ejecutar `mvn clean install`
4. Ejecutar la aplicación con `mvn spring-boot:run`

## Documentación

La documentación de la API está disponible en `/swagger-ui.html` cuando la aplicación está en ejecución.

## Contribución

Por favor, lee [CONTRIBUTING.md](CONTRIBUTING.md) para detalles sobre nuestro código de conducta y el proceso para enviarnos pull requests.

## Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para más detalles.
