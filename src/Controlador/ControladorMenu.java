package Controlador;

import Modelo.ConfiguracionDAO;
import Modelo.Juego;
import Modelo.Musica;
import Vista.SeleccionPersonaje;
import Vista.SonidoJuego;
import Vista.VentanaJuego;
import Vista.PanelOpciones;
import java.awt.CardLayout;
import javax.swing.JPanel;

public class ControladorMenu {
    
    private final VentanaJuego vista;
    private final Juego modeloJuego; 
    private final ControladorJuego controladorJuego;
    private Musica musicaMenu = new Musica("assets/audio/MenuPrincipal.wav");
    private Musica[] musicaPelea = {
    		new Musica("assets/audio/Pelea1.wav"),
    		new Musica("assets/audio/Pelea2.wav"),
    		new Musica("assets/audio/Pelea3.wav"),
    		new Musica("assets/audio/Pelea4.wav"),
    };

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
            volverAlMenu();
        }); 
        
        vista.getBtnVolverOpciones().addActionListener(evento -> {
            botones.show(contenedor, "BOTON_MENU");
        });

        //gestion de los botones de la pantalla pausa 
        vista.getPanelPausa().getBtnReanudar().addActionListener(evento -> {
            controladorJuego.alternarPausa();
        });

        vista.getPanelPausa().getBtnVolverMenu().addActionListener(evento -> {
            volverAlMenu();
        });
        
        PanelOpciones.getSldVolumenMusica().addChangeListener(evento -> {
        	int vol = PanelOpciones.getCkbMutearMusica().isSelected() ? 0 : PanelOpciones.getSldVolumenMusica().getValue();
        	musicaMenu.setVolumen(vol);	// Aplica esta configuración tanto a la música del menú
        	for (Musica m : musicaPelea) m.setVolumen(vol);		// como a la de la pelea
        	modeloJuego.guardarVolumen(vol);
        });
        
        PanelOpciones.getCkbMutearMusica().addChangeListener(evento -> {
        	int vol = PanelOpciones.getCkbMutearMusica().isSelected() ? 0 : PanelOpciones.getSldVolumenMusica().getValue();
        	musicaMenu.setVolumen(vol);	// Aplica esta configuración tanto a la música del menú
        	for (Musica m : musicaPelea) m.setVolumen(vol);		// como a la de la pelea
        	modeloJuego.guardarMute(PanelOpciones.getCkbMutearMusica().isSelected());
        });
        
        PanelOpciones.getSpnSeleccionMusica().addChangeListener(evento -> {
        	int musica = (int)PanelOpciones.getSpnSeleccionMusica().getValue();
        	modeloJuego.guardarMusica(musica);
        });

        // NUEVO: Acción para volver a la pantalla de selección de personajes desde la pausa
        vista.getPanelPausa().getBtnSeleccionPersonaje().addActionListener(evento -> {
            controladorJuego.detenerPartida(); // Detiene el motor del juego actual
            for (Musica i : musicaPelea) {
            	i.detener();					// Detiene la música de la pelea, se hace en loop para probar todas las disponibles
            }
            musicaMenu.reproducirLoop();      // Restaura la música del menú principal
            vista.getPantallaSeleccionPersonaje().reiniciarSeleccion(); // Resetea las selecciones previas
            vista.getGestorBotones().show(vista.getPanelContenedor(), "PANTALLA_SELECCION"); // Muestra la pantalla de selección
        });

        vista.getPanelPausa().getBtnVolverMenu().addActionListener(evento -> {
            volverAlMenu();
        });

        // El observador de sonido se registra una sola vez (no en cada combate)
        modeloJuego.agregarObservador(new SonidoJuego(musicaPelea, musicaMenu));
        musicaMenu.setVolumen(0);
        musicaMenu.reproducirLoop();
    }

    // Vuelve al menu principal desde cualquier parte del combate: corta la partida y cambia la musica
    private void volverAlMenu() {
        controladorJuego.detenerPartida();
        for (Musica m : musicaPelea) {
        	m.detener();					// Detiene la música de la pelea
        }
        musicaMenu.reproducirLoop();
        vista.getGestorBotones().show(vista.getPanelContenedor(), "BOTON_MENU");
    }

    public void iniciarCombate(){
        System.out.println("inicia combate");

        controladorJuego.detenerPartida();

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
        
        // Detiene la música del menú principal y reproduce la seleccionada para la pelea 
        musicaMenu.pausar();
        
        int indice = (int)PanelOpciones.getSpnSeleccionMusica().getValue() - 1;
        if (indice == -1) indice = (int)(Math.random() * 4);	// Si el índice es -1 quiere decir que se seleccionó la opción 0, en ese caso elegir un tema al azar
        musicaPelea[indice].reproducirLoop();
    }
}