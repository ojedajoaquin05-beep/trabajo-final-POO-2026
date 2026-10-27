package Vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;


public class BarraPersonaje extends JButton {

    private final BufferedImage retrato; 

    public BarraPersonaje(String nombre, Color colorFondo, String rutaRetrato) {
        super(nombre);
        this.retrato = cargarRetrato(rutaRetrato);
        setFont(Tipografias.VIDA.deriveFont(32.0f));
        setBackground(colorFondo);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setContentAreaFilled(false); 
        setOpaque(false);            
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setVerticalAlignment(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 5),
            BorderFactory.createEmptyBorder(0, 0, 25, 0)
        ));
    }

    private static BufferedImage cargarRetrato(String ruta) {
        if (ruta == null) return null;
        try {
            return ImageIO.read(new File(ruta));
        } catch (IOException e) {
            System.err.println("No se pudo cargar el retrato: " + ruta);
            return null; 
        }
    }

    // primero el fondo y el retrato, despues JButton pinta el texto encima
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int ancho = getWidth();
        int alto = getHeight();

        g2.setColor(getBackground());
        g2.fillRect(0, 0, ancho, alto);

        if (retrato != null) {
            // el retrato cubre toda la barra sin deformarse (lo que sobra se recorta), centrado
            double escala = Math.max((double) ancho / retrato.getWidth(), (double) alto / retrato.getHeight());
            int anchoImagen = (int) (retrato.getWidth() * escala);
            int altoImagen = (int) (retrato.getHeight() * escala);
            g2.drawImage(retrato, (ancho - anchoImagen) / 2, (alto - altoImagen) / 2, anchoImagen, altoImagen, null);

            // franja oscura abajo para que el nombre se lea sobre el dibujo
            int franja = alto / 4;
            g2.setPaint(new GradientPaint(0, alto - franja, new Color(0, 0, 0, 0), 0, alto, new Color(0, 0, 0, 200)));
            g2.fillRect(0, alto - franja, ancho, franja);
        }

        if (isEnabled() && getModel().isRollover()) {
            g2.setColor(new Color(255, 255, 255, 40)); // se ilumina al pasar el mouse
            g2.fillRect(0, 0, ancho, alto);
        }

        super.paintComponent(g); 
    }
}
