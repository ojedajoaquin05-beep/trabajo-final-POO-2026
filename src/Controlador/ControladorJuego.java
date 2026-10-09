package Controlador;

import Modelo.Juego;
import Modelo.Peleador;
import Vista.VentanaJuego;
import javax.swing.Timer;

public class ControladorJuego {

    private final VentanaJuego vista;
    private final Juego modeloJuego;
    private final ControladorTeclado teclado;

    private Timer gameLoop;
    private long timestampUltimoFrame = 0;

    public ControladorJuego(VentanaJuego vista, Juego modeloJuego, ControladorTeclado teclado) {
        if (vista == null || modeloJuego == null || teclado == null) {
            throw new IllegalArgumentException("Ninguna de las dependencias puede ser nula.");
        }
        this.vista = vista;
        this.modeloJuego = modeloJuego;
        this.teclado = teclado;

        configurarGameLoop();
    }

    public void iniciarPartida() {
        vista.reiniciarTiempo(); // Reiniciamos el reloj de la partida
        this.timestampUltimoFrame = System.nanoTime();
        this.teclado.configurarTeclas(vista.getPantallaJugar());
        vista.getPantallaJugar().requestFocusInWindow();

        gameLoop.start(); // Inicializo el motor del juego
    }

    public void detenerPartida() {
        if (gameLoop != null && gameLoop.isRunning()) {
            gameLoop.stop();
        }
    }

    private void configurarGameLoop() {
        gameLoop = new Timer(16, e -> {

            //Procesar movimiento horizontal únicamente (A/D y Flechas)
            procesarMovimiento();

            //Fisicas y límites del escenario
            if (modeloJuego.getEscenarioActual() != null) {
                if (modeloJuego.getJugador1() != null) {
                    modeloJuego.getEscenarioActual().delimitarMovimiento(modeloJuego.getJugador1());
                }
                if (modeloJuego.getJugador2() != null) {
                    modeloJuego.getEscenarioActual().delimitarMovimiento(modeloJuego.getJugador2());
                }
            }

            //Control del temporizador de combate
            if (timestampUltimoFrame > 0 && vista.getTiempoRestante() > 0) {
            	// Calcula el tiempo transcurrido exacto entre frames para evitar que el contador corra más lento en caso de no poder sostener 60 fps constantes
            	long timestamp = System.nanoTime();
            	vista.setTiempoRestante(vista.getTiempoRestante() - (timestamp - timestampUltimoFrame));
            	timestampUltimoFrame = System.nanoTime();
            }

            if (vista.getTiempoRestante() <= 0) {
                evaluarGanadorPorTiempo();
            }

            //  Redibujar la pantalla
            vista.getPantallaJugar().repaint();
        });
    }

    private void procesarMovimiento() {
        // JUGADOR 1 (wasd)
        Peleador p1 = modeloJuego.getJugador1();
        if (p1 != null) {
            float vel = p1.getVelocidad();
            boolean seMueveP1 = false;

            if (teclado.isP1Derecha()) {
                p1.mover(vel, 0);
                p1.setMirandoDerecha(true);
                seMueveP1 = true;
            }
            if (teclado.isP1Izquierda()) {
                p1.mover(-vel, 0);
                p1.setMirandoDerecha(false);
                seMueveP1 = true;
            }

            // Animar únicamente si se está moviendo
            if (seMueveP1) {
                p1.actualizarAnimacion(4); // 2x2 = 4 frames
            }
        }

        // JUGADOR 2 (Flechas) 
        Peleador p2 = modeloJuego.getJugador2();
        if (p2 != null) {
            float vel = p2.getVelocidad();
            boolean seMueveP2 = false;

            if (teclado.isP2Derecha()) {
                p2.mover(vel, 0);
                p2.setMirandoDerecha(true);
                seMueveP2 = true;
            }
            if (teclado.isP2Izquierda()) {
                p2.mover(-vel, 0);
                p2.setMirandoDerecha(false);
                seMueveP2 = true;
            }

            if (seMueveP2) {
                p2.actualizarAnimacion(4);
            }
        }
    }

    private void evaluarGanadorPorTiempo() {
        int vidaP1 = (modeloJuego.getJugador1() != null) ? modeloJuego.getJugador1().getVida() : 100;
        int vidaP2 = (modeloJuego.getJugador2() != null) ? modeloJuego.getJugador2().getVida() : 80;

        if (vidaP1 > vidaP2) {
            vista.setMensajeGanador("Jugador 1 gana por tiempo");
        } else if (vidaP2 > vidaP1) {
            vista.setMensajeGanador("Jugador 2 gana por tiempo");
        } else {
            vista.setMensajeGanador("Empate por tiempo");
        }
    }
}