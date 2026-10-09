package Modelo;

public class Juego { // por ahora lo dejo vacio asi pruebo el menu y los botones
private Peleador jugador1;
private Peleador jugador2;
private Escenario escenarioActual;

public Juego(){
// Constructor vacío
} 

public void inicializarJuego(String personajeP1, String personajeP2, String nombreEscenario, int anchoVentana, int altoVentana) {
    if ("EscenarioCiudad".equalsIgnoreCase(nombreEscenario) || "escenario1".equalsIgnoreCase(nombreEscenario)) {
        this.escenarioActual = new EscenarioCiudad(anchoVentana, altoVentana);
    } else if ("EscenarioItalia".equalsIgnoreCase(nombreEscenario) || "escenario2".equalsIgnoreCase(nombreEscenario)) {
        this.escenarioActual = new EscenarioItalia(anchoVentana, altoVentana);
    } else {
        throw new IllegalArgumentException("Nombre de escenario no válido: " + nombreEscenario);
    }

    // Inicializar los jugadores con sus personajes y posiciones iniciales
    this.jugador1 = new Peleador(personajeP1, 150, 400, 100, 10, 5.0f); // Posición inicial y atributos del jugador 1
    this.jugador2 = new Peleador(personajeP2, anchoVentana - 300, 400, 100, 10, 5.0f); // Posición inicial y atributos del jugador 2
    this.jugador2.setMirandoDerecha(false); // El jugador 2 comienza mirando hacia la izquierda
}

public void actualizar() {
  /*   if (jugador1 != null) {
        jugador1.actualizarAnimacion(10); // Pasa la cantidad de frames directamente
        if (escenarioActual != null) {
            escenarioActual.delimitarMovimiento(jugador1);
        }
    }
    if (jugador2 != null) {
        jugador2.actualizarAnimacion(10); // Pasa la cantidad de frames directamente
        if (escenarioActual != null) {
            escenarioActual.delimitarMovimiento(jugador2);
        }
    } */ //lo comento pq ahora la logica la maneja controladorJuego y no el modelo, asi que no es necesario que el modelo se actualice a si mismo
}

public Peleador getJugador1() {
    return jugador1;
}

public Peleador getJugador2() {
    return jugador2;
}

public Escenario getEscenarioActual() {
    return escenarioActual;
}

}
 