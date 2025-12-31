# Changelog

Todos los cambios notables en este proyecto serán documentados en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.1.1] - 2025-12-31

### Added
- Mejora en DgeFileController: ampliación de lista de clientes para generación de archivos DGE
  - Agregados IDs de cliente 2218L y 2219L a la lista de generación

### Changed
- Upgrade de Java: 24 → 25 (pom.xml, Dockerfile, GitHub Actions)
- Upgrade de Spring Boot: 3.5.6 → 3.5.8
- Actualización de imágenes Docker: eclipse-temurin-24 → eclipse-temurin-25
- Mejoras en pipeline CI/CD con JDK 25

### Fixed
- Corrección de incoherencias en documentación de versiones de dependencias

## [0.1.0] - 2025-09-30

### Added
- Nueva entidad ClienteVolumen para gestión de volúmenes de clientes
- Servicio ClienteVolumenService y repositorio ClienteVolumenRepository
- Excepción ClienteVolumenException para manejo de errores en volúmenes
- Utilidad Jsonifier para serialización JSON de objetos
- Método jsonify() en modelos Factura y Periodo para debugging y logging
- Implementación de manejo de excepciones en ClienteController
  - Manejo de ClienteException con ResponseStatusException
  - Mejora en el manejo de errores HTTP

### Changed
- Actualización de dependencias: Spring Boot 3.5.3 -> 3.5.6, openpdf 2.2.3 -> 3.0.0, springdoc 2.8.9 -> 2.8.10
- Refactorización de constructores con @RequiredArgsConstructor en controladores y servicios (DgeFileController, MedidorService, ConsumoService, DgeFileService)
- Mejoras en DgeFileService para incluir cálculo de consumo en archivos DGE generados
- Configuración de health check para mail en bootstrap.yml
- Mejora en la validación de montos en facturación
  - Implementación de límite máximo de 999999.99
  - Retorno de código "00" para montos excedidos

## [0.0.1] - 2023-05-25

### Added
- Configuración inicial del proyecto
- Implementación de CI/CD con GitHub Actions
- Integración con Spring Boot 3.1 (2023-06-27)
- Configuración de clientes
- Implementación de manejo de excepciones estandarizado (2025-01-11)
- Mejoras en la calidad del código

### Changed
- Actualización de versiones de dependencias (2023-06-30)
- Cambios en la configuración de Maven para GitHub Actions (2024-02-04)
- Actualización del Dockerfile para Java 21 (2024-02-04)
- Upgrade de Spring Boot (2024-02-04)

### Removed
- Eliminación de la conexión 767 de la DGE (2024-02-28) 