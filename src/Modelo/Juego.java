package Modelo;

public class Juego { // por ahora lo dejo vacio asi pruebo el menu y los botones
private Peleador jugador1;
private Peleador jugador2;
private String escenario;

public Juego(){
// Constructor vacío
} 

public void inicializarJuego(String personajeP1, String personajeP2, String escenario) {
    this.escenario = escenario;
    // Inicializar los jugadores con sus personajes y posiciones iniciales
    this.jugador1 = new Peleador(personajeP1, 150, 400, 100, 10, 5.0f); // Posición inicial y atributos del jugador 1
    this.jugador2 = new Peleador(personajeP2, 650, 400, 100, 10, 5.0f); // Posición inicial y atributos del jugador 2
    this.jugador2.setMirandoDerecha(false); // El jugador 2 comienza mirando hacia la izquierda
}

public void actualizar() {
    if (jugador1 != null) {
        jugador1.actualizarAnimacion(10); // Pasa la cantidad de frames directamente
    }
    if (jugador2 != null) {
        jugador2.actualizarAnimacion(10); // Pasa la cantidad de frames directamente
    }
}

public Peleador getJugador1() {
    return jugador1;
}

public Peleador getJugador2() {
    return jugador2;
}

public String getEscenario() {
    return escenario;
}

}
 