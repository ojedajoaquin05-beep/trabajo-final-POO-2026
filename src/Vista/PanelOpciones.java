package Vista;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.SpinnerNumberModel;

public class PanelOpciones extends JPanel {

    private final JButton btnVolver;

    //etiqueta titulo menu opciones
    private final JLabel lblTitulo;

    //controles deslizantes para volumen musica y fx, y sus respectivas etiquetas
    private static JSlider sldVolumenMusica, sldVolumenFX;
    private final JLabel lblVolumenMusica, lblVolumenFX, lblSeleccionMusica;
    private static JSpinner spnSeleccionMusica = null;
    
    //checkbox para mutear directamente
    private static JCheckBox ckbMutearMusica;

    public PanelOpciones() {
        setBackground(Color.DARK_GRAY);

        btnVolver = new JButton("Volver al Menu");
        lblTitulo = new JLabel("PANTALLA DE OPCIONES");
        lblTitulo.setForeground(Color.WHITE);
        lblVolumenMusica = new JLabel("VOLUMEN MUSICA");
        lblVolumenMusica.setForeground(Color.WHITE);
        lblVolumenFX = new JLabel("VOLUMEN FX");
        lblVolumenFX.setForeground(Color.WHITE);
        lblSeleccionMusica = new JLabel("SELECCION MUSICA PELEA");
        lblSeleccionMusica.setForeground(Color.WHITE);
        sldVolumenMusica = new JSlider(0, 127, 127);
        sldVolumenFX = new JSlider(0, 127, 127);
        spnSeleccionMusica = new JSpinner(new SpinnerNumberModel(1, 0, 4, 1));	// valor por defecto, minimo, maximo, paso
        ckbMutearMusica = new JCheckBox("Silenciar", false);

        add(lblTitulo);
        add(btnVolver);
        add(lblVolumenMusica);
        add(sldVolumenMusica);
        add(ckbMutearMusica);
        add(lblVolumenFX);
        add(sldVolumenFX);
        add(lblSeleccionMusica);
        add(spnSeleccionMusica);
    }

    public JButton getBtnVolver() { return btnVolver; }
    public static JSlider getSldVolumenMusica() { return sldVolumenMusica; }
    public static JSlider getSldVolumenFX() { return sldVolumenFX; }
    public static JSpinner getSpnSeleccionMusica() { return spnSeleccionMusica; }
    public static JCheckBox getCkbMutearMusica() { return ckbMutearMusica; }
}