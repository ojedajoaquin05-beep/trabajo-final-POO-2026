package Controlador;

import Modelo.Escenario;
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

    private void porcesarAtaque(){
        Peleador p1 = modeloJuego.getJugador1();
        Peleador p2 = modeloJuego.getJugador2();

        if (p1 == null || p2 == null) return;
        if (teclado.isP1Golpe()){
        p1.ejecutarAtaque(Modelo.ActionType.PUNCH, p2);
        }
        if (teclado.isP2Golpe()){
        p2.ejecutarAtaque(Modelo.ActionType.PUNCH, p1);
        }
    }

    private void configurarGameLoop() {
        gameLoop = new Timer(16, e -> {

            Peleador p1 = modeloJuego.getJugador1();
            Peleador p2 = modeloJuego.getJugador2();

            //Procesar movimiento horizontal únicamente (A/D y Flechas)
            procesarMovimiento();
            porcesarAtaque();

            if(p1 != null) p1.aplicarFisica();
            if(p2 != null) p2.aplicarFisica();

            //Fisicas y límites del escenario
            Escenario escenario = modeloJuego.getEscenarioActual();
            if (escenario != null) {
                if (p1 != null) {

                    //verifica si toca el suelo 
                    boolean sobreSueloP1 = escenario.estaSobreSuelo(p1);
                    p1.setEnElSuelo(sobreSueloP1);

                    escenario.delimitarMovimiento(p1);
                }
                if (p2 != null) {

                    //verifica si toca el suelo 
                    boolean sobreSueloP2 = escenario.estaSobreSuelo(p2);
                    p2.setEnElSuelo(sobreSueloP2);

                    escenario.delimitarMovimiento(p2);
                }
            }

            //Control del temporizador de combate
            if (!modeloJuego.hayGanador()) {
            	// Calcula el tiempo transcurrido exacto entre frames para evitar que el contador corra más lento en caso de no poder sostener 60 fps constantes
            	long timestamp = System.nanoTime();
            	modeloJuego.descontarTiempo(timestamp - timestampUltimoFrame);
            	timestampUltimoFrame = timestamp;

            	if (modeloJuego.tiempoAgotado()) {
            	    modeloJuego.definirGanadorPorTiempo();
            	}
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

            //salto
            if(teclado.isP1Arriba()){
                p1.saltar();
            }

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
            if (seMueveP1 && p1.isEnElSuelo()) {
                p1.actualizarAnimacion(4); // 2x2 = 4 frames
            }
        }

        // JUGADOR 2 (Flechas) 
        Peleador p2 = modeloJuego.getJugador2();
        if (p2 != null) {
            float vel = p2.getVelocidad();
            boolean seMueveP2 = false;

             //salto
            if(teclado.isP2Arriba()){
                p2.saltar();
            }

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

            if (seMueveP2 && p2.isEnElSuelo()) {
                p2.actualizarAnimacion(4);
            }
        }
    }
}