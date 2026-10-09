package Vista;

import Modelo.Juego;
import Modelo.Peleador;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelJuego extends JPanel {

   
    private AnimacionPeleador animacionP1; // Animaciones de los personajes y fondo dinámico
    private AnimacionPeleador animacionP2;
    private Image imagenFondoDinamico;
    private Juego modeloJuego; // Referencia al modelo del juego

    private final Image imagenFondoJuego = new ImageIcon("assets/Backgrounds/City4.png").getImage();

    private final JButton btnVolver;

    public PanelJuego() {
        setLayout(null);
        setFocusable(true); // es importante para que el panel pueda recibir eventos de teclado

        btnVolver = new JButton("Volver al Menu");
        btnVolver.setBounds(10, 10, 150, 30);
        add(btnVolver);
    }

    public void prepararGraficos(String p1, String p2, String escenario) {
        // 1. Cargar las animaciones (movimiento y salto de cada personaje)
        this.animacionP1 = new AnimacionPeleador(p1);
        this.animacionP2 = new AnimacionPeleador(p2);

        // 2. Cargar fondo
        this.imagenFondoDinamico = new ImageIcon("assets/Backgrounds/" + escenario + ".png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondoDinamico != null){
            g.drawImage(imagenFondoDinamico, 0, 0, getWidth(), getHeight(), this);
        } else if (imagenFondoJuego != null) {
            g.drawImage(imagenFondoJuego, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.BLACK); //se agrega solamente por si no aparece mi increible imagen de escenario
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        if (modeloJuego != null) {
            Peleador personaje1 = modeloJuego.getJugador1();
            Peleador personaje2 = modeloJuego.getJugador2();

            //aca dibujamos a los personajes en sus posiciones actuales
            if (personaje1 != null && animacionP1 != null) {
                BufferedImage spriteActualP1 = animacionP1.getSpriteActual(personaje1);
                int posX1 = personaje1.getPosicionX();
                int posY1 = personaje1.getPosicionY();
                int ancho1 = personaje1.ancho();
                int alto1 = personaje1.alto();

                if (personaje1.isMirandoDerecha()){
                    g.drawImage(spriteActualP1, posX1, posY1, ancho1, alto1, null); // Dibuja el sprite normalmente
                } else {
                    // Dibuja el sprite volteado horizontalmente
                    g.drawImage(spriteActualP1, posX1 + ancho1, posY1, -ancho1, alto1, null);
                }
            }

            // Dibujar al personaje 2
            if (personaje2 != null && animacionP2 != null) {
                BufferedImage spriteActualP2 = animacionP2.getSpriteActual(personaje2);
                int posX2 = personaje2.getPosicionX();
                int posY2 = personaje2.getPosicionY();
                int ancho2 = personaje2.ancho();
                int alto2 = personaje2.alto();

                if (personaje2.isMirandoDerecha()){
                    g.drawImage(spriteActualP2, posX2, posY2, ancho2, alto2, null); // Dibuja el sprite normalmente
                } else {
                    // Dibuja el sprite volteado horizontalmente
                    g.drawImage(spriteActualP2, posX2 + ancho2, posY2, -ancho2, alto2, null);
                }
            }
        }
        java.awt.Toolkit.getDefaultToolkit().sync();
    }

    public void setModeloJuego(Juego modeloJuego) { this.modeloJuego = modeloJuego; }

    public JButton getBtnVolver() { return btnVolver; }
}