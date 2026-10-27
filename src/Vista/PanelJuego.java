package Vista;

import Modelo.Juego;
import Modelo.ObservadorJuego;
import Modelo.Peleador;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

// Pantalla del combate. Observador del modelo: cada vez que Juego avisa, se repinta.
// Dibuja el fondo y los peleadores; todo lo demas lo delega en una lista de Dibujable (HUD, rayos, reloj, mensaje)
public class PanelJuego extends JPanel implements ObservadorJuego {


    private AnimacionPeleador animacionP1;
    private AnimacionPeleador animacionP2;
    private Image imagenFondoDinamico;
    private Juego modeloJuego;

    private final Image imagenFondoJuego = new ImageIcon("assets/Backgrounds/City4.png").getImage();

    private final JButton btnVolver;

    // Capas que van encima de los peleadores, en el orden en que se dibujan.
    // El panel no sabe que es cada una: solo les pide que se dibujen (polimorfismo)
    private final List<Dibujable> capas = List.of(
            new DibujoRayo(),      // los rayos del especial se dibujan encima de los personajes
            new HudJugador1(),
            new HudJugador2(),
            new RelojPartida(),
            new MensajeGanador());

    public PanelJuego() {

        setLayout(null);
        setFocusable(true); // es importante para que el panel pueda recibir eventos de teclado

        btnVolver = new JButton("Volver al Menu");
        btnVolver.setBounds(10, 10, 150, 30);
        //add(btnVolver);
    }

    // Carga los graficos (se puede llamar desde otro hilo: no toca componentes)
    public static GraficosCombate cargarGraficos(String p1, String p2, String escenario) {
        return new GraficosCombate(
                new AnimacionPeleador(p1),
                new AnimacionPeleador(p2),
                new ImageIcon("assets/Backgrounds/" + escenario + ".png").getImage());
    }

    public void aplicarGraficos(GraficosCombate graficos) {
        this.animacionP1 = graficos.getAnimacionP1();
        this.animacionP2 = graficos.getAnimacionP2();
        this.imagenFondoDinamico = graficos.getFondo();
    }

    public void prepararGraficos(String p1, String p2, String escenario) {
        aplicarGraficos(cargarGraficos(p1, p2, escenario));
    }

    @Override
    public void alCambiarEstadoJuego(Juego juego) {
        // Actualizar la referencia al modelo del juego y repintar el panel
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarFondo(g);

        if (modeloJuego != null) {
            //aca dibujamos a los personajes en sus posiciones actuales
            dibujarPeleador(g, modeloJuego.getJugador1(), animacionP1);
            dibujarPeleador(g, modeloJuego.getJugador2(), animacionP2);

            // HUD, rayos, reloj y mensaje: cada capa sabe dibujarse sola
            for (Dibujable capa : capas) {
                capa.dibujar(g, modeloJuego, getWidth(), getHeight());
            }
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void dibujarFondo(Graphics g) {
        if (imagenFondoDinamico != null){
            g.drawImage(imagenFondoDinamico, 0, 0, getWidth(), getHeight(), this);
        } else if (imagenFondoJuego != null) {
            g.drawImage(imagenFondoJuego, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.BLACK); //se agrega solamente por si no aparece mi increible imagen de escenario
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // Un solo metodo para los dos personajes (antes estaba escrito dos veces)
    private void dibujarPeleador(Graphics g, Peleador personaje, AnimacionPeleador animacion) {
        if (personaje == null || animacion == null) return;
        BufferedImage spriteActual = animacion.getSpriteActual(personaje);
        int posX = personaje.getPosicionX();
        int posY = personaje.getPosicionY();
        int alto = personaje.alto();
        // Se respeta la proporcion de la imagen y se centra en el peleador: un cuadro mas ancho
        // (por ejemplo la estocada del samurai, con la katana entera) se dibuja mas ancho sin deformarse
        int ancho = alto * spriteActual.getWidth() / spriteActual.getHeight();
        int x = posX + (personaje.ancho() - ancho) / 2;

        if (personaje.isMirandoDerecha()){
            g.drawImage(spriteActual, x, posY, ancho, alto, null); // Dibuja el sprite normalmente
        } else {
            // Dibuja el sprite volteado horizontalmente
            g.drawImage(spriteActual, x + ancho, posY, -ancho, alto, null);
        }
    }

    public void setModeloJuego(Juego modeloJuego) { this.modeloJuego = modeloJuego; }

    public JButton getBtnVolver() { return btnVolver; }
}
