package Controlador;

import Modelo.Juego; 
import Vista.SeleccionPersonaje;
import Vista.VentanaJuego;
import java.awt.CardLayout;
import javax.swing.JPanel;

public class ControladorMenu {
    
    private final VentanaJuego vista;
    private final Juego modeloJuego; 
    private final ControladorJuego controladorJuego; 

    public ControladorMenu(VentanaJuego vista, Juego modeloJuego, ControladorTeclado teclado) {
        if (vista == null) {
            throw new IllegalArgumentException("La vista no puede ser nula");
        }
        
        this.vista = vista;
        this.modeloJuego = modeloJuego;
        // Instanciamos el controlador del juego pasándole teclado y vista
        this.controladorJuego = new ControladorJuego(vista, modeloJuego, teclado);

        CardLayout botones = vista.getGestorBotones(); 
        JPanel contenedor = vista.getPanelContenedor();

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
            controladorJuego.detenerPartida(); 
            botones.show(contenedor, "BOTON_MENU");
        }); 
        
        vista.getBtnVolverOpciones().addActionListener(evento -> {
            botones.show(contenedor, "BOTON_MENU");
        });
    }

    public void iniciarCombate(){
        System.out.println("inicia combate");
        SeleccionPersonaje seleccionPanel = vista.getPantallaSeleccionPersonaje();
        String personajeP1 = seleccionPanel.getPersonajeP1();
        String personajeP2 = seleccionPanel.getPersonajeP2();
        String escenarioSeleccionado = seleccionPanel.getEscenarioSeleccionado();

        System.out.println("Personaje P1: " + personajeP1 + ", Personaje P2: " + personajeP2 + ", Escenario: " + escenarioSeleccionado);

        int anchoVentana = vista.getWidth();
        int altoVentana = vista.getHeight();

        modeloJuego.inicializarJuego(personajeP1, personajeP2, escenarioSeleccionado, anchoVentana, altoVentana); 
        System.out.println("Juego inicializado con personajes y escenario seleccionados.");
        vista.prepararGraficosCombate(personajeP1, personajeP2, escenarioSeleccionado); 
        System.out.println("graficos cargados");
        
        CardLayout botones = vista.getGestorBotones(); 
        JPanel contenedor = vista.getPanelContenedor();
        botones.show(contenedor, "BOTON_JUEGO");

        controladorJuego.iniciarPartida(); 
    }
}