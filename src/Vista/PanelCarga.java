package Vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class PanelCarga extends JPanel {

    public PanelCarga() {
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        Font tipografia;
        try {
            tipografia = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Tipografias/determination.ttf"));
        } catch (Exception e) {
            tipografia = new Font("Arial", Font.BOLD, 12);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(15, 0, 15, 0);

        JLabel lblTexto = new JLabel("CARGANDO...");
        lblTexto.setFont(tipografia.deriveFont(48.0f));
        lblTexto.setForeground(Color.WHITE);
        add(lblTexto, gbc);

        JProgressBar barra = new JProgressBar();
        barra.setIndeterminate(true); 
        barra.setPreferredSize(new Dimension(400, 20));
        add(barra, gbc);
    }
}