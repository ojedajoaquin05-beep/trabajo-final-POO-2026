package Vista;

import Modelo.Puntaje;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

// Pantalla con los mejores puntajes 
public class PanelPuntajes extends JPanel {

    private static final String[] COLUMNAS = {"#", "JUGADOR", "PUNTOS", "FECHA"};

    private final JButton btnVolver;
    private final JLabel lblSinPuntajes;
    // Se sobrescribe para que la tabla sea solo de lectura
    private final DefaultTableModel filas = new DefaultTableModel(COLUMNAS, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };

    public PanelPuntajes() {
        setLayout(new BorderLayout(0, 20));
        setBackground(Color.DARK_GRAY);
        setBorder(BorderFactory.createEmptyBorder(40, 200, 40, 200));

        JLabel lblTitulo = new JLabel("MEJORES PUNTAJES", SwingConstants.CENTER);
        lblTitulo.setFont(Tipografias.VIDA.deriveFont(48.0f));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, BorderLayout.NORTH);

        JTable tabla = new JTable(filas);
        tabla.setFont(Tipografias.VIDA);
        tabla.setRowHeight(40);
        tabla.setFocusable(false);
        tabla.setRowSelectionAllowed(false);
        tabla.getTableHeader().setFont(Tipografias.VIDA);
        tabla.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        tabla.setDefaultRenderer(Object.class, centrado);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout(0, 10));
        panelInferior.setOpaque(false);

        lblSinPuntajes = new JLabel("Todavia no hay puntajes guardados", SwingConstants.CENTER);
        lblSinPuntajes.setFont(Tipografias.VIDA);
        lblSinPuntajes.setForeground(Color.WHITE);
        panelInferior.add(lblSinPuntajes, BorderLayout.NORTH);

        btnVolver = new JButton("Volver al Menu");
        btnVolver.setFont(Tipografias.VIDA);
        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setOpaque(false);
        panelBoton.add(btnVolver);
        panelInferior.add(panelBoton, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);
    }

    // Borra la tabla y la vuelve a llenar con la lista que recibe (ya viene ordenada de mayor a menor)
    public void mostrarPuntajes(List<Puntaje> puntajes) {
        filas.setRowCount(0);
        int posicion = 1;
        for (Puntaje p : puntajes) {
            filas.addRow(new Object[]{posicion, p.getJugador(), p.getPuntos(), p.getFecha()});
            posicion++;
        }
        lblSinPuntajes.setVisible(puntajes.isEmpty());
    }

    public JButton getBtnVolver() { return btnVolver; }
}
