package Controlador;

import Modelo.ActionType;
import Modelo.Escenario;
import Modelo.Juego;
import Modelo.Peleador;
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
        vista.getPantallaJugar().requestFocusInWindow();

        vista.getPantallaJugar().revalidate();
        vista.getPantallaJugar().repaint();
        vista.getPantallaJugar().requestFocusInWindow();

        gameLoop.start(); // Inicializo el motor del juego
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

    private void procesarAtaque() {
        Peleador p1 = modeloJuego.getJugador1();
        Peleador p2 = modeloJuego.getJugador2();
        if (p1 == null || p2 == null) return;

        atacarSegunTeclas(p1, p2, teclado.isP1Golpe(), teclado.isP1Patada(), teclado.isP1Especial());
        atacarSegunTeclas(p2, p1, teclado.isP2Golpe(), teclado.isP2Patada(), teclado.isP2Especial());
    }

    // Traduce las teclas apretadas al tipo de ataque
    private void atacarSegunTeclas(Peleador atacante, Peleador rival, boolean golpe, boolean patada, boolean especial) {
        if (golpe) {
            atacante.ejecutarAtaque(ActionType.PUNCH, rival);
        } else if (patada) {
            atacante.ejecutarAtaque(ActionType.KICK, rival);
        } else if (especial) {
            atacante.ejecutarAtaque(ActionType.SPECIAL, rival);
        }
    }

    private void configurarGameLoop() {
        gameLoop = new Timer(16, e -> {

            //detectar si se presiona ESC
            if(teclado.isJuegoPausadoPulsado()){
                alternarPausa();;
            }

            //si esta pausado no se procesa ni fisica ni ataques 
            if(pausado){
                if(vista.getPanelPausa().isEscapePresionado()){
                    alternarPausa();
                }
                return;
            }

            Peleador p1 = modeloJuego.getJugador1();
            Peleador p2 = modeloJuego.getJugador2();

            //NUEVO 
            //COLISION DE LOS PERSONAJES

            int cuerpoAncho = 150;
            int offsetP1 = (p1.ancho() - cuerpoAncho) / 2;
            int offsetP2 = (p2.ancho() - cuerpoAncho) / 2;

            java.awt.Rectangle caja1 = new java.awt.Rectangle(p1.getPosicionX() + offsetP1, p1.getPosicionY(), cuerpoAncho, p1.alto());
            java.awt.Rectangle caja2 = new java.awt.Rectangle(p2.getPosicionX() + offsetP2, p2.getPosicionY(), cuerpoAncho, p2.alto());

            if (p1 != null && p2 != null && caja1.intersects(caja2)) {
                int centroP1 = p1.getPosicionX() + p1.ancho() * 2/10;
                int centroP2 = p2.getPosicionX() + p2.ancho() * 2/10; 

                if (centroP1<centroP2) {
                    p1.setPosicionX(p1.getPosicionX() - 1);
                    p2.setPosicionX(p2.getPosicionX() + 1);
                }else{
                    p1.setPosicionX(p1.getPosicionX() + 1);
                    p2.setPosicionX(p2.getPosicionX() - 1);
                }
            }
            //NUEVO
                        
            // Solo se acepta movimiento y ataque mientras la partida siga
            if (modeloJuego.hayGanador()) {
                ticksFinPartida++;
                if (ticksFinPartida >= TICKS_ESPERA_FIN) {
                    ticksFinPartida = 0;

                    //CAMBIOS DETENEMOS Y MOSTRAMOS OPCIONES EN LUGAR DE IR AL MENU PRINCIPAL
                    detenerPartida();
                    pedirInicialesYGuardar();
                    mostrarOpcionesFinPartida();
                    return;
                }
            } else {
                // Si la partida sigue activa, procesamos el movimiento y los ataques con normalidad
                procesarMovimiento();
                procesarAtaque();
            }

            if(p1 != null) p1.aplicarFisica();
            if(p2 != null) p2.aplicarFisica();
            if(p1 != null) p1.actualizarEnfriamiento();
            if(p2 != null) p2.actualizarEnfriamiento();
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


            //El modelo avanza el reloj y avisa a la vista (el panel se redibuja solo, es el Observer)
            long timestamp = System.nanoTime();
            modeloJuego.avanzarTick(timestamp - timestampUltimoFrame);
            timestampUltimoFrame = timestamp;
        });
    }

    private void procesarMovimiento() {
    // JUGADOR 1 (wasd)
    Peleador p1 = modeloJuego.getJugador1();
    if (p1 != null) {
        // S mantenida: guardia. Al soltarla, vuelve a quieto
        if (teclado.isP1Abajo()) {
            p1.bloquear();
        } else {
            p1.soltarBloqueo();
        }
        if (teclado.isP1Arriba()) {
            p1.saltar();
        }
        //si aprieta izquierda y derecha a la vez se cancelan y no se mueve
        int direccionP1 = (teclado.isP1Derecha() ? 1 : 0) - (teclado.isP1Izquierda() ? 1 : 0);
        if (direccionP1 != 0) {
            p1.caminar(direccionP1);
        }
    }

    // JUGADOR 2 (Flechas)
    Peleador p2 = modeloJuego.getJugador2();
    if (p2 != null) {
        // Flecha abajo mantenida: guardia. Al soltarla, vuelve a quieto
        if (teclado.isP2Abajo()) {
            p2.bloquear();
        } else {
            p2.soltarBloqueo();
        }
        if (teclado.isP2Arriba()) {
            p2.saltar();
        }
        // direccion neta: si aprieta izquierda y derecha a la vez se cancelan y no se mueve
        int direccionP2 = (teclado.isP2Derecha() ? 1 : 0) - (teclado.isP2Izquierda() ? 1 : 0);
        if (direccionP2 != 0) {
            p2.caminar(direccionP2);
        }
    }
}

private void mostrarOpcionesFinPartida() {
    String[] opciones = {"Revancha", "Menú Principal"};
    int seleccion = javax.swing.JOptionPane.showOptionDialog(
        vista,
                "¿Qué deseas hacer a continuación?",
                "Fin del Combate",
                javax.swing.JOptionPane.DEFAULT_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
    );

    if (seleccion == 0) {
        //REVANCHA
        reiniciarRevancha();
    } else {
     java.awt.CardLayout botones = vista.getGestorBotones();
        javax.swing.JPanel contenedor = vista.getPanelContenedor();
        botones.show(contenedor, "BOTON_MENU");   
    }
}

private void reiniciarRevancha(){
    if (modeloJuego.getJugador1() != null && modeloJuego.getJugador2() != null && modeloJuego.getEscenarioActual() != null){
        String p1 = modeloJuego.getJugador1().getNombre();
            String p2 = modeloJuego.getJugador2().getNombre();
            String escenario = modeloJuego.getEscenarioActual().getNombre();
            
            int ancho = vista.getWidth();
            int alto = vista.getHeight();

            // Reiniciamos el modelo y los gráficos con los mismos parámetros
            modeloJuego.inicializarJuego(p1, p2, escenario, ancho, alto);
            vista.prepararGraficosCombate(p1, p2, escenario);
            
            java.awt.CardLayout botones = vista.getGestorBotones();
            javax.swing.JPanel contenedor = vista.getPanelContenedor();
            botones.show(contenedor, "BOTON_JUEGO");

            iniciarPartida();
        }
}
}