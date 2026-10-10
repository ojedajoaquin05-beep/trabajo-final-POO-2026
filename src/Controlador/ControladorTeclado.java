package Controlador;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class ControladorTeclado {

    // controles jugador 1
    private boolean p1Arriba, p1Abajo, p1Izquierda, p1Derecha;
    private boolean p1Golpe, p1Patada, p1Especial;

    // controles jugador 2
    private boolean p2Arriba, p2Abajo, p2Izquierda, p2Derecha;
    private boolean p2Golpe, p2Patada, p2Especial;

    // getters jugador 1
    public boolean isP1Arriba() { return p1Arriba; }
    public boolean isP1Abajo() { return p1Abajo; }
    public boolean isP1Izquierda() { return p1Izquierda; }
    public boolean isP1Derecha() { return p1Derecha; }
    public boolean isP1Golpe() { return p1Golpe; }
    public boolean isP1Patada() { return p1Patada; }
    public boolean isP1Especial() { return p1Especial; }

    // getters jugador 2
    public boolean isP2Arriba() { return p2Arriba; }
    public boolean isP2Abajo() { return p2Abajo; }
    public boolean isP2Izquierda() { return p2Izquierda; }
    public boolean isP2Derecha() { return p2Derecha; }
    public boolean isP2Golpe() { return p2Golpe; }
    public boolean isP2Patada() { return p2Patada; }
    public boolean isP2Especial() { return p2Especial; }

    public void configurarTeclas(JPanel panel) {
        
        // JUGADOR 1: WASD (Movimiento) | F, G, H (Combate)
        
        // movimiento hacia arriba W (el salto pero hay que ver para codificarlo y tambien que no se pueda saltar en el aire)
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "P1_W_press");
        panel.getActionMap().put("P1_W_press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p1Arriba = true; }
        });

        // basicamente lo que hace el metodo de arriba es que cuando se presiona la tecla W (es lo mismo con A,S y D), 
        // se activa la acción "P1_W_press" y se establece la variable p1Arriba en true. Esto indica que el jugador 
        // está intentando moverse hacia arriba. Cuando se suelta la tecla W, se activa la acción "P1_W_release" 
        // y se establece la variable en false.
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released W"), "P1_W_release");
        panel.getActionMap().put("P1_W_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p1Arriba = false; }
        });

        // movimiento hacia abajo S (Agacharse)
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "P1_S_press");
        panel.getActionMap().put("P1_S_press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p1Abajo = true; }
        });
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released S"), "P1_S_release");
        panel.getActionMap().put("P1_S_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p1Abajo = false; }
        });

        // movimiento hacia la izquierda A
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "P1_A_press");
        panel.getActionMap().put("P1_A_press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p1Izquierda = true; }
        });
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"), "P1_A_release");
        panel.getActionMap().put("P1_A_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p1Izquierda = false; }
        });

        // movimiento hacia la derecha D
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "P1_D_press");
        panel.getActionMap().put("P1_D_press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p1Derecha = true; }
        });
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"), "P1_D_release");
        panel.getActionMap().put("P1_D_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p1Derecha = false; }
        });

        // Ataque: Golpe (F)
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F"), "P1_F_press");
        panel.getActionMap().put("P1_F_press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p1Golpe = true; }
        });
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released F"), "P1_F_release");
        panel.getActionMap().put("P1_F_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p1Golpe = false; }
        });


        // JUGADOR 2: FLECHAS (Movimiento) | J, K, L (Combate)
        
        // flecha arriba lo mismo que el jugador 1 pero con las flechas
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "P2_UP_press");
        panel.getActionMap().put("P2_UP_press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p2Arriba = true; }
        });
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released UP"), "P2_UP_release");
        panel.getActionMap().put("P2_UP_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p2Arriba = false; }
        });

        // Flecha Abajo
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "P2_DOWN_press");
        panel.getActionMap().put("P2_DOWN_press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p2Abajo = true; }
        });
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released DOWN"), "P2_DOWN_release");
        panel.getActionMap().put("P2_DOWN_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p2Abajo = false; }
        });

        // Flecha Izquierda
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "P2_LEFT_press");
        panel.getActionMap().put("P2_LEFT_press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p2Izquierda = true; }
        });
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released LEFT"), "P2_LEFT_release");
        panel.getActionMap().put("P2_LEFT_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p2Izquierda = false; }
        });

        // Flecha Derecha
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "P2_RIGHT_press");
        panel.getActionMap().put("P2_RIGHT_press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p2Derecha = true; }
        });
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released RIGHT"), "P2_RIGHT_release");
        panel.getActionMap().put("P2_RIGHT_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p2Derecha = false; }
        });

        // Ataque: Golpe (J)
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("J"), "P2_J_press");
        panel.getActionMap().put("P2_J_press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p2Golpe = true; }
        });
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released J"), "P2_J_release");
        panel.getActionMap().put("P2_J_release", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { p2Golpe = false; }
        });

        registrarTecla(panel, "G", "P1_G", v -> p1Patada = v);
        registrarTecla(panel, "H", "P1_H", v -> p1Especial = v); // botones de ataque del jugador 1
        registrarTecla(panel, "K", "P2_K", v -> p2Patada = v);   // botones de ataque del jugador 2
        registrarTecla(panel, "L", "P2_L", v -> p2Especial = v);    
    }

    private void registrarTecla(JPanel panel, String tecla, String nombre, java.util.function.Consumer<Boolean> alCambiar) {
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