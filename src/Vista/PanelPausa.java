package Vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class PanelPausa extends JPanel{

    private final JButton btnReanudar;
    private final JButton btnVolverMenu;
    private final JLabel lblTitulo;
    private boolean escapePresionado = false;

    
    public PanelPausa(){
        final float opacidad = 0.5f;					// Rango 0-1
        setBackground(new Color(0, 0, 0, opacidad));	// Fondo semitransparente
        setOpaque(true);
        setLayout(new GridBagLayout());
        setFocusable(true);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(15,0,15,0);
        gbc.anchor = GridBagConstraints.CENTER;

        lblTitulo = new JLabel("JUEGO EN PAUSA");
        lblTitulo.setFont(new Font("Determination", Font.BOLD, 48));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, gbc);

        btnReanudar = new JButton("REANUDAR PARTIDA");
        btnReanudar. setFont(new Font("Determination", Font.BOLD, 20));
        add(btnReanudar, gbc);

        btnVolverMenu = new JButton("VOLVER AL MENU PRINCIPAL");
        btnVolverMenu.setFont(new Font("Determination", Font.BOLD, 20));
        add(btnVolverMenu, gbc);

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "Pausa_salir");
        getActionMap().put("Pausa_salir", new AbstractAction() {
            @Override 
            public void actionPerformed(ActionEvent e){
                escapePresionado = true;
            }
        });
    }

    public boolean isEscapePresionado(){
        boolean estado = escapePresionado;
        escapePresionado = false;
        return estado;
    }

    public JButton getBtnReanudar(){return btnReanudar;}
    public JButton getBtnVolverMenu(){return btnVolverMenu;}
}

