package Vista;

import Modelo.Juego;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaJuego extends JFrame {

    private final CardLayout gestorBotones;
    private final JPanel panelContenedor; //lo utilizo para distribuir los botones
    private final PanelMenu panelMenu;
    private final PanelJuego panelJuego;
    private final SeleccionPersonaje pantallaSeleccionPersonaje;
    private final PanelOpciones panelOpciones;

    public VentanaJuego() {
        setTitle("Plataformero de Peleas");
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gestorBotones = new CardLayout();
        panelContenedor = new JPanel(gestorBotones);

        panelMenu = new PanelMenu(); //inicializo el panel de menu principal
        panelJuego = new PanelJuego(); //inicializo el panel de juego
        pantallaSeleccionPersonaje = new SeleccionPersonaje(); //inicializo el panel de seleccion de personaje
        panelOpciones = new PanelOpciones(); //inicializo el panel de opciones

        panelContenedor.add(panelMenu, "BOTON_MENU");
        panelContenedor.add(pantallaSeleccionPersonaje, "PANTALLA_SELECCION");
        panelContenedor.add(panelJuego, "BOTON_JUEGO");
        panelContenedor.add(panelOpciones, "BOTON_OPCIONES");

        add(panelContenedor);
    }

    public void prepararGraficosCombate(String p1, String p2, String escenario) {
        panelJuego.prepararGraficos(p1, p2, escenario);
    }

    // botones (delegan en cada panel)
    public JButton getBtnJugar() { return panelMenu.getBtnJugar(); }
    public JButton getBtnOpciones() { return panelMenu.getBtnOpciones(); }
    public JButton getBtnSalir() { return panelMenu.getBtnSalir(); }
    public JButton getBtnVolverJugar() { return panelJuego.getBtnVolver(); }
    public JButton getBtnVolverOpciones() { return panelOpciones.getBtnVolver(); }

    public CardLayout getGestorBotones() { return gestorBotones; }
    public JPanel getPanelContenedor() { return panelContenedor; }
    public JPanel getPantallaJugar() { return panelJuego; }
    public SeleccionPersonaje getPantallaSeleccionPersonaje() { return pantallaSeleccionPersonaje; }
    public void setModeloJuego(Juego modeloJuego) { panelJuego.setModeloJuego(modeloJuego); }
    
    public void mostrarVentana() {
        setVisible(true);
    }
}