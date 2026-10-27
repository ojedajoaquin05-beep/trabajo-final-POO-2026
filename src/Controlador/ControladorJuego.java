package Controlador;

import Modelo.Juego;
import Vista.VentanaJuego;
import java.awt.CardLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ControladorJuego {

    private final VentanaJuego vista;
    private final Juego modeloJuego;
    private final ControladorTeclado teclado;

    private Timer gameLoop;
    private long timestampUltimoFrame = 0;

    private int ticksFinPartida = 0;
    private static final int TICKS_ESPERA_FIN = 90;

    private boolean pausado = false;
    private AlTerminarCombate alTerminarCombate;



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
        this.pausado = false;
        this.ticksFinPartida = 0;
        this.timestampUltimoFrame = System.nanoTime();
        this.teclado.configurarTeclas(vista.getPantallaJugar());

        vista.getPantallaJugar().revalidate();
        vista.getPantallaJugar().repaint();
        vista.getPantallaJugar().requestFocusInWindow();

        gameLoop.start(); // Inicializo el motor del juego
    }

     public void setAlTerminarCombate(AlTerminarCombate alTerminarCombate) {
        this.alTerminarCombate = alTerminarCombate;
    }

    public void detenerPartida() {
        if (gameLoop != null && gameLoop.isRunning()) {
            gameLoop.stop();
        }
        teclado.resetearTeclas();
    }

    public void alternarPausa() {
    pausado = !pausado;

    if (pausado) {
        teclado.resetearTeclas();
        // Ordena al CardLayout mostrar el panel de pausa
    } else {
        timestampUltimoFrame = System.nanoTime();
        // Regresa al panel del juego
        vista.getPantallaJugar().requestFocusInWindow();
    }

    CardLayout botones = vista.getGestorBotones();
    JPanel contenedor = vista.getPanelContenedor();
    botones.show(contenedor, pausado ? "PANTALLA_PAUSA" : "BOTON_JUEGO");

    // Forzar redibujado inmediato del contenedor de tarjetas
    contenedor.revalidate();
    contenedor.repaint();
  }

// Al terminar la partida, el ganador ingresa 3 letras y el modelo guarda el puntaje
private void pedirInicialesYGuardar() {
    if (!modeloJuego.hayGanador() || modeloJuego.puntajeGanador() <= 0) {
        return; // empate o sin puntos, no se guarda nada
    }
    String mensaje = "Tu puntaje fue: " + modeloJuego.puntajeGanador() + "\nIngresa 3 letras para guardarlo:";
    while (true) {
        String iniciales = JOptionPane.showInputDialog(
                null, mensaje, "Nuevo puntaje", JOptionPane.PLAIN_MESSAGE);
        if (iniciales == null) {
            return; // cancelo. no se guarda
        }
        if (modeloJuego.guardarPuntaje(iniciales.trim())) {
            return; // guardado
        }
        mensaje = "Debes ingresar exactamente 3 letras, vuelve a intentarlo:";
    }
}

    // Un tick cada 16 ms (aprox 60 cuadros por segundo) corre en el hilo de eventos 
    private void configurarGameLoop() {
        gameLoop = new Timer(16, e -> {

            //detectar si se presiona ESC
            if(teclado.isJuegoPausadoPulsado()){
                alternarPausa();
            }

            //si esta pausado no se procesa ni fisica ni ataques
            if(pausado){
                if(vista.getPanelPausa().isEscapePresionado()){
                    alternarPausa();
                }
                return;
            }

            // Solo se acepta movimiento y ataque mientras la partida siga
            if (modeloJuego.hayGanador()) {
                ticksFinPartida++;
                if (ticksFinPartida >= TICKS_ESPERA_FIN) {
                    terminarCombate();
                    return;
                }
            } else {
                // El controlador solo entrega las teclas; que hace cada peleador lo decide el modelo
                modeloJuego.procesarControles(teclado.getJugador1(), teclado.getJugador2());
            }

            //El modelo avanza (fisica, colision, rayos, reloj) y avisa a la vista (el panel se redibuja solo, es el Observer)
            long timestamp = System.nanoTime();
            modeloJuego.avanzarTick(timestamp - timestampUltimoFrame);
            timestampUltimoFrame = timestamp;
        });
    }

   
    private void terminarCombate() {
        ticksFinPartida = 0;
        detenerPartida();
        pedirInicialesYGuardar();
        if (alTerminarCombate != null) {alTerminarCombate.ejecutar();}
    }

}
