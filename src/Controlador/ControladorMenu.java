package Controlador;

import Modelo.Juego; 
import Vista.VentanaJuego;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ControladorMenu {
    
    private final VentanaJuego vista;
    private final Juego modeloJuego; 
    private final ControladorTeclado teclado; 
    
    private Timer gameLoop;

    public ControladorMenu(VentanaJuego vista, Juego modeloJuego, ControladorTeclado teclado) {
        if (vista == null) {
            throw new IllegalArgumentException("La vista no puede ser nula");
        }
        
        this.vista = vista;
        this.modeloJuego = modeloJuego;
        this.teclado = teclado; 

        CardLayout botones = vista.getGestorBotones(); 
        JPanel contenedor = vista.getPanelContenedor();

        configurarGameLoop();

        vista.getBtnJugar().addActionListener(evento -> {botones.show(contenedor, "BOTON_JUEGO"); 
            
            this.teclado.configurarTeclas(vista.getPantallaJugar());
            
            vista.getPantallaJugar().requestFocusInWindow(); 
            
            gameLoop.start(); // inicializo el motor del juego a 60 FPS
        });

        vista.getBtnOpciones().addActionListener(evento -> {
            botones.show(contenedor, "BOTON_OPCIONES");
        });
        
        vista.getBtnSalir().addActionListener(evento -> {
            vista.dispose();
            System.exit(0); 
        });

        // BOTONES DE RETORNOS
        vista.getBtnVolverJugar().addActionListener(evento -> {
            gameLoop.stop(); 
            botones.show(contenedor, "BOTON_MENU");
        }); 
        
        vista.getBtnVolverOpciones().addActionListener(evento -> {
            botones.show(contenedor, "BOTON_MENU");
        });
    }

    private void configurarGameLoop() {
        gameLoop = new Timer(16, e -> {
            // aca va ir la fisica del juego

            vista.getPantallaJugar().repaint(); // se redibuja la pantalla
        });
    }
}