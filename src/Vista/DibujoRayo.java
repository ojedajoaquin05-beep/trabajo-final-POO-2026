package Vista;

import Modelo.Juego;
import Modelo.Peleador;
import Modelo.Rayo;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.Random;


public class DibujoRayo implements Dibujable {

    private static final Color RAYO_BORDE = new Color(0, 150, 70);
    private static final Color RAYO_CUERPO = new Color(60, 255, 140);
    private static final Color RAYO_CENTRO = new Color(220, 255, 235);

    private static final int TEXTURA_ANCHO = 120;  // la textura se repite a lo largo del rayo
    private static final int TEXTURA_ALTO = 150;
    private static final BufferedImage TEXTURA_RAYO = crearTexturaRayo();
    private static final BufferedImage DESTELLO_IMPACTO = crearDestello(RAYO_CUERPO, Color.WHITE, 140);
    private static final BufferedImage DESTELLO_CHOQUE = crearDestello(new Color(255, 190, 40), new Color(255, 255, 200), 200);

    @Override
    public void dibujar(Graphics g, Juego juego, int anchoPanel, int altoPanel) {
        dibujarRayo(g, juego.getJugador1());
        dibujarRayo(g, juego.getJugador2());
    }

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

    
    private void dibujarRayo(Graphics g, Peleador p) {
        if (p == null) return;
        Rayo rayo = p.getRayo();
        if (rayo == null || !rayo.isActivo()) return;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(rayo.getOrigenX(), rayo.getY());
        if (rayo.getDireccion() < 0) g2.scale(-1, 1); 
        int t = rayo.getTicks();
        int largo = rayo.getLargo();

    
        g2.setStroke(new BasicStroke(4f));
        for (int i = 0; i < 3; i++) {
            int radio = 30 + ((t * 3 + i * 14) % 42);
            g2.setColor(new Color(RAYO_CUERPO.getRed(), RAYO_CUERPO.getGreen(), RAYO_CUERPO.getBlue(), Math.max(0, 200 - (radio - 30) * 4)));
            g2.drawOval(-radio / 2, -radio, radio, radio * 2);
        }

        if (largo > 0) {
            Shape clipAnterior = g2.getClip();
            g2.clipRect(0, -TEXTURA_ALTO, largo, TEXTURA_ALTO * 2);
            int alto = TEXTURA_ALTO + ((t % 6 < 3) ? 12 : 0); 
            int desfase = (t * 18) % TEXTURA_ANCHO;
            for (int x = desfase - TEXTURA_ANCHO; x < largo; x += TEXTURA_ANCHO) {
                g2.drawImage(TEXTURA_RAYO, x, -alto / 2, TEXTURA_ANCHO, alto, null);
            }
            
            Random azar = new Random(t * 31L);
            g2.setColor(new Color(230, 255, 240, 170));
            for (int i = 0; i < 8; i++) {
                int tam = 3 + azar.nextInt(5);
                g2.fillRect(azar.nextInt(largo), azar.nextInt(150) - 75, tam, tam);
            }
            g2.setClip(clipAnterior);

            if (rayo.isChocando() || rayo.isImpactando()) {
                BufferedImage destello = rayo.isChocando() ? DESTELLO_CHOQUE : DESTELLO_IMPACTO;
                int diametro = destello.getWidth() + (t % 4) * 12;
                g2.drawImage(destello, largo - diametro / 2, -diametro / 2, diametro, diametro, null);
            }
        }
        g2.dispose();
    }
}
