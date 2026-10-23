package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Juego { // por ahora lo dejo vacio asi pruebo el menu y los botones
private static final long DURACION_PARTIDA = 100000000000L; // 100 segundos expresados en nanosegundos

private Peleador jugador1;
private Peleador jugador2;
private Escenario escenarioActual;
private long tiempoRestante = DURACION_PARTIDA;
private String mensajeGanador = "";
private final List<ObservadorJuego> observadores = new ArrayList<>();
private final PuntajeDAO puntajeDAO;
private final ConfiguracionDAO configuracionDAO;
private Peleador ganador; // null mientras no haya ganador

public Juego(ConfiguracionDAO configuracionDAO, PuntajeDAO puntajeDAO){
   if (configuracionDAO == null || puntajeDAO == null){
    throw new IllegalArgumentException("El DAO es obligatorio");
   }
   this.configuracionDAO = configuracionDAO;
   this.puntajeDAO = puntajeDAO;
} 

public void agregarObservador(ObservadorJuego observador) {
    if (observador == null) {
        throw new IllegalArgumentException("El observador no puede ser nulo.");
    }
    observadores.add(observador);
}

public void notificarObservadores() {
    for (ObservadorJuego observador : observadores) {
        observador.alCambiarEstadoJuego(this);
    }
}

public void inicializarJuego(String personajeP1, String personajeP2, String nombreEscenario, int anchoVentana, int altoVentana) {

    this.mensajeGanador = "";
    this.ganador = null;
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

    reiniciarTiempo(); // cada partida nueva arranca con el reloj completo y sin ganador
    notificarObservadores(); // Notificar a los observadores que el juego ha sido inicializado
}

public void reiniciarTiempo() {
    this.tiempoRestante = DURACION_PARTIDA;
    this.mensajeGanador = "";
}

// Resta el tiempo transcurrido (en nanosegundos). Nunca baja de cero.
public void descontarTiempo(long nanosTranscurridos) {
    this.tiempoRestante = Math.max(0, this.tiempoRestante - nanosTranscurridos);
}

public boolean tiempoAgotado() {
    return tiempoRestante <= 0;
}

public boolean hayGanador() {
    return !mensajeGanador.isEmpty();
}

// Gana el que tenga más vida cuando se termina el tiempo
public void definirGanadorPorTiempo() {
    int vidaP1 = jugador1.getVida();
    int vidaP2 = jugador2.getVida();

    if (vidaP1 > vidaP2) {
        mensajeGanador = "Jugador 1 gana por tiempo";
        this.ganador = jugador1;
    } else if (vidaP2 > vidaP1) {
        mensajeGanador = "Jugador 2 gana por tiempo";
        this.ganador = jugador2;
    } else {
        mensajeGanador = "Empate por tiempo";
        this.ganador = null;
    }
}

// Si algún peleador se quedó sin vida, el otro gana por KO
public void verificarKO() {
    if (hayGanador()) {return;}

    boolean p1Vivo = jugador1.estaVivo();
    boolean p2Vivo = jugador2.estaVivo();

    if (!p1Vivo && !p2Vivo) {
        mensajeGanador = "Doble KO: empate";
        this.ganador = null;
    } else if (!p2Vivo) {
        mensajeGanador = "Jugador 1 gana por KO";
        this.ganador = jugador1;
        jugador1.sumarPuntaje(100);
        notificarObservadores();
    } else if (!p1Vivo) {
        mensajeGanador = "Jugador 2 gana por KO";
        this.ganador = jugador2;
        jugador2.sumarPuntaje(100);
        notificarObservadores();
    }
   
}

// Puntaje de la partida: puntos por golpes + bonus por vida restante
public int puntajeGanador() {
    if (ganador == null) return 0;
    return ganador.getPuntaje() + ganador.getVida() * 5; // bonus por vida restante
}

public boolean guardarPuntaje(String iniciales) {
    if (ganador == null) return false;
    try {
        return puntajeDAO.guardar(new Puntaje(iniciales, puntajeGanador(), LocalDate.now()));
    } catch (IllegalArgumentException e) {
        return false; // iniciales inválidas
    }
}

public boolean guardarVolumen(int vol) {
	return configuracionDAO.setVolumen(vol);
}

public int leerVolumen() {
	return configuracionDAO.getVolumen();
}

public List<Puntaje> mejoresPuntajes() {
    return puntajeDAO.mejores(10);
}

    // Un tick del juego: avanza el reloj, define ganador por tiempo y avisa UNA sola vez a los observadores
    public void avanzarTick(long nanosTranscurridos) {
        verificarKO(); // Verifica si hay un ganador por KO antes de descontar tiempo
        if (!hayGanador()) {
          descontarTiempo(nanosTranscurridos);
           if (tiempoAgotado()) {
               definirGanadorPorTiempo();
            }
        }
        notificarObservadores();
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

public long getTiempoRestante() {
    return tiempoRestante;
}

public String getMensajeGanador() {
    return mensajeGanador;
}

}