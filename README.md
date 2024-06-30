# Resumen del Proyecto

## Problema a Resolver:
El éxito del cultivo de arándanos en el campo de Don José ha introducido nuevas necesidades, como la gestión del riego y el almacenamiento adecuado de la cosecha. El riego manual es complicado debido a la salinización de las napas y la necesidad de controlar la proporción de agua del canal y del pozo. Además, el almacenamiento de los arándanos requiere condiciones específicas de temperatura y humedad.

## Objetivos Generales:
Modernizar el campo de Don José mediante un sistema de automatización basado en IoT, permitiendo un control remoto eficiente de los procesos agrícolas clave para mejorar la eficiencia, sostenibilidad y calidad de la producción.

## Objetivos Específicos:

- Optimización del Riego:

Implementar sensores para medir la humedad del suelo y la salinidad del agua.
Automatizar la activación de bombas de riego y la mezcla de agua del canal y del pozo.

- Control Remoto de Procesos:

Desarrollar una interfaz de usuario sencilla e intuitiva.
Permitir la programación automática del riego y el manejo del almacenamiento de agua.
Mejora en el Almacenamiento de la Cosecha:

Implementar sistemas de control para la temperatura, humedad y calidad del aire en el depósito subterráneo.

- Ahorro de Tiempo y Recursos Humanos:

Reducir la intervención manual en tareas agrícolas.
Permitir a Don José y su hijo dedicar más tiempo a actividades de capacitación y gestión.

- Sostenibilidad Ambiental:

Reducir el consumo innecesario de agua.
Fomentar prácticas agrícolas más sostenibles.
Propuesta de Solución
Propuesta Funcional:
La solución se basa en una aplicación Java, utilizando MySQL para la persistencia de datos y Mosquitto como broker MQTT. El sistema incluye:

## Aplicación:

- Aplicacion Java MVC:

  Gestiona la lógica de negocio, control del riego y almacenamiento.
  Comunica con dispositivos LORA y el broker MQTT.
  Permite el control y monitoreo por los usuarios.

- Aplicación Frontend:

  Proporciona una interfaz de usuario intuitiva.
  Se conecta al servidor backend a través de Internet.
  Ofrece un dashboard sencillo con indicadores y comandos de control.

- Base de Datos MySQL:

  Almacena datos del sistema, registros de riego, niveles de agua, temperatura, humedad y datos de usuarios.

- Broker MQTT (Mosquitto):

  Gestiona la comunicación entre el gateway LORA/MQTT y la aplicación Java.

- Gateway LORA/MQTT:

  Recibe datos de sensores y dispositivos LORA.
  Transmite datos al broker MQTT para su procesamiento.
  Funciones Principales del Sistema:

- Control Remoto del Riego:
  Programación automática y manual del riego.
  Supervision humedad del suelo.
  Control de bombas.
- Control de la Cámara Frigorífica:
  Regulación automática de temperatura y humedad.
  Monitoreo y notificaciones en tiempo real.
- Notificaciones y Alertas:
  Envío de alertas y notificaciones sobre eventos importantes.

  # Prueba MVP
  ## Credenciales
  - usuario: user
  - password: pass
