package Vista;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class PanelOpciones extends JPanel {

    private final JButton btnVolver;

    //etiqueta titulo menu opciones
    private final JLabel lblTitulo;

    //controles deslizantes para volumen musica y fx, y sus respectivas etiquetas
    private final JSlider sldVolumenMusica, sldVolumenFX;
    private final JLabel lblVolumenMusica, lblVolumenFX;

    public PanelOpciones() {
        setBackground(Color.DARK_GRAY);

        btnVolver = new JButton("Volver al Menu");
        lblTitulo = new JLabel("PANTALLA DE OPCIONES");
        lblTitulo.setForeground(Color.WHITE);
        lblVolumenMusica = new JLabel("VOLUMEN MUSICA");
        lblVolumenMusica.setForeground(Color.WHITE);
        lblVolumenFX = new JLabel("VOLUMEN FX");
        lblVolumenFX.setForeground(Color.WHITE);
        sldVolumenMusica = new JSlider(0, 100);
        sldVolumenFX = new JSlider(0, 100);

        add(lblTitulo);
        add(btnVolver);
        add(lblVolumenMusica);
        add(sldVolumenMusica);
        add(lblVolumenFX);
        add(sldVolumenFX);
    }

    public JButton getBtnVolver() { return btnVolver; }
    public JSlider getSldVolumenMusica() { return sldVolumenMusica; }
    public JSlider getSldVolumenFX() { return sldVolumenFX; }
}