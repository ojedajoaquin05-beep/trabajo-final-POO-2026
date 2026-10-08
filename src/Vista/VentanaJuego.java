package Vista;

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
        JPanel pantallaOpciones = crearPanelOpciones();

        panelContenedor.add(menuPrincipal, "BOTON_MENU");
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
                if (imagenFondoJuego != null) {
                    g.drawImage(imagenFondoJuego, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(Color.BLACK); //se agrega solamente por si no aparece mi increible imagen de escenario
                    g.fillRect(0, 0, getWidth(), getHeight());
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

    public JButton getBtnJugar() { return btnJugar; }
    public JButton getBtnOpciones() { return btnOpciones; }
    public JButton getBtnSalir() { return btnSalir; }
    public JButton getBtnVolverJugar() { return btnVolverJugar; }
    public JButton getBtnVolverOpciones() { return btnVolverOpciones; }

    public CardLayout getGestorBotones() { return gestorBotones; }
    public JPanel getPanelContenedor() { return panelContenedor; }
    public JPanel getPantallaJugar() { return pantallaJugar; } 

    public void mostrarVentana() {
        setVisible(true);
    }
}