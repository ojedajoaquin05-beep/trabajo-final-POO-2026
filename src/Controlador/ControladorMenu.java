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

        vista.getBtnJugar().addActionListener(evento -> {
            vista.getPantallaSeleccionPersonaje().reiniciarSeleccion();
            botones.show(contenedor, "PANTALLA_SELECCION");     
        });

        vista.getPantallaSeleccionPersonaje().setAlFinalizarSeleccion(() -> {
            iniciarCombate();
        });

        vista.getPantallaSeleccionPersonaje().getBtnVolver().addActionListener(evento -> {
            vista.getPantallaSeleccionPersonaje().reiniciarSeleccion();
            botones.show(contenedor, "BOTON_MENU");
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

    public void iniciarCombate(){
        CardLayout botones = vista.getGestorBotones(); 
        JPanel contenedor = vista.getPanelContenedor();

        vista.reiniciarTiempo(); // <--- reiniciamos el reloj a 99 
        contadorTicks = 0; // Reiniciar el contador de ticks al iniciar un nuevo combate    
        
        botones.show(contenedor, "BOTON_JUEGO");

        this.teclado.configurarTeclas(vista.getPantallaJugar());
        vista.getPantallaJugar().requestFocusInWindow();
        gameLoop.start(); // inicializo el motor del juego a 60 FPS
    }

    private int contadorTicks = 0; // Contador de ticks del juego

    private void configurarGameLoop() {
        gameLoop = new Timer(16, e -> {

            if (vista.getTiempoRestante() > 0) {
                contadorTicks++;
                if (contadorTicks >= 60) {
                    vista.setTiempoRestante(vista.getTiempoRestante() - 1);
                    contadorTicks = 0;
                }
            }

            if (vista.getTiempoRestante() == 0) {
                evaluarGanadorPorTiempo();
            }
            // aca va ir la fisica del juego

            vista.getPantallaJugar().repaint(); // se redibuja la pantalla
        });
    }

    private void evaluarGanadorPorTiempo() {
        int vidaP1 = 100;
        int vidaP2 = 80;

        if (vidaP1 > vidaP2) {
            vista.setMensajeGanador("Jugador 1 gana por tiempo");
        } else if (vidaP2 > vidaP1) {
            vista.setMensajeGanador("Jugador 2 gana por tiempo");
        } else {
            vista.setMensajeGanador("Empate por tiempo");
        }
    }
}