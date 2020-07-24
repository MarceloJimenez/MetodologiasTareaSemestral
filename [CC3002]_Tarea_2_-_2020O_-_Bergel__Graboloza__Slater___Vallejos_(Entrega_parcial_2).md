# Tarea 2: Entrega parcial 2

Su tarea ahora consistirá en implementar el *flujo del juego*.

Para esta parte del proyecto deberá basarse en la sección 1.5 de la descripción del proyecto y 
definir todas las fases y las transiciones entre ellas (considerando las condiciones que deben 
cumplirse) para pasar de una a otra.

Para el caso especial en el que se requiera interacción del usuario, deberá crear fases adicionales
que representen una *fase de espera*.
Por ejemplo, en el momento en que un jugador debe decidir si defender o evadir se entraría en una 
fase de espera de la que se saldrá de acuerdo a la decisión del jugador.

Para testear estas fases deberá simular las acciones que podría realizar el jugador para ver que se
pueda representar un turno completo, esto quiere decir que debe definir tests en los que 
arbitrariamente se decida defender o evadir (debe haber tests para estos dos casos) y proceder de 
acuerdo a esa decisión.

*Hint:* Divida el turno en fases que sean lo más atómicas posible y dibuje un diagrama de éstas.

```yaml 
Profesor: "Alexandre Bergel"
Auxiliares:
  - "Beatriz Graboloza"
  - "Ignacio Slater"
  - "Tomás Vallejos"
```