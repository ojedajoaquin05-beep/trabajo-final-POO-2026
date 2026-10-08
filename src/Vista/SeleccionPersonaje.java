package Vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
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
    private Runnable alFinalizarSeleccion; // Variable para almacenar la acción a ejecutar al finalizar la selección

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

    public void setAlFinalizarSeleccion(Runnable accion) {
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

        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 10, 10));
        panelBotones.setOpaque(false);
        //panelBotones.setBackground(Color.DARK_GRAY);//
        panelBotones.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 80, 40, 80));

        // Crear botones para los personajes
        JButton btnCiegoButton = new JButton("Personaje 1");
        JButton btnPeleador2Button = new JButton("Personaje 2");
        /*JButton btnPersonaje3 = new JButton("Personaje 3");
        JButton btnPersonaje4 = new JButton("Personaje 4");
        JButton btnPersonaje5 = new JButton("Personaje 5");
        JButton btnPersonaje6 = new JButton("Personaje 6");*/

        // Agregar ActionListener a los botones de personajes
        btnCiegoButton.addActionListener(e -> seleccionarPersonaje("ciego"));
        btnPeleador2Button.addActionListener(e -> seleccionarPersonaje("peleador2"));
        /*btnPersonaje3.addActionListener(e -> seleccionarPersonaje("Personaje 3"));
        btnPersonaje4.addActionListener(e -> seleccionarPersonaje("Personaje 4"));
        btnPersonaje5.addActionListener(e -> seleccionarPersonaje("Personaje 5"));
        btnPersonaje6.addActionListener(e -> seleccionarPersonaje("Personaje 6"));*/

        // Agregar botones al panel
        panelBotones.add(btnCiegoButton);
        panelBotones.add(btnPeleador2Button);
        /* panelBotones.add(btnPersonaje3);
        panelBotones.add(btnPersonaje4);
        panelBotones.add(btnPersonaje5);
        panelBotones.add(btnPersonaje6); */ 

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

        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 10, 10));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 80, 40, 80));

        // Crear botones para los escenarios
        JButton btnEscenario1Button = new JButton("Escenario 1");
        JButton btnEscenario2Button = new JButton("Escenario 2");
        /*JButton btnEscenario3 = new JButton("Escenario 3");
        JButton btnEscenario4 = new JButton("Escenario 4");
        JButton btnEscenario5 = new JButton("Escenario 5");
        JButton btnEscenario6 = new JButton("Escenario 6");*/

        // Agregar ActionListener a los botones de escenarios
        btnEscenario1Button.addActionListener(e -> seleccionarEscenario("escenario1"));
        btnEscenario2Button.addActionListener(e -> seleccionarEscenario("escenario2"));
       /* btnEscenario3.addActionListener(e -> seleccionarEscenario("escenario3"));
        btnEscenario4.addActionListener(e -> seleccionarEscenario("escenario4"));
        btnEscenario5.addActionListener(e -> seleccionarEscenario("escenario5"));
        btnEscenario6.addActionListener(e -> seleccionarEscenario("escenario6"));*/

        // Agregar botones al panel
        panelBotones.add(btnEscenario1Button);
        panelBotones.add(btnEscenario2Button);
       /* panelBotones.add(btnEscenario3);
        panelBotones.add(btnEscenario4);
        panelBotones.add(btnEscenario5);
        panelBotones.add(btnEscenario6);*/

        panelEscenarios.add(panelBotones, BorderLayout.CENTER);

        return panelEscenarios;
    }

    //metodo para seleccionar personaje
    private void seleccionarPersonaje(String personaje) {
        if (personajeP1 == null) {
            personajeP1 = personaje;
            lblTituloPersonajes.setText("Jugador 2: Selecciona tu personaje");
        } else if (personajeP2 == null) {
            personajeP2 = personaje;
            // Una vez que ambos jugadores han seleccionado sus personajes, se puede pasar a la selección de escenario
            subGestorVistas.show(subContenedor, "PANEL_ESCENARIOS");
        }
    }

    //metodo para seleccionar escenario
    private void seleccionarEscenario(String escenario) {
        this.escenarioSeleccionado = escenario;
        if (alFinalizarSeleccion != null) {
            alFinalizarSeleccion.run(); // Ejecuta la acción definida al finalizar la selección
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

