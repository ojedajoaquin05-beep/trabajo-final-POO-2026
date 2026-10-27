package Controlador;

import Modelo.Juego;
import Modelo.Musica;
import Vista.GraficosCombate;
import Vista.PanelOpciones;
import Vista.SeleccionPersonaje;
import Vista.SonidoJuego;
import Vista.VentanaJuego;
import java.awt.CardLayout;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class ControladorMenu {
    
    private final VentanaJuego vista;
    private final Juego modeloJuego; 
    private final ControladorJuego controladorJuego;
    private String pantallaAlSalirDeOpciones = "BOTON_MENU"; // a las opciones se entra desde el menu o desde la pausa
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
            abrirOpciones("BOTON_MENU", "Volver al Menu");
        });
        
        // Los puntajes se piden al modelo cada vez que se abre la pantalla, asi aparece el ultimo guardado
        vista.getBtnPuntajes().addActionListener(evento -> {
            vista.getPanelPuntajes().mostrarPuntajes(modeloJuego.mejoresPuntajes());
            botones.show(contenedor, "PANTALLA_PUNTAJES");
        });

        vista.getPanelPuntajes().getBtnVolver().addActionListener(evento -> {
            botones.show(contenedor, "BOTON_MENU");
        });

        vista.getBtnSalir().addActionListener(evento -> {
            vista.dispose();
            System.exit(0); 
        });

        // BOTONES DE RETORNOS
        vista.getBtnVolverJugar().addActionListener(evento -> {
            volverAlMenu();
        }); 
        
        // Vuelve a donde se entro: al menu, o a la pausa si la partida esta en curso
        vista.getBtnVolverOpciones().addActionListener(evento -> {
            botones.show(contenedor, pantallaAlSalirDeOpciones);
        });

        // Desde la pausa se puede ajustar el volumen sin perder la partida (el juego sigue pausado)
        vista.getPanelPausa().getBtnConfiguracion().addActionListener(evento -> {
            abrirOpciones("PANTALLA_PAUSA", "Volver a la Pausa");
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


        // El observador de sonido se registra una sola vez (no en cada combate)
        controladorJuego.setAlTerminarCombate(()-> preguntarAlTerminarCombate());
        modeloJuego.agregarObservador(new SonidoJuego(musicaPelea, musicaMenu));
        musicaMenu.setVolumen(0);
        musicaMenu.reproducirLoop();
        cargarConfiguracion(); // va al final: los listeners de arriba aplican el volumen guardado a la musica
    }

    // Pide al modelo la configuracion guardada y la pone en los controles de opciones
    private void cargarConfiguracion() {
        PanelOpciones.getSldVolumenMusica().setValue(modeloJuego.leerVolumen());
        PanelOpciones.getSpnSeleccionMusica().setValue(modeloJuego.leerMusica());
        PanelOpciones.getCkbMutearMusica().setSelected(modeloJuego.leerMute());
    }

    // Muestra las opciones y recuerda a que pantalla volver
    private void abrirOpciones(String pantallaDeRegreso, String textoBotonVolver) {
        pantallaAlSalirDeOpciones = pantallaDeRegreso;
        vista.getPanelOpciones().setTextoVolver(textoBotonVolver);
        vista.getGestorBotones().show(vista.getPanelContenedor(), "BOTON_OPCIONES");
    }

    private void preguntarAlTerminarCombate() {
        String[] opciones = {"Revancha", "Menú Principal"};
        int seleccion = JOptionPane.showOptionDialog(vista, "¿Qué deseas hacer a continuación?", "Fin del Combate",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (seleccion == 0) {
            iniciarCombate();
        } else {
            volverAlMenu();
        }
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

        // Todo lo que se lee de la ventana se lee aca, en el hilo de eventos
        SeleccionPersonaje seleccionPanel = vista.getPantallaSeleccionPersonaje();
        final String personajeP1 = seleccionPanel.getPersonajeP1();
        final String personajeP2 = seleccionPanel.getPersonajeP2();
        final String escenarioSeleccionado = seleccionPanel.getEscenarioSeleccionado();

        System.out.println("Personaje P1: " + personajeP1 + ", Personaje P2: " + personajeP2 + ", Escenario: " + escenarioSeleccionado);

        final int anchoVentana = vista.getWidth();
        final int altoVentana = vista.getHeight();

        CardLayout botones = vista.getGestorBotones(); 
        JPanel contenedor = vista.getPanelContenedor();

        botones.show(contenedor, "PANTALLA_CARGA");
        new SwingWorker<GraficosCombate, Void>() {

            @Override
            protected GraficosCombate doInBackground() throws Exception { 
                long inicio = System.currentTimeMillis();
                GraficosCombate graficos = vista.cargarGraficosCombate(personajeP1, personajeP2, escenarioSeleccionado);
                long falta = 1500 - (System.currentTimeMillis() - inicio);
                if (falta > 0) Thread.sleep(falta);
                return graficos;
            }

            @Override
            protected void done() { 
                try {
                    GraficosCombate graficos = get(); 
                    modeloJuego.inicializarJuego(personajeP1, personajeP2, escenarioSeleccionado, anchoVentana, altoVentana);
                    System.out.println("Juego inicializado con personajes y escenario seleccionados.");
                    vista.aplicarGraficosCombate(graficos);
                    System.out.println("graficos cargados");
                    botones.show(contenedor, "BOTON_JUEGO");

                    controladorJuego.iniciarPartida();

                    musicaMenu.pausar();

                    int indice = (int)PanelOpciones.getSpnSeleccionMusica().getValue() - 1;
                    if (indice == -1) indice = (int)(Math.random() * 4);
                    musicaPelea[indice].reproducirLoop();
                } catch (InterruptedException | ExecutionException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(vista, "No se pudo cargar el combate");
                    volverAlMenu();
                }
            }
        }.execute();
    }
}