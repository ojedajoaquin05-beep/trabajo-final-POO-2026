package Controlador;

import Modelo.ControlJugador;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

// Controlador de entrada: traduce el teclado en el estado de las teclas de cada jugador.
// No mueve ni ataca a nadie: eso lo decide el modelo (Peleador.obedecer)
public class ControladorTeclado {

    // controles de cada jugador (mismo tipo de objeto, distintas teclas)
    private final TeclasJugador jugador1 = new TeclasJugador();
    private final TeclasJugador jugador2 = new TeclasJugador();

    //control de pausa
    private boolean juegoPausadoPulsado;

    // se devuelven como la interfaz del modelo: quien los usa solo puede leerlos
    public ControlJugador getJugador1() { return jugador1; }
    public ControlJugador getJugador2() { return jugador2; }

    public boolean isJuegoPausadoPulsado(){
        boolean estado = juegoPausadoPulsado;
        juegoPausadoPulsado = false;
        return estado;
    }

    public void resetearTeclas() {
        jugador1.resetear();
        jugador2.resetear();
    }

    public void configurarTeclas(JPanel panel) {

        //registrar tecla esc
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "Pausa_press");
        panel.getActionMap().put("Pausa_press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                juegoPausadoPulsado = true;
            }
        });

        // JUGADOR 1: WASD (Movimiento) | F, G, H (Combate)
        registrarTeclasJugador(panel, "P1", jugador1, "W", "S", "A", "D", "F", "G", "H");

        // JUGADOR 2: FLECHAS (Movimiento) | J, K, L (Combate)
        registrarTeclasJugador(panel, "P2", jugador2, "UP", "DOWN", "LEFT", "RIGHT", "J", "K", "L");
    }

    // Registra las 7 teclas de un jugador. El mismo metodo sirve para los dos (antes estaba todo escrito dos veces)
    private void registrarTeclasJugador(JPanel panel, String prefijo, TeclasJugador teclas,
            String arriba, String abajo, String izquierda, String derecha, String golpe, String patada, String especial) {
        registrarTecla(panel, arriba, prefijo + "_" + arriba, v -> teclas.setArriba(v));         // salto
        registrarTecla(panel, abajo, prefijo + "_" + abajo, v -> teclas.setAbajo(v));            // guardia
        registrarTecla(panel, izquierda, prefijo + "_" + izquierda, v -> teclas.setIzquierda(v));
        registrarTecla(panel, derecha, prefijo + "_" + derecha, v -> teclas.setDerecha(v));
        registrarTecla(panel, golpe, prefijo + "_" + golpe, v -> teclas.setGolpe(v));
        registrarTecla(panel, patada, prefijo + "_" + patada, v -> teclas.setPatada(v));
        registrarTecla(panel, especial, prefijo + "_" + especial, v -> teclas.setEspecial(v));
    }

    // basicamente lo que hace este metodo es que cuando se presiona la tecla (por ejemplo W),
    // se activa la acción "P1_W_press" y se avisa true: el jugador está intentando moverse hacia arriba.
    // Cuando se suelta la tecla, se activa la acción "P1_W_release" y se avisa false.
    private void registrarTecla(JPanel panel, String tecla, String nombre, Consumer<Boolean> alCambiar) {
    panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(tecla), nombre + "_press");
    panel.getActionMap().put(nombre + "_press", new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) { alCambiar.accept(true); }
    });
    panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released " + tecla), nombre + "_release");
    panel.getActionMap().put(nombre + "_release", new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) { alCambiar.accept(false); }
    }); // este metodo es para no repetir tanto codigo.

}


}
