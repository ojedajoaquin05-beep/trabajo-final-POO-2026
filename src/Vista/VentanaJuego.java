package Vista;

import Modelo.Juego;
import Modelo.Peleador;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class VentanaJuego extends JFrame {

    private final CardLayout gestorBotones;
    private final JPanel panelContenedor; //lo utilizo para distribuir los botones
    private JPanel pantallaJugar;
    private SeleccionPersonaje pantallaSeleccionPersonaje;

    private int tiempoRestante = 10;
    private String mensajeGanador = "";
    private BufferedImage[] spritesP1; // Arreglos de sprites para los personajes y fondo dinámico
    private BufferedImage[] spritesP2;
    private Image imagenFondoDinamico;
    private Juego modeloJuego; // Referencia al modelo del juego

    private BufferedImage spriteQuieto; // Acá guardamos solo 1 sprite recortado
    private final Image imagenFondoJuego = new ImageIcon("assets/Backgrounds/City4.png").getImage();

    //botones en menu
    private JButton btnJugar, btnOpciones, btnSalir;
    
    //botones de retorno
    private JButton btnVolverJugar, btnVolverOpciones;

    //etiqueta titulo menu opciones
    private JLabel lblTituloVentana;
    
    //controles deslizantes para volumen musica y fx, y sus respectivas etiquetas
    private JSlider sldVolumenMusica, sldVolumenFX;
    private JLabel lblVolumenMusica, lblVolumenFX;
    
    public VentanaJuego() {
        setTitle("Plataformero de Peleas");
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gestorBotones = new CardLayout();
        panelContenedor = new JPanel(gestorBotones);
 
        JPanel menuPrincipal = crearPanelMenu();
        crearPanelJugar(); //inicializo el panel de juego
        pantallaSeleccionPersonaje = new SeleccionPersonaje(); //inicializo el panel de seleccion de personaje
        JPanel pantallaOpciones = crearPanelOpciones();

        panelContenedor.add(menuPrincipal, "BOTON_MENU");
        panelContenedor.add(pantallaSeleccionPersonaje, "PANTALLA_SELECCION");
        panelContenedor.add(pantallaJugar,"BOTON_JUEGO");
        panelContenedor.add(pantallaOpciones, "BOTON_OPCIONES");

        add(panelContenedor);
    }

    private JPanel crearPanelMenu() {
        JPanel panel = new JPanel() {
            private final Image imagen = new ImageIcon("referencias/Imagen_Juego.jpg").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(imagen != null){
                    g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        panel.setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();
        // se asignan los valores para el GridLayout (filas, columnas, espacio horizontal, espacio vertical)
        panelBotones.setLayout(new GridLayout(3, 1, 0, 20));
        panelBotones.setOpaque(false); // Fondo transparente para ver la imagen de fondo

        btnJugar = disenoBoton("JUGAR", new Color(139, 195, 74));
        btnOpciones = disenoBoton("CONFIGURACIÓN", new Color(255, 193, 7));
        btnSalir = disenoBoton("SALIR", new Color(244, 67, 54));

        panelBotones.add(btnJugar);
        panelBotones.add(btnOpciones);
        panelBotones.add(btnSalir);

        JPanel panelMargenDerecho = new JPanel(new GridBagLayout());
        panelMargenDerecho.setOpaque(false);
        panelMargenDerecho.setBorder(BorderFactory.createEmptyBorder(250, 0, 0, 100));
        panelMargenDerecho.add(panelBotones);
        
        panel.add(panelMargenDerecho, BorderLayout.EAST);
        return panel;
    }

    private JButton disenoBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Comic Sans MS", Font.BOLD, 40)); // uso font.BOLD para que el texto sea más visible 
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE); 
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setOpaque(true);
        boton.setContentAreaFilled(true); 
        
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 5),
            BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
        return boton;
    }

    private void crearPanelJugar() {
        pantallaJugar = new JPanel() {
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
                    if (personaje1 != null && spritesP1 != null && spritesP1.length > 0) {
                        BufferedImage spriteActualP1 = spritesP1[personaje1.getFrameActual() % spritesP1.length];
                        int posX1 = personaje1.getPosicionX();
                        int posY1 = personaje1.getPosicionY();
                        int ancho1 = spriteActualP1.getWidth();
                        int alto1 = spriteActualP1.getHeight();

                        if (personaje1.isMirandoDerecha()){
                            g.drawImage(spriteActualP1, posX1, posY1, ancho1, alto1, null); // Dibuja el sprite normalmente
                        } else {
                            // Dibuja el sprite volteado horizontalmente
                            g.drawImage(spriteActualP1, posX1 + ancho1, posY1, -ancho1, alto1, null);
                        }
                    }

                    // Dibujar al personaje 2
                    if (personaje2 != null && spritesP2 != null && spritesP2.length > 0) {
                        BufferedImage spriteActualP2 = spritesP2[personaje2.getFrameActual() % spritesP2.length];
                        int posX2 = personaje2.getPosicionX();
                        int posY2 = personaje2.getPosicionY();
                        int ancho2 = spriteActualP2.getWidth();
                        int alto2 = spriteActualP2.getHeight();

                        if (personaje2.isMirandoDerecha()){
                            g.drawImage(spriteActualP2, posX2, posY2, ancho2, alto2, null); // Dibuja el sprite normalmente
                        } else {
                            // Dibuja el sprite volteado horizontalmente
                            g.drawImage(spriteActualP2, posX2 + ancho2/2, posY2, -ancho2, alto2, null);
                        }
                    }
                }

                // dibujar contador de tiempo
                g.setFont(new Font("arial", Font.BOLD, 48));
                g.setColor(tiempoRestante <= 10 ? Color.RED : Color.YELLOW); // cambia a rojo si quedan 10 segundos o menos
                String textoTiempo = String.valueOf(tiempoRestante);
                int anchoTexto = g.getFontMetrics().stringWidth(textoTiempo);
                g.drawString(textoTiempo, (getWidth() / 2 - anchoTexto / 2), 60);

                // dibujar mensaje de ganador si existe
                if (!mensajeGanador.isEmpty()) {
                    g.setFont(new Font("arial", Font.BOLD, 60));
                    g.setColor(new Color(0, 0, 0, 100));
                    g.fillRect(0, (getHeight() / 2) - 80, getWidth(), 120); // fondo semitransparente
                   g.setColor(Color. YELLOW);
                   
                    int anchoMensaje = g.getFontMetrics().stringWidth(mensajeGanador);
                    g.drawString(mensajeGanador, (getWidth() / 2 - anchoMensaje / 2), getHeight() / 2);
                }


                // Aquí dibujaremos al luchador cuando conectemos el modelo
                
                java.awt.Toolkit.getDefaultToolkit().sync();
            }
        };

        pantallaJugar.setLayout(null);
        pantallaJugar.setFocusable(true); // es importante para que el panel pueda recibir eventos de teclado

        btnVolverJugar = new JButton("Volver al Menu");
        btnVolverJugar.setBounds(10, 10, 150, 30);
        pantallaJugar.add(btnVolverJugar);
    }

    private JPanel crearPanelOpciones() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);

        btnVolverOpciones = new JButton("Volver al Menu");
        lblTituloVentana = new JLabel("PANTALLA DE OPCIONES");
        lblTituloVentana.setForeground(Color.WHITE);
        lblVolumenMusica = new JLabel("VOLUMEN MUSICA");
        lblVolumenMusica.setForeground(Color.WHITE);
        lblVolumenFX = new JLabel("VOLUMEN FX");
        lblVolumenFX.setForeground(Color.WHITE);
        sldVolumenMusica = new JSlider(0, 100);
        sldVolumenFX = new JSlider(0, 100);

        panel.add(lblTituloVentana);
        panel.add(btnVolverOpciones);
        panel.add(lblVolumenMusica);
        panel.add(sldVolumenMusica);
        panel.add(lblVolumenFX);
        panel.add(sldVolumenFX);
        return panel;
    }

    public void prepararGraficosCombate(String p1, String p2, String escenario) {
    String rutaP1 = "referencias/" + p1.toLowerCase() + "_movimiento.png";
    String rutaP2 = "referencias/" + p2.toLowerCase() + "_movimiento.png";

    // 1. Comprobar si el archivo físico existe en el disco
    java.io.File archivoP1 = new java.io.File(rutaP1);
    System.out.println(">>> Buscando P1 en: " + archivoP1.getAbsolutePath()); // eliminar. esto de aca era para encontrar un error.
    System.out.println(">>> ¿Existe el archivo P1?: " + archivoP1.exists());

    // 2. Cargar los sprites
    this.spritesP1 = GestorSprites.recortarMatriz(rutaP1, 2, 2);
    this.spritesP2 = GestorSprites.recortarMatriz(rutaP2, 2, 2);

    // 3. Cargar fondo
    this.imagenFondoDinamico = new ImageIcon("assets/Backgrounds/" + escenario + ".png").getImage();
}

    public JButton getBtnJugar() { return btnJugar; }
    public JButton getBtnOpciones() { return btnOpciones; }
    public JButton getBtnSalir() { return btnSalir; }
    public JButton getBtnVolverJugar() { return btnVolverJugar; }
    public JButton getBtnVolverOpciones() { return btnVolverOpciones; }

    public CardLayout getGestorBotones() { return gestorBotones; }
    public JPanel getPanelContenedor() { return panelContenedor; }
    public JPanel getPantallaJugar() { return pantallaJugar; } 
    public SeleccionPersonaje getPantallaSeleccionPersonaje() { return pantallaSeleccionPersonaje; }

    public int getTiempoRestante() { return tiempoRestante; }
    public void setTiempoRestante(int tiempoRestante) { this.tiempoRestante = tiempoRestante; }

    public String getMensajeGanador() { return mensajeGanador; }
    public void setMensajeGanador(String mensajeGanador) { this.mensajeGanador = mensajeGanador; }
    public BufferedImage[] getSpritesP1() { return spritesP1; }
    public BufferedImage[] getSpritesP2() { return spritesP2; }
    public void setModeloJuego(Juego modeloJuego) {this.modeloJuego = modeloJuego;}
    public void reiniciarTiempo() {
        this.tiempoRestante = 10;
        this.mensajeGanador = "";
    }

    public void mostrarVentana() {
        setVisible(true);
    }
}