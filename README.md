# README - ACT_1_5

Aplicación de consola en **Java + Spring Boot** para registrar eventos en un fichero de log, filtrarlos por fecha y
cambiar la codificación del fichero.

---

## Funcionalidades

- **Añadir evento**: pide fecha/hora y mensaje, y los guarda en src/main/resources/app.log.
- **Filtrar eventos**: busca en el log por una fecha dada y muestra coincidencias.
- **Cambiar codificación**: permite alternar entre UTF-8 e ISO-8859-1 para leer/escribir el log.
- **Interfaz tipo menú** en consola.

Formato de línea en el log:
[dd/MM/yyyy HH:mm:ss] Usuario: "Mensaje del evento"

---

## Uso del menú

Select an option

- 1.Add event
- 2.Filter envents
- 3.Configurate codificate
- 4.Exit

### 1. Add Event

* Introduce la fecha y hora en formato exacto: dd/MM/yyyy HH:mm:ss
* Ejemplo: 27/10/2025 14:35:00
* Escribe el mensaje del evento y pulsa ENTER.
* Se registrará en src/main/resources/app.log, por ejemplo:

[27/10/2025 14:35:00] Usuario: "Mensaje del evento"

### 2. Filter Events

+ Introduce una fecha o prefijo de fecha que exista entre corchetes en el log.
+ Ejemplo exacto: 27/10/2025 14:35:00
+ Ejemplo por día (mostrará todos los de ese día): 27/10/2025

Salida de ejemplo si se encuentra el evento:

Event found : [27/10/2025 14:35:00] Usuario: Pedido #123 confirmado

Si no se encuentra:

No events found for the given date: 26/10/2025

### 3. Configurate Codificate

* Elige entre las opciones de codificación:

        1. UTF-8
        2. ISO-8859-1

  La codificación elegida se usará inmediatamente para leer y escribir el fichero app.log.

### 4. Exit

* Termina la aplicación de consola.


