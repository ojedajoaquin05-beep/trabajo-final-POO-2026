package Vista;

import Modelo.Juego;
import Modelo.Peleador;
import java.awt.Color;
//joaco
import java.awt.Font;

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

            //BARRA DE VIDA (joaco)
            if (personaje1 != null){
                g.setColor(Color.RED);
                g.fillRect(50, 40, 300, 25);
                g.setColor(Color.GREEN);
                int anchoVidaP1 = (int) (300 * ((double) personaje1.getVida() / personaje1.getVidaMaxima()));
                g.fillRect(50, 40, Math.max(0, anchoVidaP1), 25);
            }

            if (personaje2 != null) {
                int posXMarcoP2 = getWidth() - 350;
                g.setColor(Color.RED);
                g.fillRect(posXMarcoP2, 40, 300, 25);
                
                g.setColor(Color.GREEN);
                int anchoVidaP2 = (int) (300 * ((double) personaje2.getVida() / personaje2.getVidaMaxima()));
                int posXVidaP2 = getWidth() - 50 - Math.max(0, anchoVidaP2);
                g.fillRect(posXVidaP2, 40, Math.max(0, anchoVidaP2), 25);
                
                g.setColor(Color.WHITE);
                g.drawRect(posXMarcoP2, 40, 300, 25);
                g.setFont(new Font("Arial", Font.BOLD, 14));
                g.drawString(personaje2.getNombre() + ": " + personaje2.getVida() + " HP", posXMarcoP2, 35);
            }

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

        //TEMPORIZADOR (joaco)
        long tiempoRestanteNanos = modeloJuego.getTiempoRestante();
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.setColor(tiempoRestanteNanos <= 10000000000L ? Color.RED : Color.YELLOW);
            String textoTiempo = String.format("%02.0f", Math.ceil(tiempoRestanteNanos / 1000000000.0));
            int anchoTexto = g.getFontMetrics().stringWidth(textoTiempo);
            g.drawString(textoTiempo, (getWidth() / 2 - anchoTexto / 2), 60);

            //MSJ GANADOR (joaco)
            if (modeloJuego.hayGanador()) {
              String mensaje = modeloJuego.getMensajeGanador();
                g.setFont(new Font("Arial", Font.BOLD, 60));
                g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, (getHeight() / 2) - 80, getWidth(), 120);
                g.setColor(Color.YELLOW);
                int anchoMensaje = g.getFontMetrics().stringWidth(mensaje);
                g.drawString(mensaje, (getWidth() / 2 - anchoMensaje / 2), getHeight() / 2);  
            }

        java.awt.Toolkit.getDefaultToolkit().sync();
    }

    public void setModeloJuego(Juego modeloJuego) { this.modeloJuego = modeloJuego; }

    public JButton getBtnVolver() { return btnVolver; }
}