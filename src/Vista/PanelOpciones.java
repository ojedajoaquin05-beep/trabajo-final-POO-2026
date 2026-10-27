package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class PanelOpciones extends JPanel {

    private static final Color FONDO_TARJETA = new Color(20, 20, 30, 215);
    private static final Color ACENTO = new Color(255, 193, 7);
    private static final Font LETRA = Tipografias.VIDA.deriveFont(26.0f);

    private final Image imagenFondo = new ImageIcon("referencias/Imagen_Juego.jpg").getImage();

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
        setLayout(new GridBagLayout());

        lblTitulo = new JLabel("CONFIGURACION", SwingConstants.CENTER);
        lblTitulo.setFont(Tipografias.VIDA.deriveFont(56.0f));
        lblTitulo.setForeground(ACENTO);

        lblVolumenMusica = etiqueta("VOLUMEN MUSICA");
        lblVolumenFX = etiqueta("VOLUMEN FX");
        lblSeleccionMusica = etiqueta("MUSICA DE PELEA");

        sldVolumenMusica = deslizador();
        sldVolumenFX = deslizador();

        spnSeleccionMusica = new JSpinner(new SpinnerNumberModel(1, 0, 4, 1));	// valor por defecto, minimo, maximo, paso
        spnSeleccionMusica.setFont(LETRA);
        spnSeleccionMusica.setPreferredSize(new Dimension(90, 44));

        ckbMutearMusica = new JCheckBox("SILENCIAR", false);
        ckbMutearMusica.setFont(LETRA);
        ckbMutearMusica.setForeground(Color.WHITE);
        ckbMutearMusica.setOpaque(false);
        ckbMutearMusica.setFocusPainted(false);

        btnVolver = new JButton("Volver al Menu");
        btnVolver.setFont(LETRA);
        btnVolver.setBackground(ACENTO);
        btnVolver.setForeground(Color.BLACK);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 4),
            BorderFactory.createEmptyBorder(10, 30, 10, 30)
        ));

        JPanel tarjeta = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(FONDO_TARJETA);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        tarjeta.setOpaque(false);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACENTO, 4),
            BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));

        agregarFila(tarjeta, 0, lblVolumenMusica, sldVolumenMusica);
        agregarFila(tarjeta, 1, new JLabel(), ckbMutearMusica);
        agregarFila(tarjeta, 2, lblVolumenFX, sldVolumenFX);
        agregarFila(tarjeta, 3, lblSeleccionMusica, spnSeleccionMusica);

        JPanel contenido = new JPanel(new BorderLayout(0, 30));
        contenido.setOpaque(false);
        contenido.add(lblTitulo, BorderLayout.NORTH);
        contenido.add(tarjeta, BorderLayout.CENTER);
        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(btnVolver);
        contenido.add(panelBoton, BorderLayout.SOUTH);

        add(contenido);
    }

    private static JLabel etiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(LETRA);
        lbl.setForeground(Color.WHITE);
        return lbl;
    }

    private static JSlider deslizador() {
        JSlider sld = new JSlider(0, 127, 0);
        sld.setOpaque(false);
        sld.setForeground(ACENTO);
        sld.setPreferredSize(new Dimension(360, 40));
        return sld;
    }

    private static void agregarFila(JPanel tarjeta, int fila, JComponent etiqueta, JComponent control) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = fila;
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        tarjeta.add(etiqueta, gbc);
        gbc.gridx = 1;
        tarjeta.add(control, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public JButton getBtnVolver() { return btnVolver; }
    public void setTextoVolver(String texto) { btnVolver.setText(texto); }
    public static JSlider getSldVolumenMusica() { return sldVolumenMusica; }
    public static JSpinner getSpnSeleccionMusica() { return spnSeleccionMusica; }
    public static JCheckBox getCkbMutearMusica() { return ckbMutearMusica; }
}
