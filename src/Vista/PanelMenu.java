package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelMenu extends JPanel {
    private final Image imagen = new ImageIcon("referencias/Imagen_Juego.jpg").getImage();
    private final JButton btnJugar;
    private final JButton btnOpciones;
    private final JButton btnSalir;

    public PanelMenu() {
        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 20));
        panelBotones.setOpaque(false);

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

        add(panelMargenDerecho, BorderLayout.EAST);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }

    private JButton disenoBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
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

    public JButton getBtnJugar() { return btnJugar; }
    public JButton getBtnOpciones() { return btnOpciones; }
    public JButton getBtnSalir() { return btnSalir; }
}
