# Tarea 2: Entrega parcial 1

Su trabajo en esta tarea consiste en implementar la base del controlador del juego.
El controlador servirá como intermediario entre los objetos del modelo y la interfaz gráfica de la
aplicación.

El controlador debe encargarse de mantener todos los parámetros necesarios para implementar las 
reglas y el flujo del juego.
El controlador pueden ser una o más clases, pero es importante que la interfaz gráfica no interactúe
directamente con los objetos del modelo y vice versa.

El objetivo del controlador será enviar mensajes a los objetos del modelo indicándoles lo que deben
hacer y los objetos del modelo deben ser capaces de comunicar al controlador sobre cualquier cambio
que sea relevante para el flujo del juego y para el usuario (lo que se mostrará en la interfaz gráfica)

Específicamente el controlador debe ser capaz de: 
- Crear paneles
- Crear jugadores, *wild units* y *boss units*.
  En el caso de los jugadores, también debe ser capaz de ubicarlos en algún panel.
- Asignarle a cada panel uno o más paneles siguientes.
- Obtener todos los paneles del tablero.
- Saber cuando un jugador gana llegando a la norma máxima
- Definir el objetivo para aumentar de norma para un jugador (estrellas o victorias)
- Definir el *home panel* de un jugador
- Obtener el capítulo actual del juego
- Obtener el jugador que es dueño del turno
- Terminar el turno del jugador actual
- Realizar un *norma check* y *norma clear* cuando un jugador cae en un *home panel*
- Detener el movimiento de un jugador si este:
  - Pasa por su *home panel*
  - Pasa por un panel en el que haya otro jugador
  - Cae en un panel que tiene más de un panel siguiente

Para lo anterior considere que el orden de los turnos será el mismo en el que se creen los 
jugadores.

Todas estas funcionalidades deben estar debidamente testeadas.

Además se le entrega una clase mediadora que deberá modificar según se le indica para que se adapte
a su implementación, y un set de tests que prueban las funcionalidades mínimas que debe cumplir el
controlador y que se utilizará para evaluar su entrega.

Estos tests **NO CUENTAN en el *coverage*** de su tarea y no reemplazan sus tests, cada clase y 
método que escriba debe estar respaldada por tests de su autoría.

Se le recomienda que **no se base** en el código que se le entrega para desarrollar su 
implementación debido a que está escrito de forma que sea independiente de su implementación y su 
diseño no es el que se espera de su implementación.

Además, el mediador **no debe** manejar la lógica del juego de ninguna manera, sino que encargarle
esa tarea al controlador, por ejemplo:

```java
// Esta implementación es incorrecta
public Pair<MediatorPlayer<?>, MediatorPanel<?>> createPlayer(
    MediatorPanel<?> mediatorPanel, String name, int hitPoints, int attack, int defense,
    int evasion) {
  Player player = new Player(...);
  MediatorPlayer<?> mediatorPlayer = new MediatorPlayer(player);
  return new Pair<>(mediatorPlayer, mediatorPanel);
}

// Esta es una implementación aceptable
public Pair<MediatorPlayer<?>, MediatorPanel<?>> createPlayer(
    MediatorPanel<?> mediatorPanel, String name, int hitPoints, int attack, int defense,
    int evasion) {
  MediatorPlayer<?> mediatorPlayer = new MediatorPlayer(controller.createPlayer(...));
  return new Pair<>(mediatorPlayer, mediatorPanel);
}
```

```yaml 
Profesor: "Alexandre Bergel"
Auxiliares:
  - "Beatriz Graboloza"
  - "Ignacio Slater"
  - "Tomás Vallejos"
```