# Uso de herencia y polimorfismo:

## Herencia:

El archivo "Personaje.java" se usara como herencia de los siguientes archivos; "Jugador.java" y "Enemigo.java". Se coinsidero el uso de este archivo para heredar distintas estadisticas a los personajes jugables y no jugables dentro de "The Ultimate Last Punch", estadisticas tales como vida, vida maxima, danio de los ataques comunes y especiales junto a la velocidad de los personajes.

En el caso de "Escenario.java", archivo que hereda codigo a "Combate.java". Se heredan las caracteristicas del escenario(si el suelo es solido o no para definir que esta cayendo) y la variable tiempo. Con esto, se busca que al finalizar/tiempo=0 se decida un ganador. A la vez, que propiamente, el combate pueda ser iniciado.

## Polimorfismo.
Gracias a lo heredado en por parte de "Personajes.java", dentro de las clases que heredan las varibles, se usaran para definir parametros dentro del juego. Como cuanta vida tiene un personaje, cuanto se mueve al mantener presionada determinada tecla y a que velocidad va, entre otras cosas que se iran definiendo de mejor manera durante la cursada y el avance del proyecto.

En "Combate.java" se usara el polimorfismo para poder realizar funciones de inicio y finalizacion del combate gracias a lo heredado.