# EE_Backend

Backend del proyecto **EE_Backend** (UTP), basado en una **arquitectura de microservicios**.

## 🧱 Arquitectura del Proyecto y Patrones Utilizados

El sistema **EE_Backend** emplea una **arquitectura de microservicios**, donde la aplicación se estructura como una colección de servicios pequeños e independientes. Algunos de los patrones clave utilizados incluyen:

* **API Gateway:** Actúa como un **punto de entrada único** (Single Entry Point) para todos los clientes, implementando el patrón **Gateway**.
* **Servicio de Autenticación Dedicado:** El **auth-service** sigue el patrón de **Servicio de Autenticación Centralizado**, separando la lógica de autenticación y autorización.
* **Descubrimiento de Servicios:** Se utiliza **Eureka Server** para la **detección de servicios** (Service Discovery), permitiendo a los microservicios localizarse dinámicamente.
* **Configuración Centralizada:** El **config-server** implementa el patrón de **Configuración Externa** (Externalized Configuration), facilitando la gestión y distribución de la configuración.
* **Orquestación:** El **orchestrator-service** implementa el patrón de **Orquestador** (Orchestrator) para coordinar interacciones complejas entre **client-service** y **auth-service**.
* **Comunicación Síncrona:** La comunicación principal entre los microservicios (a través del API Gateway o directamente) se realiza mediante llamadas síncronas (típicamente HTTP/REST).
* **Base de Datos Compartida:** Si bien no es un patrón estrictamente de microservicios, en este caso se utiliza una **base de datos centralizada** (Shared Database) en MySQL para la persistencia de datos.

**Componentes:**

* **api-gateway**: Punto de entrada único para clientes.
* **auth-service**: Autenticación y autorización de usuarios.
* **chat-service**: Funcionalidad de chat en tiempo real.
* **client-service**: Gestión de información de clientes.
* **config-server**: Configuración centralizada.
* **content-service**: Administración de contenido multimedia y textual.
* **employee-service**: Gestión de datos de empleados.
* **eureka-server**: Descubrimiento de servicios.
* **event-service**: Gestión de eventos.
* **orchestrator-service**: Coordina **client-service** y **auth-service**.
* **order-pay-service**: Gestión de órdenes y pagos.
* **product-service**: Administración de catálogo de productos.
* **report-service**: Generación de informes y estadísticas.

**Patrones de Comunicación:**

* **API Gateway:** Enrutamiento centralizado.
* **Eureka Server:** Registro y descubrimiento dinámico de servicios.
* **Config Server:** Gestión centralizada de la configuración.
* **Orquestador:** Coordinación específica entre servicios.
* **Comunicación Directa:** Interacciones limitadas a través del API Gateway.
* **Base de Datos:** Persistencia centralizada en MySQL.

## 🚀 Tecnologías Utilizadas

* Java 17
* Spring Boot
* Spring Cloud (Eureka, Config Server, Gateway)
* Maven
* MySQL

## 🛠️ Instalación y Ejecución

1.  Clonar el repositorio.
2.  Configurar la base de datos MySQL.
3.  Construir los microservicios con Maven (`mvn clean install`).
4.  Ejecutar cada microservicio individualmente (`mvn spring-boot:run` en el directorio de cada servicio).

## 📡 Endpoints Principales

* **API Gateway:** `http://localhost:8080/`
* **Eureka Server:** `http://localhost:8761/`
* **Config Server:** `http://localhost:8888/`

Consulta la documentación individual de cada microservicio para más detalles.

## 📊 Diagrama de Arquitectura

![Diagrama de Arquitectura EE_Backend](https://github.com/user-attachments/assets/925c7b4c-22b5-4a79-b3b7-2b405a68a894)

## 📊 Diagrama de Base de Datos
![Image](https://github.com/user-attachments/assets/2e8e68a7-14d5-44ff-aad9-25a69c27aa78)
