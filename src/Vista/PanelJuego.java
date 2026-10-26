package Vista;

import Modelo.Juego;
import Modelo.ObservadorJuego;
import Modelo.Peleador;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelJuego extends JPanel implements ObservadorJuego {

   
    private AnimacionPeleador animacionP1; 
    private AnimacionPeleador animacionP2;
    private Image imagenFondoDinamico;
    private Juego modeloJuego;

    private final Image imagenFondoJuego = new ImageIcon("assets/Backgrounds/City4.png").getImage();

    private final JButton btnVolver;

    // Las tipografias se cargan UNA vez (leer el .ttf del disco en cada cuadro hacia bajar los fps)
    private static final Font TIPOGRAFIA_VIDA = cargarTipografia("assets/Tipografias/determination.ttf", 24.0f);
    private static final Font TIPOGRAFIA_DISPLAY = cargarTipografia("assets/Tipografias/DSEG7Modern-Bold.ttf", 48.0f);

    private static Font cargarTipografia(String ruta, float tamanio) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(ruta)).deriveFont(tamanio);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la tipografia " + ruta);
            return new Font("Arial", Font.BOLD, (int) tamanio);
        }
    }

    public PanelJuego() {
        
        setLayout(null);
        setFocusable(true); // es importante para que el panel pueda recibir eventos de teclado

        btnVolver = new JButton("Volver al Menu");
        btnVolver.setBounds(10, 10, 150, 30);
        //add(btnVolver);
    }

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
            Font tipografiaVida = TIPOGRAFIA_VIDA; // se carga una sola vez (antes se leia del disco en cada cuadro)
            
            //BARRA DE VIDA (joaco)
            if (personaje1 != null){
                int posXMarcoP1 = 50;
                g.setColor(Color.RED);
                g.fillRect(50, 40, 300, 25);
                g.setColor(Color.GREEN);
                int anchoVidaP1 = (int) (300 * ((double) personaje1.getVida() / personaje1.getVidaMaxima()));
                g.fillRect(50, 40, Math.max(0, anchoVidaP1), 25);

                g.setColor(Color.BLACK);
                g.drawRect(posXMarcoP1, 40, 300, 25);
                g.setFont(tipografiaVida.deriveFont(24.0f));
                g.drawString(personaje1.getNombre().toUpperCase() + " : " + personaje1.getVida() + " HP", posXMarcoP1, 35);

                // PUNTAJE JUGADOR 1
                g.setFont(tipografiaVida.deriveFont(20.0f));
                g.setColor(Color.black);
                g.drawString("SCORE: " + personaje1.getPuntaje(), posXMarcoP1, 85);
                dibujarBarraEspecial(g, personaje1, posXMarcoP1);
            }

            if (personaje2 != null) {
                int posXMarcoP2 = getWidth() - 350;
                g.setColor(Color.RED);
                g.fillRect(posXMarcoP2, 40, 300, 25);
                
                g.setColor(Color.GREEN);
                int anchoVidaP2 = (int) (300 * ((double) personaje2.getVida() / personaje2.getVidaMaxima()));
                int posXVidaP2 = getWidth() - 50 - Math.max(0, anchoVidaP2);
                g.fillRect(posXVidaP2, 40, Math.max(0, anchoVidaP2), 25);
                
                g.setColor(Color.BLACK);
                g.drawRect(posXMarcoP2, 40, 300, 25);
                g.setFont(tipografiaVida.deriveFont(24.0f));
                g.drawString(personaje2.getNombre().toUpperCase() + " : " + personaje2.getVida() + " HP", posXMarcoP2, 35);

                // PUNTAJE JUGADOR 2
                g.setFont(tipografiaVida.deriveFont(20.0f));
                g.setColor(Color.black);
                g.drawString("SCORE: " + personaje2.getPuntaje(), posXMarcoP2, 85);
                dibujarBarraEspecial(g, personaje2, posXMarcoP2);
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

            // Los rayos del especial se dibujan encima de los personajes
            if (personaje1 != null) dibujarRayo(g, personaje1);
            if (personaje2 != null) dibujarRayo(g, personaje2);
        }

        //TEMPORIZADOR (joaco)
        long tiempoRestanteNanos = modeloJuego.getTiempoRestante();
        Font tipografiaDisplay = TIPOGRAFIA_DISPLAY; // idem: cargada una sola vez
            g.setFont(tipografiaDisplay);
            String textoTiempo = String.format("%02.0f", Math.min(99, Math.ceil(tiempoRestanteNanos / 1000000000.0)));
            int anchoTexto = g.getFontMetrics().stringWidth(textoTiempo);
            int x = (getWidth() / 2 - anchoTexto / 2);
            final int margenH = 5, margenV = 7;
            g.setColor(Color.BLACK);
            g.fillRect(x - margenH, 0, anchoTexto + margenH * 2, 60 + margenV * 2);
            g.setColor(tiempoRestanteNanos <= 10000000000L ? Color.RED : Color.YELLOW);
            g.drawString(textoTiempo, (getWidth() / 2 - anchoTexto / 2), 60);

            //MSJ GANADOR (joaco)
            if (modeloJuego.hayGanador()) {
              String mensaje = modeloJuego.getMensajeGanador();
                g.setFont(new Font("Arial", Font.BOLD, 60));
                g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, (getHeight() / 2) - 180, getWidth(), 120);
                g.setColor(Color.YELLOW);
                int anchoMensaje = g.getFontMetrics().stringWidth(mensaje);
                g.drawString(mensaje, (getWidth() / 2 - anchoMensaje / 2), getHeight() / 2 - 100);  
            }

        java.awt.Toolkit.getDefaultToolkit().sync();
    }

    public void setModeloJuego(Juego modeloJuego) { this.modeloJuego = modeloJuego; }

    public JButton getBtnVolver() { return btnVolver; }

    // Barra del ataque especial: celeste mientras se carga, amarilla cuando esta listo
    private void dibujarBarraEspecial(Graphics g, Peleador p, int x) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, 95, 300, 12);
        g.setColor(p.isEspecialListo() ? Color.YELLOW : Color.CYAN);
        g.fillRect(x, 95, (int) (300 * p.getProgresoEspecial()), 12);
        g.setColor(Color.BLACK);
        g.drawRect(x, 95, 300, 12);
    }

    // Colores del rayo: cambialos aca para probar otro color (borde oscuro, cuerpo, centro claro)
    private static final Color RAYO_BORDE = new Color(0, 150, 70);
    private static final Color RAYO_CUERPO = new Color(60, 255, 140);
    private static final Color RAYO_CENTRO = new Color(220, 255, 235);

    // El rayo se dibuja con texturas que se arman UNA sola vez (dibujar degradados en cada cuadro bajaba los fps)
    private static final int TEXTURA_ANCHO = 120;  // la textura se repite a lo largo del rayo
    private static final int TEXTURA_ALTO = 150;
    private static final BufferedImage TEXTURA_RAYO = crearTexturaRayo();
    private static final BufferedImage DESTELLO_IMPACTO = crearDestello(RAYO_CUERPO, Color.WHITE, 140);
    private static final BufferedImage DESTELLO_CHOQUE = crearDestello(new Color(255, 190, 40), new Color(255, 255, 200), 200);

    // Textura del cuerpo: aura, degradado vertical, bandas diagonales y nucleo claro.
    // Se arma de modo que repetida una al lado de otra no se note el corte
    private static BufferedImage crearTexturaRayo() {
        BufferedImage img = new BufferedImage(TEXTURA_ANCHO, TEXTURA_ALTO, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int c = TEXTURA_ALTO / 2;
        // aura
        g2.setPaint(new GradientPaint(0, 0, new Color(RAYO_BORDE.getRed(), RAYO_BORDE.getGreen(), RAYO_BORDE.getBlue(), 0),
                0, c, new Color(RAYO_CUERPO.getRed(), RAYO_CUERPO.getGreen(), RAYO_CUERPO.getBlue(), 150)));
        g2.fillRect(0, 0, TEXTURA_ANCHO, c);
        g2.setPaint(new GradientPaint(0, c, new Color(RAYO_CUERPO.getRed(), RAYO_CUERPO.getGreen(), RAYO_CUERPO.getBlue(), 150),
                0, TEXTURA_ALTO, new Color(RAYO_BORDE.getRed(), RAYO_BORDE.getGreen(), RAYO_BORDE.getBlue(), 0)));
        g2.fillRect(0, c, TEXTURA_ANCHO, c);
        // cuerpo
        g2.setPaint(new GradientPaint(0, c - 34, RAYO_BORDE, 0, c, RAYO_CUERPO));
        g2.fillRect(0, c - 34, TEXTURA_ANCHO, 34);
        g2.setPaint(new GradientPaint(0, c, RAYO_CUERPO, 0, c + 34, RAYO_BORDE));
        g2.fillRect(0, c, TEXTURA_ANCHO, 34);
        // bandas diagonales (cada 60 px, asi la textura encaja al repetirse)
        g2.setColor(new Color(255, 255, 255, 70));
        for (int x = -30; x < TEXTURA_ANCHO + 30; x += 60) {
            Path2D banda = new Path2D.Double();
            banda.moveTo(x, c - 34);
            banda.lineTo(x + 22, c - 34);
            banda.lineTo(x + 2, c + 34);
            banda.lineTo(x - 20, c + 34);
            banda.closePath();
            g2.fill(banda);
        }
        // nucleo claro ondulado (la onda se repite cada 60 px)
        g2.setColor(RAYO_CENTRO);
        Path2D nucleo = new Path2D.Double();
        nucleo.moveTo(0, c - 12);
        for (int x = 0; x <= TEXTURA_ANCHO; x += 4) nucleo.lineTo(x, c - 12 + 3 * Math.sin(x * 2 * Math.PI / 60));
        for (int x = TEXTURA_ANCHO; x >= 0; x -= 4) nucleo.lineTo(x, c + 12 + 3 * Math.sin(x * 2 * Math.PI / 60));
        nucleo.closePath();
        g2.fill(nucleo);
        g2.dispose();
        return img;
    }

    // Destello redondo con degradado radial (punta del rayo al impactar o al chocar)
    private static BufferedImage crearDestello(Color color, Color centro, int diametro) {
        BufferedImage img = new BufferedImage(diametro, diametro, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        float r = diametro / 2f;
        g2.setPaint(new RadialGradientPaint(r, r, r, new float[]{0f, 0.45f, 1f},
                new Color[]{centro, new Color(color.getRed(), color.getGreen(), color.getBlue(), 200),
                            new Color(color.getRed(), color.getGreen(), color.getBlue(), 0)}));
        g2.fillOval(0, 0, diametro, diametro);
        g2.dispose();
        return img;
    }

    // Dibuja el rayo del especial: anillos en las manos, cuerpo con textura que avanza,
    // chispas y la punta que brilla (mas grande y amarilla si choca con otro rayo)
    private void dibujarRayo(Graphics g, Peleador p) {
        Modelo.Rayo rayo = p.getRayo();
        if (rayo == null || !rayo.isActivo()) return;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(rayo.getOrigenX(), rayo.getY());
        if (rayo.getDireccion() < 0) g2.scale(-1, 1); // asi siempre se dibuja "hacia la derecha"
        int t = rayo.getTicks();
        int largo = rayo.getLargo();

        // Anillos de energia en las manos (laten mientras dura el rayo)
        g2.setStroke(new java.awt.BasicStroke(4f));
        for (int i = 0; i < 3; i++) {
            int radio = 30 + ((t * 3 + i * 14) % 42);
            g2.setColor(new Color(RAYO_CUERPO.getRed(), RAYO_CUERPO.getGreen(), RAYO_CUERPO.getBlue(), Math.max(0, 200 - (radio - 30) * 4)));
            g2.drawOval(-radio / 2, -radio, radio, radio * 2);
        }

        if (largo > 0) {
            // Cuerpo: la textura se repite a lo largo del rayo y se corre con el tiempo (efecto de energia que avanza)
            java.awt.Shape clipAnterior = g2.getClip();
            g2.clipRect(0, -TEXTURA_ALTO, largo, TEXTURA_ALTO * 2);
            int alto = TEXTURA_ALTO + ((t % 6 < 3) ? 12 : 0); // el rayo "vibra"
            int desfase = (t * 18) % TEXTURA_ANCHO;
            for (int x = desfase - TEXTURA_ANCHO; x < largo; x += TEXTURA_ANCHO) {
                g2.drawImage(TEXTURA_RAYO, x, -alto / 2, TEXTURA_ANCHO, alto, null);
            }
            // Chispas alrededor del rayo (posiciones que cambian en cada tick)
            java.util.Random azar = new java.util.Random(t * 31L);
            g2.setColor(new Color(230, 255, 240, 170));
            for (int i = 0; i < 8; i++) {
                int tam = 3 + azar.nextInt(5);
                g2.fillRect(azar.nextInt(largo), azar.nextInt(150) - 75, tam, tam);
            }
            g2.setClip(clipAnterior);

            // Punta: destello que late. Si chocan los rayos es mas grande y amarillo
            if (rayo.isChocando() || rayo.isImpactando()) {
                BufferedImage destello = rayo.isChocando() ? DESTELLO_CHOQUE : DESTELLO_IMPACTO;
                int diametro = destello.getWidth() + (t % 4) * 12;
                g2.drawImage(destello, largo - diametro / 2, -diametro / 2, diametro, diametro, null);
            }
        }
        g2.dispose();
    }
}