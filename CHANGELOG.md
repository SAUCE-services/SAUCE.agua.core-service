# Changelog

Todos los cambios notables en este proyecto serán documentados en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Implementación de manejo de excepciones en ClienteController
  - Manejo de ClienteException con ResponseStatusException
  - Mejora en el manejo de errores HTTP

### Changed
- Actualización de SpringDoc OpenAPI a 2.8.8
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