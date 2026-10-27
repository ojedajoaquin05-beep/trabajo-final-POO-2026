package Vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class SeleccionPersonaje extends JPanel {
    private final CardLayout subGestorVistas;
    private final JPanel subContenedor;
    
    //etapa1: personajes disponibles
    private String personajeP1 = null;
    private String personajeP2 = null;
    private JLabel lblTituloPersonajes ;
    //etapa2: escenarios disponibles
    private String escenarioSeleccionado = null;

    private JButton btnVolver;
    private AlFinalizarSeleccion alFinalizarSeleccion; // Variable para almacenar la acción a ejecutar al finalizar la selección

    public SeleccionPersonaje(){
        
        setLayout(new BorderLayout());
        //inicializo el subContendor y el subGestorVistas para poder cambiar entre los paneles de personajes y escenarios
        subGestorVistas = new CardLayout();
        subContenedor = new JPanel(subGestorVistas);

        JPanel panelPersonajes = crearPanelPersonajes();
        JPanel panelEscenarios = crearPanelEscenarios();

        //agrego ambas sub-pantallas al subContenedor con sus respectivos nombres para poder cambiar entre ellas
        subContenedor.add(panelPersonajes, "PANEL_PERSONAJES");
        subContenedor.add(panelEscenarios, "PANEL_ESCENARIOS");

        add(subContenedor, BorderLayout.CENTER);

        btnVolver = new JButton("Volver al menú");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 16));
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(Color.DARK_GRAY);
        panelInferior.add(btnVolver);
        add(panelInferior, BorderLayout.SOUTH);
    }

    public void setAlFinalizarSeleccion(AlFinalizarSeleccion accion) {
        this.alFinalizarSeleccion = accion;
    }
    //sub-pantalla de selección de personajes 
    private JPanel crearPanelPersonajes() {
        JPanel panelPersonajes = new JPanel(new BorderLayout());
        panelPersonajes.setBackground(Color.DARK_GRAY);

        lblTituloPersonajes = new JLabel("Selecciona tu personaje", SwingConstants.CENTER);
        lblTituloPersonajes.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloPersonajes.setForeground(Color.WHITE);
        lblTituloPersonajes.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelPersonajes.add(lblTituloPersonajes, BorderLayout.NORTH);

        // Una fila de 4 columnas: cada personaje es una barra vertical que ocupa todo el alto
        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 15, 0));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 40, 30, 40));

        // Crear las barras de los personajes
        JButton btnCiego = new BarraPersonaje("CIEGO", new Color(120, 90, 160), "assets/Retratos/ciego.png");
        JButton btnSamurai = new BarraPersonaje("SAMURAI", new Color(200, 60, 60), "assets/Retratos/samurai.png");
        JButton btnRugbier = new BarraPersonaje("RUGBIER", new Color(40, 140, 80), "assets/Retratos/rugbier.png");
        JButton btnBorracho = new BarraPersonaje("BORRACHO", new Color(200, 150, 40), "assets/Retratos/borracho.png");

        // Agregar ActionListener a los botones de personajes
        btnCiego.addActionListener(e -> seleccionarPersonaje("ciego"));
        btnSamurai.addActionListener(e -> seleccionarPersonaje("samurai"));
        btnRugbier.addActionListener(e -> seleccionarPersonaje("rugbier"));
        btnBorracho.addActionListener(e -> seleccionarPersonaje("borracho"));

        // Agregar botones al panel
        panelBotones.add(btnCiego);
        panelBotones.add(btnSamurai);
        panelBotones.add(btnRugbier);
        panelBotones.add(btnBorracho);

        panelPersonajes.add(panelBotones, BorderLayout.CENTER);

        return panelPersonajes;
    }


    //sub-pantalla2: selección de escenarios
    private JPanel crearPanelEscenarios() {
        JPanel panelEscenarios = new JPanel(new BorderLayout());
        panelEscenarios.setBackground(Color.DARK_GRAY);

        JLabel lblTituloEscenarios = new JLabel("Selecciona tu escenario", SwingConstants.CENTER);
        lblTituloEscenarios.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloEscenarios.setForeground(Color.WHITE);
        lblTituloEscenarios.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelEscenarios.add(lblTituloEscenarios, BorderLayout.NORTH);

        // Un boton por escenario, con su fondo de imagen (los datos salen del enum FondoEscenario)
        FondoEscenario[] escenarios = FondoEscenario.values();
        JPanel panelBotones = new JPanel(new GridLayout(1, escenarios.length, 15, 0));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 40, 30, 40));

        for (FondoEscenario escenario : escenarios) {
            JButton btnEscenario = new BarraPersonaje(escenario.getNombre(), Color.DARK_GRAY, escenario.getRutaFondo());
            btnEscenario.addActionListener(e -> seleccionarEscenario(escenario.getClave()));
            panelBotones.add(btnEscenario);
        }

        panelEscenarios.add(panelBotones, BorderLayout.CENTER);

        return panelEscenarios;
    }

    //metodo para seleccionar personaje
    private void seleccionarPersonaje(String personaje) {
        if (personajeP1 == null) {
            personajeP1 = personaje;
            lblTituloPersonajes.setText("Jugador 2: Selecciona tu personaje");
        } else if (personajeP2 == null) {
        	if (personaje.equals(personajeP1)) {		// Bloquea al jugador 2 la posibilidad de elegir el personaje que ya eligió el jugador 1
        		String[] opciones = {"Aceptar"};
                JOptionPane.showOptionDialog(null, "El jugador 1 ya eligió este personaje, por favor seleccioná uno distinto", "Error",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, opciones, opciones[0]);
        	} else {
        		personajeP2 = personaje;
        		// Una vez que ambos jugadores han seleccionado sus personajes, se puede pasar a la selección de escenario
        		subGestorVistas.show(subContenedor, "PANEL_ESCENARIOS");
        	}
        }
    }

    //metodo para seleccionar escenario
    private void seleccionarEscenario(String escenario) {
        this.escenarioSeleccionado = escenario;
        if (alFinalizarSeleccion != null) {
            alFinalizarSeleccion.ejecutar(); // Ejecuta la acción definida al finalizar la selección
        }
        // aca termina el flujo de la seleccion de escenario y personajes
    }

    public void reiniciarSeleccion() {
        personajeP1 = null;
        personajeP2 = null;
        escenarioSeleccionado = null;

        if (lblTituloPersonajes != null) {
            lblTituloPersonajes.setText("Selecciona tu personaje");
        }
        subGestorVistas.show(subContenedor, "PANEL_PERSONAJES");
    }

    public JButton getBtnVolver() {return btnVolver;}
    public String getPersonajeP1() {return personajeP1;}
    public String getPersonajeP2() {return personajeP2;}
    public String getEscenarioSeleccionado() {return escenarioSeleccionado;}
}

